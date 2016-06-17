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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ActionExTest�N���X�ŗ��p����B
 * 
 */
public class ActionEx_ActionExStub05 extends ActionEx {

    /**
     * isTokenValid�̃��^�[���l
     */
    public boolean tokenValid = false;
    
    /**
     * saveToken�̌Ăяo���m�F
     */
    public boolean isSaveToken = false;
    
    
    @Override
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return null;
    }

    @Override
    protected boolean isTokenValid(HttpServletRequest request) {
        return tokenValid;
    }

    @Override
    protected void saveToken(HttpServletRequest request) {
        this.isSaveToken = true;
    }    
}
