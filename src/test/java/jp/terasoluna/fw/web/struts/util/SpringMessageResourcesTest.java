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

package jp.terasoluna.fw.web.struts.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.MessageFormatCloneReturnIfUseDateFormatMap;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.mock.MockWebApplicationContext;
import junit.framework.TestCase;

import org.apache.struts.util.MessageResources;
import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * {@link SpringMessageResources} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Spring�̃��b�Z�[�W�\�[�X��Struts���痘�p����MessageResources�����N���X�B<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.util.SpringMessageResources
 */
public class SpringMessageResourcesTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SpringMessageResourcesTest.class);
    }

    /**
     * ���̃e�X�g�P�[�X�ŗ��p����ApplicationContext
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    private WebApplicationContext context = null;
    
    /**
     * �������������s��.
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void setUpData() throws Exception {
        // ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l��
        // ���b�N��ApplicationContext���Z�b�g���Ă����B
        Map currentContextPerThread = (Map) UTUtil.getPrivateField(
                ContextLoader.class, "currentContextPerThread");
        String[] configLocations = new String[] {
        "jp/terasoluna/fw/web/struts/util/SpringMessageResourcesTest01.xml"};
        this.context = new MockWebApplicationContext(configLocations);
        currentContextPerThread.put(
            Thread.currentThread().getContextClassLoader(), this.context);
    }

    /**
     * �I���������s��.
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        this.context = null;
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:SpringMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:"propertyMessageSource01"�i���݂���Bean���j<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config="propertyMessageSource01"<br>
     *                    messageSource=MessageSource�̃T�u�N���X<br>
     *                    formats=HashMap�C���X�^���X<br>
     *         
     * <br>
     * ���݂���Bean����n�����ꍇ�AinitMessageSource()���Ăяo����邱�Ƃ��m�F�B<br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�̂Ƃ��A
     * formats�͍����ւ����Ȃ����Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSpringMessageResourcesMessageResourcesFactoryString01() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "disable");
        
        // �e�X�g���{
        MessageResources resources = 
            new SpringMessageResources(factory, "propertyMessageSource01");
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("propertyMessageSource01", UTUtil.getPrivateField(resources, "config"));
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryString02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FAC
     * <br><br>
     * ���͒l�F(����) factory:SpringMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:""�i�󕶎��j<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config=""<br>
     *                    messageSource=�O������ŃZ�b�g����applicationContext<br>
     *                    formats=MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         
     * <br>
     * �󕶎���Bean����n�����ꍇ�AinitMessageSource()���Ăяo����邱�Ƃ��m�F�B<br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�ȊO�̂Ƃ��A
     * formats�����̖߂�l�ɍ����ւ����邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSpringMessageResourcesMessageResourcesFactoryString02() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // �e�X�g���{
        MessageResources resources = 
            new SpringMessageResources(factory, "");
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryString03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:SpringMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:null<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config=null<br>
     *                    messageSource=�O������ŃZ�b�g����applicationContext<br>
     *         
     * <br>
     * null��Bean����n�����ꍇ�AinitMessageSource()���Ăяo����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSpringMessageResourcesMessageResourcesFactoryString03() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // �e�X�g���{
        MessageResources resources = 
            new SpringMessageResources(factory, null);
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryStringboolean01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:SpringMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:"propertyMessageSource01"�i���݂���Bean���j<br>
     *         (����) returnNull:true<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config="propertyMessageSource01"<br>
     *                    returnNull=true<br>
     *                    messageSource=MessageSource�̃T�u�N���X<br>
     *                    formats=HashMap�C���X�^���X<br>
     *         
     * <br>
     * ���݂���Bean����n�����ꍇ�AinitMessageSource()���Ăяo����邱�Ƃ��m�F�B<br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�̂Ƃ��A
     * formats�͍����ւ����Ȃ����Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSpringMessageResourcesMessageResourcesFactoryStringboolean01() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "disable");
        
        // �e�X�g���{
        MessageResources resources = 
            new SpringMessageResources(factory, "propertyMessageSource01" ,true);
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("propertyMessageSource01", UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryStringboolean02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FAC
     * <br><br>
     * ���͒l�F(����) factory:SpringMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:""�i�󕶎��j<br>
     *         (����) returnNull:false<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config=""<br>
     *                    returnNull=false<br>
     *                    messageSource=�O������ŃZ�b�g����applicationContext<br>
     *                    formats=MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         
     * <br>
     * �󕶎���Bean����n�����ꍇ�AinitMessageSource()���Ăяo����邱�Ƃ��m�F�B<br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�ȊO�̂Ƃ��A
     * formats�����̖߂�l�ɍ����ւ����邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSpringMessageResourcesMessageResourcesFactoryStringboolean02() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // �e�X�g���{
        MessageResources resources = 
            new SpringMessageResources(factory, "", false);
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(false, UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryStringboolean03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:SpringMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:null<br>
     *         (����) returnNull:true<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config=null<br>
     *                    returnNull=true<br>
     *                    messageSource=�O������ŃZ�b�g����applicationContext<br>
     *         
     * <br>
     * null��Bean����n�����ꍇ�AinitMessageSource()���Ăяo����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSpringMessageResourcesMessageResourcesFactoryStringboolean03() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // �e�X�g���{
        MessageResources resources = 
            new SpringMessageResources(factory, null, true);
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testInitMessageSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) �Ȃ�
     *         (�O�����) factory:not null<br>
     *         (�O�����) config:""�i�󕶎��j<br>
     *         (�O�����) returnNull:true<br> 
     *         (�O�����) context:null<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�<br>
     *         (��ԕω�) messageSource:-<br>
     *         (��ԕω�) ��O:-<br>
     *         (��ԕω�) ���O:�yWARN���O�z"ApplicationContext is not found."<br>
     *         
     * <br>
     * context��null�̏ꍇ�AWARN���O���o�͂���邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMessageSource01() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // context��null�ɏ���������B
        UTUtil.setPrivateField(resources, "context", null);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // ����
        assertTrue(LogUTUtil.checkWarn("ApplicationContext is not found."));
    }
    
    /**
     * testInitMessageSource02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�
     *         (�O�����) factory:not null<br>
     *         (�O�����) config:""�i�󕶎��j<br>
     *         (�O�����) returnNull:true<br> 
     *         (�O�����) context:���b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�<br>
     *         (��ԕω�) messageSource:�O������ŃZ�b�g����applicationContext<br>
     *         (��ԕω�) ��O:-<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * config���󕶎��̏ꍇ�A�f�t�H���g�̃��b�Z�[�W�\�[�X���Z�b�g����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMessageSource02() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // ��xmessageSource������������B
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // ����
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testInitMessageSource03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�
     *         (�O�����) factory:not null<br>
     *         (�O�����) config:null<br>
     *         (�O�����) returnNull:true<br> 
     *         (�O�����) context:���b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�<br>
     *         (��ԕω�) messageSource:�O������ŃZ�b�g����applicationContext<br>
     *         (��ԕω�) ��O:-<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * config��null�̏ꍇ�A�f�t�H���g�̃��b�Z�[�W�\�[�X���Z�b�g����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMessageSource03() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), null ,true);
        // ��xmessageSource������������B
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // ����
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testInitMessageSource04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�
     *         (�O�����) factory:not null<br>
     *         (�O�����) config:"propertyMessageSource01"�i���݂���Bean���j<br>
     *         (�O�����) returnNull:true<br> 
     *         (�O�����) context:���b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�<br>
     *         (��ԕω�) messageSource:MessageSource�̃T�u�N���X<br>
     *         (��ԕω�) ��O:-<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * config�����݂���Bean���̏ꍇ�A���b�Z�[�W�\�[�X���Z�b�g����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMessageSource04() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        // ��xmessageSource������������B
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // ����
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
    }
    
    /**
     * testInitMessageSource05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) �Ȃ�
     *         (�O�����) factory:not null<br>
     *         (�O�����) config:"notExistBeanName"�i���݂��Ȃ�Bean���j<br>
     *         (�O�����) returnNull:true<br> 
     *         (�O�����) context:���b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�<br>
     *         (��ԕω�) messageSource:MessageSource�̃T�u�N���X<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.bean.exception"<br>
     *                    ���b�v������O�FBeansException�̃T�u�N���X<br>
     *         (��ԕω�) ���O:�yERROR���O�z"notExistBeanName is not found or it is not MessageSource instance."<br>
     *         
     * <br>
     * config�����݂��Ȃ�Bean���̏ꍇ�A�G���[���O���o�͂��A
     * ��O���X���[����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMessageSource05() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // ��xmessageSource������������B
        UTUtil.setPrivateField(resources, "messageSource", null);
        UTUtil.setPrivateField(resources, "config", "notExistBeanName");
        
        // �e�X�g���{
        try {
            UTUtil.invokePrivate(resources, "initMessageSource");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.bean.exception", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError("notExistBeanName is not found or it is not MessageSource instance."));
        }
    }
    
    /**
     * testGetMessage01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:null<br>
     *         (����) key:"property.message.key"<br>
     *         (�O�����) config:""�i�󕶎��j<br>
     *         (�O�����) messageSource:null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * messageSource��null��returnNull��true�̏ꍇ�Anull���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage01() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // messageSource��null�ɏ���������B
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // �e�X�g���{
        String message = resources.getMessage((Locale) null, "property.message.key");
        
        // ����
        assertNull(message);
    }
    
    /**
     * testGetMessage02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:null<br>
     *         (����) key:"not.exist.key"�i���݂��Ȃ��L�[�j<br>
     *         (�O�����) config:"propertyMessageSource01"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * locale��null��returnNull��true�̏ꍇ�A�f�t�H���g���P�[���ɑ��݂��Ȃ��L�[��
     * �n����null���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage02() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage((Locale) null, "not.exist.key");
        
        // ����
        assertNull(message);
    }
    
    /**
     * testGetMessage03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.getDefault()<br>
     *         (����) key:"not.exist.key"�i���݂��Ȃ��L�[�j<br>
     *         (�O�����) config:"propertyMessageSource01"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * locale���f�t�H���g��returnNull��true�̏ꍇ�A�f�t�H���g���P�[���ɑ��݂��Ȃ��L�[��
     * �n����null���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage03() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.getDefault(), "not.exist.key");
        
        // ����
        assertNull(message);
    }
    
    /**
     * testGetMessage04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.ENGLISH<br>
     *         (����) key:"not.exist.key_en"�i���݂��Ȃ��L�[�j<br>
     *         (�O�����) config:"propertyMessageSource_locale_en"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest02_en.properties<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * locale���w�肵��returnNull��true�̏ꍇ�A�w�肵�����P�[���ɑ��݂��Ȃ��L�[��
     * �n����null���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage04() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource_locale_en" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.ENGLISH, "not.exist.key_en");
        
        // ����
        assertNull(message);
    }
    
    /**
     * testGetMessage05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:null<br>
     *         (����) key:"property.message.key"<br>
     *         (�O�����) config:"propertyMessageSource01"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * locale��null�̏ꍇ�A����Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage05() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage((Locale) null, "property.message.key");
        
        // ����
        assertEquals("�e�X�g���b�Z�[�W", message);
    }
    
    /**
     * testGetMessage06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.getDefault()<br>
     *         (����) key:"property.message.key"<br>
     *         (�O�����) config:"propertyMessageSource01"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * �f�t�H���g���P�[���Ő���Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage06() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.getDefault(), "property.message.key");
        
        // ����
        assertEquals("�e�X�g���b�Z�[�W", message);
    }
    
    /**
     * testGetMessage07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.ENGLISH<br>
     *         (����) key:"property.message.key"<br>
     *         (�O�����) config:"propertyMessageSource_locale_en"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest02_en.properties<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"test message"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * �w�肵�����P�[���Ő���Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage07() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource_locale_en" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.ENGLISH, "property.message.key");
        
        // ����
        assertEquals("test message", message);
    }
    
    /**
     * testGetMessage08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:null<br>
     *         (����) key:"not.exist.key"�i���݂��Ȃ��L�[�j<br>
     *         (�O�����) config:"propertyMessageSource01"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:false<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"???.not.exist.key???"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * locale��null��returnNull��false�̏ꍇ�A�f�t�H���g���P�[���ɑ��݂��Ȃ��L�[��
     * �n���ƃ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage08() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,false);
        
        // �e�X�g���{
        String message = resources.getMessage((Locale) null, "not.exist.key");
        
        // ����
        assertEquals("???.not.exist.key???", message);
    }
    
    /**
     * testGetMessage09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.getDefault()<br>
     *         (����) key:"not.exist.key"�i���݂��Ȃ��L�[�j<br>
     *         (�O�����) config:"propertyMessageSource01"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:false<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"???.not.exist.key???"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * locale���f�t�H���g��returnNull��false�̏ꍇ�A�f�t�H���g���P�[���ɑ��݂��Ȃ��L�[��
     * �n���ƃ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage09() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,false);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.getDefault(), "not.exist.key");
        
        // ����
        assertEquals("???ja_JP.not.exist.key???", message);
    }
    
    /**
     * testGetMessage10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.ENGLISH<br>
     *         (����) key:"not.exist.key_en"�i���݂��Ȃ��L�[�j<br>
     *         (�O�����) config:"propertyMessageSource_locale_en"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:false<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest02_en.properties<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"???en.not.exist.key_en???"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * locale���w�肵��returnNull��false�̏ꍇ�A�w�肵�����P�[���ɑ��݂��Ȃ��L�[��
     * �n���ƃ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage10() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource_locale_en" ,false);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.ENGLISH, "not.exist.key_en");
        
        // ����
        assertEquals("???en.not.exist.key_en???", message);
    }
    
    /**
     * testGetMessage11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.getDefault()<br>
     *         (����) key:"property.message.key"<br>
     *         (�O�����) config:"dbMessageSource01"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         (�O�����) DB���b�Z�[�W:SpringMessageResourcesTest_DataSourceMessageSourceStub01<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�gDB���b�Z�[�W"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * ��ꃁ�b�Z�[�W�\�[�X��DB���b�Z�[�W�\�[�X�A��񃁃b�Z�[�W�\�[�X��
     * �v���p�e�B���b�Z�[�W�\�[�X���w�肵���ꍇ�A�o���̃��b�Z�[�W�\�[�X��
     * ���ʂɑ��݂��Ă��郁�b�Z�[�W�L�[��n���ƁA��ꃁ�b�Z�[�W�\�[�X����
     * ����Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage11() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "dbMessageSource01" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.getDefault(), "property.message.key");
        
        // ����
        assertEquals("�e�X�gDB���b�Z�[�W", message);
    }
    
    /**
     * testGetMessage12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.getDefault()<br>
     *         (����) key:"property.message.key"<br>
     *         (�O�����) config:"dbMessageSource02"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         (�O�����) DB���b�Z�[�W:SpringMessageResourcesTest_DataSourceMessageSourceStub02<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * ��ꃁ�b�Z�[�W�\�[�X��DB���b�Z�[�W�\�[�X�A��񃁃b�Z�[�W�\�[�X��
     * �v���p�e�B���b�Z�[�W�\�[�X���w�肵���ꍇ�A��ꃁ�b�Z�[�W�\�[�X��
     * ���݂��Ȃ����b�Z�[�W�L�[��n���ƁA��񃁃b�Z�[�W�\�[�X����
     * ����Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage12() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "dbMessageSource02" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.getDefault(), "property.message.key");
        
        // ����
        assertEquals("�e�X�g���b�Z�[�W", message);
    }
    
    /**
     * testGetMessage13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.getDefault()<br>
     *         (����) key:"db.message.key"<br>
     *         (�O�����) config:"propertyMessageSource02"<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     *         (�O�����) DB���b�Z�[�W:SpringMessageResourcesTest_DataSourceMessageSourceStub02<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�gDB���b�Z�[�W"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * ��ꃁ�b�Z�[�W�\�[�X�Ƀv���p�e�B���b�Z�[�W�\�[�X�A��񃁃b�Z�[�W�\�[�X��
     * DB���b�Z�[�W�\�[�X���w�肵���ꍇ�A��ꃁ�b�Z�[�W�\�[�X��
     * ���݂��Ȃ����b�Z�[�W�L�[��n���ƁA��񃁃b�Z�[�W�\�[�X����
     * ����Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage13() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource02" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.getDefault(), "db.message.key");
        
        // ����
        assertEquals("�e�X�gDB���b�Z�[�W", message);
    }
    
    /**
     * testGetMessage14()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) locale:Locale.getDefault()<br>
     *         (����) key:"default.message.key"<br>
     *         (�O�����) config:""�i�󕶎��j<br>
     *         (�O�����) messageSource:not null<br>
     *         (�O�����) returnNull:true<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesTest01.xml<br>
     *         (�O�����) ���b�Z�[�W�v���p�e�B�t�@�C��:SpringMessageResourcesTest01.properties<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"�f�t�H���g���b�Z�[�W"<br>
     *         (��ԕω�) ���O:-<br>
     *         
     * <br>
     * config���󕶎��ł������ꍇ�ABean��"messageSource"�ł��郁�b�Z�[�W�\�[�X��
     * �f�t�H���g�Ŏ擾���A����Ƀ��b�Z�[�W���ԋp����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage14() throws Exception {
        // �O����
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        
        // �e�X�g���{
        String message = resources.getMessage(Locale.getDefault(), "default.message.key");
        
        // ����
        assertEquals("�f�t�H���g���b�Z�[�W", message);
    }
}
