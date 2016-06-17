/*
 * Copyright (c) 2011 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts;

import java.text.MessageFormat;
import java.util.HashMap;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link MessageFormatCacheMapFactory} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Strutsのバグ(STR-2172)回避用HashMap(MessageFormatキャッシュ)のファクトリクラス。<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.MessageFormatCacheMapFactory
 */
public class MessageFormatCacheMapFactoryTest extends PropertyTestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageFormatCacheMapFactoryTest.class);
    }
    
    /**
     * 初期化処理を行う.
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
    }

    /**
     * 終了処理を行う.
     *
     * @throws Exception このメソッドで発生した例外
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }
    
    /**
     * testGetInstance01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(前提条件) messageResources.messageFormatClone:なし<br>
     *         
     * <br>
     * 期待値：(戻り値) MessageFormatCloneReturnIfUseDateFormatMapインスタンス<br>
     *         
     * <br>
     * messageResources.messageFormatCloneの設定が無い場合、
     * MessageFormatCloneReturnIfUseDateFormatMapが返却されることを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInstance01() throws Exception {
        // 前処理
        deleteProperty("messageResources.messageFormatClone");
        
        // テスト実施
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // 判定
        assertEquals(MessageFormatCloneReturnIfUseDateFormatMap.class, map.getClass());
    }

    /**
     * testGetInstance02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(前提条件) messageResources.messageFormatClone:"enable"<br>
     *         
     * <br>
     * 期待値：(戻り値) MessageFormatCloneReturnMapインスタンス<br>
     *         
     * <br>
     * messageResources.messageFormatCloneが"enable"の場合、
     * MessageFormatCloneReturnMapが返却されることを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInstance02() throws Exception {
        // 前処理
        addProperty("messageResources.messageFormatClone", "enable");
        
        // テスト実施
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // 判定
        assertEquals(MessageFormatCloneReturnMap.class, map.getClass());
    }

    /**
     * testGetInstance03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(前提条件) messageResources.messageFormatClone:"dateFormatOnly"<br>
     *         
     * <br>
     * 期待値：(戻り値) MessageFormatCloneReturnIfUseDateFormatMapインスタンス<br>
     *         
     * <br>
     * messageResources.messageFormatCloneが"dateFormatOnly"の場合、
     * MessageFormatCloneReturnIfUseDateFormatMapが返却されることを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInstance03() throws Exception {
        // 前処理
        addProperty("messageResources.messageFormatClone", "dateFormatOnly");
        
        // テスト実施
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // 判定
        assertEquals(MessageFormatCloneReturnIfUseDateFormatMap.class, map.getClass());
    }

    /**
     * testGetInstance04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(前提条件) messageResources.messageFormatClone:"disable"<br>
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         
     * <br>
     * messageResources.messageFormatCloneが"disable"の場合、
     * nullが返却されることを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInstance04() throws Exception {
        // 前処理
        addProperty("messageResources.messageFormatClone", "disable");
        
        // テスト実施
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // 判定
        assertNull(map);
    }

    /**
     * testGetInstance05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：F
     * <br><br>
     * 入力値：(前提条件) messageResources.messageFormatClone:"xxx"<br>
     *         
     * <br>
     * 期待値：(戻り値) MessageFormatCloneReturnIfUseDateFormatMapインスタンス<br>
     *         (状態変化) ログ:＜ログ＞<br>
     *                    警告ログ：<br>
     *                    "messageResources.messageFormatClone = xxx is invalid. set \"enable\", \"disable\" or \"dateFormatOnly\"."<br>
     *                    警告ログ：<br>
     *                    "use MessageFormatCloneReturnAtDateFormatOnlyMap."<br>
     *         
     * <br>
     * messageResources.messageFormatCloneが"dateFormatOnly"の場合、
     * MessageFormatCloneReturnIfUseDateFormatMapが返却されることを確認。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetInstance05() throws Exception {
        // 前処理
        addProperty("messageResources.messageFormatClone", "xxx");
        
        // テスト実施
        HashMap<String,MessageFormat> map = MessageFormatCacheMapFactory.getInstance();
        
        // 判定
        assertEquals(MessageFormatCloneReturnIfUseDateFormatMap.class, map.getClass());
        assertTrue(LogUTUtil.checkWarn("messageResources.messageFormatClone = xxx is invalid. set \"enable\", \"disable\" or \"dateFormatOnly\"."));
        assertTrue(LogUTUtil.checkWarn("use MessageFormatCloneReturnAtDateFormatOnlyMap."));
    }

}
