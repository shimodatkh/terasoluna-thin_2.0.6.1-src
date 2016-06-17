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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * �Ɩ����ʁA�V�X�e���̃��b�Z�[�W���\�[�X�𐶐�����N���X�B
 *
 * <p>
 * Struts�̎d�l�Ƃ��āA���b�Z�[�W���\�[�X��`�t�@�C���𗘗p����ꍇ�A
 * ���̒�`�̓��W���[�����ƂɓƗ����Ă��邽�߁A���ׂẴ��W���[���ɋ��ʂ���
 * ���b�Z�[�W���\�[�X���ꌳ�I�ɒ�`���邱�Ƃ��ł��Ȃ��B<br>
 * TERASOLUNA�ł̓��W���[�����ׂĂɋ��ʂȁA
 * �Ɩ����ʃ��b�Z�[�W��A�V�X�e�����b�Z�[�W�𗘗p������@��񋟂��Ă���B<br>
 * ���̃N���X�́A�V�X�e���i�t���[�����[�N�j�̃��b�Z�[�W���\�[�X�ƁA
 * �Ɩ����ʂ̃��b�Z�[�W���\�[�X��ێ����ATERASOLUNA�t���[�����[�N���񋟂���A
 * �ǂ̃��b�Z�[�W���\�[�X�N���X��p���Ă��Q�Ƃ����悤�ɂȂ��Ă���B<br>
 * </p>
 * <h5>��������</h5>
 * <ol>
 *  <li>TERASOLUNA�t���[�����[�N�Œ񋟂���MessageResources�́A
 *      �S�Ă��̃N���X����V�X�e���A�Ɩ����ʂ̃��b�Z�[�W���\�[�X���擾����
 *      �K�v������B</li>
 *  <li>�����Ŏ擾���ꂽ���b�Z�[�W���\�[�X�͍��ۉ��Ή�����Ȃ��B</li>
 * </ol>
 * <h5>�Ɩ����ʂ̃��b�Z�[�W���\�[�X�ݒ�</h5>
 * <p>
 * �Ɩ����ʂ̃��b�Z�[�W���\�[�X�́A�f�t�H���g�ŋƖ����ʃ��b�Z�[�W���\�[�X��`
 * �t�@�C������擾����B
 * �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���̃f�t�H���g�̃t�@�C������
 * application-messages.properties�ɐݒ肳��Ă���B
 * �t�@�C������ύX����ꍇ�́A
 * �V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j
 * �Ɉȉ��̃L�[�Őݒ���s�Ȃ��B<br>
 * <pre><code>
 * application.messages=sample1-messages
 * </code></pre>
 * application.messages�ɑΉ����镶����́A.properties���������t�@�C�����ł���B
 * <b>�K��.properties�͏����ċL�q����B</b><br>
 * </p>
 * <h5>�Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���̉��p</h5>
 * �Ɩ����ʂ̃��b�Z�[�W�͋Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��
 * �iapplication-messages.properties�j
 * ���邢�́A�V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j
 * �Œ�`�����t�@�C�����̃t�@�C���̒��ŁA
 * ���L�̂悤�ɃL�[�iadd.message.file.x�j��
 * �g�p���ĊO���t�@�C�����w�肷�邱�Ƃɂ��A
 * ���b�Z�[�W���\�[�X��ǉ����邱�Ƃ��ł���B<br>
 * <code><pre>
 * add.message.file.1=app1-message
 * add.message.file.2=app2-message
 * </pre></code>
 * �v���p�e�B�L�[������1�Ŏn�܂�ʔԂł���A�r���ŒʔԂ��r�؂�Ă���ꍇ�́A
 * �����ŊO���t�@�C���ǂݍ��ݏI���ƂȂ�B<br>
 * <h5>�V�X�e���̃��b�Z�[�W���\�[�X�ݒ�</h5>
 * GlobalMessageResources�ł́A
 * �f�t�H���g��system-message.properties�����[�h���A
 * ��������V�X�e���̃��b�Z�[�W���擾���Ă���B
 * ���̃V�X�e�����b�Z�[�W���\�[�X��`�t�@�C�����́A�ύX�ł��Ȃ��B
 *
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx
 *
 */
public final class GlobalMessageResources extends MessageResources {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -1224092114113256826L;

    /**
     * ���O�N���X�B
     */
    @SuppressWarnings("hiding")
    private static Log log
        = LogFactory.getLog(GlobalMessageResources.class);

    /**
     * �t���[�����[�N�̃��b�Z�[�W���ێ������B
     */
    private Map<String, String> fwMessages = new HashMap<String, String>();

    /**
     * �V�X�e����ӂƂȂ郁�b�Z�[�W��ێ�����B
     */
    private Map<String, String> globalMessages = new HashMap<String, String>();

    /**
     * �t���[�����[�N���b�Z�[�W���\�[�X���B
     */
    private static final String SYSTEM_MESSAGE
        = "system-messages";

    /**
     * �f�t�H���g�̃A�v���P�[�V�������b�Z�[�W���\�[�X���B
     */
    private static final String DEFAULT_APPLICATION_MESSAGE
        = "application-messages";

    /**
     * <code>system.properties</code>�ɋL�q�����Ɩ�����
     * ���b�Z�[�W���\�[�X���B
     */
    private static final String APPLICATION_CONFIG_KEY
        = "application.messages";

    /**
     * ���[�g�̃��b�Z�[�W�t�@�C���ɋL�q����A�ǉ��p�O�����b�Z�[�W�t�@�C���B
     */
    private static final String ADD_MESSAGES_FILE
        = "add.message.file.";

    /**
     * �V���O���g���I�u�W�F�N�g�B
     */
    private static GlobalMessageResources globalMessageResources
        = null;

    /**
     * ���̃N���X�̃V���O���g���C���X�^���X��ԋp����B
     *
     * @return GlobalMessageResources ���̃N���X�̃V���O���g���C���X�^���X
     */
    public static GlobalMessageResources getInstance() {
        if (globalMessageResources == null) {
            synchronized (GlobalMessageResources.class) {
                GlobalMessageResources createdResources
                    = new GlobalMessageResources(
                            null, SYSTEM_MESSAGE);
                globalMessageResources = createdResources;
            }
        }
        return globalMessageResources;
    }

    /**
     *  �w�肳�ꂽ�p�����[�^�ɂ����
     *  <code>GlobalMessageResources</code> �𐶐�����B
     *
     * @param factory ���b�Z�[�W���\�[�X�t�@�N�g��
     * @param config ���� <code>MessageResource</code> �ɑ΂���ݒ�p�����[�^
     */
    private GlobalMessageResources(MessageResourcesFactory factory,
                                   String config) {
        super(factory, config);
        globalInit();
        applicationInit();
    }

    /**
     * �v���p�e�B�t�@�C������A�����}�b�v�ւ̋l�ߑւ����s�Ȃ��B
     */
    private synchronized void globalInit() {

        // ���b�Z�[�W���N���A
        fwMessages.clear();

        // ���b�Z�[�W���\�[�X�t�@�C�������[�h����B
        Properties prop = PropertyUtil.loadProperties(this.config);
        if (prop == null) {
            // �������s�Ȃ킸�I������B
            return;
        }
        Enumeration keyEnum = prop.propertyNames();
        while (keyEnum.hasMoreElements()) {
            Object keyObj = keyEnum.nextElement();
            Object value = prop.get(keyObj);
            if (log.isDebugEnabled()) {
                log.debug(
                    "Saving framework message key [" + keyObj + "]"
                    + "value [" + value + "]");
            }
            fwMessages.put((String) keyObj, (String) value);
        }
    }

    /**
     * �Ɩ����ʃ��b�Z�[�W���\�[�X�t�@�C���̃��[�h��
     * ���b�Z�[�W���\�[�X�̎擾���s�Ȃ��B
     */
    private synchronized void applicationInit() {
        String appKey = PropertyUtil.getProperty(APPLICATION_CONFIG_KEY);
        if (appKey == null) {
            // �L�[��������Ȃ��ꍇ�A�f�t�H���g��
            // ���b�Z�[�W���\�[�X����ݒ肷��B
            appKey = DEFAULT_APPLICATION_MESSAGE;
        }

        // �Ɩ����ʂ̃��[�g�v���p�e�B�̎擾
        Properties prop = PropertyUtil.loadProperties(appKey);
        if (prop == null) {
            // �������s�Ȃ킸�I������B
            return;
        }
        // ���[�g�̋Ɩ����ʃ��b�Z�[�W�Ƃ��Đݒ肳��Ă��郁�b�Z�[�W���\�[�X��
        // Map�ɓo�^����B
        Map<String, String> rootAppricationMap = getRootApplicationMap(prop);
        // �ǉ��ǂݍ��݂��s�Ȃ����b�Z�[�W���\�[�X���擾����B
        Map<String, String> addApplicationMap =
        	getAddApplicationMap(prop, appKey);
        // �}�b�v�̃}�[�W
        addApplicationMap.putAll(rootAppricationMap);
        globalMessages = addApplicationMap;
    }

    /**
     * �Ɩ����ʂ̃��[�g���b�Z�[�W�t�@�C�����ɋL�q���ꂽ���b�Z�[�W�ꗗ�}�b�v��
     * �ԋp����B���݂��Ȃ��ꍇ����̃}�b�v��ԋp����B
     *
     * @param prop ���[�g�̃v���p�e�B�t�@�C��
     * @return ���[�g�̃v���p�e�B�t�@�C���ɋL�q���ꂽ���b�Z�[�W
     */
    private Map<String, String> getRootApplicationMap(Properties prop) {
        Map<String, String> rootApplicationMap = new HashMap<String, String>();
        // �L�[�ꗗ�擾
        Iterator it = prop.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            // �L�[�̂����Aadd.message.file.�Ŏn�܂��Ă�����̂͏��O����B
            if (key.startsWith(ADD_MESSAGES_FILE)) {
                continue;
            }
            String value = prop.getProperty(key);
            if (log.isDebugEnabled()) {
                log.debug("Saving root-application message key [" + key + "]"
                          + "value [" + value + "]");
            }
            rootApplicationMap.put(key, value);
        }
        return rootApplicationMap;
    }

    /**
     * �Ɩ����ʂ̃��[�g���b�Z�[�W����A�O���̃��b�Z�[�W���\�[�X�t�@�C���ꗗ��
     * �擾���A���b�Z�[�W���擾����B
     * �O���t�@�C�������݂��Ȃ��ꍇ����̃}�b�v��ԋp����B
     * ���[�g�̃t�@�C�����ƃ��[�h����t�@�C��������v���Ă���ꍇ�́A
     * �i�v���[�v���������ׁA�ǂݔ�΂����B
     *
     * @param prop ���[�g�̃v���p�e�B�t�@�C��
     * @param rootProperty ���[�g�̃v���p�e�B�t�@�C����
     * @return �O���̃��b�Z�[�W�}�b�v
     */
    private Map<String, String> getAddApplicationMap(Properties prop,
    		String rootProperty) {
        Map<String, String> addApplicationMap = new HashMap<String, String>();
        List<String> fileNameList = new ArrayList<String>();
        for (int i = 1; ; i++) {
            // �O���t�@�C�����擾
            String fileName = prop.getProperty(ADD_MESSAGES_FILE + i);
            if (fileName == null) {
                // �t�@�C�������擾�ł��Ȃ��ꍇ�́A�I��
                break;
            } else if (fileName.equals(rootProperty)) {
                // ���[�g�̃t�@�C�����ƈ�v���Ă���ׁA�I��
                // (�i�v���[�v���)
                break;
            }
            fileNameList.add(fileName);
        }

        // �擾���ꂽ�t�@�C��������A�����O���t�@�C�������[�h���A
        // �}�b�v�ɒǉ�����B
        Iterator fileNameIt = fileNameList.iterator();
        while (fileNameIt.hasNext()) {
            String outerFileName = (String) fileNameIt.next();
            Properties outerProp = PropertyUtil.loadProperties(outerFileName);
            // �O���t�@�C�������K�؂łȂ��Ƃ��͔�΂�
            if (outerProp == null) {
                if (log.isWarnEnabled()) {
                    log.warn("\"" + outerFileName + "\" is illegal.");
                }
                continue;
            }
            // �O���t�@�C���̃L�[�ꗗ
            Iterator outerFileKeyIt = outerProp.keySet().iterator();
            while (outerFileKeyIt.hasNext()) {
                String outerMessageKey = (String) outerFileKeyIt.next();
                String outerValue = outerProp.getProperty(outerMessageKey);
                if (log.isDebugEnabled()) {
                    log.debug("Saving outer-file-application message key ["
                              + outerMessageKey + "]" + "value [" + outerValue
                              + "]");
                }
                addApplicationMap.put(outerMessageKey, outerValue);
            }
        }
        return addApplicationMap;
    }

    /**
     * ���b�Z�[�W��ԋp����B
     * ���b�Z�[�W�擾�̗D�揇�ʂ́A
     * <ol>
     *   <li>�Ɩ����ʂ̃��b�Z�[�W���\�[�X</li>
     *   <li>�V�X�e���̃��b�Z�[�W���\�[�X</li>
     * </ol>
     * �̏��ƂȂ�B
     *
     * @param locale ���P�[���I�u�W�F�N�g
     * @param key ���b�Z�[�W���\�[�X�L�[
     * @return ���b�Z�[�W
     */
    @Override
    public String getMessage(Locale locale, String key) {
        if (key == null) {
            return null;
        }
        // �Ɩ����ʂ̃��b�Z�[�W���������A���������烊�^�[������B
        String globalMsg = globalMessages.get(key);
        if (globalMsg != null) {
            return globalMsg;
        }
        // �t���[�����[�N�̃��b�Z�[�W��ԋp����B
        return fwMessages.get(key);
    }
}
