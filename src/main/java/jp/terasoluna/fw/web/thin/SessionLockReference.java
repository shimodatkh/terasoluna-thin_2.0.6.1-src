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
 * WeakReferenceの拡張クラス。
 * <p>
 * このクラスは、SessionLockControlFilterで利用されることを前提としている。<br>
 * 弱参照が参照するオブジェクト(LimitedLock)以外に、LimitedLock(を弱参照しているこのオブジェクト)をMapに格納する際のキーであるセッションIDが保持できるよう拡張している。<br>
 * </p>
 */
public class SessionLockReference extends WeakReference<LimitedLock> {

    /**
     * セッションID
     */
    private String sessionId;

    /**
     * コンストラクタ。
     * @param sessionId セッションID
     * @param referent LimitedLockオブジェクト
     * @param q ReferenceQueue
     */
    public SessionLockReference(String sessionId, LimitedLock referent,
            ReferenceQueue<? super LimitedLock> q) {
        super(referent, q);
        this.sessionId = sessionId;
    }

    /**
     * セッションIDを取得する。
     * @return セッションID
     */
    public String getSessionId() {
        return sessionId;
    }
}
