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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.thin.LimitedLock} クラスのブラックボックステスト。
 * 
 * <p>
 * <h4>【クラスの概要】</h4>
 * ロック待ちスレッドが増えた際に、古いロック待ちスレッドを中断する機能を持つロッククラス。
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.LimitedLock
 */
public class LimitedLockTest extends TestCase {

    /**
     * このテストケースを実行する為の
     * GUI アプリケーションを起動する。
     * 
     * @param args java コマンドに設定されたパラメータ
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(LimitedLockTest.class);
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
    public LimitedLockTest(String name) {
        super(name);
    }

    /**
     * testLimitedLock01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) threshold:1<br>
     *         
     * <br>
     * 期待値：(戻り値) LimitedLock:not Null<br>
     *         (状態変化) threshold:1<br>
     * <br>
     * コンストラクタの引数が1以上である場合、引数で与えた数値がthresholdに設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLimitedLock01() throws Exception {
        // テスト実施
        LimitedLock lock = new LimitedLock(1);
        
        // 判定
        int thresholdField = (Integer) UTUtil.getPrivateField(lock, "threshold");
        assertEquals(1, thresholdField);
    }

    /**
     * testLimitedLock02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) threshold:0<br>
     *         
     * <br>
     * 期待値：(戻り値) LimitedLock:not Null<br>
     *         (状態変化) threshold:0<br>
     * <br>
     * コンストラクタの引数が0以下(0)である場合、0がthresholdに設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLimitedLock02() throws Exception {
        // テスト実施
        LimitedLock lock = new LimitedLock(0);
        
        // 判定
        int thresholdField = (Integer) UTUtil.getPrivateField(lock, "threshold");
        assertEquals(0, thresholdField);
    }

    /**
     * testLimitedLock03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(引数) threshold:-1<br>
     *         
     * <br>
     * 期待値：(戻り値) LimitedLock:not Null<br>
     *         (状態変化) threshold:0<br>
     * <br>
     * コンストラクタの引数が0以下(-1)である場合、0がthresholdに設定されることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLimitedLock03() throws Exception {
        // テスト実施
        LimitedLock lock = new LimitedLock(-1);
        
        // 判定
        int thresholdField = (Integer) UTUtil.getPrivateField(lock, "threshold");
        assertEquals(0, thresholdField);
    }

    /**
     * testLockInterruptibly01()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 未ロック状態<br>
     *         (状態) 現在のスレッドに割り込みステータスが設定されている状態<br>
     * <br>
     * 期待値：(状態変化) 例外:InterruptedException：<br>
     * <br>
     * ロック取得前に、既に現在のスレッドに割り込みステータスが設定されている場合、
     * ロック状態に関わらず、ロックの取得が中断されることを確認する。<br>
     * 
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockInterruptibly01() throws Exception {
        // 前処理
        LimitedLock lock = new LimitedLock(1);
        Thread.currentThread().interrupt();
        
        // テスト実施
        try {
            lock.lockInterruptibly();
            fail();
        } catch (InterruptedException e) {
            // 期待通り
        }
        
        // 判定
        assertNull(UTUtil.invokePrivate(lock, "getOwner"));
    }

    /**
     * testLockInterruptibly02()
     * <br><br>
     * 
     * (異常系)
     * <br>
     * 観点：G
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 別のスレッドがロックしている状態<br>
     *         (状態) 現在のスレッドがロック待ちしている間に、スレッドの割り込みステータスが設定される<br>
     * <br>
     * 期待値：(状態変化) 例外:InterruptedException：<br>
     * <br>
     * ロック待ちしている間に、現在のスレッドに割り込みステータスが設定された場合、
     * ロック待ちが中断されることを確認する。<br>
     * 
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockInterruptibly02() throws Exception {
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        ErrorFeedBackThread interruptorThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    // テストスレッドが「テスト実施」のところに進む前に
                    // 割り込みステータスを設定しないよう、
                    // 少し待つ。
                    Thread.sleep(100);
                    testThread.interrupt();
                } finally {
                    lock.unlock();
                }
            }
        };
        interruptorThread.start();
        Thread.sleep(50);
        // interruptorThreadがロックを取得したまま、約50ミリ秒後に現在のスレッドに割り込みをかける。
        
        // テスト実施
        try {
            lock.lockInterruptibly();
            fail();
        } catch (InterruptedException e) {
            // 期待通り
        }
        
        // 判定
        assertNotSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        
        // 別スレッドのエラーフィードバック
        interruptorThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 未ロック状態<br>
     * <br>
     * 期待値：(状態変化) 現在のスレッドでロックされた状態<br>
     * <br>
     * 未ロック状態である場合、直ちにロックが取得できることを確認する。
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockInterruptibly03() throws Exception {
        // 前処理
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        ErrorFeedBackThread interruptorThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    // もし1秒以内にロックが取得できなければ、中断する。
                    // 中断されると、「テスト実施」のところからInterruptedExceptionが発生し、試験はエラーとなる。
                    Thread.sleep(1000);
                    testThread.interrupt();
                } catch (InterruptedException e) {
                    // 正常にテストが終了し、スレッドを速やかに停止するための後処理が実行されたときに、
                    // ここに到達する。
                }
            }
        };
        interruptorThread.start();
        
        // テスト実施
        lock.lockInterruptibly();
        
        // 判定
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        
        // 後処理
        interruptorThread.interrupt();
        
        // 別スレッドのエラーフィードバック
        interruptorThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly04()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：B
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 別のスレッドがロックを保持しており、その他、1つのスレッドがロック待ちしている状態<br>
     * <br>
     * 期待値：(状態変化) 現在のスレッドでロックされた状態<br>
     * <br>
     * 別のスレッドがロックを保持している場合、<br>
     * threshold以内のスレッド(1)がロック待ちしていても、ロック待ちの中断が発生しないことを確認する。<br>
     * じきに、現在のスレッドがロックを取得できることを確認する。<br>
     * ロックを取得できたときには、他のスレッドがロック中でないことを確認する。<br>
     * (同時に複数のスレッドがロックを獲得しないのは、スーパークラスの機能なので、このテストメソッドのみで確認する。)<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockInterruptibly04() throws Exception {
        // 前処理
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        final HashSet<Thread> lockThreadSet = new HashSet<Thread>();
        ErrorFeedBackThread lockThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    lockThreadSet.add(Thread.currentThread());
                    Thread.sleep(500);
                } finally {
                    lockThreadSet.remove(Thread.currentThread());
                    lock.unlock();
                }
            }
        };
        lockThread.start();
        Thread.sleep(50);
        ErrorFeedBackThread waitThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    lockThreadSet.add(Thread.currentThread());
                } finally {
                    lockThreadSet.remove(Thread.currentThread());
                    lock.unlock();
                }
            }
        };
        waitThread.start();
        Thread.sleep(50);
        // lockThreadがロックを取得して、あと約400ミリ秒ロックしている状態。
        // waitThreadはロックを待っている状態。
        
        // テスト実施
        lock.lockInterruptibly();
        
        // 判定
        assertEquals(0, lockThreadSet.size());
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));

        // 別スレッドのエラーフィードバック
        lockThread.throwErrorOrExceptionIfThrown();
        waitThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly05()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：BD
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 別のスレッドがロックを保持しており、その他、2つのスレッド(現在のスレッドを含まない)がロック待ちしている状態<br>
     * <br>
     * 期待値：(状態変化) 現在のスレッドでロックされた状態<br>
     *         (状態変化) 最初にロック待ちになったスレッド1つのみが、ロック待ちを中断される。
     * <br>
     * 別のスレッドがロックを保持している場合、<br>
     * thresholdを超えるスレッド(2)がロック待ちしている場合、ロック待ちの中断が発生することを確認する。<br>
     * 古いロック待ちスレッド(ロック待ちスレッド2つのうち、最初にロック待ち状態になったスレッド)のロック待ちが中断されることを確認する。
     * じきに、現在のスレッドがロックを取得できることを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockInterruptibly05() throws Exception {
        // 前処理
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        ErrorFeedBackThread lockThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    Thread.sleep(500);
                } finally {
                    lock.unlock();
                }
            }
        };
        lockThread.start();
        Thread.sleep(50);
        ErrorFeedBackThread waitThread1 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    fail();
                } catch (InterruptedException e) {
                    // 期待通り
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread1.start();
        Thread.sleep(50);
        ErrorFeedBackThread waitThread2 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread2.start();
        Thread.sleep(50);
        // lockThreadがロックを取得して、あと約350ミリ秒ロックしている状態。
        // waitThread1はロックを待っている状態。
        // waitThread2はロックを待っている状態。
        
        // テスト実施
        lock.lockInterruptibly();
        
        // 判定
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));

        // 別スレッドのエラーフィードバック
        lockThread.throwErrorOrExceptionIfThrown();
        waitThread1.throwErrorOrExceptionIfThrown();
        waitThread2.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly06()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：D
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 別のスレッドがロックを保持しており、その他、5つのスレッド(最後の1つは現在のスレッド)がロック待ちしていく状態<br>
     * <br>
     * 期待値：(状態変化) 現在のスレッドでロックされた状態<br>
     *         (状態変化) 1, 2, 3番目にロック待ちになったスレッド3つのみが、ロック待ちを中断される。
     * <br>
     * 別のスレッドがロックを保持している場合、<br>
     * thresholdを超えるスレッドがロック待ちしている場合、ロック待ちの中断が発生することを確認する。<br>
     * 古いロック待ちスレッドからロック待ちが中断されることを確認する。
     * じきに、現在のスレッドがロックを取得できることを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockInterruptibly06() throws Exception {
        // 前処理
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        final List<String> interruptedList = Collections.synchronizedList(new ArrayList<String>());
        ErrorFeedBackThread lockThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    Thread.sleep(500);
                } finally {
                    lock.unlock();
                }
            }
        };
        lockThread.start();
        Thread.sleep(50);
        
        // テスト実施
        ErrorFeedBackThread waitThread1 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    fail();
                } catch (InterruptedException e) {
                    // 期待通り
                    interruptedList.add("1");
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread1.start();
        Thread.sleep(50);
        ErrorFeedBackThread waitThread2 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    fail();
                } catch (InterruptedException e) {
                    // 期待通り
                    interruptedList.add("2");
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread2.start();
        Thread.sleep(50);
        ErrorFeedBackThread waitThread3 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    fail();
                } catch (InterruptedException e) {
                    // 期待通り
                    interruptedList.add("3");
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread3.start();
        Thread.sleep(50);
        ErrorFeedBackThread waitThread4 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread4.start();
        Thread.sleep(50);
        // lockThreadがロックを取得して、あと約250ミリ秒ロックしている状態。
        
        lock.lockInterruptibly();
        
        // 判定
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        assertEquals(3, interruptedList.size());
        assertEquals("1", interruptedList.get(0));
        assertEquals("2", interruptedList.get(1));
        assertEquals("3", interruptedList.get(2));

        // 別スレッドのエラーフィードバック
        lockThread.throwErrorOrExceptionIfThrown();
        waitThread1.throwErrorOrExceptionIfThrown();
        waitThread2.throwErrorOrExceptionIfThrown();
        waitThread3.throwErrorOrExceptionIfThrown();
        waitThread4.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly07()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 現在のスレッドがロックを保持しており、その他、2つのスレッドがロック待ちしている状態<br>
     * <br>
     * 期待値：(状態変化) 現在のスレッドでロックされた状態<br>
     * <br>
     * 現在のスレッドが既にロックを保持している場合(再入ロック時)、<br>
     * thresholdを超えるスレッド(2)がロック待ちしていても、ロック待ちの中断は発生しないことを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testLockInterruptibly07() throws Exception {
        // 前処理
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        lock.lockInterruptibly();
        ErrorFeedBackThread waitThread1 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread1.start();
        Thread.sleep(50);
        ErrorFeedBackThread waitThread2 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                } finally {
                    lock.unlock();
                }
            }
        };
        waitThread2.start();
        Thread.sleep(50);
        
        // テスト実施
        lock.lockInterruptibly();
        
        // 判定
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        
        // 後処理
        lock.unlock();
        lock.unlock();

        // 別スレッドのエラーフィードバック
        waitThread1.throwErrorOrExceptionIfThrown();
        waitThread2.throwErrorOrExceptionIfThrown();
    }

    /**
     * testUnlock01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 現在のスレッドがロックを保持している状態<br>
     *         (状態) ロックカウント:1<br>
     * <br>
     * 期待値：(状態変化) ロックが解放された状態<br>
     * <br>
     * ロックを保持しているスレッドがunlockを実行した場合、ロックを解放することを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testUnlock01() throws Exception {
        // 前処理
        LimitedLock lock = new LimitedLock(1);
        lock.lockInterruptibly();
        
        // テスト実施
        lock.unlock();
        
        // 判定
        assertNull(UTUtil.invokePrivate(lock, "getOwner"));
    }

    /**
     * testUnlock02()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 現在のスレッドがロックを保持している状態<br>
     *         (状態) ロックカウント:2<br>
     * <br>
     * 期待値：(状態変化) 現在のスレッドがロックを保持している状態<br>
     *         (状態変化) ロックカウント:1<br>
     * <br>
     * ロックを2回取得したスレッドがunlockを1回実行した場合、ロックを解放せずにロックカウントだけデクリメントされることを確認する。<br>
     * (スーパークラスの再入可能ロックとしての機能)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testUnlock02() throws Exception {
        // 前処理
        LimitedLock lock = new LimitedLock(1);
        Thread testThread = Thread.currentThread();
        lock.lockInterruptibly();
        lock.lockInterruptibly();
        
        // テスト実施
        lock.unlock();
        
        // 判定
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        assertEquals(1, lock.getHoldCount());
    }

    /**
     * testUnlock03()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：A
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) 別のスレッドがロックを保持している状態<br>
     * <br>
     * 期待値：(状態変化) 別のスレッドがロックを保持している状態<br>
     * <br>
     * 現在のスレッドがロックを保持していない場合、何もしないことを確認する。<br>
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testUnlock03() throws Exception {
        // 前処理
        final LimitedLock lock = new LimitedLock(1);
        ErrorFeedBackThread lockThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    Thread.sleep(500);
                } finally {
                    lock.unlock();
                }
            }
        };
        lockThread.start();
        Thread.sleep(50);
        
        // テスト実施
        lock.unlock();
        
        // 判定
        assertSame(lockThread, UTUtil.invokePrivate(lock, "getOwner"));

        // 別スレッドのエラーフィードバック
        lockThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testReadObject01()
     * <br><br>
     * 
     * (正常系)
     * <br>
     * 観点：E
     * <br><br>
     * 入力値：(状態) threshold:1<br>
     *         (状態) lock:Not Null<br>
     *         (状態) waitingThreadList:Not Null<br>
     * <br>
     * 期待値：(状態変化) threshold:1<br>
     *         (状態変化) lock:Object(入力値とは異なる)<br>
     *         (状態変化) waitingThreadList:LinkedList(入力値とは異なる)<br>
     * <br>
     * シリアライズし、デシリアライズした場合、thresholdの値は引き継がれ、lockとwaitingThreadListには新しいオブジェクトが設定されることを確認する。<br>
     * (デシリアライズすることにより、readObjectメソッドを動作させる。)
     * <br>
     * 
     * @throws Exception このメソッドで発生した例外
     */
    public void testReadObject01() throws Exception {
        // 前処理
        LimitedLock lock = new LimitedLock(1);
        Object oldLockField = UTUtil.getPrivateField(lock, "lock");
        LinkedList oldWaitingThreadListField = (LinkedList) UTUtil.getPrivateField(lock, "waitingThreadList");
        
        // テスト実施
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(lock);
        oos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object deserialObject = ois.readObject();
        
        // 判定
        assertEquals(1, UTUtil.getPrivateField(deserialObject, "threshold"));
        Object newLockField = UTUtil.getPrivateField(deserialObject, "lock");
        assertNotNull(newLockField);
        assertNotSame(oldLockField, newLockField);
        LinkedList newWaitingThreadListField = (LinkedList) UTUtil.getPrivateField(deserialObject, "waitingThreadList");
        assertNotNull(newWaitingThreadListField);
        assertNotSame(oldWaitingThreadListField, newWaitingThreadListField);
    }

    /**
     * エラーをフィードバックできるスレッド。
     * 別スレッドで実施したい内容を doRun() throws Exception に実装する。
     * 試験終了時、throwErrorOrExceptionIfThrownメソッドを実行すると、
     * doRunメソッドにて想定外のエラーが発生した場合に、そのエラーがスローされる。
     */
    abstract class ErrorFeedBackThread extends Thread {
        private Exception exception;
        private Error error;
        @Override
        public void run() {
            try {
                doRun();
            } catch (Exception e) {
                exception = e;
            } catch (Error e) {
                error = e;
            }
        }
        
        public void throwErrorOrExceptionIfThrown() throws Exception {
            join();
            if (error != null) {
                throw error;
            } else if (exception != null) {
                throw exception;
            }
        }
        
        abstract void doRun() throws Exception;
    }

}
