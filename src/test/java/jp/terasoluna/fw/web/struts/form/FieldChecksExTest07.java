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

package jp.terasoluna.fw.web.struts.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Msg;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.Var;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Validator�ǉ����[���N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest07 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest07.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        deleteProperty("validation.hankaku.kana.list");
        deleteProperty("validation.zenkaku.kana.list");
    }

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public FieldChecksExTest07(String name) {
        super(name);
    }

    /**
     * testValidateNumber01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F<br>
     *                    �G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    bean is null.<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ������bean��null�̏ꍇ�Atrue���ԋp����A
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[��񂪋�ł��邱�ƁB
        assertTrue(errors.isEmpty());

        // �G���[���O�m�F
        assertTrue(LogUTUtil.checkError("bean is null."));
    }

    /**
     * testValidateNumber02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ������bean���󕶎��̏ꍇ�Atrue���ԋp����A
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[��񂪋�ł��邱�ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * Field��integerLength,scale���w�肳��Ă��Ȃ��ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue(null);
        field.addVar(var);

        // �G���[���
        ActionMessages errors = new ActionMessages();

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // 2�̃G���[��񂪓o�^����Ă��邱��
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=""<br>
     *                var:scale=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * Field��integerLength,scale�ɋ󕶎����w�肳��Ă���ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber04() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue("");
        field.addVar(var);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="abc"<br>
     *                var:scale="def"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * Field��integerLength,scale�ɐ��l�ɕϊ��ł��Ȃ�������
     * �w�肳��Ă���ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber05() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue("abc");
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue("def");
        field.addVar(var);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * scale�ɐ��l���w�肳��Ă���Ascale�̐��l���A
     * bean�̏����_�ȉ��̌����傫���Ƃ��Afalse���ԋp����邱�ƁA
     * �G���[���b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber06() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("1");
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��邱�ƁB
        assertEquals(1, errors.size());

        // ���b�Z�[�W�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale="true"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * scale�ɐ��l�AisAccordedScale��true���w�肳��Ă���A
     * scale�̐��l���bean�̏����_�ȉ��̌����������Ƃ��A
     * false���ԋp����邱�ƁA�G���[���b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // ������������v�w��
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("true");
        field.addVar(varAccorded);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��邱�ƁB
        assertEquals(1, errors.size());
        // �G���[���ɐݒ肳�ꂽ���b�Z�[�W���o�^����Ă��邱�ƁB
        Iterator it = errors.get();
        while (it.hasNext()) {
            ActionMessage error = (ActionMessage) it.next();
            assertEquals("message", error.getKey());
        }
    }

    /**
     * testValidateNumber08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.123"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale="true"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * scale�ɐ��l�AisAccordedScale��true���w�肳��Ă���Ascale�̐��l�ƁA
     * bean�̏����_�ȉ��̌����������Ƃ��Atrue���ԋp����邱�ƁA
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.123";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // ������������v�w��
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("true");
        field.addVar(varAccorded);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * scale�ɐ��l�AisAccordedScale�ɋ󕶎����w�肳��Ă���A
     * scale�̐��l���Abean�̏����_�ȉ��̌����������Ƃ��A
     * true���ԋp����邱�ƁA�G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // ������������v�w��
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("");
        field.addVar(varAccorded);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * scale�ɐ��l�AisAccordedScale��null���w�肳��Ă���Ascale�̐��l���A
     * bean�̏����_�ȉ��̌����������Ƃ��Atrue���ԋp����邱�ƁA
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // ������������v�w��
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue(null);
        field.addVar(varAccorded);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="3"<br>
     *                var:isAccordedScale="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * scale�ɐ��l�AisAccordedScale��true�ł͂Ȃ������񂪎w�肳��Ă���A
     * scale�̐��l���Abean�̏����_�ȉ��̌����������Ƃ��Atrue���ԋp����邱�ƁA
     * �G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber11() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);
        // ������������v�w��
        Var varAccorded = new Var();
        varAccorded.setName("isAccordedScale");
        varAccorded.setValue("abc");
        field.addVar(varAccorded);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale="2"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * scale�ɐ��l���w�肳��Ă���A������bean�����l�ɕϊ��ł��Ȃ��ꍇ�A
     * false���ԋp����邱�ƁB�G���[���b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber12() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue(null);
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("2");
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertEquals(1, errors.size());

        // �G���[�I�u�W�F�N�g�̌����B
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"abc.de"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * ������bean�����l�ł͂Ȃ��ꍇ�Afalse���ԋp����邱�ƁB
     * �G���[���b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber13() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc.de";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("1");
        field.addVar(varInteger);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��邱�ƁB
        assertEquals(2, errors.size());

        // �G���[�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("message"));
        assertTrue(list.contains("testMessage"));
    }

    /**
     * testValidateNumber14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="1"<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * integerLength�ɐ��l���w�肳��Ă���AintegerLength�̐��l���A
     * bean�̐������̌����傫���Ƃ��Afalse���ԋp����邱�ƁA
     * �G���[���b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber14() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("1");
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertEquals(1, errors.size());

        // �G���[�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"12.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="true"<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * integerLength�ɐ��l�AisAccordedInteger��true���w�肳��Ă���A
     * integerLength�̐��l���bean�̐������̌����������Ƃ��A
     * false���ԋp����邱�ƁA�G���[���b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber15() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "12.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // ������������v�w��
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("true");
        field.addVar(varIsAccordedInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertEquals(1, errors.size());

        // �G���[�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.123"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="true"<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * integerLength�ɐ��l�AisAccordedInteger��true���w�肳��Ă���A
     * integerLength�̐��l�ƁAbean�̐������̌����������Ƃ��A
     * true���ԋp����邱�ƁA�G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber16() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "123.123";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // ������������v�w��
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("true");
        field.addVar(varIsAccordedInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber17()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:String:"12.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger=""<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * integerLength�ɐ��l�AisAccordedInteger�ɋ󕶎����w�肳��Ă���A
     * integerLength�̐��l���Abean�̐������̌����������Ƃ��A
     * true���ԋp����邱�ƁA�G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber17() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "12.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // ������������v�w��
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("");
        field.addVar(varIsAccordedInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber18()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"12.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger=null<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * integerLength�ɐ��l�AisAccordedInteger��null���w�肳��Ă���A
     * integerLength�̐��l���Abean�̐������̌����������Ƃ��A
     * true���ԋp����邱�ƁA�G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber18() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "12.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // ������������v�w��
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue(null);
        field.addVar(varIsAccordedInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber19()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"12.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="abc"<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * integerLength�ɐ��l�AisAccordedInteger��true�ł͂Ȃ������񂪎w��
     * ����Ă���AintegerLength�̐��l���Abean�̐������̌����������Ƃ��A
     * true���ԋp����邱�ƁA�G���[���b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber19() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "12.12";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // ������������v�w��
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("abc");
        field.addVar(varIsAccordedInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue(null);
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��邱�ƁB
        assertTrue(errors.isEmpty());
    }

//    /**
//     * testValidateNumber20()
//     * <br><br>
//     *
//     * (�ُ�n)
//     * <br>
//     * �ϓ_�FF
//     * <br><br>
//     * ���͒l�F(����) bean:String:"abc"<br>
//     *         (����) va:not null<br>
//     *         (����) field:not null<br>
//     *                var:integerLength="2"<br>
//     *                var:scale=null<br>
//     *                Msg("message","message")<br>
//     *         (����) errors:not null<br>
//     *                (��̗v�f)<br>
//     *         (����) validator:not null<br>
//     *         (����) request:not null<br>
//     *
//     * <br>
//     * ���Ғl�F(�߂�l) boolean:true<br>
//     *         (��ԕω�) errors:ActionMessage("message")<br>
//     *
//     * <br>
//     * integerLength�ɐ��l���w�肳��Ă���A������bean�����l�ɕϊ��ł��Ȃ��ꍇ�Afalse���ԋp����邱�ƁB�G���[���b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
//     * <br>
//     *
//     * @throws Exception ���̃��\�b�h�Ŕ���������O
//     */
//    public void testValidateNumber20() throws Exception {
//        //�e�X�g�P�[�X�폜
//    }

    /**
     * testValidateNumber21()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"100.01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="3"<br>
     *                var:isAccordedInteger="true"<br>
     *                var:scale="2"<br>
     *                var:isAccordedScale="true"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * isAccordedInteger,isAccordedScale���Ƃ���true�̂Ƃ��A
     * integerLength�Ɛ������Ascale�Ə������̌�������v���Ă�����
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber21() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "100.01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("3");
        field.addVar(varInteger);
        // ������������v�w��
        Var varIsAccordedInteger = new Var();
        varIsAccordedInteger.setName("isAccordedInteger");
        varIsAccordedInteger.setValue("true");
        field.addVar(varIsAccordedInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("2");
        field.addVar(varScale);
        // ������������v�ݒ�
        Var varIsAccordedScale = new Var();
        varIsAccordedScale.setName("isAccordedScale");
        varIsAccordedScale.setValue("true");
        field.addVar(varIsAccordedScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber22()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"100.01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="4"<br>
     *                var:scale="3"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * �������̌���integerLength�Ŏw�肳�ꂽ�l��菬�����A
     * ��������scale�Ŏw�肳�ꂽ�l��菬�����ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber22() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "100.01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("4");
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("3");
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber23()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map<br>
     *                ["field1"="100.01"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="4"<br>
     *                var:scale="4"<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * bean��String�^�ł͂Ȃ��Ƃ��Afield��property�����Ŏw�肳�ꂽ
     * �v���p�e�B�ɑ΂��Č��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber23() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "100.01");

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field1");
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("4");
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("4");
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[��񂪒ǉ�����Ă��Ȃ�����
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber24()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map<br>
     *                ["field1"="100.01"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="2"<br>
     *                var:scale="1"<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * bean��String�^�ł͂Ȃ��Ƃ��Afield��property�����Ŏw�肳�ꂽ
     * �v���p�e�B�ɑ΂��Č��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber24() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "100.01");

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field1");
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("2");
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("1");
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[��񂪒ǉ�����Ă��邱��
        assertEquals(1, errors.size());

        // �G���[�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateNumber25()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map<br>
     *                ["field1"="100.01"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength="2"<br>
     *                var:scale="1"<br>
     *                property="field2"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * bean��String�^�ł͂Ȃ��Ƃ��Afield��property�����Ŏw�肳�ꂽ
     * �v���p�e�B�ɂ����݂��Ȃ��ꍇ�́Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber25() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "100.01");

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field2");
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �������ݒ�
        Var varInteger = new Var();
        varInteger.setName("integerLength");
        varInteger.setValue("2");
        field.addVar(varInteger);
        // �������ݒ�
        Var varScale = new Var();
        varScale.setName("scale");
        varScale.setValue("1");
        field.addVar(varScale);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[��񂪒ǉ�����Ă��邱��
        assertTrue(errors.isEmpty());
    }

    /**
     * testValidateNumber03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"123.12"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:integerLength=null<br>
     *                var:scale=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * �S�p�̐��l�����͂��ꂽ�ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumber26() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "�P�Q�R";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("integerLength");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("scale");
        var.setValue(null);
        field.addVar(var);

        // �G���[���
        ActionMessages errors = new ActionMessages();

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumber(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[��񂪒ǉ�����Ă��邱��
        assertEquals(1, errors.size());

        // �G���[�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

}
