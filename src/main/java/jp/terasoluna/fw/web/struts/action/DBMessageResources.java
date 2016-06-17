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
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
/**
 * <h3>
 * ���b�Z�[�W���\�[�X�N���X.
 * </h3>
 *
 * <p>
 * ���b�Z�[�W���\�[�X�@�\�Ƃ́AJSP���ŕ\������G���[���b�Z�[�W�ȂǁA
 * ����̃L�[�ɑ΂��ă��b�Z�[�W���擾����@�\�ł���B<br>
 * ���̃N���X���g�p���邱�Ƃɂ���āA���b�Z�[�W���\�[�X��`�t�@�C��
 * �i�ʏ�Struts�Ŏg����v���p�e�B�t�@�C���`���̃��b�Z�[�W���\�[�X�j�����łȂ��A
 * �N���X���[�h����DB���Q�Ƃ��ADB����̃��b�Z�[�W���\�[�X���g�p���邱�Ƃ�
 * �\�ł���B
 * </p>
 * <p>
 * ���̃N���X�́A
 * Won't Fix�ƂȂ��Ă���Struts�̃o�O STR-2172(https://issues.apache.org/jira/browse/STR-2172)
 * ����������i��L���Ă���B<br>
 * �ڍׂ́A{@link MessageFormatCacheMapFactory} ���Q�ƁB
 * </p>
 *
 * <h5>�T���v����p�������</h5>
 *
 * <p>
 * DB���Őݒ肳�ꂽ���b�Z�[�W���\�[�X�́A�S���W���[������
 * ���L����邪�A���b�Z�[�W���\�[�X��`�t�@�C���̃��b�Z�[�W���\�[�X�́A
 * Struts�̊e���W���[�����ƂɓƗ�����B
 * �ȉ��T���v����p���āA���̃N���X���g�p�����Ƃ��ɁA���b�Z�[�W���\�[�X�@�\��
 * �ǂ̂悤�ɐU�������������B
 * </p>
 *
 * <h6>�T���v���ݒ�</h6>
 * <p>
 * ��Ƃ��āA���W���[��A�A���W���[��B�Ƃ��������̃��W���[�������݂��A
 * ���W���[�����̃��b�Z�[�W���\�[�X�̐ݒ�i���b�Z�[�W���\�[�X��`�t�@�C���j��
 * ���L�̂悤�Ȏw�肪����Ƃ���B<br>
 * <br>
 * <table border="1">
 *   <caption>���b�Z�[�W���\�[�X�ꗗ</caption>
 *   <tr>
 *     <td>���W���[����</td>
 *     <td>���b�Z�[�W�L�[</td>
 *     <td>���b�Z�[�W����</td>
 *     <td>���b�Z�[�W�̓o�^��</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2">���W���[��A</td>
 *     <td>message.propMessageResource</td>
 *     <td>"moduleA"</td>
 *     <td>���b�Z�[�W���\�[�X��`�t�@�C��</td>
 *   </tr>
 *   <tr>
 *     <td>message.dbMessageResource</td>
 *     <td>"DB"</td>
 *     <td>DB</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="2">���W���[��B</td>
 *     <td>message.propMessageResource</td>
 *     <td>"moduleB"</td>
 *     <td>���b�Z�[�W���\�[�X��`�t�@�C��</td>
 *   </tr>
 *   <tr>
 *     <td>message.subMessageResource</td>
 *     <td>"subModule"</td>
 *     <td>���b�Z�[�W���\�[�X��`�t�@�C��</td>
 *   </tr>
 * </table>
 * </p>
 *
 * <h6>���W���[���Ԃ̃��b�Z�[�W���\�[�X�̉��͈�</h6>
 * ��̕\�ŁA���W���[��A�̃��b�Z�[�W���\�[�X�̉��͈͂́A
 * <ul>
 *   <li>���W���[��A���Œ�`���ꂽ���b�Z�[�W���\�[�X��`�t�@�C������
 *       <code>message.propMessageResource</code>
 *   </li>
 *   <li>DB���̃��b�Z�[�W���\�[�X�ł���<code>message.dbMessageResource</code>
 *   </li>
 * </ul>
 * �ł���B<br>
 * ���W���[��B�̃��b�Z�[�W���\�[�X�̉��͈͂́A
 * <ul>
 *   <li>���W���[��B�Œ�`���ꂽ���b�Z�[�W���\�[�X��`�t�@�C������
 *       <code>message.propMessageResource</code></li>
 *   <li>���W���[��B�Œ�`���ꂽ���b�Z�[�W���\�[�X��`�t�@�C������
 *       <code>message.subMessageResource</code></li>
 *   <li>���W���[��A��DB���̃��b�Z�[�W���\�[�X�ł���
 *       <code>message.dbMessageResource</code></li>
 * </ul>
 * �ł���B<br>
 * ���W���[��A�ƃ��W���[��B�ł́A�������b�Z�[�W���\�[�X�̃L�[
 * <code>message.propMessageResource</code>�������Ă��邪�A�e���W���[����
 * �擾�ł��郁�b�Z�[�W�́A
 * <ul>
 *   <li>���W���[��A��"moduleA"</li>
 *   <li>���W���[��B��"moduleB"</li>
 * </ul>
 * �ƂȂ�B
 * ���b�Z�[�W���\�[�X��`�t�@�C���ɂ�郁�b�Z�[�W���\�[�X�̐ݒ�Ɋւ��ẮA
 * �e���W���[���ԂŐݒ�͋��L�ł��Ȃ����Ƃɒ��ӂ���B<br>
 * ����ɑ΂��A���W���[���Ԃŋ��L�����DB�̃��b�Z�[�W���\�[�X�́A
 * <code>message.dbMessageResource</code>�ɑ΂��Ď擾�ł��郁�b�Z�[�W�́A
 * ���W���[��A�A���W���[��B�Ƃ��� "DB"�ł���B<br>
 * �܂��AStruts�̎d�l�Ƃ��āA���W���[��B�̃��b�Z�[�W���\�[�X��`�t�@�C����
 * �ݒ肳�ꂽ���b�Z�[�W���\�[�X�����W���[��A����擾���邱�Ƃ͂ł��Ȃ��B
 * �i���W���[��A����<code>module.subMessageResource</code>�̃L�[��
 * �Q�Ƃ��Ă��A���b�Z�[�W�͎擾�ł��Ȃ��B�j
 * <h6>���ӓ_</h6>
 * ���̂悤�ɁA���b�Z�[�W���\�[�X�̈����ɂ��Ă͉��L�̒��ӓ_������B
 * <ul>
 *   <li>���b�Z�[�W���\�[�X��`�t�@�C���ɒ�`���ꂽ���b�Z�[�W������
 *       �擾���悤�Ƃ����ꍇ�A�������b�Z�[�W�L�[���`���Ă��Ă��A
 *       �ݒ肳�ꂽ���W���[�����قȂ�Ȃ�΁A�g�p����郂�W���[���ɂ����
 *       �l���قȂ�</li>
 *   <li>DB���Ƀ��b�Z�[�W���\�[�X��ݒ肵���ꍇ�A���̃��W���[���ł�
 *       �Q�Ƃ��\</li>
 *   <li>DB���̃��b�Z�[�W���\�[�X�ƃ��b�Z�[�W���\�[�X��`�t�@�C����
 *       ���b�Z�[�W���\�[�X�ŃL�[������̎��A���b�Z�[�W���\�[�X��`�t�@�C����
 *       ���b�Z�[�W�������擾�����</li>
 *   <li>DBMessageResources��p�����ꍇ�A���ۉ��Ή��͈�؂���Ȃ��B
 *       DB����擾���郁�b�Z�[�W���\�[�X�����łȂ��A���b�Z�[�W���\�[�X��`
 *       �t�@�C�������P�[���ɉ����ĕ����p�ӂ��Ă��A�Ӗ��͂Ȃ��B<br>
 *       ���ۉ��Ή����s���K�v������ꍇ�Astruts-config.xml��
 *       &lt;message-resources&gt;�v�f��factory�����ŁAStruts���񋟂���
 *       PropertyMessageResourcesFactory��TERASOLUNA�̒񋟂���
 *       PropertyMessageResourcesExFactory��p����K�v������B
 *       �Ȃ��A���̏ꍇ��DB���̃��b�Z�[�W���\�[�X�͎擾�ł��Ȃ��Ȃ�B</li>
 * </ul>
 * �܂��A�P�̃L�[�ɑ΂��āADBMessageResources�̃��b�Z�[�W�擾�D�揇�ʂ́A
 * ���L�̒ʂ�ƂȂ�B<br>
 * <ol>
 *   <li>���b�Z�[�W���\�[�X��`�t�@�C���Œ�`���ꂽ���b�Z�[�W���\�[�X</li>
 *   <li>DB���ɒ�`���ꂽ���b�Z�[�W���\�[�X</li>
 *   <li>�Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��
 *       �iapplication-messages.properties�j��
 *       ��`���ꂽ���b�Z�[�W���\�[�X</li>
 *   <li>�V�X�e�����b�Z�[�W���\�[�X��`�t�@�C��
 *       �isystem-messages.properties�j��
 *       ��`���ꂽ���b�Z�[�W���\�[�X</li>
 * </ol>
 * �Ɩ����ʃ��b�Z�[�W���\�[�X�E�V�X�e�����b�Z�[�W���\�[�X�Ɋւ�����e�́A
 * {@link GlobalMessageResources}���Q�Ƃ̂��ƁB
 *
 * <br><br>
 *
 * <h5>�g�p���@</h5>
 * ���̃N���X�𗘗p����ɂ́AStruts�ݒ�t�@�C��(struts-config.xml)����
 * &lt;message-resource&gt;�v�f�ɁA
 * <ul>
 *   <li>parameter�����Ƀ��b�Z�[�W���\�[�X��`�t�@�C��������
 *       �g���q(<code>.properties</code>)����菜��������</li>
 *   <li>factory�����ɁA<code>DBMessageResourcesFactory</code></li>
 * </ul>
 * ���w�肷��B
 *
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>���L��Struts�ݒ�t�@�C��(struts-config.xml)�̐ݒ��ł���B</legend>
 * <code><pre>
 * &lt;struts-config&gt;
 *   �c
 *   &lt;message-resources parameter="MessageResources"
 *                      factory="jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory"
 *   /&gt;
 *   �c
 * &lt;/struts-config&gt;
 * </pre></code>
 * </fieldset>
 *
 * <br>
 *
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend><h5>DB���̃��b�Z�[�W���\�[�X���擾����ݒ��</h5></legend>
 * DB���烁�b�Z�[�W���\�[�X���擾���邽�߂ɁA�V�X�e���ݒ�v���p�e�B�t�@�C��
 * �isystem.properties�j�ŁA���L�̂悤�ɐݒ肷��B
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <pre><code>
 * messages.sql=&lt;SQL(SELECT)��&gt;
 * messages.dao=MessageResourcesDAO�̎����N���X
 * </code></pre>
 * </fieldset>
 * <br>
 *
 * ��̗�͈ȉ��̂悤�ɂȂ�B
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <pre><code>
 * messages.sql=SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGES
 * messages.dao=jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 * </code></pre>
 * </fieldset>
 * </fieldset>
 *
 * <h6>�ݒ��̒��ӓ_</h6>
 * <ul>
 *  <li>�ݒ肷��SQL�́A��P�J�����i��L�ݒ��ł�MESSAGE_KEY�j�Ƀ��b�Z�[�W�L�[��
 *      �ݒ肳��A��Q�J�����iMESSAGE_VALUE�j�Ƀ��b�Z�[�W�������i�[�����
 *      ���ʃZ�b�g��Ԃ����̂łȂ���΂Ȃ�Ȃ��B</li>
 *  <li>�ݒ肷��DAO�́AMessageResourcesDAO�����������N���X�łȂ���΂Ȃ炸�A
 *      �������Ȃ��̃R���X�g���N�^�����N���X�łȂ���΂Ȃ�Ȃ��B
 *      TERASOLUNA���񋟂���MessageResourcesDAOImpl�͓��C���^�t�F�[�X������
 *      ���A�������Ȃ��̃R���X�g���N�^���������Ă���B
 *      <strong>{@link MessageResourcesDAOImpl}�̎g�p�@�ɂ��Ă�
 *      ���N���X��Javadoc���Q�Ƃ̂��ƁB</strong></li>
 * </ul>
 *
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend><h5>���b�Z�[�W���\�[�X��`�t�@�C���i�v���p�e�B�t�@�C���j��
 *     ���b�Z�[�W���\�[�X�̐ݒ��</h5></legend>
 * �v���p�e�B�t�@�C����p�������b�Z�[�W���\�[�X�̒�`�́A�v���p�e�B�t�@�C����
 * ��`�ǂ���ɓo�^�����B
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <pre><code>
 * &lt;���b�Z�[�W�L�[&gt;=&lt;���b�Z�[�W����&gt;
 * </code></pre>
 * </fieldset>
 * <br>
 * ��̗�͈ȉ��̂悤�ɂȂ�B
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <pre><code>
 * errors.requiredArray={0}�Ԗڂ�{1}�͕K�{���͂ł��B
 * errors.alphaNumericStringArray={0}�Ԗڂ�{1}�͔��p�p�����łȂ��Ă͂Ȃ�܂���B
 * </code></pre>
 * </fieldset>
 * </fieldset>
 * <br>
 *
 * @see jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAO
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 *
 */
