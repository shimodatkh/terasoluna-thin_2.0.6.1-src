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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *　ダウンロード時の指定ファイル名をエンコードするクラス。
 * <p>
 * このクラスではInternet Explorerのみに対応している。
 */
public class DownloadFileNameEncoderImpl implements DownloadFileNameEncoder {

    /**
     * ログクラス。
     */
    private static final Log log =
        LogFactory.getLog(DownloadFileNameEncoderImpl.class);

    /**
     * ダウンロード時の指定ファイル名をエンコードする。
     *
     * @param original 元の文字列。
     * @return エンコード済みの文字列。
     */
    public String encode(String original, HttpServletRequest request, HttpServletResponse response) {
        // 文字列がnullの時にはnullを返却する。
        if(original == null){
            return null;
        }
        
        try {
            return URLEncoder.encode(original,
            		AbstractDownloadObject.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
        	// 通常、この例外処理が実行されることは無い。
        	if (log.isWarnEnabled()) {
        		log.warn(AbstractDownloadObject.DEFAULT_CHARSET
        				+ " is not supported encoding", e);
        	}
        	return original;
        }
    }
}
