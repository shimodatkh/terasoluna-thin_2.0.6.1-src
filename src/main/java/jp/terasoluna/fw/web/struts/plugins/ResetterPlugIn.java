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

package jp.terasoluna.fw.web.struts.plugins;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.web.struts.reset.Resetter;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.fw.web.struts.reset.ResetterResources;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.digester.xmlrules.XmlLoadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.xml.sax.SAXException;

/**
 * �t�H�[���̃��Z�b�g�ݒ�����[�h����v���O�C���B
 *   
 * PlugIn�@�\���g�p���A�T�[�u���b�g���������Ƀt�H�[���̃��Z�b�g�@�\��
 * �ݒ��ǂݍ��݁A�T�[�u���b�g�R���e�L�X�g�ɕۑ�����B
 *   
 * <p>
 * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)�̐ݒ����
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterResources}
 * �̃C���X�^���X�Ƃ��ăT�[�u���b�g�R���e�L�X�g�ɕۑ������B<br>
 * ���s���ɂ́A
 * {@link jp.terasoluna.fw.web.struts.form.FormEx}#reset()
 * ���\�b�h����C�ӂ�
 * {@link jp.terasoluna.fw.web.struts.reset.Resetter}
 * ���Ăяo����A
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterResources}
 * �Ɋi�[����Ă���ݒ���ɏ]���ă��Z�b�g�������s�Ȃ��B<br>
 * ���Z�b�g�������Ϗ�����N���X�� �A
 * {@link jp.terasoluna.fw.web.struts.reset.Resetter}
 * �����������C�ӂ̃N���X�B<br>
 *   <br><br>
 * </p>
 *
 * <strong>�g�p���@</strong><br>
 * ���̋@�\���g�p����ɂ� Struts�ݒ�t�@�C��(struts-config.xml)
 * �Ɉȉ��̂悤�ɐݒ肷��B<br>
 * resetter �̓��Z�b�g�������s�Ȃ������N���X
 * �i�� resetter �͏ȗ��B�ȗ����̓f�t�H���g���g�p�B
 *   �f�t�H���g="jp.terasoluna.fw.web.struts.reset.ResetterImpl")
 * resources �ɂ̓t�H�[�����Z�b�g��`�t�@�C��(reset.xml)�A
 * digesterRules�ɂ́A�t�H�[�����Z�b�g���[����`�t�@�C��(reset-rules.xml)
 * ���w�肷��B
 * �idigesterRules�͏ȗ��B�j<br>
 * <code><pre>
 * &lt;plug-in className="jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn"&gt;
 *   &lt;set-property
 *     property="resetter"
 *     value="jp.terasoluna.fw.web.struts.reset.ResetterImpl"/&gt;
 *   &lt;set-property
 *     property="resources"
 *     value="/WEB-INF/reset.xml"/&gt;
 *   &lt;set-property
 *     property="digesterRules"
 *     value="/WEB-INF/reset-rules.xml"/&gt;
 * &lt;/plug-in&gt;
 * </pre></code><br>
 * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)�ɂ̓A�N�V�������Ƃ�
 * ���Z�b�g�Ώۃt�B�[���h��ݒ肷��B<br>
 * �w��͈̓��Z�b�g�@�\���g�p����ꍇ�́Aselect ������
 * true �ɐݒ肷��B(�ڍׂɂ��Ă�
 * {@link jp.terasoluna.fw.web.struts.reset.Resetter}
 * ���Q�ƁB)
 * <code><pre>
 * &lt;reset&gt;
 *   &lt;action path="/resetAction"&gt;
 *     &lt;property-reset name="field1" /&gt;
 *     &lt;property-reset name="field2" select="true" /&gt;
 *   &lt;/action&gt;
 *   �E�E�E
 * &lt;/reset&gt;
 * </pre></code>
 * <br>
 * <p>
 * ���J���ʎq�A�����DTD��URL��ύX����ꍇ�́A<br>
 * getPublicIdentifier()��getDtdUrl()���I�[�o�[���C�h���邱�ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * 
 */
