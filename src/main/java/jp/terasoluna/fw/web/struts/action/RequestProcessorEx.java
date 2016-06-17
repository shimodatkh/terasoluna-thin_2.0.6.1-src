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
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.util.ExceptionUtil;
import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.fw.web.struts.form.ActionFormUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DelegatingRequestProcessor;

/**
 * DelegatingRequestProcessor�g���N���X�B
 * <p>
 *  �N���C�A���g����̃��N�G�X�g��ߑ����AURI�ɉ������A�N�V�����̋N���Ȃǂ��s���B
 *  org.springframework.web.struts.DelegatingRequestProcessor���p�����āA
 *  �ȉ��̋@�\��ǉ����Ă���B
 * </p>
 *
 * <ol>
 * <li>&quot;_&quot;�Ŏn�܂�
 *  �A�N�V�����t�H�[�����̃Z�b�V�������B�ꐫ�̕ۏ�</li>
 * <p>
 *  �Z�b�V�����X�R�[�v�̃A�N�V�����t�H�[���_������
 *  &quot;_&quot;�Ŏn�܂��Ă����ꍇ�A
 *  �C���X�^���X���쐬����Ƃ��ɁA�Z�b�V�����Ɋi�[����Ă���
 *  ���̃A�N�V�����t�H�[���̘_�������A&quot;_&quot;
 *  �Ŏn�܂���̂͂��ׂč폜�����B
 *  ����ɂ��A�Z�b�V�����ł͍ő�ł�1�̃A�N�V�����t�H�[
 *  ��������������邱�Ƃ��ۏ؂���A�Z�b�V�����X�R�[�v��
 *  �A�N�V�����t�H�[���폜�������L�q���Ȃ��Ă��A
 *  ���p����Ȃ��������g�p�ʂɉ������邱�Ƃ��ł���B
 * </p>
 * <h5>struts-config.xml�̃A�N�V�����t�H�[���w���</h5>
 * <code><pre>
 * &lt;struts-config&gt;
 * &lt;form-beans&gt;
 * &lt;!-- �t�H�[���̐擪��"_"������  --&gt;
 * &lt;form-bean name="_sampleForm"
 * type="jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx"
 * &gt;
 * &lt;form-property name="companyId" type="java.lang.String" /&gt;
 * �@�@�@�@�@�E�E�E
 * &lt;/struts-config&gt;
 * </pre></code>
 *
 *
 * <li>�t�H�[�����f�}�~�@�\</li>
 * <p>
 *  �r�W�l�X���W�b�N���s���ʂ𔽉f�����A�N�V�����t�H�[������
 *  �t�H�[�����؂�ւ���Ă��Ȃ��ꍇ�́AprocessPopulate()���X�L�b�v����B
 * </p>
 *
 * <li>�A�N�V�����t�H�[���̃G���[�ێ�</li>
 * <p>
 *  ���̓`�F�b�N�G���[���������ꍇ�́A�|�b�v�A�b�v��ʂɃG���[��
 *  �\�������邽�߁A�A�N�V�����t�H�[���ɃA�N�V�����G���[��ۑ�����B
 *  �G���[���������Ă��Ȃ��Ƃ��́A��̃A�N�V�����G���[���A�N�V����
 *  �t�H�[���ɐݒ肷��B
 * </p>
 *
 * <li>��O�������̃��O�o�͋@�\</li>
 * <p>
 *  RequestProcessor#process()���ŗ�O���X���[���ꂽ���A
 *  ��O�X�^�b�N�g���[�X�����O�ɏo�͂���B
 *  ���O�o�͌�A����������O��ServletException�Ƀ��b�v����A�X���[�����B
 * </p>
 * </ol>
 *
 * <p>
 *  RequestProcessorEx�𗘗p���邽�߂ɂ́Astruts-config.xml��
 *  &lt;controller&gt;�v�f��processorClass�����ɐݒ肪�K�v�ł���B
 * <h5>struts-config.xml�̐ݒ��</h5>
 * <code><pre>
 * &lt;struts-config&gt;
 *   �E�E�E
 *   &lt;controller processorClass=
 *       "jp.terasoluna.fw.web.struts.RequestProcessorEx"/&gt;
 *   �E�E�E
 * &lt;/struts-config&gt;
 * </pre></code>
 * �t�H�[���}�~�@�\�̏ڍׂɂ��ẮAActionEx���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.ActionEx
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 *
 */
