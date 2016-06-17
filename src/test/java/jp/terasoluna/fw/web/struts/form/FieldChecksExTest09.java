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
import java.util.Locale;
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
public class FieldChecksExTest09 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest09.class);
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
    public FieldChecksExTest09(String name) {
        super(name);
    }

    /**
     * testValidateDateRange01()
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
     *                Locale=JAPANESE<br>
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
    public void testValidateDateRange01() throws Exception {
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange02()
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
     *                Locale=JAPANESE<br>
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
    public void testValidateDateRange02() throws Exception {
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * datePattern�AdatePatternStrict���w�肵�Ă��炸�A
     * ������bean��Date�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
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
     * testValidateDateRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=""<br>
     *                var:datePatternStrict=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern�AdatePatternStrict�ɋ󕶎����w�肳��Ă��āA
     * ������bean��Date�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern���w�肳��Ă��炸�AdatePatternStrict�ɐ���ȓ��t�t�H�[�}�b�g
     * ���w�肳��Ă��āA������bean��Date�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) bean:"2005/10/24"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) ���O:���O���x���F<br>
     *                    �G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    Illegal pattern character 'b'<br>
     *                    ��O�F<br>
     *                    IllegalArgumentException<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * ������bean������ȓ��t��\��������ŁAdatePattern���w�肳��Ă��炸�A
     * datePatternStrict�ɕs���ȓ��t�t�H�[�}�b�g���w�肳��Ă���ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/10/24";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());

        // �G���[���O�`�F�b�N
        assertTrue(LogUTUtil.checkError("Illegal pattern character 'b'",
                new IllegalArgumentException()));
    }

    /**
     * testValidateDateRange07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/02/29"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * ������bean�����݂��Ȃ����t��\��������ŁAdatePattern���w�肳��Ă��炸�A
     * datePatternStrict�ɕs���ȓ��t�t�H�[�}�b�g���w�肳��Ă���ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/02/29";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/2/28"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * ������bean������ȓ��t��\��������ŁAdatePattern���w�肳��Ă��炸�A
     * datePatternStrict�Ɏw�肳�ꂽ���t�t�H�[�}�b�g��bean�̃t�H�[�}�b�g��
     * ���S�Ɉ�v���Ȃ��ꍇ�A�G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/2/28";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"abc"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern�ɐ���ȓ��t�t�H�[�}�b�g���w�肳��Ă��āA
     * ������bean��Date�ɕϊ��ł��Ȃ��ꍇ�A�G���[���b�Z�[�W��ǉ�����false��
     * �ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/10/24"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="abc"<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) ���O:���O���x���F<br>
     *                    �G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    Illegal pattern character 'b'<br>
     *                    ��O�F<br>
     *                    IllegalArgumentException<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * ������bean������ȓ��t��\��������ŁAdatePattern�ɕs����
     * ���t�t�H�[�}�b�g���w�肳��Ă���ꍇ�A�G���[���b�Z�[�W��ǉ�����false��
     * �ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/10/24";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("abc");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());

        // �G���[���O�`�F�b�N
        assertTrue(LogUTUtil.checkError("Illegal pattern character 'b'",
                new IllegalArgumentException()));
    }

    /**
     * testValidateDateRange11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/02/29"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * ������bean�����݂��Ȃ����t��\��������ŁAdatePattern�ɕs����
     * ���t�t�H�[�}�b�g���w�肳��Ă���ꍇ�A�G���[���b�Z�[�W��ǉ�����
     * false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange11() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/02/29";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/2/28"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate=null<br>
     *                var:endDate=null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * datePattern��datePatternStrict�������w�肳��Ă���ꍇ�A
     * datePattern�Ɏw�肵���t�H�[�}�b�g���D�悳��邱�ƁA<br>
     * field��startDate��endDate��null�̏ꍇ�Atrue���擾�ł��邱�ƁA���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange12() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/2/28";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/2/28"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate=""<br>
     *                var:endDate=""<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * datePattern��datePatternStrict�������w�肳��Ă���ꍇ�A
     * datePattern�Ɏw�肵���t�H�[�}�b�g���D�悳��邱�ƁA<br>
     * field��startDate��endDate���󕶎��̏ꍇ�Atrue���擾�ł��邱�ƁA
     * ���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange13() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/2/28";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern�AdatePatternStrict���w�肵�Ă��炸�A
     * ������bean��Date�ɕϊ��ł��Afield��startDate�����t�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange14() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange15()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/02/29"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternStrict�݂̂ɐ���ȓ��t�t�H�[�}�b�g���w�肳��A
     * ������bean��Date�ɕϊ��ł��Afield��startDate�����t�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange15() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/02/29");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange16()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/2/28"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternStrict�݂̂ɐ���ȓ��t�t�H�[�}�b�g���w�肳��A
     * ������bean��Date�ɕϊ��ł��Afield��startDate�����t�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange16() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/2/28");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange17()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePatternStrict�݂̂ɐ���ȓ��t�t�H�[�}�b�g���w�肳��A
     * ������bean��Date�ɕϊ��ł��Afield��startDate�����t�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange17() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange18()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern�AdatePatternStrict�ɐ���ȓ��t�t�H�[�}�b�g���w�肳��A
     * ������bean��Date�ɕϊ��ł��Afield��startDate�����t�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange18() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange19()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/1/2"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * bean�̓��t���AstartDate�Ŏw�肳�ꂽ���t���O�̏ꍇ�A
     * �G���[���b�Z�[�W��ǉ����āAfalse��ԋp���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange19() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/2");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange20()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="2004/01/01"<br>
     *                var.endDate="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern�AdatePatternStrict��null��endDate�����t�ɕϊ��ł��Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange20() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange21()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="2005/02/29"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern��null�AdatePatternStrict������ȓ��t�t�H�[�}�b�g�ŁA
     * endDate�����݂��Ȃ����t�̏ꍇ�A�G���[���b�Z�[�W��ǉ�����false��
     * �ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange21() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/02/29");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange22()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="2005/2/28"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern��null�AdatePatternStrict������ȓ��t�t�H�[�}�b�g�ŁA
     * endDate�̓��t�t�H�[�}�b�g�Ɗ��S�Ɉ�v���Ȃ��ꍇ�A
     * �G���[���b�Z�[�W��ǉ�����false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange22() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/2/28");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange23()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern��null�AdatePatternStrict������ȓ��t�t�H�[�}�b�g�ŁA
     * endDate�����t�ł͂Ȃ��ꍇ�A�G���[���b�Z�[�W��ǉ�����false���ԋp�����
     * ���Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange23() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange24()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="abc"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * datePattern�AdatePatternStrict�ɐ���ȓ��t�t�H�[�}�b�g���w�肳��A
     * field��endDate�����t�ɕϊ��ł��Ȃ��ꍇ�A�G���[���b�Z�[�W��ǉ����A
     * false���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange24() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange25()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/02"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2004/01/01"<br>
     *                var:endDate="2005/1/1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * bean�̓��t���AendDate�Ŏw�肳�ꂽ���t����̏ꍇ�A
     * �G���[���b�Z�[�W��ǉ����āAfalse��ԋp���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange25() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/02";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2004/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/1");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

        // ���b�Z�[�W�I�u�W�F�N�g�m�F
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange26()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/02"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="2005/1/1"<br>
     *                var:endDate="2005/1/3"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * datePattern�AdatePatternStrict��null��bean�̓��t��startDate��endDate��
     * �Ԃɂ���ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange26() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/02";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/1");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/3");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange27()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/1/2"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern="yyyy/MM/dd"<br>
     *                var:datePatternStrict="yyyy.MM.dd"<br>
     *                var:startDate="2005/1/1"<br>
     *                var:endDate="2005/1/3"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * datePattern�AdatePatternStrict�Ƃ��Ƀt�H�[�}�b�g���w�肳��Ă���ꍇ�A
     * datePattern�̃t�H�[�}�b�g���K�p����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange27() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/1/2";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy.MM.dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/1");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/3");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange28()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/02"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * datePattern��null�AdatePatternStrict�ɐ���ȃt�H�[�}�b�g���w�肳��Ă���
     * �ꍇ�ŁAbean�̓��t��startDate��endDate�̊Ԃɂ���ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange28() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/02";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange29()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"2005/01/01"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict=null<br>
     *                var:startDate="2005/1/1"<br>
     *                var:end]Date="2005/1/1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * bean�̓��t�ƁAstartDate�AendDate����v����ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange29() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "2005/01/01";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/1/1");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/1/1");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange30()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:<br>
     *                ["field1"=<br>
     *                �@"2005/01/02"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *                property="field1"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�������O�̃v���p�e�B�ɑ΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange30() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "2005/01/02");
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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
     * testValidateDateRange31()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:<br>
     *                ["field1"=<br>
     *                �@"2005/01/04"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *                property="field1"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message")<br>
     *
     * <br>
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�������O�̃v���p�e�B�ɑ΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange31() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "2005/01/04");
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field1");
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[��񂪐ݒ肳��Ă��邱�ƁB
        assertEquals(1, errors.size());

        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
    }

    /**
     * testValidateDateRange32()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:<br>
     *                ["field1"=<br>
     *                �@"2005/01/05"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:datePattern=null<br>
     *                var:datePatternStrict="yyyy/MM/dd"<br>
     *                var:startDate="2005/01/01"<br>
     *                var:endDate="2005/01/03"<br>
     *                Msg("message","message")<br>
     *                property="field2"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *                Locale=JAPANESE<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ������bean��String�^�ł͂Ȃ��Afield����擾�������O�̃v���p�e�B�����݂��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateDateRange32() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "2005/01/05");
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field2");
        Var var = new Var();
        var.setName("datePattern");
        var.setValue(null);
        field.addVar(var);
        var = new Var();
        var.setName("datePatternStrict");
        var.setValue("yyyy/MM/dd");
        field.addVar(var);
        var = new Var();
        var.setName("startDate");
        var.setValue("2005/01/01");
        field.addVar(var);
        var = new Var();
        var.setName("endDate");
        var.setValue("2005/01/03");
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
        request.setLocale(Locale.JAPANESE);
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateDateRange(
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

}
