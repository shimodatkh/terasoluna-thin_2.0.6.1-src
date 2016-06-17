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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.AbstractMultilingualCodeListLoader} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * <br>
 * ・前提条件<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.AbstractMultilingualCodeListLoader
 */
public class AbstractMultilingualCodeListLoaderTest extends TestCase {
    
    private static Map<Locale, List<CodeBean>> CODE_LIST01;
    static {
        CODE_LIST01 = new HashMap<Locale, List<CodeBean>>();
        
        List<CodeBean> list;
        
        CodeBean b;
        // ja
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("ja_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("ja_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("ja_3");
        list.add(b);
        
        CODE_LIST01.put(Locale.JAPANESE, list);
        
        // en
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("en_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("en_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("en_3");
        list.add(b);
        
        CODE_LIST01.put(Locale.ENGLISH, list);
        
        // en_GB
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("en_GB_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("en_GB_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("en_GB_3");
        list.add(b);
        
        CODE_LIST01.put(Locale.UK, list);
        
        // en_GB_gb01
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("en_GB_gb01_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("en_GB_gb01_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("en_GB_gb01_3");
        list.add(b);
        
        CODE_LIST01.put(new Locale("en", "GB", "gb01"), list);
        
        // de_AT_at01
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("de_AT_at01_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("de_AT_at01_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("de_AT_at01_3");
        list.add(b);
        
        CODE_LIST01.put(new Locale("de", "AT", "at01"), list);
    }
    
    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(
                AbstractMultilingualCodeListLoaderTest.class);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * testDefaultLocale01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：なし<br>
     *         
     * <br>
     * 期待値：VMの言語のみのデフォルトロケール
     *         
     * <br>
     * デフォルトロケールを設定しない場合はVMの言語のみの
     * デフォルトロケールとなることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDefaultLocale01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        assertEquals(new Locale(Locale.getDefault().getLanguage()), 
                loader.defaultLocale);
    }
    
    /**
     * testSetDefaultLocale01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：new Locale("ja", "JP", "tokyo")<br>
     *         
     * <br>
     * 期待値：入力値と同じロケール
     *         
     * <br>
     * 引数で指定したロケールが設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetDefaultLocale01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        Locale locale = new Locale("ja", "JP", "tokyo");
        loader.setDefaultLocale(locale);
        assertEquals(locale, loader.defaultLocale);
    }
    
    /**
     * testSetDefaultLocale01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：Locale.getDefault()<br>
     *         
     * <br>
     * 期待値：0件
     *         
     * <br>
     * 国際化対応コードリスト情報がnullの場合、0件のコードリストを取得すること
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateCodeBeans01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = null;
        CodeBean[] codeList = loader.createCodeBeans(Locale.getDefault());
        
        assertEquals(codeList.length, 0);
    }
    
    /**
     * testCreateCodeBeans02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：Locale.getDefault()<br>
     *         
     * <br>
     * 期待値：0件
     *         
     * <br>
     * 国際化対応コードリスト情報に存在しないロケールのコードリストを指定した場合、
     * 0件のコードリストを取得することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateCodeBeans02() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = loader.createCodeBeans(Locale.CHINESE);
        
        assertEquals(codeList.length, 0);
    }
    
    /**
     * testCreateCodeBeans03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：new Locale("en" , "GB", "gb01")<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'en_GB_gb01_XX'であること
     *         
     * <br>
     * 以下の条件でコードリストを取得できることを確認する。<br>
     * ・引数のロケールにバリアントまで指定。<br>
     * ・CODE_LIST01にバリアントまで一致するコードリスト情報が存在する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateCodeBeans03() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(new Locale("en" , "GB", "gb01"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_GB_gb01_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：new Locale("en" , "GB", "gb02")<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'en_GB_XX'であること
     *         
     * <br>
     * 以下の条件でコードリストを取得できることを確認する。<br>
     * ・引数のロケールにバリアントまで指定。<br>
     * ・CODE_LIST01にバリアントまで一致するコードリスト情報が存在しない。
     * ・CODE_LIST01に国コードまで一致するコードリスト情報が存在する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateCodeBeans04() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(new Locale("en" , "GB", "gb02"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_GB_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：new Locale("en" , "US", "us01")<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'en_XX'であること
     *         
     * <br>
     * 以下の条件でコードリストを取得できることを確認する。<br>
     * ・引数のロケールにバリアントまで指定。<br>
     * ・CODE_LIST01に国コード、バリアントまで一致するコードリスト情報が存在しない。
     * ・CODE_LIST01に言語コードが一致するコードリスト情報が存在する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateCodeBeans05() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(new Locale("en" , "US", "us01"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：null<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'ja_x'であること
     *         
     * <br>
     * 引数がnullの場合、デフォルトロケールのコードリストを取得することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateCodeBeans06() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.defaultLocale = Locale.JAPANESE;
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(null);
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("ja_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans07()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：null<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'ja_x'であること
     *         
     * <br>
     * デフォルトロケールがnull且つ引数がnullの場合、
     * IllegalStateExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateCodeBeans07() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.defaultLocale = null;
        loader.localeMap = CODE_LIST01;
        
        try {
            loader.createCodeBeans(null);
        } catch (IllegalStateException e) {
            assertTrue(true);
            return;
        }
        
        fail();
    }
    
    /**
     * testGetCodeBeansByLocale01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：new Locale("en" , "GB", "gb01")<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'en_GB_gb01_XX'であること
     *         
     * <br>
     * 以下の条件でコードリストを取得できることを確認する。<br>
     * ・CODE_LIST01にロケールに一致するコードリスト情報が存在する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeansByLocale01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = Locale.JAPANESE;
        CodeBean[] codeList = 
            loader.getCodeBeans(new Locale("en" , "GB", "gb01"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_GB_gb01_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testGetCodeBeansByLocale02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：Locale.CHINESE<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'ja_XX'であること
     *         
     * <br>
     * ロケールに一致するコードリストが存在しない場合、デフォルトロケールの
     * コードリストを取得すること確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeansByLocale02() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = Locale.JAPANESE;
        CodeBean[] codeList = 
            loader.getCodeBeans(Locale.CHINESE);
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("ja_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testGetCodeBeansByLocale03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：new Locale("de", "AT", "at01")<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'de_AT_at01_XX'であること
     *         
     * <br>
     * ロケールに一致するコードリストが存在しない場合、デフォルトロケールの
     * コードリストを取得すること確認する。
     * デフォルトロケールの方が下位のロケールの場合。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeansByLocale03() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = new Locale("de", "AT", "at01");
        CodeBean[] codeList = 
            loader.getCodeBeans(new Locale("de"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("de_AT_at01_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testGetCodeBeansByLocale04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：new Locale(de", "AT", "XX")<br>
     *         
     * <br>
     * 期待値： 0件。
     *         
     * <br>
     * デフォルトロケールにもコードリストが定義されていない場合
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeansByLocale04() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = new Locale("de");
        CodeBean[] codeList = 
            loader.getCodeBeans(new Locale("de", "AT", "XX"));
        
        assertEquals(0, codeList.length);
    }
    
    
    /**
     * testGetCodeBeans01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：なし<br>
     *         
     * <br>
     * 期待値： 3件。取得したコードリストのコード値が'ja_XX'であること
     *         
     * <br>
     * ロケールに一致するコードリストが存在しない場合、デフォルトロケールの
     * コードリストを取得すること確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCodeBeans01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = Locale.JAPAN;
        CodeBean[] codeList = 
            loader.getCodeBeans();

        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("ja_" + (i + 1), b.getName());
        }
    }
}
