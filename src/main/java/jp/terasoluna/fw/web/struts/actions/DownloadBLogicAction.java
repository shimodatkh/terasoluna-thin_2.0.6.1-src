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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  本クラスは、ダウンロード処理を行う場合にBLogicの起動を行うクラスである。<br>
 *  Actionから直接BLogicの起動を行う。EJBには対応していない。
 * </p>
 * <p>
 *  BLogic実装クラスを実行するためには、
 *  Bean定義ファイルのBLogicActionのBean定義で、
 *  businessLogicプロパティに目的の業務ロジック実装クラス名を
 *  下記の例のように、&lt;property&gt;要素で指定する。
 *  あわせてstruts-config.xmlの設定例も以下に示す。
 * </p>
 * <p>
 * <strong>Bean定義ファイルの設定</strong>
 * <code><pre>
 * &lt;bean name="/download/downloadAction" scope="prototype"
 *   <strong>class="jp.terasoluna.fw.web.struts.actions.DownloadBLogicAction"</strong>&gt
 *   <strong>&lt;property name="businessLogic"&gt;
 *     &lt;ref bean="downloadBLogic"&gt;&lt;/ref&gt;
 *   &lt;/property&gt;</strong>
 * &lt;/bean&gt
 * &lt;bean id="downloadBLogic" scope="prototype"
 *   <strong>class="jp.terasoluna.sample1.download.blogic.DownloadBLogic"</strong>&gt
 * &lt;/bean&gt
 * </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xmlのBLogicAction設定例</strong>
 * <code><pre>
 *    &lt;action path="/download/downloadAction"
 *       name="_downloadForm"
 *       validate="true"
 *       scope="session"
 *       input="/download/download.jsp"/&gt;
 * </pre></code>
 * </p>
 *
 * @param <P> ビジネスロジックへの入力値となるJavaBeanを指定する
 */
public class DownloadBLogicAction<P> extends BLogicAction<P> {

    /**
     * ログクラス。
     */
    Log log = LogFactory.getLog(DownloadBLogicAction.class);

    /**
     * BLogicResultからWeb層のオブジェクトへの結果反映を行う。
     * <p>
     * このクラスでは<code>resultObject</code>が以下の場合、
     * ダウンロード処理を行う。
     * <ul>
     * <li>{@link AbstractDownloadObject}継承クラスである場合</li>
     * <li>{@link AbstractDownloadObject}継承クラスをプロパティとして１つ持つ場合</li>
     * </ul>
     *
     * @param result BLogicResultインスタンス
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     * @param mappingEx 拡張アクションマッピング
     */
    @Override
    protected void processBLogicResult(BLogicResult result,
            HttpServletRequest request, HttpServletResponse response,
            ActionMappingEx mappingEx) {
        super.processBLogicResult(result, request, response, mappingEx);
        if (result.getResultString() != null) {
        	if (log.isWarnEnabled()) {
        		log.warn("result string must not be set. path :" + request.getPathInfo());
        	}
        	result.setResultString(null);
        }

        FileDownloadUtil.download(result.getResultObject(), request, response);
    }
}
