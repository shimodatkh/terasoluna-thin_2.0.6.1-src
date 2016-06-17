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

import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

/**
 * <p>
 *  1���[�U���قȂ郆�[�X�P�[�X�̉�ʂ𑀍삵�A�G���[�E���b�Z�[�W�̃|�b�v
 *  �A�b�v�\���������ɍs����Ƃ��A�Z�b�V������Ƀ��[�X�P�[�X���̃G���[�E
 *  ���b�Z�[�W��񂪌�������ĕ\������Ă��܂��B<br>
 *  ���̃^�O��&lt;ts:errors&gt;�^�O�A�y��&lt;ts:messages&gt;�^�O��
 *  �g�ݍ��킹�鎖�ɂ��A��ʒP�ʂŔ��������|�b�v�A�b�v�G���[�̍�����
 *  �����邱�Ƃ��\�ƂȂ�B<br>
 *  {@link MessagesPopupTag}�́A���N�G�X�g�����Ƃ��ēo�^����Ă���
 *  �G���[�E���b�Z�[�W�����Z�b�V�����ɕۑ�����B
 * </p>
 * <p>
 *  {@link MessagesPopupTag} �́A<code>&lt;ts:body&gt;</code>
 *  �^�O�ƘA�g���ă|�b�v�A�b�v��ʂ��J���B
 *  {@link MessagesPopupTag} ��p����ۂɂ́A�K��
 *  <code>&lt;ts:body&gt;</code>�^�O�Ƌ��ɗp���A�܂��A
 *  {@link MessagesPopupTag} ��<code>&lt;ts:body&gt;</code>�^�O����
 *  �O�ɋL�q����Ȃ��Ă͂Ȃ�Ȃ��B
 * </p>
 * <p>
 *  {@link MessagesPopupTag} �́A<code>JavaScript</code> ��
 *  <code>onLoad</code>�C�x���g�������̃X�N���v�g��ǉ����邱�ƂŁA
 *  �G���[�E���b�Z�[�W���\���p�̃|�b�v�A�b�v��ʂ��J���B<br>
 *  �]���āA�G���[�E���b�Z�[�W���\���p�̃|�b�v�A�b�v��ʂ�\������ہA
 *  ���L�̎菇�����B���̂��߁A�G���[�E���b�Z�[�W��\���O�̉�ʂł͖����A
 *  �G���[�E���b�Z�[�W�������̑J�ڐ��ʂɂ��̃^�O��z�u����悤���ӂ���
 *  ���ƁB<br>
 * <ol>
 *  <li>
 *   ��U���͂ɑ΂��錋�ʉ�ʂ��Ԃ����B
 *  </li>
 *  <li>
 *   ���̌��ʉ�ʂ�<code>onLoad</code>�C�x���g�Ń|�b�v�A�b�v��ʂ��J�����B
 *  </li>
 *  <li>
 *   �G���[�E���b�Z�[�W���\���p�̃��N�G�X�g�����炽�߂ăT�[�o�ɑ��M�����B
 *  </li>
 * </ol>
 * </p>
 * <p>
 *  {@link MessagesPopupTag} �́A<code>&lt;ts:body&gt;</code> �^�O��
 *  ��������{����<code>onLoad</code>�C�x���g�����X�N���v�g�ɏ㏑�������B
 *  <code>JSP</code> ���� {@link MessagesPopupTag}�̑O��
 *  <code>onLoad</code> �^�O������ꍇ�ɂ́A�G���[���Ȃ��Ƃ��ɂ�
 *  <code>onLoad</code> �^�O���̃X�N���v�g���L���ɂȂ�A
 *  �G���[������ꍇ�ɂ� {@link MessagesPopupTag} �ɂ��ݒ�ŏ㏑������A
 *  �G���[�\���p�̃|�b�v�A�b�v��ʂ��D�悳���B
 * </p>
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>{@link MessagesPopupTag} �ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *    <td> <code>popup</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �|�b�v�A�b�v��ʂŕ\������URL�B<code>JavaScript</code>��
 *     <code>window.open()</code>�̑������ɑΉ�����B<br>
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>title</code> </td>
 *    <td> <code>popup</code> </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �G���[��\������|�b�v�A�b�v��ʂ̃^�C�g���B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>param</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     <code>JavaScript</code> �Ń|�b�v�A�b�v��ʂ��J���Ƃ��̃p�����[�^������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>paramType</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     <code>JavaScript</code> �Ń|�b�v�A�b�v��ʂ��J���Ƃ��̃p�����[�^��������A
 *     <code>ApplicationResources</code> �t�@�C������擾����ꍇ��
 *     ���\�[�X�L�[�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>paramFunc</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     <code>JavaScript</code> �Ń|�b�v�A�b�v��ʂ��J���Ƃ���
 *     �p�����[�^��������擾���� <code>JavaScript</code> �֐����B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>windowId</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �J�����|�b�v�A�b�v��ʂ�ێ����� <code>JavaScript</code> �ϐ����B
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
 *
 * <p><code><pre>
 * &lt;ts:messagesPopup popup="/popup/errors.do" /&gt;
 *   ...
 * &lt;ts:body ...&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.struts.taglib.BodyTag
 *
 */
