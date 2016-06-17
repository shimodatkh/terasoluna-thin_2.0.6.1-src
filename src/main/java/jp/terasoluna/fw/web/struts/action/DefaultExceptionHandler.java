/*
 * Copyright (c) 2008 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.MessageResources;

/**
 * 指定したログレベルでログを出力する汎用例外ハンドラ。
 *
 * <p>
 * 例外発生時のログ出力とエラー画面への遷移を行う。<br>
 * </p>
 *
 * <p>
 * 本機能を利用するためには、Struts設定ファイル(struts-config.xml)に
 * グローバル例外、またはアクションレベル例外ハンドラクラスとして指定する。<br>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>ログレベルの指定</legend>
 * プロパティ<strong><u>logLevel</u></strong>に下記の6種類のログレベルを
 * 指定することが可能。
 * <p>
 * <ol>
 *   <li>trace</li>
 *   <li>debug</li>
 *   <li>info</li>
 *   <li>warn</li>
 *   <li>error</li>
 *   <li>fatal</li>
 * </ol>
 * </p>
 * ※指定しない場合は、デフォルトのerrorとなる。<br>
 * ※logLevelを指定する場合は、&lt;exception&gt;タグのclassName属性に
 * <strong><u>ExceptionConfigEx</u></strong>を指定すること。
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>グローバル例外ハンドリング設定例</legend>
 *
 * Struts設定ファイル(struts-config.xml)に以下のように記述する。
 * <pre><code>
 * &lt;struts-config&gt;
 *   …
 *   &lt;global-exceptions&gt;
 *     &lt;exception key="some.key"
 *                path="/system-error"
 *                type="org.springframework.dao.DataAccessException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler"&gt;
 *       &lt;set-property property="module" value="/exp"/&gt;
 *     &lt;/exception&gt;
 *     &lt;exception key="some.key"
 *                path="/system-error"
 *                type="jp.terasoluna.fw.exception.SystemException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.SystemExceptionHandler"&gt;
 *       &lt;set-property property="module" value="/exp"/&gt;
 *     &lt;/exception&gt;
 *     &lt;exception key="some.key"
 *                path="/system-error"
 *                type="java.lang.Exception"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler"&gt;
 *       &lt;set-property property="module" value="/exp"/&gt;
 *       &lt;set-property property="logLevel" value="fatal"/&gt;
 *     &lt;/exception&gt;
 *   &lt;/global-exceptions&gt;
 *   …
 * &lt;struts-config&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>アクションレベル例外ハンドリング設定例</legend>
 *
 * Struts設定ファイル(struts-config.xml)に以下のように記述する。
 * <pre><code>
 * &lt;struts-config&gt;
 *   …
 *   &lt;action path="/start"
 *           type="jp.terasoluna.sample.xxx.SampleAction"
 *           name="_sampleForm"
 *           scope="session"&gt;
 *     &lt;exception key="some.key"
 *                type="jp.terasoluna.sample.xxx.exception.XxxException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler"
 *                path="/sub-forward.do"&gt;
 *       &lt;set-property property="module" value="/sub"/&gt;
 *     &lt;/exception&gt;
 *     &lt;forward name="success" path="/business-error"/&gt;
 *   &lt;/action&gt;
 *   …
 * &lt;struts-config&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * なお、&lt;exception&gt;要素のpath属性で遷移先パスが指定
 * されていない場合は、アクションマッピングのinput属性を
 * 転送先リソースとする。
 * </p>
 *
 * <p>
 * 発生した例外インスタンスは、reuqestに<strong>Globals.EXCEPTION_KEY</strong>のキーで格納される。
 * </p>
 *
 * @see org.apache.struts.action.ExceptionHandler
 * @see jp.terasoluna.fw.web.struts.action.ExceptionConfigEx
 * @see jp.terasoluna.fw.web.struts.action.SystemExceptionHandler
 * @see org.apache.struts.Globals
 */
public class DefaultExceptionHandler extends ExceptionHandler {

    /** ログインスタンス */
    private static final Log logger = LogFactory
            .getLog(DefaultExceptionHandler.class);

    /** ログレベル(TRACE) */
    protected static final String LOG_LEVEL_TRACE = "trace";

    /** ログレベル(DEBUG) */
    protected static final String LOG_LEVEL_DEBUG = "debug";

    /** ログレベル(INFO) */
    protected static final String LOG_LEVEL_INFO = "info";

    /** ログレベル(WARN) */
    protected static final String LOG_LEVEL_WARN = "warn";

    /** ログレベル(ERROR) */
    protected static final String LOG_LEVEL_ERROR = "error";

    /** ログレベル(FATAL) */
    protected static final String LOG_LEVEL_FATAL = "fatal";

    /** メッセージリソース */
    private static MessageResources messages = MessageResources
            .getMessageResources("org.apache.struts.action.LocalStrings");

