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

import junit.framework.TestCase;

/**
 * {@link MessageFormatCloneReturnMap} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Strutsのバグ(STR-2172)回避用HashMap(MessageFormatキャッシュ)。<br>
 * getの際、cloneして返す。DecimalFormat#cloneの問題を回避するため、put時にもcloneする。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.MessageFormatCloneReturnMap
 */
public class MessageFormatCloneReturnMapTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageFormatCloneReturnMapTest.class);
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
    public MessageFormatCloneReturnMapTest(String name) {
        super(name);
    }

    /**
     * testGet01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) "key1"
     *         (状態) エントリー[key1 : MessageFormat]
     *         
     * <br>
     * 期待値：(戻り値) エントリーにあるMessageFormatと等価な別インスタンス<br>
     *                  2回目実行時には、1回目と等価な別インスタンス
     * <br>
     * エントリーに存在するものは、cloneして返却することを確認する。<br>
     * ※putもgetもcloneするため、putしてgetしただけでは、仮にgetがcloneしていなくても判断がつかない。<br>
     *   よって、ここでは、getを2回実行して、1回目と2回目の戻り値が、等価な別インスタンスであることを確認することで、
     *   get時にcloneされていることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet01() {
        // 前処理
        MessageFormatCloneReturnMap map = new MessageFormatCloneReturnMap();
        MessageFormat format = new MessageFormat("xxx");
        map.put("key1", format);
        
        // テスト実施
        MessageFormat ret1 = map.get("key1");
        MessageFormat ret2 = map.get("key1");
        
        // 判定
        assertNotSame(format, ret1);
        assertEquals(format, ret1);
        assertNotSame(ret1, ret2);
        assertEquals(ret1, ret2);
    }

    /**
     * testGet02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) "key1"
     *         (状態) key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         
     * <br>
     * キーに該当するエントリーが存在しない場合、nullを返却することを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet02() {
        // 前処理
        MessageFormatCloneReturnMap map = new MessageFormatCloneReturnMap();
        
        // テスト実施
        MessageFormat ret = map.get("key1");
        
        // 判定
        assertNull(ret);
    }

    /**
     * testPut01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"
     *         (引数) value:MessageFormat
     *         (状態) 同一のkey, valueインスタンスで一度実行済み
     *         
     * <br>
     * 期待値：(戻り値) 引数valueで渡したMessageFormatと等価な別インスタンス<br>
     *         
     * <br>
     * putする際、cloneしてキャッシュすることを確認する。<br>
     * ※putもgetもcloneするため、putしてgetしたのでは、仮にputがcloneインスタンスをキャッシュしていなくても判断がつかない。<br>
     *   よって、ここでは、putの戻り値(戻り値については拡張していない)を利用し、
     *   putの戻り値が、あらかじめ引数valueに渡したインスタンスとは異なる等価なインスタンスであることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut01() {
        // 前処理
        MessageFormatCloneReturnMap map = new MessageFormatCloneReturnMap();
        MessageFormat format = new MessageFormat("xxx");
        map.put("key1", format);
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNotSame(format, ret);
        assertEquals(format, ret);
    }
}
