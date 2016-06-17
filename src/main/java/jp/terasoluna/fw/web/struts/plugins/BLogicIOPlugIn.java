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

import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;

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
 * �r�W�l�X���W�b�N���o�͂̐ݒ�������[�h����v���O�C���B
 *
 * <p>Struts��PlugIn�@�\���g�p���A�T�[�u���b�g����������
 * �r�W�l�X���W�b�N���o�͂̐ݒ��ǂݍ��݁A�T�[�u���b�g�R���e�L�X�g
 * �ɕۑ�����B<br>
 * ���̋@�\���g�p����ɂ�struts-config.xml�Ɉȉ��̂悤�ɐݒ肷��B
 * �i digesterRules�AmapperClass�͏ȗ��B�j
 * �Ȃ��Aresources�̓J���}�Ńt�@�C����A���ł���B
 * </p>
 * <code><pre>
 * &lt;plug-in
 *   className="jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn"&gt;
 *    &lt;set-property
 *        property="resources"
 *        value="/WEB-INF/blogic-io.xml"/&gt;
 *    &lt;set-property
 *        property="digesterRules"
 *        value="/WEB-INF/blogic-io-rules.xml"/&gt;
 *    &lt;set-property
 *        property="mapperClass"
 *        value="jp.terasoluna.fw.service.thin.BLogicMapper"/&gt;
 * &lt;/plug-in&gt;
 * </pre></code>
 * <p>�r�W�l�X���W�b�N���o�͏�񔽉f�N���X���g���N���X�ɕύX����ꍇ�́A
 * mapperClass��value������AbstractBLogicMapper�̃T�u�N���X�A
 * �܂���BLogicMapper���p������BLogicMapper�g���N���X��ݒ肷��B</p>
 *
 * blogic-io.xml�ɂ̓A�N�V�������ƂɃr�W�l�X���W�b�N
 * �̓��o�͏���ݒ肷��B<br>
 * ���L��blogic-io.xml�̐ݒ�ł���B
 * <code><pre>
 * &lt;blogic-io&gt;
 *   &lt;action path="/logon/logonAction"&gt;
 *     &lt;blogic-param bean-name="jp.terasoluna.sample.blogic.LogonBean"&gt;
 *       &lt;set-property property="userName"
 *                        blogic-property="userName"
 *                        source="form" /&gt;
 *       &lt;set-property property="sessionId"
 *                        blogic-property="id"
 *                        source="session" /&gt;
 *     &lt;/blogic-param&gt;
 *     &lt;blogic-result&gt;
 *       &lt;set-property property="resultStr"
 *                        blogic-property="result"
 *                        dest="form" /&gt;
 *       &lt;set-property property="USER_VALUE_OBJECT"
 *                        blogic-property="uvo"
 *                        dest="session" /&gt;
 *     &lt;/blogic-result&gt;
 *   &lt;/action&gt;
 *   �E�E�E
 * &lt;/blogic-io&gt;
 * </pre></code>
 * <p>
 * �r�W�l�X���W�b�N�̓��͒l����&lt;blogic-param&gt;
 * �v�f���Őݒ肷��Bbean-name�����ɂ͓��͒l���i�[����
 * JavaBean�̃N���X�����w�肷��B����JavaBean�́A��q����
 * blogic-property�����Ɏw�肳���v���p�e�B���������Ă��Ȃ���΂Ȃ�Ȃ��B<br>
 * �A���A���͒l�����݂��Ȃ��r�W�l�X���W�b�N�̏ꍇ�́Abean-name�������ȗ�
 * ���邱�ƂŁA������null�̃r�W�l�X���W�b�N�����s�\�ł���B<br>
 * ���͒l���̌��ƂȂ�f�[�^�̓A�N�V�����t�H�[����Z�b�V�����Ȃǂ�Web�w��
 * �ݒ肳��Ă�����ł���A�ǂ�����f�[�^���擾���邩��
 * &lt;set-property&gt;�v�f��source�����Ɏw�肳�ꂽ������Ŏ��ʂ���B
 * �f�t�H���g�ł́Aform,session�̂ǂ��炩��source�����ɋL�q���邱�Ƃ�
 * ��񌳂��A�N�V�����t�H�[�����Z�b�V�����������ʂ���B<br>
 * ���͌��̃f�[�^�̃v���p�e�B����property�����Ŏw�肷��B
 * ���Ȃ킿�A<br>
 * property="field1" source="form"�Ɛݒ肵���ꍇ�́A
 * actionForm.get("field1")�����s����A
 * property="field2" source="session"�Ɛݒ肵���ꍇ�́A
 * session.getAttribute("field2")�����s�����B<br>
 * ��L�̌��ʎ擾���ꂽ�l�́A�O�q��bean-name�����Ŏw�肳�ꂽ
 * JavaBean�C���X�^���X�̃v���p�e�B�l�Ƃ��ăr�W�l�X���W�b�N����擾�ł���B
 * JavaBean����l���擾����ꍇ�́Ablogic-property�Ŏw�肵���l��
 * �v���p�e�B���ƂȂ�Bblogic-property�������w�肳��Ȃ��ꍇ�A
 * property�����Ɠ����l��JavaBean�̃v���p�e�B���Ƃ���B
 * </p>
 * <p>
 * <code><pre>
 *  &lt;set-property property="field1" blogic-property="blogicField1"
 *                   source="form" /&gt;
 * </pre></code>
 * �Ɛݒ肵�A�r�W�l�X���W�b�N������A�N�V�����t�H�[����field1
 * �̒l���擾����ꍇ�A
 * bean.getBlogicField1()
 * �����s���邱�ƂŃA�N�V�����t�H�[���̒l���擾�ł���B<br>
 * ���͌��̃f�[�^���Z�b�V�����̒l�ł����l�ł���A
 * <code><pre>
 *  &lt;set-property property="field2" blogic-property="blogicField2"
 *                   source="session" /&gt;
 * </pre></code>
 * �Ɛݒ肵�A�r�W�l�X���W�b�N������field2�Ƃ����L�[�ŃZ�b�V�����Ɋi�[
 * ����Ă���l���擾����ꍇ�A
 * bean.getBlogicField2()
 * �����s����Ηǂ��B
 * �܂�A�r�W�l�X���W�b�N������̓f�[�^�̓��͌����A�N�V�����t�H�[��
 * �ł���̂��A�Z�b�V�����ł���̂����ӎ�����K�v�͂Ȃ��B<br>
 * ���̐ݒ����BLogicResources�̃C���X�^���X��
 * �ǂݍ��܂�A�T�[�u���b�g�R���e�L�X�g�ɕۑ������B</p>
 * <br>
 * <p>
 * ���J���ʎq�A�����DTD��URL��ύX����ꍇ�́A<br>
 * getPublicIdentifier()��getDtdUrl()���I�[�o�[���C�h���邱�ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 * @see jp.terasoluna.fw.service.thin.BLogic
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 *
 */
public class BLogicIOPlugIn implements PlugIn {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(BLogicIOPlugIn.class);

    /**
     * �T�[�u���b�g�R���e�L�X�g�ɓo�^�����BLogicMapper��
     * �v���t�B�b�N�X�L�[�B
     */
    public static final String BLOGIC_MAPPER_KEY = "BLOGIC_MAPPER";

    /**
     * �f�t�H���g��blogic-io-rules.xml �̃p�X�B
     */
    private static final String DIGESTER_RULES_PATH =
        "/WEB-INF/blogic-io-rules.xml";

    /**
     * DTD�̌��J���ʎq�B
     */
    private String publicIdentifier =
        "-//NTTDATA//DTD TERASOLUNA for Spring blogic-io 1.0//JA";

    /**
     * DTD�p�X�B
     */
    private String dtdUrl =
        "/jp/terasoluna/fw/web/struts/plugins/dtd/blogic-io.dtd";

    /**
     * ���̃v���O�C���𐶐������T�[�u���b�g�B
     */
    private ActionServlet servlet = null;

    /**
     * �r�W�l�X���W�b�N���o�͏�񃋁[����`�t�@�C��
     * �iblogic-io-rules.xml�j�B
     */
    private String digesterRules = null;

    /**
     * �r�W�l�X���W�b�N���o�͏���`�t�@�C���iblogic-io.xml�j�B
     */
    private String resources = null;

    /**
     * BLogicMapper���w�肵���N���X�t�@�C�����B
     * �w��Ȃ��̏ꍇ�A�f�t�H���g�̃N���X�����g�p�����B
     */
    private String mapperStr = null;

    /**
     * �r�W�l�X���W�b�N���o�͏���ێ�����C���X�^���X�B
     */
    private BLogicResources blogicResources = null;

    /**
     * �r�W�l�X���W�b�N���o�͏��}�b�p�[�B
     */
    private AbstractBLogicMapper blogicMapper = null;

    /**
     * Digester�C���X�^���X�B
     */
    private static Digester digester = null;

    /**
     * �I���������B
     */
    public void destroy() {
        // �������Ȃ�

    }

    /**
     * PlugIn�̏������������B
     *
     * <p>BLogicResources�A�y�� BLogicMapper��
     * �T�[�u���b�g�R���e�L�X�g�ɓo�^����B</p>
     *
     * <p>
     *  ���̃v���O�C�����N�������T�[�u���b�g�A���W���[���R���t�B�O��
     *  null�ł���Ƃ��A NullPointerException��
     *  ��������
     * </p>
     *
     * @param servlet ���̃v���O�C�����N�������T�[�u���b�g
     * @param config ���W���[���R���t�B�O
     * @exception ServletException ����������O
     */
    public void init(ActionServlet servlet, ModuleConfig config)
        throws ServletException {

        this.servlet = servlet;
        String moduleName = config.getPrefix();

        // BLogicResources���T�[�u���b�g�R���e�L�X�g�ɓo�^
        initResources();
        servlet.getServletContext().setAttribute(
            BLogicResources.BLOGIC_RESOURCES_KEY + moduleName, blogicResources);

        // BLogicMapper���T�[�u���b�g�R���e�L�X�g�ɓo�^
        initMapper();
        servlet.getServletContext().setAttribute(BLOGIC_MAPPER_KEY + moduleName,
            this.blogicMapper);
    }

    /**
     * �r�W�l�X���W�b�N���o�͏�񏉊��������B
     *
     * <p>blogic-io.xml�Ablogic-io-rules.xml
     * �𗘗p���āA�ݒ����Bean�ɓǂݍ��ށB</p>
     *
     * <p>XML�ݒ�t�@�C���̌`���s���AIO��O�������������A
     * ServletException�Ƀ��b�v���X���[����B</p>
     *
     * @exception ServletException �r�W�l�X���W�b�N���o�͏�񏉊�����������
     * ���������O
     */
    private void initResources() throws ServletException {
        if (resources == null || "".equals(resources)) {
            if (log.isDebugEnabled()) {
                log.debug("resources file location is not specified");
            }
            return;
        }
        StringTokenizer st = new StringTokenizer(resources, ",");
        List<String> pathList = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String path = st.nextToken();
            path = path.trim();
            if (log.isDebugEnabled()) {
                log.debug("blogic io file=" + path);
            }
            pathList.add(path);
        }
        try {
            if (digester == null) {
                if (digesterRules == null) {
                    // ���[���t�@�C�����ݒ�̎��A�f�t�H���g�̃��[���t�@�C����
                    // �g�p����B
                    digesterRules = DIGESTER_RULES_PATH;
                }
                digester = DigesterLoader.createDigester(
                    servlet.getServletContext().getResource(digesterRules));
                digester.setValidating(true);
            }
            blogicResources = new BLogicResources();
            for (int i = 0; i < pathList.size(); i++) {
                digester.push(blogicResources);
                URL url = this.getClass().getResource(getDtdUrl());
                if (url != null) {
                    digester.register(getPublicIdentifier(), url.toString());
                }
                digester.parse(
                    servlet.getServletContext().getResourceAsStream(
                    pathList.get(i)));
            }
        } catch (MalformedURLException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (IOException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (SAXException e) {
            log.error("", e);
            throw new ServletException(e);
        } catch (XmlLoadException e) {
            log.error("", e);
            throw new ServletException(e);
        }

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
     * �}�b�p�[�o�^�����B
     *
     * <p>BLogicMapper�����[�h���A�ݒ����
     * �C���X�^���X�t�B�[���h�ɓo�^����B</p>
     *
     * <p>�C���X�^���X�����ɕێ�����Ă��� BLogicMapper
     * �̃N���X���[�h�Ɏ��s�������A ServletException��
     * �X���[����B</p>
     *
     * @throws ServletException �T�[�u���b�g��O
     */
    private void initMapper() throws ServletException {
        Object[] args = {resources};
        if (this.mapperStr != null) {
            try {
                this.blogicMapper =
                    (AbstractBLogicMapper) ClassUtil.create(
                            this.mapperStr, args);
            } catch (ClassLoadException e) {
                log.error("", e);
                throw new ServletException(e);
            } catch (ClassCastException e) {
                log.error("", e);
                throw new ServletException(e);
            }
        } else {
            this.blogicMapper = new BLogicMapper(resources);
        }
    }

    /**
     * blogic-io-rules.xml��ݒ肷��B
     *
     * @param digesterRules blogic-io-rules.xml
     */
    public void setDigesterRules(String digesterRules) {
        this.digesterRules = digesterRules;
    }

    /**
     * blogic-io.xml��ݒ肷��B
     *
     * @param resources blogic-io.xml
     */
    public void setResources(String resources) {
        this.resources = resources;
    }

    /**
     * �r�W�l�X���W�b�N���o�͏�񔽉f�N���X��ݒ肷��B
     *
     * @param mapperStr �r�W�l�X���W�b�N���o�͏�񔽉f�N���X��
     */
    public void setMapperClass(String mapperStr) {
        this.mapperStr = mapperStr;
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
