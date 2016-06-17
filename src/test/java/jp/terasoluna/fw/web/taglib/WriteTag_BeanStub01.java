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

package jp.terasoluna.fw.web.taglib;

import java.io.Serializable;

/**
 * <p>WriteTag�̃e�X�g�ɂ����Ďg�p����Bean�N���X�B</p>
 *
 */
@SuppressWarnings("serial")
public class WriteTag_BeanStub01 implements Serializable {

    private String value = null;

    public WriteTag_BeanStub01() {
    }

    public void setValue(String data) {
        this.value = data;
    }

    public String getValue() {
        return value;
    }

}