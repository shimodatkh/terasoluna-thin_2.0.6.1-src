/*
 * Copyright (c) 2007-2008 NTT DATA Corporation
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ExceptionUtil;
import jp.terasoluna.fw.web.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.MessageResources;

/**
 * SystemException��p�̗�O�����n���h���B
 *
 * <p>
 * �V�X�e����O�������̃��O�o�͂ƃG���[��ʂւ̑J�ڂ��s���B<br>
 * �A�N�V�������s���ɃV�X�e����O�����������Ƃ��́A
 * �G���[�������O�o�͂�����ŁA���Y�A�N�V�����}�b�s���O�ɒ�`����Ă���
 * �V�X�e���G���[��ʂɑJ�ڂ���B<br>
 * �{�@�\�𗘗p���邽�߂ɂ́AStruts�ݒ�t�@�C��(struts-config.xml)��
 * �O���[�o����O�A�܂��̓A�N�V�������x����O�n���h���N���X�Ƃ��Ďw�肷��B<br>
 * �O���[�o����O�ƁA�A�N�V�������x����O���Փ˂��Ă���ꍇ�A
 * Struts�̎d�l�ɂ��A�N�V�������x����O���D�悳���B<br>
 * �u��������SystemException�Ɏw�肳��Ă����ꍇ�́A�G���[���b�Z�[�W�ւ̖��ߍ��݂��s���B
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>���O���x���̎w��</legend>
 * �v���p�e�B<strong><u>logLevel</u></strong>�ɉ��L��6��ނ̃��O���x����
 * �w�肷�邱�Ƃ��\�B
 * <p>
 * <ol>
 *   <li>trace</li>
 *   <li>debug</li>
 *   <li>info</li>
 *   <li>warn</li>
 *   <li>error</li>
 *   <li>fatal</li>
 * </ol>
 * </p>
 * ���w�肵�Ȃ��ꍇ�́A�f�t�H���g��error�ƂȂ�B<br>
 * ��logLevel���w�肷��ꍇ�́A&lt;exception&gt;�^�O��className������
 * <strong><u>ExceptionConfigEx</u></strong>���w�肷�邱�ƁB
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>�O���[�o����O�n���h�����O�ݒ��</legend>
 *
 * Struts�ݒ�t�@�C��(struts-config.xml)�Ɉȉ��̂悤�ɋL�q����B
 * <pre><code>
 * &lt;struts-config&gt;
 *   �c
 *   &lt;global-exceptions&gt;
 *     &lt;exception key="some.key"
 *                path="/system-error"
 *                type="jp.terasoluna.fw.exception.SystemException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.SystemExceptionHandler"&gt;
 *       &lt;set-property property="module" value="/exp"/&gt;
 *       &lt;set-property property="logLevel" value="fatal"/&gt;
 *     &lt;/exception&gt;
 *   &lt;/global-exceptions&gt;
 *   �c
 * &lt;struts-config&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>�A�N�V�������x����O�n���h�����O�ݒ��</legend>
 *
 * Struts�ݒ�t�@�C��(struts-config.xml)�Ɉȉ��̂悤�ɋL�q����B
 * <pre><code>
 * &lt;struts-config&gt;
 *   �c
 *   &lt;action path="/start"
 *           type="jp.terasoluna.sample.xxx.SampleAction"
 *           name="_sampleForm"
 *           scope="session"&gt;
 *     &lt;exception key="some.key"
 *                type="jp.terasoluna.fw.exception.SystemException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.SystemExceptionHandler"
 *                path="/sub-forward.do"&gt;
 *       &lt;set-property property="module" value="/sub"/&gt;
 *     &lt;/exception&gt;
 *     &lt;forward name="success" path="/system-error"/&gt;
 *   &lt;/action&gt;
 *   �c
 * &lt;struts-config&gt;
 * </code></pre>
 * </fieldset>
 * </p>
 *
 * <p>
 * �Ȃ��A&lt;exception&gt;�v�f��path�����őJ�ڐ�p�X���w��
 * ����Ă��Ȃ��ꍇ�́A�A�N�V�����}�b�s���O��input������
 * �]���惊�\�[�X�Ƃ���B
 * </p>
 *
 * <p>
 * ����������O�C���X�^���X�́Areuqest��<strong>PageContext.EXCEPTION</strong>�̃L�[�Ŋi�[�����B
 * ����ɂ��J�ڐ��JSP�ɂāA�ÖكI�u�W�F�N�g�� exception �Ƃ��Ď擾�ł���B
 * </p>
 *
 * @see jp.terasoluna.fw.exception.SystemException
 * @see jp.terasoluna.fw.web.struts.action.ExceptionConfigEx
 * @see jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler
 * @see javax.servlet.jsp.PageContext
 *
 */
public class SystemExceptionHandler extends DefaultExceptionHandler {

    /**
     * ���O�N���X�B
     */
    @SuppressWarnings("unused")
    private static Log log
    = LogFactory.getLog(SystemExceptionHandler.class);

