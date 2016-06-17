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

package jp.terasoluna.fw.web;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.util.HashUtil;
import jp.terasoluna.fw.util.StringUtil;

/**
 * Request�Ɋւ��郆�[�e�B���e�B�N���X�B
 *
 */
public class RequestUtil {

    /**
     * <code>ServletRequest</code> �N���X�C���X�^���X���A
     * �R���e�L�X�g�p�X�ȍ~��URI<code>pathInfo</code> ���擾���܂��B
     * �Ȃ��A�����ł���pathInfo�Ƃ͒ʏ�̊g���p�X���Ƃ͈قȂ邱�Ƃɒ��ӂ��邱�ƁB
     *
     * @param request
     * <code>pathInfo</code> �̎Z�o���ƂȂ� <code>ServletRequest</code> �N���X�C���X�^���X�B
     * @return
     * �R���e�L�X�g���܂܂Ȃ��A�X���b�V������n�܂�p�X�B������request��null���n���ꂽ�ꍇ��
     * null�B�R���e�L�X�g�ȍ~�̎w�肪���������ꍇ�󕶎���B
     */
    public static String getPathInfo(ServletRequest request) {
        if (request == null) {
            return null;
        }
        // pathInfo�̎擾
        return ((HttpServletRequest) request).getRequestURI().replaceFirst(
            ((HttpServletRequest) request).getContextPath(), "");
    }

    /**
     * �T�[�u���b�g�R���e�L�X�g���擾���܂�
     * @param request ���N�G�X�g���
     * @return �T�[�u���b�g�R���e�L�X�g�B������request��null���n���ꂽ�ꍇ��
     * null�B
     */
    public static ServletContext getServletContext(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        //ServletContext���擾
        return request.getSession(true).getServletContext();
    }

    /**
     * �ʋƖ��Ɉڂ������ǂ����p�X�`�F�b�N���s���A���肷��B
     *
     * @param request ���N�G�X�g
     * @return �ʋƖ��Ɉڂ��Ă����<code>true</code>�B
     * ������request��null���n���ꂽ�ꍇ��true�B
     */
    public static boolean isChanged(ServletRequest request) {

        if (request == null) {
            return true;
        }

        //pathInfo�̎擾
        String pathInfo = getPathInfo(request);

        //pathInfo�̎擾
        String prevPathInfo = (String) request.getAttribute("PREV_PATH_INFO");
        //�p�X��null�̏ꍇ
        if (prevPathInfo == null
            || pathInfo == null
            || !toCompareStr(prevPathInfo).equals(toCompareStr(pathInfo))) {
            return true;
        }

        return false;
    }

    /**
     * �w�肳�ꂽ�p�X��񂩂�Ɩ��R���e�L�X�g�p�X���擾����B
     *
     * @param str �p�X���
     * @return �Ɩ��R���e�L�X�g�p�X
     */
    private static String toCompareStr(String str) {
        try {
            int beginIndex = str.indexOf('/') + 1;
            int endIndex = str.indexOf('/', beginIndex);
            return str.substring(beginIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            return str;
        }
    }

    /**
     * HTTP�Z�b�V����ID�̃n�b�V���l���擾����B
     *
     * @param req HTTP���N�G�X�g
     * @return �n�b�V���l�B������request��null���n���ꂽ�ꍇ��
     * null�B
     */
    public static String getSessionHash(HttpServletRequest req) {
        if (req == null) {
            return null;
        }
        byte[] hash = HashUtil.hashSHA1(req.getSession(true).getId());
        return StringUtil.toHexString(hash, "");
    }

    /**
     * ���N�G�X�g�p�����[�^�ƃ��N�G�X�g�������_���v����B
     *
     * @param req HTTP���N�G�X�g
     * @return �_���v����������
     */
    public static String dumpRequest(HttpServletRequest req) {
        return dumpRequestParameters(req) + " , " + dumpRequestAttributes(req);
    }

    /**
     * ���N�G�X�g�������_���v����B
     *
     * @param req HTTP���N�G�X�g
     * @return �_���v����������B������request��null���n���ꂽ�ꍇ��
     * null�B
     */
    public static String dumpRequestAttributes(HttpServletRequest req) {
        if (req == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" RequestAttributes {");
        Enumeration enumeration = req.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            Object value = req.getAttribute(key);
            builder.append(key);
            builder.append(" = ");
            builder.append(value);
            if (enumeration.hasMoreElements()) {
                builder.append(" , ");
            }
        }
        builder.append("}");
        return builder.toString();
    }


    /**
     * ���N�G�X�g�p�����[�^���_���v����B
     *
     * @param req HTTP���N�G�X�g
     * @return �_���v����������B������request��null���n���ꂽ�ꍇ��
     * null�B
     */
    public static String dumpRequestParameters(HttpServletRequest req) {
        if (req == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" RequestParameters {");
        Enumeration enumeration = req.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String[] values = req.getParameterValues(key);
            for (int i = 0; i < values.length; i++) {
                    builder.append(key);
                    builder.append("[");
                    builder.append(i);
                    builder.append("] = ");
                    builder.append(values[i]);
                    if (i < values.length - 1) {
                        builder.append(" , ");
                    }
            }
            if (enumeration.hasMoreElements()) {
                builder.append(" , ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * ����url�Ɉ���key�̃p�����[�^���t������Ă����ꍇ�͍폜���ĕԋp����B
     * ��F�폜�O�Ftest.do?no=2&r=8331352040140757427&no=1
     * �@�@�폜��Ftest.do?no=2&no=1
     * 
     * @param url �폜���s��URL
     * @param key �폜�Ώۂ̃L�[�ƂȂ镶����
     * @return �폜��URL
     */
    public static String deleteUrlParam(String url, String key) {

        //url,key��NULL�܂��͋󔒂̏ꍇ��url�����̂܂ܕԋp����B
        if (url == null || "".equals(url) || key == null || "".equals(key)) {
            return url;
        }

        //�H�Ŏn�܂�C���f�b�N�X���擾�B
        int start = url.indexOf("?");

        //�ԋp�pStringBuilder�B
        StringBuilder returnUrl = new StringBuilder(url);

        //�p�����[�^�����݂���ꍇ�́A�������s���B
        if (start >= 0) {

            //�p�����[�^�ȍ~�̕�������擾
            String tmp = url.substring(start + 1);

            //�p�����[�^�ȊO�̕������ԋp�pStringBuilder�Ɋi�[�B
            returnUrl = new  StringBuilder(url.substring(0, start));

            //�p�����[�^��z��ɕϊ�
            String[] params = tmp.split("&");

            //����p�����[�^�𔻒肵�āA
            //�w�肳�ꂽ�L�[�̃p�����[�^�ȊO��ԋp�pStringBuilder�Ɋi�[����B
            for (int i = 0; i < params.length; i++) {
                String param = params[i];

                //�����_��ID�̊m�F
                if (!param.startsWith(key + "=")) {

                    //�ŏ��ɕt������ꍇ�́H��t������B
                    //�����p�����[�^�̏ꍇ��&��t������B
                    if (returnUrl.indexOf("?") < 0
                            && !returnUrl.toString().endsWith("?")) {
                        returnUrl.append("?");
                    } else if (!returnUrl.toString().endsWith("&")) {
                        returnUrl.append("&");
                    }

                    //�ԋp�pStringBuilder�Ɋi�[�B
                    returnUrl.append(param);
                }
            }
        }

        //�폜���URL��ԋp����B
        return returnUrl.toString();
    }

}
