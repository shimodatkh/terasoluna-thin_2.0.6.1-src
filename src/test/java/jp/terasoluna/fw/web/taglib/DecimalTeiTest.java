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
 * DecimalTei �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * -<br>
 */
public class DecimalTeiTest extends TestCase {

    // �e�X�g�ΏۃN���X����
    DecimalTei tag = null;

    /**
     * Constructor for IterateRowSetTeiTest.
     * @param arg0
     */
    public DecimalTeiTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = new DecimalTei();
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
     * �ϓ_�FFE
     * <br><br>
     * ���͒l�F(����) data:new TagData({"id", "1"})<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) VariableInfo[]:Not Null<br>
     *         (��ԕω�) variableInfo[0].varName:data��id����<br>
     *         (��ԕω�) variableInfo[0].className:"java.lang.String"<br>
     *         (��ԕω�) variableInfo[0].declare:true<br>
     *         (��ԕω�) variableInfo[0].scope:VariableInfo.AT_BEGIN<br>
     *         (��ԕω�) variableInfo�̔z��:1<br>
     *         
     * <br>
     * ����TagData���ɕێ�����Ă���id������Not NULL�ŁAVariableInfo�z���data��id����(VariableInfo�R���X�g���N�^�̑�������NotNull)���Z�b�g����ԋp����邱�Ƃ��m�F����e�X�g�P�[�X
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetVariableInfo01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        Object[][] att = {{"id", "1"}};
        TagData data = new TagData(att);

        //�e�X�g���s
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        //�e�X�g���ʊm�F
        assertEquals(1, vInfo.length);
        assertEquals("1", vInfo[0].getVarName());
        assertEquals("java.lang.String", vInfo[0].getClassName());
        assertTrue(vInfo[0].getDeclare());
        assertEquals(VariableInfo.AT_BEGIN, vInfo[0].getScope());

    }

    /**
     * testGetVariableInfo02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FFE
     * <br><br>
     * ���͒l�F(����) data:new TagData({})<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) VariableInfo[]:��̔z��<br>
     *         (��ԕω�) variableInfo�̔z��:0<br>
     *         
     * <br>
     * ����TagData���ɕێ�����Ă���id������NULL�ŁAVariableInfo�z��̃C���X�^���X���ԋp����邱�Ƃ��m�F����e�X�g�P�[�X
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetVariableInfo02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        Object[][] att = {};
        TagData data = new TagData(att);

        //�e�X�g���s
        VariableInfo[] vInfo = tag.getVariableInfo(data);

        //�e�X�g���ʊm�F
        assertEquals(0, vInfo.length);

    }

    /**
     * testGetVariableInfo03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) data:Null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NullPointerException<br>
     *         
     * <br>
     * ����TagData��NULL�ŁANullPointerException���������邱�Ƃ��m�F����e�X�g�P�[�X
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetVariableInfo03() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        TagData data = null;
        // �e�X�g���s
        try {
            tag.getVariableInfo(data);
            fail();
        } catch (NullPointerException e) {
            // �e�X�g���ʊm�F
            return;
        }
    }

}
