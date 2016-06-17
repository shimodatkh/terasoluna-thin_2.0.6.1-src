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
 * �t�@�C���_�E�����[�h���s�����[�e�B���e�B�N���X�B
 * <p>
 * <h5>�_�E�����[�h�t�@�C�����̃G���R�[�f�B���O�ύX</h5>
 * �_�E�����[�h���̎w��t�@�C�����i�u���E�U�ŕۑ�����ۂɕ\�������t�@�C�����j��
 * �G���R�[�f�B���O�̓f�t�H���g�ł�Internet Explorer�݂̂ɑΉ����Ă���B
 * �G���R�[�f�B���O��ύX���邽�߂ɂ́A{@link DownloadFileNameEncoder}�����N���X��
 * �쐬���āABean��`�t�@�C���Őݒ���s���K�v������B
 *
 * <h5><code>DownloadFileNameEncoder</code>������</h5>
 * ���̗�ł�<code>User-Agent</code>�Ńu���E�U�𔻕ʂ��A
 * FireFox�̏ꍇ��commons-codec�̃N���X�𗘗p���ăG���R�[�f�B���O���s���Ă���B
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
 * <h5>Bean��`�t�@�C���ݒ��</h5>
 * <pre><code>
 * &lt;bean class=&quot;jp.terasoluna.fw.web.struts.actions.FileDownloadUtil&quot;&gt;
 *   &lt;property name=&quot;encoder&quot; ref=&quot;encoder&quot;/&gt;
 * &lt;/bean&gt;
 * &lt;bean name=&quot;encoder&quot; class=&quot;sample.MyEncoder&quot;/&gt;
 * </code></pre>
 */
public class FileDownloadUtil {

    /**
     * ���O�N���X�B
     */
    private final static Log log = LogFactory.getLog(FileDownloadUtil.class);

    /**
     * ���X�|���X��<code>CONTENT-DISPOSITION</code>�w�b�_���B
     */
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * �_�E�����[�h�Ώۂ̃C���X�^���X��1�ȏ゠��ꍇ�̃G���[�R�[�h�B
     */
    public static final String TOO_MANY_DOWNLOAD_ERROR = "errors.too.many.download";

    /**
     * �w��t�@�C�����̃G���R�[�_�B
     */
    protected static DownloadFileNameEncoder encoder =
        new DownloadFileNameEncoderImpl();

    /**
     * �w��t�@�C�����̃G���R�[�_��ݒ肷��B
     *
     * @param encoder �w��t�@�C�����̃G���R�[�_�B
     */
    public void setEncoder(DownloadFileNameEncoder encoder) {
    	if (encoder == null) {
    		throw new IllegalArgumentException("encoder must not be null.");
    	}
        FileDownloadUtil.encoder = encoder;
    }

    /**
     * �u���E�U�Ƀ_�E�����[�h��������B
     *
     * @param result �_�E�����[�h�f�[�^��ێ�����C���X�^���X�B
     * @param request ���N�G�X�g�B
     * @param response ���X�|���X�B
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
        // �_�E�����[�h�I�u�W�F�N�g����������ꍇ�͗�O�Ƃ���
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
     * �u���E�U�Ƀ_�E�����[�h��������B
     * @param downloadObject �_�E�����[�h�ΏہB
     * @param request ���N�G�X�g�B
     * @param response ���X�|���X�B
     *
     * @throws IOException �_�E�����[�h���ɓ��o�͗�O�����������ꍇ�B
     */
    public static void download(AbstractDownloadObject downloadObject,
            HttpServletRequest request, HttpServletResponse response,
            boolean forceDownload)  throws IOException {

        // downloadObject��null�̏ꍇ�A�������Ȃ�
        if (downloadObject == null) {
            if (log.isWarnEnabled()) {
                log.warn("No download object.");
            }
            return;
        }

        // �w�b�_���̎擾�B
        Map<String, List<String>> additionalHeaders =
            downloadObject.getAdditionalHeaders();
        
        // �w�b�_���null�̏ꍇ�A�������Ȃ�
        if (additionalHeaders == null) {
            if (log.isWarnEnabled()) {
                log.warn("Header must not be null.");
            }
            return;
        }
        
        // �w�b�_�̐ݒ�B
        Set<Entry<String, List<String>>> entrySet = additionalHeaders.entrySet();
        for (Entry<String, List<String>> entry : entrySet) {
            String headerName = entry.getKey();
            List<String> headerValues = entry.getValue();
            
            // �w�b�_�ɐݒ肷��L�[��l�̃��X�g��null�̏ꍇ�A�������Ȃ�
            if (headerValues == null || headerName == null){
                if (log.isWarnEnabled()) {
                    log.warn("Header name and value must not be null.");
                }
                return;
            }            
            for (String headerValue : headerValues) {
                // �w�b�_�ɐݒ肷��l��null�̂Ƃ��͋󕶎���ɕϊ�
                if(headerValue == null){
                    headerValue = "";
                }
                response.addHeader(headerName, headerValue);
            }
        }

        // �G���R�[�f�B���O�ݒ�
        String charSet = downloadObject.getCharset();
        if (StringUtils.isNotEmpty(charSet)) {
            response.setCharacterEncoding(downloadObject.getCharset());
        }

        // �R���e���g�^�C�v�ݒ�
        String contentType = downloadObject.getContentType();
        if (StringUtils.isNotEmpty(contentType)) {
            response.setContentType(downloadObject.getContentType());
        }

        // �f�[�^�T�C�Y�ݒ�
        int contentLength = downloadObject.getLengthOfData();
        if (contentLength > 0) {
            response.setContentLength(downloadObject.getLengthOfData());
        }

        // �t�@�C���̕ʖ������݂���ꍇ�́A������擾���Đݒ�B
        // ���݂��Ȃ��ꍇ�͋󕶎����ݒ�B
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
            // �_�E�����[�h�������s��
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
     * �t�@�C������ݒ肷��
     *
     * @param response ���X�|���X�B
     * @param name �_�E�����[�h���B
     * @param forceDownload �����_�E�����[�h���ǂ����B<code>true</code>�̏ꍇ�A�����B
     */
    protected static void setFileName(HttpServletResponse response,
            String name, boolean forceDownload) {

        if (forceDownload) {
            // ������ɃL���b�V�����䂪�����IE�ł��܂����삵�Ȃ�
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