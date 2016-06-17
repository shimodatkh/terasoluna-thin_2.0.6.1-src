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

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import jp.terasoluna.utlib.exception.Exception_PageContextImpl;
import junit.framework.TestCase;

/**
 * StringFormatterTagBase �u���b�N�{�b�N�X�e�X�g�B<br>
 */
public class StringFormatterTagBaseTest extends TestCase {

    // �e�X�g�Ώ�
    StringFormatterTagBaseImpl01 tag = null;

    /**
     * Constructor for StringFormatterTagBaseTest.
     * @param arg0
     */
    public StringFormatterTagBaseTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (StringFormatterTagBaseImpl01) TagUTUtil
                .create(StringFormatterTagBaseImpl01.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        tag = null;
    }

    /**
     * testDoStartTag01�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=*<br>
     * scope=����l<br>
     * bean=null<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �擾�����uBean�v��NULL�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "NoBeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Null<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �擾�����uvalue2�v��NULL�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag02() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        bean.setTestField(null);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="testtesttest"<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id="testtesttest"<br>
     * output=-<br>
     * StringFormatterTagBase�N���X�ϐ��́uid�v���L�[�� �y�[�W�R���e�L�X�g�Ƀt�H�[�}�b�g��̒l���Z�b�g�����ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag03() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        String testValue = "testtesttest";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();
        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals(testValue, pc.getAttribute("id"));

    } /* testDoStartTag03 End */

    /**
     * testDoStartTag04�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=true<br>
     * output="test<test>test"<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output="test&lt;test&gt;test"<br>
     * StringFormatterTagBase�N���X�ϐ��́uid�v���w�肳��Ă��Ȃ����߁A �t�H�[�}�b�g��̒l���t�B���^�[�������A�y�[�W�Ɋ֘A�t����ꂽ���C�^�� �������ޏꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag04() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals("test&lt;test&gt;test", reader);

    } /* testDoStartTag04 End */

    /**
     * testDoStartTag05�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=false<br>
     * output="test<test>test"<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id=-<br>
     * output="test<test>test"<br>
     * StringFormatterTagBase�N���X�ϐ��́uid�v���w�肳��Ă��Ȃ����߁A �t�H�[�}�b�g��̒l���t�B���^�[���������ɁA�y�[�W�Ɋ֘A�t����ꂽ���C�^�� �������ޏꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag05() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals(testValue, reader);

    } /* testDoStartTag05 End */

    /**
     * testDoStartTag06�B<br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * ���͒l value=Null<br>
     * ignore=true<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=�ُ�l<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * ���Ғl �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �uBean�v�擾����JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag06() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "NoBeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "Not Scope");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();

        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            // �e�X�g���ʊm�F
            assertEquals(JspException.class.getName(), ex.getClass().getName());
            return;
        }
    } /* testDoStartTag06 End */

    /**
     * testDoStartTag07�B<br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=�z�肵���镶����<br>
     * scope=����l<br>
     * bean=*<br>
     * valu2=*<br>
     * id=*<br>
     * filter=*<br>
     * output=*<br>
     * ���Ғl �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �uvalue2�v�擾����JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag07() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "NotestField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();

        bean.setTestField("testtesttest");

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            // �e�X�g���ʊm�F
            String mes = ex.getMessage();
            assertTrue(mes, mes.startsWith("Unknown property \'NotestField\'"));
            // assertEquals("Unknown property \'NotestField\'", ex.getMessage());
            return;
        }
    } /* testDoStartTag07 End */

    /**
     * testDoStartTag08�B<br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * ���͒l value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=true<br>
     * output=" output"<br>
     * ���Ғl �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �t�B���^�[�ς݂̒l���y�[�W�Ɋ֘A�t����ꂽ���C�^�� �������ނƂ���JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag08() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            // �e�X�g���ʊm�F
            assertEquals(IOException.class.getName(), ex.getRootCause()
                    .getClass().getName());
            return;
        }
    } /* testDoStartTag08 End */

    /**
     * testDoStartTag09�B<br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * ���͒l value=Not Null<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * bean=*<br>
     * valu2=*<br>
     * id=Null<br>
     * filter=false<br>
     * output=" output"<br>
     * ���Ғl �߂�l:int=JspException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �l���y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ނƂ��� JspException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag09() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        String testValue = "test<test>test";
        UTUtil.setPrivateField(tag, "value", testValue);
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (JspException ex) {
            // �e�X�g���ʊm�F
            assertEquals(IOException.class.getName(), ex.getRootCause()
                    .getClass().getName());
            return;
        }
    } /* testDoStartTag09 End */

