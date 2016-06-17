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

package jp.terasoluna.fw.web.taglib;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockServletConfig;

/**
 * {@link jp.terasoluna.fw.web.taglib.TagUtil} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �J�X�^���^�O�@�\�S�ʂɒʂ��郆�[�e�B���e�B�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.taglib.TagUtil
 */
@SuppressWarnings("unused")
public class TagUtilTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(TagUtilTest.class);
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
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public TagUtilTest(String name) {
        super(name);
    }

    /**
     * testGetScope01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) scopeName:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *         
     * <br>
     * scopeName��null�̎��A��O���X���[���邱�ƁB��lookup���\�b�h�̃e�X�g�P�[�X�œ���ł��Ȃ��p�^�[��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetScope01() throws Exception {
        // �e�X�g���{
        try {
            int result = TagUtil.getScope(null);
            fail();
        } catch (JspException e) {
           // ���� 
        }
    }

    /**
     * testLookupPageContextStringStringString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:null<br>
     *         (����) scopeName:null<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:TagUtil_BeanStub01[param1="page"]<br>
     *         
     * <br>
     * property��null�AscopeName��null�̎��ApageScope����擾����Object��ԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString01() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        Object result = TagUtil.lookup(pc, "name", null, null);
        
        // ����
        assertEquals("page", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:null<br>
     *         (����) scopeName:"page"<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:TagUtil_BeanStub01[param1="page"]<br>
     *         
     * <br>
     * property��null�AscopeName��"page"�̎��ApageScope����擾����Object��ԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString02() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        Object result = TagUtil.lookup(pc, "name", null, "page");
        
        // ����
        assertEquals("page", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) scopeName:"page"<br>
     *         (���) pageContext.attributes.get(name):null<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *         
     * <br>
     * property��null�AscopeName��"page"�̎��ApageScope��bean���i�[����ĂȂ���΁A��O���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString03() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        try {
            Object result = TagUtil.lookup(pc, "name", null, "page");
        } catch (JspException e) {
            // ����
        }
    }

    /**
     * testLookupPageContextStringStringString04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:null<br>
     *         (����) scopeName:"request"<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:TagUtil_BeanStub01[param1="request"]<br>
     *         
     * <br>
     * property��null�AscopeName��"request"�̎��ArequestScope����擾����Object��ԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString04() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        Object result = TagUtil.lookup(pc, "name", null, "request");
        
        // ����
        assertEquals("request", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) scopeName:"request"<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):null<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *         
     * <br>
     * property��null�AscopeName��"request"�̎��ArequestScope��bean���i�[����ĂȂ���΁A��O���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString05() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        try {
            Object result = TagUtil.lookup(pc, "name", null, "request");
        } catch (JspException e) {
            // ����
        }
    }

    /**
     * testLookupPageContextStringStringString06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:null<br>
     *         (����) scopeName:"session"<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:TagUtil_BeanStub01[param1="session"]<br>
     *         
     * <br>
     * property��null�AscopeName��"session"�̎��AsessionScope����擾����Object��ԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString06() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        Object result = TagUtil.lookup(pc, "name", null, "session");
        
        // ����
        assertEquals("session", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) scopeName:"session"<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):null<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *         
     * <br>
     * property��null�AscopeName��"session"�̎��AsessionScope��bean���i�[����ĂȂ���΁A��O���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString07() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        try {
            Object result = TagUtil.lookup(pc, "name", null, "session");
        } catch (JspException e) {
            // ����
        }
    }

    /**
     * testLookupPageContextStringStringString08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:null<br>
     *         (����) scopeName:"application"<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session"]<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * property��null�AscopeName��"application"�̎��AapplicationScope����擾����Object��ԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString08() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        Object result = TagUtil.lookup(pc, "name", null, "application");
        
        // ����
        assertEquals("application", ((TagUtil_BeanStub01) result).getParam1());
    }

    /**
     * testLookupPageContextStringStringString09()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) scopeName:"application"<br>
     *         (���) pageContext.attributes.get(name):TagUtil_BeanStub01[param1="page"]<br>
     *         (���) pageContext.request.getAttribute(name):TagUtil_BeanStub01[param1="request"]<br>
     *         (���) pageContext.session.getAttribute(name):TagUtil_BeanStub01[param1="session]"]<br>
     *         (���) pageContext.application.getAttribute(name):null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *         
     * <br>
     * property��null�AscopeName��"application"�̎��AapplicationScope��bean���i�[����ĂȂ���΁A��O���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString09() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean1 = new TagUtil_BeanStub01(); 
        bean1.setParam1("page");
        TagUtil_BeanStub01 bean2 = new TagUtil_BeanStub01(); 
        bean2.setParam1("request");
        TagUtil_BeanStub01 bean3 = new TagUtil_BeanStub01(); 
        bean3.setParam1("session");
        
        // bean��setAttribute
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("name", bean1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("name", bean2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("name", bean3);
        request.setSession(session);
        
        // PageContext�ɐݒ�
        UTUtil.setPrivateField(pc, "attributes", attributes);
        UTUtil.setPrivateField(pc, "request", request);
        
        // �e�X�g���{
        try {
            Object result = TagUtil.lookup(pc, "name", null, "application");
        } catch (JspException e) {
            // ����
        }
    }

    /**
     * testLookupPageContextStringStringString10()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) scopeName:"nothing"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *         
     * <br>
     * scopeName���z��O�̕�����̎��A��O���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString10() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // �e�X�g���{
        try {
            Object result = TagUtil.lookup(pc, "name", null, "nothing");
        } catch (JspException e) {
            // ����
        }
    }

    /**
     * testLookupPageContextStringStringString11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:"param1"<br>
     *         (����) scopeName:"APPLICATION"<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param1="application"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"application"<br>
     *         
     * <br>
     * bean���擾�ł��Aproperty�Ɏw�肳�ꂽ�t�B�[���h�����݂��鎞�A���̃t�B�[���h�l��ԋp���邱�ƁB�܂��AscopeName���啶���ł�����I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString11() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        Object result = TagUtil.lookup(pc, "name", "param1", "APPLICATION");
        
        // ����
        assertEquals("application", result);
    }

    /**
     * testLookupPageContextStringStringString12()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:"param2"<br>
     *         (����) scopeName:"application"<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[getParam2��Exception����]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException�F<br>
     *                    ���b�v������O�FInvocatinTargetException<br>
     *         
     * <br>
     * bean���擾�ł��Aproperty�Ɏw�肳�ꂽ�t�B�[���h�l�擾���ɗ�O�������������AJspException��InvocatinTargetException�����b�v���ăX���[���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString12() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        try {
            Object result = TagUtil.lookup(pc, "name", "param2", "application");
        } catch (JspException e) {
            // ����
            assertEquals(InvocationTargetException.class.getName(), e
                    .getRootCause().getClass().getName());
        }
    }
    
    /**
     * testLookupPageContextStringStringString13()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) name:"name"<br>
     *         (����) property:"param3"<br>
     *         (����) scopeName:"application"<br>
     *         (���) pageContext.application.getAttribute(name):TagUtil_BeanStub01[param3���݂��Ȃ�]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException�F<br>
     *                    ���b�v������O�FNoSuchMethodException<br>
     *         
     * <br>
     * bean���擾�ł��Aproperty�Ɏw�肳�ꂽ�t�B�[���h�����݂��Ȃ����AJspException��NoSuchMethodException�����b�v���ăX���[���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookupPageContextStringStringString13() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);
        
        // bean����
        TagUtil_BeanStub01 bean4 = new TagUtil_BeanStub01(); 
        bean4.setParam1("application");
        
        // bean��setAttribute
        MockServletContext application = new MockServletContext();
        application.setAttribute("name", bean4);
        
        // PageContext�ɐݒ�
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(application);
        UTUtil.setPrivateField(pc, "config", config);
        
        // �e�X�g���{
        try {
            Object result = TagUtil.lookup(pc, "name", "param4", "application");
        } catch (JspException e) {
            // ����
            assertEquals(NoSuchMethodException.class.getName(), e
                    .getRootCause().getClass().getName());
        }
    }

    /**
     * testWrite01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) text:"text"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �o�͓��e:"text"<br>
     *         
     * <br>
     * �������o�͂���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWrite01() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // �e�X�g���{
        TagUtil.write(pc, "text");

        // ����
        assertEquals("text", pc.getOut().toString());
    }

    /**
     * testWrite02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:writer.print(text)���s����IOException����������<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException�F<br>
     *                    ���b�v������O�FIOException<br>
     *         
     * <br>
     * writer��IOException�������������AJspException�Ń��b�v���ăX���[���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWrite02() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // IOException����������JspWriter�𐶐�
        Exception_JspWriterImpl writer = new Exception_JspWriterImpl();
        writer.setTrue();
        UTUtil.setPrivateField(pc, "jspWriter", writer);

        // �e�X�g���{
        try {
            TagUtil.write(pc, "text");
        } catch (JspException e) {
            // ����
            assertEquals(IOException.class.getName(), e.getRootCause()
                    .getClass().getName());
        }
    }

    /**
     * testWriteln01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) text:"text(���s����)"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �o�͓��e:"text(���s����)"<br>
     *         
     * <br>
     * �������o�͂���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteln01() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // �e�X�g���{
        TagUtil.writeln(pc, "text");

        // ����
        String lineSeparator = System.getProperty("line.separator");
        assertEquals("text" + lineSeparator, pc.getOut().toString());
    }

    /**
     * testWriteln02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:writer.print(text)���s����IOException����������<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException�F<br>
     *                    ���b�v������O�FIOException<br>
     *         
     * <br>
     * writer��IOException�������������AJspException�Ń��b�v���ăX���[���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testWriteln02() throws Exception {
        // �O����
        // TagUTUtil#getPageContext()���g�p���邽�ߓK����tag�𗘗p����
        TagSupport tag = TagUTUtil.create(TagSupport.class);
        PageContext pc = TagUTUtil.getPageContext(tag);

        // IOException����������JspWriter�𐶐�
        Exception_JspWriterImpl writer = new Exception_JspWriterImpl();
        writer.setTrue();
        UTUtil.setPrivateField(pc, "jspWriter", writer);
        
        // �e�X�g���{
        try {
            TagUtil.writeln(pc, "text");
        } catch (JspException e) {
            // ����
            assertEquals(IOException.class.getName(), e.getRootCause()
                    .getClass().getName());
        }
    }

    /**
     * testFilter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * ����value��null�̎��Anull��ԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFilter01() throws Exception {
        // �e�X�g���{
        String result = TagUtil.filter(null);
        // ����
        assertNull(result);
    }

    /**
     * testFilter02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""�i�󕶎��j<br>
     *         
     * <br>
     * ����value���󕶎��̎��A�󕶎���ԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFilter02() throws Exception {
        // �e�X�g���{
        String result = TagUtil.filter("");
        // ����
        assertEquals("", result);
    }

    /**
     * testFilter03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:��<��>��&��"��'<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:��&lt;��&gt;��&amp;��&quot;��&#39;<br>
     *         
     * <br>
     * ����value�Ƀt�B���^�[�Ώە����񂪂���ꍇ�A�t�B���^�[���ĕԋp���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testFilter03() throws Exception {
        // �e�X�g���{
        String result = TagUtil.filter("��<��>��&��\"��'");
        // ����
        assertEquals("��&lt;��&gt;��&amp;��&quot;��&#39;", result);
    }

}
