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
 * {@link jp.terasoluna.fw.service.thin.BLogicResult} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N�o�͏��N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 */
public class BLogicResultTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicResultTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public BLogicResultTest(String name) {
        super(name);
    }

    /**
     * testGetResultObject01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) resultObject:"resultObject"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"resultObject"<br>
     *         
     * <br>
     * BLogicResult�Ɋi�[����Ă���resultObject�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResultObject01() throws Exception {
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        String str = "resultObject";

        // resultObject�ݒ�
        UTUtil.setPrivateField(blogicResult, "resultObject", str);

        // �e�X�g���{�E����
        assertEquals("resultObject", blogicResult.getResultObject());
    }

    /**
     * testSetResultObject01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) resultObject:"resultObject"<br>
     *         (���) resultObject:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) resultObject:"resultObject"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicResult��resultObject�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetResultObject01() throws Exception {
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        String str = "resultObject";

        // �e�X�g���{
        blogicResult.setResultObject(str);

        // ����
        assertEquals("resultObject", UTUtil.getPrivateField(blogicResult,
                "resultObject"));
    }

    /**
     * testGetResultString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) resultString:"resultString"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"resultString"<br>
     *         
     * <br>
     * BLogicResult�Ɋi�[����Ă���resultString�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResultString01() throws Exception {
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        String str = "resultString";

        // resultString�ݒ�
        UTUtil.setPrivateField(blogicResult, "resultString", str);

        // �e�X�g���{�E����
        assertEquals("resultString", blogicResult.getResultString());
    }

    /**
     * testSetResultString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) resultString:"resultString"<br>
     *         (���) resultString:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) resultString:"resultString"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicResult��resultString�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetResultString01() throws Exception {
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        String str = "resultString";

        // �e�X�g���{
        blogicResult.setResultString(str);

        // ����
        assertEquals("resultString", UTUtil.getPrivateField(blogicResult,
                "resultString"));

    }

    /**
     * testGetErrors01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) errors:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicMessages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * BLogicResult�Ɋi�[����Ă���errors�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrors01() throws Exception {
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        // BLogicMessage����
        BLogicMessage error = new BLogicMessage("key", "value");
        
        // BLogicMessages����
        BLogicMessages errors = new BLogicMessages();
        errors.add("property", error);
        
        // messages�ݒ�
        UTUtil.setPrivateField(blogicResult, "errors", errors);

        // �e�X�g���{�E����
        assertSame(errors, blogicResult.getErrors());
    }

    /**
     * testSetErrors01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) paramErrors:("property",BLogicMessage("key","value"))<br>
     *         (���) paramErrors:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) errors:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicResult��errors�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetErrors01() throws Exception {
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        // BLogicMessage����
        BLogicMessage error = new BLogicMessage("key", "value");
        
        // BLogicMessages����
        BLogicMessages errors = new BLogicMessages();
        errors.add("property", error);

        // �e�X�g���{
        blogicResult.setErrors(errors);

        // ����
        assertSame(errors, UTUtil.getPrivateField(blogicResult, "errors"));

    }

    /**
     * testGetMessages01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) messages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicMessages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * BLogicResult�Ɋi�[����Ă���messages�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessages01() throws Exception {
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        // BLogicMessage����
        BLogicMessage message = new BLogicMessage("key", "value");
        
        // BLogicMessages����
        BLogicMessages messages = new BLogicMessages();
        messages.add("property", message);

        // messages�ݒ�
        UTUtil.setPrivateField(blogicResult, "messages", messages);

        // �e�X�g���{�E����
        assertSame(messages, blogicResult.getMessages());
    }

    /**
     * testSetMessages01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) paramMessages:("property",BLogicMessage("key","value"))<br>
     *         (���) paramMessages:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) messages:("property",BLogicMessage("key","value"))<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicResult��messages�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetMessages01() throws Exception {
        // �O����
        // BLogic�o�͏��
        BLogicResult blogicResult = new BLogicResult();

        // �e�X�g�l�ݒ�
        // BLogicMessage����
        BLogicMessage message = new BLogicMessage("key", "value");
        
        // BLogicMessages����
        BLogicMessages messages = new BLogicMessages();
        messages.add("property", message);

        // �e�X�g���{
        blogicResult.setMessages(messages);

        // ����
        assertSame(messages, UTUtil.getPrivateField(blogicResult, "messages"));
    }

}
