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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  <code>ifAuthorizedBlock</code> �^�O�̎����N���X�B
 * </p>
 *
 * <p>
 *  {@link IfAuthorizedTag} �̌��ʂ� <code>blockId</code>
 *  ���ɐ��䂷��ׂ̃^�O�ŁA
 *  <code>blockId</code> �� {@link IfAuthorizedTag} �ƕR�t�����A
 *  �{�f�B����\�����邩�ǂ����𔻒肷��B<br>
 *  �܂��A���̃^�O�����q��ɂ��邱�ƂŁA�A�N�Z�X�������̐�����_��ɍs�Ȃ������\�ɂȂ�B
 *  ����q��ɂ���ꍇ�́A�e�^�O�� <code>blockId</code>�����Ǝq�^�O��<code>parentBlockId</code>
 *  �^�O���R�t�����A�{�f�B����\�����邩�ǂ����𔻒肷��B
 * </p>
 *
 * <br>
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>{@link IfAuthorizedBlockTag} �ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *    <td> <code>blockId</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �ΏۂƂȂ� <code>blockId</code>�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>parentBlockId</code> </td>
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
 * <p><code><pre>
 * &lt;t:ifAthorizedBlock blockId="ABC" &gt;
 *     �{�f�B���� <code>blockId</code> �ŕR�t����ꂽ
 *     {@link IfAuthorizedBlockTag} ���\�������ꍇ�̂ݕ\�������B
 *
 *     &lt;t:ifAthorizedBlock blockId="EFG" parentBlockId="ABC" &gt;
 *      �{�f�B���� <code>blockId</code> �ŕR�t����ꂽ {@link IfAuthorizedTag}
 *      ���\�������ꍇ�̂ݕ\�������B
 *
 *         &lt;t:ifAthorized path="/sample1/test.do blockId="EFG" &gt;
 *             �w�肳�ꂽ�p�X�ւ̃A�N�Z�X����������ꍇ�A�o�͂����B
 *         &lt;/t:ifAthorized&gt;
 *
 *     &lt;/t:ifAthorizedBlock&gt;
 * &lt;/t:ifAthorizedBlock&gt;
 * </pre></code></p>
 *
 *
 * @see jp.terasoluna.fw.web.taglib.IfAuthorizedTag
 *
 */
public class IfAuthorizedBlockTag extends BodyTagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 1062137481352416171L;

    /**
     * ���O�N���X
     */
    private static Log log
                  = LogFactory.getLog(IfAuthorizedBlockTag.class);

    /**
     * �u���b�NID�B
     */
    private String blockId = null;

    /**
     * �e�u���b�NID�B
     */
    private String parentBlockId = null;

    /**
     * �u���b�NID��ݒ肷��B
     *
     * @param blockId �u���b�NID
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * �e�u���b�NID��ݒ肷��B
     *
     * @param parentBlockId �e�u���b�NID
     */
    public void setParentBlockId(String parentBlockId) {
        this.parentBlockId = parentBlockId;
    }

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w��
     * @throws JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {

        if (this.blockId == null) {
            throw new JspException("blockId is required.");
        }

        // �{�f�B�]��
        return EVAL_BODY_BUFFERED;
    }

    /**
     * �^�O�{�f�B�I�����ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w��
     * @throws JspException JSP��O
     */
    @Override
    public int doAfterBody() throws JspException {

        // �u���b�N�����擾
        Boolean blockInfo = null;
        try {
            // pageContext ����u���b�N�����擾
            blockInfo = (Boolean) pageContext.getAttribute(this.blockId);
        } catch (ClassCastException e) {
            if (log.isWarnEnabled()) {
                log.warn("Class cast error." , e);   
            }
        }

        // �{�f�B�o�͂��ׂ����ǂ���
        boolean outputBody = false;
        if (blockInfo != null && blockInfo.booleanValue()) {
            //blockInfo��null�ł͖���true�̏ꍇ
            outputBody = true;
        }

        // �e�u���b�N�����擾
        if (this.parentBlockId != null) {
            // �A�N�Z�X�����`�F�b�N���ʂ�ۑ�
            pageContext.setAttribute(
                this.parentBlockId, new Boolean(outputBody));
        }

        // �{�f�B�o��
        if (outputBody) {
            try {
                bodyContent.writeOut(bodyContent.getEnclosingWriter());
            } catch (IOException e) {
                log.error("Output error.");
                throw new JspException(e);
            }
        }

        // �{�f�B�ĕ]���͂��Ȃ�
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
        this.blockId = null;
        this.parentBlockId = null;
    }

}
