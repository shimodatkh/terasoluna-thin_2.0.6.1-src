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

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadFileNameEncoderImpl} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �_�E�����[�h���̎w��t�@�C�������G���R�[�h����N���X�B<br>
 * ���̃N���X�ł�Internet Explorer�݂̂ɑΉ����Ă���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadFileNameEncoderImpl
 */
public class DownloadFileNameEncoderImplTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadFileNameEncoderImplTest.class);
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
    public DownloadFileNameEncoderImplTest(String name) {
        super(name);
    }

    /**
     * testEncode01()
     * <br><br>
     * 
     *  (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) original:null<br>
     *         (����) request:MockHttpServletRequest<br>
     *         (����) response:MockHttpServletResponse<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * �ϐ���null�̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncode01() throws Exception {
        // �O����
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        String str = encoderImpl.encode(null, request, response);

        // ����
        assertNull(str);
    }

    /**
     * testEncode02()
     * <br><br>
     * 
     *  (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) original:"  "�i�󔒁j<br>
     *         (����) request:MockHttpServletRequest<br>
     *         (����) response:MockHttpServletResponse<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncode02() throws Exception {
        // �O����
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        String str = encoderImpl.encode("  ", request, response);

        // ����
        assertEquals(URLEncoder.encode("  ",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

    /**
     * testEncode03()
     * <br><br>
     * 
     *  (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) original:""�i�󕶎���j<br>
     *         (����) request:MockHttpServletRequest<br>
     *         (����) response:MockHttpServletResponse<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""�i�󕶎���j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncode03() throws Exception {
        // �O����
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        String str = encoderImpl.encode("", request, response);

        // ����
        assertEquals(URLEncoder.encode("",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

    /**
     * testEncode04()
     * <br><br>
     * 
     *  (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) original:"abc"<br>
     *         (����) request:MockHttpServletRequest<br>
     *         (����) response:MockHttpServletResponse<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * �ϐ����p��������̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncode04() throws Exception {
        // �O����
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        String str = encoderImpl.encode("abc", request, response);

        // ����
        assertEquals(URLEncoder.encode("abc",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

    /**
     * testEncode05()
     * <br><br>
     * 
     *  (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) original:"����������"(UTF-8)<br>
     *         (����) request:MockHttpServletRequest<br>
     *         (����) response:MockHttpServletResponse<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"����������"<br>
     *         
     * <br>
     * �ϐ������{�ꕶ����̏ꍇ�A�G���R�[�h����ĕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEncode05() throws Exception {
        // �O����
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        String str = encoderImpl.encode("����������", request, response);

        // ����
        assertEquals(URLEncoder.encode("����������",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

}
