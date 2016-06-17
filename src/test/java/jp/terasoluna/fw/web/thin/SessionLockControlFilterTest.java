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
 * {@link jp.terasoluna.fw.web.thin.SessionLockControlFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ����Z�b�V�����̏����̓��������s���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.SessionLockControlFilter
 */
public class SessionLockControlFilterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SessionLockControlFilterTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LogUTUtil.flush();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public SessionLockControlFilterTest(String name) {
        super(name);
    }


    /**
     * testInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:interruptResponseCode:500<br>
     *         (���) config:threshold:1<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) interruptResponseCode:500<br>
     *         (��ԕω�) threshold:1<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    ���b�Z�[�W�F"threshold = 1. LimitedLock is enabled."<br>
     * <br>
     * �������p�����[�^���������ݒ肳��Ă���ꍇ�A�t�B�[���hinterruptResponseCode, threshold�ɔ��f����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �O����
        // �R���t�B�O
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("interruptResponseCode", "500");
        filterConfig.setInitParameter("threshold", "1");
        // �t�B���^�̗p��
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // �e�X�g���{
        filter.init(filterConfig);

        // ����
        assertEquals(500, UTUtil.getPrivateField(filter, "interruptResponseCode"));
        assertEquals(1, UTUtil.getPrivateField(filter, "threshold"));
        assertTrue(LogUTUtil.checkDebug("threshold = 1. LimitedLock is enabled."));
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:interruptResponseCode:null(�ݒ�Ȃ�)<br>
     *         (���) config:threshold:-1<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) interruptResponseCode:500<br>
     *         (��ԕω�) threshold:-1<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    ���b�Z�[�W�F"threshold = -1. LimitedLock is disabled. Reason: threshold is negative number."<br>
     * <br>
     * LimitedLock�𖳌�������ݒ�p�^�[��������ɔ��f����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �O����
        // �R���t�B�O
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("threshold", "-1");
        // �t�B���^�̗p��
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // �e�X�g���{
        filter.init(filterConfig);

        // ����
        assertEquals(-1, UTUtil.getPrivateField(filter, "threshold"));
        assertTrue(LogUTUtil.checkDebug("threshold = -1. LimitedLock is disabled. Reason: threshold is negative number."));
    }

    /**
     * testInit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:interruptResponseCode:null(�ݒ�Ȃ�)<br>
     *         (���) config:threshold:null(�ݒ�Ȃ�)<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) interruptResponseCode:503<br>
     *         (��ԕω�) threshold:2<br>
     *         
     * <br>
     * �������p�����[�^���ݒ肳��Ă��Ȃ��ꍇ�A�t�B�[���hinterruptResponseCode, threshold�̓f�t�H���g�l(503, 2)�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit03() throws Exception {
        // �O����
        // �R���t�B�O
        MockFilterConfig filterConfig = new MockFilterConfig();
        // �t�B���^�̗p��
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // �e�X�g���{
        filter.init(filterConfig);

        // ����
        assertEquals(503, UTUtil.getPrivateField(filter, "interruptResponseCode"));
        assertEquals(2, UTUtil.getPrivateField(filter, "threshold"));
    }

    /**
     * testInit04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:interruptResponseCode:AAA<br>
     *         (���) config:threshold:null(�ݒ�Ȃ�)<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NumberFormatException<br>
     *         
     * <br>
     * �������p�����[�^interruptResponseCode�ɐ����ȊO���ݒ肳��Ă����ꍇ�ANumberFormatException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit04() throws Exception {
        // �O����
        // �R���t�B�O
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("interruptResponseCode", "AAA");
        // �t�B���^�̗p��
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // �e�X�g���{
        try {
            filter.init(filterConfig);
            fail();
        } catch (NumberFormatException e) {
            // ���Ғʂ�
        }
    }

    /**
     * testInit05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) config:interruptResponseCode:null(�ݒ�Ȃ�)<br>
     *         (���) config:threshold:AAA<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:NumberFormatException<br>
     *         
     * <br>
     * �������p�����[�^threshold�ɐ����ȊO���ݒ肳��Ă����ꍇ�ANumberFormatException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit05() throws Exception {
        // �O����
        // �R���t�B�O
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.setInitParameter("threshold", "AAA");
        // �t�B���^�̗p��
        SessionLockControlFilter filter
            = new SessionLockControlFilter();
        
        // �e�X�g���{
        try {
            filter.init(filterConfig);
            fail();
        } catch (NumberFormatException e) {
            // ���Ғʂ�
        }
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (���) req:session:null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    ���b�Z�[�W�F"not lock."<br>
     *         
     * <br>
     * �Z�b�V��������������Ă��Ȃ��ꍇ�A�Z�b�V�����ɂ�铯�������s��Ȃ����Ƃ��m�F����B<br>
     * �f�o�b�O���O�ɂāA���������s��Ȃ����[�g�ɓ��������Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.setSession(null);
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        SessionLockControlFilter filter = new SessionLockControlFilter();

        // �e�X�g���{
        filter.doFilter(req, res, chain);
        
        // ����
        assertTrue(LogUTUtil.checkDebug("not lock."));
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) threshold:-1<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �Z�b�V����ID��intern��synchronized����AFilterChain#doFilter�����s�����<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    ���b�Z�[�W�F"use synchronized lock."<br>
     *         
     * <br>
     * threshold��0��菬�����ꍇ�Asynchronized�u���b�N���g�p���ē��������s�����Ƃ��m�F����B<br>
     * �Z�b�V����ID��intern�œ���������Ă��邱�Ƃ��m�F����B<br>
     * �f�o�b�O���O�ɂāAsynchronized�u���b�N���g�p���ē��������s�����[�g�ɓ��������Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
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
                // ����
                // ����synchronized�u���b�N�Ń��b�N����Ă��Ȃ���΁A��O����������B
                "AAAA".intern().notify();
            }
        };

        // �e�X�g���{
        filter.doFilter(req, res, chain);

        // ����
        assertTrue(LogUTUtil.checkDebug("use synchronized lock."));
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) threshold:0<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �Z�b�V����ID�p�ɗp�ӂ��ꂽLimitedLock�œ���������AFilterChain#doFilter�����s�����<br>
     *         (��ԕω�) doFilter�I�����́ALimitedLock�̃��b�N����������<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    ���b�Z�[�W�F"use LimitedLock."<br>
     *         
     * <br>
     * threshold��0�ȏ�(0)�̏ꍇ�ALimitedLock���g�p���ē��������s�����Ƃ��m�F����B<br>
     * �Z�b�V����ID�p��LimitedLock�œ���������Ă��邱�Ƃ��m�F����B<br>
     * �f�o�b�O���O�ɂāALimitedLock���g�p���ē��������s�����[�g�ɓ��������Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
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
                // ����(FilterChain#doFilter���s��)
                LimitedLock limitedLock = limitedLockMapField.get("AAAA").get();
                assertNotNull(limitedLock);
                try {
                    assertSame(Thread.currentThread(), UTUtil.invokePrivate(limitedLock, "getOwner"));
                } catch (Exception e) {
                    // UTUtil.invokePrivate�����s�����ꍇ�B
                    // �����ɂ͓��B���Ȃ��B
                    fail();
                }
                
                // ���{��̔���O�ɁA������GC�ɂ����LimitedLock��ێ������Q�Ƃ��؂�Ă��܂�Ȃ��悤�A
                // LimitedLock�̎Q�Ƃ��ێ�������ԂŁA��x����GC�����s���Ă����B
                System.gc();
            }
        };

        // �e�X�g���{
        filter.doFilter(req, res, chain);
        
        // ����
        LimitedLock limitedLock = limitedLockMapField.get("AAAA").get();
        assertNull(UTUtil.invokePrivate(limitedLock, "getOwner"));
        assertTrue(LogUTUtil.checkDebug("use LimitedLock."));
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) threshold:2(�f�t�H���g)<br>
     *         (���) �Z�b�V����ID AAAA, BBBB �Ŏ��s��A�\����GC�̌�ŁA�Z�b�V����ID CCCC�Ŏ��s����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) limitedLockMap:�Z�b�V����ID AAAA, BBBB �̕��̃G���g���[���폜�����<br>
     *         
     * <br>
     * ��Q�Ƃŕێ����Ă���LimitedLock��GC�ɉ������邱�Ƃɂ��A�s�v�ɂȂ���limitedLockMap�̃G���g���[���폜����邱�Ƃ��m�F����B<br>
     * (��Q�Ƃ�ReferenceQueue�ɓ���ɂ́A�����GC��K�v�Ƃ���B
     * �����ł́u�\����GC�v�Ƃ́A��Q�Ƃ�ReferenceQueue�ɓ���܂�GC�𔭐������邱�Ƃ��Ӗ�����B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
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
        // ��Q�Ƃ�ReferenceQueue�ɓ���̂ɏ\����GC
        queue.prePollWithGC(2);
        
        // �e�X�g���{
        filter.doFilter(reqC, res, chain);
        
        // ����
        assertFalse(limitedLockMapField.containsKey("AAAA"));
        assertFalse(limitedLockMapField.containsKey("BBBB"));
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) threshold:2(�f�t�H���g)<br>
     *         (���) �Z�b�V����ID AAAA�Ŏ��s��GC(�Z�b�V����ID AAAA�p��LimitedLock1�ڂ�GC�ɉ�������)�A
     *                ���̌�ĂсA�Z�b�V����ID AAAA�Ŏ��s��(�Z�b�V����ID AAAA�p��LimitedLock2�ڂ����������)�ɁA
     *                �Z�b�V����ID AAAA�p��LimitedLock���Q�Ƃ�����ԂŁA
     *                �\����GC�̌�(LimitedLock1�ڂ̎�Q�Ƃ݂̂�ReferenceQueue�ɓ���)�A�Z�b�V����ID BBBB�Ŏ��s����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) limitedLockMap:�Z�b�V����ID AAAA �̕��̃G���g���[�͍폜����Ȃ�<br>
     *         (��ԕω�) ���O:�y�f�o�b�O���O�z<br>
     *                    ���b�Z�[�W�F"LimitedLock is deallocated. sessionId = AAAA, SessionLockReference = " + 1�ڂ�LimitedLock���i�[����SessionLockReference��toString�̌���<br>
     *         
     * <br>
     * ��Q�Ƃŕێ����Ă���LimitedLock��GC�ɉ������邱�Ƃɂ��A�s�v�ɂȂ���limitedLockMap�̃G���g���[���폜����邪�A
     * limitedLockMap�ɑ��݂���G���g���[���AReferenceQueue���瓾��ꂽ���̂ƈقȂ�(�V���ȎQ�Ƃŏ㏑������Ă���)�ꍇ�A�폜���Ȃ����Ƃ��m�F����B<br>
     * (��Q�Ƃ�ReferenceQueue�ɓ���ɂ́A�����GC��K�v�Ƃ���B
     * �����ł́u�\����GC�v�Ƃ́A��Q�Ƃ�ReferenceQueue�ɓ���܂�GC�𔭐������邱�Ƃ��Ӗ�����B)<br>
     * �f�o�b�O���O�ɂāA�G���g���[�̍폜���W�b�N�ɓ��������Ƃ��m�F����B(�폜���W�b�N�ɓ���A���A���W�b�N���̔���ɂ���č폜���Ȃ����Ƃ��m�F����)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter05() throws Exception {
        // �O����
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
                    // 2��ڎ��s���̂݁ALimitedLock�̎Q�Ƃ��ێ�����
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
        // ��Q�Ƃ�ReferenceQueue�ɓ���̂ɏ\����GC
        queue.prePollWithGC(1);
        
        // �e�X�g���{
        filter.doFilter(reqB, res, chain);
        
        // ����
        assertTrue(limitedLockMapField.containsKey("AAAA"));
        assertTrue(LogUTUtil.checkDebug("LimitedLock is deallocated. sessionId = AAAA, SessionLockReference = " + sessionLockReferenceStringForDeallocatedLog));
    }

    /**
     * testDoFilter06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) threshold:2(�f�t�H���g)<br>
     *         (���) �Z�b�V����ID AAAA�p��LimitedLock���Q�Ƃ���Ă���<br>
     *         (���) ����GC�����݂A5����s����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �����LimitedLock�C���X�^���X�����b�N����<br>
     *         
     * <br>
     * �����ꂩ�̃X���b�h��LimitedLock���Q�Ƃ��Ă���Ԃ́ALimitedLock�͉�����ꂸ�ɁA����Z�b�V����ID���ŋ��L����邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter06() throws Exception {
        // �O����
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
        
        // �e�X�g���{
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        
        // ����
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (���) �Z�b�V����ID:AAAA
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) threshold:2(�f�t�H���g)<br>
     *         (���) LimitedLock���Q�Ƃ���Ă��Ȃ�<br>
     *         (���) GC�����݂A3����s����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ���s���邽�т�(�v3��)�ALimitedLock�C���X�^���X�����������<br>
     *         
     * <br>
     * ������̃X���b�h��LimitedLock���Q�Ƃ��Ă��Ȃ��Ԃ�GC�����������ꍇ�ALimitedLock���������A�V����LimitedLock���g�p����邱�Ƃ��m�F����B<br>
     * (���ۂɃ��b�N����LimitedLock�C���X�^���X�����؂��悤�Ƃ���ƁA�uLimitedLock���Q�Ƃ���Ă��Ȃ��v�̏������������Ȃ����߁A
     *  LimitedLock�C���X�^���X�̐����񐔂ɂĊm�F����B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter07() throws Exception {
        // �O����
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
        
        // �e�X�g���{
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        System.gc();
        filter.doFilter(req, res, chain);
        
        // ����
        assertEquals(3, createCount.get());
    }

    /**
     * testDoFilter08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) interruptResponseCode:500<br>
     *         (���) threshold:2(�f�t�H���g)<br>
     *         (���) LimitedLock�̃��b�N�擾���\�b�h����InterruptedException���X���[�����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) FilterChain#doFilter�͎��s����Ȃ�<br>
     *         (��ԕω�) res:���X�|���X�R�[�h:500<br>
     *         (��ԕω�) LimitedLock�̃��b�N������\�b�h�����s�����<br>
     *         
     * <br>
     * LimitedLock�̃��b�N�擾�����f���ꂽ�Ƃ��AFilterChain#doFilter�͎��s���ꂸ�AinterruptResponseCode�ɐݒ肳�ꂽ���X�|���X�R�[�h���g�p����邱�Ƃ��m�F����B<br>
     * LimitedLock�̃��b�N�擾�����f����Ă��A���b�N������\�b�h�͎��s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter08() throws Exception {
        // �O����
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

        // �e�X�g���{
        filter.doFilter(req, res, chain);
        
        // ����
        assertNull(chain.getRequest());
        assertEquals(500, res.getStatus());
        assertTrue(unlockCalled.get());
    }

    /**
     * testDoFilter09()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) threshold:2(�f�t�H���g)<br>
     *         (���) FilterChain#doFilter����RuntimeException���X���[�����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) LimitedLock�̃��b�N������\�b�h�����s�����<br>
     *         (��ԕω�) ��O:RuntimeException�F<br>
     *         
     * <br>
     * FilterChain#doFilter�����O���X���[���ꂽ�ꍇ�A��O�����̂܂܃X���[����邱�Ƃ��m�F����B<br>
     * ���b�N������\�b�h�͎��s����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter09() throws Exception {
        // �O����
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

        // �e�X�g���{
        RuntimeException actualThrown = null;
        try {
            filter.doFilter(req, res, chain);
            fail();
        } catch (RuntimeException e) {
            actualThrown = e;
        }
        
        // ����
        assertSame(ex, actualThrown);
        assertTrue(unlockCalled.get());
    }

    /**
     * testCreateLimitedLock01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) LimitedLock�C���X�^���X[threshold=1]<br>
     *
     * <br>
     * �t�B�[���hthreshold�̒l���g�p����ALimitedLock�C���X�^���X����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLimitedLock01() throws Exception {
        // �O����
        SessionLockControlFilter filter = new SessionLockControlFilter();
        UTUtil.setPrivateField(filter, "threshold", 1);
        
        // �e�X�g���{
        LimitedLock limitedLock = filter.createLimitedLock();
        
        // ����
        assertNotNull(limitedLock);
        assertEquals(1, UTUtil.getPrivateField(limitedLock, "threshold"));
    }

    /**
     * testLockLimitedLock01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) lock:LimitedLock(���b�N������)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ���݂̃X���b�h��LimitedLock�C���X�^���X�̃��b�N���擾����<br>
     *
     * <br>
     * ���݂̃X���b�h��LimitedLock�C���X�^���X�̃��b�N���擾���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockLimitedLock01() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        SessionLockControlFilter filter = new SessionLockControlFilter();
        LimitedLock lock = new LimitedLock(1);
        
        // �e�X�g���{
        filter.lockLimitedLock(req, lock);
        
        // ����
        assertEquals(Thread.currentThread(), UTUtil.invokePrivate(lock, "getOwner"));
    }

    /**
     * testUnlockLimitedLock01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) lock:LimitedLock(���݂̃X���b�h��1�񃍃b�N���ꂽ���)<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) LimitedLock�C���X�^���X�̃��b�N����������<br>
     *
     * <br>
     * LimitedLock�C���X�^���X�̃��b�N���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnlockLimitedLock01() throws Exception {
        // �O����
        MockHttpServletRequest req = new MockHttpServletRequest();
        SessionLockControlFilter filter = new SessionLockControlFilter();
        LimitedLock lock = new LimitedLock(1);
        lock.lockInterruptibly();
        
        // �e�X�g���{
        filter.unlockLimitedLock(req, lock);
        
        // ����
        assertNull(UTUtil.invokePrivate(lock, "getOwner"));
    }

}
