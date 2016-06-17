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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import jp.terasoluna.fw.exception.SystemException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadFile} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �t�@�C�����_�E�����[�h�f�[�^�Ƃ��邽�߂̃N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadFile
 */
public class DownloadFileTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadFileTest.class);
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
    public DownloadFileTest(String name) {
        super(name);
    }

    /**
     * testDownloadFileFile01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) File:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F<br>
     *                    "errors.no.download.content"<br>
     *                    ���b�v������O�F<br>
     *                    IllegalArgumentException<br>
     *         
     * <br>
     * file��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadFileFile01() throws Exception {
        // �O����

        // �e�X�g���{
        try {
            @SuppressWarnings("unused")
            DownloadFile downloadFile = new DownloadFile(null);
            fail("SystemException���������܂���ł���");
        } catch (SystemException e) {
            // ����
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, 
                    e.getCause().getClass());
        }
    }

    /**
     * testDownloadFileFile02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) File:File()<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:File#getName�̒l<br>
     *         (��ԕω�) file:File<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * name, contentType, charset�Ƀf�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadFileFile02() throws Exception {
        // �O����
        File file = new File("");

        // �e�X�g���{
        DownloadFile downloadFile = new DownloadFile(file);
        
        // ����
        assertEquals(file.getName(), downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) file:null<br>
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
    public void testDownloadFileStringFile01() throws Exception {
        // �O����

        // �e�X�g���{
        try {
            @SuppressWarnings("unused")
            DownloadFile downloadFile = new DownloadFile(null, null);
            fail("SystemException���������܂���ł����B");
        } catch (SystemException e) {
            // ����
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, 
                    e.getCause().getClass());
        }

    }

    /**
     * testDownloadFileStringFile02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) file:File#createTempFile<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:File#getName�̒l<br>
     *         (��ԕω�) file:File<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * contentType, charset�Ƀf�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadFileStringFile02() throws Exception {
        // �O����
        File file = File.createTempFile("abc", "def");

        // �e�X�g���{
        DownloadFile downloadFile = new DownloadFile(null, file);

        // ����
        assertEquals(file.getName(), downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"  "�i�󔒁j<br>
     *         (����) file:File#createTempFile<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"  "�i�󔒁j<br>
     *         (��ԕω�) file:File<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadFileStringFile03() throws Exception {
        // �O����
        File file = File.createTempFile("abc", "def");

        // �e�X�g���{
        DownloadFile downloadFile = new DownloadFile("  ", file);

        // ����
        assertEquals("  ", downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""�i�󕶎��j<br>
     *         (����) file:File#createTempFile<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:File#getName�̒l<br>
     *         (��ԕω�) file:File<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadFileStringFile04() throws Exception {
        // �O����
        File file = File.createTempFile("abc", "def");

        // �e�X�g���{
        DownloadFile downloadFile = new DownloadFile("", file);

        // ����
        assertEquals(file.getName(), downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (����) file:File#createTempFile<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"abc"<br>
     *         (��ԕω�) file:File<br>
     *         (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDownloadFileStringFile05() throws Exception {
        // �O����
        File file = File.createTempFile("abc", "def");

        // �e�X�g���{
        DownloadFile downloadFile = new DownloadFile("abc", file);

        // ����
        assertEquals("abc", downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testGetLengthOfData01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) file:File<br>
     *                (�T�C�Y��10�ł��邱��)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:10<br>
     *         
     * <br>
     * file�̃T�C�Y���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLengthOfData01() throws Exception {
        // �O����
        // �T�C�Y��10�̃t�@�C���쐬
        File file = File.createTempFile("abc", "def");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(new byte[10]);
        outputStream.close();
        
        DownloadFile downloadFile = new DownloadFile("abc", file);

        // �e�X�g���{
        int size = downloadFile.getLengthOfData();

        // ����
        assertEquals(file.length(), size);
    }

    /**
     * testGetStreamInternal01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) file:File#createTempFile<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) InputStream:FileInputStream<br>
     *         
     * <br>
     * file�����b�v����FileInputStream���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetStreamInternal01() throws Exception {
        // �O����
        // �T�C�Y��5�̃t�@�C���쐬
        File file = File.createTempFile("abc", "def");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(new byte[5]);
        outputStream.close();
        
        DownloadFile downloadFile = new DownloadFile("abc", file);

        // �e�X�g���{
        InputStream stream = downloadFile.getStreamInternal();

        // ����
        // FileInputStream���ԋp����Ă��邩�m�F
        assertNotNull(stream);
        assertEquals(FileInputStream.class, stream.getClass());
        // file�����b�v����Ă��邩�ǂ����m�F
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertTrue(stream.read() == -1);
    }

}
