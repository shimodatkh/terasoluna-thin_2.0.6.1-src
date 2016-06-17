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

package jp.terasoluna.fw.web.struts.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RequestProcessorExTest�N���X�ŗ��p����B
 * 
 * RequestProcessor#process()���ōŏ��ɌĂ΂��
 * processPath()���I�[�o���C�h���邱�Ƃɂ��Aprocess()�̏����𑁊���
 * �I��������B
 * 
 * @version 2004/03/18
 */
public class RequestProcessorExStub01 extends RequestProcessorEx {

    /**
     * HTTP���N�G�X�g�B
     */
    private HttpServletRequest req = null;

    /**
     * �I�[�o���C�h�pprocesspath���\�b�h�B
     * 
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return ���null��ԋp
     * @exception IOException IO��O
     */
    @Override
    protected String processPath(HttpServletRequest request,
                                  HttpServletResponse response)
                                     throws IOException {
        req = request;
        // null��ԋp���邱�Ƃɂ��AprocessPath�ŏI������B
        return null;
    }
    
    /**
     * ���N�G�X�g�I�u�W�F�N�g�m�F�p���\�b�h
     * 
     * @return HTTP���N�G�X�g
     */
    protected HttpServletRequest getRequestEx() {
        return req;
    }
}
