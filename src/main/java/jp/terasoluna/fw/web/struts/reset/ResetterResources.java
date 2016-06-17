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

import java.util.HashMap;
import java.util.Map;

/**
 * ���Z�b�g�p�̐ݒ����ێ�����N���X�B
 *
 * <p>
 * �t�H�[�����Z�b�g��`�t�@�C��(reset.xml)����ǂݍ��܂ꂽ�ݒ����
 * ���̃N���X�Ɋi�[����ێ�����B
 * �X�̐ݒ����{@link ActionReset}�A{@link FieldReset}
 * �̃C���X�^���X�Ƃ��ĕێ�����B<br>
 * </p>
 *

 * @see jp.terasoluna.fw.web.struts.reset.Resetter
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 * @see jp.terasoluna.fw.web.struts.reset.ActionReset
 * @see jp.terasoluna.fw.web.struts.reset.FieldReset
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see 
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ResetterResources {

    /**
     * ���̃N���X�̃C���X�^���X���T�[�u���b�g�R���e�L�X�g�ɕۑ�
     * ����ۂ̃L�[�B
     */
    public static final String RESETTER_RESOURCES_KEY = "RESETTER_RESOURCES";

    /**
     * �A�N�V�����v�f��ێ����� Map�B
     */
    private Map<String, ActionReset> actionResets =
            new HashMap<String, ActionReset>();

    /**
     * ActionReset ��ݒ肷��B
     *
     * @param actionReset �A�N�V�����v�f�̐ݒ���
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     */
    public void setActionReset(ActionReset actionReset) {
        this.actionResets.put(actionReset.getPath(), actionReset);
    }

    /**
     * �A�N�V�����p�X���L�[�Ƃ��� ActionReset 
     * �C���X�^���X���擾����B
     *
     * @param path �A�N�V�����p�X
     * @return �����̃p�X�ƕR�Â����A�N�V�����v�f
     * @see jp.terasoluna.fw.web.struts.reset.ActionReset
     */
    public ActionReset get(String path) {
        return actionResets.get(path);
    }

}
