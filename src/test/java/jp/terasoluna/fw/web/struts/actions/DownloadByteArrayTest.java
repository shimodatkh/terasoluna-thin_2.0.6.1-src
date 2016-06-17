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
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadByteArray} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �o�C�g�z����_�E�����[�h�f�[�^�Ƃ��邽�߂̃N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadByteArray
 */
public class DownloadByteArrayTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadByteArrayTest.class);
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
    public DownloadByteArrayTest(String name) {
        super(name);
    }

    /**
     * testDownloadByteArray01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) byteArray:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.no.download.content"<br>
     *                    ���b�v������O�F<br>
     *                    IllegalArgumentException<br>
     *         
     * <br>
     * byteArray��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadByteArray01() throws Exception {
        // �O����

        // �e�X�g���{
        try {
            @SuppressWarnings("unused")
            DownloadByteArray downloadByteArray = new DownloadByteArray(null,
                    null);
            fail("SystemException���������܂���ł���");
        } catch (SystemException e) {
            // ����
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testDownloadByteArray02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) byteArray:byte[]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:null<br>
     *         (��ԕω�) byteArray:byte[]<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_FILE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * contentType, charset�Ƀf�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadByteArray02() throws Exception {
        // �O����
        byte[] byteBs = new byte[0];

        // �e�X�g���{
        DownloadByteArray downloadByteArray = new DownloadByteArray(null,
                byteBs);

        // ����
        assertNull(downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testDownloadByteArray03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"  "�i�󔒁j<br>
     *         (����) ��yteArray:byte[]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"  "�i�󔒁j<br>
     *         (��ԕω�) byteArray:byte[]<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_FILE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadByteArray03() throws Exception {
        // �O����
        byte[] byteBs = new byte[0];

        // �e�X�g���{
        DownloadByteArray downloadByteArray = new DownloadByteArray("  ",
                byteBs);

        // ����
        assertEquals("  ", downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testDownloadByteArray04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""�i�󕶎��j<br>
     *         (����) byteArray:byte[]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:""�i�󕶎��j<br>
     *         (��ԕω�) byteArray:byte[]<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_FILE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadByteArray04() throws Exception {
        // �O����
        byte[] byteBs = new byte[0];

        // �e�X�g���{
        DownloadByteArray downloadByteArray = new DownloadByteArray("", byteBs);

        // ����
        assertEquals("", downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testDownloadByteArray05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (����) byteArray:byte[1, 2]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"abc"<br>
     *         (��ԕω�) byteArray:byte[1, 2]<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_FILE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadByteArray05() throws Exception {
        // �O����
        byte[] byteBs = new byte[] { 1, 2 };

        // �e�X�g���{
        DownloadByteArray downloadByteArray = new DownloadByteArray("abc",
                byteBs);

        // ����
        assertEquals("abc", downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testGetLengthOfData01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) byteArray:byte[1, 2]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:2<br>
     *         
     * <br>
     * byteArray�̃T�C�Y���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLengthOfData01() throws Exception {
        // �O����
        DownloadByteArray downloadByteArray = new DownloadByteArray("",
                new byte[0]);
        downloadByteArray.byteArray = new byte[] { 1, 2 };

        // �e�X�g���{
        int num = downloadByteArray.getLengthOfData();

        // ����
        assertEquals(2, num);
    }

    /**
     * testGetStreamInternal01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) byteArray:byte[1, 2]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) InputStream:ByteArrayInputStream(byte[1, 2])<br>
     *         
     * <br>
     * byteArray�����b�v����ByteArrayInputStream���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetStreamInternal01() throws Exception {
        // �O����
        DownloadByteArray downloadByteArray = new DownloadByteArray("",
                new byte[0]);
        downloadByteArray.byteArray = new byte[] { 1, 2 };

        // �e�X�g���{
        InputStream stream = downloadByteArray.getStreamInternal();

        // ����
        // ByteArrayInputStream���ԋp����Ă��邩�m�F
        assertNotNull(stream);
        assertEquals(ByteArrayInputStream.class.getName(), stream.getClass()
                .getName());

        // ByteArrayInputStream��byteArray�����b�v���Ă��邩�m�F
        assertEquals(1, stream.read());
        assertEquals(2, stream.read());
        assertEquals(-1, stream.read());
    }

}
