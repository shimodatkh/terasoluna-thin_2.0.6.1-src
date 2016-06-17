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
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadByteArray} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * バイト配列をダウンロードデータとするためのクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadByteArray
 */
public class DownloadByteArrayTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadByteArrayTest.class);
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
    public DownloadByteArrayTest(String name) {
        super(name);
    }

    /**
     * testDownloadByteArray01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) byteArray:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.no.download.content"<br>
     *                    ラップした例外：<br>
     *                    IllegalArgumentException<br>
     *         
     * <br>
     * byteArrayがnullの場合、例外がスローされることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadByteArray01() throws Exception {
        // 前処理

        // テスト実施
        try {
            @SuppressWarnings("unused")
            DownloadByteArray downloadByteArray = new DownloadByteArray(null,
                    null);
            fail("SystemExceptionが発生しませんでした");
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class.getName(), e.getCause()
                    .getClass().getName());
        }
    }

    /**
     * testDownloadByteArray02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) byteArray:byte[]<br>
     *         
     * <br>
     * 期待値：(状態変化) name:null<br>
     *         (状態変化) byteArray:byte[]<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_FILEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * contentType, charsetにデフォルト値が設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadByteArray02() throws Exception {
        // 前処理
        byte[] byteBs = new byte[0];

        // テスト実施
        DownloadByteArray downloadByteArray = new DownloadByteArray(null,
                byteBs);

        // 判定
        assertNull(downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testDownloadByteArray03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:"  "（空白）<br>
     *         (引数) ｂyteArray:byte[]<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"  "（空白）<br>
     *         (状態変化) byteArray:byte[]<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_FILEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadByteArray03() throws Exception {
        // 前処理
        byte[] byteBs = new byte[0];

        // テスト実施
        DownloadByteArray downloadByteArray = new DownloadByteArray("  ",
                byteBs);

        // 判定
        assertEquals("  ", downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testDownloadByteArray04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:""（空文字）<br>
     *         (引数) byteArray:byte[]<br>
     *         
     * <br>
     * 期待値：(状態変化) name:""（空文字）<br>
     *         (状態変化) byteArray:byte[]<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_FILEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadByteArray04() throws Exception {
        // 前処理
        byte[] byteBs = new byte[0];

        // テスト実施
        DownloadByteArray downloadByteArray = new DownloadByteArray("", byteBs);

        // 判定
        assertEquals("", downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testDownloadByteArray05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *         (引数) byteArray:byte[1, 2]<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"abc"<br>
     *         (状態変化) byteArray:byte[1, 2]<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_FILEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadByteArray05() throws Exception {
        // 前処理
        byte[] byteBs = new byte[] { 1, 2 };

        // テスト実施
        DownloadByteArray downloadByteArray = new DownloadByteArray("abc",
                byteBs);

        // 判定
        assertEquals("abc", downloadByteArray.name);
        assertEquals(byteBs, downloadByteArray.byteArray);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE,
                downloadByteArray.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET,
                downloadByteArray.charset);
    }

    /**
     * testGetLengthOfData01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) byteArray:byte[1, 2]<br>
     *         
     * <br>
     * 期待値：(戻り値) int:2<br>
     *         
     * <br>
     * byteArrayのサイズが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLengthOfData01() throws Exception {
        // 前処理
        DownloadByteArray downloadByteArray = new DownloadByteArray("",
                new byte[0]);
        downloadByteArray.byteArray = new byte[] { 1, 2 };

        // テスト実施
        int num = downloadByteArray.getLengthOfData();

        // 判定
        assertEquals(2, num);
    }

    /**
     * testGetStreamInternal01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) byteArray:byte[1, 2]<br>
     *         
     * <br>
     * 期待値：(戻り値) InputStream:ByteArrayInputStream(byte[1, 2])<br>
     *         
     * <br>
     * byteArrayをラップしたByteArrayInputStreamが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetStreamInternal01() throws Exception {
        // 前処理
        DownloadByteArray downloadByteArray = new DownloadByteArray("",
                new byte[0]);
        downloadByteArray.byteArray = new byte[] { 1, 2 };

        // テスト実施
        InputStream stream = downloadByteArray.getStreamInternal();

        // 判定
        // ByteArrayInputStreamが返却されているか確認
        assertNotNull(stream);
        assertEquals(ByteArrayInputStream.class.getName(), stream.getClass()
                .getName());

        // ByteArrayInputStreamがbyteArrayをラップしているか確認
        assertEquals(1, stream.read());
        assertEquals(2, stream.read());
        assertEquals(-1, stream.read());
    }

}
