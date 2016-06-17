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

package jp.terasoluna.fw.web.struts.action;

import java.util.HashMap;
import java.util.Map;

public class DBMessageResources_MessageResourcesDAOStub01 implements
        MessageResourcesDAO {
    public Map<String, String> queryMessageMap(String sql) {
        Map<String, String> map = new HashMap<String, String>(3);
        map.put("TEST01", "�e�X�g�߂����[���O�P");
        map.put("TEST02", "testMESSAGE02");
        map.put("TEST03", "�����I����O");
        return map;
    }

}