    /**
     * <p>
     * 例外ハンドリングを行う。
     * </p>
     *
     * @param ex 例外
     * @param eConfig 例外コンフィグ
     * @param mapping アクションマッピング
     * @param formInstance アクションフォーム
     * @param request HTTPリクエスト
     * @param response HTTPレスポンス
     *
     * @return 遷移情報
     *
     * @throws ServletException サーブレット例外
     *
     * @see org.apache.struts.action.ExceptionHandler#execute(
     *  java.lang.Exception,
     *  org.apache.struts.config.ExceptionConfig,
     *  org.apache.struts.action.ActionMapping,
     *  org.apache.struts.action.ActionForm,
     *  javax.servlet.http.HttpServletRequest,
     *  javax.servlet.http.HttpServletResponse
     *  )
     */
    @Override
    public ActionForward execute(Exception ex, ExceptionConfig eConfig,
            ActionMapping mapping, ActionForm formInstance,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        // スーパークラス呼び出し
        ActionForward af = super.execute(ex, eConfig, mapping, formInstance,
                request, response);

        // ExceptionConfigExの場合はlogLevelを取得
        if (eConfig instanceof ExceptionConfigEx) {
            // 遷移先モジュールが設定されているとき、モジュール名を設定する
            af.setModule(((ExceptionConfigEx) eConfig).getModule());

            // logLevelを利用してログ出力
            this.logException(ex, ((ExceptionConfigEx) eConfig).getLogLevel());
        } else {
            // デフォルトレベルでログ出力
            this.logException(ex, null);
        }

        return af;
    }

    /**
     * 例外のログを出力する。
     *
     * @param e 発生した例外
     * @param logLevel ログレベル
     */
    protected void logException(Exception e, String logLevel) {
        this.logException(e, logLevel, messages.getMessage("exception.log"));
    }

    /**
     * 例外のログを出力する。
     *
     * @param logLevel ログレベル
     * @param message 出力するメッセージ
     */
    protected void logException(String logLevel, String message) {
        this.logException(null, logLevel, message);
    }

    /**
     * 例外のログを出力する。
     *
     * @param e 発生した例外
     * @param logLevel ログレベル
     * @param message 出力するメッセージ
     */
    protected void logException(Exception e, String logLevel, String message) {
        if (LOG_LEVEL_TRACE.equalsIgnoreCase(logLevel)) {
            if (getLogger().isTraceEnabled()) {
                // TRACE
                if (e != null) {
                    getLogger().trace(message, e);
                } else {
                    getLogger().trace(message);
                }
            }
        } else if (LOG_LEVEL_DEBUG.equalsIgnoreCase(logLevel)) {
            if (getLogger().isDebugEnabled()) {
                // DEBUG
                if (e != null) {
                    getLogger().debug(message, e);
                } else {
                    getLogger().debug(message);
                }
            }
        } else if (LOG_LEVEL_INFO.equalsIgnoreCase(logLevel)) {
            if (getLogger().isInfoEnabled()) {
                // INFO
                if (e != null) {
                    getLogger().info(message, e);
                } else {
                    getLogger().info(message);
                }
            }
        } else if (LOG_LEVEL_WARN.equalsIgnoreCase(logLevel)) {
            if (getLogger().isWarnEnabled()) {
                // WARN
                if (e != null) {
                    getLogger().warn(message, e);
                } else {
                    getLogger().warn(message);
                }
            }
        } else if (LOG_LEVEL_ERROR.equalsIgnoreCase(logLevel)) {
            if (getLogger().isErrorEnabled()) {
                // ERROR
                if (e != null) {
                    getLogger().error(message, e);
                } else {
                    getLogger().error(message);
                }
            }
        } else if (LOG_LEVEL_FATAL.equalsIgnoreCase(logLevel)) {
            if (getLogger().isFatalEnabled()) {
                // FATAL
                if (e != null) {
                    getLogger().fatal(message, e);
                } else {
                    getLogger().fatal(message);
                }
            }
        } else {
            if (getLogger().isErrorEnabled()) {
                // ERROR（デフォルト）
                if (e != null) {
                    getLogger().error(message, e);
                } else {
                    getLogger().error(message);
                }
            }
        }
    }

    /**
     * 例外のログを出力する。
     *
     * <p>
     * <strong><u>※ここでは出力しない</u></strong>
     * </p>
     *
     * @param e 発生した例外
     * @see org.apache.struts.action.ExceptionHandler#logException(
     *  java.lang.Exception)
     */
    @Override
    protected void logException(Exception e) {
        // ここでは出力しない
    }

    /**
     * ハンドラのロガーを取得する。
     *
     * <p>
     * このクラスを拡張して独自の例外ハンドラを実装する場合、
     * サブクラスでこのメソッドをオーバーライドして自身のロガーを返すこと。
     * </p>
     *
     * @return ロガー
     */
    protected Log getLogger() {
        return logger;
    }

}
