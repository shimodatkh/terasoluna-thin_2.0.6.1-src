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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  <code>HTML</code> �� <code>body</code> �^�O���g������
 *  <code>body</code> �^�O�̎����N���X�B
 * </p>
 * <p>
 *  <code>PageContext</code> �� <code>&quot;ON_LOAD&quot;</code> ���L�[��
 *  ���ߍ��܂ꂽ�X�N���v�g�� <code>onLoad</code> �C�x���g�����ɒǉ�����B<br>
 *  ���̃^�O�Ő�������<code>HTML &lt;body&gt;</code> �^�O�ł́A<code>onLoad
 *  </code>�C�x���g�������̃X�N���v�g�Ƃ��āAJavaScript�֐��� <code>__onLoad__()
 *  </code>���Ăяo���BJavaScript�֐� <code>__onLoad__()</code> �̒�`�́A
 *  ���̃^�O�Ő������邽�߁AHTML���ɓ�����JavaScript���L�q���Ă͂Ȃ�Ȃ��B
 * </p>
 * <p>
 *  <code>&quot;styleClass&quot;</code>�A<code>&quot;bgcolor&quot;</code>�A
 *  <code>&quot;text&quot;</code>�A<code>&quot;link&quot;</code>�A
 *  <code>&quot;vlink&quot;</code>�A<code>&quot;alink&quot;</code> �Ƃ�����
 *  ���̃^�O�̑����́A���̂܂܂��̃^�O����������HTML�� <code>&lt;body&gt;</code>
 *  �^�O�̑����Ƃ��ēW�J�����B
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p><code>body</code> �^�O�ł́A�ȉ��̑������T�|�[�g����B</p>
 * <br><br>
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
 *    <td> <code>onload</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ��ʕ\�����Ɏ��s����JavaScript�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>onunload</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ��ʃA�����[�h���Ɏ��s����JavaScript�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>styleClass</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �X�^�C���V�[�g�̃N���X���B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>bgcolor</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �w�i�F�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>background</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �w�i�ɐݒ肷��摜�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>text</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �e�L�X�g�����̐F�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>link</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �����N�����̐F�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>vlink</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ���ɑI�����ꂽ�����N�����̐F�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>alink</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     �I�𒆂̃����N�����̐F�B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br><br>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</p>
 * <br>
 * <h5>�g�p���@</h5>
 * <li>JSP</li>
 * <code><pre>
 *  &lt;%
 *    String&nbsp;script=&quot;�C�ӂ̃X�N���v�g��&quot;;
 *    pageContext.setAttribute(&quot;ON_LOAD&quot;,&nbsp;script);
 *  %&gt;
 *  �E�E�E
 *  &lt;ts:body&gt;
 *  �E�E�E
 *  &lt;/ts:body&gt;
 * </pre></code>
 * <li>�������ꂽHTML</li>
 * <code><pre>
 *  &lt;body onLoad="__onLoad__()"&gt;
 *    &lt;script type="text/javascript"&gt;
 *      &lt;!--
 *        function __onLoad__() {
 *          //�L�[��"ON_LOAD"��pageContext�Ɋi�[���ꂽ�X�N���v�g��
 *        }
 *      //-->
 *    &lt;/script&gt;
 *  �E�E�E
 *  &lt;/body&gt;
 * </pre></code>
 *
 * @see jp.terasoluna.fw.web.struts.taglib.MessagesPopupTag
 *
 */
public class BodyTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -3249997220773531400L;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(BodyTag.class);
    
    /**
     * <code>onLoad</code> �C�x���g�̏����ɒǉ�����X�N���v�g�����o�����߂�
     * ���N�G�X�g�����̃L�[�B
     */
    public static final String ON_LOAD_KEY = "ON_LOAD";

    /**
     * ��ʕ\�����Ɏ��s����JavaScript�B
     */
    private String onload = null;

    /**
     * ��ʃA�����[�h���Ɏ��s����JavaScript�B
     */
    private String onunload = null;

    /**
     * �X�^�C���V�[�g�̃N���X���B
     */
    private String styleClass = null;

    /**
     * �w�i�F�B
     */
    private String bgcolor = null;

    /**
     * �w�i�ɐݒ肷��摜�B
     */
    private String background = null;

    /**
     * �e�L�X�g�����̐F�B
     */
    private String text = null;

    /**
     * �����N�����̐F�B
     */
    private String link = null;

    /**
     * ���ɑI�����ꂽ�����N�����̐F�B
     */
    private String vlink = null;

    /**
     * �I�𒆂̃����N�����̐F�B
     */
    private String alink = null;

    /**
     * ��ʕ\�����Ɏ��s����JavaScript��ݒ肷��B
     *
     * @param value JavaScript
     */
    public void setOnload(String value) {
        this.onload = value;
    }

    /**
     * ��ʃA�����[�h���Ɏ��s����JavaScript��ݒ肷��B
     *
     * @param value JavaScript
     */
    public void setOnunload(String value) {
        this.onunload = value;
    }

    /**
     * �X�^�C���V�[�g�̃N���X����ݒ肷��B
     *
     * @param value �N���X��
     */
    public void setStyleClass(String value) {
        this.styleClass = value;
    }

    /**
     * �w�i�F��ݒ肷��B
     *
     * @param value �w�i�F
     */
    public void setBgcolor(String value) {
        this.bgcolor = value;
    }

    /**
     * �w�i�摜��ݒ肷��B
     *
     * @param value �w�i�F
     */
    public void setBackground(String value) {
        this.background = value;
    }

    /**
     * �e�L�X�g�����̐F��ݒ肷��B
     *
     * @param value �e�L�X�g�����̐F
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * �����N�����̐F��ݒ肷��B
     *
     * @param value �����N�����̐F
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * ���ɑI�����ꂽ�����N�����̐F��ݒ肷��B
     *
     * @param value ���ɑI�����ꂽ�����N�����̐F
     */
    public void setVlink(String value) {
        this.vlink = value;
    }

    /**
     * �I�𒆂̃����N�����̐F��ݒ肷��B
     *
     * @param value �I�𒆂̃����N�����̐F
     */
    public void setAlink(String value) {
        this.alink = value;
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
        String onLoadBody = (String) req.getAttribute(ON_LOAD_KEY);

        StringBuilder tag = new StringBuilder();
        tag.append("<body");
        if (onLoadBody != null || onload != null) {
            tag.append(" onLoad=\"__onLoad__()\"");
        }
        if (this.onunload != null) {
            tag.append(" onUnLoad=\"" + this.onunload + "\"");
        }
        if (this.styleClass != null) {
            tag.append(" class=\"" + this.styleClass + "\"");
        }
        if (this.bgcolor != null) {
            tag.append(" bgcolor=\"" + this.bgcolor + "\"");
        }
        if (this.background != null) {
            if (this.background.startsWith("/")) {
                this.background = req.getContextPath() + this.background;
            } else {
                this.background = req.getContextPath() + "/" + this.background;
            }
            tag.append(" background=\"" + this.background + "\"");
        }
        if (this.text != null) {
            tag.append(" text=\"" + this.text + "\"");
        }
        if (this.link != null) {
            tag.append(" link=\"" + this.link + "\"");
        }
        if (this.vlink != null) {
            tag.append(" vlink=\"" + this.vlink + "\"");
        }
        if (this.alink != null) {
            tag.append(" alink=\"" + this.alink + "\"");
        }
        tag.append(">" + System.getProperty("line.separator"));

        StringBuilder func = null;
        if (onLoadBody != null || onload != null) {
            func = new StringBuilder();
            func.append("<script type=\"text/javascript\">"
                        + System.getProperty("line.separator"));
            func.append("<!--" + System.getProperty("line.separator"));
            func.append("function __onLoad__() {"
                        + System.getProperty("line.separator"));
            if (onload != null) {
                func.append("  ");
                func.append(onload);
                if (!onload.endsWith(";")) {
                    func.append(";");
                }
                func.append(System.getProperty("line.separator"));
            }
            if (onLoadBody != null) {
                func.append(onLoadBody);
            }
            func.append("}" + System.getProperty("line.separator"));
            func.append("//-->" + System.getProperty("line.separator"));
            func.append("</script>" + System.getProperty("line.separator"));
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(tag.toString());
            if (func != null) {
                out.print(func.toString());
            }
        } catch (IOException e) {
            log.error("Output failed.");
            throw new JspTagException(e.toString());
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * �^�O�]���I�����ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w���B��� <code>EVAL_PAGE</code>
     * @throws JspException JSP��O
     */
    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("</body>");
        } catch (IOException e) {
            log.error("Output failed");
            throw new JspTagException(e.toString());
        }

        return EVAL_PAGE;
    }

    /**
     * �^�O�n���h��������̏����B
     */
    @Override
    public void release() {
        super.release();
        this.onload = null;
        this.onunload = null;
        this.styleClass = null;
        this.bgcolor = null;
        this.background = null;
        this.text = null;
        this.link = null;
        this.vlink = null;
        this.alink = null;
    }

}
