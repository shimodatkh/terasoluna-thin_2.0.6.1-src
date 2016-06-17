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
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �G�r�f���X���O���o�͏������s���B
 * 
 * <p>
 * �G�r�f���X���O�o�͂Ƃ́A���N�G�X�g�p�����^�������O�o�͂��邱�Ƃł���B
 * </p>
 * <h5>�g�p���@</h5>
 * ���̋@�\���g�p����ɂ̓f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�Ɉȉ��̂悤��
 * �ݒ肷��B<br>
 * <br>
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;evidenceLogFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.thin.EvidenceLogFilter
 *   &lt;/filter-class&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;evidenceLogFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre></code>
 * 
 *
 */
public class EvidenceLogFilter implements Filter {
    
    /**
     * ���N�G�X�g���t�B���^�[��ʉ߂������Ƃ��������N�G�X�g�����̃L�[�B
     */
    public static final String EVIDENCELOG_THRU_KEY = "EVIDENCELOG_THRU_KEY";
    
    /**
     * ���O�N���X
     */
    private static Log log
        = LogFactory.getLog(EvidenceLogFilter.class);

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
     * <br>
     * �����̃N���X�ł͏����͍s��Ȃ��B
     * 
     * @param config FilterConfig�C���X�^���X�B
     * 
     * @throws javax.servlet.ServletException �������ُ펞�ɃX���[������O�B
     *             
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        //���ɂȂ�
    }
    
    /**
     * �G�r�f���X���O���o�͂���B
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
        if (req.getAttribute(EVIDENCELOG_THRU_KEY) == null) {

            req.setAttribute(EVIDENCELOG_THRU_KEY, "true");

            evidenceLog("--------------------------------------------");

            // ���N�G�X�gURI���o��
            evidenceLog(
                "RequestURI = " + ((HttpServletRequest) req).getRequestURI());

            // �p�����[�^�ꗗ���o��
            evidenceLog("Parameters = {");
            Map paramMap = ((HttpServletRequest) req).getParameterMap();
            Iterator iter = paramMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = paramMap.get(key);
                if (value == null) {
                    evidenceLog("  " + key + " = null");
                } else if (value.getClass().isArray()) {
                    Object[] values = (Object[]) value;
                    for (int i = 0; i < values.length; i++) {
                        String valueView = "null";
                        if (values[i] != null) {
                            valueView = values[i].toString();
                        }
                        evidenceLog("  " + key + "[" + i + "] = " + valueView);
                    }
                } else {
                    evidenceLog("  " + key + " = " + value.toString());
                }
            }
            evidenceLog("}");

            evidenceLog("--------------------------------------------");

        }

        // ���̃t�B���^�܂��̓T�[�u���b�g��
        chain.doFilter(req, res);
    }


    /**
     * �G�r�f���X���O���o�͂���B
     *
     * @param s ���O�ɏo�͂��镶����
     */
    private void evidenceLog(String s) {
        if (log.isDebugEnabled()) {
            log.debug("**** EVIDENCE ***: " + s);
        }
    }
    
    /**
     * �t�B���^�������ɌĂяo�����B<br>
     * ���̃N���X�ł͏����͍s��Ȃ��B
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // ���ɂȂ�
    }

}
