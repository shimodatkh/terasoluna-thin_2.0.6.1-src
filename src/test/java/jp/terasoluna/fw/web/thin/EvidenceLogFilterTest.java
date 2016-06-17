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

package jp.terasoluna.fw.web.thin;

import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * {@link jp.terasoluna.fw.web.thin.EvidenceLogFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �G�r�f���X���O���o�͏������s���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.EvidenceLogFilter
 */
public class EvidenceLogFilterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(EvidenceLogFilterTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public EvidenceLogFilterTest(String name) {
        super(name);
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (����n)<br>
     * <br>
     * �ϓ_�FA <br>
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EVIDENCELOG_THRU_KEY:null<br>
     *         (���) requestURI:null<br>
     *         (���) paramMap:���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = null<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEY��null�̏ꍇ�A ���N�G�X�gURI(null)�ƃp�����[�^(���Map)�����O�ɏo�͂��AFilterChain#doFilter()���\�b�h�����s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI(null);
        
        // �O����� �EEVIDENCELOG_THRU_KEY��Null
        //        �ErequestURI��Null
        //        �EparamMap����}�b�v
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = null"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilter�����s���邩
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (����n)<br>
     * <br>
     * �ϓ_�FA <br>
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EVIDENCELOG_THRU_KEY:null<br>
     *         (���) requestURI:""<br>
     *         (���) paramMap:["param1"->null]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = "<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = null"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEY��null�̏ꍇ�A���N�G�X�gURI(�󕶎�)�ƃp�����[�^(1���Œl��null)�����O�ɏo�͂��A FilterChain#doFilter()���\�b�h�����s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("");
        // paramMap:["param1"->null]
        String value = null;
        request.addParameter("param1", value);
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = "));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = null"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilter�����s���邩
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (����n)<br>
     * <br>
     * �ϓ_�FA <br>
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EVIDENCELOG_THRU_KEY:null<br>
     *         (���) requestURI:"reqURI"<br>
     *         (���) paramMap:["param1"->""]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = "<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEY��null�̏ꍇ�A���N�G�X�gURI(not null)�ƃp�����[�^(1���Œl���󕶎�)�����O�ɏo�͂��AFilterChain#doFilter()���\�b�h�����s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // paramMap:["param1"->""]
        request.addParameter("param1", "");
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = "));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilter�����s���邩
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (����n)<br>
     * <br>
     * �ϓ_�FA <br>
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EVIDENCELOG_THRU_KEY:null<br>
     *         (���) requestURI:"reqURI"<br>
     *         (���) paramMap:["param1"->"test1"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEY��null�̏ꍇ�A���N�G�X�gURI(not null)�ƃp�����[�^(1��)�����O�ɏo�͂��AFilterChain#doFilter()���\�b�h�����s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // paramMap:["param1"->test1]
        request.addParameter("param1", "test1");
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilter�����s���邩
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (����n)<br>
     * <br>
     * �ϓ_�FA <br>
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EVIDENCELOG_THRU_KEY:null<br>
     *         (���) requestURI:"reqURI"<br>
     *         (���) paramMap:["param1"->"test1"]<br>
     *                ["param2"->"test2"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***:   param2[0] = test2"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEY��null�̏ꍇ�A���N�G�X�gURI(not null)�ƃp�����[�^(2��)�����O�ɏo�͂��AFilterChain#doFilter()���\�b�h�����s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter05() throws Exception {
        // �O����
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // ["param1"->"test1"]
        // ["param2"->"test2"]
        request.addParameter("param1", "test1");
        request.addParameter("param2", "test2");
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param2[0] = test2"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilter�����s���邩
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter06()
     * <br><br>
     * 
     * (����n)<br>
     * <br>
     * �ϓ_�FA <br>
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EVIDENCELOG_THRU_KEY:null<br>
     *         (���) requestURI:"reqURI"<br>
     *         (���) paramMap:["param1"->("test1", "test2", "test3")]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***:   param1[1] = test2"<br>
     *                    "**** EVIDENCE ***:   param1[2] = test3"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEY��null�̏ꍇ�A���N�G�X�gURI(not null)�ƃp�����[�^(1���Œl��Array)�����O�ɏo�͂��AFilterChain#doFilter()���\�b�h�����s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter06() throws Exception {
        // �O����
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // ["param1"->("test1", "test2", "test3")]
        request.addParameter("param1", new String[]{"test1", "test2", "test3"});
        
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[1] = test2"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[2] = test3"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilter�����s���邩
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter07()
     * <br><br>
     * 
     * (����n)<br>
     * <br>
     * �ϓ_�FA <br>
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EVIDENCELOG_THRU_KEY:null<br>
     *         (���) requestURI:"reqURI"<br>
     *         (���) paramMap:["param1"->{"test1", "test2", "test3", "", null}]<br>
     *                ["param2"->"�e�X�g"]<br>
     *                ["param3"->{}]���v�f�������z��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EVIDENCELOG_THRU_KEY:"true"<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *                    "**** EVIDENCE ***: RequestURI = reqURI"<br>
     *                    "**** EVIDENCE ***: Parameters = {"<br>
     *                    "**** EVIDENCE ***:   param1[0] = test1"<br>
     *                    "**** EVIDENCE ***:   param1[1] = test2"<br>
     *                    "**** EVIDENCE ***:   param1[2] = test3"<br>
     *                    "**** EVIDENCE ***:   param1[3] = "<br>
     *                    "**** EVIDENCE ***:   param1[4] = null"<br>
     *                    "**** EVIDENCE ***:   param2[0] = �e�X�g"<br>
     *                    "**** EVIDENCE ***: }"<br>
     *                    "**** EVIDENCE ***: --------------------------------------------"<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * EVIDENCELOG_THRU_KEY��null�̏ꍇ�A���N�G�X�gURI(not null)�ƃp�����[�^(3����2�̒l��Array)�����O�ɏo�͂��AFilterChain#doFilter()���\�b�h�����s���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter07() throws Exception {
        // �O����
        EvidenceLogFilter filter = new EvidenceLogFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // requestURI:""
        request.setRequestURI("reqURI");
        // ["param1"->{"test1", "test2", "test3", "", null}]
        // ["param2"->"�e�X�g"]
        // ["param3"->{}]���v�f�������z��
        request.addParameter("param1", new String[]{"test1", "test2", "test3", "", null});
        request.addParameter("param2", "�e�X�g");
        request.addParameter("param3", new String[]{});
        EvidenceLogFilter_FilterChainStub01 filterChain =
            new EvidenceLogFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EVIDENCELOG_THRU_KEY"));
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: RequestURI = reqURI"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: Parameters = {"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[0] = test1"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[1] = test2"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[2] = test3"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[3] = "));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param1[4] = null"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***:   param2[0] = �e�X�g"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: }"));
        assertTrue(LogUTUtil.checkDebug("**** EVIDENCE ***: --------------------------------------------"));
        // doFilter�����s���邩
        assertTrue(filterChain.isCalled);
    }

}
