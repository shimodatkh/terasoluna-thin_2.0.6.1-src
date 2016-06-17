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

package jp.terasoluna.fw.service.thin;

import java.io.Serializable;

/**
 * �r�W�l�X���W�b�N�o�͏��N���X�B
 *
 * <p>
 *  �t�H�[���A�Z�b�V�����Ƃ�����Web�w�ւ̃f�[�^���f���s���B
 *  ���f�������g��BLogicMapper���s�����߃R�[�f�B���O�͕s�v�����A
 *  �A�N�V�������ƂɁAblogic-io.xml�ɔ��f����f�[�^�̓��e�A
 *  Web�w�ւ̔��f����w�肷��K�v������B
 *  ���̐ݒ�ɂ��ẮA
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  ���Q�Ƃ̂��ƁB<br>
 *  ���̃N���X�Őݒ肷�ׂ����́A���L�̂��̂�����B
 * <ul>
 *   <li>�r�W�l�X���W�b�N���Ŏ擾����AWeb�w�ɓn���ׂ������A
 *   JavaBean��BLogicResult���ɐݒ肷��</li>
 *   <li>�r�W�l�X���W�b�N���ŋƖ��G���[�����������ꍇ�A
 *   BLogicMessages��ݒ肷��B</li>
 *   <li>�r�W�l�X���W�b�N�̎��s���ʂ�\���������ݒ肷��B
 *   �����struts-config.xml�Őݒ肳���
 *   �_���t�H���[�h���i&lt;forward&gt;�v�f�Ŏw��j�ɑ�������</li>
 * </ul>
 *
 *  �Ȃ��AMVC��Struts���g�p����ꍇ�A�ȍ~�̏�����BLogicMessages�̓��e��
 *  ActionMessages�ɒu����������B<br>
 *
 *  ���L�́ABLogic�����N���X������ԋp�����A
 *  BLogicResult�̎g�p��ł���B
 * </p>
 *
 * <h5>BLogicResult�̎g�p��iBLogic#execute()�������j
 * </h5>
 * <p>
 * <code><pre>
 * public BLogicResult execute(ParamsBean params) {
 *
 *     // BLogic����new���s���ABLogicResult�𐶐�����B
 *     BLogicResult result = new BLogicResult();
 *     �E�E�E
 *     //�r�W�l�X���W�b�N
 *     �E�E�E
 *     //�G���[����
 *     if (// �G���[���菈�� ) {
 *         // �t�H�[���A�Z�b�V�����ɔ��f���ׂ�����ݒ肷��B
 *         ResultBean bean = new ResultBean();
 *         bean.setUserId(userId);
 *         result.setResultObject(bean);
 *         // ���s���ʂ�"success"���w��
 *         result.setResultString("success");
 *         return result;
 *     } else {
 *         // �r�W�l�X���W�b�N���̃G���[������
 *         // �G���[�pBLogicMessages��BLogicResult�ɐݒ�
 *         result.setErrors(errorMessages);
 *         // ���s���ʂ�"failure"���w��
 *         result.setResultString("failure");
 *         return result;
 *     }
 * }
 * </pre></code>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicResult implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -1861617801019993048L;

    /**
     * �r�W�l�X���W�b�N�̎��s���ʂ��i�[����JavaBean�B
     */
    private Object resultObject = null;

    /**
     * �r�W�l�X���W�b�N�̎��s���ʂ�\��������B
     */
    private String resultString = null;

    /**
     * �r�W�l�X���W�b�N���Ő��������G���[�pBLogicMessages�B
     */
    private BLogicMessages errors = null;

    /**
     * �r�W�l�X���W�b�N���Ő�������郁�b�Z�[�W�pBLogicMessages�B
     */
    private BLogicMessages messages = null;
    
    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages���擾����B
     *
     * @return �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages
     */
    public BLogicMessages getErrors() {
        return this.errors;
    }

    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages���擾����B
     *
     * @return �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages
     */
    public BLogicMessages getMessages() {
        return this.messages;
    }

    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages��ݒ肷��B
     *
     * @param paramErrors
     *            �r�W�l�X���W�b�N���Ő������ꂽ�A�G���[�pBLogicMessages
     */
    public void setErrors(BLogicMessages paramErrors) {
        this.errors = paramErrors;
    }

    /**
     * �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages��ݒ肷��B
     *
     * @param paramMessages
     *            �r�W�l�X���W�b�N���Ő������ꂽ�A���b�Z�[�W�pBLogicMessages
     */
    public void setMessages(BLogicMessages paramMessages) {
        this.messages = paramMessages;
    }

    /**
     * �r�W�l�X���W�b�N�̎��s���ʂ�\����������擾����B
     *
     * @return �r�W�l�X���W�b�N�̎��s���ʂ�\��������
     */
    public String getResultString() {
        return this.resultString;
    }

    /**
     * �r�W�l�X���W�b�N�̎��s���ʂ�\���������ݒ肷��B
     *
     * @param resultString �r�W�l�X���W�b�N�̎��s���ʂ�\��������
     */
    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    /**
     * �r�W�l�X���W�b�N�̎��s���ʂ��i�[����JavaBean���擾����B
     *
     * @return �r�W�l�X���W�b�N�̎��s���ʂ��i�[����JavaBean
     */
    public Object getResultObject() {
        return resultObject;
    }

    /**
     * �r�W�l�X���W�b�N�̎��s���ʂ��i�[����JavaBean��ݒ肷��B
     *
     * @param resultObject �r�W�l�X���W�b�N�̎��s���ʂ��i�[����JavaBean
     */
    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }
    
}
