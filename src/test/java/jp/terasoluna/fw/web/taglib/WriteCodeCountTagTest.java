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

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import jp.terasoluna.utlib.spring.SpringTestCase;

import com.mockrunner.mock.web.MockPageContext;
import com.mockrunner.mock.web.MockServletConfig;

/**
 * {@link jp.terasoluna.fw.web.taglib.WriteCodeCountTag}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * writeCodeCount �^�O�̎����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 */
@SuppressWarnings("unused")
public class WriteCodeCountTagTest extends SpringTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     *
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
    }

    @Override
    protected void doOnSetUp() throws Exception {

    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "jp/terasoluna/fw/web/taglib/WriteCodeCountTagTest.xml" };
    }

    /**
     * testDoStartTag01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) id:""<br>
     *         (���) out.print:������s<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspTagException<br>
     *                    ���b�v������O�Fnull<br>
     *                    ���b�Z�[�W�Fid is required<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    ���b�Z�[�W�Fid is required<br>
     *
     * <br>
     * �C���X�^���X�ϐ�id���󕶎��̏ꍇ�AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag01() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        // id=""
        UTUtil.setPrivateField(tag, "id", "");

        try {
            // �e�X�g���s
            int result = tag.doStartTag();
            // ����
            fail();

        } catch (JspTagException ex) {
            // ����
            // ���b�Z�[�W�`�F�b�N
            assertEquals("id is required.", ex.getMessage());
            // ���b�v������O�`�F�b�N
            assertNull(ex.getCause());
            // ���O�`�F�b�N
            assertTrue(LogUTUtil.checkError("id is required."));
        }

    }

    /**
     * testDoStartTag02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) id:testLoader02<br>
     *         (���) WebApplicationContext.getBean(id)<br>
     *                �iCodeListLoader�j:not null<br>
     *                getCodeBeans=null<br>
     *         (���) out.print:������s<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) �o�͓��e:"0"<br>
     *         (��ԕω�) ���O:���O���x���Fwarn<br>
     *                    ���b�Z�[�W�F"Codebean is null. CodeListLoader(bean id:testLoader)"<br>
     *
     * <br>
     * DI�R���e�i����擾����CodeListLoader��getCodeBeans��null��ԋp����ꍇ�A�y�[�W��0���o�͂��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag02() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext =
            (MockPageContext) TagUTUtil.getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // getCodeBeans=null
        UTUtil.setPrivateField(tag, "id", "testLoader02");

        // �e�X�g���s
        int result = tag.doStartTag();
        // ����
        // �߂�l
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // ���O�`�F�b�N
        assertTrue(LogUTUtil
                .checkWarn("Codebean is null. CodeListLoader(bean id:testLoader02)"));
        // �o�͓��e�`�F�b�N
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("0", reader);
    }

    /**
     * testDoStartTag03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) id:testLoader03<br>
     *         (���) WebApplicationContext.getBean(id)<br>
     *                �iCodeListLoader�j:not null<br>
     *                getCodeBeans={}�i��̔z��j<br>
     *         (���) out.print:������s<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) �o�͓��e:"0"<br>
     *
     * <br>
     * DI�R���e�i����擾����CodeListLoader��getCodeBeans����̔z���ԋp����ꍇ�A�y�[�W��0���o�͂��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag03() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);

        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);
        // getCodeBeans={}�i��̔z��j
        UTUtil.setPrivateField(tag, "id", "testLoader03");

        // �e�X�g���s
        int result = tag.doStartTag();
        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // �o�͓��e�`�F�b�N
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("0", reader);
    }

    /**
     * testDoStartTag04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) id:testLoader04<br>
     *         (���) WebApplicationContext.getBean(id)<br>
     *                �iCodeListLoader�j:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id","name")<br>
     *                }<br>
     *         (���) out.print:������s<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) �o�͓��e:"1"<br>
     *
     * <br>
     * DI�R���e�i����擾����CodeListLoader��getCodeBeans���v�f��1�̔z���ԋp����ꍇ�A�y�[�W��1���o�͂��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag04() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // getCodeBeans={CodeBean("id","name")}
        UTUtil.setPrivateField(tag, "id", "testLoader04");

        // �e�X�g���s
        int result = tag.doStartTag();
        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // �o�͓��e�`�F�b�N
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("1", reader);
    }

    /**
     * testDoStartTag05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) id:testLoader05<br>
     *         (���) WebApplicationContext.getBean(id)<br>
     *                �iCodeListLoader�j:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id1","name1"),<br>
     *                CodeBean("id2","name2"),<br>
     *                CodeBean("id3","name3"),<br>
     *                }<br>
     *         (���) out.print:������s<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:TagSupport.EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) �o�͓��e:"3"<br>
     *
     * <br>
     * DI�R���e�i����擾����CodeListLoader��getCodeBeans�������̗v�f���̔z���ԋp����ꍇ�A�y�[�W�ɗv�f�����o�͂��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag05() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // getCodeBeans={
        //     CodeBean("id1","name1"),
        //     CodeBean("id2","name2"),
        //     CodeBean("id3","name3"),
        // }
        UTUtil.setPrivateField(tag, "id", "testLoader05");

        // �e�X�g���s
        int result = tag.doStartTag();
        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);
        // �o�͓��e�`�F�b�N
        String reader = TagUTUtil.getOutputString(tag);
        assertEquals("3", reader);
    }

    /**
     * testDoStartTag06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) id:testLoader06<br>
     *         (���) WebApplicationContext.getBean(id)<br>
     *                �iCodeListLoader�j:not null<br>
     *                CodeListLoader���������Ȃ��N���X�̃C���X�^���X�B<br>
     *         (���) out.print:������s<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspTagException<br>
     *                    ���b�v������O�FClassCastException<br>
     *                    ���b�Z�[�W�Fbean id:testLoader is not instance of CodeListLoader<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    ���b�Z�[�W�Fbean id:testLoader is not instance of CodeListLoader<br>
     *
     * <br>
     * DI�R���e�i����擾�����C���X�^���X��CodeListLoader�����������N���X�̃C���X�^���X�ł͂Ȃ��ꍇ�AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag06() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        // ���s����
        int result = -1;
        // CodeListLoader���������Ȃ��N���X�̃C���X�^���X�B
        UTUtil.setPrivateField(tag, "id", "testLoader06");
        try {
            // �e�X�g���s
            result = tag.doStartTag();
            fail();
        } catch (JspTagException ex) {
            // ����
            // ���b�v������O�`�F�b�N
            assertEquals(ClassCastException.class.getName(),
                    ex.getRootCause().getClass().getName());

            // ���b�Z�[�W�L�[�`�F�b�N
            assertEquals(
                    "bean id:testLoader06 is not instance of CodeListLoader.",
                    ex.getMessage());
            // ���O�`�F�b�N
            assertTrue(LogUTUtil
                    .checkError("bean id:testLoader06 is not instance of CodeListLoader."));
        }

    }

    /**
     * testDoStartTag07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) id:testLoader07<br>
     *         (���) WebApplicationContext.getBean(id)<br>
     *                �iCodeListLoader�j:not null<br>
     *                getCodeBeans={<br>
     *                CodeBean("id","name")<br>
     *                }<br>
     *         (���) out.print:IOException����<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspTagException<br>
     *                    ���b�v������O�Fnull<br>
     *         (��ԕω�) ���O:���O���x���Ferror<br>
     *                    ���b�Z�[�W�FIOException caused.<br>
     *
     * <br>
     * JspWriter����IOException�����������ꍇ�AJspTagException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag07() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);
        MockPageContext pageContext = (MockPageContext) TagUTUtil
                .getPageContext(tag);
        MockServletConfig config = new MockServletConfig();
        config.setServletContext(servletContext);
        UTUtil.setPrivateField(pageContext, "config", config);

        Exception_JspWriterImpl out = new Exception_JspWriterImpl();
        out.setTrue();
        out.setTiming(1);
        UTUtil.setPrivateField(pageContext, "jspWriter", out);


        // ���s����
        int result = -1;
        // CodeListLoader���������Ȃ��N���X�̃C���X�^���X�B
        UTUtil.setPrivateField(tag, "id", "testLoader07");

        try {
            // �e�X�g���s
            result = tag.doStartTag();
            fail();
        } catch (JspTagException ex) {
            // ����
            // ���b�v������O�`�F�b�N
            assertNull(ex.getRootCause());
            // ���O�`�F�b�N
            assertTrue(LogUTUtil
                    .checkError("IOException caused."));
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
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);

        // �e�X�g���{
        int result = tag.doEndTag();
        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
    }

    /**
     * testRelease01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) id:"id"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) id:null<br>
     *
     * <br>
     * id��null�ɂȂ邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRelease01() throws Exception {
        // �O����
        WriteCodeCountTag tag = null;
        tag = (WriteCodeCountTag) TagUTUtil.create(WriteCodeCountTag.class);

        UTUtil.setPrivateField(tag, "id", "id");

        // �e�X�g���{
        tag.release();
        // �e�X�g���ʊm�F
        assertNull(UTUtil.getPrivateField(tag, "id"));
    }
}
