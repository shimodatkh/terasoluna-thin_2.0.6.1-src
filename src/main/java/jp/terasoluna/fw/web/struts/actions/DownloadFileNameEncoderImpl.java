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
 *�@�_�E�����[�h���̎w��t�@�C�������G���R�[�h����N���X�B
 * <p>
 * ���̃N���X�ł�Internet Explorer�݂̂ɑΉ����Ă���B
 */
public class DownloadFileNameEncoderImpl implements DownloadFileNameEncoder {

    /**
     * ���O�N���X�B
     */
    private static final Log log =
        LogFactory.getLog(DownloadFileNameEncoderImpl.class);

    /**
     * �_�E�����[�h���̎w��t�@�C�������G���R�[�h����B
     *
     * @param original ���̕�����B
     * @return �G���R�[�h�ς݂̕�����B
     */
    public String encode(String original, HttpServletRequest request, HttpServletResponse response) {
        // ������null�̎��ɂ�null��ԋp����B
        if(original == null){
            return null;
        }
        
        try {
            return URLEncoder.encode(original,
            		AbstractDownloadObject.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
        	// �ʏ�A���̗�O���������s����邱�Ƃ͖����B
        	if (log.isWarnEnabled()) {
        		log.warn(AbstractDownloadObject.DEFAULT_CHARSET
        				+ " is not supported encoding", e);
        	}
        	return original;
        }
    }
}
