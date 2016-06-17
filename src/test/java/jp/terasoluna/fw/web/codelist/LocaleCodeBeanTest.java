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
 * {@link jp.terasoluna.fw.web.codelist.LocaleCodeBean} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �R�[�h�A�R�[�h�l(�\��������)�ƃ��P�[�����i����R�[�h�A���R�[�h�A�o���A���g�j
 * ��ێ�����Bean�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.CodeBean
 * 
 */
public class LocaleCodeBeanTest extends TestCase {
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(LocaleCodeBeanTest.class);
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
    public LocaleCodeBeanTest(String name) {
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
        LocaleCodeBean codeBean = new LocaleCodeBean();

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
        LocaleCodeBean codeBean = new LocaleCodeBean();
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
        LocaleCodeBean codeBean = new LocaleCodeBean();
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
        LocaleCodeBean codeBean = new LocaleCodeBean();
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
        LocaleCodeBean codeBean = new LocaleCodeBean();
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
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // �e�X�g���{�E����
        assertEquals("name", codeBean.getName());
    }

    /**
     * testSetLanguage01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) language:"language"<br>
     * (���) language:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) language:"language"<br>
     * 
     * <br>
     * �����Ɏw�肳�ꂽ�����񂪃C���X�^���X�ϐ�language��
     * �ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetLanguage01() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // �e�X�g���{
        codeBean.setLanguage("language");
        // ����
        assertEquals("language", UTUtil.getPrivateField(codeBean, "language"));
    }

    /**
     * testSetLanguage02() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) language:null<br>
     * (���) language:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) language:""<br>
     * 
     * <br>
     * ������language��null�̏ꍇ�A�󕶎����ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetLanguage02() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // �e�X�g���{
        codeBean.setLanguage(null);
        // ����
        assertEquals("", UTUtil.getPrivateField(codeBean, "language"));
    }

    /**
     * testGetLanguage01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) language:"language"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"language"<br>
     * 
     * <br>
     * �C���X�^���X�ϐ�language�̒l���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLanguage01() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "language", "language");
        
        // �e�X�g���{�E����
        assertEquals("language", codeBean.getLanguage());
    }
    
    /**
     * testSetCountry01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) country:"country"<br>
     * (���) country:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) country:"country"<br>
     * 
     * <br>
     * �����Ɏw�肳�ꂽ�����񂪃C���X�^���X�ϐ�country��
     * �ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCountry01() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // �e�X�g���{
        codeBean.setCountry("country");
        // ����
        assertEquals("country", UTUtil.getPrivateField(codeBean, "country"));
    }

    /**
     * testSetCountry02() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) country:null<br>
     * (���) country:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) country:""<br>
     * 
     * <br>
     * ������country��null�̏ꍇ�A�󕶎����ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCountry02() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // �e�X�g���{
        codeBean.setCountry(null);
        // ����
        assertEquals("", UTUtil.getPrivateField(codeBean, "country"));
    }

    /**
     * testGetCountry01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) country:"country"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"country"<br>
     * 
     * <br>
     * �C���X�^���X�ϐ�country�̒l���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCountry01() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "country", "country");
        
        // �e�X�g���{�E����
        assertEquals("country", codeBean.getCountry());
    }
    
    /**
     * testSetVariant01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) variant:"variant"<br>
     * (���) name:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) variant:"variant"<br>
     * 
     * <br>
     * �����Ɏw�肳�ꂽ�����񂪃C���X�^���X�ϐ�variant��
     * �ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetVariant01() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // �e�X�g���{
        codeBean.setVariant("variant");
        // ����
        assertEquals("variant", UTUtil.getPrivateField(codeBean, "variant"));
    }

    /**
     * testSetVariant02() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(����) variant:null<br>
     * (���) variant:null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) variant:""<br>
     * 
     * <br>
     * ������variant��null�̏ꍇ�A�󕶎����ݒ肳��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testSetVariant02() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // �e�X�g���{
        codeBean.setVariant(null);
        // ����
        assertEquals("", UTUtil.getPrivateField(codeBean, "variant"));
    }

    /**
     * testGetVariant01() <br>
     * <br>
     * 
     * (����n)<br>
     * �ϓ_�FA <br>
     * <br>
     * ���͒l�F(���) variant:"variant"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) String:"variant"<br>
     * 
     * <br>
     * �C���X�^���X�ϐ�variant�̒l���擾�ł��邱�Ƃ��m�F����B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGetVariant01() throws Exception {
        // �O����
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "variant", "variant");
        
        // �e�X�g���{�E����
        assertEquals("variant", codeBean.getVariant());
    }

}
