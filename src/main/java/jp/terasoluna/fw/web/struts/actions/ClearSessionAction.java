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
import java.util.List;

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
 * �Z�b�V��������w�肳�ꂽ�v���p�e�B���폜����B
 *
 * <p>
 *  Bean��`�t�@�C���Ɏw�肳�ꂽ�L�[�̃��X�g����A
 *  �Z�b�V�������폜����L�[���擾���폜���s���B<br>
 * </p>
 *
 * <p>
 *  �J�ڐ�́A���&lt;action&gt;�v�f��
 *  parameter�����Ɏw�肳�ꂽ�A�h���X�Ƀt�H���[�h���邽�߁A
 *  parameter�������K�{�ƂȂ�B
 *  �����āA���L��Bean��`�t�@�C���y��struts-config.xml��
 *  �ݒ��ł���B
 * </p>
 * <p>
 *  <strong>Bean��`�t�@�C����ClearSessionAction�ݒ��</strong>
 * <code><pre>
 * &lt;bean name="/clearSessionAction" scope="singleton"
 *   class="jp.terasoluna.fw.web.struts.actions.ClearSessionAction"&gt;
 *   <strong>&lt;property name="clearSessionKeys"&gt;
 *   &lt;list&gt;
 *     &lt;value&gt;userAddress&lt;/value&gt;
 *     &lt;value&gt;userPhoneNo&lt;/value&gt;
 *     &lt;value&gt;sampleSession&lt;/value&gt;
 *   &lt;/list&gt;
 *   &lt;/property&gt;</strong>
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * <p>
 *  <strong>struts-config.xml�̐ݒ��</strong>
 * <code><pre>
 * &lt;action path="/clearSessionAction"
 *   name="_sampleForm"
 *   scope="session"
 *   parameter="/sessionCleared.do"&gt;
 * &lt;/action&gt;
 * </pre></code>
 * ��L�ݒ�ɂ��A�A�N�V�����p�X��&quot;/clearSessionAction&quot;
 * �����s�����ƁAclearSessionKeys�v���p�e�B���폜�Ώۂ̃L�[
 * �ƂȂ�A���̌��ʁAuserAddress�AuserPhoneNo�AsampleSession�Ƃ���
 * 3�̃Z�b�V�����L�[����Q�Ƃ����Z�b�V������񂪍폜�����B<br>
 * ���݂̃Z�b�V�������̂��̂�j������ꍇ�́ALogoffAction���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.LogoffAction
 *
 */
public class ClearSessionAction extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(ClearSessionAction.class);
    
    /**
     * �G���[�y�[�W�i404�j�J�ڎ��s�������G���[�R�[�h�B
     */
    private static final String FORWARD_ERRORPAGE_ERROR = 
        "error.forward.errorpage";

    /**
     * �Z�b�V��������폜����L�[�̃��X�g�B
     */
    private List clearSessionKeys = null;

    /**
     * �Z�b�V��������폜����L�[�̃��X�g��ݒ�B
     *
     * @param clearSessionKeys �Z�b�V��������폜����L�[�̃��X�g
     */
    public void setClearSessionKeys(List clearSessionKeys) {
        this.clearSessionKeys = clearSessionKeys;
    }

    /**
     * �Z�b�V�����N���A���s���A����ʂփt�H���[�h����B
     * <p>
     *  �폜�Ώۂ̃L�[���P��������Ȃ��ꍇ�́A
     *  �J�ڐ����ԋp���A�������I������B
     * </p>
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param request <code>HTTP</code>���N�G�X�g
     * @param response <code>HTTP</code>���X�|���X
     * @return �J�ڐ���
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        HttpSession session = request.getSession();

        if (clearSessionKeys != null) {
            for (int cnt = 0; cnt < clearSessionKeys.size(); cnt++) {
                String sKey = (String) clearSessionKeys.get(cnt);
                if (log.isDebugEnabled()) {
                    log.debug("removing [" + sKey + "] from HttpSession");
                }
                //�Z�b�V��������폜�����s
                session.removeAttribute(sKey);
            }
        }

        // �p�����[�^�����i�t�H���[�h��j���擾
        String path = mapping.getParameter();

        if (path == null) {
            // �p�����[�^�������ݒ肳��Ă��Ȃ��ꍇ�A(404)�G���[��ԋp����
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
