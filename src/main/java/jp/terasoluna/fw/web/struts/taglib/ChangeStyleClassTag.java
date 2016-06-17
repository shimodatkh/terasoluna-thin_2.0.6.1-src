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

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;

/**
 * <p><code>changeStyleClass</code>�^�O�̎����N���X�B</p>
 *
 * <p>�w�肵���t�B�[���h�ɂ��ẴG���[��񂪐ݒ肳��Ă��邩�ǂ����ɂ���āA
 * �X�^�C���V�[�g�̃N���X���̐؂�ւ����s���B<br>
 * �A�N�V�����t�H�[���̃t�B�[���h�ɃG���[������ꍇ�ɁA���̃t�B�[���h��
 * ������Ԏ��ɂ���Ȃǂ̕\����ύX������ꍇ�ɗ��p����B</p>
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p><code>changeStyleClass</code> �^�O�ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *    <td> <code>name</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      �G���[��񂪐ݒ肳��Ă��邩�ǂ�����
 *      ���肷��t�B�[���h���B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>default</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      �G���[���Ȃ��ꍇ�̃X�^�C���V�[�g�N���X���B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>error</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      �G���[������ꍇ�̃X�^�C���V�[�g�N���X���B
 *    </td>
 *   </tr>
 * </table>
 * </div>
 * <br><br>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</p>
 * <br>
 * <h5>�g�p���@</h5>
 * <p><code><pre>
 * &lt;td class='&lt;ts:changeStyleClass name="mou1"
 *    default="gaid" error="error"/&gt;'&gt;
 *   &lt;input type="text" name="mou1"&gt;
 * &lt;/td&gt;
 * </pre></code></p>
 */
public class ChangeStyleClassTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 8969715040525132492L;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(ChangeStyleClassTag.class);
    
    /**
     * �G���[��񂪐ݒ肳��Ă��邩�ǂ����𔻒肷��t�B�[���h���B
     */
    private String name = null;

    /**
     * �t�B�[���h�ɃG���[���Ȃ��ꍇ�̃X�^�C���V�[�g�̃N���X���B
     */
    private String defaultValue = null;

    /**
     * �t�B�[���h�ɃG���[������ꍇ�̃X�^�C���V�[�g�̃N���X���B
     */
    private String errorValue = null;

    /**
     * �t�B�[���h����ݒ肷��B
     *
     * @param name �t�B�[���h��
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �t�B�[���h�ɃG���[���Ȃ��ꍇ�̃X�^�C���V�[�g�̃N���X����ݒ肷��B
     *
     * @param value �G���[���Ȃ��ꍇ�̃X�^�C���V�[�g�̃N���X��
     */
    public void setDefault(String value) {
        this.defaultValue = value;
    }

    /**
     * �t�B�[���h�ɃG���[������ꍇ�̃X�^�C���V�[�g�̃N���X����ݒ肷��B
     *
     * @param value �G���[������ꍇ�̃X�^�C���V�[�g�̃N���X��
     */
    public void setError(String value) {
        this.errorValue = value;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�ɃG���[��񂪐ݒ肳��Ă��邩�ǂ����ɂ���āA
     * �X�^�C���V�[�g�̃N���X����ԋp����B
     *
     * @param req HTTP���N�G�X�g
     * @param fieldName �t�B�[���h��
     * @param ifNormal �G���[���Ȃ��ꍇ�̃X�^�C���V�[�g�̃N���X��
     * @param ifError �G���[������ꍇ�̃X�^�C���V�[�g�̃N���X��
     * @return �X�^�C���V�[�g�̃N���X��
     */
    private String chooseClass(HttpServletRequest req,
                               String fieldName,
                               String ifNormal,
                               String ifError) {
        ActionMessages errors = (ActionMessages)
            req.getAttribute(Globals.ERROR_KEY);
        if (errors == null) {
            HttpSession session = req.getSession(true);
            errors = (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
            if (errors == null) {
                return ifNormal;
            }
        }
        Iterator iter = errors.get(fieldName);
        int errorCount = 0;
        while (iter.hasNext()) {
            iter.next();
            errorCount++;
        }
        return (errorCount == 0) ? ifNormal : ifError;
    }

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     * �G���[�̗L���ɂ���ďo�͂���X�^�C���V�[�g�N���X����ύX����B
     *
     * @return ��������w��
     * @throws JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest req
            = (HttpServletRequest) pageContext.getRequest();
        if (req == null) {
            return SKIP_BODY;
        }
        String result = chooseClass(req,
                                    this.name,
                                    this.defaultValue,
                                    this.errorValue);
        try {
            JspWriter out = pageContext.getOut();
            out.print(result);
        } catch (IOException e) {
            log.error("Output failed.");
            throw new JspTagException(e.toString());
        }
        return EVAL_BODY_INCLUDE;
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
        this.name = null;
        this.defaultValue = null;
        this.errorValue = null;
    }

}
