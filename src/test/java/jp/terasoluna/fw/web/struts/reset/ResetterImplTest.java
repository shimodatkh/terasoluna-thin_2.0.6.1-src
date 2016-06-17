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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterImpl} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �f�t�H���g�̃��Z�b�g�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ResetterImpl
 */
public class ResetterImplTest extends TestCase {

    /**
     * DynaValidatorActionFormEx�̃v���p�e�B�ݒ�t�@�C��
     */
    private static final String CONFIG_FILE_PATH =
        ResetterImplTest.class.getResource(
                "ResetterImplTest.xml").getPath();

    /**
     * DynaValidatorActionFormEx�������[���ݒ�t�@�C��
     */
    private final static String RULES_FILE_PATH =
        ResetterImplTest.class.getResource(
                "ResetterImplTest-rules.xml").getPath();
    /**
     * DynaValidatorActionFormEx�𐶐�����N���X�B
     */
    private static final DynaActionFormCreator creator
        = new DynaActionFormCreator(RULES_FILE_PATH);

    /**
     * �e�X�g�Ώۂ̃C���X�^���X
     */
    private DynaValidatorActionFormEx formEx = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ResetterImplTest.class);
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
        // �e�X�g�Ŏg�p����DynaValidatorActionFormEx�C���X�^���X�𐶐�
        this.formEx = (DynaValidatorActionFormEx) creator
        .create(CONFIG_FILE_PATH);
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
    public ResetterImplTest(String name) {
        super(name);
    }

    /**
     * testReset01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) form:not null<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *
     * <br>
     * getActionReset��null��ԋp����ꍇ�A�����������s��ꂸ�A����I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset01() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ValidatorActionFormEx form = new ResetterImpl_ValidatorActionFormExStub01();
        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form , mapping ,req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(0, stub01.resetValueCount);
    }

    /**
     * testReset02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:not null<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset�����Iterator��ԋp����ꍇ�A�����������s��ꂸ�A����I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset02() throws Exception {
        // �O����
        ActionReset actionReset = new ActionReset();
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        stub01.actionReset = actionReset;

        ValidatorActionFormEx form = new ResetterImpl_ValidatorActionFormExStub01();
        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form , mapping ,req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(0, stub01.resetValueCount);
    }

    /**
     * testReset03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="values",select=true)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:form=������form�A<br>
     *                    propMap={<br>
     *                      values[0]="a",<br>
     *                      values[1]=null,<br>
     *                      values[2]="c"}�A<br>
     *                    request=������request<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *         (��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��Ă���A�Y���t�B�[���h������������ꍇ�AresetSelectField���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset03() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        form.values = values;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetValueCount);
        assertEquals(1, stub01.resetSelectFieldCount);
        assertSame(form, stub01.resetSelectFieldArg0.get(0));
        assertSame(req, stub01.resetSelectFieldArg2.get(0));
        Map map = stub01.resetSelectFieldArg1.get(0);
        assertEquals(3, map.size());
        assertEquals("a", map.get("values[0]"));
        assertNull(map.get("values[1]"));
        assertEquals("c", map.get("values[2]"));
    }

    /**
     * testReset04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                String value = "a"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="value",select=true)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={value="a"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��Ă��邪�A�Y���t�B�[���h��1�������Ȃ��ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset04() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        form.value = "a";
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("value");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("value", entry.getKey());
        assertEquals("a", entry.getValue());
    }

    /**
     * testReset05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="values",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={values[0]="a"}<br>
     *                    �Q�Fentry={values[1]=null}<br>
     *                    �R�Fentry={values[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��ĂȂ��A�Y���t�B�[���h���z��^�̏ꍇ�A�v�f�����AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset05() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        form.values = values;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("values[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList valueList = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="valueList",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={valueList[0]="a"}<br>
     *                    �Q�Fentry={valueList[1]=null}<br>
     *                    �R�Fentry={valueList[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��ĂȂ��A�Y���t�B�[���h��Collection�^�̏ꍇ�A�v�f�����AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset06() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        form.valueList = valueList;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                HashMap map = {key="test"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={map(key)="test"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��ĂȂ��A�Y���t�B�[���h��Map�^�̏ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset07() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map map = new HashMap();
        map.put("key", "test");
        form.map = map;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("map(key)", entry.getKey());
        assertEquals("test", entry.getValue());

    }

    /**
     * testReset08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{String value = "a"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.value",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={row.value="a"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g���Ă���ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset08() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        row.value = "a";
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.value", entry.getKey());
        assertEquals("a", entry.getValue());

    }

    /**
     * testReset09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{String[] values = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={row.values[0]="a"}<br>
     *                    �Q�Fentry={row.values[1]=null}<br>
     *                    �R�Fentry={row.values[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g�����z��̏ꍇ�A�S�Ă̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset09() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        row.values = values;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.values[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{ArrayList valueList = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={row.values[0]="a"}<br>
     *                    �Q�Fentry={row.values[1]=null}<br>
     *                    �R�Fentry={row.values[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g����Collection�^�̏ꍇ�A�S�Ă̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset10() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        row.valueList = valueList;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{HashMap map = {key="test"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={row.map(key)="test"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g����Map�̏ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset11() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row =
            new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", "test");
        row.map = map;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.map(key)", entry.getKey());
        assertEquals("test", entry.getValue());

    }

    /**
     * testReset12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows[0]<br>
     *                  �{String value="a"<br>
     *                JavaBean[] rows[1]<br>
     *                  �{String value=null<br>
     *                JavaBean[] rows[2]<br>
     *                  �{String value="c"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rows.value",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rows[0].value="a"}<br>
     *                    �Q�Fentry={rows[1].value=null}<br>
     *                    �R�Fentry={rows[2].value="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���z��̃l�X�g���������̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset12() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].value = "a";
        rows[1].value = null;
        rows[2].value = "c";
        form.rows = rows;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].value", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  �{String value="a"<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  �{String value=null<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  �{String value="c"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rowList.value",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rowList[0].value="a"}<br>
     *                    �Q�Fentry={rowList[1].value=null}<br>
     *                    �R�Fentry={rowList[2].value="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h��Collection�^�̃l�X�g���������̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset13() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row1 = new ResetterImpl_JavaBeanStub01();
        row1.value = "a";
        ResetterImpl_JavaBeanStub01 row2 = new ResetterImpl_JavaBeanStub01();
        row2.value = null;
        ResetterImpl_JavaBeanStub01 row3 = new ResetterImpl_JavaBeanStub01();
        row3.value = "c";
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        form.rowList = rowList;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].value", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows[0]<br>
     *                  �{Map map={key="a"}<br>
     *                JavaBean[] rows[1]<br>
     *                  �{Map map={key=null}<br>
     *                JavaBean[] rows[2]<br>
     *                  �{Map map={key="c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rows.map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rows[0]map(key)="a"}<br>
     *                    �Q�Fentry={rows[1].map(key)=null}<br>
     *                    �R�Fentry={rows[2].map(key)="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���z��̃l�X�g����Map�^�����̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset14() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        Map map = new HashMap();
        map.put("key", "a");
        rows[0].map = map;
        map = new HashMap();
        map.put("key", null);
        rows[1].map = map;
        map = new HashMap();
        map.put("key", "c");
        rows[2].map = map;
        form.rows = rows;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        // TODO JXPathIndexedBeanWrapperImpl��������ăe�X�g
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  �{Map map={key="a"}<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  �{Map map={key=null}<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  �{Map map={key="c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rowList.map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rowList[0].value="a"}<br>
     *                    �Q�Fentry={rowList[1].value=null}<br>
     *                    �R�Fentry={rowList[2].value="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���z��̃l�X�g����Map�^�����̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset15() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        Map map = new HashMap();
        map.put("key", "a");
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.map = map;
        rowList.add(row);
        map = new HashMap();
        map.put("key", null);
        row = new ResetterImpl_JavaBeanStub01();
        row.map = map;
        rowList.add(row);
        map = new HashMap();
        map.put("key", "c");
        row = new ResetterImpl_JavaBeanStub01();
        row.map = map;
        rowList.add(row);
        form.rowList = rowList;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        // TODO JXPathIndexedBeanWrapperImpl��������ăe�X�g
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());

    }

    /**
     * testReset16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{JavaBean nestedRow<br>
     *                       �{String value="test"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.nestedRow.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={row.nestedRow.value="test"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h��������z�l�X�g���Ă���ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset16() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test";
        row.nestedRow = nestedRow;
        form.row = row;
        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.nestedRow.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.nestedRow.value", entry.getKey());
        assertEquals("test", entry.getValue());

    }

    /**
     * testReset17()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows[0]<br>
     *                  �{ArrayList<JavaBean> nestedRows[0]<br>
     *                      �{String value="test0_0"<br>
     *                  �{ArrayList<JavaBean> nestedRows[1]<br>
     *                      �{String value="test0_1"<br>
     *                  �{ArrayList<JavaBean> nestedRows[2]<br>
     *                      �{String value="test0_2"<br>
     *                JavaBean[] rows[1]<br>
     *                  �{ArrayList<JavaBean> nestedRows[0]<br>
     *                      �{String value="test1_0"<br>
     *                  �{ArrayList<JavaBean> nestedRows[1]<br>
     *                      �{String value=null<br>
     *                  �{ArrayList<JavaBean> nestedRows[2]<br>
     *                      �{String value="test1_2"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rows.nestedRows.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rows[0].nestedRows[0].value="test0_0"}<br>
     *                    �Q�Fentry={rows[0].nestedRows[1].value="test0_1"}<br>
     *                    �R�Fentry={rows[0].nestedRows[2].value="test0_2"}<br>
     *                    �S�Fentry={rows[1].nestedRows[0].value="test1_0"}<br>
     *                    �T�Fentry={rows[1].nestedRows[1].value=null}<br>
     *                    �U�Fentry={rows[1].nestedRows[2].value="test1_2"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h��������z�l�X�g���Ă���ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset17() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        List nestedRows = new ArrayList();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_0";
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_1";
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_2";
        nestedRows.add(nestedRow);
        rows[0].nestedRows = nestedRows;
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRows = new ArrayList();
        nestedRow.value = "test1_0";
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = null;
        nestedRows.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test1_2";
        nestedRows.add(nestedRow);
        rows[1].nestedRows = nestedRows;
        form.rows = rows;

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.nestedRows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(form, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(6, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].nestedRows[0].value", entry.getKey());
        assertEquals("test0_0", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[0].nestedRows[1].value", entry.getKey());
        assertEquals("test0_1", entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[0].nestedRows[2].value", entry.getKey());
        assertEquals("test0_2", entry.getValue());
        // 4��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(3));
        entry = stub01.resetValueArg1.get(3);
        assertEquals("rows[1].nestedRows[0].value", entry.getKey());
        assertEquals("test1_0", entry.getValue());
        // 5��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(4));
        entry = stub01.resetValueArg1.get(4);
        assertEquals("rows[1].nestedRows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 6��ڂ̌Ăяo���B
        assertSame(form, stub01.resetValueArg0.get(5));
        entry = stub01.resetValueArg1.get(5);
        assertEquals("rows[1].nestedRows[2].value", entry.getKey());
        assertEquals("test1_2", entry.getValue());

    }

    /**
     * testReset18()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="values",select=true)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:form=������form�A<br>
     *                    propMap={<br>
     *                      values[0]="a",<br>
     *                      values[1]=null,<br>
     *                      values[2]="c"}�A<br>
     *                    request=������request<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *         (��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��Ă���A�Y���t�B�[���h������������ꍇ�AresetSelectField���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset18() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        formEx.set("values", values);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(1, stub01.resetSelectFieldCount);
        assertEquals(0, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetSelectFieldArg0.get(0));
        Map propMap = stub01.resetSelectFieldArg1.get(0);
        assertEquals(3, propMap.size());
        assertEquals("a", propMap.get("values[0]"));
        assertNull(propMap.get("values[1]"));
        assertEquals("c", propMap.get("values[2]"));
        assertSame(req, stub01.resetSelectFieldArg2.get(0));
    }

    /**
     * testReset19()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                String value = "a"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="value",select=true)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={value="a"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��Ă��邪�A�Y���t�B�[���h��1�������Ȃ��ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset19() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        formEx.set("value", "a");

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("value");
        fieldReset.setSelect(true);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertEquals(1, stub01.resetValueCount);
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("value", entry.getKey());
        assertEquals("a", entry.getValue());
    }

    /**
     * testReset20()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                String[] values = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="values",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={values[0]="a"}<br>
     *                    �Q�Fentry={values[1]=null}<br>
     *                    �R�Fentry={values[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��ĂȂ��A�Y���t�B�[���h���z��^�̏ꍇ�A�v�f�����AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset20() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        formEx.set("values", values);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("values[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset21()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList valueList = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="valueList",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={valueList[0]="a"}<br>
     *                    �Q�Fentry={valueList[1]=null}<br>
     *                    �R�Fentry={valueList[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��ĂȂ��A�Y���t�B�[���h��Collection�^�̏ꍇ�A�v�f�����AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset21() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        formEx.set("valueList", valueList);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset22()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                HashMap map = {key="test"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={map(key)="test"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * getActionReset�Ŏ擾����ActionReset��select�w�肳��ĂȂ��A�Y���t�B�[���h��Map�^�̏ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset22() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        Map map = new HashMap();
        map.put("key", "test");
        formEx.set("map", map);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("map(key)", entry.getKey());
        assertEquals("test", entry.getValue());
    }

    /**
     * testReset23()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{String value = "a"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.value",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={row.value="a"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g���Ă���ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset23() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.value = "a";
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.value", entry.getKey());
        assertEquals("a", entry.getValue());
    }

    /**
     * testReset24()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{String[] values = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={row.values[0]="a"}<br>
     *                    �Q�Fentry={row.values[1]=null}<br>
     *                    �R�Fentry={row.values[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g�����z��̏ꍇ�A�S�Ă̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset24() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        String[] values = {
            "a",
            null,
            "c"
        };
        row.values = values;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.values");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.values[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.values[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.values[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset25()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{ArrayList valueList = {"a",null,"c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.values",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={row.values[0]="a"}<br>
     *                    �Q�Fentry={row.values[1]=null}<br>
     *                    �R�Fentry={row.values[2]="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g����Collection�^�̏ꍇ�A�S�Ă̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset25() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        List valueList = new ArrayList();
        valueList.add("a");
        valueList.add(null);
        valueList.add("c");
        row.valueList = valueList;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.valueList");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.valueList[0]", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("row.valueList[1]", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("row.valueList[2]", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset26()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{HashMap map = {key="test"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={row.map(key)="test"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���l�X�g����Map�̏ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset26() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", "test");
        row.map = map;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.map(key)", entry.getKey());
        assertEquals("test", entry.getValue());
    }

    /**
     * testReset27()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows[0]<br>
     *                  �{String value="a"<br>
     *                JavaBean[] rows[1]<br>
     *                  �{String value=null<br>
     *                JavaBean[] rows[2]<br>
     *                  �{String value="c"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rows.value",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rows[0].value="a"}<br>
     *                    �Q�Fentry={rows[1].value=null}<br>
     *                    �R�Fentry={rows[2].value="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���z��̃l�X�g���������̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset27() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].value = "a";
        rows[1].value = null;
        rows[2].value = "c";
        formEx.set("rows", rows);


        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].value", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset28()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  �{String value="a"<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  �{String value=null<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  �{String value="c"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rowList.value",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rowList[0].value="a"}<br>
     *                    �Q�Fentry={rowList[1].value=null}<br>
     *                    �R�Fentry={rowList[2].value="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h��Collection�^�̃l�X�g���������̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset28() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.value = "a";
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.value = null;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.value = "c";
        rowList.add(row);
        formEx.set("rowList", rowList);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].value", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].value", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset29()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows[0]<br>
     *                  �{Map map={key="a"}<br>
     *                JavaBean[] rows[1]<br>
     *                  �{Map map={key=null}<br>
     *                JavaBean[] rows[2]<br>
     *                  �{Map map={key="c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rows.map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rows[0]map(key)="a"}<br>
     *                    �Q�Fentry={rows[1].map(key)=null}<br>
     *                    �R�Fentry={rows[2].map(key)="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���z��̃l�X�g����Map�^�����̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset29() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        Map map = new HashMap();
        map.put("key", "a");
        rows[0].map = map;
        map = new HashMap();
        map.put("key", null);
        rows[1].map = map;
        map = new HashMap();
        map.put("key", "c");
        rows[2].map = map;
        formEx.set("rows", rows);


        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        // TODO JXPathIndexedBeanWrapperImpl��������ăe�X�g
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset30()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList<JavaBean> rowList[0]<br>
     *                  �{Map map={key="a"}<br>
     *                ArrayList<JavaBean> rowList[1]<br>
     *                  �{Map map={key=null}<br>
     *                ArrayList<JavaBean> rowList[2]<br>
     *                  �{Map map={key="c"}<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rowList.map(key)",select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rowList[0].value="a"}<br>
     *                    �Q�Fentry={rowList[1].value=null}<br>
     *                    �R�Fentry={rowList[2].value="c"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h���z��̃l�X�g����Map�^�����̏ꍇ�A���ׂĂ̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset30() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", "a");
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("key", "c");
        row.map = map;
        rowList.add(row);
        formEx.set("rowList", rowList);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rowList.map(key)");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        // TODO JXPathIndexedBeanWrapperImpl��������ăe�X�g
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(3, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rowList[0].map(key)", entry.getKey());
        assertEquals("a", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rowList[1].map(key)", entry.getKey());
        assertNull(entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rowList[2].map(key)", entry.getKey());
        assertEquals("c", entry.getValue());
    }

    /**
     * testReset31()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                  �{JavaBean nestedRow<br>
     *                       �{String value="test"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="row.nestedRow.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:form=������form�A<br>
     *                    entry={row.nestedRow.value="test"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h��������z�l�X�g���Ă���ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset31() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test";
        row.nestedRow = nestedRow;
        formEx.set("row", row);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("row.nestedRow.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(1, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("row.nestedRow.value", entry.getKey());
        assertEquals("test", entry.getValue());
    }

    /**
     * testReset32()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows[0]<br>
     *                  �{ArrayList<JavaBean> nestedRows[0]<br>
     *                      �{String value="test0_0"<br>
     *                  �{ArrayList<JavaBean> nestedRows[1]<br>
     *                      �{String value="test0_1"<br>
     *                  �{ArrayList<JavaBean> nestedRows[2]<br>
     *                      �{String value="test0_2"<br>
     *                JavaBean[] rows[1]<br>
     *                  �{ArrayList<JavaBean> nestedRows[0]<br>
     *                      �{String value="test1_0"<br>
     *                  �{ArrayList<JavaBean> nestedRows[1]<br>
     *                      �{String value=null<br>
     *                  �{ArrayList<JavaBean> nestedRows[2]<br>
     *                      �{String value="test1_2"<br>
     *         (����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getActionReset�̌���:{FieldReset(field="rows.nestedRows.value",<br>
     *                select=false)}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetSelectField:�Ăяo����Ȃ�<br>
     *         (��ԕω�) resetValue:�P�Fentry={rows[0].nestedRows[0].value="test0_0"}<br>
     *                    �Q�Fentry={rows[0].nestedRows[1].value="test0_1"}<br>
     *                    �R�Fentry={rows[0].nestedRows[2].value="test0_2"}<br>
     *                    �S�Fentry={rows[1].nestedRows[0].value="test1_0"}<br>
     *                    �T�Fentry={rows[1].nestedRows[1].value=null}<br>
     *                    �U�Fentry={rows[1].nestedRows[2].value="test1_2"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *                    ��form�͑S�Ăɂ��Ĉ�����form<br>
     *
     * <br>
     * ���Z�b�g�Ώۂ̃t�B�[���h��������z�l�X�g���Ă���ꍇ�AresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testReset32() throws Exception {
        // �O����
        ResetterImplStub01 stub01 = new ResetterImplStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        List nestedRows1 = new ArrayList();
        ResetterImpl_JavaBeanStub01 nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_0";
        nestedRows1.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_1";
        nestedRows1.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test0_2";
        nestedRows1.add(nestedRow);
        rows[0].nestedRows = nestedRows1;

        List nestedRows2 = new ArrayList();
        nestedRow =
            new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test1_0";
        nestedRows2.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = null;
        nestedRows2.add(nestedRow);
        nestedRow = new ResetterImpl_JavaBeanStub01();
        nestedRow.value = "test1_2";
        nestedRows2.add(nestedRow);
        rows[1].nestedRows = nestedRows2;

        formEx.set("rows", rows);

        ActionReset actionReset = new ActionReset();
        FieldReset fieldReset = new FieldReset();
        fieldReset.setFieldName("rows.nestedRows.value");
        fieldReset.setSelect(false);
        actionReset.setFieldReset(fieldReset);
        stub01.actionReset = actionReset;

        ActionMapping mapping = new ActionMapping();
        HttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        stub01.reset(formEx, mapping, req);

        // ����
        assertEquals(0, stub01.resetSelectFieldCount);
        assertEquals(6, stub01.resetValueCount);
        // 1��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(0));
        Entry entry = stub01.resetValueArg1.get(0);
        assertEquals("rows[0].nestedRows[0].value", entry.getKey());
        assertEquals("test0_0", entry.getValue());
        // 2��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(1));
        entry = stub01.resetValueArg1.get(1);
        assertEquals("rows[0].nestedRows[1].value", entry.getKey());
        assertEquals("test0_1", entry.getValue());
        // 3��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(2));
        entry = stub01.resetValueArg1.get(2);
        assertEquals("rows[0].nestedRows[2].value", entry.getKey());
        assertEquals("test0_2", entry.getValue());
        // 4��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(3));
        entry = stub01.resetValueArg1.get(3);
        assertEquals("rows[1].nestedRows[0].value", entry.getKey());
        assertEquals("test1_0", entry.getValue());
        // 5��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(4));
        entry = stub01.resetValueArg1.get(4);
        assertEquals("rows[1].nestedRows[1].value", entry.getKey());
        assertNull(entry.getValue());
        // 6��ڂ̌Ăяo���B
        assertSame(formEx, stub01.resetValueArg0.get(5));
        entry = stub01.resetValueArg1.get(5);
        assertEquals("rows[1].nestedRows[2].value", entry.getKey());
        assertEquals("test1_2", entry.getValue());
    }

    /**
     * testResetValue01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                hoge�v���p�e�B�����݂��Ȃ�<br>
     *         (����) entry:hoge=null<br>
     *
     * <br>
     * ���Ғl�F
     * <br>
     * entry�̒l��null�̏ꍇ�A���������������I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue01() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "hoge";
        entry.value = null;

        // �e�X�g���{
        test.resetValue(form, entry);

    }

    /**
     * testResetValue02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                hoge�v���p�e�B�����݂��Ȃ�<br>
     *         (����) entry:hoge="hoge"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"cannot access property " + form + ".hoge"<br>
     *                    ��O�FPropertyAccessException<br>
     *
     * <br>
     * �v���p�e�B�̏������Ɏ��s�����ꍇ�A�G���[���x���̃��O���o�͂��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue02() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "hoge";
        entry.value = "hoge";

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        assertTrue(LogUTUtil.checkError("cannot access property " + form
                + ".hoge", new PropertyAccessException(new NoSuchMethodException())));

    }

    /**
     * testResetValue03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                Boolean booleanValue1 = Boolean.TRUE<br>
     *         (����) entry:booleanValue1=Boolean.TRUE<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:booleanValue1 = Boolean.FALSE<br>
     *
     * <br>
     * �v���p�e�B�̌^��Boolean�^�̏ꍇ�A�l��Boolean.FALSE�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue03() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        form.booleanValue1 = Boolean.TRUE;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "booleanValue1";
        entry.value = Boolean.TRUE;

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        assertEquals(Boolean.FALSE, form.getBooleanValue1());

    }

    /**
     * testResetValue04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                boolean booleanValue2 = true;<br>
     *         (����) entry:booleanValue2=Boolean.TRUE<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:booleanValue2=false<br>
     *
     * <br>
     * �v���p�e�B�̌^��boolean�^�̏ꍇ�A�l��Boolean.FALSE�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue04() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        formEx.set("booleanValue2", true);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "booleanValue2";
        entry.value = Boolean.TRUE;

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        assertEquals(Boolean.FALSE, formEx.get("booleanValue2"));

    }

    /**
     * testResetValue05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                Byte[] byteArray1 = {<br>
     *                 0, 1, 2<br>
     *                }<br>
     *         (����) entry:byteArray1[1]=1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:byteArray1={<br>
     *                     0, 0, 2<br>
     *                    }<br>
     *
     * <br>
     * �v���p�e�B�̌^��Byte�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue05() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Byte[] byteArray1 = {
            new Byte((byte) 0),
            new Byte((byte) 1),
            new Byte((byte) 2)
        };
        form.byteArray1 = byteArray1;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "byteArray1[1]";
        entry.value = new Byte((byte) 1);

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        Byte[] result = form.getByteArray1();
        assertEquals((byte) 0, result[0].byteValue());
        assertEquals((byte) 0, result[1].byteValue());
        assertEquals((byte) 2, result[2].byteValue());

    }

    /**
     * testResetValue06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                byte[] byteArray2 = {<br>
     *                 0, 1, 2<br>
     *                }<br>
     *         (����) entry:byteArray2[1]=1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:byteArray2={<br>
     *                     0, 0, 2<br>
     *                    }<br>
     *
     * <br>
     * �v���p�e�B�̌^��byte�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue06() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        byte[] byteArray2 = {
            (byte) 0,
            (byte) 1,
            (byte) 2
        };
        formEx.set("byteArray2", byteArray2);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "byteArray2[1]";
        entry.value = new Byte((byte) 1);

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        byte[] result = (byte[]) formEx.get("byteArray2");
        assertEquals((byte) 0, result[0]);
        assertEquals((byte) 0, result[1]);
        assertEquals((byte) 2, result[2]);

    }

    /**
     * testResetValue07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:VlidatorActionFormEx�C���X�^���X<br>
     *                ArrayList charList1 = {<br>
     *                 Character('a'),<br>
     *                 Character('b'),<br>
     *                 Character('c')<br>
     *                }<br>
     *         (����) entry:charList1[1]='b'<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:ArrayList charList1 = {<br>
     *                     Character('a'),<br>
     *                     (Character) 0,<br>
     *                     Character('c')<br>
     *                    }<br>
     *
     * <br>
     * �v���p�e�B�̌^��Character�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue07() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List charList1 = new ArrayList();
        charList1.add(new Character('a'));
        charList1.add(new Character('b'));
        charList1.add(new Character('c'));
        form.charList1 = charList1;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "charList1[1]";
        entry.value = new Character((char) 1);

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        List<Character> result = form.charList1;
        assertEquals('a', result.get(0).charValue());
        assertEquals((char) 0, result.get(1).charValue());
        assertEquals('c', result.get(2).charValue());

    }

    /**
     * testResetValue08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                char charValue1 = 'a'<br>
     *         (����) entry:charValue1='a'<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:charValue1=0<br>
     *
     * <br>
     * �v���p�e�B�̌^��char�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue08() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        formEx.set("charValue1", new Character('a'));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "charValue1";
        entry.value = new Character('a');

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        assertEquals(new Character((char) 0), formEx.get("charValue1"));

    }

    /**
     * testResetValue09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                Map map = {<br>
     *                 Double doubleValue1=4.9E-324<br>
     *                }<br>
     *         (����) entry:map(doubleValue1)=4.9E-324<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:Map map = {<br>
     *                     doubleValue1=0.0<br>
     *                    }<br>
     *
     * <br>
     * �v���p�e�B�̌^��Double�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue09() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map map = new HashMap();
        map.put("doubleValue1", new Double(4.9E-324));
        form.map = map;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "map.doubleValue1";
        entry.value = new Double(4.9E-324);

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        Map result = form.map;
        assertEquals(0.0, result.get("doubleValue1"));

    }

    /**
     * testResetValue10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                double doubleValue2 = 4.9E-324<br>
     *         (����) entry:doubleValue2=4.9E-324<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:doubleValue2=0.0<br>
     *
     * <br>
     * �v���p�e�B�̌^��double�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue10() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        formEx.set("doubleValue2", new Double(4.9E-324));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "doubleValue2";
        entry.value = new Double(4.9E-324);

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        assertEquals(0.0,
                formEx.get("doubleValue2"));

    }

    /**
     * testResetValue11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row = new JavaBean()<br>
     *                 �{Float floatValue1 = 3.4028235E38<br>
     *         (����) entry:row.floatValue1=3.4028235E38<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:row.floatValue1=0.0<br>
     *
     * <br>
     * �v���p�e�B�̌^��Float�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue11() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.floatValue1 = new Float(3.4028235E38);
        form.row = row;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "row.floatValue1";
        entry.value = new Float(3.4028235E38);

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        assertEquals((float) 0.0, form.row.floatValue1);

    }

    /**
     * testResetValue12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row = new JavaBean()<br>
     *                 �{float floatValue2 = 3.4028235E38<br>
     *         (����) entry:row.floatValue2=3.4028235E38<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:row.floatValue2=0.0<br>
     *
     * <br>
     * �v���p�e�B�̌^��float�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue12() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        formEx.set("floatValue2", new Float(3.4028235E38));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "floatValue2";
        entry.value = new Float(3.4028235E38);

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        assertEquals((float) 0.0,
                formEx.get("floatValue2"));

    }

    /**
     * testResetValue13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows = {<br>
     *                 new JavaBean()<br>
     *                  �{Integer intValue1 = 1,<br>
     *                 new JavaBean()<br>
     *                  �{Integer intValue1 = 2,<br>
     *                 new JavaBean()<br>
     *                  �{Integer intValue1 = 3,<br>
     *                }<br>
     *         (����) entry:rows[1].intValue1=2<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:row[1].intValue1=0<br>
     *
     * <br>
     * �v���p�e�B�̌^��Integer�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue13() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].intValue1 = 1;
        rows[1].intValue1 = 2;
        rows[2].intValue1 = 3;
        form.rows = rows;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rows[1].intValue1";
        entry.value = new Integer(2);

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        assertEquals(new Integer(0), form.rows[1].intValue1);

    }

    /**
     * testResetValue14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows = {<br>
     *                 new JavaBean()<br>
     *                  �{int intValue2 = 1,<br>
     *                 new JavaBean()<br>
     *                  �{int intValue2 = 2,<br>
     *                 new JavaBean()<br>
     *                  �{int intValue2 = 3,<br>
     *                }<br>
     *         (����) entry:rows[1].intValue2=2<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:row[1].intValue2=0<br>
     *
     * <br>
     * �v���p�e�B�̌^��int�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue14() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_JavaBeanStub01[] rows = {
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01(),
            new ResetterImpl_JavaBeanStub01()
        };
        rows[0].intValue2 = 1;
        rows[1].intValue2 = 2;
        rows[2].intValue2 = 3;
        formEx.set("rows", rows);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rows[1].intValue2";
        entry.value = new Integer(2);

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        ResetterImpl_JavaBeanStub01[] result =
            (ResetterImpl_JavaBeanStub01[]) formEx.get("rows");
        assertEquals(0, result[1].intValue2);

    }

    /**
     * testResetValue15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList rowList = {<br>
     *                 new JavaBean()<br>
     *                  �{Long longValue1 = 1,<br>
     *                 new JavaBean()<br>
     *                  �{Long longValue1 = 2,<br>
     *                 new JavaBean()<br>
     *                  �{Long longValue1 = 3,<br>
     *                }<br>
     *         (����) entry:rowList[1].longValue1=2<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:rowList[1].longValue1=0<br>
     *
     * <br>
     * �v���p�e�B�̌^��Long�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue15() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.longValue1 = new Long(1);
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue1 = new Long(2);
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue1 = new Long(3);
        rowList.add(row);
        form.rowList = rowList;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rowList[1].longValue1";
        entry.value = new Long(2);

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        assertEquals(new Long(0), form.rowList.get(1).longValue1);

    }

    /**
     * testResetValue16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList rowList = {<br>
     *                 new JavaBean()<br>
     *                  �{long longValue2 = 1,<br>
     *                 new JavaBean()<br>
     *                  �{long longValue2 = 2,<br>
     *                 new JavaBean()<br>
     *                  �{long longValue2 = 3,<br>
     *                }<br>
     *         (����) entry:rowList[1].longValue2=2<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:rowList[1].longValue2=0<br>
     *
     * <br>
     * �v���p�e�B�̌^��long�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue16() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        row.longValue2 = 1;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue2 = 2;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        row.longValue2 = 3;
        rowList.add(row);
        formEx.set("rowList", rowList);
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rowList[1].longValue2";
        entry.value = new Long(2);

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        List<ResetterImpl_JavaBeanStub01> result = (List) formEx.get("rowList");
        assertEquals(0, result.get(1).longValue2);

    }

    /**
     * testResetValue17()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList rows = {<br>
     *                 new JavaBean()<br>
     *                  �{Map map = Short shortValue1 = 1,<br>
     *                 new JavaBean()<br>
     *                  �{Map map = Short shortValue1 = 2,<br>
     *                 new JavaBean()<br>
     *                  �{Map map = Short shortValue1 = 3,<br>
     *                }<br>
     *         (����) entry:rows[1].map(shortValue1)=2<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:rows[1].map(shortValue1)=0<br>
     *
     * <br>
     * �v���p�e�B�̌^��Short�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue17() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        List rowList = new ArrayList();
        ResetterImpl_JavaBeanStub01 row = new ResetterImpl_JavaBeanStub01();
        Map map = new HashMap();
        map.put("shortValue1", new Short((short) 1));
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("shortValue1", new Short((short) 2));
        row.map = map;
        rowList.add(row);
        row = new ResetterImpl_JavaBeanStub01();
        map = new HashMap();
        map.put("shortValue1", new Short((short) 3));
        row.map = map;
        rowList.add(row);
        form.rowList = rowList;
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "rowList[1].map(shortValue1)";
        entry.value = new Short((short) 2);

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        ResetterImpl_JavaBeanStub01 result = form.rowList.get(1);
        assertEquals(new Short((short) 0), result.map.get("shortValue1"));

    }

    /**
     * testResetValue18()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx�C���X�^���X<br>
     *                 short shortValue2 = 1<br>
     *         (����) entry:shortValue2=1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:shortValue1 = 0<br>
     *
     * <br>
     * �v���p�e�B�̌^��short�^�̏ꍇ�A�l��0�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue18() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        formEx.set("shortValue2", new Short((short) 2));
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "shortValue2";
        entry.value = new Short((short) 2);

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        assertEquals((short) 0, formEx.get("shortValue2"));

    }

    /**
     * testResetValue19()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:ValidatorActionFormEx<br>
     *                 String value="hoge"<br>
     *         (����) entry:value="hoge"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:value = null<br>
     *
     * <br>
     * �v���p�e�B�̌^���v���~�e�B�u�^�A���b�p�[�^�ł͂Ȃ��ꍇ�Anull���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue19() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        form.value = "hoge";
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "value";
        entry.value = "hoge";

        // �e�X�g���{
        test.resetValue(form, entry);

        // ����
        assertNull(form.value);

    }

    /**
     * testResetValue20()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) form:DynaValidatorActionFormEx<br>
     *                 String value="hoge"<br>
     *         (����) entry:value="hoge"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) form:value = null<br>
     *
     * <br>
     * �v���p�e�B�̌^���v���~�e�B�u�^�A���b�p�[�^�ł͂Ȃ��ꍇ�Anull���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetValue20() throws Exception {
        // �O����
        ResetterImpl test = new ResetterImpl();
        formEx.set("value", "hoge");
        ResetterImpl_EntryStub01 entry = new ResetterImpl_EntryStub01();
        entry.key = "value";
        entry.value = "hoge";

        // �e�X�g���{
        test.resetValue(formEx, entry);

        // ����
        assertNull(formEx.get("value"));

    }

    /**
     * testGetResetterResources01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) request:Globals.MODULE_KEY<br>
     *                =ModuleConfig(path="/module")<br>
     *         (���) ServletContext:RESETTER_RESOURCES/module<br>
     *                =ResetterResources�C���X�^���X<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ResetterResources:ResetterResources�C���X�^���X<br>
     *
     * <br>
     * ServletContext��RESETTER_RESOURCES/<���W���[����>�œo�^���ꂽResetterResources�C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResetterResources01() throws Exception {
        //�����ݒ�
        ResetterImpl resetter = new ResetterImpl();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        ResetterResources rr = new ResetterResources();

        // ���N�G�X�g�Ƀ��W���[���R���t�B�O��o�^
        String module = "/module";
        ModuleConfig reqConfig = new ModuleConfigImpl(module);
        req.setAttribute(Globals.MODULE_KEY, reqConfig);

        // �A�N�V�����T�[�u���b�g�쐬
        MockServletContext ctx = new MockServletContext();

        ctx.setAttribute("RESETTER_RESOURCES/module", rr);
        session.setServletContext(ctx);

        //���N�G�X�g�ɃZ�b�V������o�^
        req.setSession(session);

        //�e�X�g���s
        ResetterResources result = resetter.getResetterResources(req);

        //���ʊm�F
        assertSame(rr, result);
    }

    /**
     * testGetResetterResources02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) request:Globals.MODULE_KEY<br>
     *                =ModuleConfig(path="/module")<br>
     *         (���) ServletContext:RESETTER_RESOURCES/module<br>
     *                =null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ResetterResources:null<br>
     *
     * <br>
     * ServletContext��RESETTER_RESOURCES/<���W���[����>��ResetterResources�����݂��Ȃ��ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResetterResources02() throws Exception {
        //�����ݒ�
        ResetterImpl resetter = new ResetterImpl();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        // ���N�G�X�g�Ƀ��W���[���R���t�B�O��o�^
        String module = "/module";
        ModuleConfig reqConfig = new ModuleConfigImpl(module);
        req.setAttribute(Globals.MODULE_KEY, reqConfig);

        // �A�N�V�����T�[�u���b�g�쐬
        MockServletContext ctx = new MockServletContext();

        ctx.setAttribute("RESETTER_RESOURCES/module", null);
        session.setServletContext(ctx);

        //���N�G�X�g�ɃZ�b�V������o�^
        req.setSession(session);

        //�e�X�g���s
        ResetterResources result = resetter.getResetterResources(req);

        //���ʊm�F
        assertNull(result);
    }

    /**
     * testGetActionReset01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:not null<br>
     *         (���) getResetterResources:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionReset:null<br>
     *
     * <br>
     * getResetterResources���\�b�h��null��ԋp����ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetActionReset01() throws Exception {
        ResetterImplStub02 resetter = new ResetterImplStub02();
        resetter.resetterResources = null;

        ActionMappingEx mapping = new ActionMappingEx();
        MockHttpServletRequest request = new MockHttpServletRequest();

        Object result = resetter.getActionReset(mapping, request);

        assertNull(result);
    }

    /**
     * testGetActionReset02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                path="test"<br>
     *         (����) request:not null<br>
     *         (���) getResetterResources:not null<br>
     *                hoge=ActionReset<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionReset:null<br>
     *
     * <br>
     * mapping����擾����path�̒l�ɊY������ActionReset��ResetterResources����擾�ł��Ȃ��ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetActionReset02() throws Exception {
        // �O����
        // hoge=ActionReset��ResetterResources�ɐݒ�
        ResetterResources resources = new ResetterResources();
        ActionReset actionReset = new ActionReset();
        actionReset.setPath("hoge");
        resources.setActionReset(actionReset);

        // �e�X�g�ΏۃX�^�u
        ResetterImplStub02 resetter = new ResetterImplStub02();
        resetter.resetterResources = resources;
        MockServletContext ctx = new MockServletContext();
        ctx.setAttribute(ResetterResources.RESETTER_RESOURCES_KEY, resources);

        // ����
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("test");
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = resetter.getActionReset(mapping, request);

        // ����
        assertNull(result);
    }

    /**
     * testGetActionReset03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *                path="test"<br>
     *         (����) request:not null<br>
     *         (���) getResetterResources:not null<br>
     *                hoge=ActionReset�C���X�^���X�O�P<br>
     *                test=ActionReset�C���X�^���X�O�Q<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionReset:ActionReset�C���X�^���X�O�Q<br>
     *
     * <br>
     * mapping����擾����path�̒l�ɊY������ActionReset��ResetterResources�ɑ��݂���ꍇ�A���̃C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetActionReset03() throws Exception {
        // �O����
        // hoge=ActionReset��ResetterResources�ɐݒ�
        ResetterResources resources = new ResetterResources();
        ActionReset actionReset1 = new ActionReset();
        actionReset1.setPath("hoge");
        resources.setActionReset(actionReset1);
        ActionReset actionReset2 = new ActionReset();
        actionReset2.setPath("test");
        resources.setActionReset(actionReset2);

        // �e�X�g�ΏۃX�^�u
        ResetterImplStub02 resetter = new ResetterImplStub02();
        resetter.resetterResources = resources;
        MockServletContext ctx = new MockServletContext();
        ctx.setAttribute(ResetterResources.RESETTER_RESOURCES_KEY, resources);

        // ����
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setPath("test");
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        Object result = resetter.getActionReset(mapping, request);

        // ����
        assertSame(actionReset2, result);
    }

    /**
     * testResetSelectField01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:���Map<br>
     *         (����) request:�p�����[�^<br>
     *                startIndex="hoge"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    startIndex or endIndex is not Number.<br>
     *                    ��O�FNumberFormatException<br>
     *
     * <br>
     * ���N�G�X�g�p�����[�^����擾����startIndex�����l�ɕϊ��ł��Ȃ��ꍇ�A�G���[���O���o�͂��A�����I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField01() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "hoge");

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertTrue(
            LogUTUtil.checkError("startIndex or endIndex is not Number.",
                    new NumberFormatException()));
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:���Map<br>
     *         (����) request:�p�����[�^<br>
     *                endIndex="hoge"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    startIndex or endIndex is not Number.<br>
     *                    ��O�FNumberFormatException<br>
     *
     * <br>
     * ���N�G�X�g�p�����[�^����擾����endIndex�����l�ɕϊ��ł��Ȃ��ꍇ�A�G���[���O���o�͂��A�����I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField02() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("endIndex", "hoge");

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertTrue(
            LogUTUtil.checkError("startIndex or endIndex is not Number.",
                    new NumberFormatException()));
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:���Map<br>
     *         (����) request:�p�����[�^<br>
     *                startIndex="0"<br>
     *                endIndex="100"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *
     * <br>
     * ������propMap�ɗv�f�����݂��Ȃ��ꍇ�AresetValue���Ăяo����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField03() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "0");
        req.setParameter("endIndex", "100");

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       �`<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (����) request:�p�����[�^<br>
     *                startIndex="0"<br>
     *                endIndex="4"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:"key01" = "value01",<br>
     *                           �`<br>
     *                     "key05" = "value05"<br>
     *
     * <br>
     * ������propMap�̗v�f�̂����AstartIndex�`endIndex�̗v�f�������Ƃ��āAresetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField04() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "0");
        req.setParameter("endIndex", "4");

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertEquals(5, test.resetValueCount);
        assertSame(form, test.resetValueArg0.get(0));
        for (int i = 0; i < 5; i++) {
            String key = "key0" + (i + 1);
            String value = "value0" + (i + 1);
            assertEquals(key, test.resetValueArg1.get(i).getKey());
            assertEquals(value, test.resetValueArg1.get(i).getValue());
        }
    }

    /**
     * testResetSelectField05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       �`<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (����) request:�p�����[�^<br>
     *                startIndex�AendIndex�����݂��Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:"key01" = "value01",<br>
     *                           �`<br>
     *                     "key10" = "value10"<br>
     *
     * <br>
     * ���N�G�X�g�p�����[�^��startIndex�AendIndex�����݂��Ȃ��ꍇ�A�S�Ă̗v�f�ɂ���resetValue���Ăяo����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField05() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertEquals(10, test.resetValueCount);
        assertSame(form, test.resetValueArg0.get(0));
        for (int i = 0; i < 10; i++) {
            String key = null;
            String value = null;
            if (i < 9) {
                key = "key0" + (i + 1);
                value = "value0" + (i + 1);
            } else {
                key = "key10";
                value = "value10";
            }
            assertEquals(key, test.resetValueArg1.get(i).getKey());
            assertEquals(value, test.resetValueArg1.get(i).getValue());
        }
    }

    /**
     * testResetSelectField06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       �`<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (����) request:�p�����[�^<br>
     *                startIndex="5"<br>
     *                endIndex="20"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:"key05" = "value05",<br>
     *                           �`<br>
     *                     "key10" = "value10"<br>
     *
     * <br>
     * ���N�G�X�g�p�����[�^����擾����endIndex��propMap�̗v�f�����傫���ꍇ�A�Ō�̗v�f�܂ł�resetValue�ɓn����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField06() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "5");
        req.setParameter("endIndex", "20");

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertEquals(5, test.resetValueCount);
        assertSame(form, test.resetValueArg0.get(0));
        int index = 0;
        for (int i = 5; i < 10; i++) {
            String key = null;
            String value = null;
            if (i < 9) {
                key = "key0" + (i + 1);
                value = "value0" + (i + 1);
            } else {
                key = "key10";
                value = "value10";
            }
            assertEquals(key, test.resetValueArg1.get(index).getKey());
            assertEquals(value, test.resetValueArg1.get(index).getValue());
            index++;
        }
    }

    /**
     * testResetSelectField07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       �`<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (����) request:�p�����[�^<br>
     *                startIndex="5"<br>
     *                endIndex="0"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:�Ăяo����Ȃ�<br>
     *
     * <br>
     * startIndex�̒l��endIndex�̒l���傫���ꍇ�AresetValue���Ăяo����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField07() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "5");
        req.setParameter("endIndex", "0");

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertEquals(0, test.resetValueCount);
    }

    /**
     * testResetSelectField08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) propMap:TreeMap {<br>
     *                 "key01" = "value01",<br>
     *                       �`<br>
     *                 "key10" = "value10"<br>
     *                }<br>
     *         (����) request:�p�����[�^<br>
     *                startIndex="5"<br>
     *                endIndex="5"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resetValue:"key06" = "value06"<br>
     *
     * <br>
     * startIndex��endIndex�̒l����v����ꍇ�A���̗v�f������resetValue�ɓn����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
    public void testResetSelectField08() throws Exception {
        // �O����
        ResetterImplStub03 test = new ResetterImplStub03();
        ResetterImpl_ValidatorActionFormExStub01 form =
            new ResetterImpl_ValidatorActionFormExStub01();
        Map propMap = new TreeMap();
        propMap.put("key01", "value01");
        propMap.put("key02", "value02");
        propMap.put("key03", "value03");
        propMap.put("key04", "value04");
        propMap.put("key05", "value05");
        propMap.put("key06", "value06");
        propMap.put("key07", "value07");
        propMap.put("key08", "value08");
        propMap.put("key09", "value09");
        propMap.put("key10", "value10");
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setParameter("startIndex", "5");
        req.setParameter("endIndex", "5");

        // �e�X�g���{
        test.resetSelectField(form ,propMap, req);

        // ����
        assertEquals(1, test.resetValueCount);
        assertEquals("key06", test.resetValueArg1.get(0).getKey());
        assertEquals("value06", test.resetValueArg1.get(0).getValue());
    }

}
