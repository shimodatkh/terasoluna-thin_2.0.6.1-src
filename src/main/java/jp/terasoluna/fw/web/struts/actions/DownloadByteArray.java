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
 * バイト配列をダウンロードデータとするためのクラス。
 */
public class DownloadByteArray extends AbstractDownloadObject {

    /**
     * シリアルバージョン<code>UID</code>。
     */
    private static final long serialVersionUID = 1L;

    /**
     * ダウンロード内容。
     */
    protected byte[] byteArray = null;

    /**
     *コンストラクタ。
     *
     * @param name ダウンロード名。
     * @param byteArray ダウンロード内容であるバイト配列。
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
     * ダウンロードサイズを取得する。
     *
     * @return ダウンロードサイズ。
     */
    @Override
    public int getLengthOfData() {
        return byteArray.length;
    }

    /**
     * ダウンロード内容のストリームを内部的に取得する。
     *
     * @return ストリーム。
     */
    @Override
    public InputStream getStreamInternal() throws FileNotFoundException {
        return new ByteArrayInputStream(byteArray);
    }

}
