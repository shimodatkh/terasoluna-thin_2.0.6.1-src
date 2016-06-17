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
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.mockrunner.mock.web.MockActionMapping;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ActionEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���O�o�͂�g�����U�N�V�����g�[�N���`�F�b�N���o����A�N�V�������N���X
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.ActionEx
 */
public class ActionExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ActionExTest.class);
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
    public ActionExTest(String name) {
        super(name);
    }

    /**
     * testSetTokenCheck01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) tokenCheck:true<br>
     *         (���) tokenCheck:false<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) tokenCheck:true<br>
     *
     * <br>
     * �����Ɏw�肵���l��ActionEx��tokenCheck�ɐ���Ɋi�[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetTokenCheck01() throws Exception {
        // �O����
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "tokenCheck", false);

        // �e�X�g���{
        action.setTokenCheck(true);

        // ����
        boolean tokenCheck =
            ((Boolean) UTUtil.getPrivateField(action, "tokenCheck")).booleanValue();
        assertTrue(tokenCheck);
    }

    /**
     * testIsTokenCheck01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) tokenCheck:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ActionEx��tokenCheck�������邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsTokenCheck01() throws Exception {
        // �O����
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "tokenCheck", true);

        // �e�X�g���{
        boolean b = action.isTokenCheck();

        // ����
        assertTrue(b);
    }

    /**
     * testSetSaveToken01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) saveToken:false<br>
     *         (���) saveToken:true<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) saveToken:false<br>
     *
     * <br>
     * �����Ɏw�肵���l��ActionEx��saveToken�ɐ���Ɋi�[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSaveToken01() throws Exception {
        // �O����
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "saveToken", false);

        // �e�X�g���{
        action.setSaveToken(true);

        // ����
        boolean saveToken =
            ((Boolean) UTUtil.getPrivateField(action, "saveToken"))
            .booleanValue();
        assertTrue(saveToken);
    }

    /**
     * testIsSaveToken01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) saveToken:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ActionEx��saveToken�������邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsSaveToken01() throws Exception {
        // �O����
        ActionEx action = new ActionEx_ActionExStub01();

        UTUtil.setPrivateField(action, "saveToken", true);

        // �e�X�g���{
        boolean b = action.isSaveToken();

        // ����
        assertTrue(b);
    }

    /**
     * testExecute01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) processTokenCheck():false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionFoward<br>
     *                  (Name�F"txtoken-error")<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �C���t�H���O�F"Transaction token error."<br>
     * <br>
     * processTokenCheck()��false��Ԃ����ꍇ�A"txtoken-error"�ɑJ�ڂ���
     * ActionForward��Ԃ����Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute01() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();

        ActionForm form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = false
        ActionEx action = new ActionEx_ActionExStub01();
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.execute(mapping, form, req, res);

        // ����
        assertEquals("txtoken-error", forward.getName());
        assertTrue(LogUTUtil.checkInfo("Transaction token error."));
    }

    /**
     * testExecute02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) processTokenCheck():true<br>
     *         (���) doExecute():ActionForwardName�F"success"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *
     * <br>
     * processTokenCheck()��true�ł���A����form��null�������ꍇ�A
     * doExecute()�̃��^�[���l��Ԃ����m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute02() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();

        ActionForm form = null;

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub02();
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.execute(mapping, form, req, res);

        // ����
        assertEquals("success", forward.getName());
    }

    /**
     * testExecute03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:Form<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) processTokenCheck():true<br>
     *         (���) doExecute():ActionForwardName�F"success"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *
     * <br>
     * ����form��FormEx�łȂ������ꍇ�AdoExecute()�̃��^�[���l��Ԃ����m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute03() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();

        // ActionForm
        ActionForm form = new DynaValidatorActionForm();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub02();
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.execute(mapping, form, req, res);

        // ����
        assertEquals("success", forward.getName());
    }

    /**
     * testExecute04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:FormEx<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) processTokenCheck():true<br>
     *         (���) doExecute():null<br>
     *         (���) doExecute()���s���<br>
     *                form.isModified():false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *
     * <br>
     * doExecute()�̌��ʂ�null�ł���AdoExecute()���s���form.isModified()��
     * false�������ꍇ�Anull��Ԃ����m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute04() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        // formEx.modified = false
        // doExecute = null
        ActionEx action = new ActionEx_ActionExStub06();
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.execute(mapping, form, req, res);

        // ����
        assertNull(forward);
        assertFalse(form.isModified());
    }

    /**
     * testExecute05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:FormEx<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) processTokenCheck():true<br>
     *         (���) doExecute():Exception<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ���O:Exception<br>
     *
     * <br>
     * doExecute()����O���N�������ꍇ�AException�������邩�m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute05() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub03();

        // �e�X�g���{
        try {
            action.execute(mapping, form, req, res);
            fail();
        } catch (Exception e) {
            // ����
            assertEquals(IOException.class.getName(),
                    e.getCause().getClass().getName());
        }
    }

    /**
     * testExecute06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:FormEx<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) processTokenCheck():true<br>
     *         (���) doExecute():ActionForwardName�F"success"<br>
     *         (���) doExecute()���s���<br>
     *                form.isModified():false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *
     * <br>
     * doExecute()��ActionForward��Ԃ��Aform.isModified()��false�������ꍇ�A
     * doExecute()�̃��^�[���l��Ԃ����m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute06() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx action = new ActionEx_ActionExStub04();

        // form.isModified = false;
        UTUtil.setPrivateField(action, "modified", false);
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.execute(mapping, form, req, res);

        // ����
        assertEquals("success", forward.getName());
        assertNull(req.getAttribute("SKIP_POPULATE"));
    }

    /**
     * testExecute07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:FormEx<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) processTokenCheck():true<br>
     *         (���) doExecute():ActionForwardName�F"success"<br>
     *         (���) doExecute()���s���<br>
     *                form.isModified():true<br>
     *         (���) mapping.getName():"abc"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:mapping�ɐݒ肳��Ă���ActionFoward<br>
     *                  (Name�F"success")<br>
     *         (��ԕω�) req.getAttribute("SKIP_POPULATE"):"abc"<br>
     *
     * <br>
     * doExecute()��ActionForward��Ԃ��Aform.isModified()��true�������ꍇ�A
     * doExecute()�̃��^�[���l��Ԃ����ƁAHttpServletRequest����擾����
     * "SKIP_POPULATE"�ɊY�����鑮����mapping.getName()�ƈ�v���邩�m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute07() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();
        mapping.setName("abc");

        // FormEx
        DynaValidatorActionFormEx form = new DynaValidatorActionFormEx();

        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();

        // action:processTokenCheck = true
        ActionEx_ActionExStub04 action = new ActionEx_ActionExStub04();

        // form.isModified = true;
        UTUtil.setPrivateField(action, "modified", true);
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.execute(mapping, form, req, res);

        // ����
        assertEquals("success", forward.getName());
        assertEquals("abc", req.getAttribute("SKIP_POPULATE"));
    }

    /**
     * testProcessTokenCheck01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionMapping<br>
     *         (����) req:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����mapping��ActionMappingEx�łȂ������ꍇtrue��Ԃ����m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testProcessTokenCheck01() throws Exception {
        // �O����
        MockActionMapping mapping = new MockActionMapping();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx_ActionExStub05 action = new ActionEx_ActionExStub05();

        // �e�X�g���{
        boolean b = action.processTokenCheck(mapping, req);

        // ����
        assertTrue(b);
        assertFalse(action.isSaveToken);
    }

    /**
     * testProcessTokenCheck02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionMappingEx<br>
     *         (����) req:not null<br>
     *         (���) tokenCheck:true<br>
     *         (���) Action.isTokenValid():false<br>
     *         (���) saveToken:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) Action.saveToken():���s<br>
     *
     * <br>
     * �t�B�[���htokenCheck��true�ł���AAction.isTokenValid()��false�AsaveToken��true�������ꍇ�Afalse��Ԃ��ƂƂ���Action.saveToken()���Ăяo���ꂽ���m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testProcessTokenCheck02() throws Exception {
        // �O����
        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx action = new ActionEx_ActionExStub05();
        UTUtil.setPrivateField(action, "tokenCheck", true);
        UTUtil.setPrivateField(action, "tokenValid", false);
        UTUtil.setPrivateField(action, "saveToken", true);

        // �e�X�g���{
        boolean b = action.processTokenCheck(mapping, req);

        // ����
        assertFalse(b);
        boolean isSaveToken =
            ((Boolean) UTUtil.getPrivateField(action, "isSaveToken"))
            .booleanValue();
        assertTrue(isSaveToken);
    }

    /**
     * testProcessTokenCheck03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionMappingEx<br>
     *         (����) req:not null<br>
     *         (���) tokenCheck:true<br>
     *         (���) Action.isTokenValid():true<br>
     *         (���) saveToken:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) Action.saveToken():���s<br>
     *
     * <br>
     * �t�B�[���htokenCheck��true�ł���AAction.isTokenValid()��true�AsaveToken��true�������ꍇ�Atrue��Ԃ��ƂƂ���Action.saveToken()���Ăяo���ꂽ���m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testProcessTokenCheck03() throws Exception {
        // �O����
        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx action = new ActionEx_ActionExStub05();
        UTUtil.setPrivateField(action, "tokenCheck", true);
        UTUtil.setPrivateField(action, "tokenValid", true);
        UTUtil.setPrivateField(action, "saveToken", true);

        // �e�X�g���{
        boolean b = action.processTokenCheck(mapping, req);

        // ����
        assertTrue(b);
        boolean isSaveToken =
            ((Boolean) UTUtil.getPrivateField(action, "isSaveToken"))
            .booleanValue();
        assertTrue(isSaveToken);
    }

    /**
     * testProcessTokenCheck04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapping:ActionMappingEx<br>
     *         (����) req:not null<br>
     *         (���) tokenCheck:false<br>
     *         (���) Action.isTokenValid():true<br>
     *         (���) saveToken:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) Action.saveToken():�����s<br>
     *
     * <br>
     * �t�B�[���htokenCheck��false�ł���AAction.isTokenValid()��true�AsaveToken��false�������ꍇ�Atrue��Ԃ��ƂƂ���Action.saveToken()���Ăяo����Ȃ��������Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testProcessTokenCheck04() throws Exception {
        // �O����
        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest req = new MockHttpServletRequest();

        ActionEx action = new ActionEx_ActionExStub05();
        UTUtil.setPrivateField(action, "tokenCheck", false);
        UTUtil.setPrivateField(action, "tokenValid", true);
        UTUtil.setPrivateField(action, "saveToken", false);

        // �e�X�g���{
        boolean b = action.processTokenCheck(mapping, req);

        // ����
        assertTrue(b);
        boolean isSaveToken =
            ((Boolean) UTUtil.getPrivateField(action, "isSaveToken"))
            .booleanValue();
        assertFalse(isSaveToken);
    }

    /**
     * testAddErrors01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:null<br>
     *
     * <br>
     *
     * <br>
     * ����session��null�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors01() throws Exception {
        // �O����
        HttpSession session = null;
        ActionMessages errors = new ActionMessages();
        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);
    }

    /**
     * testAddErrors02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) errors:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *
     * <br>
     * ����errors��null�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors02() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = null;
        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        assertNull(msgs);
    }

    /**
     * testAddErrors03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) errors:�v�f��0��ActionMessages<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *
     * <br>
     * session��Globals.ERROR_KEY�ɊY�����鑮����������Ȃ��A
     * ������errors�̃T�C�Y��0�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors03() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = new ActionMessages();

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        assertNull(msgs);
    }

    /**
     * testAddErrors04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) errors:�v�f��1��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):�v�f��1��ActionMessages�F<br>
     *                    ([key="key01"�Avalue="value01"])<br>
     *
     * <br>
     * session��Globals.ERROR_KEY�ɊY�����鑮����������Ȃ��A������errors�̃T�C�Y��1�������ꍇ�Asesssion��Globals.ERROR_KEY������errors�̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors04() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        errors.add(Globals.ERROR_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddErrors05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) errors:�v�f��0��ActionMessages<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():�v�f��0��ActionMessages<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):null<br>
     *
     * <br>
     * session��Globals.ERROR_KEY�ɊY�����鑮���̃T�C�Y��0�ł���A
     * ������errors�̃T�C�Y��0�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors05() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages errors = new ActionMessages();
        // 0���̃G���[�ǉ�
        session.setAttribute(Globals.ERROR_KEY, errors);

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        assertNull(msgs);
    }

    /**
     * testAddErrors06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) errors:�v�f��1��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():�v�f��0��ActionMessages<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):�v�f��1��ActionMessages�F<br>
     *                    ([key="key01"�Avalue="value01"])<br>
     *
     * <br>
     * session��Globals.ERROR_KEY�ɊY�����鑮���̃T�C�Y��0�ł���A
     * ������errors�̃T�C�Y��1�������ꍇ�Asesssion��Globals.ERROR_KEY������
     * errors�̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors06() throws Exception {
        // �O����
        // session�ɃZ�b�g����v�f��0��ActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessages serrors = new ActionMessages();
        session.setAttribute(Globals.ERROR_KEY, serrors);

        // �p�����[�^�Ƃ��ēn���v�f��1��ActionMessages
        ActionMessages errors = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        errors.add(Globals.ERROR_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddErrors07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) errors:�v�f��3��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"]�A<br>
     *                [key="key02"�Avalue="value02"]�A<br>
     *                [key="key03"�Avalue="value03"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():�v�f��1��ActionMessages�F<br>
     *                ([key="key04"�Avalue="value04"])<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):�v�f��4��ActionMessages�F<br>
     *                    [key="key04"�Avalue="value04"])<br>
     *                    ([key="key01"�Avalue="value01"]�A<br>
     *                    [key="key02"�Avalue="value02"]�A<br>
     *                    [key="key03"�Avalue="value03"]�A<br>
     *
     * <br>
     * session��Globals.ERROR_KEY�ɊY�����鑮���̃T�C�Y��1�ł���A
     * ������errors�̃T�C�Y��3�������ꍇ�Asesssion��Globals.ERROR_KEY����
     * errors�̗v�f��session�̑����̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors07() throws Exception {
        // �O����
        // session�ɃZ�b�g�����v�f��1��ActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key04", "value04");

        ActionMessages serrors = new ActionMessages();
        serrors.add(Globals.ERROR_KEY, smsg);

        session.setAttribute(Globals.ERROR_KEY, serrors);


        // �p�����[�^�Ƃ��ēn���v�f��3��ActionMessages
        ActionMessages errors = new ActionMessages();

        ActionMessage msg = new ActionMessage("key01", "value01");
        errors.add(Globals.ERROR_KEY, msg);

        msg = new ActionMessage("key02", "value02");
        errors.add(Globals.ERROR_KEY, msg);

        msg = new ActionMessage("key03", "value03");
        errors.add(Globals.ERROR_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);
    }

    /**
     * testAddErrors08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) errors:�v�f��1��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.ERROR_KEY)).size():�v�f��3��ActionMessages�F<br>
     *                ([key="key02"�Avalue="value02"]�A<br>
     *                [key="key03"�Avalue="value03"]�A<br>
     *                [key="key04"�Avalue="value04"])<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.ERROR_KEY):�v�f��4��ActionMessages�F<br>
     *                    ([key="key02"�Avalue="value02"]�A<br>
     *                    [key="key03"�Avalue="value03"]�A<br>
     *                    [key="key04"�Avalue="value04"]�A<br>
     *                    [key="key01"�Avalue="value01"])<br>
     *
     * <br>
     * session��Globals.ERROR_KEY�ɊY�����鑮���̃T�C�Y��3�ł���A
     * ������errors�̃T�C�Y��1�������ꍇ�Asesssion��Globals.ERROR_KEY����
     * errors�̗v�f��session�̑����̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors08() throws Exception {
        // �O����
        // session�ɃZ�b�g�����v�f��3��ActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key02", "value02");
        ActionMessages serrors = new ActionMessages();
        serrors.add(Globals.ERROR_KEY, smsg);

        smsg = new ActionMessage("key03", "value03");
        serrors.add(Globals.ERROR_KEY, smsg);

        smsg = new ActionMessage("key04", "value04");
        serrors.add(Globals.ERROR_KEY, smsg);

        session.setAttribute(Globals.ERROR_KEY, serrors);


        // �p�����[�^�Ƃ��ēn���v�f��1��ActionMessages
        ActionMessages errors = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");
        errors.add(Globals.ERROR_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addErrors(session, errors);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);

        Iterator it = msgs.get(Globals.ERROR_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddMessages01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:null<br>
     *
     * <br>
     *
     * <br>
     * ����session��null�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages01() throws Exception {
        // �O����
        HttpSession session = null;
        ActionMessages errors = new ActionMessages();
        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, errors);
    }

    /**
     * testAddMessages02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) messages:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):null<br>
     *
     * <br>
     * ����messages��null�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages02() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = null;
        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, messages);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        assertNull(msgs);
    }

    /**
     * testAddMessages03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) messages:�v�f��0��ActionMessages<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):null<br>
     *
     * <br>
     * session��Globals.MESSAGE_KEY�ɊY�����鑮����������Ȃ��A
     * ������messages�̃T�C�Y��0�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages03() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = new ActionMessages();

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, messages);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        assertNull(msgs);
    }

    /**
     * testAddMessages04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) messages:�v�f��1��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):�v�f��1��ActionMessages�F<br>
     *                    ([key="key01"�Avalue="value01"])<br>
     *
     * <br>
     * session��Globals.MESSAGE_KEY�ɊY�����鑮����������Ȃ��A
     * ������messages�̃T�C�Y��1�������ꍇ�Asesssion��Globals.MESSAGE_KEY������
     * messages�̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages04() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        messages.add(Globals.MESSAGE_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, messages);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddMessages05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) messages:�v�f��0��ActionMessages<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():�v�f��0��ActionMessages<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):null<br>
     *
     * <br>
     * session��Globals.MESSAGE_KEY�ɊY�����鑮���̃T�C�Y��0�ł���A
     * ������messages�̃T�C�Y��0�������ꍇ�A�����𒆒f���邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages05() throws Exception {
        // �O����
        MockHttpSession session = new MockHttpSession();
        ActionMessages messages = new ActionMessages();
        session.setAttribute(Globals.MESSAGE_KEY, messages);

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, messages);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        assertNull(msgs);
    }

    /**
     * testAddMessages06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) messages:�v�f��1��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():�v�f��0��ActionMessages<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):�v�f��1��ActionMessages�F<br>
     *                    ([key="key01"�Avalue="value01"])<br>
     *
     * <br>
     * session��Globals.MESSAGE_KEY�ɊY�����鑮���̃T�C�Y��0�ł���A
     * ������messages�̃T�C�Y��1�������ꍇ�Asesssion��Globals.MESSAGE_KEY������
     * messages�̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages06() throws Exception {
        // �O����
        // session�ɃZ�b�g����v�f��0��ActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessages smessages = new ActionMessages();
        session.setAttribute(Globals.MESSAGE_KEY, smessages);

        // �p�����[�^�Ƃ��ēn���v�f��1��ActionMessages
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");

        messages.add(Globals.MESSAGE_KEY, msg);

        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, messages);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }

    /**
     * testAddMessages07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) messages:�v�f��3��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"]�A<br>
     *                [key="key02"�Avalue="value02"]�A<br>
     *                [key="key03"�Avalue="value03"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():�v�f��1��ActionMessages�F<br>
     *                ([key="key04"�Avalue="value04"])<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):�v�f��4��ActionMessages�F<br>
     *                    ([key="key01"�Avalue="value01"]�A<br>
     *                    [key="key02"�Avalue="value02"]�A<br>
     *                    [key="key03"�Avalue="value03"]�A<br>
     *                    [key="key04"�Avalue="value04"])<br>
     *
     * <br>
     * session��Globals.MESSAGE_KEY�ɊY�����鑮���̃T�C�Y��1�ł���A
     * ������messages�̃T�C�Y��3�������ꍇ�Asesssion��Globals.MESSAGE_KEY����
     * messages�̗v�f��session�̑����̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages07() throws Exception {
        // �O����
        // session�ɃZ�b�g�����v�f��1��ActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key04", "value04");

        ActionMessages smessages = new ActionMessages();
        smessages.add(Globals.MESSAGE_KEY, smsg);

        session.setAttribute(Globals.MESSAGE_KEY, smessages);


        // �p�����[�^�Ƃ��ēn���v�f��3��ActionMessages
        ActionMessages messages = new ActionMessages();

        ActionMessage msg = new ActionMessage("key01", "value01");
        messages.add(Globals.MESSAGE_KEY, msg);

        msg = new ActionMessage("key02", "value02");
        messages.add(Globals.MESSAGE_KEY, msg);

        msg = new ActionMessage("key03", "value03");
        messages.add(Globals.MESSAGE_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, messages);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);
    }

    /**
     * testAddMessages08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *         (����) messages:�v�f��1��ActionMessages�F<br>
     *                ([key="key01"�Avalue="value01"])<br>
     *         (���) (session#getAttribute(<br>
     *                Globals.MESSAGE_KEY)).size():�v�f��3��ActionMessages�F<br>
     *                ([key="key02"�Avalue="value02"]�A<br>
     *                [key="key03"�Avalue="value03"]�A<br>
     *                [key="key04"�Avalue="value04"])<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) sessioin#getAttribute(<br>
     *                    Globals.MESSAGE_KEY):�v�f��4��ActionMessages�F<br>
     *                    ([key="key02"�Avalue="value02"]�A<br>
     *                    [key="key03"�Avalue="value03"]�A<br>
     *                    [key="key04"�Avalue="value04"]�A<br>
     *                    [key="key01"�Avalue="value01"])<br>
     *
     * <br>
     * session��Globals.MESSAGE_KEY�ɊY�����鑮���̃T�C�Y��3�ł���A
     * ������messages�̃T�C�Y��1�������ꍇ�Asesssion��Globals.MESSAGE_KEY����
     * messages�̗v�f��session�̑����̗v�f�������Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddMessages08() throws Exception {
        // �O����
        // session�ɃZ�b�g�����v�f��3��ActionMessages
        MockHttpSession session = new MockHttpSession();
        ActionMessage smsg = new ActionMessage("key02", "value02");
        ActionMessages smessages = new ActionMessages();
        smessages.add(Globals.MESSAGE_KEY, smsg);

        smsg = new ActionMessage("key03", "value03");
        smessages.add(Globals.MESSAGE_KEY, smsg);

        smsg = new ActionMessage("key04", "value04");
        smessages.add(Globals.MESSAGE_KEY, smsg);

        session.setAttribute(Globals.MESSAGE_KEY, smessages);


        // �p�����[�^�Ƃ��ēn���v�f��1��ActionMessages
        ActionMessages messages = new ActionMessages();
        ActionMessage msg = new ActionMessage("key01", "value01");
        messages.add(Globals.MESSAGE_KEY, msg);


        ActionEx action = new ActionEx_ActionExStub01();

        // �e�X�g���{
        action.addMessages(session, messages);

        // ����
        ActionMessages msgs =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);

        Iterator it = msgs.get(Globals.MESSAGE_KEY);
        ActionMessage message = (ActionMessage) it.next();
        String key = message.getKey();
        String value = (String) message.getValues()[0];
        assertEquals("key02", key);
        assertEquals("value02", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key03", key);
        assertEquals("value03", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key04", key);
        assertEquals("value04", value);

        message = (ActionMessage) it.next();
        key = message.getKey();
        value = (String) message.getValues()[0];
        assertEquals("key01", key);
        assertEquals("value01", value);
    }
}
