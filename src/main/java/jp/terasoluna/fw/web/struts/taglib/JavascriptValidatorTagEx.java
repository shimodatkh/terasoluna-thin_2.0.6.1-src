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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import jp.terasoluna.fw.web.struts.form.FieldChecksEx;

import org.apache.struts.taglib.html.JavascriptValidatorTag;

/**
 * Struts���񋟂���AJavascriptValidatorTag�N���X���g�������N���X�B
 * �v���p�e�B�t�@�C������擾�������p�J�i�A�S�p�J�i�������
 * Javascript�̕ϐ��Ƃ��ďo�͂���B
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p> <code>&lt;html:javascript&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class JavascriptValidatorTagEx extends JavascriptValidatorTag {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 879915691393426820L;

    /**
     * �^�O�̕]���J�n���ɌĂ΂��B
     * ���p�J�i�A�S�p�J�i�̃��X�g��FieldCheckEx�N���X�̕ϐ�����
     * �擾���AJavaScript�̕ϐ��Ƃ��ďo�͂���B
     * @return ��������
     * @throws JspException �������s���̗�O
     */
    @Override
    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(this.renderKanaList());

        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return super.doStartTag();
    }

    /**
     * FieldCheckEx�N���X���甼�p�J�i�A�S�p�J�i��������擾���A
     * JavaScript�̕ϐ���`�̕�����Ƃ��ĕҏW���A�ԋp����B
     *
     * @return ���p�J�i�A�S�p�J�i�̕ϐ���`������
     */
    protected String renderKanaList() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.renderStartElement());
        if ("true".equalsIgnoreCase(htmlComment)) {
            builder.append(HTML_BEGIN_COMMENT);
        }
        builder.append("var hankakuKanaList = \"");
        builder.append(FieldChecksEx.getHankakuKanaList() + "\";" + "\n");
        builder.append("var zenkakuKanaList = \"");
        builder.append(FieldChecksEx.getZenkakuKanaList() + "\";" + "\n");
        builder.append(super.getJavascriptEnd());
        return builder.toString();
    }

}
