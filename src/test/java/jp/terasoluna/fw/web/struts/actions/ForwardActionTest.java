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

import java.io.IOException;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ForwardAction}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * JSP�Ȃǂփt�H���[�h����A�N�V����
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.ForwardAction
 */
public class ForwardActionTest extends PropertyTestCase {

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
     * �v���p�e�B������������
     * @see jp.terasoluna.utlib.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        clearProperty();
    }

    /**
     * �v���p�e�B���N�����A�b�v����
     * @see jp.terasoluna.utlib.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        clearProperty();
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
    public ForwardActionTest(String name) {
        super(name);
    }

    /**
     * testDoExecute01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (�v���p�e�B) forwardAction.contextRelative:true
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *         (��ԕω�) res.sendError():�Ăяo���m�F<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��null�̏ꍇ�AHttpServletResponse.sendError()���Ăяo����Anull��Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute01() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();

        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� true �ɐݒ�
        addProperty("forwardAction.contextRelative", "true");
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfig��ݒ�
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ForwardAction_HttpServletResponseStub01 res =
            new ForwardAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (���) res.sendError():IOException����<br>
     *         (�v���p�e�B) forwardAction.contextRelative:true
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"error.forward.errorpage"<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��null�ŁAHttpServletResponse.sendError()�ďo�ŗ�O�����������ꍇ�ASystemException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute02() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();

        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� true �ɐݒ�
        addProperty("forwardAction.contextRelative", "true");
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfig��ݒ�
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ForwardAction_HttpServletResponseStub02 res =
            new ForwardAction_HttpServletResponseStub02();
        
        @SuppressWarnings("unused") ActionForward forward = null;

        // �e�X�g���{
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("��O���������܂���ł���");
        } catch (SystemException e) {
            // ����
            // IOException�����b�v������
            assertEquals(e.getCause().getClass().getName(),
                    IOException.class.getName());
            // �G���[���O�m�F
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
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
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:"abc"<br>
     *         (�v���p�e�B) forwardAction.contextRelative:true
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ftrue<br>
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�not null�̏ꍇ�AActionForward��path���w�肵��path�ƈ�v���邩�m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute03() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();

        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� true �ɐݒ�
        addProperty("forwardAction.contextRelative", "true");
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        assertEquals("abc", forward.getPath());
        assertTrue(forward.getContextRelative());
    }

    /**
     * testDoExecute04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��not null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:"abc"<br>
     *         (�v���p�e�B) forwardAction.contextRelative:true
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ftrue<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��not null�̏ꍇ�A�p�����[�^�̎擾���ʂ�J�ڐ�Ƃ��ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute04() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();

        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� true �ɐݒ�
        addProperty("forwardAction.contextRelative", "true");

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        // �A�N�V�����t�H���[�h�̐ݒ�
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(af);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        assertEquals("abc", forward.getPath());
        assertTrue(forward.getContextRelative());
    }

    /**
     * testDoExecute05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��not null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (�v���p�e�B) forwardAction.contextRelative:true
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:mapping.findForward("success")<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ffalse<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�null��mapping.findForward("success")��not null�̏ꍇ�Amapping.findForward("success")��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute05() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
        
        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� true �ɐݒ�
        addProperty("forwardAction.contextRelative", "true");

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // �A�N�V�����t�H���[�h�̐ݒ�
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");

        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(af);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        assertEquals(af, forward);
        assertFalse(forward.getContextRelative());
    }
    
    /**
     * testDoExecute06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (�v���p�e�B) forwardAction.contextRelative:false
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *         (��ԕω�) res.sendError():�Ăяo���m�F<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��null�̏ꍇ�AHttpServletResponse.sendError()���Ăяo����Anull��Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute06() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� false �ɐݒ�
        addProperty("forwardAction.contextRelative", "false");
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfig��ݒ�
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ForwardAction_HttpServletResponseStub01 res =
            new ForwardAction_HttpServletResponseStub01();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (���) res.sendError():IOException����<br>
     *         (�v���p�e�B) forwardAction.contextRelative:false
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"error.forward.errorpage"<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��null�ŁAHttpServletResponse.sendError()�ďo�ŗ�O�����������ꍇ�ASystemException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute07() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� false �ɐݒ�
        addProperty("forwardAction.contextRelative", "false");
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfig��ݒ�
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ForwardAction_HttpServletResponseStub02 res =
            new ForwardAction_HttpServletResponseStub02();
        
        @SuppressWarnings("unused") ActionForward forward = null;
    
        // �e�X�g���{
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("��O���������܂���ł���");
        } catch (SystemException e) {
            // ����
            // IOException�����b�v������
            assertEquals(e.getCause().getClass().getName(),
                    IOException.class.getName());
            // �G���[���O�m�F
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
    }

    /**
     * testDoExecute08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:"abc"<br>
     *         (�v���p�e�B) forwardAction.contextRelative:false
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ffalse<br>
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�not null�̏ꍇ�AActionForward��path���w�肵��path�ƈ�v���邩�m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute08() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� false �ɐݒ�
        addProperty("forwardAction.contextRelative", "false");
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��not null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:"abc"<br>
     *         (�v���p�e�B) forwardAction.contextRelative:false
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ffalse<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��not null�̏ꍇ�A�p�����[�^�̎擾���ʂ�J�ڐ�Ƃ��ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute09() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� false �ɐݒ�
        addProperty("forwardAction.contextRelative", "false");
    
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        // �A�N�V�����t�H���[�h�̐ݒ�
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(af);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��not null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (�v���p�e�B) forwardAction.contextRelative:false
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:mapping.findForward("success")<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ffalse<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�null��mapping.findForward("success")��not null�̏ꍇ�Amapping.findForward("success")��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute10() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
        
        // �v���p�e�B�N���A
        clearProperty();
        
        // �v���p�e�B forwardAction.contextRelative �� false �ɐݒ�
        addProperty("forwardAction.contextRelative", "false");
    
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // �A�N�V�����t�H���[�h�̐ݒ�
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
    
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(af);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertEquals(af, forward);
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (�v���p�e�B) forwardAction.contextRelative:���ݒ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *         (��ԕω�) res.sendError():�Ăяo���m�F<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��null�̏ꍇ�AHttpServletResponse.sendError()���Ăяo����Anull��Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute11() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
                
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfig��ݒ�
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ForwardAction_HttpServletResponseStub01 res =
            new ForwardAction_HttpServletResponseStub01();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute12()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (���) res.sendError():IOException����<br>
     *         (�v���p�e�B) forwardAction.contextRelative:���ݒ�
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"error.forward.errorpage"<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��null�ŁAHttpServletResponse.sendError()�ďo�ŗ�O�����������ꍇ�ASystemException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute12() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfig��ݒ�
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ForwardAction_HttpServletResponseStub02 res =
            new ForwardAction_HttpServletResponseStub02();
        
        @SuppressWarnings("unused") ActionForward forward = null;
    
        // �e�X�g���{
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("��O���������܂���ł���");
        } catch (SystemException e) {
            // ����
            // IOException�����b�v������
            assertEquals(e.getCause().getClass().getName(),
                    IOException.class.getName());
            // �G���[���O�m�F
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
    }

    /**
     * testDoExecute13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:"abc"<br>
     *         (�v���p�e�B) forwardAction.contextRelative:���ݒ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ffalse<br>
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�not null�̏ꍇ�AActionForward��path���w�肵��path�ƈ�v���邩�m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute13() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute14()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��not null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:"abc"<br>
     *         (�v���p�e�B) forwardAction.contextRelative:���ݒ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ffalse<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʋy��mapping.findForward("success")��not null�̏ꍇ�A�p�����[�^�̎擾���ʂ�J�ڐ�Ƃ��ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute14() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
    
        // �v���p�e�B�N���A
        clearProperty();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        // �A�N�V�����t�H���[�h�̐ݒ�
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
        
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(af);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute15()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                [mapping.findForward("success")��not null]<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) path:null<br>
     *         (�v���p�e�B) forwardAction.contextRelative:���ݒ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:mapping.findForward("success")<br>
     *         (�߂�l) actionForward:ActionForward.getContextRelative()�Ffalse<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�null��mapping.findForward("success")��not null�̏ꍇ�Amapping.findForward("success")��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute15() throws Exception {
        // �O����
        ForwardAction action = new ForwardAction();
        
        // �v���p�e�B�N���A
        clearProperty();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // �A�N�V�����t�H���[�h�̐ݒ�
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
    
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(af);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
    
        // ����
        assertEquals(af, forward);
        assertFalse(forward.getContextRelative());
    }
    
}
