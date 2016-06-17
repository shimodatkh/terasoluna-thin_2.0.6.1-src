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

import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  �{�N���X�́A�_�E�����[�h�������s���ꍇ��BLogic�̋N�����s���N���X�ł���B<br>
 *  Action���璼��BLogic�̋N�����s���BEJB�ɂ͑Ή����Ă��Ȃ��B
 * </p>
 * <p>
 *  BLogic�����N���X�����s���邽�߂ɂ́A
 *  Bean��`�t�@�C����BLogicAction��Bean��`�ŁA
 *  businessLogic�v���p�e�B�ɖړI�̋Ɩ����W�b�N�����N���X����
 *  ���L�̗�̂悤�ɁA&lt;property&gt;�v�f�Ŏw�肷��B
 *  ���킹��struts-config.xml�̐ݒ����ȉ��Ɏ����B
 * </p>
 * <p>
 * <strong>Bean��`�t�@�C���̐ݒ�</strong>
 * <code><pre>
 * &lt;bean name="/download/downloadAction" scope="prototype"
 *   <strong>class="jp.terasoluna.fw.web.struts.actions.DownloadBLogicAction"</strong>&gt
 *   <strong>&lt;property name="businessLogic"&gt;
 *     &lt;ref bean="downloadBLogic"&gt;&lt;/ref&gt;
 *   &lt;/property&gt;</strong>
 * &lt;/bean&gt
 * &lt;bean id="downloadBLogic" scope="prototype"
 *   <strong>class="jp.terasoluna.sample1.download.blogic.DownloadBLogic"</strong>&gt
 * &lt;/bean&gt
 * </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xml��BLogicAction�ݒ��</strong>
 * <code><pre>
 *    &lt;action path="/download/downloadAction"
 *       name="_downloadForm"
 *       validate="true"
 *       scope="session"
 *       input="/download/download.jsp"/&gt;
 * </pre></code>
 * </p>
 *
 * @param <P> �r�W�l�X���W�b�N�ւ̓��͒l�ƂȂ�JavaBean���w�肷��
 */
public class DownloadBLogicAction<P> extends BLogicAction<P> {

    /**
     * ���O�N���X�B
     */
    Log log = LogFactory.getLog(DownloadBLogicAction.class);

    /**
     * BLogicResult����Web�w�̃I�u�W�F�N�g�ւ̌��ʔ��f���s���B
     * <p>
     * ���̃N���X�ł�<code>resultObject</code>���ȉ��̏ꍇ�A
     * �_�E�����[�h�������s���B
     * <ul>
     * <li>{@link AbstractDownloadObject}�p���N���X�ł���ꍇ</li>
     * <li>{@link AbstractDownloadObject}�p���N���X���v���p�e�B�Ƃ��ĂP���ꍇ</li>
     * </ul>
     *
     * @param result BLogicResult�C���X�^���X
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param mappingEx �g���A�N�V�����}�b�s���O
     */
    @Override
    protected void processBLogicResult(BLogicResult result,
            HttpServletRequest request, HttpServletResponse response,
            ActionMappingEx mappingEx) {
        super.processBLogicResult(result, request, response, mappingEx);
        if (result.getResultString() != null) {
        	if (log.isWarnEnabled()) {
        		log.warn("result string must not be set. path :" + request.getPathInfo());
        	}
        	result.setResultString(null);
        }

        FileDownloadUtil.download(result.getResultObject(), request, response);
    }
}
