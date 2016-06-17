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

package jp.terasoluna.fw.web.struts.action;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageBean} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * DB����擾�����s�i���b�Z�[�W���\�[�X�j���ꎞ�I�ɃI�u�W�F�N�g�̌`����
 * �ۊǂ��邽�߂ɂ���N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageBean
 */
public class DBMessageBeanTest extends TestCase {

    /**
     * �e�X�g�p�C���X�^���X�B
     */
    DBMessageBean dbmBean = null;
    
    
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageBeanTest.class);
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
        dbmBean = new DBMessageBean();
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
        dbmBean = null;
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DBMessageBeanTest(String name) {
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
     * ���͒l�F(���) key:"test01"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"test01"<br>
     *         
     * <br>
     * getter�̌Ăяo�����m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetKey01() throws Exception {
        //  �O����
        UTUtil.setPrivateField(dbmBean, "key", "test01");
        
        //  �e�X�g���{
        String actual = dbmBean.getKey();

        //  ����
        assertEquals("test01", actual);
    }

    /**
     * testSetKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"test01"<br>
     *         (���) key:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) key:"test01"<br>
     *         
     * <br>
     * setter�̌Ăяo�����m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetKey01() throws Exception {
        //  �O����
        UTUtil.setPrivateField(dbmBean, "key", null);

        //  �e�X�g���{
        dbmBean.setKey("test01");

        //  ����
        assertEquals("test01", UTUtil.getPrivateField(dbmBean, "key"));
    }

    /**
     * testGetValue01()
     * <br><br>
     * 
     *  (����n) 
     *  <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) value:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * getter�̌Ăяo�����m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValue01() throws Exception {
        //  �O����
        UTUtil.setPrivateField(dbmBean, "value", "�e�X�g���b�Z�[�W�O�P");
        
        //  �e�X�g���{
        String actual = dbmBean.getValue();

        //  ����
        assertEquals("�e�X�g���b�Z�[�W�O�P", actual);
    }

    /**
     * testSetValue01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"�e�X�g���b�Z�[�W�O�P"<br>
     *         (���) value:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) value:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * setter�̌Ăяo�����m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValue01() throws Exception {
        //  �O����
        UTUtil.setPrivateField(dbmBean, "value", null);

        //  �e�X�g���{
        dbmBean.setValue("�e�X�g���b�Z�[�W�O�P");

        //  ����
        assertEquals("�e�X�g���b�Z�[�W�O�P", 
                     UTUtil.getPrivateField(dbmBean, "value"));
    }

}
