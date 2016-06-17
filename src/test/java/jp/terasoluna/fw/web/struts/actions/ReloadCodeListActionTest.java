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
import jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.action.ActionForward;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �T�[�u���b�g�R���e�L�X�g���ɃL���b�V�����ꂽ�R�[�h���X�g�����ׂă����[�h����B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction
 */
@SuppressWarnings("unused")
public class ReloadCodeListActionTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ReloadCodeListActionTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LogUTUtil.flush();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGetCodeListLoader01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeListLoader:ReloadCodeListAction�I�u�W�F�N�g<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ReloadableCodeListLoader:�O������Ɠ����
     * ReloadCodeListAction�I�u�W�F�N�g<br>
     *
     * <br>
     * ReloadCodeListAction�Ɋi�[����Ă���codeListLoader�𐳏�Ɏ擾���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeListLoader01() throws Exception {
        // �O����
        // �����ݒ�
        ReloadableCodeListLoader loader = new ReloadableCodeListLoaderImpl01();

        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // �e�X�g���{
        // ����
        assertSame(loader, action.getCodeListLoader());
    }

    /**
     * testSetCodeListLoader01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) codeListLoader:ReloadableCodeListLoader�I�u�W�F�N�g<br>
     *         (���) codeListLoader:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeListLoader:�����Ɠ����
     * ReloadableCodeListLoader�I�u�W�F�N�g<br>
     *
     * <br>
     * �����Ɏw�肵���l��codeListLoader�ɐ���Ɋi�[����邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCodeListLoader01() throws Exception {
        // �O����
        // �����ݒ�
        ReloadableCodeListLoader loader = new ReloadableCodeListLoaderImpl01();

        ReloadCodeListAction action = new ReloadCodeListAction();

        // �e�X�g���{
        action.setCodeListLoader(loader);

        // ����
        assertSame(loader, UTUtil.getPrivateField(action, "codeListLoader"));
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
     *         (���) codeListLoader:null<br>
     *         (���) path:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:null<br>
     *         (��ԕω�) res.sendError():�ďo<br>
     *
     * <br>
     * codeListLoader��null��<br>
     * mapping����p�����[�^�̎擾���ʂ�null�̏ꍇ�A
     * HttpServletResponse.sendError()�ďo���ꂽ��null��Ԃ����Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute01() throws Exception {
        // �O����
        ReloadableCodeListLoader loader = null;
        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();

        // path : null
        String path = null;
        mapping.setParameter(path);

        ReloadCodeList_ActionFormStub01 form =
            new ReloadCodeList_ActionFormStub01();

        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendError�̌Ăяo���m�F�p
        ReloadCodeListAction_HttpServletResponseStub01 res =
            new ReloadCodeListAction_HttpServletResponseStub01();

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
     *         (���) codeListLoader:null<br>
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
     * codeListLoader��null��<br>
     * mapping����p�����[�^�̎擾���ʂ�null�̏ꍇ
     * HttpServletResponse.sendError()�ďo�ŗ�O�����������ꍇ�A
     * �w�肵���p�X�ɃZ�b�V�����f�B���N�g�����������ꂽ���m�F����B
     * �܂�SystemException���������邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute02() throws Exception {
        // �O����
        ReloadableCodeListLoader loader = null;
        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();

        // path : null
        String path = null;
        mapping.setParameter(path);

        ReloadCodeList_ActionFormStub01 form =
            new ReloadCodeList_ActionFormStub01();

        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendError�̌Ăяo���m�F�p
        ReloadCodeListAction_HttpServletResponseStub02 res =
            new ReloadCodeListAction_HttpServletResponseStub02();

        ActionForward forward = null;

        // �e�X�g���{
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail();
        } catch (SystemException e) {
            // ����
            // IOException�����b�v������
            assertTrue(e.getCause() instanceof IOException);
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
     *         (���) codeListLoader:ReloadableCodeListLoader�C���X�^���X<br>
     *         (���) path:"abc"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) actionForward:ActionForward.getPath()�F"abc"<br>
     *         (��ԕω�) codeListLoader.reload():�Ăяo���m�F<br>
     *
     * <br>
     * codeListLoader��ReloadableCodeListLoader�̃C���X�^���X��mapping����
     * �p�����[�^�̎擾���ʂ�not null�̏ꍇ�AcodeListLoader.reload()��
     * �Ăяo���ꂽ���ƂƁA��ʑJ�ڏ���������邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecute03() throws Exception {
        // �O����
        // �����ݒ�
        ReloadableCodeListLoaderImpl01 loader =
            new ReloadableCodeListLoaderImpl01();

        ReloadCodeListAction action = new ReloadCodeListAction();
        UTUtil.setPrivateField(action, "codeListLoader", loader);

        // �p�����[�^�̐ݒ�
        ActionMappingEx mapping = new ActionMappingEx();

        // path : null
        String path = "abc";
        mapping.setParameter(path);

        ReloadCodeList_ActionFormStub01 form =
            new ReloadCodeList_ActionFormStub01();

        MockHttpServletRequest req = new MockHttpServletRequest();

        // sendError�̌Ăяo���m�F�p
        MockHttpServletResponse res = new MockHttpServletResponse();

        ActionForward forward = null;

        // �e�X�g���{
        forward = action.doExecute(mapping, form, req, res);

        // ����
        assertEquals("abc", forward.getPath());
        ReloadableCodeListLoaderImpl01 result = (ReloadableCodeListLoaderImpl01)
        UTUtil.getPrivateField(action, "codeListLoader");
        assertTrue(result.isReload);
    }
}
