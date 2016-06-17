/*
 * Copyright (c) 2011 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts;

import java.text.MessageFormat;
import java.util.HashMap;

/**
 * Struts�̃o�O(STR-2172)���pHashMap(MessageFormat�L���b�V��)�B
 * <p>
 * Struts��MessageResources�́A�L���b�V������get����MessageFormat�𓯊��������ɕ����X���b�h�ŃA�N�Z�X�\�ɂ��Ă���B<br>
 * ���̃o�O��������邽�߁A�L���b�V�����瓯��C���X�^���X��Ԃ����Aclone�C���X�^���X��Ԃ��悤�g�����Ă���B
 * </p>
 * <p>
 * �܂��AMessageFormat�Ŏg�p����T�u�t�H�[�}�b�g�̒��ɂ́A
 * (��̓I�ɂ́A{0,number}���̋L�q���Ɏg�p�����DecimalFormat���Y���B)
 * clone���Ȃ���΃X���b�h�Z�[�t�ł��邪�A
 * format���s���clone����ƁAMessageFormat�C���X�^���X(�T�u�t�H�[�}�b�g�̃C���X�^���X�܂�)��
 * �X���b�h���Ƃɑ��݂���ɂ��ւ�炸�A�X���b�h�A���Z�[�t�ƂȂ���̂�����B<br>
 * ���̃N���X�́Aformat���s�O��put���\�b�h��MessageFormat�C���X�^���X���n�����O��ŁA
 * put���\�b�h���ɂ�clone���s�����ƂŁA���̖���������Ă���B<br>
 * </p>
 * <p>
 * ���̃N���X�́AStruts��MessageResources�̃o�O����p�N���X�ł���B<br>
 * put���\�b�h(null�l�̐ݒ�Ȃ�)��get���\�b�h�̂ݎg�p����邱�Ƃ�O��Ƃ��Ă���B<br>
 * ���̑��̃��\�b�h���g�p�����ꍇ�́AMap�Ƃ��Ă̓���͕ۏ؂��Ȃ��B
 * �܂��Aput�̖߂�l�ł́A�O���put�̈����œn���ꂽ�C���X�^���X�ł͂Ȃ��A������clone�C���X�^���X��Ԃ��B
 * </p>
 * @see MessageFormatCacheMapFactory
 */
public class MessageFormatCloneReturnMap extends HashMap<String, MessageFormat> {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 8965893764397816159L;

    /**
     * MessageFormat�C���X�^���X��clone�C���X�^���X���L���b�V������B
     * @param key MessageFormat�L���b�V�����̃L�[
     * @param value �L���b�V������MessageFormat�C���X�^���X
     * @return ���\�b�h���s�O�ɃL���b�V������Ă���MessageFormat�C���X�^���X
     */
    @Override
    public MessageFormat put(String key, MessageFormat value) {
        // format���s�ς݂̂��̂�clone����ƁA
        // �X���b�h�A���Z�[�t�ɂȂ�t�H�[�}�b�g�����݂��邽�߁A
        // (��̓I�ɂ́A{0,number}���̋L�q���Ɏg�p�����DecimalFormat���Y���B)
        // get���̖���clone�Ƃ͕ʂɁAput���ɂ��A
        // format�����s��Ԃ�MessageFormat��clone���ăL���b�V������B
        // (�����œn���Ă���MessageFormat�́A���̂���Struts��MessageResources�ɂ����format�����s�����̂ŁA
        //  �����œn���Ă���MessageFormat���̂̓L���b�V�����Ȃ�)
        return super.put(key, (MessageFormat) value.clone());
    }

    /**
     * �L���b�V�����ꂽMessageFormat��clone�C���X�^���X��Ԃ��B
     * @param key MessageFormat�L���b�V�����̃L�[
     * @return MessageFormat�C���X�^���X
     */
    @Override
    public MessageFormat get(Object key) {
        MessageFormat messageFormat = super.get(key);
        if (messageFormat != null) {
            messageFormat = (MessageFormat) messageFormat.clone();
        }
        return messageFormat;
    }
}
