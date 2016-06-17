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

package jp.terasoluna.fw.web.struts.action;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * ActionMappingEx �u���b�N�{�b�N�X�e�X�g�B<br>
 * <br>
 * (�O�����)<br>
 *  �@�Ȃ�
 * <br>
 */
public class ActionMappingExTest extends TestCase {

    /**
     * Constructor for ActionMappingExTest.
     * @param arg0
     */
    public ActionMappingExTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testSetClearForm01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�A�N�V�����t�H�[���N���A�t���O(true)<br>
     * ���Ғl :�Z�b�g����邱��<br>
     *
     * �A�N�V�����t�H�[���N���A�t���O���ݒ肳��邱�Ƃ��m�F����B<br>
     *
     */
    public void testSetClearForm01() throws Exception {
        //�����ݒ�
        ActionMappingEx ame = new ActionMappingEx();
        //�e�X�g���s
        ame.setClearForm(true);
        //���ʊm�F
        Boolean result = (Boolean) UTUtil.getPrivateField(ame, "clearForm");
        assertTrue(result.booleanValue());
    }

    /**
     * testGetClearForm01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�����Ȃ�<br>
     * ���Ғl :�A�N�V�����t�H�[���N���A�t���O<br>
     *
     * �A�N�V�����t�H�[���N���A�t���O���擾�ł��邱�Ƃ��m�F����B<br>
     *
     */
    public void testGetClearForm01() throws Exception {
        //�����ݒ�
        ActionMappingEx ame = new ActionMappingEx();
        Boolean clearForm = new Boolean(true);
        UTUtil.setPrivateField(ame, "clearForm", clearForm);

        //�e�X�g���s
        //���ʊm�F
        assertTrue(ame.getClearForm());
    }

    /**
     * testSetCancelPopulate01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�|�s�����[�V�����L�����Z���t���O(true)<br>
     * ���Ғl :�Z�b�g����邱��<br>
     *
     * �|�s�����[�V�����L�����Z���t���O���ݒ肳��邱�Ƃ��m�F����B<br>
     *
     */
    public void testSetCancelPopulate01() throws Exception {
        //�����ݒ�
        ActionMappingEx ame = new ActionMappingEx();
        //�e�X�g���s
        ame.setCancelPopulate(true);
        //���ʊm�F
        Boolean result = (Boolean) UTUtil.getPrivateField(ame, "cancelPopulate");
        assertTrue(result.booleanValue());

    }

    /**
     * testGetCancelPopulate01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�����Ȃ�<br>
     * ���Ғl :�|�s�����[�V�����L�����Z���t���O<br>
     *
     * �|�s�����[�V�����L�����Z���t���O���^�Ŏ擾����邱�Ƃ��m�F����B<br>
     *
     */
    public void testGetCancelPopulate01() throws Exception {
        //�����ݒ�
        ActionMappingEx ame = new ActionMappingEx();
        Boolean cancelPopulate = new Boolean(true);
        UTUtil.setPrivateField(ame, "cancelPopulate", cancelPopulate);
        //�e�X�g���s
        //���ʊm�F
        assertTrue(ame.getCancelPopulate());
    }

}
