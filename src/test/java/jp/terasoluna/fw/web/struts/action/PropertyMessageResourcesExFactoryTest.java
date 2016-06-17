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

import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

import org.apache.struts.util.MessageResources;

/**
 * {@link jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesExFactory} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * PropertyMessageResourcesEx�𐶐�����t�@�N�g���N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesFactoryEx
 */
public class PropertyMessageResourcesExFactoryTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(PropertyMessageResourcesExFactoryTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public PropertyMessageResourcesExFactoryTest(String name) {
        super(name);
    }

    /**
     * testCreateResources01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:"aaaaa"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyMessageResourcesEx:factory->�߂�l�𐶐�����PropertyMessageResourcesExFactory�C���X�^���X���g<br>
     *                  config->"aaaaa"<br>
     *                  returnNull->MessageResourcesFactory.getReturnNull()<br>
     *         
     * <br>
     * PropertyMessageResourcesEx����������Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources01() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory 
            = new PropertyMessageResourcesExFactory();
        
        // �e�X�g���{
        MessageResources resources 
            = factory.createResources("aaaaa");
       
        // ����
        assertEquals(resources.getClass().getName(),
                     PropertyMessageResourcesEx.class.getName());
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertEquals("aaaaa",
                     UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                     UTUtil.getPrivateField(resources, "returnNull"));

    }

    /**
     * testCreateResources02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyMessageResourcesEx:factory->�߂�l�𐶐�����PropertyMessageResourcesExFactory�C���X�^���X���g<br>
     *                  config->""<br>
     *                  returnNull->MessageResourcesFactory.getReturnNull()<br>
     *         
     * <br>
     * PropertyMessageResourcesEx����������Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources02() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory 
            = new PropertyMessageResourcesExFactory();
        
        // �e�X�g���{
        MessageResources resources 
            = factory.createResources("");
       
        // ����
        assertEquals(resources.getClass().getName(),
                     PropertyMessageResourcesEx.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                     UTUtil.getPrivateField(resources, "returnNull"));   
    }

    /**
     * testCreateResources03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyMessageResourcesEx:factory->�߂�l�𐶐�����PropertyMessageResourcesExFactory�C���X�^���X���g<br>
     *                  config->null<br>
     *                  returnNull->MessageResourcesFactory.getReturnNull()<br>
     *         
     * <br>
     * PropertyMessageResourcesEx����������Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources03() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory 
            = new PropertyMessageResourcesExFactory();
        
        // �e�X�g���{
        MessageResources resources 
            = factory.createResources(null);
       
        // ����
        assertEquals(resources.getClass().getName(),
                     PropertyMessageResourcesEx.class.getName());
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                     UTUtil.getPrivateField(resources, "returnNull"));  
    }

}
