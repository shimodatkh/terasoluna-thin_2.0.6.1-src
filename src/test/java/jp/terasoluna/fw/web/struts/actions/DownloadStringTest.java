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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadString} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Stringをダウンロードデータとするためのクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadString
 */
public class DownloadStringTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadStringTest.class);
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
        LogUTUtil.flush();
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
    public DownloadStringTest(String name) {
        super(name);
    }

    /**
     * testDownloadString01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) value:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.no.download.content"<br>
     *                    ラップした例外：<br>
     *                    IllegalArgumentException<br>
     *         
     * <br>
     * valueがnullの場合、例外がスローされることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadString01() throws Exception {
        // 前処理

        // テスト実施
        try {
            @SuppressWarnings("unused")
            DownloadString downloadString = new DownloadString(null, null);
            fail("SystemExceptionが発生しませんでした。");
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, e.getCause()
                    .getClass());
        }
    }

    /**
     * testDownloadString02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) value:"abc"<br>
     *         
     * <br>
     * 期待値：(状態変化) name:null<br>
     *         (状態変化) value:"abc"<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * contentType, charsetにデフォルト値が設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadString02() throws Exception {
        // 前処理

        // テスト実施
        DownloadString downloadString = new DownloadString(null, "abc");

        // 判定
        assertNull(downloadString.name);
        assertEquals("abc", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testDownloadString03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"  "（空白）<br>
     *         (引数) value:"  "（空白）<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"  "（空白）<br>
     *         (状態変化) value:"  "（空白）<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadString03() throws Exception {
        // 前処理

        // テスト実施
        DownloadString downloadString = new DownloadString("  ", "  ");

        // 判定
        assertEquals("  ", downloadString.name);
        assertEquals("  ", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testDownloadString04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:""（空文字）<br>
     *         (引数) value:""（空文字）<br>
     *         
     * <br>
     * 期待値：(状態変化) name:""（空文字）<br>
     *         (状態変化) value:""（空文字）<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadString04() throws Exception {
        // 前処理

        // テスト実施
        DownloadString downloadString = new DownloadString("", "");

        // 判定
        assertEquals("", downloadString.name);
        assertEquals("", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testDownloadString05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *         (引数) value:"abc"<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"abc"<br>
     *         (状態変化) value:"abc"<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadString05() throws Exception {
        // 前処理

        // テスト実施
        DownloadString downloadString = new DownloadString("abc", "abc");

        // 判定
        assertEquals("abc", downloadString.name);
        assertEquals("abc", downloadString.value);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadString.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadString.charset);
    }

    /**
     * testGetLengthOfData01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) value:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) int:"abc"#getBytes#lengthの値<br>
     *         
     * <br>
     * valueのサイズが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLengthOfData01() throws Exception {
        // 前処理
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";

        // テスト実施
        int size = downloadString.getLengthOfData();

        // 判定
        assertEquals("abc".getBytes().length, size);
    }

    /**
     * testGetContent01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) value:"abc"<br>
     *         (状態) charset:"NOT-EXIST"<br>
     *         
     * <br>
     * 期待値：(戻り値) byte[]:"abc"#getBytesの値<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ：<br>
     *                    "Charset is not valid."<br>
     *                    例外:<br>
     *                    UnsupportedEncodingException<br>
     *         
     * <br>
     * charsetが存在しない場合にログが出力されることと、<br>
     * valueのbyte配列が返却されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetContent01() throws Exception {
        // 前処理
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";
        downloadString.charset = "NOT-EXIST";

        // テスト実施
        byte[] byteBs = downloadString.getContent();

        // 判定
        assertEquals("abc".getBytes().length, byteBs.length);
        assertEquals("abc".getBytes()[0], byteBs[0]);
        assertEquals("abc".getBytes()[1], byteBs[1]);
        assertEquals("abc".getBytes()[2], byteBs[2]);

        assertTrue(LogUTUtil.checkWarn("Charset is not valid.",
                new UnsupportedEncodingException()));
    }

    /**
     * testGetContent02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) value:"abc"<br>
     *         (状態) charset:"UTF-8"<br>
     *         
     * <br>
     * 期待値：(戻り値) byte[]:"abc"#getBytes("UTF-8")の値<br>
     *         
     * <br>
     * valueのbyte配列が返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetContent02() throws Exception {
        // 前処理
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";
        downloadString.charset = "UTF-8";

        // テスト実施
        byte[] byteBs = downloadString.getContent();

        // 判定
        assertEquals("abc".getBytes("UTF-8").length, byteBs.length);
        assertEquals("abc".getBytes("UTF-8")[0], byteBs[0]);
        assertEquals("abc".getBytes("UTF-8")[1], byteBs[1]);
        assertEquals("abc".getBytes("UTF-8")[2], byteBs[2]);
    }

    /**
     * testGetStreamInternal01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) value:"abc"<br>
     *         
     * <br>
     * 期待値：(戻り値) InputStream:ByteArrayInputStream<br>
     *         
     * <br>
     * valueがラップされたByteArrayInputStreamが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetStreamInternal01() throws Exception {
        // 前処理
        DownloadString downloadString = new DownloadString("", "");
        downloadString.value = "abc";

        // テスト実施
        InputStream stream = downloadString.getStreamInternal();

        // 判定
        assertNotNull(stream);
        assertEquals(ByteArrayInputStream.class, stream.getClass());

        assertEquals("abc".getBytes()[0], stream.read());
        assertEquals("abc".getBytes()[1], stream.read());
        assertEquals("abc".getBytes()[2], stream.read());
        assertEquals(-1, stream.read());
    }

}