public class RequestProcessorEx extends DelegatingRequestProcessor {

    /**
     * ���O�N���X�B
     */
    @SuppressWarnings("hiding")
    private static Log log
            = LogFactory.getLog(RequestProcessorEx.class);

    /**
     * processPopulate()���X�L�b�v����t���O�����N�G�X�g�ɐݒ肷��Ƃ��̃L�[�B
     */
    public static final String SKIP_POPULATE = "SKIP_POPULATE";
    
    /**
     * Struts��HTTP���N�G�X�g�������g������B
     *
     * <p>Struts��ActionServlet��process()���\�b�h�ŃX���[���ꂽ��O��
     * SystemException���܂߂Ă��̃��\�b�h�ŃL���b�`�����B
     * �����āA��O�̓��e�ƃZ�b�V����ID�̃n�b�V���l�����O�o�͂��ꂽ��A
     * ServletException�Ƀ��b�v����ăX���[�����B</p>
     *
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @throws IOException IO��O
     * @throws ServletException �T�[�u���b�g��O
     */
    @Override
    public void process(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("process() called.");
        }

        try {
            // �X�[�p�[�N���X�� process() �����s����
            super.process(req, res);
        } catch (IOException e) {
            String sessionHash = RequestUtil.getSessionHash(req);
            log.error("sessionHash = " + sessionHash);
            log.error(ExceptionUtil.getStackTrace(e));
            throw e;
        } catch (ServletException e) {
            String sessionHash = RequestUtil.getSessionHash(req);
            log.error("sessionHash = " + sessionHash);
            log.error(ExceptionUtil.getStackTrace(e));
            throw e;
        } catch (Exception e) {
            String sessionHash = RequestUtil.getSessionHash(req);
            log.error("sessionHash = " + sessionHash);
            log.error(ExceptionUtil.getStackTrace(e));
            throw new ServletException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("process() finished.");
        }
    }

    /**
     * Struts�̃A�N�V�����t�H�[���擾�������g������B
     *
     * <p>�A�N�V�����}�b�s���O�Ŏw�肳�ꂽ�A�N�V�����t�H�[�������A
     * "_"�Ŏn�܂��Ă�A�N�V�����t�H�[���̏ꍇ�́A
     * processActionFormEx()�ɏ������Ϗ�����B</p>
     *
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @param mapping �A�N�V�����}�b�s���O
     * @return ActionForm �A�N�V�����t�H�[��
     */
    @Override
    protected ActionForm processActionForm(HttpServletRequest req,
                                           HttpServletResponse res,
                                           ActionMapping mapping) {
        if (log.isDebugEnabled()) {
            log.debug(
                "processActionForm() called. path = " + mapping.getPath());
            log.debug("mapping.name = " + mapping.getName());
            log.debug("mapping.scope = " + mapping.getScope());
        }

        if (mapping instanceof ActionMappingEx
                && mapping.getScope() != null
                && "session".equals(mapping.getScope())
                && mapping.getName() != null
                && mapping.getName().startsWith("_")) {

            // �X�R�[�v��"session"�A���A�N�V�����t�H�[���_������"_"��
            // �n�܂��Ă���ꍇ�́AprocessActionFormEx() �����s����
            return processActionFormEx(req,
                res,
                (ActionMappingEx) mapping);
        }
        // �X�[�p�[�N���X�� processActionForm() �����s����
        return super.processActionForm(req, res, mapping);
    }

    /**
     * Struts�̃A�N�V�����t�H�[���擾�������g������B
     * processActionForm()����Ă΂��B
     *
     * <p>�A�N�V�����}�b�s���O�Ŏw�肳�ꂽ�A�N�V�����t�H�[�������A
     * "_"�Ŏn�܂��Ă�A�N�V�����t�H�[������������B</p>
     *
     * <p>�A�N�V�����t�H�[���̏�����A
     * RequestProcessor��processPopulate()���\�b�h���N������B</p>
     *
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @param mapping �A�N�V�����}�b�s���O
     * @return �A�N�V�����t�H�[��
     */
    protected ActionForm processActionFormEx(HttpServletRequest req,
                                             HttpServletResponse res,
                                             ActionMappingEx mapping) {
        if (log.isDebugEnabled()) {
            log.debug(
                "processActionFormEx() called. path = " + mapping.getPath());
        }

        boolean clearForm = false;
        // �A�N�V�����}�b�s���O��clearForm�v���p�e�B���Q�Ƃ���B
        clearForm = mapping.getClearForm();
        if (log.isDebugEnabled()) {
            log.debug("clearForm = " + clearForm);
        }

        if (clearForm) {
            // clearForm�v���p�e�B��true�̂Ƃ��́A
            // "_"�w�肳�ꂽ�A�N�V�����t�H�[�����A
            // �Z�b�V�������炷�ׂč폜����B
            HttpSession session = req.getSession();
            ActionFormUtil.clearActionForm(session);
        } else {
            // clearForm�v���p�e�B��false�̂Ƃ��́A
            // "_"�Ŋi�[���ꂽ�A�N�V�����t�H�[�����A
            // �Z�b�V��������폜����B
            // �������A�w�肳�ꂽ�A�N�V�����t�H�[���������B
            HttpSession session = req.getSession();
            ActionFormUtil.clearActionForm(session, mapping.getName());
        }

        // �X�[�p�[�N���X�� processActionForm() �����s����
        return super.processActionForm(req, res, mapping);
    }

    /**
     * Struts�̃A�N�V�����t�H�[���ւ̃��N�G�X�g�p�����[�^���f�������g������B
     *
     * <p>�r�W�l�X���W�b�N���s���ʂ𔽉f�����A�N�V�����t�H�[������
     * �؂芷����Ă���ꍇ�́A
     * RequestProcessor��processPopulate()���\�b�h���N������B</p>
     * <p>struts-config.xml�̃A�N�V�����}�b�s���O�ݒ莞�A
     * cancelPopulate��true �ɂ����ꍇ�A
     * processPopulate ���L�����Z�������B</p>
     *
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @param form �A�N�V�����t�H�[��
     * @param mapping �A�N�V�����}�b�s���O
     * @throws ServletException �T�[�u���b�g��O
     */
    @Override
    protected void processPopulate(HttpServletRequest req,
                                   HttpServletResponse res,
                                   ActionForm form,
                                   ActionMapping mapping)
            throws ServletException {

        if (log.isDebugEnabled()) {
            log.debug(
                "processPopulate() called. path = " + mapping.getPath());
        }

        boolean cancelPopulateflg  = false;
        cancelPopulateflg = ((ActionMappingEx) mapping).getCancelPopulate();
        if (cancelPopulateflg) {
            if (log.isDebugEnabled()) {
                log.debug("processPopulate() canceled.");
            }
            // �ȍ~�͎��s���Ȃ�
            return;
        }

        String skipPopulate = (String) req.getAttribute(SKIP_POPULATE);
        if (skipPopulate != null) {
            if (skipPopulate.equals(mapping.getName())) {

                if (log.isDebugEnabled()) {
                    log.debug("processPopulate() skipped.");
                }
                // �ȍ~�͎��s���Ȃ�
                return;
            }
            req.removeAttribute(SKIP_POPULATE);
        }
        // �X�[�p�[�N���X�� processPopulate() �����s����
        super.processPopulate(req, res, form, mapping);

    }
}
