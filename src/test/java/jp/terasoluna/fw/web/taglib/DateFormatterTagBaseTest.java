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

package jp.terasoluna.fw.web.taglib;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import jp.terasoluna.utlib.exception.Exception_PageContextImpl;
import junit.framework.TestCase;

/**
 * DateFormatterTagBase �u���b�N�{�b�N�X�e�X�g�B<br>
 *
 */
public class DateFormatterTagBaseTest extends TestCase {

    //�e�X�g�Ώ�
    DateFormatterTagBaseImpl01 tag = null;

    /**
     * Constructor for DateFormatterTagBaseTest.
     * @param arg0
     */
    public DateFormatterTagBaseTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag =
            (DateFormatterTagBaseImpl01) TagUTUtil.create(
                DateFormatterTagBaseImpl01.class);
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
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=*<br>
     * scope=����l<br>
     * bean=null<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �擾�����uBean�v��NULL�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag01() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "NoBeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        // DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        bean.setTestField("2004/11/24 10:31:00");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Null<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �擾�����uvalue2�v��NULL�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag02() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        //DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        bean.setTestField(null);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Not Null(String)<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="<DateFormatterTagBaseTest>"<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��id="<DateFormatterTagBaseTest>"<br>
     * output=-<br>
     * 
     * �擾�����lValue��String�̂��߁ADate�^�֕ϊ������A
     * �N���X�ϐ��́uid�v���L�[�Ƀy�[�W�R���e�L�X�g�Ƀt�H�[�}�b�g��̒l��
     * �Z�b�g�����ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag03() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        // DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        bean.setTestField("2004/11/24 10:31:00");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);
        
        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals("<DateFormatterTagBaseTest>", pc.getAttribute("id"));

    } /* testDoStartTag03 End */

    /**
     * testDoStartTag04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * value=Not Null(Date)<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=true<br>
     * output="&lt;DateFormatterTagBaseTest&gt;"<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output="&lt;DateFormatterTagBaseTest&gt;"<br>
     * 
     * �擾�����lValue��Date�̂��߁ADate�^�փL���X�g�����A�N���X�ϐ��́uid�v���w�肳��Ă��Ȃ����߁A�t�H�[�}�b�g��̒l���t�B���^�[�������A�y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ޏꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag04() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "dateField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        // DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        bean.setTestField("2004/11/24 10:31:00");
        
        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        bean.setDateField(new Date(time.getTime()));
        
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals("&lt;DateFormatterTagBaseTest&gt;", reader);

    } /* testDoStartTag04 End */

    /**
     * testDoStartTag05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * value=Not Null(Not Date& Not String)<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �擾�����lValue��String�ł�Date�ł��Ȃ��ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag05() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "timeField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        //DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        bean.setTimeField(new Integer(1));

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals(Tag.SKIP_BODY, result);
        assertNull(pc.getAttribute("id"));
        assertEquals("", reader);

    } /* testDoStartTag05 End */

    /**
     * testDoStartTag06�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * value=Not Null(Date)<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=false<br>
     * output="test<test>test"<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output="<DateFormatterTagBaseTest>"<br>
     * 
     * �擾�����lValue��Date�̂��߁ADate�^�փL���X�g�����A
     * �N���X�ϐ��́uid�v���w�肳��Ă��Ȃ����߁A
     * �t�H�[�}�b�g��̒l���t�B���^�[���������ɁA
     * �y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ޏꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag06() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "dateField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        //DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();

        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        bean.setDateField(new Date(time.getTime()));

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals("<DateFormatterTagBaseTest>", reader);

    } /* testDoStartTag06 End */

    /**
     * testDoStartTag07�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=�ُ�l<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * ���Ғl
     * �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �uBean�v�擾����JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag07() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "NoBeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "Not Scope");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        //DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        UTUtil.setPrivateField(bean, "testField", "testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        //�e�X�g���s
        try {
            tag.doStartTag();
            // ��O���������Ȃ������ꍇ�e�X�g�͎��s�B
            fail();
        } catch (JspException ex) {
            //�e�X�g����
            return;
        }

        

    } /* testDoStartTag07 End */

    /**
     * testDoStartTag08�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=�z�肵���镶����<br>
     * scope=����l<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * ���Ғl
     * �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �uvalue2�v�擾����JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag08() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "NotestField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        // DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        UTUtil.setPrivateField(bean, "testField", "testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        try {
            tag.doStartTag();
            // ��O���������Ȃ������ꍇ�e�X�g�͎��s�B
            fail();
        } catch (JspException ex) {
            //�e�X�g�����B
            return;
        }
        

    } /* testDoStartTag08 End */

    /**
     * testDoStartTag09�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=true<br>
     * output=" output"<br>
     * 
     * ���Ғl
     * �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �t�B���^�[�ς݂̒l���y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ނƂ���
     * JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag09() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        String testValue = "2004/11/24 10:31:00";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        //�e�X�g���s
        try {
            tag.doStartTag();
            // ��O���������Ȃ������ꍇ�e�X�g�͎��s�B
            fail();
        } catch (JspException ex) {
            // IOException�����b�v���Ă��邱�Ƃ��m�F�B
            assertEquals(IOException.class.getName(),
                    ex.getRootCause().getClass().getName());
            return;
        }

    } /* testDoStartTag09 End */

    /**
     * testDoStartTag10�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=false<br>
     * output=" output"<br>
     * 
     * ���Ғl
     * �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �l���y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ނƂ���JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag10() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        String testValue = "2004/11/24 10:31:00";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        //�e�X�g���s
        try {
            tag.doStartTag();
            // ��O���������Ȃ������ꍇ�e�X�g�͎��s�B
            fail();
        } catch (JspException ex) {
            // IOException�����b�v���Ă��邱�Ƃ��m�F�B
            assertEquals(IOException.class.getName(),
                    ex.getRootCause().getClass().getName());
            return;
        }

    } /* testDoStartTag10 End */

    /**
     * testDoStartTag11�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=*<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="output"<br>
     * 
     * ���Ғl
     * �߂�l:int=NullPointerException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �y�[�W�R���e�L�X�g�ɃZ�b�g���鎞��NullPointerException�����������ꍇ<br>
     */
    public void testDoStartTag11() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", "2004/11/24 10:31:00");
        UTUtil.setPrivateField(tag, "id", "id");

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setNullPointerEx();
        //pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        //�e�X�g���s
        try {
            tag.doStartTag();
            // ��O���������Ȃ������ꍇ�e�X�g�͎��s�B
            fail();
        } catch (NullPointerException ex) {

            //�e�X�g���ʊm�F
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }

    } /* testDoStartTag11 End */

