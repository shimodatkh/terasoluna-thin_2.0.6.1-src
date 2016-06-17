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
 * Filter�ł̋Ɩ��ǃ`�F�b�N���s�Ȃ��C���^�t�F�[�X�B
 * <p>
 * ���̃C���^�t�F�[�X�����������N���X�͋Ɩ��ǃ`�F�b�N�@�\��񋟂���B
 * </p>
 * <p>
 * �Ȃ��A���̃C���^�t�F�[�X�̎����N���X�̓X���b�h�Z�[�t�ł���K�v������B
 * </p>
 * �����̃C���^�t�F�[�X�̎����N���X�̐ݒ���@��
 * {@link BlockageControlFilter} ���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.thin.AuthenticationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthenticationController
 * @see jp.terasoluna.fw.web.thin.AuthorizationControlFilter
 * @see jp.terasoluna.fw.web.thin.AuthorizationController
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 *
 */
public interface BlockageController {

    /**
     * �Ǐ�Ԃɂ���p�X��ݒ肷��B
     *
     * @param path �Ǐ�Ԃɂ���p�X
     */
    void blockade(String path);

    /**
     * �Ǐ�Ԃɂ���p�X��ݒ肷��B
     *
     * @param path �p�X���
     * @param req HTTP���N�G�X�g
     */
    void blockade(String path, ServletRequest req);

    /**
     * �w��p�X�̃A�N�V�������Ɩ��Ǐ�Ԃł��邩�`�F�b�N����B
     *
     * @param path �p�X���
     * 
     * @return �Ɩ��Ǐ�Ԃł����<code>true</code>
     */
    boolean isBlockaded(String path);

    /**
     * �w��p�X�̃A�N�V�������Ɩ��Ǐ�Ԃł��邩�`�F�b�N����B
     *
     * @param path �p�X���
     * @param req HTTP���N�G�X�g
     * 
     * @return �Ɩ��Ǐ�Ԃł���� <code>true</code>
     */
    boolean isBlockaded(String path, ServletRequest req);

    /**
     * �Ɩ��ǃ`�F�b�N���K�v���ǂ�����Ԃ��B
     *
     * @param req ����ΏۂƂȂ�ServletRequest�C���X�^���X
     * 
     * @return �`�F�b�N���K�v�ł����<code>true</code>
     */
    boolean isCheckRequired(ServletRequest req);

    /**
     * �Ǐ�Ԃɂ���p�X���J������B
     *
     * @param path �J���Ώۂ̃p�X
     */
    void open(String path);

    /**
     * �Ǐ�Ԃɂ���p�X���J������B
     *
     * @param path �J���Ώۂ̃p�X
     * @param req HTTP���N�G�X�g
     */
    void open(String path, ServletRequest req);

}
