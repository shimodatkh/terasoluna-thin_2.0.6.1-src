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

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.FileUtil;
import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.MakeSessionDirectoryAction}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �Z�b�V�����f�B���N�g�����쐬����A�N�V�����B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.MakeSessionDirectoryAction
 */
public class MakeSessionDirectoryActionTest extends PropertyTestCase {

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        Thread.sleep(100);

        addProperty("session.dir.base", "c:/sessions");

        // �t�@�C���폜
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        // �t�@�C���폜
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public MakeSessionDirectoryActionTest(String name) {
        super(name);
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
     *         (���) session:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"error.session.not.found"<br>
     *                    ���b�v������O�Fnull<br>
     *         (��ԕω�) �����O��<br>
     *                    �G���[���O�F"HttpSession is not available."<br>
     *
     * <br>
     * HttpSession��null�������ꍇSystemException���������邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute01() throws Exception {
        // �O����
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();

        ActionForm form = new DynaValidatorActionFormEx();

        // session:null
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setSession(null);

        // sendError�̌Ăяo���m�F�p
        MockHttpServletResponse res = new MockHttpServletResponse();

        // �e�X�g���{
        try {
            action.doExecute(mapping, form, req, res);
            fail("��O���������܂���ł���");
        } catch (SystemException e) {
            // ����
            // �G���[���O�m�F
            assertTrue(LogUTUtil.checkError("HttpSession is not available."));
            assertEquals("error.session.not.found", e.getErrorCode());
        }
    }

    /**
     * testDoExecute02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) form:not null<br>
     *         (����) req:not null<br>
     *         (����) res:not null<br>
     *         (���) session:not null<br>
     *         (���) sessionId:"dummyID"<br>
     *         (���) path:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *         (��ԕω�) directory:
     *         "c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")
     *         �f�B���N�g�������݂��邱�Ƃ��m�F<br>
     *         (��ԕω�) res.sendError():�Ăяo��<br>
     *
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�null�ł���A
     * HttpServletResponse.sendError()���Ăяo���ꂽ�ꍇ�Anull��Ԃ����Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute02() throws Exception {
        // �O����
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        // path : null
        String path = null;
        mapping.setParameter(path);

        ActionForm form = new DynaValidatorActionFormEx();

        // sessionId:"dummyID"
        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendError�̌Ăяo���m�F�p
        MockHttpServletResponse res = new MockHttpServletResponse();

        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        assertNull(forward);
        File file = new File(PropertyUtil.getProperty("session.dir.base") + "/"
                + FileUtil.getSessionDirectoryName(req.getSession().getId()));
        assertTrue(file.exists());
        assertEquals(HttpServletResponse.SC_NOT_FOUND, res.getStatus());
    }

    /**
     * testDoExecute03()
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
     *         (���) session:not null<br>
     *         (���) sessionId:"dummyID"<br>
     *         (���) path:null<br>
     *         (���) �v���p�e�B:session.dir.base=c:/sessions<br>
     *         (���) res.sendError():IOException����<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) directory:
     *         "c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")
     *         �f�B���N�g�������݂��邱�Ƃ��m�F<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"error.forward.errorpage"<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) �����O��<br>
     *                    �G���[���O�F"Error page(404) forwarding failed."<br>
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
    public void testDoExecute03() throws Exception {
        // �O����
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        // path : null
        String path = null;
        mapping.setParameter(path);

        ActionForm form = new DynaValidatorActionFormEx();

        // sessionId:"dummyID"
        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendError:IOException
        MakeSessionDirectoryAction_HttpServletResponseStub01 res =
            new MakeSessionDirectoryAction_HttpServletResponseStub01();

        // �e�X�g���{
        try {
            action.doExecute(mapping, form, req, res);
            fail("��O���������܂���ł���");
        } catch (SystemException e) {
            // ����
            // �G���[���O�m�F
            assertTrue(LogUTUtil.checkError("Error page(404) forwarding failed."));
            // IOException�����b�v������
            assertEquals(IOException.class.getName(),
                    e.getCause().getClass().getName());
            File file = FileUtil.getSessionDirectory(req.getSession().getId());
            assertTrue(file.exists());
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
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
     *         (���) sessionId:"dummyID"<br>
     *         (���) path:"abc"<br>
     *         (���) �v���p�e�B:session.dir.base=c:/sessions<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (��ԕω�) directory:
     *         "c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")
     *         �f�B���N�g�������݂��邱�Ƃ��m�F<br>
     *
     * <br>
     * mapping����p�����[�^�̎擾���ʂ�not null�̏ꍇ�A
     * �w�肵���p�X�ɃZ�b�V�����f�B���N�g�����������ꂽ���m�F����B
     * �܂�ActionForward��path���w�肵��path�ƈ�v���邩�m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute04() throws Exception {
        // �O����
        MakeSessionDirectoryAction action = new MakeSessionDirectoryAction();

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();
        // path : "abc"
        String path = "abc";
        mapping.setParameter(path);

        ActionForm form = new DynaValidatorActionFormEx();

        // sessionId:"dummyID"
        MockHttpServletRequest req = new MockHttpServletRequest();

        HttpServletResponse res = new MockHttpServletResponse();

        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        File file = new File(PropertyUtil.getProperty("session.dir.base") +"/"
                + FileUtil.getSessionDirectoryName(req.getSession().getId()));
        assertTrue(file.exists());
        assertEquals("abc", forward.getPath());
    }
}
