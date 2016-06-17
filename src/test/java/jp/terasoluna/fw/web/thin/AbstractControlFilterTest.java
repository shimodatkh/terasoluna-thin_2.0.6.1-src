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

package jp.terasoluna.fw.web.thin;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * {@link jp.terasoluna.fw.web.thin.AbstractControlFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * bean�Ƃ��Ď������ꂽ�R���g���[����p���ăA�N�Z�X������s�����ۃN���X�B<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.AbstractControlFilter
 */
@SuppressWarnings("unused")
public class AbstractControlFilterTest extends SpringTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractControlFilterTest.class);
    }


    /**
     * testInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:�������p�����[�^���Fcontroller<br>
     *                �������p�����[�^�FtestControllerStub01<br>
     *         (���) this.config:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) config:�����ŗ^����ꂽFilterConfig�C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �^����ꂽconfig���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �O����
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "testControllerStub01");
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        UTUtil.setPrivateField(filter, "config", null);
        
        // �e�X�g���{
        filter.init(config);

        // ����
        assertSame(config,
                   UTUtil.getPrivateField(filter, "config"));
    }

    /**
     * testGetController01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) config.getInitParameter("controller"):null<br>
     *         (���) getErrorCode():"errors.test.controller"<br>
     *         (���) getBean():AbstractControlFilter_ControllerStub01�C���X�^���X
     *         (���) getControllerClass():AbstractCotnrollerFilter_TestController�N���X
     *         (���) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (���) Bean��`�t�@�C��:<bean id="testControllerStub01"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub01"/><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractControlFilter_Controller:DI�R���e�i����擾�ł���Controller�C���X�^���X�Ɠ������C���X�^���X<br>
     *         
     * <br>
     * �t�B���^�̏������p�����[�^����`����Ă��Ȃ��ꍇ�A
     * �f�t�H���g��bean���Ŏ擾���ꂽ�R���g���[����
     * �ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetController01() throws Exception {
        // �O����
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", null);
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // �e�X�g���{
        Object obj = filter.getController();

        // ����        
        assertEquals(obj.getClass().getName(), 
                     AbstractControlFilter_ControllerStub01.class.getName());
    }

    /**
     * testGetController02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) config.getInitParameter("controller"):""<br>
     *         (���) getErrorCode():"errors.test.controller"<br>
     *         (���) getBean():AbstractControlFilter_ControllerStub01�C���X�^���X
     *         (���) getControllerClass():AbstractCotnrollerFilter_TestController�N���X
     *         (���) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (���) Bean��`�t�@�C��:<bean id="testControllerStub01"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub01"/><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractControlFilter_Controller:DI�R���e�i����擾�ł���Controller�C���X�^���X�Ɠ������C���X�^���X<br>
     *         
     * <br>
     * �t�B���^�̏������p�����[�^���󕶎���̏ꍇ�A
     * �f�t�H���g��bean���Ŏ擾���ꂽ�R���g���[����
     * �ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetController02() throws Exception {
        // �O����
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // �e�X�g���{
        Object obj = filter.getController();

        // ����
        assertEquals(obj.getClass().getName(), 
                     AbstractControlFilter_ControllerStub01.class.getName());
    }

    /**
     * testGetController03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) config.getInitParameter("controller"):"testControllerStub02"<br>
     *         (���) getErrorCode():"errors.test.controller"<br>
     *         (���) getBean():AbstractControlFilter_ControllerStub1�C���X�^���X
     *         (���) getControllerClass():AbstractCotnrollerFilter_TestController�N���X
     *         (���) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (���) Bean��`�t�@�C��:<bean id="testControllerStub02"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub01"/><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractControlFilter_Controller:DI�R���e�i����擾�ł���Controller�C���X�^���X�Ɠ������C���X�^���X<br>
     *         
     * <br>
     * �t�B���^�ɏ������p�����[�^�𐳏�ɗ^�����ꍇ�A
     * �^����ꂽFilterConfig�C���X�^���X�ƁA
     * DI�R���e�i���珉�����p�����[�^���Ŏ擾�����C���X�^���X��
     * �ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetController03() throws Exception {
        // �O����
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "testControllerStub02");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // �e�X�g���{
        Object obj = filter.getController();

        // ����        
        assertEquals(obj.getClass().getName(), 
                     AbstractControlFilter_ControllerStub02.class.getName());
    }

    /**
     * testGetController04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) config.getInitParameter("controller"):"aaaaa"<br>
     *         (���) getErrorCode():"errors.test.controller"<br>
     *         (���) getBean():-
     *         (���) getControllerClass():AbstractCotnrollerFilter_TestController�N���X
     *         (���) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (���) Bean��`�t�@�C��:id������aaaaa�ł���<bean>�v�f����`����Ă��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractControlFilter_Controller:-<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.test.controller"<br>
     *                    ���b�v������O�FNoSuchBeanDefinitionException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    ���b�Z�[�W�F"not found aaaaa. controller bean not defined in Beans definition file."<br>
     *                    ��O�FNoSuchBeanDefinitionException<br>
     *         
     * <br>
     * Bean��`�t�@�C���Ɏw�肳�ꂽid������<bean>�v�f��
     * ��`����Ă��Ȃ������ꍇ�A�G���[���O���o�͂��A
     * ��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetController04() throws Exception {
        // �O����
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "aaaaa");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // �e�X�g���{
        try {
            Object obj = filter.getController();
            fail();
        } catch (SystemException e) {
            // ����   
            assertEquals("errors.test.controller", e.getErrorCode());
            assertEquals(e.getCause().getClass().getName(), 
                         NoSuchBeanDefinitionException.class.getName());
            assertTrue(LogUTUtil
                    .checkError("not found aaaaa. controller bean not defined in Beans definition file.",
                                e.getCause()));
        }                                   
    }

    /**
     * testGetController05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) config.getInitParameter("controller"):"object"<br>
     *         (���) getErrorCode():"errors.test.controller"<br>
     *         (���) getBean():-
     *         (���) getControllerClass():AbstractCotnrollerFilter_TestController�N���X
     *         (���) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (���) Bean��`�t�@�C��:<bean id="object"<br>
     *                        class="java.lang.Object"/><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractControlFilter_Controller:-<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.test.controller"<br>
     *                    ���b�v������O�FBeanNotOfRequiredTypeException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    ���b�Z�[�W�F"controller not implemented " + getControllerClass().toString() + "."<br>
     *                    ��O�FBeanNotOfRequiredTypeException<br>
     *         
     * <br>
     * Bean��`�t�@�C������擾�����C���X�^���X���t�B���^��
     * �w�肵�Ă���C���^�t�F�[�X���������Ă��Ȃ������ꍇ�A
     * �G���[���O���o�͂��A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetController05() throws Exception {
        // �O����
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "object");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // �e�X�g���{
        try {
            Object obj = filter.getController();
            fail();
        } catch (SystemException e) {
            // ����   
            assertEquals("errors.test.controller", e.getErrorCode());
            assertEquals(e.getCause().getClass().getName(),
                         BeanNotOfRequiredTypeException.class.getName());
            assertTrue(LogUTUtil
                    .checkError("controller not implemented " 
                                + filter.getControllerClass().toString() + ".",
                                e.getCause()));
        } 
    }

    /**
     * testGetController06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) config.getInitParameter("controller"):"testControllerStub03"<br>
     *         (���) getErrorCode():"errors.test.controller"<br>
     *         (���) getBean():-
     *         (���) getControllerClass():AbstractCotnrollerFilter_TestController�N���X
     *         (���) getDefaultControllerBeanId():"testControllerStub01"<br>
     *         (���) Bean��`�t�@�C��:<bean id="testControllerStub03"<br>
     *                        class="jp.terasoluna.fw.web.thin.AbstractControlFilter_ControllerStub02"<br>
     *                        abstract="true"/><br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AbstractControlFilter_Controller:-<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.test.controller"<br>
     *                    ���b�v������O�FBeansException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    ���b�Z�[�W�F"bean generation failed."<br>
     *                    ��O�FBeansException<br>
     *         
     * <br>
     * Bean��`�t�@�C������擾���悤�Ƃ����C���X�^���X�̃N���X��
     * �����ł��Ȃ��ꍇ�A�G���[���O���o�͂��A��O���X���[���邱�Ƃ�
     * �m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetController06() throws Exception {
        // �O����
        AbstractControlFilterImpl01 filter = new AbstractControlFilterImpl01();
        MockFilterConfig config = new MockFilterConfig();
        config.setInitParameter("controller", "testControllerStub03");
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(filter, "config", config);
        
        // �e�X�g���{
        try {
            Object obj = filter.getController();
            System.err.println(obj.getClass().toString());
            fail();
        } catch (SystemException e) {
            // ����   
            assertEquals("errors.test.controller", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertFalse(e.getCause() instanceof BeanNotOfRequiredTypeException);
            assertFalse(e.getCause() instanceof NoSuchBeanDefinitionException);
            System.out.println(e.getCause().toString());
            assertTrue(LogUTUtil.checkError("bean generation failed.",
                                            e.getCause()));
        } 
    }
    
    /**
     * �������������s���B
     */
    @Override
    protected void doOnSetUp() throws Exception {
    }

    /**
     * ��n�����s���B
     */
    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
    }

    /**
     * XML�t�@�C���̈ʒu��Ԃ��B
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] {
            "jp/terasoluna/fw/web/thin"
            + "/AbstractControlFilterTest.xml"};
    }

}
