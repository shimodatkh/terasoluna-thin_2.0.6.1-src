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

/**
 * ���b�Z�[�W���N���X�B
 * <p>
 *  �r�W�l�X���W�b�N�̎��s���ʂ��󂯂āA���b�Z�[�W��ݒ肷��ۂɐ�������B
 *  �R���X�g���N�^�̑������Ƀ��b�Z�[�W�̃��\�[�X�o���h���L�[�A
 *  �������i�ϒ������j�ɒu����������w�肷�邱�ƂŁA
 *  ���b�Z�[�W���\�[�X���烁�b�Z�[�W���擾�ł���B
 *  ���b�Z�[�W���\�[�X���烁�b�Z�[�W���擾�����ɁA
 *  �����������̂܂܃��b�Z�[�W������Ƃ��Ďg�p���邱�Ƃ��ł��A
 *  ���̏ꍇ�́A�������ɂ��̃��b�Z�[�W������A
 *  ��������false���w�肷��B
 * </p>
 * <p>
 *  �g�p��ɂ��ẮAAbstractBLogicAction���Q�Ƃ̂��ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 *
 */
public class BLogicMessage implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 2885287921533717080L;

    // ----------------------------------------------------------- Constructors
    /**
     * �R���X�g���N�^�B
     *
     * @param key ���b�Z�[�W�̂j�d�x
     */
    public BLogicMessage(String key) {
        this(key, (Object[]) null);
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param key ���b�Z�[�W�̂j�d�x
     * @param values �u��������
     */
    public BLogicMessage(String key, Object... values) {
        this.key = key;
        this.values = values;
        this.resource = true;
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param key ���b�Z�[�W�̂j�d�x
     * @param resource �L�[���o���h���L�[������Ƃ������ʂ�̒l�ł��邩
     */
    public BLogicMessage(String key, boolean resource) {
 
        this.key = key;
        this.resource = resource;

    }

    // ----------------------------------------------------- Instance Variables

    /**
     * ���b�Z�[�W�̂j�d�x�B
     */
    protected String key = null;


    /**
     * �u����������i�[�����z��B
     */
    protected Object[] values = null;

    /**
     * �L�[���o���h���L�[�itrue�j������Ƃ������ʂ�̒l�ifalse�j�ł��邩�B
     */
    protected boolean resource = true;

    // --------------------------------------------------------- Public Methods

    /**
     * ���b�Z�[�W�̂j�d�x���擾����B
     *
     * @return ���b�Z�[�W�̂j�d�x
     */
    public String getKey() {

        return (this.key);

    }

    /**
     * �u����������i�[�����z����擾����B
     *
     * @return ���b�Z�[�W���i�[�����z��
     */
    public Object[] getValues() {

        return (this.values);

    }

    /**
     * �L�[���o���h���L�[������Ƃ������ʂ�̒l�ł��邩�𔻒肷��B
     *
     * @return �L�[���o���h���L�[�Ȃ�true
     */
    public boolean isResource() {

        return (this.resource);

    }
}
