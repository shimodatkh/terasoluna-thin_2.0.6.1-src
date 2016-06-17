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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ロック待ちスレッドが増えた際に、古いロック待ちスレッドを中断する機能を持つロッククラス。
 * <p>
 * Webにおいて、セッションで同期化する場合、synchronizedによる同期化実装では、 以下のような操作を行うユーザがいた場合に、 スレッドを無駄に占有してしまう(大量のスレッドがロック待ち状態になってしまう)問題がある。<br>
 * <ol>
 * <li>極端にレスポンスの悪い処理を実行する</li>
 * <li>なかなかレスポンスが返ってこないので、操作をやり直すため、お気に入り等からトップページに移動する<br>
 * (セッションで同期化しているので、1の処理が終わるまでは表示されない)</li>
 * <li>トップページがなかなか表示されないため、リロードを繰り返す。</li>
 * </ol>
 * <br>
 * このような操作が行われた場合、2で発生したリクエストを処理中のロック待ちスレッドや、 3で繰り返しているリクエストのうち、最後のリクエスト以外を処理中のロック待ちスレッドは、
 * 処理を続けても、レスポンスがクライアントの画面には反映されないため、ロック待ち自体を中断した方がよい。<br>
 * この中断機能を実装したのが、このクラスである。
 * </p>
 * <p>
 * このクラス内部の基本的なルールは以下の通りである。<br>
 * <ul>
 * <li>ロックを要求したスレッドが、ロック待ちせずに中断されることはない。</li>
 * <li>既にロックを取得しているスレッドに対し、スレッド割り込みをかけることはない。</li>
 * <li>ロック待ちを中断されるスレッドは、ロック待ちスレッドの中でも、もっとも古いロック待ちスレッド(他のスレッドよりも、早くロック待ちとなったスレッド)である。</li>
 * </ul>
 * (クラス外部から割り込みステータスが設定された場合は例外。例えば、既に割り込みステータスを設定されているスレッドがロック待ちしようとした場合、スーパークラスの機能により、ロック待ちせずに中断される。)
 * </p>
 * <p>
 * このクラス内部の基本的な動作は以下の通りである。<br>
 * <ul>
 * <li>このロックを待つスレッドの数がしきい値を超えている状態のときに、 さらに他のスレッドがこのロックを要求したとき、ロックが取得できるのを待つ前に、 しきい値を超えていた分のスレッドを、古いロック待ちスレッドから中断させる。</li>
 * </ul>
 * </p>
 * <p>
 * 例) しきい値が2のとき<br>
 * スレッド1, 2, 3, 4の順でロックを要求した場合、 スレッド1がロックを取得し、スレッド2, 3, 4がロック待ちとなる。<br>
 * このとき、ロック待ちスレッド(3)がしきい値(2)を超えているので、 さらにスレッド5がロックを要求したとき、 もっとも古いロック待ちスレッドである、スレッド2のロック待ちを中断し、 スレッド5がロック待ち状態となる。<br>
 * </p>
 * <p>
 * ロックを取得したスレッドがロックを返すまでの間、 別のスレッドが順次、次々とロックを要求した場合(並行して要求しない場合)、 ロック待ちスレッド数は、しきい値と、しきい値＋1の間を遷移する。<br>
 * また、新たなロック要求がない期間においては、ロック待ちスレッド数は、しきい値＋1となる。<br>
 * ただし、並行して要求があった場合の、ロック待ちスレッド数の上限は保証しない。<br>
 * また、外部で同期化してはならない。<br>
 * (同期化すると、多数のスレッドをロック待ちにしてしまう事象を回避するという、このクラスの目的が果たせない。)<br>
 * よって、ロック待ちスレッド数の上限は、ベストエフォートとなる。<br>
 * </p>
 * <p>
 * このクラスでは、lockInterruptiblyメソッドでロックを取得し、 unlockメソッドでロックを解放する。<br>
 * その他のロック制御メソッド(スーパークラスで用意されているメソッド)は使用しないこと。
 * </p>
 * <p>
 * ロック待ちが中断されたスレッドは、 lockInterruptiblyメソッドを実行中に、InterruptedExceptionが発生することにより、 ロック待ち状態から復帰する。
 * </p>
 * <p>
 * このクラスの中断(スレッド割り込み)機構により、InterruptedExceptionが発生した場合、 スレッドの割り込みステータスはクリアされる。<br>
 * ただし、このクラスの外部からのスレッド割り込みがある場合には、このクラスの処理を抜けた際の スレッドの割り込みステータスは不定である。<br>
 * 外部割り込みのタイミングによっては、 InterruptedExceptionが発生しつつ、割り込みステータスも設定されている状態となることがある。
 * (InterruptedException発生後、割り込みステータスクリアコードが実行された後に、外部割り込みが発生した場合が該当。)
 * </p>
 * <p>
 * コード記述例：<br>
 * <code><pre>
 * LimitedLock lock;
 * … 同期化したい範囲で共有するLimitedLockインスタンスを取得
 * try {
 *     lock.lockInterruptibly();
 *     … ロック取得後の処理
 * } catch (InterruptedException e) {
 *     … ロック待ち中断時の処理
 * } finally {
 *     lock.unlock(); // ロックの取得に成功したか失敗したかの事前判定は不要。(内部で自動判定)
 * }
 * </pre></code>
 * </p>
 * <p>
 * このクラスは、スーパークラスでSerializableが実装されているため、直列化可能な実装としているが、使い方次第では、このクラスの目的を果たすことが出来なくなる可能性が高いため、シリアライズ/デシリアライズの使用は推奨しない。
 * (セッションに格納することも推奨しない。) なお、デシリアライズ時は、スーパークラスと同様、シリアライズされたときの状態に関わらず、ロックが解除された状態となる。
 * </p>
 * @see ReentrantLock
 */
