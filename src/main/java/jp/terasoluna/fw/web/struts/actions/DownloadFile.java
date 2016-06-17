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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.lang.StringUtils;

/**
 * �t�@�C�����_�E�����[�h�ΏۂƂ��邽�߂̃N���X�B
 */
public class DownloadFile extends AbstractDownloadObject {

    /**
     * �V���A���o�[�W����<code>UID</code>�B
     */
    private static final long serialVersionUID = 1461327659352290323L;

    /**
     * �_�E�����[�h�Ώۂ̃t�@�C���B
     */
    protected File file = null;

    /**
     * �����Ƀt�@�C�������R���X�g���N�^�B
     * �_�E�����[�h�t�@�C���ɐݒ肷��B
     *
     * @param file �t�@�C���B
     */
    public DownloadFile(File file) {
        this(null, file);
    }

    /**
     * �����Ƀt�@�C�����ƃt�@�C�������R���X�g���N�^�B
     * �_�E�����[�h�t�@�C���ɐݒ肷��B
     *
     * @param name �t�@�C�����B
     * @param file �t�@�C���B
     */
    public DownloadFile(String name, File file) {
        super(name, null, null);
        this.file = file;
        if (file == null) {
            throw new SystemException(new IllegalArgumentException(
            "Download content must not be null"),
            NO_DOWNLOAD_CONTENT_ERROR);
        }
        if (StringUtils.isEmpty(getName())) {
            setName(file.getName());
        }
    }

    /**
     * �_�E�����[�h�T�C�Y���擾����B
     *
     * @return �_�E�����[�h�T�C�Y�B
     */
    @Override
    public int getLengthOfData() {
        return (int) file.length();
    }

    /**
     * �_�E�����[�h���e�̃X�g���[��������I�Ɏ擾����B
     *
     * @return �X�g���[���B
     */
    @Override
    protected InputStream getStreamInternal() throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
