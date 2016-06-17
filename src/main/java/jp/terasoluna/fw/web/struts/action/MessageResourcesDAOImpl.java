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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * MessageResourcesDAO�̎����N���X�B
 * 
 * DBMessageResourcesFactory�����b�Z�[�W���\�[�X�t�@�N�g���N���X�Ƃ���
 * �p����ꍇ�ADBMessageResources��MessageResourcesDAO�����������N���X��
 * �g�p����B�ڍׂ�{@link DBMessageResources}���Q�Ƃ̂��ƁB
 *
 * <h5>�g�p���@</h5>
 * DBMessageResourcesDAOImpl��Spring�t���[�����[�N�𗘗p���������ł���B
 * ������Struts�t���[�����[�N�̃A�[�L�e�N�`���ł�DI�R���e�i���擾����p��
 * �����Ȃ����߁A�f�[�^�\�[�X�̒�`���p�ɍs���K�v������B<br>
 * DB�A�N�Z�X�ɕK�v�ȃf�[�^�\�[�X�̒�`��dbMessageResources.xml�Ƃ������O��
 * Bean��`�t�@�C���ɉ��L�̂悤�ɒ�`���A�N���X�p�X��ɒu���A�ȉ��̂悤�ɏ����B<br>
 * ������Bean��`�t�@�C����BLogic�Ȃǂ̐ݒ�ɗp������̂Ƃ͕ʂɗp�ӂ��邱�ƁB<br>
 * <strong>JNDI����f�[�^�\�[�X���擾����ꍇ</strong><br>
 * <pre><code>
 * &lt;bean id="sampleDataSource" 
 *       class="org.springframework.jndi.JndiObjectFactoryBean" 
 *       abstract="false" scope="singleton" 
 *       lazy-init="default" 
 *       autowire="default" 
 *       dependency-check="default"&gt;
 *   &lt;property name="jndiName" 
 *             value="sampleJndiDataSource" /&gt; 
 * &lt;/bean&gt;
 * </code></pre>
 * �e�v�f�̑����̓V�X�e���ɂ���ĈقȂ�̂Ŋm�F���邱�ƁB<br>
 * <br>
 * <strong>JNDI�𗘗p���Ȃ��ꍇ</strong><br>
 * <pre><code>
 * &lt;bean id="dataSource"
 *       class="org.springframework.jdbc.datasource.DriverManagerDataSource"&gt;
 *   &lt;property name="driverClassName"&gt;
 *     &lt;value&gt;oracle.jdbc.OracleDriver&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name="url"&gt;
 *     &lt;value&gt;jdbc:oracle:thin:@192.0.34.166:1521:ORCL&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name="username"&gt;&lt;value&gt;username&lt;/value&gt;&lt;/property&gt;
 *   &lt;property name="password"&gt;&lt;value&gt;password&lt;/value&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </code></pre>
 * ��L�̐ݒ�̂����A����4�̓V�X�e���ɂ���ĈقȂ�̂Ŋm�F���邱�ƁB
 * <ol>
 *   <li>�h���C�o�N���X���ioracle.jdbc.OracleDriver�̕����j</li>
 *   <li>URL�ijdbc:oracle:thin:@192.0.34.166:1521:ORCL�̕����j</li>
 *   <li>���[�U���iusername�̕����j</li>
 *   <li>�p�X���[�h�ipassword�̕����j</li>
 * </ol>
 * 
 * �Ȃ��AWeb�A�v���P�[�V�����Ŏg�p����ꍇ�͊�{�I��JNDI��p����ق��𐄏�����B
 * 
 */
public class MessageResourcesDAOImpl implements MessageResourcesDAO {

    /**
     * �f�[�^�\�[�X�̒�`����Ă���Bean��`�t�@�C���B
     */
    public static final String BEANS_XML
        = "dbMessageResources.xml";
    
    /**
     * �f�[�^�\�[�Xbean���擾����ۂ�id�l�B
     */
    private static final String DATASOURCE_BEAN_ID
        = "dataSource";
    
    /**
     * �f�[�^�\�[�X�̎擾���s��\���G���[�R�[�h�B
     */
    private static final String MESSAGE_RESOURCES_DAO_IMPL_ERROR
        = "errors.message.resources.dao.impl";
    
    /**
     * ���O�N���X�B
     */
    private static Log log 
        = LogFactory.getLog(MessageResourcesDAOImpl.class);
    
    /**
     * ���b�Z�[�W���\�[�X���擾����B
     * 
     * �^����ꂽSQL�̑��J������Map�̃L�[�A���J������Map�̒l�Ƃ���
     * �ݒ肵��Map��Ԃ��B
     * SQL�ŕԂ��ꂽ���ʃZ�b�g�̑��J�����ɏd�����������ꍇ�́A�ǂꂩ1����
     * �o�^����Ă��Ȃ�Map��Ԃ��B
     * 
     * @param sql ���b�Z�[�W���\�[�X���擾���邽�߂�SQL
     * 
     * @return �L�[�Ƀ��b�Z�[�W�L�[�A�l�Ƀ��b�Z�[�W�����̓�����Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> queryMessageMap(String sql) {
        
        if (log.isDebugEnabled()) {
            log.debug("call queryMessageMap()");
        }

        // �f�[�^�\�[�X�̃I�u�W�F�N�g�𐶐�����DI�R���e�i�𐶐�
        ApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext(getBeansXml());
        } catch (BeansException e) {
            // �t�@�C�����Ȃ��AXML�\����invalid�ł���A�Ȃ�
            log.error(getBeansXml() + " not found or not " 
                      + "-//SPRING//DTD BEAN//EN or invalid or anything else.",
                      e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        // DI�R���e�i����f�[�^�\�[�X���擾
        DataSource ds = null;

        try {
            ds = (DataSource) context.getBean(DATASOURCE_BEAN_ID);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("\"dataSource\" not defined.", e);
          throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        } catch (ClassCastException e) {
            log.error("\"dataSource\" not implemented DataSource.", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        } catch (BeansException e) {
            log.error("getBean() failed", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        // O/R�}�b�s���O
        DBMessageBeanMappingSqlQuery query
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        try {
            query.compile();
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("SQL is null or something wrong.", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        List<DBMessageBean> dbMessageList = null;
        try {
            dbMessageList = query.execute();
        } catch (DataAccessException e) {
            log.error("Data access exception.", e);
            throw new SystemException(e, MESSAGE_RESOURCES_DAO_IMPL_ERROR);
        }
        
        if (log.isDebugEnabled()) {
            log.debug("db message acquired");
        }
        
        // ���b�Z�[�W���\�[�X��Map�ɋl�ߑւ�
        Map<String, String> dbMessages 
            = new HashMap<String, String>(dbMessageList.size());
        Iterator<DBMessageBean> iterator = dbMessageList.iterator();
        DBMessageBean dbmb = null;
        while (iterator.hasNext()) {
            dbmb = iterator.next();
            if (log.isDebugEnabled()) {
                log.debug("Saving db message key [" + dbmb.getKey() + "], "
                          + "value [" + dbmb.getValue() + "]");
            }
            dbMessages.put(dbmb.getKey(), dbmb.getValue());
        }
        return dbMessages;
    }

    /**
     * �f�[�^�\�[�X����`���ꂽBean��`�t�@�C�������擾����B
     * 
     * @return �f�[�^�\�[�X����`���ꂽBean��`�t�@�C����
     */
    protected String getBeansXml() {
        return BEANS_XML;
    }

}
