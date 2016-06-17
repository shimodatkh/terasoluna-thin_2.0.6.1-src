/*
 * Copyright (c) 2008 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts.util;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * SpringMessageResourcesFactory�𐶐�����t�@�N�g���N���X
 *
 * <p>
 * Spring�̃��b�Z�[�W�\�[�X�𗘗p����SpringMessageResources��
 * ��������t�@�N�g���N���X�B
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.util.SpringMessageResources
 *
 */
public class SpringMessageResourcesFactory extends MessageResourcesFactory {

    /** 
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 9001795252795100420L;

    /**
     * �V�K��SpringMessageResources�𐶐�����B
     *
     * @param config ���N�G�X�g�o���h���ɑ΂���ݒ�p�����[�^
     * @return SpringMessageResources�C���X�^���X
     */
    @Override
    public MessageResources createResources(@SuppressWarnings("hiding")
    String config) {

        return new SpringMessageResources(this, config, super.returnNull);
    }
}