public class MessagesPopupTag extends TagSupport {
    
    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 1148524236499924202L;

    /**
     * <code>onLoad</code> ���̏��������N�G�X�g�����ɕۑ�����Ƃ��̃L�[�B
     */
    public static final String ON_LOAD_KEY = BodyTag.ON_LOAD_KEY;

    /**
     * �|�b�v�A�b�v���Ƀ��N�G�X�g�p�����[�^�œn�����G���[���̃L�[�B
     */
    public static final String POPUP_ERROR_KEY = "popup_error_key";

    /**
     * �|�b�v�A�b�v���Ƀ��N�G�X�g�p�����[�^�œn����郁�b�Z�[�W���̃L�[�B
     */
    public static final String POPUP_MESSAGE_KEY = "popup_message_key";

    /**
     * �f�t�H���g�^�C�g���B�l�́A<code>popup</code>�B
     */
    private static final String DEFAULT_TITLE = "popup";

    /**
     * �|�b�v�A�b�v��ʂŕ\������URL�B�R���e�L�X�g�p�X�͊܂܂Ȃ��B
     */
    private String popup = null;

    /**
     * �G���[���E���b�Z�[�W����\������|�b�v�A�b�v��ʂ̃^�C�g���B
     */
    private String title = DEFAULT_TITLE;

    /**
     * <code>JavaScript</code>
     * �Ń|�b�v�A�b�v��ʂ��J���Ƃ��̃p�����[�^������B
     */
    private String param = null;

    /**
     * <code>JavaScript</code>
     * �Ń|�b�v�A�b�v��ʂ��J���Ƃ��̃p�����[�^�������
     * �擾���� <code>JavaScript</code> �֐����B
     */
    private String paramFunc = null;

    /**
     * �J�����|�b�v�A�b�v��ʂ�ێ����� <code>JavaScript</code> �ϐ����B
     */
    private String windowId = null;

    /**
     * �|�b�v�A�b�v��ʂŕ\������URL��ݒ肷��B
     *
     * @param value �|�b�v�A�b�v��ʂŕ\������URL
     */
    public void setPopup(String value) {
        this.popup = value;
    }

    /**
     * �G���[��\������|�b�v�A�b�v��ʂ̃^�C�g����ݒ肷��B
     *
     * @param value �^�C�g��
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * <code>JavaScript</code>
     * �Ń|�b�v�A�b�v��ʂ��J���Ƃ��̃p�����[�^�������ݒ肷��B
     *
     * @param value �p�����[�^
     */
    public void setParam(String value) {
        this.param = value;
    }

    /**
     * <code>JavaScript</code>
     * �Ń|�b�v�A�b�v��ʂ��J���Ƃ��̃p�����[�^��������A
     * <code>ApplicationResources</code> �t�@�C������擾���Đݒ肷��B
     *
     * @param value �p�����[�^
     */
    public void setParamType(String value) {
        this.param = PropertyUtil.getProperty("messages.popup.param." + value);
    }

    /**
     * <code>JavaScript</code>
     * �Ń|�b�v�A�b�v��ʂ��J���Ƃ��̃p�����[�^������Ƃ��āA
     * �w�肳�ꂽ<code>JavaScript</code> �֐��̖߂�l��ݒ肷��B
     *
     * @param value �p�����[�^
     */
    public void setParamFunc(String value) {
        this.paramFunc = value;
    }

    /**
     * �J�����|�b�v�A�b�v��ʂ�ێ����� <code>JavaScript</code>
     * �ϐ�����ݒ肷��B
     *
     * @param value �ϐ���
     */
    public void setWindowId(String value) {
        this.windowId = value;
    }

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w���B��� <code>EVAL_BODY_INCLUDE</code>
     * @throws JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();

        if (req.getAttribute(Globals.ERROR_KEY) == null
                && req.getAttribute(Globals.MESSAGE_KEY) == null) {
            // ���N�G�X�g�ɃG���[���E���b�Z�[�W���
            // ���݂��Ȃ��Ƃ��A�ȍ~�̏����͍s���Ȃ��B
            return EVAL_BODY_INCLUDE;
        }

