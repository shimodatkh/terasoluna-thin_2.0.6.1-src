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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * �r�W�l�X���W�b�N���o�͏���ێ�����N���X�B
 * <p>
 *  blogic-io.xml����ǂݍ��܂ꂽ�ݒ���͂��̃N���X��
 *  �e�ƂȂ�ێ�����B
 *  �X�̐ݒ����BLogicIO�ABLogicProperty
 *  �̃C���X�^���X�Ƃ��ĕێ�����B<br>
 *  ���̃N���X�̃C���X�^���X�̓T�[�u���b�g�R���e�L�X�g��
 *  �wBLOGIC_RESOURCES + ���W���[�����x�Ƃ����L�[���ŕۑ������B
 *  <br>
 *  blogic-io.xml�̓ǂݍ��݂ɂ��Ă̏ڍׂ́A
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  ���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicResources implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 4787121961877175389L;

    /**
     * ���̃N���X�̃C���X�^���X���T�[�u���b�g�R���e�L�X�g�ɕۑ�
     * ����ۂ̃L�[�̃v���t�B�b�N�X�l�B
     */
    public static final String BLOGIC_RESOURCES_KEY = "BLOGIC_RESOURCES";

    /**
     * �A�N�V�����p�X���L�[�ɓ��o�͏���ێ�����Map�B
     */
    private Map<String, BLogicIO> blogicIO = new HashMap<String, BLogicIO>();

    /**
     * �A�N�V�����p�X�����L�[�ɂ��ē��o�͏����擾����B
     *
     * @param path �A�N�V�����p�X��
     * @return �w�肳�ꂽ�A�N�V�����p�X�ɕR�Â������o�͏��
     */
    public BLogicIO getBLogicIO(String path) {
        return blogicIO.get(path);
    }

    /**
     * ���o�͏���ݒ肷��B
     * ���o�͏��̓A�N�V�����p�X�����L�[�ɂ��ĕۑ������B
     * �Ȃ��A����blogicIO��null�̏ꍇ�́ANullPointerException����������B
     *
     * @param blogicIO �r�W�l�X���W�b�N���o�͏��
     */
    public void setBLogicIO(BLogicIO blogicIO) {
        this.blogicIO.put(blogicIO.getPath(), blogicIO);
    }

}
