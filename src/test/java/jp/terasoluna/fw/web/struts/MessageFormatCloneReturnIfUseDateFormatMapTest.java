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

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link MessageFormatCloneReturnIfUseDateFormatMap} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * Strutsのバグ(STR-2172)回避用HashMap(MessageFormatキャッシュ)。<br>
 * 日付時刻系サブフォーマットを使用したMessageFormatのみ、cloneして返す。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.MessageFormatCloneReturnIfUseDateFormatMap
 */
public class MessageFormatCloneReturnIfUseDateFormatMapTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     *
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MessageFormatCloneReturnIfUseDateFormatMapTest.class);
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
    public MessageFormatCloneReturnIfUseDateFormatMapTest(String name) {
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
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) エントリーにあるMessageFormat<br>
     *         
     * <br>
     * cloneReturnMapでない方のエントリーに存在するものは、
     * cloneされずに返却されることを確認する。<br>
     * ※前処理にて、put実行後、cloneReturnMapを空のMapに差し替えて正常動作することにより、
     *   前処理にて事前状態(エントリー)を作り出せていることを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet01() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("xxx");
        map.put("key1", format);
        UTUtil.setPrivateField(map, "cloneReturnMap", new MessageFormatCloneReturnMap());
        
        // テスト実施
        MessageFormat ret = map.get("key1");
        
        // 判定
        assertSame(format, ret);
    }

    /**
     * testGet02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) "key1"
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:エントリー[key1 : MessageFormat]
     *         
     * <br>
     * 期待値：(戻り値) cloneReturnMapのエントリーにあるMessageFormatと等価な別インスタンス<br>
     *         
     * <br>
     * 自身のエントリーにない場合、
     * cloneReturnMapのエントリーから取得することを確認する。<br>
     * cloneReturnMapのエントリーから取得したインスタンスは、
     * cloneReturnMapのエントリーにあるものではなく、それと等価な別インスタンスであることを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet02() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("xxx{0,date}");
        MessageFormatCloneReturnMap cloneReturnMap = new MessageFormatCloneReturnMap();
        cloneReturnMap.put("key1", format);
        UTUtil.setPrivateField(map, "cloneReturnMap", cloneReturnMap);
        
        // テスト実施
        MessageFormat ret = map.get("key1");
        
        // 判定
        assertNotSame(format, ret);
        assertEquals(format, ret);
    }

    /**
     * testGet03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) "key1"
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         
     * <br>
     * キーに該当するエントリーが、自身にもcloneReturnMapにも存在しない場合、
     * nullを返却することを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGet03() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        
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
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) エントリー[key1 : 引数で渡したMessageFormat]
     *         (状態変化) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * フォーマット文字列にプレースホルダが存在しないとき、
     * cloneReturnMapではなく、自身にputする(スーパークラスの機能)ことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが同一インスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut01() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) エントリー[key1 : 引数で渡したMessageFormat]
     *         (状態変化) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * フォーマット文字列にサブフォーマットを指定しないプレースホルダのみが存在するとき、
     * cloneReturnMapではなく、自身にputする(スーパークラスの機能)ことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが同一インスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut02() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0,date}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) key1のエントリーなし
     *         (状態変化) cloneReturnMap:エントリー[key1 : 引数で渡したMessageFormat]
     *         
     * <br>
     * フォーマット文字列に日付時刻系サブフォーマットを指定したプレースホルダのみが存在するとき、
     * cloneReturnMapにputし、自身にはputしないことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが異なる等価なインスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut03() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,date}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

    /**
     * testPut04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0,time}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) key1のエントリーなし
     *         (状態変化) cloneReturnMap:エントリー[key1 : 引数で渡したMessageFormat]
     *         
     * <br>
     * フォーマット文字列に日付時刻系サブフォーマットを指定したプレースホルダのみが存在するとき、
     * cloneReturnMapにputし、自身にはputしないことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが異なる等価なインスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut04() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,date}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

    /**
     * testPut05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0,number}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) エントリー[key1 : 引数で渡したMessageFormat]
     *         (状態変化) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * フォーマット文字列に数値系サブフォーマットを指定したプレースホルダのみが存在するとき、
     * cloneReturnMapではなく、自身にputする(スーパークラスの機能)ことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが同一インスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
    * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut05() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,number}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0,choice,0#zero | 1#one}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) エントリー[key1 : 引数で渡したMessageFormat]
     *         (状態変化) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * フォーマット文字列に選択型サブフォーマットを指定したプレースホルダのみが存在するとき、
     * cloneReturnMapではなく、自身にputする(スーパークラスの機能)ことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが同一インスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
    * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut06() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0,choice,0#zero | 1#one}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0}bbb{1,number}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) エントリー[key1 : 引数で渡したMessageFormat]
     *         (状態変化) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * フォーマット文字列に日付時刻系サブフォーマット以外を指定したプレースホルダが複数存在するとき、
     * cloneReturnMapではなく、自身にputする(スーパークラスの機能)ことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが同一インスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
    * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut07() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0}bbb{1,number}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0}bbb{1,date}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) null<br>
     *         (状態変化) エントリー[key1 : 引数で渡したMessageFormat]
     *         (状態変化) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * フォーマット文字列に日付時刻系サブフォーマットを指定したプレースホルダが含まれ、プレースホルダがあわせて複数存在するとき、
     * cloneReturnMapにputし、自身にはputしないことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが異なる等価なインスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
    * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut08() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat format = new MessageFormat("aaa{0}bbb{1,date}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertNull(ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

    /**
     * testPut09()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa"<br>
     *         (状態) エントリー[key1 : MessageFormat]
     *         (状態) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * 期待値：(戻り値) もともとエントリーにあったMessageFormat<br>
     *         (状態変化) エントリー[key1 : 引数で渡したMessageFormat]
     *         (状態変化) cloneReturnMap:key1のエントリーなし
     *         
     * <br>
     * putの戻り値仕様(以前にキャッシュしていた値を返す)を満たしていることを確認する。<br>
     * フォーマット文字列にプレースホルダが存在しないとき、
     * cloneReturnMapではなく、自身にputする(スーパークラスの機能)ことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが同一インスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut09() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat old = new MessageFormat("xxx");
        map.put("key1", old);
        MessageFormat format = new MessageFormat("aaa");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertSame(old, ret);
        assertSame(format, map.get("key1"));
    }

    /**
     * testPut10()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) key:"key1"<br>
     *         (引数) value:MessageFormatインスタンス<br>
     *                      フォーマット文字列:"aaa{0,date}"<br>
     *         (状態) key1のエントリーなし
     *         (状態) cloneReturnMap:エントリー[key1 : MessageFormat]
     *         
     * <br>
     * 期待値：(戻り値) もともとエントリーにあったMessageFormat<br>
     *         (状態変化) key1のエントリーなし
     *         (状態変化) cloneReturnMap:エントリー[key1 : 引数で渡したMessageFormat]
     *         
     * <br>
     * putの戻り値仕様(以前にキャッシュしていた値を返す)を満たしていることを確認する。<br>
     * フォーマット文字列に日付時刻系サブフォーマットを指定したプレースホルダのみが存在するとき、
     * cloneReturnMapにputし、自身にはputしないことを確認する。<br>
     * ※getメソッドの動作が正しい前提で、getが異なる等価なインスタンスを返すことで、
     *   putの格納先振り分けが正常動作したと判断する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testPut10() throws Exception {
        // 前処理
        MessageFormatCloneReturnIfUseDateFormatMap map = new MessageFormatCloneReturnIfUseDateFormatMap();
        MessageFormat old = new MessageFormat("xxx{0,date}");
        map.put("key1", old);
        MessageFormat format = new MessageFormat("aaa{0,date}");
        
        // テスト実施
        MessageFormat ret = map.put("key1", format);
        
        // 判定
        assertEquals(old, ret);
        assertNotSame(format, map.get("key1"));
        assertEquals(format, map.get("key1"));
    }

}
