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

package jp.terasoluna.fw.web.thin;

import java.lang.ref.ReferenceQueue;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.SessionLockReference} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * WeakReferenceの拡張クラス。
 * セッションIDが保持できる。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.SessionLockReference
 */
public class SessionLockReferenceTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SessionLockReferenceTest.class);
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
    public SessionLockReferenceTest(String name) {
        super(name);
    }

    /**
     * testSessionLockReference01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) sessionId:1111<br>
     *         (引数) referent:LimitedLock<br>
     *         (引数) q:ReferenceQueue<br>
     * <br>
     * 期待値：(戻り値) SessionLockReference:not Null<br>
     *         (状態変化) sessionId:1111<br>
     *         (状態変化) getの戻り値:LimitedLock<br>
     * <br>
     * コンストラクタの引数sessionIdで与えた値がsessionIdに設定されることを確認する。<br>
     * 引数referentで与えたオブジェクトが、getで得られることを確認する。<br>
     * (sessionId以外の動作は、スーパークラスによるもの。)<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSessionLockReference01() throws Exception {
        // 前処理
        ReferenceQueue<LimitedLock> refQueue = new ReferenceQueue<LimitedLock>();
        LimitedLock lock = new LimitedLock(1);
        
        // テスト実施
        SessionLockReference ref = new SessionLockReference("1111", lock, refQueue);
        
        // 判定
        assertEquals("1111", UTUtil.getPrivateField(ref, "sessionId"));
        assertSame(lock, ref.get());
    }

    /**
     * testSessionLockReference02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) sessionId:null<br>
     *         (引数) referent:null<br>
     *         (引数) q:null<br>
     * <br>
     * 期待値：(戻り値) SessionLockReference:not Null<br>
     *         (状態変化) sessionId:null<br>
     *         (状態変化) getの戻り値:null<br>
     * <br>
     * コンストラクタの引数が全てnullでも、インスタンスが生成できることを確認する。<br>
     * コンストラクタの引数sessionIdで与えたnullがsessionIdに設定されることを確認する。<br>
     * 引数referentで与えたnullが、getで得られることを確認する。<br>
     * (sessionId以外の動作は、スーパークラスによるもの。)<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testSessionLockReference02() throws Exception {
        // テスト実施
        SessionLockReference ref = new SessionLockReference(null, null, null);
        
        // 判定
        assertNull(UTUtil.getPrivateField(ref, "sessionId"));
        assertNull(ref.get());
    }

    /**
     * testGetSessionId01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) sessionId:1111<br>
     *         (状態) referent:LimitedLock<br>
     *         (状態) queue:ReferenceQueue<br>
     * <br>
     * 期待値：(戻り値) String:1111<br>
     * <br>
     * 設定されているsessionIdが取得できることを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testGetSessionId01() {
        // 前処理
        ReferenceQueue<LimitedLock> refQueue = new ReferenceQueue<LimitedLock>();
        LimitedLock lock = new LimitedLock(1);
        SessionLockReference ref = new SessionLockReference("1111", lock, refQueue);
        
        // テスト実施
        String result = ref.getSessionId();
        
        // 判定
        assertEquals("1111", result);
    }
}
