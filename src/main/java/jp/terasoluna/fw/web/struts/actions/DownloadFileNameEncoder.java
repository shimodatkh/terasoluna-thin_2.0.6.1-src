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
 * �_�E�����[�h���̎w��t�@�C�������G���R�[�h����N���X����������C���^�t�F�[�X�B
 * <p>
 * ���̃N���X���������āA�ȉ��̂悤�ɐݒ肷�邱�ƂŁA�G���R�[�h���@��ύX�ł���B
 * <h5>Bean��`�t�@�C��</h5>
 * <pre><code>
 * &lt;bean class=&quot;jp.terasoluna.fw.web.struts.actions.FileDownloadUtil&quot;&gt;
 *   &lt;property name=&quot;encoder&quot; ref=&quot;encoder&quot;/&gt;
 * &lt;/bean&gt;
 * &lt;bean name=&quot;encoder&quot; class=&quot;sample.MyEncoder&quot;/&gt;
 * </code></pre>
 */
public interface DownloadFileNameEncoder {

    /**
     * �_�E�����[�h���̎w��t�@�C�������G���R�[�h����B
     *
     * @param original ���̕�����B
     * @return �G���R�[�h�ς݂̕�����B
     */
    String encode(String original, HttpServletRequest request, HttpServletResponse response);
}
