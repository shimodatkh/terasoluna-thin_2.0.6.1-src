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
 * {@link jp.terasoluna.fw.web.thin.AuthenticationControlFilter} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���O�I���ς݂��ǂ����̃`�F�b�N���s���B<br>
 * ���O�I�����K�v�ȃp�X�ւ̃A�N�Z�X���������ꍇ�́A 
 * ���[�U�����O�I���ς݂��ǂ����𔻕ʂ��A
 * ���O�I���ς݂ł͂Ȃ������ꍇ�A{@link UnauthenticatedException}���X���[����B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 */
public class AuthenticationControlFilterTest extends TestCase {



    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AuthenticationControlFilterTest.class);
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O
     */
    public AuthenticationControlFilterTest(String name) {
        super(name);
    }
    
    /**
     * �������������s���B
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // static�t�B�[���h�̏��������s���B
        UTUtil.setPrivateField(AuthenticationControlFilter.class, 
                               "controller", 
                               null);   
    }

    /**
     * ��n�����s���B
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        UTUtil.setPrivateField(AuthenticationControlFilter.class, 
                               "controller",
                               null);   
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
     *         (���) getController():AuthenticationControlFilter_AuthenticationControllerStub01�C���X�^���X<br>
     *         (���) controller:AuthenticationControlFilter_AuthenticationControllerStub01�C���X�^���X<br>
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
        AuthenticationController existingController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               existingController);
        // �t�B���^�̗p��
        AuthenticationControlFilter_AuthenticationControlFilterStub01 filter
            = new AuthenticationControlFilter_AuthenticationControlFilterStub01();
        filter.setConfig(existingConfig);

        // �V�����ݒ肳���R���t�B�O
        MockFilterConfig newConfig = new MockFilterConfig();                              
        // �V�����ݒ肳���R���g���[��
        AuthenticationControlFilter_AuthenticationControllerStub01 newController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        filter.newController = newController;

        // �e�X�g���{
        filter.init(newConfig);

        // ����
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertFalse(filter.isCalled);
        assertSame(existingController, 
                   UTUtil.getPrivateField(AuthenticationControlFilter.class,
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
     *         (���) getController():AuthenticationControlFilter_AuthenticationControllerStub01�C���X�^���X<br>
     *         (���) controller:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:�����ŗ^����FilterConfig�Ɠ���̃C���X�^���X<br>
     *         (��ԕω�) getController():�Ăяo�����<br>
     *         (��ԕω�) controller:getController()���瓾����C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * controller��null�̏ꍇ�A�����ŗ^����config���ݒ肳��A
     * getController���Ăяo����A�t�B�[���h�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �O����

        // �V�����ݒ肳���R���t�B�O
        MockFilterConfig newConfig = new MockFilterConfig();
        
        // �t�B���^�̗p��
        AuthenticationControlFilter_AuthenticationControlFilterStub01 filter
            = new AuthenticationControlFilter_AuthenticationControlFilterStub01();
                               
        // �V�����ݒ肳���R���g���[��
        AuthenticationController newController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        filter.newController = newController;

        // �e�X�g���{
        filter.init(newConfig);

        // ����
        assertSame(newConfig, UTUtil.getPrivateField(filter, "config"));
        assertTrue(filter.isCalled);
        assertSame(newController, 
                   UTUtil.getPrivateField(AuthenticationControlFilter.class,
                                          "controller"));
        
    }

    /**
     * testGetAuthenticationController01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) controller:AuthenticationControlFilter_AuthenticationControllerStub01�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) AuthenticationController:�O������Őݒ肵���C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAuthenticationController01() throws Exception {
        // �O����
        AuthenticationController existingController
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class, 
                               "controller", 
                               existingController);

        // �e�X�g���{
        AuthenticationController actual
            = AuthenticationControlFilter.getAuthenticationController();

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
     *         (���) AUTHENTICATION_THRU_KEY:"aaaaa"<br>
     *         (���) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub01<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) AUTHENTICATION_THRU_KEY:�O������Ɠ����l<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * request�̑����l�ł���AUTHENTICATION_THRU_KEY��null�łȂ��ꍇ�A
     * �ʋƖ��ւ̑J�ڃ`�F�b�N���s�킸�ɏI�����邱�ƁB<br>
     * FilterChain#doFilter()���\�b�h�����s���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g�f�[�^�̐ݒ�
        AuthenticationController controller
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("AUTHENTICATION_THRU_KEY", "aaaaa");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // �e�X�g���s
        filter.doFilter(req, res, chain);

        // ����        
        assertEquals("aaaaa", req.getAttribute("AUTHENTICATION_THRU_KEY"));
        assertTrue(chain.isCalled);
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
     *         (���) AUTHENTICATION_THRU_KEY:null<br>
     *         (���) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub01<br>
     *         (���) .isCheckRequired():false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) AUTHENTICATION_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��false�̏ꍇ�A�ȍ~�̃`�F�b�N���s�킸�A
     * request�̑����lAUTHENTICATION_THRU_KEY��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g�f�[�^�̐ݒ�
        AuthenticationControlFilter_AuthenticationControllerStub01 controller
            = new AuthenticationControlFilter_AuthenticationControllerStub01();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // �e�X�g���s
        filter.doFilter(req, res, chain);

        // ����        
        assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
        assertTrue(chain.isCalled);
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
     *         (���) AUTHENTICATION_THRU_KEY:null<br>
     *         (���) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub02<br>
     *         (���) .isCheckRequired():true<br>
     *         (���) .isAuthenticated():false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) AUTHENTICATION_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:UnauthenticatedException�F<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��ture�ŁA���O�I���ς݃`�F�b�N��false�̏ꍇ�A
     * request�̑����lAUTHENTICATION_THRU_KEY��ݒ肵��
     * UnauthenticatedException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g�f�[�^�̐ݒ�
        AuthenticationControlFilter_AuthenticationControllerStub02 controller
            = new AuthenticationControlFilter_AuthenticationControllerStub02();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/test/logon.do");
        req.setContextPath("/test");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // �e�X�g���s
        try {
            filter.doFilter(req, res, chain);
            fail();
        } catch (UnauthenticatedException e) {
            // ����
            assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
            assertFalse(chain.isCalled);
        }
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) AUTHENTICATION_THRU_KEY:null<br>
     *         (���) authenticationController:AuthenticationControlFilter_AuthenticationControllerStub03<br>
     *         (���) .isCheckRequired():true<br>
     *         (���) .isAuthenticated():true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) AUTHENTICATION_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N�A���O�I���ς݃`�F�b�N��true�̏ꍇ�A
     * request�̑����lAUTHENTICATION_THRU_KEY��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g�f�[�^�̐ݒ�
        AuthenticationControlFilter_AuthenticationControllerStub03 controller
            = new AuthenticationControlFilter_AuthenticationControllerStub03();
        UTUtil.setPrivateField(AuthenticationControlFilter.class,
                               "controller",
                               controller);
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/test/logon.do");
        req.setContextPath("/test");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // �e�X�g���s
        filter.doFilter(req, res, chain);
        
        // ����
        assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
        assertTrue(chain.isCalled);
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) AUTHENTICATION_THRU_KEY:null<br>
     *         (���) authenticationController:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:NullPointerException�F<br>
     *         
     * <br>
     * authenticationController��null�̏ꍇ�A
     * NullPointerException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter05() throws Exception {
        // �O����
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g�f�[�^�̐ݒ�       
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/test/logon.do");
        req.setContextPath("/test");
        MockHttpServletResponse res = new MockHttpServletResponse();
        AuthenticationControlFilter_FilterChainStub01 chain
            = new AuthenticationControlFilter_FilterChainStub01();

        // �e�X�g���s
        try {
            filter.doFilter(req, res, chain);
            fail();
        } catch (NullPointerException e) {
            // ����
            assertEquals("true", req.getAttribute("AUTHENTICATION_THRU_KEY"));
            assertFalse(chain.isCalled);
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
     * ���͒l�F(���) AUTHENTICATION_CONTROLLER_CLASS:AuthenticationController.class<br>
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
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g���{
        Class actual = filter.getControllerClass();

        // ����
        assertEquals(AuthenticationController.class, actual);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) AUTHENTICATION_CONTROLLER_ERROR:"errors.authentication.controller"<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"errors.authentication.controller"<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrorCode01() throws Exception {
        // �O����
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g���{
        String actual = filter.getErrorCode();

        // ����
        assertEquals("errors.authentication.controller", actual);
    }

    /**
     * testGetDefaultControllerBeanId01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) DEFAULT_AUTHENTICATION_BEAN_ID:"authenticationController"<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"authenticationController"<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDefaultControllerBeanId01() throws Exception {
        // �O����
        AuthenticationControlFilter filter = new AuthenticationControlFilter();

        // �e�X�g���{
        String actual = filter.getDefaultControllerBeanId();

        // ����
        assertEquals("authenticationController", actual);
    }



}
