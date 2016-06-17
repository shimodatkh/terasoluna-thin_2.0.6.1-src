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

import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicResult;

/**
 * AbstractBLogicActionTest�N���X�ŗ��p����B
 *
 */
public class AbstractBLogicAction_AbstractBLogicMapperStub02 extends
        AbstractBLogicMapper {

    // mapBLogicResult�Ăяo���t���O
    // public boolean isMapBLogicResult = false;
    // ��AbstractBLogicMapper�ɃL���X�g����邽�߁A�g���Ȃ�
    // ��request�Ɋi�[

    @Override
    public Object mapBLogicParams(HttpServletRequest request,
            HttpServletResponse response, BLogicIO io) {
        return new String("abc");
    }

    @Override
    public void mapBLogicResult(HttpServletRequest request,
            HttpServletResponse response, BLogicIO io, BLogicResult result) {
        // isMapBLogicResult = true;
        request.setAttribute("isMapBLogicResult", "true");
    }

    @Override
    public Object getValueFromRequest(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public Object getValueFromSession(String propName,
            HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void setValueToRequest(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public void setValueToSession(Object value, String propName,
            HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    public Object getValueFromApplication(String arg0,
            HttpServletRequest arg1, HttpServletResponse arg2) {
        return null;
    }
}
