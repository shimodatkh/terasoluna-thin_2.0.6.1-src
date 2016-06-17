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

package jp.terasoluna.fw.service.thin;

import java.io.IOException;
import java.io.InputStream;

import jp.terasoluna.fw.web.struts.actions.AbstractDownloadObject;

public class AbstractBLogicMapper_AbstractDownloadObjectStub01 extends
        AbstractDownloadObject {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -978110417088596514L;

    public AbstractBLogicMapper_AbstractDownloadObjectStub01() {
        this(null, null, null);
        
    }
    
    public AbstractBLogicMapper_AbstractDownloadObjectStub01(
            String name, String contentType, String charset) {
        super(name, contentType, charset);
        
    }

    @Override
    public int getLengthOfData() {
        
        return 0;
    }

    @Override
    protected InputStream getStreamInternal() throws IOException {
        
        return null;
    }

}
