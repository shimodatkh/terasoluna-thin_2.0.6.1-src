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

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.web.struts.MessageFormatCloneReturnIfUseDateFormatMap;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageResources} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���b�Z�[�W���\�[�X�N���X�B<br>
 * ���̃N���X���g�p���邱�Ƃɂ���āA���b�Z�[�W���\�[�X��`�t�@�C��
 * �i�ʏ�Struts�Ŏg����v���p�e�B�t�@�C���`���̃��b�Z�[�W���\�[�X�j
 * �����łȂ��A�N���X���[�h����DB���Q�Ƃ��ADB����̃��b�Z�[�W���\�[�X��
 * �g�p���邱�Ƃ��\�ł���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 */
public class DBMessageResourcesTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageResourcesTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        UTUtil.setPrivateField(DBMessageResources.class, "dbMessages", null);
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DBMessageResourcesTest(String name) {
        super(name);
    }

    /**
     * testDbInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) messages.sql:"messages.sql"�����݂��Ȃ�<br>
     *         (���) messages.dao:"messages.dao"�����݂��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) dbMessages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɃL�["messages.sql"��"messages.dao"��
     * ��`����Ȃ��ꍇ�AdbMessages�ɋ��Map���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDbInit01() throws Exception {
        // �O����
        deleteProperty("messages.sql");
        deleteProperty("messages.dao");
        
        // �e�X�g���{
        DBMessageResources.dbInit();

        // ����
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertTrue(map.isEmpty());
    }

    /**
     * testDbInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) messages.sql:"messages.sql"�����݂��Ȃ�<br>
     *         (���) messages.dao:"jp.terasoluna.fw.web.action.MessageResources_MessageResourcesDAOStub01"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�y�x�����O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "defined only one of the pair - messages.dao and messages.sql."<br>
     *         (��ԕω�) dbMessages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɃL�["messages.sql"�����݂��Ȃ��ꍇ�A
     * �x�����O���o���AdbMessages�ɋ��Map���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDbInit02() throws Exception {
        // �O����
        deleteProperty("messages.sql");
        addProperty("messages.dao", 
                    DBMessageResources_MessageResourcesDAOStub01.class.getName());        
        // �e�X�g���{
        DBMessageResources.dbInit();

        // ����
        assertTrue(LogUTUtil.checkWarn(
            "defined only one of the pair - messages.dao and messages.sql."));
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertTrue(map.isEmpty());
    }

    /**
     * testDbInit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) messages.dao:"messages.dao"�����݂��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�y�x�����O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "defined only one of the pair - messages.dao and messages.sql."<br>
     *         (��ԕω�) dbMessages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɃL�["messages.dao"�����݂��Ȃ��ꍇ�A
     * �x�����O���o���AdbMessages�ɋ��Map���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDbInit03() throws Exception {
        // �O����
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        deleteProperty("messages.dao");
        
        // �e�X�g���{
        DBMessageResources.dbInit();

        // ����
        assertTrue(LogUTUtil.checkWarn(
            "defined only one of the pair - messages.dao and messages.sql."));
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertTrue(map.isEmpty());
    }

    /**
     * testDbInit04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) messages.dao:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.db.message.resources.init"<br>
     *                    ���b�v������O�FClassLoadException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "" cannot loaded."<br>
     *                    ����O��<br>
     *                    ClassLoadException<br>
     *         (��ԕω�) dbMessages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɃL�["messages.dao"�œo�^����Ă���l��
     * ��̏ꍇ�Adbmessages�ɂ͋��Map���ݒ肳��A�G���[���O���o�͂��A
     * ��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDbInit04() throws Exception {
        // �O����
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao", "");
        
        // �e�X�g���{
        try {
            DBMessageResources.dbInit();
            fail();
        } catch (SystemException e) {
            // ����
            assertTrue(e.getCause() instanceof ClassLoadException);
            assertEquals("errors.db.message.resources.init",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError("\"\" cannot loaded.", e.getCause()));
            Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                                   "dbMessages");
            assertTrue(map.isEmpty());
        }
        
    }

    /**
     * testDbInit05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) messages.dao:"aaaaa"<br>
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
     *         (��ԕω�) dbMessages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɃL�["messages.dao"�œo�^����Ă���l��
     * ���݂��Ȃ��N���X�̏ꍇ�Adbmessages�ɂ͋��Map���ݒ肳��A�G���[���O��
     * �o�͂��A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDbInit05() throws Exception {
        // �O����
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao",
                    "aaaaa");
        
        // �e�X�g���{
        try {
            DBMessageResources.dbInit();
            fail();
        } catch (SystemException e) {
            // ����
            assertTrue(e.getCause() instanceof ClassLoadException);
            assertEquals("errors.db.message.resources.init",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError("\"aaaaa\" cannot loaded.", e.getCause()));
            Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                                   "dbMessages");
            assertTrue(map.isEmpty());
        }
        
    }

    /**
     * testDbInit06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) messages.dao:"java.lang.Object"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.db.message.resources.init"<br>
     *                    ���b�v������O�FClassCastException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    ""java.lang.Object" not implemented MessageResourcesDAO"<br>
     *                    ����O��<br>
     *                    ClassCastException<br>
     *         (��ԕω�) dbMessages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���ɃL�["messages.dao"�œo�^����Ă���
     * �N���X��MessageResourcesDAO���������Ă��Ȃ��ꍇ�Adbmessages�ɂ�
     * ���Map���ݒ肳��A�G���[���O���o�͂��A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDbInit06() throws Exception {
        // �O����
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao", "java.lang.Object");
        UTUtil.setPrivateField(DBMessageResources.class, 
                               "dbMessages",
                               new HashMap<String, String>());
        
        // �e�X�g���{
        try {
            DBMessageResources.dbInit();
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals(ClassCastException.class.getName(),
                         e.getCause().getClass().getName());
            assertEquals("errors.db.message.resources.init",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "\"java.lang.Object\" not implemented MessageResourcesDAO", 
                e.getCause()));
            Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                                   "dbMessages");
            assertTrue(map.isEmpty());
        }
    }

    /**
     * testDbInit07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) messages.sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) messages.dao:"jp.terasoluna.fw.web.struts.action.DBMessageResources_MessageResourcesDAOStub01"<br>
     *         (���) queryMessageMap:["TEST01"->"�e�X�g�߂����[���O�P"]<br>
     *                ["TEST01"->"testMESSAGE02"]<br>
     *                ["TEST01"->"�����I����O"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) dbMessages:["TEST01"->"�e�X�g�߂����[���O�P"]<br>
     *                    ["TEST02"->"testMESSAGE02"]<br>
     *                    ["TEST03"->"�����I����O"]<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C����"messages.dao"��"messages.sql"��
     * ��������`����Ă���ꍇ�ADB����擾�������b�Z�[�W�L�[�ƃ��b�Z�[�W������
     * �������ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDbInit07() throws Exception {
        // �O����
        addProperty("messages.sql",
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
        addProperty("messages.dao", 
                    DBMessageResources_MessageResourcesDAOStub01.class.getName());

        // �e�X�g���{
        DBMessageResources.dbInit();
        
        // ����
        Map map = (Map) UTUtil.getPrivateField(DBMessageResources.class,
                                               "dbMessages");
        assertEquals(3, map.size());
        assertEquals("�e�X�g�߂����[���O�P", map.get("TEST01"));
        assertEquals("testMESSAGE02", map.get("TEST02"));
        assertEquals("�����I����O", map.get("TEST03"));
    }

    /**
     * testPropertyInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyFile:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "null" is illegal."<br>
     *         (��ԕω�) messages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C������null�̂Ƃ��A���b�Z�[�W���擾������
     * �G���[���O���o�͂��ďI�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyInit01() throws Exception {
        // �O�����A�e�X�g���{
        DBMessageResources resources 
            = new DBMessageResources(null, null);

        // ����
        assertTrue(LogUTUtil.checkError(
                "Message resources file \"null\" is illegal."));
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyFile:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "" is illegal."<br>
     *         (��ԕω�) messages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C�������󕶎���̂Ƃ��A���b�Z�[�W��
     * �擾�����ɃG���[���O���o�͂��ďI�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyInit02() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, "");
        
        // ����
        assertTrue(LogUTUtil.checkError("Message resources file \"\" is illegal."));
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyFile:"aaaaa"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�y�G���[���O�z<br>
     *                    "Message resources file "aaaaa" is illegal."<br>
     *         (��ԕω�) messages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C�������݂��Ȃ��Ƃ��A���b�Z�[�W���擾������
     * �G���[���O���o�͂��ďI�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyInit03() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, "aaaaa");
        
        // ����
        assertTrue(LogUTUtil.checkError(
                "Message resources file \"aaaaa\" is illegal."));
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyFile:"DBMessageResources_MessageResources01"<br>
     *                �i��̃t�@�C���j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) messages:���Map<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C���̒��g����̏ꍇ�A���b�Z�[�W���擾������
     * �I�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyInit04() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources01");
        
        // ����
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(map.isEmpty());
    }

    /**
     * testPropertyInit05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyFile:"DBMessageResources_MessageResources02"<br>
     *                �y�t�@�C���̓��e�z<br>
     *                message.error.required=<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) messages:["message.error.required"->""]<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C������擾�����L�[�ɑ΂���l���󕶎����
     * �ꍇ�A���b�Z�[�W���󕶎���Őݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyInit05() throws Exception {
        // �O����
        DBMessageResources resources 
        = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources02");
        // ����
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, map.size());
        assertEquals("", map.get("message.error.required"));
    }

    /**
     * testPropertyInit06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyFile:"DBMessageResources_MessageResources03"<br>
     *                �y�t�@�C���̓��e�z<br>
     *                message.error.required={0}����͂��Ă��������B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) messages:["message.error.required"->"{0}����͂��Ă��������B"]<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C������擾�������ʂ�1���̏ꍇ�A
     * �������ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyInit06() throws Exception {
        // �O����
        DBMessageResources resources 
        = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources03");
        
        // ����
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, map.size());
        assertEquals("{0}����͂��Ă��������B", map.get("message.error.required"));
    }

    /**
     * testPropertyInit07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyFile:"DBMessageResources_MessageResources04"<br>
     *                �y�t�@�C���̓��e�z<br>
     *                error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *                message.error.required={0}����͂��Ă��������B<br>
     *                error.prohibited={0}�ɓ��͋֎~����"{1}"���܂܂�Ă��܂��B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) messages:["error.date.format"->"{0}�̓�����{1}�`���œ��͂��Ă��������B"]<br>
     *                    ["message.error.required"->"{0}����͂��Ă��������B"]<br>
     *                    ["error.prohibited"->"{0}�ɓ��͋֎~����"{1}"���܂܂�Ă��܂��B"]<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��`�t�@�C������擾�������ʂ��������̏ꍇ�A
     * �������ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testPropertyInit07() throws Exception {
        // �O����
        DBMessageResources resources 
        = new DBMessageResources(
                null, this.getClass().getPackage().getName().replace('.', '/')
                + "/DBMessageResources_MessageResources04");
        
        // ����
        Map map = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(3, map.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B", 
                     map.get("error.date.format"));
        assertEquals("{0}����͂��Ă��������B", 
                     map.get("message.error.required"));
        assertEquals("{0}�ɓ��͋֎~����\"{1}\"���܂܂�Ă��܂��B", 
                     map.get("error.prohibited"));
    }

    /**
     * testGetMessageLocaleString01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:""<br>
     *         (���) messages:[""->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�P"<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                         �G���[�R�[�h�F"errors.db.message.resources"<br>
     *                         ���b�v������O�Fnull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                         �����b�Z�[�W��<br>
     *                         "Message key 'null' or empty not allowed."<br>
     *         
     * <br>
     * key���󕶎���̏ꍇ�A�G���[���O���o�͂��A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString01() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        try {
            resources.getMessage(Locale.JAPAN, "");
            fail();
        } catch (SystemException e) {
            // ����
            assertNull(e.getCause());
            assertEquals("errors.db.message.resources",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "Message key 'null' or empty not allowed."));
        }
        
    }

    /**
     * testGetMessageLocaleString02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:""<br>
     *         (���) messages:���Map<br>
     *         (���) dbMessages:[""->"�c�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                         �G���[�R�[�h�F"errors.db.message.resources"<br>
     *                         ���b�v������O�Fnull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                         �����b�Z�[�W��<br>
     *                         "Message key 'null' or empty not allowed."<br>
     *         
     * <br>
     * key���󕶎���̏ꍇ�A�G���[���O���o�͂��A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString02() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        HashMap<String, String> map = new HashMap<String, String>(1);
        map.put("", "�c�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "dbMessages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        try {
            resources.getMessage(Locale.JAPAN, "");
            fail();
        } catch (SystemException e) {
            // ����
            assertNull(e.getCause());
            assertEquals("errors.db.message.resources",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "Message key 'null' or empty not allowed."));
        }
    }

    /**
     * testGetMessageLocaleString03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:["TEST01"->""]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * messages�ɓo�^����Ă���L�[�ƃ��b�Z�[�W�L�[����v�������b�Z�[�W������
     * �󕶎���̏ꍇ�A�󕶎��񂪕ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString03() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("TEST01", "");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertEquals("", actual);
    }

    /**
     * testGetMessageLocaleString04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * messages�ɂ̓��b�Z�[�W��1���o�^����Ă��āA���b�Z�[�W�L�[����v����ꍇ�A
     * ���b�Z�[�W�������擾���ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString04() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertEquals("�e�X�g���b�Z�[�W�O�P", actual);
    }

    /**
     * testGetMessageLocaleString05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST02"<br>
     *         (���) messages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *                ["TEST02"->"�e�X�g���b�Z�[�W�O�Q"]<br>
     *                ["TEST03"->"�e�X�g���b�Z�[�W�O�R"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�Q"<br>
     *         
     * <br>
     * messages�ɂ̓��b�Z�[�W���������o�^���ꂢ�ă��b�Z�[�W�L�[�����̓���1����
     * ��v����ꍇ�A���b�Z�[�W�������擾���ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString05() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        map.put("TEST02", "�e�X�g���b�Z�[�W�O�Q");
        map.put("TEST03", "�e�X�g���b�Z�[�W�O�R");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST02");

        // ����
        assertEquals("�e�X�g���b�Z�[�W�O�Q", actual);
    }

    /**
     * testGetMessageLocaleString06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:���Map<br>
     *         (���) dbMessages:["TEST01"->""]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * messages�ɉ����o�^����Ă��炸�AdbMessages�ɓo�^����Ă���L�[��
     * ���b�Z�[�W�L�[����v�������b�Z�[�W�������󕶎���̏ꍇ�A�󕶎���
     * �ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString06() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertEquals("", actual);
    }

    /**
     * testGetMessageLocaleString07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:���Map<br>
     *         (���) dbMessages:["TEST01"->"�c�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�c�a�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * messages�������o�^����Ă��炸�AdbMessages�Ƀ��b�Z�[�W��
     * 1���o�^���ꂢ�ă��b�Z�[�W�L�[����v����ꍇ�A���b�Z�[�W������
     * �擾���ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString07() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "�c�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertEquals("�c�a�e�X�g���b�Z�[�W�O�P", actual);
    }

    /**
     * testGetMessageLocaleString08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST02"<br>
     *         (���) messages:���Map<br>
     *         (���) dbMessages:["TEST01"->"�c�a�e�X�g���b�Z�[�W�O�P"]<br>
     *                ["TEST02"->"�c�a�e�X�g���b�Z�[�W�O�Q"]<br>
     *                ["TEST03"->"�c�a�e�X�g���b�Z�[�W�O�R"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�c�a�e�X�g���b�Z�[�W�O�Q"<br>
     *         
     * <br>
     * messages�������o�^����Ă��炸�AdbMessages�Ƀ��b�Z�[�W��
     * �������o�^���ꂢ�ă��b�Z�[�W�L�[�����̓���1���ƈ�v����ꍇ�A
     * ���b�Z�[�W�������擾���ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString08() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "�c�a�e�X�g���b�Z�[�W�O�P");
        dbmap.put("TEST02", "�c�a�e�X�g���b�Z�[�W�O�Q");
        dbmap.put("TEST03", "�c�a�e�X�g���b�Z�[�W�O�R");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST02");

        // ����
        assertEquals("�c�a�e�X�g���b�Z�[�W�O�Q", actual);
    }

    /**
     * testGetMessageLocaleString09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) dbMessages:["TEST01"->"�c�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * ���b�Z�[�W�L�[��dbMessages��messages�ɑ��݂���ꍇ�Amessages����
     * ���b�Z�[�W�������擾���ĕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString09() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", "�c�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertEquals("�e�X�g���b�Z�[�W�O�P", actual);
    }

    /**
     * testGetMessageLocaleString10()
     * <br><br>
     * 
     * T(�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:null<br>
     *         (���) messages:���Map<br>
     *         (���) dbMessages:���Map<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.db.message.resources"<br>
     *                    ���b�v������O�Fnull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message key 'null' or empty not allowed."<br>
     *         
     * <br>
     * key��null�̏ꍇ�A�G���[���O���o�͂��A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString10() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> dbmap = new HashMap<String, String>();
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // �e�X�g���{
        try {
            resources.getMessage(Locale.JAPAN, null);
            fail();
        } catch (SystemException e) {
            // ����
            assertNull(e.getCause());
            assertEquals("errors.db.message.resources",
                         e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "Message key 'null' or empty not allowed."));
        }
    }

    /**
     * testGetMessageLocaleString11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:["TEST01"->null]<br>
     *         (���) dbMessages:["TEST01"->null]<br>
     *         (���) fwMessage(GlobalMessageResources):["TEST01"->"�e�v�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�v�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * messages�AdbMessages�ɊY�����郁�b�Z�[�W�L�[���o�^����Ă��炸�A
     * �t���[�����[�N�̃��b�Z�[�W���\�[�X�̃��b�Z�[�W�L�[�ƈ�v����ꍇ�A
     * �t���[�����[�N����̃��b�Z�[�W���擾�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString11() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("TEST01", null);
        Map<String, String> dbmap = new HashMap<String, String>(1);
        dbmap.put("TEST01", null);
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", dbmap);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // GlobalMessageResources���̃��b�Z�[�W���\�[�X���o�b�N�A�b�v�p�ɑޔ�������
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");
        Map<String, String> newFwMap
            = new HashMap<String, String>();
        newFwMap.put("TEST01", "�e�v�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               newFwMap);
        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               new HashMap());

        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertEquals("�e�v�e�X�g���b�Z�[�W�O�P", actual);
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);
    }

    /**
     * testGetMessageLocaleString12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:���Map<br>
     *         (���) dbMessages:null<br>
     *         (���) fwMessage(GlobalMessageResources):TEST01���L�[�Ƃ���G���g���͂Ȃ�<br>
     *         (���) returnNull:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * dbMessages��null�ŁAmessages�����Map�ŁA����ɃV�X�e���̃��b�Z�[�W���\�[�X�ɂ��o�^����Ă��Ȃ��ꍇ�Anull��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString12() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", null);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.TRUE);
        
        // GlobalMessageResources���̃��b�Z�[�W���\�[�X���o�b�N�A�b�v�p�ɑޔ�������
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages",
                               new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               new HashMap());

        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertNull(actual);
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages",
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);

    }

    /**
     * testGetMessageLocaleString13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:locale.JAPAN<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:null<br>
     *         (���) dbMessages:null<br>
     *         (���) fwMessage(GlobalMessageResources):TEST01���L�[�Ƃ���G���g���͂Ȃ�<br>
     *         (���) returnNull:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"???ja_JP.TEST01???"<br>
     *         
     * <br>
     * dbMessages��null�ŁAmessages��null�ŁA����ɃV�X�e���̃��b�Z�[�W���\�[�X�ɂ��o�^����Ă��Ȃ��ꍇ�AreturnNull��false�Ȃ��???Locale.key???�`���ŕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString13() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        UTUtil.setPrivateField(resources, "messages", null);
        UTUtil.setPrivateField(resources, "dbMessages", null);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.FALSE);
        
        // GlobalMessageResources���̃��b�Z�[�W���\�[�X���o�b�N�A�b�v�p�ɑޔ�������
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               new HashMap());

        // �e�X�g���{
        String actual = resources.getMessage(Locale.JAPAN, "TEST01");

        // ����
        assertEquals("???ja_JP.TEST01???", actual);
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);

    }

    /**
     * testGetMessageLocaleString14()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) locale:null<br>
     *         (����) key:"TEST01"<br>
     *         (���) messages:���Map<br>
     *         (���) dbMessages:null<br>
     *         (���) fwMessage(GlobalMessageResources):TEST01���L�[�Ƃ���G���g���͂Ȃ�<br>
     *         (���) returnNull:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"???.TEST01???"<br>
     *         
     * <br>
     * dbMessages��null�ŁAmessages�����Map�ŁA����ɃV�X�e����
     * ���b�Z�[�W���\�[�X�ɂ��o�^����Ă��Ȃ��ꍇ�AreturnNull��false�Ȃ��
     * ���P�[����null�̂Ƃ���???.key???�`���ŕԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageLocaleString14() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(resources, "messages", map);
        UTUtil.setPrivateField(resources, "dbMessages", null);
        UTUtil.setPrivateField(resources, "returnNull", Boolean.FALSE);
        
        // GlobalMessageResources���̃��b�Z�[�W���\�[�X���o�b�N�A�b�v�p�ɑޔ�������
        Object tempFwMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "fwMessages");        
        Object tempGlobalMap 
            = UTUtil.getPrivateField(GlobalMessageResources.getInstance(), 
                                     "globalMessages");
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               new HashMap());
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages",
                               new HashMap());

        // �e�X�g���{
        Locale locale = null;
        String actual = resources.getMessage(locale, "TEST01");

        // ����
        assertEquals("???.TEST01???", actual);
        
        // ���ɖ߂�
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "fwMessages", 
                               tempFwMap);
        UTUtil.setPrivateField(GlobalMessageResources.getInstance(), 
                               "globalMessages", 
                               tempGlobalMap);

    }

    /**
     * testGetMessageString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"TEST01"<br>
     *         (���) messages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * getMessage(Locale, String)���Ăяo����Ă��邩�m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessageString01() throws Exception {
        // �O����
        DBMessageResources resources 
            = new DBMessageResources(null, null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "messages", map);
        
        // �e�X�g���{
        String actual = resources.getMessage("TEST01");

        // ����
        assertEquals("�e�X�g���b�Z�[�W�O�P", actual);
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:"DBMessageResources_MessageResources05"<br>
     *                       �y�t�@�C���̓��e�z<br>
     *                       error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *         (���) dbMessages:null<br>
     *         (���) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  formats->HashMap�C���X�^���X<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo�����<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         
     * <br>
     * dbInit���Ă΂�ApropertyInit���Ă΂�Ă��邱�Ƃ��m�F����B<br>
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�̂Ƃ��A
     * formats�͍����ւ����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryString01() throws Exception {
        // �O����
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "disable");
        
        // �e�X�g���{
        DBMessageResources resources
            = new DBMessageResources(factory, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05");     
        
        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap 
            = (Map) UTUtil.getPrivateField(DBMessageResources.class, 
                                           "dbMessages");
        assertTrue(actualDbMap.isEmpty());
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B",
                     actualMap.get("error.date.format"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:"DBMessageResources_MessageResources05"<br>
     *                       �y�t�@�C���̓��e�z<br>
     *                       error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  formats->MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         
     * <br>
     * dbInit���Ă΂ꂸ�ApropertyInit���Ă΂�Ă��邱�Ƃ��m�F����B
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�ȊO�̂Ƃ��A
     * formats�����̖߂�l�ɍ����ւ����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryString02() throws Exception {
        // �O����        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
            DBMessageResources.class,
            "dbMessages",
            dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // �e�X�g���{
        DBMessageResources resources
            = new DBMessageResources(factory, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05");  
        
        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B",
                     actualMap.get("error.date.format"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:null<br>
     *         (����) config:"DBMessageResources_MessageResources05"<br>
     *                       �y�t�@�C���̓��e�z<br>
     *                       error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->null<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         
     * <br>
     * dbInit���Ă΂ꂸ�ApropertyInit���Ă΂�Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryString03() throws Exception {
        // �O����        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        
        // �e�X�g���{
        DBMessageResources resources 
            = new DBMessageResources(null, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05");

        // ����
        assertNull(UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B",
                     actualMap.get("error.date.format"));
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:null<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->null<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������null�ŌĂяo�����<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "null" is illegal."<br>
     *         
     * <br>
     * config��null�̏ꍇ�AdbInit���Ă΂ꂸ�ApropertyInit���Ă΂�A�G���[���O���o�͂��A�C���X�^���X��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryString04() throws Exception {
        // �O����
        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
            DBMessageResources.class,
            "dbMessages",
            dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // �e�X�g���{
        DBMessageResources resources
            = new DBMessageResources(factory, null);

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
       
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());

        assertTrue(LogUTUtil.checkError(
            "Message resources file \"null\" is illegal."));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryString05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:""<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->""<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "" is illegal."<br>
     *         
     * <br>
     * config��null�̏ꍇ�AdbInit���Ă΂ꂸ�ApropertyInit���Ă΂�A
     * �G���[���O���o�͂��A�C���X�^���X��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryString05() throws Exception {
        // �O����        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
            DBMessageResources.class,
            "dbMessages",
            dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // �e�X�g���{
        DBMessageResources resources
            = new DBMessageResources(factory, "");

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());
        assertTrue(LogUTUtil.checkError(
            "Message resources file \"\" is illegal."));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean01()
     * <br><br>
     * 
     *  (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:"DBMessageResources_MessageResources05"<br>
     *                       �y�t�@�C���̓��e�z<br>
     *                       error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *         (����) returnNull:true<br>
     *         (���) dbMessages:null<br>
     *         (���) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->true<br>
     *                  formats->HashMap�C���X�^���X<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo�����<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         
     * <br>
     * dbInit���Ă΂�ApropertyInit���Ă΂�Ă��邱�Ƃ��m�F����B
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�̂Ƃ��A
     * formats�͍����ւ����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean01() throws Exception {
        // �O����
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                null);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "disable");
        
        // �e�X�g���{
        DBMessageResources resources
        = new DBMessageResources(factory, 
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            true);    
        
        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap 
            = (Map) UTUtil.getPrivateField(DBMessageResources.class, 
            "dbMessages");
        assertTrue(actualDbMap.isEmpty());
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B",
                     actualMap.get("error.date.format"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:"DBMessageResources_MessageResources05"<br>
     *                       �y�t�@�C���̓��e�z<br>
     *                       error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *         (����) returnNull:true<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         (���) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->true<br>
     *                  formats->MessageFormatCloneReturnIfUseDateFormatMap�C���X�^���X<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         
     * <br>
     * dbInit���Ă΂ꂸ�ApropertyInit���Ă΂�Ă��邱�Ƃ��m�F����B
     * MessageFormatCacheMapFactory#getInstance�̖߂�l��null�ȊO�̂Ƃ��A
     * formats�����̖߂�l�ɍ����ւ����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean02() throws Exception {
        // �O����
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // �e�X�g���{
        DBMessageResources resources
        = new DBMessageResources(factory, 
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            true);     
        
        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B",
                     actualMap.get("error.date.format"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:null<br>
     *         (����) config:"DBMessageResources_MessageResources05"<br>
     *                       �y�t�@�C���̓��e�z<br>
     *                       error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *         (����) returnNull:true<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->null<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->true<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         
     * <br>
     * dbInit���Ă΂ꂸ�ApropertyInit���Ă΂�Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean03() throws Exception {
        // �O����        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        
        // �e�X�g���{
        DBMessageResources resources 
        = new DBMessageResources(null, 
            DBMessageResources.class.getPackage().getName()
            .replace('.', '/') + "/DBMessageResources_MessageResources05",
            true);   
        
        // ����
        assertNull(UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B",
                     actualMap.get("error.date.format"));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:null<br>
     *         (����) returnNull:true<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->null<br>
     *                  returnNull->true<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "null" is illegal."<br>
     *         
     * <br>
     * config��null�̏ꍇ�AdbInit���Ă΂ꂸ�ApropertyInit���Ă΂�A
     * �G���[���O���o�͂��A�C���X�^���X��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean04() throws Exception {
        // �O����        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // �e�X�g���{
        DBMessageResources resources
            = new DBMessageResources(factory, null, true);

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));

        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());

        assertTrue(LogUTUtil.checkError(
                "Message resources file \"null\" is illegal."));
        
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:""<br>
     *         (����) returnNull:true<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->""<br>
     *                  returnNull->true<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Message resources file "" is illegal."<br>
     *         
     * <br>
     * config��null�̏ꍇ�AdbInit���Ă΂ꂸ�ApropertyInit���Ă΂�A
     * �G���[���O���o�͂��A�C���X�^���X��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean05() throws Exception {
        // �O����        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // �e�X�g���{
        DBMessageResources resources
            = new DBMessageResources(factory, "", true);

        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertTrue(actualMap.isEmpty());
        assertTrue(LogUTUtil.checkError(
                "Message resources file \"\" is illegal."));
    }

    /**
     * testDBMessageResourcesMessageResourcesFactoryStringboolean06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) factory:DBMessageResourcesFactory�C���X�^���X<br>
     *         (����) config:"DBMessageResources_MessageResources05"<br>
     *                       �y�t�@�C���̓��e�z<br>
     *                       error.date.format={0}�̓�����{1}�`���œ��͂��Ă��������B<br>
     *         (����) returnNull:false<br>
     *         (���) dbMessages:["TEST01"->"�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageResources:factory->�O������Őݒ肵���C���X�^���X<br>
     *                  config->"DBMessageResources_MessageResources05"<br>
     *                  returnNull->false<br>
     *         (��ԕω�) dbInit()�̌Ăяo��:�Ăяo����Ȃ�<br>
     *         (��ԕω�) propertyInit()�̌Ăяo��:������config�Ɠ�����������ŌĂяo�����<br>
     *         
     * <br>
     * dbInit���Ă΂ꂸ�ApropertyInit���Ă΂�Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBMessageResourcesMessageResourcesFactoryStringboolean06() throws Exception {
        // �O����        
        // dbInit���Ă΂�Ȃ��悤��map��ݒ肷��
        Map<String, String> dbMap
            = new HashMap<String, String>();
        dbMap.put("TEST01", "�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(
                DBMessageResources.class,
                "dbMessages",
                dbMap);
        DBMessageResourcesFactory factory
            = new DBMessageResourcesFactory();
        
        // �e�X�g���{

        DBMessageResources resources
            = new DBMessageResources(factory, 
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                false);   
        
        // ����
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(
                DBMessageResources.class.getPackage().getName()
                .replace('.', '/') + "/DBMessageResources_MessageResources05",
                UTUtil.getPrivateField(resources, "config"));
        assertEquals(false, UTUtil.getPrivateField(resources, "returnNull"));
        
        Map actualDbMap = (Map) UTUtil.getPrivateField(resources, "dbMessages");
        assertSame(dbMap, actualDbMap);
        assertEquals(1, actualDbMap.size());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actualDbMap.get("TEST01"));
        
        Map actualMap = (Map) UTUtil.getPrivateField(resources, "messages");
        assertEquals(1, actualMap.size());
        assertEquals("{0}�̓�����{1}�`���œ��͂��Ă��������B",
                     actualMap.get("error.date.format"));
    }

}
