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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import jp.terasoluna.utlib.exception.Exception_PageContextImpl;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.taglib.logic.IterateTag;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * SubmitTag �u���b�N�{�b�N�X�e�X�g<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class SubmitTagTest extends TestCase {

    //�e�X�g�Ώ�
    SubmitTag tag = null;

    /**
     * Constructor for SubmitTagTest.
     * @param arg0
     */
    public SubmitTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (SubmitTag) TagUTUtil.create(SubmitTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoEndTag01<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     * indexed = false<br>
     *
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     *
     * �T�u�~�b�g�^�O�̃{�^�����x���ɃX�[�p�[�N���X�́htext�h���g�p�A
     * �^�[�Q�b�g�̎w�菈�����s���A�X�[�p�[�N���X�́haccesskey�h�����
     * �htabindex�h�̎w����s���A�y�[�W�Ɋ֘A�t����ꂽ���C�^��
     * �������ޏꍇ�̃e�X�g�N���X<br>
     * indexed=false�̏ꍇ�Aname�̒l���z��^�ŏo�͂���Ȃ����Ƃ̊m�F<br>
     */
    public void testDoEndTag01() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", "accesskey");
        UTUtil.setPrivateField(tag, "tabindex", "tabindex");
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //�e�X�g���s
        int result = tag.doEndTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals(
            "  function __setFrameTarget(__frTarget) {",
            reader.readLine());
        assertEquals(
            "    document.MAPPING_TEST_FORM.target = __frTarget;",
            reader.readLine());
        assertEquals("  }", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals(
            "<input type=\"submit\" name=\"submit\" "
                + "accesskey=\"accesskey\" "
                + "tabindex=\"tabindex\" value=\"text\" "
                + "onclick=\"__setFrameTarget('target');\"/>",
            reader.readLine());
        assertEquals("frameScript", (String) pc.getAttribute("frameScript"));

    } /* testDoEndTag01 End */

    /**
     * testDoEndTag02<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     *
     * ���͒l
     * value(label)="label"<br>
     * text=*<br>
     * target=Not Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=Not Null<br>
     * property= Not Null<br>
     * indexed=true<br>
     *
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������
     * �u<input type="submit" name="[property]" 
     * value="[label]" [EventHandlers] [onclick] />�v<br>
     * onclick�������O������ŃZ�b�g�����l�Ɠ����ł��邱�ƁB<br>
     *
     * �T�u�~�b�g�^�O�̃{�^�����x���͎w�肳�ꂽ��������g�p�A
     * �X�[�p�[�N���X��getOnclick���\�b�h���擾�����l��t�^���A
     * �y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ޏꍇ�̃e�X�g�N���X<br>
     * indexed=true�̏ꍇ�Aname�̒l���z��^�ŏo�͂���邱�Ƃ̊m�F<br>
     */
    public void testDoEndTag02() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", "label");
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "target", "target");
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        tag.setOnclick("onclick");
        UTUtil.setPrivateField(tag, "property", "submit");
        tag.setIndexed(true);
        tag.setParent(new IterateTag());

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //�e�X�g���s
        int result = tag.doEndTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit[0]\" "
                + "value=\"label\" "
                + "onclick=\"__setFrameTarget('target');onclick\"/>",
            reader.readLine());
        assertEquals("onclick", UTUtil.getPrivateField(tag, "onclick"));

    } /* testDoEndTag02 End */

    /**
     * testDoEndTag03<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text=Null<br>
     * target=Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=*<br>
     * property= Not Null<br>
     * indexed=false<br>
     *
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������u<input type="submit" name="[property]" 
     * accesskey="[accesskey]" tabindex="[tabindex]" 
     * value="[label]" [EventHandlers] [Styles] />�v<br>
     *
     * �T�u�~�b�g�^�O�̃{�^�����x���́A
     * �����w�肪�Ȃ����ߕ�����hsubmit�h���g�p���A
     * �T�u�~�b�g�{�^���^�O�̕�����𐶐����A
     * �y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ޏꍇ�̃e�X�g�N���X<br>
     * indexed=false�̏ꍇ�Aname�̒l���z��^�ŏo�͂���Ȃ����Ƃ̊m�F<br>
     */
    public void testDoEndTag03() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", null);
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //�e�X�g���s
        int result = tag.doEndTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit\" " + "value=\"Submit\"/>",
            reader.readLine());

    } /* testDoEndTag03 End */

    /**
     * testDoEndTag04<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text=""<br>
     * target=Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=*<br>
     * property= Not Null<br>
     * indexed=true<br>
     *
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������u<input type="submit" 
     * name="[property]" accesskey="[accesskey]" 
     * tabindex="[tabindex]" value="[label]" 
     * [EventHandlers] [Styles] />�v<br>
     *
     * �w�肳�ꂽ�T�u�~�b�g�^�O�̃{�^�����x�����󔒕�����̂��߁A
     * ���x���ɂ͕�����hsubmit�h���g�p���A
     * �T�u�~�b�g�{�^���^�O�̕�����𐶐����A
     * �y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ޏꍇ�̃e�X�g�N���X<br>
     * indexed=true�̏ꍇ�Aname�̒l���z��^�ŏo�͂���邱�Ƃ̊m�F<br>
     */
    public void testDoEndTag04() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", "");
        UTUtil.setPrivateField(tag, "target", null);
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");
        tag.setIndexed(true);
        tag.setParent(new IterateTag());

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //�e�X�g���s
        int result = tag.doEndTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit[0]\" " + "value=\"Submit\"/>",
            reader.readLine());

    } /* testDoEndTag04 End */

    /**
     * testDoEndTag05<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     *
     * ���͒l
     * value(label)=""<br>
     * text=*<br>
     * target=Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=*<br>
     * property= Not Null<br>
     * indexed=false<br>
     *
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������u<input type="submit" name="[property]" 
     * accesskey="[accesskey]" tabindex="[tabindex]" 
     * value="[label]" [EventHandlers] [Styles] />�v<br>
     *
     * �w�肳�ꂽ�T�u�~�b�g�^�O�̃{�^�����x�����󔒕�����̂��߁A
     * ���x���ɂ͕�����hsubmit�h���g�p���A
     * �T�u�~�b�g�{�^���^�O�̕�����𐶐����A
     * �y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ޏꍇ�̃e�X�g�N���X<br>
     * indexed=false�̏ꍇ�Aname�̒l���z��^�ŏo�͂���Ȃ����Ƃ̊m�F<br>
     */
    public void testDoEndTag05() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", "");
        UTUtil.setPrivateField(tag, "text", "CLICK!!");
        UTUtil.setPrivateField(tag, "target", null);
        pc.setAttribute("frameScript", "frameScript");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //�e�X�g���s
        int result = tag.doEndTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals(
            "<input type=\"submit\" name=\"submit\" " + "value=\"Submit\"/>",
            reader.readLine());

    } /* testDoEndTag05 End */

    /**
     * testDoEndTag06<br>
     *
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text=Null<br>
     * target=Not Null<br>
     * frameScript=*<br>
     * accesskey=*<br>
     * tabindex=*<br>
     * onClick=Null<br>
     * property= Not Null<br>
     *
     * ���Ғl
     * �߂�l:int=NullPointerException<br>
     * �o�͓��e=-<br>
     *
     * �y�[�W�R���e�L�X�g����hframeScript�h�擾����
     * NullPointerException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoEndTag06() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

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
            tag.doEndTag();
            fail();
        } catch (NullPointerException ex) {
            //�e�X�g���ʊm�F
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoEndTag06 End */

    /**
     * testDoEndTag07<br>
     *
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text=Null<br>
     * target=Not Null<br>
     * frameScript=*<br>
     * accesskey=*<br>
     * tabindex=*<br>
     * onClick=Null<br>
     * property= Not Null<br>
     *
     * ���Ғl
     * �߂�l:int=IllegalArgumentException<br>
     * �o�͓��e=-<br>
     *
     * �y�[�W�R���e�L�X�g����hframeScript�h�擾����
     * IllegalArgumentException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoEndTag07() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

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
            tag.doEndTag();
            fail();
        } catch (IllegalArgumentException ex) {

            //�e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), ex
                    .getClass().getName());
            return;
        }
    } /* testDoEndTag07 End */

    /**
     * testDoEndTag08<br>
     *
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     *
     * ���Ғl
     * �߂�l:int=NullPointerException<br>
     * �o�͓��e=-<br>
     *
     * �y�[�W�R���e�L�X�g�ցhframeScript�h�̃Z�b�g����
     * NullPointerException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoEndTag08() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setNullPointerEx();
        pc2.setTiming(2);
        tag.setPageContext(pc2);

        //�e�X�g���s
        try {
            tag.doEndTag();
            fail();
        } catch (NullPointerException ex) {

            //�e�X�g���ʊm�F
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoEndTag08 End */

    /**
     * testDoEndTag09<br>
     *
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     *
     * ���Ғl
     * �߂�l:int=IllegalArgumentException<br>
     * �o�͓��e=-<br>
     *
     * �y�[�W�R���e�L�X�g�ցhframeScript�h�̃Z�b�g����
     * IllegalArgumentException�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoEndTag09() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());
        pc2.setIllegalArgumentEx();
        pc2.setTiming(2);
        tag.setPageContext(pc2);

        //�e�X�g���s
        try {
            tag.doEndTag();
            fail();
        } catch (IllegalArgumentException ex) {

            //�e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class.getName(), ex
                    .getClass().getName());
            return;
        }
    } /* testDoEndTag09 End */

    /**
     * testDoEndTag10<br>
     *
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text=Null<br>
     * target=Null<br>
     * frameScript=*<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=Null<br>
     * property= Not Null<br>
     *
     * ���Ғl
     * �߂�l:int=JapException<br>
     * �o�͓��e=-<br>
     *
     * ���C�^�ɏ������ނƂ���Exception�����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoEndTag10() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", null);
        UTUtil.setPrivateField(tag, "target", null);
        UTUtil.setPrivateField(tag, "accesskey", null);
        UTUtil.setPrivateField(tag, "tabindex", null);
        UTUtil.setPrivateField(tag, "property", "submit");

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pc, "jspWriter", out);

        //�e�X�g���s
        try {
            tag.doEndTag();
            fail();
        } catch (JspException ex) {

            //�e�X�g���ʊm�F
            assertEquals(JspException.class.getName(), ex.getClass().getName());
            assertEquals("���o�̓G���[: java.io.IOException", ex.getMessage());
            return;
        }
    } /* testDoEndTag10 End */

    /**
     * testDoEndTag11<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     *
     * ���͒l
     * value(label)=Null<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Null<br>
     * accesskey=Not Null<br>
     * tabindex=Not Null<br>
     * onClick=Null<br>
     * property= Null<br>
     *
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     *
     * �T�u�~�b�g�^�O�̃{�^�����x���ɃX�[�p�[�N���X�́htext�h���g�p�A
     * �^�[�Q�b�g�̎w�菈�����s���A�X�[�p�[�N���X�́haccesskey�h�����
     * �htabindex�h�̎w����s���A�y�[�W�Ɋ֘A�t����ꂽ���C�^��
     * �������ޏꍇ�̃e�X�g�N���X<br>
     * property= Null�̏ꍇ�� property�������o�͂��Ȃ��B<br>
     */
    public void testDoEndTag11() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "text", "text");
        UTUtil.setPrivateField(tag, "target", "target");
        UTUtil.setPrivateField(tag, "accesskey", "accesskey");
        UTUtil.setPrivateField(tag, "tabindex", "tabindex");
        UTUtil.setPrivateField(tag, "property", null);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("MAPPING_TEST_FORM");
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //�e�X�g���s
        int result = tag.doEndTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("", reader.readLine());
        assertEquals("<script type=\"text/javascript\">", reader.readLine());
        assertEquals("<!--", reader.readLine());
        assertEquals(
            "  function __setFrameTarget(__frTarget) {",
            reader.readLine());
        assertEquals(
            "    document.MAPPING_TEST_FORM.target = __frTarget;",
            reader.readLine());
        assertEquals("  }", reader.readLine());
        assertEquals("//-->", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals(
            "<input type=\"submit\" "
                + "accesskey=\"accesskey\" "
                + "tabindex=\"tabindex\" value=\"text\" "
                + "onclick=\"__setFrameTarget('target');\"/>",
            reader.readLine());
        assertEquals("frameScript", (String) pc.getAttribute("frameScript"));
        //property����"name="���o�͂��Ȃ��B
        reader = TagUTUtil.getOutputReader(tag);
        assertEquals(-1, reader.readLine().indexOf("name="));

    } /* testDoEndTag11 End */

    /**
     * testDoEndTag12<br>
     *
     * (����n)<br>
     * �ϓ_�FF<br>
     *
     * ���͒l
     * value(label)="label"<br>
     * text="text"<br>
     * target=Not Null<br>
     * frameScript=Not Null<br>
     * accesskey=Null<br>
     * tabindex=Null<br>
     * onClick=__setFrameTarget('target');hoge();<br>
     * property= Not Null<br>
     * indexed=true<br>
     *
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * �o�͓��e=������
     * �u<input type="submit" name="[property(�z��^)]"
     * value="[label]" [EventHandlers]/>�v<br>
     *
     * �T�u�~�b�g�^�O�̃{�^�����x���͎w�肳�ꂽ��������g�p�A
     * �X�[�p�[�N���X��getOnclick���\�b�h���擾�����l��
     * "__setFrameTarget"���܂܂�Ă���ꍇ�͐V�K��onClick������𐶐�������
     * �������A�y�[�W�Ɋ֘A�t����ꂽ���C�^�ɏ������ޏꍇ�̃e�X�g�N���X<br>
     * indexed=true�̏ꍇ�Aname�̒l���z��^�ŏo�͂���邱�Ƃ̊m�F<br>
     */
//    public void testDoEndTag12() throws Exception {
//
//        //�e�X�g�f�[�^�ݒ�
//        PageContext pc = TagUTUtil.getPageContext(tag);
//        UTUtil.setPrivateField(tag, "value", "label");
//        UTUtil.setPrivateField(tag, "text", "text");
//        UTUtil.setPrivateField(tag, "target", "target");
//        pc.setAttribute("frameScript", "frameScript");
//        UTUtil.setPrivateField(tag, "accesskey", null);
//        UTUtil.setPrivateField(tag, "tabindex", null);
//        tag.setOnclick("__setFrameTarget('target');hoge();");
//        UTUtil.setPrivateField(tag, "property", "submit");
//        tag.setIndexed(true);
//        tag.setParent(new IterateTag());
//
//        ActionMapping mapping = new ActionMapping();
//        mapping.setName("MAPPING_TEST_FORM");
//        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
//        req.setAttribute(Globals.MAPPING_KEY, mapping);
//
//        //�e�X�g���s
//        int result = tag.doEndTag();
//
//        //�e�X�g���ʊm�F
//        assertEquals(Tag.EVAL_PAGE, result);
//        BufferedReader reader = TagUTUtil.getOutputReader(tag);
//        assertEquals(
//            "<input type=\"submit\" name=\"submit[0]\" "
//                + "value=\"label\" "
//                + "onclick=\"__setFrameTarget('target');hoge();\"/>",
//            reader.readLine());
//
//    } /* testDoEndTag12 End */
    
    /**
     * testRelease01<br>
     *
     * (����n)<br>
     * �ϓ_�FC<br>
     *
     * ���͒l
     * target=*<br>
     *
     * ���Ғl
     * �߂�l:void<br>
     * target=Null<br>
     *
     * �O������Ƃ��Đݒ肵���e�l���A
     * ���s���Ɋe�����l������������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "target", "Jesus!!");

        //�e�X�g���s
        tag.release();

        //�e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(tag, "target"));

    } /* testRelease01 End */

    /**
     * testSetTarget01<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l
     * target="("target");"<br>
     *
     * ���Ғl
     * �߂�l:void<br>
     * target="("target");"<br>
     *
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetTarget01() throws Exception {
        //�e�X�g���s
        tag.setTarget("target");

        //�e�X�g���ʊm�F
        assertEquals("target", UTUtil.getPrivateField(tag, "target"));

    } /* testSetTarget01 End */

    /**
     * testGetTarget01<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l
     * target="("target");"<br>
     *
     * ���Ғl
     * �߂�l:String="("target");"<br>
     *
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetTarget01() throws Exception {
        //�e�X�g�ݒ�
        String value = "target";
        UTUtil.setPrivateField(tag, "target", value);

        //�e�X�g���s
        String result = tag.getTarget();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    } /* testGetTarget01 End */

} /* SubmitTagTest Class End */
