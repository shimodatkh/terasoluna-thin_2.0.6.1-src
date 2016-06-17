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

import jp.terasoluna.fw.exception.SystemException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadInputStream}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ファイルをダウンロードデータとするためのクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadInputStream
 */
public class DownloadInputStreamTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadInputStreamTest.class);
    }

    /**
     * 初期化処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 終了処理を行う。
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ。
     * 
     * @param name
     *            このテストケースの名前。
     */
    public DownloadInputStreamTest(String name) {
        super(name);
    }

    /**
     * testDownloadInputStream01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：G <br>
     * <br>
     * 入力値：(引数) name:null<br>
     * (引数) stream:null<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     * メッセージキー：<br>
     * "errors.no.download.content"<br>
     * ラップした例外：<br>
     * IllegalArgumentException<br>
     * 
     * <br>
     * streamがnullの場合、例外がスローされることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadInputStream01() throws Exception {
        // 前処理

        // テスト実施
        try {
            @SuppressWarnings("unused")
            DownloadInputStream downloadInputStream = new DownloadInputStream(
                    null, null);
            fail("SystemExceptionが発生しませんでした。");
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, e.getCause()
                    .getClass());
        }
    }

    /**
     * testDownloadInputStream02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：C <br>
     * <br>
     * 入力値：(引数) name:null<br>
     * (引数) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * 期待値：(状態変化) name:null<br>
     * (状態変化) stream:ByteArrayInputStream<br>
     * (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     * (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     * 
     * <br>
     * contentType, charsetにデフォルト値が設定されることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadInputStream02() throws Exception {
        // 前処理
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // テスト実施
        DownloadInputStream downloadInputStream = new DownloadInputStream(null,
                byteArrayInputStream);

        // 判定
        assertNull(downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);

    }

    /**
     * testDownloadInputStream03() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) name:" "（空白）<br>
     * (引数) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * 期待値：(状態変化) name:" "（空白）<br>
     * (状態変化) stream:ByteArrayInputStream<br>
     * (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     * (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     * 
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadInputStream03() throws Exception {
        // 前処理
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // テスト実施
        DownloadInputStream downloadInputStream = new DownloadInputStream("  ",
                byteArrayInputStream);

        // 判定
        assertEquals("  ", downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);
    }

    /**
     * testDownloadInputStream04() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：C <br>
     * <br>
     * 入力値：(引数) name:""（空文字）<br>
     * (引数) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * 期待値：(状態変化) name:""（空文字）<br>
     * (状態変化) stream:ByteArrayInputStream<br>
     * (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     * (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     * 
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadInputStream04() throws Exception {
        // 前処理
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // テスト実施
        DownloadInputStream downloadInputStream = new DownloadInputStream("",
                byteArrayInputStream);

        // 判定
        assertEquals("", downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);
    }

    /**
     * testDownloadInputStream05() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) name:"abc"<br>
     * (引数) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * 期待値：(状態変化) name:"abc"<br>
     * (状態変化) stream:ByteArrayInputStream<br>
     * (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     * (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     * 
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadInputStream05() throws Exception {
        // 前処理
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        // テスト実施
        DownloadInputStream downloadInputStream = new DownloadInputStream(
                "abc", byteArrayInputStream);

        // 判定
        assertEquals("abc", downloadInputStream.name);
        assertEquals(byteArrayInputStream, downloadInputStream.stream);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadInputStream.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadInputStream.charset);
    }

    /**
     * testGetLengthOfData01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) stream:ByteArrayInputStream<br>
     * 
     * <br>
     * 期待値：(戻り値) int:-1<br>
     * 
     * <br>
     * fileのサイズが返却されることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetLengthOfData01() throws Exception {
        // 前処理
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        DownloadInputStream downloadInputStream = new DownloadInputStream(null,
                byteArrayInputStream);

        // テスト実施
        int size = downloadInputStream.getLengthOfData();

        // 判定
        assertEquals(-1, size);
    }

    /**
     * testGetStreamInternal01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) file:ByteArrayInputStream<br>
     * 
     * <br>
     * 期待値：(戻り値) InputStream:ByteArrayInputStream<br>
     * 
     * <br>
     * streamが返却されることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetStreamInternal01() throws Exception {
        // 前処理
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                new byte[0]);

        DownloadInputStream downloadInputStream = new DownloadInputStream(null,
                byteArrayInputStream);

        // テスト実施
        InputStream inputStream = downloadInputStream.getStreamInternal();

        // 判定
        assertNotNull(inputStream);
        assertEquals(ByteArrayInputStream.class, inputStream.getClass());
    }

}
