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

import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * {@link jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * MessageResourcesDAO�̎����N���X�B
 * �����̃N���X�̃e�X�g���s���ۂɂ�MessageResourcesDAOImpl.sql��p����
 * DB�Ƀe�[�u�����쐬���Ă����K�v������B
 * ���e�X�g�ɍۂ��āAUTUtil��p���ăf�[�^�x�[�X�𑀍삷�邽�߁A
 * JDBC�h���C�o���N���X�p�X�ɒǉ����A
 * utlib.conf��DB�ڑ��p�ɃA�J�E���g�ƃp�X���[�h�����IP�A�h���X�̐ݒ�����邱�ƁB
 * �����l�ɁA�f�[�^�\�[�X�̐ݒ��Ǝ��ɍs���K�v�����邽�߁A
 * jp/terasoluna/fw/web/struts/action
 * /MessageResourcesDAOImpl_dbMessageResources04.xml
 * �ɑ΂��Ă��A�J�E���g�ƃp�X���[�h�AIP�A�h���X�̐ݒ���s�����ƁB
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 */
public class MessageResourcesDAOImplTest extends TestCase {




    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageResourcesDAOImplTest.class);
    }

    /**
     * ���������s���B
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        UTUtil.deleteAll("MESSAGE");
    }

    /**
     * �㏈�����s���B
     * �f�[�^�x�[�X������������B
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        UTUtil.deleteAll("MESSAGE");
    }

    /**
     * testQueryMessageMap01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) dbMessageResources.xml:"aaaaa"<br>
     *                �i���݂��Ȃ��t�@�C�����j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FBeansException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "aaaaa not found or not -//SPRING//DTD BEAN//EN or invalid or anything else."<br>
     *                    ����O��<br>
     *                    BeansException<br>
     *
     * <br>
     * Bean��`�t�@�C�����Ȃ������ꍇ�A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap01() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("aaaaa");

        // �e�X�g���{
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl",
                         e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError(
                       "jp/terasoluna/fw/web/struts/action/aaaaa not found or not -//SPRING//DTD BEAN//EN or invalid or anything else.",
                       e.getCause()));
        }

    }

    /**
     * testQueryMessageMap02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources01.xml"<br>
     *                �iid������"dataSource"��<bean>�v�f���Ȃ��j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FNoSuchBeanDefinitionException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    ""dataSource" not defined."<br>
     *                    ����O��<br>
     *                    NoSuchBeanDefinitionException<br>
     *
     * <br>
     * Bean��`�t�@�C����id������dataSource��bean�v�f���Ȃ������ꍇ�A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap02() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources01.xml");

        // �e�X�g���{
        try {
            dao.queryMessageMap(
                    "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertEquals(NoSuchBeanDefinitionException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                       "\"dataSource\" not defined.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources02.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="java.lang.Object"><br>
     *                  �c<br>
     *                </bean><br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FClassCastException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    ""dataSource" not implemented DataSource."<br>
     *                    ����O��<br>
     *                    ClassCastException<br>
     *
     * <br>
     * Bean��`�t�@�C����id������dataSource�����Aclass������DataSource��
     * �����N���X�łȂ������ꍇ�A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap03() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources02.xml");

        // �e�X�g���{
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertEquals(ClassCastException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                       "\"dataSource\" not implemented DataSource.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources03.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"
     *                        abstract="true"><br>
     *                  �c<br>
     *                </bean><br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FBeansException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "getBean() failed"<br>
     *                    ����O��<br>
     *                    BeansException<br>
     *
     * <br>
     * Bean��`�t�@�C����id������dataSource�����Aabstract������true�̏ꍇ�A
     * ��O���X���[���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap04() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources03.xml");

        // �e�X�g���{
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError(
                       "getBean() failed",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:null<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FInvalidDataAccessApiUsageException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "SQL is null or something wrong."<br>
     *                    ����O��<br>
     *                    InvalidDataAccessApiUsageException<br>
     *
     * <br>
     * sql��null�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap05() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // �e�X�g���{
        try {
            dao.queryMessageMap(null);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertEquals(InvalidDataAccessApiUsageException.class.getName(),
                         e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError(
                       "SQL is null or something wrong.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:""<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FDataAccessException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Data access exception."<br>
     *                    ����O��<br>
     *                    DataAccessException<br>
     *
     * <br>
     * sql���󕶎���̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap06() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // �e�X�g���{
        try {
            dao.queryMessageMap("");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:"aaaaa"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FDataAccessException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Data access exception."<br>
     *                    ����O��<br>
     *                    DataAccessException<br>
     *
     * <br>
     * sql�������̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap07() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // �e�X�g���{
        try {
            dao.queryMessageMap("aaaaa");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM AAAAA"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *         (���) MESSAGE_KEY:MESSAGE�e�[�u�������݂��Ȃ�<br>
     *         (���) MESSAGE_VALUE:MESSAGE�e�[�u�������݂��Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FDataAccessException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Data access exception."<br>
     *                    ����O��<br>
     *                    DataAccessException<br>
     *
     * <br>
     * �����Ώۂ̃e�[�u�������݂��Ȃ��ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap08() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // �e�X�g���{
        try {
            dao.queryMessageMap("SELECT MESSAGE_KEY, MESSAGE_VALUE FROM AAAAA");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sql:"UPDATE MESSAGE SET MESSAGE_VALUE = '�X�V���ꂽ���b�Z�[�W'"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *         (���) MESSAGE_KEY:"TEST01"<br>
     *         (���) MESSAGE_VALUE:"�e�X�g�߂����[���O�P"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.message.resources.dao.impl"<br>
     *                    ���b�v������O�FDataAccessException<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "Data access exception."<br>
     *                    ����O��<br>
     *                    DataAccessException<br>
     *
     * <br>
     * sql���X�V�n�̏ꍇ�A��O���X���[����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap09() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        String[][] data = {
            {"MESSAGE_KEY", "MESSAGE_VALUE"},
            {"TEST01", "�e�X�g�߂����[���O�P"}
        };
        UTUtil.setData("MESSAGE", data);
        // �e�X�g���{
        try {
            dao.queryMessageMap(
                    "UPDATE MESSAGE SET MESSAGE_VALUE = '�X�V���ꂽ���b�Z�[�W'");
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.message.resources.dao.impl", e.getErrorCode());
            assertTrue(e.getCause() instanceof DataAccessException);
            assertTrue(LogUTUtil.checkError(
                       "Data access exception.",
                       e.getCause()));
        }
    }

    /**
     * testQueryMessageMap10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *         (���) MESSAGE_KEY:�Ȃ�<br>
     *         (���) MESSAGE_VALUE:�Ȃ�<br>
     *         (���) count:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Map<String, String>:���Map<br>
     *
     * <br>
     * DB�̃��b�Z�[�W���\�[�X��0���̏ꍇ�A���Map���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap10() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");

        // �e�X�g���{
        Map<String, String> map = dao.queryMessageMap(
                "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");

        // ����
        assertTrue(map.isEmpty());

    }

    /**
     * testQueryMessageMap11()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *         (���) MESSAGE_KEY:"TEST01"<br>
     *         (���) MESSAGE_VALUE:"�e�X�g�߂����[���O�P"<br>
     *         (���) count:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Map<String, String>:["TEST01"->"�e�X�g�߂����[���O�P"]<br>
     *
     * <br>
     * DB�̃��b�Z�[�W���\�[�X��1���̏ꍇ�A1�������Ă���Map���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap11() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");
        String[][] data = {
            {"MESSAGE_KEY", "MESSAGE_VALUE"},
            {"TEST01", "�e�X�g�߂����[���O�P"}
        };
        UTUtil.setData("MESSAGE", data);
        // �e�X�g���{
        Map<String, String> map = dao.queryMessageMap(
                "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");

        // ����
        assertEquals(1, map.size());
        assertEquals("�e�X�g�߂����[���O�P", map.get("TEST01"));
    }

    /**
     * testQueryMessageMap12()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sql:"SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE"<br>
     *         (���) dbMessageResources.xml:"MessageResourcesDAOImple_dbMessageResources04.xml"<br>
     *                �y�t�@�C�����ɒ�`���ꂽdataSource�z<br>
     *                <bean id="dataSource"<br>
     *                        class="org.springframework.jdbc.datasource.DriverManagerDataSource"><br>
     *                  �c<br>
     *                </bean><br>
     *         (���) MESSAGE_KEY:"TEST01"<br>
     *                "TEST02"<br>
     *                "TEST03"<br>
     *         (���) MESSAGE_VALUE:"�e�X�g�߂����[���O�P"<br>
     *                "testMESSAGE02"<br>
     *                "�����I����O"<br>
     *         (���) count:3<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Map<String, String>:["TEST01"->"�e�X�g�߂����[���O�P"]<br>
     *                  ["TEST02"->"testMessage02"]<br>
     *                  ["TEST03"->"�����I����O"]<br>
     *
     * <br>
     * DB�̃��b�Z�[�W���\�[�X��3���̏ꍇ�A3�������Ă���Map���Ԃ���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testQueryMessageMap12() throws Exception {
        // �O����
        MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 dao
            = new MessageResourcesDAOImpl_MessageResourcesDAOImplStub01();
        dao.setTestBeansXmlName("MessageResourcesDAOImpl_dbMessageResources04.xml");
        String[][] data = {
            {"MESSAGE_KEY", "MESSAGE_VALUE"},
            {"TEST01", "�e�X�g�߂����[���O�P"},
            {"TEST02", "testMessage02"},
            {"TEST03", "�����I����O"},
        };
        UTUtil.setData("MESSAGE", data);
        // �e�X�g���{
        Map<String, String> map = dao.queryMessageMap(
                "SELECT MESSAGE_KEY, MESSAGE_VALUE FROM MESSAGE");

        // ����
        assertEquals(3, map.size());
        assertEquals("�e�X�g�߂����[���O�P", map.get("TEST01"));
        assertEquals("testMessage02", map.get("TEST02"));
        assertEquals("�����I����O", map.get("TEST03"));
    }

    /**
     * testGetBeanXml01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) BEANS_XML:"dbMessageResources.xml"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"dbMessageResources.xml"<br>
     *
     * <br>
     * ����n�P���̂݃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBeanXml01() throws Exception {
        // �O����
        MessageResourcesDAOImpl dao = new MessageResourcesDAOImpl();

        // �e�X�g���{
        String actual = dao.getBeansXml();

        // ����
        assertEquals("dbMessageResources.xml", actual);
    }



}
