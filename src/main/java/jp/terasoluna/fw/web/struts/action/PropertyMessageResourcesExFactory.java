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
 * PropertyMessageResourcesEx�𐶐�����t�@�N�g���N���X�B
 *
 * <p>
 * �V�X�e���A����ыƖ����ʂ̃��b�Z�[�W���\�[�X��
 * �擾�\�ɂ���{@link PropertyMessageResourcesEx}�𐶐�����t�@�N�g���N���X�B
 * </p>
 * <h5>�g�p���@</h5>
 * <p>
 * Struts�ݒ�t�@�C��(struts-config.xml)����&lt;message-resources&gt;�v�f��
 * �ȉ��̋L�q��ǉ����āAPropertyMessageResourcesExFactory
 * �����b�Z�[�W�t�@�N�g���Ƃ��ėp������悤�ɂ���B
 * </p>
 * <pre><code>
 * &lt;message-resources parameter="message"
 *                    factory="jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory"/&gt;
 * </code></pre>
 * <p>
 * ��L��ł́Amessage.properties�Ƃ������b�Z�[�W���\�[�X��`�t�@�C���A
 * �y�сA�t�@�N�g���N���X�ł���PropertyMessageResourcesExFactory��
 * �w�肵�Ă���B<br>
 * Struts�̒񋟂��郁�b�Z�[�W���\�[�X�𗘗p������@�A�y�ѐݒ�̏ڍׂɂ��ẮA
 * {@link DBMessageResources}���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 *
 */
public class PropertyMessageResourcesExFactory extends MessageResourcesFactory {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 2857185835853238603L;

    /**
     *  �V�K�� <code>PropertyMessageResourcesEx</code> �𐶐�����B
     *
     * @param config ���N�G�X�g�o���h���ɑ΂���ݒ�p�����[�^
     * 
     * @return <code>PropertyMessageResourcesEx</code> �C���X�^���X
     */
    @Override
    public MessageResources createResources(@SuppressWarnings("hiding") String config) {
        return new PropertyMessageResourcesEx(
            this, config, super.returnNull);
    }
}
