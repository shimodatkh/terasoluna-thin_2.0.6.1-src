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

import java.net.MalformedURLException;
import java.util.Arrays;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.taglib.logic.IterateTag;

/**
 * LinkTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class LinkTagTest extends TestCase {

    // �e�X�g�Ώ�
    LinkTag tag = null;

    /**
     * Constructor for LinkTagTest.
     * @param arg0
     */
    public LinkTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (LinkTag) TagUTUtil.create(LinkTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testCalculateURL01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) IterateTag:�ݒ肠��A<br>
     *                started=true<br>
     *                lengthCount=6<br>
     *         (���) href:not null("/href.do")<br>
     *         (���) indexed:true<br>
     *         (���) indexId:not null("number")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) string:�u/href.do?r=[�����_���Ȑ��l]&amp;number=5�v<br>
     *         
     * <br>
     * IterateTag��not null�Aindexed������true�AindexId������"number"�̏ꍇ�AURL�ɃL���b�V�������p�����_��ID�A�p�����[�^��"number"�ŃC���f�b�N�X�ԍ����t�^����Ă��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCalculateURL01() throws Exception {

        IterateTag iterateTag = new IterateTag();

        // IterateTag�̃C���f�b�N�X�ԍ���ݒ肷��
        UTUtil.setPrivateField(iterateTag, "started", Boolean.TRUE);
        UTUtil.setPrivateField(iterateTag, "lengthCount", Integer.valueOf("6"));

        // IterateTag��ݒ�
        tag.setParent(iterateTag);

        // LinkTag�ݒ�
        tag.setHref("/href.do");
        tag.setIndexed(true);
        tag.setIndexId("number");

        // �e�X�g���s
        String url = tag.calculateURL();
        // URL�̃A�T�[�g
        String[] urlComposition = url.split("\\?");
        assertEquals("/href.do", urlComposition[0]);
        String[] urlParamaters = urlComposition[1].split("&amp;");
        // LinkTag��URL�p�����[�^�̐�����HashMap���g�p���Ă���A���������ۏ؂���Ȃ����߁A�\�[�g���Ă���A�T�[�g
        Arrays.sort(urlParamaters);
        assertEquals("number=5", urlParamaters[0]);
        assertTrue(urlParamaters[1].startsWith("r="));

    }

    /**
     * testCalculateURL02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) IterateTag:�ݒ肠��A<br>
     *                started=true<br>
     *                lengthCount=6<br>
     *         (���) href:not null("/href.do")<br>
     *         (���) indexed:true<br>
     *         (���) indexId:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) string:�u/href.do?index=5&amp;r=[�����_���Ȑ��l]�v<br>
     *         
     * <br>
     * IterateTag��not null�Aindexed������true�AindexId������null�̏ꍇ�AURL�ɃL���b�V�������p�����_��ID�A�p�����[�^��"index"�ŃC���f�b�N�X�ԍ����t�^����Ă��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCalculateURL02() throws Exception {

        IterateTag iterateTag = new IterateTag();

        // IterateTag�̃C���f�b�N�X�ԍ���ݒ肷��
        UTUtil.setPrivateField(iterateTag, "started", Boolean.TRUE);
        UTUtil.setPrivateField(iterateTag, "lengthCount", Integer.valueOf("6"));

        // IterateTag��ݒ�
        tag.setParent(iterateTag);

        // LinkTag�ݒ�
        tag.setHref("/href.do");
        tag.setIndexed(true);
        tag.setIndexId(null);

        // �e�X�g���s
        String url = tag.calculateURL();
        // URL�̃A�T�[�g
        String[] urlComposition = url.split("\\?");
        assertEquals("/href.do", urlComposition[0]);
        String[] urlParamaters = urlComposition[1].split("&amp;");
        // LinkTag��URL�p�����[�^�̐�����HashMap���g�p���Ă���A���������ۏ؂���Ȃ����߁A�\�[�g���Ă���A�T�[�g
        Arrays.sort(urlParamaters);
        assertEquals("index=5", urlParamaters[0]);
        assertTrue(urlParamaters[1].startsWith("r="));

    }

    /**
     * testCalculateURL03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) IterateTag:�ݒ�Ȃ��B<br>
     *         (���) href:not null("/href.do")<br>
     *         (���) indexed:false<br>
     *         (���) indexId:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) string:�u/href.do?r=[�����_���Ȑ��l]�v<br>
     *         
     * <br>
     * IterateTag�̐ݒ�Ȃ��Aindexed������false�AindexId������null�̏ꍇ�AURL�ɃL���b�V�������p�����_��ID���t�^����Ă��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCalculateURL03() throws Exception {

        // LinkTag�ݒ�
        tag.setHref("/href.do");
        tag.setIndexed(false);
        tag.setIndexId(null);

        // �e�X�g���s
        String url = tag.calculateURL();
        assertEquals(
            "/href.do?r=",
            url.substring(0, url.indexOf("r=") + 2));
        assertNotNull(url.substring(url.indexOf("r=")));

    }

    /**
     * testCalculateURL04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) IterateTag:�ݒ�Ȃ��B<br>
     *         (���) href:not null("/href.do")<br>
     *         (���) indexed:true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *                    ���b�Z�[�W�F"indexed="true"�̎w���iterate�^�O�ň͂܂ꂽ���ł̂ݗL���ł�"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"iterateTag is null."<br>
     *         
     * <br>
     * IterateTag�̐ݒ�Ȃ��Aindexed������true�̏ꍇ�AJspException���������A�G���[���b�Z�[�W���i�[����Ă��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCalculateURL04() throws Exception {

        // LinkTag�ݒ�
        tag.setHref("/href.do");
        tag.setIndexed(true);

        // �e�X�g���s
        try {
            tag.calculateURL();
            fail();
        } catch (JspException e) {
            assertEquals(e.getMessage(),
                    "indexed=\"true\"�̎w���iterate�^�O�ň͂܂ꂽ���ł̂ݗL���ł�");
            PageContext pageContext = (PageContext) UTUtil.getPrivateField(tag,
                    "pageContext");
            Throwable throwable = (Throwable) pageContext.getAttribute(
                    Globals.EXCEPTION_KEY, PageContext.REQUEST_SCOPE);
            assertEquals(JspException.class.getName(), throwable.getClass()
                    .getName());
            assertTrue(LogUTUtil.checkError("iterateTag is null."));
        }

    }

    /**
     * testCalculateURL05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) IterateTag:�ݒ�Ȃ��B<br>
     *         (���) href:null<br>
     *         (���) indexId:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *                    ���b�Z�[�W�F"�����C�gURL�𐶐��ł��܂���: java.net.MalformedURLException:  "forward","href","page","action"�̂����̈���w�肷��K�v������܂�"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"Malformed URL has occurred."<br>
     *         
     * <br>
     * forward�Ahref�Apage�Aaction��������������ݒ肵�Ă��Ȃ������ꍇ�AJspException����������A�G���[���b�Z�[�W���i�[����Ă��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCalculateURL05() throws Exception {

        // �e�X�g���s
        try {
            tag.calculateURL();
            fail();
        } catch (JspException e) {
            assertEquals(e.getMessage(), "�����C�gURL�𐶐��ł��܂���: "
                    + "java.net.MalformedURLException: "
                    + "\"forward\", \"href\",\"page\",\"action\""
                    + " �̂����̈���w�肷��K�v������܂�");
            PageContext pageContext = (PageContext) UTUtil.getPrivateField(tag,
                    "pageContext");
            Throwable throwable = (Throwable) pageContext.getAttribute(
                    Globals.EXCEPTION_KEY, PageContext.REQUEST_SCOPE);
            assertEquals(MalformedURLException.class.getName(), throwable
                    .getClass().getName());
            LogUTUtil.checkError("Malformed URL has occurred.");
        }

    }

    /**
     * testCalculateURL06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) href:"/href.do"<br>
     *         (���) action:"/action.do"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:JspException<br>
     *                    ���b�Z�[�W�F"�����C�gURL�𐶐��ł��܂���: java.net.MalformedURLException:  "forward","href","page","action"�̂����̈���w�肷��K�v������܂�"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"Malformed URL has occurred."<br>
     *         
     * <br>
     * forward�Ahref�Apage�Aaction�����������ݒ肵�Ă���ꍇ�A<br>
     * JspException���������邱�ƁA�G���[���b�Z�[�W���i�[����Ă��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCalculateURL06() throws Exception {

        // LinkTag�ݒ�
        tag.setHref("/href.do");
        tag.setAction("/action.do");

        // �e�X�g���s
        try {
            tag.calculateURL();
            fail();
        } catch (JspException e) {
            assertEquals(e.getMessage(), "�����C�gURL�𐶐��ł��܂���: "
                    + "java.net.MalformedURLException: "
                    + "\"forward\", \"href\",\"page\",\"action\""
                    + " �̂����̈���w�肷��K�v������܂�");
            PageContext pageContext = (PageContext) UTUtil.getPrivateField(tag,
                    "pageContext");
            Throwable throwable = (Throwable) pageContext.getAttribute(
                    Globals.EXCEPTION_KEY, PageContext.REQUEST_SCOPE);
            assertEquals(MalformedURLException.class.getName(), throwable
                    .getClass().getName());
            LogUTUtil.checkError("Malformed URL has occurred.");
        }

    }

}
