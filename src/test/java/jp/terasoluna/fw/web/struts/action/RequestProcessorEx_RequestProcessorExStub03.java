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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * RequestProcessorExTest�N���X�ŗ��p����B
 * RequestProcessor#processActionForm()����ServletException��
 * �X���[������B
 * ���̑��̃��\�b�h�ɂ́A��O�𔭐��������ɁAprocessActionForm()�܂�
 * �������͂��悤�A�����s�Ȃ�Ȃ����\�b�h�ŃI�[�o���C�h���Ă���B
 * 
 * @version 2004/03/18
 */
public class RequestProcessorEx_RequestProcessorExStub03 extends RequestProcessorEx {


    /**
     * ServletException���X���[����B
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm form, ActionMapping mapping)
                throws IOException,
                        ServletException {
        throw new ServletException();
    }

    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */
    @Override
    protected void processLocale(HttpServletRequest request,
                                 HttpServletResponse response) {
    }

    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */   
    @Override
    protected void processContent(HttpServletRequest request,
                                  HttpServletResponse response) {
    }

    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */
    @Override
    protected void processNoCache(HttpServletRequest request,
                                  HttpServletResponse response) {
    }
 
 
    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */
    @Override
    protected ActionMapping processMapping(HttpServletRequest request,
                                           HttpServletResponse response,
                                           String path)
        throws IOException {
            // ActionMappingEx ��ԋp
            return new ActionMappingEx();
    }

    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */
    @Override
    protected boolean processRoles(HttpServletRequest request,
                                   HttpServletResponse response,
                                   ActionMapping mapping)
        throws IOException, ServletException {
            // true��ԋp
            return true;
    }   

    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */
    @Override
    protected String processPath(HttpServletRequest request,
                                  HttpServletResponse response) {
            return "";                                  
    }

    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */
    @Override
    protected Action processActionCreate(HttpServletRequest request,
                                  HttpServletResponse response,
                                  ActionMapping mapping) {
            return new Action();                  
    }

    /**
     * processActionForm�܂ŗ�O�𔭐������Ȃ��悤�A�I�[�o���C�h����B
     */
    @Override
    protected void processPopulate(HttpServletRequest req,
                                 HttpServletResponse res,
                                 ActionForm form,
                                 ActionMapping mapping) {
                                    
    }
}
