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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * ChangeStyleClassTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class ChangeStyleClassTagTest extends TestCase {

    //�e�X�g�Ώ�
    ChangeStyleClassTag tag = null;

    /**
     * Constructor for ClassTagTest.
     * @param arg0
     */
    public ChangeStyleClassTagTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (ChangeStyleClassTag) TagUTUtil.create(ChangeStyleClassTag.class);
    }

    /**
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
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * req=null<br>
     * name=null<br>
     * defaultValue=null<br>
     * errorValue=null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=-<br>
     * <br>
     * ���N�G�X�g��Null�̏ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testDoStartTag01() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(pc, "request", null);
        tag.setPageContext(pc);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * req=Not Null<br>
     * name="name"<br>
     * defaultValue="defaultValue"<br>
     * errorValue="errorValue"<br>
     * errors=Not Null(�G���[�Ȃ�)
     * <br>
     * ���Ғl<br>
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e="defaultValue"<br>
     * <br>
     * name,defaultValue,errorValue,errors�̂��ׂ�NotNull
     * �̏ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testDoStartTag02() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        ServletRequest rq = pc.getRequest();

        // �e�X�g�ΏۃN���X�ϐ��փZ�b�g����l
        String obj1 = "name";
        String obj2 = "defaultValue";
        String obj3 = "errorValue";

        // �e�X�g�Ώۂ̃N���X�ϐ��փZ�b�g
        UTUtil.setPrivateField(tag, "name", obj1);
        UTUtil.setPrivateField(tag, "defaultValue", obj2);
        UTUtil.setPrivateField(tag, "errorValue", obj3);

        // �G���[�𐶐����A���N�G�X�g�ɃZ�b�g����B
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, errors);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals(obj2, reader);

    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03�B<br>
     * <br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l<br>
     * req=Not Null<br>
     * name="name"<br>
     * defaultValue="defaultValue"<br>
     * errorValue="errorValue"<br>
     * errors=Not Null(�G���[�Ȃ�)
     * <br>
     * ���Ғl<br>
     * �߂�l:int=JspException<br>
     * �o�͓��e=-<br>
     * <br>
     * �o�͎��ɁAIOException�����������ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testDoStartTag03() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        ServletRequest rq = pc.getRequest();

        // �e�X�g�ΏۃN���X�ϐ��փZ�b�g����l
        String obj1 = "name";
        String obj2 = "defaultValue";
        String obj3 = "errorValue";

        // �e�X�g�Ώۂ̃N���X�ϐ��փZ�b�g
        UTUtil.setPrivateField(tag, "name", obj1);
        UTUtil.setPrivateField(tag, "defaultValue", obj2);
        UTUtil.setPrivateField(tag, "errorValue", obj3);

        // �G���[�𐶐����A���N�G�X�g�ɃZ�b�g����B
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, errors);

        // �e�X�g�pJspWriter�̐���
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);

        // �����E�ݒ肵���e�X�g�pJspWriter��PageContext�ɃZ�b�g
        UTUtil.setPrivateField(pc, "jspWriter", out);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException ex) {

            // �e�X�g���ʊm�F
            assertEquals("javax.servlet.jsp.JspTagException:"
                    + " java.io.IOException", ex.toString());
            return;
        }
    } /* testDoStartTag03 End */

    /**
     * testChooseClass01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Null<br>
     * sessionErrors=Null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String="ifNormal"<br>
     * <br>
     * ���N�G�X�g�ƃZ�b�V��������擾�����G���[���b�Z�[�W��
     * Null�̏ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testChooseClass01() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        ServletRequest rq = pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // �e�X�g���s
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // �e�X�g���ʊm�F
        assertEquals(obj2, result);

    } /* testChooseClass01 End */

    /**
     * testChooseClass02�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Not Null(�G���[�L��)<br>
     * sessionErrors=Null<br>
     * ���Ғl<br>
     * �߂�l:String="ifError"<br>
     * <br>
     * ���N�G�X�g����ActionMessages���擾���A ���G���[���b�Z�[�W���ꌏ�ȏ�A�܂��A
     * �Z�b�V��������擾����ActionMessages��null�̏ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception
     *             ��O<br>
     */
    public void testChooseClass02() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // �G���[�𐶐����A���N�G�X�g�ɃZ�b�g����B
        ActionMessages errors = new ActionMessages();
        errors.add(obj1, new ActionMessage("errorstagtest1"));
        rq.setAttribute(Globals.ERROR_KEY, errors);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, null);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // �e�X�g���s
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // �e�X�g���ʊm�F
        assertEquals(obj3, result);

    } /* testChooseClass02 End */

    /**
     * testChooseClass03�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Null<br>
     * sessionErrors=Not Null(�G���[�L��)<br>
     * ���Ғl<br>
     * �߂�l:String="ifError"<br>
     * <br>
     * �Z�b�V��������ActionMessages���擾���A ���G���[���b�Z�[�W���ꌏ�ȏ�A�܂��A
     * ���N�G�X�g����擾����ActionMessages��null�̏ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception
     *             ��O<br>
     */
    public void testChooseClass03() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // �G���[�𐶐����A���N�G�X�g�ɃZ�b�g����B
        ActionMessages errors = new ActionMessages();
        errors.add(obj1, new ActionMessage("errorstagtest1"));
        rq.setAttribute(Globals.ERROR_KEY, null);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // �e�X�g���s
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // �e�X�g���ʊm�F
        assertEquals(obj3, result);

    } /* testChooseClass03 End */

    /**
     * testChooseClass04�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Not Null(�G���[�Ȃ�)<br>
     * sessionErrors=Null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String="ifNormal"<br>
     * <br>
     * ���N�G�X�g����ActionMessages���擾���A ���G���[���b�Z�[�W���ꌏ���Ȃ��A�܂��A
     * �Z�b�V��������擾�����G���[�pActionMessages��null�̏ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception
     *             ��O<br>
     */
    public void testchooseClass04() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // �G���[�𐶐����A���N�G�X�g�ɃZ�b�g����B
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, errors);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, null);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // �e�X�g���s
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // �e�X�g���ʊm�F
        assertNotNull(rq.getAttribute(Globals.ERROR_KEY));
        assertEquals(obj2, result);

    } /* testChooseClass04 End */

    /**
     * testChooseClass05�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * req=Not Null<br>
     * fieldname="fieldname"<br>
     * ifNormal="ifNormal"<br>
     * ifError="ifError"<br>
     * requestErrors=Null<br>
     * sessionErrors=Not Null(�G���[�Ȃ�)<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String="ifNormal"<br>
     * <br>
     * �Z�b�V��������ActionMessages���擾���A ���G���[���b�Z�[�W���ꌏ���Ȃ��A�܂��A
     * ���N�G�X�g����擾�����G���[�pActionMessages��null�̏ꍇ�̃e�X�g�P�[�X<br>
     * 
     * @throws Exception
     *             ��O<br>
     */
    public void testChooseClass05() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();

        String obj1 = "fieldname";
        String obj2 = "ifNormal";
        String obj3 = "ifError";

        // �G���[�𐶐����A���N�G�X�g�ɃZ�b�g����B
        ActionMessages errors = new ActionMessages();
        rq.setAttribute(Globals.ERROR_KEY, null);
        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);

        Class[] obj4 = { HttpServletRequest.class, String.class, String.class,
                String.class };
        Object[] obj5 = { rq, obj1, obj2, obj3 };

        // �e�X�g���s
        String result = (String) UTUtil.invokePrivate(tag, "chooseClass", obj4,
                obj5);

        // �e�X�g���ʊm�F
        assertEquals(obj2, result);

    } /* testChooseClass05 End */

    /**
     * testRelease01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l name=*<br>
     * defaultValue=*<br>
     * errorValue=*<br>
     * 
     * ���Ғl �߂�l:void<br>
     * name=Null<br>
     * defaultValue=Null<br>
     * errorValue=Null<br>
     * 
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l������������邱�Ƃ��m�F����<br>
     * 
     * @throws Exception
     *             ��O<br>
     */
    public void testRelease01() throws Exception {

        // �e�X�g�ݒ�
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "defaultValue", "defaultValue");
        UTUtil.setPrivateField(tag, "errorValue", "errorValue");

        // �e�X�g���s
        tag.release();

        // �e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "defaultValue"));
        assertNull(UTUtil.getPrivateField(tag, "errorValue"));

    } /* testRelease1 End */

    /**
     * testSetName01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * name="name"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * name="name"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testSetName01() throws Exception {
        // �e�X�g���s
        tag.setName("name");

        // �e�X�g���ʊm�F
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));

    } /* testSetName End */

    /**
     * testSetDefault01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * default="default"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * default="default"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testSetDefault01() throws Exception {
        // �e�X�g���s
        tag.setDefault("default");

        // �e�X�g���ʊm�F
        assertEquals("default", UTUtil.getPrivateField(tag, "defaultValue"));

    } /* testSetDefault End */

    /**
     * testSetError01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * error="error"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * error="error"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testSetError01() throws Exception {
        // �e�X�g���s
        tag.setError("error");

        // �e�X�g���ʊm�F
        assertEquals("error", UTUtil.getPrivateField(tag, "errorValue"));

    } /* testSetError01 End */

    /**
     * testDoEndTag01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     *   
     * ���͒l:�Ȃ�<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_PAGE<br>
     * 
     * ���EVAL_PAGE���ԋp�����B<br>
     * 
     * @throws Exception ��O<br>
     */
    public void testDoEndTag01() throws Exception {

        int result = 0;
        // �e�X�g���s
        result = tag.doEndTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
    } /* testDoEndTag01 End */

} /* ClassTagTest Class End */
