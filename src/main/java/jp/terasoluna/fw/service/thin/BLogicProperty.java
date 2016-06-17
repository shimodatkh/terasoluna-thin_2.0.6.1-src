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

package jp.terasoluna.fw.service.thin;

import java.io.Serializable;

/**
 * �r�W�l�X���W�b�N���o�͏��̌X�̃v���p�e�B����ێ�����N���X�B
 *
 * <p>
 *  BLogicIOPlugIn�Ńr�W�l�X���W�b�N���o�͏���`�t�@�C���ł���
 *  blogic-io.xml����r�W�l�X���W�b�N�ɑ΂���
 *  ����JavaBean�y�яo��JavaBean�̃v���p�e�B�ݒ��ێ�����B<br>
 *  ���̃N���X���܂ރr�W�l�X���W�b�N���o�͏��̐ݒ�ɂ��ẮA
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  ���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicProperty implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 7045786022774532390L;

    /**
     * Web�w�̃I�u�W�F�N�g�̃v���p�e�B���B
     */
    private String property = null;

    /**
     * �r�W�l�X���W�b�N���ň�����v���p�e�B���B
     */
    private String blogicProperty = null;

    /**
     * ���͌��A�܂��͏o�͐��Web�w�̃I�u�W�F�N�g�����������ʂ���l�B
     */
    private String sourceOrDest = null;

    /**
     * �v���p�e�B����ݒ肷��B
     *
     * @param property �v���p�e�B��
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * �v���p�e�B�����擾����B
     *
     * @return �v���p�e�B��
     */
    public String getProperty() {
        return property;
    }

    /**
     * �r�W�l�X���W�b�N���ň�����v���p�e�B����ݒ肷��B
     *
     * @param blogicProperty �v���p�e�B��
     */
    public void setBLogicProperty(String blogicProperty) {
        this.blogicProperty = blogicProperty;
    }

    /**
     * �r�W�l�X���W�b�N���ň�����v���p�e�B�����擾����B
     *
     * @return �v���p�e�B��
     */
    public String getBLogicProperty() {
        return blogicProperty;
    }

    /**
     * ���͌���ݒ肷��B
     *
     * @param source ���͌�
     */
    public void setSource(String source) {
        this.sourceOrDest = source;
    }

    /**
     * ���͌����擾����B
     *
     * @return ���͌�
     */
    public String getSource() {
        return this.sourceOrDest;
    }

    /**
     * �o�͐��ݒ肷��B
     *
     * @param dest �o�͐�
     */
    public void setDest(String dest) {
        this.sourceOrDest = dest;
    }

    /**
     * �o�͐���擾����B
     *
     * @return �o�͐�
     */
    public String getDest() {
        return sourceOrDest;
    }

}
