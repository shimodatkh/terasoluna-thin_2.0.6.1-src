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

import java.io.File;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import jp.terasoluna.fw.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>HTTP�Z�b�V�����̃��C�t�T�C�N���C�x���g����������N���X�B</p>
 *
 * <p>HTTP�Z�b�V�����̖��������ɁA�Z�b�V�����ɕR�t�����Ă����f�B���N�g����
 * ���݂��Ă���΍폜����B</p>
 *
 * <p><code>web.xml</code> �ɂ����āA�ȉ��̂悤�ɐݒ肷��B</p>
 * <h5> <code>web.xml</code> �ݒ��</h5>
 * <pre><code>
 * &lt;listener&gt;
 *   &lt;listener-class&gt;
 *     jp.terasoluna.fw.web.HttpSessionListenerImpl
 *   &lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre></code>
 * �Z�b�V�����f�B���N�g�������E�폜�����ɂ��Ă�FileUtil���Q�ƁB
 *
 * @see jp.terasoluna.fw.util.FileUtil
 *
 */
public class HttpSessionListenerImpl implements HttpSessionListener {

    /**
     * ���O�N���X�B
     */
    private static Log log
          = LogFactory.getLog(HttpSessionListenerImpl.class);

    /**
     * <p>HTTP�Z�b�V�����̍쐬�C�x���g����������B</p>
     *
     * @param event �Z�b�V�����C�x���g
     */
    public void sessionCreated(HttpSessionEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("session created.");
        }
    }

    /**
     * <p>HTTP�Z�b�V�����̖������C�x���g����������B</p>
     * <p>
     *  �Z�b�V�����ɕR�t�����Ă����f�B���N�g����
     *  ���݂��Ă���΁A�폜����B
     * </p>
     *
     * @param event �Z�b�V�����C�x���g
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("session destroyed.");
        }

        HttpSession session = event.getSession();
        File dir = FileUtil.getSessionDirectory(session.getId());
        if (dir.exists() && dir.isDirectory()) {
            if (FileUtil.removeSessionDirectory(session.getId())) {
                log.debug("\"" + dir.getPath() + "\" removed.");
            } else {
                log.error("can't remove \"" + dir.getPath() + "\".");
            }
        }
    }

}
