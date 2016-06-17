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

import java.io.Serializable;
/**
 * コード、コード値(表示文字列)とロケール情報（言語、国、バリアントコード）
 * を保持するBeanクラス。
 *
 */
public class LocaleCodeBean implements Serializable {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -1048229877249057353L;

    /**
     * idを表すフィールド値。
     */
    private String id = "";

    /**
     * nameを表すフィールド値。
     */
    private String name = "";
    
    /**
     * 言語を表すフィールド
     */
    private String language = "";
    
    /**
     * 国を表すフィールド
     */
    private String country = "";

    /**
     * バリアントを表すフィールド
     */
    private String variant = "";

	/**
     * idを設定する。
     *
     * @param id idを表すフィールド値
     */
    public void setId(String id) {
        this.id = (id == null) ? "" : id;
    }

    /**
     * idを返却する。
     *
     * @return idを表すフィールド値
     */
    public String getId() {
        return this.id;
    }

    /**
     * nameを設定する。
     *
     * @param name nameを表すフィールド値
     */
    public void setName(String name) {
        this.name = (name == null) ? "" : name;
    }

    /**
     * nameを返却する。
     *
     * @return nameを表すフィールド値
     */
    public String getName() {
        return name;
    }
    
    /**
     * 言語を設定する。
     *
     * @param language 言語を表すフィールド値
     */
    public void setLanguage(String language) {
        this.language = (language == null) ? "" : language;
    }


    /**
     * 言語を返却する。
     *
     * @return 言語を表すフィールド値
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 国を設定する。
     *
     * @param contry 国を表すフィールド値
     */
    public void setCountry(String country) {
        this.country = (country == null) ? "" : country;
    }
    
    /**
     * 国を返却する。
     *
     * @return 国を表すフィールド値
     */
    public String getCountry() {
        return country;
    }

    /**
     * バリアントを設定する。
     *
     * @param variant バリアントを表すフィールド値
     */
    public void setVariant(String variant) {
        this.variant = (variant == null) ? "" : variant;
    }

    /**
     * バリアントを返却する。
     *
     * @return バリアントを表すフィールド値
     */
    public String getVariant() {
        return variant;
    }

}
