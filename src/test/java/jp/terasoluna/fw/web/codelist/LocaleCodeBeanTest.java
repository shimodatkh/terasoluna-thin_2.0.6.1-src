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

package jp.terasoluna.fw.web.codelist;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.LocaleCodeBean} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * コード、コード値(表示文字列)とロケール情報（言語コード、国コード、バリアント）
 * を保持するBeanクラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.CodeBean
 * 
 */
public class LocaleCodeBeanTest extends TestCase {
    /**
     * このテストケースを実行する為の GUI アプリケーションを起動する。
     * 
     * @param args
     *            java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(LocaleCodeBeanTest.class);
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
    public LocaleCodeBeanTest(String name) {
        super(name);
    }

    /**
     * testSetId01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) id:"id"<br>
     * (状態) id:null<br>
     * 
     * <br>
     * 期待値：(状態変化) id:"id"<br>
     * 
     * <br>
     * 引数に指定された文字列がインスタンス変数idに設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetId01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();

        // テスト実施
        codeBean.setId("id");
        // 判定
        assertEquals("id", UTUtil.getPrivateField(codeBean, "id"));
    }

    /**
     * testSetId02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) id:null<br>
     * (状態) id:null<br>
     * 
     * <br>
     * 期待値：(状態変化) id:""<br>
     * 
     * <br>
     * 引数のidがnullの場合、空文字が設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetId02() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setId(null);
        // 判定
        assertEquals("", UTUtil.getPrivateField(codeBean, "id"));
    }

    /**
     * testGetId01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) id:"id"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"id"<br>
     * 
     * <br>
     * インスタンス変数idの値が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetId01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "id", "id");
        
        // テスト実施・判定
        assertEquals("id", codeBean.getId());
    }

    /**
     * testSetName01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) name:"name"<br>
     * (状態) name:null<br>
     * 
     * <br>
     * 期待値：(状態変化) name:"name"<br>
     * 
     * <br>
     * 引数に指定された文字列がインスタンス変数nameに設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetName01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setName("name");
        // 判定
        assertEquals("name", UTUtil.getPrivateField(codeBean, "name"));
    }

    /**
     * testSetName02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) name:null<br>
     * (状態) name:null<br>
     * 
     * <br>
     * 期待値：(状態変化) name:""<br>
     * 
     * <br>
     * 引数のnameがnullの場合、空文字が設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetName02() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setName(null);
        // 判定
        assertEquals("", UTUtil.getPrivateField(codeBean, "name"));
    }

    /**
     * testGetName01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) name:"name"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"name"<br>
     * 
     * <br>
     * インスタンス変数nameの値が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetName01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "name", "name");
        
        // テスト実施・判定
        assertEquals("name", codeBean.getName());
    }

    /**
     * testSetLanguage01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) language:"language"<br>
     * (状態) language:null<br>
     * 
     * <br>
     * 期待値：(状態変化) language:"language"<br>
     * 
     * <br>
     * 引数に指定された文字列がインスタンス変数languageに
     * 設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetLanguage01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setLanguage("language");
        // 判定
        assertEquals("language", UTUtil.getPrivateField(codeBean, "language"));
    }

    /**
     * testSetLanguage02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) language:null<br>
     * (状態) language:null<br>
     * 
     * <br>
     * 期待値：(状態変化) language:""<br>
     * 
     * <br>
     * 引数のlanguageがnullの場合、空文字が設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetLanguage02() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setLanguage(null);
        // 判定
        assertEquals("", UTUtil.getPrivateField(codeBean, "language"));
    }

    /**
     * testGetLanguage01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) language:"language"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"language"<br>
     * 
     * <br>
     * インスタンス変数languageの値が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetLanguage01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "language", "language");
        
        // テスト実施・判定
        assertEquals("language", codeBean.getLanguage());
    }
    
    /**
     * testSetCountry01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) country:"country"<br>
     * (状態) country:null<br>
     * 
     * <br>
     * 期待値：(状態変化) country:"country"<br>
     * 
     * <br>
     * 引数に指定された文字列がインスタンス変数countryに
     * 設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetCountry01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setCountry("country");
        // 判定
        assertEquals("country", UTUtil.getPrivateField(codeBean, "country"));
    }

    /**
     * testSetCountry02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) country:null<br>
     * (状態) country:null<br>
     * 
     * <br>
     * 期待値：(状態変化) country:""<br>
     * 
     * <br>
     * 引数のcountryがnullの場合、空文字が設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetCountry02() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setCountry(null);
        // 判定
        assertEquals("", UTUtil.getPrivateField(codeBean, "country"));
    }

    /**
     * testGetCountry01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) country:"country"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"country"<br>
     * 
     * <br>
     * インスタンス変数countryの値が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetCountry01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "country", "country");
        
        // テスト実施・判定
        assertEquals("country", codeBean.getCountry());
    }
    
    /**
     * testSetVariant01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) variant:"variant"<br>
     * (状態) name:null<br>
     * 
     * <br>
     * 期待値：(状態変化) variant:"variant"<br>
     * 
     * <br>
     * 引数に指定された文字列がインスタンス変数variantに
     * 設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetVariant01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setVariant("variant");
        // 判定
        assertEquals("variant", UTUtil.getPrivateField(codeBean, "variant"));
    }

    /**
     * testSetVariant02() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(引数) variant:null<br>
     * (状態) variant:null<br>
     * 
     * <br>
     * 期待値：(状態変化) variant:""<br>
     * 
     * <br>
     * 引数のvariantがnullの場合、空文字が設定されることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testSetVariant02() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        // テスト実施
        codeBean.setVariant(null);
        // 判定
        assertEquals("", UTUtil.getPrivateField(codeBean, "variant"));
    }

    /**
     * testGetVariant01() <br>
     * <br>
     * 
     * (正常系)<br>
     * 観点：A <br>
     * <br>
     * 入力値：(状態) variant:"variant"<br>
     * 
     * <br>
     * 期待値：(戻り値) String:"variant"<br>
     * 
     * <br>
     * インスタンス変数variantの値が取得できることを確認する。 <br>
     * 
     * @throws Exception
     *             このメソッドで発生した例外
     */
    public void testGetVariant01() throws Exception {
        // 前処理
        LocaleCodeBean codeBean = new LocaleCodeBean();
        UTUtil.setPrivateField(codeBean, "variant", "variant");
        
        // テスト実施・判定
        assertEquals("variant", codeBean.getVariant());
    }

}