public class LimitedLock extends ReentrantLock {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 894432960610700290L;

    /**
     * ログクラス。
     */
    private static final Log log = LogFactory.getLog(LimitedLock.class);

    /**
     * ロックオブジェクト。
     */
    private transient Object lock = new Object();

    /**
     * しきい値
     */
    private int threshold;

    /**
     * ロック待ち順序を保持するList。<br>
     * このListは、あくまで順序判定用であり、List内にあるスレッドが実際にロック待ちしているとは限らない。<br>
     * (このListにaddしてからロックを試みるため、将来ロック待ちになるスレッドと、ロック待ちすることなくロックを得られるスレッドが含まれる。)
     */
    private transient LinkedList<Thread> waitingThreadList = new LinkedList<Thread>();

    /**
     * コンストラクタ。
     * @param threshold しきい値(0以下の場合は、0として扱う)
     */
    public LimitedLock(int threshold) {
        if (threshold > 0) {
            this.threshold = threshold;
        } else {
            this.threshold = 0;
        }
    }

    /**
     * ロックを取得する。
     * <p>
     * 現在のスレッドがロックが取得できるか、他のスレッドが現在のスレッドに割り込みを行うまで、現在のスレッドは待機する。<br>
     * 現在のスレッドがロックが取得できた場合、メソッドを復帰する。<br>
     * 他のスレッドが現在のスレッドに割り込みを行った場合、InterruptedExceptionがスローされ、現在のスレッドの割り込みステータスがクリアされる。<br>
     * (ただし、クラス外部からの割り込みがあった場合は、割り込みステータスは不定。)
     * </p>
     * <p>
     * 上記は、スーパークラスによるもの。拡張ポイントは以下の通り。<br>
     * <ul>
     * <li>このロックを待つスレッドの数がしきい値を超えている状態でこのメソッドが実行されたとき、ロックが取得できるのを待つ前に、しきい値を超えていた分のスレッドを、古いロック待ちスレッドから中断させる。<br>
     * 既にロックを取得しているスレッドがこのメソッドを実行したとき(再入ロック時)は、ロック待ちスレッドの数に関わらず、スレッドの中断は行わない。</li>
     * </ul>
     * </p>
     * @throws InterruptedException 現在のスレッドで割り込みが発生した場合(このクラスの機能により、ロック待ちが中断された場合を含む)
     * @see java.util.concurrent.locks.ReentrantLock#lockInterruptibly()
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        boolean successToLock = false;
        // ロック待ちスレッド数が一定数を超えた状態で、
        // 新たなスレッドがロックを要求したとき、
        // ロック待ちスレッドに割り込みをかける。
        // (既にロック取得済みのスレッドが再帰的にロック(再入ロック)を掛けた場合は、
        // ロック待ちスレッドに割り込みをかけることはしない。)
        if (getOwner() != Thread.currentThread()) {
            synchronized (lock) {
                // このブロック処理中は、super.unlock();が実行されないので、ロック待ちスレッドがロックを取得することはない。
                // どのスレッドもロックを取得していなかった場合、
                // ロックを待っていなかったスレッドがロックを取得する事はあるが、
                // そのスレッドは、状態遷移において、ロック待ち状態を経由せずにロック取得状態となるため、
                // ロックを取得できてこのメソッドを抜けるスレッドを、誤って割り込み対象スレッドと判定することはない。
                // なお、いずれかのスレッドが既にロックを取得している状態で、
                // その他に、このブロックを抜け、super.lockInterruptibly();を実行する前のスレッドが存在した場合、
                // そのスレッドは、このブロック内の処理において、割り込み対象になる場合とならない場合がある
                // (ロック待ちスレッド数が一定数を超えていても、まだ待ちスレッドになっていなければ、割り込み対象にはならない)が、
                // そのスレッドがロック待ち状態のまま、さらにロック要求スレッド数がしきい値を超えた際に、
                // 割り込み対象とする機会を得るので、支障はない。
                int queueLength = getQueueLength();
                if (queueLength > threshold) {
                    HashSet<Thread> oldWaitingThreadSet = null;
                    synchronized (waitingThreadList) {
                        List<Thread> oldWaitingThreadList = waitingThreadList
                                .subList(0, queueLength - threshold);
                        oldWaitingThreadSet = new HashSet<Thread>(
                                oldWaitingThreadList);
                    }
                    // waitingThreadListから得られるスレッドの中には、
                    // 実際にはロック待ちしていないスレッドが含まれるので、
                    // 実際にロック待ちしているスレッドリストは、
                    // oldWaitingThreadListやoldWaitingThreadSetからではなく、getQueuedThreads()から取得する。
                    for (Thread queuedThread : getQueuedThreads()) {
                        if (oldWaitingThreadSet.contains(queuedThread)) {
                            if (log.isDebugEnabled()) {
                                log.debug("interrupt thread '" + queuedThread
                                        + "'.");
                            }
                            synchronized (waitingThreadList) {
                                // ＜waitingThreadList.removeを、割り込まれたスレッドのfinally以外に、ここでも行っていることについて＞
                                // 割り込まれたスレッドが自らremoveする前に、他のスレッドが割り込み判断を行う可能性があるため、
                                // その判断時に割り込み済みスレッドの情報が得られないよう、
                                // ここで直ちにremoveする。
                                waitingThreadList.remove(queuedThread);
                                queuedThread.interrupt();

                                // 割り込みをかけても、
                                // スレッドがロック待ちキューから出るまでにタイムラグがあり、
                                // その間に別のスレッドが、割り込み判定最初のgetQueueLength()を実行すると、
                                // getQueueLength()が大きいまま、waitingThreadListだけが小さくなって、
                                // waitingThreadList.subListが失敗(Listより大きなsubListを取ろうとして失敗)してしまうので、
                                // ここ(synchronized (lock)内)で、スレッドがロック待ちキューから出るまで待つ。
                                while (getQueuedThreads()
                                        .contains(queuedThread)) {
                                    Thread.yield();
                                }
                            }
                        }
                    }
                }
            }
        }

        try {
            synchronized (waitingThreadList) {
                waitingThreadList.add(Thread.currentThread());
            }
            super.lockInterruptibly();
            successToLock = true;
        } finally {
            // 万一外部割り込みがあり、
            // そのときにこのクラス内での割り込み(判定および実行)処理も実行されていた場合
            // このクラス内での割り込み(判定/実行)処理が終了するまで待ってから
            // 後処理を実行する必要があるので、
            // lockフィールドをロック。
            synchronized (lock) {
                synchronized (waitingThreadList) {
                    waitingThreadList.remove(Thread.currentThread()); // 外部割り込み時にはremoveされていないので、ここでremove
                    if (!successToLock) {
                        // 外部割り込み後にこのクラスから割り込みをかけた場合、
                        // 割り込みステータスが残るので、
                        // ここで割り込みステータスをクリアする。
                        // ロック取得に成功して、returnされるまでの間に外部割り込みが入った場合、
                        // 割り込みステータスはクリアせずにreturnする。
                        Thread.interrupted();
                    }
                }
            }
        }
    }

    /**
     * ロックを解放する。
     * <p>
     * 現在のスレッドがこのロックのホルダである場合、スーパークラスのメソッドにより、ロックを解放する。<br>
     * </p>
     * <p>
     * このクラスでの拡張ポイントは以下の通り。<br>
     * ・このロックのホルダでないスレッドがこのメソッドを実行しても、例外をスローしない。(何もせずに復帰する)<br>
     * </p>
     * @see java.util.concurrent.locks.ReentrantLock#unlock()
     */
    @Override
    public void unlock() {
        if (getOwner() != Thread.currentThread()) {
            return;
        }
        synchronized (lock) {
            super.unlock();

            // ロック待ちスレッドのいずれもまだロックを取得していない間(一瞬の間)は、
            // 別スレッドで、スレッド中断の判定/実行を行うsynchronizedブロックが動作しないよう、
            // synchronizedブロック内にとどまる。
            while (getQueueLength() > 0 && getOwner() == null) {
                Thread.yield();
            }
        }
    }

    /**
     * デシリアライズ処理(拡張)。
     * <p>
     * デシリアライズした後も、コンストラクタで生成したときと同様に動作するよう、シリアライズ/デシリアライズ不可能なフィールドを再構築する。<br>
     * ただし、これはあくまでスーパークラスで実装されたSerializableを満たすための実装であり、シリアライズ/デシリアライズの使用は推奨しない。
     * </p>
     * @param stream ObjectInputStream
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     * @see ObjectInputStream
     */
    private void readObject(java.io.ObjectInputStream stream)
                                                             throws java.io.IOException,
                                                             ClassNotFoundException {
        stream.defaultReadObject();
        lock = new Object();
        waitingThreadList = new LinkedList<Thread>();
    }
}
