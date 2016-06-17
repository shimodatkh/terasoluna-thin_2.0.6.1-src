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

import jp.terasoluna.fw.util.ClassLoadException;
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
public class FieldChecksExTest08 extends PropertyTestCase {

    /**
     * validateMultiField�̈����Ɏg�p����Bean�B
     */
    FieldChecksEx_BeanStub01 javaBean = new FieldChecksEx_BeanStub01();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest08.class);
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
        FieldChecksEx_MultiFieldValidatorImpl01.value = null;
        FieldChecksEx_MultiFieldValidatorImpl01.fields = null;
        FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount = 0;
        FieldChecksEx_MultiFieldValidatorImpl01.result = false;
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
    public FieldChecksExTest08(String name) {
        super(name);
        this.javaBean.setField1("abc");
        this.javaBean.setField2("def");
        this.javaBean.setField3("ghi");
        this.javaBean.setField4("jkl");
    }

    /**
     * testValidateByteRange01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:maxByte=null<br>
     *                var:minByte=null<br>
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
     * ������bean��null�̂Ƃ��A�G���[���O���o�͂���true���ԋp����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue(null);
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:maxByte="1"<br>
     *                var:minByte="4"<br>
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
     * ������bean���󕶎��̂Ƃ��Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="5"<br>
     *                encoding="UTF-8"<br>
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
     * ������bean�̃o�C�g����field��minByte�AmaxByte�͈̔͂ɂ���Ƃ��A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "��";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("5");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="4"<br>
     *                var:maxByte="8"<br>
     *                encoding="Windows-31J"<br>
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
     * ������bean�̃o�C�g����field��minByte�AmaxByte�͈̔͊O�̂Ƃ��A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "��";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("8");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("Windows-31J");
        field.addVar(var);
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
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
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���2���ł��邱�ƁB
        assertEquals(2, errors.size());

        // ���b�Z�[�W�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
    }

    /**
     * testValidateByteRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="4"<br>
     *                var:maxByte="8"<br>
     *                encoding="UTF-8"<br>
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
     * ������bean�̃o�C�g����field��minByte�AmaxByte�͈̔͊O�̂Ƃ��A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "������";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("8");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
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
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���2���ł��邱�ƁB
        assertEquals(2, errors.size());

        // ���b�Z�[�W�I�u�W�F�N�g�̌���
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
    }

    /**
     * testValidateByteRange06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"������"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte=null<br>
     *                var:maxByte=null<br>
     *                encoding=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��minByte�AmaxByte��null�̂Ƃ��A���ꂼ��A0�AInteger.MAX_VALUE
     * �Ƃ��Čv�Z���s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "������";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue(null);
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"aaaaaa"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte=""<br>
     *                var:maxByte=""<br>
     *                encoding=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��minByte�AmaxByte���󕶎��̂Ƃ��A���ꂼ��A0�AInteger.MAX_VALUE
     * �Ƃ��Čv�Z���s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "aaaaaa";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="abc"<br>
     *                var:maxByte="def"<br>
     *                encoding="test-encoding"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F""<br>
     *                    ��O�FNumberFormatException<br>
     *                    ���O���x���F�x��<br>
     *                    ���b�Z�[�W�F<br>
     *                    "test-encoding is not supported."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��minByte�AmaxByte�����l�ɕϊ��ł��Ȃ��Ƃ��A���ꂼ��A0�A
     * Integer.MAX_VALUE�Ƃ��Čv�Z���s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "��";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("abc");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("def");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("test-encoding");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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

        // �G���[���O�`�F�b�N
        assertTrue(LogUTUtil.checkError("", new NumberFormatException()));

        // �x�����O�`�F�b�N
        assertTrue(LogUTUtil.checkWarn("test-encoding is not supported."));
    }

    /**
     * testValidateByteRange09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"����"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="8"<br>
     *                var:maxByte="4"<br>
     *                encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * field��minByte�̒l���AmaxByte�̒l���������Ƃ��Afalse���ԋp����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "����";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("8");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���1���ł��邱�ƁB
        assertEquals(1, errors.size());

        // �G���[�I�u�W�F�N�g�̃`�F�b�N
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateByteRange10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"����"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="4"<br>
     *                var:maxByte="4"<br>
     *                encoding="Windows-31J"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��minByte��maxByte�̒l����v���A������bean�̌��������̒l�ƈ�v����
     * �ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "����";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("4");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("Windows-31J");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="����"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="10"<br>
     *                var:encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *                property����="field1"<br>
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
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�������O�̃v���p�e�B��
     * �΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange11() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "����");
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("10");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="1234"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="3"<br>
     *                var:encoding="Windows-31J"<br>
     *                Msg("message","message")<br>
     *                property����="field1"<br>
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
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�������O�̃v���p�e�B��
     * �΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange12() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "1234");
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("3");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("Windows-31J");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���1���ł��邱�ƁB
        assertEquals(1, errors.size());

        // �G���[�I�u�W�F�N�g�̃`�F�b�N
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateByteRange13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="123"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="1"<br>
     *                var:maxByte="10"<br>
     *                var:encoding="UTF-8"<br>
     *                Msg("message","message")<br>
     *                property����="field2"<br>
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
     * ������bean��String�^�ł͂Ȃ��Afield����擾�������O�̃v���p�e�B��
     * ���݂��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange13() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "123");
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field2");
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("10");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("1");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("UTF-8");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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
     * testValidateByteRange14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"aaaaaa"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte=""<br>
     *                var:maxByte="abc"<br>
     *                encoding=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F""<br>
     *                    ��O�FNumberFormatException<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��minByte���󕶎��ŁAmaxByte�����l�ɕϊ��ł��Ȃ��Ƃ��A
     * ���ꂼ��A0�AInteger.MAX_VALUE�Ƃ��Čv�Z���s���A�G���[���O��
     * �o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange14() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "aaaaaa";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("abc");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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

        // �G���[���O�`�F�b�N
        assertTrue(LogUTUtil.checkError("", new NumberFormatException()));
    }
    
    /**
     * testValidateByteRange15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"bbbbb"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:minByte="def"<br>
     *                var:maxByte=""<br>
     *                encoding=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F""<br>
     *                    ��O�FNumberFormatException<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��minByte�����l�ɕϊ��ł����AmaxByte���󕶎��̂Ƃ��A
     * ���ꂼ��A0�AInteger.MAX_VALUE�Ƃ��Čv�Z���s���A�G���[���O��
     * �o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateByteRange15() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "bbbbb";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("maxByte");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("minByte");
        var.setValue("def");
        field.addVar(var);
        var = new Var();
        var.setName("encoding");
        var.setValue("");
        field.addVar(var);
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
            FieldChecksEx.validateByteRange(
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

        // �G���[���O�`�F�b�N
        assertTrue(LogUTUtil.checkError("", new NumberFormatException()));
    }
    
    /**
     * testValidateMultiField01()
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
     *         (��ԕω�) MultiFieldValidator:�Ăяo����Ȃ�����<br>
     *         (��ԕω�) ���O:���O���x���F<br>
     *                    �G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    bean is null.<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ������bean��null�̂Ƃ��A�G���[���O���o�͂���true���ԋp����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField01() throws Exception {
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
            FieldChecksEx.validateMultiField(
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

//    /**
//     * testValidateMultiField02()
//     * <br><br>
//     *
//     * (����n)
//     * <br>
//     * �ϓ_�FC,F
//     * <br><br>
//     * ���͒l�F(����) bean:""<br>
//     *         (����) va:not null<br>
//     *         (����) field:not null<br>
//     *                Msg("message","message")<br>
//     *         (����) errors:not null<br>
//     *                (��̗v�f)<br>
//     *         (����) validator:not null<br>
//     *         (����) request:not null<br>
//     *
//     * <br>
//     * ���Ғl�F(�߂�l) boolean:true<br>
//     *         (��ԕω�) MultiFieldValidator:�Ăяo����Ȃ�����<br>
//     *         (��ԕω�) errors:not null<br>
//     *                    (��̗v�f)<br>
//     *
//     * <br>
//     * ������bean���󕶎��̂Ƃ��Atrue���ԋp����邱�Ƃ��m�F����B
//     * <br>
//     *
//     * @throws Exception ���̃��\�b�h�Ŕ���������O
//     */
//    public void testValidateMultiField02() throws Exception {
//        // �e�X�g�P�[�X�폜
//    }

    /**
     * testValidateMultiField03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) MultiFieldValidator:�Ăяo����Ȃ�����<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * bean���󕶎��̂Ƃ��A�`�F�b�N���������s����邱�ƁB<br>
     * field��multiFieldValidator��null�̂Ƃ��AIllegalArgumentException��
     * �������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue(null);
        field.addVar(var);
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
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is required.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is required."));
        }
    }

    /**
     * testValidateMultiField04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) MultiFieldValidator:�Ăяo����Ȃ�����<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is required."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��multiFieldValidator���󕶎��̂Ƃ��A
     * IllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("");
        field.addVar(var);
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
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is required.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is required."));
        }
    }

    /**
     * testValidateMultiField05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) MultiFieldValidator:�Ăяo����Ȃ�����<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *                    ��O�F<br>
     *                    ClassLoadException(ClassNotFoundException)
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��multiFieldValidator���N���X�p�X��ɑ��݂��Ȃ��N���X���̂Ƃ��A
     * IllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("abc");
        field.addVar(var);
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
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is invalid.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is invalid.",
                    new ClassLoadException(new ClassNotFoundException())));
        }
    }

    /**
     * testValidateMultiField06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF,G
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator="java.lang.String"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) MultiFieldValidator:�Ăяo����Ȃ�����<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "var value[multiFieldValidator] is invalid."<br>
     *                    ��O�F<br>
     *                    ClassCastException<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��multiFieldValidator��MultiFieldValidator���������Ă��Ȃ��N���X����
     * �Ƃ��AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("java.lang.String");
        field.addVar(var);
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
        try {
            FieldChecksEx.validateMultiField(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(errors.isEmpty());
            assertEquals("var value[multiFieldValidator] is invalid.",
                    e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "var value[multiFieldValidator] is invalid.",
                    new ClassCastException()));
        }
    }

    /**
     * testValidateMultiField07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldChecksEx_MultiFieldValidatorImpl01"<br>
     *                var:fields=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) MultiFieldValidator.validate():true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) MultiFieldValidator:������"abc",�v�f0�̔z��ŌĂяo����邱�ƁB<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��fields��null�̏ꍇ�AMultiFieldValidator�̑������ɋ�̔z��
     * �n����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue(null);
        field.addVar(var);
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

        // MultiFieldValidator�̖߂�l��ݒ�
        FieldChecksEx_MultiFieldValidatorImpl01.result = true;

        // �e�X�g���s
        boolean result = FieldChecksEx.validateMultiField(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // ���ʊm�F
        assertTrue(result);
        assertTrue(errors.isEmpty());

        // MultiFieldValidator�̌Ăяo���A�����m�F
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(0, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);

    }

    /**
     * testValidateMultiField08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldChecksEx_MultiFieldValidatorImpl01"<br>
     *                var:fields=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) MultiFieldValidator.validate():true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) MultiFieldValidator:������"abc",�v�f0�̔z��ŌĂяo����邱�ƁB<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��fields��null�̏ꍇ�AMultiFieldValidator�̑������ɋ�̔z��
     * �n����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue("");
        field.addVar(var);
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

        // MultiFieldValidator�̖߂�l��ݒ�
        FieldChecksEx_MultiFieldValidatorImpl01.result = true;

        // �e�X�g���s
        boolean result = FieldChecksEx.validateMultiField(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // ���ʊm�F
        assertTrue(result);
        assertTrue(errors.isEmpty());

        // MultiFieldValidator�̌Ăяo���A�����m�F
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(0, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);

    }

    /**
     * testValidateMultiField09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Bean<br>
     *                field1="abc"<br>
     *                field2="def"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldCheckEx_MultiFieldValidatorImpl01"<br>
     *                var:fields="field2"<br>
     *                var:property="field1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) MultiFieldValidator.validate():true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) MultiFieldValidator:������"abc",<br>
     *                    �z��{"def"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * fields�ɃJ���}��؂薳���̕����񂪎w�肳��Ă���ꍇ�A
     * ���̖��O�̃v���p�e�B�l��������bean����擾���A
     * ����1�̔z��Ƃ���MultiFieldValidator�̑������ɓn����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue("field2");
        field.addVar(var);
        field.setProperty("field1");
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

        // MultiFieldValidator�̖߂�l��ݒ�
        FieldChecksEx_MultiFieldValidatorImpl01.result = true;

        // �e�X�g���s
        boolean result = FieldChecksEx.validateMultiField(
                javaBean,
                va,
                field,
                errors,
                validator,
                request);

        // ���ʊm�F
        assertTrue(result);
        assertTrue(errors.isEmpty());

        // MultiFieldValidator�̌Ăяo���A�����m�F
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(1, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);
        assertEquals("def", FieldChecksEx_MultiFieldValidatorImpl01.fields[0]);

    }

    /**
     * testValidateMultiField10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Bean<br>
     *                field1="abc"<br>
     *                field2="def"<br>
     *                field3="ghi"<br>
     *                field4="jkl"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:multiFieldValidator=<br>
     *                "jp.terasoluna.fw.web.struts.form.FieldCheckEx_MultiFieldValidatorImpl01"<br>
     *                var:fields="field2 ,,test,field3,field4"<br>
     *                var:property="field1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) MultiFieldValidator.validate():false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) MultiFieldValidator:������"abc",<br>
     *                    �z��{"def",null,"ghi","jkl"}<br>
     *                    �ŌĂяo����邱�ƁB<br>
     *         (��ԕω�) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * fields�ɃJ���}��؂�̕����񂪎w�肳��Ă���ꍇ�A
     * �J���}�ŋ�؂�ꂽ�S�Ă̖��O�̃v���p�e�B�l��������bean����擾���A
     * �z��Ƃ���MultiFieldValidator�̑������ɓn����邱�Ƃ��m�F����B<br>
     * MultiFieldValidator��validate���\�b�h��false��ԋp����ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateMultiField10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("multiFieldValidator");
        var.setValue("jp.terasoluna.fw.web.struts.form." +
                "FieldChecksEx_MultiFieldValidatorImpl01");
        field.addVar(var);
        var = new Var();
        var.setName("fields");
        var.setValue("field2 ,,test,field3,field4");
        field.addVar(var);
        field.setProperty("field1");
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);
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

        // MultiFieldValidator�̖߂�l��ݒ�
        FieldChecksEx_MultiFieldValidatorImpl01.result = false;

        // �e�X�g���s
        boolean result = FieldChecksEx.validateMultiField(
                javaBean,
                va,
                field,
                errors,
                validator,
                request);

        // ���ʊm�F
        assertFalse(result);
        assertEquals(2, errors.size());

        // ���b�Z�[�W�I�u�W�F�N�g�̃`�F�b�N
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            list.add(message.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));

        // MultiFieldValidator�̌Ăяo���A�����m�F
        assertEquals(1,
                FieldChecksEx_MultiFieldValidatorImpl01.validateCalledCount);
        assertEquals("abc", FieldChecksEx_MultiFieldValidatorImpl01.value);
        assertEquals(4, FieldChecksEx_MultiFieldValidatorImpl01.fields.length);
        assertEquals("def", FieldChecksEx_MultiFieldValidatorImpl01.fields[0]);
        assertNull(FieldChecksEx_MultiFieldValidatorImpl01.fields[1]);
        assertEquals("ghi", FieldChecksEx_MultiFieldValidatorImpl01.fields[2]);
        assertEquals("jkl", FieldChecksEx_MultiFieldValidatorImpl01.fields[3]);

    }

}
