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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * DispatchActionTest�Ŏg�p����B<br>
 *
 * cancelled()���A"cancelled"�A�N�V�����p�X��ԋp����B<br>
 *
 * @version 2005/01/22
 */
public class DispatchActionImpl01 extends DispatchAction{

    /**
     * ���N�G�X�g�ɃL�����Z���t���O���ݒ肳��Ă���ꍇ�̑J�ڐ��
     * ���肷��B���ӓ_�Ƃ��āAActionForward��null�Ƃ��ĕԋp����邽�߁A
     * �L�����Z�����̑J�ڐ�́A���̃N���X���p�������N���X��
     * �I�[�o���C�h���\�b�h�Ŏ�������K�v������B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �A�N�V�����t�H���[�h
     */
    @Override
    protected ActionForward cancelled(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // "cancelled"�̃A�N�V�����t�H���[�h�̐���
        ActionForward forward = new ActionForward();
        forward.setName("cancelled");
        return forward;

    }

}
