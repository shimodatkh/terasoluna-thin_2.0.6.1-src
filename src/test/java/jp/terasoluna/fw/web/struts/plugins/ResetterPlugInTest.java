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

package jp.terasoluna.fw.web.struts.plugins;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.web.struts.reset.ActionReset;
import jp.terasoluna.fw.web.struts.reset.FieldReset;
import jp.terasoluna.fw.web.struts.reset.ResetterImpl;
import jp.terasoluna.fw.web.struts.reset.ResetterResources;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.digester.xmlrules.XmlLoadException;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.xml.sax.SAXException;

/**
 * {@link jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �t�H�[���̃��Z�b�g�ݒ�����[�h����v���O�C���B<br>
 * PlugIn�@�\���g�p���A�T�[�u���b�g���������Ƀt�H�[���̃��Z�b�g�@�\�̐ݒ��ǂݍ��݁A�T�[�u���b�g�R���e�L�X�g�ɕۑ�����B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn
 */
public class ResetterPlugInTest extends TestCase {

    /**
     * �t�@�C���p�X���v���p�e�B����擾
     */
    private static final String RESET_FILE_NAME = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset.xml").getPath();

    /**
     * �t�@�C���p�X���v���p�e�B����擾
     */
    private static final String RESET_FILE_NAME2 = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset2.xml").getPath();

    /**
     * �t�@�C���p�X���v���p�e�B����擾
     */
    private static final String RESET_FILE_NAME3 = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset3.xml").getPath();

    /**
     * �t�@�C���p�X���v���p�e�B����擾
     */
    private static final String RESET_RULES_FILE_NAME = ResetterPlugInTest.class
            .getResource("ResetterPlugInTest-reset-rules.xml").getPath();
    
