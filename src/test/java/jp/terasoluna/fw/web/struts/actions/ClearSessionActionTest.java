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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.action.ActionForward;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ClearSessionAction}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �Z�b�V��������w�肳�ꂽ�v���p�e�B���폜����N���X
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.ClearSessionAction
 */
@SuppressWarnings("unused")
public class ClearSessionActionTest extends TestCase {

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
    public ClearSessionActionTest(String name) {
        super(name);
    }

    /**
     * testSetClearSessionKeys01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) clearSessionKeys:ArrayList�I�u�W�F�N�g<br>
     *         (���) clearSessionKeys:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) clearSessionKeys:�����Ɠ����ArrayList�I�u�W�F�N�g<br>
     *         
     * <br>
     * �����Ɏw�肵���l���t�B�[���hclearSessionKeys�ɐ���Ɋi�[����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetClearSessionKeys01() throws Exception {
        // �O����
        // clearSessionKeys�ݒ�
        List<String> list = new ArrayList<String>();
        list.add("test");
        
        ClearSessionAction action = new ClearSessionAction();
        action.setClearSessionKeys(list);

        // �e�X�g���s�E���ʊm�F
        assertSame(list, UTUtil.getPrivateField(action, "clearSessionKeys"));
    }

    /**
     * testDoExecute01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) clearSessionKeys:null<br>
     *         (���) path:null<br>
     *         (���) res.sendError():IOException����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"error.forward.errorpage"<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�null�ł���A
     * HttpServletResponse.sendError()�ďo�ŗ�O�����������ꍇ�A
     * �w�肵���p�X�ɃZ�b�V�����f�B���N�g�����������ꂽ���m�F����B
     * �܂�SystemException���������邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute01() throws Exception {
        // �O����
        ClearSessionAction action = new ClearSessionAction();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ClearSessionAction_HttpServletResponseStub02 res =
            new ClearSessionAction_HttpServletResponseStub02();
        
        ActionForward forward = null;

        // �e�X�g���{
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("��O���������܂���ł���");
        } catch (SystemException e) {
            // ����
            // IOException�����b�v������
            assertEquals(IOException.class.getName(), e.getCause().getClass()
                    .getName());
            // �G���[���O�m�F
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
    }

    /**
     * testDoExecute02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) clearSessionKeys:0����ArrayList<br>
     *         (���) path:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *         (��ԕω�) res.sendError():�Ăяo���m�F<br>
     *         
     * <br>
     * clearSessionKeys��0����ArrayList�ł���Apath��null�������ꍇ�A
     * res.sendRedirect()���Ăяo�����null��Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute02() throws Exception {
        // �O����
        ClearSessionAction action = new ClearSessionAction();
        
        // clearSessionKeys
        List<String> list = new ArrayList<String>();
        UTUtil.setPrivateField(action, "clearSessionKeys", list);
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        ClearSessionAction_HttpServletResponseStub01 res =
            new ClearSessionAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
        
        // ����
        assertNull(forward);
        assertTrue(res.isSendError);
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
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) clearSessionKeys:�v�f"abc"�������Ă���ArrayList<br>
     *         (���) session.getAttribute():session.getAttribute("abc")��"session01"<br>
     *         (���) path:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (��ԕω�) session.getAttribute():session.getAttribute("abc")��null<br>
     *         
     * <br>
     * clearSessionKeys��1����ArrayList�̏ꍇ�A���̗v�f�̕�����ɊY������
     * session�̑������폜����Ă��邱�ƂƁA��ʑJ�ڂ��s�����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute03() throws Exception {
        // �O����
        ClearSessionAction action = new ClearSessionAction();
        
        // clearSessionKeys
        List<String> list = new ArrayList<String>();
        list.add("abc");
        UTUtil.setPrivateField(action, "clearSessionKeys", list);
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        ClearSessionAction_HttpServletRequestStub01 req =
            new ClearSessionAction_HttpServletRequestStub01();
        
        // session�ݒ�
        ClearSessionAction_HttpSessionStub01 session =
            (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        
        // session�����ݒ�
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("abc", "session01");
        UTUtil.setPrivateField(session, "attrs", attrs);
        
        
        // sendError�̌Ăяo���m�F�p
        ClearSessionAction_HttpServletResponseStub01 res =
            new ClearSessionAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
        
        // ����
        assertEquals("abc", forward.getPath());
        
        session = (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        assertNull(session.getAttribute("abc"));
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
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) clearSessionKeys:�v�f"abc01"�A"abc02"�A"abc03"���L�q����
     *         �����Ă���ArrayList<br>
     *         (���) session.getAttribute():session.getAttribute("abc01")��
     *         "session01"<br>
     *                session.getAttribute("abc02")��"session02"<br>
     *                session.getAttribute("abc03")��"session03"<br>
     *                session.getAttribute("abc04")��"session04"<br>
     *         (���) path:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (��ԕω�) session.getAttribute():session.getAttribute("abc01")��
     *         null<br>
     *                    session.getAttribute("abc02")��null<br>
     *                    session.getAttribute("abc03")��null<br>
     *                    session.getAttribute("abc04")��"session04"<br>
     *         
     * <br>
     * clearSessionKeys��������ArrayList�̏ꍇ�A���̗v�f�̕�����ɊY������
     * session�̑������S���폜����Ă��邱�ƂƁA��ʑJ�ڂ��s�����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute04() throws Exception {
        // �O����
        ClearSessionAction action = new ClearSessionAction();
        
        // clearSessionKeys
        List<String> list = new ArrayList<String>();
        list.add("abc01");
        list.add("abc02");
        list.add("abc03");
        
        UTUtil.setPrivateField(action, "clearSessionKeys", list);
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        ClearSessionAction_ActionFormStub01 form =
            new ClearSessionAction_ActionFormStub01();
        
        ClearSessionAction_HttpServletRequestStub01 req =
            new ClearSessionAction_HttpServletRequestStub01();
        
        // session�ݒ�
        ClearSessionAction_HttpSessionStub01 session =
            (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        
        // session�����ݒ�
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("abc01", "session01");
        attrs.put("abc02", "session02");
        attrs.put("abc03", "session03");
        attrs.put("abc04", "session04");
        UTUtil.setPrivateField(session, "attrs", attrs);
        
        
        // sendError�̌Ăяo���m�F�p
        ClearSessionAction_HttpServletResponseStub01 res =
            new ClearSessionAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);
        
        // ����
        assertEquals("abc", forward.getPath());
        
        session = (ClearSessionAction_HttpSessionStub01) req.getSession(false);
        assertNull(session.getAttribute("abc01"));
        assertNull(session.getAttribute("abc02"));
        assertNull(session.getAttribute("abc03"));
        assertEquals("session04", session.getAttribute("abc04"));
    }
}
