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

package jp.terasoluna.fw.web.struts;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;

/**
 * {@link jp.terasoluna.fw.web.struts.ModuleUtil} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ModuleConfig �Ɋւ��郆�[�e�B���e�B�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.ModuleUtil
 */
public class ModuleUtilTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ModuleUtilTest.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ModuleUtilTest(String name) {
        super(name);
    }

    /**
     * testGetModuleConfig01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ModuleConfig:null<br>
     *
     * <br>
     * ������request��null�̏ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetModuleConfig01() throws Exception {
        // �e�X�g���{
        Object result = ModuleUtil.getModuleConfig(null);

        assertNull(result);
    }

    /**
     * testGetModuleConfig02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) request.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *         (���) ServletContext.getAttribute(<br>
     *                    Globals.MODULE_KEY):not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ModuleConfig:�T�[�u���b�g�R���e�L�X�g��Globals.MODULE_KEY�œo�^����Ă���<br>
     *                  �f�t�H���g���W���[�����ԋp�����B<br>
     *
     * <br>
     * ���N�G�X�g��ModuleConfig�����݂����A�T�[�u���b�g�R���e�L�X�g�ɑ��݂���ꍇ�A�T�[�u���b�g�R���e�L�X�g����ModuleConfig���擾���ĕԋp���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetModuleConfig02() throws Exception {
        //�e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // ���W���[���R���t�B�O
        ModuleConfig apConfig = new ModuleConfigImpl("");
        // �[���Z�b�V����
        MockHttpSession session = new MockHttpSession();
        // �[���T�[�u���b�g�R���e�L�X�g
        MockServletContext context = new MockServletContext();

        // ���W���[���R���t�B�O���T�[�u���b�g�R���e�L�X�g�݂̂ɓo�^����Ă���B
        req.setAttribute(Globals.MODULE_KEY, null);
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //�e�X�g���s
        ModuleConfig retConfig = ModuleUtil.getModuleConfig(req);

        //�e�X�g���ʊm�F

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郂�W���[���R���t�B�O��
        // �ԋp����邱��
        assertSame(apConfig, retConfig);
    }

    /**
     * testGetModuleConfig03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) request.getAttribute(<br>
     *                    Globals.MODULE_KEY):not null<br>
     *         (���) ServletContext.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ModuleConfig:HTTP���N�G�X�g��Globals.MODULE_KEY�œo�^����Ă���f�t�H���g���W���[����<br>
     *                  �ԋp�����B<br>
     *
     * <br>
     * ���N�G�X�g��ModuleConfig�����݂���ꍇ�A���N�G�X�g����ModuleConfig���擾���ĕԋp���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetModuleConfig03() throws Exception {
        //�e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // ���W���[���R���t�B�O(���N�G�X�g�ɓo�^)
        ModuleConfig reqConfig = new ModuleConfigImpl("/sub1");
        req.setAttribute(Globals.MODULE_KEY, reqConfig);

        // �[���Z�b�V����
        MockHttpSession session = new MockHttpSession();
        // �[���T�[�u���b�g�R���e�L�X�g
        MockServletContext context = new MockServletContext();
        // ���W���[���R���t�B�O(�T�[�u���b�g�R���e�L�X�g�ɓo�^)
        ModuleConfig apConfig = new ModuleConfigImpl("");

        // �T�[�u���b�g�R���e�L�X�g�ɂ����W���[���R���t�B�O��o�^����Ă���B
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //�e�X�g���s
        ModuleConfig retConfig = ModuleUtil.getModuleConfig(req);

        //�e�X�g���ʊm�F

        // ���N�G�X�g�ɓo�^����Ă��郂�W���[���R���t�B�O��
        // �ԋp����邱��
        assertSame(reqConfig, retConfig);
    }

    /**
     * testGetModuleConfig04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) request.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *         (���) ServletContext.getAttribute(<br>
     *                    Globals.MODULE_KEY):null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ModuleConfig:null<br>
     *
     * <br>
     * ���N�G�X�g�A�T�[�u���b�g�R���e�L�X�g��ModuleConfig�����݂��Ȃ��ꍇ�Anull���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetModuleConfig04() throws Exception {
        //�e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // �[���Z�b�V����
        MockHttpSession session = new MockHttpSession();

        // �[���T�[�u���b�g�R���e�L�X�g
        MockServletContext context = new MockServletContext();

        session.setServletContext(context);
        req.setSession(session);

        //�e�X�g���s
        ModuleConfig retConfig = ModuleUtil.getModuleConfig(req);

        //�e�X�g���ʊm�F

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郂�W���[���R���t�B�O��
        // �ԋp����邱��
        assertNull(retConfig);
    }

    /**
     * testGetPrefix01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *
     * <br>
     * ������request��null�̏ꍇ�Anull���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPrefix01() throws Exception {
        Object result = ModuleUtil.getPrefix(null);
        assertNull(result);
    }

    /**
     * testGetPrefix02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) getModuleConfig�̌���:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *
     * <br>
     * getModuleConfig��null��ԋp����ꍇ�Anull���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPrefix02() throws Exception {
        //�e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();

        // �[���Z�b�V����
        MockHttpSession session = new MockHttpSession();

        // �[���T�[�u���b�g�R���e�L�X�g
        MockServletContext context = new MockServletContext();

        session.setServletContext(context);
        req.setSession(session);

        //�e�X�g���s
        Object result = ModuleUtil.getPrefix(req);

        //�e�X�g���ʊm�F
        assertNull(result);
    }

    /**
     * testGetPrefix03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FE
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) getModuleConfig�̌���:not null<br>
     *                prefix="abc"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:abc<br>
     *
     * <br>
     * ModuleConfig��prefix�̒l���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPrefix03() throws Exception {
        //�e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // ���W���[���R���t�B�O
        ModuleConfig apConfig = new ModuleConfigImpl("abc");
        // �[���Z�b�V����
        MockHttpSession session = new MockHttpSession();
        // �[���T�[�u���b�g�R���e�L�X�g
        MockServletContext context = new MockServletContext();

        // ���W���[���R���t�B�O���T�[�u���b�g�R���e�L�X�g�݂̂ɓo�^����Ă���B
        req.setAttribute(Globals.MODULE_KEY, null);
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //�e�X�g���s
        String prefix = ModuleUtil.getPrefix(req);

        //�e�X�g���ʊm�F

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郂�W���[���R���t�B�O��
        // �ԋp����邱��
        assertEquals("abc", prefix);
    }

    /**
     * testGetPrefix04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (���) getModuleConfig�̌���:not null<br>
     *                prefix=null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *
     * <br>
     * ModuleConfig��prefix�̒l���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPrefix04() throws Exception {
        //�e�X�g�f�[�^�ݒ�

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // ���W���[���R���t�B�O
        ModuleConfig apConfig = new ModuleConfigImpl(null);
        // �[���Z�b�V����
        MockHttpSession session = new MockHttpSession();
        // �[���T�[�u���b�g�R���e�L�X�g
        MockServletContext context = new MockServletContext();

        // ���W���[���R���t�B�O���T�[�u���b�g�R���e�L�X�g�݂̂ɓo�^����Ă���B
        req.setAttribute(Globals.MODULE_KEY, null);
        context.setAttribute(Globals.MODULE_KEY, apConfig);
        session.setServletContext(context);
        req.setSession(session);

        //�e�X�g���s
        String prefix = ModuleUtil.getPrefix(req);

        //�e�X�g���ʊm�F

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郂�W���[���R���t�B�O��
        // �ԋp����邱��
        assertNull(prefix);
    }

}
