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
 * FieldChecksExTest08�Ŏg�p����MultiFieldValidator�̎����N���X�B
 * validate�̖߂�l�𑀍삷�邱�Ƃ��ł���B
 *
 *
 */
public class FieldChecksEx_MultiFieldValidatorImpl01 implements
        MultiFieldValidator {

    /**
     * validate���\�b�h�̌��ʂƂ���l�B
     */
    public static boolean result = false;

    /**
     * validate���\�b�h�̑������̒l�B
     */
    public static String value = null;

    /**
     * validate���\�b�h�̑������̒l�B
     */
    public static String[] fields = null;

    /**
     * validate���\�b�h���R�[�����ꂽ�J�E���g�B
     */
    public static int validateCalledCount = 0;

    /**
     * �C���X�^���X�ϐ�result�̒l��ԋp����B
     * �����̒l���C���X�^���X�ϐ��ɃL���b�V������B
     */
    public boolean validate(String value, @SuppressWarnings("hiding") String[] fields) {
        FieldChecksEx_MultiFieldValidatorImpl01.value = value;
        FieldChecksEx_MultiFieldValidatorImpl01.fields = fields;
        FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount++;
        return FieldChecksEx_MultiFieldValidatorImpl01.result;
    }

}
