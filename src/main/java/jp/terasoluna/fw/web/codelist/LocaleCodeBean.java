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
 * �R�[�h�A�R�[�h�l(�\��������)�ƃ��P�[�����i����A���A�o���A���g�R�[�h�j
 * ��ێ�����Bean�N���X�B
 *
 */
public class LocaleCodeBean implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -1048229877249057353L;

    /**
     * id��\���t�B�[���h�l�B
     */
    private String id = "";

    /**
     * name��\���t�B�[���h�l�B
     */
    private String name = "";
    
    /**
     * �����\���t�B�[���h
     */
    private String language = "";
    
    /**
     * ����\���t�B�[���h
     */
    private String country = "";

    /**
     * �o���A���g��\���t�B�[���h
     */
    private String variant = "";

	/**
     * id��ݒ肷��B
     *
     * @param id id��\���t�B�[���h�l
     */
    public void setId(String id) {
        this.id = (id == null) ? "" : id;
    }

    /**
     * id��ԋp����B
     *
     * @return id��\���t�B�[���h�l
     */
    public String getId() {
        return this.id;
    }

    /**
     * name��ݒ肷��B
     *
     * @param name name��\���t�B�[���h�l
     */
    public void setName(String name) {
        this.name = (name == null) ? "" : name;
    }

    /**
     * name��ԋp����B
     *
     * @return name��\���t�B�[���h�l
     */
    public String getName() {
        return name;
    }
    
    /**
     * �����ݒ肷��B
     *
     * @param language �����\���t�B�[���h�l
     */
    public void setLanguage(String language) {
        this.language = (language == null) ? "" : language;
    }


    /**
     * �����ԋp����B
     *
     * @return �����\���t�B�[���h�l
     */
    public String getLanguage() {
        return language;
    }

    /**
     * ����ݒ肷��B
     *
     * @param contry ����\���t�B�[���h�l
     */
    public void setCountry(String country) {
        this.country = (country == null) ? "" : country;
    }
    
    /**
     * ����ԋp����B
     *
     * @return ����\���t�B�[���h�l
     */
    public String getCountry() {
        return country;
    }

    /**
     * �o���A���g��ݒ肷��B
     *
     * @param variant �o���A���g��\���t�B�[���h�l
     */
    public void setVariant(String variant) {
        this.variant = (variant == null) ? "" : variant;
    }

    /**
     * �o���A���g��ԋp����B
     *
     * @return �o���A���g��\���t�B�[���h�l
     */
    public String getVariant() {
        return variant;
    }

}
