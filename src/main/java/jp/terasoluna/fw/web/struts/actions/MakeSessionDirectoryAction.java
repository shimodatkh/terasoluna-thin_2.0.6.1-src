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
import jp.terasoluna.fw.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p>�Z�b�V�����f�B���N�g�����쐬����A�N�V�����B</p>
 *
 * <p>
 * �T�[�o�T�C�h�Ő������ꂽPDF�t�@�C���Ȃǂ��i�[���邽�߂̈ꎞ�f�B���N�g��
 * �i�ȍ~�A�Z�b�V�����f�B���N�g���j�����O�I�����[�U���ɍ쐬����B<br>
 * <br>
 * ���̋@�\���g�����߂ɂ̓V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j
 * �ɃZ�b�V�����f�B���N�g���x�[�X�p�X���L�q���Ă����K�v������B<br>
 * <br>
 * <h5>�V�X�e���ݒ�v���p�e�B�t�@�C���isystem.properties�j�̐ݒ�</h5>
 * <code><pre><strong>session.dir.base=/tmp/sessions</strong></pre></code>
 * �Z�b�V�����f�B���N�g���̍쐬�ɂ͈ȉ��̓�̕��@�ŗ��p�\�ł���B<br>
 * <br>
 * �i�P�j{@link MakeSessionDirectoryAction}�N���X�̗��p<br>
 * ���O�I���ɐ���������MakeSessionDirectoryAction�ɑJ�ڂ���B<br>
 * struts-config.xml��action�v���p�e�B��parameter�����Ɏw�肵����Ƀt�H���[�h����B<br>
 * struts-config.xml�y��Bean��`�t�@�C���̐ݒ�͉��L�̂Ƃ���ł���B
 * <h5>struts-config.xml�̐ݒ�</h5>
 * <code><pre>
 * &lt;action path="<strong>/makeSessionDir</strong>"
 *     scope="session"
 *     parameter="/foo.jsp"&gt;;
 * &lt;/action&gt;
 * </pre></code>
 * <h5>Bean��`�t�@�C���̐ݒ�</h5>
 * <code><pre>
 * &lt;bean name="<strong>/makeSessionDir</strong>" scope="prototype"
 *   class="<strong>jp.terasoluna.fw.web.struts.actions.MakeSessionDirectoryAction</strong>"&gt;
 * &lt;/bean&gt;
 * </pre></code>
 *
 * �i�Q�j�A�N�V�����N���X�Œ��ڃZ�b�V�����f�B���N�g���쐬<br>
 * MakeSessionDirectoryAction�ȊO�̃A�N�V�����ł́A���[�U���O�C�����
 * {@link javax.servlet.http.HttpSession}���擾�\�ȏꏊ��
 * FileUtil#makeSessionDirectory(String sessionId)���Ăяo���K�v������B<br>
 * <br>
 * <br>
 * �Z�b�V�����f�B���N�g���̍폜�͈ȉ��̕��@�ŗ��p�\�ł���B<br>
 * <br>
 * �i�P�j{@link javax.servlet.http.HttpSessionListener}�����N���X�̗��p<br>
 * �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j��
 * HttpSessionListener�����N���X�����X�i�Ƃ��ēo�^���A
 * �Z�b�V�������j�����ꂽ�Ƃ��Z�b�V�����f�B���N�g�����폜���鏈��
 * �iFileUtil#removeSessionDirectory(String sessionId)�j��
 * ���X�i�Ɏ�������B<br>
 * web.xml�̐ݒ�͉��L�̂Ƃ���ł���B<br>
 * <h5>�f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�̐ݒ�</h5>
 * <code><pre>
 * &lt;web-app&gt;
 *   �E�E�E
 *   &lt;listener&gt;
 *     &lt;listener-class&gt;
 *       <strong>jp.terasoluna.fw.web.MyHttpSessionListener</strong>
 *     &lt;/listener-class&gt;
 *   &lt;/listener&gt;
 * &lt;/web-app&gt;
 * </pre></code>
 * </p>
 *
 */
public class MakeSessionDirectoryAction extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log =
        LogFactory.getLog(MakeSessionDirectoryAction.class);

    /**
     * �Z�b�V�����擾���s�������G���[�R�[�h�B
     */
    private static final String SESSION_NOT_FOUND_ERROR =
        "error.session.not.found";
    
    /**
     * �G���[�y�[�W�i404�j�J�ڎ��s�������G���[�R�[�h�B
     */
    private static final String FORWARD_ERRORPAGE_ERROR = 
        "error.forward.errorpage";

    /**
     * <p>
     *  �Z�b�V�����f�B���N�g���𐶐�����B<br>
     *  �Z�b�V�������擾�ł����ꍇ�A�Z�b�V�����f�B���N�g�����쐬������
     *  �p�����[�^�����ɐݒ肳�ꂽ�J�ڐ���A�N�V�����t�H���[�h�ɃZ�b�g���ĕԂ��B<br>
     *  �p�����[�^�������ݒ肳��Ă��Ȃ��ꍇ�A(404)�G���[��Ԃ��B<br>
     *  �Z�b�V�������擾�ł��Ȃ������ꍇ�A�V�X�e����O���N�����B
     * </p>
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @return �J�ڐ�̃A�N�V�����t�H���[�h�B
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res) {

        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        // ���p���̃Z�b�V�������Ȃ��������O���N����
        HttpSession session = req.getSession(false);
        if (session == null) {
            log.error("HttpSession is not available.");
            throw new SystemException(null, SESSION_NOT_FOUND_ERROR);
        }

        // �Z�b�V����ID���擾���A�Z�b�V�����f�B���N�g���𐶐�����B
        FileUtil.makeSessionDirectory(session.getId());

        // �p�����[�^�����i�t�H���[�h��j���擾
        String path = mapping.getParameter();

        if (path == null) {
            // �p�����[�^�������ݒ肳��Ă��Ȃ��ꍇ�A(404)�G���[��ԋp����
            try {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                log.error("Error page(404) forwarding failed.");
                throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
            }
            return null;
        }

        // ���̉�ʑJ��
        ActionForward retVal = new ActionForward(path);
        return retVal;
    }
}
