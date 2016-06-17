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

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * DBMessageResources�𐶐�����t�@�N�g���N���X�B
 *
 * <p>
 * DB�ɐݒ肳�ꂽ���b�Z�[�W���\�[�X�𗘗p����ɂ́A
 * Struts�ݒ�t�@�C��(struts-config.xml)����
 * &lt;message-resource&gt;�v�f�Ɉȉ��̋L�q��ǉ����āA
 * DBMessageResourcesFactory
 * �����b�Z�[�W�t�@�N�g���Ƃ��ėp������悤�ɂ���B
 * </p>
 * <pre><code>
 * &lt;message-resources parameter="MessageResources"
 *                    factory="jp.terasoluna.fw.web.common.DBMessageResourcesFactory"
 * /&gt;
 * </code></pre>
 * <p>
 * ��L��ł́AMessageResources.properties�Ƃ������b�Z�[�W���\�[�X��`�t�@�C��
 * �y�сA�t�@�N�g���N���X�ł���DBMessageResourcesFactory���w�肵��
 * ����B<br>
 * Struts���񋟂���A���b�Z�[�W���\�[�X�𗘗p������@�A�y�ѐݒ�̏ڍׂɂ��ẮA
 * DBMessageResources���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * 
 */
public class DBMessageResourcesFactory extends MessageResourcesFactory {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -1027132907070919579L;

    /**
     *  �V�K��DBMessageResources�𐶐�����B
     *
     * @param config ���N�G�X�g�o���h���ɑ΂���ݒ�p�����[�^
     * 
     * @return DBMessageResources�C���X�^���X
     */
    @Override
    public MessageResources createResources(@SuppressWarnings("hiding") String config) {

        return new DBMessageResources(this, config, super.returnNull);
    }
}
