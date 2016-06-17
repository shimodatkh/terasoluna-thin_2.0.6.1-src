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

package jp.terasoluna.fw.web.struts.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * SpringのメッセージソースをStrutsから利用するMessageResources実装クラス。<br>
 * </p>
 * <p>
 * SpringMessageResourcesFactoryをstruts-config.xmlのmessage-resources要素
 * のfactory属性に設定する。<br>
 * <ul>
 *   <li>parameter属性値にMessageSourceのBeanIDを指定した場合は、
 *       指定したMessageSourceが使われる。<br>
 *   <strong>※このとき指定したBeanが見つからない場合や、取得したBeanが
 *   MessageSourceインスタンスでない場合はAPサーバ起動時に例外が発生する。</strong>
 *   </li>
 *   <li>parameter属性値を省略した場合はデフォルトの "messageSource" が使われる。
 *   </li>
 * </ul>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>struts-config.xml設定例（※parameter省略時）</legend>
 * <pre><code>
 * &lt;message-resources parameter=""
 *   factory="jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory"/&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>struts-config.xml設定例（※parameter指定時）</legend>
 * <pre><code>
 * &lt;message-resources parameter="hogeMessageSource"
 *   factory="jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory"/&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>Bean定義ファイル設定例</legend>
 * ResourceBundleMessageSourceを利用する場合、basenamesプロパティには
 * メッセージプロパティファイルを指定する。
 * <pre><code>
 * &lt;bean id="messageSource"
 *       class="org.springframework.context.support.ResourceBundleMessageSource"&gt;
 *   &lt;property name="basenames" value="MessageResources"/&gt;
 * &lt;/bean&gt;
 * </code></pre>
 * 
 * DataSourceMessageSourceを利用するとDBメッセージが扱えるようになる。
 * <pre><code>
 * &lt;bean id="messageSource"
 *       class="jp.terasoluna.fw.message.DataSourceMessageSource"&gt;
 *   &lt;property name="dbMessageResourceDAO" ref="dbMessageResourceDAO"/&gt;
 * &lt;/bean&gt;
 * </code></pre> 
 * </fieldset>
 * </p>
 * 
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>第２メッセージソースを定義する場合のBean定義ファイル設定例</legend>
 * <pre><code>
 * &lt;bean id="messageSource"
 *       class="jp.terasoluna.fw.message.DataSourceMessageSource"&gt;
 *   &lt;property name="dbMessageResourceDAO" ref="dbMessageResourceDAO"/&gt;    
 *   &lt;property name="parentMessageSource" ref="parentSource"/&gt;
 * &lt;/bean&gt;
 * 
 * &lt;bean id="parentSource"
 *       class="org.springframework.context.support.ResourceBundleMessageSource"&gt;
 *   &lt;property name="basenames" value="MessageResources"/&gt;
 * &lt;/bean&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * このクラスは、
 * Won't FixとなっているStrutsのバグ STR-2172(https://issues.apache.org/jira/browse/STR-2172)
 * を回避する手段を有している。<br>
 * 詳細は、{@link MessageFormatCacheMapFactory} を参照。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory
 * @see jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory
 * @see org.springframework.context.support.ResourceBundleMessageSource
 * @see jp.terasoluna.fw.message.DataSourceMessageSource
 *
 */
public class SpringMessageResources extends MessageResources {

    /** 
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 8870342287587564386L;

    /**
     *  ログインスタンス
     */
    private static Log log = LogFactory.getLog(SpringMessageResources.class);

    /** 
     * エラーメッセージキー
     */
    private static final String ERR_BEAN_EXCEPTION = "errors.message.bean.exception";

    /** 
     * アプリケーションコンテキスト
     */
    private WebApplicationContext context = null;

    /** 
     * Springのメッセージソース
     */
    private MessageSource messageSource = null;

