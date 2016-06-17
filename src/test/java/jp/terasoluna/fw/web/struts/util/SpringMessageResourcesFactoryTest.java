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

import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.mock.MockWebApplicationContext;
import junit.framework.TestCase;

import org.apache.struts.util.MessageResources;
import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * {@link SpringMessageResourcesFactory} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * SpringMessageResourcesFactory�𐶐�����t�@�N�g���N���X<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory
 */
public class SpringMessageResourcesFactoryTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SpringMessageResourcesFactoryTest.class);
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
     * @see junit.framework.TestCase#setUp()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l��
        // ���b�N��ApplicationContext���Z�b�g���Ă����B
        Map currentContextPerThread = (Map) UTUtil.getPrivateField(
                ContextLoader.class, "currentContextPerThread");
        String[] configLocations = new String[] {
        "jp/terasoluna/fw/web/struts/util/SpringMessageResourcesFacotryTest01.xml"};
        this.context = new MockWebApplicationContext(configLocations);
        currentContextPerThread.put(
            Thread.currentThread().getContextClassLoader(), this.context);
    }

    /**
     * �I���������s��.
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        this.context = null;
    }
    
    /**
     * testCreateResources01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) config:"notExistBeanName"�i���݂��Ȃ�Bean���j<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:-<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.bean.exception"<br>
     *                    ���b�v������O�FBeansException�̃T�u�N���X<br>
     *         (��ԕω�) ���O:�yERROR���O�z"notExistBeanName is not found or it is not MessageSource instance."<br>
     *         
     * <br>
     * ���݂��Ȃ�Bean����n�����ꍇ�A�G���[���O���o�͂��A
     * ��O���X���[����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources01() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // �e�X�g���{
        try {
            factory.createResources("notExistBeanName");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.bean.exception", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError("notExistBeanName is not found or it is not MessageSource instance."));
        }
    }
    
    /**
     * testCreateResources02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:""�i�󕶎��j<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config=""<br>
     *                    returnNull=MessageResourcesFactory.getReturnNull()<br>
     *                    messageSource=�O������ŃZ�b�g����applicationContext<br>
     *         
     * <br>
     * �󕶎���Bean����n�����ꍇ�ASpringMessageResources����������邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources02() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // �e�X�g���{
        MessageResources resources = factory.createResources("");
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                    UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testCreateResources03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:null<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config=null<br>
     *                    returnNull=MessageResourcesFactory.getReturnNull()<br>
     *                    messageSource=�O������ŃZ�b�g����applicationContext<br>
     *         
     * <br>
     * null��Bean����n�����ꍇ�ASpringMessageResources����������邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources03() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // �e�X�g���{
        MessageResources resources = factory.createResources(null);
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(null, UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                    UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testCreateResources04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:"messageSource"�i���݂���Bean���j<br>
     *         (�O�����) applicationContext:���b�N��ApplicationContext<br>
     *         (�O�����) ContextLoader.getCurrentWebApplicationContext()�̕ԋp�l:
     *                    applicationContext�Ɠ������b�N��ApplicationContext<br>
     *         (�O�����) Bean��`�t�@�C��:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * ���Ғl�F(�߂�l) SpringMessageResources:<br>
     *                    factory=�߂�l�𐶐�����SpringMessageResourcesFactory�C���X�^���X���g<br>
     *                    config="messageSource"<br>
     *                    returnNull=MessageResourcesFactory.getReturnNull()<br>
     *                    messageSource=MessageSource�̃T�u�N���X<br>
     *         
     * <br>
     * ���݂���Bean����n�����ꍇ�ASpringMessageResources����������邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources04() throws Exception {
        // �O����
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // �e�X�g���{
        MessageResources resources = factory.createResources("messageSource");
        
        // ����
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("messageSource", UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                    UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
    }
}
