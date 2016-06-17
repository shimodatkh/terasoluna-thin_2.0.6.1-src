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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �_�E�����[�h���e��ێ����钊�ۃN���X�B
 */
public abstract class AbstractDownloadObject implements Serializable {

    /**
     * �f�t�H���g�̃G���R�[�f�B���O(<code>UTF-8</code>)
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * �f�t�H���g�̃R���e���g�^�C�v(<code>application/octetstream</code>)
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    /**
     * �_�E�����[�h���e�����݂��Ȃ��ꍇ�̃G���[�R�[�h�B
     */
    public static final String NO_DOWNLOAD_CONTENT_ERROR =
        "errors.no.download.content";

    /**
     * �R���e���g�^�C�v�B
     */
    protected String contentType = null;

    /**
     * �_�E�����[�h���B
     */
    protected String name = null;

    /**
     * �G���R�[�f�B���O�B
     */
    protected String charset = null;

    /**
     * �ǉ������w�b�_��ێ�����}�b�v�B
     */
    protected Map<String, List<String>> additionalHeaders =
        new HashMap<String, List<String>>();

    /**
     * �R���X�g���N�^�B
     *
     * @param name �_�E�����[�h���B
     * @param contentType �R���e���g�^�C�v�B
     * @param charset �G���R�[�f�B���O�B
     */
    public AbstractDownloadObject(String name,
            String contentType, String charset) {
        this.name = name;
        setContentType(contentType);
        setCharset(charset);
    }

    /**
     * �G���R�[�f�B���O���擾����B
     *
     * @return �G���R�[�f�B���O�B
     */
    public String getCharset() {
        return charset;
    }

    /**
     * �G���R�[�f�B���O��ݒ肷��B
     *
     * @param charset �G���R�[�f�B���O�B
     */
    public void setCharset(String charset) {
        this.charset = charset;
        if (this.charset == null) {
            this.charset = DEFAULT_CHARSET;
        }
    }

    /**
     * �R���e���g�^�C�v���擾����B
     *
     * @return �R���e���g�^�C�v�B
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * �R���e���g�^�C�v��ݒ肷��B
     *
     * @param contentType �R���e���g�^�C�v�B
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (this.contentType == null) {
            setContentType(DEFAULT_CONTENT_TYPE);
        }
    }

    /**
     * �_�E�����[�h�����擾����B
     *
     * @return �_�E�����[�h���B
     */
    public String getName() {
        return name;
    }

    /**
     * �_�E�����[�h����ݒ肷��B
     *
     * @param name �_�E�����[�h���B
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �_�E�����[�h���e�̃X�g���[�����擾����B
     *
     * @return �X�g���[���B
     */
    public InputStream getStream() throws IOException {
        InputStream stream = getStreamInternal();
        if (!(stream instanceof BufferedInputStream)) {
            stream = new BufferedInputStream(stream);
        }
        return stream;
    }

    /**
     * �ǉ��̃��X�|���X�w�b�_��ݒ肷��B
     *
     * @param name �w�b�_���B
     * @param value �w�b�_�̒l�B
     */
    public void addHeader(String name, String value) {
        List<String> headerValues = additionalHeaders.get(name);
        if (headerValues == null) {
            headerValues = new ArrayList<String>();
            additionalHeaders.put(name, headerValues);
        }
        headerValues.add(value);
    }

    /**
     * �ǉ��̃��X�|���X�w�b�_���擾����B
     *
     * @return �ǉ��̃��X�|���X�w�b�_�B
     */
    public Map<String, List<String>> getAdditionalHeaders() {
        return additionalHeaders;
    }

    /**
     * �_�E�����[�h���e�̃X�g���[��������I�Ɏ擾����B
     *
     * @return �X�g���[���B
     */
    protected abstract InputStream getStreamInternal() throws IOException;

    /**
     * �_�E�����[�h�T�C�Y���擾����B
     *
     * @return �_�E�����[�h�T�C�Y�B
     */
    public abstract int getLengthOfData();
}