public class DBMessageResources extends MessageResources {
    
    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 8244415315747028752L;

    /**
     * �V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j����
     * DAO���擾����ۂɎg�p����L�[�B
     */
    public static final String MESSAGES_DAO = "messages.dao";
    
    /**
     * �V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j����
     * SQL���擾����ۂɎg�p����L�[�B
     */
    public static final String MESSAGES_SQL = "messages.sql";   
 
    /**
     *  ���b�Z�[�W�̎擾���s��\���G���[�R�[�h�B
     */
    private static final String DB_MESSAGE_RESOURCES_ERROR
        = "errors.db.message.resources";
    
    /**
     *  ���b�Z�[�W���\�[�X�̏��������s��\���G���[�R�[�h�B
     */
    private static final String DB_MESSAGE_RESOURCES_ERROR_INIT
        = "errors.db.message.resources.init";
    
    /**
     * ���O�N���X�B
     */ 
    @SuppressWarnings("hiding")
    private static Log log = LogFactory.getLog(DBMessageResources.class);

    /**
     * DB����擾�������b�Z�[�W�L�[�ƃ��b�Z�[�W�������i�[����Map�B
     * �N���X�ŋ��L�������B
     */
    private static Map dbMessages = null;

    /**
     * ���b�Z�[�W���\�[�X��`�t�@�C������擾�������b�Z�[�W�L�[��
     * ���b�Z�[�W�������i�[����Map�B
     * 
     * DB�̃��b�Z�[�W���\�[�X�Ƃ͈قȂ�AStruts�̃��W���[���P�ʂ�
     * �Ɨ������邱�Ƃ��ł���B
     */
    private Map<String, String> messages = new HashMap<String, String>();

