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

package jp.terasoluna.fw.web.thin;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AbstractControlFilterImpl01 extends AbstractControlFilter {

    /**
     * �R���g���[���̃N���X��Ԃ��B
     */
    @Override
    protected Class getControllerClass() {
        return AbstractControlFilter_ControllerInterface.class;
    }

    /**
     * �G���[�R�[�h��Ԃ��B
     */
    @Override
    protected String getErrorCode() {
        return "errors.test.controller";
    }

    /**
     * �f�t�H���gbean id��Ԃ��B
     */
    @Override
    public String getDefaultControllerBeanId() {
        return "testControllerStub01";
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        // ���ɉ������Ȃ�
        return;

    }

}
