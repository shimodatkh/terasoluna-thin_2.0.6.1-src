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
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicProperty;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.digester.xmlrules.XmlLoadException;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.xml.sax.SAXException;

/**
 * {@link jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N���o�͂̐ݒ�������[�h����v���O�C���B <br>
 * Struts��PlugIn�@�\���g�p���A�T�[�u���b�g���������Ƀr�W�l�X���W�b�N���o�͂�
 * �ݒ��ǂݍ��݁A�T�[�u���b�g�R���e�L�X�g�ɕۑ�����B<br>
 * <br>
 * �O������F<br>
 * init()���\�b�h�́AinitResources()�AinitParser()�̎���������B<br>
 * �O������Ŏw�肷��xml�t�@�C�����i�[����Ă��邱�Ƃ��O��ł���B<br>
 * �܂��A������Servlet�AModuleConfig�Ƃ��̑����̃��W���[������
 * null�ł��邱�Ƃ͂��肦�Ȃ��B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 */
public class BLogicIOPlugInTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     *
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicIOPlugInTest.class);
    }

    /**
     * BLogicIOPlugIn�B
     */
    private BLogicIOPlugIn plugin = null;

    /**
     * �������������s���B
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // BLogicIOPlugIn�C���X�^���X
        plugin = new BLogicIOPlugIn();

        UTUtil.setPrivateField(BLogicIOPlugIn.class, "digester", null);
    }

    /**
     * �I���������s���B
     *
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public BLogicIOPlugInTest(String name) {
        super(name);
    }

    /**
     * testInit01()
     * <br><br>
     *
     *  (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:null<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FXmlLoadException<br>
     *                    ���f�t�H���g�̃��[���t�@�C�������݂��Ȃ��ׁA���������O<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FXmlLoadException<br>
     *         (��ԕω�) digesterRules:"/WEB-INF/blogic-io-rules.xml"<br>
     *
     * <br>
     * Digester�̃��[���t�@�C�������w�肳��Ă��Ȃ��ꍇ�A�T�[�u���b�g�R���e�L�X�g�̃��\�[�X�ɂ̓f�t�H���g�̃��[���t�@�C�����o�^����Ă��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // BLogic��`�̃��[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules", null);
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());

        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertEquals("/WEB-INF/blogic-io-rules.xml", UTUtil
                    .getPrivateField(plugin, "digesterRules"));
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
            // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郊�\�[�X��
            // �f�t�H���g�̃��[���t�@�C���ł��邱�ƁB
            BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                    .getServletContext();
            assertEquals("/WEB-INF/blogic-io-rules.xml", ctx
                    .getCalledResources());

        }
    }

    /**
     * testInit02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:""(�󕶎�)<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FXmlLoadException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FXmlLoadException<br>
     *
     * <br>
     * ���[���t�@�C�������󕶎��̏ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");
        // BLogic��`�̃��[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules", "");
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"/nothing.xml"�i���݂��Ȃ����[���t�@�C�����j<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FXmlLoadException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FXmlLoadException<br>
     *
     * <br>
     * ���݂��Ȃ����[���t�@�C�������w�肳��Ă���ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit03() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");
        // BLogic��`�̃��[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules", "/nothing.xml");
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-empty.xml"�i�p�[�X�G���[����������t�@�C�����j<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FXmlLoadException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FXmlLoadException<br>
     *
     * <br>
     * �p�[�X�G���[���������郋�[���t�@�C�������w�肳�ꂽ�ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit04() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // �p�[�X�G���[�𔭐�������t�@�C�����irules�ł͂Ȃ��j
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-empty.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(XmlLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:parse()���\�b�h��IOException���X���[����Digester<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FIOException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FIOException<br>
     *
     * <br>
     * IOException�����������ꍇ�AServletException�Ń��b�v���ăX���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit05() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr",
                "jp.terasoluna.fw.web.struts.plugins"
                        + ".BLogicIOPlugIn_BLogicMapperStub01");
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // Digester�쐬�iIOException����������j
        BLogicIOPlugIn_DigesterStub01 digester = new BLogicIOPlugIn_DigesterStub01();
        UTUtil.setPrivateField(BLogicIOPlugIn.class, "digester", digester);

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(IOException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resources:�T�[�u���b�g�R���e�L�X�g��BLOGIC_RESOURCES���L�[�ɂ���BLogicResources�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) mapper:�T�[�u���b�g�R���e�L�X�g��BLOGIC_MAPPER���L�[�ɂ���BLogicMapper�C���X�^���X���i�[����邱�ƁB<br>
     *
     * <br>
     * �}�b�p�[�N���X����null�̎��A�f�t�H���g�̃}�b�p�[�N���X"jp.terasoluna.fw.service.thin.BLogicMapper"�̃C���X�^���X����������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit06() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.init(servlet, config);

        // �T�[�u���b�g�R���e�L�X�g�擾
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // �T�[�u���b�g�R���e�L�X�g���Ƀf�t�H���g��BLogicMapper�C���X�^���X��
        // �i�[����Ă��邱��
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER").getClass().getName());

        // �T�[�u���b�g�R���e�L�X�g����BLogicResources�C���X�^���X��
        // �i�[����Ă��邱��
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES") instanceof BLogicResources);

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郊�\�[�X��
        // �w�肵�����[���t�@�C���ł��邱�ƁB
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:BLogicMapper���p�������X�^�u���w��F<br>
     *                "jp.terasoluna.fw.web.struts.plugins.BLogicMapperStub"<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resources:�T�[�u���b�g�R���e�L�X�g��BLOGIC_RESOURCES���L�[�ɂ���BLogicResources�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) mapper:�T�[�u���b�g�R���e�L�X�g��BLOGIC_MAPPER���L�[�ɂ���BLogicMapperStub�C���X�^���X���i�[����邱�ƁB<br>
     *
     * <br>
     * �}�b�p�[�N���X����BLogicMapper���p�������N���X�̎��A���̃N���X�̃C���X�^���X��ServletContext�ɓo�^����Ă��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit07() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr",
                "jp.terasoluna.fw.web.struts.plugins"
                        + ".BLogicIOPlugIn_BLogicMapperStub01");

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.init(servlet, config);

        // �T�[�u���b�g�R���e�L�X�g�擾
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // �T�[�u���b�g�R���e�L�X�g���Ƀv���O�C���Ŏw�肵��
        // BLogicMapper�p���C���X�^���X���i�[����Ă��邱��
        assertEquals("jp.terasoluna.fw.web.struts.plugins"
                + ".BLogicIOPlugIn_BLogicMapperStub01", ctx.getAttribute(
                "BLOGIC_MAPPER").getClass().getName());

        // �T�[�u���b�g�R���e�L�X�g����BLogicResources�C���X�^���X��
        // �i�[����Ă��邱��
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES") instanceof BLogicResources);

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郊�\�[�X��
        // �w�肵�����[���t�@�C���ł��邱�ƁB
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:BLogicMapper�AAbstractBLogicMapper���p�����Ă��Ȃ��N���X�����w��<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FClassCastException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FClassCastException<br>
     *
     * <br>
     * �}�b�p�[�N���X����BLogicMapper�AAbstractBLogicMapper���p�������N���X�łȂ����AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit08() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��(BLogicMapper�N���X�ł͂Ȃ��j
        UTUtil.setPrivateField(plugin, "mapperStr",
                "jp.terasoluna.fw.web.struts.plugins."
                        + "BLogicIOPlugIn_BLogicMapperStub02");

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(ClassCastException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:���݂��Ȃ��N���X�����w��<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FClassLoadException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FClassLoadException<br>
     *
     * <br>
     * ���݂��Ȃ��}�b�p�[�N���X�����w�肵���ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit09() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��(���݂��Ȃ��N���X���j
        UTUtil.setPrivateField(plugin, "mapperStr", "nothing.NothingClass");

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(ClassLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:""(�󕶎�)<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FClassLoadException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FClassLoadException<br>
     *
     * <br>
     * �}�b�p�[�N���X���󕶎��̎��AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit10() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��(�󕶎��j
        UTUtil.setPrivateField(plugin, "mapperStr", "");

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(ClassLoadException.class.getName(), e.getRootCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:null<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�Z�[�W�F"resources file location is not specified"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"resources file location is not specified"<br>
     *
     * <br>
     * �Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�������w�肳��Ă��Ȃ��ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit11() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����(null)
        UTUtil.setPrivateField(plugin, "resources", "");
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            // ���b�Z�[�W�m�F
            assertEquals("errors.resources.file", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
           }
    }

    /**
     * testInit12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"nothing.xml"(���݂��Ȃ��t�@�C����)<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�FMalformedURLException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FMalformedURLException<br>
     *
     * <br>
     * ���݂��Ȃ��Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�������w�肳��Ă���ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit12() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����(���݂��Ȃ��t�@�C����)
        UTUtil.setPrivateField(plugin, "resources", "nothing.xml");
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            assertEquals(MalformedURLException.class.getName(), e
                    .getRootCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:""(�󕶎�)<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�Z�[�W�F"resources file location is not specified"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F"resources file location is not specified"<br>
     *
     * <br>
     * �Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�������󕶎��̏ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit13() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����(�󕶎�)
        UTUtil.setPrivateField(plugin, "resources", "");
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            // ���b�Z�[�W�m�F
            assertEquals("errors.resources.file", e
                    .getMessage());
            assertTrue(LogUTUtil
                    .checkError("resources file location is not specified"));
        }
    }

    /**
     * testInit14()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-error-blogic-io.xml"(�p�[�X�G���[����������t�@�C����)<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�F<br>
     *                    SAXException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F""<br>
     *                    ��O�FSAXException<br>
     *
     * <br>
     * �p�[�X�G���[����������Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�������w�肳�ꂽ�ꍇ�AServletException���X���[����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit14() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����(�p�[�X�G���[�����j
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-error-blogic-io.xml")
                .getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail();
        } catch (ServletException e) {
            // �e�X�g���ʊm�F
            // ���ۂ��̏ꍇ�́ASAXParseException���������邪�Acatch��SAXException�Ȃ̂�instanceof�Ń`�F�b�N����
            assertTrue(e.getRootCause() instanceof SAXException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }
    }

    /**
     * testInit15()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)="/sub1"<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resources:�T�[�u���b�g�R���e�L�X�g��BLOGIC_RESOURCES/sub1���L�[�ɂ���BLogicResources�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) mapper:�T�[�u���b�g�R���e�L�X�g��BLOGIC_MAPPER/sub1���L�[�ɂ���BLogicMapperStub�C���X�^���X���i�[����邱�ƁB<br>
     *
     * <br>
     * ���W���[�������󕶎��ȊO�̎��A�T�[�u���b�g�R���e�L�X�g�ɓo�^�����BLogicResources�ABLogicMapper�̃L�[�ɁA���W���[�������ǉ�����Ă��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit15() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���("/sub1"�����W���[�����Ɏw��j
        ModuleConfig config = new ModuleConfigImpl("/sub1");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.init(servlet, config);

        // �T�[�u���b�g�R���e�L�X�g�擾
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // �T�[�u���b�g�R���e�L�X�g���Ƀf�t�H���g��BLogicMapper�C���X�^���X��
        // �i�[����Ă��邱�Ɓi"/sub1"���W���[���̃L�[�Łj
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER/sub1").getClass().getName());

        // �T�[�u���b�g�R���e�L�X�g����BLogicResources�C���X�^���X��
        // �i�[����Ă��邱�Ɓi"/sub1"���W���[���̃L�[�Łj
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES/sub1") instanceof BLogicResources);
        assertNull(ctx.getAttribute("BLOGIC_RESOURCES"));

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郊�\�[�X��
        // �w�肵�����[���t�@�C���ł��邱�ƁB
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit16()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io.xml"�A"BLogicIOPlugInTest-blogic-io2.xml"�ABLogicIOPlugInTest-blogic-io3.xml"�i�����t�@�C���w�莞�j<br>
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resources:�T�[�u���b�g�R���e�L�X�g��BLOGIC_RESOURCES���L�[�ɂ���BLogicResources�C���X�^���X���i�[����邱�ƁB<br>
     *         (��ԕω�) mapper:�T�[�u���b�g�R���e�L�X�g��BLOGIC_MAPPER���L�[�ɂ���BLogicMapper�C���X�^���X���i�[����邱�ƁB<br>
     *
     * <br>
     * blogic-io.xml�t�@�C�����R�t�@�C���A�����A���ꂼ�ꂪ������BLogicProperty�ɕۑ�����Ă��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit16() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io.xml").getPath()
                + ","
                + BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io2.xml").getPath()
                + ","
                + BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io3.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.init(servlet, config);

        // �T�[�u���b�g�R���e�L�X�g�擾
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // �T�[�u���b�g�R���e�L�X�g���Ƀf�t�H���g��BLogicMapper�C���X�^���X��
        // �i�[����Ă��邱��
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER").getClass().getName());

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郊�\�[�X��
        // �w�肵�����[���t�@�C���ł��邱�ƁB
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());

        // XML���p�[�X�������ʂ��擾
        BLogicResources resource = (BLogicResources) ctx
                .getAttribute("BLOGIC_RESOURCES");
        Map map = (Map) UTUtil.getPrivateField(resource, "blogicIO");

        // �P�t�@�C���ڂ�blogic-io�t�@�C��������ɓǂݍ��߂Ă��邱��
        BLogicIO logonIO = (BLogicIO) map.get("/logon");
        // blogic-params�`�F�b�N
        assertEquals("test_bean_name1-1", logonIO.getInputBeanName());
        List<BLogicProperty> logonParamList = logonIO.getBLogicParams();
        assertEquals(1, logonParamList.size());
        BLogicProperty property = logonParamList.get(0);
        assertEquals("test_params_blogic", property.getBLogicProperty());
        assertEquals("test_params_property", property.getProperty());
        assertEquals("form", property.getSource());
        // blogic-result�`�F�b�N
        List<BLogicProperty> logonResultList = logonIO.getBLogicResults();
        assertEquals(1, logonResultList.size());
        BLogicProperty result = logonResultList.get(0);
        assertEquals("test_result_blogic", result.getBLogicProperty());
        assertEquals("test_result_property", result.getProperty());
        assertEquals("session", result.getDest());

        BLogicIO dbIO = (BLogicIO) map.get("/db");
        // blogic-params�`�F�b�N
        assertEquals("test_bean_name1-2", dbIO.getInputBeanName());
        List dbParamList = dbIO.getBLogicParams();
        assertEquals(0, dbParamList.size());
        // blogic-result�`�F�b�N
        List dbResultList = dbIO.getBLogicResults();
        assertEquals(0, dbResultList.size());

        // �Q�t�@�C���ڂ�blogic-io�t�@�C��������ɓǂݍ��߂Ă��邱��
        BLogicIO logon2IO = (BLogicIO) map.get("/logon2");
        // blogic-params�`�F�b�N
        assertEquals("test_bean_name2-1", logon2IO.getInputBeanName());
        List<BLogicProperty> logon2ParamList = logon2IO.getBLogicParams();
        assertEquals(2, logon2ParamList.size());
        BLogicProperty property2_1 = logon2ParamList.get(0);
        assertEquals("test_params_blogic2-1", property2_1.getBLogicProperty());
        assertEquals("test_params_property2-1", property2_1.getProperty());
        assertEquals("form", property2_1.getSource());
        BLogicProperty property2_2 = logon2ParamList.get(1);
        assertEquals("test_params_blogic2-2", property2_2.getBLogicProperty());
        assertEquals("test_params_property2-2", property2_2.getProperty());
        assertEquals("form", property2_2.getSource());
        // blogic-result�`�F�b�N
        List<BLogicProperty> logon2ResultList = logon2IO.getBLogicResults();
        assertEquals(2, logon2ResultList.size());
        BLogicProperty result2_1 = logon2ResultList.get(0);
        assertEquals("test_result_blogic2-1", result2_1.getBLogicProperty());
        assertEquals("test_result_property2-1", result2_1.getProperty());
        assertEquals("session", result2_1.getDest());
        BLogicProperty result2_2 = logon2ResultList.get(1);
        assertEquals("test_result_blogic2-2", result2_2.getBLogicProperty());
        assertEquals("test_result_property2-2", result2_2.getProperty());
        assertEquals("session", result2_2.getDest());

        BLogicIO db2IO = (BLogicIO) map.get("/db2");
        // blogic-params�`�F�b�N
        assertEquals("test_bean_name2-3", db2IO.getInputBeanName());
        List db2ParamList = db2IO.getBLogicParams();
        assertEquals(0, db2ParamList.size());
        // blogic-result�`�F�b�N
        List db2ResultList = db2IO.getBLogicResults();
        assertEquals(0, db2ResultList.size());

        // �R�t�@�C���ڂ�blogic-io�t�@�C��������ɓǂݍ��߂Ă��邱��
        BLogicIO logon3IO = (BLogicIO) map.get("/logon3");
        // blogic-params�`�F�b�N
        assertEquals("test_bean_name3-1", logon3IO.getInputBeanName());
        List<BLogicProperty> logon3ParamList = logon3IO.getBLogicParams();
        assertEquals(3, logon3ParamList.size());
        BLogicProperty property3_1 = logon3ParamList.get(0);
        assertEquals("test_params_blogic3-1", property3_1.getBLogicProperty());
        assertEquals("test_params_property3-1", property3_1.getProperty());
        assertEquals("form", property3_1.getSource());
        BLogicProperty property3_2 = logon3ParamList.get(1);
        assertEquals("test_params_blogic3-2", property3_2.getBLogicProperty());
        assertEquals("test_params_property3-2", property3_2.getProperty());
        assertEquals("form", property3_2.getSource());
        BLogicProperty property3_3 = logon3ParamList.get(2);
        assertEquals("test_params_blogic3-3", property3_3.getBLogicProperty());
        assertEquals("test_params_property3-3", property3_3.getProperty());
        assertEquals("form", property3_3.getSource());
        // blogic-result�`�F�b�N
        List<BLogicProperty> logon3ResultList = logon3IO.getBLogicResults();
        assertEquals(3, logon3ResultList.size());
        BLogicProperty result3_1 = logon3ResultList.get(0);
        assertEquals("test_result_blogic3-1", result3_1.getBLogicProperty());
        assertEquals("test_result_property3-1", result3_1.getProperty());
        assertEquals("session", result3_1.getDest());
        BLogicProperty result3_2 = logon3ResultList.get(1);
        assertEquals("test_result_blogic3-2", result3_2.getBLogicProperty());
        assertEquals("test_result_property3-2", result3_2.getProperty());
        BLogicProperty result3_3 = logon3ResultList.get(2);
        assertEquals("test_result_blogic3-3", result3_3.getBLogicProperty());
        assertEquals("test_result_property3-3", result3_3.getProperty());
        assertEquals("session", result3_3.getDest());

        // �Q�t�@�C���ڂƂR�t�@�C���ڂ�blogic-io�t�@�C���ɏd�������p�X�����݂����ꍇ�A
        // �ォ��ݒ肳�ꂽ�t�@�C���̏�񂪐ݒ肳��Ă��邱��
        BLogicIO duplicateIO = (BLogicIO) map.get("/duplicate");
        // blogic-params�`�F�b�N
        assertEquals("test_bean_name3-2", duplicateIO.getInputBeanName());
        List<BLogicProperty> duplicateParamList = duplicateIO.getBLogicParams();
        assertEquals(1, duplicateParamList.size());
        BLogicProperty duplicate1 = duplicateParamList.get(0);
        assertEquals("duplicate_params_blogic2", duplicate1.getBLogicProperty());
        assertEquals("duplicate_params_property2", duplicate1.getProperty());
        assertEquals("form", duplicate1.getSource());
        // blogic-result�`�F�b�N
        List<BLogicProperty> duplicateResultList = duplicateIO
                .getBLogicResults();
        assertEquals(1, duplicateResultList.size());
        BLogicProperty duplicate2 = duplicateResultList.get(0);
        assertEquals("duplicate_result_blogic2", duplicate2.getBLogicProperty());
        assertEquals("duplicate_result_property2", duplicate2.getProperty());
        assertEquals("session", duplicate2.getDest());

        BLogicIO db3IO = (BLogicIO) map.get("/db3");
        // blogic-params�`�F�b�N
        assertEquals("test_bean_name3-3", db3IO.getInputBeanName());
        List db3ParamList = db3IO.getBLogicParams();
        assertEquals(0, db3ParamList.size());
        // blogic-result�`�F�b�N
        List db3ResultList = db3IO.getBLogicResults();
        assertEquals(0, db3ResultList.size());
    }

    /**
     * testInit17()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io4.xml"<br>
     *                bean-name�̎w��Ȃ��B
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resources:�T�[�u���b�g�R���e�L�X�g��BLOGIC_RESOURCES���L�[�ɂ���BLogicResources�C���X�^���X���i�[����邱�ƁB<br>
     *                              BlogicIO�C���X�^���X��inputBeanName��null�ł��邱�ƁB
     *         (��ԕω�) mapper:�T�[�u���b�g�R���e�L�X�g��BLOGIC_MAPPER���L�[�ɂ���BLogicMapper�C���X�^���X���i�[����邱�ƁB<br>
     *
     * <br>
     * blogic-io.xml�t�@�C����bean-name������null�̂Ƃ��ABLogicIO��inputBeanName��null�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit17() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io4.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        plugin.init(servlet, config);

        // �T�[�u���b�g�R���e�L�X�g�擾
        BLogicIOPlugIn_MockServletContextStub01 ctx = (BLogicIOPlugIn_MockServletContextStub01) servlet
                .getServletContext();

        // �T�[�u���b�g�R���e�L�X�g���Ƀf�t�H���g��BLogicMapper�C���X�^���X��
        // �i�[����Ă��邱��
        assertEquals("jp.terasoluna.fw.service.thin.BLogicMapper",
                ctx.getAttribute("BLOGIC_MAPPER").getClass().getName());

        // �T�[�u���b�g�R���e�L�X�g����BLogicResources�C���X�^���X��
        // �i�[����Ă��邱��
        assertTrue(ctx.getAttribute("BLOGIC_RESOURCES") instanceof BLogicResources);

        BLogicResources resources =
            (BLogicResources) ctx.getAttribute("BLOGIC_RESOURCES");
        BLogicIO io = resources.getBLogicIO("/logon");

        //BLogicIO#inputBeanName��null�ł��邱�Ƃ��m�F�B
        assertNull(io.getInputBeanName());

        // �T�[�u���b�g�R���e�L�X�g�ɓo�^����Ă��郊�\�[�X��
        // �w�肵�����[���t�@�C���ł��邱�ƁB
        assertEquals(BLogicIOPlugInTest.class.getResource(
                "BLogicIOPlugInTest-blogic-io-rules.xml").getPath(), ctx
                .getCalledResources());
    }

    /**
     * testInit18()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) servlet:not null<br>
     *         (����) config:���W���[����(����)=""<br>
     *         (���) rules�iDigester���[���t�@�C�����j:"BLogicIOPlugInTest-blogic-io-rules.xml"<br>
     *         (���) blogic-io�i�Ɩ����W�b�N���o�͏��}�b�s���O��`�t�@�C�����j:"BLogicIOPlugInTest-blogic-io4.xml"<br>
     *                bean-name�̎w��Ȃ��B
     *         (���) mapperStr�i�Ɩ����W�b�N���o�͏�񔽉f�N���X���j:null<br>
     *         (���) digester:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ServletException<br>
     *                    ���b�v������O�F<br>
     *                    IOException<br>
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
    public void testInit18() throws Exception {
        // �e�X�g�f�[�^�ݒ�
        // ���W���[���ݒ���
        ModuleConfig config = new ModuleConfigImpl("");

        plugin = new BLogicIOPlugIn() {
            @Override
            public String getPublicIdentifier() {
            	return "";
            }
        };

        // BLogic��`���[���t�@�C����
        UTUtil.setPrivateField(plugin, "digesterRules",
                BLogicIOPlugInTest.class.getResource(
                        "BLogicIOPlugInTest-blogic-io-rules.xml").getPath());
        // BLogic��`�t�@�C����
        UTUtil.setPrivateField(plugin, "resources", BLogicIOPlugInTest.class
                .getResource("BLogicIOPlugInTest-blogic-io4.xml").getPath());
        // �}�b�p�[�N���X��
        UTUtil.setPrivateField(plugin, "mapperStr", null);

        // �A�N�V�����T�[�u���b�g�쐬
        BLogicIOPlugIn_ActionServletStub01 servlet = new BLogicIOPlugIn_ActionServletStub01();

        // �e�X�g���s
        try {
            plugin.init(servlet, config);
            fail("�e�X�g���s");
        } catch (ServletException e) {
        	assertTrue(e.getRootCause() instanceof IOException);
            assertTrue(LogUTUtil.checkError("", e.getRootCause()));
        }

    }

    /**
     * testSetDigesterRules01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) digesterRules:"digesterRules"<br>
     *         (���) digesterRules:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) digesterRules:"digesterRules"<br>
     *
     * <br>
     * ������digesterRules���������i�[����邱��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDigesterRules01() throws Exception {
        // �e�X�g���{
        plugin.setDigesterRules("digesterRules");

        // ����
        assertEquals("digesterRules", UTUtil.getPrivateField(plugin,
                "digesterRules"));
    }

    /**
     * testSetResources01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) resources:"resources"<br>
     *         (���) resources:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) resources:"resources"<br>
     *
     * <br>
     * ������resources���������i�[����邱��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetResources01() throws Exception {
        // �e�X�g���{
        plugin.setResources("resources");

        // ����
        assertEquals("resources", UTUtil.getPrivateField(plugin, "resources"));
    }

    /**
     * testSetMapperClass01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mapperStr:"mapperStr"<br>
     *         (���) mapperStr:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) mapperStr:"mapperStr"<br>
     *
     * <br>
     * ������mapperStr���������i�[����邱��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetMapperClass01() throws Exception {
        // �e�X�g���{
        plugin.setMapperClass("mapperStr");

        // ����
        assertEquals("mapperStr", UTUtil.getPrivateField(plugin, "mapperStr"));
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
