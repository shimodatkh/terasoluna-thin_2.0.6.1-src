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

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.MockHttpSession;

/**
 * ClearSessionActionTestクラスで利用する。
 * 
 */
public class ClearSessionAction_HttpSessionStub01 extends MockHttpSession {

    public Map<String, String> attrs = new HashMap<String, String>();

    @Override
    public Object getAttribute(String s) {
        return attrs.get(s);
    }

    @Override
    public void removeAttribute(String key) {
        attrs.remove(key);
    }   
}
