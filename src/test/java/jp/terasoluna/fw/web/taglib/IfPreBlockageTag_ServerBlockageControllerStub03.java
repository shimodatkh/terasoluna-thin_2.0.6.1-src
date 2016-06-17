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

package jp.terasoluna.fw.web.taglib;

import java.util.Date;

import jp.terasoluna.fw.web.thin.ServerBlockageController;


/**
 * IfPreBlockadeTagTest�Ŏg�p����B
 * 
 */
public class IfPreBlockageTag_ServerBlockageControllerStub03 implements
        ServerBlockageController {

    /**
     * �R���X�g���N�^�B
     */
    public IfPreBlockageTag_ServerBlockageControllerStub03() {
        super();
    }

    /**
     * �Ǐ�ԂɑJ�ڂ���B
     */
    public void blockade() {
    }

    /**
     * �Ǐ�Ԃ��ǂ����𔻕ʂ���B
     *
     * @return �Ǐ�ԂȂ� <code>true</code>
     */
    public boolean isBlockaded() {
        return false;
    }

    /**
     * �Ǐ�Ԃ��ǂ����𔻕ʂ���B�w�肳�ꂽ�p�X���
     * <code>ADMIN_PATH_PREFIX</code> �܂���
     * <code>ERROR_PATH_PREFIX</code> �Ŏn�܂�Ƃ��́A
     * �ǃ`�F�b�N���s�킸�A��ɔ�Ǐ�ԂƂ݂Ȃ��B
     *
     * @param pathInfo �p�X���
     * @return �Ǐ�ԂȂ� <code>true</code>
     */
    public boolean isBlockaded(String pathInfo) {
        return true;
    }

    /**
     * �\�Ǐ�Ԃ��ǂ����𔻕ʂ���B
     *
     * @return �\�Ǐ�ԂȂ� <code>true</code>
     */
    public boolean isPreBlockaded() {
        return false;
    }

    /**
     * �ʏ��ԂɑJ�ڂ���B
     */
    public void open() {
    }

    /**
     * �\�Ǐ�ԂɑJ�ڂ���B
     */
    public void preBlockade() {
    }

    /**
     * �\�Ǐ�ԂɑJ�ڂ�
     * �w�肳�ꂽ�����ɂȂ����Ƃ��ɕǏ�ԂɑJ�ڂ���B
     *
     * @param time �Ǐ�ԂɑJ�ڂ������
     */
    public void preBlockade(Date time) {
    }

}
