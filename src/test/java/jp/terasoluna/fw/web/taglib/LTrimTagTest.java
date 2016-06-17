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
import junit.framework.TestCase;

/**
 * LTrimTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class LTrimTagTest extends TestCase {

    // �e�X�g�Ώ�
    LTrimTag tag = null;

    /**
     * Constructor for LTrimTagTest.
     * @param arg0
     */
    public LTrimTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (LTrimTag) TagUTUtil.create(LTrimTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoFormat01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) str:" test test test "<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"test test test "<br>
     *         
     * <br>
     * ����str��Null�ł͂Ȃ��ꍇ�̃e�X�g�P�[�X
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFormat01() throws Exception {

        // �e�X�g�ݒ�
        String str = " test test test ";

        // �e�X�g���s
        String result = tag.doFormat(str);

        // �e�X�g���ʊm�F
        assertEquals("test test test ",result);

    } /* testDoFormat1 End */

    /**
     * testDoFormat02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) str:Null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:Null<br>
     *         
     * <br>
     * ����str��Null�̏ꍇ�̃e�X�g�P�[�X
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFormat02() throws Exception {

        // �e�X�g�ݒ�
        String str = null;

        // �e�X�g���s
        String result = tag.doFormat(str);

        // �e�X�g���ʊm�F
        assertNull(result);

    } /* testDoFormat2 End */

    /**
     * testDoFormat03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) str:" �@test test�@test �@"<br>
     *         (���) �Ȃ�:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"test test�@test �@"<br>
     *         
     * <br>
     * ����str��Null�ł͂Ȃ��Azenkaku������true�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFormat03() throws Exception {

        // �e�X�g�ݒ�
        String str = " �@test test�@test �@";
        tag.setZenkaku(true);

        // �e�X�g���s
        String result = tag.doFormat(str);

        // �e�X�g���ʊm�F
        assertEquals("test test�@test �@", result);

    }

    /**
     * testGetZenkaku01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) zenkaku:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * �Z�b�g���Ă���l���擾�ł��邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetZenkaku01() throws Exception {

        // �e�X�g�ݒ�
        tag.zenkaku = false;

        //�e�X�g���s
        boolean result = tag.getZenkaku();

        //�e�X�g���ʊm�F
        assertFalse(result);

    }

    /**
     * testSetZenkaku01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) zenkaku:true<br>
     *         (���) zenkaku:false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) zenkaku:true<br>
     *         
     * <br>
     * ������zenkaku���������i�[����邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetZenkaku01() throws Exception {

        // �e�X�g�ݒ�
        tag.zenkaku = false;

        //�e�X�g���s
        tag.setZenkaku(true);

        //�e�X�g���ʊm�F
        assertTrue(tag.zenkaku);

    }

} /* LTrimTagTest Class End */
