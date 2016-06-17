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

package jp.terasoluna.fw.service.thin;

import java.util.ArrayList;
import java.util.Iterator;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicMessages} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���b�Z�[�W���ꗗ�N���X�B<br>
 * BLogicMessage�C���X�^���X���i�[����N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicMessages
 */
public class BLogicMessagesTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicMessagesTest.class);
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
    public BLogicMessagesTest(String name) {
        super(name);
    }

    /**
     * testBLogicMessagesBLogicMessages01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) messages:list:���b�Z�[�W�L�[{"message.test01"}��BLogicMessage��ݒ�<br>
     *                groupList:�O���[�v��{"group.test01"}��ݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) this.list:{"message.test01"}��BLogicMessage<br>
     *         (��ԕω�) this.groupList:{"group.test01"}<br>
     *         
     * <br>
     * �������ꂽBLogicMessages��list�����groupList�Ɉ���messages�Ɋi�[���ꂽ�l���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessagesBLogicMessages01() throws Exception {
        // --------------------------------------------------��������
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        list.add(new BLogicMessage("message.test01"));

        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        groupList.add("group.test01");

        // ����BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        BLogicMessages newMessages = new BLogicMessages(messages);
        
        // ����
        assertEquals(1, newMessages.list.size());
        assertEquals("message.test01", list.get(0).getKey());
        assertEquals(1, newMessages.groupList.size());
        assertEquals("group.test01", groupList.get(0));
    }

    /**
     * testClear01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) list:���b�Z�[�W{"message.test01"�A"message.test02"�A"message.test03"}�����Ԃɐݒ�<br>
     *         (���) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"}�����Ԃɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:��̃��X�g<br>
     *         (��ԕω�) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���b�Z�[�W�y�уO���[�v���̃��X�g���N���A����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClear01() throws Exception {

        // --------------------------------------------------list����
        // key����
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";

        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage�i�[
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------grouplist����
        // group����
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.clear();
        
        // ����
        assertTrue(messages.list.isEmpty());
        assertTrue(messages.groupList.isEmpty());

    }

    /**
     * testClear02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) list:��̃��X�g<br>
     *         (���) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:��̃��X�g<br>
     *         (��ԕω�) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���b�Z�[�W�y�уO���[�v���̃��X�g���N���A����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClear02() throws Exception {

        // --------------------------------------------------list����        
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList����
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.clear();
        
        // ����
        assertTrue(messages.list.isEmpty());
        assertTrue(messages.groupList.isEmpty());
    }

    /**
     * testIsEmpty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) list:���b�Z�[�W{"message.test01"�A"message.test02"�A"message.test03"}�����Ԃɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * ���b�Z�[�W���X�g�Ƀ��b�Z�[�W���i�[����Ă���ꍇ�Afalse���ԋp����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsEmpty01() throws Exception {

        // --------------------------------------------------list����        
        // key����
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";

        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage�i�[
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{�E����
        assertFalse(messages.isEmpty());
        
    }

    /**
     * testIsEmpty02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) list:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * ���b�Z�[�W���X�g�Ƀ��b�Z�[�W���i�[����Ă��Ȃ��ꍇ�Atrue���ԋp����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsEmpty02() throws Exception {

        // --------------------------------------------------list����        
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{�E����
        assertTrue(messages.isEmpty());
    }

    /**
     * testGet01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) list:���b�Z�[�W�L�[{"message.test01"�A"message.test02"�A"message.test03"}��BLogicMessage�����Ԃɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Iterator:�ݒ肵�����Ԓʂ��BLogicMessage�����o����C�e���[�^<br>
     *         
     * <br>
     * �������b�Z�[�W��ݒ肵�����A���Ԓʂ�Ƀ��b�Z�[�W���擾�ł��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet01() throws Exception {

        // --------------------------------------------------list����                
        // key����
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";
        
        // BLogicMessage�ݒ�
        BLogicMessage[] blogicMessageArray = new BLogicMessage[keyArray.length];
        for (int i = 0; i < keyArray.length; i++) {
            blogicMessageArray[i] = new BLogicMessage(keyArray[i]);
        }
        
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < blogicMessageArray.length; i++) {
            list.add(blogicMessageArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        Iterator<BLogicMessage> ite = messages.get();
        
        // ����
        int j = 0;
        while (ite.hasNext()) {
            BLogicMessage message = ite.next();
            assertEquals(keyArray[j], message.getKey());
            j++;
        }
        assertEquals(j, keyArray.length);

    }

    /**
     * testGet02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) list:���b�Z�[�W�L�[{"message.test01"}��BLogicMessage��ݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Iterator:�ݒ肵�����Ԓʂ��BLogicMessage�����o����C�e���[�^<br>
     *         
     * <br>
     * ���b�Z�[�W��1�ݒ肵�����A���Ԓʂ�Ƀ��b�Z�[�W���擾�ł��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet02() throws Exception {

        // --------------------------------------------------list����        
        // key����
        String[] keyArray = new String[1];
        keyArray[0] = "message.test01";

        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage�i�[
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        Iterator<BLogicMessage> ite = messages.get();
        
        // ����
        int j = 0;
        while (ite.hasNext()) {
            BLogicMessage message = ite.next();
            assertEquals(keyArray[j], message.getKey());
            j++;
        }
        assertEquals(j, keyArray.length);
    }

    /**
     * testGet03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) list:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Iterator:��̃��b�Z�[�W�ꗗ�C�e���[�^<br>
     *         
     * <br>
     * ���b�Z�[�W���X�g����̃��X�g�̏ꍇ�A��̃��b�Z�[�W�ꗗ�C�e���[�^���擾�ł��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet03() throws Exception {

        // --------------------------------------------------list����    
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        Iterator<BLogicMessage> ite = messages.get();
        
        // ����
        assertFalse(ite.hasNext());
    }

    /**
     * testGetGroup01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"}�����Ԃɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Iterator:�ݒ肵�����Ԓʂ�ɃO���[�v���iString�j�����o����C�e���[�^<br>
     *         
     * <br>
     * �����O���[�v����ݒ肵�����A���Ԓʂ�ɃO���[�v�����擾�ł��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetGroup01() throws Exception {

        // --------------------------------------------------groupList����  
        // group����
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        Iterator<String> ite = messages.getGroup();
        
        // ����
        int j = 0;
        while (ite.hasNext()) {
            String group = ite.next();
            assertEquals(groupArray[j], group);
            j++;
        }
        assertEquals(j, groupArray.length);

    }

    /**
     * testGetGroup02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) groupList:�O���[�v��{"group.test01"}��ݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Iterator:�ݒ肵�����Ԓʂ�ɃO���[�v���iString�j�����o����C�e���[�^<br>
     *         
     * <br>
     * �O���[�v����1�ݒ肵�����A���Ԓʂ�ɃO���[�v�����擾�ł��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetGroup02() throws Exception {

        // --------------------------------------------------groupList����  
        // group����
        String[] groupArray = new String[1];
        groupArray[0] = "group.test01";
        
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        Iterator<String> ite = messages.getGroup();
        
        // ����
        int j = 0;
        while (ite.hasNext()) {
            String group = ite.next();
            assertEquals(groupArray[j], group);
            j++;
        }
        assertEquals(j, groupArray.length);
    }

    /**
     * testGetGroup03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Iterator:��̃O���[�v���ꗗ�C�e���[�^<br>
     *         
     * <br>
     * �O���[�v�����X�g����̃��X�g�̏ꍇ�A��̃O���[�v���ꗗ�C�e���[�^���擾�ł��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetGroup03() throws Exception {

        // --------------------------------------------------groupList����  
        // group����
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        Iterator<String> ite = messages.getGroup();
        
        // ����
        assertFalse(ite.hasNext());
    }

    /**
     * testAddStringBLogicMessage01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) group:"group.test00"<br>
     *         (����) message:���b�Z�[�W�L�[{"message.test00"}��BLogicMessage<br>
     *         (���) list:���b�Z�[�W�L�[{"message.test01"�A"message.test02"�A"message.test03"}��BLogicMessage�����Ԃɐݒ�<br>
     *         (���) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"}�����Ԃɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:���b�Z�[�W�L�[{"message.test01"�A"message.test02"�A"message.test03"�A"message.test00"}��BLogicMessage�����ԂɊi�[���ꂽ���X�g<br>
     *         (��ԕω�) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"�A"group.test00"}�����ԂɊi�[���ꂽ���X�g<br>
     *         
     * <br>
     * ���b�Z�[�W���X�g�A�O���[�v�����X�g�Ƀ��b�Z�[�W�A�O���[�v�������ɐݒ肳��Ă������A���b�Z�[�W�A�O���[�v�����ǉ��ōŌ�Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddStringBLogicMessage01() throws Exception {

        // --------------------------------------------------list����
        // key����
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";
        
        // ����ɗp����key�̔z��
        String[] expectedKeyArray = new String[keyArray.length + 1];

        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage�i�[
            list.add(new BLogicMessage(keyArray[i]));
            
            // ����ɗp����key�̔z��ɃR�s�[
            expectedKeyArray[i] = keyArray[i];
        }

        // --------------------------------------------------groupList����
        // group����
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // ����ɗp����group�̔z��
        String[] expectedGroupArray = new String[groupArray.length + 1];
        
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
            
            // ����ɗp����group�̔z��ɃR�s�[
            expectedGroupArray[i] = groupArray[i];
        }
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        // ����message�ݒ�
        String key = "message.test00";
        BLogicMessage message = new BLogicMessage(key);

        // ����ɗp����key�̔z��ɃR�s�[
        expectedKeyArray[keyArray.length] = key;
        
        // ����group�ݒ�
        String group = "group.test00";
        
        // ����ɗp����group�̔z��ɃR�s�[
        expectedGroupArray[groupArray.length] = group;
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.add(group, message);
        
        // ����
        Iterator<BLogicMessage> resultItr = messages.list.iterator();
        int j = 0;
        while (resultItr.hasNext()) {
            BLogicMessage resultMessage = resultItr.next();
            assertEquals(expectedKeyArray[j], resultMessage.getKey());
            j++;
        }
        assertEquals(j, expectedKeyArray.length);
        
        int k = 0;
        Iterator<String> resultItr2 = messages.groupList.iterator();
        while (resultItr2.hasNext()) {
            String resultGroup = resultItr2.next();
            assertEquals(expectedGroupArray[k], resultGroup);
            k++;
        }
        assertEquals(k, expectedGroupArray.length);
    }

    /**
     * testAddStringBLogicMessage02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) group:"group.test00"<br>
     *         (����) message:���b�Z�[�W�L�[{"message.test00"}��BLogicMessage<br>
     *         (���) list:��̃��X�g<br>
     *         (���) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:���b�Z�[�W�L�[{"message.test00"}��BLogicMessage���i�[���ꂽ���X�g<br>
     *         (��ԕω�) groupList:�O���[�v��{"group.test00"}���i�[���ꂽ���X�g<br>
     *         
     * <br>
     * ���b�Z�[�W���X�g�A�O���[�v�����X�g�Ƀ��b�Z�[�W�A�O���[�v�����ݒ肳��Ă��Ȃ����A���b�Z�[�W�A�O���[�v�����i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddStringBLogicMessage02() throws Exception {

        // --------------------------------------------------list����
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList����
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        // ����message�ݒ�
        String key = "message.test00";
        BLogicMessage message = new BLogicMessage(key);
        
        // ����group�ݒ�
        String group = "group.test00";
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.add(group, message);
        
        // ����
        assertTrue(messages.list.size() == 1);
        assertEquals(key, messages.list.get(0).getKey());
        assertTrue(messages.groupList.size() == 1);
        assertEquals(group, messages.groupList.get(0));
    }

    /**
     * testAddStringBLogicMessage03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) group:""<br>
     *         (����) message:���b�Z�[�W�L�[{"message.test00"}��BLogicMessage<br>
     *         (���) list:��̃��X�g<br>
     *         (���) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:���b�Z�[�W�L�[{"message.test00"}��BLogicMessage���i�[���ꂽ���X�g<br>
     *         (��ԕω�) groupList:�O���[�v��{""}���i�[���ꂽ���X�g<br>
     *         
     * <br>
     * ������group���󕶎��̎��A�O���[�v�����X�g�ɋ󕶎����i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddStringBLogicMessage03() throws Exception {

        // --------------------------------------------------list����
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList����
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        // ����message�ݒ�
        String key = "message.test00";
        BLogicMessage message = new BLogicMessage(key);
        
        // ����group�ݒ�
        String group = "";
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.add(group, message);

        // ����
        assertTrue(messages.list.size() == 1);
        assertEquals(key, messages.list.get(0).getKey());
        assertTrue(messages.groupList.size() == 1);
        assertEquals(group, messages.groupList.get(0));
    }

    /**
     * testAddStringBLogicMessage04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) group:null<br>
     *         (����) message:null<br>
     *         (���) list:��̃��X�g<br>
     *         (���) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:null���i�[���ꂽ���X�g<br>
     *         (��ԕω�) groupList:�O���[�v��{null}���i�[���ꂽ���X�g<br>
     *         
     * <br>
     * ������group��null�Amessages��null�̎��A�O���[�v�����X�g��null�A���b�Z�[�W���X�g��null���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddStringBLogicMessage04() throws Exception {

        // --------------------------------------------------list����
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList����
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        // ����message�ݒ�
        BLogicMessage message = null;
        
        // ����group�ݒ�
        String group = null;
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.add(group, message);
        
        // ����
        assertTrue(messages.list.size() == 1);
        assertNull(messages.list.get(0));
        assertTrue(messages.groupList.size() == 1);
        assertNull(messages.groupList.get(0));
    }

    /**
     * testAddBLogicMessages01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) messages:list:���b�Z�[�W�L�[{"message.test10"�A"message.test20"�A"message.test30"}��BLogicMessage�����Ԃɐݒ�<br>
     *                groupList:�O���[�v��{"group.test10"�A"group.test20"�A"group.test30"}�����Ԃɐݒ�<br>
     *         (���) list:���b�Z�[�W�L�[{"message.test01"�A"message.test02"�A"message.test03"}��BLogicMessage�����Ԃɐݒ�<br>
     *         (���) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"}�����Ԃɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:���b�Z�[�W�L�[{"message.test01"�A"message.test02"�A"message.test03"�A"message.test10"�A"message.test20"�A"message.test30"}��BLogicMessage�����ԂɊi�[���ꂽ���X�g<br>
     *         (��ԕω�) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"�A"group.test10"�A"group.test20"�A"group.test30"}�����ԂɊi�[���ꂽ���X�g<br>
     *         
     * <br>
     * ���b�Z�[�W���X�g�A�O���[�v�����X�g�Ƀ��b�Z�[�W�A�O���[�v�������ɐݒ肳��Ă������A���b�Z�[�W�A�O���[�v�����ǉ��ōŌ�Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddBLogicMessages01() throws Exception {

        // --------------------------------------------------list����        
        // key����
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";
        
        // �����pkey�����@���������Ŏg�p
        String[] keyArray2 = new String[3];
        keyArray2[0] = "message.test10";
        keyArray2[1] = "message.test20";
        keyArray2[2] = "message.test30";
        
        // ����ɗp����key�̔z��
        String[] expectedKeyArray = new String[keyArray.length
                + keyArray2.length];

        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage�i�[
            list.add(new BLogicMessage(keyArray[i]));
            
            // ����ɗp����key�̔z��ɃR�s�[
            expectedKeyArray[i] = keyArray[i];
        }

        // --------------------------------------------------groupList����
        // group����
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // �����pgroup�����@���������Ŏg�p
        String[] groupArray2 = new String[3];
        groupArray2[0] = "group.test10";
        groupArray2[1] = "group.test20";
        groupArray2[2] = "group.test30";
        
        // ����ɗp����group�̔z��
        String[] expectedGroupArray = new String[groupArray.length
                + groupArray2.length];
        
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
            
            // ����ɗp����group�̔z��ɃR�s�[
            expectedGroupArray[i] = groupArray[i];
        }
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        // list�ݒ�
        ArrayList<BLogicMessage> list2 = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray2.length; i++) {
            // BLogicMessage�i�[
            list2.add(new BLogicMessage(keyArray2[i]));
            
            // ����ɗp����key�̔z��ɃR�s�[
            expectedKeyArray[keyArray.length + i] = keyArray2[i];
        }

        // groupList�ݒ�
        ArrayList<String> groupList2 = new ArrayList<String>();
        for (int i = 0; i < groupArray2.length; i++) {
            groupList2.add(groupArray2[i]);
            
            // ����ɗp����group�̔z��ɃR�s�[
            expectedGroupArray[groupArray.length + i] = groupArray2[i];
        }
        
        // ����BLogicMessages�ݒ�
        BLogicMessages messages2 = new BLogicMessages();
        UTUtil.setPrivateField(messages2, "list", list2);
        UTUtil.setPrivateField(messages2, "groupList", groupList2);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.add(messages2);
        
        // ����
        Iterator<BLogicMessage> resultItr = messages.list.iterator();
        int j = 0;
        while (resultItr.hasNext()) {
            BLogicMessage resultMessage = resultItr.next();
            assertEquals(expectedKeyArray[j], resultMessage.getKey());
            j++;
        }
        assertEquals(j, expectedKeyArray.length);
        
        int k = 0;
        Iterator<String> resultItr2 = messages.groupList.iterator();
        while (resultItr2.hasNext()) {
            String resultGroup = resultItr2.next();
            assertEquals(expectedGroupArray[k], resultGroup);
            k++;
        }
        assertEquals(k, expectedGroupArray.length);
    }

    /**
     * testAddBLogicMessages02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) messages:list:���b�Z�[�W�L�[{"message.test10"}��BLogicMessage�����Ԃɐݒ�<br>
     *                groupList:�O���[�v��{"group.test10"}�����Ԃɐݒ�<br>
     *         (���) list:��̃��X�g<br>
     *         (���) groupList:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:���b�Z�[�W�L�[{"message.test10"}��BLogicMessage���i�[���ꂽ���X�g<br>
     *         (��ԕω�) groupList:�O���[�v��{"group.test10"}���i�[���ꂽ���X�g<br>
     *         
     * <br>
     * ���b�Z�[�W���X�g�A�O���[�v�����X�g�Ƀ��b�Z�[�W�A�O���[�v�����ݒ肳��Ă��Ȃ����A���b�Z�[�W�A�O���[�v�����i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddBLogicMessages02() throws Exception {

        // --------------------------------------------------list����
        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        
        // --------------------------------------------------groupList����
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        // key����
        String[] keyArray2 = new String[1];
        keyArray2[0] = "message.test10";
        
        // list�ݒ�
        ArrayList<BLogicMessage> list2 = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray2.length; i++) {
            // BLogicMessage�i�[
            list2.add(new BLogicMessage(keyArray2[i]));
        }
        
        // group����
        String[] groupArray2 = new String[1];
        groupArray2[0] = "group.test10";
        
        // groupList�ݒ�
        ArrayList<String> groupList2 = new ArrayList<String>();
        for (int i = 0; i < groupArray2.length; i++) {
            groupList2.add(groupArray2[i]);
        }
        
        // ����BLogicMessages�ݒ�
        BLogicMessages messages2 = new BLogicMessages();
        UTUtil.setPrivateField(messages2, "list", list2);
        UTUtil.setPrivateField(messages2, "groupList", groupList2);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.add(messages2);
        
        // ����
        assertTrue(messages.list.size() == 1);
        assertEquals(keyArray2[0], messages.list.get(0).getKey());
        assertTrue(messages.groupList.size() == 1);
        assertEquals(groupArray2[0], messages.groupList.get(0));

    }

    /**
     * testAddBLogicMessages03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) messages:null<br>
     *         (���) list:���b�Z�[�W�L�[{"message.test01"�A"message.test02"�A"message.test03"}��BLogicMessage�����Ԃɐݒ�<br>
     *         (���) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"}�����Ԃɐݒ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) list:���b�Z�[�W�L�[{"message.test01"�A"message.test02"�A"message.test03"}��BLogicMessage�����ԂɊi�[���ꂽ���X�g<br>
     *         (��ԕω�) groupList:�O���[�v��{"group.test01"�A"group.test02"�A"group.test03"}�����ԂɊi�[���ꂽ���X�g<br>
     *         
     * <br>
     * messages��null��ݒ肵���ꍇ�A���������ɏ������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddBLogicMessages03() throws Exception {

        // --------------------------------------------------list����        
        // key����
        String[] keyArray = new String[3];
        keyArray[0] = "message.test01";
        keyArray[1] = "message.test02";
        keyArray[2] = "message.test03";

        // list�ݒ�
        ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();
        for (int i = 0; i < keyArray.length; i++) {
            // BLogicMessage�i�[
            list.add(new BLogicMessage(keyArray[i]));
        }

        // --------------------------------------------------groupList����
        // group����
        String[] groupArray = new String[3];
        groupArray[0] = "group.test01";
        groupArray[1] = "group.test02";
        groupArray[2] = "group.test03";
        
        // groupList�ݒ�
        ArrayList<String> groupList = new ArrayList<String>();
        for (int i = 0; i < groupArray.length; i++) {
            groupList.add(groupArray[i]);
        }
        
        // --------------------------------------------------BLogicMessages����
        // BLogicMessages�ݒ�
        BLogicMessages messages = new BLogicMessages();
        UTUtil.setPrivateField(messages, "list", list);
        UTUtil.setPrivateField(messages, "groupList", groupList);
        
        // --------------------------------------------------��������
        
        // �e�X�g���{
        messages.add(null);
        
        // ����
        Iterator<BLogicMessage> resultItr = messages.list.iterator();
        int j = 0;
        while (resultItr.hasNext()) {
            BLogicMessage resultMessage = resultItr.next();
            assertEquals(keyArray[j], resultMessage.getKey());
            j++;
        }
        assertEquals(j, keyArray.length);
        
        int k = 0;
        Iterator<String> resultItr2 = messages.groupList.iterator();
        while (resultItr2.hasNext()) {
            String resultGroup = resultItr2.next();
            assertEquals(groupArray[k], resultGroup);
            k++;
        }
        assertEquals(k, groupArray.length);
    }

}
