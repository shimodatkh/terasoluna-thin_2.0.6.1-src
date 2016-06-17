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

package jp.terasoluna.fw.web.struts.actions;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.AbstractDownloadObject} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �_�E�����[�h���e��ێ����钊�ۃN���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.AbstractDownloadObject
 */
public class AbstractDownloadObjectTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractDownloadObjectTest.class);
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
    public AbstractDownloadObjectTest(String name) {
        super(name);
    }

    /**
     * testAbstractDownloadObject01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *        (����) contentType:null<br>
     *        (����) charset:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:null<br>
     *        (��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *        (��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * contentType, charset��null�̏ꍇ�A�f�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractDownloadObject01() throws Exception {
        // �O����

        // �e�X�g���{
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // ����
        assertNull(object.name);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                object.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, object.charset);
    }

    /**
     * testAbstractDownloadObject02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"  "�i�󔒁j<br>
     *        (����) contentType:"  "�i�󔒁j<br>
     *        (����) charset:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"  "�i�󔒁j<br>
     *        (��ԕω�) contentType:"  "�i�󔒁j<br>
     *        (��ԕω�) charset:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractDownloadObject02() throws Exception {
        // �O����

        // �e�X�g���{
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01("  ",
                "  ", "  ");

        // ����
        assertEquals("  ", object.name);
        assertEquals("  ", object.contentType);
        assertEquals("  ", object.charset);
    }

    /**
     * testAbstractDownloadObject03()
     * <br><br>
     * 
     *(����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""�i�󕶎��j<br>
     *        (����) contentType:""�i�󕶎��j<br>
     *        (����) charset:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:""�i�󕶎��j<br>
     *        (��ԕω�) contentType:""�i�󕶎��j<br>
     *        (��ԕω�) charset:""�i�󕶎��j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractDownloadObject03() throws Exception {
        // �O����

        AbstractDownloadObject object = new AbstractDownloadObjectImpl01("",
                "", "");

        // ����
        assertEquals("", object.name);
        assertEquals("", object.contentType);
        assertEquals("", object.charset);
    }

    /**
     * testAbstractDownloadObject04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *        (����) contentType:"abc"<br>
     *        (����) charset:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"abc"<br>
     *        (��ԕω�) contentType:"abc"<br>
     *        (��ԕω�) charset:"abc"<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAbstractDownloadObject04() throws Exception {
        // �O����

        AbstractDownloadObject object = new AbstractDownloadObjectImpl01("abc",
                "abc", "abc");

        // ����
        assertEquals("abc", object.name);
        assertEquals("abc", object.contentType);
        assertEquals("abc", object.charset);
    }

    /**
     * testSetName01()
     * <br><br>
     * 
     *(����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:null<br>
     *         
     * <br>
     * name��null�̏ꍇ�Anull���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setName(null);

        // ����
        assertNull(object.name);
    }

    /**
     * testSetName02()
     * <br><br>
     * 
     *(����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName02() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setName("  ");

        // ����
        assertEquals("  ", object.name);
    }

    /**
     * testSetName03()
     * <br><br>
     * 
     *(����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:""�i�󕶎��j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName03() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setName("");

        // ����
        assertEquals("", object.name);
    }

    /**
     * testSetName04()
     * <br><br>
     * 
     *(����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) name:"abc"<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetName04() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setName("abc");

        // ����
        assertEquals("abc", object.name);
    }

    /**
     * testGetName01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) name:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * name��null�̏ꍇ�Anull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetName01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = null;

        // �e�X�g���{
        String str = object.getName();

        // ����
        assertNull(str);
    }

    /**
     * testGetName02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) name:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetName02() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = "  ";

        // �e�X�g���{
        String str = object.getName();

        // ����
        assertEquals("  ", str);
    }

    /**
     * testGetName03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) name:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""�i�󕶎��j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetName03() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = "";

        // �e�X�g���{
        String str = object.getName();

        // ����
        assertEquals("", str);
    }

    /**
     * testGetName04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) name:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetName04() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = "abc";

        // �e�X�g���{
        String str = object.getName();

        // ����
        assertEquals("abc", str);
    }

    /**
     * testSetContentType01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) contentType:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) contentType:DEFAULT_CONTENT_TYPE���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * contentType��null�̏ꍇ�A�f�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetContentType01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setContentType(null);

        // ����
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                object.contentType);
    }

    /**
     * testSetContentType02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) contentType:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) contentType:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetContentType02() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setContentType("  ");

        // ����
        assertEquals("  ", object.contentType);
    }

    /**
     * testSetContentType03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) contentType:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) contentType:""�i�󕶎��j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetContentType03() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setContentType("");

        // ����
        assertEquals("", object.contentType);
    }

    /**
     * testSetContentType04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) contentType:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) contentType:"abc"<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetContentType04() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setContentType("abc");

        // ����
        assertEquals("abc", object.contentType);
    }

    /**
     * testGetContentType01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) contentType:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) contentType:null<br>
     *         
     * <br>
     * �ϐ���null�̏ꍇ�Anull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetContentType01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = null;

        // �e�X�g���{
        String str = object.getContentType();

        // ����
        assertNull(str);
    }

    /**
     * testGetContentType02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) contentType:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetContentType02() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = "  ";

        // �e�X�g���{
        String str = object.getContentType();

        // ����
        assertEquals("  ", str);
    }

    /**
     * testGetContentType03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) contentType:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""�i�󕶎��j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetContentType03() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = "";

        // �e�X�g���{
        String str = object.getContentType();

        // ����
        assertEquals("", str);
    }

    /**
     * testGetContentType04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) contentType:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetContentType04() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = "abc";

        // �e�X�g���{
        String str = object.getContentType();

        // ����
        assertEquals("abc", str);
    }

    /**
     * testSetCharset01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) charset:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) charset:DEFAULT_CHARSET���ݒ肳��Ă��邱��<br>
     *         
     * <br>
     * charset��null�̏ꍇ�A�f�t�H���g�l���ݒ肳��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCharset01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setCharset(null);

        // ����
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, object.charset);
    }

    /**
     * testSetCharset02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) charset:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) charset:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCharset02() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setCharset("  ");

        // ����
        assertEquals("  ", object.charset);
    }

    /**
     * testSetCharset03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) charset:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) charset:""�i�󕶎��j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCharset03() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setCharset("");

        // ����
        assertEquals("", object.charset);
    }

    /**
     * testSetCharset04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) charset:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) charset:"abc"<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܐݒ肳��邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCharset04() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.setCharset("abc");

        // ����
        assertEquals("abc", object.charset);
    }

    /**
     * testGetCharset01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) charset:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * �ϐ���null�̏ꍇ�Anull���ԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCharset01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = null;

        // �e�X�g���{
        String str = object.getCharset();

        // ����
        assertNull(str);
    }

    /**
     * testGetCharset02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) charset:"  "�i�󔒁j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"  "�i�󔒁j<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCharset02() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = "  ";

        // �e�X�g���{
        String str = object.getCharset();

        // ����
        assertEquals("  ", str);
    }

    /**
     * testGetCharset03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) charset:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""�i�󕶎��j<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCharset03() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = "";

        // �e�X�g���{
        String str = object.getCharset();

        // ����
        assertEquals("", str);
    }

    /**
     * testGetCharset04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) charset:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A���̂܂ܕԋp����邱�Ƃ��m�F����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCharset04() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = "abc";

        // �e�X�g���{
        String str = object.getCharset();

        // ����
        assertEquals("abc", str);
    }

    /**
     * testAddHeader01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *        (����) value:null<br>
     *        (���) additionalHeaders:HashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) additionalHeaders:Map{[null, List[null]]}<br>
     *         
     * <br>
     * �ϐ���null�̏ꍇ�A�l���i�[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddHeader01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.addHeader(null, null);

        // ����
        assertNotNull(object.additionalHeaders);
        List<String> list = object.additionalHeaders.get(null);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNull(list.get(0));
    }

    /**
     * testAddHeader02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"  "�i�󔒁j<br>
     *        (����) value:"  "�i�󔒁j<br>
     *        (���) additionalHeaders:HashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) additionalHeaders:Map{["  "�i�󔒁j, List["  "�i�󔒁j]]}<br>
     *         
     * <br>
     * �ϐ����󔒂̏ꍇ�A�l���i�[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddHeader02() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.addHeader("  ", "  ");

        // ����
        assertNotNull(object.additionalHeaders);
        List list = object.additionalHeaders.get("  ");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("  ", list.get(0));
    }

    /**
     * testAddHeader03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:""�i�󕶎��j<br>
     *        (����) value:""�i�󕶎��j<br>
     *        (���) additionalHeaders:HashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) additionalHeaders:Map{[""�i�󕶎��j, List[""�i�󕶎��j]]}<br>
     *         
     * <br>
     * �ϐ����󕶎��̏ꍇ�A�l���i�[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddHeader03() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.addHeader("", "");

        // ����
        assertNotNull(object.additionalHeaders);
        List list = object.additionalHeaders.get("");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("", list.get(0));
    }

    /**
     * testAddHeader04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *        (����) value:"abc"<br>
     *        (���) additionalHeaders:HashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) additionalHeaders:Map{["abc", List["abc"]]}<br>
     *         
     * <br>
     * �ϐ���������̏ꍇ�A�l���i�[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddHeader04() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        object.addHeader("abc", "abc");

        // ����
        assertNotNull(object.additionalHeaders);
        List list = object.additionalHeaders.get("abc");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("abc", list.get(0));
    }

    /**
     * testAddHeader05()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *        (����) value:"def"<br>
     *        (���) additionalHeaders:Map{["abc", List["abc"]]}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) additionalHeaders:Map{["abc", List["abc", "def"]]}<br>
     *         
     * <br>
     * ���ɃL�[�����݂���ꍇ�ŁA�����L�[�ϐ���������̏ꍇ�A�l���i�[����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddHeader05() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list1 = new ArrayList<String>();
        list1.add("abc");
        map.put("abc", list1);
        object.additionalHeaders = map;

        // �e�X�g���{
        object.addHeader("abc", "def");

        // ����
        assertNotNull(object.additionalHeaders);
        List list2 = object.additionalHeaders.get("abc");
        assertNotNull(list2);
        assertEquals(2, list2.size());
        assertEquals("abc", list2.get(0));
        assertEquals("def", list2.get(1));
    }

    /**
     * testGetAdditionalHeaders01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) additionalHeaders:HashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) additionalHeaders:Map<br>
     *         
     * <br>
     * �ϐ����擾�ł��邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetAdditionalHeaders01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        object.additionalHeaders = hashMap;

        // �e�X�g���{

        Map map = object.getAdditionalHeaders();

        // ����
        assertSame(hashMap, map);
    }

    /**
     * testGetStream01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) getStreamInternal�̖߂�l:ByteArrayInputStream<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) InputStream:BufferedInputStream(ByteArrayInputStream)<br>
     *         
     * <br>
     * getStreamInternal�̖߂�l��BufferedInputStream�Ƀ��b�v����ĕԋp����邱�Ƃ��m�F����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetStream01() throws Exception {
        // �O����
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // �e�X�g���{
        InputStream stream = object.getStream();

        // ����
        assertEquals(BufferedInputStream.class, stream.getClass());
    }

}
