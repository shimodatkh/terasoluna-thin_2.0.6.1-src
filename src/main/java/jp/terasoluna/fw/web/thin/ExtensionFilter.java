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

package jp.terasoluna.fw.web.thin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.util.StringUtil;
import jp.terasoluna.fw.web.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �g���q�`�F�b�N���s���B
 *
 * <p>
 * �w�肳�ꂽ�֎~�g���q�����p�X�ւ̃A�N�Z�X�v���ɑ΂��ẮA
 * SC_NOT_FOUND(404)�G���[��Ԃ��B����ɂ��A�t�@�C��
 * �ւ̒��ڃA�N�Z�X���֎~����B<br>
 * �֎~�g���q�ւ̃A�N�Z�X�������s���ꍇ�ł��̃`�F�b�N�Ώۂ���͂�������
 * ���ʂȃp�X������΁A�v���p�e�B�t�@�C����restrictionEscape.
 * �Ƃ����v���t�B�N�X�������������L�[�Ƃ��Ē�`���邱�ƂŃ`�F�b�N��Ώۂ�
 * �p�X��1���畡����`�ł���B<br>
 * �܂��A���ڃA�N�Z�X�֎~�Ώۂ̊g���q��
 * access.control.prohibited.extension. �Ƃ����v���t�B�N�X�������L�[����
 * 1����w�肷�邱�ƁB
 * </p>
 *
 * <h5>�v���p�e�B�t�@�C���ݒ��</h5>
 * <p>
 * <code><pre>
 * # �g���q�����`�F�b�N�ΏۊO�ɂ���p�X��1���珇�Ɏw�肷��B
 * restrictionEscape.1=/sample/logon/index.jsp
 * restrictionEscape.2=/sample/error/error.jsp
 *
 * # �g���q���Ƃ̒��ڃA�N�Z�X�����`�F�b�N�Ώۂ̊g���q��1���珇�Ɏw�肷��B
 * access.control.prohibited.extension.1=.jsp
 * access.control.prohibited.extension.2=.csv
 * access.control.prohibited.extension.3=.pdf
 * </pre></code>
 * </p>
 * <h5>�g�p���@</h5>
 * <p>
 * ���̋@�\���g�p����ɂ� �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j��
 * �ȉ��̂悤�ɐݒ肷��B<br>
 * <br>
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;extensionFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.thin.ExtensionFilter
 *   &lt;/filter-class&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;extensionFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre></code>
 * </p>
 */
public class ExtensionFilter implements Filter {

    /**
     * ���N�G�X�g���t�B���^��ʉ߂������Ƃ��������N�G�X�g�����̃L�[�B
     */
    public static final String EXTENSION_THRU_KEY = "EXTENSION_THRU_KEY";

    /**
     * ApplicationResource����擾����A�g���q�����`�F�b�N��ΏۊO�ɂ���
     * �p�X�̃L�[�ɂ���v���t�B�b�N�X�B
     */
    public static final String RESTRICTION_ESCAPE_PREFIX = "restrictionEscape.";

    /**
     * ���O�N���X�B
     */
    private static Log log
        = LogFactory.getLog(ExtensionFilter.class);

    /**
     * ApplicationResource����擾����A���ڃA�N�Z�X���֎~����g���q��
     * �L�[�ɂ���v���t�B�N�X�B
     */
    private static final String PROHIBITED_EXTENSION_PREFIX 
        = "access.control.prohibited.extension.";

    /**
     * Web�u���E�U����̒��ڃA�N�Z�X���֎~����g���q�̃��X�g�B
     */
    private List<String> prohibitedExtensionList
        = new ArrayList<String>();

    /**
     * �g���q�����`�F�b�N�̑ΏۊO�ƂȂ�p�X�̃��X�g�B
     */
    private List<String> restrictionEscapePaths
        = new ArrayList<String>();

    /**
     * �t�B���^���T�[�r�X�J�n��ԂɂȂ�ۂɁA�R���e�i�ɂ���ČĂяo�����B
     *  
     * �R���e�i�́AFilter���C���X�^���X��������ɁAinit ���\�b�h��
     * 1 �񂾂��Ăяo���B<br>
     * Filter�Ƀt�B���^������Ƃ����s����悤�ɗv������ɂ́A
     * init ���\�b�h������� �I�����Ă��Ȃ���΂Ȃ�Ȃ��B
     * init���\�b�h�� ���̂����ꂩ�̏�Ԃ̏ꍇ�A�R���e�i��
     * Filter���T�[�r�X��Ԃɂł��Ȃ��B<br>
     * <ul>
     *  <li>ServletException ���X���[�����B </li>
     *  <li>�R���e�i�ɂ���Ē�`���ꂽ���ԓ��ɁA���A���Ȃ��B</li>
     * </ul>
     * 
     * @param config FilterConfig�C���X�^���X�B
     * 
     * @throws javax.servlet.ServletException �������ُ펞�ɃX���[������O�B
     *             
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {

        //���ڃA�N�Z�X���֎~����g���q�̃��X�g���擾����B
        for (int i = 1;; i++) {
            String extension 
                = PropertyUtil.getProperty(PROHIBITED_EXTENSION_PREFIX + i);
            
            // ���ڃA�N�Z�X�֎~�g���q���Ȃ���΁A���邢�͐������r�؂��ΏI��
            if (extension == null) {
                break;
            }
            // �g���q��.�Ŏn�܂��Ă��Ȃ��Ƃ���.��t������
            if (!extension.startsWith(".")) {
                extension = "." + extension;
            }
            if (log.isDebugEnabled()) {
                log.debug("prohibitedExtension:" + extension);
            }
            prohibitedExtensionList.add(extension);
        }
        //���ڃA�N�Z�X�֎~�������s��Ȃ��p�X���擾����B
        for (int i = 1;; i++) {
            String extensionCheckEscapePath 
                = PropertyUtil.getProperty(RESTRICTION_ESCAPE_PREFIX + i);
            
            // ���ڃA�N�Z�X�`�F�b�N�����Ȃ��p�X���Ȃ��A
            // ���邢�͐������r�؂��ΏI��
            if (extensionCheckEscapePath == null) {
                break;
            }
            if (log.isDebugEnabled()) {
                log.debug("extensionCheckEscapePath:["
                          + extensionCheckEscapePath + "]");
            }
            restrictionEscapePaths.add(extensionCheckEscapePath);
        }
    }


    /**
     * �g���q�`�F�b�N���s���B
     *
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @param chain �t�B���^�`�F�[��
     * 
     * @throws IOException I/O�G���[
     * @throws ServletException �T�[�u���b�g��O
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        //���N�G�X�g���t�B���^��ʉ߂������ǂ����𔻒�B
        if (req.getAttribute(EXTENSION_THRU_KEY) == null) {

            //�t�B���^�̒ʉߏ����Z�b�g
            req.setAttribute(EXTENSION_THRU_KEY, "true");

            //���N�G�X�g�p�X���g���q�`�F�b�N���s��Ȃ��p�X���X�g��
            //��v���Ă����ꍇ�͏������I��
            String pathInfo = RequestUtil.getPathInfo(req);
            if (pathInfo != null
                && !restrictionEscapePaths.contains(pathInfo)) {

                //�g���q�`�F�b�N���s��
                //���N�G�X�g�p�X�̊g���q���擾����B
                String extension = StringUtil.getExtension(pathInfo);
                if (prohibitedExtensionList.contains(extension)) {

                    if (log.isDebugEnabled()) {
                        log.debug("requestURI[" + pathInfo
                                  + "] has prohibited extension");
                    }

                    // HTTP�G���[404��Ԃ�
                    ((HttpServletResponse) res)
                    .sendError(HttpServletResponse.SC_NOT_FOUND);
                    return; // �ȍ~�͎��s���Ȃ�
                }
            }
        }

        // ���̃t�B���^�܂��̓T�[�u���b�g��
        chain.doFilter(req, res);
    }

    /**
     * �t�B���^�������ɌĂяo�����B<br>
     * ���̃N���X�ł͏����͍s�Ȃ�Ȃ��B
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // ���ɂȂ�
    }

}