public class ResetterPlugIn implements PlugIn {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(ResetterPlugIn.class);

    /**
     * �f�t�H���g�� �t�H�[�����Z�b�g��`�t�@�C��(reset.xml) �̃p�X�B
     */
    private static final String DIGESTER_RULES_PATH =
        "/WEB-INF/reset-rules.xml";

    /**
     * �t�H�[�����Z�b�g���[����`�t�@�C��(reset-rules.xml) �̏���ݒ�ς݂�
     * Digester�C���X�^���X�̎Q��
     */
    private static Digester digester = null;

    /**
     * �f�t�H���g���Z�b�^�N���X�̊��S�C���N���X��
     */
    private static final String DEFAULT_RESETTER =
        ResetterImpl.class.getName();

    /**
     * DTD�̌��J���ʎq�B
     */
    private String publicIdentifier =
        "-//NTTDATA//DTD TERASOLUNA for Spring reset 1.0//JA";

    /**
     * DTD�p�X�B
     */
    private String dtdUrl =
        "/jp/terasoluna/fw/web/struts/plugins/dtd/reset.dtd";

    /**
     * ���Z�b�^�N���X�̊��S�C���N���X���B
     */
    private String resetter = null;

    /**
     * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml) �̃p�X�B
     */
    private String resourcesPath = null;

    /**
     * �t�H�[�����Z�b�g���[����`�t�@�C��(reset-rules.xml) �̃p�X�B
     */
    private String digesterRules = null;

    /**
     * �I���������B
     */
    public void destroy() {
        // �������Ȃ�
    }

    /**
     * �������������B
     *
     * @param servlet ����PlugIn���N������ ActionServlet�B
     * @param config ���� PlugIn �̑����� ModuleConfig�B
     * @exception ServletException ���������ɔ���������O�����b�v������O�B
     */
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        if (log.isDebugEnabled()) {
            log.debug("init() called.");
        }

        // Resetter�̏���������
        initResetter(servlet, config);

