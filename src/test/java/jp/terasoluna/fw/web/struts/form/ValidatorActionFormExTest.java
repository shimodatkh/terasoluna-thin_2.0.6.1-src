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

import java.util.List;

import jp.terasoluna.fw.web.struts.reset.Resetter;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Validator�t���[�����[�N�Ή��A�N�V�����t�H�[�����N���X�B<br>
 * <br>
 * set(String, Object)�̃e�X�g�P�[�X�́A�ُ�n�ꌏ�̂݃e�X�g���s���B
 * ����n�̃e�X�g��set(String, int, Object)�ɂĕ�܂���B<br>
 * <br>
 * �y�O������z<br>
 * ValidatorActionFormEx_ValidatorActionFormExStub01�N���X��
 * �쐬���ȉ��̃f�[�^��ێ�����B<br>
 * int hogeInt = 123<br>
 * String hogeString = "data1"<br>
 * int[] hogeIntArray = {-100,0,10,111}<br>
 * String[] hogeStringArray = {"data1","data2","data3","data4"}<br>
 * Object[] hogeObjectArray = {new Integer(1),new Integer(2),new Integer(3),
 * new Integer(4)}<br>
 * List hogeList = {"data1","data2","data3","data4"}<br>
 * Map hogeMap = {key="field1" value="data1"}<br>
 *               {key="field2" value="data2"}<br>
 *               {key="field3" value="data3"}<br>
 *               {key="field4" value="data4"}<br>
 * Runnable hogeRunnable = new Runnable()
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 */
public class ValidatorActionFormExTest extends TestCase {

    /**
     * �e�X�g�Ώۂ̃C���X�^���X
     */
    private ValidatorActionFormEx formEx;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidatorActionFormExTest.class);
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
        // create formEx
        this.formEx = new ValidatorActionFormEx_ValidatorActionFormExStub01();
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
    public ValidatorActionFormExTest(String name) {
        super(name);
    }

    /**
     * testGetIndexedValue01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) fieldName:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *                    �����b�Z�[�W��<br>
     *                    "No indexed value for 'null[0]'"<br>
     *
     * <br>
     * fieldName��null��ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue01() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.getIndexedValue(null, 0);
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for 'null[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) fieldName:�󕶎�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *                    �����b�Z�[�W��<br>
     *                    "No mapped value for '(hello world!)'"<br>
     *
     * <br>
     * fieldName�ɋ󕶎���ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue02() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.getIndexedValue("", 0);
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for '[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) fieldName:"foo"<br>
     *                (���݂��Ȃ��t�B�[���h)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *                    �����b�Z�[�W��<br>
     *                    "No indexed value for 'foo[0]'"<br>
     *
     * <br>
     * fieldNmae�ɑ��݂��Ȃ��t�B�[���h����ݒ肵�A
     * NullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue03() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.getIndexedValue("foo", 0);
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for 'foo[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeString"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �����b�Z�[�W��<br>
     *                    "Non-indexed property for 'hogeString[0]'"<br>
     *
     * <br>
     * fieldName�ɔz��ł�List�����t�B�[���h����ݒ肵�A
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue04() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.getIndexedValue("hogeString", 0);
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            assertEquals("Non-indexed property for 'hogeString[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeStringArray"<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:null<br>
     *
     * <br>
     * fieldName��String�z��^�̃t�B�[���h��ݒ肵�A����Index��"-1"��ݒ肵�A
     * "null"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue05() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeStringArray", -1);

        //�e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGetIndexedValue06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeStringArray"<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:"data1"<br>
     *
     * <br>
     * fieldName��String�z��^�̃t�B�[���h��ݒ肵�A����Index��"0"��ݒ肵�A
     * �l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue06() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeStringArray", 0);

        //�e�X�g���ʊm�F
        assertEquals("data1", object);
    }

    /**
     * testGetIndexedValue07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeStringArray"<br>
     *         (����) index:10<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:null<br>
     *
     * <br>
     * fieldName��String�z��^�̃t�B�[���h��ݒ肵�A����Index��"10"��ݒ肵�A
     * "null"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue07() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeStringArray", 10);

        //�e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGetIndexedValue08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeList"<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:null<br>
     *
     * <br>
     * fieldName��List�^�̃t�B�[���h��ݒ肵�A����Index��"-1"��ݒ肵�A
     * "null"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue08() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeList", -1);

        //�e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGetIndexedValue09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeList"<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:"data1"<br>
     *
     * <br>
     * fieldName��List�^�̃t�B�[���h��ݒ肵�A����Index��"0"��ݒ肵�A
     * �l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue09() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeList", 0);

        //�e�X�g���ʊm�F
        assertEquals("data1", object);
    }

    /**
     * testGetIndexedValue10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeList"<br>
     *         (����) index:10<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:null<br>
     *
     * <br>
     * fieldName��List�^�̃t�B�[���h��ݒ肵�A����Index��"10"��ݒ肵�A
     * "null"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue10() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeList", 10);

        //�e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGetIndexedValue11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeInt"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �����b�Z�[�W��<br>
     *                    "Non-indexed property for 'hogeInt[0]'"<br>
     *
     * <br>
     * fieldName��int�^�̃t�B�[���h����ݒ肵�AIllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue11() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.getIndexedValue("hogeInt", 0);
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            assertEquals("Non-indexed property for 'hogeInt[0]'",
                    e.getMessage());
        }
    }

    /**
     * testGetIndexedValue12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeIntArray"<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:null<br>
     *
     * <br>
     * fieldName��int[]�^�̃t�B�[���h��ݒ肵�A����Index��"-1"��ݒ肵�A
     * "null"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue12() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeIntArray", -1);

        //�e�X�g���ʊm�F
        assertNull(object);
    }

    /**
     * testGetIndexedValue13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeIntArray"<br>
     *         (����) index:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:Integer(-100)<br>
     *
     * <br>
     * fieldName��int[]�^�̃t�B�[���h��ݒ肵�A����Index��"0"��ݒ肵�A
     * �l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue13() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeIntArray", 0);

        //�e�X�g���ʊm�F
        assertEquals(new Integer(-100), object);
    }

    /**
     * testGetIndexedValue14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) fieldName:"hogeIntArray"<br>
     *         (����) index:10<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:null<br>
     *
     * <br>
     * fieldName��int[]�^�̃t�B�[���h��ݒ肵�A����Index��"10"��ݒ肵�A
     * "null"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedValue14() throws Exception {

        //�e�X�g���s
        Object object = this.formEx.getIndexedValue("hogeIntArray", 10);

        //�e�X�g���ʊm�F
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
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:0<br>
     *
     * <br>
     * fieldName��"null"��ݒ肵�A"0"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount01() throws Exception {

        //�e�X�g���s
        int i = this.formEx.getIndexCount(null);

        //�e�X�g���ʊm�F
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
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:0<br>
     *
     * <br>
     * fieldName�ɋ󕶎���ݒ肵�A"0"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount02() throws Exception {

        //�e�X�g���s
        int i = this.formEx.getIndexCount("");

        //�e�X�g���ʊm�F
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
     * ���͒l�F(����) fieldName:"foo"(���݂��Ȃ��t�B�[���h)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:0<br>
     *
     * <br>
     * fieldNmae�ɑ��݂��Ȃ��t�B�[���h����ݒ肵�A"0"���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount03() throws Exception {

        //�e�X�g���s
        int i = this.formEx.getIndexCount("foo");

        //�e�X�g���ʊm�F
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
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:1<br>
     *
     * <br>
     * fieldName��String�^�t�B�[���h����ݒ肵�A�������l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount04() throws Exception {

        //�e�X�g���s
        int i = this.formEx.getIndexCount("hogeString");

        //�e�X�g���ʊm�F
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
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:4<br>
     *
     * <br>
     * fieldName��String[]�^�̃t�B�[���h��ݒ肵�A�������l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount05() throws Exception {

        //�e�X�g���s
        int i = this.formEx.getIndexCount("hogeStringArray");

        //�e�X�g���ʊm�F
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
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:4<br>
     *
     * <br>
     * fieldName��List�^�̃t�B�[���h��ݒ肵�A�������l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount06() throws Exception {

        //�e�X�g���s
        int i = this.formEx.getIndexCount("hogeList");

        //�e�X�g���ʊm�F
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
     *
     * <br>
     * ���Ғl�F(�߂�l) fieldName�Ŏw�肳�ꂽ�t�B�[���h:4<br>
     *
     * <br>
     * fieldName��Map�^�̃t�B�[���h��ݒ肵�A�������l���Ԃ鎖���m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexCount07() throws Exception {

        //�e�X�g���s
        int i = this.formEx.getIndexCount("hogeMap");

        //�e�X�g���ʊm�F
        assertEquals(4, i);
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
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *                    �����b�Z�[�W��<br>
     *                    "No indexed value for 'null[0]'"<br>
     *
     * <br>
     * name��"null"��ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue01() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue(null, 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for 'null[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:�󕶎�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *                    �����b�Z�[�W��<br>
     *                    "No indexed value for '[0]'"<br>
     *
     * <br>
     * name�ɋ󕶎���ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue02() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for '[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"foo"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *                    �����b�Z�[�W��<br>
     *                    "No indexed value for 'foo[0]'"<br>
     *
     * <br>
     * name�ɑ��݂��Ȃ��t�B�[���h����ݒ肵�A
     * NullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue03() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("foo", 0, "hello");
            fail();
        } catch (NullPointerException e) {
            //�e�X�g���ʊm�F
            assertEquals("No indexed value for 'foo[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeString"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �����b�Z�[�W��<br>
     *                    "Non-indexed property for 'hogeString[0]'"<br>
     *
     * <br>
     * name��String�^�̃t�B�[���h����ݒ肵�A
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue04() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeString", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            assertEquals("Non-indexed property for 'hogeString[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ArrayIndexOutOfBoundsException<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��"-1"��ݒ肵�A
     * ArrayIndexOutOfBoundsException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue05() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeStringArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * testSetIndexedValue06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:0<br>
     *         (����) value:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:Index="0"��value���i�[�����<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��"0"��ݒ肵�A
     * �Y��"0"�Ɉ����ɂƂ���"null"���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue06() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeStringArray", 0, null);

        //�e�X�g���ʊm�F
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertNull(str[0]);
    }

    /**
     * testSetIndexedValue07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:1<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                      Index="1"��value���i�[�����<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��"1"��ݒ肵�A
     * �Y��"1"�Ɉ����ɂƂ���"hello"���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue07() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeStringArray", 1, "hello");

        //�e�X�g���ʊm�F
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertEquals("hello", str[1]);
    }

    /**
     * testSetIndexedValue08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:2<br>
     *         (����) value:Integer(5)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * name��String[]�^�̃t�B�[���h����ݒ肵�A����Index��"2"��ݒ肵�A
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue08() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeStringArray", 2, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:10<br>
     *         (����) value:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                         Index="10"��value���i�[�����<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��String[]�^�̃t�B�[���h����ݒ肵�A����Index��"10"��ݒ肵�A
     * �Y��"10"�Ɉ����ɂƂ���"null"���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue09() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeStringArray", 10, null);

        //�e�X�g���ʊm�F
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertNull(str[10]);
    }

    /**
     * testSetIndexedValue10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:11<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                        Index="11"��value���i�[�����<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��String[]�^�̃t�B�[���h����ݒ肵�A����Index��"11"��ݒ肵�A
     * �Y��"11"�Ɉ����ɂƂ���"hello"���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue10() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeStringArray", 11, "hello");

        //�e�X�g���ʊm�F
        String[] str = (String[]) UTUtil.getPrivateField(
                formEx, "hogeStringArray");
        assertEquals("hello", str[11]);
    }

    /**
     * testSetIndexedValue11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeStringArray"<br>
     *         (����) index:12<br>
     *         (����) value:Integer(5)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��String[]�^�̃t�B�[���h����ݒ肵�A����Index��"12"��ݒ肵�A
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue11() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeStringArray", 12, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IndexOutOfBoundsException<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��"-1"��ݒ肵�A
     * IndexOutOfBoundsException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue12() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeList", -1, "hello");
            fail();
        } catch (IndexOutOfBoundsException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue13()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:0<br>
     *         (����) value:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                          Index="0"��value���i�[�����<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��"0"��ݒ肵�A
     * �Y��"0"�Ɉ����ɂƂ���"null"���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue13() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeList", 0, null);

        //�e�X�g���ʊm�F
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertNull(list.get(0));
    }

    /**
     * testSetIndexedValue14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:1<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                         Index="1"��value���i�[�����<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��"1"��ݒ肵�A
     * �Y��"1"�Ɉ����ɂƂ���"hello"���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue14() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeList", 1, "hello");

        //�e�X�g���ʊm�F
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals("hello", list.get(1));
    }

    /**
     * testSetIndexedValue15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:2<br>
     *         (����) value:Integer(5)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                            Index="2"��value���i�[�����<br>
     *
     * <br>
     * name��List�^�̃t�B�[���h����ݒ肵�A����Index��"2"��ݒ肵�A
     * �Y��"2"�Ɉ����ɂƂ���Integer���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue15() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeList", 2, new Integer(5));

        //�e�X�g���ʊm�F
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals(5, ((Integer)list.get(2)).intValue());
    }

    /**
     * testSetIndexedValue16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:10<br>
     *         (����) value:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                         Index="10"��value���i�[�����<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��List�^�̃t�B�[���h����ݒ肵�A����Index��"10"��ݒ肵�A
     * �Y��"10"�Ɉ����ɂƂ���"null"���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue16() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeList", 10, null);

        //�e�X�g���ʊm�F
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertNull(list.get(10));
    }

    /**
     * testSetIndexedValue17()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:11<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                         Index="11"��value���i�[�����<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��List�^�̃t�B�[���h����ݒ肵�A����Index��"11"��ݒ肵�A
     * �Y��"11"�Ɉ����ɂƂ���"helloE���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue17() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeList", 11, "hello");

        //�e�X�g���ʊm�F
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals("hello", list.get(11));
    }

    /**
     * testSetIndexedValue18()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeList"<br>
     *         (����) index:12<br>
     *         (����) value:Integer(5)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                          Index="12"��value���i�[�����<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��List�^�̃t�B�[���h����ݒ肵�A����Index��"12"��ݒ肵�A
     * �Y��"12"�Ɉ����ɂƂ���Integer���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue18() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeList", 12, new Integer(5));

        //�e�X�g���ʊm�F
        List list = (List) UTUtil.getPrivateField(formEx, "hogeList");
        assertEquals(5, ((Integer)list.get(12)).intValue());
    }

    /**
     * testSetIndexedValue19()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeInt"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �����b�Z�[�W��<br>
     *                    "Non-indexed property for 'hogeInt[2]'"<br>
     *
     * <br>
     * name��int�^�̃t�B�[���h����ݒ肵�AIllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue19() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeInt", 2, new Integer(5));
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            assertEquals("Non-indexed property for 'hogeInt[2]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue20()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:-1<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ArrayIndexOutOfBoundsException<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�AArrayIndexOutOfBoundsException��
     * �X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue20() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeIntArray", -1, "hello");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue21()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:0<br>
     *         (����) value:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�AIllegalArgumentException��
     * �X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue21() throws Exception {
        try {
            //�e�X�g���s
            this.formEx.setIndexedValue("hogeIntArray", 0, null);
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue22()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:1<br>
     *         (����) value:Integer(5)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:
     *                       Index="1"��value���i�[�����<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"1"��ݒ肵�A
     * �Y��"1"��value���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue22() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeIntArray", 1, new Integer(5));

        //�e�X�g���ʊm�F
        int[] in = (int[]) UTUtil.getPrivateField(formEx, "hogeIntArray");
        assertEquals(5, in[1]);
    }

    /**
     * testSetIndexedValue23()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:2<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * name��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"2"��ݒ肵�A
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue23() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeIntArray", 2, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue24()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:10<br>
     *         (����) value:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��int[]�^�̃t�B�[���h����ݒ肵�A
     * ����Index��"10"��ݒ肵�ANullPointerException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue24() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeIntArray", 10, null);
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue25()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:11<br>
     *         (����) value:Integer(5)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) name�Ŏw�肳�ꂽ�t�B�[���h:Index="11"��
     *                   value���i�[�����<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��int[]�^�̃t�B�[���h����ݒ肵�A
     * ����Index��"11"��ݒ肵�Avalue���i�[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue25() throws Exception {

        //�e�X�g���s
        this.formEx.setIndexedValue("hogeIntArray", 11, new Integer(5));

        //�e�X�g���ʊm�F
        int[] in = (int[]) UTUtil.getPrivateField(formEx, "hogeIntArray");
        assertEquals(5, in[11]);
    }

    /**
     * testSetIndexedValue26()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"hogeIntArray"<br>
     *         (����) index:12<br>
     *         (����) value:"hello"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *
     * <br>
     * name�ɃT�C�Y"4"��int[]�^�̃t�B�[���h����ݒ肵�A����Index��"12"��ݒ肵�A
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue26() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeIntArray", 12, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
        }
    }

    /**
     * testSetIndexedValue27()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"stringValue"<br>
     *         (����) index:0<br>
     *         (����) value:"hello"<br>
     * �O������F(ValidatorActionFormEx�T�u�N���X)
     *            setStringValues�ŗ�O����������B
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                         ���b�Z�[�W�FCannot set property for 'stringValues[0]'<br>
     *
     * <br>
     * �l�̐ݒ莞�ɗ�O����������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue27() throws Exception {

        ValidatorActionFormEx test =
            new ValidatorActionFormEx_ValidatorActionFormExStub02();

        //�e�X�g���s
        try {
            test.setIndexedValue("stringValues", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            assertEquals("Cannot set property for 'stringValues[0]'",
                    e.getMessage());
        }
    }

    /**
     * testSetIndexedValue28()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"valueList"<br>
     *         (����) index:0<br>
     *         (����) value:"hello"<br>
     * �O������F(ValidatorActionFormEx�T�u�N���X)
     *            setValueList�ŗ�O����������B
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                         ���b�Z�[�W�FCannot set property for 'valueList[0]'<br>
     *
     * <br>
     * �l�̐ݒ莞�ɗ�O����������ꍇ�AIllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue28() throws Exception {

        ValidatorActionFormEx test =
            new ValidatorActionFormEx_ValidatorActionFormExStub02();

        //�e�X�g���s
        try {
            test.setIndexedValue("valueList", 0, "hello");
            fail();
        } catch (IllegalArgumentException e) {
            //�e�X�g���ʊm�F
            assertEquals("Cannot set property for 'valueList[0]'",
                    e.getMessage());
        }
    }
    
    /**
     * testSetIndexedValue29()
     * 
     * �z�񒷃`�F�b�N�ő�l�ȏ�̃C���f�b�N�X��ݒ肵��IllegalArgumentException���������邱�ƁB
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue29() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeStringArray", 10000, "hello");
            fail();
        } catch (Exception e) {
            //�e�X�g���ʊm�F
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("index size is too long. : 10000", e.getMessage());
        }
    }
    
    /**
     * testSetIndexedValue30()
     * 
     * ���X�g���`�F�b�N�ő�l�ȏ�̃C���f�b�N�X��ݒ肵��IllegalArgumentException���������邱�ƁB
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetIndexedValue30() throws Exception {

        //�e�X�g���s
        try {
            this.formEx.setIndexedValue("hogeList", 10000, "hello");
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
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) modified:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsModified01() throws Exception {
        // �O����
        UTUtil.setPrivateField(formEx, "modified", true);

        // �e�X�g���{
        boolean result = formEx.isModified();

        // ����
        assertTrue(result);
    }

    /**
     * testSetModified01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) modified:true<br>
     *         (���) modified:false<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) modified:true<br>
     *
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetModified01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        boolean modifiedFlag = true;

        // �e�X�g���s
        formEx.setModified(modifiedFlag);

        // �e�X�g����
        // �ݒ肵��ActionMessage�Ɠ���ł��邱�ƁB
        Boolean result = (Boolean) UTUtil.getPrivateField(formEx, "modified");
        assertEquals(modifiedFlag, result.booleanValue());
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
     *         (����) request:
     *                  �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ResetterImpl#reset()�̌Ăяo��:-<br>
     *
     * <br>
     * �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�A
     * Resetter��"null"�������ꍇ�A���������ɏI�����邱�Ƃ��m�F����B
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
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
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

        //�e�X�g���s
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:
     *                  �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:Resetter�ȊO�̃I�u�W�F�N�g<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ResetterImpl#reset()�̌Ăяo��:-<br>
     *                   ���O�F�����O��<br>
     *                         �G���[���O�F""<br>
     *                         ����O��<br>
     *                         ClassCastException<br>
     *
     * <br>
     * �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�A
     * Resetter�ȊO�̃I�u�W�F�N�g���擾�����ꍇ�AClassCastException���������āA
     * ���O���o�͂��āA�I�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset02() throws Exception {

        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
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

        //�e�X�g���s
        this.formEx.reset(mapping, request);

        //�e�X�g���ʊm�F
        assertTrue(
                LogUTUtil.checkError("",new ClassCastException()));
    }

    /**
     * testReset03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:
     *                   �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:Resetter�I�u�W�F�N�g<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ResetterImpl#reset()�̌Ăяo��:�Ăяo�����<br>
     *                    request��"SUCCESS"���L�[�̒l���ݒ肳��邱�Ƃ��m�F�B
     *
     * <br>
     * �f�t�H���g���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�A
     * Resetter���A�t�B�[���h�����������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset03() throws Exception {

        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();
        // ���Z�b�^�[�̐���
        ResetterImpl resetter = new ValidatorActionFormEx_ResetterStub01();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //�e�X�g���s
        this.formEx.reset(mapping, request);

        //�e�X�g���ʊm�F
        assertNotNull(request.getAttribute("SUCCESS"));
    }

    /**
     * testReset04()
     * <br><br>
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
     * ���Ғl�F(��ԕω�) ResetterImpl#reset()�̌Ăяo��:-<br>
     *
     * <br>
     * �T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�A
     * Resetter��"null"�������ꍇ�A���������ɏI�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset04() throws Exception {

        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
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

        //�e�X�g���s
        this.formEx.reset(mapping, request);
    }

    /**
     * testReset05()
     * <br><br>
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
     * ���Ғl�F(��ԕω�) ResetterImpl#reset()�̌Ăяo��:-<br>
     *                   ���O�F�����O��<br>
     *                         �G���[���O�F""<br>
     *                         ����O��<br>
     *                         ClassCastException<br>
     *
     * <br>
     * �T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�A
     * Resetter�ȊO�̃I�u�W�F�N�g���擾�����ꍇ�A���������ɏI�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset05() throws Exception {

        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
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

        //�e�X�g���s
        this.formEx.reset(mapping, request);

        //�e�X�g���ʊm�F
        assertTrue(
                LogUTUtil.checkError("",new ClassCastException()));
    }

    /**
     * testReset06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) mapping:not null<br>
     *         (����) request:�T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g<br>
     *         (���) resetter:Resetter�I�u�W�F�N�g<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ResetterImpl#reset()�̌Ăяo��:�Ăяo�����<br>
     *                    request��"SUCCESS"���L�[�̒l���ݒ肳��邱�Ƃ��m�F�B
     *
     * <br>
     * �T�u���W���[�����i�[�������N�G�X�g�I�u�W�F�N�g��ݒ肵�A
     * Resetter���A�t�B�[���h�����������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReset06() throws Exception {

        // ���N�G�X�g�̐���
        MockHttpServletRequest request = new MockHttpServletRequest();
        // �Z�b�V�����̐���
        MockHttpSession session = new MockHttpSession();
        // �R���e�L�X�g�̐���
        MockServletContext context = new MockServletContext();
        // �A�N�V�����T�[�u���b�g�̐���
        ValidatorActionFormEx_ActionServlet_Stub01 servlet =
            new ValidatorActionFormEx_ActionServlet_Stub01();
        // �v���t�B�b�N�X�̐���
        String prefix = "sub1";
        // ���W���[���ݒ���̐���
        ModuleConfig moduleConfig = new ModuleConfigImpl(prefix);
        // �}�b�s���O���̐���
        ActionMapping mapping = new ActionMapping();
        // ���Z�b�^�[�̐���
        ResetterImpl resetter = new ValidatorActionFormEx_ResetterStub01();

        // �ݒ�
        request.setSession(session);
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        session.setServletContext(context);
        context.setAttribute(Resetter.RESETTER_KEY + prefix, resetter);
        context.setAttribute(Globals.MODULE_KEY, moduleConfig);
        this.formEx.setServlet(servlet);
        servlet.setContext(context);

        //�e�X�g���s
        this.formEx.reset(mapping, request);

        //�e�X�g���ʊm�F
        assertNotNull(request.getAttribute("SUCCESS"));
    }

}
