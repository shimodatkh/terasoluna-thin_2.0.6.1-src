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

package jp.terasoluna.fw.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.fw.web.thin.ServerBlockageControlFilter;
import jp.terasoluna.fw.web.thin.ServerBlockageController;

/**
 * <p><code>ifPreBlockade</code> �^�O�̎����N���X</p>
 * <p>
 *  {@link IfPreBlockadeTag}
 * </p>
 *
 * <p>
 *  �T�[�o���Ǐ�Ԗ��͗\�Ǐ�Ԃ̏ꍇ�ɂ̂݁A�^�O�̃{�f�B�������o�͂���B
 *  �ʏ펞�ɂ͒P�ɖ��������B�T�[�o�ǂ̃`�F�b�N�́A
 *  {@link
 *  jp.terasoluna.fw.web.thin.ServerBlockageController}
 *  �ֈϏ������B
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
 * <code><pre>
 * &lt;t:ifPreBlockade&gt;
 *   ... // �T�[�o���Ǐ�Ԗ��͗\�Ǐ�Ԃ̏ꍇ�ɂ݂̂̕\�����ړ�
 * &lt;/t:ifPreBlockade&gt;
 * </pre></code></p>
 *
 * @see
 * jp.terasoluna.fw.web.thin.ServerBlockageController
 *
 */
public class IfPreBlockadeTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -7545297874735975274L;

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w��
     * @throws JspException ��O
     */
    @Override
    public int doStartTag() throws JspException {

        ServerBlockageController sbc =
            ServerBlockageControlFilter.getServerBlockageController();

        String pathInfo = RequestUtil.getPathInfo(pageContext.getRequest());

        if (sbc.isPreBlockaded() || sbc.isBlockaded(pathInfo)) {
            // �\�Ǐ�Ԃ܂��͕Ǐ�Ԃ̂Ƃ��̓{�f�B�]��
            return EVAL_BODY_INCLUDE;
        }
        // �{�f�B�]�����X�L�b�v
        return SKIP_BODY;
    }

    /**
     * �^�O�]���I�����ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w��
     * @throws JspException ��O
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
