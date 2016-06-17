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

/**
 * DB����擾�����s�i���b�Z�[�W���\�[�X�j���ꎞ�I�ɃI�u�W�F�N�g�̌`����
 * �ۊǂ��邽�߂ɂ���N���X�B
 * 
 * ���p�̂�����Ɋւ��Ă͈ȉ��̃N���X���Q�Ƃ̂��ƁB
 * 
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 * 
 */
public class DBMessageBean {
     
    /**
     * ���b�Z�[�W�L�[�B
     */
    private String key = null;
    
    /**
     * ���b�Z�[�W�����B
     */
    private String value = null;
    
    /**
     * �R���X�g���N�^�B���ɂȂɂ������͂Ȃ��B
     *
     */
    public DBMessageBean() {
        // ���ɉ������Ȃ�
    } 
    
    /**
     * ���b�Z�[�W�L�[���擾����B
     * 
     * @return ���b�Z�[�W�L�[�B
     */
    public String getKey() {
        return key;
    }
    
    /**
     * ���b�Z�[�W�������擾����B
     * 
     * @return ���b�Z�[�W�����B
     */
    public String getValue() {
        return value;
    }
    
    /**
     * ���b�Z�[�W�L�[��ݒ肷��B
     * 
     * @param key ���b�Z�[�W�L�[�B
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * ���b�Z�[�W������ݒ肷��B
     * 
     * @param value ���b�Z�[�W�����B
     */
    public void setValue(String value) {
        this.value = value;
    }
}
