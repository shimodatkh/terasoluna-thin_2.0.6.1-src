/*
 * Copyright (c) 2007 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts.reset;

import java.util.Map.Entry;

/**
 * ResetterImplTest�Ŏg�p����Entry�̃X�^�u�����B
 *
 */
public class ResetterImpl_EntryStub01 implements Entry {

    /**
     * getKey�̖߂�l�B
     */
    Object key = null;

    /**
     * getValue�̖߂�l�B
     */
    Object value = null;

    /**
     * key��ԋp����B
     * @return key
     */
    public Object getKey() {
        return this.key;
    }

    /**
     * value��ԋp����B
     * @return value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * �������Ȃ��B
     * @param value �l
     * @return null
     */
    public Object setValue(Object value) {
        return null;
    }

}
