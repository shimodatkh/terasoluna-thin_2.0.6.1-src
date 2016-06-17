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

import jp.terasoluna.fw.service.thin.BLogic;
import jp.terasoluna.fw.service.thin.BLogicResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p> 
 *  �{�N���X�́ABLogic�̋N�����s���N���X�ł���B<br>
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
 * &lt;bean name="/logon/logonAction" scope="singleton"
 *   <strong>class="jp.terasoluna.fw.web.struts.actions.BLogicAction"</strong>&gt
 *   <strong>&lt;property name="businessLogic"&gt;
 *     &lt;ref bean="LogonBLogic"&gt;&lt;/ref&gt;
 *   &lt;/property&gt;</strong>
 * &lt;/bean&gt
 * &lt;bean id="LogonBLogic" scope="prototype"
 *   <strong>class="jp.terasoluna.sample1.logon.blogic.LogonBLogic"</strong>&gt
 * &lt;/bean&gt
 * </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xml��BLogicAction�ݒ��</strong>
 * <code><pre>
 *    &lt;action path="/logon/logonAction"
 *       name="_logonForm"
 *       validate="true"
 *       scope="session"
 *       input="/logon/logon.jsp"&gt;
 *    &lt;forward name="success" path="/logon/selectGroupSCR.do"/&gt;
 *    &lt;forward name="failure" path="/logon/logonSCR.do"/&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * </p>
 * <p>
 *  �܂��A�t�H�[���A�Z�b�V�����Ȃǂ�Web�w�ƁA
 *  �r�W�l�X���W�b�N�̓��o�͒l�Ƃ̃}�b�s���O��
 *  blogic-io.xml�ɑ΂��āA�A�N�V�����p�X�����ƂɋL�q����K�v������B
 *  �L�q���@�A�ڍא����ɂ��ẮABLogicIOPlugIn���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 *
 * @param <P> �r�W�l�X���W�b�N�ւ̓��͒l�ƂȂ�JavaBean���w�肷��
 *
 */
public class BLogicAction<P> extends AbstractBLogicAction<P> {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(BLogicAction.class);

    /**
     * �ݒ肳���r�W�l�X���W�b�N�B
     */
    private BLogic<P> businessLogic = null;

    /**
     * �r�W�l�X���W�b�N��ݒ肷��B
     *
     * @param businessLogic businessLogic ��ݒ�B
     */
    public void setBusinessLogic(BLogic<P> businessLogic) {
        this.businessLogic = businessLogic;
    }
    
    /**
     * �r�W�l�X���W�b�N���擾����B
     *
     * @return �r�W�l�X���W�b�N
     */
    public BLogic<P> getBusinessLogic() {
        return this.businessLogic;
    }
    
    /**
     * BLogic���N������B
     * <p>
     * �����Ƃ��āA�N���X�ϐ���businessLogic�ɂ�BLogic�C���^�t�F�[�X����������
     * �N���X���ݒ肳��Ă���K�v������B
     * BLogic��null�̂Ƃ��́Anull��ԋp����B
     * </p>
     *
     * @param param BLogic���͏��
     * @return BLogic�o�͏��
     * @throws Exception �\�����Ȃ���O
     */
    @Override
    public BLogicResult doExecuteBLogic(P param) throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("*** doExecuteBLogic() called. ***");
        }

        if (businessLogic == null) {
            return null;
        }

        if (log.isDebugEnabled()) {
            // DI���ꂽ�r�W�l�X���W�b�N�̃N���X�����o��
            log.debug("*** Starting blogic["
                    + businessLogic.getClass().getName() + "]. ***");
        }

        BLogicResult result = businessLogic.execute(param);

        if (log.isDebugEnabled()) {
            log.debug("*** Finished blogic["
                    + businessLogic.getClass().getName() + "]. ***");
        }

        return result;
    }
}
