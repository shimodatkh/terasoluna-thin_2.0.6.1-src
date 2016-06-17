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

package jp.terasoluna.fw.service.thin;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicResources} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N���o�͏���ێ�����N���X�B<br>
 * blogic-io.xml����ǂݍ��܂ꂽ�ݒ���͂��̃N���X���e�ƂȂ�ێ�����B
 * �X�̐ݒ����BLogicIO�ABLogicProperty�̃C���X�^���X�Ƃ��ĕێ�����B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 */
public class BLogicResourcesTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicResourcesTest.class);
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
    public BLogicResourcesTest(String name) {
        super(name);
    }

    /**
     * testGetBLogicIO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) path:"path"<br>
     *         (���) bLogicIO:key�F"path"�Avalue�FBLogicIO�i[path="path"]�j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicIO:[path="path"]<br>
     *         
     * <br>
     * BLogicResources�Ɋi�[����Ă���blogicIO����w�肳�ꂽKey�̓��e�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO01() throws Exception {
        //�r�W�l�X���W�b�N���̕ێ�
        BLogicResources blogicResources = new BLogicResources();

        //�e�X�g�l�ݒ�
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");
        map.put("path", bLogicIO);

        //blogicIO�ݒ�
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);

        //�e�X�g���s�E���ʊm�F
        BLogicIO resultBLogicIO = blogicResources.getBLogicIO("path");
        assertEquals("path", UTUtil.getPrivateField(resultBLogicIO, "path"));
    }

    /**
     * testGetBLogicIO02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) path:"path"<br>
     *         (���) bLogicIO:key�F"path"�Avalue�Fnull<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicIO:null<br>
     *         
     * <br>
     * BLogicResources�Ɋi�[����Ă���blogicIO����w�肳�ꂽKey�̓��e��null�̏ꍇ�A
     * null�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO02() throws Exception {
        //�r�W�l�X���W�b�N���̕ێ�
        BLogicResources blogicResources = new BLogicResources();

        //�e�X�g�l�ݒ�
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        map.put("path", null);

        //blogicIO�ݒ�
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);

        //�e�X�g���s�E���ʊm�F
        assertNull(blogicResources.getBLogicIO("path"));
    }

    /**
     * testGetBLogicIO03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) path:null<br>
     *         (���) bLogicIO:key�F"path"�Avalue�FBLogicIO�i[path="path"]�j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicIO:null<br>
     *         
     * <br>
     * ������null�̏ꍇ�A����ɏI�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO03() throws Exception {
        //�r�W�l�X���W�b�N���̕ێ�
        BLogicResources blogicResources = new BLogicResources();

        //�e�X�g�l�ݒ�
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");
        map.put("path", bLogicIO);

        //blogicIO�ݒ�
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);
        
        //�e�X�g���s�E���ʊm�F
        assertNull(blogicResources.getBLogicIO(null));
    }

    /**
     * testGetBLogicIO04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) path:""<br>
     *         (���) bLogicIO:key�F"path"�Avalue�FBLogicIO�i[path="path"]�j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BLogicIO:null<br>
     *         
     * <br>
     * �������󕶎��̏ꍇ�A����ɏI�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicIO04() throws Exception {
        //�r�W�l�X���W�b�N���̕ێ�
        BLogicResources blogicResources = new BLogicResources();
        
        //�e�X�g�l�ݒ�
        Map<String, BLogicIO> map = new HashMap<String, BLogicIO>();
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");
        map.put("path", bLogicIO);

        //blogicIO�ݒ�
        UTUtil.setPrivateField(blogicResources, "blogicIO", map);

        //�e�X�g���s�E���ʊm�F
        assertNull(blogicResources.getBLogicIO(""));
    }

    /**
     * testSetBLogicIO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) blogicIO:not null�i[path="path"])<br>
     *         (���) blogicIO:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) blogicIO:key�F"path"�Avalue�FBLogicIO([path="path"])���ݒ肳���B<br>
     *         
     * <br>
     * �����Ɏw�肵��BlogicIO��path��"path"�̎��ABLogicResources��blogicIO��Key��"path"�Ƃ��Đ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicIO01() throws Exception {
        //�r�W�l�X���W�b�N���̕ێ�
        BLogicResources blogicResources = new BLogicResources();

        //�e�X�g�l�ݒ�
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", "path");

        //�e�X�g���s
        blogicResources.setBLogicIO(bLogicIO);

        //�e�X�g���ʊm�F
        BLogicIO resultBLogicIO =
            (BLogicIO)
                ((Map) UTUtil.getPrivateField(blogicResources, "blogicIO")).get(
                "path");
        assertEquals("path", UTUtil.getPrivateField(resultBLogicIO, "path"));
    }

    /**
     * testSetBLogicIO02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicIO:not null�i[path=null])<br>
     *         (���) blogicIO:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) blogicIO:key�Fnull�ABLogicIO([path=null])���ݒ肳���B<br>
     *         
     * <br>
     * �����Ɏw�肵��BlogicIO��path��null�̎��ABLogicResources��blogicIO��Key��null�Ƃ��Đ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicIO02() throws Exception {
        //�r�W�l�X���W�b�N���̕ێ�
        BLogicResources blogicResources = new BLogicResources();

        //�e�X�g�l�ݒ�
        BLogicIO bLogicIO = new BLogicIO();
        UTUtil.setPrivateField(bLogicIO, "path", null);

        //�e�X�g���s
        blogicResources.setBLogicIO(bLogicIO);

        //�e�X�g���ʊm�F
        Map resultMap = (Map) UTUtil.getPrivateField(blogicResources,
                "blogicIO");
        assertTrue(resultMap.containsKey(null));

        BLogicIO resultBLogicIO = (BLogicIO) resultMap.get(null);
        assertNull(UTUtil.getPrivateField(resultBLogicIO, "path"));
    }

    /**
     * testSetBLogicIO03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC�AG
     * <br><br>
     * ���͒l�F(����) blogicIO:null<br>
     *         (���) blogicIO:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:NullPointerException<br>
     *         (��ԕω�) blogicIO:�|<br>
     *         
     * <br>
     * ������null�̏ꍇ�ANullPointerException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicIO03() throws Exception {
        //�r�W�l�X���W�b�N���̕ێ�
        BLogicResources blogicResources = new BLogicResources();

        //�e�X�g���s
        try {
            blogicResources.setBLogicIO(null);
            fail();
        } catch (NullPointerException e) {
            // �e�X�g���ʊm�F
        	return;
        }
    }

}
