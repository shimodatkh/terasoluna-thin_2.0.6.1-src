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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import jp.terasoluna.fw.exception.SystemException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadInputStream}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �t�@�C�����_�E�����[�h�f�[�^�Ƃ��邽�߂̃N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadInputStream
 */
public class DownloadInputStreamTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadInputStreamTest.class);
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
    public DownloadInputStreamTest(String name) {
        super(name);
    }

    /**
     * testDownloadInputStream01() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) name:null<br>
     * (����) stream:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     * ���b�Z�[�W�L�[�F<br>
     * "errors.no.download.content"<br>
     * ���b�v������O�F<br>
     * IllegalArgumentException<br>
     * 
     * <br>
     * stream��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadInputStream01() throws Exception {
        // �O����

        // �e�X�g���{
        try {
            @SuppressWarnings("unused")
            DownloadInputStream downloadInputStream = new DownloadInputStream(
                    null, null);
            fail("SystemException���������܂���ł����B");
        } catch (SystemException e) {
            // ����
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, e.getCause()
                    .getClass());
        }
    }

    /**
     * testDownloadInputStream02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) name:null<br>
     * (����) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) name:null<br>
     * (��ԕω�) stream:ByteArrayInputStream<br>
     * (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     * (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     * 
     * <br>
     * contentType, charset�Ƀf�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadInputStream02() throws Exception {
        // �O����
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // �e�X�g���{
        DownloadInputStream downloadInputStream = new DownloadInputStream(null,
                byteArrayInputStream);

        // ����
        assertNull(downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);

    }

    /**
     * testDownloadInputStream03() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) name:" "�i�󔒁j<br>
     * (����) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) name:" "�i�󔒁j<br>
     * (��ԕω�) stream:ByteArrayInputStream<br>
     * (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     * (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     * 
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadInputStream03() throws Exception {
        // �O����
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // �e�X�g���{
        DownloadInputStream downloadInputStream = new DownloadInputStream("  ",
                byteArrayInputStream);

        // ����
        assertEquals("  ", downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);
    }

    /**
     * testDownloadInputStream04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) name:""�i�󕶎��j<br>
     * (����) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) name:""�i�󕶎��j<br>
     * (��ԕω�) stream:ByteArrayInputStream<br>
     * (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     * (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     * 
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadInputStream04() throws Exception {
        // �O����
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // �e�X�g���{
        DownloadInputStream downloadInputStream = new DownloadInputStream("",
                byteArrayInputStream);

        // ����
        assertEquals("", downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);
    }

    /**
     * testDownloadInputStream05() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) name:"abc"<br>
     * (����) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) name:"abc"<br>
     * (��ԕω�) stream:ByteArrayInputStream<br>
     * (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     * (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     * 
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadInputStream05() throws Exception {
        // �O����
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // �e�X�g���{
        DownloadInputStream downloadInputStream = new DownloadInputStream(
                "abc", byteArrayInputStream);

        // ����
        assertEquals("abc", downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);
    }

    /**
     * testGetLengthOfData01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) int:-1<br>
     * 
     * <br>
     * file�̃T�C�Y���ԋp����邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLengthOfData01() throws Exception {
        // �O����
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        DownloadInputStream downloadInputStream = new DownloadInputStream(null,
                byteArrayInputStream);

        // �e�X�g���{
        int size = downloadInputStream.getLengthOfData();

        // ����
        assertEquals(-1, size);
    }

    /**
     * testGetStreamInternal01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) file:ByteArrayInputStream<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) InputStream:ByteArrayInputStream<br>
     * 
     * <br>
     * stream���ԋp����邱�Ƃ��m�F����e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetStreamInternal01() throws Exception {
        // �O����
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        DownloadInputStream downloadInputStream = new DownloadInputStream(null,
                byteArrayInputStream);

        // �e�X�g���{
        InputStream inputStream = downloadInputStream.getStreamInternal();

        // ����
        assertNotNull(inputStream);
        assertEquals(ByteArrayInputStream.class, inputStream.getClass());
    }

}
