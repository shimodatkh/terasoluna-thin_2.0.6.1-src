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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockFilterChain;
import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.SessionLockControlFilter} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * 同一セッションの処理の同期化を行う。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.SessionLockControlFilter
 */
public class SessionLockControlFilterTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SessionLockControlFilterTest.class);
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
    public SessionLockControlFilterTest(String name) {
        super(name);
    }


    /**
     * testInit01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) config:interruptResponseCode:500<br>
     *         (状態) config:threshold:1<br>
     *         
     * <br>
     * 期待値：(状態変化) interruptResponseCode:500<br>
     *         (状態変化) threshold:1<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    メッセージ："threshold = 1. LimitedLock is enabled."<br>
     * <br>
     * 初期化パラメータが正しく設定されている場合、フィールドinterruptResponseCode, thresholdに反映されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit01() throws Exception {
        // 前処理
        // コンフィグ
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("interruptResponseCode", "500");
        filterConfig.setInitParameter("threshold", "1");
        // フィルタの用意
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // テスト実施
        filter.init(filterConfig);

        // 判定
        assertEquals(500, UTUtil.getPrivateField(filter, "interruptResponseCode"));
        assertEquals(1, UTUtil.getPrivateField(filter, "threshold"));
        assertTrue(LogUTUtil.checkDebug("threshold = 1. LimitedLock is enabled."));
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) config:interruptResponseCode:null(設定なし)<br>
     *         (状態) config:threshold:-1<br>
     *         
     * <br>
     * 期待値：(状態変化) interruptResponseCode:500<br>
     *         (状態変化) threshold:-1<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    メッセージ："threshold = -1. LimitedLock is disabled. Reason: threshold is negative number."<br>
     * <br>
     * LimitedLockを無効化する設定パターンが正常に反映されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit02() throws Exception {
        // 前処理
        // コンフィグ
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("threshold", "-1");
        // フィルタの用意
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // テスト実施
        filter.init(filterConfig);

        // 判定
        assertEquals(-1, UTUtil.getPrivateField(filter, "threshold"));
        assertTrue(LogUTUtil.checkDebug("threshold = -1. LimitedLock is disabled. Reason: threshold is negative number."));
    }

    /**
     * testInit03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) config:interruptResponseCode:null(設定なし)<br>
     *         (状態) config:threshold:null(設定なし)<br>
     *         
     * <br>
     * 期待値：(状態変化) interruptResponseCode:503<br>
     *         (状態変化) threshold:2<br>
     *         
     * <br>
     * 初期化パラメータが設定されていない場合、フィールドinterruptResponseCode, thresholdはデフォルト値(503, 2)であることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit03() throws Exception {
        // 前処理
        // コンフィグ
        MockFilterConfig filterConfig = new MockFilterConfig();
        // フィルタの用意
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // テスト実施
        filter.init(filterConfig);

        // 判定
        assertEquals(503, UTUtil.getPrivateField(filter, "interruptResponseCode"));
        assertEquals(2, UTUtil.getPrivateField(filter, "threshold"));
    }

    /**
     * testInit04()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) config:interruptResponseCode:AAA<br>
     *         (状態) config:threshold:null(設定なし)<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:NumberFormatException<br>
     *         
     * <br>
     * 初期化パラメータinterruptResponseCodeに数字以外が設定されていた場合、NumberFormatExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit04() throws Exception {
        // 前処理
        // コンフィグ
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("interruptResponseCode", "AAA");
        // フィルタの用意
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // テスト実施
        try {
            filter.init(filterConfig);
            fail();
        } catch (NumberFormatException e) {
            // 期待通り
        }
    }

    /**
     * testInit05()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) config:not null<br>
     *         (状態) config:interruptResponseCode:null(設定なし)<br>
     *         (状態) config:threshold:AAA<br>
     *         
     * <br>
     * 期待値：(状態変化) 例外:NumberFormatException<br>
     *         
     * <br>
     * 初期化パラメータthresholdに数字以外が設定されていた場合、NumberFormatExceptionが発生することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testInit05() throws Exception {
        // 前処理
        // コンフィグ
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("threshold", "AAA");
        // フィルタの用意
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // テスト実施
        try {
            filter.init(filterConfig);
            fail();
        } catch (NumberFormatException e) {
            // 期待通り
        }
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：C
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) req:session:null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         
     * <br>
     * 期待値：(状態変化) ログ:【デバッグログ】<br>
     *                    メッセージ："not lock."<br>
     *         
     * <br>
     * セッションが生成されていない場合、セッションによる同期化を行わないことを確認する。<br>
     * デバッグログにて、同期化を行わないルートに入ったことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter01() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setSession(null);
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        SessionLockControlFilter filter = new SessionLockControlFilter();

        // テスト実施
        filter.doFilter(req, res, chain);
        
        // 判定
        assertTrue(LogUTUtil.checkDebug("not lock."));
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) threshold:-1<br>
     *         
     * <br>
     * 期待値：(状態変化) セッションIDのinternでsynchronizedされ、FilterChain#doFilterが実行される<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    メッセージ："use synchronized lock."<br>
     *         
     * <br>
     * thresholdが0より小さい場合、synchronizedブロックを使用して同期化を行うことを確認する。<br>
     * セッションIDのinternで同期化されていることを確認する。<br>
     * デバッグログにて、synchronizedブロックを使用して同期化を行うルートに入ったことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter02() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setId("AAAA");
        req.setSession(session);
        MockHttpServletResponse res = new MockHttpServletResponse();
        SessionLockControlFilter filter = new SessionLockControlFilter();
        UTUtil.setPrivateField(filter, "threshold", -1);
        FilterChain chain = new FilterChain() {
            public void doFilter(ServletRequest req, ServletResponse res)
                                                                           throws IOException,
                                                                           ServletException {
                // 判定
                // もしsynchronizedブロックでロックされていなければ、例外が発生する。
                "AAAA".intern().notify();
            }
        };

        // テスト実施
        filter.doFilter(req, res, chain);

        // 判定
        assertTrue(LogUTUtil.checkDebug("use synchronized lock."));
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) threshold:0<br>
     *         
     * <br>
     * 期待値：(状態変化) セッションID用に用意されたLimitedLockで同期化され、FilterChain#doFilterが実行される<br>
     *         (状態変化) doFilter終了時は、LimitedLockのロックが解放される<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    メッセージ："use LimitedLock."<br>
     *         
     * <br>
     * thresholdが0以上(0)の場合、LimitedLockを使用して同期化を行うことを確認する。<br>
     * セッションID用のLimitedLockで同期化されていることを確認する。<br>
     * デバッグログにて、LimitedLockを使用して同期化を行うルートに入ったことを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter03() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setId("AAAA");
        req.setSession(session);
        MockHttpServletResponse res = new MockHttpServletResponse();
        SessionLockControlFilter filter = new SessionLockControlFilter();
        UTUtil.setPrivateField(filter, "threshold", 0);
        final Map<String, SessionLockReference> limitedLockMapField = (Map<String, SessionLockReference>) UTUtil.getPrivateField(filter, "limitedLockMap");
        FilterChain chain = new FilterChain() {
            public void doFilter(ServletRequest req, ServletResponse res)
                                                                           throws IOException,
                                                                           ServletException {
                // 判定(FilterChain#doFilter実行中)
                LimitedLock limitedLock = limitedLockMapField.get("AAAA").get();
                assertNotNull(limitedLock);
                try {
                    assertSame(Thread.currentThread(), UTUtil.invokePrivate(limitedLock, "getOwner"));
                } catch (Exception e) {
                    // UTUtil.invokePrivateが失敗した場合。
                    // ここには到達しない。
                    fail();
                }
                
                // 実施後の判定前に、偶発のGCによってLimitedLockを保持する弱参照が切れてしまわないよう、
                // LimitedLockの参照を維持した状態で、一度強制GCを実行しておく。
                System.gc();
            }
        };

        // テスト実施
        filter.doFilter(req, res, chain);
        
        // 判定
        LimitedLock limitedLock = limitedLockMapField.get("AAAA").get();
        assertNull(UTUtil.invokePrivate(limitedLock, "getOwner"));
        assertTrue(LogUTUtil.checkDebug("use LimitedLock."));
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) threshold:2(デフォルト)<br>
     *         (状態) セッションID AAAA, BBBB で実行後、十分なGCの後で、セッションID CCCCで実行する<br>
     *         
     * <br>
     * 期待値：(状態変化) limitedLockMap:セッションID AAAA, BBBB の分のエントリーが削除される<br>
     *         
     * <br>
     * 弱参照で保持していたLimitedLockがGCに回収されることにより、不要になったlimitedLockMapのエントリーが削除されることを確認する。<br>
     * (弱参照がReferenceQueueに入るには、数回のGCを必要とする。
     * ここでの「十分なGC」とは、弱参照がReferenceQueueに入るまでGCを発生させることを意味する。)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter04() throws Exception {
        // 前処理
        SessionLockControlFilter_ReferenceQueueStub01 queue = new SessionLockControlFilter_ReferenceQueueStub01();
        MockHttpServletRequest reqA = new MockHttpServletRequest();
        MockHttpSession sessionA = new MockHttpSession();
        sessionA.setId("AAAA");
        reqA.setSession(sessionA);
        MockHttpServletRequest reqB = new MockHttpServletRequest();
        MockHttpSession sessionB = new MockHttpSession();
        sessionB.setId("BBBB");
        reqB.setSession(sessionB);
        MockHttpServletRequest reqC = new MockHttpServletRequest();
        MockHttpSession sessionC = new MockHttpSession();
        sessionC.setId("CCCC");
        reqC.setSession(sessionC);
        MockHttpServletResponse res = new MockHttpServletResponse();
        SessionLockControlFilter filter = new SessionLockControlFilter();
        UTUtil.setPrivateField(filter, "sessionLockRefQueue", queue);
        Map<String, SessionLockReference> limitedLockMapField = (Map<String, SessionLockReference>) UTUtil.getPrivateField(filter, "limitedLockMap");
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(reqA, res, chain);
        filter.doFilter(reqB, res, chain);
        // 弱参照がReferenceQueueに入るのに十分なGC
        queue.prePollWithGC(2);
        
        // テスト実施
        filter.doFilter(reqC, res, chain);
        
        // 判定
        assertFalse(limitedLockMapField.containsKey("AAAA"));
        assertFalse(limitedLockMapField.containsKey("BBBB"));
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) threshold:2(デフォルト)<br>
     *         (状態) セッションID AAAAで実行後GC(セッションID AAAA用のLimitedLock1つ目がGCに回収される)、
     *                その後再び、セッションID AAAAで実行後(セッションID AAAA用のLimitedLock2つ目が生成される)に、
     *                セッションID AAAA用のLimitedLockを参照した状態で、
     *                十分なGCの後(LimitedLock1つ目の弱参照のみがReferenceQueueに入る)、セッションID BBBBで実行する<br>
     *         
     * <br>
     * 期待値：(状態変化) limitedLockMap:セッションID AAAA の分のエントリーは削除されない<br>
     *         (状態変化) ログ:【デバッグログ】<br>
     *                    メッセージ："LimitedLock is deallocated. sessionId = AAAA, SessionLockReference = " + 1つ目のLimitedLockを格納したSessionLockReferenceのtoStringの結果<br>
     *         
     * <br>
     * 弱参照で保持していたLimitedLockがGCに回収されることにより、不要になったlimitedLockMapのエントリーが削除されるが、
     * limitedLockMapに存在するエントリーが、ReferenceQueueから得られたものと異なる(新たな参照で上書きされている)場合、削除しないことを確認する。<br>
     * (弱参照がReferenceQueueに入るには、数回のGCを必要とする。
     * ここでの「十分なGC」とは、弱参照がReferenceQueueに入るまでGCを発生させることを意味する。)<br>
     * デバッグログにて、エントリーの削除ロジックに入ったことを確認する。(削除ロジックに入り、かつ、ロジック内の判定によって削除しないことを確認する)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter05() throws Exception {
        // 前処理
        SessionLockControlFilter_ReferenceQueueStub01 queue = new SessionLockControlFilter_ReferenceQueueStub01();
        MockHttpServletRequest reqA = new MockHttpServletRequest();
        MockHttpSession sessionA = new MockHttpSession();
        sessionA.setId("AAAA");
        reqA.setSession(sessionA);
        MockHttpServletRequest reqB = new MockHttpServletRequest();
        MockHttpSession sessionB = new MockHttpSession();
        sessionB.setId("BBBB");
        reqB.setSession(sessionB);
        MockHttpServletResponse res = new MockHttpServletResponse();
        final List<LimitedLock> limitedLockList = new ArrayList<LimitedLock>();
        SessionLockControlFilter filter = new SessionLockControlFilter() {
            int count = 0;
            @Override
            protected void lockLimitedLock(HttpServletRequest request,
                    LimitedLock lock) throws InterruptedException {
                super.lockLimitedLock(request, lock);
                if (count == 1) {
                    // 2回目実行時のみ、LimitedLockの参照を維持する
                    limitedLockList.add(lock);
                }
                count++;
            }
        };
        UTUtil.setPrivateField(filter, "sessionLockRefQueue", queue);
        Map<String, SessionLockReference> limitedLockMapField = (Map<String, SessionLockReference>) UTUtil.getPrivateField(filter, "limitedLockMap");
        MockFilterChain chain = new MockFilterChain();
        filter.doFilter(reqA, res, chain);
        String sessionLockReferenceStringForDeallocatedLog = limitedLockMapField.get("AAAA").toString();
        System.gc();
        filter.doFilter(reqA, res, chain);
        // 弱参照がReferenceQueueに入るのに十分なGC
        queue.prePollWithGC(1);
        
        // テスト実施
        filter.doFilter(reqB, res, chain);
        
        // 判定
        assertTrue(limitedLockMapField.containsKey("AAAA"));
        assertTrue(LogUTUtil.checkDebug("LimitedLock is deallocated. sessionId = AAAA, SessionLockReference = " + sessionLockReferenceStringForDeallocatedLog));
    }

    /**
     * testDoFilter06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) threshold:2(デフォルト)<br>
     *         (状態) セッションID AAAA用のLimitedLockが参照されている<br>
     *         (状態) 毎回GCを挟みつつ、5回実行する<br>
     *         
     * <br>
     * 期待値：(状態変化) 同一のLimitedLockインスタンスをロックする<br>
     *         
     * <br>
     * いずれかのスレッドがLimitedLockを参照している間は、LimitedLockは解放されずに、同一セッションID内で共有されることを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter06() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setId("AAAA");
        req.setSession(session);
        MockHttpServletResponse res = new MockHttpServletResponse();
        final List<LimitedLock> limitedLockList = new ArrayList<LimitedLock>();
        SessionLockControlFilter filter = new SessionLockControlFilter() {
            @Override
            protected void lockLimitedLock(HttpServletRequest request,
                    LimitedLock lock) throws InterruptedException {
                super.lockLimitedLock(request, lock);
                limitedLockList.add(lock);
            }
        };
        MockFilterChain chain = new MockFilterChain();
        
        // テスト実施
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        
        // 判定
        assertNotNull(limitedLockList.get(0));
        assertSame(limitedLockList.get(0), limitedLockList.get(1));
        assertSame(limitedLockList.get(0), limitedLockList.get(2));
        assertSame(limitedLockList.get(0), limitedLockList.get(3));
        assertSame(limitedLockList.get(0), limitedLockList.get(4));
    }

    /**
     * testDoFilter07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (状態) セッションID:AAAA
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) threshold:2(デフォルト)<br>
     *         (状態) LimitedLockが参照されていない<br>
     *         (状態) GCを挟みつつ、3回実行する<br>
     *         
     * <br>
     * 期待値：(状態変化) 実行するたびに(計3回)、LimitedLockインスタンスが生成される<br>
     *         
     * <br>
     * いずれのスレッドもLimitedLockを参照していない間にGCが発生した場合、LimitedLockが解放され、新たなLimitedLockが使用されることを確認する。<br>
     * (実際にロックするLimitedLockインスタンスを検証しようとすると、「LimitedLockが参照されていない」の条件が満たせないため、
     *  LimitedLockインスタンスの生成回数にて確認する。)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter07() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setId("AAAA");
        req.setSession(session);
        MockHttpServletResponse res = new MockHttpServletResponse();
        final AtomicInteger createCount = new AtomicInteger(0);
        SessionLockControlFilter filter = new SessionLockControlFilter() {
            @Override
            protected LimitedLock createLimitedLock() {
                createCount.addAndGet(1);
                return super.createLimitedLock();
            }
            
        };
        MockFilterChain chain = new MockFilterChain();
        
        // テスト実施
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        
        // 判定
        assertEquals(3, createCount.get());
    }

    /**
     * testDoFilter08()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) interruptResponseCode:500<br>
     *         (状態) threshold:2(デフォルト)<br>
     *         (状態) LimitedLockのロック取得メソッドからInterruptedExceptionがスローされる<br>
     *         
     * <br>
     * 期待値：(状態変化) FilterChain#doFilterは実行されない<br>
     *         (状態変化) res:レスポンスコード:500<br>
     *         (状態変化) LimitedLockのロック解放メソッドが実行される<br>
     *         
     * <br>
     * LimitedLockのロック取得が中断されたとき、FilterChain#doFilterは実行されず、interruptResponseCodeに設定されたレスポンスコードが使用されることを確認する。<br>
     * LimitedLockのロック取得が中断されても、ロック解放メソッドは実行されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter08() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        final AtomicBoolean unlockCalled = new AtomicBoolean(false);
        SessionLockControlFilter filter = new SessionLockControlFilter() {
            @Override
            protected void lockLimitedLock(HttpServletRequest request,
                    LimitedLock lock) throws InterruptedException {
                throw new InterruptedException();
            }

            @Override
            protected void unlockLimitedLock(HttpServletRequest request,
                    LimitedLock lock) {
                unlockCalled.getAndSet(true);
                super.unlockLimitedLock(request, lock);
            }
        };
        UTUtil.setPrivateField(filter, "interruptResponseCode", 500);
        MockFilterChain chain = new MockFilterChain();

        // テスト実施
        filter.doFilter(req, res, chain);
        
        // 判定
        assertNull(chain.getRequest());
        assertEquals(500, res.getStatus());
        assertTrue(unlockCalled.get());
    }

    /**
     * testDoFilter09()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(引数) req:not null<br>
     *         (引数) res:not null<br>
     *         (引数) chain:not null<br>
     *         (状態) threshold:2(デフォルト)<br>
     *         (状態) FilterChain#doFilterからRuntimeExceptionがスローされる<br>
     *         
     * <br>
     * 期待値：(状態変化) LimitedLockのロック解放メソッドが実行される<br>
     *         (状態変化) 例外:RuntimeException：<br>
     *         
     * <br>
     * FilterChain#doFilterから例外がスローされた場合、例外がそのままスローされることを確認する。<br>
     * ロック解放メソッドは実行されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testDoFilter09() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        final RuntimeException ex = new RuntimeException();
        final AtomicBoolean unlockCalled = new AtomicBoolean(false);
        SessionLockControlFilter filter = new SessionLockControlFilter() {
            @Override
            protected void unlockLimitedLock(HttpServletRequest request,
                    LimitedLock lock) {
                unlockCalled.getAndSet(true);
                super.unlockLimitedLock(request, lock);
            }
        };
        FilterChain chain = new FilterChain() {
            public void doFilter(ServletRequest arg0, ServletResponse arg1)
                                                                           throws IOException,
                                                                           ServletException {
                throw ex;
            }
        };

        // テスト実施
        RuntimeException actualThrown = null;
        try {
            filter.doFilter(req, res, chain);
            fail();
        } catch (RuntimeException e) {
            actualThrown = e;
        }
        
        // 判定
        assertSame(ex, actualThrown);
        assertTrue(unlockCalled.get());
    }

    /**
     * testCreateLimitedLock01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *
     * <br>
     * 期待値：(戻り値) LimitedLockインスタンス[threshold=1]<br>
     *
     * <br>
     * フィールドthresholdの値が使用され、LimitedLockインスタンスが生成されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testCreateLimitedLock01() throws Exception {
        // 前処理
        SessionLockControlFilter filter = new SessionLockControlFilter();
        UTUtil.setPrivateField(filter, "threshold", 1);
        
        // テスト実施
        LimitedLock limitedLock = filter.createLimitedLock();
        
        // 判定
        assertNotNull(limitedLock);
        assertEquals(1, UTUtil.getPrivateField(limitedLock, "threshold"));
    }

    /**
     * testLockLimitedLock01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) lock:LimitedLock(ロック解放状態)<br>
     *
     * <br>
     * 期待値：(状態変化) 現在のスレッドがLimitedLockインスタンスのロックを取得する<br>
     *
     * <br>
     * 現在のスレッドがLimitedLockインスタンスのロックを取得することを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockLimitedLock01() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        SessionLockControlFilter filter = new SessionLockControlFilter();
        LimitedLock lock = new LimitedLock(1);
        
        // テスト実施
        filter.lockLimitedLock(req, lock);
        
        // 判定
        assertEquals(Thread.currentThread(), UTUtil.invokePrivate(lock, "getOwner"));
    }

    /**
     * testUnlockLimitedLock01()
     * <br><br>
     *
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(引数) request:not null<br>
     *         (引数) lock:LimitedLock(現在のスレッドに1回ロックされた状態)<br>
     *
     * <br>
     * 期待値：(状態変化) LimitedLockインスタンスのロックが解放される<br>
     *
     * <br>
     * LimitedLockインスタンスのロックが解放されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testUnlockLimitedLock01() throws Exception {
        // 前処理
        MockHttpServletRequest req = new MockHttpServletRequest();
        SessionLockControlFilter filter = new SessionLockControlFilter();
        LimitedLock lock = new LimitedLock(1);
        lock.lockInterruptibly();
        
        // テスト実施
        filter.unlockLimitedLock(req, lock);
        
        // 判定
        assertNull(UTUtil.invokePrivate(lock, "getOwner"));
    }

}
