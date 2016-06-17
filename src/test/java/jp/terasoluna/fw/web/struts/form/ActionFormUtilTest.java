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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.form.ActionFormUtil} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �A�N�V�����t�H�[���֘A�̃��[�e�B���e�B�N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.ActionFormUtil
 */
public class ActionFormUtilTest extends TestCase {

    /**
     * ���̃N���X�̃e�X�g�Ŏg�p����DynaValidatorActionFormEx�̐ݒ�t�@�C���B
     */
    private static final String CONFIG_FILE_NAME =
        ActionFormUtil.class.getResource(
                "ActionFormUtilTest.xml").getPath();

    /**
     * DynaValidatorActionFormEx�𐶐�����digester�̃��[���t�@�C���B
     */
    private final static String RULES_FILE_NAME =
        ActionFormUtil.class.getResource(
                "ActionFormUtilTest-rules.xml").getPath();

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
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ActionFormUtilTest.class);
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
        this.formEx =
            (DynaValidatorActionFormEx) creator.create(CONFIG_FILE_NAME);
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
    public ActionFormUtilTest(String name) {
        super(name);
    }

    /**
     * testGetPropertyConfig01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:null<br>
     *         (����) mapping:not null<br>
     *         (���) mapping.getName:"abc"<br>
     *         (���) config:not null<br>
     *                {FormPropertyConfig("aaa", "java.lang.String",null,0)}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) �^:null<br>
     *
     * <br>
     * config.fingFormPropertyConfig(fieldName)�̌��ʂ��擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyConfig01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String fieldName = null;
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);

        //�e�X�g���s
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));
    }

    /**
     * testGetPropertyConfig02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,G
     * <br><br>
     * ���͒l�F(����) fieldName:not null<br>
     *         (����) mapping:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) �^:NullPointerException<br>
     *
     * <br>
     * ������mapping��null�̏ꍇ�ANullPointerException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyConfig02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        //mapping��null��ݒ�
        String fieldName = "hoge";
        ActionMapping mapping = new ActionMapping();
        mapping = null;

        try {
            //�e�X�g���s
            ActionFormUtil.getPropertyConfig(fieldName, mapping);
            fail();
        } catch (NullPointerException e) {
            //�e�X�g����
        }
    }

    /**
     * testGetPropertyConfig03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (���) mapping.getName:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) �^:null<br>
     *
     * <br>
     * mapping����name���擾�ł��Ȃ��ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyConfig03() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        String fieldName = "field1";
        ActionMapping mapping = new ActionMapping();
        mapping.setName(null);

        //�e�X�g���s
        //�e�X�g���ʊm�F
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));
    }

    /**
     * testGetPropertyConfig04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (���) mapping.getName:"abc"<br>
     *         (���) config:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) �^:null<br>
     *
     * <br>
     * ModuleConfig����FormBeanConfig���擾�ł��Ȃ��ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyConfig04() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        String fieldName = "field1";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);

        //�e�X�g���s
        //�e�X�g���ʊm�F
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));
    }

    /**
     * testGetPropertyConfig05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) fieldName:"aaa"<br>
     *         (����) mapping:not null<br>
     *         (���) mapping.getName:"abc"<br>
     *         (���) config:not null<br>
     *                {FormPropertyConfig("aaa", "java.lang.String",null,0)}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) �^:{FormPropertyConfig(<br>
     *                  "aaa", <br>
     *                  "java.lang.String",<br>
     *                  null,0)}<br>
     *
     * <br>
     * config.fingFormPropertyConfig(fieldName)�̌��ʂ��擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyConfig05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String fieldName = "aaa";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);

        //�e�X�g���s
        FormPropertyConfig result =
            ActionFormUtil.getPropertyConfig(fieldName, mapping);
        assertEquals("aaa", result.getName());
        assertEquals("java.lang.String", result.getType());
        assertEquals("null", result.getInitial());
        assertEquals(0, result.getSize());

    }

    /**
     * testGetPropertyConfig06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:""<br>
     *         (����) mapping:not null<br>
     *         (���) mapping.getName:"abc"<br>
     *         (���) config:not null<br>
     *                {FormPropertyConfig("aaa", "java.lang.String",null,0)}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) �^:null<br>
     *
     * <br>
     * config.fingFormPropertyConfig(fieldName)�̌��ʂ��擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyConfig06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String fieldName = "";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);

        //�e�X�g���s
        assertNull(ActionFormUtil.getPropertyConfig(fieldName, mapping));

    }

    /**
     * testInitialize01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) form:null<br>
     *
     * <br>
     * ���Ғl�F
     * <br>
     * �����������s���Ȃ�����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String fieldName = "hoge";
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setName("name");

        ActionFormUtil.initialize(null, fieldName, mapping);
    }

    /**
     * testInitialize02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) form:not null<br>
     *         (����) fileldName:null<br>
     *
     * <br>
     * ���Ғl�F
     * <br>
     * �����������s���Ȃ�����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setName("name");
        String fieldName = null;

        //�e�X�g���s
        //�e�X�g���ʊm�F
        //��O�����������ɐ���I�����邱�Ƃ��m�F
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, mapping);
    }

    /**
     * testInitialize03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) form:not null<br>
     *         (����) fileldName:�󕶎�<br>
     *
     * <br>
     * ���Ғl�F
     * <br>
     * �����������s���Ȃ�����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setName("formName");
        String fieldName = "";

        //�e�X�g���s
        //�e�X�g���ʊm�F
        //��O�����������ɐ���I�����邱�Ƃ��m�F
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, mapping);
    }

    /**
     * testInitialize04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) form:not null<br>
     *         (����) fileldName:not null<br>
     *         (����) mapping:null<br>
     *
     * <br>
     * ���Ғl�F
     * <br>
     * �����������s���Ȃ�����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String fieldName = "field2";

        //�e�X�g���s
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, null);
    }

    /**
     * testInitialize05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) form:not null<br>
     *         (����) fileldName:not null<br>
     *         (����) mapping:not null<br>
     *         (���) config:null<br>
     *
     * <br>
     * ���Ғl�F
     * <br>
     * �����������s���Ȃ�����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize05() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        String fieldName = "field2";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);

        //�e�X�g���s
        ActionFormUtil.initialize(
                new DynaValidatorActionFormEx(), fieldName, mapping);
    }

    /**
     * testInitialize06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) form:not null<br>
     *                field="AAA"<br>
     *         (����) fileldName:"field"<br>
     *         (����) mapping:not null<br>
     *         (���) config:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) fieldName�Ŏw�肵���v���p�e�B�l:""<br>
     *
     * <br>
     * ��2������fieldName�Ŏw�肵���t�B�[���h�l������������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        String fieldName = "field";
        ActionMapping mapping = new ActionMapping();
        ModuleConfig moduleConfig = new ModuleConfigImpl("");
        FormBeanConfig fBConfig = creator.parse(CONFIG_FILE_NAME);
        mapping.setName("abc");
        mapping.setModuleConfig(moduleConfig);
        moduleConfig.addFormBeanConfig(fBConfig);
        formEx.set("field", "AAA");

        //�e�X�g���s
        ActionFormUtil.initialize(formEx, fieldName, mapping);
        assertEquals("", formEx.get("field"));

    }

    /**
     * testClearActionForm01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FC,G
     * <br><br>
     * ���͒l�F(����) session:null<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *
     * <br>
     * ������session��null�̏ꍇ�ANullPointerExcpetion���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm01() throws Exception {

        try {
            //�e�X�g���s
            ActionFormUtil.clearActionForm(null, "_hoge");
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            // NullPointerException���X���[���ꂽ�ꍇ�̂݃e�X�g�����B
        }
    }

    /**
     * testClearActionForm02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F
     * <br>
     * session�ɒl���i�[����Ă��Ȃ������ꍇ�́A��ԕω��͋N���炸�A������ɏI�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        if ((session.getAttributeNames()).hasMoreElements()) {
            //�v�f�������Ă���ꍇ�̓e�X�g���s�B
            fail();
        }
    }

    /**
     * testClearActionForm03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"hoge"= "value"}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{"hoge"= "value"}<br>
     *
     * <br>
     * session����ActionForm�C���X�^���X���܂܂�Ȃ��ꍇ�A��ԕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key = "hoge";
        Object value = "value";
        session.setAttribute(key, value);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        assertEquals(value, session.getAttribute(key));
    }

    /**
     * testClearActionForm04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"_hoge"= "value"}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{"_hoge"="value"}<br>
     *
     * <br>
     * session����ActionForm�C���X�^���X���܂܂�Ȃ��ꍇ�A��ԕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key = "_hoge";
        Object value = "value";
        session.setAttribute(key, value);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        assertEquals(value, session.getAttribute(key));
    }

    /**
     * testClearActionForm05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"hoge"=ActionForm�C���X�^���X}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{"hoge"=ActionForm�C���X�^���X}<br>
     *
     * <br>
     * session����ActionForm���܂܂�A��������"_"����n�܂�Ȃ��ꍇ�A��ԕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm05() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key = "hoge";
        ActionFormUtil_ActionFormStub01 form =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key, form);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        assertEquals(form, session.getAttribute(key));
    }

    /**
     * testClearActionForm06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"_hoge"=ActionForm�C���X�^���X}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{"_hoge"=ActionForm�C���X�^���X}<br>
     *
     * <br>
     * session���ɑ���������������exclude�ƈ�v����ActionForm���܂܂��ꍇ�A��ԕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm06() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key = "_hoge";
        ActionFormUtil_ActionFormStub01 form =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key, form);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        assertEquals(form, session.getAttribute(key));
    }

    /**
     * testClearActionForm07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"hoge"=ActionForm�C���X�^���X,<br>
     *                 "foo"=ActionForm�C���X�^���X,<br>
     *                 "world"=ActionForm�C���X�^���X}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{"hoge"=ActionForm�C���X�^���X,<br>
     *                     "foo"=ActionForm�C���X�^���X,<br>
     *                     "world"=ActionForm�C���X�^���X}<br>
     *
     * <br>
     * session���ɑ�������"_"����n�܂�Ȃ�ActionForm�������ݒ肳��Ă���ꍇ�A��ԕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm07() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key1 = "hoge";
        String key2 = "foo";
        String key3 = "world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        assertEquals(form1, session.getAttribute(key1));
        assertEquals(form2, session.getAttribute(key2));
        assertEquals(form3, session.getAttribute(key3));
    }

    /**
     * testClearActionForm08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"_hoge"=ActionForm�C���X�^���X,<br>
     *                 "foo"=ActionForm�C���X�^���X,<br>
     *                 "world"=ActionForm�C���X�^���X}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{"_hoge"=ActionForm�C���X�^���X,<br>
     *                     "foo"=ActionForm�C���X�^���X,<br>
     *                     "world"=ActionForm�C���X�^���X}<br>
     *
     * <br>
     * session���ɑ���������������exclude�ƈ�v����ActionForm���܂ޕ�����ActionForm(�A���A���̑��͑�������"_"����n�܂�Ȃ�)�����݂���ꍇ�A��ԕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm08() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key1 = "_hoge";
        String key2 = "foo";
        String key3 = "world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        assertEquals(form1, session.getAttribute(key1));
        assertEquals(form2, session.getAttribute(key2));
        assertEquals(form3, session.getAttribute(key3));
    }

    /**
     * testClearActionForm09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"_hoge"=ActionForm�C���X�^���X,<br>
     *                 "_foo"=ActionForm�C���X�^���X,<br>
     *                 "_world"=ActionForm�C���X�^���X}<br>
     *         (����) exclude:_hoge<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{"_hoge"=ActionForm�C���X�^���X}<br>
     *
     * <br>
     * session���ɑ�������"_"����n�܂镡����ActionForm�����݂���ꍇ���Aexclude�Ŏw�肵���l�ƈ�v������������ActionForm�����݂���ꍇ�Aexclude�Ŏw�肵���������ȊO��ActionForm���폜����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm09() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key1 = "_hoge";
        String key2 = "_foo";
        String key3 = "_world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, "_hoge");

        //�e�X�g���ʊm�F
        assertEquals(form1, session.getAttribute(key1));
        assertNull(session.getAttribute(key2));
        assertNull(session.getAttribute(key3));
    }

    /**
     * testClearActionForm10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) session:not null<br>
     *                {"_hoge"=ActionForm�C���X�^���X,<br>
     *                 "_foo"=ActionForm�C���X�^���X,<br>
     *                 "_world"=ActionForm�C���X�^���X}<br>
     *         (����) exclude:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) session:{}<br>
     *
     * <br>
     * session���ɑ�������"_"����n�܂镡����ActionForm�����݂���ꍇ���Aexclude��null�̏ꍇ�A�S�Ă�ActionForm���폜����邱�Ƃ��m�F����B<br>
     * ��clearActionFrom(HttpSession)�̃e�X�g�P�[�X���܂�
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClearActionForm10() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        String key1 = "_hoge";
        String key2 = "_foo";
        String key3 = "_world";
        ActionFormUtil_ActionFormStub01 form1 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form2 =
            new ActionFormUtil_ActionFormStub01();
        ActionFormUtil_ActionFormStub01 form3 =
            new ActionFormUtil_ActionFormStub01();
        session.setAttribute(key1, form1);
        session.setAttribute(key2, form2);
        session.setAttribute(key3, form3);

        //�e�X�g���s
        ActionFormUtil.clearActionForm(session, null);

        //�e�X�g���ʊm�F
        assertNull(session.getAttribute(key1));
        assertNull(session.getAttribute(key2));
        assertNull(session.getAttribute(key3));
    }

    /**
     * testGetActionFormName01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,G
     * <br><br>
     * ���͒l�F(����) req:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *
     * <br>
     * ������req��null�̏ꍇ�ANullPointerException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetActionFormName01() throws Exception {

        try {
            //�e�X�g���s
            ActionFormUtil.getActionFormName(null);
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            // NullPointerException���X���[���ꂽ�ꍇ�̂݃e�X�g�����B
        }
    }

    /**
     * testGetActionFormName02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (���) req.getAttribute(Globals.MAPPING_KEY):null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *
     * <br>
     * ���N�G�X�g����ActionMapping�C���X�^���X���擾�ł��Ȃ��ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetActionFormName02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[�����N�G�X�g
        HttpServletRequest req = new MockHttpServletRequest();
        req.setAttribute("key", "value");

        //�e�X�g���s
        //�e�X�g���ʊm�F
        assertNull(ActionFormUtil.getActionFormName(req));    }

    /**
     * testGetActionFormName03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (���) req.getAttribute(Globals.MAPPING_KEY):not null<br>
     *                name="abc"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *
     * <br>
     * AcionMapping��name�����̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetActionFormName03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // �[�����N�G�X�g
        HttpServletRequest req = new MockHttpServletRequest();

        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setName("name");

        req.setAttribute(Globals.MAPPING_KEY, mapping);

        //�e�X�g���s
        //�e�X�g���ʊm�F
        assertEquals("name", ActionFormUtil.getActionFormName(req));
    }

    
    /**
     * getFormArrayMaxLength�̃e�X�g01
     * 
     * null��n���ăf�t�H���g�l���ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception
     */
    public void testGetFormArrayMaxLength01() throws Exception {
        int defaultMax = (Integer) UTUtil.getPrivateField(ActionFormUtil.class,
                "DEFAULT_MAX_LENGTH");
        int actual = ActionFormUtil.getFormArrayMaxLength(null);
        assertEquals(defaultMax, actual);
    }
    
    /**
     * getFormArrayMaxLength�̃e�X�g02
     * 
     * ���l�ɕϊ��ł��Ȃ��l��n���ăf�t�H���g�l���ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception
     */
    public void testGetFormArrayMaxLength02() throws Exception {
        int defaultMax = (Integer) UTUtil.getPrivateField(ActionFormUtil.class,
                "DEFAULT_MAX_LENGTH");
        int actual = ActionFormUtil.getFormArrayMaxLength("a");
        assertEquals(defaultMax, actual);
    }

    /**
     * getFormArrayMaxLength�̃e�X�g03
     * 
     * ���l�ɕϊ��ł���l��n���ĕϊ����ꂽ�l���ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception
     */
    public void testGetFormArrayMaxLength03() throws Exception {
        int actual = ActionFormUtil.getFormArrayMaxLength("300");
        assertEquals(300, actual);
    }
    /**
     * getFormArrayMaxLength�̃e�X�g02
     * 
     * int�̍ő�l�ȏ�̒l��n���ăf�t�H���g�l���ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception
     */
    public void testGetFormArrayMaxLength04() throws Exception {
        int defaultMax = (Integer) UTUtil.getPrivateField(ActionFormUtil.class,
                "DEFAULT_MAX_LENGTH");
        int actual = ActionFormUtil.getFormArrayMaxLength(String
                .valueOf(Long.MAX_VALUE));
        assertEquals(defaultMax, actual);
    }
    
    /**
     * checkIndexLength�̃e�X�g01
     * 
     * �`�F�b�N�ő�l��菬�����l��n���ĉ����������Ȃ����Ƃ��m�F����B
     * 
     * @throws Exception
     */
    public void testCheckIndexLength01() {
        ActionFormUtil.checkIndexLength(100);
        assertTrue(true);
    }
    
    /**
     * checkIndexLength�̃e�X�g02
     * 
     * �`�F�b�N�ő�l��n���ĉ����������Ȃ����Ƃ��m�F����B
     * 
     * @throws Exception
     */
    public void testCheckIndexLength02() throws Exception {
        int max = (Integer) UTUtil.getPrivateField(ActionFormUtil.class,
                "MAX_LENGTH");
        ActionFormUtil.checkIndexLength(max);
        assertTrue(true);

    }
    
    /**
     * checkIndexLength�̃e�X�g03
     * 
     * �`�F�b�N�ő�l���傫���l��n����IllegalArgumentEception���������邱�Ƃ��m�F����B
     * 
     * @throws Exception
     */
    public void testCheckIndexLength03() {
        try {
            ActionFormUtil.checkIndexLength(10000);
            fail();
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("index size is too long. : 10000", e.getMessage());
        }
    }
}
