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

package jp.terasoluna.fw.web.struts.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;

/**
 * <p>
 *  <code>ifNotErrors</code> �^�O�̎����N���X�B
 * </p>
 * <p>
 *  ���̓`�F�b�N�G���[���Ȃ��A���o�̓p�����[�^�ɃG���[��񂪐ݒ肳���
 *  ���Ȃ��ꍇ�ɁA�^�O�̃{�f�B�������o�͂���
 * </p>
 * <p>
 *  <code>Struts</code> �̕W���I�ȏ������@�ł́A�G���[������ꍇ�ɂ�
 *  ���N�G�X�g�����܂��̓Z�b�V���������ɃG���[��񃊃X�g���ݒ肳���B {@link IfNotErrorsTag}
 *  �^�O�ł́A ���N�G�X�g�����܂��̓Z�b�V�����������`�F�b�N���A�G���[��񃊃X�g��
 *  �ݒ肳��Ă��Ȃ��ꍇ�ɁA�{�f�B�������o�͂���B
 *  ���N�G�X�g�����܂��̓Z�b�V���������ɃG���[��񃊃X�g���ݒ肳��Ă���ꍇ�ɂ́A�P�ɖ��������B
 * </p>
 * <p>
 *  {@link IfNotErrorsTag} �^�O�Ƃ͋t�ɁA�G���[������Ƃ��Ƀ{�f�B������
 *  �o�͂������ꍇ�ɂ́A {@link IfErrorsTag} �^�O��p���邱�ƁB
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>���̃^�O�ɂ���Đݒ肳��鑮���͂���܂���B</p>
 *
 * <br>
 *
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <p><code><pre>
 * &lt;ts:ifNotErrors &gt;
 *   ... // �G���[���Ȃ��ꍇ�̕\�����ړ�
 * &lt;/ts:ifNotErrors&gt;
 * </pre></code></p>
 *
 */
public class IfNotErrorsTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 196326353099851849L;

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B���N�G�X�g�ɂ܂��̓Z�b�V������
     * �G���[��񃊃X�g���ݒ肳��Ă��Ȃ��Ƃ��ɂ̓{�f�B�������o�͂��A
     * �ݒ肳��Ă���Ƃ��ɂ̓{�f�B�������X�L�b�v����B
     *
     * @return ��������w��
     * @throws JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {

        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = req.getSession(true);
        
        if (req.getAttribute(Globals.ERROR_KEY) == null &&
        	session.getAttribute(Globals.ERROR_KEY) == null) {
            // ���N�G�X�g���Z�b�V�����ɃG���[��񂪊܂܂�Ȃ��Ƃ��̓{�f�B�]��
            return EVAL_BODY_INCLUDE;   
        } else {
        	// �G���[��񂪊܂܂��Ƃ��̓{�f�B�]�����X�L�b�v
        	return SKIP_BODY;
        }
    }

    /**
     * �^�O�]���I�����ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w��
     * @throws JspException JSP��O
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
