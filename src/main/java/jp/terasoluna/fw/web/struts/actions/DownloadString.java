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
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>String</code>���_�E�����[�h�f�[�^�Ƃ��邽�߂̃N���X�B
 */
public class DownloadString extends AbstractDownloadObject {

    /**
     * �V���A���o�[�W����<code>UID</code>�B
     */
    private static final long serialVersionUID = -7476231978581381416L;

    /**
     * ���O�N���X�B
     */
    private Log log = LogFactory.getLog(DownloadString.class);

    /**
     * �_�E�����[�h���e�B
     */
    protected String value = null;

    /**
     * �R���X�g���N�^�B
     *
     * @param name �_�E�����[�h���B
     * @param value �_�E�����[�h���e�B
     */
    public DownloadString(String name, String value) {
        super(name, null, null);
        if (value == null) {
            throw new SystemException(new IllegalArgumentException(
            "Download content must not be null"),
            NO_DOWNLOAD_CONTENT_ERROR);
        }
        this.value = value;
    }

    /**
     * �_�E�����[�h�T�C�Y���擾����B
     *
     * @return �_�E�����[�h�T�C�Y�B
     */
    @Override
    public int getLengthOfData() {
        return getContent().length;
    }

    /**
     * �_�E�����[�h���e���o�C�g�z��Ƃ��Ď擾����B
     *
     * @return �o�C�g�z��B
     */
    protected byte[] getContent() {
        try {
            return value.getBytes(getCharset());
        } catch (UnsupportedEncodingException e) {
            if (log.isWarnEnabled()) {
                log.warn("Charset is not valid.", e);
            }
            return value.getBytes();
        }
    }

    /**
     * �_�E�����[�h���e�̃X�g���[��������I�Ɏ擾����B
     *
     * @return �X�g���[���B
     */
    @Override
    protected InputStream getStreamInternal() throws IOException {
        return new ByteArrayInputStream(getContent());
    }

}
