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
import java.util.Iterator;

/**
 *  ���b�Z�[�W���ꗗ�N���X�B
 * <p>
 *  BLogicMessage�C���X�^���X���i�[����N���X�B
 * </p>
 * <p>
 *  �g�p��ɂ��ẮAAbstractBLogicAction���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.BLogicMessage
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 *
 */
public class BLogicMessages implements Serializable {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -7410826431026041043L;

    // ----------------------------------------------------- Instance Variables
    /**
     * BLogicMessage���X�g�B
     *
     * <p>���b�Z�[�W�O���[�v���𖳎����āA�P����add()����������
     * BLogicMessage��ێ�����B</p>
     */
    protected ArrayList<BLogicMessage> list = new ArrayList<BLogicMessage>();

    /**
     * ���b�Z�[�W�O���[�v���̃��X�g�B
     *
     * <p>�P����add()���������Ń��b�Z�[�W�O���[�v����ێ�����B</p>
     */
    protected ArrayList<String> groupList = new ArrayList<String>();

    // --------------------------------------------------------- Public Methods

    /**
     * �R���X�g���N�^�B
     */
    public BLogicMessages() {

        super();
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param messages ���b�Z�[�W���ꗗ
     */
    public BLogicMessages(BLogicMessages messages) {
        super();
        this.add(messages);
    }

    /**
     * ���b�Z�[�W��ǉ�����B
     *
     * @param group ���b�Z�[�W�O���[�v��
     * @param message �ǉ����郁�b�Z�[�W
     */
    public void add(String group, BLogicMessage message) {

        this.list.add(message);
        this.groupList.add(group);
    }

    /**
     * ���b�Z�[�W��ǉ�����B
     *
     * @param messages �ǉ�����BLogicMessages�C���X�^���X
     */
    public void add(BLogicMessages messages) {

        if (messages == null) {
            return;
        }

        // loop over properties
        Iterator<BLogicMessage> itr = messages.get();
        Iterator<String> groupItr = messages.getGroup();
        while (itr.hasNext()) {
            BLogicMessage message = itr.next();
            String group = groupItr.next();

            this.add(group, message);
        }
    }

    /**
     * �ێ����Ă��郁�b�Z�[�W���N���A����B
     */
    public void clear() {

        this.list.clear();
        this.groupList.clear();
    }

    /**
     * ���b�Z�[�W���i�[����Ă��Ȃ��ꍇ�Atrue��Ԃ��B
     *
     * @return ���b�Z�[�W���i�[����Ă��Ȃ��ꍇ�Atrue
     */
    public boolean isEmpty() {

        return (list.isEmpty());
    }

    /**
     * �ێ����Ă��郁�b�Z�[�W�ꗗ�ɃA�N�Z�X����C�e���[�^���擾����B
     * ���b�Z�[�W�O���[�v���͖������āAadd()���������Ŏ擾����邱�Ƃ��ۏ؂����B
     *
     * @return ���b�Z�[�W�ꗗ�̃C�e���[�^
     */
    @SuppressWarnings("unchecked")
    public Iterator<BLogicMessage> get() {
        return ((ArrayList) this.list.clone()).iterator();
    }

    /**
     * �ێ����Ă��郁�b�Z�[�W�O���[�v���ꗗ�ɃA�N�Z�X����C�e���[�^���擾����B
     * add()���������Ŏ擾����邱�Ƃ��ۏ؂����B
     *
     * @return ���b�Z�[�W�O���[�v���ꗗ�̃C�e���[�^
     */
    @SuppressWarnings("unchecked")
    public Iterator<String> getGroup() {
        return ((ArrayList) this.groupList.clone()).iterator();
    }

}
