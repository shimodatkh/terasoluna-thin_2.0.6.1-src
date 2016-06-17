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

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * ファイルダウンロードを行うユーティリティクラス。
 * <p>
 * <h5>ダウンロードファイル名のエンコーディング変更</h5>
 * ダウンロード時の指定ファイル名（ブラウザで保存する際に表示されるファイル名）の
 * エンコーディングはデフォルトではInternet Explorerのみに対応している。
 * エンコーディングを変更するためには、{@link DownloadFileNameEncoder}実装クラスを
 * 作成して、Bean定義ファイルで設定を行う必要がある。
 *
 * <h5><code>DownloadFileNameEncoder</code>実装例</h5>
 * この例では<code>User-Agent</code>でブラウザを判別し、
 * FireFoxの場合はcommons-codecのクラスを利用してエンコーディングを行っている。
 * <pre><code>
 * public class MyEncoder implements DownloadFileNameEncoder {
 *
 *     public String encode(String original, HttpServletRequest request,
 *             HttpServletResponse response) {
 *         String userAgent = request.getHeader("User-Agent");
 *         if (StringUtils.contains(userAgent, "MSIE")) {
 *             return encodeForIE(original);
 *         } else if (StringUtils.contains(userAgent, "Gecko")) {
 *             return encodeForGecko(original);
 *         }
 *         return encodeForIE(original);
 *     }
 *
 *     protected String encodeForGecko(String original) {
 *         try {
 *             return new BCodec().encode(original);
 *         } catch (EncoderException e) {
 *             return original;
 *         }
 *     }
 *
 *     protected String encodeForIE(String original) {
 *         try {
 *             return URLEncoder.encode(original,
 *                     AbstractDownloadObject.DEFAULT_CHARSET);
 *         } catch (UnsupportedEncodingException e) {
 *             return original;
 *         }
 *     }
 * }
 * </code></pre>
 *
 * <h5>Bean定義ファイル設定例</h5>
 * <pre><code>
 * &lt;bean class=&quot;jp.terasoluna.fw.web.struts.actions.FileDownloadUtil&quot;&gt;
 *   &lt;property name=&quot;encoder&quot; ref=&quot;encoder&quot;/&gt;
 * &lt;/bean&gt;
 * &lt;bean name=&quot;encoder&quot; class=&quot;sample.MyEncoder&quot;/&gt;
 * </code></pre>
 */
public class FileDownloadUtil {

    /**
     * ログクラス。
     */
    private final static Log log = LogFactory.getLog(FileDownloadUtil.class);

    /**
     * レスポンスの<code>CONTENT-DISPOSITION</code>ヘッダ名。
     */
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * ダウンロード対象のインスタンスが1つ以上ある場合のエラーコード。
     */
    public static final String TOO_MANY_DOWNLOAD_ERROR = "errors.too.many.download";

    /**
     * 指定ファイル名のエンコーダ。
     */
    protected static DownloadFileNameEncoder encoder =
        new DownloadFileNameEncoderImpl();

    /**
     * 指定ファイル名のエンコーダを設定する。
     *
     * @param encoder 指定ファイル名のエンコーダ。
     */
    public void setEncoder(DownloadFileNameEncoder encoder) {
    	if (encoder == null) {
    		throw new IllegalArgumentException("encoder must not be null.");
    	}
        FileDownloadUtil.encoder = encoder;
    }

