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
 * {@link jp.terasoluna.fw.web.struts.action.ExceptionConfigEx} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �V�X�e����O�̃}�b�s���O���N���X�B<br>
 * ExceptionConfig���p�����A���W���[�����̃t�B�[���h��ǉ�����B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.ExceptionConfigEx
 */
public class ExceptionConfigExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ExceptionConfigExTest.class);
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ExceptionConfigExTest(String name) {
        super(name);
    }

    /**
     * testGetModule01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) module:"test01"<br>
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
    public void testGetModule01() throws Exception {
        // �O����
        String expected = "test01";
        ExceptionConfigEx ec = new ExceptionConfigEx();
        UTUtil.setPrivateField(ec, "module", expected);

        // �e�X�g���{
        String actual = ec.getModule();

        // ����
        assertEquals(expected, actual);
    }

    /**
     * testSetModule01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) module:"test01"<br>
     *         (���) module:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) module:"test01"<br>
     *         
     * <br>
     * setter�̌Ăяo�����m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetModule01() throws Exception {
        // �O����
        String expected = "test01";
        ExceptionConfigEx ec = new ExceptionConfigEx();
        UTUtil.setPrivateField(ec, "module", null);

        // �e�X�g���{
        ec.setModule(expected);

        // ����
        assertEquals(expected, UTUtil.getPrivateField(ec, "module"));
    }

}
