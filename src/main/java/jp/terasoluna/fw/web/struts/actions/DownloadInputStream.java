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
 * インプットストリームをダウンロードデータとするためのクラス。
 */
public class DownloadInputStream extends AbstractDownloadObject {

    /**
     * シリアルバージョン<code>UID</code>。
     */
    private static final long serialVersionUID = 7203645849484823770L;

    /**
     * ダウンロード内容のインプットストリーム。
     */
    transient protected InputStream stream = null;

    /**
     * コンストラクタ。
     *
     * @param name ダウンロード名。
     * @param stream インプットストリーム。
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
     * ダウンロードサイズを取得する。
     * <p>
     * このクラスでは取得できないため<code>-1</code>が返却される。
     *
     * @return ダウンロードサイズ。
     */
    @Override
    public int getLengthOfData() {
        return -1;
    }

    /**
     * ダウンロード内容のストリームを内部的に取得する。
     *
     * @return ストリーム。
     */
    @Override
    protected InputStream getStreamInternal() throws IOException {
        return stream;
    }

}
