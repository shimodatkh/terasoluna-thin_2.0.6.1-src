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

/**
 * TagUtil�N���X�̃e�X�g���s���ۂɎg�p����bean�B
 *
 */
public class TagUtil_BeanStub01 {
    
    /**
     * String�^�̃t�B�[���h�B
     */
    private String param1 = null;

    /**
     * param1��ԋp����B
     * 
     * @return param1
     */
    public String getParam1() {
        return param1;
    }

    /**
     * param1��ݒ肷��B
     * 
     * @param param1 ������
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }
    
    /**
     * Exception���X���[����B
     */
    public String getParam2() throws Exception {
        throw new Exception();
    }
}
