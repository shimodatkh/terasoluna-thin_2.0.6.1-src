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

package jp.terasoluna.fw.web.struts.action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.struts.action.GlobalMessageResources} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �Ɩ����ʁA�V�X�e���̃��b�Z�[�W���\�[�X�𐶐�����N���X�B
 * private�ȃR���X�g���N�^�����Ȃ����߁A�e�X�g�Ώۃ��X�^���X�̐�����getInstance()��p���Ă���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.GlobalMessageResources
 */
@SuppressWarnings("unchecked")
public class GlobalMessageResourcesTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(GlobalMessageResourcesTest.class);
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public GlobalMessageResourcesTest(String name) {
        super(name);
    }

    /**
     * testGetInstance01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) globalMessageResources:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) GlobalMessageResources:not null<br>
     *         (��ԕω�) globalMessageResources:�Ԃ��ꂽ�l�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * ���\�b�h���Ăяo�������ƂŃt�B�[���h�ɖ߂�l�Ɠ����l��
     * �ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInstance01() throws Exception {
        // �O����
        // �V���O���g���C���X�^���X��null��ݒ�
        UTUtil.setPrivateField(GlobalMessageResources.class,
                               "globalMessageResources", null);
            
        // �e�X�g���{
        Object result = 
            GlobalMessageResources.getInstance();
        
        // ����
        assertEquals(GlobalMessageResources.class.getName(),
                     result.getClass().getName());
        assertSame(result, UTUtil.getPrivateField(GlobalMessageResources.class,
            "globalMessageResources"));
    }

    /**
     * testGetInstance02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) globalMessageResources:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) GlobalMessageResources:�O������Ɠ���̃C���X�^���X<br>
     *         (��ԕω�) globalMessageResources:�O������Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �t�B�[���h�ɐݒ肳��Ă���C���X�^���X�Ɠ���̃C���X�^���X��
     * �Ԃ���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInstance02() throws Exception {
        // �O����
        Object prepare = GlobalMessageResources.getInstance();
        UTUtil.setPrivateField(GlobalMessageResources.class,
                "globalMessageResources", prepare);

        // �e�X�g���{
        Object result = 
            GlobalMessageResources.getInstance();

        // ����
        assertSame(prepare, result);
    }

    /**
     * testGlobalInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) config:"aaaaa"<br>
     *                �i���݂��Ȃ��t�@�C�����j<br>
     *         (���) fwMessages:["key"->"value"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) fwMessages:���Map<br>
     *         
     * <br>
     * �v���p�e�B������Ɏ擾�ł��Ȃ��ꍇ�A�V�X�e���̃��b�Z�[�W��
     * �N���A����A��ɂȂ��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGlobalInit01() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        UTUtil.setPrivateField(resources, "config", "aaaaa");
        
        Map fwMap = new HashMap();
        fwMap.put("key", "value");
        UTUtil.setPrivateField(resources, "fwMessages", fwMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "globalInit");
        
        // ����
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertTrue(resultMap.isEmpty());
    }

    /**
     * testGlobalInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) config:"GlobalMessageResources_system-messages01"<br>
     *                �i��̃t�@�C���j<br>
     *         (���) fwMessages:���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) fwMessages:���Map<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C�����Őݒ肳�ꂽ�v���p�e�B��0���̎��A
     * �V�X�e���̃��b�Z�[�W����ł��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGlobalInit02() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // config��0���v���p�e�B�t�@�C������ݒ�
        UTUtil.setPrivateField(resources, "config",
                GlobalMessageResources.class.getPackage().getName().replace(
                        '.', '/')
                        + "/GlobalMessageResources_system-messages01");
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "globalInit");
        
        // ����
        // �V�X�e�����b�Z�[�W��0���ł��邱�ƁB
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertTrue(resultMap.isEmpty());
    }

    /**
     * testGlobalInit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) config:"GlobalMessageResources_system-messages02"<br>
     *                �y�t�@�C���̓��e�z<br>
     *                test.fw.message.01=�e�v�e�X�g���b�Z�[�W�O�P<br>
     *         (���) fwMessages:���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) fwMessages:["test.fw.messages.01"->"�e�v�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C�����Őݒ肳�ꂽ�v���p�e�B��1���̎��A
     * �V�X�e���̃��b�Z�[�W���ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGlobalInit03() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // config��1���v���p�e�B�t�@�C������ݒ�
        UTUtil.setPrivateField(resources, "config",
                GlobalMessageResources.class.getPackage().getName().replace(
                        '.', '/')
                        + "/GlobalMessageResources_system-messages02");
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "globalInit");
        
        // ����
        // �V�X�e�����b�Z�[�W��1���ł��邱�ƁB
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertEquals(1, resultMap.size());
        assertEquals("�e�v�e�X�g���b�Z�[�W�O�P",
                     resultMap.get("test.fw.message.01"));
    }

    /**
     * testGlobalInit04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) config:"GlobalMessageResources_system-messages03"<br>
     *                �y�t�@�C���̓��e�z<br>
     *                test.fw.message.01=�e�v�e�X�g���b�Z�[�W�O�P<br>
     *                test.fw.message.02=�e�v�e�X�g���b�Z�[�W�O�Q<br>
     *                test.fw.message.03=�e�v�e�X�g���b�Z�[�W�O�R<br>
     *         (���) fwMessages:���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) fwMessages:["test.fw.messages.01"->"�e�v�e�X�g���b�Z�[�W�O�P"]<br>
     *                    ["test.fw.messages.02"->"�e�v�e�X�g���b�Z�[�W�O�Q"]<br>
     *                    ["test.fw.messages.03"->"�e�v�e�X�g���b�Z�[�W�O�R"]<br>
     *         
     * <br>
     * �v���p�e�B�t�@�C�����Őݒ肳�ꂽ�v���p�e�B��3���̎��A
     * �V�X�e���̃��b�Z�[�W���ݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGlobalInit04() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // config��3���v���p�e�B�t�@�C������ݒ�
        UTUtil.setPrivateField(resources, "config",
                GlobalMessageResources.class.getPackage().getName().replace(
                        '.', '/')
                        + "/GlobalMessageResources_system-messages03");
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "globalInit");
        
        // ����
        // �V�X�e�����b�Z�[�W��3���ł��邱�ƁB
        Map resultMap = (Map) UTUtil.getPrivateField(resources, "fwMessages");
        assertEquals(3, resultMap.size());

        assertEquals("�e�v�e�X�g���b�Z�[�W�O�P", resultMap.get("test.fw.message.01"));
        assertEquals("�e�v�e�X�g���b�Z�[�W�O�Q", resultMap.get("test.fw.message.02"));
        assertEquals("�e�v�e�X�g���b�Z�[�W�O�R", resultMap.get("test.fw.message.03"));
    }

    /**
     * testApplicationInit01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):null<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *         (���) globalMessages:���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"->"�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * PropertyUtil.getProperty("application.messages")��null�̎��A
     * application-messages.properties�����[�h����A���[�g�̋Ɩ����ʃ��b�Z�[�W�Ƃ��Đݒ肳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit01() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        deleteProperty("application.messages");
        UTUtil.setPrivateField(resources, "globalMessages", new HashMap());
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        // application-resources.properties�����[�h����A
        // app.resource=���\�[�X ���擾�ł��邱�ƁB
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.01"));
    }

    /**
     * testApplicationInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages01"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                test.gb.message.02=�f�a�e�X�g���b�Z�[�W�O�Q<br>
     *                test.gb.message.03=�f�a�e�X�g���b�Z�[�W�O�R<br>
     *         (���) globalMessages:���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"->"�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *                    ["test.gb.message.02"->"�f�a�e�X�g���b�Z�[�W�O�Q"]<br>
     *                    ["test.gb.message.03"->"�f�a�e�X�g���b�Z�[�W�O�R"]<br>
     *         
     * <br>
     * application.messages�L�[��"GlobalMessageResources_application-messages01"
     * �̎��Aapplication-messages01.properties�����[�h����A
     * �Ɩ����ʃ��b�Z�[�W�Ƃ��Đݒ肳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit02() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages01");
        UTUtil.setPrivateField(resources, "globalMessages", new HashMap());
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.01"));
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�Q", appMap.get("test.gb.message.02"));
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�R", appMap.get("test.gb.message.03"));
    }

    /**
     * testApplicationInit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"aaaaa"<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *                ["test.gb.message.02"->"�����̂f�a�e�X�g���b�Z�[�W�O�Q"]<br>
     *                ["test.gb.message.03"->"�����̂f�a�e�X�g���b�Z�[�W�O�R"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *                    ["test.gb.message.02"->"�����̂f�a�e�X�g���b�Z�[�W�O�Q"]<br>
     *                    ["test.gb.message.03"->"�����̂f�a�e�X�g���b�Z�[�W�O�R"]<br>
     *         
     * <br>
     * application.messages�L�[�œo�^����Ă���l�����݂��Ȃ��t�@�C���̎��A
     * �Ɩ����ʃ��b�Z�[�W�͓o�^���ꂸ�A�܂��A�N���A������Ȃ����ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit03() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", "jp/terasoluna/fw/web/struts/action/aaaaa");
        
        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        defaultMap.put("test.gb.message.02", "�����̂f�a�e�X�g���b�Z�[�W�O�Q");
        defaultMap.put("test.gb.message.03", "�����̂f�a�e�X�g���b�Z�[�W�O�R");
        
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertSame(defaultMap, appMap);
        assertEquals(3, appMap.size());
        assertEquals("�����̂f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.01"));
        assertEquals("�����̂f�a�e�X�g���b�Z�[�W�O�Q", appMap.get("test.gb.message.02"));
        assertEquals("�����̂f�a�e�X�g���b�Z�[�W�O�R", appMap.get("test.gb.message.03"));
    }

    /**
     * testApplicationInit04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages02"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:��̃t�@�C��<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *                ["test.gb.message.02"->"�����̂f�a�e�X�g���b�Z�[�W�O�Q"]<br>
     *                ["test.gb.message.03"->"�����̂f�a�e�X�g���b�Z�[�W�O�R"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:���Map<br>
     *         
     * <br>
     * 0���̃��b�Z�[�W���i�[���ꂽ�v���p�e�B�t�@�C�����w�肵�����A
     * �����̃��b�Z�[�W�́A0���̃��b�Z�[�W�ŏ㏑������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit04() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages02");
        
        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        defaultMap.put("test.gb.message.02", "�����̂f�a�e�X�g���b�Z�[�W�O�Q");
        defaultMap.put("test.gb.message.03", "�����̂f�a�e�X�g���b�Z�[�W�O�R");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertTrue(appMap.isEmpty());
    }

    /**
     * testApplicationInit05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages01"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                test.gb.message.02=�f�a�e�X�g���b�Z�[�W�O�Q<br>
     *                test.gb.message.03=�f�a�e�X�g���b�Z�[�W�O�R<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"->"�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *                    ["test.gb.message.02"->"�f�a�e�X�g���b�Z�[�W�O�Q"]<br>
     *                    ["test.gb.message.03"->"�f�a�e�X�g���b�Z�[�W�O�R"]<br>
     *         
     * <br>
     * 3���̃��b�Z�[�W���i�[���ꂽ�v���p�e�B�t�@�C�����w�肵�����A
     * �����̃��b�Z�[�W�́A3���̃��b�Z�[�W�ŏ㏑������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit05() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages01");
        
        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(3, appMap.size());
        
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.01"));
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�Q", appMap.get("test.gb.message.02"));
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�R", appMap.get("test.gb.message.03"));
    }

    /**
     * testApplicationInit06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages04"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                add.message.file.1=GlobalMessageResources_application-messages04_add<br>
     *         (���) �ǉ��p�̋Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��:test.gb.message.addition=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�P<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"->"�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *                    ["test.gb.message.addition"->"�ǉ��p�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * �Ɩ����ʂ̃��[�g�v���p�e�B�t�@�C�����A1���̃��b�Z�[�W�t�@�C����
     * �Ăяo���Ă���A�L�[���Փ˂��Ă��Ȃ��ꍇ�́A
     * 2���̃��b�Z�[�W���o�^����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit06() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages04");
        
        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(2, appMap.size());
        
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.01"));
        assertEquals("�ǉ��p�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.addition"));
    }

    /**
     * testApplicationInit07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages05"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                add.message.file.1=GlobalMessageResources_application-messages05_add<br>
     *         (���) �ǉ��p�̋Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��:test.gb.message.01=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�P<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"->"�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * �Ɩ����ʂ̃��[�g�v���p�e�B�t�@�C�����A1���̃��b�Z�[�W�t�@�C����
     * �Ăяo���Ă���A�L�[���Փ˂��Ă���ꍇ�́A���[�g�̃��b�Z�[�W��
     * �̗p����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit07() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages05");
        
        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        // 1���̃v���p�e�B�Ŋ����̋Ɩ����ʃ��b�Z�[�W���\�[�X���X�V����邱�ƁB
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(1, appMap.size());
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", 
                     appMap.get("test.gb.message.01"));
    }

    /**
     * testApplicationInit08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages06"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                add.message.file.1=GlobalMessageResources_application-messages06<br>
     *         (���) �ǉ��p�̋Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��:���Ɠ���̃t�@�C���̂��ߏȗ�<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"->"�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * �Ɩ����ʂ̃��[�g�v���p�e�B�t�@�C�����A���[�g�v���p�e�B�t�@�C�����g��
     * �Ăяo���Ă���ꍇ�́A�ǂݔ�΂���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit08() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages06");

        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil
            .getPrivateField(resources, "globalMessages");
        assertEquals(1, appMap.size());
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", 
                     appMap.get("test.gb.message.01"));
    }

    /**
     * testApplicationInit09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages07"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                test.gb.message.02=�f�a�e�X�g���b�Z�[�W�O�Q<br>
     *                test.gb.message.03=�f�a�e�X�g���b�Z�[�W�O�R<br>
     *                add.message.file.1=GlobalMessageResources_application-messages07_add1<br>
     *                add.message.file.2=GlobalMessageResources_application-messages07_add2<br>
     *                add.message.file.3=GlobalMessageResources_application-messages07_add3<br>
     *         (���) �ǉ��p�̋Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��:�yGlobalMessageResources_application-messages07_add1�z<br>
     *                test.gb.message.addition.01=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                �yGlobalMessageResources_application-messages07_add2�z<br>
     *                test.gb.message.addition.02=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�Q<br>
     *                �yGlobalMessageResources_application-messages07_add3�z<br>
     *                test.gb.message.addition.03=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�R<br>
     *                test.gb.message.addition.04=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�S<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                    test.gb.message.02=�f�a�e�X�g���b�Z�[�W�O�Q<br>
     *                    test.gb.message.03=�f�a�e�X�g���b�Z�[�W�O�R<br>
     *                    test.gb.message.addition.01=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                    test.gb.message.addition.02=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�Q<br>
     *                    test.gb.message.addition.03=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�R<br>
     *                    test.gb.message.addition.04=�ǉ��p�f�a�e�X�g���b�Z�[�W�O�S<br>
     *         
     * <br>
     * �����̊O���t�@�C���A�����̃��[�g���b�Z�[�W���v7���擾���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit09() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages07");

        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertEquals(7, appMap.size());

        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.01"));
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�Q", appMap.get("test.gb.message.02"));
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�R", appMap.get("test.gb.message.03"));
        assertEquals("�ǉ��p�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.addition.01"));
        assertEquals("�ǉ��p�f�a�e�X�g���b�Z�[�W�O�Q", appMap.get("test.gb.message.addition.02"));
        assertEquals("�ǉ��p�f�a�e�X�g���b�Z�[�W�O�R", appMap.get("test.gb.message.addition.03"));
        assertEquals("�ǉ��p�f�a�e�X�g���b�Z�[�W�O�S", appMap.get("test.gb.message.addition.04"));
    }

    /**
     * testApplicationInit10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) PropertyUtil.getProperty("application.messages"):"GlobalMessageResources_application-messages03"<br>
     *         (���) �Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C���iapplication-messages.properties�j:test.gb.message.01=�f�a�e�X�g���b�Z�[�W�O�P<br>
     *                add.message.file.1=aaaaa<br>
     *         (���) �ǉ��p�̋Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��:���݂��Ȃ�<br>
     *         (���) globalMessages:["test.gb.message.01"->"�����̂f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) globalMessages:["test.gb.message.01"="�f�a�e�X�g���b�Z�[�W�O�P"]<br>
     *         (��ԕω�) ���O:�y�x�����O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "aaaaa" is illegal."<br>
     *         
     * <br>
     * �ǉ��p�̃t�@�C�������s���������ꍇ�A�x�����O���o���A
     * ���̂ق��̋Ɩ����ʃ��b�Z�[�W���\�[�X��`�t�@�C��
     * �iapplication-messages.properties�j�ɐݒ肳��Ă��郁�b�Z�[�W���\�[�X��
     * ����ɐݒ肳��Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testApplicationInit10() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();

        // �v���p�e�B������������B
        deleteProperty("application.messages");
        addProperty("application.messages", 
                    "jp/terasoluna/fw/web/struts/action/GlobalMessageResources_application-messages03");

        // �����̋Ɩ����ʃ��b�Z�[�W��ݒ�
        Map defaultMap = new HashMap();
        defaultMap.put("test.gb.message.01", "�����̂f�a�e�X�g���b�Z�[�W�O�P");
        UTUtil.setPrivateField(resources, "globalMessages", defaultMap);
        
        // �e�X�g���{
        UTUtil.invokePrivate(resources, "applicationInit");

        // ����
        Map appMap = (Map) UTUtil.getPrivateField(resources, "globalMessages");
        assertEquals(1, appMap.size());
        assertEquals("�f�a�e�X�g���b�Z�[�W�O�P", appMap.get("test.gb.message.01"));
        assertTrue(LogUTUtil.checkWarn("\"aaaaa\" is illegal."));
    }

    /**
     * testGetMessage01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) key:"message"<br>
     *         (���) fwMessages:���Map<br>
     *         (���) globalMessages:���Map<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * �V�X�e���̃��b�Z�[�W�A�Ɩ����ʃ��b�Z�[�W�����ɋ��Map�̎��A
     * null���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage01() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // �V�X�e�����b�Z�[�W�ݒ�
        UTUtil.setPrivateField(resources, "fwMessages", new HashMap());
        // �Ɩ����ʃ��b�Z�[�W�ݒ�
        UTUtil.setPrivateField(resources, "globalMessages", new HashMap());

        // �e�X�g���{
        // ����
        Locale locale = null;
        assertNull(resources.getMessage(locale, "message"));
    }

    /**
     * testGetMessage02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"message.fw"<br>
     *         (���) fwMessages:["message.fw"->"�e�v���b�Z�[�W"]<br>
     *         (���) globalMessages:["message.gb"->"�Ɩ����b�Z�[�W"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�e�v���b�Z�[�W"<br>
     *         
     * <br>
     * �V�X�e�����b�Z�[�W�ɊY�����郁�b�Z�[�W�����݂��鎞�A
     * ���b�Z�[�W��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage02() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // �V�X�e�����b�Z�[�W�ݒ�
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message.fw", "�e�v���b�Z�[�W");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // �Ɩ����ʃ��b�Z�[�W�ݒ�
        Map globalMessageMap = new HashMap();
        fwMessagesMap.put("message.gb", "�Ɩ����b�Z�[�W");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // �e�X�g���{
        String result = resources.getMessage(Locale.getDefault(), "message.fw");

        // ����
        // �V�X�e�����b�Z�[�W���擾����Ă��邱��
        assertEquals("�e�v���b�Z�[�W", result);
    }

    /**
     * testGetMessage03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"message.gb"<br>
     *         (���) fwMessages:["message.fw"->"�e�v���b�Z�[�W"]<br>
     *         (���) globalMessages:["message.gb"->"�Ɩ����b�Z�[�W"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�Ɩ����b�Z�[�W"<br>
     *         
     * <br>
     * �Ɩ����b�Z�[�W�ɊY�����郁�b�Z�[�W�����݂��鎞�A
     * ���b�Z�[�W��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage03() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // �V�X�e�����b�Z�[�W�ݒ�
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message.fw", "�e�v���b�Z�[�W");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // �Ɩ����ʃ��b�Z�[�W�ݒ�
        Map globalMessageMap = new HashMap();
        globalMessageMap.put("message.gb", "�Ɩ����b�Z�[�W");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // �e�X�g���{
        Locale locale = null;
        String result = resources.getMessage(locale, "message.gb");

        // ����
        // �Ɩ����b�Z�[�W���擾����Ă��邱��
        assertEquals("�Ɩ����b�Z�[�W", result);
    }

    /**
     * testGetMessage04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"message"<br>
     *         (���) fwMessages:["message"->"�e�v���b�Z�[�W"]<br>
     *         (���) globalMessages:["message"->"�Ɩ����b�Z�[�W"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"�Ɩ����b�Z�[�W"<br>
     *         
     * <br>
     * �w�肳�ꂽ���b�Z�[�W�L�[���A�V�X�e���ƋƖ����ʂ̑o���ɑ��݂��鎞�A
     * �Ɩ����b�Z�[�W���ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage04() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // �V�X�e�����b�Z�[�W�ݒ�
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message", "�e�v���b�Z�[�W");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // �Ɩ����ʃ��b�Z�[�W�ݒ�
        Map globalMessageMap = new HashMap();
        fwMessagesMap.put("message", "�Ɩ����b�Z�[�W");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // �e�X�g���{
        Locale locale = null;
        String result = resources.getMessage(locale, "message");

        // ����
        // �Ɩ����b�Z�[�W���擾����Ă��邱��
        assertEquals("�Ɩ����b�Z�[�W", result);
    }

    /**
     * testGetMessage05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) key:null<br>
     *         (���) fwMessages:["message.fw"->"�e�v���b�Z�[�W"]<br>
     *         (���) globalMessages:["message.gb"->"�Ɩ����b�Z�[�W"]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * �w�肳�ꂽ���b�Z�[�W�L�[��null�̎��Anull��ԋp���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetMessage05() throws Exception {
        // �O����
        GlobalMessageResources resources = GlobalMessageResources.getInstance();
        // �V�X�e�����b�Z�[�W�ݒ�
        Map fwMessagesMap = new HashMap();
        fwMessagesMap.put("message.fw", "�e�v���b�Z�[�W");
        UTUtil.setPrivateField(resources, "fwMessages", fwMessagesMap);

        // �Ɩ����ʃ��b�Z�[�W�ݒ�
        Map globalMessageMap = new HashMap();
        globalMessageMap.put("message.gb", "�Ɩ����b�Z�[�W");
        UTUtil.setPrivateField(resources, "globalMessages", globalMessageMap);

        // �e�X�g���{
        // ����
        Locale locale = null;
        assertNull(resources.getMessage(locale, null));
    }

    /**
     * �V���O���g���C���X�^���X��������Ԃɂ���B
     * 
     */
    @Override
    protected void setUpData() throws Exception {

        UTUtil.setPrivateField(GlobalMessageResources.class,
                               "globalMessageResources",
                               null);
        
    }

    /**
     * �V���O���g���C���X�^���X��������Ԃɖ߂��B
     */
    @Override
    protected void cleanUpData() throws Exception {

        UTUtil.setPrivateField(GlobalMessageResources.class,
                "globalMessageResources",
                null);
        
    }

}
