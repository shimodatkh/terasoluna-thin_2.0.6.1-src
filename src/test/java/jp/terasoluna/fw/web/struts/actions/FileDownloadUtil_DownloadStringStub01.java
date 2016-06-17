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

/**
 * FileDownloadUtilTestで利用する。 存在し得ないデータサイズを返却するためのクラス。
 * 
 * 
 */
public class FileDownloadUtil_DownloadStringStub01 extends DownloadString {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1804644152887201759L;

    public int lengthOfData;

    public FileDownloadUtil_DownloadStringStub01(String name, String value) {
        super(name, value);

    }

    @Override
    protected byte[] getContent() {

        return new byte[0];
    }

    @Override
    public int getLengthOfData() {

        return lengthOfData;
    }

}
