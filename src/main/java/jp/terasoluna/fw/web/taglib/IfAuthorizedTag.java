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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.web.thin.AuthorizationControlFilter;
import jp.terasoluna.fw.web.thin.AuthorizationController;

/**
 * <p>
 *  <code>ifAuthorized</code> �^�O�̎����N���X�B
 * </p>
 *
 * <p>
 *  ���N�G�X�g�� <code>path</code> �����Ŏw�肳�ꂽ�p�X�ɑ΂��ăA�N�Z�X����
 *  ����ꍇ�ɂ̂݁A�^�O�̃{�f�B�������o�͂���B�A�N�Z�X�����Ȃ��ꍇ�ɂ́A
 *  �P�ɖ��������B�A�N�Z�X���̃`�F�b�N�́A
 *  {@link
 *  jp.terasoluna.fw.web.thin.AuthorizationController}
 *  �ֈϏ������B
 * </p>
 *
 * <br>
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>{@link IfAuthorizedTag} �ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *    <td> <code>path</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �ΏۂƂȂ� <code>path</code>�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>blockId</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ���̃^�O�̐e�ƂȂ� {@link IfAuthorizedBlockTag}
 *     �ƕR�t����ׂ� <code>blockId</code>�B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
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
 * &lt;t:ifAuthorized parh="/pathToSomewhere"&gt;
 *   ... // ���胆�[�U�݂̂̕\�����ړ�
 * &lt;/t:ifAuthorized&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.taglib.IfAuthorizedBlockTag
 * @see
 * jp.terasoluna.fw.web.thin.AuthorizationController
 *
 */
public class IfAuthorizedTag extends TagSupport {
    
    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -4929834014631292954L;

    /**
     * �p�X���B
     */
    private String path = null;

    /**
     * �u���b�NID�B
     */
    private String blockId = null;

    /**
     * �p�X����ݒ肷��B
     *
     * @param path �p�X���B
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * �u���b�NID��ݒ肷��B
     *
     * @param blockId �u���b�NID
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B<code>AccessConrol</code>
     * �Ƀ`�F�b�N���Ϗ����A<code>path</code>
     * �ɑ΂��ăA�N�Z�X����������Ƃ��ɂ̓^�O�̃{�f�B�������o�͂��A
     * �A�N�Z�X�������Ȃ��Ƃ��ɂ̓{�f�B�������X�L�b�v����B
     *
     * @return ��������w��
     * @throws JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {

        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();

        // AuthorizationController���擾
        AuthorizationController ac
             = AuthorizationControlFilter.getAuthorizationController();

        // �A�N�Z�X�����`�F�b�N
        boolean isAuthorized = ac.isAuthorized(this.path, req);

        if (isAuthorized) {
            if (this.blockId != null) {
                // �A�N�Z�X�����`�F�b�N���ʂ��u���b�N���ɒǉ����ĕۑ�
                pageContext.setAttribute(this.blockId, new Boolean(true));
            }
            // �A�N�Z�X����������Ƃ��̓{�f�B�]��
            return EVAL_BODY_INCLUDE;
        }
        // �������Ȃ��Ƃ��̓{�f�B�]�����X�L�b�v
        return SKIP_BODY;
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

    /**
     * �^�O�n���h��������̏����B
     */
    @Override
    public void release() {
        super.release();
        this.path = null;
        this.blockId = null;
    }

}
