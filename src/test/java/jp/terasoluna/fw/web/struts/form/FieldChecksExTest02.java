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
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Validator�ǉ����[���N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest02 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest02.class);
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
    public FieldChecksExTest02(String name) {
        super(name);
    }

    /**
     * testValidateCapAlphaNumericString01()
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
     *                field:var<br>
     *                name:"mask"<br>
     *                value="^[a-z]*$"<br>
     *                jsType="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name:"mask"<br>
     *                    value="^[a-z]*$"<br>
     *                    jsType="false"<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean��null�̂Ƃ��Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateCapAlphaNumericString01() throws Exception {
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
        msg.setResource(false);
        field.addMsg(msg);
        field.addVar("mask", "^[a-z]*$", "false");
        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
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
        // field�̒��g�`�F�b�N
        Var var = field.getVar("mask");
        assertNotNull(var);
        assertEquals("mask", var.getName());
        assertEquals("^[a-z]*$", var.getValue());
        assertEquals("false", var.getJsType());
    }

    /**
     * testValidateCapAlphaNumericString02()
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
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean���󕶎��̂Ƃ��Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateCapAlphaNumericString02() throws Exception {
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
        msg.setResource(false);
        field.addMsg(msg);
        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
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
        // field�̒��g�`�F�b�N
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateCapAlphaNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"ABC0"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean���啶���̔��p�p�������݂̂ō\������Ă���ꍇ�A
     * true���ԋp����Aerrors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateCapAlphaNumericString03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "ABC0";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setResource(false);
        field.addMsg(msg);

        // �G���[���
        ActionMessages errors = new ActionMessages();

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
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
        // field�̒��g�`�F�b�N
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateCapAlphaNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:HashMap<br>
     *                ["field1"="Aa0"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * ������bean��String�^�ł͂Ȃ��ꍇ�A
     * field����擾�����v���p�e�B�̒l�ɑ΂��ă`�F�b�N���s���邱�Ƃ��m�F����B
     * ���̓`�F�b�N�G���[�ƂȂ����Ƃ��Aerrors�Ƀ��b�Z�[�W���ǉ�����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateCapAlphaNumericString04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "Aa0");

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

        // �G���[���iActionMessage��1���ݒ�j
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateCapAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g��2�o�^����Ă��邱�ƁB
        assertEquals(2, errors.size());
        // �G���[���ɐݒ肳�ꂽ���b�Z�[�W���o�^����Ă��邱�ƁB
        // ������ActionMessage���㏑������Ȃ����ƁB
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage retError = (ActionMessage) it.next();
            list.add(retError.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
        // field�̒��g�`�F�b�N
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateHankakuKanaString01()
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
     * ������bean��null�̂Ƃ��A
     * �G���[���O���o�͂���true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString01() throws Exception {
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
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString02()
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
     * ������bean���󕶎��̂Ƃ��Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString02() throws Exception {
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
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"�ݶ�"<br>
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
     * ������bean�����p�J�i�����݂̂ō\������Ă���Ƃ��A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "�ݶ�";
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

        // �G���[���
        ActionMessages errors = new ActionMessages();

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"1a��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
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
     * ������bean�ɔ��p�J�i�ȊO�̕����񂪊܂܂�Ă���ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "1a��";
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

        // �G���[���iActionMessage��1���ݒ�j
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g��2�o�^����Ă��邱�ƁB
        assertEquals(2, errors.size());
        // �G���[���ɐݒ肳�ꂽ���b�Z�[�W���o�^����Ă��邱�ƁB
        // ������ActionMessage���㏑������Ȃ����ƁB
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage retError = (ActionMessage) it.next();
            list.add(retError.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
    }

    /**
     * testValidateHankakuKanaString05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"�ݶ��A"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
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
     * ������bean�ɔ��p�J�i�ȊO�̕����񂪊܂܂�Ă���ꍇ�A�G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "�ݶ��A";
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

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
        assertFalse(errors.isEmpty());
        // �G���[���ɐݒ肳�ꂽ���b�Z�[�W���o�^����Ă��邱�ƁB
        Iterator it = errors.get();
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
        // �G���[��1���ł��邱��
        assertFalse(it.hasNext());
    }

    /**
     * testValidateHankakuKanaString06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="�����"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
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
     * ������bean��String�^�ł͂Ȃ��ꍇ�A
     * field����擾�������O�̃v���p�e�B�ɑ΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "�����");

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

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateHankakuKanaString07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="123"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
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
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�������O�̃v���p�e�B��
     * �΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "123");

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

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
        assertFalse(errors.isEmpty());
        // �G���[���ɐݒ肳�ꂽ���b�Z�[�W���o�^����Ă��邱�ƁB
        Iterator it = errors.get();
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
        // �G���[��1���ł��邱��
        assertFalse(it.hasNext());
    }

    /**
     * testValidateHankakuKanaString08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="123"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
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
     * ������bean��String�^�ł͂Ȃ��Afield����擾�������O�̃v���p�e�B�����݂��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateHankakuKanaString08() throws Exception {
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
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateHankakuKanaString(
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
     * testValidateNumericString01()
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
     *                field:var<br>
     *                name:"mask"<br>
     *                value="^[a-z]*$"<br>
     *                jsType="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name:"mask"<br>
     *                    value="^[a-z]*$"<br>
     *                    jsType="false"<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean��null�̂Ƃ��Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumericString01() throws Exception {
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
        field.addVar("mask", "^[a-z]*$", "false");
        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumericString(
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
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNotNull(var);
        assertEquals("mask", var.getName());
        assertEquals("^[a-z]*$", var.getValue());
        assertEquals("false", var.getJsType());
    }

    /**
     * testValidateNumericString02()
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
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean���󕶎��̂Ƃ��Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumericString02() throws Exception {
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
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumericString(
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
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"9876"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean���������݂̂ō\������Ă���ꍇ�Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumericString03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "9876";
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

        // �G���[���
        ActionMessages errors = new ActionMessages();

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumericString(
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
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:HashMap<br>
     *                ["field1"="Aa0"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�����v���p�e�B�̒l�ɑ΂���
     * �`�F�b�N���s���邱�Ƃ��m�F����B
     * ���̓`�F�b�N�G���[�ƂȂ����Ƃ��Aerrors�Ƀ��b�Z�[�W���ǉ�����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateNumericString04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "Aa0");

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

        // �G���[���iActionMessage��1���ݒ�j
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g��2�o�^����Ă��邱�ƁB
        assertEquals(2, errors.size());
        // �G���[���ɐݒ肳�ꂽ���b�Z�[�W���o�^����Ă��邱�ƁB
        // ������ActionMessage���㏑������Ȃ����ƁB
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage retError = (ActionMessage) it.next();
            list.add(retError.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNull(var);
    }

}
