/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.web.struts.actions;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.thin.BlockageControlFilter;
import jp.terasoluna.fw.web.thin.ServerBlockageControlFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
*
* フォワード先の振り分け処理を行う。
*
* <p>
*  リクエストパラメータを用いて、遷移先を決定する。<br>
*  Strutsが提供しているDispatchAction、LookupDispatchActionと仕様が異なるため、注意すること。
* </p>
* <p>
*  まず、リクエストパラメータキーの設定をBean定義ファイルで必要に応じて行う
*  （デフォルト値は&quot;event&quot;）。
* </p>
* <p>
*  下記に記した定義ファイルは
*  リクエストパラメータのキーをcustom-eventに設定する例である。
* </p>
* <p>
* <strong>Bean定義ファイルの設定</strong>
*  <code><pre>
*  &lt;bean name="/dispatch" scope="prototype"
*    class="jp.terasoluna.fw.web.struts.actions.DispatchAction"&gt;
*    <strong>&lt;property name="event"&gt;
*      &lt;value&gt;custom-event&lt;/value&gt;
*    &lt;/property&gt;</strong>
*  &lt;/bean&gt;
*  </pre></code>
* </p>
* <p>
*  次に、遷移文字列を決定する。優先順位は次の通り。
*  <ol>
*   <li>上記で指定したリクエストパラメータキーの値のうち、
*       先頭に&quot;forward_&quot;がついているものについて、
*       &quot;forward_&quot;を取り除いた文字列</li>
*   <li>リクエストパラメータキーの先頭に&quot;forward_&quot;が付いているものについて、
*       &quot;forward_&quot;を取り除いた文字列</li>
*   <li>&quot;default&quot;固定
*   （event=&quot;XXXX&quot;、&quot;forward_XXXX&quot;といった
*       存在し得ない不正な遷移先が指定された場合など）</li>
*  </ol>
*
*  上記の結果、遷移文字列が&quot;#input&quot;
*  であったとき、struts-config.xmlのinput属性が遷移先となる。<br>
*  &quot;#input&quot;ではないとき、struts-config.xmlのforward要素の内容により遷移先が決定する。
* </p>
* <p>
* <strong>struts-config.xmlの設定</strong>
* <code><pre>
*  &lt;action path="/dispatch"
*    name="_sampleForm"
*    scope="session"
*    input="/prev.jsp"&gt;
*    &lt;forward name="regist" path="/userRegist.do"/&gt;
*    &lt;forward name="search" path="/userSearch.do"/&gt;
*    &lt;forward name="update" path="/userUpdate.do"/&gt;
*    &lt;forward name="decide" path="/prev.do"/&gt;
*    &lt;forward name="default" path="/prev.do"/&gt;
*  &lt;/action&gt;
* </pre></code>
* </p>
* <p>
* <strong>JSPでの記述例</strong>
*  <code><pre>
*  ・・・
*  &lt;html:radio property="<strong>custom-event</strong>" value="<strong>forward_regist</strong>"/&gt;
*  &lt;html:radio property="<strong>custom-event</strong>" value="<strong>forward_search</strong>"/&gt;
*  &lt;html:radio property="<strong>custom-event</strong>" value="<strong>forward_update</strong>"/&gt;
*  &lt;html:radio property="<strong>custom-event</strong>" value="<strong>forward_#input</strong>"/&gt;
*  &lt;html:submit property="<strong>forward_decide</strong>" value="決定"/&gt;
*  &lt;html:submit value="戻る"/&gt;
*  ・・・
*  </pre></code>
*  リクエストパラメータの値に"forward_" + 論理フォワード名と定義する。
* </p>
* <p>
*  各操作を行った場合の遷移先は次の通り。
*  <ol>
*   <li>1番目のラジオボタンを押して決定を押下した場合は、
*   遷移文字列は&quot;regist&quot;、遷移先は&quot;/userRegist.do&quot;</li>
*   <li>4番目のラジオボタンを押して決定を押下した場合は、
*   遷移文字列は&quot;#input&quot;、遷移先は&quot;/prev.do&quot;</li>
*   <li>ラジオボタンを選択せず、決定を押下した場合は、
*   遷移文字列は&quot;decide&quot;、遷移先は&quot;/prev.do&quot;</li>
*   <li>戻るを押下した場合、
*   遷移文字列は&quot;default&quot;、遷移先は&quot;/prev.do&quot;</li>
*  </ol>
* </p>
*
*/
public class DispatchAction extends ActionEx {

    /**
     * ログクラス。
     */
    private static Log log = LogFactory.getLog(DispatchAction.class);

