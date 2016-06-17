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
import java.io.UnsupportedEncodingException;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadString} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * String���_�E�����[�h�f�[�^�Ƃ��邽�߂̃N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadString
 */
public class DownloadStringTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadStringTest.class);
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
        LogUTUtil.flush();
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
    public DownloadStringTest(String name) {
        super(name);
    }

    /**
     * testDownloadString01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) value:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.no.download.content"<br>
     *                    ���b�v������O�F<br>
     *                    IllegalArgumentException<br>
     *         
     * <br>
     * value��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadString01() throws Exception {
        // �O����

        // �e�X�g���{
        try {
            @SuppressWarnings("unused")
            DownloadString downloadString = new DownloadString(null, null);
            fail("SystemException���������܂���ł����B");
        } catch (SystemException e) {
            // ����
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, e.getCause()
                    .getClass());
        }
    }

    /**
     * testDownloadString02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) value:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:null<br>
     *         (��ԕω�) value:"abc"<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * contentType, charset�Ƀf�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadString02() throws Exception {
        // �O����

        // �e�X�g���{
        DownloadString downloadString = new DownloadString(null, "abc");

        // ����
        assertNull(downloadString.name);
        assertEquals("abc", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testDownloadString03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"  "�i�󔒁j<br>
     *         (����) value:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"  "�i�󔒁j<br>
     *         (��ԕω�) value:"  "�i�󔒁j<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadString03() throws Exception {
        // �O����

        // �e�X�g���{
        DownloadString downloadString = new DownloadString("  ", "  ");

        // ����
        assertEquals("  ", downloadString.name);
        assertEquals("  ", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testDownloadString04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""�i�󕶎��j<br>
     *         (����) value:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:""�i�󕶎��j<br>
     *         (��ԕω�) value:""�i�󕶎��j<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadString04() throws Exception {
        // �O����

        // �e�X�g���{
        DownloadString downloadString = new DownloadString("", "");

        // ����
        assertEquals("", downloadString.name);
        assertEquals("", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testDownloadString05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (����) value:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"abc"<br>
     *         (��ԕω�) value:"abc"<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadString05() throws Exception {
        // �O����

        // �e�X�g���{
        DownloadString downloadString = new DownloadString("abc", "abc");

        // ����
        assertEquals("abc", downloadString.name);
        assertEquals("abc", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testGetLengthOfData01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) value:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:"abc"#getBytes#length�̒l<br>
     *         
     * <br>
     * value�̃T�C�Y���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLengthOfData01() throws Exception {
        // �O����
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";

        // �e�X�g���{
        int size = downloadString.getLengthOfData();

        // ����
        assertEquals("abc".getBytes().length, size);
    }

    /**
     * testGetContent01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) value:"abc"<br>
     *         (���) charset:"NOT-EXIST"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) byte[]:"abc"#getBytes�̒l<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Charset is not valid."<br>
     *                    ��O:<br>
     *                    UnsupportedEncodingException<br>
     *         
     * <br>
     * charset�����݂��Ȃ��ꍇ�Ƀ��O���o�͂���邱�ƂƁA<br>
     * value��byte�z�񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetContent01() throws Exception {
        // �O����
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";
        downloadString.charset = "NOT-EXIST";

        // �e�X�g���{
        byte[] byteBs = downloadString.getContent();

        // ����
        assertEquals("abc".getBytes().length, byteBs.length);
        assertEquals("abc".getBytes()[0], byteBs[0]);
        assertEquals("abc".getBytes()[1], byteBs[1]);
        assertEquals("abc".getBytes()[2], byteBs[2]);

        assertTrue(LogUTUtil.checkWarn("Charset is not valid.",
                new UnsupportedEncodingException()));
    }

    /**
     * testGetContent02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) value:"abc"<br>
     *         (���) charset:"UTF-8"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) byte[]:"abc"#getBytes("UTF-8")�̒l<br>
     *         
     * <br>
     * value��byte�z�񂪕ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetContent02() throws Exception {
        // �O����
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";
        downloadString.charset = "UTF-8";

        // �e�X�g���{
        byte[] byteBs = downloadString.getContent();

        // ����
        assertEquals("abc".getBytes("UTF-8").length, byteBs.length);
        assertEquals("abc".getBytes("UTF-8")[0], byteBs[0]);
        assertEquals("abc".getBytes("UTF-8")[1], byteBs[1]);
        assertEquals("abc".getBytes("UTF-8")[2], byteBs[2]);
    }

    /**
     * testGetStreamInternal01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) value:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) InputStream:ByteArrayInputStream<br>
     *         
     * <br>
     * value�����b�v���ꂽByteArrayInputStream���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetStreamInternal01() throws Exception {
        // �O����
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";

        // �e�X�g���{
        InputStream stream = downloadString.getStreamInternal();

        // ����
        assertNotNull(stream);
        assertEquals(ByteArrayInputStream.class, stream.getClass());

        assertEquals("abc".getBytes()[0], stream.read());
        assertEquals("abc".getBytes()[1], stream.read());
        assertEquals("abc".getBytes()[2], stream.read());
        assertEquals(-1, stream.read());
    }

}