    /**
     * ResetterPlugIn�B
     */
    private ResetterPlugIn plugin = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ResetterPlugInTest.class);
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
        
        plugin = new ResetterPlugIn();
        UTUtil.setPrivateField(ResetterPlugIn.class, "digester", null);
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
    public ResetterPlugInTest(String name) {
        super(name);
    }

    /**
     * testInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resetter:ResetterPlugIn_ResetterStub01�i���݂���N���X�j<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-reset.xml"�i���݂���t�@�C���j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�T�[�u���b�g�R���e�L�X�g��"RESETTER_RESOURCES"���L�[�ɂ���ResetterResources�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) RESETTER:�T�[�u���b�g�R���e�L�X�g��"RESETTER"���L�[�ɂ���ResetterPlugIn_ResetterStub01�C���X�^���X���i�[����邱�ƁB<br>
     *         
     * <br>
     * ����������������ɍs���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // ���Z�b�^�[�N���X���S�C���N���X���ݒ�
        UTUtil.setPrivateField(plugin, "resetter",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "ResetterPlugIn_ResetterStub01");

        // ���Z�b�^�[�N���X���S�C���N���X���ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // ���Z�b�g���[�����w��
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �e�X�g���s
        plugin.init(servlet, conf);
        
        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn_ResetterStub01",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx.getAttribute("RESETTER_RESOURCES"))
                        .getClass().getName());
    }

    /**
     * testInitResetter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resetter:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER:�T�[�u���b�g�R���e�L�X�g��"RESETTER"���L�[�ɂ���Resetter�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         
     * <br>
     * this.resetter��null�̎��A����Ƀf�t�H���gresetter���T�[�u���b�g�R���e�L�X�g�ɒǉ�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResetter01() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // ���Z�b�^�[�N���X���S�C���N���X���ݒ�
        UTUtil.setPrivateField(plugin, "resetter", null);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.initResetter(servlet, conf);

        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals("jp.terasoluna.fw.web.struts.reset.ResetterImpl",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());
    }

    /**
     * testInitResetter02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resetter:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER:�T�[�u���b�g�R���e�L�X�g��"RESETTER"���L�[�ɂ���Resetter�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         
     * <br>
     * this.resetter���󕶎��̎��A����Ƀf�t�H���gresetter���T�[�u���b�g�R���e�L�X�g�ɒǉ����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResetter02() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // ���Z�b�^�[�N���X���S�C���N���X���ݒ�
        UTUtil.setPrivateField(plugin, "resetter", "");

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.initResetter(servlet, conf);

        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals("jp.terasoluna.fw.web.struts.reset.ResetterImpl",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());
    }

    /**
     * testInitResetter03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resetter:Nothing�i���݂��Ȃ��N���X�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER:�|<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FClassLoadException(ClassNotFoundException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FClassLoadException<br>
     *         
     * <br>
     * this.resetter�����݂��Ȃ��N���X�̎��AServletException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResetter03() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // ���Z�b�^�[�N���X���S�C���N���X���ݒ�
        UTUtil.setPrivateField(plugin, "resetter", "Nothing");

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        try {
            // �e�X�g���s
            plugin.initResetter(servlet, conf);
            fail();
        } catch (ServletException e) {
            // ���ʊm�F
            assertEquals(ClassLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertEquals(ClassNotFoundException.class.getName(), e
                    .getRootCause().getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInitResetter04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resetter:ResetterPlugIn_ResetterStub01�i���݂���N���X�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER:�T�[�u���b�g�R���e�L�X�g��"RESETTER"���L�[�ɂ���ResetterPlugIn_ResetterStub01�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         
     * <br>
     * this.resetter�����݂���N���X�̎��A�����resetter���T�[�u���b�g�R���e�L�X�g�ɒǉ����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResetter04() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // ���Z�b�^�[�N���X���S�C���N���X���ݒ�
        UTUtil.setPrivateField(plugin, "resetter",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "ResetterPlugIn_ResetterStub01");

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.initResetter(servlet, conf);

        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn_ResetterStub01",
                ((ResetterImpl) ctx.getAttribute("RESETTER")).getClass().getName());

    }

    /**
     * testInitResetter05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)="test1"<br>
     *         (���) this.resetter:ResetterPlugIn_ResetterStub01�i���݂���N���X�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER:�T�[�u���b�g�R���e�L�X�g��"RESETTER/test1"���L�[�ɂ���ResetterPlugIn_ResetterStub01�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         
     * <br>
     * ���W���[�������󕶎��ȊO�̎��A�T�[�u���b�g�R���e�L�X�g�ɓo�^�����L�[�ɁA���W���[�������ǉ�����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResetter05() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("/test1");

        // ���Z�b�^�[�N���X���S�C���N���X���ݒ�
        UTUtil.setPrivateField(plugin, "resetter",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "ResetterPlugIn_ResetterStub01");

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.initResetter(servlet, conf);

        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.plugins.ResetterPlugIn_ResetterStub01",
                ((ResetterImpl) ctx.getAttribute("RESETTER/test1")).getClass()
                        .getName());

    }

    /**
     * testInitResources01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:null<br>
     *         (���) digesterRules:�|<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�|<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�Z�[�W�F"resources file location is not specified"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"resources file location is not specified"<br>
     *         
     * <br>
     * this.resourcesPath��null�̎�ServletException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources01() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", null);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals("resources file location is not specified", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
        }
    }

    /**
     * testInitResources02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:""<br>
     *         (���) digesterRules:�|<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�|<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�Z�[�W�F"resources file location is not specified"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"resources file location is not specified"<br>
     *         
     * <br>
     * this.resourcesPath���󕶎��̎�ServletException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources02() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", "");

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals("resources file location is not specified", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
        }
    }

    /**
     * testInitResources03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (���) digesterRules:null<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�|<br>
     *         (��ԕω�) digesterRules:"/WEB-INF/reset-rules.xml"<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FXmlLoadException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FXmlLoadException<br>
     *         
     * <br>
     * digesterRules���w�肳��Ȃ��ꍇ�A�f�t�H���g�̃t�@�C�������i�[����Ă��邱�Ƃ��m�F����B�܂��AdigesterRules�t�@�C�������݂��Ȃ��t�@�C���̎�ServletException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources03() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        try {
            // �e�X�g���s
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
            assertEquals("/WEB-INF/reset-rules.xml", UTUtil.getPrivateField(
                    plugin, "digesterRules"));
        }
    }

    /**
     * testInitResources04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (���) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (���) ServletContext#getResource():MalformedURLException����<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�|<br>
     *         (��ԕω�) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FMalformedURLException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FMalformedURLException<br>
     *         
     * <br>
     * ServletContext#getResource()��MalformedURLException�������������AServletException�Ń��b�v���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources04() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);
        // reset-rules.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub02 servlet = new ResetterPlugIn_ActionServletStub02();

        try {
            // �e�X�g���s
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            assertEquals(MalformedURLException.class.getName(), e
                    .getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
            assertEquals(RESET_RULES_FILE_NAME, UTUtil.getPrivateField(plugin,
                    "digesterRules"));
        }
    }
    
    /**
     * testInitResources05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (���) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�T�[�u���b�g�R���e�L�X�g��"RESETTER_RESOURCES"���L�[�ɂ���ResetterResources�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         
     * <br>
     * RESETTER_RESOURCES���L�[�ɂ��āA����ɃT�[�u���b�g�R���e�L�X�g�ɒǉ�����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources05() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);
        // reset-rules.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.initResources(servlet, conf);

        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx.getAttribute("RESETTER_RESOURCES"))
                        .getClass().getName());
    }

    /**
     * testInitResources06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)="/test2"<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-reset.xml"<br>
     *         (���) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�T�[�u���b�g�R���e�L�X�g��"RESETTER_RESOURCES/test2"���L�[�ɂ���ResetterResources�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         
     * <br>
     * ���W���[�������󕶎��ȊO�̎��A�T�[�u���b�g�R���e�L�X�g�ɓo�^�����L�[�ɁA���W���[�������ǉ�����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources06() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("/test2");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME);
        // reset-rules.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.initResources(servlet, conf);

        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx
                        .getAttribute("RESETTER_RESOURCES/test2")).getClass()
                        .getName());
    }

    /**
     * testInitResources07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:"nothing.xml"�i���݂��Ȃ��t�@�C�����j<br>
     *         (���) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�|<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FIOException<br>
     *         
     * <br>
     * this.resourcePath�ɑ��݂��Ȃ��t�@�C�������w�肵���ꍇ�AServletException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources07() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", "nothing.xml");

        // reset-rules.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.initResources(servlet, conf);
            fail();
        } catch (ServletException e) {
            // ���ʊm�F
            // ���ۂ��̏ꍇ�́AMalformedURLException���������邪�Acatch��IOException�Ȃ̂�instanceof�Ń`�F�b�N����
            assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }
    
    /**
     * testInitResources08()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-error-reset.xml"(�p�[�X�G���[����������t�@�C����)<br>
     *         (���) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�|<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FSAXException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FSAXException<br>
     *         
     * <br>
     * this.resourcesPath�Ƀp�[�X�G���[����������t�@�C�����w�肵�����AServletException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources08() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath",
                ResetterPlugInTest.class.getResource(
                        "ResetterPlugInTest-error-reset.xml").getPath());

        // reset-rules.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, conf);
            fail();
        } catch (ServletException e) {
            // ���ʊm�F
            // ���ۂ��̏ꍇ�́ASAXParseException���������邪�Acatch��SAXException�Ȃ̂�instanceof�Ń`�F�b�N����
            assertTrue(e.getRootCause() instanceof SAXException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }
    
    /**
     * testInitResources09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-reset.xml","ResetterPlugInTest-reset2.xml","ResetterPlugInTest-reset3.xml"<br>
     *         (���) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�T�[�u���b�g�R���e�L�X�g��"RESETTER_RESOURCES"���L�[�ɂ���ResetterResources�C���X�^���X���i�[����邱�ƁB�iResetterResources�̒��g�̊m�F���s�Ȃ��j<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         
     * <br>
     * this.resourcesPath�ɕ����̃t�@�C�������w�肵�����ARESETTER_RESOURCES���L�[�ɂ��āA����ɃT�[�u���b�g�R���e�L�X�g�ɒǉ�����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources09() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME + ","
                + RESET_FILE_NAME2 + "," + RESET_FILE_NAME3);
        // reset-rules.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.initResources(servlet, conf);

        // ���ʊm�F
        // �T�[�u���b�g�R���e�L�X�g�擾
        ServletContext ctx = servlet.getServletContext();
        assertEquals(
                "jp.terasoluna.fw.web.struts.reset.ResetterResources",
                ((ResetterResources) ctx.getAttribute("RESETTER_RESOURCES"))
                        .getClass().getName());

        // XML���p�[�X�������ʂ��擾
        ResetterResources resource = (ResetterResources) ctx
                .getAttribute("RESETTER_RESOURCES");
        Map actionResets = (Map) UTUtil.getPrivateField(resource,
                "actionResets");

        // �P�t�@�C���ڂ�reset�t�@�C��������ɓǂݍ��߂Ă��邱��
        ActionReset actionReset1_1 = (ActionReset) actionResets
                .get("/changePage");
        // fieldReset�`�F�b�N
        Map fieldResets1_1 = (Map) UTUtil.getPrivateField(actionReset1_1,
                "fieldResets");
        assertEquals(1, fieldResets1_1.size());
        FieldReset fieldReset1_1 = (FieldReset) fieldResets1_1.get("field1-1");
        assertEquals("field1-1", fieldReset1_1.getFieldName());
        assertFalse(fieldReset1_1.isSelect());

        ActionReset actionReset1_2 = (ActionReset) actionResets.get("/bbb/BBB");
        // fieldReset�`�F�b�N
        Map fieldResets1_2 = (Map) UTUtil.getPrivateField(actionReset1_2,
                "fieldResets");
        assertEquals(3, fieldResets1_2.size());
        FieldReset fieldReset1_2 = (FieldReset) fieldResets1_2.get("field1-1");
        assertEquals("field1-1", fieldReset1_2.getFieldName());
        assertFalse(fieldReset1_2.isSelect());
        FieldReset fieldReset1_3 = (FieldReset) fieldResets1_2.get("field1-2");
        assertEquals("field1-2", fieldReset1_3.getFieldName());
        assertTrue(fieldReset1_3.isSelect());
        FieldReset fieldReset1_4 = (FieldReset) fieldResets1_2.get("field1-3");
        assertEquals("field1-3", fieldReset1_4.getFieldName());
        assertTrue(fieldReset1_4.isSelect());

        ActionReset actionReset1_3 = (ActionReset) actionResets.get("/ccc/CCC");
        // fieldReset�`�F�b�N
        Map fieldResets1_3 = (Map) UTUtil.getPrivateField(actionReset1_3,
                "fieldResets");
        assertEquals(2, fieldResets1_3.size());
        FieldReset fieldReset1_5 = (FieldReset) fieldResets1_3.get("field1-1");
        assertEquals("field1-1", fieldReset1_5.getFieldName());
        assertFalse(fieldReset1_5.isSelect());
        FieldReset fieldReset1_6 = (FieldReset) fieldResets1_3.get("field1-2");
        assertEquals("field1-2", fieldReset1_6.getFieldName());
        assertFalse(fieldReset1_6.isSelect());

        // �Q�t�@�C���ڂ�reset�t�@�C��������ɓǂݍ��߂Ă��邱��
        ActionReset actionReset2_1 = (ActionReset) actionResets
                .get("/aaa/AAA2");
        // fieldReset�`�F�b�N
        Map fieldResets2_1 = (Map) UTUtil.getPrivateField(actionReset2_1,
                "fieldResets");
        assertEquals(1, fieldResets2_1.size());
        FieldReset fieldReset2_1 = (FieldReset) fieldResets2_1.get("field2-1");
        assertEquals("field2-1", fieldReset2_1.getFieldName());
        assertFalse(fieldReset2_1.isSelect());

        ActionReset actionReset2_2 = (ActionReset) actionResets
                .get("/bbb/BBB2");
        // fieldReset�`�F�b�N
        Map fieldResets2_2 = (Map) UTUtil.getPrivateField(actionReset2_2,
                "fieldResets");
        assertEquals(3, fieldResets2_2.size());
        FieldReset fieldReset2_2 = (FieldReset) fieldResets2_2.get("field2-1");
        assertEquals("field2-1", fieldReset2_2.getFieldName());
        assertFalse(fieldReset2_2.isSelect());
        FieldReset fieldReset2_3 = (FieldReset) fieldResets2_2.get("field2-2");
        assertEquals("field2-2", fieldReset2_3.getFieldName());
        assertTrue(fieldReset2_3.isSelect());
        FieldReset fieldReset2_4 = (FieldReset) fieldResets2_2.get("field2-3");
        assertEquals("field2-3", fieldReset2_4.getFieldName());
        assertTrue(fieldReset2_4.isSelect());

        ActionReset actionReset2_3 = (ActionReset) actionResets
                .get("/ccc/CCC2");
        // fieldReset�`�F�b�N
        Map fieldResets2_3 = (Map) UTUtil.getPrivateField(actionReset2_3,
                "fieldResets");
        assertEquals(2, fieldResets2_3.size());
        FieldReset fieldReset2_5 = (FieldReset) fieldResets2_3.get("field2-1");
        assertEquals("field2-1", fieldReset2_5.getFieldName());
        assertFalse(fieldReset2_5.isSelect());
        FieldReset fieldReset2_6 = (FieldReset) fieldResets2_3.get("field2-2");
        assertEquals("field2-2", fieldReset2_6.getFieldName());
        assertFalse(fieldReset2_6.isSelect());

        // �R�t�@�C���ڂ�reset�t�@�C��������ɓǂݍ��߂Ă��邱��
        ActionReset actionReset3_1 = (ActionReset) actionResets
                .get("/aaa/AAA3");
        // fieldReset�`�F�b�N
        Map fieldResets3_1 = (Map) UTUtil.getPrivateField(actionReset3_1,
                "fieldResets");
        assertEquals(1, fieldResets3_1.size());
        FieldReset fieldReset3_1 = (FieldReset) fieldResets3_1.get("field3-1");
        assertEquals("field3-1", fieldReset3_1.getFieldName());
        assertFalse(fieldReset3_1.isSelect());

        ActionReset actionReset3_2 = (ActionReset) actionResets
                .get("/bbb/BBB3");
        // fieldReset�`�F�b�N
        Map fieldResets3_2 = (Map) UTUtil.getPrivateField(actionReset3_2,
                "fieldResets");
        assertEquals(3, fieldResets3_2.size());
        FieldReset fieldReset3_2 = (FieldReset) fieldResets3_2.get("field3-1");
        assertEquals("field3-1", fieldReset3_2.getFieldName());
        assertFalse(fieldReset3_2.isSelect());
        FieldReset fieldReset3_3 = (FieldReset) fieldResets3_2.get("field3-2");
        assertEquals("field3-2", fieldReset3_3.getFieldName());
        assertTrue(fieldReset3_3.isSelect());
        FieldReset fieldReset3_4 = (FieldReset) fieldResets3_2.get("field3-3");
        assertEquals("field3-3", fieldReset3_4.getFieldName());
        assertTrue(fieldReset3_4.isSelect());

        ActionReset actionReset3_3 = (ActionReset) actionResets
                .get("/ccc/CCC3");
        // fieldReset�`�F�b�N
        Map fieldResets3_3 = (Map) UTUtil.getPrivateField(actionReset3_3,
                "fieldResets");
        assertEquals(2, fieldResets3_3.size());
        FieldReset fieldReset3_5 = (FieldReset) fieldResets3_3.get("field3-1");
        assertEquals("field3-1", fieldReset3_5.getFieldName());
        assertFalse(fieldReset3_5.isSelect());
        FieldReset fieldReset3_6 = (FieldReset) fieldResets3_3.get("field3-2");
        assertEquals("field3-2", fieldReset3_6.getFieldName());
        assertFalse(fieldReset3_6.isSelect());

        // �Q�t�@�C���ڂƂR�t�@�C���ڂ�reset�t�@�C���ɏd�������p�X�����݂����ꍇ�A
        // �ォ��ݒ肳�ꂽ�t�@�C���̏�񂪐ݒ肳��Ă��邱��
        ActionReset actionResetDuplicate = (ActionReset) actionResets
                .get("/duplicate");
        // fieldReset�`�F�b�N
        Map duplicate = (Map) UTUtil.getPrivateField(actionResetDuplicate,
                "fieldResets");
        assertEquals(1, duplicate.size());
        FieldReset fieldReset1 = (FieldReset) duplicate.get("duplicate1");
        assertNull(fieldReset1);
        FieldReset fieldReset2 = (FieldReset) duplicate.get("duplicate2");
        assertEquals("duplicate2", fieldReset2.getFieldName());
        assertFalse(fieldReset2.isSelect());
    }

    /**
     * testInitResources10()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) this.resourcePath:"ResetterPlugInTest-reset.xml","ResetterPlugInTest-reset2.xml","ResetterPlugInTest-reset3.xml"<br>
     *         (���) digesterRules:"ResetterPlugInTest-reset-rules.xml"<br>
     *         (���) ServletContext#getResource():�|<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) RESETTER_RESOURCES:�|<br>
     *         (��ԕω�) digesterRules:�|<br>
     *         (��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FIOException<br>
     *         
     * <br>
     * getPublicIdentifier()�̖߂肪�󔒂ŁAURL�̃I�u�W�F�N�g�������ł��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitResources10() throws Exception {
        // ���W���[���ݒ���
        ModuleConfig conf = new ModuleConfigImpl("");

        plugin = new ResetterPlugIn() {
            @Override
            public String getPublicIdentifier() {
            	return "";
            }
        };

        // reset.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "resourcesPath", RESET_FILE_NAME + ","
                + RESET_FILE_NAME2 + "," + RESET_FILE_NAME3);
        // reset-rules.xml�̃p�X�ݒ�
        UTUtil.setPrivateField(plugin, "digesterRules", RESET_RULES_FILE_NAME);

        // �A�N�V�����T�[�u���b�g�쐬
        ResetterPlugIn_ActionServletStub01 servlet = new ResetterPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.initResources(servlet, conf);
            fail("�e�X�g���s");
        } catch (ServletException e) {
        	assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }

    }

    /**
     * testSetResetter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) string:"string"<br>
     *         (���) resetter:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) resetter:"string"<br>
     *         
     * <br>
     * ������string���������i�[����邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetResetter01() throws Exception {
        // �����ݒ�
        ResetterPlugIn rpi = new ResetterPlugIn();
        // �e�X�g���s
        rpi.setResetter("string");
        // ���ʊm�F
        assertEquals("string", UTUtil.getPrivateField(rpi, "resetter"));
    }

    /**
     * testSetResources01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) string:"string"<br>
     *         (���) resourcesPath:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) resourcesPath:"string"<br>
     *         
     * <br>
     * ������string���������i�[����邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetResources01() throws Exception {
        // �����ݒ�
        ResetterPlugIn rpi = new ResetterPlugIn();
        // �e�X�g���s
        rpi.setResources("string");
        // ���ʊm�F
        assertEquals("string", UTUtil.getPrivateField(rpi, "resourcesPath"));
    }

    /**
     * testSetDigesterRules01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) digesterRules:"string"<br>
     *         (���) digesterRules:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) digesterRules:"string"<br>
     *         
     * <br>
     * ������digesterRules���������i�[����邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDigesterRules01() throws Exception {
        // �����ݒ�
        ResetterPlugIn rpi = new ResetterPlugIn();
        // �e�X�g���s
        rpi.setDigesterRules("string");
        // ���ʊm�F
        assertEquals("string", UTUtil.getPrivateField(rpi, "digesterRules"));
    }

    /**
     * testSetPublicIdentifier01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) publicIdentifier:"publicIdentifier"<br>
     *         (���) publicIdentifier:not null�i�f�t�H���g�l�j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) publicIdentifier:"publicIdentifier"<br>
     *
     * <br>
     * ������publicIdentifier���������i�[����邱��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPublicIdentifier01() throws Exception {
        // �e�X�g���{
        plugin.setPublicIdentifier("publicIdentifier");

        // ����
        assertEquals("publicIdentifier", UTUtil.getPrivateField(plugin,
                "publicIdentifier"));
    }

    /**
     * testSetDtdUrl01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) dtdUrl:"dtdUrl"<br>
     *         (���) dtdUrl:not null�i�f�t�H���g�l�j<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) dtdUrl:"dtdUrl"<br>
     *
     * <br>
     * ������dtdUrl���������i�[����邱��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDtdUrl01() throws Exception {
        // �e�X�g���{
        plugin.setDtdUrl("dtdUrl");

        // ����
        assertEquals("dtdUrl", UTUtil.getPrivateField(plugin, "dtdUrl"));
    }

}
