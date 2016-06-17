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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * コードリスト情報の初期化を <code>Map</code> で行う、
 * {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 * 実装クラス。<br>
 * <br>
 * このクラスを用いてコードリストを形成する場合は、
 * {@link #setCodeListMap(Map)} メソッドでコードリスト情報を <code>Map</code>
 * 形式で与えた後、{@link #load()} を実行する必要がある。<br>
 * <h5>Springフレームワークでの使用例。</h5>
 * <code><pre>
 * &lt;bean id="reader1"
 *       class="jp.terasoluna.fw.web.codelist.MappedCodeListLoader"
 *       init-method="load"&gt;
 *     &lt;property name="codeListMap"&gt;
 *         &lt;map&gt;
 *             &lt;entry key="001"&gt;
 *                 &lt;value&gt;value001&lt;/value&gt;
 *             &lt;/entry&gt;
 *             &lt;entry key="002"&gt;
 *                 &lt;value&gt;value002&lt;/value&gt;
 *             &lt;/entry&gt;
 *             &lt;entry key="003"&gt;
 *                 &lt;value&gt;value003&lt;/value&gt;
 *             &lt;/entry&gt;
 *         &lt;/map&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * <h5>Springフレームワークでの使用例(国際化したコードリストを定義)。</h5>
 * <code><pre>
 * &lt;bean id="reader1"
 *       class="jp.terasoluna.fw.web.codelist.MappedCodeListLoader"
 *       init-method="load"&gt;
 *     &lt;property name="codeListMap"&gt;
 *         &lt;map&gt;
 *             &lt;entry key="ja"&gt;
 *                 &lt;map&gt;
 *                    &lt;entry key="001"&gt;
 *                         &lt;value&gt;値001&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="002"&gt;
 *                         &lt;value&gt;値002&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="003"&gt;
 *                         &lt;value&gt;値003&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                 &lt;/map&gt;
 *             &lt;/entry&gt;
 *         &lt;/map&gt;
 *         &lt;map&gt;
 *             &lt;entry key="en"&gt;
 *                 &lt;map&gt;
 *                    &lt;entry key="001"&gt;
 *                         &lt;value&gt;value001&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="002"&gt;
 *                         &lt;value&gt;value002&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="003"&gt;
 *                         &lt;value&gt;value003&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                 &lt;/map&gt;
 *             &lt;/entry&gt;
 *         &lt;/map&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 *
 */
public class MappedCodeListLoader extends AbstractMultilingualCodeListLoader {

    /**
     * コードリスト情報を初期化時に与えるための <code>Map</code> 。
     */
    private Map<String, ?> codeListMap = null;

    /**
     * コードリスト初期化情報 <code>Map</code> を取得する。
     *
     * @return コードリスト初期化情報 <code>Map</code>
     */
    public Map getCodeListMap() {
        return codeListMap;
    }

    /**
     * コードリスト初期化情報 <code>Map</code> を設定する。
     *
     * @param codeListMap コードリスト初期化情報 <code>Map</code>
     */
    public void setCodeListMap(Map<String, ?> codeListMap) {
        this.codeListMap = codeListMap;
    }

    /**
     * コードリストの初期化を行う。<br>
     * <br>
     * 既に設定されている <code>codeListMap</code> の情報から
     * {@link CodeBean} を生成する。
     */
    public void load() {
        if (localeMap != null) {
            return;
        }

        Map<Locale, List<CodeBean>> localeCodeListMap =
                                     new HashMap<Locale, List<CodeBean>>();
        if (codeListMap != null) {
            Iterator<String> it = codeListMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object value = codeListMap.get(key);

                // valueがMapの場合、ロケールのコードリストを作成
                // valueがStringの場合、デフォルトロケールのコードリストを作成
                if (value instanceof Map) {
                    Map clMap = (Map) value;
                    if (clMap == null) {
                        continue;
                    }

                    List<CodeBean> list = new ArrayList<CodeBean>();
                    Iterator clMapIt = clMap.keySet().iterator();
                    while (clMapIt.hasNext()) {
                        String id = (String) clMapIt.next();
                        CodeBean cb = new CodeBean();
                        cb.setId(id);
                        cb.setName((String) clMap.get(id));
                        list.add(cb);
                    }
                    localeCodeListMap.put(createLocale(key), list);
                } else if (value instanceof String) {

                    Locale locale = defaultLocale;
                    List<CodeBean> list = localeCodeListMap.get(locale);
                    if (list == null) {
                        list = new ArrayList<CodeBean>();
                    }

                    CodeBean cb = new CodeBean();
                    cb.setId(key);
                    cb.setName((String) value);
                    list.add(cb);

                    localeCodeListMap.put(locale, list);
                }
                // Map、String以外の場合、コードリスト情報として保持しない
            }

            for (Entry<Locale, List<CodeBean>> entry : localeCodeListMap
                    .entrySet()) {
                localeCodeListMap.put(entry.getKey(), Collections
                        .unmodifiableList(entry.getValue()));
            }
        }

        localeMap = Collections.unmodifiableMap(localeCodeListMap);
    }
	
    /**
     * ロケール文字列からロケールオブジェクトを作成する。
     * 
     * @param locale
     *            ロケール文字列
     * @return ロケールオブジェクト
     */
    Locale createLocale(String locale) {
        if (locale == null || locale.length() == 0) {
            return defaultLocale;
        }

        Locale result;
        String[] localeElements = locale.split("_", 3);
        switch (localeElements.length) {
        case 1:
            result = createLocale(localeElements[0], "", "");
            break;
        case 2:
            result = createLocale(localeElements[0], localeElements[1], "");
            break;
        case 3:
            result = createLocale(localeElements[0], localeElements[1],
                    localeElements[2]);
            break;
        default:
            result = Locale.getDefault();
        }
        return result;
    }
}