    /**
     * 指定されたパラメータによってSpringMessageResourcesを生成する。
     *
     * @param factory メッセージリソースファクトリ
     * @param config コンテナから取得するMessageSourceのBean名
     *               （省略時はデフォルトの"messageSource"）
     */
    public SpringMessageResources(MessageResourcesFactory factory, String config) {
        super(factory, config);
        replaceMessageFormatCache();
        this.context = ContextLoader.getCurrentWebApplicationContext();

        initMessageSource();
    }

    /**
     * 指定されたパラメータによってSpringMessageResourcesを生成する。
     *
     * @param factory メッセージリソースファクトリ
     * @param config コンテナから取得するMessageSourceのBean名
     *               （省略時はデフォルトの"messageSource"）
     * @param returnNull <code>org.apache.struts.util.MessageResources</code>
     *                   クラスの <code>returnNull</code>。
     *                   <code>false</code> 指定時、キーに該当するメッセージが
     *                   存在しない場合???Locale.key???という形式でメッセージを
     *                   返却する。
     */
    public SpringMessageResources(MessageResourcesFactory factory,
            String config, boolean returnNull) {
        super(factory, config, returnNull);
        replaceMessageFormatCache();
        this.context = ContextLoader.getCurrentWebApplicationContext();

        initMessageSource();
    }

    /**
     * MessageFormatキャッシュ(formats)のインスタンス差し替えを行う。
     * <p>
     * Strutsのバグ STR-2172回避用のキャッシュオブジェクトに差し替える。
     * </p>
     * @see MessageFormatCacheMapFactory
     */
    private void replaceMessageFormatCache() {
        HashMap<String, MessageFormat> map = MessageFormatCacheMapFactory
                .getInstance();
        if (map != null) {
            formats = map;
        }
    }

    /**
     * MessageSourceの初期化を行う。
     */
    private void initMessageSource() {
        if (this.context != null) {
            if (config != null && !("".equals(config))) {
                // パラメータに指定されたBeanIDを取得する
                try {
                    this.messageSource = (MessageSource) this.context.getBean(
                            config, MessageSource.class);
                } catch (BeansException e) {
                    if (log.isErrorEnabled()) {
                        StringBuilder mes = new StringBuilder();
                        mes.append(config);
                        mes.append(" is not found");
                        mes.append(" or it is not MessageSource instance.");
                        log.error(mes.toString());
                    }
                    throw new SystemException(e, ERR_BEAN_EXCEPTION);
                }

                if (log.isDebugEnabled()) {
                    StringBuilder mes = new StringBuilder();
                    mes.append(config);
                    mes.append(" MessageSource is used.");
                    log.debug(mes.toString());
                }
            }

            // デフォルトのMessageSourceを採用（ApplicationContext自身）
            if (this.messageSource == null) {
                this.messageSource = this.context;
                if (log.isDebugEnabled()) {
                    log.debug("Default MessageSource is used.");
                }
            }
        } else {
            if (log.isWarnEnabled()) {
                log.warn("ApplicationContext is not found.");
            }
        }
    }

    /**
     * 指定されたキーとロケールにもとづきメッセージ文言を取得する。
     *
     * @see org.apache.struts.util.MessageResources#getMessage(java.util.Locale, java.lang.String)
     */
    @Override
    public String getMessage(Locale locale, String key) {
        if (this.messageSource != null) {
            String retMessage = null;

            try {
                retMessage = this.messageSource.getMessage(key, null, locale);
            } catch (NoSuchMessageException e) {
                // 何もしない
            }
            
            if (retMessage != null) {
                if (log.isDebugEnabled()) {
                    StringBuilder mes = new StringBuilder();
                    mes.append("key:[");
                    mes.append(key);
                    mes.append("] locale:[");
                    mes.append(locale);
                    mes.append("] message:[");
                    mes.append(retMessage);
                    mes.append("]");
                    log.debug(mes.toString());
                }
                return retMessage;
            }
        }

        // メッセージが存在しない場合は、returnNullの設定の有無に従って返却する
        if (!returnNull) {
            StringBuilder mes = new StringBuilder();
            mes.append("???");
            mes.append(messageKey(locale, key));
            mes.append("???");
            return mes.toString();
        }
        return null;
    }

}
