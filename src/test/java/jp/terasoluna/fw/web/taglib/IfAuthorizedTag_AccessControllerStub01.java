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

package jp.terasoluna.fw.web.taglib;

import javax.servlet.ServletRequest;

import jp.terasoluna.fw.web.thin.AuthorizationController;

/**
 * IfAuthorizedTag_AccessControllerStub01�B<br>
 *
 * <code>AccessController</code>���p������Stub�N���X�B
 *
 */
public class IfAuthorizedTag_AccessControllerStub01
    implements AuthorizationController {

    /**
     * AccessController�N���X�� <code>isAuthorized(String pathInfo, 
     * ServletRequest req)</code> ���I�[�o�[���C�h����B
     * ��� <code>true</code> ��Ԃ����߁A
     *
     * @param pathInfo �p�X���
     * @param req HTTP���N�G�X�g
     * @return �����`�F�b�N�ɐ�������� <code>true</code>�B
     *         ���̃N���X�̎����ł͏�� <code>true</code>
     */
    public boolean isAuthorized(String pathInfo, ServletRequest req) {
        // �T�u�N���X�ŃI�[�o�[���C�h����
        return true;
    }

    /**
     * ���N�G�X�g�̃p�X���ɑ΂��āA�w�肳�ꂽHTTP�Z�b�V������
     * �F�؍ς݂ł��邩�ǂ����𔻒肷��B
     *
     * @param pathInfo �p�X���
     * @param req HTTP���N�G�X�g
     * @return �F�؂ɐ�������� <code>true</code>�B
     *         ���̃N���X�̎����ł͏�� <code>false</code>
     */
    public boolean isAuthenticated(String pathInfo, ServletRequest req) {
        return false;
    }

    /**
     * �ʋƖ��Ɉڂ������ǂ����p�X�`�F�b�N���s���A���肷��B
     * @param req ����ΏۂƂȂ� <code>ServletRequest</code> �N���X�C���X�^���X
     * @return �ʋƖ��Ɉڂ��Ă����<code>true</code>�B
     *         ���̃N���X�̎����ł͏�� <code>false</code>
     */
    public boolean isCheckRequired(ServletRequest req) {
        return false;
    }

}