    /**
     * testDoStartTag10�B<br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=*<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="output"<br>
     * ���Ғl �߂�l:int=NullPointerException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �y�[�W�R���e�L�X�g�ɃZ�b�g���鎞�� NullPointerException�����������ꍇ<br>
     */
    public void testDoStartTag10() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", "value");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_PageContextImpl pc2 = new Exception_PageContextImpl(pc
                .getServletConfig(), pc.getRequest(), pc.getResponse());
        pc2.setNullPointerEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (NullPointerException ex) {

            // �e�X�g���ʊm�F
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoStartTag10 End */

    /**
     * testDoStartTag11�B<br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=*<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="output"<br>
     * ���Ғl �߂�l:int=IllegalArgumentException<br>
     * pageContext��id=-<br>
     * output=-<br>
     * �y�[�W�R���e�L�X�g�ɃZ�b�g���鎞�� IllegalArgumentException�����������ꍇ<br>
     */
    public void testDoStartTag11() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", "value");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));

        PageContext pc = TagUTUtil.getPageContext(tag);
        Exception_PageContextImpl pc2 = new Exception_PageContextImpl(pc
                .getServletConfig(), pc.getRequest(), pc.getResponse());
        pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        tag.setPageContext(pc2);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (IllegalArgumentException ex) {

            // �e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), ex
                    .getClass().getName());
            return;
        }
    } /* testDoStartTag11 End */

    /**
     * testDoStartTag12�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Null<br>
     * filter=*<br>
     * output="test  TEST"(���p�X�y�[�X�~2)<br>
     * replaceSpToNbsp=true<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id="test&nbsp;&nbsp;TEST"<br>
     * output=-<br>
     * ���p�X�y�[�X���܂ޕ�����ŁAStringFormatterTagBase�N���X�ϐ��́uid�v���L�[�� �y�[�W�R���e�L�X�g�Ƀt�H�[�}�b�g��̒l���Z�b�g�����ꍇ�̃e�X�g�P�[�X
     * (replaceSpToNbsp��true�Ȃ̂Ŕ��p�X�y�[�X���u������Ă���)<br>
     */
    public void testDoStartTag12() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));

        String testValue = "test  TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();

        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);
        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("test&nbsp;&nbsp;TEST", reader);

    } /* testDoStartTag12 End */

    /**
     * testDoStartTag13�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Null<br>
     * filter=*<br>
     * output="test�@�@TEST"(�S�p�X�y�[�X�~2)<br>
     * replaceSpToNbsp=true<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id="testTEST"<br>
     * output=-<br>
     * �S�p�X�y�[�X���܂ޕ�����ŁAStringFormatterTagBase�N���X�ϐ��́uid�v���L�[�� �y�[�W�R���e�L�X�g�Ƀt�H�[�}�b�g��̒l���Z�b�g�����ꍇ�̃e�X�g�P�[�X
     * (replaceSpToNbsp��true�����S�p�X�y�[�X�Ȃ̂Œu������ĂȂ�)<br>
     */
    public void testDoStartTag13() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));

        String testValue = "test�@�@TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();

        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(testValue, reader.readLine());

    } /* testDoStartTag13 End */

    /**
     * testDoStartTag14�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Null<br>
     * filter=*<br>
     * output="test  TEST"(���p�X�y�[�X�~2)<br>
     * replaceSpToNbsp=false<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id="testTEST"<br>
     * output=-<br>
     * ���p�X�y�[�X���܂ޕ�����ŁAStringFormatterTagBase�N���X�ϐ��́uid�v���L�[�� �y�[�W�R���e�L�X�g�Ƀt�H�[�}�b�g��̒l���Z�b�g�����ꍇ�̃e�X�g�P�[�X
     * (replaceSpToNbsp��false�Ȃ̂Ŕ��p�X�y�[�X���u������ĂȂ�)<br>
     */
    public void testDoStartTag14() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        String testValue = "test  TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();

        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(testValue, reader.readLine());

    } /* testDoStartTag14 End */

    /**
     * testDoStartTag15�B<br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * ���͒l value=Null<br>
     * ignore=false<br>
     * name=Not Null<br>
     * property=Not Null<br>
     * scope=����l<br>
     * bean=Not Null<br>
     * valu2=Not Null<br>
     * id=Not Null<br>
     * filter=*<br>
     * output="test  TEST"(���p�X�y�[�X�~2)<br>
     * replaceSpToNbsp=true<br>
     * ���Ғl �߂�l:int=SKIP_BODY<br>
     * pageContext��id="testTEST"<br>
     * output=-<br>
     * ���p�X�y�[�X���܂ޕ�����ŁAStringFormatterTagBase�N���X�ϐ��́uid�v���L�[�� �y�[�W�R���e�L�X�g�Ƀt�H�[�}�b�g��̒l���Z�b�g�����ꍇ�̃e�X�g�P�[�X (replaceSpToNbsp�����͖��������)<br>
     */
    public void testDoStartTag15() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "BeanKey");
        UTUtil.setPrivateField(tag, "property", "testField");
        UTUtil.setPrivateField(tag, "scope", "page");
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));

        String testValue = "test  TEST";

        StringFormatterTagBase_BeanStub01 bean = new StringFormatterTagBase_BeanStub01();

        bean.setTestField(testValue);

        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("BeanKey", bean);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        assertEquals(testValue, pc.getAttribute("id"));

    } /* testDoStartTag15 End */

    /**
     * testRelease01�B<br>
     * (����n)<br>
     * �ϓ_�FC<br>
     * ���͒l id=*<br>
     * filter=false<br>
     * ignore=true<br>
     * name="name"<br>
     * property="property"<br>
     * scope="scope"<br>
     * value="value"<br>
     * replaceSpToNbsp=false<br>
     * ���Ғl �߂�l:void<br>
     * id=Null<br>
     * filter=true<br>
     * ignore=false<br>
     * name=Null<br>
     * property=Null<br>
     * scope=Null<br>
     * value=Null<br>
     * replaceSpToNbsp=true<br>
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l������������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "id", "id");
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "property", "property");
        UTUtil.setPrivateField(tag, "scope", "scope");
        UTUtil.setPrivateField(tag, "value", "value");
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        // �e�X�g���s
        tag.release();

        // �e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(tag, "id"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "filter"));
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "ignore"));
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "property"));
        assertNull(UTUtil.getPrivateField(tag, "scope"));
        assertNull(UTUtil.getPrivateField(tag, "value"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));

    } /* testRelease01 End */

    /**
     * testSetId01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l id="id"<br>
     * ���Ғl �߂�l:void<br>
     * id="id"<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetId01() throws Exception {
        // �e�X�g���s
        tag.setId("id");

        // �e�X�g���ʊm�F
        assertEquals("id", UTUtil.getPrivateField(tag, "id"));

    } /* testSetId01 End */

    /**
     * testSetFilter01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l filter=false<br>
     * ���Ғl �߂�l:void<br>
     * filter=false<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetFilter01() throws Exception {
        // �e�X�g���s
        tag.setFilter(false);

        // �e�X�g���ʊm�F
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "filter"));

    } /* testSetFilter01 End */

    /**
     * testSetIgnore01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l ignore=true<br>
     * ���Ғl �߂�l:void<br>
     * ignore=true<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetIgnore01() throws Exception {
        // �e�X�g���s
        tag.setIgnore(true);

        // �e�X�g���ʊm�F
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "ignore"));

    } /* testSetIgnore01 End */

    /**
     * testSetName01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l name="name"<br>
     * ���Ғl �߂�l:void<br>
     * name="name"<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetName01() throws Exception {
        // �e�X�g���s
        tag.setName("name");

        // �e�X�g���ʊm�F
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));

    } /* testSetNam01 End */

    /**
     * testSetProperty01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l property="property"<br>
     * ���Ғl �߂�l:void<br>
     * property="property"<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetProperty01() throws Exception {
        // �e�X�g���s
        tag.setProperty("property");

        // �e�X�g���ʊm�F
        assertEquals("property", UTUtil.getPrivateField(tag, "property"));

    } /* testSetProperty01 End */

    /**
     * testSetScope01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l scope="scope"<br>
     * ���Ғl �߂�l:void<br>
     * scope="scope"<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetScope01() throws Exception {
        // �e�X�g���s
        tag.setScope("scope");

        // �e�X�g���ʊm�F
        assertEquals("scope", UTUtil.getPrivateField(tag, "scope"));

    } /* testSetScope01 End */

    /**
     * testSetValue01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l value="value"<br>
     * ���Ғl �߂�l:void<br>
     * value="value"<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetValue01() throws Exception {
        // �e�X�g���s
        tag.setValue("value");

        // �e�X�g���ʊm�F
        assertEquals("value", UTUtil.getPrivateField(tag, "value"));

    } /* testSetValue01 End */

    /**
     * testSetReplaceSpToNbsp01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l value=false<br>
     * ���Ғl �߂�l:void<br>
     * value=false<br>
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetReplaceSpToNbsp01() throws Exception {
        // �e�X�g���s
        tag.setReplaceSpToNbsp(false);

        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));
    } /* testSetReplaceSpToNbsp01 End */

    /**
     * testGetId01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l id="id"<br>
     * ���Ғl �߂�l:String="id"<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetId01() throws Exception {
        // �e�X�g�ݒ�
        String value = "id";
        UTUtil.setPrivateField(tag, "id", value);

        // �e�X�g���s
        String result = tag.getId();

        // �e�X�g���ʊm�F
        assertEquals(value, result);

    } /* testGetId01 End */

    /**
     * testGetFilter01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l filter=false<br>
     * ���Ғl �߂�l:Boolean=false<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetFilter01() throws Exception {
        // �e�X�g�ݒ�
        boolean value = false;
        UTUtil.setPrivateField(tag, "filter", new Boolean(value));

        // �e�X�g���s
        boolean result = tag.getFilter();

        // �e�X�g���ʊm�F
        assertFalse(result);

    } /* testGetFilter01 End */

    /**
     * testGetIgnore01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l ignore=true<br>
     * ���Ғl �߂�l:Boolean=true<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetIgnore01() throws Exception {
        // �e�X�g�ݒ�
        boolean value = true;
        UTUtil.setPrivateField(tag, "ignore", new Boolean(value));

        // �e�X�g���s
        boolean result = tag.getIgnore();

        // �e�X�g���ʊm�F
        assertTrue(result);

    } /* testGetIgnore01 End */

    /**
     * testGetName01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l name="name"<br>
     * ���Ғl �߂�l:String="name"<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetName01() throws Exception {
        // �e�X�g�ݒ�
        String value = "name";
        UTUtil.setPrivateField(tag, "name", value);

        // �e�X�g���s
        String result = tag.getName();

        // �e�X�g���ʊm�F
        assertEquals(value, result);

    } /* testGetName01 End */

    /**
     * testGetProperty01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l property="property"<br>
     * ���Ғl �߂�l:String="property"<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetProperty01() throws Exception {
        // �e�X�g�ݒ�
        String value = "property";
        UTUtil.setPrivateField(tag, "property", value);

        // �e�X�g���s
        String result = tag.getProperty();

        // �e�X�g���ʊm�F
        assertEquals(value, result);

    } /* testGetProperty01 End */

    /**
     * testGetScope01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l scope="scope"<br>
     * ���Ғl �߂�l:String="scope"<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetScope01() throws Exception {
        // �e�X�g�ݒ�
        String value = "scope";
        UTUtil.setPrivateField(tag, "scope", value);

        // �e�X�g���s
        String result = tag.getScope();

        // �e�X�g���ʊm�F
        assertEquals(value, result);

    } /* testGetScope01 End */

    /**
     * testGetValue01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l value="value"<br>
     * ���Ғl �߂�l:String="value"<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetValue01() throws Exception {
        // �e�X�g�ݒ�
        String value = "value";
        UTUtil.setPrivateField(tag, "value", value);

        // �e�X�g���s
        String result = tag.getValue();

        // �e�X�g���ʊm�F
        assertEquals(value, result);

    } /* testGetValue01 End */

    /**
     * testGetReplaceSpToNbsp01�B<br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * ���͒l value="(false);"<br>
     * ���Ғl �߂�l:String="(false);"<br>
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetReplaceSpToNbsp01() throws Exception {
        // �e�X�g�ݒ�
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        // �e�X�g���s
        boolean result = tag.getReplaceSpToNbsp();

        // �e�X�g���ʊm�F
        assertFalse(result);

    } /* testGetReplaceSpToNbsp01 End */

} /* StringFormatterTagBaseTest Class End */
