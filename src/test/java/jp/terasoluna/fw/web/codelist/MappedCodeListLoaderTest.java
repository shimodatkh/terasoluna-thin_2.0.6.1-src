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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader} クラス
 * のブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * コードリスト情報の初期化をMapで行う、
 * jp.terasoluna.fw.web.codelist.CodeListLoader実装クラス。
 * <p>
 *
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 */
public class MappedCodeListLoaderTest extends TestCase {

    /**
     * テスト対象。
     */
    private MappedCodeListLoader test = null;

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MappedCodeListLoaderTest.class);
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
        test = new MappedCodeListLoader();
    }

    /**
     * 終了処理を行う。
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     *
     * @param name このテストケースの名前。
     */
    public MappedCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testGetCodeListMap01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) codeListMap:not null<br>
     *
     * <br>
     * 期待値：(戻り値) Map:インスタンス変数のcodeListMap<br>
     *
     * <br>
     * インスタンス変数のcodeListMapが取得できることを確認する。<br>
     * ※正常系1件のみ確認。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeListMap01() throws Exception {
        // 前処理
        Map map = new HashMap();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        Object result = test.getCodeListMap();

        // 判定
        assertSame(map, result);
    }

    /**
     * testSetCodeListMap01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) codeListMap:not null<br>
     *         (状態) codeListMap:null<br>
     *
     * <br>
     * 期待値：(状態変化) codeListMap:not null（引数に指定したMap）<br>
     *
     * <br>
     * 引数に指定したMapがインスタンス変数codeListMapに設定されることを確認する。<br>
     * ※正常系1件のみ確認。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCodeListMap01() throws Exception {
        // 前処理
        UTUtil.setPrivateField(test, "codeListMap", null);

        // テスト実施
        Map<String, String> map = new HashMap<String, String>();
        test.setCodeListMap(map);

        // 判定
        Object result = UTUtil.getPrivateField(test, "codeListMap");
        assertSame(map, result);
    }

    /**
     * testLoad01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) localeMap:not null<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:変化なし<br>
     *
     * <br>
     * インスタンス変数のcodeListsがnot nullの場合、何も行われず処理が終了することを確認する。codeLists変数に変化がないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad01() throws Exception {
        // 前処理
        CodeBean cb = new CodeBean();
        cb.setId("id");
        cb.setName("name");
        List<CodeBean> list = new ArrayList<CodeBean>();
        list.add(cb);
        Map<Locale, List<CodeBean>> localeMap = new HashMap<Locale, List<CodeBean>>();
        localeMap.put(Locale.JAPANESE, list);
        
        test.defaultLocale = Locale.JAPANESE;
        test.localeMap = localeMap;

        // テスト実施
        test.load();

        // 判定
        Map<Locale, List<CodeBean>> result = test.localeMap;
        assertSame(localeMap, result);
        
        List<CodeBean> codeLists = result.get(Locale.JAPANESE);
        assertSame(list, codeLists);
        assertEquals(1, codeLists.size());
        CodeBean codebean = codeLists.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
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
     *         (状態) codeListMap:null<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が0のList<br>
     *
     * <br>
     * codeListMapがnullの場合、codeListsが要素数0のListとして設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad02() throws Exception {
        // 前処理
        test.localeMap = null;
        UTUtil.setPrivateField(test, "codeListMap", null);

        // テスト実施
        test.load();

        // 判定
        Map<Locale, List<CodeBean>> result = test.localeMap;
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad03()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) codeListMap:要素数0のMap<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が0のList<br>
     *
     * <br>
     * codeListMapの要素が空の場合、codeListsが要素数0のListとして設定されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad03() throws Exception {
        // 前処理
        test.localeMap = null;
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        test.load();

        // 判定
        Map<Locale, List<CodeBean>> result = test.localeMap;
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad04()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) codeListMap:要素数1のMap<br>
     *                {"id","name"}<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が1のList<br>
     *                    CodeBean{"id","name"}<br>
     *
     * <br>
     * codeListMapに1件の要素がある場合、そのMapの内容でcodeListsが初期化されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad04() throws Exception {
        // 前処理
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "name");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        test.load();

        // 判定
        Map<Locale, List<CodeBean>> result = test.localeMap;
        List<CodeBean> resutCodeList = result.get(Locale.JAPANESE);
        assertEquals(1, resutCodeList.size());
        CodeBean codebean = resutCodeList.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
    }

    /**
     * testLoad05()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) codeListMap:要素数3のMap<br>
     *                {"id1","name1"},<br>
     *                {"id2","name2"},<br>
     *                {"id3","name3"}<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が1のList<br>
     *                    CodeBean{"id1","name1"},<br>
     *                    CodeBean{"id2","name2"},<br>
     *                    CodeBean{"id3","name3"},<br>
     *
     * <br>
     * codeListMapに複数件の要素がある場合、そのMapの内容でcodeListsが初期化されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad05() throws Exception {
        // 前処理
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        Map<String, String> map = new TreeMap<String, String>();
        map.put("id1", "name1");
        map.put("id2", "name2");
        map.put("id3", "name3");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        test.load();

        // 判定
        Map<Locale, List<CodeBean>> result = test.localeMap;
        List<CodeBean> resutCodeList = result.get(Locale.JAPANESE);
        
        assertEquals(3, resutCodeList.size());

        // 1件目
        CodeBean codebean = resutCodeList.get(0);
        assertEquals("id1", codebean.getId());
        assertEquals("name1", codebean.getName());

        // 2件目
        codebean = resutCodeList.get(1);
        assertEquals("id2", codebean.getId());
        assertEquals("name2", codebean.getName());

        // 3件目
        codebean = resutCodeList.get(2);
        assertEquals("id3", codebean.getId());
        assertEquals("name3", codebean.getName());
    }
    
    /**
     * testLoad06()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) localeMap:null<br>
     *         (状態) codeListMap:要素数3のMap<br>
     *                {"id1","name1"},<br>
     *                {"id2","name2"},<br>
     *                {"id3","name3"}<br>
     *
     * <br>
     * 期待値：(状態変化) codeLists:要素数が1のList<br>
     *                    CodeBean{"id1","name1"},<br>
     *                    CodeBean{"id2","name2"},<br>
     *                    CodeBean{"id3","name3"},<br>
     *
     * <br>
     * codeListMapに複数件の要素がある場合、そのMapの内容でcodeListsが初期化されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLoad06() throws Exception {
        // 前処理
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        Map<String, Map<String, String>> map = 
                        new LinkedHashMap<String, Map<String,String>>();
        Map<String, String> childMap = new LinkedHashMap<String, String>();
        childMap.put("001", "val_1");
        childMap.put("002", "val_2");
        childMap.put("003", "val_3");
        map.put("ja", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("004", "val_4");
        childMap.put("005", "val_5");
        map.put("en", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("006", "val_6");
        childMap.put("007", "val_7");
        map.put("en_GB", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("008", "val_8");
        childMap.put("009", "val_9");
        map.put("en_US", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("010", "val_10");
        childMap.put("011", "val_11");
        map.put("en_US_us01", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("012", "val_12");
        childMap.put("013", "val_13");
        map.put("en_US_us02", childMap);

        UTUtil.setPrivateField(test, "codeListMap", map);

        // テスト実施
        test.load();

        // 判定
        Map<Locale, List<CodeBean>> result = test.localeMap;
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
     * testCreateLocale01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：
     * <br><br>
     * 入力値：""
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // テスト実施
        Locale result = test.createLocale("");
        
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
     * 入力値："__us01"
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // テスト実施
        Locale result = test.createLocale("__us01");
        
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
     * 入力値：_US_
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // テスト実施
        Locale result = test.createLocale("_US_");
        
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
     * 入力値："_US_us01"
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // テスト実施
        Locale result = test.createLocale("_US_us01");
        
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
     * 入力値："en"
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // テスト実施
        Locale result = test.createLocale("en");
        
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
     * 入力値："en__us01"
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;

        // テスト実施
        Locale result = test.createLocale("en__us01");
        
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
     * 入力値："en_US"
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // テスト実施
        Locale result = test.createLocale("en_US");
        
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
     * 入力値："en_US_us01"
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
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // テスト実施
        Locale result = test.createLocale("en_US_us01");
        
        // 判定
        assertEquals(new Locale("en", "US", "us01"), result);
    }
}
