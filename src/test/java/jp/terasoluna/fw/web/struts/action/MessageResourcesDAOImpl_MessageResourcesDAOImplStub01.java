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

public class MessageResourcesDAOImpl_MessageResourcesDAOImplStub01 extends
        MessageResourcesDAOImpl {

    // getBeansXml()�ŕԋp����t�@�C����
    private String testBeansXmlName = null;

    /**
     * testBeansXml��Ԃ��B
     * 
     * @return testBeansXmlName
     */
    public String getTestBeansXmlName() {
        return testBeansXmlName;
    }

    /**
     * testBeansXml��ݒ肷��B
     * 
     * @params testBeansXmlName testBeansXmlName
     */
    public void setTestBeansXmlName(String testBeansXmlName) {
        this.testBeansXmlName = testBeansXmlName;
    }

    /**
     * ���݂��Ȃ��t�@�C������Ԃ��B
     * 
     * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl#getBeansXml()
     */
    @Override
    protected String getBeansXml() {
        return "jp/terasoluna/fw/web/struts/action/"
                + this.testBeansXmlName;
    }

}
