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

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicMessage} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���b�Z�[�W���N���X�B<br>
 * �r�W�l�X���W�b�N�̎��s���ʂ��󂯂āA���b�Z�[�W��ݒ肷��ۂɐ�������B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicMessage
 */
public class BLogicMessageTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicMessageTest.class);
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
    public BLogicMessageTest(String name) {
        super(name);
    }

    /**
     * testGetKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) key:"key"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"key"<br>
     *         
     * <br>
     * BLogicMessage�Ɋi�[����Ă���key�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetKey01() throws Exception {
        // BLogic�o�͏��
        BLogicMessage blogicMessage = new BLogicMessage(null);

        // �e�X�g�l�ݒ�
        String str = "key";
        
        // key�ݒ�
        UTUtil.setPrivateField(blogicMessage, "key", str);

        // �e�X�g���{�E����
        assertEquals("key", blogicMessage.getKey());
    }

    /**
     * testGetValues01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) values:{"value1", "value2", "value3"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object[]:{"value1", "value2", "value3"}<br>
     *         
     * <br>
     * BLogicMessage�Ɋi�[����Ă���values�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValues01() throws Exception {
        // BLogic�o�͏��
        BLogicMessage blogicMessage = new BLogicMessage(null);

        // �e�X�g�l�ݒ�
        Object[] values = {"value1", "value2", "value3"};
        
        // values�ݒ�
        UTUtil.setPrivateField(blogicMessage, "values", values);

        // �e�X�g���{
        Object[] results = blogicMessage.getValues();
        
        // ����
        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], results[i]);
        }
        assertEquals(values.length, results.length);
    }

    /**
     * testIsResource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) resource:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * BLogicMessage�Ɋi�[����Ă���resource�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsResource01() throws Exception {
        // �e�X�g�l�ݒ�
        BLogicMessage blogicMessage = new BLogicMessage(null);
        
        // �e�X�g�l�ݒ�
        // resource�ݒ�
        UTUtil.setPrivateField(blogicMessage, "resource", true);

        // �e�X�g���{�E����
        assertTrue(blogicMessage.isResource());
    }

    /**
     * testBLogicMessageString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:"key"<br>
     *         (��ԕω�) values:null<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key��"key"�̏ꍇ�A�������ꂽBLogicMessage��key��"key"���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageString01() throws Exception {
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage("key");

        // ����
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageString02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) key:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:""<br>
     *         (��ԕω�) values:null<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key���󕶎��̏ꍇ�A�������ꂽBLogicMessage��key�ɋ󕶎����i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageString02() throws Exception {
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage("");

        // ����
        assertEquals("", UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageString03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) key:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:null<br>
     *         (��ԕω�) values:null<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key��null�̏ꍇ�A�������ꂽBLogicMessage��key��null���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageString03() throws Exception {
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage(null);

        // ����
        assertNull(UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringObject01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key"<br>
     *         (����) values:{"value1", "value2", "value3"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:"key"<br>
     *         (��ԕω�) values:{"value1", "value2", "value3"}<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key��"key"�Avalues�������I�u�W�F�N�g�̏ꍇ�A�������ꂽBLogicMessage��key��"key"�Avalues�Ɏw��̃I�u�W�F�N�g���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageStringObject01() throws Exception {
        // �e�X�g�l�ݒ�
        Object[] values = {"value1", "value2", "value3"};
        
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage("key", values);

        // ����
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        Object[] results = (Object[]) UTUtil.getPrivateField(blogicMessage,
                "values");
        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], results[i]);
        }
        assertEquals(values.length, results.length);
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
        
    }

    /**
     * testBLogicMessageStringObject02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:""<br>
     *         (����) values:{"value1"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:""<br>
     *         (��ԕω�) values:{"value1"}<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key���󕶎��Avalues���P�̃I�u�W�F�N�g�̏ꍇ�A�������ꂽBLogicMessage��key�ɋ󕶎��Avalues�Ɏw��̃I�u�W�F�N�g���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageStringObject02() throws Exception {
        // �e�X�g�l�ݒ�
        Object[] values = {"value1"};
        
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage("", values);

        // ����
        assertEquals("", UTUtil.getPrivateField(blogicMessage, "key"));
        Object[] results = ((Object[]) UTUtil.getPrivateField(blogicMessage,
                "values"));
        assertEquals(1, results.length);
        assertEquals("value1", results[0]);
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringObject03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) key:null<br>
     *         (����) values:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:null<br>
     *         (��ԕω�) values:null<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key��null�Avalues��null�̏ꍇ�A�������ꂽBLogicMessage��key��null�Avalues��null���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageStringObject03() throws Exception {        
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage(null, (Object[]) null);

        // ����
        assertNull(UTUtil.getPrivateField(blogicMessage, "key"));
        assertNull(UTUtil.getPrivateField(blogicMessage, "values"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringObject04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) key:"key"<br>
     *         (����) values:{ }�i��̔z��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:"key"<br>
     *         (��ԕω�) values:{ }<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����values����̔z��̏ꍇ�A�������ꂽBLogicMessage��values�ɋ�̔z�񂪊i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageStringObject04() throws Exception {
        // �e�X�g�l�ݒ�
        Object[] values = new Object[] {};
        
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage("key", values);

        // ����
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        assertEquals(0, ((Object[]) UTUtil.getPrivateField(blogicMessage,
                "values")).length);
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringboolean01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key"<br>
     *         (����) resource:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:"key"<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key��"key"�Aresource��true�̏ꍇ�A�������ꂽBLogicMessage��key��"key"�Aresource��true���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageStringboolean01() throws Exception {
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage("key", true);

        // ����
        assertEquals("key", UTUtil.getPrivateField(blogicMessage, "key"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringboolean02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA�AC
     * <br><br>
     * ���͒l�F(����) key:""<br>
     *         (����) resource:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:""<br>
     *         (��ԕω�) resource:false<br>
     *         
     * <br>
     * ����key���󕶎��Aresource��false�̏ꍇ�A�������ꂽBLogicMessage��key�ɋ󕶎��A"�Aresource��false���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageStringboolean02() throws Exception {
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage("", false);

        // ����
        assertEquals("", UTUtil.getPrivateField(blogicMessage, "key"));
        assertFalse((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

    /**
     * testBLogicMessageStringboolean03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) key:null<br>
     *         (����) resource:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�:�|<br>
     *         (��ԕω�) key:null<br>
     *         (��ԕω�) resource:true<br>
     *         
     * <br>
     * ����key��null�̏ꍇ�A�������ꂽBLogicMessage��key��null���i�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMessageStringboolean03() throws Exception {
        // �e�X�g���{
        BLogicMessage blogicMessage = new BLogicMessage(null, true);

        // ����
        assertNull(UTUtil.getPrivateField(blogicMessage, "key"));
        assertTrue((Boolean) UTUtil.getPrivateField(blogicMessage, "resource"));
    }

}
