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

package jp.terasoluna.fw.web.struts.form;


/**
 * ValidatorActionFormEx,DynaValidatorActionFormEx
 * �̋��ʃ��\�b�h���`����C���^�t�F�[�X�B
 *
 * <p>
 *  TERASOLUNA�t���[�����[�N�����ł͂��̃C���^�[�t�F�C�X�̌^��
 *  �ʂ��ăA�N�V�����t�H�[���ɃA�N�Z�X���邱�ƂŊe�t�H�[���̍�����
 *  �z�����Ă���B
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * @see
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 *
 */
public interface FormEx {

    /**
     * �w�肵���C���f�b�N�X�̃v���p�e�B�l���擾����B
     *
     * @param name �擾�Ώۂ̃v���p�e�B��
     * @param index �擾�Ώۂ̃C���f�b�N�X
     * @return �v���p�e�B�l
     */
    Object getIndexedValue(String name, int index);

    /**
     * �w�肵�����O�̃v���p�e�B�l�̌������擾����B
     *
     * @param name �擾�Ώۂ̃v���p�e�B��
     * @return �v���p�e�B�l
     * <p>�v���p�e�B�����擾�ł��Ȃ������ꍇ��0��Ԃ��B</p>
     */
    int getIndexCount(String name);

    /**
     * �w�肵���C���f�b�N�X�̈ʒu�Ƀv���p�e�B�l��ݒ肷��B
     *
     * @param name �ݒ�Ώۂ̃C���f�b�N�X�t�v���p�e�B��
     * @param index �ݒ�Ώۂ̃C���f�b�N�X�ʒu
     * @param value �ݒ肷��v���p�e�B�l
     */
    void setIndexedValue(String name, int index, Object value);

    /**
     * �l�ύX�t���O���擾����B
     *
     * @return �l�ύX�t���O
     */
    boolean isModified();

    /**
     * �l�ύX�t���O��ݒ肷��B
     *
     * @param modified �l�ύX�t���O
     */
    void setModified(boolean modified);

}
