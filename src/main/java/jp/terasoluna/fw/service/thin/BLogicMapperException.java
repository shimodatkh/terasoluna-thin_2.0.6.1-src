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

/**
 *  {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper}
 *  �܂��́A���̌p���N���X�ɂāA�}�b�s���O�����Ɏ��s�����ꍇ�ɔ��������O�B
 * <p>
 *  {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper}
 *  �ł́A���̗�O��
 *  {@link jp.terasoluna.fw.exception.SystemException}
 *  �����b�v���ăX���[����B
 *  {@link jp.terasoluna.fw.service.thin.BLogic}
 *  �̃}�b�s���O�����Ɋւ��ẮA
 *  {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper}�A
 *  {@link jp.terasoluna.fw.service.thin.BLogicMapper}�A
 *  {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn}
 *  ���Q�Ƃ̂��ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogic
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 */
public class BLogicMapperException extends Exception {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -2391490636688083481L;

    /**
     * �R���X�g���N�^�B
     */
    public BLogicMapperException() {
        super();
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param cause �����ƂȂ�����O�B
     */
    public BLogicMapperException(Throwable cause) {
        super(cause);
    }

}
