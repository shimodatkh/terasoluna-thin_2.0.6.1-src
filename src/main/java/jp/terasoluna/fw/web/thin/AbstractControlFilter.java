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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * bean�Ƃ��Ď������ꂽ�R���g���[����p���ăA�N�Z�X������s�����ۃN���X�B
 * 
 * <p>
 * ���̃N���X�́ADI�R���e�i����擾����bean��p���ăA�N�Z�X������s���N���X��
 * ��ʃN���X�ł���B<br>
 * </p>
 * 
 * <h5>�g�p���@</h5>
 * <p>
 * ���̃N���X�̎����N���X���g�p����ɂ� �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j
 * ��Bean��`�t�@�C���Ɉȉ��̂悤�ɐݒ肷��B
 * ���̂Ƃ��ABean��`�t�@�C���ɒ�`����id�������A
 * sampleXxxController�ł���&lt;bean&gt;�v�f��
 * class�����ɂ́A�e�t�B���^���w�肷��C���^�t�F�[�X�i�����ł�xxxController�j��
 * ���������N���X�i�����ł�SampleXxxController�j��ݒ肷��B<br>
 * ��Xxx�͊e�@�\��񋟂���Filter�ɂ���ĕς��B<br>
 *</p>
 * �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j<pre><code>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;
 *     xxxControlFilter
 *   &lt;/filter-name&gt;
 *   &lt;filter-class&gt;
 *     jp.terasoluna.sample.XxxControlFilter
 *   &lt;/filter-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;controller&lt;/param-name&gt;
 *     &lt;param-value&gt;
 *       "sampleXxxController"
 *     &lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/filter&gt;
 *
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;xxxControlFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * 
 * &lt;error-page&gt;
 *   &lt;exception-type&gt;
 *     jp.terasoluna.sample.XxxException
 *   &lt;/exception-type&gt;
 *   &lt;location&gt;/XxxError.jsp&lt;/location&gt;
 * &lt;/error-page&gt;
 * </pre></code>
 * 
 * Bean��`�t�@�C��
 * <pre><code>
 * &lt;bean id=&quot;sampleXxxController&quot;
 *       class=&quot;jp.terasoluna�cSampleXxxController&quot; /&gt;
 * </code></pre>
 * 
 * <p>
 * �Ȃ��A�t�B���^�ɂ���ẮABean��`�t�@�C���ɒ�`����&lt;bean&gt;�v�f��
 * id�����Ƀf�t�H���g�l��p�ӂ��Ă���B
 * ���̏ꍇ�́A�f�v���C�����g�f�B�X�N���v�^�iweb.xml�j����&lt;filter&gt;�v�f����
 * &lt;init-param&gt;�v�f���ȗ����邱�Ƃ��ł���B
 * </p>
 * 
 * @param <E> �R���g���[���N���X���w�肷��B
 * 
 */
public abstract class AbstractControlFilter<E> implements Filter {
   
  
    /**
     * ���O�N���X�B
     */
    private static Log log 
        = LogFactory.getLog(AbstractControlFilter.class);

    /**
     * �t�B���^�ݒ���B
     */
    protected FilterConfig config = null;
   
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
     */
    public void init(FilterConfig config) throws ServletException {
        this.setConfig(config);
    }
    
    /**
     * �t�B���^�ݒ����ݒ肷��B
     * 
     * @param config �t�B���^�ݒ���
     */
    protected void setConfig(FilterConfig config) {
        if (log.isDebugEnabled()) {
            log.debug("setConfig() called.");
        }
        this.config = config;
    }
    
    /**
     * DI�R���e�i����R���g���[���C���X�^���X���擾���Ă���B
     * 
     * @return E �擾�����R���g���[���C���X�^���X
     */
    @SuppressWarnings("unchecked")
    protected E getController() {

        if (log.isDebugEnabled()) {
            log.debug("setController() called.");
        }
        
        // WebApplicationContext�擾
        WebApplicationContext wac 
            = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());


        // �t�B���^�̏����p�����[�^�Ƃ���Bean��`�t�@�C����
        // id�������n����Ă��邩
        String controllerId = config.getInitParameter("controller");
        if (controllerId == null || "".equals(controllerId)) {
            if (log.isDebugEnabled()) {
                log.debug("init parameter 'controller' isn't defined or "
                          + "empty");
            }
            controllerId = getDefaultControllerBeanId();
        }
        
        // �R���g���[���Ƃ��Ē�`���ꂽbean id��\��
        if (log.isDebugEnabled()) {
            log.debug("controller bean id = \"" + controllerId + "\"");
        }
        
        // DI�R���e�i����R���g���[���C���X�^���X�擾
        E controller = null;
        try {    
            controller = (E) wac.getBean(controllerId, getControllerClass());
        } catch (NoSuchBeanDefinitionException e) {
            // Bean��`�t�@�C���Ɏw�肳��Ă���Bean����`����Ă��Ȃ��ꍇ
            log.error("not found " + controllerId + ". "
                      + "controller bean not defined in Beans definition file.",
                      e);
            throw new SystemException(e, getErrorCode());
        } catch (BeanNotOfRequiredTypeException e) {
            // Bean��`�t�@�C���Ɏw�肳��Ă���Bean���A�w�肳��Ă���N���X��
            // ���̎q���N���X�ł͂Ȃ��ꍇ
            log.error("controller not implemented " 
                      + getControllerClass().toString() + ".",
                      e);
            throw new SystemException(e, getErrorCode());
        } catch (BeansException e) {
            // �C���X�^���X�����Ɏ��s�����ꍇ
            log.error("bean generation failed.", e);
            throw new SystemException(e, getErrorCode());
        }
        
        return controller;
    }
    
    /**
     * �A�N�Z�X������s���N���X���������ׂ��C���^�t�F�[�X��Ԃ��B
     * 
     * @return ���̃t�B���^�Ŏg�p����R���g���[���̃N���X
     */
    protected abstract Class getControllerClass();
    
    /**
     * �R���g���[���̐������s�������G���[�R�[�h��Ԃ��B
     * 
     * @return �G���[�R�[�h
     */
    protected abstract String getErrorCode();

    /**
     * DI�R���e�i����R���g���[�����擾����ۂ̃f�t�H���g��id��Ԃ��B
     * 
     * @return �f�t�H���g��id�����l
     */
    public abstract String getDefaultControllerBeanId();

    /**
     * �A�N�Z�X������s���B
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
    public abstract void doFilter(ServletRequest req, 
                         ServletResponse res,
                         FilterChain chain) 
            throws IOException, 
                   ServletException;


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
