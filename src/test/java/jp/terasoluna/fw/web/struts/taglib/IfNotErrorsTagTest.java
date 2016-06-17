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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * IfNotErrorsTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class IfNotErrorsTagTest extends TestCase {

    // �e�X�g�Ώ�
    IfNotErrorsTag tag = null;

    /**
     * Constructor for IfNotErrorsTagTest.
     * @param arg0
     */
    public IfNotErrorsTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (IfNotErrorsTag) TagUTUtil.create(IfNotErrorsTag.class);
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
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * requestErrors=Not Null<br>
     * sessionErrors=Not Null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=SKIP_BODY<br>
     * <br>
     * ���N�G�X�g����擾�����G���[��Null�ł͂Ȃ��ꍇ����<br>
     * �Z�b�V��������擾�����G���[��Null�ł͂Ȃ��ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag01() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();
        ActionMessages errors = new ActionMessages();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
        rq.setAttribute(Globals.ERROR_KEY, errors);

        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);
        
        
        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag01 End */

    /**
     * testDoStartTag01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * requestErrors=Null<br>
     * sessionErrors=Not Null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * ���N�G�X�g����擾�����G���[��Null�ł���ꍇ����<br>
     * �Z�b�V��������擾�����G���[��Null�ł͂Ȃ��ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag02() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();
        ActionMessages errors = new ActionMessages();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));

        HttpSession session = rq.getSession(true);
        session.setAttribute(Globals.ERROR_KEY, errors);
        
        
        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag02 End */

    
    /**
     * testDoStartTag03�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * requestErrors=Not Null<br>
     * sessionErrors=Null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * ���N�G�X�g����擾�����G���[��Null�ł͂Ȃ��ꍇ����<br>
     * �Z�b�V��������擾�����G���[��Null�ł���ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag03() throws Exception {

        // �e�X�g�ݒ�
        PageContext pc = TagUTUtil.getPageContext(tag);
        HttpServletRequest rq = (HttpServletRequest) pc.getRequest();
        ActionMessages errors = new ActionMessages();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(""));
        rq.setAttribute(Globals.ERROR_KEY, errors);

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    } /* testDoStartTag03 End */

    
    /**
     * testDoStartTag04�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * requestErrors=Null<br>
     * sessionErrors=Null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:int=EVAL_BODY_INCLUDE<br>
     * <br>
     * ���N�G�X�g����擾�����G���[��Null�̏ꍇ����<br>
     * �Z�b�V��������擾�����G���[��Null�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag04() throws Exception {

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
     * ���͒l
     * �Ȃ�<br>
     * 
     * ���Ғl
     * �߂�l:int=EVAL_PAGE<br>
     * 
     * EVAL_PAGE���ԋp����邱�Ƃ��m�F����<br>
     */
    public void testDoEndTag01() throws Exception {
        // �e�X�g���s
        int result = tag.doEndTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.EVAL_PAGE, result);
    }

} /* IfNotErrorsTagTest Class End */
