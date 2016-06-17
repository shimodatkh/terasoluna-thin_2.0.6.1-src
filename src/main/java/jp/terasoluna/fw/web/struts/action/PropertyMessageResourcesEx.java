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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;

import jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

/**
 * �V�X�e���i�t���[�����[�N�j�ƋƖ����ʂ̃��b�Z�[�W���\�[�X��\���\�ɂ���
 * �v���p�e�B�t�@�C�����b�Z�[�W���\�[�X�B
 * 
 * <p>
 * Struts�̎d�l�Ƃ��āA���b�Z�[�W���\�[�X��`�t�@�C���𗘗p����ꍇ�A
 * ���̒�`�̓��W���[�����ƂɓƗ����Ă��邽�߁A
 * ���ׂẴ��W���[���ɋ��ʂ��郁�b�Z�[�W���\�[�X�͈ꌳ�I�ɒ�`�ł��Ȃ��B<br>
 * TERASOLUNA�ł̓��W���[�����ׂĂɋ��ʂȁA
 * �Ɩ����ʃ��b�Z�[�W��A�V�X�e�����b�Z�[�W�𗘗p���邽�߂̕��@��
 * �񋟂��Ă���B<br>
 * ���̃N���X�́AStruts��PropertyMessageResources���g�����A
 * �e�Ɩ��̃��b�Z�[�W���\�[�X��`�t�@�C�������łȂ��A�Ɩ����ʃ��b�Z�[�W�ƁA
 * �V�X�e���̃��b�Z�[�W�𗘗p�\�ɂ���B<br>
 * �Ɩ����ʁE�V�X�e���̃��b�Z�[�W���\�[�X�̒�`���e�ɂ��ẮA
 * GlobalMessageResources���Q�Ƃ̂��ƁB
 * </p>
 * <p>
 * ���̃N���X�́A
 * Won't Fix�ƂȂ��Ă���Struts�̃o�O STR-2172(https://issues.apache.org/jira/browse/STR-2172)
 * ����������i��L���Ă���B<br>
 * �ڍׂ́A{@link MessageFormatCacheMapFactory} ���Q�ƁB
 * </p>
 * <h5>�g�p���@</h5>
 *  ���̃N���X�𗘗p����ɂ́Astruts-config.xml����
 *  &lt;message-resource&gt;�v�f��
 * <ul>
 *  <li>parameter�����Ƀv���p�e�B�t�@�C����(.properties�͕s�v)</li>
 *  <li>factory�����ɁAPropertyMessageResourcesExFactory</li>
 * </ul>
 *  ���w�肷��B���L��struts-config.xml�̐ݒ��ł���B
 * <pre><code>
 * &lt;struts-config&gt;
 *   �c
 *   &lt;message-resources parameter="MessageResources"
 *                      factory="jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory"
 *   /&gt;
 *  �c
 * &lt;/struts-config&gt;
 * </code></pre>
 *
 * <h5>��������</h5>
 * <ol>
 *  <li>�V�X�e���̃��b�Z�[�W���\�[�X�͍��ۉ��Ή�����Ȃ��B</li>
 *  <li>�Ɩ����ʂ̃��b�Z�[�W���\�[�X�͍��ۉ��Ή�����Ȃ��B</li>
 * </ol>
 * ����L�[�Ŏ擾����郁�b�Z�[�W���\�[�X�̗D�揇�ʂ͉��L�̒ʂ�ƂȂ�B
 * <ol>
 *   <li>���b�Z�[�W���\�[�X��`�t�@�C���̃��b�Z�[�W���\�[�X</li>
 *   <li>�Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��
 *       �iapplication-messages.properties�j�̃��b�Z�[�W���\�[�X</li>
 *   <li>�V�X�e�����b�Z�[�W���\�[�X��`�t�@�C���isystem-messages.properties�j��
 *       ���b�Z�[�W���\�[�X</li>
 * </ol>
 *
 * @see jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 *
 */
public class PropertyMessageResourcesEx extends PropertyMessageResources {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -8239553100324527837L;

    /**
     * �R���X�g���N�^�B
     *
     * @param factory ���̃N���X�̃t�@�N�g���I�u�W�F�N�g
     * @param config ���b�Z�[�W���\�[�X�t�@�C����
     */
    public PropertyMessageResourcesEx(
            MessageResourcesFactory factory,
            String config) {
        super(factory, config);
        replaceMessageFormatCache();
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param factory ���̃N���X�̃t�@�N�g���I�u�W�F�N�g
     * @param config ���b�Z�[�W���\�[�X�t�@�C����
     * @param returnNull ���b�Z�[�W���\�[�X�L�[���o�^����Ă��Ȃ��ꍇ�Anull��
     *                   �Ԃ����ǂ���
     */
    public PropertyMessageResourcesEx(MessageResourcesFactory factory,
                                      String config,
                                      boolean returnNull) {
        super(factory, config, returnNull);
        replaceMessageFormatCache();
    }

    /**
     * MessageFormat�L���b�V��(formats)�̃C���X�^���X�����ւ����s���B
     * <p>
     * Struts�̃o�O STR-2172���p�̃L���b�V���I�u�W�F�N�g�ɍ����ւ���B
     * </p>
     * @see MessageFormatCacheMapFactory
     */
    private void replaceMessageFormatCache() {
        HashMap<String, MessageFormat> map = MessageFormatCacheMapFactory
                .getInstance();
        if (map != null) {
            formats = map;
        }
    }

    /**
     * ���b�Z�[�W���擾����B
     * ���b�Z�[�W�擾�̗D�揇�ʂ͉��L�̂悤�ɂȂ�B
     * <ol>
     *  <li>(�e���W���[���Œ�`���ꂽ)���b�Z�[�W���\�[�X
     *  �t�@�C�����̃��b�Z�[�W</li>
     *  <li>�Ɩ����ʂ̃��b�Z�[�W</li>
     *  <li>�V�X�e���̃��b�Z�[�W</li>
     * </ol>
     *
     * @param locale ���N�G�X�g����擾���ꂽ���P�[��
     * @param key ���b�Z�[�W���\�[�X�L�[
     * @return ���b�Z�[�W
     */
    @Override
    public String getMessage(Locale locale, String key) {
        // �Ɩ��̃��b�Z�[�W���\�[�X�t�@�C�����������A���b�Z�[�W��ԋp����B
        String localMessage = super.getMessage(locale, key);
        if (localMessage != null && !localMessage.startsWith("???")
                && !localMessage.endsWith("???")) {
            // null���ԋp���ꂸ�A�L�[�l��"???"�ŋ��܂�Ă��Ȃ�
            // (�R���X�g���N�^��returnNull��false)�̎��A
            // �Ɩ����b�Z�[�W��ԋp����B
            return localMessage;
        }

        // �Ɩ����ʁE�V�X�e���̃��b�Z�[�W�����������ꍇ�ԋp����B
        MessageResources globalMessageResources
            = GlobalMessageResources.getInstance();
        String globalMessage = globalMessageResources.getMessage(locale, key);
        if (globalMessage != null) {
            // �Ɩ����ʁE�V�X�e�����b�Z�[�W�����������ꍇ�́A
            // ���b�Z�[�W��ԋp����B
            return globalMessage;
        }
        // ������Ȃ��ꍇ�AStruts�̃��b�Z�[�W���\�[�X�`���ŕԋp
        return localMessage;
    }
}
