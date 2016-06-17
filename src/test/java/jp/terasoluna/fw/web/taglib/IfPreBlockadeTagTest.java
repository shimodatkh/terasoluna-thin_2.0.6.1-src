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

import jp.terasoluna.fw.web.thin.ServerBlockageControlFilter;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * IfPreBlockadeTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * WebLogic�T�[�o���N�����Ă��邱��<br>
 * <br>
 */
public class IfPreBlockadeTagTest extends TestCase {

    // �e�X�g�Ώ�
    IfPreBlockadeTag tag = null;

    /**
     * Constructor for IfPreBlockadeTagTest.
     * @param arg0
     */
    public IfPreBlockadeTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfPreBlockadeTag) TagUTUtil.create(IfPreBlockadeTag.class);
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
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * sbc=Not Null<br>
     * isPreBlockaded=false<br>
     * isBlockaded=false<br>
     * req=Not Null(requestURI,contextPath����)<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=SKIP_BODY<br>
     * <br>
     * isPreBlockaded�AisBlockaded����false�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag01() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub01 sbc = 
                new IfPreBlockageTag_ServerBlockageControllerStub01();
        // �e�X�g�ݒ�
        sbc.open();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // �[�����N�G�X�g
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag02�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * sbc=Not Null<br>
     * isPreBlockaded=true<br>
     * isBlockaded=false<br>
     * req=Not Null(requestURI,contextPath����)<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * isPreBlockaded��true�AisBlockaded��false�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag02() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub02 sbc = 
            new IfPreBlockageTag_ServerBlockageControllerStub02();
        // �e�X�g�ݒ�
        sbc.preBlockade();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // �[�����N�G�X�g
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag02 End */

    /**
     * testDoStartTag03�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * sbc=Not Null<br>
     * isPreBlockaded=false<br>
     * isBlockaded=true<br>
     * req=Not Null(requestURI,contextPath����)<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * isPreBlockaded��false�AisBlockaded��true�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag03() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub03 sbc = 
            new IfPreBlockageTag_ServerBlockageControllerStub03();
        // �e�X�g�ݒ�
        sbc.blockade();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // �[�����N�G�X�g
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag03 End */

    /**
     * testDoStartTag04�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * sbc=Not Null<br>
     * isPreBlockaded=true<br>
     * isBlockaded=true<br>
     * req=Not Null(requestURI,contextPath����)<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * isPreBlockaded��true�AisBlockaded��true�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag04() throws Exception {
        IfPreBlockageTag_ServerBlockageControllerStub04 sbc = 
            new IfPreBlockageTag_ServerBlockageControllerStub04();
        // �e�X�g�ݒ�
        sbc.preBlockade();
        UTUtil.setPrivateField(
            ServerBlockageControlFilter.class,
            "controller",
            sbc);
        // �[�����N�G�X�g
        PageContext pc = TagUTUtil.getPageContext(tag);
        MockHttpServletRequest req = (MockHttpServletRequest) pc.getRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_BODY_INCLUDE, result);

    } /* testDoStartTag04 End */

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
    } /* testDoEndTag01 End */

}
