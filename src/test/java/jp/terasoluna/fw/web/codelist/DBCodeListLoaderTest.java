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

package jp.terasoluna.fw.web.codelist;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import jp.terasoluna.utlib.MockDataSource;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;

/**
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �R�[�h���X�g���̏��������f�[�^�x�[�X��p���čs���N���X<br>
 * �E�O�����<br>
 * �Eutlib.conf��p�ӂ�DB�ڑ������L�q���邱�ƁB<br>
 * �Ecreate_dbcodetest.sql�Őݒ肳���DB��񂪂��炩���ߐݒ肳��Ă���K�v������B<br>
 * ���̃t�@�C���̒��ł͈ȉ��̂悤��DB���쐬���A�e�X�g�p�^�[�����ɉ��L�̗v�f��DB�ɐݒ肷��B<br>
 * DB�̖��O�FDBCODETEST<br>
 * �J�����FKEY, VALUE<br>
 * �v�f�F <br>
 * �i�P�jKEY = '1' , VALUE = 'abc'<br>
 * �i�Q�jKEY = '2' , VALUE = 'xyz'<br>
 * �i�R�jKEY = '3' , VALUE = '����������'
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 */
public class DBCodeListLoaderTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBCodeListLoaderTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        // DB�f�[�^�ݒ�       
        UTUtil.deleteAll("DBCODETEST");
        String[][] data = { {"KEY", "VALUE","LANGUAGE","COUNTRY","VARIANT"},
                {"001", "val_1",null,null,null},
                {"002", "val_2","ja",null,null},
                {"003", "val_3","ja",null,null},
                {"004", "val_4","en",null,null},
                {"005", "val_5","en",null,null},
                {"006", "val_6","en","GB",null},
                {"007", "val_7","en","GB",null},
                {"008", "val_8","en","US",null},
                {"009", "val_9","en","US",null},
                {"010", "val_10","en","US","us01"},
                {"011", "val_11","en","US","us01"},
                {"012", "val_12","en","US","us02"},
                {"013", "val_13","en","US","us02"}};
        UTUtil.setData("DBCODETEST", data);
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        
        // DB�f�[�^�폜       
        UTUtil.deleteAll("DBCODETEST");
        
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DBCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testLoad01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) localeMap:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s����Ȃ��B<br>
     *         
     * <br>
     * codeLists�����݂���ꍇ�͉��������I�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad01() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        
        loader.localeMap = new HashMap<Locale, List<CodeBean>>();
        
        // �e�X�g���{
        loader.load();

        // ����
        assertFalse(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoad02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s�����B<br>
     *         
     * <br>
     * codeLists�����݂��Ȃ��ꍇ��loadCodeList()���Ăяo����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad02() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        loader.localeMap = null;
        
        // �e�X�g���{
        loader.load();

        // ����
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) localeMap:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s�����B<br>
     *         
     * <br>
     * ���̃��\�b�h���Ă΂���loadCodeList()���Ă΂�邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReload01() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        loader.localeMap = new HashMap<Locale, List<CodeBean>>();
        
        // �e�X�g���{
        loader.reload();

        // ����
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) loadCodeList():���s�����B<br>
     *         
     * <br>
     * codeLists��null�̏ꍇ�ł�loadCodeList()���Ă΂�邱�Ƃ��m�F����B�isynchronized���������ɌĂԁj
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReload02() throws Exception {
        // �O����
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        loader.localeMap = null;
        
        // �e�X�g���{
        loader.reload();

        // ����
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoadCodeList01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvalidDataAccessApiUsageException<br>
     *                    ���b�Z�[�W�FProperty 'dataSource' is required<br>
     *         
     * <br>
     * dataSource��null�̏ꍇInvalidDataAccessApiUsageException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'");
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", null);
        
        // �e�X�g���{
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // ����
            assertEquals("Property 'dataSource' is required", e.getMessage());
            return;
        }
        fail();
    }
   
    /**
     * testLoadCodeList02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���Ғl: ���͒l�F(���) localeMap:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * �ȉ��̃R�[�h���X�g���ݒ肳��邱��
     * �E���P�[��: ja (�f�t�H���g���P�[��)
     * 001 val_1
     * 002 val_2
     * 003 val_3
     * 004 val_4
     * 005 val_5
     * 006 val_6
     * 007 val_7
     * 008 val_8
     * 009 val_9
     * 010 val_10
     * 011 val_11
     * 012 val_12
     * 013 val_13
     *         
     * <br>
     * sql�Ń��P�[�����擾���Ȃ��ꍇ�A�f�t�H���g���P�[���ɃR�[�h���X�g��
     * �ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList02() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST ORDER BY KEY ASC");
        
        // �e�X�g���{
        loader.loadCodeList();
        
        // ����
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // �f�t�H���g���P�[���̃R�[�h���X�g���Q��
        List<CodeBean> codeList = result.get(loader.defaultLocale);
        assertEquals(13, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        assertEquals("004", codeList.get(3).getId());
        assertEquals("val_4", codeList.get(3).getName());
        
        assertEquals("005", codeList.get(4).getId());
        assertEquals("val_5", codeList.get(4).getName());
        
        assertEquals("006", codeList.get(5).getId());
        assertEquals("val_6", codeList.get(5).getName());
        
        assertEquals("007", codeList.get(6).getId());
        assertEquals("val_7", codeList.get(6).getName());
        
        assertEquals("008", codeList.get(7).getId());
        assertEquals("val_8", codeList.get(7).getName());
        
        assertEquals("009", codeList.get(8).getId());
        assertEquals("val_9", codeList.get(8).getName());
        
        assertEquals("010", codeList.get(9).getId());
        assertEquals("val_10", codeList.get(9).getName());
        
        assertEquals("011", codeList.get(10).getId());
        assertEquals("val_11", codeList.get(10).getName());
        
        assertEquals("012", codeList.get(11).getId());
        assertEquals("val_12", codeList.get(11).getName());
        
        assertEquals("013", codeList.get(12).getId());
        assertEquals("val_13", codeList.get(12).getName());
    }
    
    /**
     * testLoadCodeList03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE,LANGUAGE 
     *                                    FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * ���Ғl: �ȉ��̃R�[�h���X�g���ݒ肳��邱��
     * �E���P�[��: ja (�f�t�H���g���P�[��)
     * 001 val_1
     * 002 val_2
     * 003 val_3
     * �E���P�[��: en
     * 004 val_4
     * 005 val_5
     * 006 val_6
     * 007 val_7
     * 008 val_8
     * 009 val_9
     * 010 val_10
     * 011 val_11
     * 012 val_12
     * 013 val_13    
     * <br>
     * sql�Ń��P�[�����擾���Ȃ��ꍇ�A�f�t�H���g���P�[���ɃR�[�h���X�g��
     * �ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList03() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE,LANGUAGE FROM DBCODETEST ORDER BY KEY ASC");
        
        // �e�X�g���{
        loader.loadCodeList();
        
        // ����
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // ���P�[��: ja
        List<CodeBean> codeList = result.get(new Locale("ja"));
        assertEquals(3, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        // ���P�[��: en
        codeList = result.get(new Locale("en"));
        assertEquals(10, codeList.size());
        
        assertEquals("004", codeList.get(0).getId());
        assertEquals("val_4", codeList.get(0).getName());
        
        assertEquals("005", codeList.get(1).getId());
        assertEquals("val_5", codeList.get(1).getName());
        
        assertEquals("006", codeList.get(2).getId());
        assertEquals("val_6", codeList.get(2).getName());
        
        assertEquals("007", codeList.get(3).getId());
        assertEquals("val_7", codeList.get(3).getName());
        
        assertEquals("008", codeList.get(4).getId());
        assertEquals("val_8", codeList.get(4).getName());
        
        assertEquals("009", codeList.get(5).getId());
        assertEquals("val_9", codeList.get(5).getName());
        
        assertEquals("010", codeList.get(6).getId());
        assertEquals("val_10", codeList.get(6).getName());
        
        assertEquals("011", codeList.get(7).getId());
        assertEquals("val_11", codeList.get(7).getName());
        
        assertEquals("012", codeList.get(8).getId());
        assertEquals("val_12", codeList.get(8).getName());
        
        assertEquals("013", codeList.get(9).getId());
        assertEquals("val_13", codeList.get(9).getName());
    }
    
    /**
     * testLoadCodeList04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE,LANGUAGE,COUNTRY
     *                                    FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * ���Ғl: �ȉ��̃R�[�h���X�g���ݒ肳��邱��
     * �E���P�[��: ja (�f�t�H���g���P�[��)
     * 001 val_1
     * 002 val_2
     * 003 val_3
     * 
     * �E���P�[��: en
     * 004 val_4
     * 005 val_5
     * 
     * �E���P�[��: en_GB
     * 006 val_6
     * 007 val_7
     * 
     * �E���P�[��: en_US
     * 008 val_8
     * 009 val_9
     * 010 val_10
     * 011 val_11
     * 012 val_12
     * 013 val_13         
     * <br>
     * sql�Ń��P�[�����擾���Ȃ��ꍇ�A�f�t�H���g���P�[���ɃR�[�h���X�g��
     * �ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList04() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE,LANGUAGE,COUNTRY FROM DBCODETEST ORDER BY KEY ASC");
        
        // �e�X�g���{
        loader.loadCodeList();
        
        // ����
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // ���P�[��: ja
        List<CodeBean> codeList = result.get(new Locale("ja"));
        assertEquals(3, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        // ���P�[��: en
        codeList = result.get(new Locale("en"));
        assertEquals(2, codeList.size());
        
        assertEquals("004", codeList.get(0).getId());
        assertEquals("val_4", codeList.get(0).getName());
        
        assertEquals("005", codeList.get(1).getId());
        assertEquals("val_5", codeList.get(1).getName());
        
        // ���P�[��: en_GB
        codeList = result.get(new Locale("en", "GB"));
        assertEquals(2, codeList.size());
        
        assertEquals("006", codeList.get(0).getId());
        assertEquals("val_6", codeList.get(0).getName());
        
        assertEquals("007", codeList.get(1).getId());
        assertEquals("val_7", codeList.get(1).getName());
        
        // ���P�[��: en_US
        codeList = result.get(new Locale("en", "US"));
        assertEquals(6, codeList.size());
        
        assertEquals("008", codeList.get(0).getId());
        assertEquals("val_8", codeList.get(0).getName());
        
        assertEquals("009", codeList.get(1).getId());
        assertEquals("val_9", codeList.get(1).getName());
        
        assertEquals("010", codeList.get(2).getId());
        assertEquals("val_10", codeList.get(2).getName());
        
        assertEquals("011", codeList.get(3).getId());
        assertEquals("val_11", codeList.get(3).getName());
        
        assertEquals("012", codeList.get(4).getId());
        assertEquals("val_12", codeList.get(4).getName());
        
        assertEquals("013", codeList.get(5).getId());
        assertEquals("val_13", codeList.get(5).getName());
    }
    
    /**
     * testLoadCodeList05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE,LANGUAGE,COUNTRY,VARIANT
     *                                    FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * ���Ғl: �ȉ��̃R�[�h���X�g���ݒ肳��邱��
     * �E���P�[��: ja (�f�t�H���g���P�[��)
     * 001 val_1
     * 002 val_2
     * 003 val_3
     * 
     * �E���P�[��: en
     * 004 val_4
     * 005 val_5
     * 
     * �E���P�[��: en_GB
     * 006 val_6
     * 007 val_7
     * 
     * �E���P�[��: en_US
     * 008 val_8
     * 009 val_9
     * 
     * �E���P�[��: en_US_us01

     * 010 val_10
     * 011 val_11
     * 
     * �E���P�[��: en_US_us02
     * 012 val_12
     * 013 val_13         
     * <br>
     * sql�Ń��P�[�����擾���Ȃ��ꍇ�A�f�t�H���g���P�[���ɃR�[�h���X�g��
     * �ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList05() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE,LANGUAGE,COUNTRY,VARIANT FROM DBCODETEST ORDER BY KEY ASC");
        
        // �e�X�g���{
        loader.loadCodeList();
        
        // ����
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // ���P�[��: ja
        List<CodeBean> codeList = result.get(new Locale("ja"));
        assertEquals(3, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        // ���P�[��: en
        codeList = result.get(new Locale("en"));
        assertEquals(2, codeList.size());
        
        assertEquals("004", codeList.get(0).getId());
        assertEquals("val_4", codeList.get(0).getName());
        
        assertEquals("005", codeList.get(1).getId());
        assertEquals("val_5", codeList.get(1).getName());
        
        // ���P�[��: en_GB
        codeList = result.get(new Locale("en", "GB"));
        assertEquals(2, codeList.size());
        
        assertEquals("006", codeList.get(0).getId());
        assertEquals("val_6", codeList.get(0).getName());
        
        assertEquals("007", codeList.get(1).getId());
        assertEquals("val_7", codeList.get(1).getName());
        
        // ���P�[��: en_US
        codeList = result.get(new Locale("en", "US"));
        assertEquals(2, codeList.size());
        
        assertEquals("008", codeList.get(0).getId());
        assertEquals("val_8", codeList.get(0).getName());
        
        assertEquals("009", codeList.get(1).getId());
        assertEquals("val_9", codeList.get(1).getName());
        
        // ���P�[��: en_US_us01
        codeList = result.get(new Locale("en", "US", "us01"));
        assertEquals(2, codeList.size());
        
        assertEquals("010", codeList.get(0).getId());
        assertEquals("val_10", codeList.get(0).getName());
        
        assertEquals("011", codeList.get(1).getId());
        assertEquals("val_11", codeList.get(1).getName());
        
        // ���P�[��: en_US_us02
        codeList = result.get(new Locale("en", "US", "us02"));
        assertEquals(2, codeList.size());
        
        assertEquals("012", codeList.get(0).getId());
        assertEquals("val_12", codeList.get(0).getName());
        
        assertEquals("013", codeList.get(1).getId());
        assertEquals("val_13", codeList.get(1).getName());
    }

    /**
     * testLoadCodeList06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InvalidDataAccessApiUsageException<br>
     *                    ���b�Z�[�W�FProperty 'sql' is required<br>
     *         
     * <br>
     * SQL�������݂��Ȃ��ꍇ�AInvalidDataAccessApiUsageException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList06() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", null);

        // �e�X�g���{
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // ����
            assertEquals("Property 'sql' is required", e.getMessage());
            return;
        }
        fail();
    }

    /**
     * testLoadCodeList07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC<br>
     *         (���) sql���ł̎擾����:�������ʂ�0��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:���List<br>
     *         
     * <br>
     * �������ʂ�0���̏ꍇ�A���List���o�^����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList07() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC");

        // �e�X�g���{
        loader.loadCodeList();

        // ����
        assertTrue(loader.localeMap.isEmpty());
    }

    /**
     * testLoadCodeList08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) localeMap<br>
     *         (���) defaultLocale:Locale.JAPANESE;
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '001'<br>
     *         (���) sql���ł̎擾����:�������ʂ�1��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�������ʂ�1���������ݒ肳���B<br>
     *                    codeLists��0�ԖځFid = 001,name = 'val_1'<br>
     *         
     * <br>
     * �������ʂ��P���̏ꍇ�ɐ���ɓo�^����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList08() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '001'");

        // �e�X�g���{
        loader.loadCodeList();

        // ����
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        List<CodeBean> codeList = result.get(Locale.JAPANESE);
        assertEquals(1, result.size());
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
    }

    /**
     * testLoadCodeList09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY <= '3' ORDER BY KEY ASC<br>
     *         (���) sql���ł̎擾����:�������ʂ�3��<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�������ʂ�3���������ݒ肳���B<br>
     *                    codeLists��0�ԖځFid = 1,name = 'abc'<br>
     *                    codeLists��1�ԖځFid = 2,name = 'xyz'<br>
     *                    codeLists��2�ԖځFid = 3,name = '����������'<br>
     *         
     * <br>
     * �������ʂ��R���̏ꍇ�ɐ���ɓo�^����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList09() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY IN ('001', '002' , '003') ORDER BY KEY ASC");

        // �e�X�g���{
        loader.loadCodeList();

        // ����
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        List<CodeBean> codeList = result.get(Locale.JAPANESE);
        assertEquals(3, codeList.size());
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
    }

    /**
     * testLoadCodeList10()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) localeMap<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:SELECT KEY,VALUE FROM NODB<br>
     *         (���) sql���ł̎擾����:�e�[�u�������݂��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:BadSqlGrammarException<br>
     *                    ���b�v���ꂽ��O�FSQLException<br>
     *         
     * <br>
     * SQL���Ŏw�肵���e�[�u�������݂��Ȃ��ꍇBadSqlGrammarException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList10() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM NODB");

        // �e�X�g���{
        try {
            loader.loadCodeList();
        } catch (BadSqlGrammarException e) {
            // ����
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } finally {
            // PostgreSQL�̏ꍇ�ABadSqlGrammarException�����������ꍇ�A
            // ���[���o�b�N���s��Ȃ��ƁA�ȍ~�̃e�X�g�ɉe����^����
        	UTUtil.getConnection().rollback();
        }
        fail();
    }

    /**
     * testLoadCodeList11()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:UncategorizedSQLException<br>
     *         
     * <br>
     * SQL�����󕶎��̏ꍇUncategorizedSQLException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList11() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "");

        // �e�X�g���{
        try {
            loader.loadCodeList();
        } catch (UncategorizedSQLException e) {
            // ����
            // Oracle�̏ꍇ
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } catch (DataIntegrityViolationException e) {
            // ����
            // Postgres�̏ꍇ
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } 
        fail();
    }

    /**
     * testLoadCodeList12()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:UPDATE DBCODETEST SET VALUE = 'test'<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:DataAccessException<br>
     *
     * <br>
     * SQL����SELECT���ł͂Ȃ�UPDATE�������s���ꂽ�ꍇDataAccessException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList12() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "UPDATE DBCODETEST SET VALUE = 'test'");

        // �e�X�g���{
        try {
            loader.loadCodeList();

        } catch (DataAccessException e) {
            // ����
            assertTrue(e.getCause() instanceof SQLException); 
            return;
        }
        fail();
    }

    /**
     * testLoadCodeList13()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) dataSource:not null<br>
     *         (���) sql:aaaaa<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:BadSqlGrammarException<br>
     *                    ���b�v���ꂽ��O�FSQLException<br>
     *         
     * <br>
     * SQL���̕��@���Ԉ���Ă���ꍇ�ABadSqlGrammarException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoadCodeList13() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "aaaaa");

        // �e�X�g���{
        try {
            loader.loadCodeList();
        } catch (BadSqlGrammarException e) {
            // ����
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } finally {
        	// PostgreSQL�̏ꍇ�ABadSqlGrammarException�����������ꍇ�A
            // ���[���o�b�N���s��Ȃ��ƁA�ȍ~�̃e�X�g�ɉe����^����
        	UTUtil.getConnection().rollback();
        }
        fail();
    }
    
    /**
     * testCreateLocale01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = ""
     *         country = ""
     *         variant = ""
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry(null);
        lcb.setVariant(null);
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }
    
    /**
     * testCreateLocale02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = ""
     *         country = ""
     *         variant = "us01"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale02() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry(null);
        lcb.setVariant("us01");
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }
    
    /**
     * testCreateLocale03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = ""
     *         country = "US"
     *         variant = ""
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale03() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry("US");
        lcb.setVariant(null);
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }
    /**
     * testCreateLocale04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = ""
     *         country = "US"
     *         variant = "us01"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale04() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry("US");
        lcb.setVariant("us01");
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }
    /**
     * testCreateLocale05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = "en"
     *         country = ""
     *         variant = ""
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: en
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale05() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry(null);
        lcb.setVariant(null);
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(new Locale("en"), result);
    }
    /**
     * testCreateLocale06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = "en"
     *         country = ""
     *         variant = "us01"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: en
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale06() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry(null);
        lcb.setVariant("us01");
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(new Locale("en"), result);
    }
    /**
     * testCreateLocale07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = "en"
     *         country = "US"
     *         variant = ""
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: en_US
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale07() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry("US");
        lcb.setVariant(null);
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(new Locale("en", "US"), result);
    }
    /**
     * testCreateLocale08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�FLocaleCodeBean
     *         language = "en"
     *         country = "US"
     *         variant = "us01"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: en_US_us01
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale08() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry("US");
        lcb.setVariant("us01");
        
        // �e�X�g���{
        Locale result = loader.createLocale(lcb);
        
        // ����
        assertEquals(new Locale("en", "US", "us01"), result);
    }

    /**
     * testGetDataSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.dataSource:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DataSource:not null<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDataSource01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "dataSource", ds);
        
        // �e�X�g���{�E����
        assertSame(ds, loader.getDataSource());
    }

    /**
     * testSetDataSource01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) dataSource:not null<br>
     *         (���) this.dataSource:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.dataSource:not null<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDataSource01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        
        // �e�X�g���{
        loader.setDataSource(ds);

        // ����
        assertSame(ds, UTUtil.getPrivateField(loader, "dataSource"));
    }

    /**
     * testGetSQL01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) sql:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSql01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql", "abc");
        
        // �e�X�g���{�E����
        assertEquals("abc", loader.getSql());
    }

    /**
     * testSetSql01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sql:"abc"<br>
     *         (���) this.sql:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.sql:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetSql01() throws Exception {
        // �O����
        DBCodeListLoader loader = new DBCodeListLoader();
        
        // �e�X�g���{
        loader.setSql("abc");

        // ����
        assertEquals("abc", UTUtil.getPrivateField(loader, "sql"));
    }

}
