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

package jp.terasoluna.fw.web.struts.actions;

import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicMessage;
import jp.terasoluna.fw.service.thin.BLogicMessages;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

import com.mockrunner.mock.web.MockActionMapping;
import com.mockrunner.mock.web.MockServletContext;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N�N�����ۃN���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 */
public class AbstractBLogicActionTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractBLogicActionTest.class);
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
        LogUTUtil.flush();
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
    public AbstractBLogicActionTest(String name) {
        super(name);
    }

    /**
     * testSetSaveMessageScope01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) saveMessageScope:"request"<br>
     *         (���) saveMessageScope:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) saveMessageScope:"request"<br>
     *
     * <br>
     * �����Ɏw�肵���l��AbstractBLogicAction��saveMessageScope�ɐ���Ɋi�[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSaveMessageScope01() throws Exception {
        // �O����
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        UTUtil.setPrivateField(action, "saveMessageScope", null);

        // �e�X�g���{
        action.setSaveMessageScope("request");

        // ����
        assertEquals("request", UTUtil.getPrivateField(action, "saveMessageScope"));
    }

    /**
     * testDoExecute02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:Mapping�F<br>
     *                ClassCastException����<br>
     *         (����) form:FormEx<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "error.blogic.mapping"<br>
     *                    ���b�v������O�F<br>
     *                    ClassCastException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal ActionMapping."<br>
     *
     * <br>
     * ����mapping��ActionMappingEx�̃C���X�^���X�łȂ������ꍇ�A
     * ��O���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute02() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();

        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        try {
            // �e�X�g���{
            action.doExecute(mapping, form, req, res);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("Illegal ActionMapping."));
            assertEquals("errors.blogic.mapping", e.getErrorCode());
        }
    }

    /**
     * testDoExecute03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:MappingEx<br>
     *         (����) form:ActionForm<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getBLogic<br>
     *                Params():null<br>
     *         (���) doExecuteBLogic()<br>
     *                ���^�[���lresult:BLogicResult<br>
     *                ([resultString=<br>
     *                "resultString"])<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:ActionForward.<br>
     *                  getName()�F<br>
     *                  "resultString"<br>
     *         (��ԕω�) preDoExecute<br>
     *                    BLogic():�Ăяo���m�F�F<br>
     *                    preDoExecuteBLogic(request, response, null)<br>
     *         (��ԕω�) doExecute<br>
     *                    BLogic():�Ăяo���m�F�F<br>
     *                    doExecuteBLogic(null)<br>
     *         (��ԕω�) postDoExecute<br>
     *                    Blogic():�Ăяo���m�F�F<br>
     *                    postDoExecuteBLogic(request, response, params, null)<br>
     *         (��ԕω�) evaluate<br>
     *                    BLogicResult():�Ăяo���m�F<br>
     *                    evaluateBLogicResult(result, request, response, mappingEx)<br>
     *
     * <br>
     * AbstractBLogicAction��getBLogicParams()��null��Ԃ����ꍇ�A<br>
     * ���O���o�͂�����A<br>
     * preDoExecuteBLogic()�AdoExecuteBLogic()�ApostDoExecuteBLogic()���Ăяo���B
     * <br>
     * doExecuteBLogic()�̃��^�[���l��null�łȂ������ꍇ�AevaluateBLogicResult()
     * ���Ăяo������ActionForward�I�u�W�F�N�g��Ԃ����Ƃ��m�F����B<br>
     * ActionForm��FormEx�łȂ��Ă�������s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute03() throws Exception {
        // �O����
        ActionMappingEx mapping = new ActionMappingEx();

        // "resultString"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forw = new ActionForward();
        forw.setName("resultString");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forw);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionEx
        ActionForm form = 
        	new ActionForm(){
                private static final long serialVersionUID = 1L;
            };

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        // BLogicParams : null
        Object params = null;
        UTUtil.setPrivateField(action, "params", params);

        // BLogicResult : resultString="resultString"
        BLogicResult result = new BLogicResult();
        result.setResultString("resultString");
        UTUtil.setPrivateField(action, "result", result);

        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        // forward.getName()
        assertEquals("resultString", forward.getName());

        // �Ăяo���m�F:preDoExecuteBLogic()
        assertTrue(action.isPreDoExecuteBLogic);
        assertSame(req, action.requestPre);
        assertSame(res, action.responsePre);
        assertSame(params, action.paramsPre);

        // �Ăяo���m�F:doExecuteBLogic()
        assertTrue(action.isDoExecuteBLogic);
        assertSame(params, action.paramDoExecuteBLogic);

        // �Ăяo���m�F:postDoExecuteBLogic()
        assertTrue(action.isPostDoExecuteBLogic);
        assertSame(req, action.requestPost);
        assertSame(res, action.responsePost);
        assertSame(params, action.paramsPost);
        assertSame(result, action.resultPost);

        // �Ăяo���m�F:evaluateBLogicResult()
        assertTrue(action.isEvaluateBLogicResult);
        assertSame(result, action.resultEvaluateBLogicResult);
        assertSame(req, action.requestEvaluateBLogicResult);
        assertSame(res, action.responseEvaluateBLogicResult);
        assertSame(mapping, action.mappingEvaluateBLogicResult);
    }

    /**
     * testDoExecute04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:MappingEx<br>
     *         (����) form:FormEx<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getBLogic<br>
     *                Params():�I�u�W�F�N�g<br>
     *         (���) doExecuteBLogic()<br>
     *                ���^�[���lresult:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) preDoExecute<br>
     *                    BLogic():�Ăяo���m�F�F<br>
     *                    preDoExecuteBLogic(request, response, params)<br>
     *         (��ԕω�) doExecute<br>
     *                    BLogic():�Ăяo���m�F�F<br>
     *                    doExecuteBLogic(params)<br>
     *         (��ԕω�) postDoExecute<br>
     *                    Blogic():�Ăяo���m�F�F<br>
     *                    postDoExecuteBLogic(request, response, params, null)<br>
     *         (��ԕω�) evaluate<br>
     *                    BLogicResult():�Ăяo�����Ȃ����Ƃ��m�F<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.blogic.result.null"<br>
     *                    ���b�v������O�F<br>
     *                    NullPointerException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "BLogicResult is null."<br>
     *
     * <br>
     * AbstractBLogicAction��getBLogicParams()��not null�ł���AdoExcecute()��
     * BLogicResult�C���X�^���X��Ԃ����ꍇ�ApreDoExecuteBLogic()�A
     * doExecuteBLogic()�ApostDoExecuteBLogic()���Ăяo���B<br>
     * doExecuteBLogic()�̃��^�[���l��null�̏ꍇ�A��O���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute04() throws Exception {
        // �O����
        ActionMappingEx mapping = new ActionMappingEx();

        // "resultString"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forw = new ActionForward();
        forw.setName("resultString");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forw);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        // BLogicParams : Object
        Object params = new Object();
        UTUtil.setPrivateField(action, "params", params);

        // BLogicResult : null
        BLogicResult result = null;
        UTUtil.setPrivateField(action, "result", result);


        try {
            // �e�X�g���{
            action.doExecute(mapping, form, req, res);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
            assertEquals("errors.blogic.result.null", e.getErrorCode());

            // �Ăяo���m�F:preDoExecuteBLogic()
            assertTrue(action.isPreDoExecuteBLogic);
            assertSame(req, action.requestPre);
            assertSame(res, action.responsePre);
            assertSame(params, action.paramsPre);

            // �Ăяo���m�F:doExecuteBLogic()
            assertTrue(action.isDoExecuteBLogic);
            assertSame(params, action.paramDoExecuteBLogic);

            // �Ăяo���m�F:postDoExecuteBLogic()
            assertTrue(action.isPostDoExecuteBLogic);
            assertSame(req, action.requestPost);
            assertSame(res, action.responsePost);
            assertSame(params, action.paramsPost);
            assertSame(result, action.resultPost);

            // �Ăяo���m�F:evaluateBLogicResult()
            assertFalse(action.isEvaluateBLogicResult);
        }
    }

    /**
     * testDoExecute05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:MappingEx<br>
     *         (����) form:FormEx<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getBLogic<br>
     *                Params():�I�u�W�F�N�g<br>
     *         (���) doExecuteBLogic()<br>
     *                ���^�[���lresult:BLogicResult<br>
     *                ([resultString=<br>
     *                "resultString"])<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:ActionForward.<br>
     *                  getName()�F<br>
     *                  "resultString"<br>
     *         (��ԕω�) preDoExecute<br>
     *                    BLogic():�Ăяo���m�F�F<br>
     *                    preDoExecuteBLogic(request, response, params)<br>
     *         (��ԕω�) doExecute<br>
     *                    BLogic():�Ăяo���m�F�F<br>
     *                    doExecuteBLogic(params)<br>
     *         (��ԕω�) postDoExecute<br>
     *                    Blogic():�Ăяo���m�F�F<br>
     *                    postDoExecuteBLogic(request, response, params, result)<br>
     *         (��ԕω�) evaluate<br>
     *                    BLogicResult():�Ăяo���m�F<br>
     *                    evaluateBLogicResult(result, request, response, mappingEx)<br>
     *
     * <br>
     * AbstractBLogicAction��getBLogicParams()��not null�ł���AdoExcecute()��
     * BLogicResult�C���X�^���X��Ԃ����ꍇ�ApreDoExecuteBLogic()�A
     * doExecuteBLogic()�ApostDoExecuteBLogic()���Ăяo���B<br>
     * doExecuteBLogic()�̃��^�[���l��null�łȂ������ꍇ�AevaluateBLogicResult()
     * ���Ăяo������ActionForward�I�u�W�F�N�g��Ԃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute05() throws Exception {
        // �O����
        ActionMappingEx mapping = new ActionMappingEx();

        // "resultString"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forw = new ActionForward();
        forw.setName("resultString");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forw);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub01 action =
            new AbstractBLogicAction_AbstractBLogicActionStub01();

        // BLogicParams : Object
        Object params = "abc";
        UTUtil.setPrivateField(action, "params", params);

        // BLogicResult : resultString="resultString"
        BLogicResult result = new BLogicResult();
        result.setResultString("resultString");
        UTUtil.setPrivateField(action, "result", result);

        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        // forward.getName()
        assertEquals("resultString", forward.getName());

        // �Ăяo���m�F:preDoExecuteBLogic()
        assertTrue(action.isPreDoExecuteBLogic);
        assertSame(req, action.requestPre);
        assertSame(res, action.responsePre);
        assertSame(params, action.paramsPre);

        // �Ăяo���m�F:doExecuteBLogic()
        assertTrue(action.isDoExecuteBLogic);
        assertSame(params, action.paramDoExecuteBLogic);

        // �Ăяo���m�F:postDoExecuteBLogic()
        assertTrue(action.isPostDoExecuteBLogic);
        assertSame(req, action.requestPost);
        assertSame(res, action.responsePost);
        assertSame(params, action.paramsPost);
        assertSame(result, action.resultPost);

        // �Ăяo���m�F:evaluateBLogicResult()
        assertTrue(action.isEvaluateBLogicResult);
        assertSame(result, action.resultEvaluateBLogicResult);
        assertSame(req, action.requestEvaluateBLogicResult);
        assertSame(res, action.responseEvaluateBLogicResult);
        assertSame(mapping, action.mappingEvaluateBLogicResult);
    }

    /**
     * testGetBLogicParams01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getBLogicIO(<br>
     *                request, response):BLogicIO<br>
     *                ([path="path"])<br>
     *         (���) getBLogicMapper(<br>
     *                request):AbstractBLogicAction_AbstractBLogicMapperStub01��
     *                �I�u�W�F�N�g<br>
     *         (���) AbstractBLogicMapper.<br>
     *                mapBLogicParams():null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) P:null<br>
     *
     * <br>
     * AbstractBLogicMapper.mapBLogicParams()��null�̏ꍇ�Anull��Ԃ����Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicParams01() throws Exception {
        // �O����
        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub03 action =
            new AbstractBLogicAction_AbstractBLogicActionStub03();

        // mapper.mapBLogicParams : null
        AbstractBLogicMapper mapper =
            new AbstractBLogicAction_AbstractBLogicMapperStub01();
        action.mapper = mapper;

        // �e�X�g���{
        Object bean = action.getBLogicParams(mapping, req, res);

        // ����
        assertNull(bean);
    }

    /**
     * testGetBLogicParams02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) getBLogicIO(<br>
     *                request, response):BLogicIO<br>
     *                ([path="path"])<br>
     *         (���) getBLogicMapper(<br>
     *                request):AbstractBLogicAction_AbstractBLogicMapperStub02��
     *                �I�u�W�F�N�g<br>
     *         (���) AbstractBLogicMapper.<br>
     *                mapBLogicParams():String("abc")<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) P:String("abc")<br>
     *
     * <br>
     * AbstractBLogicMapper.mapBLogicParams()���I�u�W�F�N�g�̏ꍇ�A
     * ���̃I�u�W�F�N�g��Ԃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicParams02() throws Exception {
        // �O����
        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub03 action =
            new AbstractBLogicAction_AbstractBLogicActionStub03();

        // mapper.mapBLogicParams : new String("abc")
        AbstractBLogicMapper mapper =
            new AbstractBLogicAction_AbstractBLogicMapperStub02();
        action.mapper = mapper;

        // �e�X�g���{
        Object bean = action.getBLogicParams(mapping, req, res);

        // ����
        assertEquals("abc", bean);
    }

    /**
     * testConvertMessages01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicMessages:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionMessages:null<br>
     *
     * <br>
     * ����blogicMessages��null�̏ꍇ�Anull��Ԃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertMessages01() throws Exception {
        // �O����
        BLogicMessages errors = null;

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        ActionMessages messages = action.convertMessages(errors);

        // ����
        assertNull(messages);
    }

    /**
     * testConvertMessages02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicMessages:0��<br>
     *         (���) ���b�Z�[�W�ꗗ:list.size()==0<br>
     *         (���) ���b�Z�[�W<br>
     *                �O���[�v���ꗗ:groupList.size()==0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionMessages:ActionMessages�F()<br>
     *
     * <br>
     * BLogicMessages�ƃ��b�Z�[�W�O���[�v���̃T�C�Y��0�̏ꍇ�A���ActionMessages��
     * �Ԃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertMessages02() throws Exception {
        // �O����
        // list size : 0
        BLogicMessages errors = new BLogicMessages();

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        ActionMessages messages = action.convertMessages(errors);

        // ����
        assertTrue(messages.isEmpty());
    }

    /**
     * testConvertMessages03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicMessages:not null<br>
     *         (���) ���b�Z�[�W�ꗗ:list�F<br>
     *                (BLogicMessage�F<br>
     *                [key�F"error01"�Avalue�F"value01"]<br>
     *                )<br>
     *         (���) ���b�Z�[�W<br>
     *                �O���[�v���ꗗ:groupList�F<br>
     *                ("errors")<br>
     *         (���) blogicMessage.<br>
     *                isResource():true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionMessages:ActionMessages�F<br>
     *                  (<br>
     *                  ["errors"�AActionMessage[<br>
     *                  key�F"error01"�Avalue�F"value01"]]<br>
     *                  )<br>
     *
     * <br>
     * BLogicMessages��1����BLogicMessage���i�[����Ă���A���b�Z�[�W�O���[�v��
     * ��1���̃O���[�v�����i�[����Ă���AblogicMessage.isResource()��true��
     * �ꍇ�A�O������̃O���[�v�����L�[�ł���A�O�������BLogicMessasge�̃L�[��
     * �o�����[������ActionMessage���i�[���ꂽActionMessages���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertMessages03() throws Exception {
        // �O����
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        ActionMessages messages = action.convertMessages(errors);

        // ����
        // messages����
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);
    }

    /**
     * testConvertMessages04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicMessages:not null<br>
     *         (���) ���b�Z�[�W�ꗗ:list�F<br>
     *                (BLogicMessage�F<br>
     *                [key�F"error01"�Avalue[0]�F"value0"�Avalue[1]�F"value1"�A
     *                value[2]�F"value2"]<br>
     *                )<br>
     *         (���) ���b�Z�[�W<br>
     *                �O���[�v���ꗗ:groupList�F<br>
     *                ("errors")<br>
     *         (���) blogicMessage.<br>
     *                isResource():true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionMessages:ActionMessages�F<br>
     *                  (<br>
     *                  ["errors"�AActionMessage[<br>
     *                  key�F"error01"�Avalue[0]�F"value0"�Avalue[1]�F"value1"�A
     *                  value[2]�F"value2"]]<br>
     *                  )<br>
     *
     * <br>
     * BLogicMessages��1����BLogicMessage���i�[����Ă���A���b�Z�[�W�O���[�v��
     * ��1���̃O���[�v�����i�[����Ă���AblogicMessage.isResource()��true��
     * �ꍇ�A�O������̃O���[�v�����L�[�ł���A�O�������BLogicMessasge�̃L�[��
     * �o�����[������ActionMessage���i�[���ꂽActionMessages���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertMessages04() throws Exception {
        // �O����
        BLogicMessages errors = new BLogicMessages();
        Object[] values = new Object[3];
        values[0] = "value0";
        values[1] = "value1";
        values[2] = "value2";
        BLogicMessage error = new BLogicMessage("error01", values);
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        ActionMessages messages = action.convertMessages(errors);

        // ����
        // messages����
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertEquals("value0", resultMessage.getValues()[0]);
        assertEquals("value1", resultMessage.getValues()[1]);
        assertEquals("value2", resultMessage.getValues()[2]);
    }

    /**
     * testConvertMessages05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicMessages:not null<br>
     *         (���) ���b�Z�[�W�ꗗ:list�F<br>
     *                (BLogicMessage�F<br>
     *                [key�F"error01"�Avalue�F"value01"]<br>
     *                )<br>
     *         (���) ���b�Z�[�W<br>
     *                �O���[�v���ꗗ:groupList�F<br>
     *                ("errors")<br>
     *         (���) blogicMessage.<br>
     *                isResource():false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionMessages:ActionMessages�F<br>
     *                  (<br>
     *                  ["errors"�AActionMessage[<br>
     *                  key�F"error01"�Avalue�Fnull�Aresource�Ffalse]]<br>
     *                  )<br>
     *
     * <br>
     * BLogicMessages��1����BLogicMessage���i�[����Ă���A���b�Z�[�W�O���[�v��
     * ��1���̃O���[�v�����i�[����Ă���AblogicMessage.isResource()��true
     * �łȂ������ꍇ�A�O������̃O���[�v�����L�[�ł���A�O�������
     * BLogicMessasge�̃L�[��BLogicMessage.isResource()�̃o�����[������
     * ActionMessage���i�[���ꂽActionMessages���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertMessages05() throws Exception {
        // �O����
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        UTUtil.setPrivateField(error, "resource", false);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        ActionMessages messages = action.convertMessages(errors);

        // ����
        // messages����
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertNull(resultMessage.getValues());
        assertFalse(resultMessage.isResource());
    }

    /**
     * testConvertMessages06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicMessages:not null<br>
     *         (���) ���b�Z�[�W�ꗗ:list�F<br>
     *                (BLogicMessage�F<br>
     *                [key�F"error01"�Avalue�F"value01"]�A<br>
     *                BLogicMessage�F<br>
     *                [key�F"error02"�Avalue�F"value02"]�A<br>
     *                BLogicMessage�F<br>
     *                [key�F"error03"�Avalue�F"value03"]<br>
     *                )<br>
     *         (���) ���b�Z�[�W<br>
     *                �O���[�v���ꗗ:groupList�F<br>
     *                ("errors)<br>
     *         (���) blogicMessage.<br>
     *                isResource():list.get(0)�Ftrue<br>
     *                list.get(1)�Ftrue<br>
     *                list.get(2)�Ftrue<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionMessages:ActionMessages�F<br>
     *                  (<br>
     *                  ["errors"�AActionMessage[<br>
     *                  key�F"error01"�Avalue�F"value01"]]�A<br>
     *                  ["errors"�AActionMessage[<br>
     *                  key�F"error02"�Avalue�F"value02"]]�A<br>
     *                  ["errors"�AActionMessage[<br>
     *                  key�F"error03"�Avalue�F"value03"]]<br>
     *                  )<br>
     *
     * <br>
     * blogicMessages�ɕ�����BLogicMessage�ƃ��b�Z�[�W�O���[�v���i�[����Ă���
     * �ꍇ�A�O���[�v�����L�[�Ƃ��A�O�������BLogicMessage�̃L�[�ƃo�����[������
     * ActionMessage�������ǂ���Ɋi�[���ꂽActionMessages���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testConvertMessages06() throws Exception {
        // �O����
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        error = new BLogicMessage("error02", "value02");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        error = new BLogicMessage("error03", "value03");
        UTUtil.setPrivateField(error, "resource", true);
        errors.add("errors", error);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        ActionMessages messages = action.convertMessages(errors);

        // ����
        // messages����
        // group
        Iterator itProps = messages.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);

        // message
        Iterator itMessages = messages.get(prop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error02", resultMessage.getKey());
        assertEquals("value02", resultMessage.getValues()[0]);

        resultMessage = (ActionMessage) itMessages.next();
        assertEquals("error03", resultMessage.getKey());
        assertEquals("value03", resultMessage.getValues()[0]);
    }

    /**
     * testEvaluateBLogicResult01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) result:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) mapping:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.blogic.result.null"<br>
     *                    ���b�v������O�F<br>
     *                    NullPointerException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "BLogicResult is null."<br>
     *
     * <br>
     * ����result��null�̏ꍇ�ASystemException����������B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEvaluateBLogicResult01() throws Exception {
        // �O����
        // result : null
        BLogicResult result = null;

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        try {
            // �e�X�g���{
            action.evaluateBLogicResult(result, req, res, mapping);
            fail();
        } catch (SystemException e) {
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
            assertEquals("errors.blogic.result.null", e.getErrorCode());
        }
    }

    /**
     * testEvaluateBLogicResult02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                �I�u�W�F�N�g<br>
     *                result.getErrors()�F<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="error01"�Avalue="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("errors")<br>
     *                <br>
     *                result.getMessages()�F<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"�Avalue="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) mapping:not null<br>
     *         (���) saveMessageSope:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) request.getAttribute(<br>
     *                    Globals.ERROR_KEY):ActionMessages�F<br>
     *                    (<br>
     *                    ["errors"�AActionMessage[<br>
     *                    key�F"error01"�Avalue�F"value01"]]<br>
     *                    )<br>
     *         (��ԕω�) request.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages�F<br>
     *                    (<br>
     *                    ["messages"�AActionMessage[<br>
     *                    key�F"message01"�Avalue�F"value01"]]<br>
     *                    )<br>
     *         (��ԕω�) getBLogicIO(<br>
     *                    mappingEx, request):�Ăяo�����Ȃ����Ƃ��m�F<br>
     *         (��ԕω�) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():�Ăяo�����Ȃ����Ƃ��m�F<br>
     *
     * <br>
     * saveMessageScope��null�̏ꍇ�Arequest�ɃG���[�ƃ��b�Z�[�W�̃��X�g��
     * �i�[����Ă��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEvaluateBLogicResult02() throws Exception {
        // �O����
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        errors.add("errors", error);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setErrors(errors);
        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : null
        String saveMessageScope = null;
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // �e�X�g���{
        action.evaluateBLogicResult(result, req, res, mapping);

        // ����
        // errors����
        ActionMessages resultErrors =
            (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        // group
        Iterator itProps = resultErrors.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // error
        Iterator itErrors = resultErrors.get(prop);
        ActionMessage resultError = (ActionMessage) itErrors.next();
        assertEquals("error01", resultError.getKey());
        assertEquals("value01", resultError.getValues()[0]);

        // messages����
        ActionMessages resultMessages =
            (ActionMessages) req.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO�Ăяo���m�F
        assertFalse(action.isGetBLogicIO);

        // mapBLogicResult�Ăяo���m�F
        assertEquals("false", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testEvaluateBLogicResult03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                �I�u�W�F�N�g<br>
     *                result.getErrors()�F<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="error01"�Avalue="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("errors")<br>
     *                <br>
     *                result.getMessages()�F<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"�Avalue="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) mapping:not null<br>
     *         (���) saveMessageSope:""<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) request.getAttribute(<br>
     *                    Globals.ERROR_KEY):ActionMessages�F<br>
     *                    (<br>
     *                    ["errors"�AActionMessage[<br>
     *                    key�F"error01"�Avalue�F"value01"]]<br>
     *                    )<br>
     *         (��ԕω�) request.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages�F<br>
     *                    (<br>
     *                    ["messages"�AActionMessage[<br>
     *                    key�F"message01"�Avalue�F"value01"]]<br>
     *                    )<br>
     *         (��ԕω�) getBLogicIO(<br>
     *                    mappingEx, request):�Ăяo�����Ȃ����Ƃ��m�F<br>
     *         (��ԕω�) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():�Ăяo�����Ȃ����Ƃ��m�F<br>
     *
     * <br>
     * saveMessageScope��"session"�łȂ������ꍇ�Arequest�ɃG���[�ƃ��b�Z�[�W��
     * ���X�g���i�[����Ă��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEvaluateBLogicResult03() throws Exception {
        // �O����
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        errors.add("errors", error);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setErrors(errors);
        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : ""
        String saveMessageScope = "";
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // �e�X�g���{
        action.evaluateBLogicResult(result, req, res, mapping);

        // ����
        // errors����
        ActionMessages resultErrors =
            (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        // group
        Iterator itProps = resultErrors.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // error
        Iterator itErrors = resultErrors.get(prop);
        ActionMessage resultError = (ActionMessage) itErrors.next();
        assertEquals("error01", resultError.getKey());
        assertEquals("value01", resultError.getValues()[0]);

        // messages����
        ActionMessages resultMessages =
            (ActionMessages) req.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO�Ăяo���m�F
        assertFalse(action.isGetBLogicIO);

        // mapBLogicResult�Ăяo���m�F
        assertEquals("false", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testEvaluateBLogicResult04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                �I�u�W�F�N�g<br>
     *                result.getErrors()�F<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="error01"�Avalue="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("errors")<br>
     *                <br>
     *                result.getMessages()�F<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"�Avalue="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) mapping:not null<br>
     *         (���) saveMessageSope:"session"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session.getAttribute(<br>
     *                    Globals.ERROR_KEY):ActionMessages�F<br>
     *                    (<br>
     *                    ["errors"�AActionMessage[<br>
     *                    key�F"error01"�Avalue�F"value01"]]<br>
     *                    )<br>
     *         (��ԕω�) session.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages�F<br>
     *                    (<br>
     *                    ["messages"�AActionMessage[<br>
     *                    key�F"message01"�Avalue�F"value01"]]<br>
     *                    )<br>
     *         (��ԕω�) getBLogicIO(<br>
     *                    mappingEx, request):�Ăяo�����Ȃ����Ƃ��m�F<br>
     *         (��ԕω�) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():�Ăяo�����Ȃ����Ƃ��m�F<br>
     *
     * <br>
     * saveMessageScope��"session"�������ꍇ�Asession�ɃG���[�ƃ��b�Z�[�W��
     * ���X�g���i�[����Ă��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEvaluateBLogicResult04() throws Exception {
        // �O����
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("error01", "value01");
        errors.add("errors", error);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setErrors(errors);
        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : "session"
        String saveMessageScope = "session";
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // �e�X�g���{
        action.evaluateBLogicResult(result, req, res, mapping);

        // ����
        // session����擾����
        HttpSession session = req.getSession();
        // errors����
        ActionMessages resultErrors =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        // group
        Iterator itProps = resultErrors.properties();
        String prop = (String) itProps.next();
        assertEquals("errors", prop);
        // error
        Iterator itErrors = resultErrors.get(prop);
        ActionMessage resultError = (ActionMessage) itErrors.next();
        assertEquals("error01", resultError.getKey());
        assertEquals("value01", resultError.getValues()[0]);

        // messages����
        ActionMessages resultMessages =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO�Ăяo���m�F
        assertFalse(action.isGetBLogicIO);

        // mapBLogicResult�Ăяo���m�F
        assertEquals("false", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testEvaluateBLogicResult05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                �I�u�W�F�N�g�F<br>
     *                result.getErrors()�Fnull<br>
     *                <br>
     *                result.getMessages()�F<br>
     *                BLogicMessages.list(<br>
     *                  BLogicMessage[key="message01"�Avalue="value01"]<br>
     *                )<br>
     *                BLogicMessages.groupList("messages")<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) mapping:not null<br>
     *         (���) saveMessageSope:"SESSION"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session.getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *         (��ԕω�) session.getAttribute(<br>
     *                    Globals.MESSAGE_KEY):ActionMessages�F<br>
     *                    (<br>
     *                    ["messages"�AActionMessage[<br>
     *                    key�F"message01"�Avalue�F"value01"]]<br>
     *                    )<br>
     *         (��ԕω�) getBLogicIO(<br>
     *                    mappingEx, request):�Ăяo���m�F�F<br>
     *                    getBLogicIO(<br>
     *                    mappingEx, request)<br>
     *         (��ԕω�) AbstractBLogicMapper.<br>
     *                    mapBLogicReslut():�Ăяo���m�F�F<br>
     *                    mapBLogicResult(request, response, blogicIO, result)<br>
     *
     * <br>
     * ����result.getErrors()��null�������ꍇ�AAbstractBLogicMapper.<br>
     * mapBLogicReslut()���Ăяo����邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEvaluateBLogicResult05() throws Exception {
        // �O����
        // result : not null
        BLogicResult result = new BLogicResult();

        // errors : null
        result.setErrors(null);

        // messages
        BLogicMessages messages = new BLogicMessages();
        BLogicMessage message = new BLogicMessage("message01", "value01");
        messages.add("messages", message);

        result.setMessages(messages);

        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("isMapBLogicResult", "false");
        MockHttpServletResponse res = new MockHttpServletResponse();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub04 action =
            new AbstractBLogicAction_AbstractBLogicActionStub04();

        // saveMessageScope : "SESSION"
        String saveMessageScope = "SESSION";
        UTUtil.setPrivateField(action, "saveMessageScope", saveMessageScope);

        // �e�X�g���{
        action.evaluateBLogicResult(result, req, res, mapping);

        // ����
        // session����擾����
        HttpSession session = req.getSession();
        // errors����
        ActionMessages resultErrors =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        assertNull(resultErrors);

        // messages����
        ActionMessages resultMessages =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        // group
        Iterator itMProps = resultMessages.properties();
        String mprop = (String) itMProps.next();
        assertEquals("messages", mprop);
        // message
        Iterator itMessages = resultMessages.get(mprop);
        ActionMessage resultMessage = (ActionMessage) itMessages.next();
        assertEquals("message01", resultMessage.getKey());
        assertEquals("value01", resultMessage.getValues()[0]);

        // getBLogicIO�Ăяo���m�F
        assertTrue(action.isGetBLogicIO);

        // mapBLogicResult�Ăяo���m�F
        assertEquals("true", req.getAttribute("isMapBLogicResult"));
    }

    /**
     * testIsErrorsEmpty01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) result:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.blogic.result.null"<br>
     *                    ���b�v������O�F<br>
     *                    NullPointerException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "BLogicResult is null."<br>
     *
     * <br>
     * result��null�̏ꍇ�A�G���[���������邱�Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsErrorsEmpty01() throws Exception {
        // �O����
        BLogicResult result = null;

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        try {
            // �e�X�g���{
            action.isErrorsEmpty(result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
            assertEquals("errors.blogic.result.null", e.getErrorCode());
        }
    }

    /**
     * testIsErrorsEmpty02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                �I�u�W�F�N�g<br>
     *         (���) result.getErrors():null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * result.getErrors()��null�̏ꍇ�Atrue���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsErrorsEmpty02() throws Exception {
        // �O����
        BLogicResult result = new BLogicResult();
        BLogicMessages errors = null;
        result.setErrors(errors);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        boolean b = action.isErrorsEmpty(result);

        // ����
        assertTrue(b);
    }

    /**
     * testIsErrorsEmpty03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                �I�u�W�F�N�g<br>
     *         (���) result.getErrors():BLogicMessages.list�F(new ArrayList())
     *         <br>
     *         (���) BLogicMessages.<br>
     *                isEmpty():true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * result.getErrors()��list��0���̃��X�g�̏ꍇ�Atrue���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsErrorsEmpty03() throws Exception {
        // �O����
        BLogicResult result = new BLogicResult();
        BLogicMessages errors = new BLogicMessages();
        result.setErrors(errors);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        boolean b = action.isErrorsEmpty(result);

        // ����
        assertTrue(b);
    }

    /**
     * testIsErrorsEmpty04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                �I�u�W�F�N�g<br>
     *         (���) result.getErrors():BLogicMessages.list�F<br>
     *                ([key="key"�Avalue="value"])<br>
     *         (���) BLogicMessages.<br>
     *                isEmpty():false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * result.getErrors()��list��1���ȏ�̃��X�g�̏ꍇ�Afalse���Ԃ���邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsErrorsEmpty04() throws Exception {
        // �O����
        BLogicResult result = new BLogicResult();
        BLogicMessages errors = new BLogicMessages();
        BLogicMessage error = new BLogicMessage("key", "value");
        errors.add("errors", error);
        result.setErrors(errors);

        AbstractBLogicAction_AbstractBLogicActionStub05 action =
            new AbstractBLogicAction_AbstractBLogicActionStub05();

        // �e�X�g���{
        boolean b = action.isErrorsEmpty(result);

        // ����
        assertFalse(b);
    }

    /**
     * testGetBLogicMapper01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (���) ServletContext.getAttribute("BLOGIC_MAPPER" +
     *         ModuleUtil.getPrefix(req)):null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.blogic.mapper.null"<br>
     *                    ���b�v������O�F<br>
     *                    NullPointerException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "BLogicMapper is null."<br>
     *
     * <br>
     * ServletContext����擾�����I�u�W�F�N�g��null�̏ꍇ�A
     * ��O���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicMapper01() throws Exception {
        // �O����
        // request
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", null);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        try {
            // �e�X�g���{
            action.getBLogicMapper(req);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals(NullPointerException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicMapper is null."));
            assertEquals("errors.blogic.mapper.null", e.getErrorCode());
        }
    }

    /**
     * testGetBLogicMapper02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (���) ServletContext.getAttribute("BLOGIC_MAPPER" +
     *         ModuleUtil.getPrefix(req)):"abc"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.blogic.mapping"<br>
     *                    ���b�v������O�F<br>
     *                    ClassCastException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Cannot cast BLogicMapper : java.lang.String"<br>
     *
     * <br>
     * ServletContext����擾�����I�u�W�F�N�g��AbstractBLogicMapper
     * �C���X�^���X�ȊO�̏ꍇ�A��O���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicMapper02() throws Exception {
        // �O����
        // request
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", "abc");
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        try {
            // �e�X�g���{
            action.getBLogicMapper(req);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            // java6�ȍ~�͗�O���b�Z�[�W�����P����Ă��邽�߁A�o�[�W�����ɕ������A�T�[�g���s��
            String javaVersion = System.getProperty("java.version");
            if (Integer.valueOf(javaVersion.split("\\.")[1]) <= 5) {
                assertTrue(LogUTUtil
                        .checkError("Cannot cast BLogicMapper : java.lang.String"));
            } else {
                assertTrue(LogUTUtil
                        .checkError("Cannot cast BLogicMapper : java.lang.String"
                                + " cannot be cast to jp.terasoluna.fw.service.thin.AbstractBLogicMapper"));
            }
            assertEquals("errors.blogic.mapping", e.getErrorCode());
        }
    }

    /**
     * testGetBLogicMapper03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (���) ServletContext.getAttribute("BLOGIC_MAPPER" +
     *         ModuleUtil.getPrefix(req)):AbstractBLogicAction_AbstractBLogicMapperStub01�̃I�u�W�F�N�g<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) AbstractBLogicMapper:AbstractBLogicAction_AbstractBLogicMapperStub01�̃I�u�W�F�N�g<br>
     *
     * <br>
     * ServletContext����擾�����I�u�W�F�N�g��AbstractBLogicMapper�C���X�^���X
     * �̏ꍇ�A�O�������AbstractBLogicMapper�̃C���X�^���X��Ԃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicMapper03() throws Exception {
        // �O����
        // request
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        AbstractBLogicAction_AbstractBLogicMapperStub01 tmapper =
            new AbstractBLogicAction_AbstractBLogicMapperStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", tmapper);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        AbstractBLogicMapper mapper = null;
        // �e�X�g���{
        mapper = action.getBLogicMapper(req);

        // ����
        assertSame(tmapper, mapper);
    }

    /**
     * testGetBLogicIO01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) req:not null<br>
     *         (���) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) BLogicIO:null<br>
     *
     * <br>
     * ServletContext����擾�����I�u�W�F�N�g��null�̏ꍇ�A
     * null��Ԃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO01() throws Exception {
        // �O����
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", null);
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        // �e�X�g���{
        io = action.getBLogicIO(mapping, req);

        // ����
        assertNull(io);
    }

    /**
     * testGetBLogicIO02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) req:not null<br>
     *         (���) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):"abc"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.blogic.resources"<br>
     *                    ���b�v������O�F<br>
     *                    ClassCastException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Cannot cast BLogicResources : java.lang.String"<br>
     *
     * <br>
     * ServletContext����擾�����I�u�W�F�N�g��BLogicResources�C���X�^���X
     * �ȊO�̏ꍇ�A��O���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO02() throws Exception {
        // �O����
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", "abc");
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        try {
            // �e�X�g���{
            io = action.getBLogicIO(mapping, req);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals(ClassCastException.class.getName(),
                    e.getCause().getClass().getName());
            // java6�ȍ~�͗�O���b�Z�[�W�����P����Ă��邽�߁A�o�[�W�����ɕ������A�T�[�g���s��
            String javaVersion = System.getProperty("java.version");
            if (Integer.valueOf(javaVersion.split("\\.")[1]) <= 5) {
                assertTrue(LogUTUtil
                        .checkError("Cannot cast BLogicResources : java.lang.String"));
            } else {
                assertTrue(LogUTUtil
                        .checkError("Cannot cast BLogicResources : java.lang.String"
                                + " cannot be cast to jp.terasoluna.fw.service.thin.BLogicResources"));
            }
            assertEquals("errors.blogic.resources", e.getErrorCode());
        }

        // ����
        assertNull(io);
    }

    /**
     * testGetBLogicIO03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) req:not null<br>
     *         (���) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):BLogicResources�C���X�^���X<br>
     *         (���) mapping.getPath():"abc"<br>
     *         (���) resource.getBLogicIO("abc"):BLogicIO<br>
     *                ([path="abc"])<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) BLogicIO:�O�������resource.getBLogicIO("abc")�Ɠ����
     * BLogicIO�C���X�^���X<br>
     *
     * <br>
     * ServletContext����擾�����I�u�W�F�N�g��BLogicResources�C���X�^���X��
     * �ꍇ�A�O�������resource.getBLogicIO("abc")�ɊY������BLogicIO�C���X�^���X
     * ��Ԃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO03() throws Exception {
        // �O����
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("abc");

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // BLogicIO("abc") : path="abc"
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("abc");
        resources.setBLogicIO(blogicIO);

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", resources);
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        // �e�X�g���{
        io = action.getBLogicIO(mapping, req);

        // ����
        assertSame(blogicIO, io);
    }

    /**
     * testGetBLogicIO04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) req:not null<br>
     *         (���) ServletContext.getAttribute(<br>
     *                "BLOGIC_RESOURCES" + <br>
     *                ModuleUtil.getPrefix(req)):BLogicResources�C���X�^���X<br>
     *         (���) mapping.getPath():"abc"<br>
     *         (���) resource.getBLogicIO("abc"):null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) BLogicIO:null<br>
     *
     * <br>
     * �O�������resource.getBLogicIO("abc")��null��Ԃ��ꍇ�Anull���Ԃ���邱��
     * ���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO04() throws Exception {
        // �O����
        // request, response
        MockHttpServletRequest req = new MockHttpServletRequest();

        // mapping
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("abc");

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        req.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();

        // BLogicIO("abc") : path="abc"
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("def");
        resources.setBLogicIO(blogicIO);

        // ServletContext
        ServletContext context = new MockServletContext();
        context.setAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", resources);
        servlet.setServletContext(context);

        // AbstractBLogicAction
        AbstractBLogicAction_AbstractBLogicActionStub02 action =
            new AbstractBLogicAction_AbstractBLogicActionStub02();

        UTUtil.setPrivateField(action, "servlet", servlet);

        BLogicIO io = null;

        // �e�X�g���{
        io = action.getBLogicIO(mapping, req);

        // ����
        assertNull(io);
    }
}
