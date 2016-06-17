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

package jp.terasoluna.fw.service.thin;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * �r�W�l�X���W�b�N���o�͏�񔽉f�N���X�B
 *
 * <p>
 *  BLogicIOPlugIn�ɂ���Đ������ꂽBLogicResources�����ƂɁA
 *  Web�w�̃I�u�W�F�N�g�ƁA�r�W�l�X���W�b�N�Ԃ̃f�[�^�̃}�b�s���O���s���B<br>
 *  Web�w����̓��͂̓��N�G�X�g����(request)�A�Z�b�V��������(session)�A
 *  �T�[�u���b�g�R���e�L�X�g(application)���ΏۂƂȂ�B<br>
 *  �r�W�l�X���W�b�N����̏o�͂̓��N�G�X�g����(request)�A�Z�b�V��������(session)
 *  ���ΏۂƂȂ�B���r�W�l�X���W�b�N����T�[�u���b�g�R���e�L�X�g�ւ̏o�͂�
 *  �T�|�[�g���Ă��Ȃ��B
 * </p>
 * <p>
 *  AbstractBLogicMapper�̃T�u�N���X�Ƃ��āA
 *  �f�t�H���g�ł��̃N���X��񋟂��Ă��邪�A
 *  struts-config.xml��BLogicIOPlugIn�̐ݒ�ɂ���āA
 *  ���̋@�\��u�������邱�Ƃ��ł���B
 *  ���̍ہAAbstractBLogicMapper�܂���BLogicMapper��
 *  �p�������r�W�l�X���W�b�N���o�͏�񔽉f�N���X���쐬����K�v������B
 *  �g�������r�W�l�X���W�b�N���o�͏�񔽉f�N���X�ł́A
 *  blogic-io.xml��source������request�Asession�Aapplication�A
 *  dest������request�Asession�ȊO��
 *  �C�ӂ̕�������w�肵���ꍇ�̓��͒l�擾�����A�o�͒l���f��������������B<br>
 *  ���͒l�擾�����̃��\�b�h���́A
 *  "getValueFrom" + source�����Ɏw�肷�镶����Ƃ���B
 *  source������"factory"�Ǝw�肷��ꍇ�A���\�b�h����getValueFromFactory�ƂȂ�B
 *  �����͑S�Ă̓��͒l�擾���\�b�h�ŋ��ʂŁA
 *  getValueFromForm()���\�b�h�Ɠ����������Ƃ�B<br>
 *  �o�͒l���f�����̃��\�b�h���́A
 *  "setValueTo" + dest�����Ɏw�肷�镶����Ƃ���B
 *  dest������"factory"�Ǝw�肷��ꍇ�A���\�b�h����getValueToFactory�ƂȂ�B
 *  �����͑S�Ă̏o�͒l���f���\�b�h�ŋ��ʂŁA
 *  getValueToForm()���\�b�h�Ɠ����������Ƃ�B<br>
 *  ���A�l�̎擾��A����ActionForm�̏ꍇ�̓l�X�g�����v���p�e�B�����w��
 *  ���邱�Ƃ��\�ł���B
 * </p>
 * <p>
 *  �r�W�l�X���W�b�N���o�͏�񔽉f�N���X�̓���ւ��E
 *  struts-config.xml�̋L�q���@�ɂ��ẮABLogicIOPlugIn���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 *
 */
public class BLogicMapper extends AbstractBLogicMapper {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(BLogicMapper.class);

    /**
     * ���\�[�X�t�@�C�����Ȃ������ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_RESOURCES_FILE = "errors.resources.file";

    /**
     * �R���X�g���N�^�B
     */
    public BLogicMapper(){
        
    }
    
    /**
     * �R���X�g���N�^�B
     * @param resources ���\�[�X�̃p�X
     */
    public BLogicMapper(String resources) {
        if (resources == null || "".equals(resources)) {
            log.error("resources file location is not specified");
            throw new IllegalArgumentException(ERROR_RESOURCES_FILE);
        }
    }
    
    /**
     * ���N�G�X�g����w�肳�ꂽ�v���p�e�B�l���擾����B
     *
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �v���p�e�B�l
     */
    @Override
    public Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // ���N�G�X�g����l���擾����
        Object value = request.getAttribute(propName);

        if (log.isDebugEnabled()) {
            if (request.getAttribute(propName) == null) {
                log.debug("Request's attribute is null:key =[" + propName
                            + "]");
            }
        }

        return value;
    }

    /**
     * �t�H�[������w�肳�ꂽ�v���p�e�B�l���擾����B
     *
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �v���p�e�B�l
     */
    public Object getValueFromForm(String propName, HttpServletRequest request,
            @SuppressWarnings("unused") HttpServletResponse response) throws PropertyAccessException {
        // �t�H�[�����擾����
        ActionForm form = getActionForm(request);

        // Form����l���擾����
        Object value = null;
        try {
            value = BeanUtil.getBeanProperty(form, propName);
        } catch (PropertyAccessException e) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw e;
        }
        return value;
    }

    /**
     * �Z�b�V��������w��̃v���p�e�B�����L�[�ɒl���擾����B
     *
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �v���p�e�B�l
     */
    @Override
    public Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // �Z�b�V�����̎擾
        HttpSession session = request.getSession(true);
        // �Z�b�V��������l���擾����
        Object value = session.getAttribute(propName);

        if (log.isDebugEnabled()) {
            if (session.getAttribute(propName) == null) {
                log.debug("Session's attribute is null:key =[" + propName
                            + "]");
            }
        }
        return value;
    }

    /**
     * ���N�G�X�g�̎w�肳�ꂽ�v���p�e�B�ɒl���i�[����B
     *
     * @param value �o�͒l
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        if (log.isDebugEnabled()) {
            Enumeration<String> enumeration = request.getAttributeNames();
            int existFlag = 0;
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                if (key.equals(propName)) {
                    existFlag = 1;
                    break;
                }
            }
            if (existFlag != 1) {
                log.debug("Request's key does not exist:key =[" + propName
                        + "]");
            }
        }
        // ���N�G�X�g�ɒl���i�[����
        request.setAttribute(propName, value);
    }

    /**
     * �t�H�[���̎w�肳�ꂽ�v���p�e�B�ɒl���i�[����B<br>
     * �t�H�[����FormEx�̃C���X�^���X�ł���΁Amodified�t���O��true��
     * �ݒ肷��B
     *
     * @param value �o�͒l
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     */
    public void setValueToForm(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response)
                throws PropertyAccessException {
        // �t�H�[�����擾����
        ActionForm form = getActionForm(request);

        // �t�H�[���ɒl���i�[����
        try {
            BeanUtil.setBeanProperty(form, propName, value);
            if (form instanceof FormEx) {
                FormEx formEx = (FormEx) form;
                formEx.setModified(true);
            }
        } catch (PropertyAccessException e) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw e;
        }
    }

    /**
     * �Z�b�V�����Ɏw��̃v���p�e�B�����L�[�ɒl���i�[����B
     *
     * @param value �o�͒l
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // �Z�b�V�����̎擾
        HttpSession session = request.getSession(true);

        if (log.isDebugEnabled()) {
            Enumeration<String> enumeration = session.getAttributeNames();
            int existFlag = 0;
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                if (key.equals(propName)) {
                    existFlag = 1;
                    break;
                }
            }
            if (existFlag != 1) {
                log.debug("Session's key does not exist:key =[" + propName
                        + "]");
            }
        }
        // �Z�b�V�����ɒl���i�[����
        session.setAttribute(propName, value);
    }

    /**
     * ���N�G�X�g�܂��̓Z�b�V�����Ɋi�[����Ă���ActionForm
     * �C���X�^���X���擾����B
     *
     * @param request HTTP���N�G�X�g
     * @return ActionForm�C���X�^���X
     */
    protected ActionForm getActionForm(HttpServletRequest request) {

        ActionMapping mapping = (ActionMapping) request
                .getAttribute(Globals.MAPPING_KEY);
        ActionForm form = null;
        if ("request".equals(mapping.getScope())) {
            form = (ActionForm) request.getAttribute(mapping.getAttribute());
        } else {
            HttpSession session = request.getSession();
            form = (ActionForm) session.getAttribute(mapping.getAttribute());
        }
        // ActionForm��ԋp����
        return form;
    }

    /**
     * �T�[�u���b�g�R���e�L�X�g����w��̃v���p�e�B�����L�[�ɒl���擾����B
     *
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �v���p�e�B�l
     */
    @Override
    public Object getValueFromApplication(String propName,
            HttpServletRequest request, HttpServletResponse response) {

        if (propName == null || "".equals(propName)) {
            log.error("illegal argument:propName = [" + propName + "]");
            throw new IllegalArgumentException();
        }

        // �T�[�u���b�g�R���e�L�X�g�̎擾
        HttpSession session = request.getSession(true);
        ServletContext context = session.getServletContext();
        // �Z�b�V��������l���擾����
        Object value = context.getAttribute(propName);

        if (log.isDebugEnabled()) {
            if (value == null) {
                log.debug("ServletContext's attribute is null:key =[" + propName
                            + "]");
            }
        }
        return value;
    }
}
