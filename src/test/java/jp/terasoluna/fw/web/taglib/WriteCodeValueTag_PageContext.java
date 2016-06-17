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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import org.springframework.mock.web.MockPageContext;

public class WriteCodeValueTag_PageContext extends MockPageContext {

    public JspWriter out = null;

    @Override
    public JspWriter getOut() {
        return out;
    }

    public WriteCodeValueTag_PageContext() {
        super();
        // TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
    }

    public WriteCodeValueTag_PageContext(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, ServletConfig servletConfig) {
        super(servletContext, request, response, servletConfig);
        // TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
    }

    public WriteCodeValueTag_PageContext(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
        super(servletContext, request, response);
        // TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
    }

    public WriteCodeValueTag_PageContext(ServletContext servletContext, HttpServletRequest request) {
        super(servletContext, request);
        // TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
    }

    public WriteCodeValueTag_PageContext(ServletContext servletContext) {
        super(servletContext);
        // TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
    }

}
