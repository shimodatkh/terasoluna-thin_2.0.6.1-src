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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;

/**
 * SessionLockControlFilterTestクラスで利用する。
 */
public class SessionLockControlFilter_ReferenceQueueStub01 extends
                                                          ReferenceQueue<LimitedLock> {
    private LinkedList<Reference<? extends LimitedLock>> pollList = new LinkedList<Reference<? extends LimitedLock>>();

    @Override
    public Reference<? extends LimitedLock> poll() {
        Reference<? extends LimitedLock> ret = null;
        if (pollList.size() > 0) {
            ret = pollList.removeFirst();
        } else {
            ret = super.poll();
        }
        return ret;
    }

    /**
     * テスト中にpollメソッドで確実にReferenceを返せるよう、事前にpollしておく。
     * @param count テスト中にpollメソッドで返すReferenceオブジェクト数。
     */
    public void prePollWithGC(int count) {
        for (int i = 0; pollList.size() < count && i < 100; i++) {
            System.gc();
            Reference<? extends LimitedLock> next = super.poll();
            if (next != null) {
                pollList.add(next);
            }
        }
    }

}
