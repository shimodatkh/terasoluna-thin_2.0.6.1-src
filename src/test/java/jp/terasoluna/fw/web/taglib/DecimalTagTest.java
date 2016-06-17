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

package jp.terasoluna.fw.web.taglib;

import java.io.BufferedReader;
import java.math.BigDecimal;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_PageContextImpl;
import junit.framework.TestCase;

/**
 * DecimalTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * 
 */
public class DecimalTagTest extends TestCase {

    //�e�X�g�ΏۃN���X����
    DecimalTag tag = null;

    /**
     * Constructor for IterateRowSetTeiTest.
     * @param arg0
     */
    public DecimalTagTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (DecimalTag) TagUTUtil.create(DecimalTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGetId01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="id"<br>
     * 
     * ���Ғl
     * �߂�l:String="id"<br>
     * 
     * "id"���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetId01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "id";
        UTUtil.setPrivateField(tag, "id", value);

        //�e�X�g���s
        String result = tag.getId();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    }

    /**
     * testSetId01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"id"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetId01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "id";

        //�e�X�g���s
        tag.setId(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "id");
        assertEquals(value, result);
    }

    /**
     * testGetFilter01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value=false<br>
     * 
     * ���Ғl
     * �߂�l:boolean=false<br>
     * 
     * <code>false</code> ���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testGetFilter01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        Boolean value = new Boolean(false);
        UTUtil.setPrivateField(tag, "filter", value);

        //�e�X�g���s
        boolean result = tag.getFilter();

        //�e�X�g���ʊm�F
        assertFalse(result);

    }

    /**
     * testSetFilter01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����Ffalse
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetFilter01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        boolean value = false;

        //�e�X�g���s
        tag.setFilter(value);

        //���ʊm�F
        Boolean result = (Boolean) UTUtil.getPrivateField(tag, "filter");
        assertFalse(result.booleanValue());
    }

    /**
     * testGetIgnore01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value=true<br>
     * 
     * ���Ғl
     * �߂�l:boolean=true<br>
     * 
     * <code>true</code> ���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testGetIgnore01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        Boolean value = new Boolean(true);
        UTUtil.setPrivateField(tag, "ignore", value);

        //�e�X�g���s
        boolean result = tag.getIgnore();

        //�e�X�g���ʊm�F
        assertTrue(result);

    }

    /**
     * testSetIgnore01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����Ftrue
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetIgnore01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        boolean value = true;

        //�e�X�g���s
        tag.setIgnore(value);

        //���ʊm�F
        Boolean result = (Boolean) UTUtil.getPrivateField(tag, "ignore");
        assertTrue(result.booleanValue());
    }

    /**
     * testGetName01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="name"<br>
     * 
     * ���Ғl
     * �߂�l:String="name"<br>
     * 
     * "name"���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetName01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "name";
        UTUtil.setPrivateField(tag, "name", value);

        //�e�X�g���s
        String result = tag.getName();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    }

    /**
     * testSetName01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"name"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetName01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "name";

        //�e�X�g���s
        tag.setName(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "name");
        assertEquals(value, result);
    }

    /**
     * testGetProperty01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="property"<br>
     * 
     * ���Ғl
     * �߂�l:String="property"<br>
     * 
     * "property"���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetProperty01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "property";
        UTUtil.setPrivateField(tag, "property", value);

        //�e�X�g���s
        String result = tag.getProperty();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    }

    /**
     * testSetProperty01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"property"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetProperty01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "property";

        //�e�X�g���s
        tag.setProperty(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "property");
        assertEquals(value, result);
    }

    /**
     * testGetScope01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="scope"<br>
     * 
     * ���Ғl
     * �߂�l:String="scope"<br>
     * 
     * "scope"���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetScope01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "scope";
        UTUtil.setPrivateField(tag, "scope", value);

        //�e�X�g���s
        String result = tag.getScope();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    }

    /**
     * testSetScope01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"scope"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetScope01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "scope";

        //�e�X�g���s
        tag.setScope(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "scope");
        assertEquals(value, result);
    }

    /**
     * testGetPattern01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="pattern"<br>
     * 
     * ���Ғl
     * �߂�l:String="pattern"<br>
     * 
     * "pattern"���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetPattern01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "pattern";
        UTUtil.setPrivateField(tag, "pattern", value);

        //�e�X�g���s
        String result = tag.getPattern();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    }

    /**
     * testSetPattern01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"pattern"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetPattern01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "pattern";

        //�e�X�g���s
        tag.setPattern(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "pattern");
        assertEquals(value, result);
    }

    /**
     * testGetValue01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="value"<br>
     * 
     * ���Ғl
     * �߂�l:String="value"<br>
     * 
     * "value"���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetValue01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "value";
        UTUtil.setPrivateField(tag, "value", value);

        //�e�X�g���s
        String result = tag.getValue();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    }

    /**
     * testSetValueString01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"value"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetValueString01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "value";

        //�e�X�g���s
        tag.setValue(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "value");
        assertEquals(value, result);
    }

    /**
     * testGetScale01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value=1<br>
     * 
     * ���Ғl
     * �߂�l:int=1<br>
     * 
     * 1���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetScale01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        Integer value = new Integer(1);
        UTUtil.setPrivateField(tag, "scale", value);

        //�e�X�g���s
        int result = tag.getScale();

        //�e�X�g���ʊm�F
        assertEquals(value.intValue(), result);

    }

    /**
     * testSetScale01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F1
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetScale01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        int value = 1;

        //�e�X�g���s
        tag.setScale(value);

        //���ʊm�F
        Integer result = (Integer) UTUtil.getPrivateField(tag, "scale");
        assertEquals(value, result.intValue());
    }

    /**
     * testGetRound01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * value="ROUND_HALF_UP"<br>
     * 
     * ���Ғl
     * �߂�l:"ROUND_HALF_UP"<br>
     * 
     * "ROUND_HALF_UP"���擾�o���邱�Ƃ��m�F����B<br>
     */
    public void testGetRound01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "ROUND_HALF_UP";
        UTUtil.setPrivateField(tag, "round", value);

