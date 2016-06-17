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
 * {@link jp.terasoluna.fw.service.thin.BLogicProperty} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N���o�͏��̌X�̃v���p�e�B����ێ�����N���X�B<br>
 * BLogicIOPlugIn�Ńr�W�l�X���W�b�N���o�͏���`�t�@�C���ł���blogic-io.xml����A
 * �r�W�l�X���W�b�N�ɑ΂������JavaBean�y�яo��JavaBean�̃v���p�e�B�ݒ��ێ�����B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 */
public class BLogicPropertyTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicPropertyTest.class);
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
    public BLogicPropertyTest(String name) {
        super(name);
    }

    /**
     * testGetProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) property:"property"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"property"<br>
     *         
     * <br>
     * BLogicProperty�Ɋi�[����Ă���property�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetProperty01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //property�ݒ�
        UTUtil.setPrivateField(blogicProperty, "property", "property");

        //�e�X�g���s�E���ʊm�F
        assertEquals("property", blogicProperty.getProperty());
    }

    /**
     * testSetProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"property"<br>
     *         (���) property:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) property:"property"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicProperty��property�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetProperty01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //�e�X�g���s
        blogicProperty.setProperty("property");

        //�e�X�g���ʊm�F
        assertEquals("property", UTUtil.getPrivateField(blogicProperty,
                "property"));
    }

    /**
     * testGetBLogicProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) blogicProperty:"blogicProperty"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"blogicProperty"<br>
     *         
     * <br>
     * BLogicProperty�Ɋi�[����Ă���blogicProperty�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicProperty01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //blogicProperty�ݒ�
        UTUtil.setPrivateField(blogicProperty, "blogicProperty",
                "blogicProperty");

        //�e�X�g���s�E���ʊm�F
        assertEquals("blogicProperty", blogicProperty.getBLogicProperty());
    }

    /**
     * testSetBLogicProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) blogicProperty:"blogicProperty"<br>
     *         (���) blogicProperty:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) blogicProperty:"blogicProperty"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicProperty��blogicProperty�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicProperty01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //�e�X�g���s
        blogicProperty.setBLogicProperty("blogicProperty");

        //�e�X�g���ʊm�F
        assertEquals(
            "blogicProperty",
            UTUtil.getPrivateField(blogicProperty, "blogicProperty"));
    }

    /**
     * testGetSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) sourceOrDest:"source"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"source"<br>
     *         
     * <br>
     * BLogicProperty�Ɋi�[����Ă���sourceOrDest�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetSource01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //sourceOrDest�ݒ�
        UTUtil.setPrivateField(blogicProperty, "sourceOrDest", "source");

        //�e�X�g���s�E���ʊm�F
        assertEquals("source", blogicProperty.getSource());
    }

    /**
     * testSetSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) source:"source"<br>
     *         (���) sourceOrDest:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) sourceOrDest:"source"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicProperty��sourceOrDest�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSource01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //�e�X�g���s
        blogicProperty.setSource("source");

        //�e�X�g���ʊm�F
        assertEquals("source", UTUtil.getPrivateField(blogicProperty,
                "sourceOrDest"));
    }

    /**
     * testGetDest01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) sourceOrDest:"dest"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"dest"<br>
     *         
     * <br>
     * BLogicProperty�Ɋi�[����Ă���sourceOrDest�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDest01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //sourceOrDest�ݒ�
        UTUtil.setPrivateField(blogicProperty, "sourceOrDest", "dest");

        //�e�X�g���s�E���ʊm�F
        assertEquals("dest", blogicProperty.getDest());
    }

    /**
     * testSetDest01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) dest:"dest"<br>
     *         (���) sourceOrDest:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) sourceOrDest:"dest"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicProperty��sourceOrDest�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDest01() throws Exception {
        //�r�W�l�X���W�b�N�v���p�e�B���
        BLogicProperty blogicProperty = new BLogicProperty();

        //�e�X�g���s
        blogicProperty.setDest("dest");

        //�e�X�g���ʊm�F
        assertEquals("dest", UTUtil.getPrivateField(blogicProperty,
                "sourceOrDest"));
    }

}
