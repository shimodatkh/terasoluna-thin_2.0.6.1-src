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
 * {@link jp.terasoluna.fw.web.thin.AuthorizationControlFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �A�N�Z�X�����̃`�F�b�N���s���B<br>
 * �w�肳�ꂽ�p�X�ւ̃A�N�Z�X�����������Ă��邩�𔻕ʂ���B �A�N�Z�X�����ᔽ�̏ꍇ�́A{@link UnauthorizedException}���X���[����
 * <p>
 *
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 */
public class AuthorizationControlFilterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AuthorizationControlFilterTest.class);
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
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                               "controller",
                               null);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public AuthorizationControlFilterTest(String name) {
        super(name);
    }

    /**
     * testInit01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:not null<br>
     *         (���) getController():AuthorizationControlFilter_AuthorizationControllerStub01�C���X�^���X<br>
     *         (���) controller:AuthorizationControlFilter_AuthorizationControllerStub01�C���X�^���X<br>
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
        AuthorizationController existingController =
            new AuthorizationControlFilter_AuthorizationControllerStub01();

        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                                "controller",
                                existingController);
        // �t�B���^�̗p��
        AuthorizationControlFilter_AuthorizationControlFilterStub01 filter
            = new AuthorizationControlFilter_AuthorizationControlFilterStub01();

        filter.setConfig(existingConfig);

        // �V�����ݒ肳���R���t�B�O
        MockFilterConfig newConfig = new MockFilterConfig();
        // �V�����ݒ肳���R���g���[��
        AuthorizationControlFilter_AuthorizationControllerStub01 newController
            = new AuthorizationControlFilter_AuthorizationControllerStub01();
        filter.newController = newController;

        // �e�X�g���{
        filter.init(newConfig);
        // ����
        assertSame(newConfig,UTUtil.getPrivateField(filter,"config"));
        assertFalse(filter.isCalled);

        assertSame(existingController,
                UTUtil.getPrivateField(AuthorizationControlFilter.class,
                        "controller"));
    }

    /**
     * testInit02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:null<br>
     *         (���) getController():AuthorizationControlFilter_AuthorizationControllerStub01�C���X�^���X<br>
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
        AuthorizationControlFilter_AuthorizationControlFilterStub01 filter
            = new AuthorizationControlFilter_AuthorizationControlFilterStub01();

        // �V�����ݒ肳���R���t�B�O
        MockFilterConfig newConfig = new MockFilterConfig();
        // �V�����ݒ肳���R���g���[��
        AuthorizationControlFilter_AuthorizationControllerStub01 newController
            = new AuthorizationControlFilter_AuthorizationControllerStub01();
        filter.newController = newController;

        // �e�X�g���{
        filter.init(newConfig);
        // ����
        assertSame(newConfig,UTUtil.getPrivateField(filter,"config"));
        assertTrue(filter.isCalled);

        assertSame(newController,
                UTUtil.getPrivateField(AuthorizationControlFilter.class,
                        "controller"));
    }

    /**
     * testGetAuthorizationController01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) controller:AuthorizationControlFilter_AuthorizationControllerStub01�C���X�^���X<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) AuthorizationController:�O������Őݒ肵���C���X�^���X�Ɠ���̃C���X�^���X<br>
     *
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAuthorizationController01() throws Exception {
        // �O����
        AuthorizationController existingController =
            new AuthorizationControlFilter_AuthorizationControllerStub01();

        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                                "controller",
                                existingController);
        // �e�X�g���{
        AuthorizationController actual =
            AuthorizationControlFilter.getAuthorizationController();

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
     *         (���) AUTHORIZATION_THRU_KEY:"aaaaa"<br>
     *         (���) authorizationController:AuthorizationControlFilter_AuthorizationControllerStub01<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) AUTHORIZATION_THRU_KEY:�O������Ɠ����l<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *
     * <br>
     * request�̑����l�ł���AUTHORIZATION_THRU_KEY��null�łȂ��ꍇ�A�ʋƖ��ւ̑J�ڃ`�F�b�N���s�킸�ɏI�����邱�ƁB<br>
     * FilterChain#doFilter()���\�b�h�����s���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        AuthorizationController existingController
            = new AuthorizationControlFilter_AuthorizationControllerStub01();

        AuthorizationControlFilter filter = new AuthorizationControlFilter();
        // �e�X�g�f�[�^�̐ݒ�
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setAttribute("AUTHORIZATION_THRU_KEY","aaaaa");

        AuthorizationControlFilter_FilterChainStub01 filterChain
            = new AuthorizationControlFilter_FilterChainStub01();

        // �e�X�g���{
        filter.doFilter(request,response,filterChain);
        // ����
        assertEquals("aaaaa",request.getAttribute("AUTHORIZATION_THRU_KEY"));
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
     *         (���) AUTHORIZATION_THRU_KEY:null<br>
     *         (���) authorizationController:AuthorizationControlFilter_AuthorizationControllerStub01<br>
     *         (���) .isCheckRequired():false<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) AUTHORIZATION_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��false�̏ꍇ�A�ȍ~�̃`�F�b�N���s�킸�Arequest�̑����lAUTHORIZATION_THRU_KEY��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
        // .isCheckRequired() = false
        AuthorizationController existingController
            = new AuthorizationControlFilter_AuthorizationControllerStub01();

        AuthorizationControlFilter filter = new AuthorizationControlFilter();
        // �e�X�g�f�[�^�̐ݒ�
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthorizationControlFilter_FilterChainStub01 filterChain
            = new AuthorizationControlFilter_FilterChainStub01();

        // �e�X�g���{
        filter.doFilter(request,response,filterChain);
        // ����
        assertEquals("true",request.getAttribute("AUTHORIZATION_THRU_KEY"));
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
     *         (���) AUTHORIZATION_THRU_KEY:null<br>
     *         (���) authorizationController:AuthorizationControlFilter_AuthorizationControllerStub02<br>
     *         (���) .isCheckRequired():true<br>
     *         (���) .isAuthorized():false<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) AUTHORIZATION_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:UnauthorizedException�F<br>
     *
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��ture�ŁA�A�N�Z�X�����`�F�b�N��false�̏ꍇ�Arequest�̑����lAUTHORIZATION_THRU_KEY��ݒ肵��UnauthorizedException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
        // .isCheckRequired() = true   isAuthorized = false
        AuthorizationController existingController
            = new AuthorizationControlFilter_AuthorizationControllerStub02();

        AuthorizationControlFilter filter = new AuthorizationControlFilter();
        // �e�X�g�f�[�^�̐ݒ�
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthorizationControlFilter_FilterChainStub01 filterChain
            = new AuthorizationControlFilter_FilterChainStub01();

        // �e�X�g���{
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (UnauthorizedException e) {
            // ����
            assertEquals("true", request.getAttribute("AUTHORIZATION_THRU_KEY"));
            assertFalse(filterChain.isCalled);
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
     *         (���) AUTHORIZATION_THRU_KEY:null<br>
     *         (���) authorizationController:AuthorizationControlFilter_AuthorizationControllerStub03<br>
     *         (���) .isCheckRequired():true<br>
     *         (���) .isAuthorized():true<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) AUTHORIZATION_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N�A�A�N�Z�X�����`�F�b�N��true�̏ꍇ�Arequest�̑����lAUTHORIZATION_THRU_KEY��ݒ肵�ď������I�����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
        // .isCheckRequired() = true   isAuthorized = true
        AuthorizationController existingController
            = new AuthorizationControlFilter_AuthorizationControllerStub03();

        AuthorizationControlFilter filter = new AuthorizationControlFilter();
        // �e�X�g�f�[�^�̐ݒ�
        UTUtil.setPrivateField(filter,
                                "controller",
                                existingController);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthorizationControlFilter_FilterChainStub01 filterChain
            = new AuthorizationControlFilter_FilterChainStub01();

        // �e�X�g���{
        filter.doFilter(request, response, filterChain);

        // ����
        assertEquals("true", request.getAttribute("AUTHORIZATION_THRU_KEY"));
        assertTrue(filterChain.isCalled);
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
     *         (���) AUTHORIZATION_THRU_KEY:null<br>
     *         (���) authorizationController:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:NullPointerException�F<br>
     *
     * <br>
     * authorizationController��null�̏ꍇ�ANullPointerException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter05() throws Exception {
        // �O����
        AuthorizationControlFilter filter = new AuthorizationControlFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthorizationControlFilter_FilterChainStub01 filterChain
            = new AuthorizationControlFilter_FilterChainStub01();

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
     * ���͒l�F(���) AUTHORIZATION_CONTROLLER_CLASS:AuthorizationController.class<br>
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
        AuthorizationControlFilter filter = new AuthorizationControlFilter();

        // �e�X�g���{
        Class actual = filter.getControllerClass();

        // ����
        assertEquals(AuthorizationController.class,actual);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) AUTHORIZATION_CONTROLLER_ERROR:"errors.authorization.controller"<br>
     *                ��static final<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"errors.authorization.controller"<br>
     *
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrorCode01() throws Exception {
        // �O����
        AuthorizationControlFilter filter = new AuthorizationControlFilter();

        // �e�X�g���{
        String actual = filter.getErrorCode();

        // ����
        assertEquals("errors.authorization.controller",actual);
    }

    /**
     * testGetDefaultControllerBeanId01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) DEFAULT_AUTHORIZATION_BEAN_ID:"authorizationController"<br>
     *                ��static final<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"authorizationController"<br>
     *
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDefaultControllerBeanId01() throws Exception {
        // �O����
        AuthorizationControlFilter filter = new AuthorizationControlFilter();

        // �e�X�g���{
        String actual = filter.getDefaultControllerBeanId();

        // ����
        assertEquals("authorizationController",actual);
    }

}
