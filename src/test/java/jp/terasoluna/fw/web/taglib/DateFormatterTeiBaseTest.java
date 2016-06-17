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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.VariableInfo;

import junit.framework.TestCase;

/**
 * DateFormatterTeiBase �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class DateFormatterTeiBaseTest extends TestCase {

    // �e�X�g�Ώ�
    DateFormatterTeiBaseImpl01 tag = null;

    /**
     * Constructor for DateFormatterTeiBaseTest.
     * @param arg0
     */
    public DateFormatterTeiBaseTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = new DateFormatterTeiBaseImpl01();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGetVariableInfo01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FEF
     * <br><br>
     * ���͒l�F(����) data:new TagData({{"idon", "id"}})<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) variableInfo:��̔z��<br>
     *         (��ԕω�) variableInfo�z��:0<br>
     *         
     * <br>
     * ����TagData���ɕێ�����Ă���hid�h�̒l��NULL�ŁAVariableInfo�z��(�v�f��0��)���ԋp����邱�Əꍇ�̃e�X�g�P�[�X
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetVariableInfo01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        Object[][] att = {{"idon", "id"}};
        TagData data = new TagData(att);

        // �e�X�g���s
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        // �e�X�g���ʊm�F
        assertEquals(0, vInfo.length);

    } /* testGetVariableInfo01 End */

    /**
     * testGetVariableInfo02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FEF
     * <br><br>
     * ���͒l�F(����) data:new TagData({{"id", "id"}})<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) variableInfo:Not Null<br>
     *         (��ԕω�) variableInfo[0].varName:"id"<br>
     *         (��ԕω�) variableInfo[0].className:"java.lang.String"<br>
     *         (��ԕω�) variableInfo[0].declare:true<br>
     *         (��ԕω�) variableInfo[0].scope:VariableInfo.AT_BEGIN<br>
     *         (��ԕω�) variableInfo�z��:1<br>
     *         
     * <br>
     * ����TagData���ɕێ�����Ă���hid�h�̒l��NULL�ł͂Ȃ��AVariableInfo�z��(�v�f��1��)���ԋp����邱�Əꍇ�̃e�X�g�P�[�X
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetVariableInfo02() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        Object[][] att = {{"id", "id"}};
        TagData data = new TagData(att);

        // �e�X�g���s
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        // �e�X�g���ʊm�F
        assertEquals(1, vInfo.length);
        assertEquals("id", vInfo[0].getVarName());
        assertEquals("java.lang.String", vInfo[0].getClassName());
        assertTrue(vInfo[0].getDeclare());
        assertEquals(VariableInfo.AT_BEGIN, vInfo[0].getScope());

    } /* testGetVariableInfo02 End */

} /* DateFormatterTeiBaseTest */