        // ResetterResources�̏���������
        initResources(servlet, config);
    }

    /**
     * ���Z�b�g�����N���X�̏����������B
     * 
     * Struts�ݒ�t�@�C��(struts-config.xml)
     * �ɐݒ肵�����Z�b�g�����N���X���擾����B
     * ���ݒ�̏ꍇ�̓f�t�H���g���Z�b�g�����N���X���擾����B
     * @param servlet ���̃v���O�C�����N�������T�[�u���b�g�B
     * @param config ���W���[���R���t�B�O
     * @exception ServletException ���Z�b�g�����N���X�̏������������ɔ��������O
     */
    protected void initResetter(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        // Resetter�̃C���X�^���X���i�[����ϐ�
        Resetter resetterObj = null;

        try {
            if (this.resetter == null || "".equals(this.resetter)) {
                resetterObj = (Resetter) ClassUtil.create(DEFAULT_RESETTER);
            } else {
                resetterObj = (Resetter) ClassUtil.create(this.resetter);
            }
        } catch (ClassLoadException e) {
            log.error("", e);
            throw new ServletException(e);
        }

        // Resetter���T�[�u���b�g�R���e�L�X�g�ɒǉ�
        servlet.getServletContext().setAttribute(
            Resetter.RESETTER_KEY + config.getPrefix(),
            resetterObj);
    }

    /**
     * ���Z�b�g�����������B
     * 
     * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)�A
     * �t�H�[�����Z�b�g���[����`�t�@�C��(reset-rules.xml)�𗘗p���āA
     * �ݒ����
     * {@link 
     *   jp.terasoluna.fw.web.struts.reset.ResetterResources}
     * �ɓǂݍ��ށB
     *
     * @param servlet ���̃v���O�C�����N�������T�[�u���b�g�B
     * @param config ���W���[���R���t�B�O
     * @exception ServletException ���\�[�X�t�@�C����������Ȃ�����
     * ���������O
     */
    protected void initResources(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        if (this.resourcesPath == null || "".equals(this.resourcesPath)) {
            log.error("resources file location is not specified");
            throw new ServletException(
                "resources file location is not specified");
        }
        StringTokenizer st = new StringTokenizer(resourcesPath, ",");
        List<String> pathList = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String path = st.nextToken();
            path = path.trim();
            if (log.isDebugEnabled()) {
                log.debug("reset file=" + path);
            }
            pathList.add(path);
        }
        if (digester == null) {
            if (digesterRules == null) {
                // ���[���t�@�C�����ݒ�̂Ƃ��A�f�t�H���g�̃��[���t�@�C����
                // �ݒ肷��B
                digesterRules = DIGESTER_RULES_PATH;
            }
            // Digester�̐���
            try {
                digester =
                    DigesterLoader.createDigester(
                        servlet.getServletContext().getResource(
                            digesterRules));
                            digester.setValidating(true);
            } catch (MalformedURLException e) {
                log.error("", e);
                throw new ServletException(e);
            } catch (XmlLoadException e) {
                log.error("", e);
                throw new ServletException(e);
            }
        }
        ResetterResources resetterResources = new ResetterResources();
        try {
            for (int i = 0; i < pathList.size(); i++) {
                digester.push(resetterResources);
        	    URL url = this.getClass().getResource(getDtdUrl());
        	    if (url != null) {
        	        digester.register(getPublicIdentifier(), url.toString());
        	    }
                digester.parse(
                    servlet.getServletContext().getResourceAsStream(
                    pathList.get(i)));
            }
        } catch (IOException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (SAXException e) {
            log.error("", e);
            throw new ServletException(e);
        }
        // ResetterResources���T�[�u���b�g�R���e�L�X�g�ɒǉ�
        servlet.getServletContext().setAttribute(
            ResetterResources.RESETTER_RESOURCES_KEY + config.getPrefix(),
            resetterResources);
    }

    /**
     * ���J���ʎq��ԋp����B
     * ���J���ʎq��ύX����ꍇ�́A���̃��\�b�h���I�[�o�[���C�h����B
     * @return ���J���ʎq
     */
    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    /**
     * DTD��URL��ԋp����B
     * DTD��URL��ύX����ꍇ�́A���̃��\�b�h���I�[�o�[���C�h����B
     * @return DTD��URL
     */
    public String getDtdUrl(){
        return dtdUrl;
    }

    /**
     * Struts�ݒ�t�@�C��(struts-config.xml)
     * �ɐݒ肳��Ă��郊�Z�b�^�̊��S�C������
     * �ݒ肷��B
     * 
     * @param string
     * ���Z�b�^�̊��S�C����
     */
    public void setResetter(String string) {
        resetter = string;
    }

    /**
     * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)�̃p�X����ݒ肷��B
     * 
     * @param string
     * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)�̃p�X��
     */
    public void setResources(String string) {
        resourcesPath = string;
    }

    /**
     * �t�H�[�����Z�b�g���[����`�t�@�C��(reset-rules.xml)��ݒ肷��B
     *
     * @param digesterRules �t�H�[�����Z�b�g���[����`�t�@�C��(reset-rules.xml)
     * �̃p�X��
     */
    public void setDigesterRules(String digesterRules) {
        this.digesterRules = digesterRules;
    }

    /**
     * ���J���ʎq��ݒ肷��B
     * @param publicIdentifier ���J���ʎq
     */
    public void setPublicIdentifier(String publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    /**
     * DTD��URL��ݒ肷��B
     * @param dtdUrl DTD��URL
     */
    public void setDtdUrl(String dtdUrl) {
        this.dtdUrl = dtdUrl;
    }

}
