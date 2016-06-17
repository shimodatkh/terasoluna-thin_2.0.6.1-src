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
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 同一セッションの処理の同期化を行う。
 * <p>
 * 同一セッションの処理を複数スレッドで同時に実行したくない場合に、このフィルタを使用する。<br>
 * セッションスコープのアクションフォームを使用する場合、同一セッションの処理を複数スレッドで同時に実行すべきではないため、このフィルタを使用する。<br>
 * (セッションスコープのアクションフォームを使用する場合、同一セッションの処理を複数スレッドで同時に実行されると、入力値検証が通った後、ActionやBLogicの処理に移る前に、アクションフォームが書き換えられる可能性がある。)<br>
 * </p>
 * <p>
 * このフィルタでは、２つのロック方式を提供している。<br>
 * <ul>
 * <li>{@link LimitedLock}によるロック(ロック待ちスレッドが増えた際に、古いロック待ちスレッドを中断する機能を持つロック)</li>
 * <li>synchronizedブロックによるロック</li>
 * </ul>
 * デフォルトでは、{@link LimitedLock}(しきい値=2)によるロックを使用する。<br>
 * ※{@link LimitedLock}のロック方式やしきい値、このロック方式の存在意義は、{@link LimitedLock}を参照のこと。
 * </p>
 * <p>
 * {@link LimitedLock}のしきい値の変更や、synchronizedブロックによるロック方式への切り替えは、このフィルタの初期化パラメータthresholdにて行う。<br>
 * threshold≧0の場合は、thresholdの値をしきい値として使用し、{@link LimitedLock}によるロックを行う。<br>
 * threshold＜0の場合は、synchronizedブロックによるロックを行う。<br>
 * ※thresholdには整数値を設定すること。
 * </p>
 * <p>
 * {@link LimitedLock}の機能により、ロック待ちが中断されたスレッドでは、レスポンスとして、特定のレスポンスコードを返すことができる。また、デプロイメントディスクリプタに&lt;error-page&gt;要素を記述することにより、
 * レスポンスコードに対応するエラーページを割り当てることができる。<br>
 * ただし、ユーザが同一セッションで複数ウィンドウを操作しない限り、ロック待ちが中断されたスレッドにて、レスポンスに何を返しても、ユーザには見えない。<br>
 * (最新のリクエストに対するレスポンスだけがブラウザに表示されるが、最新のリクエストを処理しようとしているスレッドは中断対象にならない。ロック待ちが中断されたスレッドは、古いリクエストを処理するものであり、ブラウザはそのレスポンスを無視する。)
 * デフォルトでは、ロック待ち中断時のレスポンスコードは503(過負荷状態で一時的に処理が実行できない状態であることを表すレスポンスコード)となっている。<br>
 * レスポンスコードの設定は、このフィルタの初期化パラメータinterruptResponseCodeにて行う。<br>
 * ※interruptResponseCodeには整数値を設定すること。また、このクラスでは値の範囲を制限しないが、JavaEEサーバが使用できるレスポンスコードを設定すること。<br>
 * </p>
 * <h5>使用方法</h5> この機能を使用するにはデプロイメントディスクリプタ（web.xml）に以下のように 設定する。<br>
 * <br>
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;sessionLockControlFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;jp.terasoluna.fw.web.thin.SessionLockControlFilter&lt;/filter-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;interruptResponseCode&lt;/param-name&gt;
 *     &lt;param-value&gt;503&lt;/param-value&gt;
 *   &lt;/init-param&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;threshold&lt;/param-name&gt;
 *     &lt;param-value&gt;2&lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/filter&gt;
 * 
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;sessionLockControlFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;*.do&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * 
 * &lt;error-page&gt;
 *   &lt;error-code&gt;503&lt;/error-code&gt;
 *   &lt;location&gt;/error.jsp&lt;/location&gt;
 * &lt;/error-page&gt;
 * </pre></code> <br>
 * なお、各初期化パラメータにおいて、デフォルト値(interruptResponseCode=503、threshold=2)を利用する場合は、 デプロイメントディスクリプタ
 * （web.xml）内の&lt;filter&gt;要素から&lt;init-param&gt;要素を省略することができる。<br>
 * また、エラーページを設定しない場合は、&lt;error-page&gt;要素を省略することができる。
 * @see LimitedLock
 */
public class SessionLockControlFilter implements Filter {

    /**
     * ログクラス。
     */
    private static final Log log = LogFactory
            .getLog(SessionLockControlFilter.class);

    /**
     * 初期化パラメータ名：interruptResponseCode。
     */
    private static final String INIT_PARAM_INTERRUPT_RESPONSE_CODE = "interruptResponseCode";

    /**
     * 初期化パラメータ名：threshold。
     */
    private static final String INIT_PARAM_THRESHOLD = "threshold";

    /**
     * 初期化パラメータinterruptResponseCodeのデフォルト値(503)。
     */
    private static final int DEFAULT_INTERRUPT_RESPONSE_CODE = HttpServletResponse.SC_SERVICE_UNAVAILABLE; // 503

