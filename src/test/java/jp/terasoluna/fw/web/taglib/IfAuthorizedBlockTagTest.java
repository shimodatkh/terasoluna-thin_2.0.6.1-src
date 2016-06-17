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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_BodyContentImpl;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

/**
 * IfAuthorizedBlockTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * -<br>
 */
public class IfAuthorizedBlockTagTest extends TestCase {

    // �e�X�g�ΏۃN���X����
    IfAuthorizedBlockTag tag = null;
    
    /**
     * Constructor for IterateRowSetTeiTest.
     * @param arg0
     */
    public IfAuthorizedBlockTagTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfAuthorizedBlockTag) TagUTUtil.create(
                IfAuthorizedBlockTag.class);
        LogUTUtil.flush();

    }

    /**
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testSetBlockId01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F"blockId"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetBlockId01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        String value = "blockId";

        // �e�X�g���s
        tag.setBlockId(value);

        // ���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "blockId");
        assertEquals(value, result);
    }

    /**
     * testSetParentBlockId01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F"parentBlockId"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetParentBlockId01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        String value = "parentBlockId";

        // �e�X�g���s
        tag.setParentBlockId(value);

        // ���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "parentBlockId");
        assertEquals(value, result);
    }

    /**
     * testRelease01�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l
     * this.blockId=*<br>
     * this.parentBlockId=*<br>
     *
     * �߂�l:void<br>
     * this.blockId=null<br>
     * this.parentBlockId=null<br>
     *
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l��
     * ����������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        String blockId = "��";
        String parentBlockId = "��";

        UTUtil.setPrivateField(tag, "blockId", blockId);
        UTUtil.setPrivateField(tag, "parentBlockId", parentBlockId);

        // �e�X�g���s
        tag.release();

        // �e�X�g���ʊm�F

        String blockId2 = (String) UTUtil.getPrivateField(tag, "blockId");
        String parentBlockId2 =
            (String) UTUtil.getPrivateField(tag, "parentBlockId");

        assertNull(blockId2);
        assertNull(parentBlockId2);

    }

    /**
     * testDoStartTag01�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l
     * this.blockId=not null<br>
     *
     * �߂�l:int=EVAL_BODY_BUFFERED<br>
     *
     * "this.blockId"��not null�̏ꍇ�AEVAL_BODY_BUFFERED��
     * �Ԃ邱�Ƃ��m�F����<br>
     */
    public void testDoStartTag01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(BodyTag.EVAL_BODY_BUFFERED, result);

    }

    /**
     * testDoStartTag02�B<br>
     *
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     *
     * ���͒l
     * this.blockId=null<br>
     *
     * �߂�l:int=JspException<br>
     *
     * "this.blockId"��null�̏ꍇ�AJspException���������邱�Ƃ��m�F����<br>
     */
    public void testDoStartTag02() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", null);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            // �e�X�g���ʊm�F
            assertEquals("blockId is required.", e.getMessage());
            return;
        }

    }

    /**
     * testDoAfterBody01�B<br>
     *
     * (����n)<br>
     * �ϓ_�FC<br>
     *
     * ���͒l
     * blockId=null<br>
     * parentBlockId=null<br>
     *
     * �߂�l:int=SKIP_BODY<br>
     *        parentBlockId=null<br>
     *
     *�EblockId��null�̏ꍇ�ASKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     *�E�A�N�Z�X�����`�F�b�N���ʂ��ۑ�����Ă��Ȃ����Ƃ��m�F<br>
     */
    public void testDoAfterBody01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", null);
        // parentBlockID�̐ݒ�
        UTUtil.setPrivateField(tag, "parentBlockId", null);
        // �e�X�g���s
        int result = tag.doAfterBody();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertNull(parentBlockId);
    }

    /**
     * testDoAfterBody02�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA,F<br>
     *
     * ���͒l
     * blockId= not null<br>
     * blockInfo= false<br>
     * parentBlockId=null<br>
     *
     * �߂�l:int=SKIP_BODY<br>
     *        parentBlockId=null<br>
     *
     * blockId��not null�̏ꍇ�ASKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     * �E�A�N�Z�X�����`�F�b�N���ʂ��ۑ�����Ă��Ȃ����Ƃ��m�F<br>
     */
    public void testDoAfterBody02() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // �e�u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "parentBlockId", null);
        // blockInfo�̐ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute(
            "blockId",
            new Boolean("false"),
            PageContext.PAGE_SCOPE);

        // �e�X�g���s
        int result = tag.doAfterBody();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertNull(parentBlockId);

    }

    /**
     * testDoAfterBody03�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA,F<br>
     *
     * ���͒l
     * blockId= not null<br>
     * blockInfo= false<br>
     * parentBlockId=not null<br>
     *
     * �߂�l:int=SKIP_BODY<br>
     *        parentBlockId=false<br>
     *
     * blockId��not null�̏ꍇ�ASKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     * �E�A�N�Z�X�����`�F�b�N���ʂ��ۑ�����Ă��邱�Ƃ��m�F<br>
     */
    public void testDoAfterBody03() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // �e�u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        //blockInfo�̐ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute(
            "blockId",
            new Boolean("false"),
            PageContext.PAGE_SCOPE);

        // �e�X�g���s
        int result = tag.doAfterBody();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertFalse(parentBlockId.booleanValue());

    }

    /**
     * testDoAfterBody04�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA,F<br>
     *
     * ���͒l
     * blockId= not null<br>
     * blockInfo= true<br>
     * parentBlockId=not null<br>
     *
     * �߂�l:int=SKIP_BODY<br>
     *        parentBlockId=true<br>
     *
     * blockId��not null�̏ꍇ�ASKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     * �E�A�N�Z�X�����`�F�b�N���ʂ��ۑ�����Ă��邱�Ƃ��m�F<br>
     */
    public void testDoAfterBody04() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // �e�u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        // blockInfo�̐ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("blockId", new Boolean("True"), PageContext.PAGE_SCOPE);

        // bodyContent
        UTUtil.setPrivateField(tag, "bodyContent", pc.pushBody());

        // �e�X�g���s
        int result = tag.doAfterBody();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        pc = TagUTUtil.getPageContext(tag);
        Boolean parentBlockId = (Boolean) pc.getAttribute("parentBlockId");
        assertTrue(parentBlockId.booleanValue());

    }

    /**
     * testDoAfterBody05�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l
     * blockId= not null<br>
     * blockInfo= Boolean�^�ȊO<br>
     * parentBlockId=not null<br>
     *
     * �߂�l:ClassCastException���������Ă��郍�O��\��<br>
     *       int=SKIP_BODY���ԋp�����B<br>
     *
     * pageContext ����u���b�N����Cast����ۂɁA<br>
     * ClassCastException���������邱�Ƃ��m�F����B<br>
     * ClassCastException���������Ă��A�����͎��s����ASKIP_BODY���Ԃ����B<br>
     */
    public void testDoAfterBody05() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // �e�u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        // blockInfo�̐ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        Object obj = "a";
        pc.setAttribute("blockId", obj, PageContext.PAGE_SCOPE);

        // bodyContent
        UTUtil.setPrivateField(
            tag,
            "bodyContent",
            pc.pushBody());

        // �e�X�g���s
        int result = tag.doAfterBody();

        // ���ʂ̊m�F
        // ClassCastException���N���������ǂ���log����m�F�B
        boolean logCheck = LogUTUtil.checkWarn("Class cast error.",
                new ClassCastException());
        assertTrue(logCheck);
        assertEquals(Tag.SKIP_BODY, result);
    }

    /**
     * testDoAfterBody06�B<br>
     *
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     *
     * ���͒l
     * blockId= not null<br>
     * blockInfo= true<br>
     * parentBlockId=not null<br>
     *
     * �߂�l:IOException������<br>
     *
     * bodyContent.writeOut()���s���ۂɁA<br>
     * IOException���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoAfterBody06() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // �e�u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "parentBlockId", "parentBlockId");
        // blockInfo�̐ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        pc.setAttribute("blockId", new Boolean("True"), PageContext.PAGE_SCOPE);

        // bodyContent
        UTUtil.setPrivateField(tag, "bodyContent", pc
                .pushBody());
        // IOException�����ݒ�
        Exception_JspWriterImpl jsp = new Exception_JspWriterImpl();
        Exception_BodyContentImpl exception = new Exception_BodyContentImpl(jsp);
        exception.setTrue();
        exception.setTiming(1);
        UTUtil.setPrivateField(tag, "bodyContent", exception);
        // �e�X�g���s
        try {
            tag.doAfterBody();
            fail();
        } catch (JspException ex) {
            // �e�X�g���ʊm�F
            assertEquals(IOException.class.getName(), ex.getRootCause()
                    .getClass().getName());
            return;
        }
    }

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
     */
    public void testDoEndTag01() throws Exception {

        int result = 0;
        // �e�X�g���s
        result = tag.doEndTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
    }
}
