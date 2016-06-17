/*
 * Copyright (c) 2007-2008 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts.action;

import org.apache.struts.config.ExceptionConfig;

/**
 * �V�X�e����O�̃}�b�s���O���N���X.
 *
 * <p>
 * ExceptionConfig���p�����A���W���[�����ƃ��O���x����
 * �t�B�[���h��ǉ�����B
 * </p>
 *
 * @see org.apache.struts.config.ExceptionConfig
 * @see jp.terasoluna.fw.web.struts.action.SystemExceptionHandler
 *
 */
public class ExceptionConfigEx extends ExceptionConfig {

    /**
     * �V���A���o�[�W����ID.
     */
    private static final long serialVersionUID = -1181268336500823437L;

    /**
     * ���W���[����.
     */
    private String module;

    /**
     * ���O���x��.
     */
    private String logLevel = null;

    /**
     * ���W���[�������擾����.
     *
     * @return ���W���[����
     */
    public String getModule() {
        return module;
    }

    /**
     * ���W���[������ݒ肷��.
     *
     * @param module ���W���[����
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * ���O���x�����擾����.
     * @return logLevel ���O���x��
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * ���O���x����ݒ肷��.
     * @param logLevel �ݒ肷�郍�O���x��
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}