    /**
     * �w�肳�ꂽ�p�����[�^�ɂ����DBMessageResources�𐶐�����B
     *
     * @param factory ���b�Z�[�W���\�[�X�t�@�N�g��
     * @param config ���b�Z�[�W���\�[�X��`�t�@�C����
     */
    public DBMessageResources(MessageResourcesFactory factory,
                              String config) {
        super(factory, config);
        if (log.isDebugEnabled()) {
            log.debug("call DBMessageResources()");
        }
        replaceMessageFormatCache();
        // DB�̃��b�Z�[�W���\�[�X�����ݒ�̎��ADB���b�Z�[�W���\�[�X���擾����B
        if (dbMessages == null) {
            dbInit();
        }
        propertyInit(config);
    }

    /**
     * �w�肳�ꂽ�p�����[�^�ɂ����DBMessageResources�𐶐�����B
     * 
     * @param factory ���b�Z�[�W���\�[�X�t�@�N�g��
     * @param config ���b�Z�[�W���\�[�X��`�t�@�C����
     * @param returnNull <code>org.apache.struts.util.MessageResources</code>
     *                   �N���X�� <code>returnNull</code>
     *                   <code>false</code> �w�莞�A�L�[�ɊY�����郁�b�Z�[�W��
     *                   ���݂��Ȃ��ꍇ???Locale.key???�Ƃ����`���Ń��b�Z�[�W��
     *                   �ԋp����B
     */
    public DBMessageResources(MessageResourcesFactory factory,
                              String config, boolean returnNull) {
        super(factory, config, returnNull);
        if (log.isDebugEnabled()) {
            log.debug("call DBMessageResources()");
        }
        replaceMessageFormatCache();
        // DB�̃��b�Z�[�W���\�[�X�����ݒ�̎��ADB���b�Z�[�W���\�[�X���擾����B
        if (dbMessages == null) {
            dbInit();
        }
        propertyInit(config);
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
     * DB���̃��b�Z�[�W�L�[�ƃ��b�Z�[�W�����̃y�A���擾����B
     */
    protected static void dbInit() {
        
        if (log.isDebugEnabled()) {
            log.debug("call dbInit()");
        }
        
        // ��x�Ă΂ꂽ���Ƃ�������悤��dbMessages�ɋ��Map��ݒ�
        dbMessages = new HashMap();
        
        // �V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j����v���p�e�B��
        // �擾����
        String daoName = PropertyUtil.getProperty(MESSAGES_DAO);
        String sql = PropertyUtil.getProperty(MESSAGES_SQL);
        if (daoName == null && sql == null) {
            // �ǂ���̃L�[����`����Ă��Ȃ���ΏI������
            return;
        } else if (daoName == null || sql == null) {
            // ���������`����Ă��Ȃ��ꍇ�͌x�����O���o���āA�I������
            if (log.isWarnEnabled()) {
                log.warn("defined only one of the pair - " + MESSAGES_DAO
                         + " and " + MESSAGES_SQL + ".");
            }
            return;
        }
        
        // DAO�̃C���X�^���X�𐶐�
        MessageResourcesDAO dao = null;
        try {
            dao = (MessageResourcesDAO) ClassUtil.create(daoName); 
        } catch (ClassLoadException e) {
            // ��`����Ă���N���X�����[�h�ł��Ȃ������ꍇ
            log.error("\"" + daoName + "\" cannot loaded.", e);
            throw new SystemException(e, DB_MESSAGE_RESOURCES_ERROR_INIT);
        } catch (ClassCastException e) {
            // ��`����Ă���N���X��MessageResourcesDAO���������Ă��Ȃ��ꍇ
            log.error("\"" + daoName + "\" not implemented"
                      + " MessageResourcesDAO", e);
            throw new SystemException(e, DB_MESSAGE_RESOURCES_ERROR_INIT);
        }
        
        // Message�̋l�܂���Map���擾�B
        dbMessages = dao.queryMessageMap(sql);

    }
    
    /**
     *  ���b�Z�[�W���\�[�X��`�t�@�C�����烁�b�Z�[�W�L�[�ƃ��b�Z�[�W������
     *  �y�A���擾����B
     *
     * @param propertyFile ���b�Z�[�W���\�[�X��`�t�@�C����
     */
    protected void propertyInit(String propertyFile) {
        Properties props = null;
        Iterator names = null;
        
        if (log.isDebugEnabled()) {
            log.debug("call propertyInit()");
        }
        
        // ���b�Z�[�W���\�[�X��`�t�@�C���̃��[�h
        props = PropertyUtil.loadProperties(propertyFile);
        if (props == null) {
            log.error(
                "Message resources file \"" + propertyFile + "\" is illegal.");
            return;
        }
        // �n�b�V���}�b�v�ւ̋l�ߑւ����s��
        names = props.keySet().iterator();
        while (names.hasNext()) {
            String key = (String) names.next();
            if (log.isDebugEnabled()) {
                log.debug(
                    "Saving property message key [" + key + "]"
                    + "value [" + props.getProperty(key) + "]");
            }
            messages.put(key, props.getProperty(key));
        }
    }



    /**
     * �w�肳�ꂽ�L�[�ɂ��ƂÂ����b�Z�[�W�������擾����B
     * �����L�[���قȂ郁�b�Z�[�W�̒�`�ꏊ�ɑ��݂��鎞�A�擾�����D�揇�ʂ�
     * ���L�̂悤�ɂȂ�B
     * <ol>
     *  <li>���b�Z�[�W���\�[�X��`�t�@�C���̃��b�Z�[�W���\�[�X</li>
     *  <li>DB���̃��b�Z�[�W�L�[�ƃ��b�Z�[�W�����̓����Ă��郁�b�Z�[�W���\�[�X
     *      </li>
     *  <li>�Ɩ����ʃ��b�Z�[�W���\�[�X�t�@�C����`�t�@�C��
     *      �iapplication-messages.properties�j�̃��b�Z�[�W���\�[�X</li>
     *  <li>�V�X�e�����b�Z�[�W���\�[�X��`�t�@�C���isystem-messages.properties�j
     *      �̃��b�Z�[�W���\�[�X</li>
     * </ol>
     * <p>
     *  ���ׂẴ��b�Z�[�W���\�[�X�ɏ������������s���Ȃ������ꍇ�A
     *  ���邢�́A�ǂ̒�`��������b�Z�[�W�L�[�ɊY������l��
     *  �擾�ł��Ȃ������ꍇ�A��������returnNull�w��ɂ���āA
     *  null���A���邢��Struts�̌`���i???Locale.key???�j�ŕԋp�����B
     * </p>
     * <p>
     *  �Ȃ��A�����Ŏw�肳��Ă��郍�P�[���͍l������Ȃ��B
     *  ���Ȃ킿getMessage(key)�Ɠ������������B
     * </p>
     *
     * @param locale ���b�Z�[�W���P�[���B�l������Ȃ�
     * @param key ���b�Z�[�W�L�[
     * 
     * @return locale��key�ɑΉ����郁�b�Z�[�W����
     */
    @Override
    public String getMessage(Locale locale, String key) {
        MessageResources globalMessageResources = null;
        
        if (log.isDebugEnabled()) {
            log.debug("call getMessage(Locale, String)");
        }
        
        if (key == null || "".equals(key)) {
            log.error("Message key 'null' or empty not allowed.");
            throw new SystemException(
                null, DB_MESSAGE_RESOURCES_ERROR);
        }
        
        // ���b�Z�[�W���\�[�X��`�t�@�C������̃��b�Z�[�W�����擾
        if (messages != null) {
            String retMessage = messages.get(key);
            if (retMessage != null) {
                return retMessage;
            }
        }

        // DB����̃��b�Z�[�W�����擾
        if (dbMessages != null) {
            String retMessage = (String) dbMessages.get(key);
            if (retMessage != null) {
                return retMessage;
            }
        }

        // �Ɩ����ʁE�V�X�e������̃��b�Z�[�W�����擾
        globalMessageResources = GlobalMessageResources.getInstance();
        String retMessage = globalMessageResources.getMessage(locale, key);
        if (retMessage != null) {
            return retMessage;
        }
        
        // �ǂ��ɂ��Ȃ��ꍇ��returnNull�̐ݒ�̗L���ɉ����ĕԂ����̂�ς���
        if (!returnNull) {
            return "???" + messageKey(locale, key) + "???";
        }
        return null;        
    }

    /**
     * �w�肳�ꂽ�L�[�ɂ��ƂÂ����b�Z�[�W�������擾����B
     * �����L�[���قȂ郁�b�Z�[�W�̒�`�ꏊ�ɑ��݂��鎞�A�擾�����D�揇�ʂ�
     * ���L�̂悤�ɂȂ�B
     * <ol>
     *  <li>���b�Z�[�W���\�[�X��`�t�@�C��</li>
     *  <li>DB���̃��b�Z�[�W�L�[�ƃ��b�Z�[�W�����̓����Ă���e�[�u��</li>
     *  <li>�Ɩ����ʃ��b�Z�[�W���\�[�X�t�@�C����`�t�@�C��
     *      �iapplication-messages.properties�j�̃��b�Z�[�W</li>
     *  <li>�V�X�e�����b�Z�[�W���\�[�X��`�t�@�C���isystem-messages.properties�j
     *      �̃��b�Z�[�W</li>
     * </ol>
     *  ���ׂẴ��b�Z�[�W���\�[�X�ɏ������������s���Ȃ������ꍇ�A
     *  ���邢�́A�ǂ̒�`��������b�Z�[�W�L�[�ɊY������l��
     *  �擾�ł��Ȃ������ꍇ�Anull���ԋp�����B
     *
     * @param key ���b�Z�[�W�L�[
     * 
     * @return key�ɑΉ����郁�b�Z�[�W����
     */
    @Override
    public String getMessage(String key) {
        Locale locale = null;
        return getMessage(locale , key);
    }
    
}
