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

package jp.terasoluna.fw.web.struts.reset;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.struts.reset.ResetterResources} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���Z�b�g�p�̐ݒ����ێ�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.reset.ResetterResources
 */
public class ResetterResourcesTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ResetterResourcesTest.class);
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
    public ResetterResourcesTest(String name) {
        super(name);
    }

    /**
     * testSetActionReset01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) actionReset:ActionReset<br>
     *                path="path"<br>
     *         (���) actionResets:{}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) actionResets:{"path"=ActionReset}<br>
     *
     * <br>
     * �����Ɏw�肵��ActionReset��path�������L�[�Ƃ��āAactionResets�Ɉ�����actionReset���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetActionReset01() throws Exception {
        //�����ݒ�
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("path");
        //�e�X�g���s
        rr.setActionReset(ar);

        //���ʊm�F
        Map map = (Map) UTUtil.getPrivateField(rr, "actionResets");
        ActionReset obj = (ActionReset) map.get("path");
        assertEquals("path", obj.getPath());
        //�L�[�̊m�F
        assertTrue(map.containsKey("path"));
    }

    /**
     * testSetActionReset02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) actionReset:ActionReset<br>
     *                path=null<br>
     *         (���) actionResets:{}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) actionResets:{null=ActionReset}<br>
     *
     * <br>
     * �����Ɏw�肵��ActionReset��path�������L�[�Ƃ��āAactionResets�Ɉ�����actionReset���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetActionReset02() throws Exception {
        //�����ݒ�
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath(null);
        //�e�X�g���s
        rr.setActionReset(ar);
        //���ʊm�F
        Map map = (Map) UTUtil.getPrivateField(rr, "actionResets");
        //�L�[�̊m�F
        assertTrue(map.containsKey(null));
        ActionReset obj = (ActionReset) map.get(null);
        assertNull(obj.getPath());
    }

    /**
     * testSetActionReset03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC,F
     * <br><br>
     * ���͒l�F(����) actionReset:ActionReset<br>
     *                path=""<br>
     *         (���) actionResets:{}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) actionResets:{""=ActionReset}<br>
     *
     * <br>
     * �����Ɏw�肵��ActionReset��path�������L�[�Ƃ��āAactionResets�Ɉ�����actionReset���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetActionReset03() throws Exception {
        //�����ݒ�
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("");
        //�e�X�g���s
        rr.setActionReset(ar);
        //���ʊm�F
        Map map = (Map) UTUtil.getPrivateField(rr, "actionResets");
        //�L�[�̊m�F
        assertTrue(map.containsKey(""));
        ActionReset obj = (ActionReset) map.get("");
        assertEquals("", obj.getPath());
    }

    /**
     * testGet01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) path:"path"<br>
     *         (���) actionResets:{"path"=ActionReset}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionReset:ActonReset<br>
     *                   path="path"<br>
     *
     * <br>
     * �����Ɏw�肵���l���L�[�Ƃ���actionResets��ActionReset�����݂���ꍇ�A����ActionReset�C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet01() throws Exception {
        //�����ݒ�
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("path");

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //�e�X�g���s
        ActionReset result = rr.get("path");
        //���ʊm�F
        assertEquals("path", result.getPath());
    }

    /**
     * testGet02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) path:"path0"<br>
     *         (���) actionResets:{"path"=ActionReset}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionReset:null<br>
     *
     * <br>
     * �����Ɏw�肵���l���L�[�Ƃ���actionResets��ActionReset�����݂��Ȃ��ꍇ�Anull���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet02() throws Exception {
        //�����ݒ�
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("path");

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //�e�X�g���s
        //���ʊm�F
        assertNull(rr.get("path0"));
    }

    /**
     * testGet03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) path:null<br>
     *         (���) actionResets:{null=ActionReset}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionReset:ActionReset<br>
     *                   path=null<br>
     *
     * <br>
     * �����Ɏw�肵���l���L�[�Ƃ���actionResets��ActionReset�����݂���ꍇ�A����ActionReset�C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet03() throws Exception {
        //�����ݒ�
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath(null);

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //�e�X�g���s
        ActionReset result = rr.get(null);
        //���ʊm�F
        assertNull(result.getPath());
    }

    /**
     * testGet04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) path:""<br>
     *         (���) actionResets:{""=ActionReset}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) ActionReset:ActionReset<br>
     *                   path=""<br>
     *
     * <br>
     * �����Ɏw�肵���l���L�[�Ƃ���actionResets��ActionReset�����݂���ꍇ�A����ActionReset�C���X�^���X���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGet04() throws Exception {
        //�����ݒ�
        ResetterResources rr = new ResetterResources();
        ActionReset ar = new ActionReset();
        ar.setPath("");

        Map<String, ActionReset> actionResets = new HashMap<String, ActionReset>();
        actionResets.put(ar.getPath(), ar);

        UTUtil.setPrivateField(rr, "actionResets", actionResets);

        //�e�X�g���s
        ActionReset result = rr.get("");
        //���ʊm�F
        assertEquals("", result.getPath());
    }

}
