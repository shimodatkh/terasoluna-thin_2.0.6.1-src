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

package jp.terasoluna.fw.web.struts;

import java.text.MessageFormat;

import junit.framework.TestCase;

/**
 * {@link MessageFormatCloneReturnMap} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Struts�̃o�O(STR-2172)���pHashMap(MessageFormat�L���b�V��)�B<br>
 * get�̍ہAclone���ĕԂ��BDecimalFormat#clone�̖���������邽�߁Aput���ɂ�clone����B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.MessageFormatCloneReturnMap
 */
public class MessageFormatCloneReturnMapTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageFormatCloneReturnMapTest.class);
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
    public MessageFormatCloneReturnMapTest(String name) {
        super(name);
    }

    /**
     * testGet01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) "key1"
     *         (���) �G���g���[[key1 : MessageFormat]
     *         
     * <br>
     * ���Ғl�F(�߂�l) �G���g���[�ɂ���MessageFormat�Ɠ����ȕʃC���X�^���X<br>
     *                  2��ڎ��s���ɂ́A1��ڂƓ����ȕʃC���X�^���X
     * <br>
     * �G���g���[�ɑ��݂�����̂́Aclone���ĕԋp���邱�Ƃ��m�F����B<br>
     * ��put��get��clone���邽�߁Aput����get���������ł́A����get��clone���Ă��Ȃ��Ă����f�����Ȃ��B<br>
     *   ����āA�����ł́Aget��2����s���āA1��ڂ�2��ڂ̖߂�l���A�����ȕʃC���X�^���X�ł��邱�Ƃ��m�F���邱�ƂŁA
     *   get����clone����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet01() {
        // �O����
        MessageFormatCloneReturnMap map = new MessageFormatCloneReturnMap();
        MessageFormat format = new MessageFormat("xxx");
        map.put("key1", format);
        
        // �e�X�g���{
        MessageFormat ret1 = map.get("key1");
        MessageFormat ret2 = map.get("key1");
        
        // ����
        assertNotSame(format, ret1);
        assertEquals(format, ret1);
        assertNotSame(ret1, ret2);
        assertEquals(ret1, ret2);
    }

    /**
     * testGet02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) "key1"
     *         (���) key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         
     * <br>
     * �L�[�ɊY������G���g���[�����݂��Ȃ��ꍇ�Anull��ԋp���邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet02() {
        // �O����
        MessageFormatCloneReturnMap map = new MessageFormatCloneReturnMap();
        
        // �e�X�g���{
        MessageFormat ret = map.get("key1");
        
        // ����
        assertNull(ret);
    }

    /**
     * testPut01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"
     *         (����) value:MessageFormat
     *         (���) �����key, value�C���X�^���X�ň�x���s�ς�
     *         
     * <br>
     * ���Ғl�F(�߂�l) ����value�œn����MessageFormat�Ɠ����ȕʃC���X�^���X<br>
     *         
     * <br>
     * put����ہAclone���ăL���b�V�����邱�Ƃ��m�F����B<br>
     * ��put��get��clone���邽�߁Aput����get�����̂ł́A����put��clone�C���X�^���X���L���b�V�����Ă��Ȃ��Ă����f�����Ȃ��B<br>
     *   ����āA�����ł́Aput�̖߂�l(�߂�l�ɂ��Ă͊g�����Ă��Ȃ�)�𗘗p���A
     *   put�̖߂�l���A���炩���߈���value�ɓn�����C���X�^���X�Ƃ͈قȂ铙���ȃC���X�^���X�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut01() {
        // �O����
        MessageFormatCloneReturnMap map = new MessageFormatCloneReturnMap();
        MessageFormat format = new MessageFormat("xxx");
        map.put("key1", format);
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNotSame(format, ret);
        assertEquals(format, ret);
    }
}
