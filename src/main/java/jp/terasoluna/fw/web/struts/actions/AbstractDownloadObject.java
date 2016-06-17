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
 * ダウンロード内容を保持する抽象クラス。
 */
public abstract class AbstractDownloadObject implements Serializable {

    /**
     * デフォルトのエンコーディング(<code>UTF-8</code>)
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * デフォルトのコンテントタイプ(<code>application/octetstream</code>)
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    /**
     * ダウンロード内容が存在しない場合のエラーコード。
     */
    public static final String NO_DOWNLOAD_CONTENT_ERROR =
        "errors.no.download.content";

    /**
     * コンテントタイプ。
     */
    protected String contentType = null;

    /**
     * ダウンロード名。
     */
    protected String name = null;

    /**
     * エンコーディング。
     */
    protected String charset = null;

    /**
     * 追加されるヘッダを保持するマップ。
     */
    protected Map<String, List<String>> additionalHeaders =
        new HashMap<String, List<String>>();

    /**
     * コンストラクタ。
     *
     * @param name ダウンロード名。
     * @param contentType コンテントタイプ。
     * @param charset エンコーディング。
     */
    public AbstractDownloadObject(String name,
            String contentType, String charset) {
        this.name = name;
        setContentType(contentType);
        setCharset(charset);
    }

    /**
     * エンコーディングを取得する。
     *
     * @return エンコーディング。
     */
    public String getCharset() {
        return charset;
    }

    /**
     * エンコーディングを設定する。
     *
     * @param charset エンコーディング。
     */
    public void setCharset(String charset) {
        this.charset = charset;
        if (this.charset == null) {
            this.charset = DEFAULT_CHARSET;
        }
    }

    /**
     * コンテントタイプを取得する。
     *
     * @return コンテントタイプ。
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * コンテントタイプを設定する。
     *
     * @param contentType コンテントタイプ。
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (this.contentType == null) {
            setContentType(DEFAULT_CONTENT_TYPE);
        }
    }

    /**
     * ダウンロード名を取得する。
     *
     * @return ダウンロード名。
     */
    public String getName() {
        return name;
    }

    /**
     * ダウンロード名を設定する。
     *
     * @param name ダウンロード名。
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ダウンロード内容のストリームを取得する。
     *
     * @return ストリーム。
     */
    public InputStream getStream() throws IOException {
        InputStream stream = getStreamInternal();
        if (!(stream instanceof BufferedInputStream)) {
            stream = new BufferedInputStream(stream);
        }
        return stream;
    }

    /**
     * 追加のレスポンスヘッダを設定する。
     *
     * @param name ヘッダ名。
     * @param value ヘッダの値。
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
     * 追加のレスポンスヘッダを取得する。
     *
     * @return 追加のレスポンスヘッダ。
     */
    public Map<String, List<String>> getAdditionalHeaders() {
        return additionalHeaders;
    }

    /**
     * ダウンロード内容のストリームを内部的に取得する。
     *
     * @return ストリーム。
     */
    protected abstract InputStream getStreamInternal() throws IOException;

    /**
     * ダウンロードサイズを取得する。
     *
     * @return ダウンロードサイズ。
     */
    public abstract int getLengthOfData();
}
