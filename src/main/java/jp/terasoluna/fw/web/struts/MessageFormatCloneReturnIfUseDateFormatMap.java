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

import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * Struts�̃o�O(STR-2172)���pHashMap(MessageFormat�L���b�V��)�B
 * <p>
 * Struts��MessageResources�́A�L���b�V������get����MessageFormat�𓯊��������ɕ����X���b�h�ŃA�N�Z�X�\�ɂ��Ă���B<br>
 * ���̃o�O��������邽�߁A�L���b�V�����瓯��C���X�^���X��Ԃ����Aclone�C���X�^���X��Ԃ��悤�g�����Ă���B<br>
 * �������A�S�Ă�MessageFormat�𖳏�����clone����̂ł͂Ȃ��A
 * ���炩�ɃX���b�h�A���Z�[�t��肪��������ADateFormat���T�u�t�H�[�}�b�g�Ɏ���MessageFormat�̂݁A
 * get����clone����B
 * </p>
 * <p>
 * ���̃N���X�́AStruts��MessageResources�̃o�O����p�N���X�ł���B<br>
 * put���\�b�h(null�l�̐ݒ�Ȃ�)��get���\�b�h�̂ݎg�p����邱�Ƃ�O��Ƃ��Ă���B<br>
 * ���̑��̃��\�b�h���g�p�����ꍇ�́AMap�Ƃ��Ă̓���͕ۏ؂��Ȃ��B<br>
 * �܂��Aput�̖߂�l�ł́A�O���put�̈����œn���ꂽ�C���X�^���X�ł͂Ȃ��A������clone�C���X�^���X��Ԃ��ꍇ������B
 * (get����clone�C���X�^���X��Ԃ������𖞂������̂�����ɊY������B)
 * </p>
 * @see MessageFormatCacheMapFactory
 */
public class MessageFormatCloneReturnIfUseDateFormatMap
                                                       extends
                                                       HashMap<String, MessageFormat> {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 5766672928587118585L;

    /**
     * get����MessageFormat��clone�C���X�^���X��Ԃ�Map
     */
    private MessageFormatCloneReturnMap cloneReturnMap = new MessageFormatCloneReturnMap();

    /**
     * MessageFormat�C���X�^���X���L���b�V������B
     * @param key MessageFormat�L���b�V�����̃L�[
     * @param value �L���b�V������MessageFormat�C���X�^���X
     * @return ���\�b�h���s�O�ɃL���b�V������Ă���MessageFormat�C���X�^���X
     */
    @Override
    public MessageFormat put(String key, MessageFormat value) {
        boolean needClone = false;
        for (Format format : value.getFormats()) {
            if (format instanceof DateFormat) {
                needClone = true;
                break;
            }
        }
        if (needClone) {
            return cloneReturnMap.put(key, value);
        }
        return super.put(key, value);
    }

    /**
     * �L���b�V�����ꂽMessageFormat�C���X�^���X���A�K�v�ɉ�����clone���ĕԂ��B
     * @param key MessageFormat�L���b�V�����̃L�[
     * @return MessageFormat�C���X�^���X
     */
    @Override
    public MessageFormat get(Object key) {
        MessageFormat messageFormat = super.get(key);
        if (messageFormat == null) {
            messageFormat = cloneReturnMap.get(key);
        }
        return messageFormat;
    }
}