    /**
     * SystemException��O�n���h���̃G���g���|�C���g�B
     *
     * @param ex ��O
     * @param eConfig ��O�R���t�B�O
     * @param mapping �A�N�V�����}�b�s���O
     * @param formInstance �A�N�V�����t�H�[��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     *
     * @return �J�ڏ��
     *
     * @throws ServletException �T�[�u���b�g��O
     *
     * @see jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler#execute(
     *  java.lang.Exception,
     *  org.apache.struts.config.ExceptionConfig,
     *  org.apache.struts.action.ActionMapping,
     *  org.apache.struts.action.ActionForm,
     *  javax.servlet.http.HttpServletRequest,
     *  javax.servlet.http.HttpServletResponse
     *  )
     */
    @Override
    public ActionForward execute(Exception ex,
                                 ExceptionConfig eConfig,
                                 ActionMapping mapping,
                                 ActionForm formInstance,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException {
        // �y�t�H���[�h���ݒ肷��z
        // path�ɂ��t�H���[�h�悪�w�肳��Ȃ��ꍇ�́A
        // �A�N�V�����}�b�s���O��input�������f�t�H���g�Ƃ���B
        String path = null;
        if (eConfig.getPath() != null) {
            path = eConfig.getPath();
        } else {
            path = mapping.getInput();
        }
        ActionForward forward = new ActionForward(path);
        String logLevel = null;

        // �y�J�ڐ��ݒ肷��z
        if (eConfig instanceof ExceptionConfigEx) {
            //�J�ڐ惂�W���[�����ݒ肳��Ă���Ƃ��A���W���[������ݒ肷��
            forward.setModule(((ExceptionConfigEx) eConfig).getModule());
            // ���O���x�����擾����
            logLevel = ((ExceptionConfigEx) eConfig).getLogLevel();
        }

        // �ySystemException�̏ꍇ�A�G���[�L�[�ƃG���[���b�Z�[�W�̒u�����s���z
        if (ex instanceof SystemException) {
            SystemException se = (SystemException) ex;

            // �y���N�G�X�g���烁�b�Z�[�W���\�[�X���擾����B�z
            MessageResources resources = null;

            // �X�R�[�v���烁�b�Z�[�W���\�[�X���擾����ۂ̃o���h���L�[���擾����B
            String bundle = eConfig.getBundle();
            if (bundle == null) {
                // struts-config.xml��message-resources��
                // bundle�������w�肳��Ă��Ȃ��ꍇ�A
                // �f�t�H���g�̃o���h���L�[��ݒ肷��
                bundle = Globals.MESSAGES_KEY;
            }

            // ���N�G�X�g��������̎擾�����݂�B
            resources = (MessageResources) request.getAttribute(bundle);
            if (resources == null) {
                // ���N�G�X�g�����ɂȂ���΃A�v���P�[�V������������̎擾�����݂�B
                resources = (MessageResources) RequestUtil.getServletContext(
                        request).getAttribute(bundle);
            }

            // �y�G���[�L�[�ƃG���[���b�Z�[�W�̒u�����s���z
            // SystemException�̃G���[�L�[���G���[���b�Z�[�W�ɒu������B
            String message = null;
            if (resources == null) {
                // ���\�[�X�擾�ł��Ȃ��ꍇ�̓G���[�L�[�����b�Z�[�W�Ƃ���
                message = se.getErrorCode();
            } else {
                message = getErrorMessage(request, se, resources);
            }
            se.setMessage(message);

            // �y��ʕ\���p��ActionMessage��ݒ肷��z
            String key = eConfig.getKey();
            ActionMessage error = null;
            if (resources != null) {
                // �G���[���b�Z�[�W�̒u����������擾����
                String[] options = se.getOptions();

                if (options != null && options.length > 0) {
                    error = new ActionMessage(key, options);
                } else {
                    error = new ActionMessage(key);
                }
            } else {
                // ��ʂł����Ă����b�Z�[�W���\�[�X�������ꍇ�̓G���[�L�[�����b�Z�[�W�ɂ���
                error = new ActionMessage(key, false);
            }
            super.storeException(request,
                    key,
                    error,
                    forward,
                    eConfig.getScope());

            // �ϊ����ꂽ��O���b�Z�[�W�A�X�^�b�N�g���[�X��
            // �Z�b�V�����n�b�V���l�����O�ɏo��
            String sessionHash = RequestUtil.getSessionHash(request);
            logException(logLevel, "sessionHash = " + sessionHash);
            logException(logLevel, ExceptionUtil.getStackTrace(se));

            // �y�u���ς�SystemException��ݒ肷��z
            // �V�X�e����O��JSP�G���[�y�[�W�� exception �Ƃ���
            // �擾�ł���悤�� request �ɐݒ肷��
            request.setAttribute(PageContext.EXCEPTION, se);
        }

        // �V�X�e���G���[���́A�A�N�V�����}�b�s���O�̐ݒ�ɉ����đJ�ڂ���B
        return forward;
    }

    /**
     * ���b�Z�[�W���\�[�X�ɑ΂��ă��b�Z�[�W�L�[���w�肷�邱�Ƃ�
     * �G���[���b�Z�[�W���擾����B
     *
     * @param req HTTP���N�G�X�g
     * @param se SystemException
     * @param resources ���b�Z�[�W���\�[�X
     *
     * @return �G���[���b�Z�[�W
     */
    private String getErrorMessage(HttpServletRequest req,
                                    SystemException se,
            MessageResources resources) {
        String errorCode = se.getErrorCode();
        // ���b�Z�[�W�L�[���w�肳��Ă��Ȃ��Ƃ��Anull��ԋp����B
        if (errorCode == null) {
            return null;
        }

        String[] options = se.getOptions();
        if (options == null) {
            return resources.getMessage(req.getLocale(), errorCode);
        }
        return resources.getMessage(req.getLocale(), errorCode, options);
    }

    /**
     * �n���h���̃��K�[���擾����B
     *
     * @return ���K�[
     */
    @Override
    protected Log getLogger() {
        return log;
    }
}
