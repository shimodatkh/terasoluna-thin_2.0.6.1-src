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

package jp.terasoluna.fw.web.codelist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.object.MappingSqlQuery;


/**
 * コードリスト情報の初期化をデータベースを用いて行う、
 * {@link jp.terasoluna.fw.web.codelist.AbstractMultilingualCodeListLoader}、
 * {@link jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader}
 * 実装クラスである。
 *
 * <p>
 * このクラスを用いてコードリストを生成する場合は、
 * dataSource属性に使用するデータソースを指定したあと、
 * init-method属性にloadを指定し、load()メソッドを最初に実行する必要がある。<br>
 * また、本クラスはスレッドセーフではないため、
 * 業務閉塞機能もしくはサーバ閉塞機能等を利用し、
 * コードリストへの参照が行われない状態にてreload()メソッドを
 * 実行するように閉塞設計を行う必要がある。<br>
 * スレッドセーフとする場合、コードリスト参照時に排他制御がかかり
 * 性能劣化をもたらす原因となり得るため注意が必要である。
 * </p>
 *
 * <p>
 * コードリストをデータベースから取得するための SQL は、Bean定義ファイルから
 * 設定する。
 * </p>
 * <p>
 * <strong>Bean定義ファイルの設定例。</strong>
 * <br>
 * データソースをTerasolunaDataSourceとして定義している場合。
 * </p>
 * <p>
 * <code><pre>
 * &lt;bean id=<strong>&quot;loader1&quot;</strong>
 *       class=&quot;jp.terasoluna.fw.web.codelist.DBCodeListLoader&quot;
 *       init-method=&quot;load&quot;&gt;
 *   &lt;property name=&quot;defaultLocale&quot; value=&quot;ja&quot; /&gt;
 *   &lt;property name=&quot;dataSource&quot;&gt;
 *     &lt;ref bean=<strong>&quot;TerasolunaDataSource&quot;</strong>/&gt;
 *   &lt;/property&gt;
 *   &lt;property name=&quot;sql&quot;&gt;
 *     &lt;value&gt;<strong>SELECT KEY, VALUE, LANGUAGE, COUNTRY, VARIANT FROM CODE_LISTS</strong>&lt;/value&gt;
 *   &lt;/property&gt;
 *
 * &lt;/bean&gt;
 * </code></pre>
 * </p>
 * <p>
 *  SQLで取得されたコードリストを格納するクラス LocaleCodeBean は、属性
 *  id 、 name、language、country、variant を持ち、
 *   SELECT 文で指定されたカラムの順番で LocaleCodeBean に
 *  格納される。<br>
 *  上記例では、 KEY が id 、VALUE が name、LANGUAGEがlanguage、
 *  、COUNTRYがcountry、VARIANTがvariant の順番で格納される。<br>
 *  取得されたカラムが2つに満たない場合、例えば上記例で
 *   KEY のみ取得した場合は、 name、language、country、variant に
 *   null が格納される。
 *   SELECT 文で取得したカラムが6つ以上であるときは、
 *  6つめ以降のカラムは無視される。<br>
 *  コードリストが国際化対応されていない場合は、
 *  LANGUAGE、COUNTRY、VARIANTカラムを取得しなくてよい。
 * </p>
 * <p>
 * SQLで取得された結果(LocaleCodeBean)からコードリスト情報として保持する
 * Map&lt;Locale,List&lt;CodeBean&gt;&gt;を作成する。
 * MapのキーLocaleはLocaleCodeBeanのlanguage、country、variantから作成する。
 * </p>
 * <p>
 *  JSP 内において、 Struts の&lt;logic:iterate&gt;
 *  タグの name 属性や、&lt;html:options&gt;タグの
 *  collection 属性をbean名として参照する。<br>
 *  以下は、bean名を &quot;loader1&quot; として、
 *  &lt;html:options&gt; の collection属性に指定
 *  した場合の例である。
 * </p>
 * <strong>JSP 内でのコードリスト使用例。</strong><br>
 * <p>
 *  <code><pre>
 *  &lt;ts:defineCodeList id=<b>"loader1"</b> /&gt;
 *  …
 *  &lt;html:select property="selectOptions"&gt;
 *    &lt;html:options collection=<b>"loader1"</b>
 *                  labelProperty="name"
 *                  property="id"/&gt;
 *  &lt;/html:select&gt;
 *  </pre></code>
 * </p>
 * コードリストのリロードについては、
 * {@link
 *  jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction}
 *
 * を、またコードリストのJSP内での使用方法は、
 * {@link
 *  jp.terasoluna.fw.web.taglib.DefineCodeListTag}
 * と、
 * {@link
 *  jp.terasoluna.fw.web.taglib.WriteCodeCountTag}
 * を参照のこと。
 *
 * </p>
 * @see jp.terasoluna.fw.web.codelist.CodeBean
 * @see
 *    jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 */
