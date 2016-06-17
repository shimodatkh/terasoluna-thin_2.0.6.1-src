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

package jp.terasoluna.fw.web.taglib;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * LeftTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class LeftTagTest extends TestCase {

    // �e�X�g�Ώ�
    LeftTag tag = null;

    /**
     * Constructor for LeftTagTest.
     * @param arg0
     */
    public LeftTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (LeftTag) TagUTUtil.create(LeftTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoFormat01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * string="Java write once, run anywhere."<br>
     * length=15<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String="Java write once"<br>
     * <br>
     * ����string��Null�ł͂Ȃ��A����length���
     * ����string�̒����������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat01() throws Exception {

        // �e�X�g�ݒ�
        UTUtil.setPrivateField(tag, "length", 15);

        // �e�X�g���s
        String result = tag.doFormat("Java write once, run anywhere.");

        // �e�X�g���ʊm�F
        assertEquals("Java write once", result);

    } /* testDoFormat1 End */

    /**
     * testDoFormat02�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * string="Java write once, run anywhere."<br>
     * length=50<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String="Java write once, run anywhere."<br>
     * <br>
     * ����string��Null�ł͂Ȃ��A����length���
     * ����string�̒������Z���ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat02() throws Exception {

        // �e�X�g�ݒ�
        UTUtil.setPrivateField(tag, "length", 50);

        // �e�X�g���s
        String result = tag.doFormat("Java write once, run anywhere.");

        // �e�X�g���ʊm�F
        assertEquals("Java write once, run anywhere.", result);

    } /* testDoFormat2 End */

    /**
     * testDoFormat03�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * string=null<br>
     * length=10<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String=NullPointerException<br>
     * <br>
     * ����string��Null�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat03() throws Exception {

        // �e�X�g�ݒ�
        UTUtil.setPrivateField(tag, "length", 10);

        // �e�X�g���s
        try {
            tag.doFormat(null);
            fail();
        } catch (NullPointerException ex) {
            // �e�X�g���ʊm�F
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }
    } /* testDoFormat3 End */

    /**
     * testDoFormat04�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * string="Java write once, run anywhere."<br>
     * length=-<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String=�󔒕�����<br>
     * <br>
     * ����string��Null�ł͂Ȃ��Alength�̐ݒ�����Ȃ������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat04() {

        // �e�X�g���s
        String result = tag.doFormat("Java write once, run anywhere.");

        // �e�X�g���ʊm�F
        assertEquals("", result);

    } /* testDoFormat4 End */

    /**
     * testRelease01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * length=24<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * length=0<br>
     * 
     * �O������Ƃ��Đݒ肵���e�l���A
     * ���s���Ɋe�����l������������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "length", 24);

        // �e�X�g���s
        tag.release();

        // �e�X�g���ʊm�F
        assertEquals(new Integer(0), UTUtil.getPrivateField(tag, "length"));

    } /* testRelease1 End */

    /**
     * testSetLength01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * length="99"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * length="99"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetLength01() throws Exception {
        // �e�X�g���s
        tag.setLength(99);

        // �e�X�g���ʊm�F
        assertEquals(new Integer(99), UTUtil.getPrivateField(tag, "length"));

    } /* testSetLength End */

    /**
     * testGetLength01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�Ȃ�<br>
     * ���Ғl :length=30<br>
     *
     * �Z�b�g���Ă���l���擾�ł��邱�Ƃ��m�F����B<br>
     *
     */
    public void testGetLength01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "length", 30);

        // �e�X�g���s
        // �e�X�g���ʊm�F
        assertEquals(30, tag.getLength());
    }

} /* LeftTagTest Class End */
