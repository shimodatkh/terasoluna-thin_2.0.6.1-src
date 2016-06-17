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

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicIO} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N���o�͏���ێ�����N���X�B<br>
 * BLogicIOPlugIn�Ńr�W�l�X���W�b�N���o�͏���`�t�@�C���ł���blogic-io.xml����A
 * �A�N�V�����p�X���Ƃ��̃A�N�V�������N�����ꂽ���̓��͏��A�o�͏���ێ�����B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 */
public class BLogicIOTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BLogicIOTest.class);
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
    public BLogicIOTest(String name) {
        super(name);
    }

    /**
     * testGetInputBeanName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) inputBeanName:"inputBeanName"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"inputBeanName"<br>
     *         
     * <br>
     * BLogicIO�Ɋi�[����Ă���inputBeanName�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetInputBeanName01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // path�ݒ�
        UTUtil.setPrivateField(blogicIO, "inputBeanName", "inputBeanName");

        // �e�X�g���s�E���ʊm�F
        assertEquals("inputBeanName", blogicIO.getInputBeanName());
    }

    /**
     * testSetInputBeanName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) inputBeanName:"inputBeanName"<br>
     *         (���) inputBeanName:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) inputBeanName:"inputBeanName"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicIO��inputBeanName�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetInputBeanName01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // �e�X�g���s
        blogicIO.setInputBeanName("inputBeanName");

        // �e�X�g���ʊm�F
        assertEquals("inputBeanName", UTUtil.getPrivateField(blogicIO,
                "inputBeanName"));
    }

    /**
     * testGetPath01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) path:"path"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"path"<br>
     *         
     * <br>
     * BLogicIO�Ɋi�[����Ă���path�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPath01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // path�ݒ�
        UTUtil.setPrivateField(blogicIO, "path", "path");

        // �e�X�g���s�E���ʊm�F
        assertEquals("path", blogicIO.getPath());
    }

    /**
     * testSetPath01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) path:"path"<br>
     *         (���) path:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) path:"path"<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicIO��path�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPath01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // �e�X�g���s
        blogicIO.setPath("path");

        // �e�X�g���ʊm�F
        assertEquals("path", UTUtil.getPrivateField(blogicIO, "path"));
    }

    /**
     * testGetBLogicParams01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) blogicParams:List<BLogicProperty>�I�u�W�F�N�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�O������Ɠ����List<BLogicProperty>�I�u�W�F�N�g<br>
     *         
     * <br>
     * BLogicIO�Ɋi�[����Ă���blogicParams�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicParams01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // blogicParams�ݒ�
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicParams", list);

        // �e�X�g���s�E���ʊm�F
        assertSame(list, blogicIO.getBLogicParams());
    }

    /**
     * testGetBLogicResults01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) blogicResults:List<BLogicProperty>�I�u�W�F�N�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:�O������Ɠ����List<BLogicProperty>�I�u�W�F�N�g<br>
     *         
     * <br>
     * BLogicIO�Ɋi�[����Ă���blogicResults�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBLogicResults01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // blogicResults�ݒ�
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicResults", list);

        // �e�X�g���s�E���ʊm�F
        assertSame(list, blogicIO.getBLogicResults());
    }

    /**
     * testSetBLogicParam01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) blogicParam:BlogicProperty[property="property"]<br>
     *         (���) blogicParams:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) blogicParams:�w�肵��BLogicProperty�C���X�^���X��1�ԖڂɊi�[����Ă���<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicIO��blogicParams�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicParam01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // BlogicProperty�쐬
        BLogicProperty blogicParam = new BLogicProperty();
        UTUtil.setPrivateField(blogicParam, "blogicProperty", "BlogicProperty");

        // �e�X�g���s
        blogicIO.setBLogicParam(blogicParam);

        // �e�X�g���ʊm�F
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicParams");
        assertEquals(1, resultList.size());
        BLogicProperty resultBlogicProperty = (BLogicProperty) resultList
                .get(0);
        assertEquals("BlogicProperty", UTUtil.getPrivateField(
                resultBlogicProperty, "blogicProperty"));
    }

    /**
     * testSetBLogicParam02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicParam:null<br>
     *         (���) blogicParams:1���ݒ肳�ꂽ���X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) blogicParams:null��2�ԖڂɊi�[����Ă���<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicIO��blogicParams�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicParam02() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // blogicParams�ݒ�
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicParams", list);
        
        // �e�X�g���s
        blogicIO.setBLogicParam(null);

        // �e�X�g���ʊm�F
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicParams");
        assertEquals(2, resultList.size());
        assertNull(((List) UTUtil.getPrivateField(blogicIO, "blogicParams"))
                .get(1));

    }

    /**
     * testSetBLogicResult01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) blogicResult:BlogicProperty[property="property"]<br>
     *         (���) blogicResult:��̃��X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) blogicResults:�w�肵��BLogicProperty�C���X�^���X��1�ԖڂɊi�[����Ă���<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicIO��blogicResults�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicResult01() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // BlogicProperty�쐬
        BLogicProperty blogicParam = new BLogicProperty();
        UTUtil.setPrivateField(blogicParam, "blogicProperty", "BlogicProperty");

        // �e�X�g���s
        blogicIO.setBLogicResult(blogicParam);

        // �e�X�g���ʊm�F
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicResults");
        assertEquals(1, resultList.size());
        BLogicProperty resultBlogicProperty = (BLogicProperty) resultList
                .get(0);
        assertEquals("BlogicProperty", UTUtil.getPrivateField(
                resultBlogicProperty, "blogicProperty"));
    }

    /**
     * testSetBLogicResult02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) blogicResult:null<br>
     *         (���) blogicResult:1���ݒ肳�ꂽ���X�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) blogicResults:null��2�ԖڂɊi�[����Ă���<br>
     *         
     * <br>
     * �����Ɏw�肵���l��BLogicIO��blogicResults�ɐ���Ɋi�[����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetBLogicResult02() throws Exception {
        // �r�W�l�X���W�b�N���o�͏��
        BLogicIO blogicIO = new BLogicIO();

        // blogicResults�ݒ�
        List<BLogicProperty> list = new ArrayList<BLogicProperty>();
        BLogicProperty blogicProperty = new BLogicProperty();
        list.add(blogicProperty);
        UTUtil.setPrivateField(blogicIO, "blogicResults", list);
        
        // �e�X�g���s
        blogicIO.setBLogicResult(null);

        // �e�X�g���ʊm�F
        List resultList = (List) UTUtil.getPrivateField(blogicIO,
                "blogicResults");
        assertEquals(2, resultList.size());
        assertNull(((List) UTUtil.getPrivateField(blogicIO, "blogicResults"))
                .get(1));

    }

}
