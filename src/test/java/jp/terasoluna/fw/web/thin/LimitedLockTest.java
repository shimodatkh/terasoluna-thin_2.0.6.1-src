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
 * {@link jp.terasoluna.fw.web.thin.LimitedLock} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���b�N�҂��X���b�h���������ۂɁA�Â����b�N�҂��X���b�h�𒆒f����@�\�������b�N�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.LimitedLock
 */
public class LimitedLockTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(LimitedLockTest.class);
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
    public LimitedLockTest(String name) {
        super(name);
    }

    /**
     * testLimitedLock01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) threshold:1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) LimitedLock:not Null<br>
     *         (��ԕω�) threshold:1<br>
     * <br>
     * �R���X�g���N�^�̈�����1�ȏ�ł���ꍇ�A�����ŗ^�������l��threshold�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLimitedLock01() throws Exception {
        // �e�X�g���{
        LimitedLock lock = new LimitedLock(1);
        
        // ����
        int thresholdField = (Integer) UTUtil.getPrivateField(lock, "threshold");
        assertEquals(1, thresholdField);
    }

    /**
     * testLimitedLock02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) threshold:0<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) LimitedLock:not Null<br>
     *         (��ԕω�) threshold:0<br>
     * <br>
     * �R���X�g���N�^�̈�����0�ȉ�(0)�ł���ꍇ�A0��threshold�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLimitedLock02() throws Exception {
        // �e�X�g���{
        LimitedLock lock = new LimitedLock(0);
        
        // ����
        int thresholdField = (Integer) UTUtil.getPrivateField(lock, "threshold");
        assertEquals(0, thresholdField);
    }

    /**
     * testLimitedLock03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(����) threshold:-1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) LimitedLock:not Null<br>
     *         (��ԕω�) threshold:0<br>
     * <br>
     * �R���X�g���N�^�̈�����0�ȉ�(-1)�ł���ꍇ�A0��threshold�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLimitedLock03() throws Exception {
        // �e�X�g���{
        LimitedLock lock = new LimitedLock(-1);
        
        // ����
        int thresholdField = (Integer) UTUtil.getPrivateField(lock, "threshold");
        assertEquals(0, thresholdField);
    }

    /**
     * testLockInterruptibly01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) �����b�N���<br>
     *         (���) ���݂̃X���b�h�Ɋ��荞�݃X�e�[�^�X���ݒ肳��Ă�����<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InterruptedException�F<br>
     * <br>
     * ���b�N�擾�O�ɁA���Ɍ��݂̃X���b�h�Ɋ��荞�݃X�e�[�^�X���ݒ肳��Ă���ꍇ�A
     * ���b�N��ԂɊւ�炸�A���b�N�̎擾�����f����邱�Ƃ��m�F����B<br>
     * 
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockInterruptibly01() throws Exception {
        // �O����
        LimitedLock lock = new LimitedLock(1);
        Thread.currentThread().interrupt();
        
        // �e�X�g���{
        try {
            lock.lockInterruptibly();
            fail();
        } catch (InterruptedException e) {
            // ���Ғʂ�
        }
        
        // ����
        assertNull(UTUtil.invokePrivate(lock, "getOwner"));
    }

    /**
     * testLockInterruptibly02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) �ʂ̃X���b�h�����b�N���Ă�����<br>
     *         (���) ���݂̃X���b�h�����b�N�҂����Ă���ԂɁA�X���b�h�̊��荞�݃X�e�[�^�X���ݒ肳���<br>
     * <br>
     * ���Ғl�F(��ԕω�) ��O:InterruptedException�F<br>
     * <br>
     * ���b�N�҂����Ă���ԂɁA���݂̃X���b�h�Ɋ��荞�݃X�e�[�^�X���ݒ肳�ꂽ�ꍇ�A
     * ���b�N�҂������f����邱�Ƃ��m�F����B<br>
     * 
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockInterruptibly02() throws Exception {
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        ErrorFeedBackThread interruptorThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    // �e�X�g�X���b�h���u�e�X�g���{�v�̂Ƃ���ɐi�ޑO��
                    // ���荞�݃X�e�[�^�X��ݒ肵�Ȃ��悤�A
                    // �����҂B
                    Thread.sleep(100);
                    testThread.interrupt();
                } finally {
                    lock.unlock();
                }
            }
        };
        interruptorThread.start();
        Thread.sleep(50);
        // interruptorThread�����b�N���擾�����܂܁A��50�~���b��Ɍ��݂̃X���b�h�Ɋ��荞�݂�������B
        
        // �e�X�g���{
        try {
            lock.lockInterruptibly();
            fail();
        } catch (InterruptedException e) {
            // ���Ғʂ�
        }
        
        // ����
        assertNotSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        
        // �ʃX���b�h�̃G���[�t�B�[�h�o�b�N
        interruptorThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) �����b�N���<br>
     * <br>
     * ���Ғl�F(��ԕω�) ���݂̃X���b�h�Ń��b�N���ꂽ���<br>
     * <br>
     * �����b�N��Ԃł���ꍇ�A�����Ƀ��b�N���擾�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockInterruptibly03() throws Exception {
        // �O����
        final LimitedLock lock = new LimitedLock(1);
        final Thread testThread = Thread.currentThread();
        ErrorFeedBackThread interruptorThread = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    // ����1�b�ȓ��Ƀ��b�N���擾�ł��Ȃ���΁A���f����B
                    // ���f�����ƁA�u�e�X�g���{�v�̂Ƃ��납��InterruptedException���������A�����̓G���[�ƂȂ�B
                    Thread.sleep(1000);
                    testThread.interrupt();
                } catch (InterruptedException e) {
                    // ����Ƀe�X�g���I�����A�X���b�h�𑬂₩�ɒ�~���邽�߂̌㏈�������s���ꂽ�Ƃ��ɁA
                    // �����ɓ��B����B
                }
            }
        };
        interruptorThread.start();
        
        // �e�X�g���{
        lock.lockInterruptibly();
        
        // ����
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        
        // �㏈��
        interruptorThread.interrupt();
        
        // �ʃX���b�h�̃G���[�t�B�[�h�o�b�N
        interruptorThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FB
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) �ʂ̃X���b�h�����b�N��ێ����Ă���A���̑��A1�̃X���b�h�����b�N�҂����Ă�����<br>
     * <br>
     * ���Ғl�F(��ԕω�) ���݂̃X���b�h�Ń��b�N���ꂽ���<br>
     * <br>
     * �ʂ̃X���b�h�����b�N��ێ����Ă���ꍇ�A<br>
     * threshold�ȓ��̃X���b�h(1)�����b�N�҂����Ă��Ă��A���b�N�҂��̒��f���������Ȃ����Ƃ��m�F����B<br>
     * �����ɁA���݂̃X���b�h�����b�N���擾�ł��邱�Ƃ��m�F����B<br>
     * ���b�N���擾�ł����Ƃ��ɂ́A���̃X���b�h�����b�N���łȂ����Ƃ��m�F����B<br>
     * (�����ɕ����̃X���b�h�����b�N���l�����Ȃ��̂́A�X�[�p�[�N���X�̋@�\�Ȃ̂ŁA���̃e�X�g���\�b�h�݂̂Ŋm�F����B)<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockInterruptibly04() throws Exception {
        // �O����
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
        // lockThread�����b�N���擾���āA���Ɩ�400�~���b���b�N���Ă����ԁB
        // waitThread�̓��b�N��҂��Ă����ԁB
        
        // �e�X�g���{
        lock.lockInterruptibly();
        
        // ����
        assertEquals(0, lockThreadSet.size());
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));

        // �ʃX���b�h�̃G���[�t�B�[�h�o�b�N
        lockThread.throwErrorOrExceptionIfThrown();
        waitThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FBD
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) �ʂ̃X���b�h�����b�N��ێ����Ă���A���̑��A2�̃X���b�h(���݂̃X���b�h���܂܂Ȃ�)�����b�N�҂����Ă�����<br>
     * <br>
     * ���Ғl�F(��ԕω�) ���݂̃X���b�h�Ń��b�N���ꂽ���<br>
     *         (��ԕω�) �ŏ��Ƀ��b�N�҂��ɂȂ����X���b�h1�݂̂��A���b�N�҂��𒆒f�����B
     * <br>
     * �ʂ̃X���b�h�����b�N��ێ����Ă���ꍇ�A<br>
     * threshold�𒴂���X���b�h(2)�����b�N�҂����Ă���ꍇ�A���b�N�҂��̒��f���������邱�Ƃ��m�F����B<br>
     * �Â����b�N�҂��X���b�h(���b�N�҂��X���b�h2�̂����A�ŏ��Ƀ��b�N�҂���ԂɂȂ����X���b�h)�̃��b�N�҂������f����邱�Ƃ��m�F����B
     * �����ɁA���݂̃X���b�h�����b�N���擾�ł��邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockInterruptibly05() throws Exception {
        // �O����
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
                    // ���Ғʂ�
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
        // lockThread�����b�N���擾���āA���Ɩ�350�~���b���b�N���Ă����ԁB
        // waitThread1�̓��b�N��҂��Ă����ԁB
        // waitThread2�̓��b�N��҂��Ă����ԁB
        
        // �e�X�g���{
        lock.lockInterruptibly();
        
        // ����
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));

        // �ʃX���b�h�̃G���[�t�B�[�h�o�b�N
        lockThread.throwErrorOrExceptionIfThrown();
        waitThread1.throwErrorOrExceptionIfThrown();
        waitThread2.throwErrorOrExceptionIfThrown();
    }

    /**
     * testLockInterruptibly06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) �ʂ̃X���b�h�����b�N��ێ����Ă���A���̑��A5�̃X���b�h(�Ō��1�͌��݂̃X���b�h)�����b�N�҂����Ă������<br>
     * <br>
     * ���Ғl�F(��ԕω�) ���݂̃X���b�h�Ń��b�N���ꂽ���<br>
     *         (��ԕω�) 1, 2, 3�ԖڂɃ��b�N�҂��ɂȂ����X���b�h3�݂̂��A���b�N�҂��𒆒f�����B
     * <br>
     * �ʂ̃X���b�h�����b�N��ێ����Ă���ꍇ�A<br>
     * threshold�𒴂���X���b�h�����b�N�҂����Ă���ꍇ�A���b�N�҂��̒��f���������邱�Ƃ��m�F����B<br>
     * �Â����b�N�҂��X���b�h���烍�b�N�҂������f����邱�Ƃ��m�F����B
     * �����ɁA���݂̃X���b�h�����b�N���擾�ł��邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockInterruptibly06() throws Exception {
        // �O����
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
        
        // �e�X�g���{
        ErrorFeedBackThread waitThread1 = new ErrorFeedBackThread() {
            @Override
            public void doRun() throws Exception {
                try {
                    lock.lockInterruptibly();
                    fail();
                } catch (InterruptedException e) {
                    // ���Ғʂ�
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
                    // ���Ғʂ�
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
                    // ���Ғʂ�
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
        // lockThread�����b�N���擾���āA���Ɩ�250�~���b���b�N���Ă����ԁB
        
        lock.lockInterruptibly();
        
        // ����
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        assertEquals(3, interruptedList.size());
        assertEquals("1", interruptedList.get(0));
        assertEquals("2", interruptedList.get(1));
        assertEquals("3", interruptedList.get(2));

        // �ʃX���b�h�̃G���[�t�B�[�h�o�b�N
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
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) ���݂̃X���b�h�����b�N��ێ����Ă���A���̑��A2�̃X���b�h�����b�N�҂����Ă�����<br>
     * <br>
     * ���Ғl�F(��ԕω�) ���݂̃X���b�h�Ń��b�N���ꂽ���<br>
     * <br>
     * ���݂̃X���b�h�����Ƀ��b�N��ێ����Ă���ꍇ(�ē����b�N��)�A<br>
     * threshold�𒴂���X���b�h(2)�����b�N�҂����Ă��Ă��A���b�N�҂��̒��f�͔������Ȃ����Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLockInterruptibly07() throws Exception {
        // �O����
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
        
        // �e�X�g���{
        lock.lockInterruptibly();
        
        // ����
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        
        // �㏈��
        lock.unlock();
        lock.unlock();

        // �ʃX���b�h�̃G���[�t�B�[�h�o�b�N
        waitThread1.throwErrorOrExceptionIfThrown();
        waitThread2.throwErrorOrExceptionIfThrown();
    }

    /**
     * testUnlock01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) ���݂̃X���b�h�����b�N��ێ����Ă�����<br>
     *         (���) ���b�N�J�E���g:1<br>
     * <br>
     * ���Ғl�F(��ԕω�) ���b�N��������ꂽ���<br>
     * <br>
     * ���b�N��ێ����Ă���X���b�h��unlock�����s�����ꍇ�A���b�N��������邱�Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnlock01() throws Exception {
        // �O����
        LimitedLock lock = new LimitedLock(1);
        lock.lockInterruptibly();
        
        // �e�X�g���{
        lock.unlock();
        
        // ����
        assertNull(UTUtil.invokePrivate(lock, "getOwner"));
    }

    /**
     * testUnlock02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) ���݂̃X���b�h�����b�N��ێ����Ă�����<br>
     *         (���) ���b�N�J�E���g:2<br>
     * <br>
     * ���Ғl�F(��ԕω�) ���݂̃X���b�h�����b�N��ێ����Ă�����<br>
     *         (��ԕω�) ���b�N�J�E���g:1<br>
     * <br>
     * ���b�N��2��擾�����X���b�h��unlock��1����s�����ꍇ�A���b�N����������Ƀ��b�N�J�E���g�����f�N�������g����邱�Ƃ��m�F����B<br>
     * (�X�[�p�[�N���X�̍ē��\���b�N�Ƃ��Ă̋@�\)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnlock02() throws Exception {
        // �O����
        LimitedLock lock = new LimitedLock(1);
        Thread testThread = Thread.currentThread();
        lock.lockInterruptibly();
        lock.lockInterruptibly();
        
        // �e�X�g���{
        lock.unlock();
        
        // ����
        assertSame(testThread, UTUtil.invokePrivate(lock, "getOwner"));
        assertEquals(1, lock.getHoldCount());
    }

    /**
     * testUnlock03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) �ʂ̃X���b�h�����b�N��ێ����Ă�����<br>
     * <br>
     * ���Ғl�F(��ԕω�) �ʂ̃X���b�h�����b�N��ێ����Ă�����<br>
     * <br>
     * ���݂̃X���b�h�����b�N��ێ����Ă��Ȃ��ꍇ�A�������Ȃ����Ƃ��m�F����B<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnlock03() throws Exception {
        // �O����
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
        
        // �e�X�g���{
        lock.unlock();
        
        // ����
        assertSame(lockThread, UTUtil.invokePrivate(lock, "getOwner"));

        // �ʃX���b�h�̃G���[�t�B�[�h�o�b�N
        lockThread.throwErrorOrExceptionIfThrown();
    }

    /**
     * testReadObject01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(���) threshold:1<br>
     *         (���) lock:Not Null<br>
     *         (���) waitingThreadList:Not Null<br>
     * <br>
     * ���Ғl�F(��ԕω�) threshold:1<br>
     *         (��ԕω�) lock:Object(���͒l�Ƃ͈قȂ�)<br>
     *         (��ԕω�) waitingThreadList:LinkedList(���͒l�Ƃ͈قȂ�)<br>
     * <br>
     * �V���A���C�Y���A�f�V���A���C�Y�����ꍇ�Athreshold�̒l�͈����p����Alock��waitingThreadList�ɂ͐V�����I�u�W�F�N�g���ݒ肳��邱�Ƃ��m�F����B<br>
     * (�f�V���A���C�Y���邱�Ƃɂ��AreadObject���\�b�h�𓮍삳����B)
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReadObject01() throws Exception {
        // �O����
        LimitedLock lock = new LimitedLock(1);
        Object oldLockField = UTUtil.getPrivateField(lock, "lock");
        LinkedList oldWaitingThreadListField = (LinkedList) UTUtil.getPrivateField(lock, "waitingThreadList");
        
        // �e�X�g���{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(lock);
        oos.close();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object deserialObject = ois.readObject();
        
        // ����
        assertEquals(1, UTUtil.getPrivateField(deserialObject, "threshold"));
        Object newLockField = UTUtil.getPrivateField(deserialObject, "lock");
        assertNotNull(newLockField);
        assertNotSame(oldLockField, newLockField);
        LinkedList newWaitingThreadListField = (LinkedList) UTUtil.getPrivateField(deserialObject, "waitingThreadList");
        assertNotNull(newWaitingThreadListField);
        assertNotSame(oldWaitingThreadListField, newWaitingThreadListField);
    }

    /**
     * �G���[���t�B�[�h�o�b�N�ł���X���b�h�B
     * �ʃX���b�h�Ŏ��{���������e�� doRun() throws Exception �Ɏ�������B
     * �����I�����AthrowErrorOrExceptionIfThrown���\�b�h�����s����ƁA
     * doRun���\�b�h�ɂđz��O�̃G���[�����������ꍇ�ɁA���̃G���[���X���[�����B
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
