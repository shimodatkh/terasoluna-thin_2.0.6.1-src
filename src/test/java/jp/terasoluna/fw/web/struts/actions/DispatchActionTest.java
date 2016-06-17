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

import java.util.HashMap;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DispatchAction} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �t�H���[�h��̐U�蕪���������s���A�N�V�����B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DispatchAction
 */
@SuppressWarnings("unchecked")
public class DispatchActionTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DispatchActionTest.class);
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
    public DispatchActionTest(String name) {
        super(name);
    }

    /**
     * testSetEvent01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"event"<br>
     *         (���) event:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) event:"event"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��event�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetEvent01() throws Exception {
        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���{
        action.setEvent("event");

        // ����
        assertEquals("event", UTUtil.getPrivateField(action, "event"));
    }

    /**
     * testDoExecute01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_success"}]<br>
     *                ["forward_success2"�F{"ABC"}]<br>
     *         (���) forward:"success"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *                "BLOCKAGE_THRU_KEY"�F"BLOCK"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * "event"�ɊY�����郊�N�G�X�g�̃p�����[�^�l��"forward_success"�̏ꍇ�A�}�b�s���O�ɐݒ肳��Ă���ActionForward"success"���ԋp����邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"���폜����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute01() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        req.setParameter("forward_success2", eventParamValues2);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"success"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertEquals("success", ret.getName());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_success1"}]<br>
     *         (���) forward:"success1"<br>
     *         (���) attribute:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:null<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * "event"�ɊY�����郊�N�G�X�g�̃p�����[�^�l��"forward_success1"�̏ꍇ�A�}�b�s���O�ɐݒ肳��Ă���ActionForward"success1"�����݂��Ȃ�����null�̃A�N�V�����t�H���[�h���ԋp����邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute02() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success1" };
        req.setParameter("event", eventParamValues1);

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // null�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertNull(ret);
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FAC
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *         (����) form:null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_success"}]<br>
     *         (���) forward:"success"<br>
     *         (���) attribute:"BLOCKAGE_THRU_KEY"�F"BLOCK"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * formEx��null�̏ꍇ�A��O���������邱�ƂȂ����������삷�邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_KEY"�����݂��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute03() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DynaValidatorActionFormEx formEx = null;

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success" };
        req.setParameter("event", eventParamValues1);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"success"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertEquals("success", ret.getName());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:"EVENT_FIELD"<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_success1"}]<br>
     *                ["EVENT_FIELD"�F{"forward_success"}]<br>
     *         (���) forward:"success"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * mapping�̃C�x���g�t�B�[���h�l��"EVENT_FIELD"�̏ꍇ�A���N�G�X�g�̃p�����[�^�l���L�[�F"EVENT_FIELD"�Ƃ��Ēl���擾���A�}�b�s���O�ɐݒ肳��Ă���ActionForward"success"���ԋp����邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute04() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success1" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();
        
        // event�F"EVENT_FIELD"
        UTUtil.setPrivateField(action, "event", "EVENT_FIELD");

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"success"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertEquals("success", ret.getName());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:Input�F"/input.jsp"<br>
     *                ActionForwardName�F"success"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_#input"}]<br>
     *                ["EVENT_FIELD"�F{"forward_success"}]<br>
     *         (���) forward:"#input"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *                "BLOCKAGE_THRU_KEY"�F"BLOCK"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�V���ɍ쐬����ActionFoward<br>
     *                  �iPath�F"/input.jsp"�j<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * ���N�G�X�g�̃p�����[�^�l��"forward_#input"�ŁA�}�b�s���O��input�����ƃA�N�V�����t�H���[�h���̗�����ݒ肵���ꍇ�AInput�������p�X���Ƃ���ActionForward���V���ɐ�������A�ԋp����邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute05() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setInput("/input.jsp");

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_#input" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"/input.jsp"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertEquals("/input.jsp", ret.getPath());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:Input�F"/input.jsp"<br>
     *                ActionForwardName�F"success"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_#INPUT"}]<br>
     *                ["EVENT_FIELD"�F{"forward_success"}]<br>
     *         (���) forward:"#INPUT"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *                "BLOCKAGE_THRU_KEY"�F"BLOCK"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�V���ɍ쐬����ActionFoward<br>
     *                  �iPath�F"/input.jsp"�j<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * ���N�G�X�g�̃p�����[�^�l��"forward_#INPUT"�Ƒ啶���̏ꍇ�ł��AInput�������p�X���Ƃ���ActionForward���V���ɐ�������A�ԋp����邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute06() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setInput("/input.jsp");

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_#INPUT" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"/input.jsp"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertEquals("/input.jsp", ret.getPath());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *                ActionForwardName�F"default"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["not_event"�F{"forward_success"}]<br>
     *         (���) forward:"default"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *                "BLOCKAGE_THRU_KEY"�F"BLOCK"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"default")<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * "event"�ɊY�����郊�N�G�X�g�p�����[�^�l�����݂��Ȃ����ߘ_���t�H���[�h����"default"�ł���ꍇ�A�}�b�s���O�ɐݒ肳��Ă���ActionForward"default"���ԋp����邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute07() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // "default"�̃A�N�V�����t�H���[�h�̐���
        ActionForward dForward = new ActionForward();
        dForward.setName("default");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        mConfig.addForwardConfig(dForward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success" };
        req.setParameter("not_event", eventParamValues1);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"default"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertEquals("default", ret.getName());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FAC
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_"}]<br>
     *         (���) forward:""<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *                "BLOCKAGE_THRU_KEY"�F"BLOCK"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:null<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * "event"�ɊY�����郊�N�G�X�g�̃p�����[�^�l��"forward_"�̏ꍇ�A�}�b�s���O�ɐݒ肳��Ă���ActionForward""�����݂��Ȃ�����null�̃A�N�V�����t�H���[�h���ԋp����邱�Ƃ��m�F����B<br>
     * �ǃ`�F�b�N�t���O"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute08() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_" };
        req.setParameter("event", eventParamValues1);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // null�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertNull(ret);
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute09()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:not null<br>
     *         (����) req:{CANCEL_KEY != null}<br>
     *         (����) res:not null<br>
     *         (���) event:null<br>
     *         (���) eventField:*<br>
     *         (���) params:not null<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *                "BLOCKAGE_THRU_KEY"�F"BLOCK"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:cancell���\�b�h����ԋp�����A�N�V�����p�X<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂���<br>
     *         
     * <br>
     * ���N�G�X�g�ɂ�CANCEL_KEY�����݂���ꍇ�Acancell���\�b�h���Ăяo�����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute09() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute(Globals.CANCEL_KEY, "true");
        UTUtil.setPrivateField(req, "params", null);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");
        req.setAttribute("BLOCKAGE_THRU_KEY", "BLOCK");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchActionImpl01();

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);
        
        assertEquals("cancelled", ret.getName());
        
        // �������폜����Ă��Ȃ�
        assertEquals("THRU", req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertEquals("BLOCK", req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *         (����) form:not null<br>
     *         (����) req:{CANCEL_KEY != null}<br>
     *         (����) res:not null<br>
     *         (���) event:"EVENT_FIELD"<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_success1"}]<br>
     *                ["EVENT_FIELD"�F{"forward_success"}]<br>
     *         (���) forward:"success"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *         
     * <br>
     * ���N�G�X�g�ɂ�CANCEL_KEY�����݂��Acancell���\�b�h��null��ԋp�����Ƃ��A�}�b�s���O�ɐݒ肳��Ă���ActionForward"success"���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute10() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("success");
        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forward);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_success1" };
        req.setParameter("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_success" };
        req.setParameter("EVENT_FIELD", eventParamValues2);
        req.setAttribute(Globals.CANCEL_KEY, "true");
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();
        
        // event�F"EVENT_FIELD"
        UTUtil.setPrivateField(action, "event", "EVENT_FIELD");

        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"success"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertEquals("success", ret.getName());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }

    /**
     * testDoExecute11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *                ActionForwardName�F"default"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:"EVENT_FIELD"<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["event"�F{"forward_XXXX"}]<br>
     *         (���) forward:"default"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"default")<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *
     * <br>
     * ActionMapping �̘_���t�H���[�h���F"default"���ݒ肳��Ă���O��ŁA
     * "event"�ɊY�����郊�N�G�X�g�p�����[�^�l��"forward_XXXX"
     * �iXXXX: ActionMapping �ɂ͑��݂��Ȃ��_���t�H���[�h���j�̂Ƃ��A
     * �ԋp�l�Ƃ��� "default"��ActionMapping ���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute11() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forwardSuccess = new ActionForward();
        forwardSuccess.setName("success");
        // "default"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forwardDefault = new ActionForward();
        forwardDefault.setName("default");

        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forwardSuccess);
        mConfig.addForwardConfig(forwardDefault);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "forward_XXXX" };
        req.setParameter("event", eventParamValues1);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();
        
        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"default"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertNotNull(ret);
        assertEquals("default", ret.getName());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }
    
    /**
     * testDoExecute12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionForwardName�F"success"<br>
     *                ActionForwardName�F"default"<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) event:"EVENT_FIELD"<br>
     *         (���) eventField:"event"<br>
     *         (���) params:["forward_XXXX"�F{"ABC"}]<br>
     *         (���) forward:"default"<br>
     *         (���) attribute:"SERVER_BLOCKAGE_THRU_KEY"�F"THRU"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"default")<br>
     *         (��ԕω�) restrictionEscapePaths:"SERVER_BLOCKAGE_THRU_KEY"��"BLOCKAGE_THRU_KEY"�����݂��Ȃ�<br>
     *
     * <br>
     * ActionMapping �̘_���t�H���[�h���F"default"���ݒ肳��Ă���O��ŁA
     * "event"�ɊY�����郊�N�G�X�g�p�����[�^�l��"forward_XXXX"
     * �iXXXX: ActionMapping �ɂ͑��݂��Ȃ��_���t�H���[�h���j�̂Ƃ��A
     * �ԋp�l�Ƃ��� "default"��ActionMapping ���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute12() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();

        // "success"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forwardSuccess = new ActionForward();
        forwardSuccess.setName("success");
        // "default"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forwardDefault = new ActionForward();
        forwardDefault.setName("default");

        // ModuleConfig���쐬
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // �ԋp�����ActionForward���w��
        mConfig.addForwardConfig(forwardSuccess);
        mConfig.addForwardConfig(forwardDefault);
        // ���W���[���R���t�B�O��forward�����w��
        mapping.setModuleConfig(mConfig);

        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] eventParamValues1 = { "ABC" };
        req.setParameter("forward_XXXX", eventParamValues1);
        // �����̐ݒ�
        req.setAttribute("SERVER_BLOCKAGE_THRU_KEY", "THRU");

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        // DispatchAction����
        DispatchAction action = new DispatchAction();
        
        // �e�X�g���s
        ActionForward ret = action.doExecute(mapping, formEx, req, res);

        // ���ʂ̊m�F
        // �}�b�s���O�ɐݒ肳��Ă���"success"�̃A�N�V�����t�H���[�h��ԋp���邱��
        assertNotNull(ret);
        assertEquals("default", ret.getName());
        // �������폜����Ă��邱��
        assertNull(req.getAttribute("SERVER_BLOCKAGE_THRU_KEY"));
        assertNull(req.getAttribute("BLOCKAGE_THRU_KEY"));
    }
    
    /**
     * testDoDetermineForward01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["event"�F{"forward_success1"}]<br>
     *                ["forward_success2"�F{"ABC"}]<br>
     *         (����) event:"event"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"success1"<br>
     *         
     * <br>
     * params�ɃL�["event"�����݂��A���̒l��"forward_"����n�܂��Ă���΁A"forward_"���������������ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward01() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "event");

        // ���ʊm�F
        assertEquals("success1", ret);
    }

    /**
     * testDoDetermineForward02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["event"�F{"forward1_success","forward_success1"}]<br>
     *                ["forward_success2"�F{"ABC"}]<br>
     *         (����) event:"event"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"success1"<br>
     *         
     * <br>
     * params�ɃL�["event"�����݂��A���̓�1�̒l��"forward_"����n�܂��Ă���΁A"forward_"�������������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward02() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward1_success", "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "event");

        // ���ʊm�F
        assertEquals("success1", ret);
    }

    /**
     * testDoDetermineForward03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["event"�F{"success1","success2"}]<br>
     *                ["forward_success2"�F{"ABC"}]<br>
     *         (����) event:"event"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"success2"<br>
     *         
     * <br>
     * params�ɃL�["event"�����݂��A���̒l��"forward_"����n�܂���̂������ꍇ�A"forward_"����n�܂�L�[������΁A"forward_"���������������ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward03() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "success1", "success2" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "event");

        // ���ʊm�F
        assertEquals("success2", ret);
    }

    /**
     * testDoDetermineForward04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["not_event"�F{"forward_success1"}]<br>
     *                ["forward_success2"�F{"ABC"}]<br>
     *         (����) event:"event"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"success2"<br>
     *         
     * <br>
     * params�ɃL�["event"�����݂��Ȃ��ꍇ�A"forward_"����n�܂�L�[������΁A"forward_"���������������ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward04() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("not_event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "event");

        // ���ʊm�F
        assertEquals("success2", ret);
    }

    /**
     * testDoDetermineForward05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["not_event"�F"forward_success1"]<br>
     *                ["not_forward_success2"�F{"ABC"}]<br>
     *         (����) event:"event"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"default"<br>
     *         
     * <br>
     * params�ɃL�["event"�����݂����A�����"forward_"����n�܂�L�[�����݂��Ȃ��ꍇ�A"default"���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward05() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("not_event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("not_forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "event");

        // ���ʊm�F
        assertEquals("default", ret);
    }

    /**
     * testDoDetermineForward06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["event"�F{"forward_"}]<br>
     *                ["forward_success2"�F{"ABC"}]<br>
     *         (����) event:"event"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * params�ɃL�["event"�����݂��A���̒l��"forward_"�̏ꍇ�A�󕶎����ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward06() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("not_forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "event");

        // ���ʊm�F
        assertEquals("", ret);
    }

    /**
     * testDoDetermineForward07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FAC
     * <br><br>
     * ���͒l�F(����) params:���Map<br>
     *         (����) event:"event"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"default"<br>
     *         
     * <br>
     * params����̏ꍇ�A"default"���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward07() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "event");

        // ���ʊm�F
        assertEquals("default", ret);
    }

    /**
     * testDoDetermineForward08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["event"�F{"forward_success1"}]<br>
     *                ["forward_success2"�F{"forward_ABC","forward_success"}]<br>
     *         (����) event:"forward_success2"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"ABC"<br>
     *         
     * <br>
     * eventField��"forward_success2"�ɂ����ꍇ�Aparams�ɃL�[�����݂��A���̒l��"forward_"����n�܂��Ă���΁A"forward_"���������������ԋp���邱�Ƃ��m�F����B<br>
     * "forward_"����n�܂�l���������݂���ꍇ�́Aindex�̍ł��Ⴂ�l��"forward_"�������������񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward08() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_ABC", "forward_success" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "forward_success2");

        // ���ʊm�F
        assertEquals("ABC", ret);
    }

    /**
     * testDoDetermineForward09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:["event"�F{"forward_success1"}]<br>
     *                ["forward_"�F{"forward_ABC"}]<br>
     *         (����) event:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * eventField���󕶎��ɐݒ肵�Aparams����擾�����l��"forward_"�̏ꍇ�A�󕶎���ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward09() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("event", eventParamValues1);
        String[] eventParamValues2 = { "forward_ABC" };
        params.put("forward_", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "");

        // ���ʊm�F
        assertEquals("", ret);
    }

    /**
     * testDoDetermineForward10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FAC
     * <br><br>
     * ���͒l�F(����) params:[""�F{"forward_success1"}]<br>
     *                ["forward_success2"�F{"ABC"}]<br>
     *         (����) event:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"success1"<br>
     *         
     * <br>
     * eventField���󕶎��ɂ����ꍇ�Aparams�ɃL�[�����݂��A���̒l��"forward_"����n�܂��Ă���΁A"forward_"���������������ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward10() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "forward_success1" };
        params.put("", eventParamValues1);
        String[] eventParamValues2 = { "ABC" };
        params.put("forward_success2", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "");

        // ���ʊm�F
        assertEquals("success1", ret);
    }

    /**
     * testDoDetermineForward11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) params:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"default"<br>
     *         
     * <br>
     * params��null�̏ꍇ�A"default"���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward11() throws Exception {
        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(null, "event");
        
        // ���ʊm�F
        assertEquals("default", ret);
    }
    
    /**
     * testDoDetermineForward12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FAC
     * <br><br>
     * ���͒l�F(����) params:["forward_success.x"�F{"100"}]<br>
     *                ["forward_success.y"�F{"200"}]<br>
     *         (����) event:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"success"<br>
     *         
     * <br>
     * eventField���󕶎��ɂ����ꍇ�Aparams�ɃL�[�����݂��A���̒l��"forward_"����n�܂�A".x"��".y"���I����Ă���΁A"forward_"��".x"��".y"���������������ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoDetermineForward12() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        String[] eventParamValues1 = { "100" };
        params.put("forward_success.x", eventParamValues1);
        String[] eventParamValues2 = { "200" };
        params.put("forward_success.y", eventParamValues2);

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        String ret = action.doDetermineForward(params, "");

        // ���ʊm�F
        assertEquals("success", ret);
    }


    /**
     * testExists01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:{["PARAM_NAME":"PARAM_VALUE"]}<br>
     *         (����) name:"PARAM_NAME"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ��O:�|<br>
     *         
     * <br>
     * params��name���L�[�Ƃ���v�f������ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExists01() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        params.put("PARAM_NAME", "PARAM_VALUE");

        // ���N�G�X�g�p�����[�^��
        String name = "PARAM_NAME";

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        boolean ret = action.exists(params, name);

        // ���ʊm�F
        assertTrue(ret);
    }

    /**
     * testExists02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) params:���Map<br>
     *         (����) name:"PARAM_NAME"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) ��O:�|<br>
     *         
     * <br>
     * params��name���L�[�Ƃ���v�f���Ȃ��ꍇ�Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExists02() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();

        // ���N�G�X�g�p�����[�^��
        String name = "PARAM_NAME";

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        boolean ret = action.exists(params, name);

        // ���ʊm�F
        assertFalse(ret);
    }

    /**
     * testExists03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) params:{["":"PARAM_VALUE"]}<br>
     *         (����) name:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ��O:�|<br>
     *         
     * <br>
     * name���󕶎��̏ꍇ�ł���O�����������ɁA��ʓI�ȕ�����Ɠ��l�̏��������s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExists03() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = new HashMap();
        params.put("", "PARAM_VALUE");

        // ���N�G�X�g�p�����[�^��
        String name = "";

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���s
        boolean ret = action.exists(params, name);

        // ���ʊm�F
        assertTrue(ret);
    }

    /**
     * testExists04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) params:null<br>
     *         (����) name:"PARAM_NAME"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:�|<br>
     *         (��ԕω�) ��O:NullPointerException<br>
     *         
     * <br>
     * params��null�̏ꍇ�ANullPointerException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExists04() throws Exception {
        // ���N�G�X�g�p�����[�^�i�}�b�v�`���j����
        HashMap params = null;

        // ���N�G�X�g�p�����[�^��
        String name = "PARAM_NAME";

        // DispatchAction����
        DispatchAction action = new DispatchAction();

        try {
            // �e�X�g���s
            action.exists(params, name);
            fail();
        } catch (NullPointerException npe) {
            // ���ʊm�F
        }
    }

    /**
     * testCancelled01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:null<br>
     *         
     * <br>
     * null���ԋp����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCancelled01() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();
        
        // ActionFormEx�̍쐬
        DispatchAction_DynaValidatorActionFormExStub01 formEx =
            new DispatchAction_DynaValidatorActionFormExStub01();

        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        
        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���{
        ActionForward af = action.cancelled(mapping, formEx, req, res);
        
        // ����
        assertNull(af);
    }

    /**
     * testCancelled02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:null<br>
     *         
     * <br>
     * null���ԋp����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCancelled02() throws Exception {
        // ActionMapping���쐬
        ActionMappingEx mapping = new ActionMappingEx();
        
        // HTTP���N�G�X�g�̐���
        MockHttpServletRequest req = new MockHttpServletRequest();

        // HTTP���X�|���X�̐���
        MockHttpServletResponse res = new MockHttpServletResponse();

        
        // DispatchAction����
        DispatchAction action = new DispatchAction();

        // �e�X�g���{
        ActionForward af = action.cancelled(mapping, null, req, res);
        
        // ����
        assertNull(af);
    }

}
