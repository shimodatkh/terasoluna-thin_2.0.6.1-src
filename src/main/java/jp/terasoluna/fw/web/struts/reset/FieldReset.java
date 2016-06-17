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

package jp.terasoluna.fw.web.struts.reset;

/**
 *   �t�B�[���h�̃��Z�b�g����ێ����� Bean �N���X�B
 *
 * <p>
 * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)����ǂݍ��܂ꂽ�ݒ����
 * �t�B�[���h���ɂ��̃N���X�Ɋi�[����ێ�����B
 * ���̃N���X�̃C���X�^���X�� {@link ActionReset} �C���X�^���X��
 * �i�[�����B
 * </p>
 * 
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * 
 */
public class FieldReset {

    /**
     * ���Z�b�g�����ΏۂƂȂ�t�B�[���h���B
     */
    private String fieldName = null;

    /**
     * �w��͈̓��Z�b�g�@�\�I���t���O�B
     */
    private boolean select = false;

    /**
     * ���Z�b�g�����ΏۂƂȂ�t�B�[���h�����擾����B
     *
     * @return ���Z�b�g�����ΏۂƂȂ�t�B�[���h��
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * ���Z�b�g�����ΏۂƂȂ�t�B�[���h����ݒ肷��B
     *
     * @param fieldName ���Z�b�g�����ΏۂƂȂ�t�B�[���h��
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * �w��͈̓��Z�b�g�@�\�I���t���O���擾����B
     *
     * @return �w��͈̓��Z�b�g�@�\�I���t���O�B
     */
    public boolean isSelect() {
        return select;
    }

    /**
     * �w��͈̓��Z�b�g�@�\�I���t���O��ݒ肷��B
     *
     * @param select �w��͈̓��Z�b�g�@�\�I���h�t���O
     */
    public void setSelect(boolean select) {
        this.select = select;
    }

}
