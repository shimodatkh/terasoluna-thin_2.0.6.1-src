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

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �P���t�H���[�h�A�N�V�����B
 *
 * <p>
 *  ActionEx�̋@�\�i�J�ڃ��O�o�́E�g�����U�N�V�����g�[�N���`�F�b�N�j���p�����A
 *  JSP�Ȃǂփt�H���[�h����A�N�V�����ł���B
 *  Struts���񋟂��Ă���ForwardAction�Ɠ��l��
 *  struts-config.xml��&lt;action&gt;�v�f��
 *  parameter�����Ɏw�肵����Ƀt�H���[�h����B
 *  parameter�������ݒ肳��Ă��Ȃ��ꍇ�A
 *  �Œ�̘_���t�H���[�h���usuccess�v�ŃA�N�V�����t�H���[�h���擾����B
 *  �t�H���[�h�悪�ݒ肳��Ă��Ȃ��ꍇ�A
 *  SC_NOT_FOUND�i404�j�G���[��Ԃ��B
 *  *.jsp�t�@�C���ւ̒��ڃA�N�Z�X���֎~����Ă���ꍇ�ɁA
 *  JSP���Ɩ��������o���P���ɕ\������ɂ�
 *  ���̃A�N�V������p����struts-config.xml
 *  �ɃG���g�����쐬����K�v������B
 *  ActionEx#execute()�ōs���Ă��鏈���́A
 *  �����ł��p�������B
 * </p>
 * 
 *<br>
 *
 * <p>Bean��`�t�@�C���y��struts-config.xml�̋L�q����ȉ��Ɏ����B</p>
 *
 * ��:<br>
 * <p>
 *  <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *   <legend><strong>Bean��`�t�@�C���̐ݒ�</strong></legend>
 *   <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *  <code><pre>
 *  &lt;bean name="/foo" scope="prototype"
 *      <strong>class="jp.terasoluna.fw.web.struts.actions.ForwardAction"</strong>&gt
 *  &lt;/bean&gt
 *  </pre></code>
 *   </fieldset>
 *  </fieldset>
 * </p>
 * 
 * <p>
 *  <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *   <legend><strong>struts-config.xml�̐ݒ�</strong></legend>
 *   <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *    <code><pre>
 *  &lt;action path="/foo"
 *          parameter="/foo.jsp"&gt;
 *  &lt;/action&gt;
 *    </pre></code>
 *   </fieldset>
 *   �܂���
 *   <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *    <code><pre>
 *  &lt;action path="/foo"
 *          parameter="/foo.jsp"&gt;
 *    &lt;forward name="success" path="/foo.jsp" module="/sub1" redirect="true"&gt;
 *  &lt;/action&gt;
 *    </pre></code>
 *   </fieldset>
 *  </fieldset>
 * </p>
 *
 *<br>
 *
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *  <legend><strong>contextRelative�t�B�[���h</strong></legend>
 * <strong>parameter����</strong>�̃p�X�̎w����@��ύX����B
 * 
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *  <legend><strong>contextRelative�̐ݒ�l�ɂ��p�X�̎w����@�̈Ⴂ</strong></legend>
 *  <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *   <legend><strong>true</strong>�̏ꍇ</legend>
 *   parameter�����ł́A�R���e�L�X�g���[�g����̐�΃p�X�̎w��ƂȂ�B<br>
 *   ��{�I�ɓ��ꃂ�W���[�����̑J�ڂɎg�p����B
 *   <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *    <code><pre>
 *  &lt;action path="/pagelinkForward"
 *          parameter="<strong>/pagelink/</strong>sc2401.jsp"/&gt;
 *    </pre></code>
 *   </fieldset> 
 *  </fieldset> 
 *  <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *   <legend><strong>false</strong>�̏ꍇ</legend>
 *   parameter�����ł́A���W���[�����΃p�X�̎w�肵���s�Ȃ��Ȃ��B<br>
 *  ���W���[�����ׂ�J�ڂ⃊�_�C���N�g���s�Ȃ������ꍇ�́A
 *  �����Ƃ���&lt;forward&gt;�v�f���g�p���邱�ƁB
 *   <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *    <code><pre>
 *  &lt;action path="/pagelinkForward"
 *          parameter="<strong>/</strong>sc2401.jsp"/&gt;
 *    </pre></code>
 *   </fieldset> 
 *  </fieldset> 
 * </fieldset> 
 * 
 * 
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *  <legend><strong>contextRelative�̐ݒ���@</strong></legend>
 *  system.properties�ɐݒ���L�q����B<br>
 *  �ݒ莩�̂��ȗ������ꍇ�� <strong>false</strong> �Ƃ��Ĉ�����B<br>
 *  <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 *   <legend><strong>system.properties</strong></legend>
 *    <code><pre>
 * forwardAction.contextRelative=true
 *    </pre></code>
 *  </fieldset> 
 * </fieldset> 
 * </fieldset> 
 *
 *<br>
 *
 */
public class ForwardAction extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log
              = LogFactory.getLog(ForwardAction.class);

    /**
     * �G���[�y�[�W�i404�j�J�ڎ��s�������G���[�R�[�h�B
     */
    private static final String FORWARD_ERRORPAGE_ERROR =
        "error.forward.errorpage";

    /**
     * �Œ�̘_���t�H���[�h���B
     */
    private static final String FORWARD_SUCCESS = "success";

    /**
     * contextRelative�ݒ�l�̃v���p�e�B�L�[
     */
    private static final String FORWARD_ACTION_CONTEXT_RELATIVE_KEY = "forwardAction.contextRelative";
       
    /**
     * parameter�����ɐݒ肳�ꂽ�J�ڐ��
     * �A�N�V�����t�H���[�h�ɃZ�b�g���ĕԂ��B
     * parameter�������ݒ肳��Ă��Ȃ��ꍇ�A
     * �J�ڐ�_���t�H���[�h��"success"��
     * �A�N�V�����t�H���[�h���擾���ĕԂ��B
     * �ǂ�����ݒ肳��Ă��Ȃ��ꍇ�́A�i404�j�G���[��Ԃ��B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @return �J�ڐ�̃A�N�V�����t�H���[�h
     */
    @SuppressWarnings("deprecation")
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest req,
                                HttpServletResponse res) {
        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        // parameter�����i�t�H���[�h��j���擾
        String path = mapping.getParameter();

        // �A�N�V�����t�H���[�h�𐶐�
        ActionForward retVal = null;

        if (path == null) {

            // ActionMapping����ActionForward���擾
            retVal = mapping.findForward(FORWARD_SUCCESS);

            // ActionFoward���ݒ肳��Ă��Ȃ��ꍇ
            if (retVal == null) {
                // parameter�����Aforward�v�f���Ƃ��ɐݒ肳��Ă��Ȃ��ꍇ�A
                // �i404�j�G���[��ԋp����
                try {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e) {
                    log.error("Error page(404) forwarding failed.");

                    throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
                }
                return null;
            }
            return retVal;
        }

        retVal = new ActionForward(path);

        // contextRelative�ݒ�l���擾
        String contextRelativeStr = PropertyUtil.getProperty(
                FORWARD_ACTION_CONTEXT_RELATIVE_KEY, Boolean.FALSE.toString());
        Boolean contextRelative = new Boolean(contextRelativeStr);

        // ActionForward��contextRelative�̒l��ݒ肷��
        retVal.setContextRelative(contextRelative);
        if (log.isDebugEnabled()) {
            StringBuilder debugLog = new StringBuilder();
            debugLog.append("contextRelative:");
            debugLog.append(contextRelative);
            log.debug(debugLog.toString());
        }

        return retVal;
    }

}
