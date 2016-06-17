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

package jp.terasoluna.fw.web.struts.form;

import javax.servlet.ServletContext;

import jp.terasoluna.utlib.MockServletContext;

import org.apache.struts.action.ActionServlet;

/**
 * DynaActionFormExTest�N���X�ŗ��p����B
 *
 * @version 2004/04/08
 */
public class DynaValidatorActionFormEx_ActionServletStub01
    extends ActionServlet {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -3815501252468515570L;

    private ServletContext servletContext = new MockServletContext();

    /**
     * servletContext��ݒ肷��B
     */
    public void setContext(ServletContext context) {
        this.servletContext = context;
    }

    /**
     * servletContext���擾����B
     */
    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

}
