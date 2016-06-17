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

import java.util.Locale;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * FieldChecksEx�̃e�X�g�Ŏg�p����MessageResources�̎����N���X�B
 *
 */
@SuppressWarnings("serial")
public class FieldChecksEx_MessageResourcesImpl01 extends MessageResources {

    /**
     * �ԋp���郁�b�Z�[�W�B
     */
    public String message = null;

    /**
     * �R���X�g���N�^�B
     * @param factory factory
     * @param config config
     * @param returnNull returnNull
     */
    public FieldChecksEx_MessageResourcesImpl01(MessageResourcesFactory factory, String config, boolean returnNull) {
        super(factory, config, returnNull);
    }

    /**
     * �R���X�g���N�^�B
     * @param factory factory
     * @param config config
     */
    public FieldChecksEx_MessageResourcesImpl01(MessageResourcesFactory factory, String config) {
        super(factory, config);
    }

    /**
     * ���b�Z�[�W���擾����B
     * @param local ���P�[��
     * @param key ���b�Z�[�W�L�[
     */
    @Override
    public String getMessage(Locale locale, String key) {
        return message;
    }

}