public class DBCodeListLoader extends AbstractMultilingualCodeListLoader implements ReloadableCodeListLoader {

    /**
     * ログクラス。
     */
    private Log log = LogFactory.getLog(DBCodeListLoader.class);
    
    /**
     * コードリストを取得するためのSQL。
     */
    private String sql = null;

    /**
     * DB接続に使用するデータソース。
     */
    private DataSource dataSource = null;

    /**
     * コードリストに使用するロックオブジェクト。
     */
    private final Object lockObject = new Object();

	/**
     * コードリストの初期化を行う。
     *
     * <p>
     * {@link #loadCodeList()}を使って既に設定されている
     * dataSourceと指定されたSQL文を元にコードリストを生成する。
     * コードリストがすでに存在する場合には、何も行わない。
     * </p>
     */
    public void load() {
        if (log.isDebugEnabled()) {
            log.debug("load() called.");
        }
        if (localeMap != null) {
            // すでにコードリストが読み込まれているときには何もしない。
            return;
        }
        // コードリストを読み込む。
        loadCodeList();
    }

    /**
     * コードリストの再読み込みを行う。
     *
     * <p>
     * codeListsの同期を取った上で{@link #loadCodeList()}
     * を呼び出す。業務閉塞中の管理者業務にて利用することが望ましい。
     * </p>
     */
    public void reload() {
        if (log.isDebugEnabled()) {
            log.debug("reload() called.");
        }
        if (localeMap == null) {
            // codeListsが存在しない場合はそのままloadCodeListを呼ぶ。
            loadCodeList();
        } else {
            synchronized (lockObject) {
                // codeListsが存在する場合、
                // 読み込み中に再読み込みを防ぐため同期を取る。
                loadCodeList();
            }
        }
    }

    /**
     * コードリストを読み込む。
     *
     * dataSourceと指定されたSQL文を元にコードリストを生成する。
     */
    @SuppressWarnings("unchecked")
    protected void loadCodeList() {
        if (log.isDebugEnabled()) {
            log.debug("loadCodeList() called.");
        }

        Map<Locale, List<CodeBean>> resultMap = 
                                        new HashMap<Locale, List<CodeBean>>();

        // データベースから値をコードリストを取得する。
        MappingSqlQuery query = new DBCodeListQuery(dataSource, sql);
        query.compile();
        List<LocaleCodeBean> resultList = query.execute();
        for (LocaleCodeBean lcb : resultList) {
            CodeBean cb = new CodeBean();
            cb.setId(lcb.getId());
            cb.setName(lcb.getName());

            Locale curLocale = createLocale(lcb);
            if (curLocale == null) {
                curLocale = defaultLocale;
            }

            List<CodeBean> codeList = resultMap.get(curLocale);
            // コードリストが存在しない場合、初期化する。
            if (codeList == null) {
                codeList = new ArrayList<CodeBean>();
            }
            codeList.add(cb);


            resultMap.put(curLocale, codeList);
        }

        for (Entry<Locale, List<CodeBean>> entry : resultMap.entrySet()) {
            resultMap.put(entry.getKey(), 
                             Collections.unmodifiableList(entry.getValue()));
        }

        localeMap = Collections.unmodifiableMap(resultMap);
    }
    
    /**
     * Localeオブジェクトを生成する。<br>
     * 言語コード、国コード、バリアントコードからLocaleオブジェクトを生成する。
     * 言語コードが与えられていない場合は、デフォルトロケールの言語コードのみ
     * を格納し、Localeオブジェクトを生成する。
     *
     * @param bean コードリスト
     *
     * @return
     *      言語コード、国コード、バリアントコードを格納したLocaleオブジェクト。
     */
    Locale createLocale(LocaleCodeBean bean) {
        return createLocale(bean.getLanguage(), bean.getCountry(), bean
                .getVariant());
    }

    /**
     * dataSourceを取得する。
     *
     * @return dataSource を表すフィールド値。
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * dataSourceを設定する。
     *
     * @param dataSource dataSourceを表すフィールド値。
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * コードリストを取得するSQLを取得する。
     * @return SQL文
     */
    public String getSql() {
        return sql;
    }

    /**
     * コードリストを取得するSQLを設定する。
     * @param sql SQL文
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

}
