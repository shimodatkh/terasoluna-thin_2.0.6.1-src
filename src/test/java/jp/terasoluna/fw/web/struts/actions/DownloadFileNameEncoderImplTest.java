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

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownloadFileNameEncoderImpl} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ダウンロード時の指定ファイル名をエンコードするクラス。<br>
 * このクラスではInternet Explorerのみに対応している。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.DownloadFileNameEncoderImpl
 */
public class DownloadFileNameEncoderImplTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownloadFileNameEncoderImplTest.class);
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
    public DownloadFileNameEncoderImplTest(String name) {
        super(name);
    }

    /**
     * testEncode01()
     * <br><br>
     * 
     *  (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) original:null<br>
     *         (引数) request:MockHttpServletRequest<br>
     *         (引数) response:MockHttpServletResponse<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         
     * <br>
     * 変数がnullの場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testEncode01() throws Exception {
        // 前処理
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        String str = encoderImpl.encode(null, request, response);

        // 判定
        assertNull(str);
    }

    /**
     * testEncode02()
     * <br><br>
     * 
     *  (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) original:"  "（空白）<br>
     *         (引数) request:MockHttpServletRequest<br>
     *         (引数) response:MockHttpServletResponse<br>
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
    public void testEncode02() throws Exception {
        // 前処理
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        String str = encoderImpl.encode("  ", request, response);

        // 判定
        assertEquals(URLEncoder.encode("  ",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

    /**
     * testEncode03()
     * <br><br>
     * 
     *  (正常系) 
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) original:""（空文字列）<br>
     *         (引数) request:MockHttpServletRequest<br>
     *         (引数) response:MockHttpServletResponse<br>
     *         
     * <br>
     * 期待値：(戻り値) String:""（空文字列）<br>
     *         
     * <br>
     * 変数が空文字の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testEncode03() throws Exception {
        // 前処理
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        String str = encoderImpl.encode("", request, response);

        // 判定
        assertEquals(URLEncoder.encode("",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

    /**
     * testEncode04()
     * <br><br>
     * 
     *  (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) original:"abc"<br>
     *         (引数) request:MockHttpServletRequest<br>
     *         (引数) response:MockHttpServletResponse<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"abc"<br>
     *         
     * <br>
     * 変数が英字文字列の場合、そのまま返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testEncode04() throws Exception {
        // 前処理
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        String str = encoderImpl.encode("abc", request, response);

        // 判定
        assertEquals(URLEncoder.encode("abc",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

    /**
     * testEncode05()
     * <br><br>
     * 
     *  (正常系) 
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) original:"あいうえお"(UTF-8)<br>
     *         (引数) request:MockHttpServletRequest<br>
     *         (引数) response:MockHttpServletResponse<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"あいうえお"<br>
     *         
     * <br>
     * 変数が日本語文字列の場合、エンコードされて返却されることを確認するテスト
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testEncode05() throws Exception {
        // 前処理
        DownloadFileNameEncoderImpl encoderImpl = new DownloadFileNameEncoderImpl();
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        String str = encoderImpl.encode("あいうえお", request, response);

        // 判定
        assertEquals(URLEncoder.encode("あいうえお",
                AbstractDownloadObject.DEFAULT_CHARSET), str);
    }

}
