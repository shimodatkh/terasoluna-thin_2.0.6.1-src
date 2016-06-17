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

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicMessage;
import jp.terasoluna.fw.service.thin.BLogicMessages;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.ModuleUtil;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


/**
 * �r�W�l�X���W�b�N�N�����ۃN���X�B
 *
 * <p>
 *  �r�W�l�X���W�b�N�̋N�����s���A�N�V�����N���X�ɋ��ʂ���@�\��
 *  �W�񂵂����ۃN���X�ł���B�S�Ẵr�W�l�X���W�b�N�N���A�N�V�����N���X��
 *  ���̃N���X���p�����Ď�������B
 *  BLogicAction�����̃N���X���p�����Ă���B
 *  �񋟂���@�\�ꗗ�͉��L�̂Ƃ���ł���B
 * <ol>
 *  <li>struts-config.xml�̃A�N�V�����ݒ肩��
 *   �r�W�l�X���W�b�N�N���X�����擾����B</li>
 *  <li>�r�W�l�X���W�b�N�̓��͏��ƂȂ� JavaBean �𐶐�����B</li>
 *  <li>�p����N���X�̃G���g���|�C���g�ƂȂ�doExecuteBLogic()�����s����B</li>
 *  <li>�r�W�l�X���W�b�N������I�������ꍇ�ABLogicResult�̌��ʂ���������B</li>
 * </ol>
 *
 * <p>
 *  �r�W�l�X���W�b�N�N���A�N�V�����N���X�́A
 *  Bean��`�t�@�C���ɂċN������r�W�l�X���W�b�N��ݒ肵����ŁA
 *  ���s����B
 *  ���L�̗�́A�r�W�l�X���W�b�N�ł���SampleBLogic��SampleAction����
 *  �N�����邽�߂̐ݒ�ł���B
 *  ���킹��struts-config.xml�̐ݒ����ȉ��Ɏ����B
 * </p>
 * <p>
 * <strong>Bean��`�t�@�C���̐ݒ�</strong>
 * <code><pre>
 * &lt;bean name="/SampleAction" scope="prototype"
 *   <b>class="jp.terasoluna.sample1.actions.SampleAction"</b>&gt;
 *   <b>&lt;property name="sampleBLogic"&gt;
 *     &lt;ref bean="SampleBLogic"&gt;&lt;/ref&gt;
 *   &lt;/property&gt;</b>
 * &lt;/bean&gt
 * &lt;bean id="SampleBLogic"
 *   <b>class="jp.terasoluna.sample1.blogic.SampleBLogic"</b>&gt;
 * &lt;/bean&gt
 * </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xml�̐ݒ�</strong>
 * <code><pre>
 *  &lt;action path="/SampleAction"
 *    name="_sampleForm"
 *    validate="true"
 *    scope="session"
 *    input="/sample.jsp"&gt;
 *    &lt;forward name="success" path="/sampleSCR.do"/&gt;
 *    &lt;forward name="failure" path="/errorSCR.do"/&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * </p>
 * BLogicResult�̗��p�A�ݒ�ɂ��ẮA
 * BLogicIOPlugIn�ABLogicResult�AAbstractBLogicMapper���Q�Ƃ̂��ƁB
 * </p>
 *
 * <p>
 *  �܂��A�r�W�l�X���W�b�N�̎��s�Ɏ��s�����ꍇ�ȂǂŁA
 *  ���b�Z�[�W��ݒ肵�����ۂ́A���̂悤��BLogicResult�Ƀ��b�Z�[�W���i�[����B
 * </p>
 * <p>
 * <code><pre>
 * public BLogicResult doExecuteBLogic(ParamsBean params) {
 *
 *     // BLogicResult�𐶐�����B
 *     BLogicResult result = new BLogicResult();
 *     �E�E�E
 *     //�r�W�l�X���W�b�N
 *     �E�E�E
 *     //�G���[����
 *     if (// �G���[���菈�� ) {
 *         // Web�w�ɂɔ��f���ׂ�����ݒ肷��B
 *         �E�E�E
 *         return result;
 *     } else {
 *         // �r�W�l�X���W�b�N���̃G���[������
 *         // BLogicMessages�𐶐�
 *         BLogicMessages messages = new BLogicMessages();
 *         // GROUP_ERROR�O���[�v�̃��b�Z�[�W�Ƃ��āABLogicMessage���i�[
 *         messages.add("GROUP_ERROR", new BLogicMessage("message.error.sample", "sample"));
 *         // �G���[�pBLogicMessages��BLogicResult�ɐݒ�
 *         result.setErrors(messages);
 *         // ���s���ʂ�"failure"���w��
 *         result.setResultString("failure");
 *         return result;
 *     }
 * }
 * </pre></code>
 * </p>
 * <p>
 * ���L�̂悤�ɁA&lt;property&gt;�v�f��"saveMessageScope"�ɁA
 * �r�W�l�X���W�b�N���Ő�������BLogicMessages�̕ۑ����
 * "request"�܂���"session"�̂����ꂩ�Ŏw�肷�邱�Ƃ��ł���B
 * �v���p�e�B��`���ȗ������ꍇ��"request"�ɂȂ�B
 * 
 * <code><pre>
 * &lt;bean name="/SampleAction" scope="prototype"
 *   class="jp.terasoluna.sample1.actions.SampleAction"&gt;
 *   &lt;property name="sampleBLogic"&gt;
 *     &lt;ref bean="SampleBLogic"&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 *   <b>&lt;property name="saveMessageScope" value="session"/&gt;</b>
 * &lt;/bean&gt
 * </pre></code>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMessage
 * @see jp.terasoluna.fw.service.thin.BLogicMessages
 *
 * @param <P> �r�W�l�X���W�b�N�ւ̓��͒l�ƂȂ�JavaBean���w�肷��
 *
 */
