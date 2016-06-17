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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.struts.action.DBMessageResources;
import jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx;
import jp.terasoluna.fw.web.struts.util.SpringMessageResources;

/**
 * Struts�̃o�O(STR-2172)���pHashMap(MessageFormat�L���b�V��)�̃t�@�N�g���N���X�B
 * <p>
 * Struts��MessageResources�́A�L���b�V������get����MessageFormat�𓯊��������ɕ����X���b�h�ŃA�N�Z�X�\�ɂ��Ă���B<br>
 * ���̃o�O��������邽�߂̊g��HashMap�𐶐�����B
 * </p>
 * <p>
 * �Q�l)<br>
 * ���Ȃ��Ƃ�Sun JDK�̎������g�p���Ă������́A���t�����n�̃T�u�t�H�[�}�b�g({0,date}��)���g�p���Ȃ�����A
 * MessageFormat�̎d�l�ɂ͔�������̂́A����C���X�^���X��format���\�b�h�𕡐��X���b�h�œ����Ɏ��s���Ă��A
 * �S�����Ȃ��B(���ꂪ�AStruts��MessageResources�̃o�OSTR-2172��Won't Fix�ƂȂ�A�C������Ȃ��������R�B)<br>
 * ���̃N���X�ł̓o�O�̉����@�𒲐��\�Ƃ��Ă���Bsystem.properties�Ɉȉ��̐ݒ���s���B
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>system.properties�ݒ��</legend>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>����MessageFormat�𕡐��X���b�h�Ŏg�p���Ȃ��悤�A���clone����(MessageFormat�̎d�l�ɏ���)</legend>
 * <code>
 * messageResources.messageFormatClone = enable
 * </code>
 * </fieldset>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>���t�����n�̃T�u�t�H�[�}�b�g���g�p���ꂽ�Ƃ��̂݁A����MessageFormat�𕡐��X���b�h�Ŏg�p���Ȃ��悤�Aclone���� (�f�t�H���g)<br>
 * (Sun��MessageFormat����т�������g�p�����Format�N���X�̎����ɍ��킹�œK��)</legend>
 * <code>
 * messageResources.messageFormatClone = dateFormatOnly
 * </code>
 * </fieldset>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>�o�O������s�킸�AStruts�̎����̂܂ܓ��삷��</legend>
 * <code>
 * messageResources.messageFormatClone = disable
 * </code>
 * </fieldset>
 * �ݒ莩�̂��ȗ������ꍇ��A��L�ȊO�̐ݒ�l���ݒ肳�ꂽ�ꍇ�� <strong>dateFormatOnly</strong> �Ƃ��Ĉ�����B<br>
 * </p>
 * @see MessageFormatCloneReturnMap
 * @see MessageFormatCloneReturnIfUseDateFormatMap
 * @see PropertyMessageResourcesEx
 * @see DBMessageResources
 * @see SpringMessageResources
 */
public class MessageFormatCacheMapFactory {

    /**
     * ���O�C���X�^���X
     */
    private static Log log = LogFactory
            .getLog(MessageFormatCacheMapFactory.class);

    /**
     * messageFormatClone�ݒ�l�̃v���p�e�B�L�[
     */
    private static final String MESSAGE_FORMAT_CLONE_KEY = "messageResources.messageFormatClone";

    /**
     * messageFormatClone�ݒ�l:enable
     */
    private static final String ENABLE = "enable";

    /**
     * messageFormatClone�ݒ�l:disable
     */
    private static final String DISABLE = "disable";

    /**
     * messageFormatClone�ݒ�l:dateFormatOnly
     */
    private static final String DATE_FORMAT_ONLY = "dateFormatOnly";

    /**
     * Struts�̃o�O(STR-2172)���pHashMap�C���X�^���X��Ԃ��B
     * <p>
     * �ݒ�ɂ��A�o�O���������������Ă���ꍇ�́Anull��Ԃ��B
     * </p>
     * @return Struts�̃o�O(STR-2172)���pHashMap�C���X�^���X
     */
    public static HashMap<String, MessageFormat> getInstance() {
        String cloneSetting = PropertyUtil
                .getProperty(MESSAGE_FORMAT_CLONE_KEY);
        if (log.isDebugEnabled()) {
            log.debug(MESSAGE_FORMAT_CLONE_KEY + " = " + cloneSetting);
        }
        if (cloneSetting == null) {
            log.debug("use MessageFormatCloneReturnAtDateFormatOnlyMap.");
            return new MessageFormatCloneReturnIfUseDateFormatMap();
        }

        cloneSetting = cloneSetting.toLowerCase();
        if (ENABLE.equals(cloneSetting)) {
            log.debug("use MessageFormatCloneReturnMap.");
            return new MessageFormatCloneReturnMap();
        } else if (DATE_FORMAT_ONLY.toLowerCase().equals(cloneSetting)) {
            log.debug("use MessageFormatCloneReturnAtDateFormatOnlyMap.");
            return new MessageFormatCloneReturnIfUseDateFormatMap();
        } else if (DISABLE.equals(cloneSetting)) {
            return null;
        } else {
            log.warn(MESSAGE_FORMAT_CLONE_KEY + " = " + cloneSetting
                    + " is invalid. set \"" + ENABLE + "\", \"" + DISABLE
                    + "\" or \"" + DATE_FORMAT_ONLY + "\".");
            log.warn("use MessageFormatCloneReturnAtDateFormatOnlyMap.");
            return new MessageFormatCloneReturnIfUseDateFormatMap();
        }
    }
}
