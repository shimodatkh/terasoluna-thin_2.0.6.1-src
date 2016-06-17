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

import java.io.File;
import java.io.FileInputStream;

import jp.terasoluna.fw.util.FileUtil;
import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link jp.terasoluna.fw.web.HttpSessionListenerImpl} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * HTTP�Z�b�V�����̃��C�t�T�C�N���C�x���g����������N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.HttpSessionListenerImpl
 */
public class HttpSessionListenerImplTest extends PropertyTestCase {

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        
        addProperty("session.dir.base", "c:/sessions");
        
        // �t�@�C���폜
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        // �t�@�C���폜
        FileUtil.removeSessionDirectory("dummyID");
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public HttpSessionListenerImplTest(String name) {
        super(name);
    }

    /**
     * testSessionDestroyed01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) event:not null<br>
     *         (���) session.getId():"dummyID"<br>
     *         (���) dir.exists():false<br>
     *         (���) dir.isDirectory():false<br>
     *         (���) �f�B���N�g��
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :���݂��Ȃ��ꍇ<br>
     *         (���) �v���p�e�B:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �f�B���N�g��
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :���݂��Ȃ�<br>
     *         
     * <br>
     * �Z�b�V�����f�B���N�g�������݂��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSessionDestroyed01() throws Exception {
        // �O����
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);
        
        // �e�X�g���{
        listener.sessionDestroyed(event);
        
        // ����
        File file = new File(PropertyUtil.getProperty("session.dir.base") + "/"
                + FileUtil.getSessionDirectoryName("dummyID"));
        
        assertFalse(file.exists());
    }

    /**
     * testSessionDestroyed02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) event:not null<br>
     *         (���) session.getId():"dummyID"<br>
     *         (���) dir.exists():true<br>
     *         (���) dir.isDirectory():false<br>
     *         (���) �f�B���N�g��
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :���݂��邪�A�f�B���N�g���łȂ��ꍇ<br>
     *         (���) �v���p�e�B:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �f�B���N�g��
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :�폜����Ȃ�<br>
     *         
     * <br>
     * �Z�b�V�����f�B���N�g�������݂��邪�A�f�B���N�g���łȂ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSessionDestroyed02() throws Exception {
        // �O����
        // �Z�b�V�����f�B���N�g���쐬
        File file = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));
        
        file.createNewFile();
        
        
        // �O����
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);
        
        // �e�X�g���{
        listener.sessionDestroyed(event);
        
        // ����
        File result = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));
        
        assertTrue(result.exists());
        
        // �㏈���F�t�@�C���̍폜���f�B���N�g���łȂ�����cleanUpData()�ł͍폜����Ȃ�
        file.delete();
        
    }

    /**
     * testSessionDestroyed03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) event:not null<br>
     *         (���) session.getId():"dummyID"<br>
     *         (���) dir.exists():true<br>
     *         (���) dir.isDirectory():true<br>
     *         (���) �f�B���N�g��
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :�f�B���N�g��
     *         (���) �v���p�e�B:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �f�B���N�g��
     *         ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID"))<br>
     *         :�폜�����<br>
     *         
     * <br>
     * �Z�b�V�����f�B���N�g�������݂��A�f�B���N�g���̏ꍇ�A�폜���ꂽ�����m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSessionDestroyed03() throws Exception {
        // �O����
        // �Z�b�V�����f�B���N�g���쐬
        FileUtil.makeSessionDirectory("dummyID");
                
        // �O����
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);
        
        // �e�X�g���{
        listener.sessionDestroyed(event);
        
        // ����
        File result = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));
        
        assertFalse(result.exists());
    }
    

    /**
     * testSessionDestroyed04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) event:not null<br>
     *         (���) session.getId():"dummyID"<br>
     *         (���) dir.exists():true<br>
     *         (���) dir.isDirectory():true<br>
     *         (���) FileUtil.removeSessionDirectory():true<br>
     *         (���) �f�B���N�g��<br>
     *                ("c:/sessions/" +�@FileUtil.getSessionDirectoryName("dummyID")):�f�B���N�g���i�폜�ł��Ȃ��j<br>
     *         (���) �v���p�e�B:session.dir.base=<br>
     *                c:/sessions<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �f�B���N�g��<br>
     *                    ("c:/sessions/" + FileUtil.getSessionDirectoryName("dummyID")):�폜����Ȃ�<br>
     *         (��ԕω�) ���O:<error���x��><br>
     *                    ���b�Z�[�W�F"can't remove \"" + dir.getPath() + "\"."<br>
     *         
     * <br>
     * �Z�b�V�����f�B���N�g�������݂��A�f�B���N�g���ł��邪�폜�ł��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unused")
    public void testSessionDestroyed04() throws Exception {
        // �O����
        // �Z�b�V�����f�B���N�g���쐬
        FileUtil.makeSessionDirectory("dummyID");
        // �f�B���N�g�����Ƀt�@�C���쐬
        File file = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID")
                + "/test.txt");
        file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(file);

        // �O����
        HttpSessionListenerImpl listener = new HttpSessionListenerImpl();
        
        MockHttpSession session = new MockHttpSession();
        // HttpSessionEvent
        HttpSessionListenerImpl_HttpSessionEventStub01 event =
            new HttpSessionListenerImpl_HttpSessionEventStub01(session);

        // �e�X�g���{
        listener.sessionDestroyed(event);

        // ����
        File result = new File(PropertyUtil.getProperty("session.dir.base")
                + "/" + FileUtil.getSessionDirectoryName("dummyID"));

        assertTrue(result.exists());
        assertTrue(LogUTUtil.checkError("can't remove \"" + result.getPath()
                + "\"."));

        // FileInputStream���N���[�Y
        fileInputStream.close();
    }
}