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

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 国際化対応した{@link jp.terasoluna.fw.web.codelist.CodeListLoader}を
 * 実装するための抽象クラス。
 *
 * <p>
 * このクラスは国際化されたコードリスト情報をロケールと
 * コードリストのマップ形式で保持する。
 * また、このマップからロケールを指定して
 * コードリストを取得するメソッドを実装している。
 * <br> 
 * コードリスト情報の読込みは、このクラスを継承したサブクラスで実装する。
 * 
 * @see DBCodeListLoader
 * @see MappedCodeListLoader
 * 
 */
public abstract class AbstractMultilingualCodeListLoader implements CodeListLoader {

    /**
     * ログクラス。
     */
    private Log log = LogFactory.getLog(AbstractMultilingualCodeListLoader.class);
    
    /**
     * 国際化対応コードリスト情報マップ
     * @see jp.terasoluna.fw.web.codelist.CodeBean
     */
    protected Map<Locale, List<CodeBean>> localeMap = null;

    
    /**
     * ロケールが指定されていない場合のデフォルトロケール。 コードリスト内
     * でロケールが指定されていない場合、 このロケールが設定される。
     * デフォルトではサーバー側JVMの言語コードのみをロケールとして使用する。
     */
    protected Locale defaultLocale
                            = new Locale(Locale.getDefault().getLanguage());

    /**
     * デフォルトロケールを設定する。
     * 
     * @param defaultLocale デフォルトロケール
     */
    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /** 
     * コードリストを取得する。
     *
     * コードリストは {@link CodeBean} の配列として取得できる。<br>
     * ※コードリストは原則としてアプリケーション中で一意となる情報である。
     * このメソッドをオーバーライドする場合は、業務ロジックなどで
     * コードリストの内容が編集されても影響がないように実装する必要がある。
     *
     * @return コードリスト
     */
    public CodeBean[] getCodeBeans() {
        return createCodeBeans(defaultLocale);
    }

    /**
     * ロケールを指定してコードリストを取得する。
     * <p>
     * コードリストは {@link CodeBean} の配列として取得できる。<br>
     * 引数で指定したロケールに対応するコードリストを取得できない場合、
     * デフォルトロケールに対応するコードリストを取得する。
     * 
     * ※コードリストは原則としてアプリケーション中で一意となる情報である。
     * このメソッドをオーバーライドする場合は、業務ロジックなどで
     * コードリストの内容が編集されても影響がないように実装する必要がある。
     *
     * @param locale ロケール
     * @return コードリスト
     */
    public CodeBean[] getCodeBeans(Locale locale) {
        
        if (log.isDebugEnabled()) {
            log.debug("getCodeBeans(" + locale + ") called.");
        }

        CodeBean[] result = createCodeBeans(locale);

        // コードリストが取得できなかった場合、デフォルトロケールで再作成
        if (locale != null && result.length == 0) {
            result = createCodeBeans(defaultLocale);
        }
        return result;
    }
    
    /**
     * ロケールに対応するコードリストを作成する。
     * <p>
     * 引数で指定したロケールに対応するコードリストが存在しない場合、
     * 上位のロケールに対応するコードリストを作成する。
     * 
     * @param locale ロケール
     * @return コードリスト
     */
    protected CodeBean[] createCodeBeans(Locale locale) {
        
        if (log.isDebugEnabled()) {
            log.debug("createCodeBeans(" + locale + ") called.");
        }
        
        if (localeMap == null) {
            if (log.isDebugEnabled()) {
                log.debug("field codeListsMap is null.");
            }
            // codeListsMapが存在しないときは空の配列を返す。
            return new CodeBean[0];
        }

        if (locale == null) {
            if (log.isDebugEnabled()) {
                log.debug("arg locale is null. replace locale default : "
                        + defaultLocale);
            }
            if (defaultLocale == null) {
                throw new IllegalStateException("Default locale is null.");
            }
            locale = defaultLocale;
        }

        List<CodeBean> codeLists = localeMap.get(locale);
        
        // コードリストが取得できない場合、上位のロケールで再作成
        if (codeLists == null) {
            if (locale.getVariant().length() > 0) {
                return createCodeBeans(new Locale(locale.getLanguage(), locale
                        .getCountry()));
            } else if (locale.getCountry().length() > 0) {
                return createCodeBeans(new Locale(locale.getLanguage()));
            }

            // codeListsが存在しないときは空の配列を返す。
            return new CodeBean[0];
        }

        CodeBean[] cb = new CodeBean[codeLists.size()];
        for (int i = 0; i < codeLists.size(); i++) {
            cb[i] = new CodeBean();
            cb[i].setId(codeLists.get(i).getId());
            cb[i].setName(codeLists.get(i).getName());
        }
        return cb;
    }
    
    /**
     * 言語コード、国コード、バリアントコードからロケールを作成する。
     * <p>
     * 言語コードがnullまたは空文字の場合はデフォルトロケールを返却する。
     * 
     * @param language 言語コード
     * @param country 国コード
     * @param variant バリアントコード
     * @return ロケール
     */
    protected Locale createLocale(String language, String country,
            String variant) {
        
        // 言語コードが設定されていない場合、デフォルトロケールを返却
        if (language == null || language.length() == 0) {
            return defaultLocale;
        }
        // 国コードが設定されていない場合、言語コードで作成したロケールを返却
        if (country == null || country.length() == 0) {
            return new Locale(language);
        }
        // バリアントコードが設定されていない場合、
        // 言語コード、国コードで作成したロケールを返却
        if (variant == null || variant.length() == 0) {
            return new Locale(language, country);
        }
        return new Locale(language, country, variant);
    }

}
