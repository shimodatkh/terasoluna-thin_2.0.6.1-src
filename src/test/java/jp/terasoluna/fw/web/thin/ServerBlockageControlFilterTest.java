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

import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.ServerBlockageControlFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �T�[�o�Ǐ�Ԃ��ǂ����̃`�F�b�N���s���B<br>
 * �T�[�o�Ǐ�Ԃ������ꍇ�́A{@link ServerBlockageException}���X���[����B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 */
public class ServerBlockageControlFilterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ServerBlockageControlFilterTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        UTUtil.setPrivateField(ServerBlockageControlFilter.class, 
                               "controller",
                               null);  
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ServerBlockageControlFilterTest(String name) {
        super(name);
    }

    /**
     * testInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:not null<br>
     *         (���) getController():ServerBlockageControlFilter_ServerBlockageControllerStub01�C���X�^���X<br>
     *         (���) controller:ServerBlockageControlFilter_ServerBlockageControllerStub01�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) config:�����ŗ^����FilterConfig�Ɠ���̃C���X�^���X<br>
     *         (��ԕω�) getController():�Ăяo����Ȃ�<br>
     *         (��ԕω�) controller:�O������Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * controller��null�Ŗ����ꍇ�Aconroll�����ŗ^����config���ݒ肳��AgetController���Ăяo����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �O����
        // ���ɐݒ肵�Ă���R���t�B�O
        MockFilterConfig existingConfig = new MockFilterConfig();
        // ���ɐݒ肵�Ă���R���g���[��
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        
        UTUtil.setPrivateField(ServerBlockageControlFilter.class,
                                "controller",
                                existingController);
        // �t�B���^�̗p��
        ServerBlockageControlFilter_ServerBlockageControlFilterStub01 filter
            = new ServerBlockageControlFilter_ServerBlockageControlFilterStub01();
        
        filter.setConfig(existingConfig);
        
        // �V�����ݒ肳���R���t�B�O
        MockFilterConfig newConfig = new MockFilterConfig();
        // �V�����ݒ肳���R���g���[��
        ServerBlockageControlFilter_ServerBlockageControllerStub01 newController 
            = new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        filter.newController = newController;
        
        // �e�X�g���{
        filter.init(newConfig);
        // ����
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertFalse(filter.isCalled);
        
        assertSame(existingController,
                UTUtil.getPrivateField(ServerBlockageControlFilter.class,
                        "controller"));
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:null<br>
     *         (���) getController():ServerBlockageControlFilter_ServerBlockageControllerStub01�C���X�^���X<br>
     *         (���) controller:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) config:�����ŗ^����FilterConfig�Ɠ���̃C���X�^���X<br>
     *         (��ԕω�) getController():�Ăяo�����<br>
     *         (��ԕω�) controller:getController()���瓾����C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * controller��null�̏ꍇ�A�����ŗ^����config���ݒ肳��AgetController���Ăяo����A�t�B�[���h�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �O����
        // �t�B���^�̗p��
        ServerBlockageControlFilter_ServerBlockageControlFilterStub01 filter
            = new ServerBlockageControlFilter_ServerBlockageControlFilterStub01();
        
        // �V�����ݒ肳���R���t�B�O
        MockFilterConfig newConfig = new MockFilterConfig();
        // �V�����ݒ肳���R���g���[��
        ServerBlockageControlFilter_ServerBlockageControllerStub01 newController 
            = new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        filter.newController = newController;
        
        // �e�X�g���{
        filter.init(newConfig);
        // ����
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertTrue(filter.isCalled);
        
        assertSame(newController,
                UTUtil.getPrivateField(ServerBlockageControlFilter.class,
                        "controller"));
    }

    /**
     * testGetServerBlockageController01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) controller:ServerBlockageControlFilter_ServerBlockageControllerStub01�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ServerBlockageController:�O������Őݒ肵���C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetServerBlockageController01() throws Exception {
        // �O����
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
        
        UTUtil.setPrivateField(ServerBlockageControlFilter.class,
                                "controller",
                                existingController);
        // �e�X�g���{
        ServerBlockageController actual =
            ServerBlockageControlFilter.getServerBlockageController();
        
        // ����
        assertSame(existingController, actual);
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) SERVER_BLOCKAGE_THRU_KEY:"aaaaa"<br>
     *         (���) serverBlockageController:ServerBlockageControlFilter_ServerBlockageControllerStub01<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) SERVER_BLOCKAGE_THRU_KEY:�O������Ɠ����l<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * request�̑����l�ł���SERVER_BLOCKAGE_THRU_KEY��null�łȂ��ꍇ�A�ʋƖ��ւ̑J�ڃ`�F�b�N���s�킸�ɏI�����邱�ƁB<br>
     * FilterChain#doFilter()���\�b�h�����s���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
 
        // �t�B���^�̗p��
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        // �e�X�g�f�[�^�̐ݒ�
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "aaaaa");
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);    
        // ����
        assertEquals("aaaaa", request.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) SERVER_BLOCKAGE_THRU_KEY:null<br>
     *         (���) serverBlockageController:ServerBlockageControlFilter_ServerBlockageControllerStub01<br>
     *         (���) .isBlockaded():false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) SERVER_BLOCKAGE_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��false�̏ꍇ�A�ȍ~�̃`�F�b�N���s�킸�Arequest�̑����lSERVER_BLOCKAGE_THRU_KEYFilter��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub01();
 
        // �t�B���^�̗p��
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        // �e�X�g�f�[�^�̐ݒ�
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);    
        // ����
        assertEquals("true", request.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) SERVER_BLOCKAGE_THRU_KEY:null<br>
     *         (���) serverBlockageController:ServerBlockageControlFilter_ServerBlockageControllerStub02<br>
     *         (���) .isBlockaded():true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) SERVER_BLOCKAGE_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:ServerBlockageException�F<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��ture�ŁA�T�[�o�[�ǃ`�F�b�N��true�̏ꍇ�Arequest�̑����lSERVER_BLOCKAGE_THRU_KEY��ݒ肵��ServerBlockageException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
        ServerBlockageController existingController =
            new ServerBlockageControlFilter_ServerBlockageControllerStub02();
 
        // �t�B���^�̗p��
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        // �e�X�g�f�[�^�̐ݒ�
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // �e�X�g���{
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (ServerBlockageException ex) {
            // ����
            assertEquals("true", request.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
            assertFalse(filterChain.isCalled);            
        }

    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) SERVER_BLOCKAGE_THRU_KEY:null<br>
     *         (���) serverBlockageController:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:NullPointerException�F<br>
     *         
     * <br>
     * serverBlockageController��null�̏ꍇ�ANullPointerException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
        // �t�B���^�̗p��
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        ServerBlockageControlFilter_FilterChainStub01 filterChain 
            = new ServerBlockageControlFilter_FilterChainStub01();
  
        // �e�X�g���{
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (NullPointerException e) {
            // ����
            // ���s���Ȃ�
            assertFalse(filterChain.isCalled);
        }           
    }

    /**
     * testGetControllerClass01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) SERVER_BLOCKAGE_CONTROLLER_CLASS:ServerBlockageController.class<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Class:�O������Őݒ肵���C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetControllerClass01() throws Exception {
        // �O����
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        //  �e�X�g���{
        Class actual = filter.getControllerClass();
        
        //  ����
        assertEquals(ServerBlockageController.class, actual);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) SERVER_BLOCKAGE_CONTROLLER_ERROR:"errors.server.blockage.controller"<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"errors.server.blockage.controller"<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrorCode01() throws Exception {
        // �O����
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        //  �e�X�g���{
        String actual = filter.getErrorCode();
        
        //  ����
        assertEquals("errors.server.blockage.controller", actual);
    }

    /**
     * testGetDefaultControllerBeanId01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) DEFAULT_SERVER_BLOCKAGE_BEAN_ID:"serverBlocakgeController"<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"serverBlocakgeController"<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDefaultControllerBeanId01() throws Exception {
        // �O����
        ServerBlockageControlFilter filter = new ServerBlockageControlFilter();
        
        //  �e�X�g���{
        String actual = filter.getDefaultControllerBeanId();
        
        //  ����
        assertEquals("serverBlockageController", actual);
    }

}
