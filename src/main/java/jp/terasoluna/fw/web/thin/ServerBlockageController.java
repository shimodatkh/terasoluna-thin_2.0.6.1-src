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

import java.util.Date;

/**
 * Filter�ł̃T�[�o�ǃ`�F�b�N���s�Ȃ��C���^�t�F�[�X�B
 * <p>
 * ���̃C���^�t�F�[�X�����������N���X�̓T�[�o�ǃ`�F�b�N�@�\��񋟂���B 
 * </p>
 * <p>
 * �Ȃ��A���̃C���^�t�F�[�X�̎����N���X�̓X���b�h�Z�[�t�ł���K�v������B
 * </p>
 * <p>
 * �����̃C���^�t�F�[�X�̎����N���X�̐ݒ���@��
 * {@link ServerBlockageControlFilter} ���Q�Ƃ̂��ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.BlockageController
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 *
 */
public interface ServerBlockageController {

    /**
     * �Ǐ�ԂɑJ�ڂ���B
     */
    void blockade();

    /**
     * �Ǐ�Ԃ��ǂ����𔻕ʂ���B
     *
     * @return �Ǐ�ԂȂ� true
     */
    boolean isBlockaded();

    /**
     * �Ǐ�Ԃ��ǂ����𔻕ʂ���B
     *
     * @param pathInfo �p�X���
     * 
     * @return �Ǐ�ԂȂ� true
     */
    boolean isBlockaded(String pathInfo);

    /**
     * �\�Ǐ�Ԃ��ǂ����𔻕ʂ���B
     *
     * @return �\�Ǐ�ԂȂ� true
     */
    boolean isPreBlockaded();

    /**
     * �ʏ��ԂɑJ�ڂ���B
     */
    void open();

    /**
     * �\�Ǐ�ԂɑJ�ڂ���B
     */
    void preBlockade();

    /**
     * �\�Ǐ�ԂɑJ�ڂ��A
     * �w�肳�ꂽ�����ɂȂ����Ƃ��ɕǏ�ԂɑJ�ڂ���B
     *
     * @param time �Ǐ�ԂɑJ�ڂ������
     */
    void preBlockade(Date time);
}
