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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.struts.MessageFormatCloneReturnIfUseDateFormatMap;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.spring.mock.MockWebApplicationContext;
import junit.framework.TestCase;

import org.apache.struts.util.MessageResources;
import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * {@link SpringMessageResources} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * SpringのメッセージソースをStrutsから利用するMessageResources実装クラス。<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.util.SpringMessageResources
 */
public class SpringMessageResourcesTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SpringMessageResourcesTest.class);
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
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void setUpData() throws Exception {
        // ContextLoader.getCurrentWebApplicationContext()の返却値に
        // モックのApplicationContextをセットしておく。
        Map currentContextPerThread = (Map) UTUtil.getPrivateField(
                ContextLoader.class, "currentContextPerThread");
        String[] configLocations = new String[] {
        "jp/terasoluna/fw/web/struts/util/SpringMessageResourcesTest01.xml"};
        this.context = new MockWebApplicationContext(configLocations);
        currentContextPerThread.put(
            Thread.currentThread().getContextClassLoader(), this.context);
    }

    /**
     * 終了処理を行う.
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
        this.context = null;
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryString01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:SpringMessageResourcesFactoryインスタンス<br>
     *         (引数) config:"propertyMessageSource01"（存在するBean名）<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config="propertyMessageSource01"<br>
     *                    messageSource=MessageSourceのサブクラス<br>
     *                    formats=HashMapインスタンス<br>
     *         
     * <br>
     * 存在するBean名を渡した場合、initMessageSource()が呼び出されることを確認。<br>
     * MessageFormatCacheMapFactory#getInstanceの戻り値がnullのとき、
     * formatsは差し替えられないことを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSpringMessageResourcesMessageResourcesFactoryString01() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "disable");
        
        // テスト実施
        MessageResources resources = 
            new SpringMessageResources(factory, "propertyMessageSource01");
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("propertyMessageSource01", UTUtil.getPrivateField(resources, "config"));
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryString02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：AC
     * <br><br>
     * 入力値：(引数) factory:SpringMessageResourcesFactoryインスタンス<br>
     *         (引数) config:""（空文字）<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config=""<br>
     *                    messageSource=前提条件でセットしたapplicationContext<br>
     *                    formats=MessageFormatCloneReturnIfUseDateFormatMapインスタンス<br>
     *         
     * <br>
     * 空文字のBean名を渡した場合、initMessageSource()が呼び出されることを確認。<br>
     * MessageFormatCacheMapFactory#getInstanceの戻り値がnull以外のとき、
     * formatsがその戻り値に差し替えられることを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSpringMessageResourcesMessageResourcesFactoryString02() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // テスト実施
        MessageResources resources = 
            new SpringMessageResources(factory, "");
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryString03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:SpringMessageResourcesFactoryインスタンス<br>
     *         (引数) config:null<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config=null<br>
     *                    messageSource=前提条件でセットしたapplicationContext<br>
     *         
     * <br>
     * nullのBean名を渡した場合、initMessageSource()が呼び出されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSpringMessageResourcesMessageResourcesFactoryString03() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // テスト実施
        MessageResources resources = 
            new SpringMessageResources(factory, null);
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryStringboolean01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:SpringMessageResourcesFactoryインスタンス<br>
     *         (引数) config:"propertyMessageSource01"（存在するBean名）<br>
     *         (引数) returnNull:true<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config="propertyMessageSource01"<br>
     *                    returnNull=true<br>
     *                    messageSource=MessageSourceのサブクラス<br>
     *                    formats=HashMapインスタンス<br>
     *         
     * <br>
     * 存在するBean名を渡した場合、initMessageSource()が呼び出されることを確認。<br>
     * MessageFormatCacheMapFactory#getInstanceの戻り値がnullのとき、
     * formatsは差し替えられないことを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSpringMessageResourcesMessageResourcesFactoryStringboolean01() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "disable");
        
        // テスト実施
        MessageResources resources = 
            new SpringMessageResources(factory, "propertyMessageSource01" ,true);
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("propertyMessageSource01", UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), HashMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryStringboolean02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：AC
     * <br><br>
     * 入力値：(引数) factory:SpringMessageResourcesFactoryインスタンス<br>
     *         (引数) config:""（空文字）<br>
     *         (引数) returnNull:false<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config=""<br>
     *                    returnNull=false<br>
     *                    messageSource=前提条件でセットしたapplicationContext<br>
     *                    formats=MessageFormatCloneReturnIfUseDateFormatMapインスタンス<br>
     *         
     * <br>
     * 空文字のBean名を渡した場合、initMessageSource()が呼び出されることを確認。<br>
     * MessageFormatCacheMapFactory#getInstanceの戻り値がnull以外のとき、
     * formatsがその戻り値に差し替えられることを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSpringMessageResourcesMessageResourcesFactoryStringboolean02() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // テスト実施
        MessageResources resources = 
            new SpringMessageResources(factory, "", false);
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertEquals("", UTUtil.getPrivateField(resources, "config"));
        assertEquals(false, UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
        assertEquals(UTUtil.getPrivateField(resources, "formats").getClass(), MessageFormatCloneReturnIfUseDateFormatMap.class);
    }
    
    /**
     * testSpringMessageResourcesMessageResourcesFactoryStringboolean03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) factory:SpringMessageResourcesFactoryインスタンス<br>
     *         (引数) config:null<br>
     *         (引数) returnNull:true<br>
     *         (前提条件) applicationContext:モックのApplicationContext<br>
     *         (前提条件) ContextLoader.getCurrentWebApplicationContext()の返却値:
     *                    applicationContextと同じモックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * 期待値：(戻り値) SpringMessageResources:<br>
     *                    factory=戻り値を生成したSpringMessageResourcesFactoryインスタンス自身<br>
     *                    config=null<br>
     *                    returnNull=true<br>
     *                    messageSource=前提条件でセットしたapplicationContext<br>
     *         
     * <br>
     * nullのBean名を渡した場合、initMessageSource()が呼び出されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSpringMessageResourcesMessageResourcesFactoryStringboolean03() throws Exception {
        // 前処理
        SpringMessageResourcesFactory factory = new SpringMessageResourcesFactory();
        
        // テスト実施
        MessageResources resources = 
            new SpringMessageResources(factory, null, true);
        
        // 判定
        assertEquals(resources.getClass().getName(),
                SpringMessageResources.class.getName());
        assertSame(factory, UTUtil.getPrivateField(resources, "factory"));
        assertNull(UTUtil.getPrivateField(resources, "config"));
        assertEquals(true, UTUtil.getPrivateField(resources, "returnNull"));
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testInitMessageSource01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(引数) なし
     *         (前提条件) factory:not null<br>
     *         (前提条件) config:""（空文字）<br>
     *         (前提条件) returnNull:true<br> 
     *         (前提条件) context:null<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * 期待値：(戻り値) なし<br>
     *         (状態変化) messageSource:-<br>
     *         (状態変化) 例外:-<br>
     *         (状態変化) ログ:【WARNログ】"ApplicationContext is not found."<br>
     *         
     * <br>
     * contextがnullの場合、WARNログが出力されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMessageSource01() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // contextをnullに初期化する。
        UTUtil.setPrivateField(resources, "context", null);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // 判定
        assertTrue(LogUTUtil.checkWarn("ApplicationContext is not found."));
    }
    
    /**
     * testInitMessageSource02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) なし
     *         (前提条件) factory:not null<br>
     *         (前提条件) config:""（空文字）<br>
     *         (前提条件) returnNull:true<br> 
     *         (前提条件) context:モックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * 期待値：(戻り値) なし<br>
     *         (状態変化) messageSource:前提条件でセットしたapplicationContext<br>
     *         (状態変化) 例外:-<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * configが空文字の場合、デフォルトのメッセージソースがセットされることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMessageSource02() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // 一度messageSourceを初期化する。
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // 判定
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testInitMessageSource03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) なし
     *         (前提条件) factory:not null<br>
     *         (前提条件) config:null<br>
     *         (前提条件) returnNull:true<br> 
     *         (前提条件) context:モックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * 期待値：(戻り値) なし<br>
     *         (状態変化) messageSource:前提条件でセットしたapplicationContext<br>
     *         (状態変化) 例外:-<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * configがnullの場合、デフォルトのメッセージソースがセットされることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMessageSource03() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), null ,true);
        // 一度messageSourceを初期化する。
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // 判定
        assertSame(this.context, UTUtil.getPrivateField(resources, "messageSource"));
    }
    
    /**
     * testInitMessageSource04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) なし
     *         (前提条件) factory:not null<br>
     *         (前提条件) config:"propertyMessageSource01"（存在するBean名）<br>
     *         (前提条件) returnNull:true<br> 
     *         (前提条件) context:モックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * 期待値：(戻り値) なし<br>
     *         (状態変化) messageSource:MessageSourceのサブクラス<br>
     *         (状態変化) 例外:-<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * configが存在するBean名の場合、メッセージソースがセットされることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMessageSource04() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        // 一度messageSourceを初期化する。
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // テスト実施
        UTUtil.invokePrivate(resources, "initMessageSource");
        
        // 判定
        assertTrue(UTUtil.getPrivateField(resources, "messageSource") instanceof MessageSource);
    }
    
    /**
     * testInitMessageSource05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) なし
     *         (前提条件) factory:not null<br>
     *         (前提条件) config:"notExistBeanName"（存在しないBean名）<br>
     *         (前提条件) returnNull:true<br> 
     *         (前提条件) context:モックのApplicationContext<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         
     * <br>
     * 期待値：(戻り値) なし<br>
     *         (状態変化) messageSource:MessageSourceのサブクラス<br>
     *         (状態変化) 例外:SystemException：<br>
     *                    エラーコード："errors.message.bean.exception"<br>
     *                    ラップした例外：BeansExceptionのサブクラス<br>
     *         (状態変化) ログ:【ERRORログ】"notExistBeanName is not found or it is not MessageSource instance."<br>
     *         
     * <br>
     * configが存在しないBean名の場合、エラーログを出力し、
     * 例外がスローされることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInitMessageSource05() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // 一度messageSourceを初期化する。
        UTUtil.setPrivateField(resources, "messageSource", null);
        UTUtil.setPrivateField(resources, "config", "notExistBeanName");
        
        // テスト実施
        try {
            UTUtil.invokePrivate(resources, "initMessageSource");
            fail();
        } catch (SystemException e) {
            // 判定
            assertEquals("errors.message.bean.exception", e.getErrorCode());
            assertTrue(e.getCause() instanceof BeansException);
            assertTrue(LogUTUtil.checkError("notExistBeanName is not found or it is not MessageSource instance."));
        }
    }
    
    /**
     * testGetMessage01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:null<br>
     *         (引数) key:"property.message.key"<br>
     *         (前提条件) config:""（空文字）<br>
     *         (前提条件) messageSource:null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * messageSourceがnullでreturnNullがtrueの場合、nullが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage01() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        // messageSourceをnullに初期化する。
        UTUtil.setPrivateField(resources, "messageSource", null);
        
        // テスト実施
        String message = resources.getMessage((Locale) null, "property.message.key");
        
        // 判定
        assertNull(message);
    }
    
    /**
     * testGetMessage02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:null<br>
     *         (引数) key:"not.exist.key"（存在しないキー）<br>
     *         (前提条件) config:"propertyMessageSource01"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * localeがnullでreturnNullがtrueの場合、デフォルトロケールに存在しないキーを
     * 渡すとnullが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage02() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // テスト実施
        String message = resources.getMessage((Locale) null, "not.exist.key");
        
        // 判定
        assertNull(message);
    }
    
    /**
     * testGetMessage03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.getDefault()<br>
     *         (引数) key:"not.exist.key"（存在しないキー）<br>
     *         (前提条件) config:"propertyMessageSource01"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * localeがデフォルトでreturnNullがtrueの場合、デフォルトロケールに存在しないキーを
     * 渡すとnullが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage03() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.getDefault(), "not.exist.key");
        
        // 判定
        assertNull(message);
    }
    
    /**
     * testGetMessage04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.ENGLISH<br>
     *         (引数) key:"not.exist.key_en"（存在しないキー）<br>
     *         (前提条件) config:"propertyMessageSource_locale_en"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest02_en.properties<br>
     *         
     * <br>
     * 期待値：(戻り値) String:null<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * localeを指定してreturnNullがtrueの場合、指定したロケールに存在しないキーを
     * 渡すとnullが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage04() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource_locale_en" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.ENGLISH, "not.exist.key_en");
        
        // 判定
        assertNull(message);
    }
    
    /**
     * testGetMessage05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:null<br>
     *         (引数) key:"property.message.key"<br>
     *         (前提条件) config:"propertyMessageSource01"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * localeがnullの場合、正常にメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage05() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // テスト実施
        String message = resources.getMessage((Locale) null, "property.message.key");
        
        // 判定
        assertEquals("テストメッセージ", message);
    }
    
    /**
     * testGetMessage06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.getDefault()<br>
     *         (引数) key:"property.message.key"<br>
     *         (前提条件) config:"propertyMessageSource01"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * デフォルトロケールで正常にメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage06() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.getDefault(), "property.message.key");
        
        // 判定
        assertEquals("テストメッセージ", message);
    }
    
    /**
     * testGetMessage07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.ENGLISH<br>
     *         (引数) key:"property.message.key"<br>
     *         (前提条件) config:"propertyMessageSource_locale_en"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest02_en.properties<br>
     * <br>
     * 期待値：(戻り値) String:"test message"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * 指定したロケールで正常にメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage07() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource_locale_en" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.ENGLISH, "property.message.key");
        
        // 判定
        assertEquals("test message", message);
    }
    
    /**
     * testGetMessage08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:null<br>
     *         (引数) key:"not.exist.key"（存在しないキー）<br>
     *         (前提条件) config:"propertyMessageSource01"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:false<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     * <br>
     * 期待値：(戻り値) String:"???.not.exist.key???"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * localeがnullでreturnNullがfalseの場合、デフォルトロケールに存在しないキーを
     * 渡すとメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage08() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,false);
        
        // テスト実施
        String message = resources.getMessage((Locale) null, "not.exist.key");
        
        // 判定
        assertEquals("???.not.exist.key???", message);
    }
    
    /**
     * testGetMessage09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.getDefault()<br>
     *         (引数) key:"not.exist.key"（存在しないキー）<br>
     *         (前提条件) config:"propertyMessageSource01"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:false<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     * <br>
     * 期待値：(戻り値) String:"???.not.exist.key???"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * localeがデフォルトでreturnNullがfalseの場合、デフォルトロケールに存在しないキーを
     * 渡すとメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage09() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource01" ,false);
        
        // テスト実施
        String message = resources.getMessage(Locale.getDefault(), "not.exist.key");
        
        // 判定
        assertEquals("???ja_JP.not.exist.key???", message);
    }
    
    /**
     * testGetMessage10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.ENGLISH<br>
     *         (引数) key:"not.exist.key_en"（存在しないキー）<br>
     *         (前提条件) config:"propertyMessageSource_locale_en"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:false<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest02_en.properties<br>
     * <br>
     * 期待値：(戻り値) String:"???en.not.exist.key_en???"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * localeを指定してreturnNullがfalseの場合、指定したロケールに存在しないキーを
     * 渡すとメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage10() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource_locale_en" ,false);
        
        // テスト実施
        String message = resources.getMessage(Locale.ENGLISH, "not.exist.key_en");
        
        // 判定
        assertEquals("???en.not.exist.key_en???", message);
    }
    
    /**
     * testGetMessage11()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.getDefault()<br>
     *         (引数) key:"property.message.key"<br>
     *         (前提条件) config:"dbMessageSource01"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         (前提条件) DBメッセージ:SpringMessageResourcesTest_DataSourceMessageSourceStub01<br>
     * <br>
     * 期待値：(戻り値) String:"テストDBメッセージ"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * 第一メッセージソースにDBメッセージソース、第二メッセージソースに
     * プロパティメッセージソースを指定した場合、双方のメッセージソースに
     * 共通に存在しているメッセージキーを渡すと、第一メッセージソースから
     * 正常にメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage11() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "dbMessageSource01" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.getDefault(), "property.message.key");
        
        // 判定
        assertEquals("テストDBメッセージ", message);
    }
    
    /**
     * testGetMessage12()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.getDefault()<br>
     *         (引数) key:"property.message.key"<br>
     *         (前提条件) config:"dbMessageSource02"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         (前提条件) DBメッセージ:SpringMessageResourcesTest_DataSourceMessageSourceStub02<br>
     * <br>
     * 期待値：(戻り値) String:"テストメッセージ"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * 第一メッセージソースにDBメッセージソース、第二メッセージソースに
     * プロパティメッセージソースを指定した場合、第一メッセージソースに
     * 存在しないメッセージキーを渡すと、第二メッセージソースから
     * 正常にメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage12() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "dbMessageSource02" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.getDefault(), "property.message.key");
        
        // 判定
        assertEquals("テストメッセージ", message);
    }
    
    /**
     * testGetMessage13()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.getDefault()<br>
     *         (引数) key:"db.message.key"<br>
     *         (前提条件) config:"propertyMessageSource02"<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     *         (前提条件) DBメッセージ:SpringMessageResourcesTest_DataSourceMessageSourceStub02<br>
     * <br>
     * 期待値：(戻り値) String:"テストDBメッセージ"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * 第一メッセージソースにプロパティメッセージソース、第二メッセージソースに
     * DBメッセージソースを指定した場合、第一メッセージソースに
     * 存在しないメッセージキーを渡すと、第二メッセージソースから
     * 正常にメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage13() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "propertyMessageSource02" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.getDefault(), "db.message.key");
        
        // 判定
        assertEquals("テストDBメッセージ", message);
    }
    
    /**
     * testGetMessage14()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C,F
     * <br><br>
     * 入力値：(引数) locale:Locale.getDefault()<br>
     *         (引数) key:"default.message.key"<br>
     *         (前提条件) config:""（空文字）<br>
     *         (前提条件) messageSource:not null<br>
     *         (前提条件) returnNull:true<br>
     *         (前提条件) Bean定義ファイル:SpringMessageResourcesTest01.xml<br>
     *         (前提条件) メッセージプロパティファイル:SpringMessageResourcesTest01.properties<br>
     * <br>
     * 期待値：(戻り値) String:"デフォルトメッセージ"<br>
     *         (状態変化) ログ:-<br>
     *         
     * <br>
     * configが空文字であった場合、Bean名"messageSource"であるメッセージソースを
     * デフォルトで取得し、正常にメッセージが返却されることを確認
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetMessage14() throws Exception {
        // 前処理
        MessageResources resources = 
            new SpringMessageResources(
                    new SpringMessageResourcesFactory(), "" ,true);
        
        // テスト実施
        String message = resources.getMessage(Locale.getDefault(), "default.message.key");
        
        // 判定
        assertEquals("デフォルトメッセージ", message);
    }
}