    /**
     * testDoStartTag12�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=*<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="output"<br>
     * 
     * ���Ғl
     * �߂�l:int=IllegalArgumentException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �y�[�W�R���e�L�X�g�ɃZ�b�g���鎞��IllegalArgumentException�����������ꍇ<br>
     */
    public void testDoStartTag12() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", "2004/11/24 10:31:00");
        UTUtil.setPrivateField(tag, "id", "id");

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        //pc2.setNullPointerEx();
        pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        //�e�X�g���s
        try {
            tag.doStartTag();
            // ��O���������Ȃ������ꍇ�e�X�g�͎��s�B
            fail();
        } catch (IllegalArgumentException ex) {

            //�e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(),
                    ex.getClass().getName());
            return;
        }

    } /* testDoStartTag12 End */

    /**
     * testDoStartTag13�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=Not Null(String)<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * 
     * ���Ғl
     * �߂�l:int=JspTagException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * 
     * �擾�����lValue��String�̂��߁ADate�^�ւ̕ϊ�����ParseException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag13() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", "go to ParseException");

        //�e�X�g���s
        try {
            tag.doStartTag();
            // ��O���������Ȃ������ꍇ�e�X�g�͎��s�B
            fail();
        } catch (JspTagException ex) {

            //�e�X�g���ʊm�F
            assertTrue(LogUTUtil.checkError("Date parsing error."));
            assertEquals("Unparseable date: \"go to ParseException\"", ex
                    .getMessage());
            return;
        }

    } /* testDoStartTag13 End */

    /**
     * testDoStartTag14�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * format="GGG yyyy.MM.dd HH,mm,ss,SSS"<br>
     * value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=DateFormatterTagBase_BeanStub01
     *      testField="���� 2009.01.08 17,00,00,000"<br>
     * valu2=Not Null(String)<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="<DateFormatterTagBaseTest>"<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��id="<DateFormatterTagBaseTest>"<br>
     * output=-<br>
     * 
     * format�����ɓ��t�p�^�[�����ݒ肳��Ă���A�擾�����lValue�����t�p�^�[����
     * �}�b�`����String�̏ꍇ�A�����Date�^�֕ϊ������ParseException���������Ȃ�
     * ���Ƃ��m�F����B<br>
     */
    public void testDoStartTag14() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "format", "GGG yyyy.MM.dd HH,mm,ss,SSS");
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        // DateFormatterTagBase_BeanStub01�C���X�^���X�̐���
        DateFormatterTagBase_BeanStub01 bean = 
            new DateFormatterTagBase_BeanStub01();
        bean.setTestField("���� 2009.01.08 17,00,00,000");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);
        
        try {
            // �e�X�g���s
            int result = tag.doStartTag();

            // �e�X�g���ʊm�F
            assertEquals(Tag.SKIP_BODY, result);
            assertEquals("<DateFormatterTagBaseTest>", pc.getAttribute("id"));
        } catch (JspTagException ex) {
            //��O�����������ꍇ�e�X�g�͎��s�B
            fail();
        }

    } /* testDoStartTag14 End */
    
    /**
     * testRelease01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * id="id"<br>
     * filter=false<br>
     * ignore=true<br>
     * name="name"<br>
     * property="property"<br>
     * scope="scope"<br>
     * pattern="pattern"<br>
     * value="value"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * id=Null<br>
     * filter=true<br>
     * ignore=false<br>
     * name=Null<br>
     * property=Null<br>
     * scope=Null<br>
     * pattern=Null<br>
     * value=Null<br>
     * 
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l������������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "id", "");
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "property", "property");
        UTUtil.setPrivateField(tag, "scope", "scope");
        UTUtil.setPrivateField(tag, "pattern", "pattern");
        UTUtil.setPrivateField(tag, "value", "value");

        //�e�X�g���s
        tag.release();

        //�e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(tag, "id"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "filter"));
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "ignore"));
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "property"));
        assertNull(UTUtil.getPrivateField(tag, "scope"));
        assertNull(UTUtil.getPrivateField(tag, "pattern"));
        assertNull(UTUtil.getPrivateField(tag, "value"));

    } /* testRelease1 End */

    /**
     * testSetId01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * id="id"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * id="id"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetId01() throws Exception {
        //�e�X�g���s
        tag.setId("id");

        //�e�X�g���ʊm�F
        assertEquals("id", UTUtil.getPrivateField(tag, "id"));

    } /* testSetId01 End */
    /**
     * testSetFilter01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * filter=false<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * filter=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetFilter01() throws Exception {
        //�e�X�g���s
        tag.setFilter(false);

        //�e�X�g���ʊm�F
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "filter"));

    } /* testSetFilter01 End */
    /**
     * testSetIgnore01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=true<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ignore=true<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetIgnore01() throws Exception {
        //�e�X�g���s
        tag.setIgnore(true);

        //�e�X�g���ʊm�F
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "ignore"));

    } /* testSetIgnore01 End */
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
     */
    public void testSetName01() throws Exception {
        //�e�X�g���s
        tag.setName("name");

        //�e�X�g���ʊm�F
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));

    } /* testSetName01 End */
    /**
     * testSetProperty01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * property="property"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * property="property"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetProperty01() throws Exception {
        //�e�X�g���s
        tag.setProperty("property");

        //�e�X�g���ʊm�F
        assertEquals("property", UTUtil.getPrivateField(tag, "property"));

    } /* testSetProperty01 End */
    /**
     * testSetScope01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * scope="scope"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * scope="scope"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetScope01() throws Exception {
        //�e�X�g���s
        tag.setScope("scope");

        //�e�X�g���ʊm�F
        assertEquals("scope", UTUtil.getPrivateField(tag, "scope"));

    } /* testSetScope01 End */
    /**
     * testSetPattern01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * pattern="pattern"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * pattern="pattern"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetPattern01() throws Exception {
        //�e�X�g���s
        tag.setPattern("pattern");

        //�e�X�g���ʊm�F
        assertEquals("pattern", UTUtil.getPrivateField(tag, "pattern"));

    } /* testSetPattern01 End */
    /**
     * testSetValue01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="value"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * value="value"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetValue01() throws Exception {
        //�e�X�g���s
        tag.setValue("value");

        //�e�X�g���ʊm�F
        assertEquals("value", UTUtil.getPrivateField(tag, "value"));

    } /* testSetValue01 End */

    /**
     * testSetFormat01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * format="format"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * format="format"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetFormat01() throws Exception {
        //�e�X�g���s
        tag.setFormat("format");
    
        //�e�X�g���ʊm�F
        assertEquals("format", UTUtil.getPrivateField(tag, "format"));
    
    } /* testSetFormat01 End */

    /**
     * testGetFormat01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���� �Ȃ�<br>
     * �O�����
     * format="format"<br>
     * 
     * ���Ғl
     * �߂�l: format="format"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetFormat01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "format", "format");
        
        //�e�X�g���s
        String fmt = tag.getFormat();
    
        //�e�X�g���ʊm�F
        assertEquals("format", fmt);
    
    } /* testGetFormat01 End */
    
    /**
     * testGetDefaultDateFormat01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���� �Ȃ�<br>
     * �O����� �Ȃ�<br>
     * 
     * ���Ғl
     * �߂�l: "yyyy/MM/dd HH:mm:ss"<br>
     * 
     * ����n1���̂�<br>
     */
    public void testGetDefaultDateFormat01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        
        //�e�X�g���s
        String defaultDataFormat = tag.getDefaultDateFormat();
    
        //�e�X�g���ʊm�F
        assertEquals("yyyy/MM/dd HH:mm:ss", defaultDataFormat);
    
    } /* testGetDefaultDateFormat01 End */
} /* DateFormatterTagBaseTest Class End */
