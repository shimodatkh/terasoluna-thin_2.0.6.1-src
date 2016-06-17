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

import java.util.Arrays;

import javax.servlet.ServletContext;

import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.AbstractBLogicMapperImpl01;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownLoadBLogicAction} クラスのブラックボックステスト。
 *
 * <p>
 * <h4>【クラスの概要】</h4>
 * ダウンロード処理を行う場合にBLogicの起動を行うクラスである。
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.DownLoadBLogicAction
 */
public class DownLoadBLogicActionTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownLoadBLogicActionTest.class);
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
    public DownLoadBLogicActionTest(String name) {
        super(name);
    }

    /**
     * testProcessBLogicResult01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                ※resultObjectにAbstractDownloadObject継承クラスを設定すること<br>
     *         (引数) request:MockHttpServletRequest<br>
     *         (引数) response:MockHttpServletResponse<br>
     *         (状態) mapBLogicResultの戻り値:AbstarctBLogicMapperのスタブ<br>
     *
     * <br>
     * 期待値：(状態変化) response:ストリームに値が書き込まれていること<br>
     *
     * <br>
     * FileDownLoadUtil#downloadが実行されることを確認するテスト
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testProcessBLogicResult01() throws Exception {
        // 前処理
        // request
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // DownloadBLogicAction
        DownloadBLogicAction<AbstractDownloadObject> action =
            new DownloadBLogicAction<AbstractDownloadObject>();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();
        AbstractBLogicMapper mapper = new AbstractBLogicMapperImpl01();

        // ServletContext
        ServletContext context = new MockServletContext();
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("blogicIO");
        resources.setBLogicIO(blogicIO);
        context.setAttribute(BLogicResources.BLOGIC_RESOURCES_KEY + "prefix",
                resources);
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix",
                mapper);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        // ActionMappingEx
        ActionMappingEx mappingEx = new ActionMappingEx();
        mappingEx.setPath("blogicIO");

        // BLogicResultにAbstractDownloadObject継承クラスである
        // DownloadStringクラスのインスタンスをセット
        DownloadString resultObject = new DownloadString("abc", "abc");
        BLogicResult result = new BLogicResult();
        result.setResultObject(resultObject);


        // テスト実施
        action.processBLogicResult(result, request, response, mappingEx);

        // 判定
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));

    }

    /**
     * testProcessBLogicResult02()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) result:BLogicResult<br>
     *                ※resultObjectにAbstractDownloadObject継承クラスを設定すること<br>
     *                ※resultStringに"abc"を設定すること<br>
     *         (引数) request:MockHttpServletRequest<br>
     *                ※pathInfoに"path"を設定すること<br>
     *         (引数) response:MockHttpServletResponse<br>
     *         (状態) mapBLogicResultの戻り値:AbstarctBLogicMapperのスタブ<br>
     *
     * <br>
     * 期待値：(状態変化) response:ストリームに値が書き込まれていること<br>
     *           (状態変化) ログ出力:ログレベル：WARN<br>
     *                          ログメッセージ：result string must not be set. path :path<br>
     *
     * <br>
     * FileDownLoadUtil#downloadが実行されることを確認するテスト
     * <br>
     *
     * @throws Exception このメソッドで発生した例外
     */
    public void testProcessBLogicResult02() throws Exception {
        // 前処理
        // request
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 判定用
        String str = Arrays.toString(response.getContentAsByteArray());

        // ModuleUtil.getPrefix(req)："prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathInfo("path");

        // DownloadBLogicAction
        DownloadBLogicAction<AbstractDownloadObject> action =
            new DownloadBLogicAction<AbstractDownloadObject>();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();
        AbstractBLogicMapper mapper = new AbstractBLogicMapperImpl01();

        // ServletContext
        ServletContext context = new MockServletContext();
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("blogicIO");
        resources.setBLogicIO(blogicIO);
        context.setAttribute(BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", resources);
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", mapper);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        // ActionMappingEx
        ActionMappingEx mappingEx = new ActionMappingEx();
        mappingEx.setPath("blogicIO");

        // BLogicResultにAbstractDownloadObject継承クラスである
        // DownloadStringクラスのインスタンスをセット
        DownloadString resultObject = new DownloadString("abc", "abc");
        BLogicResult result = new BLogicResult();
        result.setResultObject(resultObject);
        result.setResultString("abc");


        // テスト実施
        action.processBLogicResult(result, request, response, mappingEx);

        // 判定
        assertFalse(str.equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil.checkWarn("result string must not be set. path :path"));
    }
}
