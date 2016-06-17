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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.util.StringUtil;
import jp.terasoluna.fw.web.struts.actions.AbstractDownloadObject;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �r�W�l�X���W�b�N���o�͏�񔽉f���ۃN���X�B
 *
 * <p>
 *  �r�W�l�X���W�b�N���o�͏���ێ�����BLogicResources�����ƂɁA
 *  Web�w�̃I�u�W�F�N�g�ƁA�r�W�l�X���W�b�N�Ԃ̃f�[�^�̃}�b�s���O���s���@�\��
 *  �W�񂵂����ۃN���X�ł���B�S�Ẵr�W�l�X���W�b�N���o�͏�񔽉f�N���X��
 *  ���̃N���X���p�����Ď�������B
 *  AbstractBLogicMapper�̎�Ȗ����́A�ȉ��̂Q��
 *  <ul>
 *   <li>BLogicResources�̐ݒ肩��A�A�N�V�������Ƃ�
 *    �r�W�l�X���W�b�N�̓��͏��N���X�ƂȂ�JavaBean
 *    �𐶐�����@�\��񋟂���</li>
 *   <li>�r�W�l�X���W�b�N�̏o�͏��N���X�ł���
 *    BLogicResult��p���ABLogicResources�̐ݒ肩��A
 *    Web�w�̃I�u�W�F�N�g�ɔ��f����@�\��񋟂���</li>
 *  </ul>
 * </p>
 * <p>
 *  AbstractBLogicMapper�̃T�u�N���X�Ƃ��āA
 *  �f�t�H���g�ł�BLogicMapper��񋟂��Ă��邪�A
 *  ���̋@�\��u�������邱�Ƃ��ł���B
 *  ���̍ہAAbstractBLogicMapper�܂���BLogicMapper��
 *  �p�������r�W�l�X���W�b�N���o�͏�񔽉f�N���X���쐬����K�v������B
 *  �g�������r�W�l�X���W�b�N���o�͏�񔽉f�N���X�ł́A
 *  blogic-io.xml��source������request�Asession�Aapplication�A
 *  dest������request�Asession�ȊO�̔C�ӂ̕������
 *  �w�肵���ꍇ�̓��͒l�擾�����A�o�͒l���f��������������B<br>
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
 *  ���A�f�t�H���g��BLogicMapper���T�|�[�g����request�Asession�Aapplication��
 *  ���ꂼ��A���N�G�X�g�����A�Z�b�V���������A�T�[�u���b�g�R���e�L�X�g����
 *  ��Ώۂɂ��Ă���B
 * </p>
 * <p>
 *  Struts���g�p�����ꍇ�̃r�W�l�X���W�b�N���o�͏�񔽉f�N���X�̓���ւ��E
 *  struts-config.xml�̋L�q���@�ɂ��ẮABLogicIOPlugIn���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public abstract class AbstractBLogicMapper {


    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(AbstractBLogicMapper.class);

    /**
     * BLogicResult��null�������ꍇ�̃G���[�R�[�h�B
     */
    private static final String NULL_RESULT_KEY =
        "errors.blogic.mapper.result";

    /**
     * ���͒l�̎擾���̎w��Ɍ�肪�������ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_SOURCE =
        "errors.blogic.mapper.source";

    /**
     * �o�͒l�̐ݒ��̎w��Ɍ�肪�������ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_DEST =
        "errors.blogic.mapper.dest";

    /**
     * �r�W�l�X���W�b�N�ɓ��͂���JavaBean�̐����Ɏ��s�����ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_BEAN_CREATE =
        "errors.blogic.mapper.create";

    /**
     * �v���p�e�B�l��JavaBean����擾�ł��Ȃ������ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_GETPROPERTY =
        "errors.blogic.mapper.getproperty";

    /**
     * �v���p�e�B�l��JavaBean�ɐݒ�ł��Ȃ������ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_SETPROPERTY =
        "errors.blogic.mapper.setproperty";

    /**
     * �l����͌��̃C���X�^���X����擾�ł��Ȃ������ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_GETVALUE =
        "errors.blogic.mapper.getvalue";

    /**
     * �l���o�͐�̃C���X�^���X�ɔ��f�ł��Ȃ������ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_SETVALUE =
        "errors.blogic.mapper.setvalue";

    /**
     * ���ʔ��f����io��null�Ȃ̂�result.getResultObject()�Ŏ擾�����l��null�łȂ�����
     * �ꍇ�̃G���[�R�[�h�B
     */
    private static final String ERROR_BEAN_NOTNULL =
        "errors.blogic.mapper.notnull";

    /**
     * Web�w�̃I�u�W�F�N�g�Ɋi�[���ꂽ�l��JavaBean�Ƀ}�b�s���O����B
     *
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param io �r�W�l�X���W�b�N���o�͏��
     * @return Web�w�Ɋi�[���ꂽ�K�v�ȃp�����[�^��S�Ċi�[����JavaBean�Aio��
     * �N���X���w�肳��Ă��Ȃ��ꍇ��null��ԋp����B
     */
    public Object mapBLogicParams(HttpServletRequest request,
            HttpServletResponse response, BLogicIO io) {
        if (io == null) {
            // io��null�̏ꍇ�Anull��ԋp����
            return null;
        }

        if (io.getInputBeanName() == null) {
            // ����Bean���w�肳��Ă��Ȃ��ꍇ��null��ԋp����B
            if (log.isDebugEnabled()) {
                log.debug("blogic-io.inputBeanName is null.");
            }
            return null;
        }
        
        // JavaBean�Ƀ}�b�s���O����
        return setParams(request, response, io);
    }
    
    /**
     * �r�W�l�X���W�b�N���͏��ƂȂ�JavaBean������ɁA
     * Web�w�̃I�u�W�F�N�g�Ɋi�[���ꂽ�l��JavaBean�Ƀ}�b�s���O����B
     * blogic-io��blogic-params���}�b�s���O����B
     * 
     * @param request
     * @param response
     * @param io
     * @return �r�W�l�X���W�b�N���͏��ƂȂ�JavaBean
     */
    protected Object setParams(HttpServletRequest request, HttpServletResponse response, BLogicIO io) {

        // �C�e���[�^�[�̐���
        Iterator it = io.getBLogicParams().iterator();

        // �߂�l�ŕԂ�JavaBean�̐���
        Object bean = null;
        // blogic-io��blogic-params���ݒ肳��Ă����ꍇ�̂݃C���X�^���X���쐬
        if (it.hasNext()) {
            try {
                bean = ClassUtil.create(io.getInputBeanName());
            } catch (ClassLoadException e) {
                log.error("bean creation failure.");
                throw new SystemException(e, ERROR_BEAN_CREATE);
            }
        } else {
            return null;
        }

        // List���̑S�Ă�BLogicProperty�C���X�^���X�ɂ��āA�������s��
        while (it.hasNext()) {

            // BLogicProperty�C���X�^���X���擾
            BLogicProperty property = (BLogicProperty) it.next();
            // �v���p�e�B�����擾
            String propertyName = property.getProperty();

            // �r�W�l�X���W�b�N���Ŏg�p�����v���p�e�B�����擾
            String blogicPropertyName = property.getBLogicProperty();

            // blogicPropertyName�����ݒ�̏ꍇ�ApropertyName��������
            if (blogicPropertyName == null) {
                blogicPropertyName = propertyName;
            }

            // ���͒l�i�[���̔�����s���AJavaBean�Ɋi�[����
            if (!"".equals(property.getSource())
                    && (property.getSource() != null)) {
                // �N��������͒l�ݒ胁�\�b�h�ɑ΂�������̔z��
                Object[] methodParams = new Object[] { propertyName, request,
                        response };
                Class[] parameterTypes = new Class[] { String.class,
                        HttpServletRequest.class, HttpServletResponse.class };
                Object value = null;
                try {
                    // ���͒l�擾���������s
                    value = MethodUtils.invokeMethod(this, "getValueFrom"
                            + StringUtil
                                    .capitalizeInitial(property.getSource()),
                            methodParams, parameterTypes);
                } catch (NoSuchMethodException e) {
                    log.error("no such method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETVALUE, new String[] { propertyName });
                } catch (IllegalAccessException e) {
                    log.error("illegal access to the method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETVALUE, new String[] { propertyName });
                } catch (InvocationTargetException e) {
                    log.error("exception is thrown out by invokeMethod.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETVALUE, new String[] { propertyName });
                }

                try {
                    // ���͒l�ݒ胁�\�b�h�����s
                    BeanUtil.setBeanProperty(bean, blogicPropertyName, value);
                } catch (PropertyAccessException e) {
                    log.error("setBeanProperty failure.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETPROPERTY,
                            new String[] { blogicPropertyName });
                }

            } else {
                // �i�[���̎w�肪�Ԉ���Ă��邽�ߗ�O���X���[
                log.error("source is illegal.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_SOURCE);
            }
        }
        return bean;
    }

    /**
     * ���N�G�X�g����w�肳�ꂽ�v���p�e�B�l���擾����B
     *
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �v���p�e�B�l
     */
    public abstract Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * �Z�b�V��������w��̃v���p�e�B�����L�[�ɒl���擾����B
     *
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �v���p�e�B�l
     */
    public abstract Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * BLogicIO�ɏ]���AWeb�w�̃I�u�W�F�N�g�ɒl���i�[����B
     *
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param io �r�W�l�X���W�b�N���o�͏��
     * @param result �r�W�l�X���W�b�N�̏o�͏��
     */
    public void mapBLogicResult(HttpServletRequest request,
                                          HttpServletResponse response,
                                          BLogicIO io,
                                          BLogicResult result) {
        // BLogicResult��null�`�F�b�N
        if (result == null) {
            log.error("BLogicResult is null.");
            throw new SystemException(new BLogicMapperException(
                    new NullPointerException()), NULL_RESULT_KEY);
        }

        // result����JavaBean���擾
        Object bean = result.getResultObject();
        
        // bean��null�̏ꍇ�A�ȍ~�̏����͍s�Ȃ�Ȃ�      
        if (bean == null) {
            
        	return;
        }
        
        // bean��AbstractDownloadObject���p�����Ă���ꍇ�A
        // �ȍ~�̏����͍s�Ȃ�Ȃ�      
        if (bean instanceof AbstractDownloadObject) {
            
        	return;
        }

        // io�����ݒ�Ȃ̂ɁAbean���i�[����Ă����ꍇ�̃`�F�b�N
        if (io == null) {
            if (bean != null) {
                if (log.isDebugEnabled()) {
                    log.debug("The bean should be null.");
                }
                log.error("bean is not null.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_BEAN_NOTNULL);
            }
        }
        
        getResults(request, response, io, bean);
    }

    /**
     * blogic-io��blogic-result��Mapping���s���B
     * 
     * @param request
     * @param response
     * @param io
     * @param bean
     */
    protected void getResults(HttpServletRequest request, HttpServletResponse response,
            BLogicIO io, Object bean) {


        // �C�e���[�^�[�̐���
        Iterator it = io.getBLogicResults().iterator();

        // blogic-result�����ݒ�Ȃ̂ɁAbean���i�[����Ă����ꍇ�̃`�F�b�N
        if (!it.hasNext()) {
            if (bean != null) {
                if (log.isDebugEnabled()) {
                    log.debug("The bean should be null.");
                }
                log.error("bean is not null.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_BEAN_NOTNULL);
            }
        }

        // List���̑S�Ă�BLogicProperty�N���X�C���X�^���X�ɂ��āA�������s��
        while (it.hasNext()) {

            // BLogicProperty�C���X�^���X���擾
            BLogicProperty property = (BLogicProperty) it.next();

            // �v���p�e�B�����擾
            String propertyName = property.getProperty();

            // �r�W�l�X���W�b�N���Ŏg�p�����v���p�e�B�����擾
            String blogicPropertyName = property.getBLogicProperty();

            // blogicPropertyName�����ݒ�̏ꍇ�ApropertyName��������
            if (blogicPropertyName == null) {
                blogicPropertyName = propertyName;
            }

            if (!"".equals(property.getDest()) && (property.getDest() != null)) {

                Object value = null;
                try {
                    // �o�͒l�擾���\�b�h�����s
                    value = BeanUtil.getBeanProperty(bean, blogicPropertyName);
                } catch (PropertyAccessException e) {
                    log.error("getBeanProperty failure.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_GETPROPERTY,
                            new String[] { blogicPropertyName });
                }

                // �i�[��̔�����s���A�o�͒l���i�[����
                Object[] methodParams = new Object[] { value, propertyName,
                       request, response };
                Class[] parameterTypes = new Class[] { Object.class,
                        String.class, HttpServletRequest.class,
                        HttpServletResponse.class };
                try {
                    MethodUtils.invokeMethod(this, "setValueTo"
                            + StringUtil
                                    .capitalizeInitial(property.getSource()),
                            methodParams, parameterTypes);
                } catch (NoSuchMethodException e) {
                    log.error("no such method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETVALUE, new String[] { propertyName });
                } catch (IllegalAccessException e) {
                    log.error("illegal access to the method.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETVALUE, new String[] { propertyName });
                } catch (InvocationTargetException e) {
                    log.error("exception is thrown out by invokeMethod.");
                    throw new SystemException(new BLogicMapperException(e),
                            ERROR_SETVALUE, new String[] { propertyName });
                }
            } else {
                // �i�[��̎w�肪�Ԉ���Ă邽�߁A��O���X���[
                log.error("dest is illegal.");
                throw new SystemException(new BLogicMapperException(),
                        ERROR_DEST);
            }
        }
    }

    /**
     * ���N�G�X�g�̎w�肳�ꂽ�v���p�e�B�ɒl���i�[����B
     *
     * @param value �o�͒l
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     */
    public abstract void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * �Z�b�V�����Ɏw��̃v���p�e�B�����L�[�ɒl���i�[����B
     *
     * @param value �o�͒l
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     */
    public abstract void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * �T�[�u���b�g�R���e�L�X�g����w�肳�ꂽ�v���p�e�B�l���擾����B
     *
     * @param propName �v���p�e�B��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �v���p�e�B�l
     */
    public abstract Object getValueFromApplication(String propName,
            HttpServletRequest request, HttpServletResponse response);

}
