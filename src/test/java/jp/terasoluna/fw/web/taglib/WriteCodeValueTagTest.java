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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.SpringTestCase;

/**
 * {@link jp.terasoluna.fw.web.taglib.WriteCodeValue} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * writeCodeValue �^�O�̎����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.taglib.WriteCodeValue
 */
public class WriteCodeValueTagTest extends SpringTestCase {

    /**
     * �e�X�g�ΏۃC���X�^���X�B
     */
    private WriteCodeValueTag tag = null;


    @Override
    protected void doOnSetUp() throws Exception {
        this.tag = new WriteCodeValueTag();
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "jp/terasoluna/fw/web/taglib/WriteCodeValueTagTest.xml" };
    }

    /**
     * testDoStartTag01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:""<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�Z�[�W�F"codeList is required."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    "codeList is required."<br>
     *
     * <br>
     * codeList�����̒l���󕶎��̂Ƃ��AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag01() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "codeList is required.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�Z�[�W�F"codeList is required."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    "codeList is required."<br>
     *
     * <br>
     * codeList�����̒l��null�̂Ƃ��AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag02() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", null);
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "codeList is required.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:"hoge"<br>
     *         (���) key:null<br>
     *         (���) name:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�Z�[�W�F"key and name is required."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    "key and name is required."<br>
     *
     * <br>
     * key�����Aname�������Ƃ���null�̏ꍇ�AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag03() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", null);
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "key and name is required.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:"hoge"<br>
     *         (���) key:null<br>
     *         (���) name:"hogeBean"<br>
     *         (���) scope:session<br>
     *         (���) scope�����Ŏw�肳�ꂽ�X�R�[�v:session�X�R�[�v��"hogeBean"�Ƃ������O�̃I�u�W�F�N�g�����݂��Ȃ��B<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�Z�[�W�F"bean id:hogeBean is not defined."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    "bean id:hogeBean is not defined."<br>
     *
     * <br>
     * name�����Ɏw�肳�ꂽBean�����݂��Ȃ��ꍇ�AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag04() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "scope", "session");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "bean id:hogeBean is not defined.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:"hoge"<br>
     *         (���) key:null<br>
     *         (���) name:"hogeBean"<br>
     *         (���) property:"hogeProperty"<br>
     *         (���) scope:request<br>
     *         (���) scope�����Ŏw�肳�ꂽ�X�R�[�v:request�X�R�[�v��"hogeBean"�����݂��邪�AhogeBean��hogeProperty�����݂��Ȃ��B<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�Z�[�W�F"Cannot get property[hogeBean.hogeProperty]."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    "Cannot get property[hogeBean.hogeProperty]."<br>
     *
     * <br>
     * property�����Ɏw�肳�ꂽ�v���p�e�B���擾�ł��Ȃ��ꍇ�AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag05() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "request");
        HttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("hogeBean", "hogeBeanString");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext(null, req);
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "Cannot get property[hogeBean.hogeProperty].";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:"hoge"<br>
     *         (���) key:null<br>
     *         (���) name:"hogeBean"<br>
     *         (���) property:"hogeProperty"<br>
     *         (���) scope:page<br>
     *         (���) scope�����Ŏw�肳�ꂽ�X�R�[�v:page�X�R�[�v��"hogeBean"��hogeProperty�̒l��"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:ApplicationContext����擾�ł��Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�Z�[�W�F"CodeListLoader:hoge is not defined."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    "CodeListLoader:hoge is not defined."<br>
     *
     * <br>
     * codeListLoader�C���X�^���X���擾�ł��Ȃ��ꍇ�AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag06() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "CodeListLoader:hoge is not defined.";
            assertEquals(message, e.getMessage());
            assertTrue(LogUTUtil.checkError(message));
            assertFalse(out.isCalled);
        }

    }

    /**
     * testDoStartTag07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(���) codeList:"hoge1"<br>
     *         (���) key:null<br>
     *         (���) name:"hogeBean"<br>
     *         (���) property:"hogeProperty"<br>
     *         (���) scope:page<br>
     *         (���) scope�����Ŏw�肳�ꂽ�X�R�[�v:page�X�R�[�v��"hogeBean"��hogeProperty�̒l��"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:getCodeBeans�̌��ʂ�null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         (��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ���O:���O���x���F�x��<br>
     *                    "Codebean is null. CodeListLoader(bean id:hoge1)"<br>
     *
     * <br>
     * codeListLoader����CodeBean���擾�ł��Ȃ��ꍇ�A�x�����O���o�͂��ď����I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag07() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge1");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        int result = tag.doStartTag();
        assertEquals(1, result);
        String message = "Codebean is null. CodeListLoader(bean id:hoge1)";
        assertTrue(LogUTUtil.checkWarn(message));
        assertFalse(out.isCalled);

    }

    /**
     * testDoStartTag08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) codeList:"hoge2"<br>
     *         (���) key:null<br>
     *         (���) name:"hogeBean"<br>
     *         (���) property:"hogeProperty"<br>
     *         (���) scope:page<br>
     *         (���) scope�����Ŏw�肳�ꂽ�X�R�[�v:page�X�R�[�v��"hogeBean"��hogeProperty�̒l��"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:CodeBean[] {<br>
     *                 "000"="�R�[�h0"<br>
     *                }<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         (��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *
     * <br>
     * �R�[�h�l�Ɉ�v����CodeBean�����݂��Ȃ��ꍇ�A�����o�͂����ɏ����I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag08() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge2");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertFalse(out.isCalled);

    }

    /**
     * testDoStartTag09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) codeList:"hoge3"<br>
     *         (���) key:"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:CodeBean[] {<br>
     *                 "000"="�R�[�h0",<br>
     *                 "002"="�R�[�h2",<br>
     *                 "003"="�R�[�h3"<br>
     *                }<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         (��ԕω�) out.print:�Ăяo����Ȃ�<br>
     *
     * <br>
     * �R�[�h�l�Ɉ�v����CodeBean�����݂��Ȃ��ꍇ�A�����o�͂����ɏ����I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag09() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge3");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertFalse(out.isCalled);

    }

    /**
     * testDoStartTag10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) codeList:"hoge4"<br>
     *         (���) key:null<br>
     *         (���) name:"hogeBean"<br>
     *         (���) property:"hogeProperty"<br>
     *         (���) scope:page<br>
     *         (���) scope�����Ŏw�肳�ꂽ�X�R�[�v:page�X�R�[�v��"hogeBean"��hogeProperty�̒l��"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:CodeBean[] {<br>
     *                 "001"="�R�[�h1"<br>
     *                }<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         (��ԕω�) out.print:����"�R�[�h1"�ŌĂяo�����B<br>
     *
     * <br>
     * �R�[�h�l�Ɉ�v����CodeBean�����݂���ꍇ�A���̒l���y�[�W�ɏo�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag10() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge4");
        UTUtil.setPrivateField(tag, "key", null);
        UTUtil.setPrivateField(tag, "name", "hogeBean");
        UTUtil.setPrivateField(tag, "property", "hogeProperty");
        UTUtil.setPrivateField(tag, "scope", "page");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        WriteCodeValueTag_JavaBeanStub01 hogeBean =
            new WriteCodeValueTag_JavaBeanStub01();
        hogeBean.setHogeProperty("001");
        context.setAttribute("hogeBean", hogeBean);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertTrue(out.isCalled);
        assertEquals("�R�[�h1", out.result);

    }

    /**
     * testDoStartTag11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) codeList:"hoge5"<br>
     *         (���) key:"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:CodeBean[] {<br>
     *                 "000"="�R�[�h0",<br>
     *                 "001"="�R�[�h1",<br>
     *                 "002"="�R�[�h2"<br>
     *                }<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         (��ԕω�) out.print:����"�R�[�h1"�ŌĂяo�����B<br>
     *
     * <br>
     * �R�[�h�l�Ɉ�v����CodeBean�����݂���ꍇ�A���̒l���y�[�W�ɏo�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag11() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge5");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub01 out =
            new WriteCodeValueTag_JspWriterStub01(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        int result = tag.doStartTag();
        assertEquals(1, result);
        assertTrue(out.isCalled);
        assertEquals("�R�[�h1", out.result);

    }

    /**
     * testDoStartTag12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:"hoge5"<br>
     *         (���) key:"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:CodeBean[] {<br>
     *                 "000"="�R�[�h0",<br>
     *                 "001"="�R�[�h1",<br>
     *                 "002"="�R�[�h2"<br>
     *                }<br>
     *         (���) out.print:IOException����<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:����"�R�[�h1"�ŌĂяo�����B<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ""<br>
     *                    ��O�FIOException<br>
     *
     * <br>
     * out.print��IOException����������ꍇ�AJspTagException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag12() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge5");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub02 out =
            new WriteCodeValueTag_JspWriterStub02(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(out.isCalled);
            assertEquals("�R�[�h1", out.result);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }

    }

    /**
     * testDoStartTag13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeList:"hoge6"<br>
     *         (���) key:"001"<br>
     *         (���) codeList�����Ŏw�肳�ꂽ���O��codeListLoader:codeListLoader�C���X�^���X�ł͂Ȃ�<br>
     *         (���) out.print:-<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) out.print:�Ăяo����Ȃ��B<br>
     *         (��ԕω�) ��O:JspTagException<br>
     *                    ���b�Z�[�W�F"bean id:hoge6 is not instance of CodeListLoader."
     *                    ���b�v������O�FClassCastException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"bean id:hoge6 is not instance of CodeListLoader."
     *
     * <br>
     * �w�肳�ꂽ���O�Ŏ擾����codeListLoader��CodeListLoader�C���X�^���X�ł͂Ȃ��ꍇ
     * JspTagException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag13() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "hoge6");
        UTUtil.setPrivateField(tag, "key", "001");
        WriteCodeValueTag_PageContext context =
            new WriteCodeValueTag_PageContext();
        WriteCodeValueTag_JspWriterStub02 out =
            new WriteCodeValueTag_JspWriterStub02(0, false);
        context.out = out;
        tag.setPageContext(context);
        UTUtil.setPrivateField(context, "servletContext", servletContext);

        // �e�X�g���{
        // ����
        try {
            tag.doStartTag();
            fail();
        } catch (JspTagException e) {
            String message = "bean id:hoge6 is not instance of CodeListLoader.";
            assertTrue(e.getRootCause() instanceof ClassCastException);
            assertEquals(message, e.getMessage());
            assertFalse(out.isCalled);
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testDoEndTag01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F
     * <br>
     * ���Ғl�F(�߂�l) int:TagSupport.EVAL_PAGE<br>
     *
     * <br>
     * TagSupport.EVAL_PAGE���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoEndTag01() throws Exception {
        assertEquals(Tag.EVAL_PAGE, tag.doEndTag());
    }

    /**
     * testRelease01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) codeList:"codeList"<br>
     *         (���) key:"key"<br>
     *         (���) name:"name"<br>
     *         (���) property:"property"<br>
     *         (���) scope:"scope"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeList:null<br>
     *         (��ԕω�) key:null<br>
     *         (��ԕω�) name:null<br>
     *         (��ԕω�) property:null<br>
     *         (��ԕω�) scope:null<br>
     *
     * <br>
     * �S�Ă̑����l��null�ɏ���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRelease01() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", "codeList");
        UTUtil.setPrivateField(tag, "key", "key");
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "property", "property");
        UTUtil.setPrivateField(tag, "scope", "scope");

        // �e�X�g���{
        tag.release();

        // ����
        assertNull(UTUtil.getPrivateField(tag, "codeList"));
        assertNull(UTUtil.getPrivateField(tag, "key"));
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "property"));
        assertNull(UTUtil.getPrivateField(tag, "scope"));
    }

    /**
     * testSetCodeList01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) codeList:"codeList"<br>
     *         (���) codeList:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeList:"codeList"<br>
     *
     * <br>
     * �����̒l��codeList�Ɋi�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCodeList01() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "codeList", null);

        // �e�X�g���{
        tag.setCodeList("codeList");

        // ����
        assertEquals("codeList", UTUtil.getPrivateField(tag, "codeList"));
    }

    /**
     * testSetName01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"name"<br>
     *         (���) name:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name:"name"<br>
     *
     * <br>
     * �����̒l��name�Ɋi�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName01() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "name", null);

        // �e�X�g���{
        tag.setName("name");

        // ����
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));
    }

    /**
     * testSetProperty01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"property"<br>
     *         (���) property:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) property:"property"<br>
     *
     * <br>
     * �����̒l��property�Ɋi�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetProperty01() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "property", null);

        // �e�X�g���{
        tag.setProperty("property");

        // ����
        assertEquals("property", UTUtil.getPrivateField(tag, "property"));
    }

    /**
     * testSetScope01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) scope:"scope"<br>
     *         (���) scope:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) scope:"scope"<br>
     *
     * <br>
     * �����̒l��scope�Ɋi�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetScope01() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "scope", null);

        // �e�X�g���{
        tag.setScope("scope");

        // ����
        assertEquals("scope", UTUtil.getPrivateField(tag, "scope"));
    }

    /**
     * testSetKey01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key"<br>
     *         (���) key:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) key:"key"<br>
     *
     * <br>
     * �����̒l��key�Ɋi�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetKey01() throws Exception {
        // �O����
        UTUtil.setPrivateField(tag, "key", null);

        // �e�X�g���{
        tag.setKey("key");

        // ����
        assertEquals("key", UTUtil.getPrivateField(tag, "key"));
    }

}
