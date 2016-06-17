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

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

/**
 * �|�b�v�A�b�v��ʂŃG���[�\�����s���B
 *
 * <code>Struts</code>��<code>ErrorsTag</code>���g�����A
 * &lt;ts:messagePopup&gt;<br>�^�O�Ƌ��Ƀ|�b�v�A�b�v���s����
 * �G���[�����Z�b�V�������烊�N�G�X�g�Ɉڂ������āA�Z�b�V���������
 * �폜���s���B<br>
 *
 * <b>�� ���ӁATERASOLUNA1.1.x�Ƃ�ErrorsTag�̋@�\�Ƃ͈قȂ�B</b><br>
 *
 * �|�b�v�A�b�v��ʂł̃G���[�\���́A���̃^�O���g�p����Ȃ�����A
 * �Z�b�V��������G���[���͍폜����Ȃ����߁A���ӂ��邱�ƁB<br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>{@link ErrorsTag} �ł́A�ȉ��̑������T�|�[�g����B</p>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>������</b> </td>
 *    <td> <b>�f�t�H���g�l</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *    <td> <b>���s����</b> </td>
 *    <td> <b>�T�v</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>bundle</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ���b�Z�[�W���\�[�X�����w�肷��B�������w�肵�Ȃ��ꍇ�f�t�H���g
 *     �̃��b�Z�[�W���\�[�X�ƂȂ�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>locale</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �o�̓��b�Z�[�W�̃��P�[�����w�肷��B
 *     �������w�肵�Ȃ��ꍇ�A�f�t�H���g�̃��P�[�����g�p�����B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>name</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �\�����s�Ȃ��A�N�V�����G���[�̃G���[�L�[���ʂɎw�肷��B
 *     �������w�肵�Ȃ��ꍇ�A<code>Globals.ERROR_KEY</code>�����Ɏ擾�����
 *     �G���[���b�Z�[�W�ꗗ��\������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>property</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �\�����s�Ȃ��i�t�H�[���j�v���p�e�B�����w�肷��B
 *     �������w�肳��Ȃ��ꍇ�A�v���p�e�B���Ɋւ�炸�A
 *     �S�ẴA�N�V�����G���[���\�������B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br><br>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <p><code><pre>
 * &lt;ts:errors bundle=&quot;sampleResources&quot;
 *     name=&quot;normalKey&quot; /&gt;
 * </pre></code></p>
 *
 */
public class ErrorsTag extends org.apache.struts.taglib.html.ErrorsTag {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -1764868652464908717L;

    /**
     * �|�b�v�A�b�v��ʂŁA�Z�b�V�����Ɋi�[����Ă���G���[����
     * ���N�G�X�g�Ɉړ����A�G���[���̕\���������s���B
     *
     * @return �������ʃX�e�[�^�X
     * @throws javax.servlet.jsp.JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {
        // ���N�G�X�g�p�����[�^�ő��M����Ă���G���[�L�[���擾����B
        HttpServletRequest request
            = (HttpServletRequest) pageContext.getRequest();
        String errorKey = request.getParameter(
            MessagesPopupTag.POPUP_ERROR_KEY);
        if (errorKey == null) {
            // (Popup��ʂƒʏ�̃G���[�\����ʂ����˂Ă���ꍇ�ɔ���)
            // �G���[�L�[�����݂��Ȃ��ꍇ�AStruts�̃G���[�\��������
            // �ڍs����B
            return super.doStartTag();
        }

        HttpSession session = pageContext.getSession();
        ActionMessages errors = (ActionMessages) session.getAttribute(errorKey);
        request.setAttribute(Globals.ERROR_KEY, errors);

        // �Z�b�V��������G���[���폜����B
        session.removeAttribute(errorKey);

        return super.doStartTag();
    }
}
