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

package jp.terasoluna.fw.web.codelist;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.CodeBean} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �R�[�h�ƃR�[�h�l(�\��������)��ێ�����Bean�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.CodeBean
 * 
 */
public class CodeBeanTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(CodeBeanTest.class);
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
    public CodeBeanTest(String name) {
        super(name);
    }

    /**
     * testSetId01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) id:"id"<br>
     * (���) id:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) id:"id"<br>
     * 
     * <br>
     * �����Ɏw�肳�ꂽ�����񂪃C���X�^���X�ϐ�id�ɐݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetId01() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();

        // �e�X�g���{
        codeBean.setId("id");
        // ����
        assertEquals("id", UTUtil.getPrivateField(codeBean, "id"));
    }

    /**
     * testSetId02() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) id:null<br>
     * (���) id:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) id:""<br>
     * 
     * <br>
     * ������id��null�̏ꍇ�A�󕶎����ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetId02() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        // �e�X�g���{
        codeBean.setId(null);
        // ����
        assertEquals("", UTUtil.getPrivateField(codeBean, "id"));
    }

    /**
     * testGetId01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) id:"id"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"id"<br>
     * 
     * <br>
     * �C���X�^���X�ϐ�id�̒l���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetId01() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "id");
        
        // �e�X�g���{�E����
        assertEquals("id", codeBean.getId());
    }

    /**
     * testSetName01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) name:"name"<br>
     * (���) name:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) name:"name"<br>
     * 
     * <br>
     * �����Ɏw�肳�ꂽ�����񂪃C���X�^���X�ϐ�name�ɐݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName01() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        // �e�X�g���{
        codeBean.setName("name");
        // ����
        assertEquals("name", UTUtil.getPrivateField(codeBean, "name"));
    }

    /**
     * testSetName02() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) name:null<br>
     * (���) name:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) name:""<br>
     * 
     * <br>
     * ������name��null�̏ꍇ�A�󕶎����ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName02() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        // �e�X�g���{
        codeBean.setName(null);
        // ����
        assertEquals("", UTUtil.getPrivateField(codeBean, "name"));
    }

    /**
     * testGetName01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) name:"name"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"name"<br>
     * 
     * <br>
     * �C���X�^���X�ϐ�name�̒l���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetName01() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // �e�X�g���{�E����
        assertEquals("name", codeBean.getName());
    }

    /**
     * testGetLabel01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) id:null<br>
     * (���) name:null<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"null null"<br>
     * 
     * <br>
     * �ϐ�id��name��null�̏ꍇ�A�unull null�v���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLabel01() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", null);
        UTUtil.setPrivateField(codeBean, "name", null);
        
        // �e�X�g���{�E����
        assertEquals("null null", codeBean.getLabel());
    }

    /**
     * testGetLabel02() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) id:""<br>
     * (���) name:""<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:" "<br>
     * 
     * <br>
     * �ϐ�id��name���󕶎��̏ꍇ�A�u �v�i���p�X�y�[�X�j���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLabel02() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "");
        UTUtil.setPrivateField(codeBean, "name", "");
        
        // �e�X�g���{�E����
        assertEquals(" ", codeBean.getLabel());
    }

    /**
     * testGetLabel03() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) id:"id"<br>
     * (���) name:"name"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"id name"<br>
     * 
     * <br>
     * �ϐ�id��name�ɕ����񂪐ݒ肳��Ă���ꍇ�A
     * id�̕������name�̕�����𔼊p�X�y�[�X�ŘA�����������񂪎擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLabel03() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "id");
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // �e�X�g���{�E����
        assertEquals("id name", codeBean.getLabel());
    }

    /**
     * testGetCodeCommaName01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) id:null<br>
     * (���) name:null<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"null,null"<br>
     * 
     * <br>
     * �ϐ�id��name��null�̏ꍇ�A�unull,null�v���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeCommaName01() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", null);
        UTUtil.setPrivateField(codeBean, "name", null);
        
        // �e�X�g���{�E����
        assertEquals("null,null", codeBean.getCodeCommaName());
    }

    /**
     * testGetCodeCommaName02() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) id:""<br>
     * (���) name:""<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:","<br>
     * 
     * <br>
     * �ϐ�id��name���󕶎��̏ꍇ�A�u,�v���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeCommaName02() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "");
        UTUtil.setPrivateField(codeBean, "name", "");
        
        // �e�X�g���{�E����
        assertEquals(",", codeBean.getCodeCommaName());

    }

    /**
     * testGetCodeCommaName03() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) id:"id"<br>
     * (���) name:"name"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"id,name"<br>
     * 
     * <br>
     * �ϐ�id��name�ɕ����񂪐ݒ肳��Ă���ꍇ�A
     * id�̕������name�̕�������J���}�ŘA�����������񂪎擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeCommaName03() throws Exception {
        // �O����
        CodeBean codeBean = new CodeBean();
        UTUtil.setPrivateField(codeBean, "id", "id");
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // �e�X�g���{�E����
        assertEquals("id,name", codeBean.getCodeCommaName());
    }

}