        ActionMessages errors =
            (ActionMessages) req.getAttribute(Globals.ERROR_KEY);

        ActionMessages messages =
            (ActionMessages) req.getAttribute(Globals.MESSAGE_KEY);

        String errorKey = null;
        String messageKey = null;

        HttpSession session
             = pageContext.getSession();
        if (errors != null && !errors.isEmpty()) {
            errorKey = RandomUtil.generateRandomID();
            // �Z�b�V�����ɃG���[����ۑ�����B
            session.setAttribute(errorKey, errors);
        }
        if (messages != null && !messages.isEmpty()) {
            messageKey = RandomUtil.generateRandomID();
            // �Z�b�V�����Ƀ��b�Z�[�W����ۑ�����B
            session.setAttribute(messageKey, messages);
        }

        // <body>�^�O��onLoad�����Ŏw�肳���X�N���v�g������
        // ���擾����B
        String script = getOnLoadScript(req, errorKey, messageKey);
        req.setAttribute(ON_LOAD_KEY, script);

        return EVAL_BODY_INCLUDE;
    }

    /**
     * <code>&lt;body&gt;</code>�^�O��<code>onLoad</code>������
     * �L�q�����E�B���h�E�I�[�v���̂��߂̃X�N���v�g�𐶐�����B
     *
     * @param req HTTP���N�G�X�g
     * @param errorKey �G���[���̃L�[
     * @param messageKey ���b�Z�[�W���̃L�[
     * @return �E�B���h�E�I�[�v�����s���X�N���v�g
     */
    private String getOnLoadScript(HttpServletRequest req,
            String errorKey, String messageKey) {

        // �G���[�E���b�Z�[�W���̃L�[������
        // ���݂��Ȃ��ꍇ�A�������I������B
        if (errorKey == null && messageKey == null) {
            return null;
        }

        StringBuilder onLoad = new StringBuilder();
        onLoad.append("  ");
        if (this.windowId != null) {
            // �E�B���h�EID�i�X�N���v�g����̖߂�l���i�[�j
            onLoad.append(this.windowId);
            onLoad.append(" = ");
        }
        onLoad.append("window.open(\"");
        // URL���w��
        onLoad.append(req.getContextPath());
        onLoad.append(this.popup);
        // �G���[���E���b�Z�[�W���̂����ꂩ�����݂��鎞�A
        // ���N�G�X�g�p�����[�^�ŃL�[�𑗐M����B
        onLoad.append(getRequestParameterKey(errorKey,
            messageKey));
        onLoad.append("\", \"");
        if (this.title != null) {
            // �E�B���h�E�^�C�g���\�����w��
            onLoad.append(this.title);
        }
        onLoad.append("\", ");
        if (this.paramFunc != null) {
            // JavaScript�֐��̖߂�l���w��
            onLoad.append(this.paramFunc);
        } else {
            onLoad.append("\"");
            if (this.param != null) {
                // �|�b�v�A�b�v��ʂ̃T�C�Y�A�ʒu�Ȃǂ�
                // �p�����[�^�����w��
                onLoad.append(this.param);
            }
            onLoad.append("\"");
        }
        onLoad.append(");" + System.getProperty("line.separator"));

        return onLoad.toString();
    }

    /**
     * �Z�b�V�����Ɋi�[����Ă���G���[�E���b�Z�[�W
     * ���̃L�[�����ɁA���N�G�X�g�p�����[�^��
     * �N�G����������쐬����B
     *
     * @param errorKey �G���[���L�[
     * @param messageKey ���b�Z�[�W���L�[
     * @return ���N�G�X�g�p�����[�^�Ƃ��đ��M�����N�G��������
     */
    private String getRequestParameterKey(String errorKey,
            String messageKey) {

        StringBuilder param = new StringBuilder();
        param.append("?");
        if (errorKey != null) {
            param.append(POPUP_ERROR_KEY);
            param.append("=");
            param.append(errorKey);
        }
        if (errorKey != null && messageKey != null) {
            // �G���[���L�[�E���b�Z�[�W���L�[������
            // ���݂���ꍇ�̂݁A�A���������ǉ�����B
            param.append("&");
        }
        if (messageKey != null) {
            param.append(POPUP_MESSAGE_KEY);
            param.append("=");
            param.append(messageKey);
        }

        return param.toString();
    }

    /**
     * �^�O�]���I�����ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w���B��� <code>EVAL_PAGE</code>
     * @throws JspException JSP��O
     */
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    /**
     * �^�O�n���h��������̏����B
     */
    @Override
    public void release() {
        super.release();
        this.popup = null;
        this.title = DEFAULT_TITLE;
        this.param = null;
        this.paramFunc = null;
        this.windowId = null;
    }

}
