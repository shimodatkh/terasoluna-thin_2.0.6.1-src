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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * �r�W�l�X���W�b�N���o�͏���ێ�����N���X�B
 *
 * <p>
 *  BLogicIOPlugIn�Ńr�W�l�X���W�b�N���o�͏���`�t�@�C���ł���
 *  blogic-io.xml����A�A�N�V�����p�X����
 *  ���̃A�N�V�������N�����ꂽ���̓��͏��A�o�͏���ێ�����B
 *  �ݒ�ɂ��ẮABLogicIOPlugIn���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicIO implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 5498810299684603811L;

    /**
     * �A�N�V�����p�X���B
     */
    private String path = null;

    /**
     * �r�W�l�X���W�b�N���͏��B
     */
    private List<BLogicProperty> blogicParams = new ArrayList<BLogicProperty>();

    /**
     * �r�W�l�X���W�b�N�o�͏��B
     */
    private List<BLogicProperty> blogicResults  =
        new ArrayList<BLogicProperty>();

    /**
     * �r�W�l�X���W�b�N���͏��ƂȂ�JavaBean���B
     */
    private String inputBeanName = null;

    /**
     * �r�W�l�X���W�b�N���͏��ƂȂ�JavaBean�����擾����B
     *
     * @return �r�W�l�X���W�b�N���͏��JavaBean��
     */
    public String getInputBeanName() {
        return inputBeanName;
    }

    /**
     * �r�W�l�X���W�b�N���͏��ƂȂ�JavaBean����ݒ肷��B
     *
     * @param inputBeanName �r�W�l�X���W�b�N���͏��JavaBean��
     */
    public void setInputBeanName(String inputBeanName) {
        this.inputBeanName = inputBeanName;
    }

    /**
     * �r�W�l�X���W�b�N���͏����擾����B
     *
     * @return �r�W�l�X���W�b�N���͏��
     */
    public List<BLogicProperty> getBLogicParams() {
        return blogicParams;
    }

    /**
     * �r�W�l�X���W�b�N���͏���ݒ肷��B
     *
     * @param blogicParam �r�W�l�X���W�b�N���͏��
     */
    public void setBLogicParam(BLogicProperty blogicParam) {
        this.blogicParams.add(blogicParam);
    }

    /**
     * �A�N�V�����p�X����ݒ肷��B
     *
     * @param path �A�N�V�����p�X��
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * �A�N�V�����p�X�����擾����B
     *
     * @return �A�N�V�����p�X��
     */
    public String getPath() {
        return path;
    }

    /**
     * �r�W�l�X���W�b�N�o�͏���ݒ肷��B
     *
     * @param blogicResult �r�W�l�X���W�b�N�o�͏��
     */
    public void setBLogicResult(BLogicProperty blogicResult) {
        this.blogicResults.add(blogicResult);
    }

    /**
     * �r�W�l�X���W�b�N�o�͏����擾����B
     *
     * @return �r�W�l�X���W�b�N�o�͏��
     */
    public List<BLogicProperty> getBLogicResults() {
        return blogicResults;
    }
}
