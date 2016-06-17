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

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import jp.terasoluna.fw.exception.SystemException;

/**
 * �o�C�g�z����_�E�����[�h�f�[�^�Ƃ��邽�߂̃N���X�B
 */
public class DownloadByteArray extends AbstractDownloadObject {

    /**
     * �V���A���o�[�W����<code>UID</code>�B
     */
    private static final long serialVersionUID = 1L;

    /**
     * �_�E�����[�h���e�B
     */
    protected byte[] byteArray = null;

    /**
     *�R���X�g���N�^�B
     *
     * @param name �_�E�����[�h���B
     * @param byteArray �_�E�����[�h���e�ł���o�C�g�z��B
     */
    public DownloadByteArray(String name, byte[] byteArray) {
        super(name, null, null);
        if (byteArray == null) {
            throw new SystemException(new IllegalArgumentException(
                            "Download content must not be null"),
                NO_DOWNLOAD_CONTENT_ERROR);
        }
        this.byteArray = byteArray;
    }

    /**
     * �_�E�����[�h�T�C�Y���擾����B
     *
     * @return �_�E�����[�h�T�C�Y�B
     */
    @Override
    public int getLengthOfData() {
        return byteArray.length;
    }

    /**
     * �_�E�����[�h���e�̃X�g���[��������I�Ɏ擾����B
     *
     * @return �X�g���[���B
     */
    @Override
    public InputStream getStreamInternal() throws FileNotFoundException {
        return new ByteArrayInputStream(byteArray);
    }

}
