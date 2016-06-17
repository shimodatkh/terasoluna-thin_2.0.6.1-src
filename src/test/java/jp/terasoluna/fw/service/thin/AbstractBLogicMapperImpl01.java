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

package jp.terasoluna.fw.service.thin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AbstratBLogicMapperTest�N���X�ŗ��p����B
 *
 */
public class AbstractBLogicMapperImpl01 extends AbstractBLogicMapper {

    /**
     * �upropName + "_FromRequest"�v��ԋp����B
     */
    @Override
    public Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return propName + "_FromRequest";
    }

    /**
     * �upropName + "_FromSession"�v��ԋp����B
     */
    @Override
    public Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return propName + "_FromSession";
    }

    /**
     * �upropName + "_FromForm"�v��ԋp����B
     */
    public Object getValueFromForm(String propName, HttpServletRequest request,
            HttpServletResponse response) {
        return propName + "_FromForm";
    }

    /**
     * request�ɁupropName + "_ToRequest"�v�Ƃ����L�[��value��ݒ肷��B
     */
    @Override
    public void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(propName + "_ToRequest", value);

    }

    /**
     * request�ɁupropName + "_ToSession"�v�Ƃ����L�[��value��ݒ肷��B
     */
    @Override
    public void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(propName + "_ToSession", value);

    }

    /**
     * request�ɁupropName + "_ToForm"�v�Ƃ����L�[��value��ݒ肷��B
     */
    public void setValueToForm(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(propName + "_ToForm", value);
    }

    /**
     * Exception���X���[����B
     */
    public Object getValueFromError(String propName,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        throw new Exception();
    }

    /**
     * Exception���X���[����B
     */
    public void setValueToError(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        throw new Exception();
    }

    /**
     * �upropName + "_FromApplication"�v��ԋp����B
     */
    @Override
    public Object getValueFromApplication(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return propName + "_FromApplication";
    }
}
