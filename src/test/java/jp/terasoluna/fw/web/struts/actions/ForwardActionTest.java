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

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.ForwardAction}
 * クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * JSPなどへフォワードするアクション
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.ForwardAction
 */
public class ForwardActionTest extends PropertyTestCase {

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
     * プロパティを初期化する
     * @see jp.terasoluna.utlib.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        clearProperty();
    }

    /**
     * プロパティをクリンアップする
     * @see jp.terasoluna.utlib.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        clearProperty();
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
    public ForwardActionTest(String name) {
        super(name);
    }

    /**
     * testDoExecute01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (プロパティ) forwardAction.contextRelative:true
     *         
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *         (状態変化) res.sendError():呼び出し確認<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnullの場合、HttpServletResponse.sendError()が呼び出され、nullを返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute01() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();

        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を true に設定
        addProperty("forwardAction.contextRelative", "true");
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfigを設定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ForwardAction_HttpServletResponseStub01 res =
            new ForwardAction_HttpServletResponseStub01();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (状態) res.sendError():IOException発生<br>
     *         (プロパティ) forwardAction.contextRelative:true
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："error.forward.errorpage"<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnullで、HttpServletResponse.sendError()呼出で例外が発生した場合、SystemExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute02() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();

        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を true に設定
        addProperty("forwardAction.contextRelative", "true");
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfigを設定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ForwardAction_HttpServletResponseStub02 res =
            new ForwardAction_HttpServletResponseStub02();
        
        @SuppressWarnings("unused") ActionForward forward = null;

        // テスト実施
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("例外が発生しませんでした");
        } catch (SystemException e) {
            // 判定
            // IOExceptionをラップしたか
            assertEquals(e.getCause().getClass().getName(),
                    IOException.class.getName());
            // エラーログ確認
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
    }

    /**
     * testDoExecute03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:"abc"<br>
     *         (プロパティ) forwardAction.contextRelative:true
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：true<br>
     * <br>
     * mappingからパラメータの取得結果がnot nullの場合、ActionForwardのpathが指定したpathと一致するか確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute03() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();

        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を true に設定
        addProperty("forwardAction.contextRelative", "true");
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertEquals("abc", forward.getPath());
        assertTrue(forward.getContextRelative());
    }

    /**
     * testDoExecute04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnot null]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:"abc"<br>
     *         (プロパティ) forwardAction.contextRelative:true
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：true<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnot nullの場合、パラメータの取得結果を遷移先として返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute04() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();

        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を true に設定
        addProperty("forwardAction.contextRelative", "true");

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        // アクションフォワードの設定
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(af);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertEquals("abc", forward.getPath());
        assertTrue(forward.getContextRelative());
    }

    /**
     * testDoExecute05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnot null]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (プロパティ) forwardAction.contextRelative:true
     *         
     * <br>
     * 期待値：(戻り値) actionForward:mapping.findForward("success")<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：false<br>
     *         
     * <br>
     * mappingからパラメータの取得結果がnullでmapping.findForward("success")がnot nullの場合、mapping.findForward("success")を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute05() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
        
        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を true に設定
        addProperty("forwardAction.contextRelative", "true");

        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // アクションフォワードの設定
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");

        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(af);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;

        // テスト実施
        forward = action.doExecute(mapping, form, req, res);

        // 判定
        assertEquals(af, forward);
        assertFalse(forward.getContextRelative());
    }
    
    /**
     * testDoExecute06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (プロパティ) forwardAction.contextRelative:false
     *         
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *         (状態変化) res.sendError():呼び出し確認<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnullの場合、HttpServletResponse.sendError()が呼び出され、nullを返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute06() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を false に設定
        addProperty("forwardAction.contextRelative", "false");
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfigを設定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ForwardAction_HttpServletResponseStub01 res =
            new ForwardAction_HttpServletResponseStub01();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute07()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (状態) res.sendError():IOException発生<br>
     *         (プロパティ) forwardAction.contextRelative:false
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："error.forward.errorpage"<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnullで、HttpServletResponse.sendError()呼出で例外が発生した場合、SystemExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute07() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を false に設定
        addProperty("forwardAction.contextRelative", "false");
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfigを設定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ForwardAction_HttpServletResponseStub02 res =
            new ForwardAction_HttpServletResponseStub02();
        
        @SuppressWarnings("unused") ActionForward forward = null;
    
        // テスト実施
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("例外が発生しませんでした");
        } catch (SystemException e) {
            // 判定
            // IOExceptionをラップしたか
            assertEquals(e.getCause().getClass().getName(),
                    IOException.class.getName());
            // エラーログ確認
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
    }

    /**
     * testDoExecute08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:"abc"<br>
     *         (プロパティ) forwardAction.contextRelative:false
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：false<br>
     * <br>
     * mappingからパラメータの取得結果がnot nullの場合、ActionForwardのpathが指定したpathと一致するか確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute08() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を false に設定
        addProperty("forwardAction.contextRelative", "false");
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnot null]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:"abc"<br>
     *         (プロパティ) forwardAction.contextRelative:false
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：false<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnot nullの場合、パラメータの取得結果を遷移先として返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute09() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を false に設定
        addProperty("forwardAction.contextRelative", "false");
    
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        // アクションフォワードの設定
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(af);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnot null]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (プロパティ) forwardAction.contextRelative:false
     *         
     * <br>
     * 期待値：(戻り値) actionForward:mapping.findForward("success")<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：false<br>
     *         
     * <br>
     * mappingからパラメータの取得結果がnullでmapping.findForward("success")がnot nullの場合、mapping.findForward("success")を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute10() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
        
        // プロパティクリア
        clearProperty();
        
        // プロパティ forwardAction.contextRelative を false に設定
        addProperty("forwardAction.contextRelative", "false");
    
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // アクションフォワードの設定
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
    
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(af);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertEquals(af, forward);
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (プロパティ) forwardAction.contextRelative:無設定
     *         
     * <br>
     * 期待値：(戻り値) actionForward:null<br>
     *         (状態変化) res.sendError():呼び出し確認<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnullの場合、HttpServletResponse.sendError()が呼び出され、nullを返すことを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute11() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
                
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfigを設定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ForwardAction_HttpServletResponseStub01 res =
            new ForwardAction_HttpServletResponseStub01();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertNull(forward);
        assertTrue(res.isSendError);
    }

    /**
     * testDoExecute12()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (状態) res.sendError():IOException発生<br>
     *         (プロパティ) forwardAction.contextRelative:無設定
     *         
     * <br>
     * 期待値：(状態変化) 例外:SystemException：<br>
     *                    メッセージキー："error.forward.errorpage"<br>
     *                    ラップした例外：IOException<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    エラーログ："Error page(404) forwarding failed."<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnullで、HttpServletResponse.sendError()呼出で例外が発生した場合、SystemExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoExecute12() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // ModuleConfigを設定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // sendErrorの呼び出し確認用
        ForwardAction_HttpServletResponseStub02 res =
            new ForwardAction_HttpServletResponseStub02();
        
        @SuppressWarnings("unused") ActionForward forward = null;
    
        // テスト実施
        try {
            forward = action.doExecute(mapping, form, req, res);
            fail("例外が発生しませんでした");
        } catch (SystemException e) {
            // 判定
            // IOExceptionをラップしたか
            assertEquals(e.getCause().getClass().getName(),
                    IOException.class.getName());
            // エラーログ確認
            assertTrue(LogUTUtil.checkError(
                    "Error page(404) forwarding failed."));
            assertEquals("error.forward.errorpage", e.getErrorCode());
        }
    }

    /**
     * testDoExecute13()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnull]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:"abc"<br>
     *         (プロパティ) forwardAction.contextRelative:無設定
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：false<br>
     * <br>
     * mappingからパラメータの取得結果がnot nullの場合、ActionForwardのpathが指定したpathと一致するか確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute13() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute14()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnot null]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:"abc"<br>
     *         (プロパティ) forwardAction.contextRelative:無設定
     *         
     * <br>
     * 期待値：(戻り値) actionForward:ActionForward.getPath()："abc"<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：false<br>
     *         
     * <br>
     * mappingからパラメータの取得結果及びmapping.findForward("success")がnot nullの場合、パラメータの取得結果を遷移先として返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute14() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
    
        // プロパティクリア
        clearProperty();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = "abc";
        mapping.setParameter(path);
        
        // アクションフォワードの設定
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
        
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(af);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertEquals("abc", forward.getPath());
        assertFalse(forward.getContextRelative());
    }

    /**
     * testDoExecute15()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) mapping:not null<br>
     *                [mapping.findForward("success")がnot null]<br>
     *         (引数) form:not null<br>
     *         (引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (状態) path:null<br>
     *         (プロパティ) forwardAction.contextRelative:無設定
     *         
     * <br>
     * 期待値：(戻り値) actionForward:mapping.findForward("success")<br>
     *         (戻り値) actionForward:ActionForward.getContextRelative()：false<br>
     *         
     * <br>
     * mappingからパラメータの取得結果がnullでmapping.findForward("success")がnot nullの場合、mapping.findForward("success")を返却することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    @SuppressWarnings("deprecation")
    public void testDoExecute15() throws Exception {
        // 前処理
        ForwardAction action = new ForwardAction();
        
        // プロパティクリア
        clearProperty();
        
        // パラメータの設定
        ActionMappingEx mapping = new ActionMappingEx();
        String path = null;
        mapping.setParameter(path);
        
        // アクションフォワードの設定
        ActionForward af = new ActionForward();
        af.setName("success");
        af.setPath("def");
    
        // ModuleConfigを作成
        ModuleConfig mConfig = new ModuleConfigImpl("");
        // 返却されるActionForwardを指定
        mConfig.addForwardConfig(af);
        // モジュールコンフィグのforward属性指定
        mapping.setModuleConfig(mConfig);
        
        ForwardAction_ActionFormStub01 form =
            new ForwardAction_ActionFormStub01();
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        
        ActionForward forward = null;
    
        // テスト実施
        forward = action.doExecute(mapping, form, req, res);
    
        // 判定
        assertEquals(af, forward);
        assertFalse(forward.getContextRelative());
    }
    
}
