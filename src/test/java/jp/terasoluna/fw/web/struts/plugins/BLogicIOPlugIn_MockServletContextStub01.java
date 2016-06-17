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

package jp.terasoluna.fw.web.struts.plugins;

import java.net.MalformedURLException;
import java.net.URL;

import jp.terasoluna.utlib.MockServletContext;

/**
 * BLogicIOPlugInTest�N���X�ŗ��p����B
 * 
 * @version 2004/04/28
 */
public class BLogicIOPlugIn_MockServletContextStub01
        extends MockServletContext {

    /**
     * getResource�ŌĂяo���ꂽ���\�[�X�B
     */
    private String calledResources = null;  
    
    /**
     * �Ăяo���ꂽ���\�[�X��URL��ԋp����B
     * 
     * @return getResource�ŌĂяo���ꂽURL
     */
    public String getCalledResources() {
        return calledResources;
    }

    /**
     * ���\�[�X�ɑ΂��āA�Ă΂ꂽURL��ێ�����B
     */
    @Override
    public URL getResource(String s) throws MalformedURLException {
        this.calledResources = s;
        URL retUrl = super.getResource(s);
        return retUrl;
    }
}
