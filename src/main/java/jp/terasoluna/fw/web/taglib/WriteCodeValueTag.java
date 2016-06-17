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
 * WriteCodeValue �^�O�B
 *
 * <p>
 * �T�[�u���b�g�R���e�L�X�g����
 * ���� codeList �Ŏw�肳�ꂽ CodeListLoader ��T���o���A
 * ���̒��ɕۑ�����Ă���R�[�h���X�g����l���擾���A�o�͂���B
 * ������Ȃ��ꍇ�͉����o�͂��Ȃ��B
 * </p>
 *
 * �R�[�h���X�g�̓ǂݍ��݂́A
 * {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 * �C���^�t�F�[�X�̎����N���X���Q�Ƃ̂��ƁB
 *
 * <strong>�^�O���T�|�[�g���鑮��</strong><br>
 * <p> writeCodeValue �^�O�ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *   <td> <code>codeList</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>true</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    ���̑�������CodeListLoader����������B�܂�
 *    {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 *    �C���^�t�F�[�X����������bean�̖��O���w�肷��B
 *  </tr>
 *  <tr>
 *   <td> <code>key</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    �擾�����R�[�h���X�g����l���擾���邽�߂̃L�[�𒼐ڎw�肷��B
 *  </tr>
 *  <tr>
 *   <td> <code>name</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    �擾�����R�[�h���X�g����l���擾���邽�߂̃L�[��ێ�����Bean�̖��O�B
 *    key�������w�肳��Ă����ꍇ�́A�����B
 *  </tr>
 *  <tr>
 *   <td> <code>property</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    �擾�����R�[�h���X�g����l���擾���邽�߂̃L�[��ێ�����Bean�̃v���p�e�B�B
 *    key�������w�肳��Ă����ꍇ�́A�����B
 *  </tr>
 *  <tr>
 *   <td> <code>scope</code> </td>
 *   <td> <code>-</code> </td>
 *   <td> <code>false</code> </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    �擾�����R�[�h���X�g����l���擾���邽�߂̃L�[��ێ�����Bean��
 *    ���݂���X�R�[�v�B
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
 * &quot;loader1&quot;�ƌ������O�Œ�`����Ă���A
 * ���̒�����&quot;key1&quot;�Ƃ����L�[�Ŏ擾�ł���l��
 * �o�͂���ꍇ�̐ݒ��ł���B<br>
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
 *  &lt;t:writeCodeValue codeList=<b>"loader1"</b> key=<b>"key1"</b> /&gt;
 * </pre></code>
 * �R�[�h���X�g�̃T�C�Y�擾�Ɋւ��ẮA{@link WriteCodeCountTag} ���Q��
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.CodeListLoader
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 *
 */
public class WriteCodeValueTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 8199383777405058816L;

    /**
     * ���O�N���X�B
     */
     private static Log log =
         LogFactory.getLog(WriteCodeValueTag.class);

     /**
      * �R�[�h���X�g���B
      */
     private String codeList = null;

     /**
      * �R�[�h���X�g����value���擾���邽�߂̃L�[�B
      */
     private String key = null;

     /**
      *  property�Ŏw�肵���l�����o���ׂ�Bean���B
      */
     private String name = null;

     /**
      *  name�ɂ���Ďw�肳�ꂽBean��ŃA�N�Z�X�����v���p�e�B���B
      */
     private String property = null;

     /**
      *  name�ɂ���Ďw�肵��bean�����o���ׂɌ�������X�R�[�v�B
      */
     private String scope = null;

     /**
      * Bean����ݒ肷��B
      *
      * @param name Bean��
      */
     public void setName(String name) {
         this.name = name;
     }

     /**
      * �v���p�e�B����ݒ肷��B
      *
      * @param property �v���p�e�B��
      */
     public void setProperty(String property) {
         this.property = property;
     }

     /**
      * �X�R�[�v��ݒ肷��B
      *
      * @param scope �X�R�[�v
      */
     public void setScope(String scope) {
         this.scope = scope;
     }

     /**
      * �L�[��ݒ肷��B
      *
      * @param key �R�[�h���X�g�̃L�[
      */
     public void setKey(String key) {
         this.key = key;
     }

     /**
      * �R�[�h���X�g����ݒ肷��B
      *
      * @param codeList �R�[�h���X�g��
      */
     public void setCodeList(String codeList) {
         this.codeList = codeList;
     }


    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * <p>
     * �T�[�u���b�g�R���e�L�X�g����AApplicationContext���擾���A
     * &quot;codeList&quot; �����Ŏw�肳�ꂽ id
     * �� CodeListLoader ���擾���A���̒��̃R�[�h���X�g����
     * �l���擾���A�o�͂���B
     * &quot;key&quot; �������w�肳��Ă���΁A���̃L�[�l���擾���A
     * �w�肳��Ȃ���΁A&quot;name&quot; �����Ŏw�肳���bean����
     * �L�[���擾���ėp����B
     *
     * �R�[�h���X�g�������ł��Ȃ��ꍇ��A
     * �L�[�����݂��Ȃ��ꍇ�́A�����o�͂��Ȃ��B
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

        if ("".equals(codeList) || codeList == null) {
            // codeList���w�肳��Ȃ��ꍇ
            log.error("codeList is required.");
            throw new JspTagException("codeList is required.");
        }

        if (key == null && name == null) {
            // key��name���Ɏw�肳��Ȃ��ꍇ
            log.error("key and name is required.");
            throw new JspTagException("key and name is required.");
        }

        String codeKey = null;

        if ((key == null) && (name != null)) {
            if (TagUtil.lookup(pageContext, name, scope) == null) {
                log.error("bean id:" + name + " is not defined.");
                throw new JspTagException("bean id:" + name
                        + " is not defined.");
            }
            Object bean = null;
            try {
                bean = TagUtil.lookup(pageContext, name, property, scope);
            } catch (JspException e) {
                // �������Ȃ�
            }
            if (bean == null) {
                log.error("Cannot get property[" + name + "."
                        + property + "].");
                throw new JspTagException("Cannot get property[" + name + "."
                        + property + "].");
            }
            codeKey = bean.toString();
        } else {
            codeKey = key;
        }

        // �T�[�u���b�g�R���e�L�X�g���AApplicationContext���擾����B
        ServletContext sc = pageContext.getServletContext();
        ApplicationContext context =
            WebApplicationContextUtils.getRequiredWebApplicationContext(sc);

        CodeListLoader loader = null;

        if (context.containsBean(codeList)) {
            try {
                loader = (CodeListLoader) context.getBean(codeList);
            } catch (ClassCastException e) {
                // �擾����Bean��CodeListLoader�ł͂Ȃ��������O���X���[
                String errorMessage = "bean id:" + codeList
                        + " is not instance of CodeListLoader.";
                log.error(errorMessage);
                throw new JspTagException(errorMessage, e);
            }
        }

        JspWriter out = pageContext.getOut();

        if (loader == null) {
            log.error("CodeListLoader:" + codeList + " is not defined.");
            throw new JspTagException("CodeListLoader:" + codeList
                    + " is not defined.");
        }

        // ���P�[�����擾
        Locale locale = RequestUtils.getUserLocale(
                (HttpServletRequest) pageContext.getRequest(),
                Globals.LOCALE_KEY);

        CodeBean[] codeBeanArray = loader.getCodeBeans(locale);
        if (codeBeanArray == null) {
            // codeBeanList��null�̏ꍇ
            if (log.isWarnEnabled()) {
                log.warn("Codebean is null. CodeListLoader(bean id:"
                        + codeList + ")");
            }
            // �������Ȃ�
        } else {
            try {
                // ����Ɏ擾�ł����ꍇ�̓R�[�h���X�g�̒l���o�͂���B
                for (int i = 0; i < codeBeanArray.length; i++) {
                    if (codeKey.equals(codeBeanArray[i].getId())) {
                        out.print(codeBeanArray[i].getName());
                        break;
                    }
                }
                // �������Ȃ�
            } catch (IOException ioe) {
                log.error("", ioe);
                throw new JspTagException(ioe);
            }

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
        this.codeList = null;
        this.key = null;
        this.name = null;
        this.property = null;
        this.scope = null;
    }

}
