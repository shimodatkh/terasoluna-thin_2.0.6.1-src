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
import java.util.HashMap;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link MessageFormatCacheMapFactory} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Struts�̃o�O(STR-2172)���pHashMap(MessageFormat�L���b�V��)�̃t�@�N�g���N���X�B<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory
 */
public class MessageFormatCacheMapFactoryTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageFormatCacheMapFactoryTest.class);
    }
    
    /**
     * �������������s��.
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
    }

    /**
     * �I���������s��.
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }
    
    /**
     * testGetInstance01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(�O�����) messageResources.messageFormatClone:�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         
     * <br>
     * messageResources.messageFormatClone�̐ݒ肪�����ꍇ�A
     * MessageFormatCloneReturnIfUseDateFormatMap���ԋp����邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInstance01() throws Exception {
        // �O����
        deleteProperty("messageResources.messageFormatClone");
        
        // �e�X�g���{
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // ����
        assertEquals(MessageFormatCloneReturnIfUseDateFormatMap.class, map.getClass());
    }

    /**
     * testGetInstance02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(�O�����) messageResources.messageFormatClone:"enable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) MessageFormatCloneReturnMap�C���X�^���X<br>
     *         
     * <br>
     * messageResources.messageFormatClone��"enable"�̏ꍇ�A
     * MessageFormatCloneReturnMap���ԋp����邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInstance02() throws Exception {
        // �O����
        addProperty("messageResources.messageFormatClone", "enable");
        
        // �e�X�g���{
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // ����
        assertEquals(MessageFormatCloneReturnMap.class, map.getClass());
    }

    /**
     * testGetInstance03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(�O�����) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         
     * <br>
     * messageResources.messageFormatClone��"dateFormatOnly"�̏ꍇ�A
     * MessageFormatCloneReturnIfUseDateFormatMap���ԋp����邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInstance03() throws Exception {
        // �O����
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // �e�X�g���{
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // ����
        assertEquals(MessageFormatCloneReturnIfUseDateFormatMap.class, map.getClass());
    }

    /**
     * testGetInstance04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(�O�����) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) null<br>
     *         
     * <br>
     * messageResources.messageFormatClone��"disable"�̏ꍇ�A
     * null���ԋp����邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInstance04() throws Exception {
        // �O����
        addProperty("messageResources.messageFormatClone", "disable");
        
        // �e�X�g���{
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // ����
        assertNull(map);
    }

    /**
     * testGetInstance05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(�O�����) messageResources.messageFormatClone:"xxx"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �x�����O�F<br>
     *                    "messageResources.messageFormatClone = xxx is invalid. set \"enable\", \"disable\" or \"dateFormatOnly\"."<br>
     *                    �x�����O�F<br>
     *                    "use MessageFormatCloneReturnAtDateFormatOnlyMap."<br>
     *         
     * <br>
     * messageResources.messageFormatClone��"dateFormatOnly"�̏ꍇ�A
     * MessageFormatCloneReturnIfUseDateFormatMap���ԋp����邱�Ƃ��m�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInstance05() throws Exception {
        // �O����
        addProperty("messageResources.messageFormatClone", "xxx");
        
        // �e�X�g���{
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // ����
        assertEquals(MessageFormatCloneReturnIfUseDateFormatMap.class, map.getClass());
        assertTrue(LogUTUtil.checkWarn("messageResources.messageFormatClone = xxx is invalid. set \"enable\", \"disable\" or \"dateFormatOnly\"."));
        assertTrue(LogUTUtil.checkWarn("use MessageFormatCloneReturnAtDateFormatOnlyMap."));
    }

}
