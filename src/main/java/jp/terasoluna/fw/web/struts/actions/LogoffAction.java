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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���O�I�t���������s����A�N�V�����B
 *
 * <p>
 * ���̃N���X�̎����ł́A���݂�HTTP�Z�b�V�����𖳌������A
 * struts-config.xml��&lt;action&gt;�v�f��
 * parameter�����Ɏw�肵����Ƀt�H���[�h����B
 * struts-config.xml�y��Bean��`�t�@�C���̐ݒ�͉��L�̂Ƃ���ł���B
 * </p>
 * <p>
 * <strong>Bean��`�t�@�C���̐ݒ�</strong>
 *  <code><pre>
 *  &lt;bean name="/logoff" scope="prototype"
 *      <strong>class="jp.terasoluna.fw.web.struts.actions.LogoffAction"</strong>&gt;
 *  &lt;/bean&gt;
 *  </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xml�̐ݒ�</strong>
 * <code><pre>
 * &lt;action path="/logoff"
 *     name="_logonForm"
 *     scope="session"
 *     parameter="/foo.jsp"&gt;
 * &lt;/action&gt;
 * </pre></code>
 * ���O�C�������ɂ��ẮAUserValueObject�ABLogic���Q�Ƃ̂��ƁB
 * </p>
 *
 */
public class LogoffAction extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(LogoffAction.class);
    
    /**
     * �G���[�y�[�W�i404�j�J�ڎ��s�������G���[�R�[�h�B
     */
    private static final String FORWARD_ERRORPAGE_ERROR = 
        "error.forward.errorpage";

    /**
     * ���O�I�t���������s����BHTTP�Z�b�V�����𖳌������A
     * parameter�����ɐݒ肳�ꂽ�J�ڐ��
     * �A�N�V�����t�H���[�h�ɃZ�b�g���ĕԂ��B
     * parameter�������ݒ肳��Ă��Ȃ��ꍇ�A�i404�j�G���[��Ԃ��B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @return �J�ڐ�̃A�N�V�����t�H���[�h
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res) {
        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        // ���p���̃Z�b�V�����𖳌�������
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // parameter�����i�t�H���[�h��j���擾
        String path = mapping.getParameter();

        if (path == null) {
            // parameter�������ݒ肳��Ă��Ȃ��ꍇ�A�i404�j�G���[��ԋp����
            try {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                log.error("Error page(404) forwarding failed.");
                
                throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
            }
            return null;
        }

        // �A�N�V�����t�H���[�h�𐶐�
        ActionForward retVal = new ActionForward(path);

        return retVal;
    }

}
