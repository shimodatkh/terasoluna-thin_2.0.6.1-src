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
import java.lang.ref.WeakReference;

/**
 * WeakReference�̊g���N���X�B
 * <p>
 * ���̃N���X�́ASessionLockControlFilter�ŗ��p����邱�Ƃ�O��Ƃ��Ă���B<br>
 * ��Q�Ƃ��Q�Ƃ���I�u�W�F�N�g(LimitedLock)�ȊO�ɁALimitedLock(����Q�Ƃ��Ă��邱�̃I�u�W�F�N�g)��Map�Ɋi�[����ۂ̃L�[�ł���Z�b�V����ID���ێ��ł���悤�g�����Ă���B<br>
 * </p>
 */
public class SessionLockReference extends WeakReference<LimitedLock> {

    /**
     * �Z�b�V����ID
     */
    private String sessionId;

    /**
     * �R���X�g���N�^�B
     * @param sessionId �Z�b�V����ID
     * @param referent LimitedLock�I�u�W�F�N�g
     * @param q ReferenceQueue
     */
    public SessionLockReference(String sessionId, LimitedLock referent,
            ReferenceQueue<? super LimitedLock> q) {
        super(referent, q);
        this.sessionId = sessionId;
    }

    /**
     * �Z�b�V����ID���擾����B
     * @return �Z�b�V����ID
     */
    public String getSessionId() {
        return sessionId;
    }
}
