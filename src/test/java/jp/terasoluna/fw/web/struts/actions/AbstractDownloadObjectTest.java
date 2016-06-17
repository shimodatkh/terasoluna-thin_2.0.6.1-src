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

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.AbstractDownloadObject} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ダウンロード内容を保持する抽象クラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.AbstractDownloadObject
 */
public class AbstractDownloadObjectTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractDownloadObjectTest.class);
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
    public AbstractDownloadObjectTest(String name) {
        super(name);
    }

    /**
     * testAbstractDownloadObject01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:null<br>
     *        (引数) contentType:null<br>
     *        (引数) charset:null<br>
     *         
     * <br>
     * 期待値：(状態変化) name:null<br>
     *        (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *        (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * contentType, charsetがnullの場合、デフォルト値が設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAbstractDownloadObject01() throws Exception {
        // 前処理

        // テスト実施
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // 判定
        assertNull(object.name);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                object.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, object.charset);
    }

    /**
     * testAbstractDownloadObject02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"  "（空白）<br>
     *        (引数) contentType:"  "（空白）<br>
     *        (引数) charset:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"  "（空白）<br>
     *        (状態変化) contentType:"  "（空白）<br>
     *        (状態変化) charset:"  "（空白）<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAbstractDownloadObject02() throws Exception {
        // 前処理

        // テスト実施
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01("  ",
                "  ", "  ");

        // 判定
        assertEquals("  ", object.name);
        assertEquals("  ", object.contentType);
        assertEquals("  ", object.charset);
    }

    /**
     * testAbstractDownloadObject03()
     * <br><br>
     * 
     *(正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:""（空文字）<br>
     *        (引数) contentType:""（空文字）<br>
     *        (引数) charset:""（空文字）<br>
     *         
     * <br>
     * 期待値：(状態変化) name:""（空文字）<br>
     *        (状態変化) contentType:""（空文字）<br>
     *        (状態変化) charset:""（空文字）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAbstractDownloadObject03() throws Exception {
        // 前処理

        AbstractDownloadObject object = new AbstractDownloadObjectImpl01("",
                "", "");

        // 判定
        assertEquals("", object.name);
        assertEquals("", object.contentType);
        assertEquals("", object.charset);
    }

    /**
     * testAbstractDownloadObject04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *        (引数) contentType:"abc"<br>
     *        (引数) charset:"abc"<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"abc"<br>
     *        (状態変化) contentType:"abc"<br>
     *        (状態変化) charset:"abc"<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAbstractDownloadObject04() throws Exception {
        // 前処理

        AbstractDownloadObject object = new AbstractDownloadObjectImpl01("abc",
                "abc", "abc");

        // 判定
        assertEquals("abc", object.name);
        assertEquals("abc", object.contentType);
        assertEquals("abc", object.charset);
    }

    /**
     * testSetName01()
     * <br><br>
     * 
     *(正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         
     * <br>
     * 期待値：(状態変化) name:null<br>
     *         
     * <br>
     * nameがnullの場合、nullが設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetName01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setName(null);

        // 判定
        assertNull(object.name);
    }

    /**
     * testSetName02()
     * <br><br>
     * 
     *(正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"  "（空白）<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetName02() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setName("  ");

        // 判定
        assertEquals("  ", object.name);
    }

    /**
     * testSetName03()
     * <br><br>
     * 
     *(正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:""（空文字）<br>
     *         
     * <br>
     * 期待値：(状態変化) name:""（空文字）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetName03() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setName("");

        // 判定
        assertEquals("", object.name);
    }

    /**
     * testSetName04()
     * <br><br>
     * 
     *(正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"abc"<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetName04() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setName("abc");

        // 判定
        assertEquals("abc", object.name);
    }

    /**
     * testGetName01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) name:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * nameがnullの場合、nullが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetName01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = null;

        // テスト実施
        String str = object.getName();

        // 判定
        assertNull(str);
    }

    /**
     * testGetName02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) name:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"  "（空白）<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetName02() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = "  ";

        // テスト実施
        String str = object.getName();

        // 判定
        assertEquals("  ", str);
    }

    /**
     * testGetName03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) name:""（空文字）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""（空文字）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetName03() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = "";

        // テスト実施
        String str = object.getName();

        // 判定
        assertEquals("", str);
    }

    /**
     * testGetName04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) name:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetName04() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.name = "abc";

        // テスト実施
        String str = object.getName();

        // 判定
        assertEquals("abc", str);
    }

    /**
     * testSetContentType01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) contentType:null<br>
     *         
     * <br>
     * 期待値：(状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         
     * <br>
     * contentTypeがnullの場合、デフォルト値が設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetContentType01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setContentType(null);

        // 判定
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                object.contentType);
    }

    /**
     * testSetContentType02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) contentType:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(状態変化) contentType:"  "（空白）<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetContentType02() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setContentType("  ");

        // 判定
        assertEquals("  ", object.contentType);
    }

    /**
     * testSetContentType03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) contentType:""（空文字）<br>
     *         
     * <br>
     * 期待値：(状態変化) contentType:""（空文字）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetContentType03() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setContentType("");

        // 判定
        assertEquals("", object.contentType);
    }

    /**
     * testSetContentType04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) contentType:"abc"<br>
     *         
     * <br>
     * 期待値：(状態変化) contentType:"abc"<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetContentType04() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setContentType("abc");

        // 判定
        assertEquals("abc", object.contentType);
    }

    /**
     * testGetContentType01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) contentType:null<br>
     *         
     * <br>
     * 期待値：(戻り値) contentType:null<br>
     *         
     * <br>
     * 変数がnullの場合、nullが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetContentType01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = null;

        // テスト実施
        String str = object.getContentType();

        // 判定
        assertNull(str);
    }

    /**
     * testGetContentType02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) contentType:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"  "（空白）<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetContentType02() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = "  ";

        // テスト実施
        String str = object.getContentType();

        // 判定
        assertEquals("  ", str);
    }

    /**
     * testGetContentType03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) contentType:""（空文字）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""（空文字）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetContentType03() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = "";

        // テスト実施
        String str = object.getContentType();

        // 判定
        assertEquals("", str);
    }

    /**
     * testGetContentType04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) contentType:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetContentType04() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.contentType = "abc";

        // テスト実施
        String str = object.getContentType();

        // 判定
        assertEquals("abc", str);
    }

    /**
     * testSetCharset01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) charset:null<br>
     *         
     * <br>
     * 期待値：(状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * charsetがnullの場合、デフォルト値が設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCharset01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setCharset(null);

        // 判定
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, object.charset);
    }

    /**
     * testSetCharset02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) charset:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(状態変化) charset:"  "（空白）<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCharset02() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setCharset("  ");

        // 判定
        assertEquals("  ", object.charset);
    }

    /**
     * testSetCharset03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) charset:""（空文字）<br>
     *         
     * <br>
     * 期待値：(状態変化) charset:""（空文字）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCharset03() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setCharset("");

        // 判定
        assertEquals("", object.charset);
    }

    /**
     * testSetCharset04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) charset:"abc"<br>
     *         
     * <br>
     * 期待値：(状態変化) charset:"abc"<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSetCharset04() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.setCharset("abc");

        // 判定
        assertEquals("abc", object.charset);
    }

    /**
     * testGetCharset01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) charset:null<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * 変数がnullの場合、nullが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCharset01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = null;

        // テスト実施
        String str = object.getCharset();

        // 判定
        assertNull(str);
    }

    /**
     * testGetCharset02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) charset:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"  "（空白）<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCharset02() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = "  ";

        // テスト実施
        String str = object.getCharset();

        // 判定
        assertEquals("  ", str);
    }

    /**
     * testGetCharset03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(状態) charset:""（空文字）<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""（空文字）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCharset03() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = "";

        // テスト実施
        String str = object.getCharset();

        // 判定
        assertEquals("", str);
    }

    /**
     * testGetCharset04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) charset:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetCharset04() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);
        object.charset = "abc";

        // テスト実施
        String str = object.getCharset();

        // 判定
        assertEquals("abc", str);
    }

    /**
     * testAddHeader01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:null<br>
     *        (引数) value:null<br>
     *        (状態) additionalHeaders:HashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) additionalHeaders:Map{[null, List[null]]}<br>
     *         
     * <br>
     * 変数がnullの場合、値が格納されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddHeader01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.addHeader(null, null);

        // 判定
        assertNotNull(object.additionalHeaders);
        List<String> list = object.additionalHeaders.get(null);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertNull(list.get(0));
    }

    /**
     * testAddHeader02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"  "（空白）<br>
     *        (引数) value:"  "（空白）<br>
     *        (状態) additionalHeaders:HashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) additionalHeaders:Map{["  "（空白）, List["  "（空白）]]}<br>
     *         
     * <br>
     * 変数が空白の場合、値が格納されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddHeader02() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.addHeader("  ", "  ");

        // 判定
        assertNotNull(object.additionalHeaders);
        List list = object.additionalHeaders.get("  ");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("  ", list.get(0));
    }

    /**
     * testAddHeader03()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:""（空文字）<br>
     *        (引数) value:""（空文字）<br>
     *        (状態) additionalHeaders:HashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) additionalHeaders:Map{[""（空文字）, List[""（空文字）]]}<br>
     *         
     * <br>
     * 変数が空文字の場合、値が格納されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddHeader03() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.addHeader("", "");

        // 判定
        assertNotNull(object.additionalHeaders);
        List list = object.additionalHeaders.get("");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("", list.get(0));
    }

    /**
     * testAddHeader04()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *        (引数) value:"abc"<br>
     *        (状態) additionalHeaders:HashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) additionalHeaders:Map{["abc", List["abc"]]}<br>
     *         
     * <br>
     * 変数が文字列の場合、値が格納されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddHeader04() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        object.addHeader("abc", "abc");

        // 判定
        assertNotNull(object.additionalHeaders);
        List list = object.additionalHeaders.get("abc");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("abc", list.get(0));
    }

    /**
     * testAddHeader05()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *        (引数) value:"def"<br>
     *        (状態) additionalHeaders:Map{["abc", List["abc"]]}<br>
     *         
     * <br>
     * 期待値：(状態変化) additionalHeaders:Map{["abc", List["abc", "def"]]}<br>
     *         
     * <br>
     * 既にキーが存在する場合で、同じキー変数が文字列の場合、値が格納されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testAddHeader05() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list1 = new ArrayList<String>();
        list1.add("abc");
        map.put("abc", list1);
        object.additionalHeaders = map;

        // テスト実施
        object.addHeader("abc", "def");

        // 判定
        assertNotNull(object.additionalHeaders);
        List list2 = object.additionalHeaders.get("abc");
        assertNotNull(list2);
        assertEquals(2, list2.size());
        assertEquals("abc", list2.get(0));
        assertEquals("def", list2.get(1));
    }

    /**
     * testGetAdditionalHeaders01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) additionalHeaders:HashMap<br>
     *         
     * <br>
     * 期待値：(状態変化) additionalHeaders:Map<br>
     *         
     * <br>
     * 変数が取得できることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetAdditionalHeaders01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        object.additionalHeaders = hashMap;

        // テスト実施

        Map map = object.getAdditionalHeaders();

        // 判定
        assertSame(hashMap, map);
    }

    /**
     * testGetStream01()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) getStreamInternalの戻り値:ByteArrayInputStream<br>
     *         
     * <br>
     * 期待値：(戻り値) InputStream:BufferedInputStream(ByteArrayInputStream)<br>
     *         
     * <br>
     * getStreamInternalの戻り値がBufferedInputStreamにラップされて返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetStream01() throws Exception {
        // 前処理
        AbstractDownloadObject object = new AbstractDownloadObjectImpl01(null,
                null, null);

        // テスト実施
        InputStream stream = object.getStream();

        // 判定
        assertEquals(BufferedInputStream.class, stream.getClass());
    }

}
