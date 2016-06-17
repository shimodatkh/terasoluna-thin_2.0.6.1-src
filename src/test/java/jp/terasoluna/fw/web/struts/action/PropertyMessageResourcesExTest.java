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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.terasoluna.fw.web.struts.MessageFormatCloneReturnIfUseDateFormatMap;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �V�X�e���ƋƖ����ʂ̃��b�Z�[�W���\�[�X��\���\�ɂ���
 * �v���p�e�B�t�@�C�����b�Z�[�W���\�[�X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx
 */
public class PropertyMessageResourcesExTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(PropertyMessageResourcesExTest.class);
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
    public PropertyMessageResourcesExTest(String name) {
        super(name);
    }

    /**
     * testGetMessage01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:Locale.JAPAN<br>
     *         (����) key:"test.message"<br>
     *         (���) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (���) config:"PropertyMessageResourcesEx_MessageResources01"<br>
     *                �y�t�@�C�����e�z<br>
     *                test.message=�e�X�g���b�Z�[�W�O�P<br>
     *         (���) returnNull:false<br>
     *         (���) system-messages�̒�:��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C������̃��b�Z�[�W�������ԋp����邱�Ƃ�
     * �m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage01() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources01",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "test.message");

        // ����
        assertEquals(actual, "�e�X�g���b�Z�[�W�O�P");
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:Locale.JAPAN<br>
     *         (����) key:"test.message"<br>
     *         (���) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (���) config:"PropertyMessageResourcesEx_MessageResources02"<br>
     *                �i��̃t�@�C���j<br>
     *         (���) returnNull:false<br>
     *         (���) system-messages�̒�:test.message=�e�v�e�X�g���b�Z�[�W�O�P<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�v�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɗ^����ꂽ�L�[���o�^����Ă��Ȃ����A
     * GlobalMessageResources�ɓo�^����Ă���V�X�e���̃��b�Z�[�W�L�[��
     * ��v������̂��܂܂��ꍇ�A�V�X�e���̃��b�Z�[�W�������ԋp����邱�Ƃ�
     * �m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage02() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources02",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        Map<String, String> newFwMessages = new HashMap<String, String>(1);
        newFwMessages.put("test.message", "�e�v�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", newFwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "test.message");

        // ����
        assertEquals(actual, "�e�v�e�X�g���b�Z�[�W�O�P");
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:Locale.JAPAN<br>
     *         (����) key:"test.message"<br>
     *         (���) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (���) config:"PropertyMessageResourcesEx_MessageResources01"<br>
     *                �y�t�@�C�����e�z<br>
     *                test.message=�e�X�g���b�Z�[�W�O�P<br>
     *         (���) returnNull:false<br>
     *         (���) system-messages�̒�:test.message=�e�v�e�X�g���b�Z�[�W�O�P<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɂ�GlobalMessageResources�ɂ��^����ꂽ
     * �L�[�Ń��b�Z�[�W���\�[�X���o�^����Ă���ꍇ�ɂ́A���b�Z�[�W���\�[�X
     * ��`�t�@�C���ɒ�`���ꂽ���b�Z�[�W������ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage03() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources01",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        Map<String, String> newFwMessages = new HashMap<String, String>(1);
        newFwMessages.put("test.message", "�e�v�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", newFwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "test.message");

        // ����
        assertEquals(actual, "�e�X�g���b�Z�[�W�O�P");
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:Locale.JAPAN<br>
     *         (����) key:"aaaaa"<br>
     *         (���) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (���) config:"PropertyMessageResourcesEx_MessageResources02"<br>
     *                �i��̃t�@�C���j<br>
     *         (���) returnNull:false<br>
     *         (���) system-messages�̒�:��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"???ja_JP.aaaaa???"<br>
     *         
     * <br>
     * �v���p�e�B�A�V�X�e���o���̃��b�Z�[�W�������炸�AreturnNull�t�B�[���h��
     * false�ł���Ƃ��A???ja_JP.aaaaa???�̌`���ŕԋp�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage04() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources02",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "aaaaa");

        // ����
        assertEquals(actual, "???ja_JP.aaaaa???");
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:Locale.JAPAN<br>
     *         (����) key:"aaaaa"<br>
     *         (���) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (���) config:"PropertyMessageResourcesEx_MessageResources02"<br>
     *                �i��̃t�@�C���j<br>
     *         (���) returnNull:true<br>
     *         (���) system-messages�̒�:��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * �v���p�e�B�A�V�X�e���o���̃��b�Z�[�W�������炸�AreturnNull�t�B�[���h��
     * true�ł���Ƃ��Anull���ԋp�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage05() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources02",
                true);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "aaaaa");

        // ����
        assertNull(actual);
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testGetMessage06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:Locale.ENGLISH<br>
     *         (����) key:"test.message"<br>
     *         (���) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (���) config:"PropertyMessageResourcesEx_MessageResources03"<br>
     *                �y"PropertyMessageResourcesEx_MessageResources03_en"�̓��e�z<br>
     *                test.message=Test Message 01<br>
     *                �y"PropertyMessageResourcesEx_MessageResources03"�̓��e�z<br>
     *                test.message=�e�X�g���b�Z�[�W�O�P<br>
     *         (���) returnNull:false<br>
     *         (���) system-messages�̒�:��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"Test Message"<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���Ƀ��P�[�����l�������t�@�C�����������ꍇ�A
     * �������D�悵�ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage06() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory,
                PropertyMessageResourcesEx.class.getPackage().getName().replace('.', '/')
                + "/PropertyMessageResourcesEx_MessageResources03",
                false);
        Object fwMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "fwMessages");
        Object globalMessages 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(),
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "fwMessages", new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                                 "globalMessages", new HashMap());
        
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.ENGLISH, "test.message");

        // ����
        assertEquals(actual, "Test Message 01");
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "fwMessages", fwMessages);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(),
                "globalMessages", globalMessages);
    }

    /**
     * testPropertyMessageResourcesExMessageResourcesFactoryString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (����) config:"application-messages"�i���݂���v���p�e�B�t�@�C���j<br>
     *         (���) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyMessageResourcesEx:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"application-messages"<br>
     *                  formats->HashMap�C���X�^���X<br>
     *         
     * <br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�̂Ƃ��A
     * formats�͍����ւ����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyMessageResourcesExMessageResourcesFactoryString01() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        addProperty("messageResources.messageFormatClone", "disable");

        // �e�X�g���{
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory, "application-messages");

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("application-messages", UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }

    /**
     * testPropertyMessageResourcesExMessageResourcesFactoryString02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (����) config:"application-messages"�i���݂���v���p�e�B�t�@�C���j<br>
     *         (���) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyMessageResourcesEx:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"application-messages"<br>
     *                  formats->MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         
     * <br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�ȊO�̂Ƃ��A
     * formats�����̖߂�l�ɍ����ւ����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyMessageResourcesExMessageResourcesFactoryString02() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");

        // �e�X�g���{
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory, "application-messages");

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("application-messages", UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }

    /**
     * testPropertyMessageResourcesExMessageResourcesFactoryStringboolean01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (����) config:"application-messages"�i���݂���v���p�e�B�t�@�C���j<br>
     *         (����) returnNull:true<br>
     *         (���) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyMessageResourcesEx:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"application-messages"<br>
     *                  returnNull->true<br>
     *                  formats->HashMap�C���X�^���X<br>
     *         
     * <br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�̂Ƃ��A
     * formats�͍����ւ����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyMessageResourcesExMessageResourcesFactoryStringboolean01() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        addProperty("messageResources.messageFormatClone", "disable");

        // �e�X�g���{
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory, "application-messages", true);

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("application-messages", UTUtil.getPrivateField(resources, "config"));
        assertTrue((Boolean) UTUtil.getPrivateField(resources, "returnNull"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }

    /**
     * testPropertyMessageResourcesExMessageResourcesFactoryStringboolean02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) factory:PropertyMessageResourcesExFactory�C���X�^���X<br>
     *         (����) config:"application-messages"�i���݂���v���p�e�B�t�@�C���j<br>
     *         (����) returnNull:true<br>
     *         (���) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyMessageResourcesEx:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"application-messages"<br>
     *                  returnNull->true<br>
     *                  formats->MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         
     * <br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�ȊO�̂Ƃ��A
     * formats�����̖߂�l�ɍ����ւ����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyMessageResourcesExMessageResourcesFactoryStringboolean02() throws Exception {
        // �O����
        PropertyMessageResourcesExFactory factory
            = new PropertyMessageResourcesExFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");

        // �e�X�g���{
        PropertyMessageResourcesEx resources 
            = new PropertyMessageResourcesEx(factory, "application-messages", true);

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("application-messages", UTUtil.getPrivateField(resources, "config"));
        assertTrue((Boolean) UTUtil.getPrivateField(resources, "returnNull"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }

}
