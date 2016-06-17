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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.util.ExceptionUtil;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import junit.framework.TestCase;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * RequestProcessorEx �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �EprocessActionFormEx()���\�b�h�̊m�F�́AprocessActionForm()
 * ���\�b�h�����ɓ�����B<br>
 * 
 * @version 2004/03/18
 */
public class RequestProcessorExTest extends TestCase {

    /**
     * �R���X�g���N�^�B
     */
    public RequestProcessorExTest(String arg) {
        super(arg);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LogUTUtil.flush();
    }

    /**
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testProcess01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=HttpServletRequest�C���X�^���X<br>
     * res=not null<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * 
     * req��HttpServletRequest�̃C���X�^���X�̎��A
     * HttpServletRequestEx�Ƀ��b�v����邱�Ɓ�1<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcess01() throws Exception {

        //�e�X�g�f�[�^�ݒ�

        // process()�œn���ꂽ���N�G�X�g�I�u�W�F�N�g��ԋp����p���N���X
        // process()���ōŏ��ɌĂ΂�郁�\�b�h�AprocessPath()����
        // null��ԋp���邱�Ƃɂ��AprocessPath()�ȍ~�̏�����i�߂Ȃ��B
        RequestProcessorEx_RequestProcessorExStub01 processor =
            new RequestProcessorEx_RequestProcessorExStub01();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        //�e�X�g���s
        processor.process(req, res);

        //�e�X�g���ʊm�F
        assertSame(processor.getRequestEx(), req);
    }

    /**
     * testProcess02�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * 
     * �e�N���X��process()��IOException���X���[����Ƃ��A
     * ���̂܂܃X���[����邱��<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcess02() throws Exception {
        // process()�œn���ꂽ���N�G�X�g�I�u�W�F�N�g��ԋp����p���N���X
        // process()���ōŏ��ɌĂ΂�郁�\�b�h�AprocessPath()����
        // IOException���X���[���邱�Ƃɂ��AprocessPath()�ȍ~�̏�����
        // �i�߂Ȃ��B
        RequestProcessorEx_RequestProcessorExStub02 processor =
            new RequestProcessorEx_RequestProcessorExStub02();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        try {
            //�e�X�g���s
            processor.process(req, res);
            fail();
        } catch (IOException e) {
            // ���O�o�͂̊m�F�iIOException�j
            assertTrue(LogUTUtil.checkError(ExceptionUtil.getStackTrace(e)));
        }
    }

    /**
     * testProcess03�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * 
     * �e�N���X��process()��ServletException���X���[����Ƃ��A
     * ���̂܂܃X���[����邱��<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcess03() throws Exception {
        // process()�œn���ꂽ���N�G�X�g�I�u�W�F�N�g��ԋp����p���N���X
        // process()���ŌĂ΂�郁�\�b�h�AprocessActionPerform()��
        // ServletException���X���[����B
        // processActionForm()�ɂ��ǂ蒅���܂ł̊ԁA
        // �������I�����Ă��܂����\�b�h�͑S�ăI�[�o���C�h����B
        RequestProcessorEx_RequestProcessorExStub03 processor =
            new RequestProcessorEx_RequestProcessorExStub03();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        //�e�X�g���s
        try {
            processor.process(req, res);
            fail();
        } catch (ServletException e) {
            // ���O�o�͂̊m�F�ijavax.servlet.ServletException�j
            assertTrue(LogUTUtil.checkError(ExceptionUtil.getStackTrace(e)));
        }
    }

    /**
     * testProcess04�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * 
     * �e�N���X��process()��IOException�AServletException�ȊO��
     * ��O���X���[����Ƃ��AServletException�Ƀ��b�v����A
     * �X���[����邱��<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcess04() throws Exception {
        //�e�X�g�f�[�^�ݒ�

        // process()�œn���ꂽ���N�G�X�g�I�u�W�F�N�g��ԋp����p���N���X
        // process()���ŌĂ΂�郁�\�b�h�AprocessPath()��
        // RuntimeException���X���[����B
        RequestProcessorEx_RequestProcessorExStub04 processor =
            new RequestProcessorEx_RequestProcessorExStub04();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();

        //�e�X�g���s
        try {
            processor.process(req, res);
            fail();
        } catch (ServletException e) {
            //�e�X�g���ʊm�F
            // ServletException���X���[���ꂽ�ꍇ�̂݃e�X�g�����B
            assertTrue(LogUTUtil.checkError(ExceptionUtil.getStackTrace(e.getRootCause())));
        }
    }

    /**
     * testProcessPopulate01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=�p�����[�^�FcompanyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE����)=null<br>
     * name="formName"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * SKIP_POPULATE=null<br>
     * form(�t�B�[���h�l)=companyId="companyId"<br>
     * 
     * ���N�G�X�g������SKIP_POPULATE���o�^����Ă��Ȃ��ꍇ�A
     * ���N�G�X�g�p�����[�^���t�H�[���ɔ��f����A
     * ���N�G�X�g������SKIP_POPULATE�L�[��null�̂܂܂ł���B<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcessPopulate01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();

        // �[�����N�G�X�g�E���X�|���X
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, null);

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //�e�X�g���ʊm�F
        assertEquals("companyId", form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=�p�����[�^�FcompanyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE����)=""<br>
     * name="formName"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * SKIP_POPULATE=""<br>
     * form(�t�B�[���h�l)=companyId="companyId"<br>
     * 
     * ���N�G�X�g������SKIP_POPULATE�ŋ󕶎����o�^����A
     * �t�H�[�������󕶎��ł͂Ȃ��Ƃ��A���N�G�X�g�p�����[�^��
     * �t�H�[���ɔ��f����A���N�G�X�g������SKIP_POPULATE
     * �L�[�͍폜�����B<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcessPopulate02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();

        // �[�����N�G�X�g�E���X�|���X
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, "");

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //�e�X�g���ʊm�F
        assertEquals("companyId", form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=�p�����[�^�FcompanyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE����)="formName"<br>
     * name="formName"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * SKIP_POPULATE=null<br>
     * form(�t�B�[���h�l)=companyId=null<br>
     * 
     * ���N�G�X�g������SKIP_POPULATE�œo�^���ꂽ�t�H�[�����ƁA
     * �A�N�V�����}�b�s���O�̃t�H�[��������v���Ă���Ƃ��A
     * ���N�G�X�g�p�����[�^�͔��f���ꂸ�A���N�G�X�g������
     * SKIP_POPULATE�͍폜����Ȃ��B<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcessPopulate03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();

        // �[�����N�G�X�g�E���X�|���X
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, "formName");

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //�e�X�g���ʊm�F
        assertNull(form.getCompanyId());
        assertEquals(
            "formName",
            req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=�p�����[�^�FcompanyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE����)="anotherName"<br>
     * name="formName"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * SKIP_POPULATE="anotherName"<br>
     * form(�t�B�[���h�l)=companyId="companyId"<br>
     * 
     * ���N�G�X�g������SKIP_POPULATE�œo�^���ꂽ�t�H�[�����ƁA
     * �A�N�V�����}�b�s���O�̃t�H�[��������v���Ă��Ȃ��Ƃ��A
     * ���N�G�X�g�p�����[�^�͔��f����A���N�G�X�g������
     * SKIP_POPULATE�͍폜�����B<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcessPopulate04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();

        // �[�����N�G�X�g�E���X�|���X
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, "anotherName");

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");

        processor.processPopulate(req, res, form, mapping);

        //�e�X�g���ʊm�F
        assertEquals("companyId", form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessPopulate05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=�p�����[�^�FcompanyId="companyId"<br>
     * res=not null<br>
     * form=not null<br>
     * mapping=not null<br>
     * request(SKIP_POPULATE����)=null<br>
     * name="formName"<br>
     * cancelPopulate=true<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * SKIP_POPULATE=null<br>
     * form(�t�B�[���h�l)=companyId=null<br>
     * 
     * ActionMappingEx�̑���cancelPopulate��true�̂Ƃ��A
     * ���N�G�X�g�p�����[�^�͔��f���ꂸ�A
     * ���N�G�X�g������SKIP_POPULATE�L�[��null�̂܂܂ł���B<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testProcessPopulate05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();

        // �[�����N�G�X�g�E���X�|���X
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("companyId", "companyId");
        req.setAttribute(RequestProcessorEx.SKIP_POPULATE, null);

        HttpServletResponse res = new MockHttpServletResponse();
        RequestProcessorEx_ActionFormStub01 form =
            new RequestProcessorEx_ActionFormStub01();

        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("formName");
        mapping.setCancelPopulate(true);

        processor.processPopulate(req, res, form, mapping);

        //�e�X�g���ʊm�F
        assertNull(form.getCompanyId());
        assertNull(req.getAttribute(RequestProcessorEx.SKIP_POPULATE));
    }

    /**
     * testProcessActionForm01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * mapping=ActionMapping�C���X�^���X<br>
     * scope="session"<br>
     * name="_formName"<br>
     * 
     * ���Ғl
     * �߂�l:form=ActionForm���ԋp�����<br>
     * session="_anothorForm"���폜����Ȃ�����<br>
     * 
     * �����̃A�N�V�����}�b�s���O��ActionMappingEx
     * �̃C���X�^���X�ł͂Ȃ����A
     * super.processActionForm()���Ă΂�Ă��邱�ƁB<br>
     */
    public void testProcessActionForm01() throws Exception {

        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        
        //�e�X�g�f�[�^�ݒ�
        // �e�X�g���s�ɂ́AModuleConfig��ݒ肷��K�v������B
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // �[�����N�G�X�g�E���X�|���X
        session.setAttribute("_anotherForm", new DynaActionForm());

        // �[���Z�b�V���������N�G�X�g�ɓo�^
        req.setSession(session);

        // Struts�̃A�N�V�����}�b�s���O��p��
        ActionMapping normalMapping = new ActionMapping();
        normalMapping.setScope("session");
        normalMapping.setName("_formName");

        //�e�X�g���s
        processor.processActionForm(req, res, normalMapping);

        //�e�X�g���ʊm�F
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * mapping=ActionMappingEx�C���X�^���X<br>
     * scope=null<br>
     * name=null<br>
     * 
     * ���Ғl
     * �߂�l:form=ActionForm���ԋp�����<br>
     * session="_anothorForm"���폜����Ȃ�����<br>
     * 
     * �X�R�[�v������null�̎��Asuper.processActionForm()��
     * �Ă΂�Ă��邱�ƁB<br>
     */
    public void testProcessActionForm02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // �[�����N�G�X�g�E���X�|���X
        session.setAttribute("_anotherForm", new DynaActionForm());

        // �[���Z�b�V���������N�G�X�g�ɓo�^
        req.setSession(session);

        mapping.setScope(null);
        mapping.setName(null);

        // ActionFormUtil.clearForm()�ɕK�v�Ȑݒ�(���ݒ�)
        //mapping.setClearForm("_formName");

        //�e�X�g���s
        processor.processActionForm(req, res, mapping);

        //�e�X�g���ʊm�F
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="��(request)"<br>
     * name=null<br>
     * 
     * ���Ғl
     * �߂�l:form=ActionForm���ԋp�����<br>
     * session="_anothorForm"���폜����Ȃ�����<br>
     * 
     * �X�R�[�v������"session"�ȊO�̕�����̎��A
     * super.processActionForm()���Ă΂�Ă��邱�ƁB<br>
     */
    public void testProcessActionForm03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();
        
        // �[�����N�G�X�g�E���X�|���X
        session.setAttribute("_anotherForm", new DynaActionForm());

        // �[���Z�b�V���������N�G�X�g�ɓo�^
        req.setSession(session);

        mapping.setScope("request");
        mapping.setName(null);

        // ActionFormUtil.clearForm()�ɕK�v�Ȑݒ�i���ݒ�j
        // mapping.setClearForm("_formName");

        //�e�X�g���s
        processor.processActionForm(req, res, mapping);

        //�e�X�g���ʊm�F
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name=null<br>
     * 
     * ���Ғl
     * �߂�l:form=ActionForm���ԋp�����<br>
     * session="_anothorForm"���폜����Ȃ�����<br>
     * 
     * �t�H�[������null�̎��Asuper.processActionForm()
     * ���Ă΂�Ă��邱�ƁB<br>
     */
    public void testProcessActionForm04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        session.setAttribute("_anotherForm", new DynaActionForm());

        // �[���Z�b�V���������N�G�X�g�ɓo�^
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName(null);

        // ActionFormUtil.clearForm()�ɕK�v�Ȑݒ�i���ݒ�j
        // mapping.setClearForm("_formName");

        //�e�X�g���s
        processor.processActionForm(req, res, mapping);

        //�e�X�g���ʊm�F
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name="��(formName)"<br>
     * 
     * ���Ғl
     * �߂�l:form=ActionForm���ԋp�����<br>
     * session="_anothorForm"���폜����Ȃ�����<br>
     * 
     * �t�H�[�����̐擪���A���_�[�X�R�A�ł͂Ȃ����A
     * super.processActionForm()���Ă΂�Ă��邱�ƁB<br>
     */
    public void testProcessActionForm05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // �e�X�g���s�ɂ́AModuleConfig��ݒ肷��K�v������B
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // �[�����N�G�X�g�E���X�|���X
        session.setAttribute("_anotherForm", new DynaActionForm());

        // �[���Z�b�V���������N�G�X�g�ɓo�^
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName("formName");

        // ActionFormUtil.clearForm()�ɕK�v�Ȑݒ�i���ݒ�j
        // mapping.setClearForm("_formName");

        //�e�X�g���s
        processor.processActionForm(req, res, mapping);

        //�e�X�g���ʊm�F
        assertNotNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm06�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name="_��(_formName)"<br>
     * clearForm=true<br>
     * 
     * ���Ғl
     * �߂�l:form=ActionForm���ԋp�����<br>
     * session=_anothorForm���폜����邱��<br>
     * 
     * �t�H�[�����̐擪���A���_�[�X�R�A�ł��鎞�A
     * processActionFormEx()���Ă΂�A"_"�t���̃t�H�[�����S�č폜
     * ����Ă��邱�ƁB<br>
     */
    public void testProcessActionForm06() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // �e�X�g���s�ɂ́AModuleConfig��ݒ肷��K�v������B
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // �[�����N�G�X�g�E���X�|���X
        session.setAttribute("_formName", new DynaActionForm());
        session.setAttribute("_anotherForm", new DynaActionForm());

        // �[���Z�b�V���������N�G�X�g�ɓo�^
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName("_formName");
        mapping.setClearForm(true);

        // ActionFormUtil.clearForm()�ɕK�v�Ȑݒ�
        mapping.setClearForm(true);

        //�e�X�g���s
        processor.processActionForm(req, res, mapping);

        //�e�X�g���ʊm�F
        assertNull(session.getAttribute("_formName"));
        assertNull(session.getAttribute("_anotherForm"));
    }

    /**
     * testProcessActionForm07�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * req=not null<br>
     * res=not null<br>
     * mapping=not null<br>
     * scope="session"<br>
     * name="_��(_formName)"<br>
     * clearForm=false<br>
     * 
     * ���Ғl
     * �߂�l:form=ActionForm���ԋp�����<br>
     * session=_anothorForm���폜����Ă��邱��<br>
     * 
     * �t�H�[�����̐擪���A���_�[�X�R�A�ł��鎞�A
     * processActionFormEx()���Ă΂�A"_"�t���̃t�H�[���ŁA
     * ���݃A�N�V�����}�b�s���O�Ŏw�肳��Ă���t�H�[���ȊO�̂��̂��폜
     * ����Ă��邱�ƁB<br>
     */
    public void testProcessActionForm07() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        RequestProcessorEx processor = new RequestProcessorEx();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        ActionMappingEx mapping = new ActionMappingEx();

        // �e�X�g���s�ɂ́AModuleConfig��ݒ肷��K�v������B
        ModuleConfig config = new ModuleConfigImpl("");
        processor.init(null, config);

        // �[�����N�G�X�g�E���X�|���X
        session.setAttribute("_formName", new DynaActionForm());
        session.setAttribute("_anotherForm", new DynaActionForm());

        // �[���Z�b�V���������N�G�X�g�ɓo�^
        req.setSession(session);

        mapping.setScope("session");
        mapping.setName("_formName");
        mapping.setClearForm(true);

        // ActionFormUtil.clearForm()�ɕK�v�Ȑݒ�
        mapping.setClearForm(false);

        //�e�X�g���s
        processor.processActionForm(req, res, mapping);

        //�e�X�g���ʊm�F
        //�A�N�V�����}�b�s���O�ɓo�^����Ă���t�H�[���͍폜����Ȃ����ƁB
        assertNotNull(session.getAttribute("_formName"));
        assertNull(session.getAttribute("_anotherForm"));
    }
}
