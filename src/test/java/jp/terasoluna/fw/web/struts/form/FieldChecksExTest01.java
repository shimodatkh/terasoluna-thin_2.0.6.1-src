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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.PropertyTestCase;

import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Msg;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.Var;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * {@link jp.terasoluna.fw.web.struts.form.FieldChecksEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Validator�ǉ����[���N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FieldChecksEx
 */
public class FieldChecksExTest01 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(FieldChecksExTest01.class);
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
        deleteProperty("validation.hankaku.kana.list");
        deleteProperty("validation.zenkaku.kana.list");
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
    public FieldChecksExTest01(String name) {
        super(name);
    }

    /**
     * testIsHankakuKana01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:����������������������¯�������������������֬�������
     *                  ܦ��ް�����<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵��������hankakuKanaList�Ɋ܂܂��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKana01() throws Exception {
        // ���͒l�̐ݒ�
        String hankakuKanaList =
            "����������������������¯�������������������֬�������ܦ��ް�����";

        // isHankakuKana���s
        for (int i = 0; i < hankakuKanaList.length(); i++) {
            assertTrue(FieldChecksEx.isHankakuKana(hankakuKanaList.charAt(i)));
        }
    }

    /**
     * testIsHankakuKana02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�'-1<br>
     *                '�'+1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵��������hankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�A
     * false���擾�ł��邱�Ƃ��m�F����B�i���p�J�i�̋��E�e�X�g�j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKana02() throws Exception {
        // ���͒l�̐ݒ�
        char chStart = '�' - 1;
        char chEnd = '�' + 1;

        // isHankakuKana���s
        assertFalse(FieldChecksEx.isHankakuKana(chStart));
        assertFalse(FieldChecksEx.isHankakuKana(chEnd));
    }

    /**
     * testIsHankakuKana03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�S'<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵��������hankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�A
     * false���擾�ł��邱�Ƃ��m�F����B�i�S�p�����j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKana03() throws Exception {
        // ���͒l�̐ݒ�
        char chZenkaku = '�S';

        // isHankakuKana���s
        assertFalse(FieldChecksEx.isHankakuKana(chZenkaku));
    }

    /**
     * testIsZenkakuKana01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\
     *                  �U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z
     *                  �o�r�u�x�{�p�s�v�y�|�}�~����������������������������
     *                  �����������b���[<br>
     *                ���ꕶ�����m�F<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R����
     *                                �K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g
     *                                �_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{
     *                                �p�s�v�y�|�}�~����������������������������
     *                                �����������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵��������zenkakuKanaList�Ɋ܂܂��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKana01() throws Exception {
        // ���͒l�̐ݒ�
        String zenkakuKanaList = "�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R" +
                "�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g" +
                "�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{" +
                "�p�s�v�y�|�}�~����������������������������" +
                "�����������b���[";

        // �e�X�g���s
        for (int i = 0; i < zenkakuKanaList.length(); i++) {
            assertTrue(FieldChecksEx.isZenkakuKana(zenkakuKanaList.charAt(i)));
        }
    }

    /**
     * testIsZenkakuKana02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�@' - 1<br>
     *                '�[' + 1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R����
     *                                �K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g
     *                                �_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{
     *                                �p�s�v�y�|�}�~����������������������������
     *                                �����������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵��������zenkakuKanaList�Ɋ܂܂�Ȃ��ꍇ�A
     * false���擾�ł��邱�Ƃ��m�F����B�i���p�J�i�̋��E�e�X�g�j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKana02() throws Exception {
        // ���͒l�̐ݒ�
        char chStart = '�@' - 1;
        char chEnd = '�[' + 1;

        // �e�X�g�̎��s
        assertFalse(FieldChecksEx.isZenkakuKana(chStart));
        assertFalse(FieldChecksEx.isZenkakuKana(chEnd));
    }

    /**
     * testIsZenkakuKana03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'��'<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R����
     *                                �K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g
     *                                �_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{
     *                                �p�s�v�y�|�}�~����������������������������
     *                                �����������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵��������zenkakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B�i�S�p�������j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKana03() throws Exception {
        // ���͒l�̐ݒ�
        char chHiragana = '��';

        // �e�X�g�̎��s
        assertFalse(FieldChecksEx.isZenkakuKana(chHiragana));
    }

    /**
     * testIsHankaku01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u00ff'<br>
     *                '�'<br>
     *                '�'<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȉ����A
     * "�_�������N�ʁ��}�L���~��"�ł͂Ȃ��AhankakuKanaList�Ɋ܂܂��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankaku01() throws Exception {

        // ���͒l�̐ݒ�
        char chHankakuMax = '\u00ff';
        char chHankakuKanaStart = '�';
        char chHankakuKanaEnd = '�';

        // isHankaku���s
        // ���p�������ݒ肳�ꂽ�Ƃ��Atrue���ԋp����邱��
        assertTrue(FieldChecksEx.isHankaku(chHankakuMax));
        assertTrue(FieldChecksEx.isHankaku(chHankakuKanaStart));
        assertTrue(FieldChecksEx.isHankaku(chHankakuKanaEnd));
    }

    /**
     * testIsHankaku02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u0100'<br>
     *                '�'-1<br>
     *                '�'+1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȏ�A�܂��́A
     * "�_�������N�ʁ��}�L���~��"�Ɋ܂܂��A�܂��́A
     * hankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankaku02() throws Exception {

        // ���͒l�̐ݒ�
        char chUpperff = '\u0100';

        char chKanaStart = '�' - 1;
        char chKanaEnd = '�' + 1;

        // isHankaku���s
        assertFalse(FieldChecksEx.isHankaku(chUpperff));
        assertFalse(FieldChecksEx.isHankaku(chKanaStart));
        assertFalse(FieldChecksEx.isHankaku(chKanaEnd));

    }

    /**
     * testIsHankaku03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�A'<br>
     *                '�U'<br>
     *                '��'<br>
     *                '��'<br>
     *                '��'<br>
     *                '�`'<br>
     *                '�y'<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵���������S�p�����ł���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankaku03() throws Exception {

        // ���͒l�̐ݒ�
        char[] input = {
            '�A',
            '�U',
            '��',
            '��',
            '��',
            '�`',
            '�y'
        };

        // isHankaku���s
        // �S�p�������ݒ肳�ꂽ�Ƃ��Afalse���ԋp����邱��
        for (char c : input) {
            assertFalse(FieldChecksEx.isHankaku(c));
        }
    }

    /**
     * testIsHankaku04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:"�_�������N�ʁ��}�L���~��"<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȏ�A�܂��́A
     * "�_�������N�ʁ��}�L���~��"�Ɋ܂܂��A�܂��́A
     * hankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankaku04() throws Exception {
        // ���͒l�̐ݒ�
        String zenkakuBeginU00List = "�_�������N�ʁ��}�L���~��";

        // �e�X�g���s
        for (int i = 0; i < zenkakuBeginU00List.length(); i++) {
            assertFalse(FieldChecksEx.isHankaku(zenkakuBeginU00List.charAt(i)));
        }
    }

    /**
     * testIsZenkaku01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u0100'<br>
     *                '�'-1<br>
     *                '�'+1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'���傫���A���A
     * "�_�������N�ʁ��}�L���~��"�Ɋ܂܂�邩�A
     * hankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkaku01() throws Exception {

        // ���͒l�̐ݒ�
        char chZenkakuMin = '\u0100';
        char chZenkakuKanaStart = '�' - 1;
        char chZenkakuKanaEnd = '�' + 1;

        // isZenkaku���s
        // �S�p�����񂪐ݒ肳�ꂽ�Ƃ��Atrue���ԋp����邱��
        assertTrue(FieldChecksEx.isZenkaku(chZenkakuMin));
        assertTrue(FieldChecksEx.isZenkaku(chZenkakuKanaStart));
        assertTrue(FieldChecksEx.isZenkaku(chZenkakuKanaEnd));
    }

    /**
     * testIsZenkaku02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u00ff'<br>
     *                '�'<br>
     *                '�'<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȉ����A
     * "�_�������N�ʁ��}�L���~��"�ł͂Ȃ��A
     * hankakuKanaList�Ɋ܂܂��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkaku02() throws Exception {

        // ���͒l�̐ݒ�
        char chZenkakuMin = '\u00ff';
        char chZenkakuKanaStart = '�';
        char chZenkakuKanaEnd = '�';

        // isZenkaku���s
        // ���p�������ݒ肳�ꂽ�Ƃ��Afalse���ԋp����邱��
        assertFalse(FieldChecksEx.isZenkaku(chZenkakuMin));
        assertFalse(FieldChecksEx.isZenkaku(chZenkakuKanaStart));
        assertFalse(FieldChecksEx.isZenkaku(chZenkakuKanaEnd));

    }

    /**
     * testIsZenkaku03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�'<br>
     *                '6'<br>
     *                '&'<br>
     *                'a'<br>
     *                'z'<br>
     *                'A'<br>
     *                'Z'<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵�����������p�����ł���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkaku03() throws Exception {

        // ���͒l�̐ݒ�
        char[] input = {
            '�',
            '6',
            '&',
            'a',
            'z',
            'A',
            'Z'
        };

        // isZenkaku���s
        // ���p�������ݒ肳�ꂽ�Ƃ��Afalse���ԋp����邱��
        for (char c : input) {
            assertFalse(FieldChecksEx.isZenkaku(c));
        }
    }

    /**
     * testIsZenkaku04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:"�_�������N�ʁ��}�L���~��"<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�����������������
     *                                ��֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * �����Ɏw�肵��������"�_�������N�ʁ��}�L���~��"�Ɋ܂܂��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkaku04() throws Exception {
        // ���͒l�̐ݒ�
        String zenkakuBeginU00List = "�_�������N�ʁ��}�L���~��";

        // �e�X�g���s
        for (int i = 0; i < zenkakuBeginU00List.length(); i++) {
            assertTrue(FieldChecksEx.isZenkaku(zenkakuBeginU00List.charAt(i)));
        }
    }

    /**
     * testIsValid01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) result:Boolean(true)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * �����̒l��Boolean��true�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsValid01() throws Exception {

        // ���͒l�̐ݒ�
        Boolean result = new Boolean(true);

        // �e�X�g���s
        Boolean ret = FieldChecksEx.isValid(result);
        assertTrue(ret.booleanValue());
    }

    /**
     * testIsValid02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) result:Boolean(false)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����̒l��Boolean��false�̏ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsValid02() throws Exception {

        // ���͒l�̐ݒ�
        Boolean result = new Boolean(false);

        // �e�X�g���s
        Boolean ret = FieldChecksEx.isValid(result);
        assertFalse(ret.booleanValue());
    }

    /**
     * testIsValid03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) result:"String"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * �����̒l��Boolean�^�ł͂Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsValid03() throws Exception {

        // ���͒l�̐ݒ�
        String result = "String";

        // �e�X�g���s
        Boolean ret = FieldChecksEx.isValid(result);
        assertTrue(ret.booleanValue());
    }

    /**
     * testReplaceIndexString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) arg:null<br>
     *         (����) pos:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *
     * <br>
     * ������arg��null�̏ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReplaceIndexString01() throws Exception {
        // �e�X�g���s
        String ret = FieldChecksEx.replaceIndexString(null, 0);
        assertNull(ret);
    }

    /**
     * testReplaceIndexString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) arg:"##INDEX"<br>
     *         (����) pos:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"0"<br>
     *
     * <br>
     * ������arg��"##INDEX"�̏ꍇ�A�������œn����pos�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReplaceIndexString02() throws Exception {
        // �e�X�g���s
        String ret = FieldChecksEx.replaceIndexString("##INDEX", 0);
        assertEquals("0", ret);
    }

    /**
     * testReplaceIndexString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) arg:"test"<br>
     *         (����) pos:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"test"<br>
     *
     * <br>
     * ������arg��"##INDEX"�ł͂Ȃ��ꍇ�Aarg�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReplaceIndexString03() throws Exception {

        // �e�X�g���s
        String ret = FieldChecksEx.replaceIndexString("test", 0);
        assertEquals("test", ret);
    }

    /**
     * testReplaceIndexString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) arg:"##index"<br>
     *         (����) pos:99<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"99"<br>
     *
     * <br>
     * ������arg��"##index"(���������܂܂��)�̏ꍇ�A�������œn����pos�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReplaceIndexString04() throws Exception {
        // �e�X�g���s
        String ret = FieldChecksEx.replaceIndexString("##index", 99);
        assertEquals("99", ret);
    }

    /**
     * testReplaceIndexString05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) arg:""<br>
     *         (����) pos:999<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *
     * <br>
     * ������arg��"##INDEX"�ł͂Ȃ��ꍇ�Aarg�̒l���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testReplaceIndexString05() throws Exception {
        // �e�X�g���s
        String ret = FieldChecksEx.replaceIndexString("", 999);
        assertEquals("", ret);
    }

    /**
     * testGetArrayIndexField01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) field:not null<br>
     *                ��Arg���������݂��Ȃ�<br>
     *         (����) pos:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Field:not null<br>
     *                  �i��ԕω��Ȃ��j<br>
     *
     * <br>
     * ������field��Arg�C���X�^���X���ݒ肳��Ă��Ȃ��ꍇ�A
     * field�ɕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetArrayIndexField01() throws Exception {

        // ���͒l�̐ݒ�
        Field field = new Field();

        // �e�X�g���s
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);
        assertNull(retField.getArg(0));
        assertNull(retField.getArg(1));
        assertNull(retField.getArg(2));
        assertNull(retField.getArg(3));
    }

    /**
     * testGetArrayIndexField02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) field:not null<br>
     *                {Arg:key="arg0", position=0;<br>
     *                 Arg:key="arg1", position=1;<br>
     *                 Arg:key="arg2", position=2;<br>
     *                 Arg:key="arg3", position=3}<br>
     *         (����) pos:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Field:not null<br>
     *                  {Arg:key="arg0", position=0;<br>
     *                   Arg:key="arg1", position=1;<br>
     *                   Arg:key="arg2", position=2;<br>
     *                   Arg:key="arg3", position=3}<br>
     *                  (��ԕω��Ȃ�)<br>
     *
     * <br>
     * ������field�ɐݒ肳�ꂽArg�C���X�^���X��key�̒l��"##INDEX"�ł͂Ȃ��ꍇ�A
     * field�ɕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetArrayIndexField02() throws Exception {

        // ���͒l�̐ݒ�
        Field field = new Field();
        // arg�ݒ�
        Arg param0 = new Arg();
        param0.setKey("arg0");
        param0.setPosition(0);
        field.addArg(param0);

        Arg param1 = new Arg();
        param1.setKey("arg1");
        param1.setPosition(1);
        field.addArg(param1);

        Arg param2 = new Arg();
        param2.setPosition(2);
        param2.setKey("arg2");
        field.addArg(param2);

        Arg param3 = new Arg();
        param3.setPosition(3);
        param3.setKey("arg3");
        field.addArg(param3);

        // �e�X�g���s
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);

        assertEquals("arg0", retField.getArg(0).getKey());
        assertEquals("arg1", retField.getArg(1).getKey());
        assertEquals("arg2", retField.getArg(2).getKey());
        assertEquals("arg3", retField.getArg(3).getKey());
    }

    /**
     * testGetArrayIndexField03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) field:not null<br>
     *                {Arg:key="##INDEX", position=0;<br>
     *                 Arg:key="##INDEX", position=1;<br>
     *                 Arg:key="##INDEX", position=2;<br>
     *                 Arg:key="##INDEX", position=3}<br>
     *         (����) pos:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Field:not null<br>
     *                  {Arg:key="1", position=0;<br>
     *                   Arg:key="1", position=1;<br>
     *                   Arg:key="1", position=2;<br>
     *                   Arg:key="1", position=3}<br>
     *
     * <br>
     * ������field�ɐݒ肳�ꂽArg�C���X�^���X��key�̒l��"##INDEX"�ł���ꍇ�A
     * key�̒l����������pos�̒l�{�P�ɂȂ邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetArrayIndexField03() throws Exception {

        // ���͒l�̐ݒ�
        Field field = new Field();
        // arg�ݒ�
        Arg param0 = new Arg();
        param0.setKey("##INDEX");
        param0.setPosition(0);
        field.addArg(param0);

        Arg param1 = new Arg();
        param1.setKey("##INDEX");
        param1.setPosition(1);
        field.addArg(param1);

        Arg param2 = new Arg();
        param2.setKey("##INDEX");
        param2.setPosition(2);
        field.addArg(param2);

        Arg param3 = new Arg();
        param3.setKey("##INDEX");
        param3.setPosition(3);
        field.addArg(param3);

        // �e�X�g���s
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);

        assertEquals("1", retField.getArg(0).getKey());
        assertEquals("1", retField.getArg(1).getKey());
        assertEquals("1", retField.getArg(2).getKey());
        assertEquals("1", retField.getArg(3).getKey());
    }

    /**
     * testGetArrayIndexField04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) field:not null<br>
     *                {Arg:key="##INDEX", position=0;<br>
     *                 Arg:key="arg1", position=1;<br>
     *                 Arg:key="arg2", position=2;<br>
     *                 Arg:key="##INDEX", position=3}<br>
     *         (����) pos:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Field:not null<br>
     *                  {Arg:key="1", position=0;<br>
     *                   Arg:key="arg1", position=1;<br>
     *                   Arg:key="arg2", position=2;<br>
     *                   Arg:key="1", position=3}<br>
     *
     * <br>
     * ������field�ɐݒ肳�ꂽArg�C���X�^���X��key�̒l��"##INDEX"�ł���ꍇ�A
     * key�̒l����������pos�̒l�{�P�ɂȂ邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetArrayIndexField04() throws Exception {

        // ���͒l�̐ݒ�
        Field field = new Field();
        // arg�ݒ�
        Arg param0 = new Arg();
        param0.setKey("##INDEX");
        param0.setPosition(0);
        field.addArg(param0);

        Arg param1 = new Arg();
        param1.setKey("arg1");
        param1.setPosition(1);
        field.addArg(param1);

        Arg param2 = new Arg();
        param2.setKey("arg2");
        param2.setPosition(2);
        field.addArg(param2);

        Arg param3 = new Arg();
        param3.setKey("##INDEX");
        param3.setPosition(3);
        field.addArg(param3);

        // �e�X�g���s
        Field retField = FieldChecksEx.getArrayIndexField(field, 0);

        assertEquals("1", retField.getArg(0).getKey());
        assertEquals("arg1", retField.getArg(1).getKey());
        assertEquals("arg2", retField.getArg(2).getKey());
        assertEquals("1", retField.getArg(3).getKey());
    }

    /**
     * testValidateAlphaNumericString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:null<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                Msg("message","message")<br>
     *                field:var<br>
     *                name:"mask"<br>
     *                value="^[a-z]*$"<br>
     *                jsType="false"<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name:"mask"<br>
     *                    value="^[a-z]*$"<br>
     *                    jsType="false"<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean��null�̂Ƃ��Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateAlphaNumericString01() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = null;
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setResource(false);
        field.addMsg(msg);
        field.addVar("mask", "^[a-z]*$", "false");
        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[��񂪋�ł��邱�ƁB
        assertTrue(errors.isEmpty());
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNotNull(var);
        assertEquals("mask", var.getName());
        assertEquals("^[a-z]*$", var.getValue());
        assertEquals("false", var.getJsType());
    }

    /**
     * testValidateAlphaNumericString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:""<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean���󕶎��̂Ƃ��Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateAlphaNumericString02() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setResource(false);
        field.addMsg(msg);
        // �G���[���
        ActionMessages errors = new ActionMessages();
        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[��񂪋�ł��邱�ƁB
        assertTrue(errors.isEmpty());
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateAlphaNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:"a0A"<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                (��̗v�f)<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:��ԕω��Ȃ�<br>
     *
     * <br>
     * ������bean�����p�p�������݂̂ō\������Ă���ꍇ�Atrue���ԋp����A
     * errors�Ƀ��b�Z�[�W���ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateAlphaNumericString03() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        String bean = "a0A";
        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);

        // �G���[���
        ActionMessages errors = new ActionMessages();

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);
        // �e�X�g���ʊm�F
        // true���ԋp����Ă��邱�ƁB
        assertTrue(result);
        // �G���[���ɃG���[�I�u�W�F�N�g���o�^����Ă��Ȃ����ƁB
        assertTrue(errors.isEmpty());
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testValidateAlphaNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) bean:HashMap<br>
     *                ["field1"="Zg3%"]<br>
     *         (����) va:not null<br>
     *         (����) field:not null<br>
     *                property="field1"<br>
     *                Msg("message","message")<br>
     *         (����) errors:not null<br>
     *                ActionMessage("testMessage")<br>
     *         (����) validator:not null<br>
     *         (����) request:not null<br>
     *         (���) validateMask:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         (��ԕω�) field:var�F<br>
     *                    name="mask"<br>
     *                    ��null�ł��邱�ƁB<br>
     *         (��ԕω�) errors:ActionMessage("testMessage")<br>
     *                    ActionMessage("message")<br>
     *
     * <br>
     * ������bean��String�^�ł͂Ȃ��ꍇ�A
     * field����擾�����v���p�e�B�̒l�ɑ΂��ă`�F�b�N���s���邱�Ƃ��m�F����B
     * ���̓`�F�b�N�G���[�ƂȂ����Ƃ��A
     * errors�Ƀ��b�Z�[�W���ǉ�����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValidateAlphaNumericString04() throws Exception {
        //�e�X�g�f�[�^�ݒ�
        // ++++ bean�I�u�W�F�N�g ++++
        Map<String, String> bean = new HashMap<String, String>();
        bean.put("field1", "Zg3%");

        // ++++ ���ؐݒ�I�u�W�F�N�g
        ValidatorAction va = new ValidatorAction();
        va.setName("message");
        // ++++ ���؃t�B�[���h���
        Field field = new Field();
        field.setProperty("field1");
        // ���b�Z�[�W�ݒ�
        Msg msg = new Msg();
        msg.setKey("message");
        msg.setName("message");
        msg.setResource(false);
        field.addMsg(msg);

        // �G���[���iActionMessage��1���ݒ�j
        ActionMessages errors = new ActionMessages();
        ActionMessage error = new ActionMessage("testMessage");
        errors.add(Globals.ERROR_KEY, error);

        // �[��HTTP���N�G�X�g
        MockHttpServletRequest request = new MockHttpServletRequest();
        Validator validator = new Validator(new ValidatorResources());

        // �e�X�g���s
        boolean result =
            FieldChecksEx.validateAlphaNumericString(
                bean,
                va,
                field,
                errors,
                validator,
                request);

        // �e�X�g���ʊm�F
        // false���ԋp����Ă��邱�ƁB
        assertFalse(result);
        // �G���[���ɃG���[�I�u�W�F�N�g��2�o�^����Ă��邱�ƁB
        assertEquals(2, errors.size());
        // �G���[���ɐݒ肳�ꂽ���b�Z�[�W���o�^����Ă��邱�ƁB
        // ������ActionMessage���㏑������Ȃ����ƁB
        Iterator it = errors.get();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            ActionMessage retError = (ActionMessage) it.next();
            list.add(retError.getKey());
        }
        assertTrue(list.contains("testMessage"));
        assertTrue(list.contains("message"));
        // field�̏�ԕω��m�F
        Var var = field.getVar("mask");
        assertNull(var);
    }

    /**
     * testGetByteLength01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *
     * <br>
     * ������value��null�̂Ƃ��A0���擾�ł��邱�Ƃ��m�F����B<br>
     * ���̑��̃p�^�[����validateByteLength�AvalidateByteRange�ŕ�܁B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetByteLength01() throws Exception {
        int result = FieldChecksEx.getByteLength(null, "test");
        assertEquals(0, result);
    }

    /**
     * testGetByteLength02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) int:0<br>
     *
     * <br>
     * ������value���󕶎��̂Ƃ��A0���擾�ł��邱�Ƃ��m�F����B<br>
     * ���̑��̃p�^�[����validateByteLength�AvalidateByteRange�ŕ�܁B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetByteLength02() throws Exception {
        int result = FieldChecksEx.getByteLength("", "test");
        assertEquals(0, result);
    }

}