    /**
     * ブラウザにダウンロードをさせる。
     *
     * @param result ダウンロードデータを保持するインスタンス。
     * @param request リクエスト。
     * @param response レスポンス。
     */
    @SuppressWarnings("unchecked")
    public static void download(Object result, HttpServletRequest request,
            HttpServletResponse response) {
        List<AbstractDownloadObject> downloadList =
            new ArrayList<AbstractDownloadObject>();

        if (result instanceof AbstractDownloadObject) {
            downloadList.add((AbstractDownloadObject) result);
        } else {
            BeanWrapper wrapper = new BeanWrapperImpl(result);
            PropertyDescriptor[] propertyDescriptors =
                wrapper.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod == null) {
                    continue;
                }
                Class type = readMethod.getReturnType();
                if (AbstractDownloadObject.class.isAssignableFrom(type)) {
                    downloadList.add(
                            (AbstractDownloadObject) wrapper.getPropertyValue(
                                    propertyDescriptor.getName()));
                }
            }
        }

        if (downloadList.isEmpty()) {
            return;
        }
        // ダウンロードオブジェクトが複数ある場合は例外とする
        if (downloadList.size() != 1) {
            throw new SystemException(new IllegalStateException(
                    "Too many AbstractDownloadObject properties."),
                    TOO_MANY_DOWNLOAD_ERROR);
        }

        try {
            download(downloadList.get(0), request, response, true);
        } catch (SocketException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("IOException has occurred while downloading", e);
            }
        }
    }

    /**
     * ブラウザにダウンロードをさせる。
     * @param downloadObject ダウンロード対象。
     * @param request リクエスト。
     * @param response レスポンス。
     *
     * @throws IOException ダウンロード時に入出力例外が発生した場合。
     */
    public static void download(AbstractDownloadObject downloadObject,
            HttpServletRequest request, HttpServletResponse response,
            boolean forceDownload)  throws IOException {

        // downloadObjectがnullの場合、処理しない
        if (downloadObject == null) {
            if (log.isWarnEnabled()) {
                log.warn("No download object.");
            }
            return;
        }

        // ヘッダ情報の取得。
        Map<String, List<String>> additionalHeaders =
            downloadObject.getAdditionalHeaders();
        
        // ヘッダ情報がnullの場合、処理しない
        if (additionalHeaders == null) {
            if (log.isWarnEnabled()) {
                log.warn("Header must not be null.");
            }
            return;
        }
        
        // ヘッダの設定。
        Set<Entry<String, List<String>>> entrySet = additionalHeaders.entrySet();
        for (Entry<String, List<String>> entry : entrySet) {
            String headerName = entry.getKey();
            List<String> headerValues = entry.getValue();
            
            // ヘッダに設定するキーや値のリストがnullの場合、処理しない
            if (headerValues == null || headerName == null){
                if (log.isWarnEnabled()) {
                    log.warn("Header name and value must not be null.");
                }
                return;
            }            
            for (String headerValue : headerValues) {
                // ヘッダに設定する値がnullのときは空文字列に変換
                if(headerValue == null){
                    headerValue = "";
                }
                response.addHeader(headerName, headerValue);
            }
        }

        // エンコーディング設定
        String charSet = downloadObject.getCharset();
        if (StringUtils.isNotEmpty(charSet)) {
            response.setCharacterEncoding(downloadObject.getCharset());
        }

        // コンテントタイプ設定
        String contentType = downloadObject.getContentType();
        if (StringUtils.isNotEmpty(contentType)) {
            response.setContentType(downloadObject.getContentType());
        }

        // データサイズ設定
        int contentLength = downloadObject.getLengthOfData();
        if (contentLength > 0) {
            response.setContentLength(downloadObject.getLengthOfData());
        }

        // ファイルの別名が存在する場合は、それを取得して設定。
        // 存在しない場合は空文字列を設定。
        String name = downloadObject.getName();
        if (name != null) {
            name = encoder.encode(name, request, response);
        } else {
            name = encoder.encode("", request, response);
        }
        setFileName(response, name, forceDownload);

        InputStream inputStream = downloadObject.getStream();
        OutputStream outputStream = null;

        try {
            // ダウンロード処理を行う
            outputStream = response.getOutputStream();
            Streams.copy(inputStream, outputStream, false);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }

    }

    /**
     * ファイル名を設定する
     *
     * @param response レスポンス。
     * @param name ダウンロード名。
     * @param forceDownload 強制ダウンロードかどうか。<code>true</code>の場合、強制。
     */
    protected static void setFileName(HttpServletResponse response,
            String name, boolean forceDownload) {

        if (forceDownload) {
            // こちらにキャッシュ制御があるとIEでうまく動作しない
            String contentDispositionValue = "attachment;"
                + " filename=" + name;
            response.setHeader(HEADER_CONTENT_DISPOSITION,
                    contentDispositionValue);
        } else {
            String contentDispositionValue = "inline; filename=" + name;
            response.setHeader(HEADER_CONTENT_DISPOSITION,
                    contentDispositionValue);
        }
    }
}