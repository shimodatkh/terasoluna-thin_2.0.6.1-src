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

package jp.terasoluna.fw.web.struts.form;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.struts.ModuleUtil;
import jp.terasoluna.fw.web.struts.reset.Resetter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * Validator�t���[�����[�N�Ή��A�N�V�����t�H�[�����N���X�B
 *
 * <p>
 * Struts�� ValidatorActionForm ���p������
 * �A�N�V�����t�H�[�����N���X�ł���B<br>
 * ���L�́AStruts�ݒ�t�@�C��(struts-config.xml) �ŁA���̃N���X���p������
 * �A�N�V�����t�H�[���̐ݒ��ł���B
 * <p>
 * <strong> Struts�ݒ�t�@�C��(struts-config.xml) �̃A�N�V�����t�H�[���ݒ��</strong>
 * </p>
 * <code><pre>
 * &lt;form-beans&gt;
 *  <b>&lt;form-bean
 *    name="_validateSampleForm"
 *    type="jp.terasoluna.sample.xxxx.SampleValidatorActionFormEx"
 *  &gt;</b>
 *  &lt;/form-bean&gt;
 * &lt;/form-beans&gt;
 * </pre></code>
 * &lt;form-bean&gt;�^�O���� name ������
 * �t�H�[���_�������w�肵�Atype �����ɃN���X�����w�肷��B<br>
 * �t�H�[���_�����̐擪�� &quot;_&quot ��t���A�R���g���[����
 * RequestProcessorEx���w�肷�邱�Ƃɂ��A
 * �Z�b�V������̃t�H�[���̗B�ꐫ���ۏႳ���B<br>
 * �t�H�[���̎�����͉��L�̂悤�ɂȂ�B<br>
 * <p>
 * <strong>�t�H�[��������</strong>
 * </p>
 * <code><pre>
 * public class SampleValidatorActionForm extends ValidatorActionFormEx {
 *
 *    // ���ID
 *    private String companyId = null;
 *    // ���[�UID
 *    private String userId = null;
 *    // �p�X���[�h
 *    private String password = null;
 *    �E�E�E
 *    // ���ID��setter
 *    public void setCompanyId(String companyId) {
 *        this.companyId = companyId;
 *    }
 *
 *    // ���ID��getter
 *    public String getCompanyId() {
 *        return companyId;
 *    }
 *    �E�E�E
 * }
 * </pre></code>
 * �t�H�[�����ŕێ����ׂ����̃t�B�[���h���쐬���A���̃t�B�[���h��
 * getter/setter ���L�q����B
 *
 * <p>
 * <strong>�g���_�Œ񋟂���@�\</strong>
 * </p>
 * <ul>
 * <li>���Z�b�g�@�\�B<br>
 * �T�[�u���b�g�R���e�L�X�g�ɐݒ肳��Ă��� ResetterImpl �N���X
 * �� reset() ���\�b�h���N������B<br></li>
 * <li>�C���f�b�N�X�t�v���p�e�B�����擾�@�\�B<br>
 *  getIndexCount(String fieldName) �ɂ��A�Ώۂ̃t�B�[���h��
 * �������擾�ł���B<br></li>
 * <li>�C���f�b�N�X�t�v���p�e�B�ݒ�@�\�B<br>
 * �z��A List �̃C���f�b�N�X�͈͊O�̃Z�b�^�[���\�b�h���Ă΂ꂽ
 * �Ƃ��ɓ��I�ɃC���f�b�N�X�̗v�f�𑝉�������B<br></li>
 * <li>�C���f�b�N�X�t�v���p�e�B�擾�@�\�B<br>
 * �z��A List �̃C���f�b�N�X�͈͊O�̃Q�b�^�[���\�b�h���Ă΂ꂽ
 * �Ƃ��� null ��ԋp����B<br></li>
 * <li>�t�B�[���h�l�ύX�t���O�B<br>
 * BLogicMapper�ɂăA�N�V�����t�H�[���̃t�B�[���h�l�ύX�̍ۂ�
 * modified������true�ɐݒ肵�A�ύX���s��ꂽ���ǂ��������m�ł���B
 * <br></li>
 * </ul>
 *
 * �@�\�̏ڍׂƂ��ĎQ�l�ɂ��ׂ�����
 * <ul>
 *  <li> Struts�ݒ�t�@�C��(struts-config.xml) �ɋL�q���邱�Ƃɂ��A
 *   ���I�Ƀt�H�[�����쐬����ꍇ�̐ݒ��ɂ��ẮA
 *    DynaValidatorActionFormEx ���Q�ƁB</li>
 *  <li>�t�H�[���̗B�ꐫ�A�t�B�[���h�l�ύX�t���O�̏ڍׂɂ��ẮA
 *     RequestProcessorEx ���Q�ƁB</li>
 *  <li>���Z�b�g�E�t�B�[���h�l�N���A�@�\�ɂ��Ă̏ڍׂ́A ResetterImpl �A
 *     ResetterPlugIn ���Q�ƁB</li>
 *  <li>�g���o���f�[�V�����@�\�ɂ��Ă�  FieldChecksEx ���Q��</li>
 * </ul>
 * </p>
 *
 * @see
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.action.RequestProcessorEx
 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 *
 */
public class ValidatorActionFormEx
    extends ValidatorActionForm
    implements FormEx {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -744848917166154997L;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(ValidatorActionFormEx.class);

    /**
     * �l�ύX�t���O�B
     */
    private boolean modified = false;

    /**
     * �w�肵���C���f�b�N�X�̃v���p�e�B�l���擾����B
     *
     * @param name �擾�Ώۂ̃v���p�e�B��
     * @param index �擾�Ώۂ̃C���f�b�N�X
     * @return �v���p�e�B�l
     */
    public Object getIndexedValue(String name, int index) {
        Object field = null;
        try {
            field = BeanUtil.getBeanProperty(this, name);
        } catch (PropertyAccessException e) {
            // DynaValidatorActionFormEx�Ƃ̎d�l����ׁ̈A
            // ��O�������̏����͍s�Ȃ�Ȃ��B
        }
        if (field == null) {
            throw new NullPointerException(
                "No indexed value for '" + name + "[" + index + "]'");
        } else if (field.getClass().isArray()) {
            try {
                return (Array.get(field, index));
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        } else if (field instanceof List) {
            try {
                return ((List) field).get(index);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException(
                "Non-indexed property for '" + name + "[" + index + "]'");
        }
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̌������擾����B
     *
     * @param fieldName �����擾�Ώۂ̃t�B�[���h���B
     * @return �t�B�[���h�l�̌����B
     * <p>�v���p�e�B�����擾�ł��Ȃ������ꍇ��0��Ԃ��B</p>
     */
    public int getIndexCount(String fieldName) {
        if (log.isDebugEnabled()) {
            log.debug("getIndexCount(" + fieldName + ") called.");
        }
        int count = 0;
        Object value = null;
        try {
            value = BeanUtil.getBeanProperty(this, fieldName);
        } catch (PropertyAccessException e) {
            // DynaValidatorActionFormEx�Ƃ̎d�l����ׁ̈A
            // ��O�������̏����͍s�Ȃ�Ȃ��B
        }
        if (value == null) {
            count = 0;
        } else if (value.getClass().isArray()) {
            count = ((Object[]) value).length;
        } else if (value instanceof List) {
            count = ((List) value).size();
        } else if (value instanceof Map) {
            count = ((Map) value).size();
        } else {
            count = 1;
        }
        if (log.isDebugEnabled()) {
            log.debug("size = [" + count + "]");
        }
        return count;
    }

    /**
     * �w�肵���C���f�b�N�X�̈ʒu�Ƀv���p�e�B�l��ݒ肷��B
     *
     * @param name �ݒ�Ώۂ̃C���f�b�N�X�t�v���p�e�B��
     * @param index �ݒ�Ώۂ̃C���f�b�N�X�ʒu
     * @param value �ݒ肷��v���p�e�B�l
     */
    @SuppressWarnings("unchecked")
    public void setIndexedValue(String name, int index, Object value) {
        if (log.isDebugEnabled()) {
            log.debug(
                "set(" + name + ", " + index + ", " + value + ") called.");
        }

        Object prop = null;
        try {
            prop = BeanUtil.getBeanProperty(this, name);
        } catch (PropertyAccessException e) {
            // DynaValidatorActionFormEx�Ƃ̎d�l����ׁ̈A
            // ��O�������̏����͍s�Ȃ�Ȃ��B
        }
        if (prop == null) {
            throw new NullPointerException(
                "No indexed value for '" + name + "[" + index + "]'");
        } else if (prop.getClass().isArray()) {
            if (index < Array.getLength(prop)) {
                Array.set(prop, index, value);
            } else {
                // �C���f�b�N�X�����`�F�b�N
                ActionFormUtil.checkIndexLength(index);
                // �V�K�̔z��𐶐�
                Object newArray =
                    Array.newInstance(prop.getClass().getComponentType(),
                                                                    index + 1);
                // �z��̃R���|�[�l���g���R�s�[
                System.arraycopy(prop, 0, newArray, 0, Array.getLength(prop));
                // �ǉ����̃R���|�[�l���g���Z�b�g
                Array.set(newArray, index, value);
                // �Q�Ƃ̃R�s�[
                prop = newArray;
            }
            try {
                BeanUtil.setBeanProperty(this, name, prop);
            } catch (PropertyAccessException e) {
                throw new IllegalArgumentException(
                    "Cannot set property for '" + name + "[" + index + "]'");
            }
        } else if (prop instanceof List) {
            if (index < ((List) prop).size()) {
                ((List) prop).set(index, value);
            } else {
                // �C���f�b�N�X�����`�F�b�N
                ActionFormUtil.checkIndexLength(index);
                Object[] oldValues = ((List) prop).toArray();
                Object[] newValues =
                    (Object[]) Array.newInstance(
                        oldValues.getClass().getComponentType(),
                        index + 1);
                System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
                newValues[index] = value;
                ((List) prop).clear();
                ((List) prop).addAll(Arrays.asList(newValues));
            }
            try {
                BeanUtil.setBeanProperty(this, name, prop);
            } catch (PropertyAccessException e) {
                throw new IllegalArgumentException(
                    "Cannot set property for '" + name + "[" + index + "]'");
            }
        } else {
            throw new IllegalArgumentException(
                "Non-indexed property for '" + name + "[" + index + "]'");
        }

    }

    /**
     * �l�ύX�t���O���擾����B
     *
     * @return �l�ύX�t���O
     */
    public boolean isModified() {
        return this.modified;
    }

    /**
     * �l�ύX�t���O��ݒ肷��B
     *
     * @param modified �l�ύX�t���O
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * �A�N�V�����t�H�[���̃t�B�[���h�l���Z�b�g���s���B
     *
     * @param mapping �}�b�s���O���
     * @param request ���N�G�X�g���
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("reset() called.");
        }

        //Prefix���擾
        String prefix = ModuleUtil.getPrefix(request);

        try {
            Resetter resetter =
                (Resetter) getServlet().getServletContext().getAttribute(
                    Resetter.RESETTER_KEY + prefix);
            if (resetter == null) {
                if (log.isDebugEnabled()) {
                    log.debug("resetter class is not specified.");
                }
                return;
            }
            resetter.reset(this, mapping, request);
        } catch (ClassCastException e) {
            log.error("", e);
            return;
        }
    }

}
