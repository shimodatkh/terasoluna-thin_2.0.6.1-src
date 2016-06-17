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

package jp.terasoluna.fw.web.struts.action;

import org.apache.struts.action.ActionMapping;

/**
 * ActionMapping�g���N���X�B
 *
 * <p>
 *  Struts��ActionMapping�ɑ΂��āA�ȉ��̓_���g������Ă���B
 *  <ul>
 *    <li>ActionForm�̏������w��F
 *        RequestProcessorEx���Q�Ƃ̂���</li>
 *    <li>cancelPopulate�t���O�w��F
 *        RequestProcessorEx���Q�Ƃ̂���</li>
 *  </ul>
 *  �t�B�[���h��getter��setter�𓯖��ɂ���ƁA
 *  �v���p�e�B���ݒ肳��Ȃ����Ƃ����邽�߁Agetter��
 *  setter�ł̓��\�b�h�𓯖��ɂ��Ȃ����ƁB<br>
 *  �܂��Astruts-config.xml��set-property�w���
 *  �v���p�e�B�́A���̂�setter�ɍ��킹�邱�ƁB<br>
 *  �{�@�\�𗘗p���邽�߂ɂ́Astruts-config.xml�ɑ΂��A
 *  &lt;action-mappings&gt;�v�f��type������
 *  �N���X�����w�肷��B
 * </p>
 * <p>
 *  <strong>struts-config.xml �ɂ��ActionMappingEx�̐ݒ��</strong>
 *  <code><pre>
 *  &lt;struts-config&gt;
 *    �E�E�E
 *   &lt;action-mappings
 *    <b>type="jp.terasoluna.fw.web.struts.action.ActionMappingEx"</b>&gt;
 *    �E�E�E
 *     &lt;action path="/start"
 *       name="validateSampleForm"
 *       scope="session"&gt;
 *       �E�E�E
 *     &lt;/action&gt;
 *    �E�E�E
 *   &lt;/action-mappings&gt;
 * �E�E�E
 * </pre></code>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.RequestProcessorEx
 *
 */
public class ActionMappingEx extends ActionMapping {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -7859832817953363932L;

    /**
     * �A�N�V�����t�H�[���������I�ɐV�K�쐬���邩�ǂ����B
     * �f�t�H���g��false�i����������΍ė��p�j�B
     */
    private boolean clearForm = false;

    /**
     * processPopulate ���X�L�b�v���邩�ǂ����B
     * �f�t�H���g��false�i�X�L�b�v���Ȃ��j�B
     */
    private boolean cancelPopulate = false;

    /**
     * �A�N�V�����t�H�[���N���A�t���O��ݒ肷��B
     *
     * @param clearForm �A�N�V�����t�H�[���N���A�t���O
     */
    public void setClearForm(boolean clearForm) {
        this.clearForm = clearForm;
    }

    /**
     * �A�N�V�����t�H�[���N���A�t���O���擾����B
     *
     * @return �A�N�V�����t�H�[���N���A�t���O
     */
    public boolean getClearForm() {
        return this.clearForm;
    }

    /**
     * �|�s�����[�V�����L�����Z���t���O��ݒ肷��B
     *
     * @param cancelPopulate �|�s�����[�V�����L�����Z���t���O
     */
    public void setCancelPopulate(boolean cancelPopulate) {
        this.cancelPopulate = cancelPopulate;
    }

    /**
     * �|�s�����[�V�����L�����Z���t���O���擾����B
     *
     * @return �|�s�����[�V�����L�����Z���t���O
     */
    public boolean getCancelPopulate() {
        return this.cancelPopulate;
    }
}
