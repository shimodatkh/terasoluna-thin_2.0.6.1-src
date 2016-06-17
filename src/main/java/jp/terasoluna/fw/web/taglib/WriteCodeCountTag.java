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
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.web.codelist.CodeBean;
import jp.terasoluna.fw.web.codelist.CodeListLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.util.RequestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * writeCodeCount �^�O�̎����N���X�B
 *
 * <p>
 * �R�[�h���X�g�̃T�C�Y��ԋp����B<br>
 * ���������Ƃ��āA�T�[�u���b�g�R���e�L�X�g����
 * �iid�j�Ŏw�肳�ꂽ CodeListLoader ��T���o���A
 * ���̒��ɕۑ�����Ă���R�[�h���X�g���擾���A���̃T�C�Y��ԋp����B
 * ������Ȃ��ꍇ�A�T�C�Y 0 ��ԋp����B
 * </p>
 *
 * <strong>�^�O���T�|�[�g���鑮��</strong><br>
 * <p> writeCodeCount �^�O�ł́A�ȉ��̑������T�|�[�g����B</p>
 * <p>
 * <div align="center">
 * <table width="90%" border="1" bgcolor="#FFFFFF">
 *  <tr>
 *   <td> <b>������</b> </td>
 *   <td> <b>�f�t�H���g�l</b> </td>
 *   <td> <b>�K�{��</b> </td>
 *   <td> <b>���s����</b> </td>
 *   <td> <b>�T�v</b> </td>
 *  </tr>
 *  <tr>
 *   <td> <code>id</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>true</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    ���̑�������R�[�h���X�g�����R�[�h���X�g���[�_�[����������B�܂�
 *    {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 *    ���������� bean �̖��O���w�肷��B
 *    �R�[�h���X�g��������Ȃ��ꍇ�A0���ԋp�����B
 *   </td>
 *  </tr>
 * </table>
 * </div>
 * </p>
 * <strong>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</strong><br>
 * <p>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</p>
 * <br>
 * <strong>�g�p���@</strong><br>
 * �ȉ��̗�́ACodeListLoader �C���^�t�F�[�X�����������N���X�� bean ��
 * &quot;loader1&quot; �ƌ������O�Œ�`���Ďg�p����ꍇ�̐ݒ��ł���B<br>
 * ��`�̕��@��
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader}�A
 * �y��
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader}
 * ���Q�ƁB
 * </p>
 *
 * <strong>JSP���ł̋L�q��B</strong><br>
 * <p>
 * <code><pre>
 *  �c
 *  &lt;t:writeCodeCount id=<Strong>"loader1"</Strong> /&gt;
 *  �c
 * </pre></code>
 * �R�[�h���X�g�̎擾�Ɋւ��ẮA{@link DefineCodeListTag} ���Q�ƁB<br>
 *
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 *
 */
public class WriteCodeCountTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -2318799214314166540L;

    /**
     * ���O�N���X�B
     */
     private static Log log =
         LogFactory.getLog(WriteCodeCountTag.class);

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * <p>
     *   �T�[�u���b�g�R���e�L�X�g����R�[�h���X�g���[�_�[
     * ���������A�R�[�h���X�g�𔭌������ꍇ�A�R�[�h���X�g�̗v�f����
     * �ԋp����B
     * �R�[�h���X�g�������ł��Ȃ��ꍇ�A0����ʂɏ������܂��B
     * </p>
     *
     * @return ��������w���B��� <code>EVAL_BODY_INCLUDE</code>
     * @throws JspException <code>JSP</code>��O
     */
    @Override
    public int doStartTag() throws JspException {
        if (log.isDebugEnabled()) {
            log.debug("doStartTag() called.");
        }

        JspWriter out = pageContext.getOut();

        try {
            if ("".equals(id)) {
                // id�����݂��Ȃ��ꍇ
                log.error("id is required.");
                throw new JspTagException("id is required.");
            }

            // pageContext���AApplicationContext���擾����B
            ServletContext sc = pageContext.getServletContext();
            ApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(sc);

            CodeListLoader loader = null;

            try {
                loader = (CodeListLoader) context.getBean(id);
            } catch (ClassCastException e) {
                //�擾����Bean��CodeListLoader�ł͂Ȃ��������O���X���[
                String errorMessage = "bean id:" + id
                    + " is not instance of CodeListLoader.";
                log.error(errorMessage);
                throw new JspTagException(errorMessage, e);
            }

            // ���P�[�����擾
            Locale locale = RequestUtils.getUserLocale(
                    (HttpServletRequest) pageContext.getRequest(),
                    Globals.LOCALE_KEY);

            CodeBean[] codeBeanList = loader.getCodeBeans(locale);
            if (codeBeanList == null) {
                // codeBeanList��null�̏ꍇ0���o�͂���B
                if (log.isWarnEnabled()) {
                    log.warn("Codebean is null. CodeListLoader(bean id:"
                            + id + ")");
                }
                out.print(0);
            } else {
                // ����Ɏ擾�ł����ꍇ�̓R�[�h���X�g�̒������o�͂���B
                out.print(codeBeanList.length);
            }

            return EVAL_BODY_INCLUDE;
        } catch (IOException ioe) {
            log.error("IOException caused.");
            throw new JspTagException(ioe.toString());
        }
    }

    /**
     * �^�O�]���I�����ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w���B��� EVAL_PAGE
     * @throws JspException  JSP ��O
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
    }

}
