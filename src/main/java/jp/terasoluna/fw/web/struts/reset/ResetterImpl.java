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

package jp.terasoluna.fw.web.struts.reset;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.beans.IndexedBeanWrapper;
import jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl;
import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.fw.web.struts.ModuleUtil;
import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

/**
 * �f�t�H���g�̃��Z�b�g�����N���X�B
 *
 * <p>
 * TERASOLUNA���f�t�H���g�ŗp�ӂ��Ă��� Resetter �����N���X�B<br>
 * ���̃N���X��
 * {@link  jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn}
 * �𗘗p���AXML �t�@�C���ɐݒ肳�ꂽ��������
 * �t�H�[���t�B�[���h�̃��Z�b�g���s���B<br>
 * �A�N�V�����t�H�[����reset()���\�b�h���g�����Ă���ׁA���Z�b�g������
 * Struts�̃��C�t�T�C�N�����݂̂Ŏ��s�����B���Z�b�g�����̓`�F�b�N�{�b�N�X��
 * ���W�I�{�^���̒l�����v���p�e�B�̏�������ړI�Ƃ��Ē񋟂����ׁA
 * ���̑��̖ړI�ł̗��p�ɂ��Ă͓���͕ۏ؂��Ȃ��B<br>
 * ���P���Ƀt�B�[���h���������������ꍇ�́A�r�W�l�X���W�b�N�Ȃǂ�ʂ��A
 * ���f���̒l�ύX�Ƃ��Ď��s���邱�ƁBStruts�̃��C�t�T�C�N���ł̓��Z�b�g���s��A
 * ���N�G�X�g�p�����[�^�ł��̃v���p�e�B���ēx����������B<br>
 * <br>
 * ���Z�b�g�����͑Ώۂ̃t�B�[���h�̒l��null�̏ꍇ�A�^���肪�ł��Ȃ��ׁA
 * null�̂܂܁A�l�ύX�����s���Ȃ��Bnull�ȊO��Object�^�t�B�[���h��null�l�ɁA
 * boolean�^��false�ɁA���̑��̃v���~�e�B�u�^�t�B�[���h��0�ɏ��������s���B
 * DynaValidatorActionFormEx���g�p����ꍇ�A
 * struts-config.xml�ɋL�q���������l�ɂ̓��Z�b�g����Ȃ��̂ŁA���ӂ��邱�ƁB<br>
 * ���Z�b�g�@�\�Ƃ��āA �w�ʏ�̃��Z�b�g�����x �y�� �w�w��͈̓��Z�b�g�@�\�x
 * �������A�w��͈̓��Z�b�g�@�\�ł́A�z�񖔂� List �I�u�W�F�N�g��
 * �C�ӂ͈݂̔͂̂����Z�b�g���鎖���\�B<br>
 * ���Z�b�g����C�ӂ͈̔͂̓��N�G�X�g�p�����[�^�� &quot;startIndex&quot;,
 * &quot;endIndex&quot;���L�[�Ƃ��Ċi�[���Ă����K�v������B
 * </p>
 *
 * <strong>�g�p���@</strong><br>
 * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)�ɂ̓A�N�V�������Ƃ�
 * ���Z�b�g�Ώۃt�B�[���h��ݒ肷��B <code><pre>
 *  &lt;reset&gt;
 *    &lt;action path=&quot;/resetAction&quot;&gt;
 *      &lt;property-reset name=&quot;field1&quot; /&gt;
 *      &lt;property-reset name=&quot;field2&quot; select=&quot;true&quot; /&gt;
 *    &lt;/action&gt;
 *  &lt;/reset&gt;
 * </pre></code> ��L�̐ݒ�̏ꍇ�A/resetAction.do ���Ă΂���
 * ���N�G�X�g�p�����[�^���t�H�[���ɔ��f�����O�Ƀt�H�[����
 * �w��̃t�B�[���h���N���A�����B<br>
 * �N���A�Ώۂ̃t�B�[���h�� property-reset �^�O�� name �����Ŏw�肳�ꂽ�A
 * &quot;field1&quot; ��
 * &quot;field2&quot; �ƂȂ�B<br>
 * &quot;field2&quot; �ɂ��ẮA select ������ true �ƂȂ��Ă���ׁA
 * ���N�G�X�g�p�����^��&quot;startIndex&quot; �` &quot;endIndex&quot;
 * �Ŏw�肳�ꂽ�C���f�b�N�X�Ԃ̃t�B�[���h�l�݂̂� null
 * �ɏ����������B<br>
 * �܂��A�{�N���X�̓l�X�g�����v���p�e�B�̃��Z�b�g�ɑΉ����Ă���B
 * �A�N�V�����t�H�[���̃l�X�g�����v���p�e�B�����Z�b�g����ꍇ�́A
 * �v���p�e�B�����u.�v�ŘA�����������Q�Ǝ����g�p���邱�ƁB
 * �ȉ��ɁA�l�X�g�����v���p�e�B�����Z�b�g����ꍇ�̗�������B<br><br>
 * <strong>�l�X�g�����v���p�e�B�̃��Z�b�g���@</strong><br>
 * <code><pre>
 * public class MyActionForm extends ValidatorActionFormEx {
 *     private List<Row> rows = null;
 *     public void setRows(List<Row> rows) {
 *         this.rows = rows;
 *     }
 *     public List<Row> getRows() {
 *         return this.rows;
 *     }
 *     private Map<String, String> map = new HashMap();
 *     //�ȉ��ȗ�
 * }
 * public class Row {
 *     private String value = null;
 *     public void setValue(String value) {
 *         this.value = value;
 *     }
 *     public String getValue() {
 *         return this.value;
 *     }
 * }
 * </pre></code>
 * ��L��MyActionForm������Row�N���X�̃��X�g�̊evalue�v���p�e�B�A
 * Map�^��map�v���p�e�B�Łufield�v�Ƃ����L�[���̒l
 * �����Z�b�g����ꍇ�͈ȉ��̂悤��reset.xml�ɋL�q����B
 * <code><pre>
 *  &lt;reset&gt;
 *    &lt;action path=&quot;/resetAction&quot;&gt;
 *      &lt;property-reset name=&quot;rows.value&quot; /&gt;
 *      &lt;property-reset name=&quot;map(field)&quot; /&gt;
 *    &lt;/action&gt;
 *  &lt;/reset&gt;
 * </pre></code>
 * �����Q�Ǝ��̓v���p�e�B���݂̂��L�q����B�v�f���z���List�̏ꍇ��
 * �t���[�����[�N�������I�ɔF�����A�S�Ă̗v�f�����Z�b�g����B
 * �ʏ�̃v���p�e�B���l�Aselect������true���w�肷�邱�ƂŁA
 * �͈͎w�胊�Z�b�g���\�ł���B
 *
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ResetterImpl implements Resetter {

    /**
     * ���O�N���X
     */
    private static Log log = LogFactory.getLog(ResetterImpl.class);

    /**
     * �t�H�[���̃t�B�[���h�l�̃��Z�b�g���s���B
     *
     * @param form
     *            �A�N�V�����t�H�[��
     * @param mapping
     *            �}�b�s���O���
     * @param request
     *            ���N�G�X�g���
     *
     * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
     * @see jp.terasoluna.fw.web.struts.form.FormEx
     * @see jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
     * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
     */
    public void reset(FormEx form, ActionMapping mapping,
            HttpServletRequest request) {
        // form,mapping��null�̏ꍇ�͂����܂œ��B���Ȃ��̂�
        // NullPointer�����͍l�����Ȃ�
        if (log.isDebugEnabled()) {
            log.debug(mapping.getName() + " reset() called.");
        }
        ActionReset reset = getActionReset(mapping, request);
        if (reset == null) {
            return;
        }
        Iterator<String> it = reset.getFieldNames();
        IndexedBeanWrapper wrapper = new JXPathIndexedBeanWrapperImpl(form);
        while (it.hasNext()) {

            // �t�B�[���h�����擾
            String fieldName = it.next();
            if (log.isDebugEnabled()) {
                log.debug("reset[" + fieldName + "]");
            }

            Map<String, Object> propMap =
                wrapper.getIndexedPropertyValues(fieldName);
            // select�w�肳��Ă���ꍇ�͎w��͈͂̂݃��Z�b�g����
            // �A���A�t�B�[���h���z��AList�łȂ��ꍇ�͒ʏ�̃��Z�b�g�����s����
            if (reset.isSelectField(fieldName) && propMap.size() > 1) {
                resetSelectField(form, propMap, request);
            } else {
                for (Entry<String, Object> e : propMap.entrySet()) {
                    resetValue(form, e);
                }
            }
        }
    }

    /**
     * �A�N�V�����t�H�[���̎w�肳�ꂽ�v���p�e�B�����Z�b�g����B
     * �v���p�e�B�̌^��boolean�^�ABoolean�^�̏ꍇfalse��ݒ肷��B
     * ���̑��̃v���~�e�B�u�^����т��̃��b�p�[�^�̏ꍇ�́A0��ݒ肷��B
     * �v���p�e�B�̌^�����b�p�[�^�ȊO��Object�^�̏ꍇ��null��ݒ肷��B<br>
     * ���A������entry�ɂ�null���n����邱�Ƃ͂Ȃ��B
     *
     * @param form ���݂̃��N�G�X�g�Ŏg�p����A�N�V�����t�H�[��
     * @param entry ���Z�b�g�Ώۂ̃v���p�e�B���ƌ��݂̒l�̃G���g��
     * @throws PropertyAccessException �v���p�e�B�̒l�ݒ�Ɏ��s�����ꍇ
     */
    protected void resetValue(FormEx form, Entry<String, Object> entry) {
        if (log.isDebugEnabled()) {
            log.debug("resetValue(" + form + ", " +
                    entry.getKey() + ") called.");
        }
        String propName = entry.getKey();
        try {
            Object value = entry.getValue();
            if (value == null) {
                return;
            }
            Class type = null;
            type = value.getClass();
            if (type != null) {
                // �^�̎�ނŏ������킯��B
                if (type == Boolean.TYPE || type == Boolean.class) {
                    BeanUtil.setBeanProperty(form, propName, Boolean.FALSE);
                } else if (type == Byte.TYPE || type == Byte.class) {
                    BeanUtil.setBeanProperty(
                            form, propName, new Byte((byte) 0));
                } else if (type == Character.TYPE || type == Character.class) {
                    BeanUtil.setBeanProperty(form, propName,
                            new Character((char) 0));
                } else if (type == Double.TYPE || type == Double.class) {
                    BeanUtil.setBeanProperty(form, propName,
                            new Double(0.0));
                } else if (type == Float.TYPE || type == Float.class) {
                    BeanUtil.setBeanProperty(form, propName,
                            new Float((float) 0.0));
                } else if (type == Integer.TYPE || type == Integer.class) {
                    BeanUtil.setBeanProperty(
                            form, propName, new Integer(0));
                } else if (type == Long.TYPE || type == Long.class) {
                    BeanUtil.setBeanProperty(form, propName, new Long(0));
                } else if (type == Short.TYPE || type == Short.class) {
                    BeanUtil.setBeanProperty(
                            form, propName, new Short((short) 0));
                } else {
                    // �v���~�e�B�u�^�A���b�p�[�^�łȂ��ꍇ��null��ݒ肷��
                    BeanUtil.setBeanProperty(form, propName, null);
                }
            }
        } catch (PropertyAccessException e) {
            log.error("cannot access property " + form + "." + propName, e);
        }
    }

    /**
     * ResetterResources �̃C���X�^���X���擾����B
     *
     * @param request ���N�G�X�g���
     * @return ResetterResources �C���X�^���X
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     */
    protected ResetterResources getResetterResources(
            HttpServletRequest request) {

        // Prefix���擾
        String prefix = ModuleUtil.getPrefix(request);

        // ServletContext���擾
        ServletContext application = RequestUtil.getServletContext(request);

        // ResetterResource���擾
        ResetterResources resources = (ResetterResources) application
                .getAttribute(ResetterResources.RESETTER_RESOURCES_KEY + prefix);

        return resources;
    }

    /**
     * �A�N�V�����p�X�ɕR�Â��� ActionReset �̃C���X�^���X���擾����B
     *
     * @param mapping ���݂̃��N�G�X�g�̃}�b�s���O���
     * @param request ���N�G�X�g���
     * @return ActionReset �̃C���X�^���X
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
     */
    protected ActionReset getActionReset(ActionMapping mapping,
            HttpServletRequest request) {

        ResetterResources resources = getResetterResources(request);
        if (resources == null) {
            if (log.isDebugEnabled()) {
                log.debug("ResetterResources is null.");
            }
            return null;
        }

        ActionReset reset = resources.get(mapping.getPath());

        if (reset == null) {
            if (log.isDebugEnabled()) {
                log.debug("action path:" + mapping.getPath()
                        + " is not specified to reset.");
            }
        }
        return reset;

    }

    /**
     * �I���t�B�[���h�̃��Z�b�g���s���B
     *
     * select ������ true ���ݒ肳��Ă����Ƃ��� ���N�G�X�g�p�����^��
     * &quot;startIndex&quot; �` &quot;endIndex&quot;
     * �Ŏw�肳�ꂽ�C���f�b�N�X�Ԃ̃t�B�[���h�l�� null �ɏ���������B<br>
     * ���N�G�X�g�p�����^�� &quot;startIndex&quot;, &quot;endIndex&quot;
     * ���܂܂�Ă��Ȃ���ΑS�Ă̗v�f�̃��Z�b�g���s���B<br>
     * �܂��A�t�B�[���h�l�̌^���z��^�AList �^�Ő錾����Ă���ꍇ�́A
     * �ʏ�̃��Z�b�g�������s���B
     *
     * @param form ���݂̃��N�G�X�g�Ŏg�p����A�N�V�����t�H�[��
     * @param propMap �v���p�e�B���ƌ��݂̒l��Map
     * @param request ���N�G�X�g���
     *
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     * @see jp.terasoluna.fw.web.struts.reset.FieldReset
     * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
     */
    protected void resetSelectField(FormEx form,
                                    Map<String, Object> propMap,
                                    HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("resetSelectField() called.");
        }
        int startIndex = 0;
        int size = propMap.size();
        int endIndex = size;
        try {
            String startIndexStr = request.getParameter("startIndex");
            String endIndexStr = request.getParameter("endIndex");
            if (startIndexStr != null) {
                startIndex = Integer.parseInt(startIndexStr);
            }
            if (endIndexStr != null) {
                endIndex = Integer.parseInt(endIndexStr);
            }
            if (log.isDebugEnabled()) {
                log.debug("startIndex = [" + startIndex + "]");
                log.debug("endIndex = [" + endIndex + "]");
            }
        } catch (NumberFormatException e) {
            log.error("startIndex or endIndex is not Number.", e);
            return;
        }

        // ���Z�b�g�������s���B
        Set<Map.Entry<String, Object>> set = propMap.entrySet();
        int index = 0;
        for (Entry<String, Object> e : set) {
            if (index >= startIndex &&
                    startIndex < size && index <= endIndex && index < size) {
                resetValue(form, e);
            }
            index++;
        }
    }
}
