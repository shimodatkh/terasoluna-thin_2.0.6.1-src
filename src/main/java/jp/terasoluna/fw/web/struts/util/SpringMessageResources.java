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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * Spring�̃��b�Z�[�W�\�[�X��Struts���痘�p����MessageResources�����N���X�B<br>
 * </p>
 * <p>
 * SpringMessageResourcesFactory��struts-config.xml��message-resources�v�f
 * ��factory�����ɐݒ肷��B<br>
 * <ul>
 *   <li>parameter�����l��MessageSource��BeanID���w�肵���ꍇ�́A
 *       �w�肵��MessageSource���g����B<br>
 *   <strong>�����̂Ƃ��w�肵��Bean��������Ȃ��ꍇ��A�擾����Bean��
 *   MessageSource�C���X�^���X�łȂ��ꍇ��AP�T�[�o�N�����ɗ�O����������B</strong>
 *   </li>
 *   <li>parameter�����l���ȗ������ꍇ�̓f�t�H���g�� "messageSource" ���g����B
 *   </li>
 * </ul>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>struts-config.xml�ݒ��i��parameter�ȗ����j</legend>
 * <pre><code>
 * &lt;message-resources parameter=""
 *   factory="jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory"/&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>struts-config.xml�ݒ��i��parameter�w�莞�j</legend>
 * <pre><code>
 * &lt;message-resources parameter="hogeMessageSource"
 *   factory="jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory"/&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>Bean��`�t�@�C���ݒ��</legend>
 * ResourceBundleMessageSource�𗘗p����ꍇ�Abasenames�v���p�e�B�ɂ�
 * ���b�Z�[�W�v���p�e�B�t�@�C�����w�肷��B
 * <pre><code>
 * &lt;bean id="messageSource"
 *       class="org.springframework.context.support.ResourceBundleMessageSource"&gt;
 *   &lt;property name="basenames" value="MessageResources"/&gt;
 * &lt;/bean&gt;
 * </code></pre>
 * 
 * DataSourceMessageSource�𗘗p�����DB���b�Z�[�W��������悤�ɂȂ�B
 * <pre><code>
 * &lt;bean id="messageSource"
 *       class="jp.terasoluna.fw.message.DataSourceMessageSource"&gt;
 *   &lt;property name="dbMessageResourceDAO" ref="dbMessageResourceDAO"/&gt;
 * &lt;/bean&gt;
 * </code></pre> 
 * </fieldset>
 * </p>
 * 
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>��Q���b�Z�[�W�\�[�X���`����ꍇ��Bean��`�t�@�C���ݒ��</legend>
 * <pre><code>
 * &lt;bean id="messageSource"
 *       class="jp.terasoluna.fw.message.DataSourceMessageSource"&gt;
 *   &lt;property name="dbMessageResourceDAO" ref="dbMessageResourceDAO"/&gt;    
 *   &lt;property name="parentMessageSource" ref="parentSource"/&gt;
 * &lt;/bean&gt;
 * 
 * &lt;bean id="parentSource"
 *       class="org.springframework.context.support.ResourceBundleMessageSource"&gt;
 *   &lt;property name="basenames" value="MessageResources"/&gt;
 * &lt;/bean&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * ���̃N���X�́A
 * Won't Fix�ƂȂ��Ă���Struts�̃o�O STR-2172(https://issues.apache.org/jira/browse/STR-2172)
 * ����������i��L���Ă���B<br>
 * �ڍׂ́A{@link MessageFormatCacheMapFactory} ���Q�ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory
 * @see jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory
 * @see org.springframework.context.support.ResourceBundleMessageSource
 * @see jp.terasoluna.fw.message.DataSourceMessageSource
 *
 */
public class SpringMessageResources extends MessageResources {

    /** 
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 8870342287587564386L;

    /**
     *  ���O�C���X�^���X
     */
    private static Log log = LogFactory.getLog(SpringMessageResources.class);

    /** 
     * �G���[���b�Z�[�W�L�[
     */
    private static final String ERR_BEAN_EXCEPTION = "errors.message.bean.exception";

    /** 
     * �A�v���P�[�V�����R���e�L�X�g
     */
    private WebApplicationContext context = null;

    /** 
     * Spring�̃��b�Z�[�W�\�[�X
     */
    private MessageSource messageSource = null;

    /**
     * �w�肳�ꂽ�p�����[�^�ɂ����SpringMessageResources�𐶐�����B
     *
     * @param factory ���b�Z�[�W���\�[�X�t�@�N�g��
     * @param config �R���e�i����擾����MessageSource��Bean��
     *               �i�ȗ����̓f�t�H���g��"messageSource"�j
     */
    public SpringMessageResources(MessageResourcesFactory factory, String config) {
        super(factory, config);
        replaceMessageFormatCache();
        this.context = ContextLoader.getCurrentWebApplicationContext();

        initMessageSource();
    }

    /**
     * �w�肳�ꂽ�p�����[�^�ɂ����SpringMessageResources�𐶐�����B
     *
     * @param factory ���b�Z�[�W���\�[�X�t�@�N�g��
     * @param config �R���e�i����擾����MessageSource��Bean��
     *               �i�ȗ����̓f�t�H���g��"messageSource"�j
     * @param returnNull <code>org.apache.struts.util.MessageResources</code>
     *                   �N���X�� <code>returnNull</code>�B
     *                   <code>false</code> �w�莞�A�L�[�ɊY�����郁�b�Z�[�W��
     *                   ���݂��Ȃ��ꍇ???Locale.key???�Ƃ����`���Ń��b�Z�[�W��
     *                   �ԋp����B
     */
    public SpringMessageResources(MessageResourcesFactory factory,
            String config, boolean returnNull) {
        super(factory, config, returnNull);
        replaceMessageFormatCache();
        this.context = ContextLoader.getCurrentWebApplicationContext();

        initMessageSource();
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
     * MessageSource�̏��������s���B
     */
    private void initMessageSource() {
        if (this.context != null) {
            if (config != null && !("".equals(config))) {
                // �p�����[�^�Ɏw�肳�ꂽBeanID���擾����
                try {
                    this.messageSource = (MessageSource) this.context.getBean(
                            config, MessageSource.class);
                } catch (BeansException e) {
                    if (log.isErrorEnabled()) {
                        StringBuilder mes = new StringBuilder();
                        mes.append(config);
                        mes.append(" is not found");
                        mes.append(" or it is not MessageSource instance.");
                        log.error(mes.toString());
                    }
                    throw new SystemException(e, ERR_BEAN_EXCEPTION);
                }

                if (log.isDebugEnabled()) {
                    StringBuilder mes = new StringBuilder();
                    mes.append(config);
                    mes.append(" MessageSource is used.");
                    log.debug(mes.toString());
                }
            }

            // �f�t�H���g��MessageSource���̗p�iApplicationContext���g�j
            if (this.messageSource == null) {
                this.messageSource = this.context;
                if (log.isDebugEnabled()) {
                    log.debug("Default MessageSource is used.");
                }
            }
        } else {
            if (log.isWarnEnabled()) {
                log.warn("ApplicationContext is not found.");
            }
        }
    }

    /**
     * �w�肳�ꂽ�L�[�ƃ��P�[���ɂ��ƂÂ����b�Z�[�W�������擾����B
     *
     * @see org.apache.struts.util.MessageResources#getMessage(java.util.Locale, java.lang.String)
     */
    @Override
    public String getMessage(Locale locale, String key) {
        if (this.messageSource != null) {
            String retMessage = null;

            try {
                retMessage = this.messageSource.getMessage(key, null, locale);
            } catch (NoSuchMessageException e) {
                // �������Ȃ�
            }
            
            if (retMessage != null) {
                if (log.isDebugEnabled()) {
                    StringBuilder mes = new StringBuilder();
                    mes.append("key:[");
                    mes.append(key);
                    mes.append("] locale:[");
                    mes.append(locale);
                    mes.append("] message:[");
                    mes.append(retMessage);
                    mes.append("]");
                    log.debug(mes.toString());
                }
                return retMessage;
            }
        }

        // ���b�Z�[�W�����݂��Ȃ��ꍇ�́AreturnNull�̐ݒ�̗L���ɏ]���ĕԋp����
        if (!returnNull) {
            StringBuilder mes = new StringBuilder();
            mes.append("???");
            mes.append(messageKey(locale, key));
            mes.append("???");
            return mes.toString();
        }
        return null;
    }

}
