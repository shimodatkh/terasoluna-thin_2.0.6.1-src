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

package jp.terasoluna.fw.web.struts.action;

import java.util.Map;

/**
 * DBMessageResources��DB�A�N�Z�X����ۂɗp������DAO�N���X���������ׂ�
 * �C���^�t�F�[�X�B
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResources
 * @see jp.terasoluna.fw.web.struts.action.DBMessageResourcesFactory
 * 
 */
public interface MessageResourcesDAO {

    /**
     * ���b�Z�[�W���\�[�X���擾����B
     * 
     * �^����ꂽSQL�̑��J������Map�̃L�[�A���J������Map�̒l�Ƃ���
     * �ݒ肵��Map��Ԃ��B
     * SQL�ŕԂ��ꂽ�r���[�̑��J������unique�łȂ��ꍇ�̓���͕ۏ؂��Ȃ��B
     * 
     * @param sql ���b�Z�[�W���\�[�X���擾���邽�߂�SQL
     * 
     * @return �L�[�Ƀ��b�Z�[�W�L�[�A�l�Ƀ��b�Z�[�W�����̓�����Map
     */
    Map<String, String> queryMessageMap(String sql);

}
