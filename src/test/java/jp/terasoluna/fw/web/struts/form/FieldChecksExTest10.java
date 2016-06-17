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

package jp.terasoluna.fw.web.struts.form;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * static�߂̃e�X�g�B</br>
 * validation.hankaku.kana.list</br>
 * validation.zenkaku.kana.list</br>
 * ���v���p�e�B���琳��ɓǂݍ��ނ��e�X�g����B</br>
 * ���̃N���X�͑��i01�`09�j��FieldChecksEx�̃e�X�g�Ƃ͕ʂ�VM
 * �Ŏ��s����K�v������B
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest10 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest10.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        LogUTUtil.flush();
        addProperty("validation.hankaku.kana.list", "hankaku.kana");
        addProperty("validation.zenkaku.kana.list", "zenkaku.kana");
    }

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public FieldChecksExTest10(String name) {
        super(name);
    }

    /**
     * testGetHankakuKanaList01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                =hankaku.kana<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:hankaku.kana<br>
     *
     * <br>
     * �N���X�ϐ�hankakuKanaList�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHankakuKanaList01() throws Exception {
        assertEquals("hankaku.kana",
                FieldChecksEx.getHankakuKanaList());
    }

    /**
     * testGetZenkakuKanaList01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                =zenkaku.kana<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:zenkaku.kana<br>
     *
     * <br>
     * �N���X�ϐ�zenkakuKanaList�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetZenkakuKanaList01() throws Exception {
        assertEquals("zenkaku.kana",
                FieldChecksEx.getZenkakuKanaList());
    }

}
