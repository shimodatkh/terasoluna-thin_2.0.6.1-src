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
public class FieldChecksExTest04 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest04.class);
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
    public FieldChecksExTest04(String name) {
        super(name);
    }

    /**
     * testValidateZenkakuKanaString01()
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
     * ������bean��null�̂Ƃ��A�G���[���O���o�͂���true���ԋp����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString01() throws Exception {
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
            FieldChecksEx.validateZenkakuKanaString(
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
     * testValidateZenkakuKanaString02()
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
    public void testValidateZenkakuKanaString02() throws Exception {
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
            FieldChecksEx.validateZenkakuKanaString(
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
     * testValidateZenkakuKanaString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"�[���J�N"<br>
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
     * ������bean���S�p�J�i�����݂̂ō\������Ă���Ƃ��A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "�[���J�N";
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
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateZenkakuKanaString(
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
     * testValidateZenkakuKanaString04()
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
     * ������bean�ɑS�p�J�i�ȊO�̕����񂪊܂܂�Ă���ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString04() throws Exception {
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
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateZenkakuKanaString(
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
     * testValidateZenkakuKanaString05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"�[���J�N�"<br>
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
     * ������bean�ɑS�p�J�i�ȊO�̕����񂪊܂܂�Ă���ꍇ�A�G���[���b�Z�[�W��
     * �ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "�[���J�N�";
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
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateZenkakuKanaString(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }

    /**
     * testValidateZenkakuKanaString06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="�@�B�D�F�H"]<br>
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
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�������O�̃v���p�e�B��
     * �΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "�@�B�D�F�H");

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
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateZenkakuKanaString(
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
     * testValidateZenkakuKanaString07()
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
    public void testValidateZenkakuKanaString07() throws Exception {
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
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateZenkakuKanaString(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }

    /**
     * testValidateZenkakuKanaString08()
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
     * ������bean��String�^�ł͂Ȃ��Afield����擾�������O�̃v���p�e�B��
     * ���݂��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateZenkakuKanaString08() throws Exception {
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
        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateZenkakuKanaString(
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
     * testValidateProhibited01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars=null<br>
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
    public void testValidateProhibited01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("chars");
        var.setValue("a");
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars=null<br>
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
    public void testValidateProhibited02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Var var = new Var();
        var.setName("chars");
        var.setValue("a");
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"abc0#"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars=null<br>
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
     * var�v�f��char��null�̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc0#";
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
        // ���ؒl�ݒ�
        Var var = new Var();
        var.setName("chars");
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"abc0#"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars=""<br>
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
     * var�v�f��char���󕶎��̏ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc0#";
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
        var.setName("chars");
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"abc��"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars="!"#$%&'()"<br>
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
     * ������bean�̒l�ɁAvar�v�f�Ŏw�肳�ꂽ�������ꕶ�����܂܂�Ȃ��ꍇ�A
     * true���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc��";
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
        var.setName("chars");
        var.setValue("!\"#$%&'()");
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"!abc0#"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars="!"#$%&'()"<br>
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
     * ������bean�̒l�ɁAvar�v�f�Ŏw�肳�ꂽ�������܂܂��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "!abc0#";
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
        var.setName("chars");
        var.setValue("!\"#$%&'()");
        field.addVar(var);

        // �G���[���iActionMessage��1���ݒ�j
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"abc0#)"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars="!"#$%&'()"<br>
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
     * ������bean�̒l�ɁAvar�v�f�Ŏw�肳�ꂽ�������܂܂��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc0#)";
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
        var.setName("chars");
        var.setValue("!\"#$%&'()");
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
            FieldChecksEx.validateProhibited(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }

    /**
     * testValidateProhibited08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"=" a1"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field1"<br>
     *                var:chars="!"#$%&'()"<br>
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
     * ������bean��String�^�ł͂Ȃ��ꍇ�Afield����擾�������O�̃v���p�e�B��
     * �΂��ă`�F�b�N���s�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", " a1");

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
        Var var = new Var();
        var.setName("chars");
        var.setValue("!\"#$%&'()");
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="*)0a"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field1"<br>
     *                var:chars="!"#$%&'()"<br>
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
    public void testValidateProhibited09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "*)0a");

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
        Var var = new Var();
        var.setName("chars");
        var.setValue("!\"#$%&'()");
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
            FieldChecksEx.validateProhibited(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }

    /**
     * testValidateProhibited10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="&!"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field2"<br>
     *                var:chars="!"#$%&'()"<br>
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
     * ������bean��String�^�ł͂Ȃ��Afield����擾�������O�̃v���p�e�B��
     * ���݂��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "&!");

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
        Var var = new Var();
        var.setName("chars");
        var.setValue("!\"#$%&'()");
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
            FieldChecksEx.validateProhibited(
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
     * testValidateProhibited11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"abc0 "<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars="!"#$%&'() "<br>
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
     * ������bean�̒l�ɁAvar�v�f�Ŏw�肳�ꂽ�������܂܂��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited11() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "abc0 ";
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
        var.setName("chars");
        var.setValue("!\"#$%&'() ");
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
            FieldChecksEx.validateProhibited(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }    

    /**
     * testValidateProhibited12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:String:"    "<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                var:chars="!"#$%&'() "<br>
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
     * ������bean�̒l�ɁAvar�v�f�Ŏw�肳�ꂽ�������܂܂��ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateProhibited12() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "    ";
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
        var.setName("chars");
        var.setValue("!\"#$%&'() ");
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
            FieldChecksEx.validateProhibited(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }    
    
    /**
     * testValidateProhibited13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="*0a "]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field1"<br>
     *                var:chars="!"#$%&'() "<br>
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
    public void testValidateProhibited13() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "*0a ");

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
        Var var = new Var();
        var.setName("chars");
        var.setValue("!\"#$%&'() ");
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
            FieldChecksEx.validateProhibited(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }
    
    /**
     * testValidateProhibited14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:Map:["field1"="    "]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field1"<br>
     *                var:chars="!"#$%&'() "<br>
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
    public void testValidateProhibited14() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "    ");

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
        Var var = new Var();
        var.setName("chars");
        var.setValue("!\"#$%&'() ");
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
            FieldChecksEx.validateProhibited(
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
        ActionMessage error = (ActionMessage) it.next();
        assertEquals("message", error.getKey());
    }
    
}
