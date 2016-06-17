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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *  �A�N�V�����p�X�P�ʂ̃��Z�b�g�p�̐ݒ����ێ����� Bean �N���X�B
 *
 * <p>
 * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)����ǂݍ��܂ꂽ�ݒ����
 * �A�N�V�����p�X���ɂ��̃N���X�Ɋi�[�����ێ�����B
 * ���Z�b�g�ΏۂƂȂ�t�B�[���h�̐ݒ���� {@link FieldReset}
 * �C���X�^���X�Ƃ��ĕێ�����B<br>
 * ���̃N���X�̃C���X�^���X�� {@link ResetterResources} �C���X�^���X��
 * �i�[����A�T�[�u���b�g�R���e�L�X�g��
 * RESETTER_RESOURCES + "���W���[����"�Ƃ����L�[���ŕۑ������B
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ActionReset {

    /**
     * ���Z�b�g�����ΏۂƂȂ�A�N�V�����p�X�B
     */
    private String path = null;

    /**
     * ���Z�b�g�����ΏۂƂȂ�t�B�[���h�v�f��ێ����� Map�B
     */
    private Map<String, FieldReset> fieldResets = 
        new HashMap<String, FieldReset>();

    /**
     * ���Z�b�g�����ΏۂƂȂ�t�B�[���h�v�f��ݒ肷��B
     *
     * @param fieldReset ���Z�b�g�����ΏۂƂȂ�t�B�[���h�v�f
     */
    public void setFieldReset(FieldReset fieldReset) {
        fieldResets.put(fieldReset.getFieldName(), fieldReset);
    }

    /**
     * ���Z�b�g�����ΏۂƂȂ�A�N�V�����p�X���擾����B
     *
     * @return ���Z�b�g�����ΏۂƂȂ�A�N�V�����p�X
     */
    public String getPath() {
        return path;
    }

    /**
     * ���Z�b�g�����ΏۂƂȂ�A�N�V�����p�X��ݒ肷��B
     *
     * @param path ���Z�b�g�����ΏۂƂȂ�A�N�V�����p�X
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * ���Z�b�g�����ΏۂƂȂ�t�B�[���h���̈ꗗ���擾����B
     *
     * @return ���Z�b�g�����ΏۂƂȂ�t�B�[���h���ꗗ
     */
    public Iterator<String> getFieldNames() {
        return this.fieldResets.keySet().iterator();
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̎w��͈̓��Z�b�g�@�\�I���t���O���擾����B
     *
     * @param fieldName �t�B�[���h��
     * @return �w��͈̓��Z�b�g�@�\�I���t���O�B
     */
    public boolean isSelectField(String fieldName) {
        FieldReset field = fieldResets.get(fieldName);
        if (field != null) {
            return field.isSelect();
        }
        return false;
    }

}
