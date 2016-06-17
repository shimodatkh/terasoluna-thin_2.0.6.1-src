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

package jp.terasoluna.fw.web.struts.util;

import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.mock.MockWebApplicationContext;
import junit.framework.TestCase;

import org.apache.struts.util.MessageResources;
import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * {@link SpringMessageResourcesFactory} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * SpringMessageResourcesFactoryを生成するファクトリクラス<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.util.SpringMessageResourcesFactory
 */
public class SpringMessageResourcesFactoryTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SpringMessageResourcesFactoryTest.class);
    }

    /**
     * このテストケースで利用するApplicationContext
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    private WebApplicationContext context = null;
    
    /**
     * 初期化処理を行う.
     *
     * @throws Exception このメソッドで発生した例外
     * @see junit.framework.TestCase#setUp()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // ContextLoader.getCurrentWebApplicationContext()の返却値に
        // モックのApplicationContextをセットしておく。
        Map currentContextPerThread = (Map) UTUtil.getPrivateField(
                ContextLoader.class, "currentContextPerThread");
        String[] configLocations = new String[] {
        "jp/terasoluna/fw/web/struts/util/SpringMessageResourcesFacotryTest01.xml"};
        this.context = new MockWebApplicationContext(configLocations);
        currentContextPerThread.put(
            Thread.currentThread().getContextClassLoader(), this.context);
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
        this.context = null;
    }
    
    /**
     * testCreateResources01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) config:"notExistBeanName"（存在しないBean名）<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * 期待値：(戻り値) SpringMessageResources:-<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.bean.exception"<br>
     *                    ラップした例外：BeansExceptionのサブクラス<br>
     *         (状態変化) ログ:【ERRORログ】"notExistBeanName is not found or it is not MessageSource instance."<br>
     *         
     * <br>
     * 存在しないBean名を渡した場合、エラーログを出力し、
     * 例外がスローされることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources01() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // テスト実施
        try {
            factory.createResources("notExistBeanName");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.bean.exception", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError("notExistBeanName is not found or it is not MessageSource instance."));
        }
    }
    
    /**
     * testCreateResources02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:""（空文字）<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config=""<br>
     *                    returnNull=MessageResourcesFactory.getReturnNull()<br>
     *                    messageSource=前提条件でセットしたapplicationContext<br>
     *         
     * <br>
     * 空文字のBean名を渡した場合、SpringMessageResourcesが生成されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources02() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // テスト実施
        MessageResources resources = factory.createResources("");
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                    UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testCreateResources03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:null<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config=null<br>
     *                    returnNull=MessageResourcesFactory.getReturnNull()<br>
     *                    messageSource=前提条件でセットしたapplicationContext<br>
     *         
     * <br>
     * nullのBean名を渡した場合、SpringMessageResourcesが生成されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources03() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // テスト実施
        MessageResources resources = factory.createResources(null);
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals(null, UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                    UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testCreateResources04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:"messageSource"（存在するBean名）<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesFacotryTest01.xml<br>
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config="messageSource"<br>
     *                    returnNull=MessageResourcesFactory.getReturnNull()<br>
     *                    messageSource=MessageSourceのサブクラス<br>
     *         
     * <br>
     * 存在するBean名を渡した場合、SpringMessageResourcesが生成されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateResources04() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // テスト実施
        MessageResources resources = factory.createResources("messageSource");
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("messageSource", UTUtil.getPrivateField(resources, "config"));
        assertEquals(factory.getReturnNull(),
                    UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
    }
}
