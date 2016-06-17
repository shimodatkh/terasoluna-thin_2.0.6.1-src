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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.FileDownloadUtil} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ファイルダウンロードを行うユーティリティクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.FileDownloadUtil
 */
public class FileDownloadUtilTest extends TestCase {

    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FileDownloadUtilTest.class);
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
        LogUTUtil.flush();
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
    public FileDownloadUtilTest(String name) {
        super(name);
    }

    /**
     * testSetEncoder01() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：G <br>
     * <br>
     * 入力値：(引数) encoder:null<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:IllegalArgumentException<br>
     * メッセージ：<br>
     * "encoder must not be null."<br>
     * 
     * <br>
     * encoderがnullである場合、例外がスローされることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetEncoder01() throws Exception {
        // 前処理
        FileDownloadUtil util = new FileDownloadUtil();

        // テスト実施
        try {
            util.setEncoder(null);
            fail("IllegalArgumentExceptionが発生しませんでした。");
        } catch (IllegalArgumentException e) {
            // 判定
            assertEquals("encoder must not be null.", e.getMessage());
        }

    }

    /**
     * testSetEncoder02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) encoder:DownloadFileNameEncoder<br>
     * 
     * <br>
     * 期待値：(状態変化) encoder:DownloadFileNameEncoder<br>
     * 
     * <br>
     * エンコーダが正しく設定されることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetEncoder02() throws Exception {
        // 前処理
        FileDownloadUtil util = new FileDownloadUtil();
        DownloadFileNameEncoder encoder = new DownloadFileNameEncoderImpl();

        // テスト実施
        util.setEncoder(encoder);

        // 判定
        assertEquals(encoder, FileDownloadUtil.encoder);
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) result:AbstractDownloadObject継承クラス<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * 期待値：(状態変化) request:レスポンスが書き込まれる<br>
     * 
     * <br>
     * resultがAbstarctDownloadObject継承クラスの場合、そのままダウンロード対象とされることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse01()
            throws Exception {
        // 前処理
        AbstractDownloadObject result = new DownloadString("abc", "abc");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(result, request, response);

        // 判定
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) result:AbstractDownloadObject継承クラスをプロパティに持たないJavaBean<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * 期待値：(状態変化) request:レスポンスが書き込まれない<br>
     * 
     * <br>
     * resultがAbstarctDownloadObjectをプロパティに持たないJavaBeanの場合、ダウンロードが行われないことを確認するテスト。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse02()
            throws Exception {
        // 前処理
        FileDownloadUtil_Stub01 result = new FileDownloadUtil_Stub01();
        result.setObject1("abc");
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(result, request, response);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse03() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) result:AbstractDownloadObject継承クラスを1つプロパティに持つJavaBean<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * 期待値：(状態変化) request:レスポンスが書き込まれる<br>
     * 
     * <br>
     * resultがAbstractDownloadObject継承クラスを1つプロパティに持つJavaBeanである場合、そのプロパティがダウンロード対象となることを確認するテスト。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse03()
            throws Exception {
        // 前処理
        FileDownloadUtil_Stub02 result = new FileDownloadUtil_Stub02();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(result, request, response);

        // 判定
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse04() <br>
     * <br>
     * 
     * (異常系) <br>
     * 観点：G <br>
     * <br>
     * 入力値：(引数) result:AbstractDownloadObject継承クラスを2つプロパティに持つJavaBean<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     * メッセージキー：<br>
     * "errors.too.many.download"<br>
     * ラップした例外：<br>
     * IllegalStateException<br>
     * 
     * <br>
     * resultがAbstractDownloadObject継承クラスを2つ以上プロパティに持つJavaBeanである場合、例外がスローされることを確認するテスト。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse04()
            throws Exception {
        // 前処理
        FileDownloadUtil_Stub03 result = new FileDownloadUtil_Stub03();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2(new DownloadString("def", "def"));
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        try {
            FileDownloadUtil.download(result, request, response);
            fail("SystemExceptionが発生しませんでした。");
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.too.many.download", e.getErrorCode());
            assertEquals(IllegalStateException.class, e.getCause().getClass());
        }
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse05() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) result:AbstractDownloadObject継承クラスをプロパティに持つがgetterを持たないJavaBean<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * 
     * <br>
     * 期待値：(状態変化) request:レスポンスが書き込まれない<br>
     * 
     * <br>
     * resultがAbstractDownloadObject継承クラスを1つプロパティに持つがそのgetterがないJavaBeanである場合、ダウンロードが行われないことを確認するテスト。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse05()
            throws Exception {
        // 前処理
        FileDownloadUtil_Stub04 result = new FileDownloadUtil_Stub04();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(result, request, response);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse06() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) result:AbstractDownloadObject継承クラスを1つプロパティに持つJavaBean<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:HttpServletResponseを実装した例外発生用スタブクラス<br>
     * (状態) download(HttpServletRequest・・・)の結果:SocketExeption<br>
     * 
     * <br>
     * 期待値：(状態変化) request:レスポンスが書き込まれない<br>
     * 
     * <br>
     * SocketExceptionがスローされた場合、ダウンロードが行われないことを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse06()
            throws Exception {
        // 前処理
        FileDownloadUtil_Stub02 result = new FileDownloadUtil_Stub02();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        FileDownloadUtil_HttpServletResponseStub01 response = new FileDownloadUtil_HttpServletResponseStub01();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(result, request, response);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
    }

    /**
     * testDownloadObjectHttpServletRequestHttpServletResponse07() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) result:AbstractDownloadObject継承クラスを1つプロパティに持つJavaBean<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:HttpServletResponseを実装した例外発生用スタブクラス<br>
     * (状態) download(HttpHttpServletRequest・・・)の結果:IOEｘception<br>
     * 
     * <br>
     * 期待値：(状態変化) request:レスポンスが書き込まれない<br>
     * (状態変化) ログ:ログレベル：ERROR<br>
     * ログ内容：IOException has occurred while downloading<br>
     * 例外：IOException<br>
     * 
     * <br>
     * IOExceptionがスローされた場合、ログ出力が行われ、ダウンロードが行われないことを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadObjectHttpServletRequestHttpServletResponse07()
            throws Exception {
        // 前処理
        FileDownloadUtil_Stub02 result = new FileDownloadUtil_Stub02();
        result.setObject1(new DownloadString("abc", "abc"));
        result.setObject2("def");
        MockHttpServletRequest request = new MockHttpServletRequest();
        FileDownloadUtil_HttpServletResponseStub02 response = new FileDownloadUtil_HttpServletResponseStub02();

        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施

        FileDownloadUtil.download(result, request, response);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil
                .checkError("IOException has occurred while downloading",
                        new IOException()));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean01()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:null<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスに値が書き込まれない<br>
     * (状態変化) ログ:ログレベル：WARN<br>
     * ログ内容："No download object."<br>
     * 例外：なし<br>
     * 
     * <br>
     * downloadObjectがnullの場合、処理されないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean01()
            throws Exception {
        // 前処理
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(null, request, response, true);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil.checkWarn("No download object."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean02()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数)
     * downloadObject:name="xyz"で、additionalHeadersがnullのAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスに値が書き込まれない<br>
     * (状態変化) ログ:ログレベル：WARN<br>
     * ログ内容："Header must not be null."<br>
     * 例外：なし<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeadersがnullの場合、処理されないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean02()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.additionalHeaders = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil.checkWarn("Header must not be null."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean03()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数)
     * downloadObject:name="xyz"で、Mapの要素数が0のadditionalHeadersを持つAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのヘッダに<br>
     * ヘッダ名: "Content-Disposition"<br>
     * 値: "attachment; filename=xyz"<br>
     * が設定される<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeadersがエントリを持たないMapの場合、正常に処理されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean03()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.additionalHeaders = new HashMap<String, List<String>>();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean04()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、Map{[null,
     * null]}を保持するadditionalHeadersを持つAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスに値が書き込まれない<br>
     * (状態変化) ログ:ログレベル：WARN<br>
     * ログ内容："Header name and value must not be null."<br>
     * 例外：なし<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeadersのエントリのListがnullである場合、処理されないことを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean04()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.additionalHeaders.put(null, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil
                .checkWarn("Header name and value must not be null."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean05()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、Map{[null,
     * List["abc"]]}を保持するadditionalHeadersを持つAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスに値が書き込まれない<br>
     * (状態変化) ログ:ログレベル：WARN<br>
     * ログ内容："Header name and value must not be null."<br>
     * 例外：なし<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeadersのエントリのキーがnullである場合、処理されないことを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean05()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add("abc");
        object.additionalHeaders.put(null, list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertTrue(str
                .equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil
                .checkWarn("Header name and value must not be null."));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean06()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、Map{["abc",
     * List[null]]}を保持するadditionalHeadersを持つAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのヘッダに<br>
     * ヘッダ名: "Content-Disposition", 値: "attachment; filename=xyz"<br>
     * ヘッダ名: "abc", 値: ""（空文字列）<br>
     * が設定される<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeadersのエントリのListの要素がnullである場合、空文字列に変換されて正常に処理されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean06()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add(null);
        object.additionalHeaders.put("abc", list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
        assertTrue(response.getHeaderNames().contains("abc"));
        assertEquals("", (String) response.getHeader("abc"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean07()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、Map{["abc",
     * List["abc"]]}を保持するadditionalHeadersを持つAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのヘッダに<br>
     * ヘッダ名: "Content-Disposition", 値: "attachment; filename=xyz"<br>
     * ヘッダ名: "abc", 値: "abc"<br>
     * が設定される<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeadersのエントリのListがnullを含まない要素1つを持つ場合、正常に処理されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean07()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add("abc");
        object.additionalHeaders.put("abc", list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
        assertTrue(response.getHeaderNames().contains("abc"));
        assertEquals("abc", (String) response.getHeader("abc"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean08()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、Map{["abc", List["abc",
     * "def"]]}を保持するadditionalHeadersを持つAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのヘッダに<br>
     * ヘッダ名: "Content-Disposition", 値: "attachment; filename=xyz"<br>
     * ヘッダ名: "abc", 値: List["abc", "def"]<br>
     * が設定される<br>
     * 
     * <br>
     * AbstractDownloadObject#additionalHeadersのエントリのListがnullを含まない要素2つを持つ場合、正常に処理されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean08()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("def");
        object.additionalHeaders.put("abc", list);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
        assertTrue(response.getHeaderNames().contains("abc"));
        List list2 = response.getHeaders("abc");
        assertEquals("abc", list2.get(0));
        assertEquals("def", list2.get(1));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean09()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数)
     * downloadObject:name="xyz"で、エンコーディングとコンテントタイプにnullが設定されているAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスにエンコーディングとコンテントタイプが設定されない<br>
     * 
     * <br>
     * AbstractDownloadObjectのエンコーディングとコンテントタイプにnullが設定されている場合、レスポンスにエンコーディングとコンテントタイプが設定されないことを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean09()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = null;
        object.contentType = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String charset = response.getCharacterEncoding();
        String contentType = response.getContentType();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertEquals(charset, response.getCharacterEncoding());
        assertEquals(contentType, response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean10()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数)
     * downloadObject:name="xyz"で、エンコーディングとコンテントタイプに""（空文字列）が設定されているAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスにエンコーディングとコンテントタイプが設定されない<br>
     * 
     * <br>
     * AbstractDownloadObjectのエンコーディングとコンテントタイプに""（空文字列）が設定されている場合、レスポンスにエンコーディングとコンテントタイプが設定されないことを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean10()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = "";
        object.contentType = "";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        String charset = response.getCharacterEncoding();
        String contentType = response.getContentType();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertEquals(charset, response.getCharacterEncoding());
        assertEquals(contentType, response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean11()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、エンコーディングとコンテントタイプに "
     * "（空白）が設定されているAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのエンコーディングとコンテントタイプにそれぞれ" "（空白）が設定される<br>
     * 
     * <br>
     * AbstractDownloadObjectのエンコーディングとコンテントタイプに"
     * "（空白）が設定されている場合、レスポンスのエンコーディングとコンテントタイプにそれぞれ" "（空白）が設定される <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean11()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = "  ";
        object.contentType = "  ";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertEquals("  ", response.getCharacterEncoding());
        assertEquals("  ", response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean12()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数)
     * downloadObject:name="xyz"で、エンコーディングとコンテントタイプにそれぞれ"abc"が設定されているAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのエンコーディングとコンテントタイプにそれぞれ"abc"が設定される<br>
     * 
     * <br>
     * AbstractDownloadObjectのエンコーディングとコンテントタイプに"abc"が設定されている場合、レスポンスのエンコーディングとコンテントタイプにそれぞれ"abc"が設定される
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean12()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.charset = "abc";
        object.contentType = "abc";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertEquals("abc", response.getCharacterEncoding());
        assertEquals("abc", response.getContentType());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean13()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、データサイズが-1のAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスにデータサイズが設定されない<br>
     * 
     * <br>
     * AbstractDownloadObjectのデータサイズが-1のとき、レスポンスにデータサイズが設定されないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean13()
            throws Exception {
        // 前処理
        FileDownloadUtil_DownloadStringStub01 object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.lengthOfData = -1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        int length = response.getContentLength();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertEquals(length, response.getContentLength());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean14()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、データサイズが0のAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスにデータサイズが設定されない<br>
     * 
     * <br>
     * AbstractDownloadObjectのデータサイズが0のとき、レスポンスにデータサイズが設定されないことを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean14()
            throws Exception {
        // 前処理
        FileDownloadUtil_DownloadStringStub01 object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.lengthOfData = 0;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // 判定用
        int length = response.getContentLength();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertEquals(length, response.getContentLength());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean15()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、データサイズが1のAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのデータサイズが1に設定される<br>
     * 
     * <br>
     * AbstractDownloadObjectのデータサイズが0のとき、レスポンスのデータサイズが1に設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean15()
            throws Exception {
        // 前処理
        FileDownloadUtil_DownloadStringStub01 object = new FileDownloadUtil_DownloadStringStub01(
                "xyz", "xyz");
        object.lengthOfData = 1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertEquals(1, response.getContentLength());
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean16()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"であるAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのヘッダに<br>
     * ヘッダ名: "Content-Disposition"<br>
     * 値: "attachment; filename=xyz"<br>
     * が設定される<br>
     * 
     * <br>
     * AbstractDownloadObjectのファイルの別名を設定するとき、setFileNameが正しく実行されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean16()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.name = "xyz";
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=xyz", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean17()
     * <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) downloadObject:name="xyz"で、ファイルの別名が存在しないAbstractDownloadObject<br>
     * (引数) request:org.springframework.mock.web.MockHttpServletRequest<br>
     * (引数) response:org.springframework.mock.web.MockHttpServletResponse<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(状態変化) response:レスポンスのヘッダに<br>
     * ヘッダ名: "Content-Disposition"<br>
     * 値: "attachment; filename="<br>
     * が設定される<br>
     * 
     * <br>
     * AbstractDownloadObjectのファイルの別名を設定しないときでも、setFileNameが正しく実行されることを確認する。
     * <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testDownloadAbstractDownloadObjectHttpServletRequestHttpServletResponseboolean17()
            throws Exception {
        // 前処理
        AbstractDownloadObject object = new DownloadString("xyz", "xyz");
        object.name = null;
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        FileDownloadUtil.download(object, request, response, true);

        // 判定
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testSetFileNameHttpServletResponseStringboolean01() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) response:MockHttpServletResponse<br>
     * (引数) name:"abc"<br>
     * (引数) forceDownload:true<br>
     * 
     * <br>
     * 期待値：(戻り値) null:レスポンスのヘッダに<br>
     * キー: "Content-Disposition"<br>
     * 値: "attachment; filename=abc"<br>
     * が設定される<br>
     * 
     * <br>
     * レスポンスのヘッダに正しい文字列が書き込まれることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetFileNameHttpServletResponseStringboolean01()
            throws Exception {
        String name = "abc";
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        FileDownloadUtil.setFileName(response, name, true);

        // 判定
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("attachment; filename=abc", (String) response
                .getHeader("Content-Disposition"));
    }

    /**
     * testSetFileNameHttpServletResponseStringboolean02() <br>
     * <br>
     * 
     * (正常系) <br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) response:MockHttpServletResponse<br>
     * (引数) name:"abc"<br>
     * (引数) forceDownload:false<br>
     * 
     * <br>
     * 期待値：(戻り値) null:レスポンスのヘッダに<br>
     * キー: "Content-Disposition"<br>
     * 値: "inline; filename=abc"<br>
     * が設定される<br>
     * 
     * <br>
     * レスポンスのヘッダに正しい文字列が書き込まれることを確認するテスト。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetFileNameHttpServletResponseStringboolean02()
            throws Exception {
        String name = "abc";
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        FileDownloadUtil.setFileName(response, name, false);

        // 判定
        assertTrue(response.getHeaderNames().contains("Content-Disposition"));
        assertEquals("inline; filename=abc", (String) response
                .getHeader("Content-Disposition"));
    }

}
