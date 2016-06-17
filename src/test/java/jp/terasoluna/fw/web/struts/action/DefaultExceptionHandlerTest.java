/*
 * Copyright (c) 2008 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts.action;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.ExceptionConfig;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler} クラスのブラックボックステスト.
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * 指定したログレベルでログを出力する例外ハンドラ。<br>
 * 例外時のログ出力と エラー画面への遷移を行う。<br>
 * アクション実行中にシステム例外が発生したときは、エラー情報をログ出力した上で、当該アクションマッピングに定義されているシステムエラー画面に遷移する。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler
 */
public class DefaultExceptionHandlerTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する.
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefaultExceptionHandlerTest.class);
    }

    /**
     * 初期化処理を行う.
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 終了処理を行う.
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * コンストラクタ.
     *
     * @param name このテストケースの名前。
     */
    public DefaultExceptionHandlerTest(String name) {
        super(name);
    }

    /**
     * testExecute01()
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfig<br>
     *                  path="/errorPath.do"<br>
     *         (引数) mapping:not null<br>
     *         (引数) formInstance:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：(戻り値) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (状態変化) ログ:【ERRORログ】"例外ハンドラが例外を検知しました"<br>
     *                         【DEBUGログ】（出力されない）<br>
     *
     * <br>
     * ExceptionConfigを設定した場合、デフォルトのERRORレベルで
     * ログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute01() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // 例外
        Exception e = new Exception();

        // 例外設定情報
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // 判定
        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ログの確認
        assertTrue(LogUTUtil.checkError("例外ハンドラが例外を検知しました", e));
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));

    }

    /**
     * testExecute02().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  module="module"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (引数) mapping:not null<br>
     *         (引数) formInstance:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：(戻り値) ActionForward:path="/errorPath.do"<br>
     *                                module="module"<br>
     *         (状態変化) ログ:【TRACEログ】"例外ハンドラが例外を検知しました"<br>
     *                         【DEBUGログ】（出力されない）<br>
     *
     * <br>
     * ExceptionConfigExのlogLevelにTRACEを設定し、遷移先モジュールを設定した場合、
     * TRACEレベルでログが出力され、アクションフォワードに遷移先モジュールが設定
     * されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute02() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // 例外
        Exception e = new Exception();

        // 例外設定情報
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setModule("module");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_TRACE);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // 判定

        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertEquals("module", forward.getModule());
        // ログの確認
        assertTrue(LogUTUtil.checkTrace("例外ハンドラが例外を検知しました", e));
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));

    }

    /**
     * testExecute03().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_DEBUG<br>
     *         (引数) mapping:not null<br>
     *         (引数) formInstance:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：(戻り値) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (状態変化) ログ:【DEBUGログ】"例外ハンドラが例外を検知しました"<br>
     *
     * <br>
     * ExceptionConfigExのlogLevelにDEBUGを設定した場合、
     * DEBUGレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute03() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // 例外
        Exception e = new Exception();

        // 例外設定情報
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_DEBUG);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // 判定

        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ログの確認
        assertTrue(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));

    }

    /**
     * testExecute04().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_INFO<br>
     *         (引数) mapping:not null<br>
     *         (引数) formInstance:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：(戻り値) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (状態変化) ログ:【INFOログ】"例外ハンドラが例外を検知しました"<br>
     *                         【DEBUGログ】（出力されない）<br>
     * <br>
     * ExceptionConfigExのlogLevelにINFOを設定した場合、
     * INFOレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute04() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // 例外
        Exception e = new Exception();

        // 例外設定情報
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_INFO);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // 判定

        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ログの確認
        assertTrue(LogUTUtil.checkInfo("例外ハンドラが例外を検知しました", e));
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));

    }

    /**
     * testExecute05().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_WARN<br>
     *         (引数) mapping:not null<br>
     *         (引数) formInstance:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：(戻り値) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (状態変化) ログ:【WARNログ】"例外ハンドラが例外を検知しました"<br>
     *                         【DEBUGログ】（出力されない）<br>
     * <br>
     * ExceptionConfigExのlogLevelにWARNを設定した場合、
     * WARNレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute05() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // 例外
        Exception e = new Exception();

        // 例外設定情報
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_WARN);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // 判定

        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ログの確認
        assertTrue(LogUTUtil.checkWarn("例外ハンドラが例外を検知しました", e));
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));

    }

    /**
     * testExecute06().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_ERROR<br>
     *         (引数) mapping:not null<br>
     *         (引数) formInstance:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：(戻り値) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (状態変化) ログ:【ERRORログ】"例外ハンドラが例外を検知しました"<br>
     *                         【DEBUGログ】（出力されない）<br>
     * <br>
     * ExceptionConfigExのlogLevelにERRORを設定した場合、
     * ERRORレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute06() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // 例外
        Exception e = new Exception();

        // 例外設定情報
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_ERROR);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // 判定

        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ログの確認
        assertTrue(LogUTUtil.checkError("例外ハンドラが例外を検知しました", e));
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));

    }

    /**
     * testExecute07().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) ex:Exception<br>
     *         (引数) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_FATAL<br>
     *         (引数) mapping:not null<br>
     *         (引数) formInstance:not null<br>
     *         (引数) request:not null<br>
     *         (引数) response:not null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：(戻り値) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (状態変化) ログ:【FATALログ】"例外ハンドラが例外を検知しました"<br>
     *                         【DEBUGログ】（出力されない）<br>
     * <br>
     * ExceptionConfigExのlogLevelにFATALを設定した場合、
     * FATALレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testExecute07() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // 例外
        Exception e = new Exception();

        // 例外設定情報
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_FATAL);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // テスト実施
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // 判定

        // 戻り値の確認
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ログの確認
        assertTrue(LogUTUtil.checkFatal("例外ハンドラが例外を検知しました", e));
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));

    }

    /**
     * testLogExceptionException01().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【DEBUGログ】（出力されない）<br>
     *
     * <br>
     * スーパークラスのExceptionHandler#logException(Exception e)の処理を
     * オーバーライドし、デバッグログが出力されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionException01() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = null;
        
        // テスト実施
        handler.logException(e);
    
        // 判定
    
        // ログの確認
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));
    
    }

    /**
     * testLogExceptionException02().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【DEBUGログ】（出力されない）<br>
     *
     * <br>
     * スーパークラスのExceptionHandler#logException(Exception e)の処理を
     * オーバーライドし、デバッグログが出力されないことを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionException02() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = new Exception();
        
        // テスト実施
        handler.logException(e);
    
        // 判定
    
        // ログの確認
        assertFalse(LogUTUtil.checkDebug("例外ハンドラが例外を検知しました", e));
    
    }

    /**
     * testLogExceptionExceptionString01().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】"例外ハンドラが例外を検知しました"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelがnullの場合、デフォルトのERRORレベルで
     * ログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionString01() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = null;
        String logLevel = null;
        
        // テスト実施
        handler.logException(e, logLevel);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError("例外ハンドラが例外を検知しました"));
    
    }

    /**
     * testLogExceptionExceptionString02().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【TRACEログ】"例外ハンドラが例外を検知しました"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelが設定されている場合、設定されたログレベルで
     * ログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionString02() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        
        // テスト実施
        handler.logException(e, logLevel);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkTrace("例外ハンドラが例外を検知しました"));
    
    }

    /**
     * testLogExceptionExceptionString03().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】"例外ハンドラが例外を検知しました"<br>
     *                         出力される例外：引数のException<br>
     * <br>
     * 引数のeがnullでなくlogLevelがnullの場合、デフォルトのERRORレベルで
     * ログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionString03() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = new Exception();
        String logLevel = null;
        
        // テスト実施
        handler.logException(e, logLevel);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError("例外ハンドラが例外を検知しました", e));
    
    }

    /**
     * testLogExceptionExceptionString04().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【TRACEログ】"例外ハンドラが例外を検知しました"<br>
     *                         出力される例外：引数のException<br>
     * <br>
     * 引数のeがnullでなくlogLevelが設定されている場合、設定されたログレベルで
     * ログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionString04() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        
        // テスト実施
        handler.logException(e, logLevel);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkTrace("例外ハンドラが例外を検知しました", e));
    
    }

    /**
     * testLogExceptionExceptionStringString01().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:null<br>
     *         (引数) message:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】null<br>
     *
     * <br>
     * 引数のeがnullでlogLevelとメッセージが設定されていない場合、
     * デフォルトのERRORレベルでnullのログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString01() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = null;
        String message = null;
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError(null));
    
    }

    /**
     * testLogExceptionExceptionStringString02().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:null<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】"hoge"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelが設定されておらずメッセージが設定されている場合、
     * デフォルトのERRORレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString02() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = null;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString03().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【TRACEログ】"hoge"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelがTRACEでメッセージが設定されている場合、
     * TRACEレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString03() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkTrace("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString04().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_DEBUG<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【DEBUGログ】"hoge"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelがDEBUGでメッセージが設定されている場合、
     * DEBUGレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString04() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_DEBUG;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkDebug("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString05().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_INFO<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【INFOログ】"hoge"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelがINFOでメッセージが設定されている場合、
     * INFOレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString05() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_INFO;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkInfo("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString06().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_WARN<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【WARNログ】"hoge"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelがWARNでメッセージが設定されている場合、
     * WARNレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString06() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_WARN;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkWarn("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString07().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_ERROR<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】"hoge"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelがERRORでメッセージが設定されている場合、
     * ERRORレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString07() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_ERROR;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString08().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:null<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_FATAL<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【FATALログ】"hoge"<br>
     *
     * <br>
     * 引数のeがnullでlogLevelがFATALでメッセージが設定されている場合、
     * FATALレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString08() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_FATAL;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkFatal("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString09().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:null<br>
     *         (引数) message:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】null<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelとメッセージが設定されていない場合、
     * デフォルトのERRORレベルでnullのログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString09() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = null;
        String message = null;
    
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError(null, e));
    
    }

    /**
     * testLogExceptionExceptionStringString10().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:null<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】"hoge"<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelが設定されておらずメッセージが設定されている場合、
     * デフォルトのERRORレベルでログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString10() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = null;
        String message = "hoge";
    
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString11().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【TRACEログ】"hoge"<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelがTRACEでメッセージが設定されている場合、
     * TRACEレベルでログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString11() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkTrace("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString12().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_DEBUG<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【DEBUGログ】"hoge"<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelがDEBUGでメッセージが設定されている場合、
     * DEBUGレベルでログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString12() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_DEBUG;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkDebug("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString13().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_INFO<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【INFOログ】"hoge"<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelがINFOでメッセージが設定されている場合、
     * INFOレベルでログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString13() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_INFO;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkInfo("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString14().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_WARN<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【WARNログ】"hoge"<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelがWARNでメッセージが設定されている場合、
     * WARNレベルでログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString14() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_WARN;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkWarn("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString15().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_ERROR<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】"hoge"<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelがERRORでメッセージが設定されている場合、
     * ERRORレベルでログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString15() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_ERROR;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString16().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) e:Exception<br>
     *         (引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_FATAL<br>
     *         (引数) message:"hoge"<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【FATALログ】"hoge"<br>
     *                         出力される例外：引数のException<br>
     *
     * <br>
     * 引数のeに例外が設定されており、logLevelがFATALでメッセージが設定されている場合、
     * FATALレベルでログと例外が出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionExceptionStringString16() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_FATAL;
        String message = "hoge";
        
        // テスト実施
        handler.logException(e, logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkFatal("hoge", e));
    
    }

    /**
     * testLogExceptionStringString01().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) logLevel:null<br>
     *         (引数) message:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】null<br>
     *
     * <br>
     * 引数のlogLevelがnullでmessageがnullの場合、
     * デフォルトのERRORレベルでnullのログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionStringString01() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = null;
        String message = null;
        
        // テスト実施
        handler.logException(logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError(null));
    
    }

    /**
     * testLogExceptionStringString02().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) logLevel:null<br>
     *         (引数) message:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【ERRORログ】"hoge"<br>
     *
     * <br>
     * 引数のlogLevelがnullでmessageがnullでない場合、
     * デフォルトのERRORレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionStringString02() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = null;
        String message = "hoge";
        
        // テスト実施
        handler.logException(logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkError("hoge"));
    
    }

    /**
     * testLogExceptionStringString03().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (引数) message:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【TRACEログ】null<br>
     *
     * <br>
     * 引数のlogLevelが設定されておりmessageがnullの場合、
     * 設定されたログレベルでnullのログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionStringString03() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = null;
        
        // テスト実施
        handler.logException(logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkTrace(null));
    
    }

    /**
     * testLogExceptionStringString04().
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (引数) message:null<br>
     *         (前提条件) messages:-<br>
     * <br>
     * 期待値：
     *         (状態変化) ログ:【TRACEログ】"hoge"<br>
     *
     * <br>
     * 引数のlogLevelが設定されておりmessageがnullでない場合、
     * 設定されたログレベルでログが出力されることを確認する。
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testLogExceptionStringString04() throws Exception {
        // 前処理
    
        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = "hoge";
        
        // テスト実施
        handler.logException(logLevel, message);
    
        // 判定
    
        // ログの確認
        assertTrue(LogUTUtil.checkTrace("hoge"));
    
    }

    /**
     * testGetLogger01()
     * <br><br>
     *
     * 正常系
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(前提条件) logger:-<br>
     * <br>
     * 期待値：(戻り値) LogFactory.getLog(DefaultExceptionHandler.class)と同一のインスタンス<br>
     * <br>
     * 正常系1件のみのテスト
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetLogger01() throws Exception {
        // 前処理

        // 汎用例外ハンドラ
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // テスト実施
        Log logger = handler.getLogger();

        // 判定
        assertSame(LogFactory.getLog(DefaultExceptionHandler.class), logger);
    }

}
