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
 * �R�[�h�ƃR�[�h�l(�\��������)��ێ�����Bean�N���X�B
 *
 */
public class CodeBean implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -6727581752645857389L;

    /**
     * id��\���t�B�[���h�l�B
     */
    private String id = "";

    /**
     * name��\���t�B�[���h�l�B
     */
    private String name = "";

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
     * �R�[�h�ƃR�[�h�l(�\��������)�𔼊p�X�y�[�X�ŘA��������������擾����B
     *
     * <p>�o�͂���錋�ʂ́A���L�̂Ƃ���ɂȂ�B<br>
     *  <code>id</code>+(���p�X�y�[�X)+ <code>name</code>
     * </p>
     *
     * @return �R�[�h�ƃR�[�h�l(�\��������)�𔼊p�X�y�[�X�ŘA������������
     */
    public String getLabel() {
        return id + " " + name;
    }

    /**
     * �R�[�h�ƃR�[�h�l(�\��������)���J���}(",")�ŘA��������������擾����B
     *
     * <p>�o�͂���錋�ʂ́A���L�̂Ƃ���ɂȂ�B<br>
     *  <code>id</code> + , + <code>name</code>
     * </p>
     *
     * @return �R�[�h�ƃR�[�h�l(�\��������)���J���}�ŘA������������
     */
    public String getCodeCommaName() {
        return id + "," + name;
    }
}
