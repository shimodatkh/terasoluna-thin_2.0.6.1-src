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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.ActionReset} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �A�N�V�����p�X�P�ʂ̃��Z�b�g�p�̐ݒ����ێ����� Bean �N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 */
public class ActionResetTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ActionResetTest.class);
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
    public ActionResetTest(String name) {
        super(name);
    }

    /**
     * testSetFieldReset01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) fieldReset:name="name"<br>
     *         (���) fieldResets:{}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) fieldResets:{"name"=FieldReset(name="name")}<br>
     *
     * <br>
     * ������FieldReset�C���X�^���X��name�������L�[�A�C���X�^���X��l�Ƃ��āAfieldResets�ɒǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFieldReset01() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();
        fr.setFieldName("name");

        //�e�X�g���s
        ar.setFieldReset(fr);
        //���ʊm�F
        Map result = (HashMap) UTUtil.getPrivateField(ar, "fieldResets");
        assertTrue(result.containsKey("name"));
        assertTrue(result.containsValue(fr));
    }

    /**
     * testGetPath01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) path:"path"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"path"<br>
     *
     * <br>
     * �C���X�^���X�ϐ�path�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPath01() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();
        UTUtil.setPrivateField(ar, "path", "path");

        //�e�X�g���s
        //���ʊm�F
        assertEquals("path", ar.getPath());
    }

    /**
     * testSetPath01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) path:"path1"<br>
     *         (���) path:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) path:"path1"<br>
     *
     * <br>
     * �����̒l���C���X�^���X�ϐ�path�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPath01() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();
        //�e�X�g���s
        ar.setPath("path1");
        //���ʊm�F
        assertEquals("path1", UTUtil.getPrivateField(ar, "path"));
    }

    /**
     * testGetFieldNames01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) fieldResets:{"key1"=FieldReset,<br>
     *                 "key2"=FieldReset,<br>
     *                 "key3"=FieldReset}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Iterator:{"key1","key2","key3"}(���s��)<br>
     *
     * <br>
     * fieldResets�ɕ����̗v�f�����݂���ꍇ�A�S�ẴL�[�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetFieldNames01() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();

        Map<String, FieldReset> map = new TreeMap<String, FieldReset>();
        map.put("key1", fr);
        map.put("key2", fr);
        map.put("key3", fr);

        UTUtil.setPrivateField(ar, "fieldResets", map);

        //�e�X�g���s
        Iterator result = ar.getFieldNames();

        //���ʊm�F
        assertEquals("key1", (String) result.next());
        assertEquals("key2", (String) result.next());
        assertEquals("key3", (String) result.next());
        assertFalse(result.hasNext());
    }

    /**
     * testGetFieldNames02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) fieldResets:{"key1"=FieldReset}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Iterator:{"key1"}<br>
     *
     * <br>
     * fieldResets��1���̗v�f�����݂���ꍇ�A���̃L�[�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetFieldNames02() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();

        Map<String, FieldReset> map = new HashMap<String, FieldReset>();
        map.put("key1", fr);

        UTUtil.setPrivateField(ar, "fieldResets", map);

        //�e�X�g���s
        Iterator result = ar.getFieldNames();

        //���ʊm�F
        assertEquals("key1", (String) result.next());
        assertFalse(result.hasNext());
    }

    /**
     * testGetFieldNames03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) fieldResets:{}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Iterator:{}<br>
     *
     * <br>
     * fieldResets�ɗv�f�����݂��Ȃ��ꍇ�A���Iterator���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetFieldNames03() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();

        Map map = new HashMap();

        UTUtil.setPrivateField(ar, "fieldResets", map);
        //�e�X�g���s
        Iterator result = ar.getFieldNames();
        //���ʊm�F
        assertFalse(result.hasNext());
    }

    /**
     * testIsSelectedField01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) fieldName:"aaa"<br>
     *         (���) fieldResets:{"name"=FieldReset(select = true)}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ������fieldName�Ɏw�肵�����O��FieldReset�����݂��Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsSelectedField01() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();
        fr.setFieldName("name");
        fr.setSelect(true);
        Map<String, FieldReset> map = new HashMap<String, FieldReset>();
        map.put("name", fr);
        UTUtil.setPrivateField(ar, "fieldResets", map);

        //�e�X�g���s
        //���ʊm�F
        assertFalse(ar.isSelectField("aaa"));
    }

    /**
     * testIsSelectedField02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) fieldName:"name"<br>
     *         (���) fieldResets:{"name"=FieldReset(select = true)}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ������fieldName�Ɏw�肵�����O��FieldReset�����݂���ꍇ�A����FieldReset�̃C���X�^���X�ϐ�select�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsSelectedField02() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();
        FieldReset fr = new FieldReset();
        fr.setSelect(true);

        Map<String, FieldReset> map = new HashMap<String, FieldReset>();
        map.put("name", fr);

        UTUtil.setPrivateField(ar, "fieldResets", map);

        //�e�X�g���s
        //���ʊm�F
        assertTrue(ar.isSelectField("name"));
    }

    /**
     * testIsSelectedField03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:null<br>
     *         (���) fieldResets:{}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ������fieldName�Ɏw�肵���l��null�̏ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsSelectedField03() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();

        //�e�X�g���s
        //���ʊm�F
        assertFalse(ar.isSelectField(null));
    }

    /**
     * testIsSelectedField04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:""<br>
     *         (���) fieldResets:{}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ������fieldName�Ɏw�肵���l���󕶎��̏ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsSelectedField04() throws Exception {
        //�����ݒ�
        ActionReset ar = new ActionReset();

        //�e�X�g���s
        //���ʊm�F
        assertFalse(ar.isSelectField(""));
    }

}
