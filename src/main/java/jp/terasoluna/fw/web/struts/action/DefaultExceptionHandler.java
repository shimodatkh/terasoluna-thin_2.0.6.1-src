/*
 * Copyright (c) 2008 NTT DATA Corporation
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.MessageResources;

/**
 * �w�肵�����O���x���Ń��O���o�͂���ėp��O�n���h���B
 *
 * <p>
 * ��O�������̃��O�o�͂ƃG���[��ʂւ̑J�ڂ��s���B<br>
 * </p>
 *
 * <p>
 * �{�@�\�𗘗p���邽�߂ɂ́AStruts�ݒ�t�@�C��(struts-config.xml)��
 * �O���[�o����O�A�܂��̓A�N�V�������x����O�n���h���N���X�Ƃ��Ďw�肷��B<br>
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
 *                type="org.springframework.dao.DataAccessException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler"&gt;
 *       &lt;set-property property="module" value="/exp"/&gt;
 *     &lt;/exception&gt;
 *     &lt;exception key="some.key"
 *                path="/system-error"
 *                type="jp.terasoluna.fw.exception.SystemException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.SystemExceptionHandler"&gt;
 *       &lt;set-property property="module" value="/exp"/&gt;
 *     &lt;/exception&gt;
 *     &lt;exception key="some.key"
 *                path="/system-error"
 *                type="java.lang.Exception"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler"&gt;
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
 *                type="jp.terasoluna.sample.xxx.exception.XxxException"
 *                className="jp.terasoluna.fw.web.struts.action.ExceptionConfigEx"
 *                handler="jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler"
 *                path="/sub-forward.do"&gt;
 *       &lt;set-property property="module" value="/sub"/&gt;
 *     &lt;/exception&gt;
 *     &lt;forward name="success" path="/business-error"/&gt;
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
 * ����������O�C���X�^���X�́Areuqest��<strong>Globals.EXCEPTION_KEY</strong>�̃L�[�Ŋi�[�����B
 * </p>
 *
 * @see org.apache.struts.action.ExceptionHandler
 * @see jp.terasoluna.fw.web.struts.action.ExceptionConfigEx
 * @see jp.terasoluna.fw.web.struts.action.SystemExceptionHandler
 * @see org.apache.struts.Globals
 */
public class DefaultExceptionHandler extends ExceptionHandler {

    /** ���O�C���X�^���X */
    private static final Log logger = LogFactory
            .getLog(DefaultExceptionHandler.class);

    /** ���O���x��(TRACE) */
    protected static final String LOG_LEVEL_TRACE = "trace";

    /** ���O���x��(DEBUG) */
    protected static final String LOG_LEVEL_DEBUG = "debug";

    /** ���O���x��(INFO) */
    protected static final String LOG_LEVEL_INFO = "info";

    /** ���O���x��(WARN) */
    protected static final String LOG_LEVEL_WARN = "warn";

    /** ���O���x��(ERROR) */
    protected static final String LOG_LEVEL_ERROR = "error";

    /** ���O���x��(FATAL) */
    protected static final String LOG_LEVEL_FATAL = "fatal";

    /** ���b�Z�[�W���\�[�X */
    private static MessageResources messages = MessageResources
            .getMessageResources("org.apache.struts.action.LocalStrings");

    /**
     * <p>
     * ��O�n���h�����O���s���B
     * </p>
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
     * @see org.apache.struts.action.ExceptionHandler#execute(
     *  java.lang.Exception,
     *  org.apache.struts.config.ExceptionConfig,
     *  org.apache.struts.action.ActionMapping,
     *  org.apache.struts.action.ActionForm,
     *  javax.servlet.http.HttpServletRequest,
     *  javax.servlet.http.HttpServletResponse
     *  )
     */
    @Override
    public ActionForward execute(Exception ex, ExceptionConfig eConfig,
            ActionMapping mapping, ActionForm formInstance,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        // �X�[�p�[�N���X�Ăяo��
        ActionForward af = super.execute(ex, eConfig, mapping, formInstance,
                request, response);

        // ExceptionConfigEx�̏ꍇ��logLevel���擾
        if (eConfig instanceof ExceptionConfigEx) {
            // �J�ڐ惂�W���[�����ݒ肳��Ă���Ƃ��A���W���[������ݒ肷��
            af.setModule(((ExceptionConfigEx) eConfig).getModule());

            // logLevel�𗘗p���ă��O�o��
            this.logException(ex, ((ExceptionConfigEx) eConfig).getLogLevel());
        } else {
            // �f�t�H���g���x���Ń��O�o��
            this.logException(ex, null);
        }

        return af;
    }

