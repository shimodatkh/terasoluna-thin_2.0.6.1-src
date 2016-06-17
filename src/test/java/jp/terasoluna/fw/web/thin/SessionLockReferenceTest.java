/*
 * Copyright (c) 2011 NTT DATA Corporation
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

import java.lang.ref.ReferenceQueue;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.SessionLockReference} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * WeakReference�̊g���N���X�B
 * �Z�b�V����ID���ێ��ł���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.SessionLockReference
 */
public class SessionLockReferenceTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SessionLockReferenceTest.class);
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
    public SessionLockReferenceTest(String name) {
        super(name);
    }

    /**
     * testSessionLockReference01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sessionId:1111<br>
     *         (����) referent:LimitedLock<br>
     *         (����) q:ReferenceQueue<br>
     * <br>
     * ���Ғl�F(�߂�l) SessionLockReference:not Null<br>
     *         (��ԕω�) sessionId:1111<br>
     *         (��ԕω�) get�̖߂�l:LimitedLock<br>
     * <br>
     * �R���X�g���N�^�̈���sessionId�ŗ^�����l��sessionId�ɐݒ肳��邱�Ƃ��m�F����B<br>
     * ����referent�ŗ^�����I�u�W�F�N�g���Aget�œ����邱�Ƃ��m�F����B<br>
     * (sessionId�ȊO�̓���́A�X�[�p�[�N���X�ɂ����́B)<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSessionLockReference01() throws Exception {
        // �O����
        ReferenceQueue<LimitedLock> refQueue = new ReferenceQueue<LimitedLock>();
        LimitedLock lock = new LimitedLock(1);
        
        // �e�X�g���{
        SessionLockReference ref = new SessionLockReference("1111", lock, refQueue);
        
        // ����
        assertEquals("1111", UTUtil.getPrivateField(ref, "sessionId"));
        assertSame(lock, ref.get());
    }

    /**
     * testSessionLockReference02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sessionId:null<br>
     *         (����) referent:null<br>
     *         (����) q:null<br>
     * <br>
     * ���Ғl�F(�߂�l) SessionLockReference:not Null<br>
     *         (��ԕω�) sessionId:null<br>
     *         (��ԕω�) get�̖߂�l:null<br>
     * <br>
     * �R���X�g���N�^�̈������S��null�ł��A�C���X�^���X�������ł��邱�Ƃ��m�F����B<br>
     * �R���X�g���N�^�̈���sessionId�ŗ^����null��sessionId�ɐݒ肳��邱�Ƃ��m�F����B<br>
     * ����referent�ŗ^����null���Aget�œ����邱�Ƃ��m�F����B<br>
     * (sessionId�ȊO�̓���́A�X�[�p�[�N���X�ɂ����́B)<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSessionLockReference02() throws Exception {
        // �e�X�g���{
        SessionLockReference ref = new SessionLockReference(null, null, null);
        
        // ����
        assertNull(UTUtil.getPrivateField(ref, "sessionId"));
        assertNull(ref.get());
    }

    /**
     * testGetSessionId01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) sessionId:1111<br>
     *         (���) referent:LimitedLock<br>
     *         (���) queue:ReferenceQueue<br>
     * <br>
     * ���Ғl�F(�߂�l) String:1111<br>
     * <br>
     * �ݒ肳��Ă���sessionId���擾�ł��邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetSessionId01() {
        // �O����
        ReferenceQueue<LimitedLock> refQueue = new ReferenceQueue<LimitedLock>();
        LimitedLock lock = new LimitedLock(1);
        SessionLockReference ref = new SessionLockReference("1111", lock, refQueue);
        
        // �e�X�g���{
        String result = ref.getSessionId();
        
        // ����
        assertEquals("1111", result);
    }
}
