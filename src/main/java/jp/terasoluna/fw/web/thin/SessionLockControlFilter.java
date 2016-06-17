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
 * ����Z�b�V�����̏����̓��������s���B
 * <p>
 * ����Z�b�V�����̏����𕡐��X���b�h�œ����Ɏ��s�������Ȃ��ꍇ�ɁA���̃t�B���^���g�p����B<br>
 * �Z�b�V�����X�R�[�v�̃A�N�V�����t�H�[�����g�p����ꍇ�A����Z�b�V�����̏����𕡐��X���b�h�œ����Ɏ��s���ׂ��ł͂Ȃ����߁A���̃t�B���^���g�p����B<br>
 * (�Z�b�V�����X�R�[�v�̃A�N�V�����t�H�[�����g�p����ꍇ�A����Z�b�V�����̏����𕡐��X���b�h�œ����Ɏ��s�����ƁA���͒l���؂��ʂ�����AAction��BLogic�̏����Ɉڂ�O�ɁA�A�N�V�����t�H�[����������������\��������B)<br>
 * </p>
 * <p>
 * ���̃t�B���^�ł́A�Q�̃��b�N������񋟂��Ă���B<br>
 * <ul>
 * <li>{@link LimitedLock}�ɂ�郍�b�N(���b�N�҂��X���b�h���������ۂɁA�Â����b�N�҂��X���b�h�𒆒f����@�\�������b�N)</li>
 * <li>synchronized�u���b�N�ɂ�郍�b�N</li>
 * </ul>
 * �f�t�H���g�ł́A{@link LimitedLock}(�������l=2)�ɂ�郍�b�N���g�p����B<br>
 * ��{@link LimitedLock}�̃��b�N�����₵�����l�A���̃��b�N�����̑��݈Ӌ`�́A{@link LimitedLock}���Q�Ƃ̂��ƁB
 * </p>
 * <p>
 * {@link LimitedLock}�̂������l�̕ύX��Asynchronized�u���b�N�ɂ�郍�b�N�����ւ̐؂�ւ��́A���̃t�B���^�̏������p�����[�^threshold�ɂčs���B<br>
 * threshold��0�̏ꍇ�́Athreshold�̒l���������l�Ƃ��Ďg�p���A{@link LimitedLock}�ɂ�郍�b�N���s���B<br>
 * threshold��0�̏ꍇ�́Asynchronized�u���b�N�ɂ�郍�b�N���s���B<br>
 * ��threshold�ɂ͐����l��ݒ肷�邱�ƁB
 * </p>
 * <p>
 * {@link LimitedLock}�̋@�\�ɂ��A���b�N�҂������f���ꂽ�X���b�h�ł́A���X�|���X�Ƃ��āA����̃��X�|���X�R�[�h��Ԃ����Ƃ��ł���B�܂��A�f�v���C�����g�f�B�X�N���v�^��&lt;error-page&gt;�v�f���L�q���邱�Ƃɂ��A
 * ���X�|���X�R�[�h�ɑΉ�����G���[�y�[�W�����蓖�Ă邱�Ƃ��ł���B<br>
 * �������A���[�U������Z�b�V�����ŕ����E�B���h�E�𑀍삵�Ȃ�����A���b�N�҂������f���ꂽ�X���b�h�ɂāA���X�|���X�ɉ���Ԃ��Ă��A���[�U�ɂ͌����Ȃ��B<br>
 * (�ŐV�̃��N�G�X�g�ɑ΂��郌�X�|���X�������u���E�U�ɕ\������邪�A�ŐV�̃��N�G�X�g���������悤�Ƃ��Ă���X���b�h�͒��f�ΏۂɂȂ�Ȃ��B���b�N�҂������f���ꂽ�X���b�h�́A�Â����N�G�X�g������������̂ł���A�u���E�U�͂��̃��X�|���X�𖳎�����B)
 * �f�t�H���g�ł́A���b�N�҂����f���̃��X�|���X�R�[�h��503(�ߕ��׏�Ԃňꎞ�I�ɏ��������s�ł��Ȃ���Ԃł��邱�Ƃ�\�����X�|���X�R�[�h)�ƂȂ��Ă���B<br>
 * ���X�|���X�R�[�h�̐ݒ�́A���̃t�B���^�̏������p�����[�^interruptResponseCode�ɂčs���B<br>
 * ��interruptResponseCode�ɂ͐����l��ݒ肷�邱�ƁB�܂��A���̃N���X�ł͒l�͈̔͂𐧌����Ȃ����AJavaEE�T�[�o���g�p�ł��郌�X�|���X�R�[�h��ݒ肷�邱�ƁB<br>
 * </p>
 * <h5>�g�p���@</h5> ���̋@�\���g�p����ɂ̓f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�Ɉȉ��̂悤�� �ݒ肷��B<br>
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
 * �Ȃ��A�e�������p�����[�^�ɂ����āA�f�t�H���g�l(interruptResponseCode=503�Athreshold=2)�𗘗p����ꍇ�́A �f�v���C�����g�f�B�X�N���v�^
 * �iweb.xml�j����&lt;filter&gt;�v�f����&lt;init-param&gt;�v�f���ȗ����邱�Ƃ��ł���B<br>
 * �܂��A�G���[�y�[�W��ݒ肵�Ȃ��ꍇ�́A&lt;error-page&gt;�v�f���ȗ����邱�Ƃ��ł���B
 * @see LimitedLock
 */
public class SessionLockControlFilter implements Filter {

    /**
     * ���O�N���X�B
     */
    private static final Log log = LogFactory
            .getLog(SessionLockControlFilter.class);

    /**
     * �������p�����[�^���FinterruptResponseCode�B
     */
    private static final String INIT_PARAM_INTERRUPT_RESPONSE_CODE = "interruptResponseCode";

    /**
     * �������p�����[�^���Fthreshold�B
     */
    private static final String INIT_PARAM_THRESHOLD = "threshold";

    /**
     * �������p�����[�^interruptResponseCode�̃f�t�H���g�l(503)�B
     */
    private static final int DEFAULT_INTERRUPT_RESPONSE_CODE = HttpServletResponse.SC_SERVICE_UNAVAILABLE; // 503

    /**
     * �������p�����[�^threshold�̃f�t�H���g�l(2)�B
     */
    private static final int DEFAULT_THRESHOLD = 2;

    /**
     * �������p�����[�^interruptResponseCode�B
     * <p>
     * ���b�N�҂����f���̃��X�|���X�R�[�h�B
     * </p>
     */
    private int interruptResponseCode = DEFAULT_INTERRUPT_RESPONSE_CODE;

    /**
     * �������p�����[�^threshold�B
     * <p>
     * 0�ȏ�̂Ƃ��A{@link LimitedLock}�ɓn���������l�ƂȂ�B<br>
     * 0�����̂Ƃ��A{@link LimitedLock}���g�p�����Asynchronized�u���b�N���g�p����B
     * </p>
     */
    protected int threshold = DEFAULT_THRESHOLD;

    /**
     * �Z�b�V���������ɗp���Ă���{@link LimitedLock}�̎Q�ƃL���[�B
     * <p>
     * �Z�b�V���������ɗp���Ă���{@link LimitedLock}�C���X�^���X���Q�Ƃ���Ȃ��Ȃ�A�K�x�[�W�R���N�^�ɉ�������Ƃ��A�K�x�[�W�R���N�^�ɂ���āA {@link LimitedLock}�C���X�^���X��ێ����Ă���
     * {@link SessionLockReference}�����̎Q�ƃL���[�ɒǉ������B<br>
     * {@link limitedLockMap}���ŕs�v�ɂȂ����G���g���[���폜����ۂɗ��p����B
     * </p>
     */
    private ReferenceQueue<LimitedLock> sessionLockRefQueue = new ReferenceQueue<LimitedLock>();

    /**
     * {@link LimitedLock}�̎�Q�ƃ}�b�v�B
     * <p>
     * �L�[�̓Z�b�V����ID�A�l�́A{@link LimitedLock}�ւ̎�Q�Ƃł���{@link SessionLockReference}�B<br>
     * ����Z�b�V����ID�p��{@link LimitedLock}�C���X�^���X��p�ӂ����ꍇ�A{@link SessionLockReference}�Ƀ��b�v���Ă��炱�̃}�b�v��put����B<br>
     * ���́A����Z�b�V����ID�p�ɗp�ӂ���{@link LimitedLock}���A�����ꂩ�̃X���b�h�Ŏg�p���ł���ꍇ�A ���̃}�b�v����A���̃Z�b�V����ID���L�[��{@link SessionLockReference}���A����ɁA
     * {@link SessionLockReference}����A���̃Z�b�V����ID�p��{@link LimitedLock}���擾�\�ł���B<br>
     * ���́A����Z�b�V����ID�p�ɗp�ӂ���{@link LimitedLock}���A�ǂ̃X���b�h���Q�Ƃ��Ă��Ȃ��Ƃ��A����{@link LimitedLock}�̓K�x�[�W�R���N�^�ɂ���ĉ���\�ƂȂ�B<br>
     * {@link LimitedLock}���K�x�[�W�R���N�^�ɉ�����ꂽ�ꍇ�A���̃}�b�v���瓾��ꂽ{@link SessionLockReference}����́A{@link LimitedLock}�𓾂邱�Ƃ��ł��Ȃ��B<br>
     * ���̏ꍇ�A{@link LimitedLock}���擾�ł��Ȃ��G���g���[���ꎞ�I�Ɏc�邪�AsessionLockRefQueue�ƘA�g���邱�Ƃɂ��A�s�v�ɂȂ����G���g���[�́A�����ɍ폜�����B
     * </p>
     */
    private ConcurrentHashMap<String, SessionLockReference> limitedLockMap = new ConcurrentHashMap<String, SessionLockReference>();

    /**
     * ����Z�b�V�����̏����̓��������s���B
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @param chain �t�B���^�`�F�[��
     * @throws IOException I/O�G���[
     * @throws ServletException �T�[�u���b�g��O
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        if (session != null) {
            // �Z�b�V���������p����Ă��邩�L���Ȃ̂ŁA������������s���B
            if (threshold < 0) {
                // �������l�𖳌��ɂ��Ă���ꍇ�A
                // �҂��X���b�h�����܂��Ă��A�������Ȃ��A
                // �P����synchronized�ɂ�铯�������s���B
                log.debug("use synchronized lock.");
                synchronized (session.getId().intern()) {
                    chain.doFilter(req, res);
                }
            } else {
                // ���g����ɂȂ����ASessionLockReference��l�ɂ��A
                // limitedLockMap�̃G���g���[���폜����B
                // (LimitedLock���Q�Ƃ���Ă��Ȃ���ԂŁAGC�����������Ƃ�(LimitedLock�C���X�^���X��������ꂽ�Ƃ�)�ɁA
                // SessionLockReference�̒��g�͋�ɂȂ�B)
                SessionLockReference oldRef = null;
                while ((oldRef = (SessionLockReference) sessionLockRefQueue
                        .poll()) != null) {

                    // limitedLockMap���̃G���g���[���폜���ėǂ����̂ł��邩�A
                    // �m�F���Ă���폜����B
                    // (LimitedLock���Q�Ƃ���Ȃ��Ȃ��Ă���A
                    // SessionLockReference(WeakReference)��ReferenceQueue�ɓ���܂łɂ�
                    // ���Ȃ�̃^�C�����O������̂ŁA
                    // limitedLockMap����SessionLockReference�����ɍ����ւ���Ă��Ȃ����A�����Ŋm�F����B)
                    // �m�F����폜�܂ł̊ԂɁA����Z�b�V����ID��put�����ƁA����č폜�����˂Ȃ��̂ŁA
                    // put���ɂ����b�N���Ă���A�Z�b�V����ID�Ń��b�N����B
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
                    // ����Z�b�V�����ŏ����������Ă���ԂɁA
                    // �x�d�Ȃ郊���[�h���ŁA���b�N�҂��X���b�h��臒l�𒴂������߁A
                    // �Â����N�G�X�g�ɑ΂��鏈�������s�����A
                    // �Œ背�X�|���X��Ԃ��ďI���B
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
     * LimitedLock�C���X�^���X�𐶐�����B
     * <p>
     * LimitedLock���g�������ꍇ�A���̃��\�b�h���I�[�o�[���C�h���ALimitedLock�g���N���X�̃C���X�^���X��Ԃ��悤�g������B
     * </p>
     * @return LimitedLock�C���X�^���X
     */
    protected LimitedLock createLimitedLock() {
        return new LimitedLock(threshold);
    }

    /**
     * LimitedLock�̃��b�N���擾����B
     * <p>
     * ���b�N�擾�O��ɏ�����ǉ�����g���_�B<br>
     * �K�v�ɉ����āA���̃��\�b�h���g�����邱�ƁB<br>
     * </p>
     * <p>
     * �g����)<br>
     * ���X�|���X��Ԃ��O�ɁA�C�ӂ̏ꏊ�Ń��b�N������������ꍇ���A�t�B���^�ȊO����A���b�N���擾���Ă���LimitedLock�ɃA�N�Z�X�������ꍇ�A������LimitedLock�����N�G�X�g�����ɐݒ肷��悤�g������B
     * </p>
     * @param request HTTP���N�G�X�g
     * @param lock LimitedLock�C���X�^���X
     * @throws InterruptedException ���݂̃X���b�h�Ŋ��荞�݂����������ꍇ(LimitedLock�̋@�\�ɂ��A���b�N�҂������f���ꂽ�ꍇ���܂�)
     */
    protected void lockLimitedLock(HttpServletRequest request, LimitedLock lock)
                                                                                throws InterruptedException {
        lock.lockInterruptibly();
    }

    /**
     * LimitedLock�̃��b�N���������B
     * <p>
     * ���b�N����O��ɏ�����ǉ�����g���_�B<br>
     * �K�v�ɉ����āA���̃��\�b�h���g�����邱�ƁB
     * </p>
     * @param request HTTP���N�G�X�g
     * @param lock LimitedLock�C���X�^���X
     */
    protected void unlockLimitedLock(HttpServletRequest request,
            LimitedLock lock) {
        lock.unlock();
    }

    /**
     * �t�B���^���T�[�r�X�J�n��ԂɂȂ�ۂɁA�R���e�i�ɂ���ČĂяo�����B �R���e�i�́AFilter���C���X�^���X��������ɁAinit���\�b�h�� 1 �񂾂��Ăяo���B<br>
     * Filter�Ƀt�B���^������Ƃ����s����悤�ɗv������ɂ́A init ���\�b�h������� �I�����Ă��Ȃ���΂Ȃ�Ȃ��B init���\�b�h�� ���̂����ꂩ�̏�Ԃ̏ꍇ�A�R���e�i�� Filter���T�[�r�X��Ԃɂł��Ȃ��B<br>
     * <ul>
     * <li>ServletException ���X���[�����B</li>
     * <li>�R���e�i�ɂ���Ē�`���ꂽ���ԓ��ɁA���A���Ȃ��B</li>
     * <li>�ݒ�ُ펞�B</li>
     * </ul>
     * @param config FilterConfig�C���X�^���X�B
     * @throws javax.servlet.ServletException �������ُ펞�ɃX���[������O�B
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
     * �T�[�r�X��Ԃ��I���������t�B���^�ɓ`���邽�߂ɁA�R���e�i���Ăяo���B<br>
     * ���̃N���X�ł͏����͍s�Ȃ�Ȃ��B
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // ���ɂȂ�
    }
}
