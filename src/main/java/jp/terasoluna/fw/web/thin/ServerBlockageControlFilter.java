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
 * �T�[�o�Ǐ�Ԃ��ǂ����̃`�F�b�N���s���B
 * 
 * <p>
 * ���̃N���X�ł̓u���E�U����̃��N�G�X�g�ɑ΂���t�B���^�������A 
 * �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j��
 * �w�肳�ꂽ�C�ӂ�{@link ServerBlockageController}�C���X�^���X���Ăяo���A 
 * �w�肳�ꂽ�p�X���Ɩ��Ǐ�Ԃ��ǂ����̃`�F�b�N�������Ϗ�����B
 * </p>
 * 
 * <h5>�T�[�o�ǃ`�F�b�N�@�\</h5>
 * <p>
 * �T�[�o�Ǐ�Ԃ������ꍇ�́A{@link ServerBlockageException}���X���[����B
 * </p>
 * <h5>�g�p���@</h5>
 * <p>
 * ���̋@�\���g�p����ɂ̓f�v���C�����g�f�B�X�N���v�^�iweb.xml�j��
 * Bean��`�t�@�C���Ɉȉ��̂悤�ɐݒ肷��B
 * ���̂Ƃ��ABean��`�t�@�C���ɒ�`����id�������A
 * sampleServerBlockageController�ł���&lt;bean&gt;�v�f��
 * class�����ɂ́A{@link ServerBlockageController}�C���^�t�F�[�X��
 * ���������N���X��ݒ肷��B<br>
 * <br>
 * �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j
 * <code><pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;serverBlockageControlFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.fw.web.common.ServerBlockageControlFilter
 *   &lt;/filter-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;controller&lt;/param-name&gt;
 *     &lt;param-value&gt;
 *       "sampleServerBlockageController"
 *     &lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;serverBlockageControlFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre></code>
 * 
 * Bean��`�t�@�C��
 * <code><pre>
 * &lt;bean id="sampleServerBlockageController"
 *       class="jp.terasoluna�cSampleServerBlockageController" /&gt;
 * </pre></code>
 * 
 * �Ȃ��ABean��`�t�@�C���ɒ�`����&lt;bean&gt;�v�f��id�������f�t�H���g�l�ł���
 * "serverBlockageController"�ɐݒ肷��ꍇ�ɂ́A�f�v���C�����g�f�B�X�N���v�^
 * �iweb.xml�j����&lt;filter&gt;�v�f����&lt;init-param&gt;�v�f���ȗ����邱�Ƃ�
 * �ł���B
 *  
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.BlockageController
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 * 
 */
public class ServerBlockageControlFilter 
        extends AbstractControlFilter<ServerBlockageController> {
    
    /**
     * ���N�G�X�g���t�B���^��ʉ߂������Ƃ��������N�G�X�g�����̃L�[�B
     */
    public static final String SERVER_BLOCKAGE_THRU_KEY 
        = "SERVER_BLOCKAGE_THRU_KEY";   
    
    /**
     * DI�R���e�i����R���g���[���[�̎����N���X���擾���邽�߂�
     * &lt;bean&gt;�v�f��id�����Ɏg�p�����f�t�H���gid�B
     */
    public static final String DEFAULT_SERVER_BLOCKAGE_BEAN_ID
        = "serverBlockageController";
    
    /**
     * �T�[�o�[�ǃR���g���[���̐������s�������G���[�R�[�h�B
     */
    private static final String SERVER_BLOCKAGE_CONTROLLER_ERROR 
        = "errors.server.blockage.controller";

    /**
     * �T�[�o�[�Ǐ������Ϗ�����R���g���[���N���X�B
     */
    private static final Class SERVER_BLOCKAGE_CONTROLLER_CLASS 
        = ServerBlockageController.class;
   
    /**
     * ���O�N���X�B
     */
    private static Log log 
       = LogFactory
           .getLog(ServerBlockageControlFilter.class);

    /**
     * ServerBlockageController�C���X�^���X�B
     */
    protected static ServerBlockageController controller = null;

    /**
     * ServerBlockageController ��Ԃ��B
     * 
     * @return ServerBlockageController
     */
    public static ServerBlockageController getServerBlockageController() {
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
     * �T�[�o�ǃ`�F�b�N���s���B
     * 
     * @param req
     *            HTTP���N�G�X�g
     * @param res
     *            HTTP���X�|���X
     * @param chain
     *            �t�B���^�`�F�[��
     * @throws IOException
     *             I/O�G���[
     * @throws ServletException
     *             �T�[�u���b�g��O
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
        if (req.getAttribute(SERVER_BLOCKAGE_THRU_KEY) == null) {

            // �t�B���^�̒ʉߏ����Z�b�g
            req.setAttribute(SERVER_BLOCKAGE_THRU_KEY, "true");

            // �T�[�o�ǃ`�F�b�N
            if (controller
                .isBlockaded(RequestUtil.getPathInfo(req))) {
                if (log.isDebugEnabled()) {
                    log.debug("isBlockaded() failed.");
                }
                throw new ServerBlockageException();
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
        return SERVER_BLOCKAGE_CONTROLLER_CLASS;
    }

    /**
     * �R���g���[���̐������s�������G���[�R�[�h��Ԃ��B
     * 
     * @return �G���[�R�[�h
     */
    @Override
    protected String getErrorCode() {
        return SERVER_BLOCKAGE_CONTROLLER_ERROR;
    }

    /**
     * DI�R���e�i����R���g���[�����擾����ۂ̃f�t�H���g��id��Ԃ��B
     * 
     * @return �f�t�H���g��id�����l
     */
    @Override
    public String getDefaultControllerBeanId() {
        return DEFAULT_SERVER_BLOCKAGE_BEAN_ID;
    }
    
}
