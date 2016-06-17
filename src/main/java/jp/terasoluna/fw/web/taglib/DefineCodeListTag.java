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

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
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
 * defineCodeList �^�O�̎����N���X�B
 *
 * <p>
 * �T�[�u���b�g�R���e�L�X�g����
 * ���� id �Ŏw�肳�ꂽ CodeListLoader ��T���o���A
 * ���̒��ɕۑ�����Ă���R�[�h���X�g���擾����B
 * ������Ȃ��ꍇ�͋�̃R�[�h���X�g�̎擾���s���B
 * </p>
 *
 * �R�[�h���X�g���̗v�f�̓v���p�e�B���� id �A name
 * �Ŋi�[����Ă��邽�߁A�A�N�Z�X���邽�߂ɂ́A�^�O���̃v���p�e�B��
 * �����v�f����p����B
 * �R�[�h���X�g�̓ǂݍ��݂́A
 * {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 * �C���^�t�F�[�X�̎����N���X���Q�Ƃ̂��ƁB
 *
 *
 * <strong>�^�O���T�|�[�g���鑮��</strong><br>
 * <p> defineCodeList �^�O�ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *    ���̑�������CodeListLoader����������B�܂�
 *    {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 *    �C���^�t�F�[�X����������bean�̖��O���w�肷��B
 *    ���̃^�O�錾�ȍ~�A&lt;logic:iterator&gt;�^�O�A
 *    &lt;html:options&gt;�^�O�ȂǂŃR�[�h���X�g���Q�Ƃł���B
 *  </tr>
 * </table>
 * </div>
 * </p>
 * <strong>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</strong><br>
 * <p>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</p>
 * <br>
 * <strong>�g�p���@</strong><br>
 * <p>
 * �ȉ��̗�́ACodeListLoader �C���^�t�F�[�X�����������N���X�� bean ��
 * &quot;loader1&quot;�ƌ������O�Œ�`���Ďg�p����ꍇ�̐ݒ��ł���B<br>
 * ��`�̕��@��
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader}�A
 * �y��
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader}
 * ���Q�ƁB
 * </p>
 *
 * <strong>JSP ���ł̋L�q��B</strong><br>
 *
 * <code><pre>
 *  &lt;t:defineCodeList id=<b>"loader1"</b> /&gt;
 *  �c
 *  &lt;html:select property="selectOptions"&gt;
 *    &lt;html:options collection=<b>"loader1"</b>
 *                  labelProperty=<b>"name"</b>
 *                  property=<b>"id"</b>/&gt;
 *  &lt;/html:select&gt;
 *  �c
 * </pre></code>
 * �R�[�h���X�g�̃T�C�Y�擾�Ɋւ��ẮA{@link WriteCodeCountTag} ���Q��
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.CodeListLoader
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 *
 */
public class DefineCodeListTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 7550280327858449058L;

    /**
     * ���O�N���X�B
     */
     private static Log log =
         LogFactory.getLog(DefineCodeListTag.class);

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * <p>
     * �T�[�u���b�g�R���e�L�X�g����AApplicationContext���擾���A
     * &quot;page&quot; �����Ŏw�肳�ꂽ id
     * �� CodeListLoader ���擾���A���̒���
     * �R�[�h���X�g�� pageContext �ɓo�^����B
     *
     * �R�[�h���X�g�������ł��Ȃ��ꍇ�A���ArrayList��
     *  pageContext �ɓo�^����B
     * �Ȃ��A�o�^���̃X�R�[�v�� &quot;page&quot; �����Ƃ���B
     * </p>
     *
     * @return ��������w���B��� EVAL_BODY_INCLUDE
     * @throws JspException JSP ��O
     */
    @Override
    public int doStartTag() throws JspException {
        if (log.isDebugEnabled()) {
            log.debug("doStartTag() called.");
        }

        if ("".equals(id)) {
            // id�����݂��Ȃ��ꍇ
            log.error("id is required.");
            throw new JspTagException("id is required.");
        }
        // �T�[�u���b�g�R���e�L�X�g���AApplicationContext���擾����B
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

        CodeBean[] codeBeanArray = loader.getCodeBeans(locale);
        if (codeBeanArray == null) {
            // codeBeanList��null�̏ꍇ���ArrayList��ݒ肷��B
            if (log.isWarnEnabled()) {
                log.warn("Codebean is null. CodeListLoader(bean id:"
                        + id + ")");
            }
            pageContext.setAttribute(id ,
                    new ArrayList() , PageContext.PAGE_SCOPE);
        } else {
            // �R�[�h���X�g��o�^����B
            pageContext.setAttribute(
                    id , codeBeanArray , PageContext.PAGE_SCOPE);
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * �^�O�]���I�����ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w���B��� EVAL_PAGE
     * @throws JspException JSP ��O
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