    /**
     * リクエストパラメータに振り分け指示がなかった場合の、
     * デフォルトの遷移先の論理フォワード名。
     */
    private static final String FORWARD_DEFAULT = "default";

    /**
     * 振り分け指示を識別するための、
     * リクエストパラメータのキーのプリフィックス。
     */
    private static final String FORWARD_PREFIX = "forward_";

    /**
     * 遷移先を表すプロパティ名。
     */
    private String event = null;

    /**
     * 遷移先を表すプロパティ名を設定する。
     *
     * @param value 遷移先プロパティ名
     */
     public void setEvent(String value) {
         this.event = value;
     }

    /**
     * フォワード先の振り分け処理を行う。
     * <p>
     *   フォワード先を振り分けた後、サーバ閉塞通過フラグを
     *   削除する。<br>
     *   実際の遷移先は、doDetamineForward()が決定している。
     * </p>
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req <code>HTTP</code>リクエスト
     * @param res <code>HTTP</code>レスポンス
     * @return 遷移先情報
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                          ActionForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res) {
        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }
        // リクエストにキャンセルフラグが設定されていることを確認し、
        // cancelled()メソッドを実行するかどうかを決定する。
        if (isCancelled(req)) {
            ActionForward af = cancelled(mapping, form, req, res);
            if (af != null) {
                return af;
            }
        }

        if (event == null) {
            // eventが指定なしの時、"event"をデフォルトとして設定する。
            event = "event";
        }

        String forward = doDetermineForward(req.getParameterMap(), event);

        ActionForward actionForward = null;
        if ("#input".equalsIgnoreCase(forward)) {
            actionForward = new ActionForward(mapping.getInput());
        } else {
            actionForward = mapping.findForward(forward);
        }

        // フォワード先が見つからない場合、"default" で指定されている
        // アクションフォワードを返却する。
        if (actionForward == null) {
            if (log.isWarnEnabled()) {
                log.warn("forward name[" + forward + "] is invalid by user request.");
            }
            actionForward = mapping.findForward(FORWARD_DEFAULT);
        }

        // フォワード先で閉塞チェック等を有効とするため
        // THRU_FILTERフラグを削除する
        // サーバ閉塞
        req.removeAttribute(ServerBlockageControlFilter
            .SERVER_BLOCKAGE_THRU_KEY);
        // 業務閉塞
        req.removeAttribute(BlockageControlFilter.BLOCKAGE_THRU_KEY);

        if (log.isDebugEnabled()) {
            log.debug("forward = " + forward + " (" + ((actionForward == null)
                ? "null" : actionForward.getPath()) + ")");
        }
        return actionForward;
    }

    /**
     * リクエストパラメータに基づいてフォワード先をディスパッチする。
     * paramsがnullの場合は、デフォルトの文字列を返却する。
     *
     * @param params リクエストパラメータ（マップ形式）
     * @param event アクションマッピングに指定されたイベント名
     * @return 振り分け先の論理フォワード名
     */
    protected String doDetermineForward(Map params, @SuppressWarnings("hiding") String event) {
        if (params != null) {
            if (exists(params, event)) {
                String[] eventValues = (String[]) params.get(event);
                for (int i = 0; i < eventValues.length; i++) {
                    if (eventValues[i].startsWith(FORWARD_PREFIX)) {
                        return eventValues[i].substring(FORWARD_PREFIX.length());
                    }
                }
            }
            Iterator iter = params.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                if (key.startsWith(FORWARD_PREFIX)) {
                    String forward = key.substring(FORWARD_PREFIX.length());
                    if(forward.endsWith(".x") || forward.endsWith(".y")){
                        forward = forward.substring(0, forward.length() - 2);
                    }
                    return forward;
                }
            }
        }
        return FORWARD_DEFAULT;
    }


    /**
     * リクエストパラメータに、nameで指定した名称のパラメータが
     * 存在しているかを判定する。
     *
     * @param params リクエストパラメータ（マップ形式）
     * @param name リクエストパラメータ名
     * @return リクエストパラメータ名が存在しているならば <code>true</code>
     */
    protected boolean exists(Map params, String name) {
        return params.containsKey(name);
    }

    /**
     * リクエストにキャンセルフラグが設定されている場合の遷移先を
     * 決定する。注意点として、ActionForwardはnullとして返却されるため、
     * キャンセル時の遷移先は、
     * このクラスを継承したクラスのオーバライドメソッドで実装する必要がある。
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @return アクションフォワード
     */
    protected ActionForward cancelled(ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        return null;
    }

}
