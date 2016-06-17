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

package jp.terasoluna.fw.web.codelist;

import java.util.Locale;

/**
 * �R�[�h���X�g�@�\�̃C���^�[�t�F�[�X�B
 * <br>
 * �R�[�h���X�g�̓ǂݍ��݂��s���B
 * �܂��A�ǂݍ��񂾃R�[�h���X�g��ԋp����B
 *
 */
public interface CodeListLoader {

    /**
     * �R�[�h���X�g�̏��������s���B
     */
    void load();

    /**
     * �f�t�H���g���P�[���ŃR�[�h���X�g��ԋp����B
     * <br>
     * �R�[�h���X�g�� {@link CodeBean} �̔z��Ƃ��ĕԋp����B<br>
     * �R�[�h���X�g�͌����Ƃ��ăA�v���P�[�V�������ň�ӂƂȂ���ł���B
     * �R�[�h���X�g�̓��e���ҏW����Ă��e�����Ȃ��悤�Ɏ�������K�v������B
     *
     * @return �R�[�h���X�g
     */
    CodeBean[] getCodeBeans();
  
    
    /**
     * ���P�[�����w�肵�ăR�[�h���X�g��ԋp����B
     * <br>
     * �R�[�h���X�g�� {@link CodeBean} �̔z��Ƃ��ĕԋp����B<br>
     * �R�[�h���X�g�͌����Ƃ��ăA�v���P�[�V�������ň�ӂƂȂ���ł���B
     * �R�[�h���X�g�̓��e���ҏW����Ă��e�����Ȃ��悤�Ɏ�������K�v������B
     *
     * @param locale ���P�[��
     * @return �R�[�h���X�g
     */
    CodeBean[] getCodeBeans(Locale locale);

}