public abstract class AbstractBLogicAction<P> extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(AbstractBLogicAction.class);

    /**
     * �g���A�N�V�����t�H�[���ݒ�G���[���̃G���[�R�[�h�B
     */
    protected static final String BLOGIC_FORM_ILLEGAL_ERROR =
                                                         "errors.blogic.form";

    /**
     * �g���A�N�V�����}�b�s���O�ݒ�G���[���̃G���[�R�[�h�B
     */
    protected static final String BLOGIC_MAPPING_ILLEGAL_ERROR =
        "errors.blogic.mapping";

    /**
     * �g���A�N�V�������\�[�X�ݒ�G���[���̃G���[�R�[�h�B
     */
    protected static final String BLOGIC_RESOURCES_ILLEGAL_ERROR =
        "errors.blogic.resources";

    /**
     * BLogicResult��null�ŕԋp���ꂽ���̃G���[�R�[�h�B
     */
    protected static final String BLOGIC_RESULT_NULL_ERROR =
        "errors.blogic.result.null";

    /**
     * AbstractBLogicMapper��null�������ꍇ�̃G���[�R�[�h�B
     */
    protected static final String NULL_MAPPER_KEY =
        "errors.blogic.mapper.null";

    /**
     * ���b�Z�[�W�ۑ���X�R�[�v�B
     *
     * �r�W�l�X���W�b�N���Ő�������BLogicMessages�̕ۑ����
     * request�܂���session�̂����ꂩ�Ŏw�肷��B
     */
    private String saveMessageScope = null;

    /**
     * ���b�Z�[�W�ۑ���X�R�[�v��ݒ肷��B
     *
     * @param saveMessageScope ���b�Z�[�W�ۑ���X�R�[�v
     */
    public void setSaveMessageScope(String saveMessageScope) {
        this.saveMessageScope = saveMessageScope;
    }

    /**
     * �r�W�l�X���W�b�N�����s����B
     * <p>
     *  �r�W�l�X���W�b�N�̎��s�ɕK�v�ȉ��L�̋��ʏ������s���B<br>
     *  <ul>
     *   <li>�r�W�l�X���W�b�N�̓��͏��ł���JavaBean�̐���</li>
     *   <li>�r�W�l�X���W�b�N�̏o�͏��ł���BLogicResult�̉��</li>
     *   <li>�t�H���[�h��̌���</li>
     *  </ul>
     * </p>
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form    �t�H�[��
     * @param request ���N�G�X�g
     * @param response ���X�|���X
     *
     * @return �J�ڐ�A�N�V�����t�H���[�h
     * @throws Exception �T�u�N���X����X���[���ꂽ�A�\�����Ȃ���O
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                         ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("*** doExecute() called. action path=["
                + mapping.getPath() + "] ***");
        }

        ActionMappingEx mappingEx = null;
        try {
            mappingEx = (ActionMappingEx) mapping;
        } catch (ClassCastException e) {
            log.error("Illegal ActionMapping.");
            throw new SystemException(e, BLOGIC_MAPPING_ILLEGAL_ERROR);
        }

        P params = getBLogicParams(mappingEx, request, response);

        if (log.isDebugEnabled()) {
            log.debug("*** BLogicParams is prepared. ***");
            if (params != null) {
                // params�̐ݒ�����_���v����B
                log.debug("BLogicParams:" + params.toString());
            } else {
                // params��null�̏ꍇ
                log.debug("BLogicParams:null");
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("*** starting blogic action["
                 + this.getClass().getName() + "] ***");
        }

        BLogicResult result = null;

        // �O����
        preDoExecuteBLogic(request, response, params);

        // �r�W�l�X���W�b�N���N��
        result = doExecuteBLogic(params);

        // �㏈��
        postDoExecuteBLogic(request, response, params, result);

        if (log.isDebugEnabled()) {
            log.debug("*** finished blogic action["
                 + this.getClass().getName() + "] ***");
        }

        if (result != null) {
            // BLogicResult��null�ŕԋp����Ȃ������ꍇ�̂�
            // �]�����s���B
            evaluateBLogicResult(
                result, request, response, mappingEx);
            // ActionForward�擾
            return mapping.findForward(result.getResultString());
        }
        log.error("BLogicResult is null.");
        // null�ŕԋp���ꂽ�ꍇ��SystemException���X���[�B
        throw new SystemException(new NullPointerException(),
            BLOGIC_RESULT_NULL_ERROR);
    }

    /**
     * �r�W�l�X���W�b�N�̎��s�O�����B
     *
     * @param request ���N�G�X�g
     * @param response ���X�|���X
     * @param params �p�����[�^�iJavaBean�j
     * @throws Exception �\�����Ȃ���O
     */
    protected void preDoExecuteBLogic(HttpServletRequest request,
            HttpServletResponse response, P params) throws Exception {

    }

    /**
     * �r�W�l�X���W�b�N�̎��s�㏈���B
     *
     * <p>�r�W�l�X���W�b�N�ŗ�O���������Ȃ������ꍇ�̂݁A���s�����B</p>
     *
     * @param request ���N�G�X�g
     * @param response ���X�|���X
     * @param params �p�����[�^�iJavaBean�j
     * @param result �r�W�l�X���W�b�N���s����
     * @throws Exception �\�����Ȃ���O
     */
    protected void postDoExecuteBLogic(HttpServletRequest request,
            HttpServletResponse response, P params, BLogicResult result)
            throws Exception {

    }

    /**
     * BLogicResult�̕]���AWeb�w�̃I�u�W�F�N�g�ւ̌��ʔ��f���s���B
     *
     * @param result BLogicResult�C���X�^���X
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param mappingEx �g���A�N�V�����}�b�s���O
     */
    protected void evaluateBLogicResult(
            BLogicResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            ActionMappingEx mappingEx) {

        // BLogicResult��null�`�F�b�N
        if (result == null) {
            log.error("BLogicResult is null.");
            throw new SystemException(new NullPointerException(),
                    BLOGIC_RESULT_NULL_ERROR);
        }

        // BLogicResult�̐ݒ�����_���v����B
        if (log.isDebugEnabled()) {
            log.debug("*** BLogicResult is prepared. ***");
            log.debug("BLogicResult:" + result.toString());
        }

        ActionMessages errors = convertMessages(result.getErrors());
        ActionMessages messages = convertMessages(result.getMessages());

        // �A�N�V�����}�b�s���O�Ɏw�肳�ꂽ�G���[�E���b�Z�[�W����
        // �ۑ�����擾����B
        if ("session".equalsIgnoreCase(saveMessageScope)) {
            // �ۑ��悪�Z�b�V�����Ŏw�肳��Ă���ꍇ�A
            // �Z�b�V�����ɑ΂��ăG���[�E���b�Z�[�W����ǉ�����B

            // �Z�b�V�����̎擾
            HttpSession session = request.getSession(true);

            addErrors(session, errors);
            addMessages(session, messages);
        } else {
            // ����ȊO�̏ꍇ�A���N�G�X�g�ɒǉ�����B
            addErrors(request, errors);
            addMessages(request, messages);
        }

        if (isErrorsEmpty(result)) {
            processBLogicResult(result, request, response, mappingEx);
        }
    }

    /**
     * BLogicResult����Web�w�̃I�u�W�F�N�g�ւ̌��ʔ��f���s���B
     *
     * @param result BLogicResult�C���X�^���X
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param mappingEx �g���A�N�V�����}�b�s���O
     */
    protected void processBLogicResult(BLogicResult result,
            HttpServletRequest request, HttpServletResponse response,
            ActionMappingEx mappingEx) {
        // �G���[��񂪋�ł���Ƃ��AWeb�w�Ɍ��ʂ𔽉f����
        if (log.isDebugEnabled()) {
            log.debug(
                "*** setting result into web layer. ***");
        }
        getBLogicMapper(request).mapBLogicResult(
            request, response, getBLogicIO(mappingEx, request), result);
    }

    /**
     * BLogicMessages�̓��e��ActionMessages�Ɋi�[���Ȃ����B
     *
     * @param blogicMessages BLogicMessages�C���X�^���X
     * @return ActionMessages
     */
    @SuppressWarnings("unchecked")
    protected ActionMessages convertMessages(BLogicMessages blogicMessages) {

        if (blogicMessages == null) {
            // blogicMessages��null�̏ꍇ�Anull��ԋp����
            return null;
        }

        ActionMessages messages = new ActionMessages();
        // BLogicMessages�擾�p�C�e���[�^
        Iterator itr = blogicMessages.get();
        // ���b�Z�[�W�O���[�v���擾�p�C�e���[�^
        Iterator groupItr = blogicMessages.getGroup();
        while (itr.hasNext()) {

            // BLogicMessage���擾
            BLogicMessage blogicMessage = (BLogicMessage) itr.next();

            // ActionMessage���쐬
            ActionMessage actionMessage = null;
            if (blogicMessage.isResource()) {
                actionMessage = new ActionMessage(blogicMessage.getKey(),
                        blogicMessage.getValues());
            } else {
                actionMessage = new ActionMessage(blogicMessage.getKey(),
                        blogicMessage.isResource());
            }

            // ���b�Z�[�W�O���[�v�����擾
            String group = (String) groupItr.next();

            // ���b�Z�[�W�����i�[
            messages.add(group, actionMessage);
        }
        return messages;
    }

    /**
     * �r�W�l�X���W�b�N���s���ۃ��\�b�h�B
     * �T�u�N���X�Ŏ�������B
     *
     * @param param �r�W�l�X���W�b�N���͏��
     * @return �r�W�l�X���W�b�N�o�͏��
     * @throws Exception �\�����Ȃ���O
     */
    public abstract BLogicResult doExecuteBLogic(P param) throws Exception;

    /**
     * BLogicResult�Ɋi�[����Ă���G���[���
     * null�ł��邩��ł���Ƃ��Atrue��ԋp����B
     *
     * @param result �r�W�l�X���W�b�N���s����
     * @return �G���[�pBLogicMessages��null�܂��́A��ł���Ƃ�true
     */
    protected boolean isErrorsEmpty(BLogicResult result) {
        // BLogicResult null�`�F�b�N
        if (result == null) {
            log.error("BLogicResult is null.");
            throw new SystemException(new NullPointerException(),
                    BLOGIC_RESULT_NULL_ERROR);
        }

        BLogicMessages errors = result.getErrors();
        if (errors == null) {
            return true;
        }
        return errors.isEmpty();
    }

    /**
     * BLogicMapper�C���X�^���X���擾����B
     *
     * @param req HTTP���N�G�X�g
     * @return BLogicMapper�C���X�^���X
     */
    protected AbstractBLogicMapper getBLogicMapper(HttpServletRequest req) {
        String moduleName = ModuleUtil.getPrefix(req);

        AbstractBLogicMapper mapper = null;
        try {
            mapper = (AbstractBLogicMapper) servlet.getServletContext()
            .getAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + moduleName);
        } catch (ClassCastException e) {
            // AbstractBLogicMapper�C���X�^���X�łȂ��ꍇ�A��O���N�����B
            log.error("Cannot cast BLogicMapper : " + e.getMessage());
            throw new SystemException(e, BLOGIC_MAPPING_ILLEGAL_ERROR);
        }

        // BLogicMapper��null�̏ꍇ�A��O����
        if (mapper == null) {
            log.error("BLogicMapper is null.");
            throw new SystemException(new NullPointerException(),
                    NULL_MAPPER_KEY);
        }

        return mapper;
    }

    /**
     * params�𐶐����A�ԋp����B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �r�W�l�X���W�b�N���͏��
     * @throws Exception �\�����Ȃ���O
     */
    @SuppressWarnings("unchecked")
    protected P getBLogicParams(ActionMappingEx mapping,
                                          HttpServletRequest request,
                                          HttpServletResponse response)
            throws Exception {

        BLogicIO io = getBLogicIO(mapping, request);

        // �t�H�[���A�Z�b�V��������̐ݒ�l�擾
        P bean = (P) getBLogicMapper(request).mapBLogicParams(
                request, response, io);

        return bean;
    }

    /**
     * BLogicIO���擾����B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param request HTTP���N�G�X�g
     * @return �r�W�l�X���W�b�N���o�͏��
     */
    protected BLogicIO getBLogicIO(ActionMapping mapping,
            HttpServletRequest request) {

        String moduleName = ModuleUtil.getPrefix(request);

        BLogicResources resource = null;
        try {
            resource =
                (BLogicResources) servlet.getServletContext().getAttribute(
                BLogicResources.BLOGIC_RESOURCES_KEY + moduleName);
        } catch (ClassCastException e) {
            // BLogicResources�C���X�^���X�łȂ��ꍇ�A��O���N�����B
            log.error("Cannot cast BLogicResources : " + e.getMessage());
            throw new SystemException(e, BLOGIC_RESOURCES_ILLEGAL_ERROR);
        }

        if (resource != null) {
            return resource.getBLogicIO(mapping.getPath());
        }
        return null;
    }
}