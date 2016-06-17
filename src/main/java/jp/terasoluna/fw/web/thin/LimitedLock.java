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
 * ���b�N�҂��X���b�h���������ۂɁA�Â����b�N�҂��X���b�h�𒆒f����@�\�������b�N�N���X�B
 * <p>
 * Web�ɂ����āA�Z�b�V�����œ���������ꍇ�Asynchronized�ɂ�铯���������ł́A �ȉ��̂悤�ȑ�����s�����[�U�������ꍇ�ɁA �X���b�h�𖳑ʂɐ�L���Ă��܂�(��ʂ̃X���b�h�����b�N�҂���ԂɂȂ��Ă��܂�)��肪����B<br>
 * <ol>
 * <li>�ɒ[�Ƀ��X�|���X�̈������������s����</li>
 * <li>�Ȃ��Ȃ����X�|���X���Ԃ��Ă��Ȃ��̂ŁA�������蒼�����߁A���C�ɓ��蓙����g�b�v�y�[�W�Ɉړ�����<br>
 * (�Z�b�V�����œ��������Ă���̂ŁA1�̏������I���܂ł͕\������Ȃ�)</li>
 * <li>�g�b�v�y�[�W���Ȃ��Ȃ��\������Ȃ����߁A�����[�h���J��Ԃ��B</li>
 * </ol>
 * <br>
 * ���̂悤�ȑ��삪�s��ꂽ�ꍇ�A2�Ŕ����������N�G�X�g���������̃��b�N�҂��X���b�h��A 3�ŌJ��Ԃ��Ă��郊�N�G�X�g�̂����A�Ō�̃��N�G�X�g�ȊO���������̃��b�N�҂��X���b�h�́A
 * �����𑱂��Ă��A���X�|���X���N���C�A���g�̉�ʂɂ͔��f����Ȃ����߁A���b�N�҂����̂𒆒f���������悢�B<br>
 * ���̒��f�@�\�����������̂��A���̃N���X�ł���B
 * </p>
 * <p>
 * ���̃N���X�����̊�{�I�ȃ��[���͈ȉ��̒ʂ�ł���B<br>
 * <ul>
 * <li>���b�N��v�������X���b�h���A���b�N�҂������ɒ��f����邱�Ƃ͂Ȃ��B</li>
 * <li>���Ƀ��b�N���擾���Ă���X���b�h�ɑ΂��A�X���b�h���荞�݂������邱�Ƃ͂Ȃ��B</li>
 * <li>���b�N�҂��𒆒f�����X���b�h�́A���b�N�҂��X���b�h�̒��ł��A�����Ƃ��Â����b�N�҂��X���b�h(���̃X���b�h�����A�������b�N�҂��ƂȂ����X���b�h)�ł���B</li>
 * </ul>
 * (�N���X�O�����犄�荞�݃X�e�[�^�X���ݒ肳�ꂽ�ꍇ�͗�O�B�Ⴆ�΁A���Ɋ��荞�݃X�e�[�^�X��ݒ肳��Ă���X���b�h�����b�N�҂����悤�Ƃ����ꍇ�A�X�[�p�[�N���X�̋@�\�ɂ��A���b�N�҂������ɒ��f�����B)
 * </p>
 * <p>
 * ���̃N���X�����̊�{�I�ȓ���͈ȉ��̒ʂ�ł���B<br>
 * <ul>
 * <li>���̃��b�N��҂X���b�h�̐����������l�𒴂��Ă����Ԃ̂Ƃ��ɁA ����ɑ��̃X���b�h�����̃��b�N��v�������Ƃ��A���b�N���擾�ł���̂�҂O�ɁA �������l�𒴂��Ă������̃X���b�h���A�Â����b�N�҂��X���b�h���璆�f������B</li>
 * </ul>
 * </p>
 * <p>
 * ��) �������l��2�̂Ƃ�<br>
 * �X���b�h1, 2, 3, 4�̏��Ń��b�N��v�������ꍇ�A �X���b�h1�����b�N���擾���A�X���b�h2, 3, 4�����b�N�҂��ƂȂ�B<br>
 * ���̂Ƃ��A���b�N�҂��X���b�h(3)���������l(2)�𒴂��Ă���̂ŁA ����ɃX���b�h5�����b�N��v�������Ƃ��A �����Ƃ��Â����b�N�҂��X���b�h�ł���A�X���b�h2�̃��b�N�҂��𒆒f���A �X���b�h5�����b�N�҂���ԂƂȂ�B<br>
 * </p>
 * <p>
 * ���b�N���擾�����X���b�h�����b�N��Ԃ��܂ł̊ԁA �ʂ̃X���b�h�������A���X�ƃ��b�N��v�������ꍇ(���s���ėv�����Ȃ��ꍇ)�A ���b�N�҂��X���b�h���́A�������l�ƁA�������l�{1�̊Ԃ�J�ڂ���B<br>
 * �܂��A�V���ȃ��b�N�v�����Ȃ����Ԃɂ����ẮA���b�N�҂��X���b�h���́A�������l�{1�ƂȂ�B<br>
 * �������A���s���ėv�����������ꍇ�́A���b�N�҂��X���b�h���̏���͕ۏ؂��Ȃ��B<br>
 * �܂��A�O���œ��������Ă͂Ȃ�Ȃ��B<br>
 * (����������ƁA�����̃X���b�h�����b�N�҂��ɂ��Ă��܂����ۂ��������Ƃ����A���̃N���X�̖ړI���ʂ����Ȃ��B)<br>
 * ����āA���b�N�҂��X���b�h���̏���́A�x�X�g�G�t�H�[�g�ƂȂ�B<br>
 * </p>
 * <p>
 * ���̃N���X�ł́AlockInterruptibly���\�b�h�Ń��b�N���擾���A unlock���\�b�h�Ń��b�N���������B<br>
 * ���̑��̃��b�N���䃁�\�b�h(�X�[�p�[�N���X�ŗp�ӂ���Ă��郁�\�b�h)�͎g�p���Ȃ����ƁB
 * </p>
 * <p>
 * ���b�N�҂������f���ꂽ�X���b�h�́A lockInterruptibly���\�b�h�����s���ɁAInterruptedException���������邱�Ƃɂ��A ���b�N�҂���Ԃ��畜�A����B
 * </p>
 * <p>
 * ���̃N���X�̒��f(�X���b�h���荞��)�@�\�ɂ��AInterruptedException�����������ꍇ�A �X���b�h�̊��荞�݃X�e�[�^�X�̓N���A�����B<br>
 * �������A���̃N���X�̊O������̃X���b�h���荞�݂�����ꍇ�ɂ́A���̃N���X�̏����𔲂����ۂ� �X���b�h�̊��荞�݃X�e�[�^�X�͕s��ł���B<br>
 * �O�����荞�݂̃^�C�~���O�ɂ���ẮA InterruptedException���������A���荞�݃X�e�[�^�X���ݒ肳��Ă����ԂƂȂ邱�Ƃ�����B
 * (InterruptedException������A���荞�݃X�e�[�^�X�N���A�R�[�h�����s���ꂽ��ɁA�O�����荞�݂����������ꍇ���Y���B)
 * </p>
 * <p>
 * �R�[�h�L�q��F<br>
 * <code><pre>
 * LimitedLock lock;
 * �c �������������͈͂ŋ��L����LimitedLock�C���X�^���X���擾
 * try {
 *     lock.lockInterruptibly();
 *     �c ���b�N�擾��̏���
 * } catch (InterruptedException e) {
 *     �c ���b�N�҂����f���̏���
 * } finally {
 *     lock.unlock(); // ���b�N�̎擾�ɐ������������s�������̎��O����͕s�v�B(�����Ŏ�������)
 * }
 * </pre></code>
 * </p>
 * <p>
 * ���̃N���X�́A�X�[�p�[�N���X��Serializable����������Ă��邽�߁A���񉻉\�Ȏ����Ƃ��Ă��邪�A�g��������ł́A���̃N���X�̖ړI���ʂ������Ƃ��o���Ȃ��Ȃ�\�����������߁A�V���A���C�Y/�f�V���A���C�Y�̎g�p�͐������Ȃ��B
 * (�Z�b�V�����Ɋi�[���邱�Ƃ��������Ȃ��B) �Ȃ��A�f�V���A���C�Y���́A�X�[�p�[�N���X�Ɠ��l�A�V���A���C�Y���ꂽ�Ƃ��̏�ԂɊւ�炸�A���b�N���������ꂽ��ԂƂȂ�B
 * </p>
 * @see ReentrantLock
 */
public class LimitedLock extends ReentrantLock {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 894432960610700290L;

    /**
     * ���O�N���X�B
     */
    private static final Log log = LogFactory.getLog(LimitedLock.class);

    /**
     * ���b�N�I�u�W�F�N�g�B
     */
    private transient Object lock = new Object();

    /**
     * �������l
     */
    private int threshold;

    /**
     * ���b�N�҂�������ێ�����List�B<br>
     * ����List�́A�����܂ŏ�������p�ł���AList���ɂ���X���b�h�����ۂɃ��b�N�҂����Ă���Ƃ͌���Ȃ��B<br>
     * (����List��add���Ă��烍�b�N�����݂邽�߁A�������b�N�҂��ɂȂ�X���b�h�ƁA���b�N�҂����邱�ƂȂ����b�N�𓾂���X���b�h���܂܂��B)
     */
    private transient LinkedList<Thread> waitingThreadList = new LinkedList<Thread>();

    /**
     * �R���X�g���N�^�B
     * @param threshold �������l(0�ȉ��̏ꍇ�́A0�Ƃ��Ĉ���)
     */
    public LimitedLock(int threshold) {
        if (threshold > 0) {
            this.threshold = threshold;
        } else {
            this.threshold = 0;
        }
    }

    /**
     * ���b�N���擾����B
     * <p>
     * ���݂̃X���b�h�����b�N���擾�ł��邩�A���̃X���b�h�����݂̃X���b�h�Ɋ��荞�݂��s���܂ŁA���݂̃X���b�h�͑ҋ@����B<br>
     * ���݂̃X���b�h�����b�N���擾�ł����ꍇ�A���\�b�h�𕜋A����B<br>
     * ���̃X���b�h�����݂̃X���b�h�Ɋ��荞�݂��s�����ꍇ�AInterruptedException���X���[����A���݂̃X���b�h�̊��荞�݃X�e�[�^�X���N���A�����B<br>
     * (�������A�N���X�O������̊��荞�݂��������ꍇ�́A���荞�݃X�e�[�^�X�͕s��B)
     * </p>
     * <p>
     * ��L�́A�X�[�p�[�N���X�ɂ����́B�g���|�C���g�͈ȉ��̒ʂ�B<br>
     * <ul>
     * <li>���̃��b�N��҂X���b�h�̐����������l�𒴂��Ă����Ԃł��̃��\�b�h�����s���ꂽ�Ƃ��A���b�N���擾�ł���̂�҂O�ɁA�������l�𒴂��Ă������̃X���b�h���A�Â����b�N�҂��X���b�h���璆�f������B<br>
     * ���Ƀ��b�N���擾���Ă���X���b�h�����̃��\�b�h�����s�����Ƃ�(�ē����b�N��)�́A���b�N�҂��X���b�h�̐��Ɋւ�炸�A�X���b�h�̒��f�͍s��Ȃ��B</li>
     * </ul>
     * </p>
     * @throws InterruptedException ���݂̃X���b�h�Ŋ��荞�݂����������ꍇ(���̃N���X�̋@�\�ɂ��A���b�N�҂������f���ꂽ�ꍇ���܂�)
     * @see java.util.concurrent.locks.ReentrantLock#lockInterruptibly()
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        boolean successToLock = false;
        // ���b�N�҂��X���b�h������萔�𒴂�����ԂŁA
        // �V���ȃX���b�h�����b�N��v�������Ƃ��A
        // ���b�N�҂��X���b�h�Ɋ��荞�݂�������B
        // (���Ƀ��b�N�擾�ς݂̃X���b�h���ċA�I�Ƀ��b�N(�ē����b�N)���|�����ꍇ�́A
        // ���b�N�҂��X���b�h�Ɋ��荞�݂������邱�Ƃ͂��Ȃ��B)
        if (getOwner() != Thread.currentThread()) {
            synchronized (lock) {
                // ���̃u���b�N�������́Asuper.unlock();�����s����Ȃ��̂ŁA���b�N�҂��X���b�h�����b�N���擾���邱�Ƃ͂Ȃ��B
                // �ǂ̃X���b�h�����b�N���擾���Ă��Ȃ������ꍇ�A
                // ���b�N��҂��Ă��Ȃ������X���b�h�����b�N���擾���鎖�͂��邪�A
                // ���̃X���b�h�́A��ԑJ�ڂɂ����āA���b�N�҂���Ԃ��o�R�����Ƀ��b�N�擾��ԂƂȂ邽�߁A
                // ���b�N���擾�ł��Ă��̃��\�b�h�𔲂���X���b�h���A����Ċ��荞�ݑΏۃX���b�h�Ɣ��肷�邱�Ƃ͂Ȃ��B
                // �Ȃ��A�����ꂩ�̃X���b�h�����Ƀ��b�N���擾���Ă����ԂŁA
                // ���̑��ɁA���̃u���b�N�𔲂��Asuper.lockInterruptibly();�����s����O�̃X���b�h�����݂����ꍇ�A
                // ���̃X���b�h�́A���̃u���b�N���̏����ɂ����āA���荞�ݑΏۂɂȂ�ꍇ�ƂȂ�Ȃ��ꍇ������
                // (���b�N�҂��X���b�h������萔�𒴂��Ă��Ă��A�܂��҂��X���b�h�ɂȂ��Ă��Ȃ���΁A���荞�ݑΏۂɂ͂Ȃ�Ȃ�)���A
                // ���̃X���b�h�����b�N�҂���Ԃ̂܂܁A����Ƀ��b�N�v���X���b�h�����������l�𒴂����ۂɁA
                // ���荞�ݑΏۂƂ���@��𓾂�̂ŁA�x��͂Ȃ��B
                int queueLength = getQueueLength();
                if (queueLength > threshold) {
                    HashSet<Thread> oldWaitingThreadSet = null;
                    synchronized (waitingThreadList) {
                        List<Thread> oldWaitingThreadList = waitingThreadList
                                .subList(0, queueLength - threshold);
                        oldWaitingThreadSet = new HashSet<Thread>(
                                oldWaitingThreadList);
                    }
                    // waitingThreadList���瓾����X���b�h�̒��ɂ́A
                    // ���ۂɂ̓��b�N�҂����Ă��Ȃ��X���b�h���܂܂��̂ŁA
                    // ���ۂɃ��b�N�҂����Ă���X���b�h���X�g�́A
                    // oldWaitingThreadList��oldWaitingThreadSet����ł͂Ȃ��AgetQueuedThreads()����擾����B
                    for (Thread queuedThread : getQueuedThreads()) {
                        if (oldWaitingThreadSet.contains(queuedThread)) {
                            if (log.isDebugEnabled()) {
                                log.debug("interrupt thread '" + queuedThread
                                        + "'.");
                            }
                            synchronized (waitingThreadList) {
                                // ��waitingThreadList.remove���A���荞�܂ꂽ�X���b�h��finally�ȊO�ɁA�����ł��s���Ă��邱�Ƃɂ��ā�
                                // ���荞�܂ꂽ�X���b�h������remove����O�ɁA���̃X���b�h�����荞�ݔ��f���s���\�������邽�߁A
                                // ���̔��f���Ɋ��荞�ݍς݃X���b�h�̏�񂪓����Ȃ��悤�A
                                // �����Œ�����remove����B
                                waitingThreadList.remove(queuedThread);
                                queuedThread.interrupt();

                                // ���荞�݂������Ă��A
                                // �X���b�h�����b�N�҂��L���[����o��܂łɃ^�C�����O������A
                                // ���̊Ԃɕʂ̃X���b�h���A���荞�ݔ���ŏ���getQueueLength()�����s����ƁA
                                // getQueueLength()���傫���܂܁AwaitingThreadList�������������Ȃ��āA
                                // waitingThreadList.subList�����s(List���傫��subList����낤�Ƃ��Ď��s)���Ă��܂��̂ŁA
                                // ����(synchronized (lock)��)�ŁA�X���b�h�����b�N�҂��L���[����o��܂ő҂B
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
            // ����O�����荞�݂�����A
            // ���̂Ƃ��ɂ��̃N���X���ł̊��荞��(���肨��ю��s)���������s����Ă����ꍇ
            // ���̃N���X���ł̊��荞��(����/���s)�������I������܂ő҂��Ă���
            // �㏈�������s����K�v������̂ŁA
            // lock�t�B�[���h�����b�N�B
            synchronized (lock) {
                synchronized (waitingThreadList) {
                    waitingThreadList.remove(Thread.currentThread()); // �O�����荞�ݎ��ɂ�remove����Ă��Ȃ��̂ŁA������remove
                    if (!successToLock) {
                        // �O�����荞�݌�ɂ��̃N���X���犄�荞�݂��������ꍇ�A
                        // ���荞�݃X�e�[�^�X���c��̂ŁA
                        // �����Ŋ��荞�݃X�e�[�^�X���N���A����B
                        // ���b�N�擾�ɐ������āAreturn�����܂ł̊ԂɊO�����荞�݂��������ꍇ�A
                        // ���荞�݃X�e�[�^�X�̓N���A������return����B
                        Thread.interrupted();
                    }
                }
            }
        }
    }

    /**
     * ���b�N���������B
     * <p>
     * ���݂̃X���b�h�����̃��b�N�̃z���_�ł���ꍇ�A�X�[�p�[�N���X�̃��\�b�h�ɂ��A���b�N���������B<br>
     * </p>
     * <p>
     * ���̃N���X�ł̊g���|�C���g�͈ȉ��̒ʂ�B<br>
     * �E���̃��b�N�̃z���_�łȂ��X���b�h�����̃��\�b�h�����s���Ă��A��O���X���[���Ȃ��B(���������ɕ��A����)<br>
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

            // ���b�N�҂��X���b�h�̂�������܂����b�N���擾���Ă��Ȃ���(��u�̊�)�́A
            // �ʃX���b�h�ŁA�X���b�h���f�̔���/���s���s��synchronized�u���b�N�����삵�Ȃ��悤�A
            // synchronized�u���b�N���ɂƂǂ܂�B
            while (getQueueLength() > 0 && getOwner() == null) {
                Thread.yield();
            }
        }
    }

    /**
     * �f�V���A���C�Y����(�g��)�B
     * <p>
     * �f�V���A���C�Y��������A�R���X�g���N�^�Ő��������Ƃ��Ɠ��l�ɓ��삷��悤�A�V���A���C�Y/�f�V���A���C�Y�s�\�ȃt�B�[���h���č\�z����B<br>
     * �������A����͂����܂ŃX�[�p�[�N���X�Ŏ������ꂽSerializable�𖞂������߂̎����ł���A�V���A���C�Y/�f�V���A���C�Y�̎g�p�͐������Ȃ��B
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
