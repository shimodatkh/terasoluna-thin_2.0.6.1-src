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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �L���b�V������Ă���R�[�h���X�g���ēǂݍ��݂���A�N�V�����B
 *
 * <p>
 * �ēǂݍ��݂��s�� ReloadableCodeListLoader ��
 * setCodeListLoader���\�b�h�Őݒ肷�邱�Ƃɂ���āA
 * ���̃A�N�V���������s�����Ƃ���ReloadableCodeListLoader ������
 * reload() ���\�b�h���Ăяo���A�R�[�h���X�g�̍ēǂݍ��݂��s���B
 * �R�[�h���X�g�ēǂݍ��݂����s��AStruts�ݒ�t�@�C��(struts-config.xml) ��
 * action �v���p�e�B�� parameter �����Ɏw�肵����Ƀt�H���[�h����B
 * �t�H���[�h�悪�ݒ肳��Ă��Ȃ��ꍇ�A
 * SC_NOT_FOUND�i404�j �G���[��Ԃ��B
 * </p>
 *
 * <strong>Bean��`�t�@�C���̐ݒ��B</strong><br>
 * �ȉ��� ReloadableCodeListLoader �Ƃ��� &quot;loader1&quot; ��
 * ��`���Ă���ꍇ�̗�ł���B
 * <code><pre>
 * &lt;bean name=<Strong>"/reloadAction"</Strong> scope="prototype"
 *       class = "jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction"&gt;
 *   <Strong>&lt;property name="codeListLoader"&gt;
 *     &lt;ref bean="loader1"/&gt;
 *   &lt;/property&gt;</Strong>
 * &lt;/bean&gt;
 * </pre></code>
 * <strong> Struts�ݒ�t�@�C��(struts-config.xml) �ݒ��</strong><br>
 * <code><pre>
 *  &lt;action path=<Strong>"/reloadAction"</Strong>
 *          name="_sampleFormBean"
 *          parameter = "/reloaded.do"/&gt;
 * </pre></code></p>
 * <p>
 *
 * ReloadableCodeListLoader ���̂��̂��Đ�������@�\�ł͂Ȃ����߁A
 * ���̒��g�� SQL ���Ȃǂ�ύX���邱�Ƃ͕s�\�ł���B<br>
 *
 * �ēǂݍ��݉\�ȃR�[�h���X�g�̐����ɂ��ẮA
 * ReloadableCodeListLoader ���Q�ƁB<br>
 * �܂�JSP���̃R�[�h���X�g�̎g�p���@�ɂ��ẮADefineCodeListTag�A
 * DefineCodeListCountTag ���Q�ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader
 * @see jp.terasoluna.fw.web.codelist.DBCodeListLoader
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 *
 */
public class ReloadCodeListAction extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(ReloadCodeListAction.class);

    /**
     * �G���[�y�[�W�i404�j�J�ڎ��s�������G���[�R�[�h�B
     */
    private static final String FORWARD_ERRORPAGE_ERROR =
        "error.forward.errorpage";

    /**
     * �ēǂݍ��݂����{���邽�߂Ɏg�p���� ReloadableCodeListLoader�B
     */
    private ReloadableCodeListLoader codeListLoader = null;

    /**
     * �L���b�V������Ă���R�[�h���X�g���ēǍ��݂���B
     *
     * <p>
     *  ���̏������s��A��� parameter �����Ɏw�肵����Ƀt�H���[�h����B
     *  parameter �������w�肳��Ă��Ȃ�������AcodeListLoader�����݂��Ȃ��ꍇ
     * SC_NOT_FOUND�i404�j �G���[��Ԃ��B
     * </p>
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @return parameter �����Ɏw�肵���J�ڐ���
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res) {

        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        if (codeListLoader != null) {
            codeListLoader.reload();
        }

        String path = mapping.getParameter();

        if (path == null) {
            // �p�����[�^�������ݒ肳��Ă��Ȃ��ꍇ�A�i404�j�G���[��ԋp����B
            try {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                log.error("Error page(404) forwarding failed.");
                throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
            }
            return null;
        }

        return new ActionForward(path);
    }

    /**
     * codeListLoader ���擾����B
     *
     * @return codeListLoader ��\���t�B�[���h�l�B
     */
    public ReloadableCodeListLoader getCodeListLoader() {
        return codeListLoader;
    }

    /**
     * codeListLoader ��ݒ肷��B
     *
     * @param codeListLoader codeListLoader ��\���t�B�[���h�l�B
     */
    public void setCodeListLoader(ReloadableCodeListLoader codeListLoader) {
        this.codeListLoader = codeListLoader;
    }
}
