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
import junit.framework.TestCase;

import org.apache.struts.action.ActionForward;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.LogoffAction}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * JSP�Ȃǂփt�H���[�h����A�N�V����
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.LogoffAction
 */
public class LogoffActionTest extends TestCase {

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
    public LogoffActionTest(String name) {
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
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) session:null<br>
     *         (���) path:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *         (��ԕω�) res.sendError():�Ăяo���m�F<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�null�ł���A
     * HttpServletResponse.sendError()���Ăяo���ꂽ�ꍇ�Anull��Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute01() throws Exception {
        // �O����
        LogoffAction action = new LogoffAction();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        LogoffAction_HttpServletResponseStub01 res =
            new LogoffAction_HttpServletResponseStub01();
        
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
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) session:null<br>
     *         (���) path:null<br>
     *         (���) res.sendError():IOException����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"error.forward.errorpage"<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�null�ł���A
     * HttpServletResponse.sendError()�ďo�ŗ�O�����������ꍇ�A
     * SystemException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute02() throws Exception {
        // �O����
        LogoffAction action = new LogoffAction();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : null
        String path = null;
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        LogoffAction_HttpServletResponseStub02 res =
            new LogoffAction_HttpServletResponseStub02();
        
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
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) session:null<br>
     *         (���) path:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�not null�̏ꍇ�A
     * ActionForward��path���w�肵��path�ƈ�v���邩�m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute03() throws Exception {
        // �O����
        LogoffAction action = new LogoffAction();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendError�̌Ăяo���m�F�p
        LogoffAction_HttpServletResponseStub01 res =
            new LogoffAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        assertEquals("abc", forward.getPath());
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
     *         (���) session:not null<br>
     *         (���) path:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (��ԕω�) session.invalidate():�Ăяo���m�F<br>
     *         
     * <br>
     * req���擾����session��not null�̏ꍇ�A
     * session.invalidate()���Ăяo���ꂽ���m�F����B
     * ActionForward��path���w�肵��path�ƈ�v���邩�m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute04() throws Exception {
        // �O����
        LogoffAction action = new LogoffAction();
        
        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);
        
        LogoffAction_ActionFormStub01 form =
            new LogoffAction_ActionFormStub01();
        
        // LogoffAction_HttpSessionStub01���擾����X�^�u
        LogoffAction_HttpServletRequestStub01 req =
            new LogoffAction_HttpServletRequestStub01();
        
        // sendError�̌Ăяo���m�F�p
        LogoffAction_HttpServletResponseStub01 res =
            new LogoffAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        LogoffAction_HttpSessionStub01 session =
            (LogoffAction_HttpSessionStub01) req.getSession(false); 
        assertEquals("abc", forward.getPath());
        assertTrue(session.isInvalidate);
    }

}
