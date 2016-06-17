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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ダウンロード時の指定ファイル名をエンコードするクラスが実装するインタフェース。
 * <p>
 * このクラスを実装して、以下のように設定することで、エンコード方法を変更できる。
 * <h5>Bean定義ファイル</h5>
 * <pre><code>
 * &lt;bean class=&quot;jp.terasoluna.fw.web.struts.actions.FileDownloadUtil&quot;&gt;
 *   &lt;property name=&quot;encoder&quot; ref=&quot;encoder&quot;/&gt;
 * &lt;/bean&gt;
 * &lt;bean name=&quot;encoder&quot; class=&quot;sample.MyEncoder&quot;/&gt;
 * </code></pre>
 */
public interface DownloadFileNameEncoder {

    /**
     * ダウンロード時の指定ファイル名をエンコードする。
     *
     * @param original 元の文字列。
     * @return エンコード済みの文字列。
     */
    String encode(String original, HttpServletRequest request, HttpServletResponse response);
}
