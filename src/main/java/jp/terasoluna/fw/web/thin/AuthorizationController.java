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

package jp.terasoluna.fw.web.thin;

import javax.servlet.ServletRequest;


/**
 * Filter�ł̃A�N�Z�X�����`�F�b�N���s�Ȃ��C���^�t�F�[�X�B
 *
 * <p>
 * ���̃C���^�t�F�[�X�����������N���X�̓A�N�Z�X�����`�F�b�N�@�\��񋟂���B
 * </p>
 * <p>
 * �Ȃ��A���̃C���^�t�F�[�X�̎����N���X�̓X���b�h�Z�[�t�ł���K�v������B
 * </p>
 * <p>
 * �����̃C���^�t�F�[�X�̎����N���X�̐ݒ���@��
 * {@link AuthorizationControlFilter} ���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.BlockageController
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 *
 */
public interface AuthorizationController {

    /**
     * ���N�G�X�g�̃p�X���ɑ΂��āA�w�肳�ꂽHTTP�Z�b�V������
     * �����`�F�b�N���s���B
     *
     * @param pathInfo �p�X���
     * @param req HTTP���N�G�X�g
     * 
     * @return �����`�F�b�N�ɐ�������� <code>true</code>�B
     */
    boolean isAuthorized(String pathInfo, ServletRequest req);
    
    /**
     * ���O�I���ς݃`�F�b�N���K�v���ǂ�����Ԃ��B
     * 
     * @param req ����ΏۂƂȂ� <code>ServletRequest</code>�C���X�^���X
     * 
     * @return �`�F�b�N���K�v�ł����<code>true</code>
     */
    boolean isCheckRequired(ServletRequest req);

}