    /**
     * ��O�̃��O���o�͂���B
     *
     * @param e ����������O
     * @param logLevel ���O���x��
     */
    protected void logException(Exception e, String logLevel) {
        this.logException(e, logLevel, messages.getMessage("exception.log"));
    }

    /**
     * ��O�̃��O���o�͂���B
     *
     * @param logLevel ���O���x��
     * @param message �o�͂��郁�b�Z�[�W
     */
    protected void logException(String logLevel, String message) {
        this.logException(null, logLevel, message);
    }

    /**
     * ��O�̃��O���o�͂���B
     *
     * @param e ����������O
     * @param logLevel ���O���x��
     * @param message �o�͂��郁�b�Z�[�W
     */
    protected void logException(Exception e, String logLevel, String message) {
        if (LOG_LEVEL_TRACE.equalsIgnoreCase(logLevel)) {
            if (getLogger().isTraceEnabled()) {
                // TRACE
                if (e != null) {
                    getLogger().trace(message, e);
                } else {
                    getLogger().trace(message);
                }
            }
        } else if (LOG_LEVEL_DEBUG.equalsIgnoreCase(logLevel)) {
            if (getLogger().isDebugEnabled()) {
                // DEBUG
                if (e != null) {
                    getLogger().debug(message, e);
                } else {
                    getLogger().debug(message);
                }
            }
        } else if (LOG_LEVEL_INFO.equalsIgnoreCase(logLevel)) {
            if (getLogger().isInfoEnabled()) {
                // INFO
                if (e != null) {
                    getLogger().info(message, e);
                } else {
                    getLogger().info(message);
                }
            }
        } else if (LOG_LEVEL_WARN.equalsIgnoreCase(logLevel)) {
            if (getLogger().isWarnEnabled()) {
                // WARN
                if (e != null) {
                    getLogger().warn(message, e);
                } else {
                    getLogger().warn(message);
                }
            }
        } else if (LOG_LEVEL_ERROR.equalsIgnoreCase(logLevel)) {
            if (getLogger().isErrorEnabled()) {
                // ERROR
                if (e != null) {
                    getLogger().error(message, e);
                } else {
                    getLogger().error(message);
                }
            }
        } else if (LOG_LEVEL_FATAL.equalsIgnoreCase(logLevel)) {
            if (getLogger().isFatalEnabled()) {
                // FATAL
                if (e != null) {
                    getLogger().fatal(message, e);
                } else {
                    getLogger().fatal(message);
                }
            }
        } else {
            if (getLogger().isErrorEnabled()) {
                // ERROR�i�f�t�H���g�j
                if (e != null) {
                    getLogger().error(message, e);
                } else {
                    getLogger().error(message);
                }
            }
        }
    }

    /**
     * ��O�̃��O���o�͂���B
     *
     * <p>
     * <strong><u>�������ł͏o�͂��Ȃ�</u></strong>
     * </p>
     *
     * @param e ����������O
     * @see org.apache.struts.action.ExceptionHandler#logException(
     *  java.lang.Exception)
     */
    @Override
    protected void logException(Exception e) {
        // �����ł͏o�͂��Ȃ�
    }

    /**
     * �n���h���̃��K�[���擾����B
     *
     * <p>
     * ���̃N���X���g�����ēƎ��̗�O�n���h������������ꍇ�A
     * �T�u�N���X�ł��̃��\�b�h���I�[�o�[���C�h���Ď��g�̃��K�[��Ԃ����ƁB
     * </p>
     *
     * @return ���K�[
     */
    protected Log getLogger() {
        return logger;
    }

}
