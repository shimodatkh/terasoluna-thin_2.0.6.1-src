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

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.fw.web.thin.AuthorizationControlFilter;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * IfAuthorizedTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * -<br>
 */
public class IfAuthorizedTagTest extends TestCase {

    // �e�X�g�ΏۃN���X����
    IfAuthorizedTag tag = null;

    /**
     * �R���X�g���N�^<br>
     * @param arg0
     */
    public IfAuthorizedTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfAuthorizedTag) TagUTUtil.create(IfAuthorizedTag.class);
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
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * blockId=not null<br>
     * isAuthorized=true<br>
     * AccessController= not null<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * pageContext��getAttribute("blockId") = true<br>
     * 
     * �EblockId��not null�AisAuthorized��true�̏ꍇ�A
     * �@EVAL_BODY_INCLUDE���Ԃ邱�Ƃ��m�F����B<br>
     * �E�܂��APageContext�̃u���b�N����true���ۑ�����Ă���<br>
     * �EIfAuthorizedTag_AccessControllerStub01�̏ꍇ�A
     *   isAuthorized()�ɂ�true��ԋp<br>
     */
    public void testDoStartTag01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // AccessControlFilter�AAccessController�̐���
        IfAuthorizedTag_AccessControllerStub01 ac =
            new IfAuthorizedTag_AccessControllerStub01();
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", ac);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean bId = (Boolean) pc.getAttribute("blockId");
        assertTrue(bId.booleanValue());
    }

    /**
     * testDoStartTag02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC�AF<br>
     * 
     * ���͒l
     * blockId=null<br>
     * isAuthorized=true<br>
     * AccessController= not null<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * pageContext��getAttribute("blockId") = null<br>
     * 
     * �EblockId��null�AisAuthorized��true�̏ꍇ�A
     * �@EVAL_BODY_INCLUDE���Ԃ邱�Ƃ��m�F����B<br>
     * �EIfAuthorizedTag_AccessControllerStub01�̏ꍇ�A
     *   isAuthorized()�ɂ�true��ԋp<br>
     */
    public void testDoStartTag02() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", null);
        // AccessControlFilter�AAccessController�̐���
        IfAuthorizedTag_AccessControllerStub01 ac =
            new IfAuthorizedTag_AccessControllerStub01();
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", ac);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean bId = (Boolean) pc.getAttribute("blockId");
        assertNull(bId);

    }

    /**
     * testDoStartTag03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * blockId=not null<br>
     * isAuthorized=false<br>
     * AccessController= not null<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext��getAttribute("blockId") = null<br>
     * 
     * �EblockId��not null�AisAuthorized��false�̏ꍇ�A
     * �@SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     * �EIfAuthorizedTag_AccessControllerStub02�̏ꍇ�A
     *   isAuthorized()�ɂ�false��ԋp<br>
     */
    public void testDoStartTag03() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // AccessControlFilter�AAccessController�̐���
        IfAuthorizedTag_AccessControllerStub02 ac =
            new IfAuthorizedTag_AccessControllerStub02();
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", ac);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        Boolean bId = (Boolean) pc.getAttribute("blockId");
        assertNull(bId);

    }

    /**
     * testDoStartTag04�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * blockId=not null<br>
     * AccessController= null<br>
     * 
     * ���Ғl
     * �߂�l:NullPointerException����������<br>
     * 
     * �EAccessController= null�̏ꍇ�A<br>
     * �@NullPointerException���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag04() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �u���b�NID�̐ݒ�
        UTUtil.setPrivateField(tag, "blockId", "blockId");
        // AccessControlFilter�AAccessController�̐���
        UTUtil.setPrivateField(AuthorizationControlFilter.class,
                "controller", null);

        // �e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (NullPointerException e) {
            return;
        }

    }

    /**
     * testRelease01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * this.path=*<br>
     * this.blockId=*<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * this.path=null<br>
     * this.blockId=null<br>
     * 
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l��
     * ����������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String path = "��";
        String blockId = "��";

        UTUtil.setPrivateField(tag, "path", path);
        UTUtil.setPrivateField(tag, "blockId", blockId);

        //�e�X�g���s
        tag.release();

        //�e�X�g���ʊm�F

        String path2 = (String) UTUtil.getPrivateField(tag, "path");
        String blockId2 = (String) UTUtil.getPrivateField(tag, "blockId");

        assertNull(path2);
        assertNull(blockId2);

    }

    /**
     * testSetBlockId01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"blockId"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetBlockId01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "blockId";

        //�e�X�g���s
        tag.setBlockId(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "blockId");
        assertEquals(value, result);
    }

    /**
     * testSetPath01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"path"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetPath01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "path";

        //�e�X�g���s
        tag.setPath(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "path");
        assertEquals(value, result);
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
        //�e�X�g���s
        result = tag.doEndTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
    }
}