    /**
     * 初期化パラメータthresholdのデフォルト値(2)。
     */
    private static final int DEFAULT_THRESHOLD = 2;

    /**
     * 初期化パラメータinterruptResponseCode。
     * <p>
     * ロック待ち中断時のレスポンスコード。
     * </p>
     */
    private int interruptResponseCode = DEFAULT_INTERRUPT_RESPONSE_CODE;

    /**
     * 初期化パラメータthreshold。
     * <p>
     * 0以上のとき、{@link LimitedLock}に渡すしきい値となる。<br>
     * 0未満のとき、{@link LimitedLock}を使用せず、synchronizedブロックを使用する。
     * </p>
     */
    protected int threshold = DEFAULT_THRESHOLD;

    /**
     * セッション同期に用いている{@link LimitedLock}の参照キュー。
     * <p>
     * セッション同期に用いている{@link LimitedLock}インスタンスが参照されなくなり、ガベージコレクタに回収されるとき、ガベージコレクタによって、 {@link LimitedLock}インスタンスを保持していた
     * {@link SessionLockReference}がこの参照キューに追加される。<br>
     * {@link limitedLockMap}内で不要になったエントリーを削除する際に利用する。
     * </p>
     */
    private ReferenceQueue<LimitedLock> sessionLockRefQueue = new ReferenceQueue<LimitedLock>();

    /**
     * {@link LimitedLock}の弱参照マップ。
     * <p>
     * キーはセッションID、値は、{@link LimitedLock}への弱参照である{@link SessionLockReference}。<br>
     * あるセッションID用に{@link LimitedLock}インスタンスを用意した場合、{@link SessionLockReference}にラップしてからこのマップにputする。<br>
     * この、あるセッションID用に用意した{@link LimitedLock}が、いずれかのスレッドで使用中である場合、 このマップから、そのセッションIDをキーに{@link SessionLockReference}が、さらに、
     * {@link SessionLockReference}から、そのセッションID用の{@link LimitedLock}が取得可能である。<br>
     * この、あるセッションID用に用意した{@link LimitedLock}を、どのスレッドも参照していないとき、この{@link LimitedLock}はガベージコレクタによって回収可能となる。<br>
     * {@link LimitedLock}がガベージコレクタに回収された場合、このマップから得られた{@link SessionLockReference}からは、{@link LimitedLock}を得ることができない。<br>
     * この場合、{@link LimitedLock}が取得できないエントリーが一時的に残るが、sessionLockRefQueueと連携することにより、不要になったエントリーは、じきに削除される。
     * </p>
     */
    private ConcurrentHashMap<String, SessionLockReference> limitedLockMap = new ConcurrentHashMap<String, SessionLockReference>();

    /**
     * 同一セッションの処理の同期化を行う。
     * @param req HTTPリクエスト
     * @param res HTTPレスポンス
     * @param chain フィルタチェーン
     * @throws IOException I/Oエラー
     * @throws ServletException サーブレット例外
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        if (session != null) {
            // セッションが利用されているかつ有効なので、同期化制御を行う。
            if (threshold < 0) {
                // しきい値を無効にしている場合、
                // 待ちスレッドが溜まっても、何もしない、
                // 単純なsynchronizedによる同期化を行う。
                log.debug("use synchronized lock.");
                synchronized (session.getId().intern()) {
                    chain.doFilter(req, res);
                }
            } else {
                // 中身が空になった、SessionLockReferenceを値にもつ、
                // limitedLockMapのエントリーを削除する。
                // (LimitedLockが参照されていない状態で、GCが発生したとき(LimitedLockインスタンスが回収されたとき)に、
                // SessionLockReferenceの中身は空になる。)
                SessionLockReference oldRef = null;
                while ((oldRef = (SessionLockReference) sessionLockRefQueue
                        .poll()) != null) {

                    // limitedLockMap内のエントリーが削除して良いものであるか、
                    // 確認してから削除する。
                    // (LimitedLockが参照されなくなってから、
                    // SessionLockReference(WeakReference)がReferenceQueueに入るまでには
                    // かなりのタイムラグがあるので、
                    // limitedLockMap内のSessionLockReferenceが既に差し替わっていないか、ここで確認する。)
                    // 確認から削除までの間に、同一セッションIDでputされると、誤って削除しかねないので、
                    // put時にもロックしている、セッションIDでロックする。
                    synchronized (oldRef.getSessionId().intern()) {
                        if (oldRef == limitedLockMap.get(oldRef.getSessionId())) {
                            limitedLockMap.remove(oldRef.getSessionId());
                        }
                        if (log.isDebugEnabled()) {
                            log
                                    .debug("LimitedLock is deallocated. sessionId = "
                                            + oldRef.getSessionId()
                                            + ", SessionLockReference = "
                                            + oldRef);
                        }
                    }
                }

                LimitedLock lock = null;
                synchronized (session.getId().intern()) {
                    SessionLockReference sessionLockRef = limitedLockMap
                            .get(session.getId());
                    if (sessionLockRef != null) {
                        lock = sessionLockRef.get();
                    }
                    if (lock == null) {
                        lock = createLimitedLock();
                        sessionLockRef = new SessionLockReference(session
                                .getId(), lock, sessionLockRefQueue);

                        limitedLockMap.put(session.getId(), sessionLockRef);
                        if (log.isDebugEnabled()) {
                            log.debug("LimitedLock is allocated. sessionId = "
                                    + session.getId() + ", "
                                    + INIT_PARAM_THRESHOLD + " = " + threshold
                                    + ", SessionLockReference = "
                                    + sessionLockRef);
                        }
                    }
                }

                try {
                    log.debug("use LimitedLock.");
                    lockLimitedLock((HttpServletRequest) req, lock);
                    chain.doFilter(req, res);
                } catch (InterruptedException e) {
                    // 同一セッションで処理が動いている間に、
                    // 度重なるリロード等で、ロック待ちスレッドが閾値を超えたため、
                    // 古いリクエストに対する処理を実行せず、
                    // 固定レスポンスを返して終了。
                    log.info("interrupt wait for lock.");
                    if (interruptResponseCode >= 0) {
                        ((HttpServletResponse) res)
                                .sendError(interruptResponseCode);
                    }
                } finally {
                    unlockLimitedLock((HttpServletRequest) req, lock);
                }
            }
        } else {
            log.debug("not lock.");
            chain.doFilter(req, res);
        }
    }

    /**
     * LimitedLockインスタンスを生成する。
     * <p>
     * LimitedLockを拡張した場合、このメソッドをオーバーライドし、LimitedLock拡張クラスのインスタンスを返すよう拡張する。
     * </p>
     * @return LimitedLockインスタンス
     */
    protected LimitedLock createLimitedLock() {
        return new LimitedLock(threshold);
    }

