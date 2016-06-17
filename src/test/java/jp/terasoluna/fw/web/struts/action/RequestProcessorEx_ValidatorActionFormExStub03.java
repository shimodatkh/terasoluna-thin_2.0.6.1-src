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

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * RequestProcessorExTest�N���X�ŗ��p����B
 * 
 * @version 2004/03/18
 */
public class RequestProcessorEx_ValidatorActionFormExStub03
        extends ValidatorActionFormEx {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 8739212369844208791L;

    /**
     * ���ID
     */
    private String companyId = null;

    /**
     * ��Ж�
     */
    private String companyName = null;

    /**
     * ���[�UID
     */
    private String userId = null;

    /**
     * ���[�U��
     */
    private String userName = null;

    /**
     * ���ID��ݒ肷��B
     * 
     * @param companyId
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * ���ID���擾����B
     * 
     * @return ���ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * ��Ж���ݒ肷��B
     * 
     * @param companyName ��Ж�
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * ��Ж����擾����B
     * 
     * @return ��Ж�
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * ���[�UID��ݒ肷��B
     * 
     * @param userId ���[�UID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * ���[�UID���擾����B
     * 
     * @return ���[�UID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ���[�U����ݒ肷��B
     * 
     * @param userName ���[�U��
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * ���[�U�����擾����B
     * 
     * @return ���[�U��
     */
    public String getUserName() {
        return userName;
    }

    /**
     * ���̃t�H�[���̃t�B�[���h�����؂���B
     * 
     * @return �A�N�V�����G���[
     */
    @Override
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request){
        // ���ActionErrors��ԋp
        return new ActionErrors();
    }
}
