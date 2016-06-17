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
 * ファイルをダウンロード対象とするためのクラス。
 */
public class DownloadFile extends AbstractDownloadObject {

    /**
     * シリアルバージョン<code>UID</code>。
     */
    private static final long serialVersionUID = 1461327659352290323L;

    /**
     * ダウンロード対象のファイル。
     */
    protected File file = null;

    /**
     * 引数にファイルを持つコンストラクタ。
     * ダウンロードファイルに設定する。
     *
     * @param file ファイル。
     */
    public DownloadFile(File file) {
        this(null, file);
    }

    /**
     * 引数にファイル名とファイルを持つコンストラクタ。
     * ダウンロードファイルに設定する。
     *
     * @param name ファイル名。
     * @param file ファイル。
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
     * ダウンロードサイズを取得する。
     *
     * @return ダウンロードサイズ。
     */
    @Override
    public int getLengthOfData() {
        return (int) file.length();
    }

    /**
     * ダウンロード内容のストリームを内部的に取得する。
     *
     * @return ストリーム。
     */
    @Override
    protected InputStream getStreamInternal() throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
