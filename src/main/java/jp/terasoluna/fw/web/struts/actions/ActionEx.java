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
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.action.RequestProcessorEx;
import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * �A�N�V�������N���X�B
 *
 * <p>
 *  Struts��Action�ɁA���O�o�͋@�\�E�g�����U�N�V����
 *  �g�[�N���`�F�b�N�@�\��ǉ����Ă���B<br>
 *  �����̋@�\�𗘗p����ꍇ�́A�{�N���X���p�����邱�ƁB<br>
 *  �{�N���X�𗘗p���邽�߂ɂ́AdoExecute()���\�b�h��
 *  �I�[�o���C�h�����T�u�N���X���쐬���Astruts-config.xml��
 *  &lt;action&gt;�v�f����type�����ŃN���X����ݒ肷��B<br>
 *  �g�����U�N�V�����g�[�N���`�F�b�N�Ƃ́A�T�u�~�b�g�Q�x������A
 *  �u���E�U�́u�߂�v�{�^�����g�����d���T�u�~�b�g��h���@�\�ł���B<br>
 *  �g�����U�N�V�����g�[�N���`�F�b�N���s�����߂ɂ́A�ȉ��̐ݒ��
 *  �s���K�v������B
 *  <ul>
 *   <li>
 *     �A�N�V�����N���X��Bean��`��
 *     &lt;property&gt;�v�f��
 *     &quot;tokenCheck&quot;�ɑ΂��A
 *     &quot;true&quot;�𖾎�����K�v������
 *   </li>
 *   <li>
 *     struts-config.xml��
 *     &lt;forward&gt;�v�f�i&lt;grobal-forwards&gt;
 *     ���ł��j��&quot;txtoken-error&quot;�Ƃ������O��
 *     �g�[�N���G���[���̃p�X���w�肷��
 *   </li>
 *  </ul>
 *  ActionEx���g�p�����ꍇ�AsaveToken()��
 *  ����Ď����I�Ƀg�[�N�����ۑ�����邪�A�ۑ����Ȃ��ꍇ�ɂ͈ȉ��̐ݒ���s��
 *  �K�v������B<br>
 *  <ul>
 *   <li>
 *     �A�N�V�����N���X��Bean��`��
 *     &lt;property&gt;�v�f��
 *     &quot;saveToken&quot;�ɑ΂��A
 *     &quot;false&quot;�𖾎�����K�v������
 *   </li>
 *  </ul>
 *  �g�����U�N�V�����g�[�N���`�F�b�N���܂߂�struts-config.xml
 *  �y��Bean��`�t�@�C���̐ݒ����A���L�Ɏ����B<br>
 * </p>
 * <p>
 *  <strong>�T�u�N���XLogoffAction��Bean��`�t�@�C���̐ݒ��</strong>
 *  <code><pre>
 *  &lt;bean name="/admin/Logout" scope="prototype"
 *      class="jp.terasoluna.fw.web.struts.actions.LogoffAction"&gt
 *    &lt;property property="tokenCheck"&gt;
 *      &lt;value&gt;true&lt;/value&gt;
 *    &lt;/property&gt;
 *    &lt;property property="saveToken"&gt;
 *      &lt;value&gt;false&lt;/value&gt;
 *    &lt;/property&gt;
 *  &lt;/bean&gt
 *  </pre></code>
 * </p>
 * <p>
 *  <strong>�T�u�N���XLogoffAction��struts-config.xml�̐ݒ��</strong>
 *  <code><pre>
 *  &lt;action path="/admin/Logout"
 *      name="logonSampleForm"
 *      scope="session"
 *      parameter="/sub/logout.jsp"&gt
 *    &lt;forward name="txtoken-error" modeule="/sub"
 *        path="/doubleRegistError.jsp"/&gt
 *  &lt;/action&gt
 *  </pre></code>
 *
 *  �Ȃ��A�����v�f��forward�ŁApath�̐擪�ɃX���b�V��
 *  &quot;/&quot;��t���Amodule��ݒ肵���ꍇ�A
 *  ���W���[�����Ƃ̑��΃p�X�őJ�ڂ��邱�Ƃ��ł���B<br>
 *  �܂��A�{�@�\���T�u�N���X�Ōp������ꍇ�AdoExecute()���\�b�h��
 *  �G���g���|�C���g�Ƃ��Ď�������K�v������B<br>
 *  �A�N�V�����}�b�s���O�̐ݒ�ɂ��ẮAActionMappingEx���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.action.ActionMappingEx
 *
 */
public abstract class ActionEx extends Action {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(ActionEx.class);

    /**
     * �g�����U�N�V�����g�[�N���`�F�b�N�G���[���̘_���t�H���[�h���B
     */
    protected static final String FORWARD_TXTOKEN_ERROR = "txtoken-error";

    /**
     * �g�����U�N�V�����g�[�N���`�F�b�N���s�����ǂ����B
     * �f�t�H���g��false�i�s��Ȃ��j�B
     */
    private boolean tokenCheck = false;

    /**
     * �g�����U�N�V�����g�[�N���̕ۑ������邩�ǂ����B
     * �f�t�H���g��true�i�ۑ�����j�B
     */
    private boolean saveToken = true;

    /**
     * �g�����U�N�V�����g�[�N���`�F�b�N�t���O��ݒ肷��B
     *
     * @param tokenCheck �g�����U�N�V�����g�[�N���`�F�b�N�t���O
     */
    public void setTokenCheck(boolean tokenCheck) {
        this.tokenCheck = tokenCheck;
    }

    /**
     * �g�����U�N�V�����g�[�N���ۑ��t���O��ݒ肷��B
     *
     * @param saveToken �g�����U�N�V�����g�[�N���ۑ��t���O
     */
    public void setSaveToken(boolean saveToken) {
        this.saveToken = saveToken;
    }
    
    /**
     * �g�����U�N�V�����g�[�N���`�F�b�N�t���O���擾����B
     *
     * @return �g�����U�N�V�����g�[�N���`�F�b�N�t���O
     */
    protected boolean isTokenCheck() {
        return tokenCheck;
    }
    
    /**
     * �g�����U�N�V�����g�[�N���ۑ��t���O���擾����
     *
     * @return �g�����U�N�V�����g�[�N���ۑ��t���O
     */
    protected boolean isSaveToken() {
        return saveToken;
    }

    /**
     * �A�N�V���������s����B
     * <p>
     *  �T�u�N���X�Ŏ������ꂽdoExecute()���\�b�h�Ăяo���O�ɁA
     *  �A�N�V�����t�H�[����modified������false�ɏ���������B<br>
     *  doExecute()�̎��s��A�A�N�V�����t�H�[����
     *  �t�B�[���h�l�ɕύX������΃��N�G�X�g������SKIP_POPULATE
     *  ��ۑ����ARequestProcessorEx��
     *  processPopulate()�̏�����}�����Ă���B
     * </p>
     *
     * @param mapping ���̃A�N�V�����ɕR�Â����A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param request ���N�G�X�g���
     * @param response ���X�|���X���
     * @return �J�ڐ���
     * @throws Exception �A�N�V�������s���̗�O
     */
    @Override
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute() called.");
        }

        if (!processTokenCheck(mapping, request)) {
            if (log.isInfoEnabled()) {
                log.info("Transaction token error.");                
            }
            if (log.isDebugEnabled()) {
                log.debug("forward = " + FORWARD_TXTOKEN_ERROR);
            }
            return mapping.findForward(FORWARD_TXTOKEN_ERROR);
        }

        if (form != null && form instanceof FormEx) {
            FormEx formEx = (FormEx) form;
            formEx.setModified(false);
        }

        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }
        ActionForward forward = doExecute(mapping, form, request, response);
        if (log.isDebugEnabled()) {
            log.debug("doExecute() finished.");
        }

        if (form != null && form instanceof FormEx) {
            FormEx formEx = (FormEx) form;
            if (formEx.isModified()) {
                request.setAttribute(RequestProcessorEx.SKIP_POPULATE,
                    mapping.getName());
            }
        }

        if (log.isDebugEnabled()) {
            if (forward != null) {
                log.debug("forward = " + forward.getName()
                    + "(" + forward.getPath() + ")");
            } else {
                log.debug("forward = null");
            }
        }

        return forward;
    }

    /**
     * �A�N�V���������s���钊�ۃ��\�b�h�B
     * <p>
     *  ���̃��\�b�h���T�u�N���X�ŃI�[�o�[���C�h���A
     *  �A�N�V�����̋@�\���g������B
     * </p>
     *
     * @param mapping ���̃A�N�V�����ɕR�Â����A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param request ���N�G�X�g���
     * @param response ���X�|���X���
     * @return �J�ڐ���
     * @throws Exception �\�����Ȃ���O
     */
    public abstract ActionForward doExecute(ActionMapping mapping,
                                            ActionForm form,
                                            HttpServletRequest request,
                                            HttpServletResponse response)
        throws Exception;

    /**
     * �g�����U�N�V�����g�[�N���̃`�F�b�N���s���B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param req HTTP���N�G�X�g
     * @return �g�[�N���������ł���� <code>true</code>
     */
    protected boolean processTokenCheck(ActionMapping mapping,
                                        HttpServletRequest req) {
        if (log.isDebugEnabled()) {
            log.debug("processTokenCheck() called.");
        }

        // mapping��ActionMappingEx�ł͂Ȃ��Ƃ��A��������true��ԋp����
        if (!(mapping instanceof ActionMappingEx)) {
            if (log.isDebugEnabled()) {
                log.debug("mapping is not instance of ActionMappingEx.");
            }
            return true;
        }

        boolean result = true;
        synchronized (req.getSession().getId().intern()) {
            if (tokenCheck) {
                if (!isTokenValid(req)) {
                    result = false;
                }
            }
            if (saveToken) {
                saveToken(req);
            }
        }
        return result;
    }

    /**
     * �Z�b�V��������AGlobals.ERROR_KEY���L�[�Ƃ���
     * ActionMessages���擾���A�ǉ���ɍēx�i�[���s���B
     *
     * @param session �Z�b�V����
     * @param errors �G���[���b�Z�[�W���
     */
    protected void addErrors(HttpSession session, ActionMessages errors) {

        if (session == null) {
            return;
        }
        
        if (errors == null) {
            return;
        }

        // �Z�b�V��������G���[���b�Z�[�W�����擾����A�܂��͐V�����Ώۂ��쐬����
        ActionMessages sessionErrors =
            (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
        if (sessionErrors == null) {
            sessionErrors = new ActionMessages();
        }
        // �����݃G���[��ǉ�����
        sessionErrors.add(errors);

        // �܂��A�G���[���b�Z�[�W��񂪋�̏ꍇ�A�Z�b�V�����̑S�ẴG���[���b�Z�[�W�����폜����
        if (sessionErrors.isEmpty()) {
            session.removeAttribute(Globals.ERROR_KEY);
            return;
        }

        // �Z�b�V�����ɃG���[���b�Z�[�W��ۑ�����
        session.setAttribute(Globals.ERROR_KEY, sessionErrors);

    }

    /**
     * �Z�b�V��������AGlobals.MESSAGE_KEY���L�[�Ƃ���
     * ActionMessages���擾���A�ǉ���ɍēx�i�[���s���B
     *
     * @param session �Z�b�V����
     * @param messages ���b�Z�[�W���
     */
    protected void addMessages(HttpSession session, ActionMessages messages) {

        if (session == null) {
            return;
        }
        
        if (messages == null) {
            return;
        }

        // �Z�b�V�������烁�b�Z�[�W�����擾����A�܂��͐V�����Ώۂ��쐬����
        ActionMessages sessionMessages =
            (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
        if (sessionMessages == null) {
            sessionMessages = new ActionMessages();
        }
        // �����݃��b�Z�[�W��ǉ�����
        sessionMessages.add(messages);

        // �܂��A���b�Z�[�W��񂪋�̏ꍇ�A�Z�b�V�����̑S�Ẵ��b�Z�[�W�����폜����
        if (sessionMessages.isEmpty()) {
            session.removeAttribute(Globals.MESSAGE_KEY);
            return;
        }

        // �Z�b�V�����Ƀ��b�Z�[�W��ۑ�����
        session.setAttribute(Globals.MESSAGE_KEY, sessionMessages);
    }

}