    /**
     * LimitedLockのロックを取得する。
     * <p>
     * ロック取得前後に処理を追加する拡張点。<br>
     * 必要に応じて、このメソッドを拡張すること。<br>
     * </p>
     * <p>
     * 拡張例)<br>
     * レスポンスを返す前に、任意の場所でロックを解放したい場合等、フィルタ以外から、ロックを取得しているLimitedLockにアクセスしたい場合、ここでLimitedLockをリクエスト属性に設定するよう拡張する。
     * </p>
     * @param request HTTPリクエスト
     * @param lock LimitedLockインスタンス
     * @throws InterruptedException 現在のスレッドで割り込みが発生した場合(LimitedLockの機能により、ロック待ちが中断された場合を含む)
     */
    protected void lockLimitedLock(HttpServletRequest request, LimitedLock lock)
                                                                                throws InterruptedException {
        lock.lockInterruptibly();
    }

    /**
     * LimitedLockのロックを解放する。
     * <p>
     * ロック解放前後に処理を追加する拡張点。<br>
     * 必要に応じて、このメソッドを拡張すること。
     * </p>
     * @param request HTTPリクエスト
     * @param lock LimitedLockインスタンス
     */
    protected void unlockLimitedLock(HttpServletRequest request,
            LimitedLock lock) {
        lock.unlock();
    }

    /**
     * フィルタがサービス開始状態になる際に、コンテナによって呼び出される。 コンテナは、Filterをインスタンス化した後に、initメソッドを 1 回だけ呼び出す。<br>
     * Filterにフィルタ処理作業を実行するように要求するには、 init メソッドが正常に 終了していなければならない。 initメソッドが 次のいずれかの状態の場合、コンテナは Filterをサービス状態にできない。<br>
     * <ul>
     * <li>ServletException をスローした。</li>
     * <li>コンテナによって定義された時間内に、復帰しない。</li>
     * <li>設定異常時。</li>
     * </ul>
     * @param config FilterConfigインスタンス。
     * @throws javax.servlet.ServletException 初期化異常時にスローされる例外。
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     * @see jp.terasoluna.fw.web.thin.AbstractControlFilter
     */
    public void init(FilterConfig config) throws ServletException {
        String interruptResponseCodeStr = config
                .getInitParameter(INIT_PARAM_INTERRUPT_RESPONSE_CODE);
        if (interruptResponseCodeStr != null) {
            interruptResponseCode = Integer.parseInt(interruptResponseCodeStr);
        }

        String thresholdStr = config.getInitParameter(INIT_PARAM_THRESHOLD);
        if (thresholdStr != null) {
            threshold = Integer.parseInt(thresholdStr);
        }

        if (log.isDebugEnabled()) {
            log.debug(INIT_PARAM_INTERRUPT_RESPONSE_CODE + " = "
                    + interruptResponseCode + ".");
            if (threshold >= 0) {
                log.debug(INIT_PARAM_THRESHOLD + " = " + threshold
                        + ". LimitedLock is enabled.");
            } else {
                log.debug(INIT_PARAM_THRESHOLD + " = " + threshold
                        + ". LimitedLock is disabled. Reason: "
                        + INIT_PARAM_THRESHOLD + " is negative number.");
            }
        }
    }

    /**
     * サービス状態を終えた事をフィルタに伝えるために、コンテナが呼び出す。<br>
     * このクラスでは処理は行なわない。
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // 特になし
    }
}
