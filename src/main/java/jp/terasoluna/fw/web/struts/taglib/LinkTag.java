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

package jp.terasoluna.fw.web.struts.taglib;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import jp.terasoluna.fw.web.RequestUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.logic.IterateTag;

/**
 * <p>�g�� <code>link</code> �^�O�B</p>
 *
 * <p>
 *  <code>Struts</code> �̒񋟂��� <code>&lt;html:link&gt;</code> �^�O���g������B
 *  �@�\�Ƃ��āA�A�N�V���� <code>URL</code> �ɃL���b�V�������p�����_��
 *  <code>ID</code> ��ǉ�����B
 *  �l�X�g����Ă��Ȃ�<code></code>iterateTag</code>��
 *  <code>BODY</code>����<code>LinkTag</code>���g�p����Ƃ��̂�
 *  <code>indexed</code>�����A<code>indexId</code>�������L���ƂȂ�B
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p><code>&lt;html:link&gt;</code>�^�O��<code>API</code> ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p> <code>&lt;html:link&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <p> <code>&lt;html:link&gt;</code> �^�O�� <code>API</code> ���Q�ƁB</p>
 *
 *
 *
 */
public class LinkTag extends org.apache.struts.taglib.html.LinkTag {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -2143604832271336809L;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(LinkTag.class);
    
    /**
     * �L���b�V�������p�����_��ID�̃p�����[�^���B
     */
    private static final String RANDOM_ID_KEY = RandomUtil.RANDOM_ID_KEY;

    /**
     * <p>URL�ɃL���b�V�������p�����_��ID��ǉ�����B</p>
     *
     * <p>�z�b�g�X�|�b�g���Ȃ����߁A<code>Struts 1.2.4</code> ��
     * <code>LinkTag.calculate()</code> �̃R�[�h���R�s�[���ĕύX���Ă���B
     * <code>Struts</code> �̃o�[�W������ύX�����ꍇ��
     * ���������K�v�ƂȂ�̂Œ��ӂ��邱�ƁB</p>
     *
     * @return ��������w��
     * @exception JspException �G���[�����������Ƃ�
     */
    @SuppressWarnings("unchecked")
    @Override
    protected String calculateURL() throws JspException {

        // Identify the parameters we will add to the completed URL
        Map<String, String> params = TagUtils.getInstance().computeParameters(
                pageContext, paramId, paramName, paramProperty, paramScope,
                name, property, scope, transaction);

        // ========== �y�g���ӏ��z����������==========
        if (params == null) {
            params = new HashMap<String, String>();
        }

        //���łɃ����_��ID�����݂���ꍇ�͍폜����B
        forward = RequestUtil.deleteUrlParam(forward, RANDOM_ID_KEY);
        href = RequestUtil.deleteUrlParam(href, RANDOM_ID_KEY);
        page = RequestUtil.deleteUrlParam(page, RANDOM_ID_KEY);
        action = RequestUtil.deleteUrlParam(action, RANDOM_ID_KEY);

        //�p�����[�^�}�b�v�Ƀ����_��ID��ǉ��B
        params.put(RANDOM_ID_KEY, RandomUtil.generateRandomID());

        // if "indexed=true", add "index=x" parameter to query string
        // * @since Struts 1.1
        if (indexed) {

           // look for outer iterate tag
           IterateTag iterateTag =
               (IterateTag) findAncestorWithClass(this, IterateTag.class);
           if (iterateTag == null) {
               // iterateTag��body���Ŏg�p����Ă��Ȃ��ꍇ�͗�O�𓊂���
               JspException e = new JspException
                   (messages.getMessage("indexed.noEnclosingIterate"));
               TagUtils.getInstance().saveException(pageContext, e);
               
               log.error("iterateTag is null.");
               throw e;
           }

           if (indexId != null) {
               params.put(indexId, Integer.toString(iterateTag.getIndex()));
           } else {
               params.put("index", Integer.toString(iterateTag.getIndex()));
           }
        }
        // ========== �y�g���ӏ��z�������܂�==========

        String url = null;
        try {
            url = TagUtils.getInstance().computeURLWithCharEncoding(
                    pageContext, forward, href, page, action, module, params,
                    anchor, false, useLocalEncoding);
        } catch (MalformedURLException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            
            log.error("Malformed URL has occurred.");
            throw new JspException
                (messages.getMessage("rewrite.url", e.toString()));
        }
        return (url);

    }
}
