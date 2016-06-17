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

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link MessageFormatCloneReturnIfUseDateFormatMap} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Struts�̃o�O(STR-2172)���pHashMap(MessageFormat�L���b�V��)�B<br>
 * ���t�����n�T�u�t�H�[�}�b�g���g�p����MessageFormat�̂݁Aclone���ĕԂ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.MessageFormatCloneReturnIfUseDateFormatMap
 */
public class MessageFormatCloneReturnIfUseDateFormatMapTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageFormatCloneReturnIfUseDateFormatMapTest.class);
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
    public MessageFormatCloneReturnIfUseDateFormatMapTest(String name) {
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
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) �G���g���[�ɂ���MessageFormat<br>
     *         
     * <br>
     * cloneReturnMap�łȂ����̃G���g���[�ɑ��݂�����̂́A
     * clone���ꂸ�ɕԋp����邱�Ƃ��m�F����B<br>
     * ���O�����ɂāAput���s��AcloneReturnMap�����Map�ɍ����ւ��Đ��퓮�삷�邱�Ƃɂ��A
     *   �O�����ɂĎ��O���(�G���g���[)�����o���Ă��邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet01() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("xxx");
        map.put("key1", format);
        UTUtil.setPrivateField(map, "cloneReturnMap", new MessageFormatCloneReturnMap());
        
        // �e�X�g���{
        MessageFormat ret = map.get("key1");
        
        // ����
        assertSame(format, ret);
    }

    /**
     * testGet02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) "key1"
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:�G���g���[[key1 : MessageFormat]
     *         
     * <br>
     * ���Ғl�F(�߂�l) cloneReturnMap�̃G���g���[�ɂ���MessageFormat�Ɠ����ȕʃC���X�^���X<br>
     *         
     * <br>
     * ���g�̃G���g���[�ɂȂ��ꍇ�A
     * cloneReturnMap�̃G���g���[����擾���邱�Ƃ��m�F����B<br>
     * cloneReturnMap�̃G���g���[����擾�����C���X�^���X�́A
     * cloneReturnMap�̃G���g���[�ɂ�����̂ł͂Ȃ��A����Ɠ����ȕʃC���X�^���X�ł��邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet02() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("xxx{0,date}");
        MessageFormatCloneReturnMap cloneReturnMap = new MessageFormatCloneReturnMap();
        cloneReturnMap.put("key1", format);
        UTUtil.setPrivateField(map, "cloneReturnMap", cloneReturnMap);
        
        // �e�X�g���{
        MessageFormat ret = map.get("key1");
        
        // ����
        assertNotSame(format, ret);
        assertEquals(format, ret);
    }

    /**
     * testGet03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) "key1"
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         
     * <br>
     * �L�[�ɊY������G���g���[���A���g�ɂ�cloneReturnMap�ɂ����݂��Ȃ��ꍇ�A
     * null��ԋp���邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet03() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        
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
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) �G���g���[[key1 : �����œn����MessageFormat]
     *         (��ԕω�) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * �t�H�[�}�b�g������Ƀv���[�X�z���_�����݂��Ȃ��Ƃ��A
     * cloneReturnMap�ł͂Ȃ��A���g��put����(�X�[�p�[�N���X�̋@�\)���Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget������C���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut01() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) �G���g���[[key1 : �����œn����MessageFormat]
     *         (��ԕω�) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * �t�H�[�}�b�g������ɃT�u�t�H�[�}�b�g���w�肵�Ȃ��v���[�X�z���_�݂̂����݂���Ƃ��A
     * cloneReturnMap�ł͂Ȃ��A���g��put����(�X�[�p�[�N���X�̋@�\)���Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget������C���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut02() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0,date}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) key1�̃G���g���[�Ȃ�
     *         (��ԕω�) cloneReturnMap:�G���g���[[key1 : �����œn����MessageFormat]
     *         
     * <br>
     * �t�H�[�}�b�g������ɓ��t�����n�T�u�t�H�[�}�b�g���w�肵���v���[�X�z���_�݂̂����݂���Ƃ��A
     * cloneReturnMap��put���A���g�ɂ�put���Ȃ����Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget���قȂ铙���ȃC���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut03() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,date}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

    /**
     * testPut04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0,time}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) key1�̃G���g���[�Ȃ�
     *         (��ԕω�) cloneReturnMap:�G���g���[[key1 : �����œn����MessageFormat]
     *         
     * <br>
     * �t�H�[�}�b�g������ɓ��t�����n�T�u�t�H�[�}�b�g���w�肵���v���[�X�z���_�݂̂����݂���Ƃ��A
     * cloneReturnMap��put���A���g�ɂ�put���Ȃ����Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget���قȂ铙���ȃC���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut04() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,date}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

    /**
     * testPut05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0,number}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) �G���g���[[key1 : �����œn����MessageFormat]
     *         (��ԕω�) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * �t�H�[�}�b�g������ɐ��l�n�T�u�t�H�[�}�b�g���w�肵���v���[�X�z���_�݂̂����݂���Ƃ��A
     * cloneReturnMap�ł͂Ȃ��A���g��put����(�X�[�p�[�N���X�̋@�\)���Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget������C���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
    * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut05() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,number}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0,choice,0#zero | 1#one}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) �G���g���[[key1 : �����œn����MessageFormat]
     *         (��ԕω�) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * �t�H�[�}�b�g������ɑI���^�T�u�t�H�[�}�b�g���w�肵���v���[�X�z���_�݂̂����݂���Ƃ��A
     * cloneReturnMap�ł͂Ȃ��A���g��put����(�X�[�p�[�N���X�̋@�\)���Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget������C���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
    * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut06() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,choice,0#zero | 1#one}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0}bbb{1,number}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) �G���g���[[key1 : �����œn����MessageFormat]
     *         (��ԕω�) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * �t�H�[�}�b�g������ɓ��t�����n�T�u�t�H�[�}�b�g�ȊO���w�肵���v���[�X�z���_���������݂���Ƃ��A
     * cloneReturnMap�ł͂Ȃ��A���g��put����(�X�[�p�[�N���X�̋@�\)���Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget������C���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
    * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut07() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0}bbb{1,number}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0}bbb{1,date}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         (��ԕω�) �G���g���[[key1 : �����œn����MessageFormat]
     *         (��ԕω�) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * �t�H�[�}�b�g������ɓ��t�����n�T�u�t�H�[�}�b�g���w�肵���v���[�X�z���_���܂܂�A�v���[�X�z���_�����킹�ĕ������݂���Ƃ��A
     * cloneReturnMap��put���A���g�ɂ�put���Ȃ����Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget���قȂ铙���ȃC���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
    * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut08() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0}bbb{1,date}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertNull(ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

    /**
     * testPut09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa"<br>
     *         (���) �G���g���[[key1 : MessageFormat]
     *         (���) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * ���Ғl�F(�߂�l) ���Ƃ��ƃG���g���[�ɂ�����MessageFormat<br>
     *         (��ԕω�) �G���g���[[key1 : �����œn����MessageFormat]
     *         (��ԕω�) cloneReturnMap:key1�̃G���g���[�Ȃ�
     *         
     * <br>
     * put�̖߂�l�d�l(�ȑO�ɃL���b�V�����Ă����l��Ԃ�)�𖞂����Ă��邱�Ƃ��m�F����B<br>
     * �t�H�[�}�b�g������Ƀv���[�X�z���_�����݂��Ȃ��Ƃ��A
     * cloneReturnMap�ł͂Ȃ��A���g��put����(�X�[�p�[�N���X�̋@�\)���Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget������C���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut09() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat old = new MessageFormat("xxx");
        map.put("key1", old);
        MessageFormat format = new MessageFormat("aaa");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertSame(old, ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key1"<br>
     *         (����) value:MessageFormat�C���X�^���X<br>
     *                      �t�H�[�}�b�g������:"aaa{0,date}"<br>
     *         (���) key1�̃G���g���[�Ȃ�
     *         (���) cloneReturnMap:�G���g���[[key1 : MessageFormat]
     *         
     * <br>
     * ���Ғl�F(�߂�l) ���Ƃ��ƃG���g���[�ɂ�����MessageFormat<br>
     *         (��ԕω�) key1�̃G���g���[�Ȃ�
     *         (��ԕω�) cloneReturnMap:�G���g���[[key1 : �����œn����MessageFormat]
     *         
     * <br>
     * put�̖߂�l�d�l(�ȑO�ɃL���b�V�����Ă����l��Ԃ�)�𖞂����Ă��邱�Ƃ��m�F����B<br>
     * �t�H�[�}�b�g������ɓ��t�����n�T�u�t�H�[�}�b�g���w�肵���v���[�X�z���_�݂̂����݂���Ƃ��A
     * cloneReturnMap��put���A���g�ɂ�put���Ȃ����Ƃ��m�F����B<br>
     * ��get���\�b�h�̓��삪�������O��ŁAget���قȂ铙���ȃC���X�^���X��Ԃ����ƂŁA
     *   put�̊i�[��U�蕪�������퓮�삵���Ɣ��f����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPut10() throws Exception {
        // �O����
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat old = new MessageFormat("xxx{0,date}");
        map.put("key1", old);
        MessageFormat format = new MessageFormat("aaa{0,date}");
        
        // �e�X�g���{
        MessageFormat ret = map.put("key1", format);
        
        // ����
        assertEquals(old, ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

}
