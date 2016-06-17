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

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.service.thin.BLogic;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.BLogicAction} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �t���[�����[�N�����Ńr�W�l�X���W�b�N�����s����W���A�N�V�����N���X
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 */
@SuppressWarnings("unchecked")
public class BLogicActionTest extends TestCase {
    
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
    public BLogicActionTest(String name) {
        super(name);
    }

    /**
     * testSetBusinessLogic01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) businessLogic:Blogic�I�u�W�F�N�g<br>
     *         (���) businessLogic:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) businessLogic:�����Ɠ����Blogic�I�u�W�F�N�g<br>
     *         
     * <br>
     * �����Ɏw�肵���l��businessLogic�ɐ���Ɋi�[����邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBusinessLogic01() throws Exception {
        // �O����
        BLogicAction action = new BLogicAction();
        UTUtil.setPrivateField(action, "businessLogic", null);
        BLogicImpl01 blogic = new BLogicImpl01();

        // �e�X�g���{
        action.setBusinessLogic(blogic);

        // ����
        assertSame(blogic, UTUtil.getPrivateField(action, "businessLogic"));
    }

    /**
     * testGetBusinessLogic01()�B<br>
     * 
     * �i����n�j<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l :�����Ȃ�<br>
     *         (���) businessLogic:blogic<br>
     * ���Ғl :businessLogic:blogic<br>
     *
     * businessLogic�̒l���擾�ł��邱�Ƃ��m�F����B<br>
     *
     */
    public void testGetBusinessLogic01() throws Exception {
        // �O����
        BLogicAction action = new BLogicAction();
        BLogicImpl01 blogic = new BLogicImpl01();        
        UTUtil.setPrivateField(action, "businessLogic", blogic);

        // �e�X�g���{
        BLogic businessLogic = action.getBusinessLogic();

        // ����
        assertSame(blogic, businessLogic);
    }

    /**
     * testDoExecuteBLogic01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) param:null<br>
     *         (���) businessLogic:not null<br>
     *         (���) businessLogic.execute():"businessLogic"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicResult:"businessLogic"<br>
     *         
     * <br>
     * param��null�ł���AbusinessLogic��not null�̏ꍇ�A
     * businessLogic.execute���\�b�h�̎��s���ʂ�Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecuteBLogic01() throws Exception {
        // �O����
        BLogicAction action = new BLogicAction();
        // execute() : "businessLogic"
        BLogicImpl02 blogic = new BLogicImpl02();
        UTUtil.setPrivateField(action, "businessLogic", blogic);
        Map param = null;

        // �e�X�g���{
        BLogicResult result = action.doExecuteBLogic(param);

        // ����
        assertEquals("businessLogic", result.getResultString());
    }

    /**
     * testDoExecuteBLogic02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) param:not null<br>
     *         (���) businessLogic:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicResult:null<br>
     *         
     * <br>
     * �t�B�[���hbusinessLogic��null�������ꍇ�Anull��Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecuteBLogic02() throws Exception {
        // �O����
        BLogicAction action = new BLogicAction();
        
        // businessLogic : null
        UTUtil.setPrivateField(action, "businessLogic", null);
        Map param = new HashMap();

        // �e�X�g���{
        BLogicResult result = action.doExecuteBLogic(param);

        // ����
        assertNull(result);
    }

    /**
     * testDoExecuteBLogic03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) param:not null<br>
     *         (���) businessLogic:not null<br>
     *         (���) businessLogic.execute():null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicResult:null<br>
     *         
     * <br>
     * businessLogic.execute()�̌��ʂ�null�������ꍇ�A
     * �߂�l��null�ł��邱�Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecuteBLogic03() throws Exception {
        // �O����
        BLogicAction action = new BLogicAction();
        // execute() : null
        BLogicImpl01 blogic = new BLogicImpl01();
        UTUtil.setPrivateField(action, "businessLogic", blogic);
        Map param = new HashMap();

        // �e�X�g���{
        BLogicResult result = action.doExecuteBLogic(param);

        // ����
        assertNull(result);
    }

    /**
     * testDoExecuteBLogic04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) param:not null<br>
     *         (���) businessLogic:not null<br>
     *         (���) businessLogic.execute():"businessLogic"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicResult:"businessLogic"<br>
     *         
     * <br>
     * param��not null�ł���AbusinessLogic��not null�̏ꍇ�A
     * businessLogic.execute���\�b�h�̎��s���ʂ�Ԃ����Ƃ��m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoExecuteBLogic04() throws Exception {
        // �O����
        BLogicAction action = new BLogicAction();
        // execute() : "businessLogic"
        BLogicImpl02 blogic = new BLogicImpl02();
        UTUtil.setPrivateField(action, "businessLogic", blogic);
        Map param = new HashMap();

        // �e�X�g���{
        BLogicResult result = action.doExecuteBLogic(param);

        // ����
        assertEquals("businessLogic", result.getResultString());
    }
}
