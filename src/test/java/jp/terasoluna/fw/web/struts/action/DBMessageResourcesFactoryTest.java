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

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

import org.apache.struts.util.MessageResources;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * DBMessageResources�𐶐�����t�@�N�g���N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 */
public class DBMessageResourcesFactoryTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageResourcesFactoryTest.class);
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
    public DBMessageResourcesFactoryTest(String name) {
        super(name);
    }

    /**
     * testCreateResources01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) config:"DBMessageResourcesFactory_MessageResources01"<br>
     *                �i��̃t�@�C���j<br>
     *         (���) system.properties���̊֘A����v���p�e�B:messages.dao="aaaaa"<br>
     *                messages.sql=SELECT KEY, VALUE FROM MESSAGES<br>
     *         (���) DBMessageResources��dbMessages:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.db.message.resources.init"<br>
     *                    ���b�v������O�FClassLoadException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    ""aaaaa" cannot loaded."<br>
     *                    ����O��<br>
     *                    ClassLoadException<br>
     *         
     * <br>
     * �V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j�ɁA���݂��Ȃ�dao��messages.dao�Ƃ����L�[�Œ�`���Ă���ꍇ�A�G���[���O���o�͂��A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources01() throws Exception {
        // �O����
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        addProperty("messages.dao", "aaaaa");
        addProperty("messages.sql", "SELECT KEY, VALUE FROM MESSAGES");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // �e�X�g���{
        try {
            factory.createResources(
                    "DBMessageResourcesFactory_MessageResources01");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.db.message.resources.init", e.getErrorCode());
            assertEquals(ClassLoadException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("\"aaaaa\" cannot loaded.",
                                            e.getCause()));
            
        }
    }

    /**
     * testCreateResources02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:"DBMessageResourcesFactory_MessageResources01"<br>
     *                �i��̃t�@�C���j<br>
     *         (���) system.properties���̊֘A����v���p�e�B:messages.dao���L�[�Ƃ���v���p�e�B�����݂��Ȃ�<br>
     *                messages.sql���L�[�Ƃ���v���p�e�B�����݂��Ȃ�<br>
     *         (���) DBMessageResources��dbMessages:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory: �߂�l�𐶐�����DBMessageResourcesFactory�C���X�^���X���g<br>
     *                  config: "DBMessageResourcesFactory_MessageResources01"<br>
     *                  returnNull: super.returnNull<br>
     *         
     * <br>
     * DBMessageResources����������Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources02() throws Exception {
        // �O����
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        deleteProperty("messages.dao");
        deleteProperty("messages.sql");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // �e�X�g���{
        MessageResources resources 
            = factory.createResources(
                "DBMessageResourcesFactory_MessageResources01");
       
        // ����
        assertEquals(DBMessageResources.class.getName(),
                     resources.getClass().getName());
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertEquals("DBMessageResourcesFactory_MessageResources01",
                     UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(factory, "returnNull"),
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
     * ���͒l�F(����) config:""<br>
     *         (���) DBMessageResources��dbMessages:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory: �߂�l�𐶐�����DBMessageResourcesFactory�C���X�^���X���g<br>
     *                  config: ""<br>
     *                  returnNull: super.returnNull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "" is illegal."<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɋ󕶎�����w�肵���ꍇ�A�G���[���O���o�͂��ADBMessageResources����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources03() throws Exception {
        // �O����
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        deleteProperty("messages.dao");
        deleteProperty("messages.sql");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // �e�X�g���{
        MessageResources resources 
            = factory.createResources(
                "");
       
        // ����
        assertTrue(resources instanceof DBMessageResources);
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertEquals("",
                     UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(factory, "returnNull"),
                     UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue((LogUTUtil.checkError("Message resources file \"\" is illegal.")));
    }

    /**
     * testCreateResources04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:null<br>
     *         (���) DBMessageResources��dbMessages:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory: �߂�l�𐶐�����DBMessageResourcesFactory�C���X�^���X���g<br>
     *                  config: null<br>
     *                  returnNull: super.returnNull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "null" is illegal."<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C����null���w�肵���ꍇ�A�G���[���O���o�͂��ADBMessageResources����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateResources04() throws Exception {
        // �O����
        DBMessageResourcesFactory factory 
            = new DBMessageResourcesFactory();
        deleteProperty("messages.dao");
        deleteProperty("messages.sql");
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
        
        // �e�X�g���{
        MessageResources resources 
            = factory.createResources(
                null);
       
        // ����
        assertTrue(resources instanceof DBMessageResources);
        assertSame(factory,
                   UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(UTUtil.getPrivateField(factory, "returnNull"),
                     UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue(LogUTUtil.checkError("Message resources file \"null\" is illegal."));
    }

}
