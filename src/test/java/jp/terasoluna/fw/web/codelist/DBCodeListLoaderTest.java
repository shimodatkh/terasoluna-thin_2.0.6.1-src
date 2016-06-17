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
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * コードリスト情報の初期化をデータベースを用いて行うクラス<br>
 * ・前提条件<br>
 * ・utlib.confを用意しDB接続情報を記述すること。<br>
 * ・create_dbcodetest.sqlで設定されるDB情報があらかじめ設定されている必要がある。<br>
 * このファイルの中では以下のようにDBを作成し、テストパターン毎に下記の要素をDBに設定する。<br>
 * DBの名前：DBCODETEST<br>
 * カラム：KEY, VALUE<br>
 * 要素： <br>
 * （１）KEY = '1' , VALUE = 'abc'<br>
 * （２）KEY = '2' , VALUE = 'xyz'<br>
 * （３）KEY = '3' , VALUE = 'あいうえお'
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 */
public class DBCodeListLoaderTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBCodeListLoaderTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        // DBデータ設定       
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
     * 終了処理を行う。
     * 
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        
        // DBデータ削除       
        UTUtil.deleteAll("DBCODETEST");
        
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * 
     * @param name このテストケースの名前。
     */
    public DBCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testLoad01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) localeMap:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行されない。<br>
     *         
     * <br>
     * codeListsが存在する場合は何もせず終了することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad01() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        
        loader.localeMap = new HashMap<Locale, List<CodeBean>>();
        
        // テスト実施
        loader.load();

        // 判定
        assertFalse(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoad02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行される。<br>
     *         
     * <br>
     * codeListsが存在しない場合はloadCodeList()が呼び出されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad02() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        loader.localeMap = null;
        
        // テスト実施
        loader.load();

        // 判定
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) localeMap:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行される。<br>
     *         
     * <br>
     * このメソッドが呼ばれるとloadCodeList()が呼ばれることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReload01() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        loader.localeMap = new HashMap<Locale, List<CodeBean>>();
        
        // テスト実施
        loader.reload();

        // 判定
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testReload02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         
     * <br>
     * 期待値：(状態変化) loadCodeList():実行される。<br>
     *         
     * <br>
     * codeListsがnullの場合でもloadCodeList()が呼ばれることを確認する。（synchronizedをかけずに呼ぶ）
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReload02() throws Exception {
        // 前処理
        DBCodeListLoaderImpl01 loader = new DBCodeListLoaderImpl01();
        loader.localeMap = null;
        
        // テスト実施
        loader.reload();

        // 判定
        assertTrue(loader.isLoadCodeListIsCalled());
    }

    /**
     * testLoadCodeList01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:InvalidDataAccessApiUsageException<br>
     *                    メッセージ：Property 'dataSource' is required<br>
     *         
     * <br>
     * dataSourceがnullの場合InvalidDataAccessApiUsageExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '1' AND KEY = '3'");
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", null);
        
        // テスト実施
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // 判定
            assertEquals("Property 'dataSource' is required", e.getMessage());
            return;
        }
        fail();
    }
   
    /**
     * testLoadCodeList02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 期待値: 入力値：(状態) localeMap:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * 以下のコードリストが設定されること
     * ・ロケール: ja (デフォルトロケール)
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
     * sqlでロケールを取得しない場合、デフォルトロケールにコードリストが
     * 設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList02() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE FROM DBCODETEST ORDER BY KEY ASC");
        
        // テスト実施
        loader.loadCodeList();
        
        // 判定
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // デフォルトロケールのコードリストを参照
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
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE,LANGUAGE 
     *                                    FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * 期待値: 以下のコードリストが設定されること
     * ・ロケール: ja (デフォルトロケール)
     * 001 val_1
     * 002 val_2
     * 003 val_3
     * ・ロケール: en
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
     * sqlでロケールを取得しない場合、デフォルトロケールにコードリストが
     * 設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList03() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE,LANGUAGE FROM DBCODETEST ORDER BY KEY ASC");
        
        // テスト実施
        loader.loadCodeList();
        
        // 判定
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // ロケール: ja
        List<CodeBean> codeList = result.get(new Locale("ja"));
        assertEquals(3, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        // ロケール: en
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
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE,LANGUAGE,COUNTRY
     *                                    FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * 期待値: 以下のコードリストが設定されること
     * ・ロケール: ja (デフォルトロケール)
     * 001 val_1
     * 002 val_2
     * 003 val_3
     * 
     * ・ロケール: en
     * 004 val_4
     * 005 val_5
     * 
     * ・ロケール: en_GB
     * 006 val_6
     * 007 val_7
     * 
     * ・ロケール: en_US
     * 008 val_8
     * 009 val_9
     * 010 val_10
     * 011 val_11
     * 012 val_12
     * 013 val_13         
     * <br>
     * sqlでロケールを取得しない場合、デフォルトロケールにコードリストが
     * 設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList04() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE,LANGUAGE,COUNTRY FROM DBCODETEST ORDER BY KEY ASC");
        
        // テスト実施
        loader.loadCodeList();
        
        // 判定
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // ロケール: ja
        List<CodeBean> codeList = result.get(new Locale("ja"));
        assertEquals(3, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        // ロケール: en
        codeList = result.get(new Locale("en"));
        assertEquals(2, codeList.size());
        
        assertEquals("004", codeList.get(0).getId());
        assertEquals("val_4", codeList.get(0).getName());
        
        assertEquals("005", codeList.get(1).getId());
        assertEquals("val_5", codeList.get(1).getName());
        
        // ロケール: en_GB
        codeList = result.get(new Locale("en", "GB"));
        assertEquals(2, codeList.size());
        
        assertEquals("006", codeList.get(0).getId());
        assertEquals("val_6", codeList.get(0).getName());
        
        assertEquals("007", codeList.get(1).getId());
        assertEquals("val_7", codeList.get(1).getName());
        
        // ロケール: en_US
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
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE,LANGUAGE,COUNTRY,VARIANT
     *                                    FROM DBCODETEST ORDER BY KEY ASC<br>
     *         
     * <br>
     * 期待値: 以下のコードリストが設定されること
     * ・ロケール: ja (デフォルトロケール)
     * 001 val_1
     * 002 val_2
     * 003 val_3
     * 
     * ・ロケール: en
     * 004 val_4
     * 005 val_5
     * 
     * ・ロケール: en_GB
     * 006 val_6
     * 007 val_7
     * 
     * ・ロケール: en_US
     * 008 val_8
     * 009 val_9
     * 
     * ・ロケール: en_US_us01

     * 010 val_10
     * 011 val_11
     * 
     * ・ロケール: en_US_us02
     * 012 val_12
     * 013 val_13         
     * <br>
     * sqlでロケールを取得しない場合、デフォルトロケールにコードリストが
     * 設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList05() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql",
            "SELECT KEY,VALUE,LANGUAGE,COUNTRY,VARIANT FROM DBCODETEST ORDER BY KEY ASC");
        
        // テスト実施
        loader.loadCodeList();
        
        // 判定
        Map<Locale, List<CodeBean>> result = loader.localeMap;
        // ロケール: ja
        List<CodeBean> codeList = result.get(new Locale("ja"));
        assertEquals(3, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        // ロケール: en
        codeList = result.get(new Locale("en"));
        assertEquals(2, codeList.size());
        
        assertEquals("004", codeList.get(0).getId());
        assertEquals("val_4", codeList.get(0).getName());
        
        assertEquals("005", codeList.get(1).getId());
        assertEquals("val_5", codeList.get(1).getName());
        
        // ロケール: en_GB
        codeList = result.get(new Locale("en", "GB"));
        assertEquals(2, codeList.size());
        
        assertEquals("006", codeList.get(0).getId());
        assertEquals("val_6", codeList.get(0).getName());
        
        assertEquals("007", codeList.get(1).getId());
        assertEquals("val_7", codeList.get(1).getName());
        
        // ロケール: en_US
        codeList = result.get(new Locale("en", "US"));
        assertEquals(2, codeList.size());
        
        assertEquals("008", codeList.get(0).getId());
        assertEquals("val_8", codeList.get(0).getName());
        
        assertEquals("009", codeList.get(1).getId());
        assertEquals("val_9", codeList.get(1).getName());
        
        // ロケール: en_US_us01
        codeList = result.get(new Locale("en", "US", "us01"));
        assertEquals(2, codeList.size());
        
        assertEquals("010", codeList.get(0).getId());
        assertEquals("val_10", codeList.get(0).getName());
        
        assertEquals("011", codeList.get(1).getId());
        assertEquals("val_11", codeList.get(1).getName());
        
        // ロケール: en_US_us02
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
     * (異常系)
     * <br>
     * 観点：CG
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:InvalidDataAccessApiUsageException<br>
     *                    メッセージ：Property 'sql' is required<br>
     *         
     * <br>
     * SQL文が存在しない場合、InvalidDataAccessApiUsageExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList06() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", null);

        // テスト実施
        try {
            loader.loadCodeList();
        } catch (InvalidDataAccessApiUsageException e) {
            // 判定
            assertEquals("Property 'sql' is required", e.getMessage());
            return;
        }
        fail();
    }

    /**
     * testLoadCodeList07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC<br>
     *         (状態) sql文での取得結果:検索結果が0件<br>
     *         
     * <br>
     * 期待値：(状態変化) codeLists:空のList<br>
     *         
     * <br>
     * 検索結果が0件の場合、空のListが登録されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList07() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '200' ORDER BY KEY ASC");

        // テスト実施
        loader.loadCodeList();

        // 判定
        assertTrue(loader.localeMap.isEmpty());
    }

    /**
     * testLoadCodeList08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) localeMap<br>
     *         (状態) defaultLocale:Locale.JAPANESE;
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '001'<br>
     *         (状態) sql文での取得結果:検索結果が1件<br>
     *         
     * <br>
     * 期待値：(状態変化) codeLists:検索結果が1件正しく設定される。<br>
     *                    codeListsの0番目：id = 001,name = 'val_1'<br>
     *         
     * <br>
     * 検索結果が１件の場合に正常に登録されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList08() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY = '001'");

        // テスト実施
        loader.loadCodeList();

        // 判定
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
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM DBCODETEST WHERE KEY <= '3' ORDER BY KEY ASC<br>
     *         (状態) sql文での取得結果:検索結果が3件<br>
     *         
     * <br>
     * 期待値：(状態変化) codeLists:検索結果が3件正しく設定される。<br>
     *                    codeListsの0番目：id = 1,name = 'abc'<br>
     *                    codeListsの1番目：id = 2,name = 'xyz'<br>
     *                    codeListsの2番目：id = 3,name = 'あいうえお'<br>
     *         
     * <br>
     * 検索結果が３件の場合に正常に登録されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList09() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM DBCODETEST WHERE KEY IN ('001', '002' , '003') ORDER BY KEY ASC");

        // テスト実施
        loader.loadCodeList();

        // 判定
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
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) localeMap<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:SELECT KEY,VALUE FROM NODB<br>
     *         (状態) sql文での取得結果:テーブルが存在しない<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:BadSqlGrammarException<br>
     *                    ラップされた例外：SQLException<br>
     *         
     * <br>
     * SQL文で指定したテーブルが存在しない場合BadSqlGrammarExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList10() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "SELECT KEY,VALUE FROM NODB");

        // テスト実施
        try {
            loader.loadCodeList();
        } catch (BadSqlGrammarException e) {
            // 判定
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } finally {
            // PostgreSQLの場合、BadSqlGrammarExceptionが発生した場合、
            // ロールバックを行わないと、以降のテストに影響を与える
        	UTUtil.getConnection().rollback();
        }
        fail();
    }

    /**
     * testLoadCodeList11()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:""<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:UncategorizedSQLException<br>
     *         
     * <br>
     * SQL文が空文字の場合UncategorizedSQLExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList11() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "");

        // テスト実施
        try {
            loader.loadCodeList();
        } catch (UncategorizedSQLException e) {
            // 判定
            // Oracleの場合
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } catch (DataIntegrityViolationException e) {
            // 判定
            // Postgresの場合
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } 
        fail();
    }

    /**
     * testLoadCodeList12()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:UPDATE DBCODETEST SET VALUE = 'test'<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:DataAccessException<br>
     *
     * <br>
     * SQL文がSELECT文ではなくUPDATE文が実行された場合DataAccessExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList12() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "UPDATE DBCODETEST SET VALUE = 'test'");

        // テスト実施
        try {
            loader.loadCodeList();

        } catch (DataAccessException e) {
            // 判定
            assertTrue(e.getCause() instanceof SQLException); 
            return;
        }
        fail();
    }

    /**
     * testLoadCodeList13()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) codeLists:null<br>
     *         (状態) dataSource:not null<br>
     *         (状態) sql:aaaaa<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:BadSqlGrammarException<br>
     *                    ラップされた例外：SQLException<br>
     *         
     * <br>
     * SQL文の文法が間違っている場合、BadSqlGrammarExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoadCodeList13() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        loader.localeMap = null;
        UTUtil.setPrivateField(loader, "dataSource", ds);
        UTUtil.setPrivateField(loader, "sql", "aaaaa");

        // テスト実施
        try {
            loader.loadCodeList();
        } catch (BadSqlGrammarException e) {
            // 判定
            assertTrue(e.getCause() instanceof SQLException);
            return;
        } finally {
        	// PostgreSQLの場合、BadSqlGrammarExceptionが発生した場合、
            // ロールバックを行わないと、以降のテストに影響を与える
        	UTUtil.getConnection().rollback();
        }
        fail();
    }
    
    /**
     * testCreateLocale01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = ""
     *         country = ""
     *         variant = ""
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: LOCALE.JAPANESE
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry(null);
        lcb.setVariant(null);
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(Locale.JAPANESE, result);
    }
    
    /**
     * testCreateLocale02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = ""
     *         country = ""
     *         variant = "us01"
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: LOCALE.JAPANESE
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale02() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry(null);
        lcb.setVariant("us01");
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(Locale.JAPANESE, result);
    }
    
    /**
     * testCreateLocale03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = ""
     *         country = "US"
     *         variant = ""
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: LOCALE.JAPANESE
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale03() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry("US");
        lcb.setVariant(null);
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(Locale.JAPANESE, result);
    }
    /**
     * testCreateLocale04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = ""
     *         country = "US"
     *         variant = "us01"
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: LOCALE.JAPANESE
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale04() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage(null);
        lcb.setCountry("US");
        lcb.setVariant("us01");
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(Locale.JAPANESE, result);
    }
    /**
     * testCreateLocale05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = "en"
     *         country = ""
     *         variant = ""
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: en
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale05() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry(null);
        lcb.setVariant(null);
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(new Locale("en"), result);
    }
    /**
     * testCreateLocale06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = "en"
     *         country = ""
     *         variant = "us01"
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: en
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale06() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry(null);
        lcb.setVariant("us01");
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(new Locale("en"), result);
    }
    /**
     * testCreateLocale07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = "en"
     *         country = "US"
     *         variant = ""
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: en_US
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale07() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry("US");
        lcb.setVariant(null);
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(new Locale("en", "US"), result);
    }
    /**
     * testCreateLocale08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：LocaleCodeBean
     *         language = "en"
     *         country = "US"
     *         variant = "us01"
     *         (状態) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * 期待値: en_US_us01
     *         
     * <br>
     * 入力値に対応するロケールを取得することを確認する。
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLocale08() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        loader.localeMap = null;
        loader.defaultLocale = Locale.JAPANESE;
        
        LocaleCodeBean lcb = new LocaleCodeBean();
        lcb.setLanguage("en");
        lcb.setCountry("US");
        lcb.setVariant("us01");
        
        // テスト実施
        Locale result = loader.createLocale(lcb);
        
        // 判定
        assertEquals(new Locale("en", "US", "us01"), result);
    }

    /**
     * testGetDataSource01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) this.dataSource:not null<br>
     *         
     * <br>
     * 期待値：(戻り値) DataSource:not null<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetDataSource01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        UTUtil.setPrivateField(loader, "dataSource", ds);
        
        // テスト実施・判定
        assertSame(ds, loader.getDataSource());
    }

    /**
     * testSetDataSource01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) dataSource:not null<br>
     *         (状態) this.dataSource:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.dataSource:not null<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDataSource01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        DataSource ds = new MockDataSource();
        
        // テスト実施
        loader.setDataSource(ds);

        // 判定
        assertSame(ds, UTUtil.getPrivateField(loader, "dataSource"));
    }

    /**
     * testGetSQL01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) sql:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSql01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        UTUtil.setPrivateField(loader, "sql", "abc");
        
        // テスト実施・判定
        assertEquals("abc", loader.getSql());
    }

    /**
     * testSetSql01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) sql:"abc"<br>
     *         (状態) this.sql:null<br>
     *         
     * <br>
     * 期待値：(状態変化) this.sql:"abc"<br>
     *         
     * <br>
     * ※正常系一件のみテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetSql01() throws Exception {
        // 前処理
        DBCodeListLoader loader = new DBCodeListLoader();
        
        // テスト実施
        loader.setSql("abc");

        // 判定
        assertEquals("abc", UTUtil.getPrivateField(loader, "sql"));
    }

}
