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

package jp.terasoluna.fw.web;

import java.util.HashMap;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.RequestUtil} �N���X�� �u���b�N�{�b�N�X�e�X�g�B
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4> Request�Ɋւ��郆�[�e�B���e�B�N���X�B
 * <p>
 * @see jp.terasoluna.fw.web.RequestUtil
 */
public class RequestUtilTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestUtilTest.class);
    }

    /**
     * �������������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public RequestUtilTest(String name) {
        super(name);
    }

    /**
     * testGetPathInfo01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getRequestURI:"/sample1/logon.do"<br>
     * (���) request.getContextPath:"/sample1"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/logon.do"<br>
     * <br>
     * ���N�G�X�g�p�X��<�R���e�L�X�g>/<���\�[�X��>�̌`���̏ꍇ�A /<���\�[�X��>���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPathInfo01() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/sample1/logon.do");
        req.setContextPath("/sample1");

        // �e�X�g���s
        String path = RequestUtil.getPathInfo(req);

        // �e�X�g���ʊm�F
        // �R���e�L�X�g�p�X�ȍ~��URI���擾�ł��邱��
        assertEquals("/logon.do", path);
    }

    /**
     * testGetPathInfo02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getRequestURI:"/sample2"<br>
     * (���) request.getContextPath:"/sample2"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     * <br>
     * ���N�G�X�g�p�X��<�R���e�L�X�g>�̌`���̏ꍇ�A �󕶎����擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPathInfo02() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setRequestURI("/sample2");
        req.setContextPath("/sample2");

        // �e�X�g���s
        String path = RequestUtil.getPathInfo(req);

        // �e�X�g���ʊm�F
        // �󕶎��񂪎擾�ł��邱�ƁB
        assertEquals("", path);
    }

    /**
     * testGetPathInfo03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) request:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * <br>
     * ������request��null�̏ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPathInfo03() throws Exception {
        // �e�X�g���{
        String path = RequestUtil.getPathInfo(null);

        // ����
        assertNull(path);
    }

    /**
     * testGetServletContext01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getSession:not null<br>
     * (���) session.getServletContext:not null<br>
     * <br>
     * ���Ғl�F(�߂�l) ServletContext:session����擾���� �T�[�u���b�g�R���e�L�X�g�C���X�^���X<br>
     * <br>
     * �Z�b�V��������T�[�u���b�g�R���e�L�X�g�C���X�^���X���擾�ł��邱�Ƃ� �m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetServletContext01() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockServletContext ctx = new MockServletContext();
        MockHttpSession session = new MockHttpSession();
        session.setServletContext(ctx);
        req.setSession(session);

        // �e�X�g���{
        Object result = RequestUtil.getServletContext(req);

        // ����
        assertSame(ctx, result);
    }

    /**
     * testGetServletContext02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) request:null<br>
     * <br>
     * ���Ғl�F(�߂�l) ServletContext:null<br>
     * <br>
     * ������request��null�̏ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetServletContext02() throws Exception {
        // �e�X�g���{
        Object result = RequestUtil.getServletContext(null);

        // ����
        assertNull(result);
    }

    /**
     * testIsChanged01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getRequestURI:"/sample1/logon/start.do"<br>
     * (���) request.getAttribute("PREV_PATH_INFO"):"/logon/start.do" <br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false���ԋp����邱�ƁB<br>
     * <br>
     * �R���e�L�X�g���[�g�ȉ��̊K�w�ƃ��N�G�X�g�ɕۑ����ꂽ�O��̃p�X�� ���K�w����v����ꍇ�Afalse���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsChanged01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ���݂̃R���e�L�X�g�p�X
        req.setContextPath("/sample1");

        // ���݂̃��N�G�X�g�p�X
        req.setRequestURI("/sample1/logon/start.do");

        // �O��̃��N�G�X�g�p�X
        req.setAttribute("PREV_PATH_INFO", "/logon/start.do");

        // �e�X�g���s
        boolean result = RequestUtil.isChanged(req);

        // �e�X�g���ʊm�F
        // �Ɩ����؂�ւ���Ă��Ȃ����ƁB
        assertFalse(result);
    }

    /**
     * testIsChanged02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getRequestURI:"/sample1/logon/start.do"<br>
     * (���) request.getAttribute("PREV_PATH_INFO"):"/logon/end.do"<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false���ԋp����邱�ƁB<br>
     * <br>
     * �R���e�L�X�g���[�g�ȉ��̊K�w�ƃ��N�G�X�g�ɕۑ����ꂽ�O��̃p�X�� ���K�w����v����ꍇ�Afalse���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsChanged02() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ���݂̃R���e�L�X�g�p�X
        req.setContextPath("/sample1");

        // ���݂̃��N�G�X�g�p�X
        req.setRequestURI("/sample1/logon/start.do");

        // �O��̃��N�G�X�g�p�X
        req.setAttribute("PREV_PATH_INFO", "/logon/end.do");

        // �e�X�g���s
        boolean result = RequestUtil.isChanged(req);

        // �e�X�g���ʊm�F
        // �Ɩ����؂�ւ���Ă��Ȃ����ƁB
        assertFalse(result);
    }

    /**
     * testIsChanged03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getRequestURI:"/sample1/logon/start.do"<br>
     * (���) request.getAttribute("PREV_PATH_INFO"):"/logout/end.do" <br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true���ԋp����邱�ƁB<br>
     * <br>
     * �R���e�L�X�g���[�g�ȉ��̊K�w�ƃ��N�G�X�g�ɕۑ����ꂽ�O��̃p�X�� ���K�w����v���Ȃ��ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsChanged03() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ���݂̃R���e�L�X�g�p�X
        req.setContextPath("/sample1");

        // ���݂̃��N�G�X�g�p�X
        req.setRequestURI("/sample1/logon/start.do");

        // �O��̃��N�G�X�g�p�X
        req.setAttribute("PREV_PATH_INFO", "/logout/end.do");

        // �e�X�g���s
        boolean result = RequestUtil.isChanged(req);

        // �e�X�g���ʊm�F
        // �Ɩ����؂�ւ�����Ɣ��f����邱�ƁB
        assertTrue(result);
    }

    /**
     * testIsChanged04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getRequestURI:"/sample1/logon/start.do"<br>
     * (���) request.getAttribute("PREV_PATH_INFO"):null<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true���ԋp����邱�ƁB<br>
     * <br>
     * ���N�G�X�g����O��̃p�X���擾�ł��Ȃ��ꍇ�A true���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsChanged04() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ���݂̃R���e�L�X�g�p�X
        req.setContextPath("/sample1");

        // ���݂̃��N�G�X�g�p�X
        req.setRequestURI("/sample1/logon/start.do");

        // �O��̃��N�G�X�g�p�X
        req.setAttribute("PREV_PATH_INFO", null);

        // �e�X�g���s
        boolean result = RequestUtil.isChanged(req);

        // �e�X�g���ʊm�F
        // �Ɩ����؂�ւ�����Ɣ��f����邱�ƁB
        assertTrue(result);
    }

    /**
     * testIsChanged05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) request:not null<br>
     * (���) request.getRequestURI:"/sample1/service/logon/start.do" <br>
     * (���) request.getAttribute("PREV_PATH_INFO"): "/service/logout/end.do"<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:false���ԋp����邱�ƁB<br>
     * <br>
     * �R���e�L�X�g���[�g�ȉ��ɕ����̊K�w������A���N�G�X�g�ɕۑ����ꂽ �O��̃p�X�Ƒ��K�w����v����ꍇ�Afalse���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsChanged05() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // ���݂̃R���e�L�X�g�p�X
        req.setContextPath("/sample1");

        // ���݂̃��N�G�X�g�p�X
        req.setRequestURI("/sample1/service/logon/start.do");

        // �O��̃��N�G�X�g�p�X
        req.setAttribute("PREV_PATH_INFO", "/service/logout/end.do");

        // �e�X�g���s
        boolean result = RequestUtil.isChanged(req);

        // �e�X�g���ʊm�F
        // �Ɩ����؂�ւ�����Ɣ��f����邱�ƁB
        assertFalse(result);
    }

    /**
     * testIsChanged06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) request:null<br>
     * <br>
     * ���Ғl�F(�߂�l) boolean:true���ԋp����邱�ƁB<br>
     * <br>
     * ������request��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsChanged06() throws Exception {
        // �e�X�g���{
        boolean result = RequestUtil.isChanged(null);

        // ����
        assertTrue(result);
    }

    /**
     * testGetSessionHash01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) req.getSession():not null<br>
     * (���) session.getId():"dummyId"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"95E301072238E16FD3FFB3D256D3EA930544C6D7"<br>
     * <br>
     * �Z�b�V����ID�̃n�b�V���l���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetSessionHash01() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // �[���Z�b�V����
        MockHttpSession session = new MockHttpSession();
        req.setSession(session);

        // �e�X�g���s
        String hash = RequestUtil.getSessionHash(req);
        // �e�X�g���ʊm�F
        assertEquals("95E301072238E16FD3FFB3D256D3EA930544C6D7", hash);
    }

    /**
     * testGetSessionHash02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) req:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * <br>
     * ������request��null�̏ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetSessionHash02() throws Exception {
        // �e�X�g���{
        String result = RequestUtil.getSessionHash(null);

        // ����
        assertNull(result);
    }

    /**
     * testDumpRequestAttributes01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) req:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * <br>
     * �����̃��N�G�X�g��null�̏ꍇ�A<br>
     * null<br>
     * ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestAttributes01() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = null;
        // �����ɉ����o�^���Ȃ�

        // �e�X�g���s
        String dump = RequestUtil.dumpRequestAttributes(req);
        // �e�X�g���ʊm�F
        assertNull(dump);
    }

    /**
     * testDumpRequestAttributes02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) request.getAttributeNames():�v�f�Ȃ�<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestAttributes {}"<br>
     * <br>
     * �����̃��N�G�X�g����擾���������ɗv�f���Ȃ��ꍇ�A<br>
     * " RequestAttributes {}"<br>
     * ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestAttributes02() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // �����ɉ����o�^���Ȃ�

        // �e�X�g���s
        String dump = RequestUtil.dumpRequestAttributes(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestAttributes {}", dump);
    }

    /**
     * testDumpRequestAttributes03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) request.getAttributeNames():attr0="value0"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestAttributes {attr0 = value0}"<br>
     * <br>
     * �����̃��N�G�X�g����擾���������ɒl��String�^�̗v�f�������ꍇ�A<br>
     * " RequestAttributes {<�v�f��> = <�l>}"<br>
     * ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestAttributes03() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // �����ɓo�^����B
        req.setAttribute("attr0", "value0");
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestAttributes(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestAttributes {attr0 = value0}", dump);
    }

    /**
     * testDumpRequestAttributes04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) request.getAttributeNames():attr0=""<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestAttributes {attr0 = }"<br>
     * <br>
     * �����̃��N�G�X�g����擾���������ɗv�f�������A�l���󕶎��̏ꍇ�A<br>
     * " RequestAttributes {<�v�f��> = }"<br>
     * ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestAttributes04() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // �����ɓo�^����B
        req.setAttribute("attr0", "");
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestAttributes(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestAttributes {attr0 = }", dump);
    }

    /**
     * testDumpRequestAttributes05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FE <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) request.getAttributeNames(): attr0=HashMap(={inner0="inner0"})<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestAttributes {attr0 = {inner0=inner0}}"<br>
     * <br>
     * �����̃��N�G�X�g����擾���������ɒl��String�^�ȊO�̗v�f�������ꍇ�A " RequestAttributes {<�v�f��> = <�l��toString�̌���>}"<br>
     * ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestAttributes05() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("inner0", "inner0");
        // �����ɓo�^����B
        req.setAttribute("attr0", map);
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestAttributes(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestAttributes {attr0 = {inner0=inner0}}", dump);
    }

    /**
     * testDumpRequestAttributes06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FB <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) request.getAttributeNames():attr0="value0"<br>
     * attr1="value1"<br>
     * attr2="value2"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestAttributes {attr0 = value0 , attr1 = value1 , attr2 = value2}"<br>
     * <br>
     * �����̃��N�G�X�g����擾���������ɕ����̗v�f������̏ꍇ�A<br>
     * " RequestAttributes {<�v�f��> = <�l>*�v�f����}"<br>
     * ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestAttributes06() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // �����ɓo�^����B
        req.setAttribute("attr0", "value0");
        req.setAttribute("attr1", "value1");
        req.setAttribute("attr2", "value2");
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestAttributes(req);
        // �e�X�g���ʊm�F
        // String expect = " RequestAttributes {attr1 = value1 , "
        // + "attr2 = value2 , attr0 = value0}";
        assertNotNull(dump);
        assertTrue(dump.startsWith(" RequestAttributes {"));
        assertTrue(dump.endsWith("}"));
        assertTrue(dump.indexOf("attr1 = value1") != -1);
        assertTrue(dump.indexOf("attr2 = value2") != -1);
        assertTrue(dump.indexOf("attr0 = value0") != -1);
        // assertEquals(expect, dump);
    }

    /**
     * testDumpRequestParameters01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) req:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * <br>
     * �����̃��N�G�X�g��null�̏ꍇ�A<br>
     * null<br>
     * ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestParameters01() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = null;
        // �p�����[�^�ɓo�^���Ȃ�
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestParameters(req);
        // �e�X�g���ʊm�F
        assertNull(dump);
    }

    /**
     * testDumpRequestParameters02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) req.getParameterNames():{} (�v�f�Ȃ�:0��)<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestParameters {}"<br>
     * <br>
     * �����̃��N�G�X�g����擾�����p�����[�^�����ɗv�f���Ȃ��ꍇ�A " RequestAttributes {}"���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestParameters02() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���s
        String dump = RequestUtil.dumpRequestParameters(req);

        // �e�X�g���ʊm�F
        assertEquals(" RequestParameters {}", dump);
    }

    /**
     * testDumpRequestParameters03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) req.getParameterNames():{"param"}�i1���j<br>
     * (���) parameter�̓��e:param[] = {null}(�v�fnull:1��)<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestParameters {}"<br>
     * <br>
     * �����̃��N�G�X�g����擾�����p�����[�^�����ɗv�f��1�����݂��A �z��̗v�f��1����null�ł���ꍇ�A " RequestAttributes {}"���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestParameters03() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        String value = null;
        // �p�����[�^�ɓo�^����
        req.setParameter("param", value);
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestParameters(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestParameters {}", dump);
    }

    /**
     * testDumpRequestParameters04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) req.getParameterNames():{"param"}�i1���j<br>
     * (���) parameter�̓��e:param[] = {""}(�v�f��:1��)<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestParameters {param[0] = }"<br>
     * <br>
     * �����̃��N�G�X�g����擾�����p�����[�^�����ɗv�f��1�����݂��A �z��̗v�f��1���̋󕶎��ł���ꍇ�A "RequestAttributes{<�p�����[�^��>[0]=}"���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestParameters04() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = { "" };
        // �p�����[�^�ɓo�^����
        req.setParameter("param", values);
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestParameters(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestParameters {param[0] = }", dump);
    }

    /**
     * testDumpRequestParameters05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) req.getParameterNames():{"param"}�i1���j<br>
     * (���) parameter�̓��e:param[] = {"param0"}(�v�f:1��)<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"RequestParameters{param[0]=param0}"<br>
     * <br>
     * �����̃��N�G�X�g����擾�����p�����[�^�����ɗv�f��1�����݂��A �z��̗v�f��1���ł���ꍇ�A "RequestAttributes{<�p�����[�^��>[0]=<�l>}"���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestParameters05() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = { "param0" };
        // �p�����[�^�ɓo�^����
        req.setParameter("param", values);
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestParameters(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestParameters {param[0] = param0}", dump);
    }

    /**
     * testDumpRequestParameters06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) req.getParameterNames():{"param"}�i1���j<br>
     * (���) parameter�̓��e:param[]={"param0","param1","param2"} (�v�f:����)<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"RequestParameters{param[0]=param0, param[1] = param1, param[2]=param2}"<br>
     * <br>
     * �����̃��N�G�X�g����擾�����p�����[�^�����ɗv�f��1�����݂��A �z��̗v�f���������ł���ꍇ�A "RequestAttributes{<�p�����[�^��>[n]=<�l>*�v�f����}"���ԋp����邱�Ƃ� �m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestParameters06() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = { "param0", "param1", "param2" };
        // �p�����[�^�ɓo�^����
        req.setParameter("param", values);
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestParameters(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestParameters {param[0] = param0 , param[1] = "
                + "param1 , param[2] = param2}", dump);
    }

    /**
     * testDumpRequestParameters07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FD <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) req.getParameterNames():{"param0","param1"}(n��)<br>
     * (���) parameter�̓��e:param0[]={"param0","param1","param2"}<br>
     * param1[]={"value0","param1","param2"}<br>
     * (�v�f:n��)<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"RequestParameters{<br>
     * param0[0]=param0 , param0[1]=param1 , param0[2]=param2, param1[0]=value0 , param1[1]=value1 , param1[2]=value2 }"<br>
     * <br>
     * �����̃��N�G�X�g����擾�����p�����[�^�����ɗv�f�����������݂��A �z��̗v�f���������ł���ꍇ�A "RequestAttributes{<�p�����[�^��>[n]=<�l>*�v�f����*�p�����[�^�v�f����}" ���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequestParameters07() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = { "param0", "param1", "param2" };
        String[] values2 = { "value0", "value1", "value2" };
        // �p�����[�^�ɓo�^����
        req.setParameter("param0", values);
        req.setParameter("param1", values2);
        // �e�X�g���s
        String dump = RequestUtil.dumpRequestParameters(req);
        // �e�X�g���ʊm�F
        assertEquals(
                " RequestParameters {"
                        + "param0[0] = param0 , param0[1] = param1 , param0[2] = param2 , "
                        + "param1[0] = value0 , param1[1] = value1 , param1[2] = value2}",
                dump);
    }

    /**
     * testDumpRequest01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) req:not null<br>
     * (���) parameter����:param = {"param0"}<br>
     * (���) attribute����:attr0="value0"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:" RequestParameters = {param[0]=param0}" , " RequestAttributes{attr0=value0} "<br>
     * (��s)<br>
     * <br>
     * dumpRequestParameters��dumpRequestAttributes�̌��ʂ��J���}��؂�� �擾�ł��邱�ƁB<br>
     * ������n1���̂݊m�F <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDumpRequest01() throws Exception {
        // �e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        String[] values = { "param0" };
        // �p�����[�^�ɓo�^����
        req.setParameter("param", values);
        req.setAttribute("attr0", "value0");
        // �e�X�g���s
        String dump = RequestUtil.dumpRequest(req);
        // �e�X�g���ʊm�F
        assertEquals(" RequestParameters {param[0] = param0} ,  "
                + "RequestAttributes {attr0 = value0}", dump);
    }

    /**
     * testDeleteUrlParam01() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:null<br>
     * (����) key:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * <br>
     * url��null�̏ꍇ��null�����̂܂ܕԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam01() throws Exception {
        // �O����
        String url = null;
        String key = null;

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertNull(value);
    }

    /**
     * testDeleteUrlParam02() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:""<br>
     * (����) key:""<br>
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     * <br>
     * url���󔒂̏ꍇ�͋󔒂����̂܂ܕԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam02() throws Exception {
        // �O����
        String url = "";
        String key = "";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("", value);
    }

    /**
     * testDeleteUrlParam03() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do"<br>
     * (����) key:null<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do"<br>
     * <br>
     * key��null�̏ꍇ�́Aurl�����̂܂ܕԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam03() throws Exception {
        // �O����
        String url = "/test.do";
        String key = null;

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam04() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do"<br>
     * (����) key:""<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do"<br>
     * <br>
     * key���󔒂̏ꍇ�́Aurl�����̂܂ܕԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam04() throws Exception {
        // �O����
        String url = "/test.do";
        String key = "";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam05() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do"<br>
     * (����) key:"��"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do"<br>
     * <br>
     * url�ɁA�p�����[�^�����݂��Ȃ��ꍇ�́Aurl�����̂܂ܕԋp����邱�Ƃ� �m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam05() throws Exception {
        // �O����
        String url = "/test.do";
        String key = "r";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam06() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do?no2=2&no1=1"<br>
     * (����) key:"��"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do?no2=2&no1=1"<br>
     * <br>
     * url�ɁA�폜�Ώۂ̃p�����[�^�����݂��Ȃ��ꍇ�́A�폜���ꂸ�ɁAurl�� �ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam06() throws Exception {
        // �O����
        String url = "/test.do?no2=2&no1=1";
        String key = "r";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do?no2=2&no1=1", value);
    }

    /**
     * testDeleteUrlParam07() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do?r=8331352040"<br>
     * (����) key:"��"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do"<br>
     * <br>
     * url�ɁA�폜�Ώۂ̃p�����[�^�݂̂̏ꍇ�A�p�����[�^���폜���ꂽurl�� �ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam07() throws Exception {
        // �O����
        String url = "/test.do?r=8331352040";
        String key = "r";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do", value);
    }

    /**
     * testDeleteUrlParam08() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do?r=8331352040&no1=1"<br>
     * (����) key:"��"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do?no1=1"<br>
     * <br>
     * url�ɁA�폜�Ώۂ̃p�����[�^�ƁA���̌�ɕʂ̃p�����[�^�����݂���ꍇ�A �폜�Ώۂ̃p�����[�^�����폜���ꂽurl���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam08() throws Exception {
        // �O����
        String url = "/test.do?r=8331352040&no1=1";
        String key = "r";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do?no1=1", value);
    }

    /**
     * testDeleteUrlParam09() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do?no2=2&r=8331352040"<br>
     * (����) key:"��"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do?no2=2"<br>
     * <br>
     * url�ɁA�폜�Ώۂ̃p�����[�^�ƁA���̑O�ɕʂ̃p�����[�^�����݂���ꍇ�A �폜�Ώۂ̃p�����[�^�����폜���ꂽurl���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam09() throws Exception {
        // �O����
        String url = "/test.do?no2=2&r=8331352040";
        String key = "r";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do?no2=2", value);
    }

    /**
     * testDeleteUrlParam10() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do?no2=2&r=8331352040&no1=1"<br>
     * (����) key:"��"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do?no2=2&no1=1"<br>
     * <br>
     * url�ɁA�폜�Ώۂ̃p�����[�^�ƁA���̑O��ɕʂ̃p�����[�^�����݂���ꍇ�A �폜�Ώۂ̃p�����[�^�����폜���ꂽurl���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam10() throws Exception {
        // �O����
        String url = "/test.do?no2=2&r=8331352040&no1=1";
        String key = "r";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do?no2=2&no1=1", value);
    }

    /**
     * testDeleteUrlParam11() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do?r=9856456131&r=8331352040&no1=1"<br>
     * (����) key:"��"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do?no1=1"<br>
     * <br>
     * url�ɁA�폜�Ώۂ̃p�����[�^���������݂���ꍇ�A�ΏۂƂȂ�p�����[�^�� ���ׂč폜���ꂽurl���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam11() throws Exception {
        // �O����
        String url = "/test.do?r=9856456131&r=8331352040&no1=1";
        String key = "r";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals("/test.do?no1=1", value);
    }

    /**
     * testDeleteUrlParam12() <br>
     * <br>
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) url:"/test.do?r=8331352040"<br>
     * (����) key:"s"<br>
     * <br>
     * ���Ғl�F(�߂�l) String:"/test.do?r=8331352040"<br>
     * <br>
     * �폜�Ώۂ̃p�����[�^�ƈ���key���Ⴄ�ꍇ�A�p�����[�^���폜���ꂸ�� url���ԋp����邱�Ƃ��m�F����B <br>
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDeleteUrlParam12() throws Exception {
        // �O����
        String url = "/test.do?r=8331352040";
        String key = "s";

        // �e�X�g���{
        String value = RequestUtil.deleteUrlParam(url, key);

        // ����
        assertEquals(url, value);
    }

}
