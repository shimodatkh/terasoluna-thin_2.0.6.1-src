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

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

import org.apache.commons.validator.Arg;
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
public class FieldChecksExTest06 extends PropertyTestCase {

    /**
     * ���̃N���X�̃e�X�g�Ŏg�p����DynaValidatorActionFormEx�̐ݒ�t�@�C���B
     */
    private static final String CONFIG_FILE_NAME =
        FieldChecksExTest06.class.getResource(
                "FieldChecksExTest.xml").getPath();

    /**
     * DynaValidatorActionFormEx�𐶐�����digester�̃��[���t�@�C���B
     */
    private final static String RULES_FILE_NAME =
        FieldChecksExTest06.class.getResource(
                "FieldChecksExTest-rules.xml").getPath();

    /**
     * DynaValidatorActionFormEx�𐶐�����N���X�B
     */
    private static final DynaActionFormCreator creator
        = new DynaActionFormCreator(RULES_FILE_NAME);

    /**
     * �e�X�g�Ɏg�p����DynaValidatorActionFormEx�C���X�^���X�B
     */
    private DynaValidatorActionFormEx formEx = null;

    /**
     * ����Ȉ����̌^���X�g�B
     */
    public Class[] validClassList = {
        Object.class,
        ValidatorAction.class,
        Field.class,
        ActionMessages.class,
        Validator.class,
        HttpServletRequest.class
    };

    /**
     * ����Ȉ����̕�����B
     */
    public String validClassStr = "java.lang.Object," +
            "org.apache.commons.validator.ValidatorAction," +
            "org.apache.commons.validator.Field," +
            "org.apache.struts.action.ActionMessages," +
            "org.apache.commons.validator.Validator," +
            "javax.servlet.http.HttpServletRequest";

