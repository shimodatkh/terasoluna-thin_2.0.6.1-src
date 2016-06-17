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

package jp.terasoluna.fw.web.struts.actions;

import java.io.IOException;
import java.io.InputStream;

import jp.terasoluna.fw.exception.SystemException;

/**
 * �C���v�b�g�X�g���[�����_�E�����[�h�f�[�^�Ƃ��邽�߂̃N���X�B
 */
public class DownloadInputStream extends AbstractDownloadObject {

    /**
     * �V���A���o�[�W����<code>UID</code>�B
     */
    private static final long serialVersionUID = 7203645849484823770L;

    /**
     * �_�E�����[�h���e�̃C���v�b�g�X�g���[���B
     */
    transient protected InputStream stream = null;

    /**
     * �R���X�g���N�^�B
     *
     * @param name �_�E�����[�h���B
     * @param stream �C���v�b�g�X�g���[���B
     */
    public DownloadInputStream(String name, InputStream stream) {
        super(name, null, null);
        if (stream == null) {
            throw new SystemException(new IllegalArgumentException(
            "Download content must not be null"),
            NO_DOWNLOAD_CONTENT_ERROR);
        }
        this.stream = stream;
    }

    /**
     * �_�E�����[�h�T�C�Y���擾����B
     * <p>
     * ���̃N���X�ł͎擾�ł��Ȃ�����<code>-1</code>���ԋp�����B
     *
     * @return �_�E�����[�h�T�C�Y�B
     */
    @Override
    public int getLengthOfData() {
        return -1;
    }

    /**
     * �_�E�����[�h���e�̃X�g���[��������I�Ɏ擾����B
     *
     * @return �X�g���[���B
     */
    @Override
    protected InputStream getStreamInternal() throws IOException {
        return stream;
    }

}
