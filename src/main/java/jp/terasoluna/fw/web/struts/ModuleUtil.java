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

package jp.terasoluna.fw.web.struts;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.RequestUtil;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;

/**
 * <p>
 *  <code>ModuleConfig</code> �Ɋւ��郆�[�e�B���e�B�B
 * </p>
 *
 */
public class ModuleUtil {

    /**
     * ���N�G�X�g�Ɋ֘A���� <code>ModuleConfig</code> �C���X�^���X��Ԃ��܂��B
     *
     * @param request ���N�G�X�g���
     * @return ModuleConfig�C���X�^���X�B�����̃��N�G�X�g��null�̏ꍇ�A
     *     ���N�G�X�g�A�T�[�u���b�g�R���e�L�X�g���烂�W���[���R���t�B�O��
     *     �擾�ł��Ȃ��ꍇ��null��ԋp����B
     */
    public static ModuleConfig getModuleConfig(HttpServletRequest request) {

        if (request == null) {
            return null;
        }

        //ServletContext���擾
        ServletContext application = RequestUtil.getServletContext(request);

        //ModuleConfig���擾
        ModuleConfig config =
            (ModuleConfig) request.getAttribute(Globals.MODULE_KEY);

        //���N�G�X�g�Ƀ��W���[���������ꍇ�̓f�t�H���g���W���[����Ԃ�
        if (config == null) {
            config =
                (ModuleConfig) application.getAttribute(Globals.MODULE_KEY);
        }

        return config;

    }

    /**
     * ���N�G�X�g�Ɋ֘A�������W���[�����̃v���t�B�b�N�X�p�X��Ԃ��܂��B
     *
     * @param request ���N�G�X�g���
     * @return ���W���[���v���t�B�b�N�X�B������request��null�̏ꍇ�A
     *     getModuleConfig�̌��ʂ�null�̏ꍇ��null��ԋp����B
     */
    public static String getPrefix(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        ModuleConfig config = getModuleConfig(request);
        //Prefix���擾
        String prefix = null;
        if (config != null) {
            prefix = config.getPrefix();
        }
        return prefix;
    }


}
