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

package jp.terasoluna.fw.web.struts.reset;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.FieldReset} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �t�B�[���h�̃��Z�b�g����ێ����� Bean �N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 */
public class FieldResetTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldResetTest.class);
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
    public FieldResetTest(String name) {
        super(name);
    }

    /**
     * testGetFieldName01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) fieldName:"field4"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"field4"<br>
     *
     * <br>
     * �C���X�^���X�ϐ�fieldName�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetFieldName01() throws Exception {
        //�����ݒ�
        FieldReset fr = new FieldReset();
        UTUtil.setPrivateField(fr, "fieldName", "field4");
        //�e�X�g���s
        //���ʊm�F
        assertEquals("field4", fr.getFieldName());
    }

    /**
     * testSetFieldName01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) fieldName:"field5"<br>
     *         (���) fieldName:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) fieldName:"field5"<br>
     *
     * <br>
     * �����̒l���C���X�^���X�ϐ�fieldName�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFieldName01() throws Exception {
        //�����ݒ�
        FieldReset fr = new FieldReset();
        //�e�X�g���s
        fr.setFieldName("field5");
        //���ʊm�F
        assertEquals("field5", UTUtil.getPrivateField(fr, "fieldName"));
    }

    /**
     * testIsSelect01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) select:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * �C���X�^���X�ϐ�select�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsSelect01() throws Exception {
        //�����ݒ�
        FieldReset fr = new FieldReset();
        Boolean select = new Boolean(true);
        UTUtil.setPrivateField(fr, "select", select);
        //�e�X�g���s
        assertTrue(fr.isSelect());
    }

    /**
     * testSetSelect01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) select:true<br>
     *         (���) select:false<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) select:true<br>
     *
     * <br>
     * �����̒l���C���X�^���X�ϐ�select�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSelect01() throws Exception {
        //�����ݒ�
        FieldReset fr = new FieldReset();
        //�e�X�g���s
        fr.setSelect(true);
        //���ʊm�F
        Boolean result = (Boolean) UTUtil.getPrivateField(fr, "select");
        assertTrue(result.booleanValue());
    }

}
