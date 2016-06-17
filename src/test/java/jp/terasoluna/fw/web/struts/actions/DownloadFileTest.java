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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import jp.terasoluna.fw.exception.SystemException;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadFile} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ファイルをダウンロードデータとするためのクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadFile
 */
public class DownloadFileTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadFileTest.class);
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
    public DownloadFileTest(String name) {
        super(name);
    }

    /**
     * testDownloadFileFile01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) File:null<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー：<br>
     *                    "errors.no.download.content"<br>
     *                    ラップした例外：<br>
     *                    IllegalArgumentException<br>
     *         
     * <br>
     * fileがnullの場合、例外がスローされることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadFileFile01() throws Exception {
        // 前処理

        // テスト実施
        try {
            @SuppressWarnings("unused")
            DownloadFile downloadFile = new DownloadFile(null);
            fail("SystemExceptionが発生しませんでした");
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, 
                    e.getCause().getClass());
        }
    }

    /**
     * testDownloadFileFile02()
     * <br><br>
     * 
     * (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) File:File()<br>
     *         
     * <br>
     * 期待値：(状態変化) name:File#getNameの値<br>
     *         (状態変化) file:File<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * name, contentType, charsetにデフォルト値が設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadFileFile02() throws Exception {
        // 前処理
        File file = new File("");

        // テスト実施
        DownloadFile downloadFile = new DownloadFile(file);
        
        // 判定
        assertEquals(file.getName(), downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) file:null<br>
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
    public void testDownloadFileStringFile01() throws Exception {
        // 前処理

        // テスト実施
        try {
            @SuppressWarnings("unused")
            DownloadFile downloadFile = new DownloadFile(null, null);
            fail("SystemExceptionが発生しませんでした。");
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.no.download.content", e.getErrorCode());
            assertEquals(IllegalArgumentException.class, 
                    e.getCause().getClass());
        }

    }

    /**
     * testDownloadFileStringFile02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:null<br>
     *         (引数) file:File#createTempFile<br>
     *         
     * <br>
     * 期待値：(状態変化) name:File#getNameの値<br>
     *         (状態変化) file:File<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * contentType, charsetにデフォルト値が設定されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadFileStringFile02() throws Exception {
        // 前処理
        File file = File.createTempFile("abc", "def");

        // テスト実施
        DownloadFile downloadFile = new DownloadFile(null, file);

        // 判定
        assertEquals(file.getName(), downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"  "（空白）<br>
     *         (引数) file:File#createTempFile<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"  "（空白）<br>
     *         (状態変化) file:File<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が空白の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadFileStringFile03() throws Exception {
        // 前処理
        File file = File.createTempFile("abc", "def");

        // テスト実施
        DownloadFile downloadFile = new DownloadFile("  ", file);

        // 判定
        assertEquals("  ", downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) name:""（空文字）<br>
     *         (引数) file:File#createTempFile<br>
     *         
     * <br>
     * 期待値：(状態変化) name:File#getNameの値<br>
     *         (状態変化) file:File<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadFileStringFile04() throws Exception {
        // 前処理
        File file = File.createTempFile("abc", "def");

        // テスト実施
        DownloadFile downloadFile = new DownloadFile("", file);

        // 判定
        assertEquals(file.getName(), downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testDownloadFileStringFile05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) name:"abc"<br>
     *         (引数) file:File#createTempFile<br>
     *         
     * <br>
     * 期待値：(状態変化) name:"abc"<br>
     *         (状態変化) file:File<br>
     *         (状態変化) contentType:DEFAULT_CONTENT_TYPEが設定されていること<br>
     *         (状態変化) charset:DEFAULT_CHARSETが設定されていること<br>
     *         
     * <br>
     * 変数が文字列の場合、そのまま設定されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDownloadFileStringFile05() throws Exception {
        // 前処理
        File file = File.createTempFile("abc", "def");

        // テスト実施
        DownloadFile downloadFile = new DownloadFile("abc", file);

        // 判定
        assertEquals("abc", downloadFile.name);
        assertEquals(file, downloadFile.file);
        assertEquals(AbstractDownloadObject.DEFAULT_CONTENT_TYPE, 
                downloadFile.contentType);
        assertEquals(AbstractDownloadObject.DEFAULT_CHARSET, 
                downloadFile.charset);
    }

    /**
     * testGetLengthOfData01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) file:File<br>
     *                (サイズが10であること)<br>
     *         
     * <br>
     * 期待値：(戻り値) int:10<br>
     *         
     * <br>
     * fileのサイズが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLengthOfData01() throws Exception {
        // 前処理
        // サイズが10のファイル作成
        File file = File.createTempFile("abc", "def");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(new byte[10]);
        outputStream.close();
        
        DownloadFile downloadFile = new DownloadFile("abc", file);

        // テスト実施
        int size = downloadFile.getLengthOfData();

        // 判定
        assertEquals(file.length(), size);
    }

    /**
     * testGetStreamInternal01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) file:File#createTempFile<br>
     *         
     * <br>
     * 期待値：(戻り値) InputStream:FileInputStream<br>
     *         
     * <br>
     * fileをラップしたFileInputStreamが返却されることを確認するテスト。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetStreamInternal01() throws Exception {
        // 前処理
        // サイズが5のファイル作成
        File file = File.createTempFile("abc", "def");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(new byte[5]);
        outputStream.close();
        
        DownloadFile downloadFile = new DownloadFile("abc", file);

        // テスト実施
        InputStream stream = downloadFile.getStreamInternal();

        // 判定
        // FileInputStreamが返却されているか確認
        assertNotNull(stream);
        assertEquals(FileInputStream.class, stream.getClass());
        // fileがラップされているかどうか確認
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertFalse(stream.read() == -1);
        assertTrue(stream.read() == -1);
    }

}
