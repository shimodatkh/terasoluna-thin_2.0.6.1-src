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

import java.io.BufferedReader;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * BodyTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class BodyTagTest extends TestCase {

    //�e�X�g�Ώ�
    BodyTag tag = null;

    /**
     * Constructor for BodyTagTest.
     * @param arg0
     */
    public BodyTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (BodyTag) TagUTUtil.create(BodyTag.class);
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
     * onLoadBody=Null<br>
     * onload=Null<br>
     * onunload=Null<br>
     * styleClass=Null<br>
     * bgcolor=Null<br>
     * background=Null<br>
     * text=Null<br>
     * link=Null<br>
     * vlink=Null<br>
     * alink=Null<br>
     * out=Not Null<br>
     * ContextPath=*<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������u"<body>"�v����щ��s<br>
     * 
     * onLoadBody,onload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alink�̂��ׂĂ�
     * NULL�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag01() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // ���N�G�X�g��ON_LOAD������ݒ�
        req.setAttribute(BodyTag.ON_LOAD_KEY, null);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "onload", null);
        UTUtil.setPrivateField(tag, "onunload", null);
        UTUtil.setPrivateField(tag, "styleClass", null);
        UTUtil.setPrivateField(tag, "bgcolor", null);
        UTUtil.setPrivateField(tag, "background", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "link", null);
        UTUtil.setPrivateField(tag, "vlink", null);
        UTUtil.setPrivateField(tag, "alink", null);

        // �J�X�^���^�O��ContextPath��ݒ�
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body>", reader.readLine());

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l onLoadBody=*<br>
     * onload=*<br>
     * onunload=*<br>
     * styleClass=*<br>
     * bgcolor=*<br>
     * background=*<br>
     * text=*<br>
     * link=*<br>
     * vlink=*<br>
     * alink=*<br>
     * ContextPath=*<br>
     * 
     * ���Ғl �߂�l:int=JspTagException<br>
     * 
     * �o�͎���IOException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag02() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

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
    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * onLoadBody="onLoadBody"<br>
     * onload="onload;"<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="/background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������
     * �u<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="contextPath/background" 
     * text="text" link="link" vlink="vlink" alink="alink">�v���s
     * �u<script type="text/javascript">�v���s
     * �u<!--�v���s
     * �ufunction __onLoad__() {�v���s
     * �u  onload;�v���s
     * �uonLoadBody}�v���s
     * �u//-->�v���s
     * �u</script>�v���s<br>
     * 
     * onLoadBody,onload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alink�̂��ׂĂɒl������A
     * ���Abackground�̒l�̍ŏ��Ɂu/�v������A���A
     * onload�̍Ō�Ɂu;�v������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag03() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // ���N�G�X�g��ON_LOAD������ݒ�
        req.setAttribute(BodyTag.ON_LOAD_KEY, "onLoadBody");

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "onload", "onload;");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "/background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // �J�X�^���^�O��ContextPath��ݒ�
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("  onload;", reader.readLine());
        assertEquals("onLoadBody}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag03 End */

    /**
     * testDoStartTag04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * onLoadBody="onLoadBody"<br>
     * onload="onload"<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������
     * �u<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="/contextPath/background" 
     * text="text" link="link" vlink="vlink" alink="alink">�v���s
     * �u<script type="text/javascript">�v���s
     * �u<!--�v���s
     * �ufunction __onLoad__() {�v���s
     * �u  onload;�v���s
     * �uonLoadBody}�v���s
     * �u//-->�v���s
     * �u</script>�v���s<br>
     * 
     * onLoadBody,onload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alink�̂��ׂĂɒl������A
     * ���Abackground�̒l�̍ŏ��Ɂu/�v���Ȃ��A���A
     * onload�̍Ō�Ɂu;�v���Ȃ��ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag04() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // ���N�G�X�g��ON_LOAD������ݒ�
        req.setAttribute(BodyTag.ON_LOAD_KEY, "onLoadBody");

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "onload", "onload");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // �J�X�^���^�O��ContextPath��ݒ�
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("  onload;", reader.readLine());
        assertEquals("onLoadBody}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag04 End */

    /**
     * testDoStartTag05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * onLoadBody=Null<br>
     * onload="onload"<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������
     * �u<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="/path/background" 
     * text="text" link="link" vlink="vlink" alink="alink">�v���s
     * �u<script type="text/javascript">�v���s
     * �u<!--�v���s�ufunction __onLoad__() {�v���s
     * �u  onload;�v���s
     * �u}�v���s
     * �u//-->�v���s
     * �u</script>�v���s<br>
     * 
     * onLoadBody��NULL�ŁAonload,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alink�̂��ׂĂɒl������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag05() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // ���N�G�X�g��ON_LOAD������ݒ�
        req.setAttribute(BodyTag.ON_LOAD_KEY, null);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "onload", "onload");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // �J�X�^���^�O��ContextPath��ݒ�
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("  onload;", reader.readLine());
        assertEquals("}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag05 End */

    /**
     * testDoStartTag06�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FF<br>
     * 
     * ���͒l
     * onLoadBody="onLoadBody"<br>
     * onload=Null<br>
     * onunload="onunload"<br>
     * styleClass="styleClass"<br>
     * bgcolor="bgcolor"<br>
     * background="background"<br>
     * text="text"<br>
     * link="link"<br>
     * vlink="vlink"<br>
     * alink="alink"<br>
     * out=Not Null<br>
     * ContextPath="/path"<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������
     * �u<body onLoad="__onLoad__()" onUnLoad="onunload" class="styleClass" 
     * bgcolor="bgcolor" background="/path/background" 
     * text="text" link="link" vlink="vlink" alink="alink">�v���s
     * �u<script type="text/javascript">�v���s
     * �u<!--�v���s
     * �ufunction __onLoad__() {�v���s
     * �u  onLoadBody}�v���s
     * �u//-->�v���s
     * �u</script>�v���s<br>
     * 
     * onload��NULL�ŁAonLoadBody,onunload,styleClass,bgcolor,
     * background,text,link,vlink,alink�̂��ׂĂɒl������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag06() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ɋ֘A�t�����ꂽ(Mock)HttpServletRequest���擾
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();

        // ���N�G�X�g��ON_LOAD������ݒ�
        req.setAttribute(BodyTag.ON_LOAD_KEY, "onLoadBody");

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "onload", null);
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // �J�X�^���^�O��ContextPath��ݒ�
        String contextPath = "contextPath";
        TagUTUtil.setContextPath(tag, contextPath);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_BODY_INCLUDE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<body onLoad=\"__onLoad__()\" onUnLoad=\"onunload\" "
                + "class=\"styleClass\" bgcolor=\"bgcolor\" "
                + "background=\"contextPath/background\" "
                + "text=\"text\" link=\"link\" vlink=\"vlink\" "
                + "alink=\"alink\">", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals("function __onLoad__() {", reader.readLine());
        assertEquals("onLoadBody}", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());

    } /* testDoStartTag06 End */

    /**
     * testDoEndTag01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * out=Not Null<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_PAGE<br>
     * �o�͓��e=������u"</body>"�v<br>
     * 
     * HTML��BODY�^�O������ɏo�͂��ꂽ�ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoEndTag01() throws Exception {

        // �e�X�g���s
        int result = tag.doEndTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.EVAL_PAGE);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("</body>", reader.readLine());

    } /* testDoEndTag01 End */

    /**
     * testDoEndTag02�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * 
     * ���Ғl
     * �߂�l:int=JspTagException<br>
     * �o�͓��e=-<br>
     * 
     * �o�͎���IOException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoEndTag02() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        // �e�X�g���s
        try {
            tag.doEndTag();
            fail();
        } catch (JspTagException ex) {
            assertEquals("javax.servlet.jsp.JspTagException:"
                    + " java.io.IOException", ex.toString());
            return;
        }
    } /* testDoEndTag02 End */

    /**
     * testRelease01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * onload=*<br>
     * onunload=*<br>
     * styleClass=*<br>
     * bgcolor=*<br>
     * background=*<br>
     * text=*<br>
     * link=*<br>
     * vlink=*<br>
     * alink=*<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * onload=Null<br>
     * onunload=Null<br>
     * styleClass=Null<br>
     * bgcolor=Null<br>
     * background=Null<br>
     * text=Null<br>
     * link=Null<br>
     * vlink=Null<br>
     * alink=Null<br>
     * 
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l��
     * ����������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "onload", "onload");
        UTUtil.setPrivateField(tag, "onunload", "onunload");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "bgcolor", "bgcolor");
        UTUtil.setPrivateField(tag, "background", "background");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "link", "link");
        UTUtil.setPrivateField(tag, "vlink", "vlink");
        UTUtil.setPrivateField(tag, "alink", "alink");

        // �e�X�g���s
        tag.release();

        // �e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(tag, "onload"));
        assertNull(UTUtil.getPrivateField(tag, "onunload"));
        assertNull(UTUtil.getPrivateField(tag, "styleClass"));
        assertNull(UTUtil.getPrivateField(tag, "bgcolor"));
        assertNull(UTUtil.getPrivateField(tag, "background"));
        assertNull(UTUtil.getPrivateField(tag, "text"));
        assertNull(UTUtil.getPrivateField(tag, "link"));
        assertNull(UTUtil.getPrivateField(tag, "vlink"));
        assertNull(UTUtil.getPrivateField(tag, "alink"));

    } /* testRelease01 End */

    /**
     * testSetOnload01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l onload="onload"<br>
     * 
     * ���Ғl �߂�l:void<br>
     * onload="onload"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetOnload01() throws Exception {
        // �e�X�g���s
        tag.setOnload("onload");

        // �e�X�g���ʊm�F
        assertEquals("onload", UTUtil.getPrivateField(tag, "onload"));

    } /* testSetOnload01 End */

    /**
     * testSetOnunload01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * onunload="onunload"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * onunload="onunload"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetOnunload01() throws Exception {
        // �e�X�g���s
        tag.setOnunload("onunload");

        // �e�X�g���ʊm�F
        assertEquals("onunload", UTUtil.getPrivateField(tag, "onunload"));

    } /* testSetOnunload01 End */

    /**
     * testSetStyleClass01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * styleClass="styleClass"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * styleClass="styleClass"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetStyleClass01() throws Exception {
        // �e�X�g���s
        tag.setStyleClass("styleClass");

        // �e�X�g���ʊm�F
        assertEquals("styleClass", UTUtil.getPrivateField(tag, "styleClass"));

    } /* testSetStyleClass01 End */

    /**
     * testSetBgcolor01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * bgcolor="bgcolor"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * bgcolor="bgcolor"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetBgcolor01() throws Exception {
        // �e�X�g���s
        tag.setBgcolor("bgcolor");

        // �e�X�g���ʊm�F
        assertEquals("bgcolor", UTUtil.getPrivateField(tag, "bgcolor"));

    } /* testSetBgcolor01 End */

    /**
     * testSetBackground01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * background="background"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * background="background"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetBackground01() throws Exception {
        // �e�X�g���s
        tag.setBackground("background");

        // �e�X�g���ʊm�F
        assertEquals("background", UTUtil.getPrivateField(tag, "background"));

    } /* testSetBackground01 End */

    /**
     * testSetText01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * text="text"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * text="text"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetText01() throws Exception {
        // �e�X�g���s
        tag.setText("text");

        // �e�X�g���ʊm�F
        assertEquals("text", UTUtil.getPrivateField(tag, "text"));

    } /* testSetText01 End */

    /**
     * testSetLink01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * link="link"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * link="link"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetLink01() throws Exception {
        // �e�X�g���s
        tag.setLink("link");

        // �e�X�g���ʊm�F
        assertEquals("link", UTUtil.getPrivateField(tag, "link"));

    } /* testSetLink01 End */

    /**
     * testSetVlink01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * vlink="vlink"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * vlink="vlink"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetVlink01() throws Exception {
        // �e�X�g���s
        tag.setVlink("vlink");

        // �e�X�g���ʊm�F
        assertEquals("vlink", UTUtil.getPrivateField(tag, "vlink"));

    } /* testSetVlink01 End */

    /**
     * testSetAlink01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * alink="alink"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * alink="alink"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetAlink01() throws Exception {
        // �e�X�g���s
        tag.setAlink("alink");

        // �e�X�g���ʊm�F
        assertEquals("alink", UTUtil.getPrivateField(tag, "alink"));

    } /* testSetAlink01 End */

} /* BodyTagTest Class End */
