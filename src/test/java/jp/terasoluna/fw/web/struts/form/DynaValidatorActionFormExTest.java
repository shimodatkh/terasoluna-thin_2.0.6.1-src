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
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.web.struts.reset.Resetter;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���I�A�N�V�����t�H�[�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 */
public class DynaValidatorActionFormExTest extends TestCase {

    /**
     * DynaValidatorActionFormEx�̃v���p�e�B�ݒ�t�@�C��
     */
    private static final String CONFIG_FILE_PATH =
        DynaValidatorActionFormExTest.class.getResource(
                "DynaValidatorActionFormExTest.xml").getPath();

    /**
     * DynaValidatorActionFormEx�������[���ݒ�t�@�C��
     */
    private final static String RULES_FILE_PATH =
        DynaValidatorActionFormExTest.class.getResource(
                "DynaValidatorActionFormExTest-rules.xml").getPath();
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
        junit.swingui.TestRunner.run(DynaValidatorActionFormExTest.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // create formEx
        // �����Ώۂł���set���\�b�h���Ăяo���Ȃ��悤�ɁA�v���p�e�B�ݒ�̋L�q���Ȃ��t�@�C�����w��
        this.formEx = (DynaValidatorActionFormEx) creator
                .create(CONFIG_FILE_PATH);

        // �i�[�l�̍쐬
        int hogeInt = 123;
        String hogeString = "data1";
        int[] hogeIntArray = {-100, 0, 10, 111};
        String[] hogeStringArray = new String[4];
        Object[] hogeObjectArray = new Object[4];
        List<Object> hogeList = new ArrayList<Object>();
        Map<String, Object> hogeMap = new HashMap<String, Object>();
        Runnable hogeRunnable = new Runnable() {
            public void run() {
            }
        };
        boolean hogeBoolean = true;
        byte hogeByte = 1;
        char hogeChar = 'A';
        double hogeDouble = 999.9;
        float hogeFloat = 999;
        short hogeShort = 9;
        long hogeLong = 9;

        for (int i = 0; i < 4; i++) {
            hogeStringArray[i] = "data" + (i + 1);
            hogeObjectArray[i] = new Integer(i + 1);
            hogeList.add(i, "data" + (i + 1));
            hogeMap.put("field" + (i + 1), "data" + (i + 1));
        }

        String[] fields = {
                "hogeInt",
                "hogeString",
                "hogeIntArray",
                "hogeStringArray",
                "hogeObjectArray",
                "hogeList",
                "hogeMap",
                "hogeRunnable",
                "hogeBoolean",
                "hogeByte",
                "hogeChar",
                "hogeDouble",
                "hogeFloat",
                "hogeShort",
                "hogeLong"
                };
        Class[] fieldClasses = {
                int.class,
                hogeString.getClass(),
                hogeIntArray.getClass(),
                hogeStringArray.getClass(),
                hogeObjectArray.getClass(),
                hogeList.getClass(),
                hogeMap.getClass(),
                hogeRunnable.getClass(),
                boolean.class,
                byte.class,
                char.class,
                double.class,
                float.class,
                short.class,
                long.class
                };
        DynaProperty[] props = new DynaProperty[fields.length];
        HashMap<String, DynaProperty> propsMap = new HashMap<String, DynaProperty>();
        for (int i = 0;i < fields.length; i++) {
            props[i] = new DynaProperty(fields[i], fieldClasses[i]);
            propsMap.put(props[i].getName(), props[i]);
        }
        DynaActionFormClass dynaActionFormClass = (DynaActionFormClass) UTUtil
                .getPrivateField(this.formEx, "dynaClass");
        UTUtil.setPrivateField(dynaActionFormClass, "properties", props);
        UTUtil.setPrivateField(dynaActionFormClass, "propertiesMap", propsMap);

        Map<String, Object> map = (Map) UTUtil.getPrivateField(this.formEx,
                "dynaValues");
        map.put("hogeInt", hogeInt);
        map.put("hogeString", hogeString);
        map.put("hogeIntArray", hogeIntArray);
        map.put("hogeStringArray", hogeStringArray);
        map.put("hogeObjectArray", hogeObjectArray);
        map.put("hogeList", hogeList);
        map.put("hogeMap", hogeMap);
        map.put("hogeRunnable", hogeRunnable);
        map.put("hogeBoolean", hogeBoolean);
        map.put("hogeByte", hogeByte);
        map.put("hogeChar", hogeChar);
        map.put("hogeDouble", hogeDouble);
        map.put("hogeFloat", hogeFloat);
        map.put("hogeShort", hogeShort);
        map.put("hogeLong", hogeLong);
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
    public DynaValidatorActionFormExTest(String name) {
        super(name);
    }

    /**
     * testGet01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException�F<br>
     *                    ���b�Z�[�W�F"No indexed value for 'null[0]'"<br>
     *
     * <br>
     * name��null��ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet01() throws Exception {
        // �e�X�g���s
        try {
            this.formEx.get(null, 0);
            fail();
        } catch (NullPointerException e) {
            // �e�X�g���ʊm�F
            assertEquals("No indexed value for 'null[0]'", e.getMessage());
        }
    }

    /**
     * testGet02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:�󕶎�<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException�F<br>
     *                    ���b�Z�[�W�F"No indexed value for '[0]'"<br>
     *
     * <br>
     * name�ɋ󕶎���ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet02() throws Exception {
        // �e�X�g���s
        try {
            this.formEx.get("", 0);
            fail();
        } catch (NullPointerException e) {
            // �e�X�g���ʊm�F
            assertEquals("No indexed value for '[0]'", e.getMessage());
        }
    }

    /**
     * testGet03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"foo"�i���݂��Ȃ��t�B�[���h���j<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException�F<br>
     *                    ���b�Z�[�W�F"No indexed value for 'foo[0]'"<br>
     *
     * <br>
     * name�ɑ��݂��Ȃ��t�B�[���h����ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet03() throws Exception {
        // �e�X�g���s
        try {
            this.formEx.get("foo", 0);
            fail();
        } catch (NullPointerException e) {
            // �e�X�g���ʊm�F
            assertEquals("No indexed value for 'foo[0]'", e.getMessage());
        }
    }

    /**
     * testGet04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeString"<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     *                    ���b�Z�[�W�F"Non-indexed property for 'hogeString[0]'"<br>
     *
     * <br>
     * fieldName�ɔz��ł�List�����t�B�[���h����ݒ肵�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet04() throws Exception {
        // �e�X�g���s
        try {
            this.formEx.get("hogeString", 0);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertEquals("Non-indexed property for 'hogeString[0]'", e
                    .getMessage());
        }
    }

    /**
     * testGet05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:-1<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * name�ɔz��^�̃t�B�[���h��ݒ肵�A����Index��-1��ݒ肵�Anull���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet05() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeStringArray", -1);

        // �e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGet06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:0<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"data1"<br>
     *
     * <br>
     * name�ɔz��^�̃t�B�[���h��ݒ肵�A����Index��0��ݒ肵�A�l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet06() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeStringArray", 0);

        // �e�X�g���ʊm�F
        assertEquals("data1", object);
    }

    /**
     * testGet07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:10<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * name�ɔz��^�̃t�B�[���h��ݒ肵�A����Index��10��ݒ肵�Anull���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet07() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeStringArray", 10);

        // �e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGet08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:-1<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h��ݒ肵�A����Index��-1��ݒ肵�Anull���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet08() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeList", -1);

        // �e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGet09() <br>
     * <br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:0<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"data1"<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h��ݒ肵�A����Index��0��ݒ肵�A�l���Ԃ鎖���m�F����B <br>
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testGet09() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeList", 0);

        // �e�X�g���ʊm�F
        assertEquals("data1", object);
    }

    /**
     * testGet10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:10<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h��ݒ肵�A����Index��10��ݒ肵�Anull���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet10() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeList", 10);

        // �e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGet11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:-1<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * name�ɔz��^�̃t�B�[���h��ݒ肵�A����Index��-1��ݒ肵�Anull���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet11() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeIntArray", -1);

        // �e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGet12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:0<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:-100<br>
     *
     * <br>
     * name�ɔz��^�̃t�B�[���h��ݒ肵�A����Index��0��ݒ肵�A�l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet12() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeIntArray", 0);

        // �e�X�g���ʊm�F
        assertEquals(-100, object);
    }

    /**
     * testGet13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:10<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * name�ɔz��^�̃t�B�[���h��ݒ肵�A����Index��10��ݒ肵�Anull���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet13() throws Exception {
        // �e�X�g���s
        Object object = this.formEx.get("hogeIntArray", 10);

        // �e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGetIndexCount01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *
     * <br>
     * fieldName��null��ݒ肵�A0���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount01() throws Exception {
        // �e�X�g���s
        int i = this.formEx.getIndexCount(null);

        // �e�X�g���ʊm�F
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:�󕶎�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *
     * <br>
     * fieldName�ɋ󕶎���ݒ肵�A0���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount02() throws Exception {
        // �e�X�g���s
        int i = this.formEx.getIndexCount("");

        // �e�X�g���ʊm�F
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"foo"�i���݂��Ȃ��t�B�[���h���j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *
     * <br>
     * fieldNmae�ɑ��݂��Ȃ��t�B�[���h����ݒ肵�A0���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount03() throws Exception {
        // �e�X�g���s
        int i = this.formEx.getIndexCount("foo");

        // �e�X�g���ʊm�F
        assertEquals(0, i);
    }

    /**
     * testGetIndexCount04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeString"<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:String�^:"data0"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *
     * <br>
     * fieldName�ɔz��ł�List�����t�B�[���h����ݒ肵�A1���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount04() throws Exception {
        // �e�X�g���s
        int i = this.formEx.getIndexCount("hogeString");

        // �e�X�g���ʊm�F
        assertEquals(1, i);
    }

    /**
     * testGetIndexCount05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeStringArray"<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:4<br>
     *
     * <br>
     * fieldName�ɔz��^�̃t�B�[���h��ݒ肵�A�������l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount05() throws Exception {
        // �e�X�g���s
        int i = this.formEx.getIndexCount("hogeStringArray");

        // �e�X�g���ʊm�F
        assertEquals(4, i);
    }

    /**
     * testGetIndexCount06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeList"<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:4<br>
     *
     * <br>
     * fieldName��List�^�̃t�B�[���h��ݒ肵�A�������l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount06() throws Exception {
        // �e�X�g���s
        int i = this.formEx.getIndexCount("hogeList");

        // �e�X�g���ʊm�F
        assertEquals(4, i);
    }

    /**
     * testGetIndexCount07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeMap"<br>
     *         (���) fieldName�Ŏw�肳�ꂽ�t�B�[���h:Map�^:[field1="data1", field2="data2", field3="data3", field4=data4"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:4<br>
     *
     * <br>
     * fieldName��Map�^�̃t�B�[���h��ݒ肵�A�������l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount07() throws Exception {
        // �e�X�g���s
        int i = this.formEx.getIndexCount("hogeMap");

        // �e�X�g���ʊm�F
        assertEquals(4, i);
    }

    /**
     * testReset01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:�f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) DynaValidatorActionFormEx_ResetterStub01#reset():�����s<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�AResetter��null�������ꍇ�A�ω������ɏI�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset01() throws Exception {
        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        DynaValidatorActionFormEx_ActionServletStub01 servlet =
            new DynaValidatorActionFormEx_ActionServletStub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, null);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // �e�X�g���s
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset02() <br>
     * <br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:�f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:Resetter�ȊO�̃I�u�W�F�N�g<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) DynaValidatorActionFormEx_ResetterStub01#reset():�����s<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ����O��<br>
     *                    ClassCastException<br>
     *
     * <br>
     * �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�AResetter�ȊO�̃I�u�W�F�N�g���擾�����ꍇ�A���O���o�͂��A�I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testReset02() throws Exception {
        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, new Object());
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // �e�X�g���s
        this.formEx.reset(mapping, request);

        // �e�X�g���ʊm�F
        assertTrue(LogUTUtil.checkError("", new ClassCastException()));
    }

    /**
     * testReset03() <br>
     * <br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:�f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:Resetter�I�u�W�F�N�g�iDynaValidatorActionFormEx_ResetterStub01�j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) DynaValidatorActionFormEx_ResetterStub01#reset():���s<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�Aresetter��Resetter�I�u�W�F�N�g�̏ꍇ�Areset()���\�b�h���Ă΂�邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testReset03() throws Exception {
        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();
        // ���Z�b�^�[�̐���
        ResetterImpl resetter = new DynaValidatorActionFormEx_ResetterStub01();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // �e�X�g���s
        this.formEx.reset(mapping, request);

        // �e�X�g���ʊm�F
        assertNotNull(request.getAttribute("SUCCESS"));
    }

    /**
     * testReset04() <br>
     * <br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:�T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) DynaValidatorActionFormEx_ResetterStub01#reset():�����s<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * �T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�AResetter��null�������ꍇ�A�ω������ɏI�����邱�Ƃ��m�F����B <br>
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testReset04() throws Exception {
        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "sub1";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, null);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // �e�X�g���s
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset05() <br>
     * <br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:�T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:Resetter�ȊO�̃I�u�W�F�N�g<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) DynaValidatorActionFormEx_ResetterStub01#reset():�����s<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ����O��<br>
     *                    ClassCastException<br>
     *
     * <br>
     * �T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�AResetter�ȊO�̃I�u�W�F�N�g���擾�����ꍇ�A���O���o�͂��A�I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testReset05() throws Exception {
        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "sub1";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, new Object());
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // �e�X�g���s
        this.formEx.reset(mapping, request);

        // �e�X�g���ʊm�F
        assertTrue(LogUTUtil.checkError("", new ClassCastException()));
    }

    /**
     * testReset06() <br>
     * <br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:�T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:Resetter�I�u�W�F�N�g�iDynaValidatorActionFormEx_ResetterStub01�j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) DynaValidatorActionFormEx_ResetterStub01#reset():���s<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * �T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�Aresetter��Resetter�I�u�W�F�N�g�̏ꍇ�Areset()���\�b�h���Ă΂�邱�Ƃ��m�F����B�B
     * <br>
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testReset06() throws Exception {
        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        DynaValidatorActionFormEx_ActionServletStub01 servlet = new DynaValidatorActionFormEx_ActionServletStub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "sub1";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();
        // ���Z�b�^�[�̐���
        ResetterImpl resetter = new DynaValidatorActionFormEx_ResetterStub01();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        // �e�X�g���s
        this.formEx.reset(mapping, request);

        // �e�X�g���ʊm�F
        assertNotNull(request.getAttribute("SUCCESS"));
    }

    /**
     * testSetStringintObject01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) index:0<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException�F<br>
     *                    ���b�Z�[�W�F"No indexed value for 'null[0]'"<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��null��ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject01() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set(null, 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for 'null[0]'", e.getMessage());
        }
    }

    /**
     * testSetStringintObject02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:�󕶎�<br>
     *         (����) index:0<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException�F<br>
     *                    ���b�Z�[�W�F"No indexed value for '[0]'"<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name�ɋ󕶎���ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject02() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for '[0]'", e.getMessage());
        }
    }

    /**
     * testSetStringintObject03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"foo"�i���݂��Ȃ��t�B�[���h���j<br>
     *         (����) index:0<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException�F<br>
     *                    ���b�Z�[�W�F"No indexed value for 'foo[0]'"<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name�ɑ��݂��Ȃ��t�B�[���h����ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject03() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("foo", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for 'foo[0]'", e.getMessage());
        }
    }

    /**
     * testSetStringintObject04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeString"<br>
     *         (����) index:0<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     *                    ���b�Z�[�W�F"Non-indexed property for 'hogeString[0]'"<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��String�^�̃t�B�[���h����ݒ肵�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject04() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeString", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            assertEquals("Non-indexed property for 'hogeString[0]'", e
                    .getMessage());
        }
    }

    /**
     * testSetStringintObject05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:-1<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ArrayIndexOutOfBoundsException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��-1��ݒ肵�AArrayIndexOutOfBoundsException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject05() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeStringArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:0<br>
     *         (����) value:null<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeStringArray[0] = null<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��0��ݒ肵�A�Y��0�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject06() throws Exception {

        //�e�X�g���s
        this.formEx.set("hogeStringArray", 0, null);

        //�e�X�g���ʊm�F
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertNull(resultArray[0]);
    }

    /**
     * testSetStringintObject07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:1<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeStringArray[1] = "hello"<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��1��ݒ肵�A�Y��1�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject07() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeStringArray", 1, "hello");

        //�e�X�g���ʊm�F
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertEquals("hello", resultArray[1]);
    }

    /**
     * testSetStringintObject08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:2<br>
     *         (����) value:Integer(5)<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��2��ݒ肵�Avalue���s�K�؂Ȍ^�̏ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject08() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeStringArray", 2, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F;
        }
    }

    /**
     * testSetStringintObject09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:10<br>
     *         (����) value:null<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeStringArray[10] = null<br>
     *
     * <br>
     * name�ɃT�C�Y4��String[]�^�̃t�B�[���h����ݒ肵�A����Index��10��ݒ肵�A�Y��10�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject09() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeStringArray", 10, null);

        //�e�X�g���ʊm�F
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertNull(resultArray[10]);
    }

    /**
     * testSetStringintObject10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:11<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeStringArray[11] = "hello"<br>
     *
     * <br>
     * name�ɃT�C�Y4��String[]�^�̃t�B�[���h����ݒ肵�A����Index��11��ݒ肵�A�Y��11�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject10() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeStringArray", 11, "hello");

        //�e�X�g���ʊm�F
        String[] resultArray = (String[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeStringArray");
        assertEquals("hello", resultArray[11]);
    }

    /**
     * testSetStringintObject11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:12<br>
     *         (����) value:Integer(5)<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:String�z��^:["data1", "data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name�ɃT�C�Y4��String[]�^�̃t�B�[���h����ݒ肵�A����Index��12��ݒ肵�Avalue���s�K�؂Ȍ^�̏ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject11() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeStringArray", 12, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:-1<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IndexOutOfBoundsException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��-1��ݒ肵�AIndexOutOfBoundsException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject12() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeList", -1, "hello");
            fail();
        } catch (IndexOutOfBoundsException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:0<br>
     *         (����) value:null<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeList.get(0) = null<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��0��ݒ肵�A�Y��0�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject13() throws Exception {
        // �O������̊m�F
        assertNotNull(this.formEx.get("hogeList", 0));

        //�e�X�g���s
        this.formEx.set("hogeList", 0, null);

        //�e�X�g���ʊm�F
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertNull(resultList.get(0));
    }

    /**
     * testSetStringintObject14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:1<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeList.get(1) = "hello"<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��1��ݒ肵�A�Y��1�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject14() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeList", 1, "hello");

        //�e�X�g���ʊm�F
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals("hello", resultList.get(1));
    }

    /**
     * testSetStringintObject15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:2<br>
     *         (����) value:Integer(5)<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeList.get(2) = Integer(5)<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��2��ݒ肵�A�Y��2�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject15() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeList", 2, new Integer(5));

        //�e�X�g���ʊm�F
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals(new Integer(5), resultList.get(2));
    }

    /**
     * testSetStringintObject16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:10<br>
     *         (����) value:null<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeList.get(10) = null<br>
     *
     * <br>
     * name�ɃT�C�Y4��List�^�̃t�B�[���h����ݒ肵�A����Index��10��ݒ肵�A�Y��10�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject16() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeList", 10, null);

        //�e�X�g���ʊm�F
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertNull(resultList.get(10));
    }

    /**
     * testSetStringintObject17()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:11<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeList.get(11) = "hello"<br>
     *
     * <br>
     * name�ɃT�C�Y4��List�^�̃t�B�[���h����ݒ肵�A����Index��11��ݒ肵�A�Y��11�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject17() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeList", 11, "hello");

        //�e�X�g���ʊm�F
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals("hello", resultList.get(11));
    }

    /**
     * testSetStringintObject18()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:12<br>
     *         (����) value:Integer(5)<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:List�^:["data1","data2", "data3", "data4"]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeList.get(12) = Integer(5)<br>
     *
     * <br>
     * name�ɃT�C�Y4��List�^�̃t�B�[���h����ݒ肵�A����Index��12��ݒ肵�A�Y��12�Ɉ���value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject18() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeList", 12, new Integer(5));

        //�e�X�g���ʊm�F
        List resultList = (List) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeList");
        assertEquals(new Integer(5), resultList.get(12));
    }

    /**
     * testSetStringintObject19()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:-1<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ArrayIndexOutOfBoundsException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�AArrayIndexOutOfBoundsException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject19() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeIntArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject20()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:0<br>
     *         (����) value:null<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject20() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeIntArray", 0, null);
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject21()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:1<br>
     *         (����) value:Integer(5)<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeStringArray[1] = Integer(5)<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"1"��ݒ肵�A�Y��"1"��value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject21() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeIntArray", 1, new Integer(5));

        //�e�X�g���ʊm�F
        int[] resultArray = (int[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeIntArray");
        assertEquals(new Integer(5), (Integer) resultArray[1]);
    }

    /**
     * testSetStringintObject22()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:2<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"2"��ݒ肵�Avalue���s�K�؂Ȍ^�̏ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject22() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeIntArray", 2, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject23()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:10<br>
     *         (����) value:null<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"10"��ݒ肵�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject23() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeIntArray", 10, null);
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject24()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:11<br>
     *         (����) value:Integer(5)<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:hogeStringArray[11] = Integer(5)<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"11"��ݒ肵�Avalue���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject24() throws Exception {
        //�e�X�g���s
        this.formEx.set("hogeIntArray", 11, new Integer(5));

        //�e�X�g���ʊm�F
        int[] resultArray = (int[]) ((HashMap) UTUtil.getPrivateField(
                this.formEx, "dynaValues")).get("hogeIntArray");
        assertEquals(new Integer(5), (Integer) resultArray[11]);
    }

    /**
     * testSetStringintObject25()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:12<br>
     *         (����) value:"hello"<br>
     *         (���) name�Ŏw�肳�ꂽ�t�B�[���h:int�z��^:[-100, 0, 10, 111]<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:�|<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"12"��ݒ肵�Avalue���s�K�؂Ȍ^�̏ꍇ�AIllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject25() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeIntArray", 12, "hello");
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetStringintObject26()
     * 
     * �z�񒷃`�F�b�N�ő�l�ȏ�̃C���f�b�N�X��ݒ肵��IllegalArgumentException���������邱�ƁB
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject26() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeIntArray", 10000, "hello");
            fail();
        } catch (Exception e) {
            //�e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("index size is too long. : 10000", e.getMessage());
        }
    }
    
    /**
     * testSetStringintObject27()
     * 
     * ���X�g���`�F�b�N�ő�l�ȏ�̃C���f�b�N�X��ݒ肵��IllegalArgumentException���������邱�ƁB
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStringintObject27() throws Exception {
        //�e�X�g���s
        try {
            this.formEx.set("hogeList", 10000, "hello");
            fail();
        } catch (Exception e) {
            //�e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("index size is too long. : 10000", e.getMessage());
        }
    }
    
    
    /**
     * testIsModified01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) modified:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * �i�[����Ă���modified�𐳏�Ɏ擾���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsModified01() throws Exception {
        // �O����
        UTUtil.setPrivateField(this.formEx, "modified", true);

        // �e�X�g���{�E����
        assertTrue(this.formEx.isModified());
    }

    /**
     * testSetModified01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) modified:true<br>
     *         (���) modified:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) modified:true<br>
     *
     * <br>
     * �����Ɏw�肵���l��modified�ɐ���Ɋi�[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetModified01() throws Exception {
        // �e�X�g���s
        this.formEx.setModified(true);

        // �e�X�g����
        assertTrue((Boolean) UTUtil.getPrivateField(formEx, "modified"));
    }

    /**
     * testSetIndexedValue01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:hoge<br>
     *                index:100<br>
     *                value:hoge<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) �w�肵��������set���\�b�h���Ăяo����邱��<br>
     *
     * <br>
     * �����Ɏw�肵���l��set���\�b�h���Ăяo�����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue01() throws Exception {
        DynaValidatorActionFormExStub01 stub =
            new DynaValidatorActionFormExStub01();
        stub.setIndexedValue("hoge", 100, "hoge");

        assertEquals("hoge", stub.name);
        assertEquals(100, stub.index);
        assertEquals("hoge", stub.value);
    }

    /**
     * testGetIndexedValue01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:hoge<br>
     *                index:100<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"hoge"<br>
     *         (��ԕω�) �w�肵��������get���\�b�h���Ăяo����邱��<br>
     *
     * <br>
     * �����Ɏw�肵���l��get���\�b�h���Ăяo�����Ƃ��m�F����B�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue01() throws Exception {
        DynaValidatorActionFormExStub01 stub =
            new DynaValidatorActionFormExStub01();
        Object result = stub.getIndexedValue("hoge", 100);

        assertEquals("hoge", stub.name);
        assertEquals(100, stub.index);
        assertEquals("called", result);
    }

}
