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

package jp.terasoluna.fw.web.struts.actions;

import javax.servlet.http.HttpSession;

import jp.terasoluna.utlib.MockHttpServletRequest;

/**
 * {@link LogoffActionTest}�ɂĎg�p����X�^�u�N���X�B
 * 
 */
public class LogoffAction_HttpServletRequestStub01 extends
        MockHttpServletRequest {

    public LogoffAction_HttpSessionStub01 session =
        new LogoffAction_HttpSessionStub01();
    
    @Override
    public HttpSession getSession(boolean create) {
        return session;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

}
