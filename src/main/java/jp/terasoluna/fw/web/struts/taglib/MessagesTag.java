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
 * �|�b�v�A�b�v��ʂŁA�A�N�V�������b�Z�[�W�̕\�����s���B
 *
 * <code>Struts</code>��<code>MessagesTag</code>���g�����A
 * &lt;ts:messagePopup&gt;<br>�^�O�Ƌ��Ƀ|�b�v�A�b�v���s����
 * ���b�Z�[�W�����Z�b�V�������烊�N�G�X�g�Ɉڂ������āA�Z�b�V���������
 * �폜���s���B<br>
 *
 * <b>�� ���ӁATERASOLUNA1.1.x�Ƃ�MessagesTag�̋@�\�Ƃ͈قȂ�B</b><br>
 *
 * �|�b�v�A�b�v��ʂł̃��b�Z�[�W���\���́A���̃^�O���g�p����Ȃ�����A
 * �Z�b�V�������烁�b�Z�[�W���͍폜����Ȃ����߁A���ӂ��邱�ƁB<br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>{@link MessagesTag} �ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *    <td> <code>id</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>false</code> </td>
 *    <td align="left">
 *     ���b�Z�[�W���i�[������<code>bean</code>���B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>bundle</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ���b�Z�[�W���\�[�X�����w�肷��B�������w�肵�Ȃ��ꍇ
 *     �f�t�H���g�̃��b�Z�[�W���\�[�X���g�p�����B
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
 *     �\�����s�Ȃ��A�N�V�������b�Z�[�W�̃��b�Z�[�W�L�[���ʂɎw�肷��B
 *     <code>message</code>�����̒l��&quot;true&quot;�Ɏw�肵���ꍇ�́A
 *     �K���A<code>Globals.MESSAGE_KEY</code>���ݒ肳���B
 *     �Ȃ��A�ݒ肪�s�Ȃ��Ă��Ȃ��ꍇ�A<code>Globals.ERROR_KEY</code>
 *     ���ݒ肳���B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>property</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �\�����s�Ȃ��i�t�H�[���j�v���p�e�B�����w�肷��B
 *     �������w�肳��Ȃ��ꍇ�A�v���p�e�B���Ɋւ�炸�A�S�Ă�
 *     �A�N�V�������b�Z�[�W���\�������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>header</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ���b�Z�[�W�{���ꗗ�̑O�ɏo�͂����w�b�_���b�Z�[�W�L�[��
 *     �w�肷��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>footer</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ���b�Z�[�W�{���ꗗ�̌�ɏo�͂����t�b�^���b�Z�[�W�L�[��
 *     �w�肷��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>message</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td>
 *     �l�� <code>&quot;true&quot;</code>�Ɏw�肵���Ƃ��A<code>name</code>
 *     �������A<code>Globals.MESSAGE_KEY</code>�Ƃ��Đݒ肳���B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br><br>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</p>
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <code>&lt;ts:messages&gt;</code>��<code>id</code>�����Ŏw�肵��
 * <code>bean</code>���œ����^�O����Q�Ɖ\�ƂȂ�B
 * <p><code><pre>
 * &lt;ts:messages id=&quot;msg&quot; bundle=&quot;sampleResources&quot;
 *     message=&quot;true&quot;&gt;
 *    &lt;bean:write name="msg"/&gt;
 * &lt;/ts:messages&gt;
 * </pre></code></p>
 *
 */
public class MessagesTag extends org.apache.struts.taglib.html.MessagesTag {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 1811855638381884729L;

    /**
     * �|�b�v�A�b�v��ʂŁA�Z�b�V�����Ɋi�[����Ă��郁�b�Z�[�W����
     * ���N�G�X�g�Ɉړ����A���b�Z�[�W���̕\���������s���B
     *
     * @return �������ʃX�e�[�^�X
     * @throws javax.servlet.jsp.JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {
        // ���N�G�X�g�p�����[�^�ő��M����Ă��郁�b�Z�[�W�L�[���擾����B
        HttpServletRequest request
            = (HttpServletRequest) pageContext.getRequest();
        String messageKey = request.getParameter(
            MessagesPopupTag.POPUP_MESSAGE_KEY);
        if (messageKey == null) {
            // (Popup��ʂƒʏ�̃��b�Z�[�W�\����ʂ����˂Ă���ꍇ�ɔ���)
            // ���b�Z�[�W�L�[�����݂��Ȃ��ꍇ�AStruts�̃��b�Z�[�W�\��������
            // �ڍs����B
            return super.doStartTag();
        }

        HttpSession session = pageContext.getSession();
        ActionMessages messages
            = (ActionMessages) session.getAttribute(messageKey);
        request.setAttribute(Globals.MESSAGE_KEY, messages);

        // �Z�b�V��������G���[���폜����B
        session.removeAttribute(messageKey);

        return super.doStartTag();
    }
}
