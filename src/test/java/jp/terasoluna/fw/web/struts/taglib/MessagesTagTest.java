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

package jp.terasoluna.fw.web.struts.taglib;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

/**
 * MessagesTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class MessagesTagTest extends TestCase {

    /**
     * �e�X�g�Ώ�
     */
    private MessagesTag tag = null;

    /**
     * �e�X�g�P�[�X�J�n�O�����B
     * �e�X�g�Ώۃ^�O�A�t�H�[���C���X�^���X�̐������s�Ȃ��B
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // �e�X�g�Ώۃ^�O�̐���
        tag = (MessagesTag) TagUTUtil.create(MessagesTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoStartTag01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l<br>
     * ���N�G�X�g�p�����[�^=messageKey�����݂��Ȃ��B
     *                      errorKey�����݂���B
     * <br>
     * ���Ғl:
     * �߂�l:SKIP_BODY<br>
     * <br>
     * ���N�G�X�g�p�����[�^��messageKey�����݂��Ȃ��ꍇ�A
     * SKIP_BODY���ԋp����A�I�����邱�ƁB
     */
    public void testDoStartTag01() throws Exception {
        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ���N�G�X�g�p�����[�^�ݒ�i�G���[�̂݁j
        req.setupAddParameter(MessagesPopupTag.POPUP_ERROR_KEY, "errorKey");

        // �e�X�g���s�E���ʊm�F
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
    }

    /**
     * testDoStartTag02�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l<br>
     * ���N�G�X�g�p�����[�^=errorKey�����݂���B
     *                      messageKey�����݂���B
     * <br>
     * ���Ғl:
     * �߂�l:SKIP_BODY<br>
     * <br>
     * ���N�G�X�g�p�����[�^��messageKey�����݂���Ƃ��A
     * �Z�b�V������̃��b�Z�[�W��񂪃��N�G�X�g�����ɃR�s�[����A
     * �Z�b�V��������͍폜����邱�Ƃ��m�F����B
     */
    public void testDoStartTag02() throws Exception {
        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // ���b�Z�[�W���\�[�X��`�i�e�X�g�ʉ߂̂��ߕK�v�j
        MessageResources resources = new PropertyMessageResources(
        new PropertyMessageResourcesFactory(), "");
        pc.setAttribute(
            Globals.MESSAGES_KEY,
            resources,
            PageContext.REQUEST_SCOPE);

        // ���N�G�X�g�p�����[�^�ݒ�
        req.setupAddParameter(MessagesPopupTag.POPUP_ERROR_KEY, "errorKey");
        req.setupAddParameter(MessagesPopupTag.POPUP_MESSAGE_KEY, "messageKey");
        MockHttpSession session = (MockHttpSession) pc.getSession();

        ActionMessages messages = new ActionMessages();
        ActionMessage message1 = new ActionMessage("messages.message1");
        ActionMessage message2 = new ActionMessage("messages.message2");
        messages.add(Globals.MESSAGE_KEY, message1);
        messages.add(Globals.MESSAGE_KEY, message2);
        // ���b�Z�[�W�����Z�b�V���������Ƃ��Đݒ�
        session.setAttribute("messageKey", messages);

        ActionMessages errors = new ActionMessages();
        ActionMessage error1 = new ActionMessage("errors.error1");
        ActionMessage error2 = new ActionMessage("errors.error2");
        errors.add(Globals.ERROR_KEY, error1);
        errors.add(Globals.ERROR_KEY, error2);
        // �G���[�����Z�b�V���������Ƃ��Đݒ�
        session.setAttribute("errorKey", errors);

        // �e�X�g���s
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        // ���N�G�X�g������Globals.MESSAGE_KEY���L�[�Ƃ���
        // ���b�Z�[�W����񂪐ݒ肳��Ă��邱�ƁB
        assertSame(messages, req.getAttribute(Globals.MESSAGE_KEY));
        // �G���[���̓��N�G�X�g�Ɋi�[����Ă��Ȃ����ƁB
        assertNull(req.getAttribute(Globals.ERROR_KEY));
        // �Z�b�V�������烁�b�Z�[�W��񂪍폜����Ă��邱�ƁB
        assertNull(session.getAttribute("messageKey"));
        // �Z�b�V��������G���[��񂪍폜����Ă��Ȃ����ƁB
        assertNotNull(session.getAttribute("errorKey"));
    }

}
