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

package jp.terasoluna.fw.web.struts.reset;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.struts.action.ActionMapping;

/**
 * �A�N�V�����t�H�[���̃t�B�[���h������������C���^�t�F�[�X�B
 *
 * {@link jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx}
 * �A{@link jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx}
 * �𗘗p���Areset���\�b�h�����s�����ƁA�T�[�u���b�g�R���e�L�X�g��
 * "RESETTER"�Ƃ������O�ŕۑ����ꂽ���̃C���^�t�F�[�X�̎����N���X�C���X�^���X
 * ���擾���A{@link #reset(FormEx, ActionMapping, HttpServletRequest)}
 * ���\�b�h�����s����B
 * �A�v���P�[�V�����̗v���ɍ��킹�Ă��̃C���^�t�F�[�X���������邱�ƁB
 * <br>
 * �܂��ATERASOLUNA�t���[�����[�N�ł͂��̃C���^�t�F�[�X�̃f�t�H���g��
 * �����N���X{@link jp.terasoluna.fw.web.struts.reset.ResetterImpl}
 * ��p�ӂ��Ă���B�T�^�I�ȏ����������ł����ResetterImpl���g�p���邱�Ƃ�
 * �ł���B
 *
 * @see jp.terasoluna.fw.web.struts.reset.ResetterImpl
 */
public interface Resetter {

    /**
     * ���̃N���X�̃C���X�^���X���T�[�u���b�g�R���e�L�X�g
     * �ɕۑ�����ۂ̃L�[�B
     */
    public static final String RESETTER_KEY = "RESETTER";

    /**
     * �A�N�V�����t�H�[���̃v���p�e�B�̒l������������B
     *
     * @param form �A�N�V�����t�H�[���C���X�^���X
     * @param mapping �A�N�V�����}�b�s���O�C���X�^���X
     * @param request ���N�G�X�g���
     */
    void reset(FormEx form,
               ActionMapping mapping,
               HttpServletRequest request);

}