        //�e�X�g���s
        String result = tag.getRound();

        //�e�X�g���ʊm�F
        assertEquals(value, result);

    }

    /**
     * testSetRound01()�B<br>
     *
     * (����n)<br>
     * �ϓ_�FA<br>
     *
     * ���͒l�F
     *   �����F"ROUND_HALF_UP"
     * 
     * ���Ғl�F�����ɂĐݒ肵���l
     *
     * �����Őݒ肵���l���ݒ肳��Ă��邱�Ƃ��m�F����B
     *
     * @throws Exception �e�X�g�R�[�h�̖{���Ƃ������̖�����O
     */
    public void testSetRound01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String value = "ROUND_HALF_UP";

        //�e�X�g���s
        tag.setRound(value);

        //���ʊm�F
        String result = (String) UTUtil.getPrivateField(tag, "round");
        assertEquals(value, result);
    }

    /**
     * testDoStartTag01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=null<br>
     * ignore=true<br>
     * RequestUtils.lookup(pageContext, name, scope)=null<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��null�Abeen�͑��݂��Ȃ��ꍇ�A
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, null);
        // �N���X�ϐ��ݒ�
        String value = null;
        UTUtil.setPrivateField(tag, "value", value);
        Boolean ignore = new Boolean(true);
        UTUtil.setPrivateField(tag, "ignore", ignore);
        UTUtil.setPrivateField(tag, "name", name);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=null<br>
     * ignore=false<br>
     * RequestUtils.lookup(pageContext, name, property, scope)=not null
     * [String�i"1.1 "�j]<br>
     * scale=0<br>
     * id=not null<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=1<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��null�Abeen�͑��݂��A
     * �v�������v���p�e�B�̒l�����݂���ꍇ�A
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1 ");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", name);
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1", id);

    }

    /**
     * testDoStartTag03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=null<br>
     * ignore=true<br>
     * RequestUtils.lookup(pageContext, name, scope)=not null<br>
     * RequestUtils.lookup(pageContext, name, property, scope)=null<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��null�Abeen�͑��݂��A
     * �v�������v���p�e�B�̒l�����݂��Ȃ��ꍇ�A
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        //DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1(null);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", name);
        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.15"�j]<br>
     * RequestUtils.lookup(pageContext, name, scope)=not null<br>
     * RequestUtils.lookup(pageContext, name, property, scope)=not null
     * [String�i"1.00"�j]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=id�L�[�Ƃ����l�����݂���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.15"�j�j�A
     * �����_�ȉ����w�肳��Aid���w�肳��Ă����ꍇ�APageContext��
     * �ݒ肳��SKIP_BODY���Ԃ邱�Ƃ��m�F����B
     * �܂��Abean����value��D�悷�邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.00");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.15");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1.2", id);

    }

    /**
     * testDoStartTag05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.15"�j]<br>
     * RequestUtils.lookup(pageContext, name, scope)=null<br>
     * scale=-1<br>
     * id=not null<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=id�L�[�Ƃ����l�����݂���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.15"�j�j�A
     * �����_�ȉ����w�肳��Aid���w�肳��Ă����ꍇ�APageContext��
     * �ݒ肳��SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag05() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);

        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.15");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(-1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1.15", id);

    }

    /**
     * testDoStartTag06�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.1 "�j]<br>
     * scale=-1<br>
     * id=null<br>
     * filter=true<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=���X�|���X��output���t�B���^����A�������܂�Ă���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.1 "�j�j�A
     * �����_�ȉ����w�肳��Aid���w�肳��Ă��Ȃ��A
     * filter��true�̏ꍇ�A���X�|���X�փt�B���^�����l���ݒ肳��
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag06() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1 ");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.1 ");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(-1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.1", reader.readLine());

    }

    /**
     * testDoStartTag07�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.1 "�j]<br>
     * scale=-1<br>
     * id=null<br>
     * filter=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=���X�|���X��output���������܂�Ă���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.1 "�j�j�A
     * �����_�ȉ����w�肳��Aid���w�肳��Afilter��false�̏ꍇ�A
     * ���X�|���X�֒l���ݒ肳��SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag07() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1 ");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.1 ");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(-1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.1", reader.readLine());

    }

    /**
     * testDoStartTag08�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i" "�j]<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i" "�j�j��
     * �ꍇ�ASKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", "");

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag09�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * value=not null[String�i"aa "�j]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * ���Ғl
     * �߂�l:int=Exception<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"aa "�j�j��
     * �ꍇ�ANumberFormatException���������邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "value", "aa");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        //�e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (NumberFormatException e) {
            //�e�X�g���ʊm�F
            return;
        }
    }

    /**
     * testDoStartTag10�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[BigDecimal]<br>
     * scale=*(0)<br>
     * id=*(not null)<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=id�L�[�Ƃ����l�����݂���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iBigDecimal�j�̏ꍇ�A
     * �����_�ȉ����w�肳��Aid���w�肳��Ă��Ȃ��ꍇ�A
     * ���X�|���X�փt�B���^�����l���ݒ肳��
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        BigDecimal value = new BigDecimal("1.01");
        bean.setField2(value);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", name);
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        String property = "field2";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        // PageControlInfo
        String id = (String) pc.getAttribute("id1");
        assertEquals("1", id);

    }

    /**
     * testDoStartTag11�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[int(String�ABigDecimal�ȊO)]<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iint�iString�ABigDecimal�ȊO�j�j�̏ꍇ�ASKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag11() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        
        Integer value = new Integer("1");
        bean.setField3(value);

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", null);
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", name);
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        String property = "field3";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

    }

    /**
     * testDoStartTag12�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.1"�j]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     *
     * pageContext#setAttribute()��NullPointerException�����������ꍇ�A
     * ���ꂪ�X���[����邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag12() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        // �e�X�g�p�y�[�W�R���e�L�X�g�̐���
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());

        pc2.setNullPointerEx();
        pc2.setTiming(1);
        // �ݒ���s�����e�X�g�p�y�[�W�R���e�L�X�g���e�X�g�Ώۃ^�O�ɃZ�b�g����B
        tag.setPageContext(pc2);

        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.1");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            return;
        }

    }

    /**
     * testDoStartTag13�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.1"�j]<br>
     * scale=1<br>
     * id=not null<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     *
     * pageContext#setAttribute()��IllegalArgumentException�����������ꍇ�A
     * ���ꂪ�X���[����邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag13() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.1");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        // �e�X�g�p�y�[�W�R���e�L�X�g�̐���
        Exception_PageContextImpl pc2 =
            new Exception_PageContextImpl(
                pc.getServletConfig(),
                pc.getRequest(),
                pc.getResponse());

        pc2.setIllegalArgumentEx();
        pc2.setTiming(1);
        // �ݒ���s�����e�X�g�p�y�[�W�R���e�L�X�g���e�X�g�Ώۃ^�O�ɃZ�b�g����B
        tag.setPageContext(pc2);

        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.1");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            return;
        }

    }

    /**
     * testDoStartTag14�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.25"�j]<br>
     * filter=false
     * pattern=#.###
     * id=null
     * scale=1
     * round=null
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=���X�|���X��output���������܂�Ă���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.25"�j�j�A
     * scale������1�Around�������ݒ肳��Ă��Ȃ��ꍇ�A
     * ���X�|���X�֎w�肵�������_���l�̌ܓ������l���ݒ肳��A
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag14() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.25");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.25");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.3", reader.readLine());

    }
    
    /**
     * testDoStartTag15�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.23"�j]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round="rounding test"
     * 
     * ���Ғl
     * �߂�l:
     * IllegalArgumentException
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.23"�j�j�A
     * scale������1�Around�����Ɋۂ߃��[�h�ȊO���ݒ肳��Ă���ꍇ�A
     * IllegalArgumentException���������邱�Ƃ��m�F����B
     * �m�F����B<br>
     */
    public void testDoStartTag15() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.23");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.23");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "rounding test");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        try {
            tag.doStartTag();
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(LogUTUtil.checkError("Please set a rounding mode"));
            assertEquals("Please set a rounding mode", e.getMessage());
        }
    }
    
    /**
     * testDoStartTag16�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.29"�j]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round="ROUND_FLOOR"
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=���X�|���X��output���������܂�Ă���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.29"�j�j�A
     * scale������1�Around������"ROUND_FLOOR"���ݒ肳��Ă���ꍇ�A
     * ���X�|���X�֎w�肵�������_���؂�̂Ă��ꂽ�l���ݒ肳��A
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag16() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.29");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.29");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "round_floor");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.2", reader.readLine());
    }
    
    /**
     * testDoStartTag17�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.21"�j]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round=ROUND_CEILING
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=���X�|���X��output���������܂�Ă���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.21"�j�j�A
     * scale������1�Around������"ROUND_CEILING"���ݒ肳��Ă���ꍇ�A
     * ���X�|���X�֎w�肵�������_���؂�グ���ꂽ�l���ݒ肳��A
     * SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag17() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.21");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.21");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "round_ceiling");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.3", reader.readLine());
    }
    
    /**
     * testDoStartTag18�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA�AF<br>
     * 
     * ���͒l
     * value=not null[String�i"1.24"�j]<br>
     * filter=false
     * pattern=##.##
     * id=null
     * scale=1
     * round="ROUND_HALF_UP"
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * pageContext=���X�|���X��output���������܂�Ă���<br>
     * 
     * �t�H�[�}�b�g�Ώۂ̒l(value)��not null�iString�i"1.24"�j�j�A
     * scale������1�Around������"ROUND_HALF_UP"���ݒ肳��Ă���ꍇ�A
     * ���X�|���X�֎w�肵�������_���l�̌ܓ������l��
     * �ݒ肳��SKIP_BODY���Ԃ邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag18() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // DecimalTag_BeanStub01�C���X�^���X�̐���
        DecimalTag_BeanStub01 bean = new DecimalTag_BeanStub01();
        bean.setField1("1.24");

        // PageContext
        PageContext pc = TagUTUtil.getPageContext(tag);
        String name = "aa";
        pc.setAttribute(name, bean);
        // �N���X�ϐ��ݒ�
        UTUtil.setPrivateField(tag, "value", "1.24");

        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "id", null);
        UTUtil.setPrivateField(tag, "scale", new Integer(1));
        UTUtil.setPrivateField(tag, "round", "round_half_up");

        String property = "field1";
        UTUtil.setPrivateField(tag, "property", property);

        //�e�X�g���s
        int result = tag.doStartTag();

        //�e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);

        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1.2", reader.readLine());
    }
    
    /**
     * testRelease01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * id=*<br>
     * filter=*<br>
     * ignore=*<br>
     * name=*<br>
     * property=*<br>
     * scope=*<br>
     * pattern=*<br>
     * value=*<br>
     * scale=*<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * id=null<br>
     * filter=true<br>
     * ignore=false<br>
     * name=null<br>
     * property=null<br>
     * scope=null<br>
     * pattern=null<br>
     * value=null<br>
     * scale=-1<br>
     * 
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l������������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "id", "id1");
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", "aa");
        UTUtil.setPrivateField(tag, "property", "field1");
        UTUtil.setPrivateField(tag, "scope", "scope");
        UTUtil.setPrivateField(tag, "pattern", "##.##");
        UTUtil.setPrivateField(tag, "value", "aa");
        UTUtil.setPrivateField(tag, "scale", new Integer(0));

        //�e�X�g���s
        tag.release();

        //�e�X�g���ʊm�F
        String id2 = (String) UTUtil.getPrivateField(tag, "id");
        Boolean filter2 = (Boolean) UTUtil.getPrivateField(tag, "filter");
        Boolean ignore2 = (Boolean) UTUtil.getPrivateField(tag, "ignore");
        String name2 = (String) UTUtil.getPrivateField(tag, "name");
        String property2 = (String) UTUtil.getPrivateField(tag, "property");
        String scope2 = (String) UTUtil.getPrivateField(tag, "scope");
        String pattern2 = (String) UTUtil.getPrivateField(tag, "pattern");
        String value2 = (String) UTUtil.getPrivateField(tag, "value");
        Integer scale2 = (Integer) UTUtil.getPrivateField(tag, "scale");

        assertNull(id2);
        assertTrue(filter2.booleanValue());
        assertFalse(ignore2.booleanValue());
        assertNull(name2);
        assertNull(property2);
        assertNull(scope2);
        assertNull(pattern2);
        assertNull(value2);
        assertEquals(new Integer(-1), scale2);

    }

}