    /**
     * �e�X�g�Ŏg�p����A�N�V�����t�H�[���B
     */
    FieldChecksEx_ValidatorActionFormExStub01 form = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest06.class);
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
        form = new FieldChecksEx_ValidatorActionFormExStub01();
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
    public FieldChecksExTest06(String name) {
        super(name);
    }

    /**
     * testValidateArraysIndex01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:*<br>
     *         (����) field:var:indexedListProperty=null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��indexedListProperty��null�̂Ƃ��Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//    public void testValidateArraysIndex01() throws Exception {
//        //�e�X�g�f�[�^�ݒ�
//        // ++++ bean�I�u�W�F�N�g ++++
//        String bean = null;
//        // ++++ ���ؐݒ�I�u�W�F�N�g
//        ValidatorAction va = new ValidatorAction();
//        // ++++ ���؃t�B�[���h���
//        Field field = new Field();
//        field.setIndexedListProperty(null);
//        // �G���[���
//        ActionMessages errors = new ActionMessages();
//        // �[��HTTP���N�G�X�g
//        HttpServletRequest request = new MockHttpServletRequest();
//
//        // ValidatorResources�C���X�^���X
//        ValidatorResources validatorResources = new ValidatorResources();
//        // Validator�C���X�^���X
//        Validator validator = new Validator(validatorResources);
//
//        // �e�X�g���s
//        boolean result =
//            FieldChecksEx.validateArraysIndex(
//                    bean,
//                    va,
//                    field,
//                    errors,
//                    validator,
//                    request);
//        // �e�X�g���ʊm�F
//        // true���ԋp����Ă��邱�ƁB
//        assertTrue(result);
//        // �G���[��񂪋�ł��邱�ƁB
//        assertTrue(errors.isEmpty());
//    }

    /**
     * testValidateArraysIndex02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:*<br>
     *         (����) field:var:indexedListProperty=""<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * field��indexedListProperty���󕶎��̂Ƃ��A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
//    public void testValidateArraysIndex02() throws Exception {
//        //�e�X�g�f�[�^�ݒ�
//        // ++++ bean�I�u�W�F�N�g ++++
//        String bean = null;
//
//        // ++++ ���ؐݒ�I�u�W�F�N�g
//        ValidatorAction va = new ValidatorAction();
//
//        // ++++ ���؃t�B�[���h���
//        Field field = new Field();
//        field.setIndexedListProperty("");
//
//        // �G���[���
//        ActionMessages errors = new ActionMessages();
//        // �[��HTTP���N�G�X�g
//        HttpServletRequest request = new MockHttpServletRequest();
//
//        // ValidatorResources�C���X�^���X
//        ValidatorResources validatorResources = new ValidatorResources();
//        // Validator�C���X�^���X
//        Validator validator = new Validator(validatorResources);
//
//        // �e�X�g���s
//        boolean result =
//            FieldChecksEx.validateArraysIndex(
//                    bean,
//                    va,
//                    field,
//                    errors,
//                    validator,
//                    request);
//        // �e�X�g���ʊm�F
//        // true���ԋp����Ă��邱�ƁB
//        assertTrue(result);
//        // �G���[��񂪋�ł��邱�ƁB
//        assertTrue(errors.isEmpty());
//    }

    /**
     * testValidateArraysIndex03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F��̃��X�g<br>
     *                name�Fnot null<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Class pattern length is zero.<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va��methodParams����̃��X�g�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B<br>
     * ��getParamClass�̃e�X�g���܂���
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams("");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Class pattern length is zero."));
    }

    /**
     * testValidateArraysIndex04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�FtestClass<br>
     *                ���݂��Ȃ��N���X��<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ��O�N���X�FClassNotFoundException<br>
     *                    ���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get class pattern."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va��methodParams�ɑ��݂��Ȃ��N���X��������ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B<br>
     * ��getParamClass�̃e�X�g���܂���
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams("testClass");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new ClassNotFoundException()));
        assertTrue(LogUTUtil.checkError("Can not get class pattern."));
    }

    /**
     * testValidateArraysIndex05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�Fnull<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get validateMethod."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va��name��null�̂Ƃ��Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName(null);

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F""<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get validateMethod."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va��name���󕶎��̂Ƃ��Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"testArray"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ��O�N���X�F<br>
     *                    NoSuchMethodException<br>
     *                    ���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get validateMethod."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va��name����擾�������\�b�h����FieldChecksEx�A
     * ValidWhen�ɑ��݂��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("testArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new NoSuchMethodException()));
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F"java.lang.String"<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ��O�N���X�F<br>
     *                    NoSuchMethodException<br>
     *                    ���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get validateMethod."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va�ɐݒ肳�ꂽ�`�F�b�N���\�b�h�̈����ƁA
     * methodParams�Ŏw�肳�ꂽ��������v���Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams("java.lang.String");
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new NoSuchMethodException()));
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�̏������قȂ�<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ��O�N���X�F<br>
     *                    NoSuchMethodException<br>
     *                    ���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get validateMethod."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va�ɐݒ肳�ꂽ�`�F�b�N���\�b�h�̈����ƁA
     * methodParams�Ŏw�肳�ꂽ��������v���Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        String argStr = "javax.servlet.http.HttpServletRequest," +
            "org.apache.commons.validator.ValidatorAction," +
            "org.apache.commons.validator.Field," +
            "org.apache.struts.action.ActionMessages," +
            "org.apache.commons.validator.Validator," +
            "java.lang.Object";
        va.setMethodParams(argStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("", new NoSuchMethodException()));
        assertTrue(LogUTUtil.checkError("Can not get validateMethod."));
    }

    /**
     * testValidateArraysIndex10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:ActionMapping�Fnull<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get ActionForm."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���N�G�X�g����ActionMapping���擾�ł��Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g�ȊO<br>
     *         (����) ActionForm:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get ActionForm."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * �Z�b�V��������擾�����A�N�V�����t�H�[����ActionForm�����N���X�ł͂Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex11() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("logon", "hoge");
        request.setSession(session);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *                �L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g�ȊO<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l�����݂��Ȃ�<br>
     *         (����) ActionForm:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get ActionForm."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���N�G�X�g����擾�����A�N�V�����t�H�[����ActionForm�����N���X�ł͂Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex12() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute("logon", "hoge");


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:not null<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *                �L�[�F"logon"�ɑ΂���l�����݂��Ȃ�<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l�����݂��Ȃ�<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    "Can not get ActionForm."<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * �A�N�V�����t�H�[�����擾�ł��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex13() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get ActionForm."));
    }

    /**
     * testValidateArraysIndex14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *                msg:key="message",name="stringLengthArray",resource="false"<br>
     *         (����) field:property=testArray<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:String testArray="String"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("testMessage")<br>
     *
     * <br>
     * ���ؑΏۂ̃v���p�e�B���z��ACollection�^�ł͂Ȃ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex14() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("testArray");
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("stringLengthArray");
        msg.setResource(false);
        field.addMsg(msg);
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("3");
        field.addVar(var);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        form.setTestArray("String");
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���1���ǉ�����Ă��邱�ƁB
        assertEquals(1, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());

    }

    /**
     * testValidateArraysIndex15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:property="codeArray"<br>
     *                var:stringLength=3<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *                arg:position="1",key="${var:length}",resource="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *                �L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l�����݂��Ȃ�<br>
     *         (����) ActionForm:ArrayList codeArray(���List)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���ؑΏۂ̃v���p�e�B��List�^�ŗv�f����̏ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex15() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("codeArray");
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("3");
        field.addVar(var);

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setKey("${var:stringLength}");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        form.setCodeArray(new ArrayList());
        request.setAttribute("logon", form);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"hankakuString12345"<br>
     *         (����) field:property="codeArray"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:ArrayList codeArray = {<br>
     *                    "�ݶ�"<br>
     *                }<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���؃��[���������݂��郋�[�����i�C���f�b�N�X�`�F�b�N�ȊO�j
     * +5�����̃��[���̂Ƃ��A���؂����s����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex16() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("hankakuString12345");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("codeArray");

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("�ݶ�");
        form.setCodeArray(testList);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex17()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"hankakuStringArray"<br>
     *         (����) field:property="codeArray"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:Arraylist codeArray = {<br>
     *                    "abc",<br>
     *                    ":�ݶ�",<br>
     *                    "123",<br>
     *                    "*!"",<br>
     *                    ""<br>
     *                }<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���ؑΏۂ̃v���p�e�B�������v�f�̂���List�Ō��؃G���[�ɂȂ�v�f��
     * ���݂��Ȃ��ꍇ�Atrue���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex17() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("hankakuStringArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("codeArray");

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("abc");
        testList.add(":�ݶ�");
        testList.add("123");
        testList.add("*!\"");
        testList.add("");
        form.setCodeArray(testList);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex18()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:property="stringArray"<br>
     *                var:stringLength="3"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *                arg:position="1",key="${var:stringLength}",resource="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:String[] stringArray<br>
     *                (��̗v�f)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���ؑΏۂ̃v���p�e�B���z��^�ŗv�f����̏ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex18() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("stringArray");
        Var var = new Var();
        var.setName("stringLength");
        var.setValue("3");
        field.addVar(var);

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setKey("${var:stringLength}");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        form.setStringArray(new String[]{});
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex19()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"requiredArray"<br>
     *         (����) field:property="stringArray"<br>
     *         (����) errors:not null<br>
     *                ActionMessage(<br>
     *                  "testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:String[] stringArray = {<br>
     *                    null<br>
     *                }<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                      "testMessage")<br>
     *                    ActionMessage(<br>
     *                      "message")<br>
     *
     * <br>
     * ���ؑΏۂ̃v���p�e�B���v�f1���̔z��^�ŁA���؃G���[����������ꍇ�A
     * �G���[���b�Z�[�W��ǉ����Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex19() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("stringArray");

        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("requiredArray");
        msg.setResource(false);
        field.addMsg(msg);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            null
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[��񂪋�ł��邱�ƁB
        assertEquals(2, errors.size());

        Iterator it = errors.get();
        List<String> nameList = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            nameList.add(message.getKey());
        }
        assertTrue(nameList.contains("testMessage"));
        assertTrue(nameList.contains("message"));
    }

    /**
     * testValidateArraysIndex20()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"requiredArray"<br>
     *         (����) field:property="stringArray"<br>
     *                arg:position="0",key="##INDEX",resource="false"<br>
     *                arg:position="1",key="##INDEX",resource="false"<br>
     *                arg:position="3",key="##INDEX",resource="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:���ؑΏہFString[1]<br>
     *                ���،��ʁF<br>
     *                [0]:null(false)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage("message"<br>
     *                      args{<br>
     *                        "1", "1", "1"<br>
     *                      })<br>
     *
     * <br>
     * arg�v�f��##INDEX��������������Ƃ��ɁA����Ƀ`�F�b�N�����s����邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex20() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("stringArray");

        Arg arg0 = new Arg();
        arg0.setKey("##INDEX");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        Arg arg1 = new Arg();
        arg1.setKey("##INDEX");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        Arg arg2 = new Arg();
        arg2.setKey("##INDEX");
        arg2.setPosition(3);
        arg2.setResource(false);
        field.addArg(arg2);

        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("requiredArray");
        field.addMsg(msg);

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            null
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1},{2},{3}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���1���ł��邱�ƁB
        assertEquals(1, errors.size());

        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("message", message.getKey());
        Object values[] = message.getValues();
        assertEquals("1", values[0]);
        assertEquals("1", values[1]);
        assertNull(values[2]);
        assertEquals("1", values[3]);
    }

    /**
     * testValidateArraysIndex21()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"numberArray"<br>
     *         (����) field:property="stringArray"<br>
     *                var:integerLength="2"<br>
     *                var:scale="1"<br>
     *                var:isAccordedScale="true"<br>
     *                arg:position="0",key="${var:integerLength}",<br>
     *                  resource="false"<br>
     *                arg:position="1",key="${var:scale}",<br>
     *                  resource="false"<br>
     *                arg:position="2",key="${var:isAccordedInteger}",<br>
     *                  resource="false"<br>
     *                arg:position="3",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="numberArray",name="numberArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:���ؑΏہFString[5]<br>
     *                ���،��ʁF<br>
     *                [0]:10.1(true)<br>
     *                [1]:1.11(false)<br>
     *                [2]:100.1(false)<br>
     *                [3]:100(false)<br>
     *                [4]:10.0(true)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                      "numberArray",<br>
     *                      arg{"2","1","true","2"})<br>
     *                    ActionMessage(<br>
     *                      "numberArray",<br>
     *                      arg{"2","1","true","3"})<br>
     *                    ActionMessage(<br>
     *                      "numberArray",<br>
     *                      arg{"2","1","true","4"})<br>
     *
     * <br>
     * ���ؑΏۂ̃v���p�e�B�������v�f�̔z��ŁA�����̃G���[����������ꍇ�A
     * �u���������##INDEX�̒l���G���[�����������C���f�b�N�X�ɒu������邱�Ƃ�
     * �m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex21() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("numberArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("stringArray");

        Var var = new Var();
        var.setName("integerLength");
        var.setValue("2");
        field.addVar(var);

        var = new Var();
        var.setName("scale");
        var.setValue("1");
        field.addVar(var);

        var = new Var();
        var.setName("isAccordedScale");
        var.setValue("true");
        field.addVar(var);

        Arg arg0 = new Arg();
        arg0.setKey("${var:integerLength}");
        arg0.setPosition(0);
        arg0.setResource(false);
        field.addArg(arg0);

        Arg arg1 = new Arg();
        arg1.setKey("${var:scale}");
        arg1.setPosition(1);
        arg1.setResource(false);
        field.addArg(arg1);

        Arg arg2 = new Arg();
        arg2.setKey("${var:isAccordedScale}");
        arg2.setPosition(2);
        arg2.setResource(false);
        field.addArg(arg2);

        Arg arg3 = new Arg();
        arg3.setKey("##INDEX");
        arg3.setPosition(3);
        arg3.setResource(false);
        field.addArg(arg3);

        Msg msg = new Msg();
        msg.setKey("numberArray");
        msg.setName("numberArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            "10.1",
            "1.11",
            "100.1",
            "100",
            "10.0"
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1},{2},{3}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���1���ł��邱�ƁB
        assertEquals(3, errors.size());

        Iterator it = errors.get();
        int counter = 2;
        while (it.hasNext()) {
            ActionMessage message = (ActionMessage) it.next();
            assertEquals("numberArray", message.getKey());
            assertEquals("2", message.getValues()[0]);
            assertEquals("1", message.getValues()[1]);
            assertEquals("true", message.getValues()[2]);
            assertEquals(String.valueOf(counter++), message.getValues()[3]);
        }
    }

    /**
     * testValidateArraysIndex22()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"stringLengthArray"<br>
     *         (����) field:property�F"codeArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[����"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:ArrayList codeArray=null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���ؑΏۂ̃v���p�e�B�̒l��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex22() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("stringLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("codeArray");

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        form.setCodeArray(null);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex23()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�Fnull<br>
     *         (����) field:*<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Can not get class pattern.<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * va��methodParams�����null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B<br>
     * ��getParamClass�̃e�X�g���܂���
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex23() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        UTUtil.setPrivateField(va, "methodParams", null);

        // ++++ ���؃t�B�[���h���
        Field field = new Field();

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
        assertTrue(LogUTUtil.checkError("Can not get class pattern."));
    }

    /**
     * testValidateArraysIndex24()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:6�S�Đ���<br>
     *                name�F"numberArray"<br>
     *         (����) field:property�FnoIndexedListProperty<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (����) session:not null<br>
     *         (����) ActionForm:noIndexedListProperty�t�B�[���h��<br>
     *                ���݂��Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) errors:not null<br>
     *                    (��̗v�f)<br>
     *
     * <br>
     * ���ؑΏۂ̃t�B�[���h�����݂��Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateArraysIndex24() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("numberArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("noIndexedListProperty");

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        String[] testArray = {
            null
        };
        form.setStringArray(testArray);
        session.setAttribute("logon", form);
        request.setSession(session);


        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
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
     * testValidateArraysIndex25()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"minLengthArray"<br>
     *         (����) field:property="row.values"<br>
     *                var:minlength="3"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                arg:position="1",key="${var:minlength}",<br>
     *                  resource="false"<br>
     *                msg:key="minLengthArray",name="minLengthArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:JavaBean row<br>
     *                 + String[] values = {<br>
     *                     "a", "bbbb","cc"<br>
     *                }<br>
     *         (����) ���b�Z�[�W���\�[�X:message={0},{1}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2","3"})<br>
     *
     * <br>
     * ���ؑΏۂ̒l���A�N�V�����t�H�[���̃l�X�g�����v���p�e�B�̏ꍇ�A����Ɍ��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex25() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("minLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("row.values");
        Var var = new Var();
        var.setName("minlength");
        var.setValue("3");
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        //arg0.setName("minLengthArray");
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setPosition(1);
        arg1.setKey("${var:minlength}");
        arg1.setResource(false);
        //arg1.setName("minLength");
        field.addArg(arg1);
        Msg msg = new Msg();
        msg.setKey("minLengthArray");
        msg.setName("minLengthArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        String[] values = {
            "a", "bbbb", "cc"
        };
        row.values = values;
        form.setRow(row);
        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���1���ǉ�����Ă��邱�ƁB
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);
        message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("3", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);


    }

    /**
     * testValidateArraysIndex26()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"alphaNumericStringArray"<br>
     *         (����) field:property="rows.value"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="alphaNumericStringArray",name="alphaNumericStringArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:<br>
     *                JavaBean[] rows<br>
     *                  +rows[0]<br>
     *                     + String value = "�Ă���"<br>
     *                  +rows[1]<br>
     *                     + String value = "test"<br>
     *                  +rows[2]<br>
     *                     + String value = "�Ă���"<br>
     *         (����) ���b�Z�[�W���\�[�X:message={0}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                           "alphaNumericStringArray",<br>
     *                           arg{"2"})<br>
     *
     * <br>
     * ���ؑΏۂ̒l���A�N�V�����t�H�[���̃l�X�g�����v���p�e�B�̏ꍇ�A����Ɍ��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex26() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("alphaNumericStringArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("rows.value");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("alphaNumericStringArray");
        msg.setName("alphaNumericStringArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01[] rows = {
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01()
        };
        rows[0].value = "�Ă���";
        rows[1].value = "test";
        rows[2].value = "�Ă���";
        form.rows = rows;

        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���2���ǉ�����Ă��邱�ƁB
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("3", message.getValues()[0]);


    }

    /**
     * testValidateArraysIndex27()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"requiredArray"<br>
     *         (����) field:property="rowList.map(key)"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="requiredArray",name="requiredArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:ArrayList<JavaBean> rowList<br>
     *                 + rowList[0]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[1]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[2]<br>
     *                     + Map map = { key = null }<br>
     *         (����) ���b�Z�[�W���\�[�X:message={0}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"1"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"3"})<br>
     *
     * <br>
     * ���ؑΏۂ̒l���A�N�V�����t�H�[���̃l�X�g�����v���p�e�B�̏ꍇ�A����Ɍ��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings({"deprecation","unchecked"})
    public void testValidateArraysIndex27() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("rowList.map(key)");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("requiredArray");
        msg.setName("requiredArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        List rowList = new ArrayList();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        form.rowList = rowList;

        session.setAttribute("logon", form);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���2���ǉ�����Ă��邱�ƁB
        assertEquals(3, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("2", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("3", message.getValues()[0]);


    }

    /**
     * testValidateArraysIndex28()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"minLengthArray"<br>
     *         (����) field:property="row.values"<br>
     *                var:minlength="3"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                arg:position="1",key="${var:minlength}",<br>
     *                  resource="false"<br>
     *                msg:key="minLengthArray",name="minLengthArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean row<br>
     *                 + String[] values = {<br>
     *                     "a", "bbbb","cc"<br>
     *                }<br>
     *         (����) ���b�Z�[�W���\�[�X:message={0},{1}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2","3"})<br>
     *
     * <br>
     * ���ؑΏۂ̒l���A�N�V�����t�H�[���̃l�X�g�����v���p�e�B�̏ꍇ�A����Ɍ��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex28() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("minLengthArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("row.values");
        Var var = new Var();
        var.setName("minlength");
        var.setValue("3");
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        //arg0.setName("minLengthArray");
        field.addArg(arg0);
        Arg arg1 = new Arg();
        arg1.setPosition(1);
        arg1.setKey("${var:minlength}");
        arg1.setResource(false);
        //arg1.setName("minLength");
        field.addArg(arg1);
        Msg msg = new Msg();
        msg.setKey("minLengthArray");
        msg.setName("minLengthArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        String[] values = {
            "a", "bbbb", "cc"
        };
        row.values = values;
        formEx.set("row", row);
        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0},{1}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���2���ǉ�����Ă��邱�ƁB
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);
        message = (ActionMessage) it.next();
        assertEquals("minLengthArray", message.getKey());
        assertEquals("3", message.getValues()[0]);
        assertEquals("3", message.getValues()[1]);


    }

    /**
     * testValidateArraysIndex29()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"alphaNumericStringArray"<br>
     *         (����) field:property="rows.value"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="alphaNumericStringArray",name="alphaNumericStringArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:DynaValidatorActionFormEx�C���X�^���X<br>
     *                JavaBean[] rows<br>
     *                  +rows[0]<br>
     *                     + String value = "�Ă���"<br>
     *                  +rows[1]<br>
     *                     + String value = "test"<br>
     *                  +rows[2]<br>
     *                     + String value = "�Ă���"<br>
     *         (����) ���b�Z�[�W���\�[�X:message={0}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                           "alphaNumericStringArray",<br>
     *                           arg{"2"})<br>
     *
     * <br>
     * ���ؑΏۂ̒l���A�N�V�����t�H�[���̃l�X�g�����v���p�e�B�̏ꍇ�A����Ɍ��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testValidateArraysIndex29() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("alphaNumericStringArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("rows.value");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("alphaNumericStringArray");
        msg.setName("alphaNumericStringArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        FieldChecksEx_JavaBeanStub01[] rows = {
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01(),
            new FieldChecksEx_JavaBeanStub01()
        };
        rows[0].value = "�Ă���";
        rows[1].value = "test";
        rows[2].value = "�Ă���";
        formEx.set("rows", rows);

        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���2���ǉ�����Ă��邱�ƁB
        assertEquals(2, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("alphaNumericStringArray", message.getKey());
        assertEquals("3", message.getValues()[0]);



    }

    /**
     * testValidateArraysIndex30()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"requiredArray"<br>
     *         (����) field:property="rowList.map(key)"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="requiredArray",name="requiredArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:DynaValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList<JavaBean> rowList<br>
     *                 + rowList[0]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[1]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[2]<br>
     *                     + Map map = { key = null }<br>
     *         (����) ���b�Z�[�W���\�[�X:message={0}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) errors:ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"1"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2"})<br>
     *                    ActionMessage(<br>
     *                      "minLengthArray",<br>
     *                      arg{"2"})<br>
     *
     * <br>
     * ���ؑΏۂ̒l���A�N�V�����t�H�[���̃l�X�g�����v���p�e�B�̏ꍇ�A����Ɍ��؂��s���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings({"deprecation","unchecked"})
    public void testValidateArraysIndex30() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("rowList.map(key)");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("requiredArray");
        msg.setName("requiredArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        List rowList = new ArrayList();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        formEx.set("rowList", rowList);

        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���2���ǉ�����Ă��邱�ƁB
        assertEquals(3, errors.size());
        Iterator it = errors.get();
        ActionMessage message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("1", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("2", message.getValues()[0]);
        message = (ActionMessage) it.next();
        assertEquals("requiredArray", message.getKey());
        assertEquals("3", message.getValues()[0]);


    }

    /**
     * testValidateArraysIndex31()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:*<br>
     *         (����) va:methodParams�F6�S�Đ���<br>
     *                name�F"requiredArray"<br>
     *         (����) field:property="/"<br>
     *                arg:position="0",key="##INDEX",<br>
     *                  resource="false"<br>
     *                msg:key="requiredArray",name="requiredArray"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:�A�N�V�����t�H�[�����F"logon"<br>
     *         (����) session:�L�[�F"logon"�ɑ΂���l��<br>
     *                ActionForm�����I�u�W�F�N�g<br>
     *         (����) ActionForm:DynaValidatorActionFormEx�C���X�^���X<br>
     *                ArrayList<JavaBean> rowList<br>
     *                 + rowList[0]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[1]<br>
     *                     + Map map = { key = null }<br>
     *                 + rowList[2]<br>
     *                     + Map map = { key = null }<br>
     *         (����) ���b�Z�[�W���\�[�X:message={0}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) ���O:���O���x���F�G���[
     *                         ��O�FIllegalArgumentException
     *                               "Invalid character has found within property name. '/' Cannot use [ / \" ' ]"
     *
     * <br>
     * �s���ȃv���p�e�B�����w�肳�ꂽ�ꍇ�A�G���[���O���o�͂��Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings({"deprecation","unchecked"})
    public void testValidateArraysIndex31() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setMethodParams(this.validClassStr);
        va.setName("requiredArray");

        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("/");
        Var var = new Var();
        field.addVar(var);
        Arg arg0 = new Arg();
        arg0.setPosition(0);
        arg0.setKey("##INDEX");
        arg0.setResource(false);
        field.addArg(arg0);
        Msg msg = new Msg();
        msg.setKey("requiredArray");
        msg.setName("requiredArray");
        field.addMsg(msg);
        UTUtil.invokePrivate(field, "process", Map.class, Map.class, 
        		new HashMap(), new HashMap());

        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        FieldChecksEx_HttpServletRequestImpl01 request =
            new FieldChecksEx_HttpServletRequestImpl01();
        ActionMappingEx mapping = new ActionMappingEx();
        mapping.setName("logon");
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        MockHttpSession session = new MockHttpSession();
        List rowList = new ArrayList();
        FieldChecksEx_JavaBeanStub01 row = new FieldChecksEx_JavaBeanStub01();
        Map map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        row = new FieldChecksEx_JavaBeanStub01();
        map = new HashMap();
        map.put("key", null);
        row.map = map;
        rowList.add(row);
        formEx.set("rowList", rowList);

        session.setAttribute("logon", formEx);
        request.setSession(session);

        FieldChecksEx_MessageResourcesImpl01 messageResources =
            new FieldChecksEx_MessageResourcesImpl01(null ,null);
        messageResources.message = "{0}";
        request.setAttribute(Globals.MESSAGES_KEY, messageResources);

        // ValidatorResources�C���X�^���X
        ValidatorResources validatorResources = new ValidatorResources();
        // Validator�C���X�^���X
        Validator validator = new Validator(validatorResources);

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateArraysIndex(
                    bean,
                    va,
                    field,
                    errors,
                    validator,
                    request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���O
        assertTrue(LogUTUtil.checkError("",
                new IllegalArgumentException(
                        "Invalid character has found " +
                        "within property name. '/' Cannot use [ / \" ' ]")));

    }

    /**
     * testGetHankakuKanaList01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:����������������������¯�������������������֬��
     *                         �����ܦ��ް�����<br>
     *
     * <br>
     * �N���X�ϐ�hankakuKanaList�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHankakuKanaList01() throws Exception {
        assertEquals("����������������������¯��" +
                "�����������������֬�������ܦ��ް�����",
                FieldChecksEx.getHankakuKanaList());
    }

    /**
     * testGetZenkakuKanaList01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R����
     *                                �K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g
     *                                �_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{
     *                                �p�s�v�y�|�}�~����������������������������
     *                                �����������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S
     *                         �T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h
     *                         �i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|
     *                         �}�~������������������������������������
     *                         ���b���[<br>
     *
     * <br>
     * �N���X�ϐ�zenkakuKanaList�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetZenkakuKanaList01() throws Exception {
        assertEquals("�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S" +
                "�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m" +
                "�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~������������" +
                "���������������������������b���[",
                FieldChecksEx.getZenkakuKanaList());
    }

}
