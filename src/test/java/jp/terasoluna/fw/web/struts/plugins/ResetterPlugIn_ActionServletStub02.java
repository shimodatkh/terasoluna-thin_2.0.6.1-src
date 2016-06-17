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

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionServlet;

/**
 * ResetterPlugInTest�N���X�Ŏg�p����B<br>
 * 
 * <br>
 */
public class ResetterPlugIn_ActionServletStub02 extends ActionServlet {
    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -5781766880483415916L;

    private ServletContext ctx = new ResetterPlugIn_MockServletContextStub01();

    /**
     * �T�[�u���b�g�R���e�L�X�g��ԋp����B
     * 
     * @return �[���T�[�u���b�g�R���e�L�X�g
     */
    @Override
    public ServletContext getServletContext() {
        return ctx;
    }

    /**
     * �T�[�u���b�g�R���e�L�X�g��ݒ肷��B
     * 
     * @param ctx ServletContext
     */
    public void setServletContext(ServletContext ctx) {
        this.ctx = ctx;
    }

}
