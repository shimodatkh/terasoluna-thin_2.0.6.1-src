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

package jp.terasoluna.fw.web.struts.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.FileDownloadUtil} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �t�@�C���_�E�����[�h���s�����[�e�B���e�B�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.FileDownloadUtil
 */
public class FileDownloadUtilTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FileDownloadUtilTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LogUTUtil.flush();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public FileDownloadUtilTest(String name) {
        super(name);
    }

    /**
     * testSetEncoder01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) encoder:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     * ���b�Z�[�W�F<br>
     * "encoder must not be null."<br>
     * 
     * <br>
     * encoder��null�ł���ꍇ�A��O���X���[����邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetEncoder01() throws Exception {
        // �O����
        FileDownloadUtil util = new FileDownloadUtil();

        // �e�X�g���{
        try {
            util.setEncoder(null);
            fail("IllegalArgumentException���������܂���ł����B");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("encoder must not be null.", e.getMessage());
        }

    }

    /**
     * testSetEncoder02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) encoder:DownloadFileNameEncoder<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) encoder:DownloadFileNameEncoder<br>
     * 
     * <br>
     * �G���R�[�_���������ݒ肳��邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetEncoder02() throws Exception {
        // �O����
        FileDownloadUtil util = new FileDownloadUtil();
        DownloadFileNameEncoder encoder = new DownloadFileNameEncoderImpl();

        // �e�X�g���{
        util.setEncoder(encoder);

        // ����
        assertEquals(encoder, FileDownloadUtil.encoder);
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) result:AbstractDownloadObject�p���N���X<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) request:���X�|���X���������܂��<br>
     * 
     * <br>
     * result��AbstarctDownloadObject�p���N���X�̏ꍇ�A���̂܂܃_�E�����[�h�ΏۂƂ���邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse01()
            throws Exception {
        // �O����
        AbstractDownloadObject result = new DownloadString("abc", "abc");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(result, request, response);

        // ����
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) result:AbstractDownloadObject�p���N���X���v���p�e�B�Ɏ����Ȃ�JavaBean<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) request:���X�|���X���������܂�Ȃ�<br>
     * 
     * <br>
     * result��AbstarctDownloadObject���v���p�e�B�Ɏ����Ȃ�JavaBean�̏ꍇ�A�_�E�����[�h���s���Ȃ����Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse02()
            throws Exception {
        // �O����
        FileDownloadUtil_Stub01 result = new FileDownloadUtil_Stub01();
        result.setObject1("abc");
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(result, request, response);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse03() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) result:AbstractDownloadObject�p���N���X��1�v���p�e�B�Ɏ���JavaBean<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) request:���X�|���X���������܂��<br>
     * 
     * <br>
     * result��AbstractDownloadObject�p���N���X��1�v���p�e�B�Ɏ���JavaBean�ł���ꍇ�A���̃v���p�e�B���_�E�����[�h�ΏۂƂȂ邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse03()
            throws Exception {
        // �O����
        FileDownloadUtil_Stub02 result = new FileDownloadUtil_Stub02();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(result, request, response);

        // ����
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse04() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) result:AbstractDownloadObject�p���N���X��2�v���p�e�B�Ɏ���JavaBean<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     * ���b�Z�[�W�L�[�F<br>
     * "errors.too.many.download"<br>
     * ���b�v������O�F<br>
     * IllegalStateException<br>
     * 
     * <br>
     * result��AbstractDownloadObject�p���N���X��2�ȏ�v���p�e�B�Ɏ���JavaBean�ł���ꍇ�A��O���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse04()
            throws Exception {
        // �O����
        FileDownloadUtil_Stub03 result = new FileDownloadUtil_Stub03();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2(new DownloadString("def", "def"));
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        try {
            FileDownloadUtil.download(result, request, response);
            fail("SystemException���������܂���ł����B");
        } catch (SystemException e) {
            // ����
            assertEquals("errors.too.many.download", e.getErrorCode());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse05() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) result:AbstractDownloadObject�p���N���X���v���p�e�B�Ɏ���getter�������Ȃ�JavaBean<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) request:���X�|���X���������܂�Ȃ�<br>
     * 
     * <br>
     * result��AbstractDownloadObject�p���N���X��1�v���p�e�B�Ɏ�������getter���Ȃ�JavaBean�ł���ꍇ�A�_�E�����[�h���s���Ȃ����Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse05()
            throws Exception {
        // �O����
        FileDownloadUtil_Stub04 result = new FileDownloadUtil_Stub04();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(result, request, response);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse06() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) result:AbstractDownloadObject�p���N���X��1�v���p�e�B�Ɏ���JavaBean<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:HttpServletResponse������������O�����p�X�^�u�N���X<br>
     * (���) download(HttpServletRequest�E�E�E)�̌���:SocketExeption<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) request:���X�|���X���������܂�Ȃ�<br>
     * 
     * <br>
     * SocketException���X���[���ꂽ�ꍇ�A�_�E�����[�h���s���Ȃ����Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse06()
            throws Exception {
        // �O����
        FileDownloadUtil_Stub02 result = new FileDownloadUtil_Stub02();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        FileDownloadUtil_HttpServletResponseStub01 response = new FileDownloadUtil_HttpServletResponseStub01();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(result, request, response);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse07() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) result:AbstractDownloadObject�p���N���X��1�v���p�e�B�Ɏ���JavaBean<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:HttpServletResponse������������O�����p�X�^�u�N���X<br>
     * (���) download(HttpHttpServletRequest�E�E�E)�̌���:IOE��ception<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) request:���X�|���X���������܂�Ȃ�<br>
     * (��ԕω�) ���O:���O���x���FERROR<br>
     * ���O���e�FIOException has occurred while downloading<br>
     * ��O�FIOException<br>
     * 
     * <br>
     * IOException���X���[���ꂽ�ꍇ�A���O�o�͂��s���A�_�E�����[�h���s���Ȃ����Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse07()
            throws Exception {
        // �O����
        FileDownloadUtil_Stub02 result = new FileDownloadUtil_Stub02();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        FileDownloadUtil_HttpServletResponseStub02 response = new FileDownloadUtil_HttpServletResponseStub02();

        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{

        FileDownloadUtil.download(result, request, response);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil
                .checkError("IOException has occurred while downloading",
                        new IOException()));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean01()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:null<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�ɒl���������܂�Ȃ�<br>
     * (��ԕω�) ���O:���O���x���FWARN<br>
     * ���O���e�F"No download object."<br>
     * ��O�F�Ȃ�<br>
     * 
     * <br>
     * downloadObject��null�̏ꍇ�A��������Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean01()
            throws Exception {
        // �O����
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(null, request, response, true);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil.checkWarn("No download object."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean02()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����)
     * downloadObject:name="xyz"�ŁAadditionalHeaders��null��AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�ɒl���������܂�Ȃ�<br>
     * (��ԕω�) ���O:���O���x���FWARN<br>
     * ���O���e�F"Header must not be null."<br>
     * ��O�F�Ȃ�<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeaders��null�̏ꍇ�A��������Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean02()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.additionalHeaders = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil.checkWarn("Header must not be null."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean03()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����)
     * downloadObject:name="xyz"�ŁAMap�̗v�f����0��additionalHeaders������AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃w�b�_��<br>
     * �w�b�_��: "Content-Disposition"<br>
     * �l: "attachment; filename=xyz"<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeaders���G���g���������Ȃ�Map�̏ꍇ�A����ɏ�������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean03()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.additionalHeaders = new HashMap<String, List<String>>();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean04()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁAMap{[null,
     * null]}��ێ�����additionalHeaders������AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�ɒl���������܂�Ȃ�<br>
     * (��ԕω�) ���O:���O���x���FWARN<br>
     * ���O���e�F"Header name and value must not be null."<br>
     * ��O�F�Ȃ�<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeaders�̃G���g����List��null�ł���ꍇ�A��������Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean04()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.additionalHeaders.put(null, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil
                .checkWarn("Header name and value must not be null."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean05()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁAMap{[null,
     * List["abc"]]}��ێ�����additionalHeaders������AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�ɒl���������܂�Ȃ�<br>
     * (��ԕω�) ���O:���O���x���FWARN<br>
     * ���O���e�F"Header name and value must not be null."<br>
     * ��O�F�Ȃ�<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeaders�̃G���g���̃L�[��null�ł���ꍇ�A��������Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean05()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add("abc");
        object.additionalHeaders.put(null, list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil
                .checkWarn("Header name and value must not be null."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean06()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁAMap{["abc",
     * List[null]]}��ێ�����additionalHeaders������AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃w�b�_��<br>
     * �w�b�_��: "Content-Disposition", �l: "attachment; filename=xyz"<br>
     * �w�b�_��: "abc", �l: ""�i�󕶎���j<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeaders�̃G���g����List�̗v�f��null�ł���ꍇ�A�󕶎���ɕϊ�����Đ���ɏ�������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean06()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add(null);
        object.additionalHeaders.put("abc", list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
        assertTrue(response.getHeaderNames().contains("abc"));
        assertEquals("", (String) response.getHeader("abc"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean07()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁAMap{["abc",
     * List["abc"]]}��ێ�����additionalHeaders������AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃w�b�_��<br>
     * �w�b�_��: "Content-Disposition", �l: "attachment; filename=xyz"<br>
     * �w�b�_��: "abc", �l: "abc"<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeaders�̃G���g����List��null���܂܂Ȃ��v�f1�����ꍇ�A����ɏ�������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean07()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add("abc");
        object.additionalHeaders.put("abc", list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
        assertTrue(response.getHeaderNames().contains("abc"));
        assertEquals("abc", (String) response.getHeader("abc"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean08()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁAMap{["abc", List["abc",
     * "def"]]}��ێ�����additionalHeaders������AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃w�b�_��<br>
     * �w�b�_��: "Content-Disposition", �l: "attachment; filename=xyz"<br>
     * �w�b�_��: "abc", �l: List["abc", "def"]<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeaders�̃G���g����List��null���܂܂Ȃ��v�f2�����ꍇ�A����ɏ�������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean08()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("def");
        object.additionalHeaders.put("abc", list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
        assertTrue(response.getHeaderNames().contains("abc"));
        List list2 = response.getHeaders("abc");
        assertEquals("abc", list2.get(0));
        assertEquals("def", list2.get(1));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean09()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����)
     * downloadObject:name="xyz"�ŁA�G���R�[�f�B���O�ƃR���e���g�^�C�v��null���ݒ肳��Ă���AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�ɃG���R�[�f�B���O�ƃR���e���g�^�C�v���ݒ肳��Ȃ�<br>
     * 
     * <br>
     * AbstractDownloadObject�̃G���R�[�f�B���O�ƃR���e���g�^�C�v��null���ݒ肳��Ă���ꍇ�A���X�|���X�ɃG���R�[�f�B���O�ƃR���e���g�^�C�v���ݒ肳��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean09()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = null;
        object.contentType = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String charset = response.getCharacterEncoding();
        String contentType = response.getContentType();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertEquals(charset, response.getCharacterEncoding());
        assertEquals(contentType, response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean10()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����)
     * downloadObject:name="xyz"�ŁA�G���R�[�f�B���O�ƃR���e���g�^�C�v��""�i�󕶎���j���ݒ肳��Ă���AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�ɃG���R�[�f�B���O�ƃR���e���g�^�C�v���ݒ肳��Ȃ�<br>
     * 
     * <br>
     * AbstractDownloadObject�̃G���R�[�f�B���O�ƃR���e���g�^�C�v��""�i�󕶎���j���ݒ肳��Ă���ꍇ�A���X�|���X�ɃG���R�[�f�B���O�ƃR���e���g�^�C�v���ݒ肳��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean10()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = "";
        object.contentType = "";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        String charset = response.getCharacterEncoding();
        String contentType = response.getContentType();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertEquals(charset, response.getCharacterEncoding());
        assertEquals(contentType, response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean11()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁA�G���R�[�f�B���O�ƃR���e���g�^�C�v�� "
     * "�i�󔒁j���ݒ肳��Ă���AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃G���R�[�f�B���O�ƃR���e���g�^�C�v�ɂ��ꂼ��" "�i�󔒁j���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject�̃G���R�[�f�B���O�ƃR���e���g�^�C�v��"
     * "�i�󔒁j���ݒ肳��Ă���ꍇ�A���X�|���X�̃G���R�[�f�B���O�ƃR���e���g�^�C�v�ɂ��ꂼ��" "�i�󔒁j���ݒ肳��� <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean11()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = "  ";
        object.contentType = "  ";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertEquals("  ", response.getCharacterEncoding());
        assertEquals("  ", response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean12()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����)
     * downloadObject:name="xyz"�ŁA�G���R�[�f�B���O�ƃR���e���g�^�C�v�ɂ��ꂼ��"abc"���ݒ肳��Ă���AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃G���R�[�f�B���O�ƃR���e���g�^�C�v�ɂ��ꂼ��"abc"���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject�̃G���R�[�f�B���O�ƃR���e���g�^�C�v��"abc"���ݒ肳��Ă���ꍇ�A���X�|���X�̃G���R�[�f�B���O�ƃR���e���g�^�C�v�ɂ��ꂼ��"abc"���ݒ肳���
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean12()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = "abc";
        object.contentType = "abc";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertEquals("abc", response.getCharacterEncoding());
        assertEquals("abc", response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean13()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁA�f�[�^�T�C�Y��-1��AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�Ƀf�[�^�T�C�Y���ݒ肳��Ȃ�<br>
     * 
     * <br>
     * AbstractDownloadObject�̃f�[�^�T�C�Y��-1�̂Ƃ��A���X�|���X�Ƀf�[�^�T�C�Y���ݒ肳��Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean13()
            throws Exception {
        // �O����
        FileDownloadUtil_DownloadStringStub01 object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.lengthOfData = -1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        int length = response.getContentLength();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertEquals(length, response.getContentLength());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean14()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁA�f�[�^�T�C�Y��0��AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�Ƀf�[�^�T�C�Y���ݒ肳��Ȃ�<br>
     * 
     * <br>
     * AbstractDownloadObject�̃f�[�^�T�C�Y��0�̂Ƃ��A���X�|���X�Ƀf�[�^�T�C�Y���ݒ肳��Ȃ����Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean14()
            throws Exception {
        // �O����
        FileDownloadUtil_DownloadStringStub01 object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.lengthOfData = 0;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // ����p
        int length = response.getContentLength();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertEquals(length, response.getContentLength());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean15()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁA�f�[�^�T�C�Y��1��AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃f�[�^�T�C�Y��1�ɐݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject�̃f�[�^�T�C�Y��0�̂Ƃ��A���X�|���X�̃f�[�^�T�C�Y��1�ɐݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean15()
            throws Exception {
        // �O����
        FileDownloadUtil_DownloadStringStub01 object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.lengthOfData = 1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertEquals(1, response.getContentLength());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean16()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ł���AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃w�b�_��<br>
     * �w�b�_��: "Content-Disposition"<br>
     * �l: "attachment; filename=xyz"<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject�̃t�@�C���̕ʖ���ݒ肷��Ƃ��AsetFileName�����������s����邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean16()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.name = "xyz";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean17()
     * <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) downloadObject:name="xyz"�ŁA�t�@�C���̕ʖ������݂��Ȃ�AbstractDownloadObject<br>
     * (����) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (����) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) response:���X�|���X�̃w�b�_��<br>
     * �w�b�_��: "Content-Disposition"<br>
     * �l: "attachment; filename="<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * AbstractDownloadObject�̃t�@�C���̕ʖ���ݒ肵�Ȃ��Ƃ��ł��AsetFileName�����������s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean17()
            throws Exception {
        // �O����
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.name = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        FileDownloadUtil.download(object, request, response, true);

        // ����
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testSetFileNameHttpServletResponseStringboolean01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) response:MockHttpServletResponse<br>
     * (����) name:"abc"<br>
     * (����) forceDownload:true<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) null:���X�|���X�̃w�b�_��<br>
     * �L�[: "Content-Disposition"<br>
     * �l: "attachment; filename=abc"<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * ���X�|���X�̃w�b�_�ɐ����������񂪏������܂�邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFileNameHttpServletResponseStringboolean01()
            throws Exception {
        String name = "abc";
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        FileDownloadUtil.setFileName(response, name, true);

        // ����
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=abc", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testSetFileNameHttpServletResponseStringboolean02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) response:MockHttpServletResponse<br>
     * (����) name:"abc"<br>
     * (����) forceDownload:false<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) null:���X�|���X�̃w�b�_��<br>
     * �L�[: "Content-Disposition"<br>
     * �l: "inline; filename=abc"<br>
     * ���ݒ肳���<br>
     * 
     * <br>
     * ���X�|���X�̃w�b�_�ɐ����������񂪏������܂�邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFileNameHttpServletResponseStringboolean02()
            throws Exception {
        String name = "abc";
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        FileDownloadUtil.setFileName(response, name, false);

        // ����
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("inline; filename=abc", (String) response
                .getHeader("Content-Disposition"));
    }

}
