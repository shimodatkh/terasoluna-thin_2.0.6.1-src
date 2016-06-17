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

package jp.terasoluna.fw.web;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;

/**
 * {@link jp.terasoluna.fw.web.UserValueObject} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���O�I�����[�U��񒊏ۃN���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.UserValueObject
 * 
 */
@SuppressWarnings("unused")
public class UserValueObjectTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(UserValueObjectTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public UserValueObjectTest(String name) {
        super(name);
    }

    /**
     * testCreateUserValueObject01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) �v���p�e�B�t�@�C��:user.value.object=<br>
     *                jp.terasoluna.fw.web.UserValueObjectImpl01<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) UserValueObject:UserValueObjectImpl01�C���X�^���X<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C����user.value.object���L�[�Ƃ��āAUserValueObject�����N���X�����L�q����Ă���ꍇ�A���̃N���X�̃C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUserValueObject01() throws Exception {
        // �O����
        addProperty("user.value.object",
                "jp.terasoluna.fw.web.UserValueObjectImpl01");

        // �e�X�g���{
        UserValueObject userObject = UserValueObject.createUserValueObject();

        // ����
        assertEquals(UserValueObjectImpl01.class.getName(), userObject
                .getClass().getName());
    }

    /**
     * testCreateUserValueObject02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) �v���p�e�B�t�@�C��:user.value.object=java.lang.String<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.uvo.class"<br>
     *                    �u��������P�F<br>
     *                    "java.lang.String"<br>
     *                    ���b�v������O�FClassCastException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    illegal uvo class:java.lang.String<br>
     *                    ��O�FClassCastException<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C����user.value.object���L�[�Ƃ��āAUserValueObject���������Ă��Ȃ��N���X�����L�q����Ă���ꍇ�AClassCastException�����b�v����SystemException���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUserValueObject02() throws Exception {
        // �O����
        addProperty("user.value.object", "java.lang.String");
        try {
            // �e�X�g���{
            UserValueObject userObject = 
                UserValueObject.createUserValueObject();
            fail();

        } catch (SystemException sysEx) {
            // ����
            // SystemException �̒��g���`�F�b�N
            // ���b�Z�[�W�L�[�`�F�b�N
            assertEquals("errors.uvo.class", sysEx.getErrorCode());
            // ���b�v������O�`�F�b�N
            assertEquals(ClassCastException.class.getName(), 
                    sysEx.getCause().getClass().getName());
            
            // �u��������`�F�b�N
            assertEquals("java.lang.String", sysEx.getOptions()[0]);
            // ���O�`�F�b�N
            assertTrue(LogUTUtil.checkError(
                    "illegal uvo class:java.lang.String", sysEx.getCause()));
        }
    }

    /**
     * testCreateUserValueObject03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) �v���p�e�B�t�@�C��:user.value.object=uvo<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.uvo.class"<br>
     *                    �u��������P�F<br>
     *                    "uvo"<br>
     *                    ���b�v������O�FClassLoadException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F<br>
     *                    illegal uvo class:uvo<br>
     *                    ��O�FClassLoadException<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C����user.value.object���L�[�Ƃ��āA�N���X�p�X��ɑ��݂��Ȃ��N���X�����L�q����Ă���ꍇ�AClassLoadException�����b�v����SystemException���X���[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUserValueObject03() throws Exception {
        // �O����
        addProperty("user.value.object", "uvo");
        try {
            // �e�X�g���{
            UserValueObject userObject = 
                UserValueObject.createUserValueObject();
            fail();

        } catch (SystemException sysEx) {
            // ����
            // SystemException �̒��g���`�F�b�N
            // ���b�Z�[�W�L�[�`�F�b�N
            assertEquals("errors.uvo.class", sysEx.getErrorCode());
            // ���b�v������O�`�F�b�N
            assertEquals(ClassLoadException.class.getName(),
                    sysEx.getCause().getClass().getName());
            // �u��������`�F�b�N
            assertEquals("uvo", sysEx.getOptions()[0]);
            // ���O�`�F�b�N
            assertTrue(LogUTUtil.checkError("illegal uvo class:uvo", 
                    sysEx.getCause()));

        }
    }

    /**
     * testCreateUserValueObject04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) �v���p�e�B�t�@�C��:user.value.object�̐ݒ�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) UserValueObject:null<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�Fspecify user.value.object.<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C����user.value.object�̃L�[�����݂��Ȃ��ꍇ�A�G���[���O���o�͂��Anull��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateUserValueObject04() throws Exception {
        // �O����
        // user.value.object�̐ݒ�Ȃ�

        // �e�X�g���{
        UserValueObject userObject = UserValueObject.createUserValueObject();

        // �e�X�g����
        assertNull(userObject);
        
        // ���O�`�F�b�N
        assertTrue(LogUTUtil.checkError("specify user.value.object."));        

    }
}
