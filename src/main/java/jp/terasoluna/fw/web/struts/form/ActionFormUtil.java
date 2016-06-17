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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;

/**
 * �A�N�V�����t�H�[���֘A�̃��[�e�B���e�B�N���X�B
 *
 * <p>
 *  �A�N�V�����t�H�[�������֘A�ŗp������@�\�����[�e�B���e�B
 *  �Ƃ��ďW�񂵂Ă���B
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see
 *  jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ActionFormUtil {
    /**
     * �C���f�b�N�X���̍ő�l�̃L�[
     */
    private static final String FORM_ARRAY_MAX_LENGTH_KEY = "form.array.max.length";
    /**
     * �C���f�b�N�X���̍ő�l�̃f�t�H���g�l
     */
    private static final int DEFAULT_MAX_LENGTH = 5000;
    /**
     * �C���f�b�N�X���̍ő�l
     */
    private static final int MAX_LENGTH = getFormArrayMaxLength(PropertyUtil
            .getProperty(FORM_ARRAY_MAX_LENGTH_KEY));

    /**
     * �C���f�b�N�X���̍ő�l��ԋp����B
     * 
     * @param formArrayMaxLength �C���f�b�N�X���̍ő�l������
     * @return �C���f�b�N�X���̍ő�l
     */
    protected static int getFormArrayMaxLength(String formArrayMaxLength) {
        int max = DEFAULT_MAX_LENGTH;
        try {
            if (formArrayMaxLength != null) {
                max = Integer.parseInt(formArrayMaxLength);
            }
        } catch (Throwable e) {
            // ��O�����������ꍇ�̓f�t�H���g�l���g�p����
        }
        return max;
    }

    /**
     * �C���f�b�N�X�̒������`�F�b�N����B<br>
     * <p>
     * �C���f�b�N�X�����ő�l�����傫���ꍇ��{@link IllegalArgumentException}���X���[����B �ő�l�̃f�t�H���g��5000�ł���A�v���p�e�B�t�@�C���ɐݒ�\�ł���B<br>
     * {@link PropertyUtil}����A�N�Z�X�ł���v���p�e�B�t�@�C����<code>form.array.max.length</code>�̃L�[�ɑ΂��čő�l��ݒ肷�邱�ƁB<br>
     * �E<code>form.array.max.length</code>�̐ݒ肪�Ȃ�<br>
     * �E�ݒ�l��int�Ńp�[�X�o���Ȃ�<br>
     * �̏ꍇ�̓f�t�H���g�l���g�p����B
     * </p>
     * 
     * @param index �C���f�b�N�X�ʒu
     * @throws IllegalArgumentException �C���f�b�N�X�����ő�l�����傫���ꍇ
     */
    protected static void checkIndexLength(int index)
                                                     throws IllegalArgumentException {
        if (index > MAX_LENGTH) {
            throw new IllegalArgumentException("index size is too long. : "
                    + index);
        }
    }
    /**
     * �w�肵���t�B�[���h�̐ݒ�����擾����B
     *
     * @param fieldName �t�B�[���h��
     * @param mapping �}�b�s���O���
     * @return FormPropertyConfig
     */
    public static FormPropertyConfig getPropertyConfig(
        String fieldName,
        ActionMapping mapping) {
        String name = mapping.getName();
        if (name == null) {
            return null;
        }
        FormBeanConfig config =
            mapping.getModuleConfig().findFormBeanConfig(name);
        if (config == null) {
            return null;
        }
        return config.findFormPropertyConfig(fieldName);
    }

    /**
     * DynaActionForm�̎w�肳�ꂽ�v���p�e�B�l��
     * ����������B
     *
     * @param form DynaActionForm �̃C���X�^���X
     * @param fieldName �������Ώۂ̃v���p�e�B
     * @param mapping �A�N�V�����}�b�s���O
     */
    public static void initialize(
        DynaActionForm form,
        String fieldName,
        ActionMapping mapping) {
        // �p�����[�^�l�̃`�F�b�N
        if (form == null
            || fieldName == null
            || "".equals(fieldName)
            || mapping == null) {
            return;
        }

        FormPropertyConfig config =
            ActionFormUtil.getPropertyConfig(fieldName, mapping);
        if (config == null) {
            return;
        }
        form.set(fieldName, config.initial());

    }

    /**
     *  �Z�b�V�����X�R�[�v�Ɋi�[���ꂽ�_������&quot;_&quot;�Ŏn�܂�S�Ă�
     *  �A�N�V�����t�H�[�����폜���郆�[�e�B���e�B���\�b�h�B
     *
     * <p>
     *  ������ exclude �Ŏw�肳�ꂽ�A�N�V�����t�H�[���͍폜���Ȃ��B
     * </p>
     *
     * @param session HTTP�Z�b�V����
     * @param exclude �폜�ΏۂƂ��Ȃ��A�N�V�����t�H�[����
     */
    public static void clearActionForm(HttpSession session, String exclude) {

        Enumeration enumeration = session.getAttributeNames();
        List<String> removeList = new ArrayList<String>();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            if (key.startsWith("_")) {
                if (exclude == null || !key.equals(exclude)) {
                    if (session.getAttribute(key) instanceof ActionForm) {
                        removeList.add(key);
                    }
                }
            }
        }

        int cnt = removeList.size();
        for (int i = 0; i < cnt; i++) {
            String removeKey = removeList.get(i);
            session.removeAttribute(removeKey);
        }
    }

    /**
     * �Z�b�V�����X�R�[�v�Ɋi�[���ꂽ�_������&quot;_&quot;��
     * �n�܂�S�ẴA�N�V�����t�H�[�����폜���郆�[�e�B���e�B���\�b�h�B
     *
     * @param session HTTP�Z�b�V����
     */
    public static void clearActionForm(HttpSession session) {
        clearActionForm(session, null);
    }

    /**
     *  HTTP���N�G�X�g�����Ƃ��Đݒ肳��Ă���A�N�V�����}�b�s���O����
     *  �A�N�V�����t�H�[�������擾����B
     *
     * @param req HTTP���N�G�X�g
     * @return �A�N�V�����t�H�[����
     */
    public static String getActionFormName(HttpServletRequest req) {
        ActionMapping mapping =
            (ActionMapping) req.getAttribute(Globals.MAPPING_KEY);
        if (mapping == null) {

            // �ȍ~�͎��s���Ȃ�
            return null;
        }
        return mapping.getName();
    }

}
