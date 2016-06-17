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

package jp.terasoluna.fw.web.thin;

import javax.servlet.ServletException;

/**
 * �T�[�o�Ǘ�O�N���X�B
 * 
 * <p>
 * �T�[�o�ǂ��Ă��邱�Ƃ��t���[�����[�N�����m�������ɁA 
 * �����ʒm���邽�߂ɗp������B
 * {@link ServerBlockageControlFilter}�����s���A
 * �R���e�i�ɂ��G���[�y�[�W���\�������B
 * </p>
 * 
 * <h5>�ݒ��</h5>
 * �f�v���C�����g�f�B�X�N���v�^�iweb.xml�j�ŁA�ȉ��̂悤�ɋL�q����K�v������B
 * <code><pre>
 * &lt;web-app&gt;
 *   �c
 *   &lt;error-page&gt;
 *     &lt;exception-type&gt;
 *       jp.terasoluna.exception.ServerBlockageException
 *     &lt;/exception-type&gt;
 *     &lt;location&gt;/blockageexception.html&lt;/location&gt;
 *   &lt;/error-page&gt;
 *   �c
 * &lt;/web-app&gt;
 * </pre></code>
 * 
 * @see jp.terasoluna.fw.web.thin.ServerBlockageControlFilter
 * @see jp.terasoluna.fw.web.thin.ServerBlockageController
 * 
 */
public class ServerBlockageException extends ServletException {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -3311976557292798334L;
}
