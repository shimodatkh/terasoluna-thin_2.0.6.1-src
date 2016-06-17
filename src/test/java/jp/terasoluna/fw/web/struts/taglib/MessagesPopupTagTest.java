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

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * MessagesPopupTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class MessagesPopupTagTest extends PropertyTestCase {

    //�e�X�g�Ώ�
    MessagesPopupTag tag = null;

    /**
     * Constructor for MessagesPopupTagTest.
     * @param arg0
     */
    public MessagesPopupTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUpData() throws Exception {
        Map<String, String> overProps = new TreeMap<String, String>();
        overProps.put("messages.popup.param.paramType", "religion");
        addPropertyAll(overProps);
        tag = (MessagesPopupTag) TagUTUtil.create(MessagesPopupTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * testDoStartTag01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * ���N�G�X�g��̃G���[���Fnull
     * ���N�G�X�g��̃��b�Z�[�W���Fnull
     * 
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * ���N�G�X�g���ON_LOAD_KEY=null
     * 
     * ���N�G�X�g�ɃG���[�E���b�Z�[�W��񂪋��ɑ��݂��Ȃ����A
     * ���N�G�X�g�Ƀ|�b�v�A�b�v�X�N���v�g���o�^����Ă��Ȃ�
     * ���ƂƁA���N�G�X�g��ɃX�N���v�g���o�^����Ă��Ȃ����Ƃ�
     * �m�F����B
     */
    public void testDoStartTag01() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // ���N�G�X�g��ɃX�N���v�g���o�^����Ă��Ȃ����ƁB
        assertNull(req.getAttribute(MessagesPopupTag.ON_LOAD_KEY));
        // �Z�b�V������ɁA�G���[�E���b�Z�[�W��񂪓o�^����Ă��Ȃ����ƁB
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        assertFalse(enumeration.hasMoreElements());
    }

    /**
     * testDoStartTag02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * ���N�G�X�g��̃G���[���F�G���[����
     * ���N�G�X�g��̃��b�Z�[�W���Fnull
     * 
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * ���N�G�X�g���ON_LOAD_KEY=null
     * 
     * ���N�G�X�g�ɃG���[��񂪋�œo�^����Ă���Ƃ�
     * �Z�b�V������ɃG���[��񂪓o�^���ꂸ�A���N�G�X�g
     * ��ɃX�N���v�g���o�^����Ă��Ȃ����Ƃ��m�F����B
     */
    public void testDoStartTag02() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // �e�X�g�f�[�^�ݒ�
        // �G���[���͋�
        ActionMessages messages = new ActionMessages();
        req.setAttribute(Globals.ERROR_KEY, messages);
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // ���N�G�X�g��ɃX�N���v�g���o�^����Ă��Ȃ����ƁB
        assertNull(req.getAttribute(MessagesPopupTag.ON_LOAD_KEY));
        // �Z�b�V������ɁA�G���[�E���b�Z�[�W��񂪓o�^����Ă��Ȃ����ƁB
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        assertFalse(enumeration.hasMoreElements());
    }

    /**
     * testDoStartTag03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * ���N�G�X�g��̃G���[���F�G���[3��
     * ���N�G�X�g��̃��b�Z�[�W���Fnull
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * ���N�G�X�g���ON_LOAD_KEY=
     *   window.open("contextPath/popup.do?popup_error_key=XXXXXXX",
     *       "popup",
     *       "");
     * 
     * ���N�G�X�g�ɃG���[���3���o�^����Ă���Ƃ�
     * ����̃G���[��񂪃Z�b�V�����Ɋi�[����A���N�G�X�g��
     * ��ɃX�N���v�g���o�^����邱�Ƃ��m�F����B
     */
    public void testDoStartTag03() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // �e�X�g�f�[�^�ݒ�
        // �G���[���3��
        ActionMessages errors = new ActionMessages();
        ActionMessage error1 = new ActionMessage("errors.error1");
        ActionMessage error2 = new ActionMessage("errors.error2");
        ActionMessage error3 = new ActionMessage("errors.error3");
        errors.add(Globals.ERROR_KEY, error1);
        errors.add(Globals.ERROR_KEY, error2);
        errors.add(Globals.ERROR_KEY, error3);
        req.setAttribute(Globals.ERROR_KEY, errors);
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // ���N�G�X�g��ɃX�N���v�g���o�^����Ă��邱�ƁB
        // �X�N���v�g�̃����_���L�[�����ȊO����v���Ă��邱�ƁB
        String script = (String) req.getAttribute(MessagesPopupTag.ON_LOAD_KEY);
        assertTrue(
            script.startsWith(
                "  window.open(\"contextPath/popup.do?popup_error_key="));
        assertTrue(
            script.endsWith(
                "\", \"popup\", \"\");"
                    + System.getProperty("line.separator")));

        // �Z�b�V������ɁA�G���[��񂪓o�^����Ă��邱�ƁB
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        int cnt = 0;
        while (enumeration.hasMoreElements()) {
            Object obj = session.getAttribute((String) enumeration
                    .nextElement());
            assertEquals(ActionMessages.class.getName(), obj.getClass()
                    .getName());
            assertEquals(3, ((ActionMessages) obj).size(Globals.ERROR_KEY));
            cnt++;
        }
        assertEquals(1, cnt);
    }

    /**
     * testDoStartTag04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * ���N�G�X�g��̃G���[���Fnull
     * ���N�G�X�g��̃��b�Z�[�W���F���b�Z�[�W��񂪋�
     * 
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * ���N�G�X�g���ON_LOAD_KEY=null
     * 
     * ���N�G�X�g�Ƀ��b�Z�[�W��񂪋�œo�^����Ă���Ƃ�
     * �Z�b�V������Ƀ��b�Z�[�W��񂪓o�^���ꂸ�A���N�G�X�g
     * ��ɃX�N���v�g���o�^����Ă��Ȃ����Ƃ��m�F����B
     */
    public void testDoStartTag04() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // �e�X�g�f�[�^�ݒ�
        // �G���[���͋�
        ActionMessages messages = new ActionMessages();
        req.setAttribute(Globals.MESSAGE_KEY, messages);
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // ���N�G�X�g��ɃX�N���v�g���o�^����Ă��Ȃ����ƁB
        assertNull(req.getAttribute(MessagesPopupTag.ON_LOAD_KEY));
        // �Z�b�V������ɁA�G���[�E���b�Z�[�W��񂪓o�^����Ă��Ȃ����ƁB
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        assertFalse(enumeration.hasMoreElements());
    }

    /**
     * testDoStartTag05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * ���N�G�X�g��̃G���[���Fnull
     * ���N�G�X�g��̃��b�Z�[�W���F3��
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * ���N�G�X�g���ON_LOAD_KEY=
     *   window.open("contextPath/popup.do?popup_message_key=XXXXXXX",
     *       "popup",
     *       "");
     * 
     * ���N�G�X�g�Ƀ��b�Z�[�W���3���o�^����Ă���Ƃ�
     * ����̃��b�Z�[�W��񂪃Z�b�V�����Ɋi�[����A���N�G�X�g��
     * ��ɃX�N���v�g���o�^����邱�Ƃ��m�F����B
     */
    public void testDoStartTag05() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // �e�X�g�f�[�^�ݒ�
        // ���b�Z�[�W���3��
        ActionMessages messages = new ActionMessages();
        ActionMessage message1 = new ActionMessage("messages.message1");
        ActionMessage message2 = new ActionMessage("messages.message2");
        ActionMessage message3 = new ActionMessage("messages.message3");
        messages.add(Globals.MESSAGE_KEY, message1);
        messages.add(Globals.MESSAGE_KEY, message2);
        messages.add(Globals.MESSAGE_KEY, message3);
        req.setAttribute(Globals.MESSAGE_KEY, messages);
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // ���N�G�X�g��ɃX�N���v�g���o�^����Ă��邱�ƁB
        // �X�N���v�g�̃����_���L�[�����ȊO����v���Ă��邱�ƁB
        String script = (String) req.getAttribute(MessagesPopupTag.ON_LOAD_KEY);
        assertTrue(
            script.startsWith(
                "  window.open(\"contextPath/popup.do?popup_message_key="));
        assertTrue(
            script.endsWith(
                "\", \"popup\", \"\");"
                    + System.getProperty("line.separator")));

        // �Z�b�V������ɁA���b�Z�[�W��񂪓o�^����Ă��邱�ƁB
        HttpSession session = req.getSession(true);
        Enumeration enumeration = session.getAttributeNames();
        int cnt = 0;
        while (enumeration.hasMoreElements()) {
            Object obj = session.getAttribute((String) enumeration
                    .nextElement());
            assertEquals(ActionMessages.class.getName(), obj.getClass()
                    .getName());
            assertEquals(3, ((ActionMessages) obj).size(Globals.MESSAGE_KEY));
            cnt++;
        }
        assertEquals(1, cnt);
    }

    /**
     * testDoStartTag06�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l ���N�G�X�g��̃G���[���F2�� ���N�G�X�g��̃��b�Z�[�W���F3��
     * 
     * ���Ғl �߂�l:int=EVAL_BODY_INCLUDE<br>
     * ���N�G�X�g���ON_LOAD_KEY=
     * window.open("contextPath/popup.do?popup_error_key=XXXXXXX&popup_message_key=YYYYYY",
     * "popup", "");
     * 
     * ���N�G�X�g�ɃG���[���2���A���b�Z�[�W���3���o�^ ����Ă���Ƃ�����̃G���[�E���b�Z�[�W��񂪃Z�b�V������
     * �i�[����A���N�G�X�g��ɃX�N���v�g���o�^����邱�Ƃ��m�F����B
     */
    public void testDoStartTag06() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // �e�X�g�f�[�^�ݒ�
        // �G���[���2��
        ActionMessages errors = new ActionMessages();
        ActionMessage error1 = new ActionMessage("errors.error1");
        ActionMessage error2 = new ActionMessage("errors.error2");
        errors.add(Globals.ERROR_KEY, error1);
        errors.add(Globals.ERROR_KEY, error2);
        req.setAttribute(Globals.ERROR_KEY, errors);

        // ���b�Z�[�W���3��
        ActionMessages messages = new ActionMessages();
        ActionMessage message1 = new ActionMessage("messages.message1");
        ActionMessage message2 = new ActionMessage("messages.message2");
        ActionMessage message3 = new ActionMessage("messages.message3");
        messages.add(Globals.MESSAGE_KEY, message1);
        messages.add(Globals.MESSAGE_KEY, message2);
        messages.add(Globals.MESSAGE_KEY, message3);
        req.setAttribute(Globals.MESSAGE_KEY, messages);
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        // ���N�G�X�g��ɃX�N���v�g���o�^����Ă��邱�ƁB
        // �X�N���v�g�̃����_���L�[�����ȊO����v���Ă��邱�ƁB
        String script = (String) req.getAttribute(MessagesPopupTag.ON_LOAD_KEY);
        assertTrue(
            script.startsWith(
                "  window.open(\"contextPath/popup.do?popup_error_key="));
        assertTrue(
            script.indexOf(
                "&popup_message_key=",
                "  window.open(\"contextPath/popup.do?popup_error_key="
                    .length())
                != -1);
        assertTrue(
            script.endsWith(
                "\", \"popup\", \"\");"
                    + System.getProperty("line.separator")));

        // �Z�b�V������ɁA���b�Z�[�W��񂪓o�^����Ă��邱�ƁB
        HttpSession session = req.getSession(true);
        Enumeration enu = session.getAttributeNames();
        // �G���[�E���b�Z�[�W���̃I�u�W�F�N�g���݌���
        int errorCount = 0;
        int messageCount = 0;
        int cnt = 0;
        while (enu.hasMoreElements()) {
            Object obj = session.getAttribute((String) enu.nextElement());
            assertEquals(ActionMessages.class.getName(), obj.getClass()
                    .getName());
            if (((ActionMessages) obj).size(Globals.ERROR_KEY) == 2) {
                // �G���[��2������
                errorCount++;
            }
            if (((ActionMessages) obj).size(Globals.MESSAGE_KEY) == 3) {
                // ���b�Z�[�W��3������
                messageCount++;
            }
            cnt++;
        }
        // ���킹��2���ł��邱�ƁB
        assertEquals(2, cnt);
        // �Z�b�V������̃G���[�E���b�Z�[�W����
        // ���݌�����1�����ł��邱�ƁB
        assertEquals(1, errorCount);
        assertEquals(1, messageCount);
    }

    /**
     * testGetOnLoadScript01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey=null
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:�X�N���v�g������null<br>
     * 
     * �Z�b�V�����Ɋi�[�����G���[�L�[�A���b�Z�[�W�L�[������
     * null�ł���Ƃ��A�X�N���v�g�������null�ŕԋp����邱�ƁB
     */
    public void testGetOnLoadScript01() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");
        
        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, null, null };

        // �e�X�g���s
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertNull(retObj);

    }

    /**
     * testGetOnLoadScript02()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:"  window.open("contextPath/popup.do?popup_error_key=errorKey",
     *  "popup", "");<br>
     * <br>
     * �Z�b�V�����Ɋi�[�����G���[�L�[�̕����񂪐ݒ肳��A
     * ���b�Z�[�W�L�[��null�ł���Ƃ��A�G���[�L�[���|�b�v�A�b�v���
     * ���N�G�X�g�p�����[�^�Ƃ��ăX�N���v�g�ɏo�͂���邱�ƁB
     */
    public void testGetOnLoadScript02() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // �e�X�g���s
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);

    }

    /**
     * testGetOnLoadScript03()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey=null
     * messageKey="messageKey"
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:"  window.open("contextPath/popup.do?popup_message_key=messageKey",
     *  "popup", "");<br>
     * <br>
     * �Z�b�V�����Ɋi�[�����G���[�L�[��null�ł���A
     * ���b�Z�[�W�L�[�ɕ����񂪐ݒ肳��Ă���Ƃ��A���b�Z�[�W�L�[��
     * �|�b�v�A�b�v��̃��N�G�X�g�p�����[�^�Ƃ��ăX�N���v�g��
     * �o�͂���邱�ƁB
     */
    public void testGetOnLoadScript03() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, null, "messageKey" };

        // �e�X�g���s
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_message_key=messageKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);

    }

    /**
     * testGetOnLoadScript04()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey="errorKey"
     * messageKey="messageKey"
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:"  window.open("contextPath/popup.do?popup_error_key=errorKey&
     *  popup_message_key=messageKey","popup", "");<br>
     * <br>
     * �Z�b�V�����Ɋi�[�����G���[���A���b�Z�[�W���
     * ���ɑ��݂��鎞�����̃L�[���|�b�v�A�b�v�X�N���v�g��
     * �o�͂���A&�ɂ�茋������Ă��邱�ƁB
     */
    public void testGetOnLoadScript04() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", "messageKey" };

        // �e�X�g���s
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey&"
                + "popup_message_key=messageKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);

    }

    /**
     * testGetOnLoadScript05()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId="windowId"
     * title=null
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:"  windowId = window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","popup", "");<br>
     * <br>
     * 
     * MessagePopupTag#setWindowId()�ɂ��A�E�B���h�EID���w�肳���
     * ����ꍇ�A�X�N���v�g�̖߂�l�Ƃ��ĕϐ�����`����邱�ƁB
     */
    public void testGetOnLoadScript05() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // �e�X�g���s
        UTUtil.setPrivateField(tag, "windowId", "windowId");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertEquals(
            "  windowId = window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", \"\");"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetOnLoadScript06()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title="title"
     * paramFunc=null
     * param=null
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:"  window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","title", "");<br>
     * <br>
     * 
     * MessagePopupTag#setTitle()�ɂ��A�E�B���h�E�^�C�g����
     * �w�肳��Ă���ꍇ�A�X�N���v�g�̈����Ƃ��ďo�͂���邱�ƁB
     */
    public void testGetOnLoadScript06() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // �e�X�g���s
        UTUtil.setPrivateField(tag, "title", "title");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"title\", \"\");"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetOnLoadScript07()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc="paramFunc()"
     * param=null
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:"  window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","popup", paramFunc());<br>
     * <br>
     * 
     * MessagePopupTag#setParamFunc()�ɂ��A�p�����[�^��������擾����
     * JavaScript�֐������o�͂���邱�ƁB
     */
    public void testGetOnLoadScript07() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // �e�X�g���s
        UTUtil.setPrivateField(tag, "paramFunc", "paramFunc()");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", paramFunc());"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetOnLoadScript08()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * req=�[�����N�G�X�g
     * errorKey="errorKey"
     * messageKey=null
     * popup="/popup.do"
     * windowId=null
     * title=null
     * paramFunc=null
     * param="param"
     * paramType=null
     * 
     * ���Ғl
     * �߂�l:"  window.open("contextPath/popup.do?
     *  popup_error_key=errorKey","popup", "param");<br>
     * <br>
     * 
     * MessagePopupTag#setParam()�ɂ��A�|�b�v�A�b�v��̈ʒu�A�T�C�Y
     * �������肷��p�����[�^�����񂪃X�N���v�g�����Ƃ��ēn����邱�ƁB
     */
    public void testGetOnLoadScript08() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        // �|�b�v�A�b�v��URL
        UTUtil.setPrivateField(tag, "popup", "/popup.do");
        // �R���e�L�X�g�p�X�w��
        TagUTUtil.setContextPath(tag, "contextPath");

        Class[] argClazz =
            new Class[] {
                HttpServletRequest.class,
                String.class,
                String.class };

        Object[] argObj = new Object[] { req, "errorKey", null };

        // �e�X�g���s
        UTUtil.setPrivateField(tag, "param", "param");
        Object retObj =
            UTUtil.invokePrivate(tag, "getOnLoadScript", argClazz, argObj);

        // �e�X�g���ʊm�F
        assertEquals(
            "  window.open(\"contextPath/popup.do?popup_error_key=errorKey\", \"popup\", \"param\");"
                + System.getProperty("line.separator"),
            retObj);
    }

    /**
     * testGetRequestParameterKey01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * 
     * �Z�b�V�����Ɋi�[����Ă���G���[�E���b�Z�[�W���̃L�[��
     * �A�������N�G����������擾����B
     */
    public void testGetRequestParameterKey01() throws Exception {
        // �{�����́AtestGetOnLoadScriptXX()�ɓ�����B
    }

    /**
     * testSetPopup01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * popup�t�B�[���h�̃Z�b�^�[�m�F�B
     */
    public void testSetPopup01() throws Exception {

        // �e�X�g���s
        tag.setPopup("popup");

        // �e�X�g���ʊm�F
        assertEquals("popup", UTUtil.getPrivateField(tag, "popup"));
    }

    /**
     * testSetTitle01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * title�t�B�[���h�̃Z�b�^�[�m�F�B
     * �f�t�H���g��DEFAULT_TITLE���ݒ肳��Ă���A
     * �Z�b�^�[�����s���邱�Ƃɂ��㏑�����s���邱�ƁB
     */
    public void testSetTitle01() throws Exception {

        // �e�X�g���s�E���ʊm�F
        // �f�t�H���g��DEFAULT_TITLE���ݒ肳��Ă��邱�ƁB
        assertEquals(
            UTUtil.getPrivateField(MessagesPopupTag.class, "DEFAULT_TITLE"),
            UTUtil.getPrivateField(tag, "title"));

        tag.setTitle("title");
        
        // title�t�B�[���h���㏑������Ă��邱�ƁB
        assertEquals("title", UTUtil.getPrivateField(tag, "title"));
    }

    /**
     * testSetParam01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * param�t�B�[���h�̃Z�b�^�[�m�F�B
     */
    public void testSetParam01() throws Exception {
 
        // �e�X�g���s
        tag.setParam("param");
        
        // �e�X�g���ʊm�F
        assertEquals("param", UTUtil.getPrivateField(tag, "param"));
    }

    /**
     * testSetParamType01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * param�̃Z�b�^�[�m�F�B
     * �v���p�e�B�t�@�C������messages.popup.param. + [�Z�b�^�[����]��
     * �Q�Ƃ��邱�Ƃɂ��Aparam�t�B�[���h�ɁA���̒l���ݒ肳��Ă��邱�ƁB
     */
    public void testSetParamType01() throws Exception {

        // �e�X�g���s
        tag.setParamType("paramType");

        // �e�X�g���ʊm�F
        assertEquals("religion", UTUtil.getPrivateField(tag,"param"));
    }

    /**
     * testSetParamFunc01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * paramFunc�t�B�[���h�̃Z�b�^�[�m�F�B
     */
    public void testSetParamFunc01() throws Exception {
 
        // �e�X�g���s
        tag.setParamFunc("paramFunc()");
        
        // �e�X�g���ʊm�F
        assertEquals("paramFunc()", UTUtil.getPrivateField(tag, "paramFunc"));
    }

    /**
     * testSetWindowId01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * windowId�t�B�[���h�̃Z�b�^�[�m�F�B
     */
    public void testSetWindowId01() throws Exception {
 
        // �e�X�g���s
        tag.setWindowId("windowId");

        // �e�X�g���ʊm�F
        assertEquals("windowId", UTUtil.getPrivateField(tag, "windowId"));
    }

    /**
     * testDoEndTag01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FB<br>
     * 
     * EVAL_PAGE��ԋp���邱�ƁB
     */
    public void testDoEndTag01() throws Exception {
        // �e�X�g���s
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
    }

    /**
     * testRelease01()�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FB<br>
     * 
     * MessagesPopupTag���Ŏg�p�����C���X�^���X�ϐ����J������Ă��邱�ƁB
     * 
     */
    public void testRelease01() throws Exception {

        UTUtil.setPrivateField(tag, "popup", "popup");
        UTUtil.setPrivateField(tag, "title", "title");
        UTUtil.setPrivateField(tag, "param", "param");
        UTUtil.setPrivateField(tag, "paramFunc", "paramFunc()");
        UTUtil.setPrivateField(tag, "windowId", "windowId");

        // �e�X�g���s
        tag.release();

        // �e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(tag, "popup"));
        assertEquals(
            UTUtil.getPrivateField(MessagesPopupTag.class, "DEFAULT_TITLE"),
            UTUtil.getPrivateField(tag, "title"));
        assertNull(UTUtil.getPrivateField(tag, "param"));
        assertNull(UTUtil.getPrivateField(tag, "paramFunc"));
        assertNull(UTUtil.getPrivateField(tag, "windowId"));
    }

}