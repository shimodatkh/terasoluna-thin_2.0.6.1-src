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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;

import com.mockrunner.mock.web.MockPageContext;

/**
 * {@link jp.terasoluna.framework.web.struts.taglib.PageLinksTag} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �y�[�W�P�ʂɃy�[�W��J�ڂ���@�\�B
 * getInt()�̃e�X�g�̈ꕔ��doStartTag()�ɕ�܂���B
 * <p>
 * 
 * @see jp.terasoluna.framework.web.struts.taglib.PageLinksTag
 */
public class PageLinksTagTest extends PropertyTestCase {

    /**
     * �e�X�g�ΏۃN���X
     */
    private PageLinksTag pageLinks = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(PageLinksTagTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUpData() throws Exception {
        pageLinks = (PageLinksTag) TagUTUtil.create(PageLinksTag.class);
        clearProperty();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void cleanUpData() throws Exception {
        pageLinks = null;
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public PageLinksTagTest(String name) {
        super(name);
    }

    /**
     * testGetId01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) id:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetId01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "id", param);

        // �e�X�g���{
        String value = pageLinks.getId();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetId01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) id:"abc"<br>
     *         (���) id:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) id:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetId01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "id", null);

        // �e�X�g���{
        pageLinks.setId(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "id"));
    }

    /**
     * testGetAction01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) action:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAction01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "action", param);

        // �e�X�g���{
        String value = pageLinks.getAction();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetAction01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) action:"abc"<br>
     *         (���) action:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) action:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetAction01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "action", null);

        // �e�X�g���{
        pageLinks.setAction(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "action"));
    }

    /**
     * testGetName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) name:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetName01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "name", param);

        // �e�X�g���{
        String value = pageLinks.getName();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (���) name:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "name", null);

        // �e�X�g���{
        pageLinks.setName(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "name"));
    }

    /**
     * testGetRowProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) rowProperty:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetRowProperty01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "rowProperty", param);

        // �e�X�g���{
        String value = pageLinks.getRowProperty();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetRowProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) rowProperty:"abc"<br>
     *         (���) rowProperty:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) rowProperty:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetRowProperty01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "rowProperty", null);

        // �e�X�g���{
        pageLinks.setRowProperty(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "rowProperty"));
    }

    /**
     * testGetIndexProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) indexProperty:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexProperty01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "indexProperty", param);

        // �e�X�g���{
        String value = pageLinks.getIndexProperty();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetIndexProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) indexProperty:"abc"<br>
     *         (���) indexProperty:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) indexProperty:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexProperty01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "indexProperty", null);

        // �e�X�g���{
        pageLinks.setIndexProperty(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "indexProperty"));
    }

    /**
     * testGetTotalProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) totalProperty:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTotalProperty01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalProperty", param);

        // �e�X�g���{
        String value = pageLinks.getTotalProperty();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetTotalProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) totalProperty:"abc"<br>
     *         (���) totalProperty:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) totalProperty:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetTotalProperty01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalProperty", null);

        // �e�X�g���{
        pageLinks.setTotalProperty(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "totalProperty"));
    }

    /**
     * testGetScope01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) scope:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetScope01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "scope", param);

        // �e�X�g���{
        String value = pageLinks.getScope();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetScope01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) scope:"abc"<br>
     *         (���) scope:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) scope:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetScope01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "scope", null);

        // �e�X�g���{
        pageLinks.setScope(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "scope"));
    }

    /**
     * testGetSubmit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) submit:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetSubmit01() throws Exception {
        // �O����
        Boolean param = new Boolean(true);
        UTUtil.setPrivateField(pageLinks, "submit", param);

        // �e�X�g���{
        boolean value = pageLinks.getSubmit();

        // ����
        assertTrue(value);
    }

    /**
     * testSetSubmit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) submit:true<br>
     *         (���) submit:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) submit:true<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSubmit01() throws Exception {
        // �O����
        boolean param = true;
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));

        // �e�X�g���{
        pageLinks.setSubmit(param);

        // ����
        boolean result = ((Boolean) UTUtil.getPrivateField(
                pageLinks, "submit")).booleanValue();
        assertTrue(result);
    }

    /**
     * testGetForward01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) forward:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetForward01() throws Exception {
        // �O����
        Boolean param = new Boolean(true);
        UTUtil.setPrivateField(pageLinks, "forward", param);

        // �e�X�g���{
        boolean value = pageLinks.getForward();

        // ����
        assertTrue(value);
    }

    /**
     * testSetForward01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) forward:true<br>
     *         (���) forward:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) forward:true<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetForward01() throws Exception {
        // �O����
        boolean param = true;
        UTUtil.setPrivateField(pageLinks, "forward", new Boolean(false));

        // �e�X�g���{
        pageLinks.setForward(param);

        // ����
        boolean result = ((Boolean) UTUtil.getPrivateField(
                pageLinks, "forward")).booleanValue();
        assertTrue(result);
    }

    /**
     * testGetEvent01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) event:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetEvent01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "event", param);

        // �e�X�g���{
        String value = pageLinks.getEvent();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetEvent01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) event:"abc"<br>
     *         (���) event:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) event:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetEvent01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "event", null);

        // �e�X�g���{
        pageLinks.setEvent(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "event"));
    }

    /**
     * testGetResetIndex01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) resetIndex:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResetIndex01() throws Exception {
        // �O����
        Boolean param = new Boolean(true);
        UTUtil.setPrivateField(pageLinks, "resetIndex", param);

        // �e�X�g���{
        boolean value = pageLinks.getResetIndex();

        // ����
        assertTrue(value);
    }

    /**
     * testSetResetIndex01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) resetIndex:true<br>
     *         (���) resetIndex:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) resetIndex:true<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetResetIndex01() throws Exception {
        // �O����
        boolean param = true;
        UTUtil.setPrivateField(pageLinks, "resetIndex", new Boolean(false));

        // �e�X�g���{
        pageLinks.setResetIndex(param);

        // ����
        boolean result = ((Boolean) UTUtil.getPrivateField(
                pageLinks, "resetIndex")).booleanValue();
        assertTrue(result);
    }

    /**
     * testGetCurrentPageIndex01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) currentPageIndex:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCurrentPageIndex01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", param);

        // �e�X�g���{
        String value = pageLinks.getCurrentPageIndex();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetCurrentPageIndex01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) currentPageIndex:"abc"<br>
     *         (���) currentPageIndex:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) currentPageIndex:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCurrentPageIndex01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", null);

        // �e�X�g���{
        pageLinks.setCurrentPageIndex(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "currentPageIndex"));
    }

    /**
     * testGetTotalPageCount01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) totalPageCount:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTotalPageCount01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalPageCount", param);

        // �e�X�g���{
        String value = pageLinks.getTotalPageCount();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetTotalPageCount01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) totalPageCount:"abc"<br>
     *         (���) totalPageCount:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) totalPageCount:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetTotalPageCount01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(pageLinks, "totalPageCount", null);

        // �e�X�g���{
        pageLinks.setTotalPageCount(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(pageLinks, "totalPageCount"));
    }

    /**
     * testDoStartTag01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) submit:false<br>
     *         (���) action:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspException<br>
     *                    ���b�Z�[�W�F�uAction attribute is required when submit 
     *                    attribute is �gfalse�h.�v<br>
     *         (��ԕω�) ���O:���b�Z�[�W�F<br>
     *                    �uAction attribute is required when submit attribute 
     *                    is "false".�v<br>
     *         
     * <br>
     * submit������false�ŁAaction������null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag01() throws Exception {
        // �O����
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", null);

        // �e�X�g���{
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // ����
            assertTrue(LogUTUtil.checkError(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED));
            assertEquals(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED, e.getMessage());
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
     * ���͒l�F(���) submit:false<br>
     *         (���) action:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspException<br>
     *                    ���b�Z�[�W�F�uAction attribute is required when submit 
     *                    attribute is �gfalse�h.�v<br>
     *         (��ԕω�) ���O:���b�Z�[�W�F<br>
     *                    �uAction attribute is required when submit attribute 
     *                    is "false".�v<br>
     *         
     * <br>
     * submit������false�ŁAaction�������󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag02() throws Exception {
        // �O����
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "");

        // �e�X�g���{
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // ����
            assertTrue(LogUTUtil.checkError(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED));
            assertEquals(
                    PageLinksTag.ERROR_MESSAGE_ACTION_REQUIRED, e.getMessage());
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
     * ���͒l�F(���) submit:false<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="a", index="0", total="100"}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspException<br>
     *                    ���b�Z�[�W�F-<br>
     *                    ���b�v������O�FNumberFormatException<br>
     *         (��ԕω�) ���O:���b�Z�[�W�F
     *         NumberFormatException#getMessage()�̒l<br>
     *         
     * <br>
     * �擾�����\���s�������l�ɕϊ��ł��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag03() throws Exception {
        // �O����
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("a");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // ����
            assertEquals(NumberFormatException.class.getName(),
                    e.getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(e.getRootCause().getMessage()));
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
     * ���͒l�F(���) submit:false<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="a", total="100"}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspException<br>
     *                    ���b�Z�[�W�F-<br>
     *                    ���b�v������O�FNumberFormatException<br>
     *         (��ԕω�) ���O:���b�Z�[�W�F
     *         NumberFormatException#getMessage()�̒l<br>
     *         
     * <br>
     * �擾�����\���J�n�C���f�b�N�X�����l�ɕϊ��ł��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag04() throws Exception {
        // �O����
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("a");
        bean.setTotal("100");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // ����
            assertEquals(NumberFormatException.class.getName(),
                    e.getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(e.getRootCause().getMessage()));
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
     * ���͒l�F(���) submit:false<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="a"}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspException<br>
     *                    ���b�Z�[�W�F-<br>
     *                    ���b�v������O�FNumberFormatException<br>
     *         (��ԕω�) ���O:���b�Z�[�W�F
     *         NumberFormatException#getMessage()�̒l<br>
     *         
     * <br>
     * �擾�����S���������l�ɕϊ��ł��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag05() throws Exception {
        // �O����
        pageLinks = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("a");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspException e) {
            // ����
            assertEquals(NumberFormatException.class.getName(),
                    e.getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(e.getRootCause().getMessage()));
        }
    }

    /**
     * testDoStartTag06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) submit:false<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index=null, total=null}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) JspWriter:"abc"���o�͂���邱�Ƃ��m�F<br>
     *         (��ԕω�) addPrevLink()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F0<br>
     *                    �ŌĂяo����邱�Ƃ��m�F�B<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"a"��ݒ肷��B<br>
     *         (��ԕω�) addDirectLink()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F0<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"b"��ݒ肷��B<br>
     *         (��ԕω�) addNextLink()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F0<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"c"��ݒ肷��B<br>
     *         
     * <br>
     * �w�肳�ꂽBean�̃v���p�e�B�̒l�����ׂ�null�̏ꍇ�A
     * �f�t�H���g���K�p����ă����N���o�͂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag06() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        int value = pageLinks.doStartTag();

        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertFalse(pageLinks.isDefineHtmlCalled());
        assertFalse(pageLinks.isAddPrevSubmitCalled());
        assertFalse(pageLinks.isAddDirectSubmitCalled());
        assertFalse(pageLinks.isAddNextSubmitCalled());
        assertTrue(pageLinks.isAddPrevLinkCalled());
        assertTrue(pageLinks.isAddDirectLinkCalled());
        assertTrue(pageLinks.isAddNextLinkCalled());
        StringBuilder sb = new StringBuilder();
        assertEquals(sb.toString(), pageLinks.getAddPrevLinkSb().toString());
        assertEquals(10, pageLinks.getAddPrevLinkRow());
        assertEquals(0, pageLinks.getAddPrevLinkStartIndex());
        assertEquals(0, pageLinks.getAddPrevLinkTotalCount());
        sb.append("a");
        assertEquals(sb.toString(), pageLinks.getAddDirectLinkSb().toString());
        assertEquals(10, pageLinks.getAddDirectLinkRow());
        assertEquals(0, pageLinks.getAddDirectLinkStartIndex());
        assertEquals(0, pageLinks.getAddDirectLinkTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextLinkSb().toString());
        assertEquals(10, pageLinks.getAddNextLinkRow());
        assertEquals(0, pageLinks.getAddNextLinkStartIndex());
        assertEquals(0, pageLinks.getAddNextLinkTotalCount());
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        sb.append("c");
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) submit:false<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="", total=""}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) JspWriter:"abc"���o�͂���邱�Ƃ��m�F<br>
     *         (��ԕω�) addPrevLink()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F0<br>
     *                    �ŌĂяo����邱�Ƃ��m�F�B<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"a"��ݒ肷��B<br>
     *         (��ԕω�) addDirectLink()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F0<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"b"��ݒ肷��B<br>
     *         (��ԕω�) addNextLink()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F0<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"c"��ݒ肷��B<br>
     *         
     * <br>
     * �w�肳�ꂽBean�̃v���p�e�B�̒l�����ׂċ󔒂̏ꍇ�A
     * �f�t�H���g���K�p����ă����N���o�͂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag07() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("");
        bean.setTotal("");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        int value = pageLinks.doStartTag();

        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertFalse(pageLinks.isDefineHtmlCalled());
        assertFalse(pageLinks.isAddPrevSubmitCalled());
        assertFalse(pageLinks.isAddDirectSubmitCalled());
        assertFalse(pageLinks.isAddNextSubmitCalled());
        assertTrue(pageLinks.isAddPrevLinkCalled());
        assertTrue(pageLinks.isAddDirectLinkCalled());
        assertTrue(pageLinks.isAddNextLinkCalled());
        StringBuilder sb = new StringBuilder();
        assertEquals(sb.toString(), pageLinks.getAddPrevLinkSb().toString());
        assertEquals(10, pageLinks.getAddPrevLinkRow());
        assertEquals(0, pageLinks.getAddPrevLinkStartIndex());
        assertEquals(0, pageLinks.getAddPrevLinkTotalCount());
        sb.append("a");
        assertEquals(sb.toString(), pageLinks.getAddDirectLinkSb().toString());
        assertEquals(10, pageLinks.getAddDirectLinkRow());
        assertEquals(0, pageLinks.getAddDirectLinkStartIndex());
        assertEquals(0, pageLinks.getAddDirectLinkTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextLinkSb().toString());
        assertEquals(10, pageLinks.getAddNextLinkRow());
        assertEquals(0, pageLinks.getAddNextLinkStartIndex());
        assertEquals(0, pageLinks.getAddNextLinkTotalCount());
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        sb.append("c");
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) submit:true<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) JspWriter:"abc"���o�͂���邱�Ƃ��m�F<br>
     *         (��ԕω�) defineHtml()��<br>
     *                    �Ăъm�F:������<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    �ŌĂяo����邱�Ƃ��m�F�B<br>
     *         (��ԕω�) addPrevSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"a"��ݒ肷��B<br>
     *         (��ԕω�) addDirectSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"b"��ݒ肷��B<br>
     *         (��ԕω�) addNextSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"c"��ݒ肷��B<br>
     *         
     * <br>
     * �l������Ɏ擾�ϊ��ł��A�T�u�~�b�g�����郊���N���o�͂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag08() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        int value = pageLinks.doStartTag();

        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(pageLinks.isDefineHtmlCalled());
        assertTrue(pageLinks.isAddPrevSubmitCalled());
        assertTrue(pageLinks.isAddDirectSubmitCalled());
        assertTrue(pageLinks.isAddNextSubmitCalled());
        assertFalse(pageLinks.isAddPrevLinkCalled());
        assertFalse(pageLinks.isAddDirectLinkCalled());
        assertFalse(pageLinks.isAddNextLinkCalled());
        StringBuilder sb = new StringBuilder();
        assertEquals(sb.toString(), pageLinks.getAddPrevSubmitSb().toString());
        assertEquals(10, pageLinks.getAddPrevSubmitRow());
        assertEquals(0, pageLinks.getAddPrevSubmitStartIndex());
        assertEquals(100, pageLinks.getAddPrevSubmitTotalCount());
        sb.append("a");
        assertEquals(sb.toString(),
                pageLinks.getAddDirectSubmitSb().toString());
        assertEquals(10, pageLinks.getAddDirectSubmitRow());
        assertEquals(0, pageLinks.getAddDirectSubmitStartIndex());
        assertEquals(100, pageLinks.getAddDirectSubmitTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextSubmitSb().toString());
        assertEquals(10, pageLinks.getAddNextSubmitRow());
        assertEquals(0, pageLinks.getAddNextSubmitStartIndex());
        assertEquals(100, pageLinks.getAddNextSubmitTotalCount());
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        sb.append("c");
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag09()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) submit:true<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         (���) JspWriter#println():IOException������<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspTagException<br>
     *                    ���b�Z�[�W�F-<br>
     *                    ���b�v������O�F-<br>
     *         
     * <br>
     * JspWriter��IOException�����������ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag09() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g�pJspWriter�̐���
        Exception_JspWriterImpl out =
            new Exception_JspWriterImpl();
        out.setTrue();

        // �����E�ݒ肵���e�X�g�pJspWriter��PageContext�ɃZ�b�g
        UTUtil.setPrivateField(pageContext, "jspWriter", out);

        // �e�X�g���{
        try {
            pageLinks.doStartTag();
            fail();
        } catch (JspTagException e) {
            // ����
            if (e.getMessage().indexOf("IOException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testDoStartTag10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) submit:false<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row=null, index=null, total=null}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) ���O:���b�Z�[�W�F"Row param is illegal."<br>
     *         
     * <br>
     * �w�肳�ꂽBean��row�v���p�e�B�̒l��null�̏ꍇ�A
     * �x�����O���o�͂��ďI�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag10() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        int value = pageLinks.doStartTag();

        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(LogUTUtil.checkWarn(PageLinksTag.WARN_MESSAGE_ILLEGAL_ROW));
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) submit:false<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="", index="", total=""}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_INCLUDE<br>
     * �w�肳�ꂽBean�̃v���p�e�B�̒l�����ׂ�null�̏ꍇ�A
     * �f�t�H���g���K�p����ă����N���o�͂���ꍇ
     * 
     * <br>
     * �w�肳�ꂽBean��row�v���p�e�B�̒l���󔒂̏ꍇ�A
     * �x�����O���o�͂��ďI�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag11() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(false));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("");
        bean.setIndex("");
        bean.setTotal("");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        int value = pageLinks.doStartTag();

        // ����
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(LogUTUtil.checkWarn(PageLinksTag.WARN_MESSAGE_ILLEGAL_ROW));
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) id:"listPageLinks"<br>
     *         (���) submit:true<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         (���) currentPageIndex:null<br>
     *         (���) totalPageCount:null<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) JspWriter:�����o�͂���Ȃ����ƁB<br>
     *         (��ԕω�) pageContext.getAttribut("listPageLinks"):"abc"<br>
     *         (��ԕω�) defineHtml()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F�B<br>
     *         (��ԕω�) addPrevSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"a"��ݒ肷��B<br>
     *         (��ԕω�) addDirectSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"b"��ݒ肷��B<br>
     *         (��ԕω�) addNextSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"c"��ݒ肷��B<br>
     *         
     * <br>
     * �l������Ɏ擾�ϊ��ł��A�T�u�~�b�g�����郊���N���o�͂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag12() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "id", "listPageLinks");
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        int value = pageLinks.doStartTag();

        // ����
        StringBuilder sb = new StringBuilder();
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(pageLinks.isDefineHtmlCalled());
        assertTrue(pageLinks.isAddPrevSubmitCalled());
        assertTrue(pageLinks.isAddDirectSubmitCalled());
        assertTrue(pageLinks.isAddNextSubmitCalled());
        assertFalse(pageLinks.isAddPrevLinkCalled());
        assertFalse(pageLinks.isAddDirectLinkCalled());
        assertFalse(pageLinks.isAddNextLinkCalled());
        assertEquals(10, pageLinks.getDefineHtmlRow());
        assertEquals(0, pageLinks.getDefineHtmlStartIndex());
        assertEquals(100, pageLinks.getDefineHtmlTotalCount());
        assertEquals(sb.toString(), pageLinks.getAddPrevSubmitSb().toString());
        assertEquals(10, pageLinks.getAddPrevSubmitRow());
        assertEquals(0, pageLinks.getAddPrevSubmitStartIndex());
        assertEquals(100, pageLinks.getAddPrevSubmitTotalCount());
        sb.append("a");
        assertEquals(sb.toString(),
                pageLinks.getAddDirectSubmitSb().toString());
        assertEquals(10, pageLinks.getAddDirectSubmitRow());
        assertEquals(0, pageLinks.getAddDirectSubmitStartIndex());
        assertEquals(100, pageLinks.getAddDirectSubmitTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextSubmitSb().toString());
        assertEquals(10, pageLinks.getAddNextSubmitRow());
        assertEquals(0, pageLinks.getAddNextSubmitStartIndex());
        assertEquals(100, pageLinks.getAddNextSubmitTotalCount());
        sb.append("c");
        String resultValue = (String) pageContext.getAttribute("listPageLinks");
        assertEquals(sb.toString(), resultValue);
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) id:""<br>
     *         (���) submit:true<br>
     *         (���) action:"/list"<br>
     *         (���) pageContext:not null<br>
     *                ["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="10", index="0", total="100"}]<br>
     *         (���) name:"bean"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) totalProperty:"total"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_INCLUDE<br>
     *         (��ԕω�) JspWriter:"abc"���o�͂���邱�Ƃ��m�F<br>
     *         (��ԕω�) defineHtml()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F�B<br>
     *         (��ԕω�) addPrevSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"a"��ݒ肷��B<br>
     *         (��ԕω�) addDirectSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"b"��ݒ肷��B<br>
     *         (��ԕω�) addNextSubmit()��<br>
     *                    �Ăъm�F:������<br>
     *                    sb�Fnew StringBuilder()<br>
     *                    row�F10<br>
     *                    startIndex�F0<br>
     *                    totalCount�F100<br>
     *                    �ŌĂяo����邱�Ƃ��m�F<br>
     *                    �X�^�u�N���X�̂��̃��\�b�h�̍Ō�ɁA
     *                    sb�ɕ�����"c"��ݒ肷��B<br>
     *         
     * <br>
     * �l������Ɏ擾�ϊ��ł��A�T�u�~�b�g�����郊���N���o�͂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag13() throws Exception {
        // �O����
        PageLinksTag_PageLinksTagStub01 pageLinks
            = (PageLinksTag_PageLinksTagStub01) TagUTUtil.create(
                PageLinksTag_PageLinksTagStub01.class);
        UTUtil.setPrivateField(pageLinks, "id", "");
        UTUtil.setPrivateField(pageLinks, "submit", new Boolean(true));
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "name", "bean");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "total");
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("10");
        bean.setIndex("0");
        bean.setTotal("100");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("bean", bean);

        // �e�X�g���{
        int value = pageLinks.doStartTag();

        // ����
        StringBuilder sb = new StringBuilder();
        assertEquals(Tag.EVAL_BODY_INCLUDE, value);
        assertTrue(pageLinks.isDefineHtmlCalled());
        assertTrue(pageLinks.isAddPrevSubmitCalled());
        assertTrue(pageLinks.isAddDirectSubmitCalled());
        assertTrue(pageLinks.isAddNextSubmitCalled());
        assertFalse(pageLinks.isAddPrevLinkCalled());
        assertFalse(pageLinks.isAddDirectLinkCalled());
        assertFalse(pageLinks.isAddNextLinkCalled());
        assertEquals(10, pageLinks.getDefineHtmlRow());
        assertEquals(0, pageLinks.getDefineHtmlStartIndex());
        assertEquals(100, pageLinks.getDefineHtmlTotalCount());
        assertEquals(sb.toString(), pageLinks.getAddPrevSubmitSb().toString());
        assertEquals(10, pageLinks.getAddPrevSubmitRow());
        assertEquals(0, pageLinks.getAddPrevSubmitStartIndex());
        assertEquals(100, pageLinks.getAddPrevSubmitTotalCount());
        sb.append("a");
        assertEquals(sb.toString(),
                pageLinks.getAddDirectSubmitSb().toString());
        assertEquals(10, pageLinks.getAddDirectSubmitRow());
        assertEquals(0, pageLinks.getAddDirectSubmitStartIndex());
        assertEquals(100, pageLinks.getAddDirectSubmitTotalCount());
        sb.append("b");
        assertEquals(sb.toString(), pageLinks.getAddNextSubmitSb().toString());
        assertEquals(10, pageLinks.getAddNextSubmitRow());
        assertEquals(0, pageLinks.getAddNextSubmitStartIndex());
        assertEquals(100, pageLinks.getAddNextSubmitTotalCount());
        sb.append("c");
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals(sb.toString(), reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDefineHtml01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) forward:true<br>
     *         (���) event:"abs"<br>
     *         (���) resetIndex:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:false<br>
     *         (���) fromName:"pageLinks"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"index\" value=\"0\"/><br>
     *                    <input type=\"hidden\" name=\"abs\" value=\"\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.abs.value = "forward_pageLinks";<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:false<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         
     * <br>
     * �y�[�W�R���e�L�X�g�̃t���O�����ׂ�false�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", false);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g���{
        pageLinks.defineHtml(10, 0, 0);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"index\" value=\"0\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"abs\"" +
                " value=\"\"/>",reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.abs.value " +
                "= \"forward_pageLinks\";", reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) forward:true<br>
     *         (���) event:"abs"<br>
     *         (���) resetIndex:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JspWriter:�����o�͂���Ȃ����Ƃ��m�F�B<br>
     *         
     * <br>
     * �y�[�W�R���e�L�X�g�̃t���O�����ׂ�true�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, true);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g���{
        pageLinks.defineHtml(10, 0, 0);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) forward:false<br>
     *         (���) resetIndex:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JspWriter:�����o�͂���Ȃ����Ƃ��m�F�B<br>
     *         
     * <br>
     * �y�[�W�R���e�L�X�g�̃t���O�����ׂ�true�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +PageLinksTag.FORWARD_NAME, true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"row", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"index", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, true);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g���{
        pageLinks.defineHtml(10, 0, 0);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + PageLinksTag.FORWARD_NAME);
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY +"row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY +"index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) forward:true<br>
     *         (���) resetIndex:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         (���) JspWriter#println():IOException������<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspTagException<br>
     *                    ���b�Z�[�W�F-<br>
     *                    ���b�v������O�F-<br>
     *         
     * <br>
     * JspWriter��IOException�����������ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +PageLinksTag.FORWARD_NAME, true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                +"index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", true);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g�pJspWriter�̐���
        Exception_JspWriterImpl out =
            new Exception_JspWriterImpl();
        out.setTrue();

        // �����E�ݒ肵���e�X�g�pJspWriter��PageContext�ɃZ�b�g
        UTUtil.setPrivateField(pageContext, "jspWriter", out);

        // �e�X�g���{
        try {
            pageLinks.defineHtml(10, 0, 0);
            fail();
        } catch (JspTagException e) {
            // ����
            if (e.getMessage().indexOf("IOException") == -1) {
                //���ʊm�F
                fail();
            }
        }
    }

    /**
     * testDefineHtml05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) forward:false<br>
     *         (���) event:"abs"<br>
     *         (���) resetIndex:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:false<br>
     *         (���) fromName:"pageLinks"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"index\" value=\"0\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         
     * <br>
     * �y�[�W�R���e�L�X�g�̃t���O�����ׂ�false�ŁAforward������false�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml05() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", false);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g���{
        pageLinks.defineHtml(10, 0, 0);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"index\" value=\"0\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:9<br>
     *         (����) totalCount:15<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) forward:false<br>
     *         (���) event:"abs"<br>
     *         (���) resetIndex:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:false<br>
     *         (���) fromName:"pageLinks"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"index\" value=\"9\"/><br>
     *                    <input type=\"hidden\" name=\"startIndex\" value=\"9\"/><br>
     *                    <input type=\"hidden\" name=\"endIndex\" value=\"14\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         
     * <br>
     * �y�[�W�R���e�L�X�g�̃t���O�����ׂ�false�ŁAresetIndex������true�ŁA�J�n�C���f�b�N�X���A"startIndex"�ł͂Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml06() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g���{
        pageLinks.defineHtml(10, 9, 15);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"index\" value=\"9\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"startIndex\" value=\"9\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"endIndex\" value=\"14\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "index");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:9<br>
     *         (����) totalCount:50<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"startIndex"<br>
     *         (���) forward:false<br>
     *         (���) event:"abs"<br>
     *         (���) resetIndex:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:false<br>
     *         (���) fromName:"pageLinks"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"startIndex\" value=\"9\"/><br>
     *                    <input type=\"hidden\" name=\"endIndex\" value=\"18\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         
     * <br>
     * �y�[�W�R���e�L�X�g�̃t���O�����ׂ�false�ŁAresetIndex������true�ŁA�J�n�C���f�b�N�X���A"startIndex"�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml07() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "startIndex");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g���{
        pageLinks.defineHtml(10, 9, 50);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"startIndex\" value=\"9\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"endIndex\" value=\"18\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "startIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }

    /**
     * testDefineHtml08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:41<br>
     *         (����) totalCount:50<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"startIndex"<br>
     *         (���) forward:false<br>
     *         (���) event:"abs"<br>
     *         (���) resetIndex:true<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:false<br>
     *         (���) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:false<br>
     *         (���) fromName:"pageLinks"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) JspWriter:<input type=\"hidden\" name=\"row\" value=\"10\"/><br>
     *                    <input type=\"hidden\" name=\"startIndex\" value=\"41\"/><br>
     *                    <input type=\"hidden\" name=\"endIndex\" value=\"49\"/><br>
     *                    <script type=\"text/javascript\"><br>
     *                    <!--<br>
     *                      function pageLinkSubmit(rowObj, indexObj, row, startIndex){<br>
     *                        rowObj.value = row;<br>
     *                        indexObj.value = startIndex;<br>
     *                        document.pageLinks.submit();<br>
     *                      }<br>
     *                    // --><br>
     *                    </script><br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{event�̒l:false<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{rowProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{indexProperty�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY �{"resetIndex"�̒l:true<br>
     *         (��ԕω�) pageContext��PAGELINKS_JAVASCRIPT_KEY�̒l:true<br>
     *         
     * <br>
     * �y�[�W�R���e�L�X�g�̃t���O�����ׂ�false�ŁAresetIndex������true�ŁA�J�n�C���f�b�N�X���A"startIndex"�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineHtml08() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "startIndex");
        UTUtil.setPrivateField(pageLinks, "forward", false);
        UTUtil.setPrivateField(pageLinks, "event", "abs");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "abs", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "row", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "index", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY
                + "resetIndex", false);
        pageContext.setAttribute(PageLinksTag.PAGELINKS_JAVASCRIPT_KEY, false);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        // �e�X�g���{
        pageLinks.defineHtml(10, 41, 50);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(pageLinks);
        assertEquals("<input type=\"hidden\" name=\"row\" value=\"10\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"startIndex\" value=\"41\"/>"
                ,reader.readLine());
        assertEquals("<input type=\"hidden\" name=\"endIndex\" value=\"49\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("  function pageLinkSubmit(rowObj, indexObj, row, " +
                "startIndex){",reader.readLine());
        assertEquals("    rowObj.value = row;",reader.readLine());
        assertEquals("    indexObj.value = startIndex;",reader.readLine());
        assertEquals("    document.pageLinks.submit();",reader.readLine());
        assertEquals("  }",reader.readLine());
        assertEquals("// -->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());

        Boolean bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "abs");
        assertFalse(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "row");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "startIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY + "resetIndex");
        assertTrue(bol);
        bol = (Boolean) pageContext.getAttribute(
                PageLinksTag.PAGELINKS_JAVASCRIPT_KEY);
        assertTrue(bol);
    }
    
    
    /**
     * testAddPrevSubmit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:&lt;&lt;&lt;&nbsp;&lt;&lt;&nbsp;<a href=\"#\"
     *  onclick=\"pageLinkSubmit(document.pageLinks.row,document.pageLinks.
     *  index,10,10)\">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row���P�O�AstartIndex���Q�O�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevSubmit01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // �e�X�g���{
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);
        

        // ����
        assertEquals("&lt;&lt;&lt;&nbsp;&lt;&lt;&nbsp;<a href=\"#\"" +
                " onclick=\"pageLinkSubmit(document.pageLinks.row," +
                "document.pageLinks.index,10,10)\">&lt;</a>&nbsp;"
                , sb.toString());
    }

    /**
     * testAddPrevSubmit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:0<br>
     *         (����) startIndex:0<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:
     * <a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,0,0)\">&lt;&lt;&lt;</a>&nbsp;
     * <a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,0,0)\">&lt;&lt;</a>&nbsp;
     * <a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,0,0)\">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row���O�AstartIndex���O�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevSubmit02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // �e�X�g���{
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);

        // ����
        assertEquals("<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,0,0)\">" +
                "&lt;&lt;&lt;</a>&nbsp;<a href=\"#\" " +
                "onclick=\"pageLinkSubmit(document.pageLinks.row," +
                "document.pageLinks.index,0,0)\">&lt;&lt;</a>&nbsp;" +
                "<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,0,0)\">" +
                "&lt;</a>&nbsp;", sb.toString());
    }

    /**
     * testAddPrevSubmit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:-1<br>
     *         (����) startIndex:-1<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:prev1.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href=\"#\" onclick=\"pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,-1,0)\">&lt;</a>&nbsp;
     * <br>
     *         
     * <br>
     * ����row���|�P�AstartIndex���|�P�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevSubmit03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // �e�X�g���{
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);

        // ����
        assertEquals("<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,-1,0)\">" +
                "&lt;</a>&nbsp;", sb.toString());
    }

    /**
     * testAddPrevSubmit04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:1<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href=\"#\"
     *  onclick=\"pageLinkSubmit(document.pageLinks.row,document.pageLinks.
     *  index,10,10)\">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row���P�O�AstartIndex���Q�O�ŁAmaxLinkNo���P�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevSubmit04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // �e�X�g���{
        pageLinks.addPrevSubmit(sb, row, startIndex, totalCount);

        // ����
        assertEquals("<a href=\"#\"" +
                " onclick=\"pageLinkSubmit(document.pageLinks.row," +
                "document.pageLinks.index,10,10)\">&lt;</a>&nbsp;"
                , sb.toString());
    }

    /**
     * testAddDirectSubmit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:100<br>
     *         (����) totalCount:130<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):"10"<br>
     *                �i�v���p�e�B����擾����maxDirectLinkCount�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(document.
     * pageLinks.row,document.pageLinks.index,10,30)">4</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,40)">5</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,50)">6</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,60)">7</a>
     * &nbsp;<a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,70)">8</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,80)">9</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,90)">10</a>
     * &nbsp;<b>11</b>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,110)">12</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,120)">13</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��100�AtotalCount��130�ŁA
     * directLinkNo��"10"���w�肳�ꂽ�ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectSubmit01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "10");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 130;

        // �e�X�g���{
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,30)\">" +
                "4</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,40)\">" +
                "5</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,50)\">" +
                "6</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,60)\">" +
                "7</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,70)\">" +
                "8</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,80)\">" +
                "9</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,90)\">" +
                "10</a>&nbsp;<b>11</b>&nbsp;" +
                "<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,110)\">" +
                "12</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(" +
                "document.pageLinks.row,document.pageLinks.index,10,120)\">" +
                "13</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:100<br>
     *         (����) totalCount:200<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):"a"<br>
     *                �i�v���p�e�B����擾����maxDirectLinkCount�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,50)">6</a>
     * &nbsp;<a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,60)">7</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,70)">8</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,80)">9</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,90)">10</a>&nbsp;<b>11</b>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,110)">12</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,120)">13</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,130)">14</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,140)">15</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��100�AtotalCount��200�ŁA
     * directLinkNo��"a"���w�肳�ꂽ�ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectSubmit02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "a");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 200;

        // �e�X�g���{
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,50)\">6</a>&nbsp;<a hr" +
                "ef=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,doc" +
                "ument.pageLinks.index,10,60)\">7</a>&nbsp;<a href=\"#\" oncl" +
                "ick=\"pageLinkSubmit(document.pageLinks.row,document.pageLin" +
                "ks.index,10,70)\">8</a>&nbsp;<a href=\"#\" onclick=\"pageLin" +
                "kSubmit(document.pageLinks.row,document.pageLinks.index,10,8" +
                "0)\">9</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(docum" +
                "ent.pageLinks.row,document.pageLinks.index,10,90)\">10</a>&n" +
                "bsp;<b>11</b>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(do" +
                "cument.pageLinks.row,document.pageLinks.index,10,110)\">12</" +
                "a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,120)\">13</a>&nbsp;<a " +
                "href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,d" +
                "ocument.pageLinks.index,10,130)\">14</a>&nbsp;<a href=\"#\" " +
                "onclick=\"pageLinkSubmit(document.pageLinks.row,document.pag" +
                "eLinks.index,10,140)\">15</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:2<br>
     *         (����) totalCount:200<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount�̒l��
     *                �l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,0)">1</a>&nbsp;
     * <b>2</b>&nbsp;<a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,20)">3</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,30)">4</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,40)">5</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,50)">6</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,60)">7</a>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,70)">8</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,80)">9</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,90)">10</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��2�AtotalCount��200�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectSubmit03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 200;

        // �e�X�g���{
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,0)\">1</a>&nbsp;<b>2</" +
                "b>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,20)\">3</a>&nbsp;<a hr" +
                "ef=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,doc" +
                "ument.pageLinks.index,10,30)\">4</a>&nbsp;<a href=\"#\" oncl" +
                "ick=\"pageLinkSubmit(document.pageLinks.row,document.pageLin" +
                "ks.index,10,40)\">5</a>&nbsp;<a href=\"#\" onclick=\"pageLin" +
                "kSubmit(document.pageLinks.row,document.pageLinks.index,10,5" +
                "0)\">6</a>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(docum" +
                "ent.pageLinks.row,document.pageLinks.index,10,60)\">7</a>&nb" +
                "sp;<a href=\"#\" onclick=\"pageLinkSubmit(document.pageLinks" +
                ".row,document.pageLinks.index,10,70)\">8</a>&nbsp;<a href=\"" +
                "#\" onclick=\"pageLinkSubmit(document.pageLinks.row,document" +
                ".pageLinks.index,10,80)\">9</a>&nbsp;<a href=\"#\" onclick=" +
                "\"pageLinkSubmit(document.pageLinks.row,document.pageLinks.i" +
                "ndex,10,90)\">10</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:2<br>
     *         (����) totalCount:50<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount�̒l��
     *                �l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,0)">1</a>&nbsp;
     * <b>2</b>&nbsp;<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,20)">3</a>&nbsp;
     * <a href="#" onclick="pageLinkSubmit(document.pageLinks.row,
     * document.pageLinks.index,10,30)">4</a>&nbsp;<a href="#" 
     * onclick="pageLinkSubmit(document.pageLinks.row,document.pageLinks.index,
     * 10,40)">5</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��2�AtotalCount��50�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectSubmit04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 50;

        // �e�X�g���{
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,0)\">1</a>&nbsp;<b>2</" +
                "b>&nbsp;<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,20)\">3</a>&nbsp;<a hr" +
                "ef=\"#\" onclick=\"pageLinkSubmit(document.pageLinks.row,doc" +
                "ument.pageLinks.index,10,30)\">4</a>&nbsp;<a href=\"#\" oncl" +
                "ick=\"pageLinkSubmit(document.pageLinks.row,document.pageLin" +
                "ks.index,10,40)\">5</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:0<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount�̒l��
     *                �l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,0,0)">1</a>&nbsp;<br>
     *         
     * <br>
     * ����row��0�AstartIndex��0�AtotalCount��0�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectSubmit05() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // �e�X�g���{
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,0,0)\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectSubmit06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:-1<br>
     *         (����) startIndex:-1<br>
     *         (����) totalCount:-1<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount�̒l��
     *                �l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,-1,0)">1</a>&nbsp;<br>
     *         
     * <br>
     * ����row��-1�AstartIndex��-1�AtotalCount��-1�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectSubmit06() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // �e�X�g���{
        pageLinks.addDirectSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,-1,0)\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (����) totalCount:50<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,30)">
     * &gt;</a>&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��20�AtotalCount��50�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextSubmit01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // �e�X�g���{
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,30)\">&gt;</a>&nbsp;&g" +
                "t;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:0<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * ����row��0�AstartIndex��0�AtotalCount��0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextSubmit02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // �e�X�g���{
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:-1<br>
     *         (����) startIndex:-1<br>
     *         (����) totalCount:-1<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,-1,-2)">
     * &gt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row��-1�AstartIndex��-1�AtotalCount��-1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextSubmit03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // �e�X�g���{
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,-1,-2)\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextSubmit04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (����) totalCount:50<br>
     *         (���) formName:"pageLinks"<br>
     *         (���) maxLinkNo:1<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="#" onclick="pageLinkSubmit(
     * document.pageLinks.row,document.pageLinks.index,10,30)">
     * &gt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��20�AtotalCount��50�ŁAmaxLinksNo���P�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextSubmit04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        ActionMapping mapping = new ActionMapping();
        mapping.setName("pageLinks");
        pageContext.getRequest().setAttribute(Globals.MAPPING_KEY, mapping);

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // �e�X�g���{
        pageLinks.addNextSubmit(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"#\" onclick=\"pageLinkSubmit(document.page" +
                "Links.row,document.pageLinks.index,10,30)\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (����) totalCount:100<br>
     *         (���) action:"/list"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) links:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:&gt;&gt;&gt;&nbsp;&gt;&gt;&nbsp;
     * <a href="/list?row=10&index=10">&gt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��20�AtotalCount��100�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevLink01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // �e�X�g���{
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "&lt;&lt;&lt;&nbsp;&lt;&lt;&nbsp;" +
                "<a href=\"/list?row=10&index=10\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:0<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) action:"/list?no=1"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) links:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?no=1&row=0&index=0">
     * &lt;&lt;&lt;</a>&nbsp;<a href="/list?no=1&row=0&index=0">
     * &lt;&lt;</a>&nbsp;<a href="/list?no=1&row=0&index=0">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row��0�AstartIndex��0�AtotalCount��0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevLink02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // �e�X�g���{
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "<a href=\"/list?no=1&row=0&index=0\">&lt;&lt;&lt;</a" +
                ">&nbsp;<a href=\"/list?no=1&row=0&index=0\">&lt;&lt;</a>&nbs" +
                "p;<a href=\"/list?no=1&row=0&index=0\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:-1<br>
     *         (����) startIndex:-1<br>
     *         (����) totalCount:-1<br>
     *         (���) action:"/list"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) links:prev1.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?row=-1&index=0">&lt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row��-1�AstartIndex��-1�AtotalCount��-1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevLink03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // �e�X�g���{
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "<a href=\"/list?row=-1&index=0\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddPrevLink04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (����) totalCount:100<br>
     *         (���) action:"/list"<br>
     *         (���) maxLinkNo:1<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) links:prev1.char=&lt;<br>
     *                prev2.char=<br>
     *                prev5.char=&lt;&lt;<br>
     *                prev10.char=&lt;&lt;&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:
     * <a href="/list?row=10&index=10">&gt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��20�AtotalCount��100�ŁAmaxLinkNo���P�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddPrevLink04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("prev1.char", "&lt;");
        links.put("prev2.char", "");
        links.put("prev5.char", "&lt;&lt;");
        links.put("prev10.char", "&lt;&lt;&lt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 100;

        // �e�X�g���{
        pageLinks.addPrevLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "<a href=\"/list?row=10&index=10\">&lt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:100<br>
     *         (����) totalCount:130<br>
     *         (���) action:"/list?no=1"<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):"10"<br>
     *                �i�v���p�e�B����擾����maxDirectLinkCount�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?no=1&row=10&index=30">4</a>
     * &nbsp;<a href="/list?no=1&row=10&index=40">5</a>&nbsp;
     * <a href="/list?no=1&row=10&index=50">6</a>&nbsp;
     * <a href="/list?no=1&row=10&index=60">7</a>&nbsp;
     * <a href="/list?no=1&row=10&index=70">8</a>&nbsp;
     * <a href="/list?no=1&row=10&index=80">9</a>&nbsp;
     * <a href="/list?no=1&row=10&index=90">10</a>&nbsp;<b>11</b>&nbsp;
     * <a href="/list?no=1&row=10&index=110">12</a>&nbsp;
     * <a href="/list?no=1&row=10&index=120">13</a>&nbsp;<br>
     * <br>
     * ����row��10�AstartIndex��100�AtotalCount��130�ŁA
     * directLinkNo��"10"���w�肳�ꂽ�ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectLink01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "10");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 130;

        // �e�X�g���{
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"/list?no=1&row=10&index=30\">4</a>&nbsp;<a" +
                " href=\"/list?no=1&row=10&index=40\">5</a>&nbsp;<a href=\"/l" +
                "ist?no=1&row=10&index=50\">6</a>&nbsp;<a href=\"/list?no=1&r" +
                "ow=10&index=60\">7</a>&nbsp;<a href=\"/list?no=1&row=10&inde" +
                "x=70\">8</a>&nbsp;<a href=\"/list?no=1&row=10&index=80\">9</" +
                "a>&nbsp;<a href=\"/list?no=1&row=10&index=90\">10</a>&nbsp;<" +
                "b>11</b>&nbsp;<a href=\"/list?no=1&row=10&index=110\">12</a>" +
                "&nbsp;<a href=\"/list?no=1&row=10&index=120\">13</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:100<br>
     *         (����) totalCount:200<br>
     *         (���) action:"/list"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):"a"<br>
     *                �i�v���p�e�B����擾����maxDirectLinkCount�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?row=10&index=50">6</a>&nbsp;
     * <a href="/list?row=10&index=60">7</a>&nbsp;
     * <a href="/list?row=10&index=70">8</a>&nbsp;
     * <a href="/list?row=10&index=80">9</a>&nbsp;
     * <a href="/list?row=10&index=90">10</a>&nbsp;<b>11</b>&nbsp;
     * <a href="/list?row=10&index=110">12</a>&nbsp;
     * <a href="/list?row=10&index=120">13</a>&nbsp;
     * <a href="/list?row=10&index=130">14</a>&nbsp;
     * <a href="/list?row=10&index=140">15</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��100�AtotalCount��200�ŁA
     * directLinkNo��"a"���w�肳�ꂽ�ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectLink02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put(PageLinksTag.MAX_DSP_SIZE, "a");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 100;
        int totalCount = 200;

        // �e�X�g���{
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"/list?row=10&index=50\">6</a>&nbsp;<a href" +
                "=\"/list?row=10&index=60\">7</a>&nbsp;<a href=\"/list?row=10" +
                "&index=70\">8</a>&nbsp;<a href=\"/list?row=10&index=80\">9</" +
                "a>&nbsp;<a href=\"/list?row=10&index=90\">10</a>&nbsp;<b>11<" +
                "/b>&nbsp;<a href=\"/list?row=10&index=110\">12</a>&nbsp;<a h" +
                "ref=\"/list?row=10&index=120\">13</a>&nbsp;<a href=\"/list?r" +
                "ow=10&index=130\">14</a>&nbsp;<a href=\"/list?row=10&index=1" +
                "40\">15</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:2<br>
     *         (����) totalCount:200<br>
     *         (���) action:"/list"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount��
     *                �l�̒l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?row=10&index=0">1</a>
     * &nbsp;<b>2</b>&nbsp;<a href="/list?row=10&index=20">3</a>
     * &nbsp;<a href="/list?row=10&index=30">4</a>&nbsp;
     * <a href="/list?row=10&index=40">5</a>&nbsp;
     * <a href="/list?row=10&index=50">6</a>&nbsp;
     * <a href="/list?row=10&index=60">7</a>&nbsp;
     * <a href="/list?row=10&index=70">8</a>&nbsp;
     * <a href="/list?row=10&index=80">9</a>&nbsp;
     * <a href="/list?row=10&index=90">10</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��2�AtotalCount��200�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectLink03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 200;

        // �e�X�g���{
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"/list?row=10&index=0\">1</a>&nbsp;<b>2</b>" +
                "&nbsp;<a href=\"/list?row=10&index=20\">3</a>&nbsp;<a href=" +
                "\"/list?row=10&index=30\">4</a>&nbsp;<a href=\"/list?row=10&" +
                "index=40\">5</a>&nbsp;<a href=\"/list?row=10&index=50\">6</a" +
                ">&nbsp;<a href=\"/list?row=10&index=60\">7</a>&nbsp;<a href=" +
                "\"/list?row=10&index=70\">8</a>&nbsp;<a href=\"/list?row=10&" +
                "index=80\">9</a>&nbsp;<a href=\"/list?row=10&index=90\">10</" +
                "a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:2<br>
     *         (����) totalCount:50<br>
     *         (���) action:"/list"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount�̒l��
     *                �l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?row=10&index=0">1</a>&nbsp;
     * <b>2</b>&nbsp;<a href="/list?row=10&index=20">3</a>&nbsp;
     * <a href="/list?row=10&index=30">4</a>&nbsp;
     * <a href="/list?row=10&index=40">5</a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��2�AtotalCount��50�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectLink04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 2;
        int totalCount = 50;

        // �e�X�g���{
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"/list?row=10&index=0\">1</a>&nbsp;<b>2</b>" +
                "&nbsp;<a href=\"/list?row=10&index=20\">3</a>&nbsp;<a href=" +
                "\"/list?row=10&index=30\">4</a>&nbsp;<a href=\"/list?row=10&" +
                "index=40\">5</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:0<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) action:"/list"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount�̒l
     *                �̒l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?row=0&index=0">1</a>&nbsp;<br>
     *         
     * <br>
     * ����row��0�AstartIndex��0�AtotalCount��0�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectLink05() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // �e�X�g���{
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"/list?row=0&index=0\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddDirectLink06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:-1<br>
     *         (����) startIndex:-1<br>
     *         (����) totalCount:-1<br>
     *         (���) action:"/list"<br>
     *         (���) directLinkNo<br>
     *                (maxPageCount):10<br>
     *                �i�v���p�e�B����maxDirectLinkCount�̒l
     *                �̒l���擾�ł����A�f�t�H���g�̒l�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?row=-1&index=0">1</a>&nbsp;<br>
     *         
     * <br>
     * ����row��-1�AstartIndex��-1�AtotalCount��-1�ŁAdirectLinkNo��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddDirectLink06() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // �e�X�g���{
        pageLinks.addDirectLink(sb, row, startIndex, totalCount);

        // ����
        String result = "<a href=\"/list?row=-1&index=0\">1</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (����) totalCount:50<br>
     *         (���) action:"/list?no=1"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?no=1&row=10&index=30">&gt;
     * </a>&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��20�AtotalCount��50�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextLink01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // �e�X�g���{
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "<a href=\"/list?no=1&row=10&index=30\">&gt;</a>" +
                "&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:0<br>
     *         (����) startIndex:0<br>
     *         (����) totalCount:0<br>
     *         (���) action:"/list"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;<br>
     *         
     * <br>
     * ����row��0�AstartIndex��0�AtotalCount��0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextLink02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 0;
        int startIndex = 0;
        int totalCount = 0;

        // �e�X�g���{
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "&gt;&nbsp;&gt;&gt;&nbsp;&gt;&gt;&gt;&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:-1<br>
     *         (����) startIndex:-1<br>
     *         (����) totalCount:-1<br>
     *         (���) action:"/list"<br>
     *         (���) maxLinkNo:10<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?row=-1&index=-2">&gt;</a>&nbsp;<br>
     *         
     * <br>
     * ����row��-1�AstartIndex��-1�AtotalCount��-1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextLink03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(10));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = -1;
        int startIndex = -1;
        int totalCount = -1;

        // �e�X�g���{
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "<a href=\"/list?row=-1&index=-2\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testAddNextLink04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) sb:""<br>
     *         (����) row:10<br>
     *         (����) startIndex:20<br>
     *         (����) totalCount:50<br>
     *         (���) action:"/list?no=1"<br>
     *         (���) maxLinkNo:1<br>
     *         (���) rowProperty:"row"<br>
     *         (���) indexProperty:"index"<br>
     *         (���) �v���p�e�B�̐ݒ�<br>
     *                �uMap links�v:next1.char=&gt;<br>
     *                next2.char=<br>
     *                next5.char=&gt;&gt;<br>
     *                next10.char=&gt;&gt;&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) sb:<a href="/list?no=1&row=10&index=30">&gt;
     * </a>&nbsp;<br>
     *         
     * <br>
     * ����row��10�AstartIndex��20�AtotalCount��50�ŁAmaxLinkNo���P�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddNextLink04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "action", "/list?no=1");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "row");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "index");
        UTUtil.setPrivateField(pageLinks, "maxLinkNo", new Integer(1));
        TagUTUtil.setContextPath(pageLinks, "");

        Map<String, String> links = new HashMap<String, String>();
        links.put("next1.char", "&gt;");
        links.put("next2.char", "");
        links.put("next5.char", "&gt;&gt;");
        links.put("next10.char", "&gt;&gt;&gt;");
        UTUtil.setPrivateField(pageLinks, "links", links);

        StringBuilder sb = new StringBuilder();
        int row = 10;
        int startIndex = 20;
        int totalCount = 50;

        // �e�X�g���{
        pageLinks.addNextLink(sb, row, startIndex, totalCount);
        
        // ����
        String result = "<a href=\"/list?no=1&row=10&index=30\">&gt;</a>&nbsp;";
        assertEquals(result, sb.toString());
    }

    /**
     * testGetLinkProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:�L�[�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:0<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[�����݂��Ȃ������ꍇ�A
     * links�֒l���ݒ肳��Ă��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        deleteProperty("pageLinks.");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(0, links2.size());
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:0<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��"pageLinks."�̏ꍇ�A
     * links�֒l���ݒ肳��Ă��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        addProperty("pageLinks.","&lt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(0, links2.size());
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.prev1.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:1<br>
     *                    prev1.char=&lt;<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��1�����݂���ꍇ�A
     * links�֒l��1���ݒ肳��A"maxLinkNo"�Ɂu1�v���ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        addProperty("pageLinks.prev1.char", "&lt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev1.char"));
    }

    /**
     * testGetLinkProperty04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.prev2.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:1<br>
     *                    prev2.char=&lt;<br>
     *         (��ԕω�) maxLinkNo:2<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��1�����݂���ꍇ�A
     * links�֒l��1���ݒ肳��A"maxLinkNo"�Ɂu2�v���ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty04() throws Exception {
        addProperty("pageLinks.prev2.char", "&lt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(2, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev2.char"));
    }

    /**
     * testGetLinkProperty05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.prev-2.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:1<br>
     *                    prev-2.char=&lt;<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��1�����݂���ꍇ�A
     * links�֒l��1���ݒ肳��A"maxLinkNo"�Ɂu1�v���ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty05() throws Exception {
        addProperty("pageLinks.prev-2.char", "&lt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev-2.char"));
    }

    /**
     * testGetLinkProperty06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.next1.char=&gt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:1<br>
     *                    next1.char=&gt;<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��1�����݂���ꍇ�A
     * links�֒l��1���ݒ肳��A"maxLinkNo"�Ɂu1�v���ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty06() throws Exception {
        addProperty("pageLinks.next1.char", "&gt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("&gt;", (String) links2.get("next1.char"));
    }

    /**
     * testGetLinkProperty07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.test.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:0<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��1�����݂��邪�L�[��
     * �w��O�̏ꍇ�Alinks�֒l���ݒ肳��Ă��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty07() throws Exception {
        addProperty("pageLinks.test.char", "&lt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(0, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.nexttest.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:0<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��1�����݂��邪�L�[��
     * �y�[�W�W�����v���̎w�肪������̏ꍇ�A
     * links�֒l���ݒ肳��Ă��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty08() throws Exception {
        addProperty("pageLinks.nexttest.char", "&lt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(0, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
    }

    /**
     * testGetLinkProperty09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.prev1.char=&lt;<br>
     *                pageLinks.prev2.char=&lt;<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:2<br>
     *                    prev1.char=&lt;<br>
     *                    prev2.char=&lt;<br>
     *         (��ԕω�) maxLinkNo:2<br>
     *         
     * <br>
     * property�t�@�C����PAGE_LINKS_PREFIX�̃L�[��2�����݂���ꍇ�A
     * links�֒l��2���ݒ肳��A"maxLinkNo"�Ɂu2�v���ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty09() throws Exception {
        addProperty("pageLinks.prev1.char", "&lt;");
        addProperty("pageLinks.prev2.char", "&lt;");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(2, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(2, maxLinkNo2.intValue());
        assertEquals("&lt;", (String) links2.get("prev1.char"));
        assertEquals("&lt;", (String) links2.get("prev2.char"));
    }

    /**
     * testGetLinkProperty10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.maxDirectLinkCount=3<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:1<br>
     *                    maxDirectLinkCount=3<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����pageLinks.maxDirectLinkCount=3���w�肳�ꂽ
     * �ꍇ�̃e�X�g�P�[�X�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty10() throws Exception {
        addProperty("pageLinks.maxDirectLinkCount", "3");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("3", (String) links2.get("maxDirectLinkCount"));
    }

    /**
     * testGetLinkProperty11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.maxDirectLinkCount=0<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:1<br>
     *                    maxDirectLinkCount=0<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����pageLinks.maxDirectLinkCount=0
     * ���w�肳�ꂽ�ꍇ�̃e�X�g�P�[�X�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty11() throws Exception {
        addProperty("pageLinks.maxDirectLinkCount", "0");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("0", (String) links2.get("maxDirectLinkCount"));
    }

    /**
     * testGetLinkProperty12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) property�t�@�C���ɐݒ肷��PAGE_LINKS_PREFIX:
     * pageLinks.maxDirectLinkCount=-1<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) links:size:1<br>
     *                    maxDirectLinkCount=-1<br>
     *         (��ԕω�) maxLinkNo:1<br>
     *         
     * <br>
     * property�t�@�C����pageLinks.maxDirectLinkCount=-1��
     * �w�肳�ꂽ�ꍇ�̃e�X�g�P�[�X�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLinkProperty12() throws Exception {
        addProperty("pageLinks.maxDirectLinkCount", "-1");

        //�e�X�g���s
        UTUtil.invokePrivate(pageLinks, "getLinkProperty");

        //�e�X�g���ʊm�F
        Map links2 = (Map) UTUtil.getPrivateField(pageLinks, "links");
        assertEquals(1, links2.size());
        Integer maxLinkNo2 =
            (Integer) UTUtil.getPrivateField(pageLinks, "maxLinkNo");
        assertEquals(1, maxLinkNo2.intValue());
        assertEquals("-1", (String) links2.get("maxDirectLinkCount"));
    }

    /**
     * testGetPageIndex01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:0<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *         
     * <br>
     * �\���s����0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageIndex01() throws Exception {
        // �O����
        int row = 0;
        int startIndex = 0;

        // �e�X�g���{
        int value = pageLinks.getPageIndex(row, startIndex);

        // ����
        assertEquals(0, value);
    }

    /**
     * testGetPageIndex02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:1<br>
     *         (����) startIndex:0<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         
     * <br>
     * �\���s����1�ŁA�J�n�s����0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageIndex02() throws Exception {
        // �O����
        int row = 1;
        int startIndex = 0;

        // �e�X�g���{
        int value = pageLinks.getPageIndex(row, startIndex);

        // ����
        assertEquals(1, value);
    }

    /**
     * testGetPageIndex03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:-10<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *         
     * <br>
     * �\��������-10�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageIndex03() throws Exception {
        // �O����
        int row = -10;
        int startIndex = 0;

        // �e�X�g���{
        int value = pageLinks.getPageIndex(row, startIndex);

        // ����
        assertEquals(0, value);
    }

    /**
     * testGetPageIndex04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:2<br>
     *         
     * <br>
     * �\��������10�ŁA�J�n�s����1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageIndex04() throws Exception {
        // �O����
        int row = 10;
        int startIndex = 1;

        // �e�X�g���{
        int value = pageLinks.getPageIndex(row, startIndex);

        // ����
        assertEquals(2, value);
    }

    /**
     * testGetPageIndex05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:-10<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *         
     * <br>
     * �\��������10�ŁA�J�n�s����-10�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageIndex05() throws Exception {
        // �O����
        int row = 10;
        int startIndex = -10;

        // �e�X�g���{
        int value = pageLinks.getPageIndex(row, startIndex);

        // ����
        assertEquals(0, value);
    }

    /**
     * testGetPageIndex06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:10<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:2<br>
     *         
     * <br>
     * �\��������10�ŁA�J�n�s����10�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageIndex06() throws Exception {
        // �O����
        int row = 10;
        int startIndex = 10;

        // �e�X�g���{
        int value = pageLinks.getPageIndex(row, startIndex);

        // ����
        assertEquals(2, value);
    }

    /**
     * testGetPageIndex07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) startIndex:12<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:3<br>
     *         
     * <br>
     * �\��������10�ŁA�J�n�s����12�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageIndex07() throws Exception {
        // �O����
        int row = 10;
        int startIndex = 12;

        // �e�X�g���{
        int value = pageLinks.getPageIndex(row, startIndex);

        // ����
        assertEquals(3, value);
    }

    /**
     * testGetPageCount01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:0<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         
     * <br>
     * �\���s����0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageCount01() throws Exception {
        // �O����
        int row = 0;
        int totalCount = 0;

        // �e�X�g���{
        int value = pageLinks.getPageCount(row, totalCount);

        // ����
        assertEquals(1, value);
    }

    /**
     * testGetPageCount02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:1<br>
     *         (����) totalCount:0<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *         
     * <br>
     * �\���s����1�ŁA�S������0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageCount02() throws Exception {
        // �O����
        int row = 1;
        int totalCount = 0;

        // �e�X�g���{
        int value = pageLinks.getPageCount(row, totalCount);

        // ����
        assertEquals(0, value);
    }

    /**
     * testGetPageCount03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:-10<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         
     * <br>
     * �\��������-10�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageCount03() throws Exception {
        // �O����
        int row = -10;
        int totalCount = 1;

        // �e�X�g���{
        int value = pageLinks.getPageCount(row, totalCount);

        // ����
        assertEquals(1, value);
    }

    /**
     * testGetPageCount04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) totalCount:1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         
     * <br>
     * �\��������10�ŁA�S������1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageCount04() throws Exception {
        // �O����
        int row = 10;
        int totalCount = 1;

        // �e�X�g���{
        int value = pageLinks.getPageCount(row, totalCount);

        // ����
        assertEquals(1, value);
    }

    /**
     * testGetPageCount05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) totalCount:-10<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:-1<br>
     *         
     * <br>
     * �\��������10�ŁA�S������-10�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageCount05() throws Exception {
        // �O����
        int row = 10;
        int totalCount = -10;

        // �e�X�g���{
        int value = pageLinks.getPageCount(row, totalCount);

        // ����
        assertEquals(-1, value);
    }

    /**
     * testGetPageCount06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) totalCount:10<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         
     * <br>
     * �\��������10�ŁA�S������10�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageCount06() throws Exception {
        // �O����
        int row = 10;
        int totalCount = 10;

        // �e�X�g���{
        int value = pageLinks.getPageCount(row, totalCount);

        // ����
        assertEquals(1, value);
    }

    /**
     * testGetPageCount07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) row:10<br>
     *         (����) totalCount:15<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:2<br>
     *         
     * <br>
     * �\��������10�ŁA�S������15�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageCount07() throws Exception {
        // �O����
        int row = 10;
        int totalCount = 15;

        // �e�X�g���{
        int value = pageLinks.getPageCount(row, totalCount);

        // ����
        assertEquals(2, value);
    }

    /**
     * testGetPageContextFlg01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         
     * <br>
     * ����key��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg01() throws Exception {
        // �O����
        String key = null;

        //MockRunner��MockPageContext���g�p����ƁA
        //getAttaribute���\�b�h�̈���key��null�ł�NullPointerException��
        //�������Ȃ����߁ASpring��MockPageContext���g�p����B
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        pageLinks.setPageContext(pageContext);

        // �e�X�g���{
        try {
            pageLinks.getPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //��O���m�F�B
        }
    }

    /**
     * testGetPageContextFlg02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:""<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=Boolean(true)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * ����key���󔒂ŁApageContext��Boolean(true)�Œl�����݂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg02() throws Exception {
        // �O����
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key",new Boolean(true));

        // �e�X�g���{
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // ����
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=String("true")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * pageContext��String("true")�Œl�����݂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg03() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key","true");

        // �e�X�g���{
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // ����
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=Boolean(true)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * pageContext��Boolean(true)�Œl�����݂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg04() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key",new Boolean(true));

        // �e�X�g���{
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // ����
        assertTrue(value);
    }

    /**
     * testGetPageContextFlg05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * pageContext�ɐݒ肳��Ă���l��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg05() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("key",null);

        // �e�X�g���{
        boolean value = pageLinks.getPageContextFlg(pageContext, key);

        // ����
        assertFalse(value);
    }

    /**
     * testSetPageContextFlg01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:null<br>
     *         (���) pageContext���ŕێ����Ă���l:�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         
     * <br>
     * ����key��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPageContextFlg01() throws Exception {
        // �O����
        String key = null;

        //MockRunner��MockPageContext���g�p����ƁA
        //setAttaribute���\�b�h�̈���key��null�ł�NullPointerException��
        //�������Ȃ����߁ASpring��MockPageContext���g�p����B
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        pageLinks.setPageContext(pageContext);

        // �e�X�g���{
        try {
            pageLinks.setPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //��O���m�F�B
        }
    }

    /**
     * testSetPageContextFlg02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:""<br>
     *         (���) pageContext���ŕێ����Ă���l:�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext���ŕێ����Ă���l:""=Boolean(true)<br>
     *         
     * <br>
     * ����key���󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPageContextFlg02() throws Exception {
        // �O����
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);

        // �e�X�g���{
        pageLinks.setPageContextFlg(pageContext, key);

        // ����
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testSetPageContextFlg03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext���ŕێ����Ă���l:"key"=Boolean(true)<br>
     *         
     * <br>
     * ����key��"key"�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPageContextFlg03() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);

        // �e�X�g���{
        pageLinks.setPageContextFlg(pageContext, key);

        // ����
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testLookup01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:["row"= "a"]<br>
     *         (����) name:null<br>
     *         (����) property:"row"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"a"<br>
     *         
     * <br>
     * name��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup01() throws Exception {
        // �O����
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("row", "a");
        String name = null;
        String property = "row";

        // �e�X�g���{
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // ����
        assertEquals("a", obj);
    }

    /**
     * testLookup02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:["row"= "b"]<br>
     *         (����) name:""<br>
     *         (����) property:"row"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"b"<br>
     *         
     * <br>
     * name���󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup02() throws Exception {
        // �O����
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        pageContext.setAttribute("row", "b");
        String name = "";
        String property = "row";

        // �e�X�g���{
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // ����
        assertEquals("b", obj);
    }

    /**
     * testLookup03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:["bean"= <br>
     *                new PageLinksTag_BeanStub01(){<br>
     *                row="c", index="0", total="102"}]<br>
     *         (����) name:"bean"<br>
     *         (����) property:"row"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"c"<br>
     *         
     * <br>
     * name��NotNull�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup03() throws Exception {
        // �O����
        PageContext pageContext = TagUTUtil.getPageContext(pageLinks);
        PageLinksTag_BeanStub01 bean = new PageLinksTag_BeanStub01();
        bean.setRow("c");
        pageContext.setAttribute("bean", bean);
        String name = "bean";
        String property = "row";

        // �e�X�g���{
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // ����
        assertEquals("c", obj);
    }

    /**
     * testLookup04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:null<br>
     *         (����) name:null<br>
     *         (����) property:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         
     * <br>
     * property��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup04() throws Exception {
        // �O����
        PageContext pageContext = null;
        String name = null;
        String property = null;

        // �e�X�g���{
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // ����
        assertNull(obj);
    }

    /**
     * testLookup05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:null<br>
     *         (����) name:null<br>
     *         (����) property:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         
     * <br>
     * property���󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup05() throws Exception {
        // �O����
        PageContext pageContext = null;
        String name = null;
        String property = "";

        // �e�X�g���{
        Object obj = pageLinks.lookup(pageContext, name, property, null);

        // ����
        assertNull(obj);
    }

    /**
     * testGetInt01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) Object:Integer(1)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         
     * <br>
     * ������Integer�̏ꍇ
     * �ُ�n��doStartTag()�ɂăe�X�g�ς݁B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInt01() throws Exception {
        // �O����
        Integer obj = new Integer(1);

        // �e�X�g���{
        int i = pageLinks.getInt(obj);

        // ����
        assertEquals(1, i);
    }

    /**
     * testGetInt02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) Object:String("2")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:2<br>
     *         
     * <br>
     * ������String�̏ꍇ
     * �ُ�n��doStartTag()�ɂăe�X�g�ς݁B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInt02() throws Exception {
        // �O����
        String obj = "2";

        // �e�X�g���{
        int i = pageLinks.getInt(obj);

        // ����
        assertEquals(2, i);
    }

    /**
     * testAttributePageCount01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) now:10<br>
     *         (����) total:100<br>
     *         (���) currentPageIndex:null<br>
     *         (���) totalPageCount:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext�ɕۑ�����Ă���y�[�W�J�E���g:currentPageIndex=10<br>
     *                    totalPageCount=100<br>
     *         
     * <br>
     * null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAttributePageCount01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", null);
        UTUtil.setPrivateField(pageLinks, "totalPageCount", null);

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // �e�X�g���{
        pageLinks.attributePageCount(10,100);

        // ����
        assertEquals(10,
                pageContext.getAttribute(PageLinksTag.CURRENT_PAGE_INDEX));
        assertEquals(100,
                pageContext.getAttribute(PageLinksTag.TOTAL_PAGE_COUNT));
    }

    /**
     * testAttributePageCount02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) now:10<br>
     *         (����) total:100<br>
     *         (���) currentPageIndex:""<br>
     *         (���) totalPageCount:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext�ɕۑ�����Ă���y�[�W�J�E���g:currentPageIndex=10<br>
     *                    totalPageCount=100<br>
     *         
     * <br>
     * �󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAttributePageCount02() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // �e�X�g���{
        pageLinks.attributePageCount(10,100);

        // ����
        assertEquals(10,
                pageContext.getAttribute(PageLinksTag.CURRENT_PAGE_INDEX));
        assertEquals(100,
                pageContext.getAttribute(PageLinksTag.TOTAL_PAGE_COUNT));
    }

    /**
     * testAttributePageCount03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) now:10<br>
     *         (����) total:100<br>
     *         (���) currentPageIndex:"nownow"<br>
     *         (���) totalPageCount:"totaltotal"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext�ɕۑ�����Ă���y�[�W�J�E���g:nownow=10<br>
     *                    totaltotal=100<br>
     *         
     * <br>
     * ������̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAttributePageCount03() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "nownow");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "totaltotal");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // �e�X�g���{
        pageLinks.attributePageCount(10,100);

        // ����
        assertEquals(10,
                pageContext.getAttribute("nownow"));
        assertEquals(100,
                pageContext.getAttribute("totaltotal"));
    }

    /**
     * testAttributePageCount04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) now:1<br>
     *         (����) total:0<br>
     *         (���) currentPageIndex:"nownow"<br>
     *         (���) totalPageCount:"totaltotal"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext�ɕۑ�����Ă���y�[�W�J�E���g:nownow=0<br>
     *                    totaltotal=0<br>
     *         
     * <br>
     * ������total���O�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAttributePageCount04() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "nownow");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "totaltotal");

        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(pageLinks);

        // �e�X�g���{
        pageLinks.attributePageCount(1,0);

        // ����
        assertEquals(0,
                pageContext.getAttribute("nownow"));
        assertEquals(0,
                pageContext.getAttribute("totaltotal"));
    }

    /**
     * testRelease01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) super.parent:new TagSupport()<br>
     *         (���) action:"abc"<br>
     *         (���) name:"abc"<br>
     *         (���) rowProperty:"abc"<br>
     *         (���) indexProperty:"abc"<br>
     *         (���) totalProperty:"abc"<br>
     *         (���) scope:"abc"<br>
     *         (���) submit:true<br>
     *         (���) forward:true<br>
     *         (���) event:"abc"<br>
     *         (���) resetIndex:true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) super.parent:null<br>
     *         (��ԕω�) action:null<br>
     *         (��ԕω�) name:null<br>
     *         (��ԕω�) rowProperty:null<br>
     *         (��ԕω�) indexProperty:null<br>
     *         (��ԕω�) totalProperty:null<br>
     *         (��ԕω�) scope:null<br>
     *         (��ԕω�) submit:false<br>
     *         (��ԕω�) forward:false<br>
     *         (��ԕω�) event:PageLinksTag.DEFAULT_EVENT<br>
     *         (��ԕω�) resetIndex:false<br>
     *         
     * <br>
     * ����n�̂݁B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRelease01() throws Exception {
        // �O����
        UTUtil.setPrivateField(pageLinks, "parent", new TagSupport());
        UTUtil.setPrivateField(pageLinks, "id", "abc");
        UTUtil.setPrivateField(pageLinks, "action", "abc");
        UTUtil.setPrivateField(pageLinks, "name", "abc");
        UTUtil.setPrivateField(pageLinks, "rowProperty", "abc");
        UTUtil.setPrivateField(pageLinks, "indexProperty", "abc");
        UTUtil.setPrivateField(pageLinks, "totalProperty", "abc");
        UTUtil.setPrivateField(pageLinks, "scope", "abc");
        UTUtil.setPrivateField(pageLinks, "submit", true);
        UTUtil.setPrivateField(pageLinks, "forward", true);
        UTUtil.setPrivateField(pageLinks, "event", "abc");
        UTUtil.setPrivateField(pageLinks, "resetIndex", true);
        UTUtil.setPrivateField(pageLinks, "currentPageIndex", "abc");
        UTUtil.setPrivateField(pageLinks, "totalPageCount", "abc");

        // �e�X�g���{
        pageLinks.release();

        // ����
        assertNull(UTUtil.getPrivateField(pageLinks, "parent"));
        assertNull(UTUtil.getPrivateField(pageLinks, "id"));
        assertNull(UTUtil.getPrivateField(pageLinks, "action"));
        assertNull(UTUtil.getPrivateField(pageLinks, "name"));
        assertNull(UTUtil.getPrivateField(pageLinks, "rowProperty"));
        assertNull(UTUtil.getPrivateField(pageLinks, "indexProperty"));
        assertNull(UTUtil.getPrivateField(pageLinks, "totalProperty"));
        assertNull(UTUtil.getPrivateField(pageLinks, "scope"));
        assertFalse(((Boolean)UTUtil.getPrivateField(
                pageLinks, "submit")).booleanValue());
        assertFalse(((Boolean)UTUtil.getPrivateField(
                pageLinks, "forward")).booleanValue());
        assertEquals(PageLinksTag.DEFAULT_EVENT, 
                UTUtil.getPrivateField(pageLinks, "event"));
        assertFalse(((Boolean)UTUtil.getPrivateField(
                pageLinks, "resetIndex")).booleanValue());
        assertEquals(PageLinksTag.CURRENT_PAGE_INDEX, 
                UTUtil.getPrivateField(pageLinks, "currentPageIndex"));
        assertEquals(PageLinksTag.TOTAL_PAGE_COUNT, 
                UTUtil.getPrivateField(pageLinks, "totalPageCount"));
    }

}
