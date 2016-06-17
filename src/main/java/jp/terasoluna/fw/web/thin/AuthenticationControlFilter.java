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

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import jp.terasoluna.fw.web.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���O�I���ς݂��ǂ����̃`�F�b�N���s���B
 * 
 * <p>
 * ���̃N���X�ł̓u���E�U����̃��N�G�X�g�ɑ΂���t�B���^�������A 
 * Bean��`�t�@�C���Ŏw�肳�ꂽ�C�ӂ�{@link AuthenticationController} 
 * �C���X�^���X�Ƀ��O�I���ς݂��ǂ����̃`�F�b�N�������Ϗ�����B
 * </p>
 * 
 * <h5>���O�I���`�F�b�N�@�\</h5>
 * <p>
 * ���O�I�����K�v�ȃp�X�ւ̃A�N�Z�X���������ꍇ�́A ���[�U�����O�I���ς݂�
 * �ǂ����𔻕ʂ��A���O�I���ς݂ł͂Ȃ������ꍇ�A
 * {@link UnauthenticatedException}���X���[����B
 * </p>
 * <h5>�g�p���@</h5>
 * <p>
 * ���̋@�\���g�p����ɂ� �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j��
 * Bean��`�t�@�C���Ɉȉ��̂悤�ɐݒ肷��B
 * ���̂Ƃ��ABean��`�t�@�C���ɒ�`����id�������A
 * sampleAuthenticationController�ł���&lt;bean&gt;�v�f��
 * class�����ɂ́A{@link AuthenticationController}�C���^�t�F�[�X��
 * ���������N���X��ݒ肷��B<br>
 * </p>
 * �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;
 *     authenticationControlFilter
 *   &lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 *   &lt;/filter-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;controller&lt;/param-name&gt;
 *     &lt;param-value&gt;
 *       "sampleAuthenticationController"
 *     &lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/filter&gt;
 * 
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;authenticationControlFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * 
 * &lt;error-page&gt;
 *   &lt;exception-type&gt;
 *     jp.terasoluna.fw.web.thin.UnauthenticationException
 *   &lt;/exception-type&gt;
 *   &lt;location&gt;/unauthenticatedError.jsp&lt;/location&gt;
 * &lt;/error-page&gt;
 * 
 * </pre></code>
 * 
 * Bean��`�t�@�C��
 * <code><pre>
 * &lt;bean id="sampleAuthenticationController"
 *       class="jp.terasoluna�cSampleAuthenticationController"/&gt;
 * </pre></code>
 * 
 * �Ȃ��ABean��`�t�@�C���ɒ�`����&lt;bean&gt;�v�f��id�������f�t�H���g�l�ł���
 * "authenticationController"�ɐݒ肷��ꍇ�ɂ́A�f�v���C�����g�f�B�X�N���v�^
 * �iweb.xml�j����&lt;filter&gt;�v�f����&lt;init-param&gt;�v�f���ȗ����邱�Ƃ�
 * �ł���B
 * 
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.BlockageController
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 * 
 */
public class AuthenticationControlFilter
        extends AbstractControlFilter<AuthenticationController> {

    /**
     * ���N�G�X�g���t�B���^��ʉ߂������Ƃ��������N�G�X�g�����̃L�[�B
     */
    public static final String AUTHENTICATION_THRU_KEY 
        = "AUTHENTICATION_THRU_KEY";   
    
    /**
     * DI�R���e�i����R���g���[���[�̎����N���X���擾���邽�߂�
     * &lt;bean&gt;�v�f��id�����Ɏg�p�����f�t�H���gid�B
     */
    public static final String DEFAULT_AUTHENTICATION_BEAN_ID
        = "authenticationController";
    
    /**
     * �I�[�Z���e�B�P�[�V�����R���g���[���̐������s�������G���[�R�[�h�B
     */
    private static final String AUTHENTICATION_CONTROLLER_ERROR 
        = "errors.authentication.controller";

    /**
     * ���O�I���ς݃`�F�b�N�������Ϗ�����R���g���[���N���X�B
     */
    private static final Class AUTHENTICATION_CONTROLLER_CLASS 
        = AuthenticationController.class;
   
    /**
     * ���O�N���X�B
     */
    private static Log log 
       = LogFactory
           .getLog(AuthenticationControlFilter.class);

    /**
     * AuthenticationController�C���X�^���X�B
     */
    protected static AuthenticationController controller = null;
    
    /**
     * AuthenticationController�C���X�^���X��߂��B
     * 
     * @return ���̃t�B���^�ɐݒ肳��Ă���AuthenticationController�C���X�^���X
     */
    public static AuthenticationController getAuthenticationController() {
        return controller;
    }
    
    /**
     * �t�B���^���T�[�r�X�J�n��ԂɂȂ�ۂɁA�R���e�i�ɂ���ČĂяo�����B 
     * 
     * �R���e�i�́AFilter���C���X�^���X��������ɁAinit���\�b�h��
     * 1 �񂾂��Ăяo���B<br>
     * Filter�Ƀt�B���^������Ƃ����s����悤�ɗv������ɂ́A
     * init ���\�b�h������� �I�����Ă��Ȃ���΂Ȃ�Ȃ��B
     * init���\�b�h�� ���̂����ꂩ�̏�Ԃ̏ꍇ�A�R���e�i��
     * Filter���T�[�r�X��Ԃɂł��Ȃ��B<br>
     * <ul>
     *  <li>ServletException ���X���[�����B</li>
     *  <li>�R���e�i�ɂ���Ē�`���ꂽ���ԓ��ɁA���A���Ȃ��B</li>
     *  <li>�ݒ肳�ꂽ�R���g���[���̎����N���X�����݂��Ȃ��A 
     *      �܂��͐ݒ�ُ펞�B</li>
     * </ul>
     * 
     * @param config FilterConfig�C���X�^���X�B
     * 
     * @throws javax.servlet.ServletException �������ُ펞�ɃX���[������O�B
     *             
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     * @see jp.terasoluna.fw.web.thin.AbstractControlFilter
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        if (controller == null) {
            controller = getController();
        }
    }

    /**
     * ���O�I���ς݃`�F�b�N���s���B
     * 
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @param chain �t�B���^�`�F�[��
     * 
     * @throws IOException I/O�G���[
     * @throws ServletException �T�[�u���b�g��O
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *                                    javax.servlet.ServletResponse,
     *                                    javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, 
                   ServletException {

        // ���N�G�X�g���t�B���^��ʉ߂������ǂ����𔻒�B
        if (req.getAttribute(AUTHENTICATION_THRU_KEY) == null) {

            // �t�B���^�̒ʉߏ����Z�b�g
            req.setAttribute(AUTHENTICATION_THRU_KEY, "true");
            
            // �ʋƖ��Ɉڂ������ǂ����p�X�`�F�b�N������B
            if (controller.isCheckRequired(req)) {

                // ���O�I���ς݃`�F�b�N
                if (!controller
                    .isAuthenticated(RequestUtil.getPathInfo(req), req)) {
                    if (log.isDebugEnabled()) {
                        log.debug("isAuthenticated() failed.");
                    }
                    throw new UnauthenticatedException();
                }
            }
        }

        // ���̃t�B���^�܂��̓T�[�u���b�g��
        chain.doFilter(req, res);
    }

    /**
     * �A�N�Z�X������s���N���X���������ׂ��C���^�t�F�[�X��Ԃ��B
     * 
     * @return ���̃t�B���^�Ŏg�p����R���g���[���̃N���X
     */
    @Override
    protected Class getControllerClass() {
        return AUTHENTICATION_CONTROLLER_CLASS;
    }

    /**
     * �R���g���[���̐������s�������G���[�R�[�h��Ԃ��B
     * 
     * @return �G���[�R�[�h
     */
    @Override
    protected String getErrorCode() {
        return AUTHENTICATION_CONTROLLER_ERROR;
    }

    /**
     * DI�R���e�i����R���g���[�����擾����ۂ̃f�t�H���g��id��Ԃ��B
     * 
     * @return �f�t�H���g��id�����l
     */
    @Override
    public String getDefaultControllerBeanId() {
        return DEFAULT_AUTHENTICATION_BEAN_ID;
    }
    
}
