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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import jp.terasoluna.fw.web.RequestUtil;

import org.apache.struts.taglib.TagUtils;

/**
 * <p>�g�� <code>form</code> �^�O�B</p>
 *
 * <p>
 *  <code>Struts</code> �̒񋟂��� <code>&lt;html:form&gt;</code> �^�O���g������B
 *  �@�\�Ƃ��āA�A�N�V���� <code>URL</code> �ɃL���b�V�������p�����_��
 *  <code>ID</code> ��ǉ�����B
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p> <code>&lt;html:form&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p> <code>&lt;html:form&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <p> <code>&lt;html:form&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 *
 *
 *
 */
public class FormTag extends org.apache.struts.taglib.html.FormTag {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 5890474956835784840L;

    /**
     * �L���b�V�������p�����_��ID�̃p�����[�^���B
     */
    private static final String RANDOM_ID_KEY = RandomUtil.RANDOM_ID_KEY;

    /**
     * <code>&lt;form&gt;</code>�̊J�n�^�O�������_���p�����[�^�t��
     * ��������B
     *
     * @return form�̊J�n�^�O
     */
    @Override
    protected String renderFormStartElement() {
        HttpServletResponse response =
            (HttpServletResponse) this.pageContext.getResponse();

        StringBuilder results = new StringBuilder("<form");
        results.append(" name=\"");
        results.append(beanName);
        results.append("\"");
        results.append(" method=\"");
        results.append(method == null ? "post" : method);
        results.append("\" action=\"");
        results.append(
            response.encodeURL(
                getActionMappingURL(super.action, super.pageContext)));

        results.append("\"");

        if (styleClass != null) {
            results.append(" class=\"");
            results.append(styleClass);
            results.append("\"");
        }
        if (enctype != null) {
            results.append(" enctype=\"");
            results.append(enctype);
            results.append("\"");
        }
        if (onreset != null) {
            results.append(" onreset=\"");
            results.append(onreset);
            results.append("\"");
        }
        if (onsubmit != null) {
            results.append(" onsubmit=\"");
            results.append(onsubmit);
            results.append("\"");
        }
        if (style != null) {
            results.append(" style=\"");
            results.append(style);
            results.append("\"");
        }
        if (styleId != null) {
            results.append(" id=\"");
            results.append(styleId);
            results.append("\"");
        }
        if (target != null) {
            results.append(" target=\"");
            results.append(target);
            results.append("\"");
        }
        results.append(">");
        return results.toString();
    }

    /**
     * �A�N�V����URL�ɃL���b�V�������p�����_��ID��ǉ�����B
     *
     * @param action �A�N�V�����p�X
     * @param pageContext �y�[�W���
     * @return �����_��ID��t�������A�N�V����URL
     */
    protected String getActionMappingURL(@SuppressWarnings("hiding") String action,
                                         PageContext pageContext) {
        TagUtils tagUtils = TagUtils.getInstance();

        //URL�̎擾�B
        String url =
            tagUtils.getActionMappingURL(action, pageContext);

        //URL�ɂ��łɃ����_��ID���t������Ă���ꍇ�͍폜����B
        url = RequestUtil.deleteUrlParam(url, RANDOM_ID_KEY);

        if (url.indexOf("?") < 0) {
            url = url.concat("?");
        } else {
            url = url.concat("&");
        }

        //URL�Ƀ����_��ID��t�����ĕԋp����B
        return url + RANDOM_ID_KEY + "=" + RandomUtil.generateRandomID();
    }

}
