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
import javax.servlet.jsp.JspException;

import jp.terasoluna.fw.web.struts.form.ActionFormUtil;

import org.apache.struts.taglib.TagUtils;

/**
 * <p>�g��submit�^�O�B�t�H�[���̃^�[�Q�b�g���w�肵�܂��B</p>
 *
 * <p>
 * �t�H�[���̃T�u�~�b�g����target�̑����l��ݒ肵�܂��B(�g�p���@���Q��)
 * </p>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 *
 * <p><code>submitTag</code> �ł́A�ȉ��̑������T�|�[�g����B</p>
 *
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td> <b>������</b> </td>
 *    <td> <b>�f�t�H���g�l</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *    <td> <b>���s����</b> </td>
 *    <td> <b>�T�v</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>target</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      �^�[�Q�b�g����w�肷��B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <p>
 *  ���̑��̑����ɂ��ẮA
 *  <code>&lt;html:submit&gt;</code> �^�O�� <code>API</code> ���Q�ƁB
 * </p>
 * <br>
 * <br>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p> <code>&lt;html:submit&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 * <br>
 * <h5>�g�p���@</h5>
 * <code><pre>
 *  &lt;ts:submit value=" submit " target=" rightFrame "/&gt;
 * </code></pre>
 *
 */
public class SubmitTag extends org.apache.struts.taglib.html.SubmitTag {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -795727425454129052L;

    /** Submit���x�� */
    private static final String LABEL_SUBMIT = "Submit";

    /** frameScript���� */
    private static final String ATTRIBUTE_FRAME_SCRIPT = "frameScript";

    /** �s��؂蕶�� �V�X�e���v���p�e�B */
    private static final String SYSTEM_LINE_SEPARATOR = "line.separator";

    /** javascript�J�n�^�O */
    private static final String TAG_JS_START = "<script type=\"text/javascript\">";

    /** javascript�I���^�O */
    private static final String TAG_JS_END = "</script>";

    /** �R�����g�J�n�^�O */
    private static final String TAG_COMMENT_START = "<!--";

    /** �R�����g�I���^�O */
    private static final String TAG_COMMENT_END = "//-->";

    /** submit�J�n�^�O */
    private static final String TAB_SUBMIT_START = "<input type=\"submit\"";

    /** submit�I���^�O */
    private static final String TAG_SUBMIT_END = "/>";

    /** setFrameTarget���\�b�h�� */
    private static final String JS_METHOD_FRAME_TARGET = "__setFrameTarget";

    /**
     * �w�肷��^�[�Q�b�g��
     */
    protected String target = null;

    /**
     * <p>�^�[�Q�b�g�����擾����B</p>
     *
     * @return target �^�[�Q�b�g��
     */
    public String getTarget() {
        return this.target;
    }

    /**
     * <p>�^�[�Q�b�g����ݒ肷��B</p>
     *
     * @param target �^�[�Q�b�g��
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * <p>���ׂẴA���P�[�g���ꂽ�������������B</p>
     */
    @Override
    public void release() {
        super.release();
        this.target = null;
    }

    /**
     * <p>�^�O�I���������B</p>
     *
     * @return EVAL_PAGE
     * @exception JspException ��O
     */
    @Override
    public int doEndTag() throws JspException {

        // Acquire the label value we will be generating
        String label = value;
        if ((label == null) && (text != null)) {
            label = text;
        }
        if ((label == null) || (label.length() < 1)) {
            label = LABEL_SUBMIT;
        }

        // Generate an HTML element
        StringBuffer results = new StringBuffer();

        //�������g���ӏ���������
        if (target != null
                && pageContext.getAttribute(ATTRIBUTE_FRAME_SCRIPT) == null) {
            HttpServletRequest req
                = (HttpServletRequest) pageContext.getRequest();
            String formName = ActionFormUtil.getActionFormName(req);
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append(TAG_JS_START);
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append(TAG_COMMENT_START);
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append("  function ");
            results.append(JS_METHOD_FRAME_TARGET);
            results.append("(__frTarget) {");
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append("    document.");
            results.append(formName);
            results.append(".target = ");
            results.append("__frTarget;");
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append("  }");
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append(TAG_COMMENT_END);
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append(TAG_JS_END);
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));
            results.append(System.getProperty(SYSTEM_LINE_SEPARATOR));

            pageContext.setAttribute(ATTRIBUTE_FRAME_SCRIPT,
                    ATTRIBUTE_FRAME_SCRIPT);
        }
        //�������g���ӏ������܂�

        results.append(TAB_SUBMIT_START);
        if (property != null) {
            results.append(" name=\"");
            results.append(property);
            if (indexed) {
                prepareIndex(results, null);
            }
            results.append("\"");
        }

        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }
        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }
        results.append(" value=\"");
        results.append(label);
        results.append("\"");

        //�������g���ӏ���������
        String old = getOnclick();
        if (target != null) {
            StringBuilder onclickStr = new StringBuilder();
            onclickStr.append(JS_METHOD_FRAME_TARGET);
            onclickStr.append("(\'");
            onclickStr.append(this.target);
            onclickStr.append("\');");
            if (old != null) {
                onclickStr.append(old);
            }
            setOnclick(onclickStr.toString());
        }
        //�������g���ӏ������܂�

        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(TAG_SUBMIT_END);
        //TagUtils�I�v�V�������擾�B
        TagUtils tagUtils = TagUtils.getInstance();
        // Render this element to our writer
        tagUtils.write(pageContext, results.toString());
        // onclick�����𕜌����Ă����B
        setOnclick(old);
        // Evaluate the remainder of this page
        return EVAL_PAGE;
    }

}
