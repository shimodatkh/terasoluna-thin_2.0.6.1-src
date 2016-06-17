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
 * <code>String</code>をダウンロードデータとするためのクラス。
 */
public class DownloadString extends AbstractDownloadObject {

    /**
     * シリアルバージョン<code>UID</code>。
     */
    private static final long serialVersionUID = -7476231978581381416L;

    /**
     * ログクラス。
     */
    private Log log = LogFactory.getLog(DownloadString.class);

    /**
     * ダウンロード内容。
     */
    protected String value = null;

    /**
     * コンストラクタ。
     *
     * @param name ダウンロード名。
     * @param value ダウンロード内容。
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
     * ダウンロードサイズを取得する。
     *
     * @return ダウンロードサイズ。
     */
    @Override
    public int getLengthOfData() {
        return getContent().length;
    }

    /**
     * ダウンロード内容をバイト配列として取得する。
     *
     * @return バイト配列。
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
     * ダウンロード内容のストリームを内部的に取得する。
     *
     * @return ストリーム。
     */
    @Override
    protected InputStream getStreamInternal() throws IOException {
        return new ByteArrayInputStream(getContent());
    }

}
