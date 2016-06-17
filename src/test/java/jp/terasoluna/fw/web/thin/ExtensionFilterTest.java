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

package jp.terasoluna.fw.web.thin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * {@link jp.terasoluna.fw.web.thin.ExtentionFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �g���q�`�F�b�N���s���B<br>
 * �w�肳�ꂽ�֎~�g���q�����p�X�ւ̃A�N�Z�X�v���ɑ΂��ẮASC_NOT_FOUND(404)�G���[��Ԃ��B����ɂ��A�t�@�C���ւ̒��ڃA�N�Z�X���֎~����B�֎~�g���q�ւ̃A�N�Z�X�������s���ꍇ�ł��̃`�F�b�N�Ώۂ���͂����������ʂȃp�X������΁A�v���p�e�B�t�@�C����restrictionEscape.�Ƃ����v���t�B�N�X�������������L�[�Ƃ��Ē�`���邱�ƂŃ`�F�b�N��Ώۂ̃p�X��1���畡����`�ł���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.ExtensionFilter
 */
public class ExtensionFilterTest extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ExtensionFilterTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        
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
    public ExtensionFilterTest(String name) {
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
     * ���͒l�F(����) config:not null<br>
     *         (���) �v���p�e�B�t�@�C���̊֘A����v���p�e�B:access.control.prohibited.extension.1=.test1<br>
     *                restrictedEscape.1=./test/test1<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prohibitedExtensionList:".test1"<br>
     *         (��ԕω�) restrictionEscapePaths:"./test/test1"<br>
     *         
     * <br>
     * �P�j�v���p�e�B��access.control.prohibited.extension.n��1������ɐݒ肳��Ă���ꍇ�AprohibitedExtensionList�ɐݒ�l���ǉ�����邱�Ƃ��m�F����B<br>
     * �Q�j�v���p�e�B��restrictionEscape.n��1������ɐݒ肳��Ă���ꍇ�ArestrictionEscapePaths�ɐݒ�l���ǉ�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �O����
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("restrictionEscape.1", "./test/test1");

        // �e�X�g���{
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // ����
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(
                filter, 
                "prohibitedExtensionList");
        
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(
                filter, 
                "restrictionEscapePaths");
        
        // prohibitedExtensionList���m�F
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".test1", prohibitedExtensionList.get(0));
        // restrictionEscapePaths���m�F
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("./test/test1", restrictionEscapePaths.get(0));        
        
        
    }

    /**
     * testInit02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) �v���p�e�B�t�@�C���̊֘A����v���p�e�B:access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.2=.test2<br>
     *                access.control.prohibited.extension.3=.test3<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.2=./test/test2<br>
     *                restrictedEscape.3=./test/test3<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prohibitedExtensionList:".test1"<br>
     *                    ".test2"<br>
     *                    ".test3<br>
     *         (��ԕω�) restrictionEscapePaths:"./test/test1"<br>
     *                    "./test/test2"<br>
     *                    "./test/test3"<br>
     *         
     * <br>
     * �P�j�v���p�e�B��access.control.prohibited.extension.n������������ɐݒ肳��Ă���ꍇ�AprohibitedExtensionList�ɐݒ�l���ǉ�����邱�Ƃ��m�F����B<br>
     * �Q�j�v���p�e�B��access.control.prohibited.extension.n��"."����n�܂�Ȃ��������ݒ肳��Ă���ꍇ�AprohibitedExtensionList��"." + �ݒ�l���ǉ�����邱�Ƃ��m�F����B<br>
     * �R�j�v���p�e�B��restrictionEscape.n������������ɐݒ肳��Ă���ꍇ�ArestrictionEscapePaths�ɐݒ�l���ǉ�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �O����
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.2", "test2");
        addProperty("access.control.prohibited.extension.3", ".test3");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.2", "./test/test2");
        addProperty("restrictionEscape.3", "./test/test3");

        // �e�X�g���{
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // ����
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(filter, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(filter, "restrictionEscapePaths");
        // prohibitedExtensionList���m�F
        assertEquals(3, prohibitedExtensionList.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(".test" + (i + 1), prohibitedExtensionList.get(i));
        }
        
        // restrictionEscapePaths���m�F
        assertEquals(3, restrictionEscapePaths.size());
        for (int i = 0; i < 3; i++) {
            assertEquals("./test/test" + (i + 1), restrictionEscapePaths.get(i));
        }
    }

    /**
     * testInit03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) �v���p�e�B�t�@�C���̊֘A����v���p�e�B:access.control.prohibited.extension.1=<br>
     *                restrictedEscape.1=<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prohibitedExtensionList:"."<br>
     *         (��ԕω�) restrictionEscapePaths:""<br>
     *         
     * <br>
     * �P�j�v���p�e�B��access.control.prohibited.extension.n�ɋ󕶎��񂪐ݒ肳��Ă���ꍇ�AprohibitedExtensionList��"."���ǉ�����邱�Ƃ��m�F����B<br>
     * �Q�j�v���p�e�B��restrictionEscape.n�ɋ󕶎��񂪐ݒ肳��Ă���ꍇ�ArestrictionEscapePaths�ɋ󕶎��񂪒ǉ�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit03() throws Exception {
        // �O����
        addProperty("access.control.prohibited.extension.1", "");
        addProperty("restrictionEscape.1", "");

        // �e�X�g���{
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // ����
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(filter, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(filter, "restrictionEscapePaths");
        // prohibitedExtensionList���m�F
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".", prohibitedExtensionList.get(0));
        // restrictionEscapePaths���m�F
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("", restrictionEscapePaths.get(0));    
    }

    /**
     * testInit04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) �v���p�e�B�t�@�C���̊֘A����v���p�e�B:�iaccess.control.prohibited.extension.n�����݂��Ȃ��j<br>
     *                �irestrictedEscape.n�����݂��Ȃ��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prohibitedExtensionList:���List<br>
     *         (��ԕω�) restrictionEscapePaths:���List<br>
     *         
     * <br>
     * �P�j�v���p�e�B��access.control.prohibited.extension.n�����݂��Ȃ��ꍇ�AprohibitedExtensionList�ɉ����ݒ肳��Ă��Ȃ����Ƃ��m�F����B<br>
     * �Q�j�v���p�e�B��restrictedEscape.n�����݂��Ȃ��ꍇ�ArestrictedEscapePaths�ɉ����ݒ肳��Ă��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit04() throws Exception {
        // �O����
        // �iaccess.control.prohibited.extension.n�����݂��Ȃ��j
        // �irestrictionEscapePaths.n�����݂��Ȃ��j

        // �e�X�g���{
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // ����
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(filter, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(filter, "restrictionEscapePaths");
        // prohibitedExtensionList���m�F
        assertEquals(0, prohibitedExtensionList.size());
        // restrictionEscapePaths���m�F
        assertEquals(0, restrictionEscapePaths.size());
    }

    /**
     * testInit05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) �v���p�e�B�t�@�C���̊֘A����v���p�e�B:access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.3=.test3<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.3=./test/test3<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prohibitedExtensionList:".test1"<br>
     *         (��ԕω�) restrictionEscapePaths:"./test/test1"<br>
     *         
     * <br>
     * �P�j�v���p�e�B��access.control.prohibited.extension.n��n�ɔ���������ꍇ�AprohibitedExtensionList�ɔ������C���f�b�N�X�܂ł̒l���ǉ�����邱�Ƃ��m�F����B<br>
     * �Q�j�v���p�e�B��restrictedEscape.n��n�ɔ���������ꍇ�ArestrictedEscapePaths�ɔ������C���f�b�N�X�܂ł̒l���ǉ�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit05() throws Exception {
        // �O����
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.3", ".test3");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.3", "./test/test3");

        // �e�X�g���{
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // ����
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(filter, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(filter, "restrictionEscapePaths");
        // prohibitedExtensionList���m�F
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".test1", prohibitedExtensionList.get(0));
        // restrictionEscapePaths���m�F
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("./test/test1", restrictionEscapePaths.get(0));    
    }

    /**
     * testInit06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) �v���p�e�B�t�@�C���̊֘A����v���p�e�B:access.control.prohibited.extension.0=.test0<br>
     *                access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.2=.test2<br>
     *                restrictedEscape.0=./test/test0<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.2=./test/test2<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prohibitedExtensionList:".test1"<br>
     *                    ".test2"<br>
     *         (��ԕω�) restrictionEscapePaths:"./test/test1"<br>
     *                    "./test/test2"<br>
     *         
     * <br>
     * �P�j�v���p�e�B��access.control.prohibited.extension.n��n��0����n�܂�ꍇ�AprohibitedExtensionList��n��0�̒l�͒ǉ�����Ȃ����Ƃ��m�F����B<br>
     * �Q�j�v���p�e�B��restrictedEscapen��n��0����n�܂�ꍇ�ArestrictedEscapePaths��n��0�̒l�͒ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit06() throws Exception {
        // �O����
        addProperty("access.control.prohibited.extension.0", ".test0");
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.2", ".test2");
        addProperty("restrictionEscape.0", "./test/test0");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.2", "./test/test2");

        // �e�X�g���{
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // ����
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(filter, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(filter, "restrictionEscapePaths");
        // prohibitedExtensionList���m�F
        assertEquals(2, prohibitedExtensionList.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(".test" + (i + 1), prohibitedExtensionList.get(i));
        }
        
        // restrictionEscapePaths���m�F
        assertEquals(2, restrictionEscapePaths.size());
        for (int i = 0; i < 2; i++) {
            assertEquals("./test/test" + (i + 1), restrictionEscapePaths.get(i));
        }
    }

    /**
     * testInit07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) config:not null<br>
     *         (���) �v���p�e�B�t�@�C���̊֘A����v���p�e�B:access.control.prohibited.extension.1=.test1<br>
     *                access.control.prohibited.extension.a=.testA<br>
     *                restrictedEscape.1=./test/test1<br>
     *                restrictedEscape.A=./test/testA<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) prohibitedExtensionList:".test1"<br>
     *         (��ԕω�) restrictionEscapePaths:"./test/test1"<br>
     *         
     * <br>
     * �P�j�v���p�e�B��access.control.prohibited.extension.n��n�������łȂ��ꍇ�AprohibitedExtensionList��n�������łȂ��l�͒ǉ�����Ȃ����Ƃ��m�F����B<br>
     * �Q�j�v���p�e�B��restrictedEscape.n��n�������łȂ��ꍇ�ArestrictedEscapePaths��n�������łȂ��l�͒ǉ�����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit07() throws Exception {
        // �O����
        addProperty("access.control.prohibited.extension.1", ".test1");
        addProperty("access.control.prohibited.extension.a", ".testA");
        addProperty("restrictionEscape.1", "./test/test1");
        addProperty("restrictionEscape.A", "./test/testA");

        // �e�X�g���{
        ExtensionFilter filter = new ExtensionFilter();
        MockFilterConfig config = new MockFilterConfig();
        filter.init(config);
        
        // ����
        List prohibitedExtensionList = (List) UTUtil.getPrivateField(filter, "prohibitedExtensionList");
        List restrictionEscapePaths = (List) UTUtil.getPrivateField(filter, "restrictionEscapePaths");
        // prohibitedExtensionList���m�F
        assertEquals(1, prohibitedExtensionList.size());
        assertEquals(".test1", prohibitedExtensionList.get(0));
        // restrictionEscapePaths���m�F
        assertEquals(1, restrictionEscapePaths.size());
        assertEquals("./test/test1", restrictionEscapePaths.get(0));    
    }

    /**
     * testDoFilter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:"aaaaa"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:�O������ŗ^�����l�Ɠ���<br>
     *         (��ԕω�) sendError:�Ă΂�Ȃ�<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * request�̑����l�ł���EXTENSION_THRU_KEY��null�łȂ��ꍇ�A�g���q�`�F�b�N���s�킸�ɏI�����邱�ƁB<br>
     * FilterChain#doFilter()���\�b�h�����s���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response = new ExtensionFilter_ServletResponseStub01();
        // EXTENSION_THRU_KEY:"aaaaa"
        request.setAttribute("EXTENSION_THRU_KEY", "aaaaa");
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("aaaaa", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:null<br>
     *         (���) requestURI:null<br>
     *         (���) contextPath:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:"true"<br>
     *         (��ԕω�) sendError:�Ă΂�Ȃ�<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * requestURI��null�̏ꍇ�ANullPointerException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath(null);
        request.setRequestURI(null);
        
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
        
        try {
            // �e�X�g���{
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (NullPointerException e) {
            // ����
            assertEquals(NullPointerException.class.getName(),
                         e.getClass().getName());
        }
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:null<br>
     *         (���) requestURI:"/test/test"<br>
     *         (���) contextPath:"/test"<br>
     *         (���) restrictionEscapePaths:"/test"<br>
     *         (���) prohibitedExtensionList:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:"true"<br>
     *         (��ԕω�) sendError:�Ă΂�Ȃ�<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * �g���q�`�F�b�N�ΏۊO���X�g��requestURI�̒l���܂܂��ꍇ�A�g���q�֎~���X�g��requestURI����擾�����g���q�i�󕶎��j���܂܂�Ă��Ă��A�g���q�`�F�b�N���s�킸�Arequest�̑����lEXTENSION_THRU_KEY��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:"" 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add("");
        // restrictionEscapePaths:"/test/test"
        ArrayList<String> restrictionEscapePaths = new ArrayList<String>();
        restrictionEscapePaths.add("/test");
        
        // prohibitedExtensionList��ݒ�
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePaths��ݒ�
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURI��ݒ�
        request.setRequestURI("/test/test");
        request.setContextPath("/test");

        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:null<br>
     *         (���) requestURI:"/test/test"<br>
     *         (���) contextPath:"/test"<br>
     *         (���) restrictionEscapePaths:���List<br>
     *         (���) prohibitedExtensionList:���List<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:"true"<br>
     *         (��ԕω�) sendError:�Ă΂�Ȃ�<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * �g���q�`�F�b�N�ΏۊO���X�g��requestURI�̒l���܂܂ꂸ�A�g���q�֎~���X�g��requestURI����擾�����g���q�i�󕶎��j���܂܂�Ȃ��ꍇ�Arequest�̑����lEXTENSION_THRU_KEY��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:���List 
        ArrayList prohibitedExtensionList = new ArrayList();
        // restrictionEscapePaths:���List
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionList��ݒ�
        UTUtil.setPrivateField(filter,
                                "prohibitedExtensionList", 
                                prohibitedExtensionList);
        
        // restrictionEscapePaths��ݒ�
        UTUtil.setPrivateField(filter, 
                                "restrictionEscapePaths",
                                restrictionEscapePaths); 
        // requestURI��ݒ�
        request.setRequestURI("/test/test");
        request.setContextPath("/test");
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:null<br>
     *         (���) requestURI:"/test/test"<br>
     *         (���) contextPath:"/test"<br>
     *         (���) restrictionEscapePaths:���List<br>
     *         (���) prohibitedExtensionList:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:"true"<br>
     *         (��ԕω�) sendError:HttpServletResponse.SC_NOT_FOUND���^������<br>
     *         (��ԕω�) doFilter:���s���Ȃ�<br>
     *         
     * <br>
     * �g���q�`�F�b�N�ΏۊO���X�g��requestURI�̒l���܂܂ꂸ�A�g���q�֎~���X�g��requestURI����擾�����g���q�i�󕶎��j���܂܂��ꍇ�Arequest�̑����lEXTENSION_THRU_KEY��true��ݒ肵�Arequest�̏�ԃR�[�h��"404"��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter05() throws Exception {
           // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:���List 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add("");
        // restrictionEscapePaths:���List
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionList��ݒ�
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePaths��ݒ�
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURI��ݒ�
        request.setRequestURI("/test/test");
        request.setContextPath("/test");
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(HttpServletResponse.SC_NOT_FOUND, UTUtil.getPrivateField(response, "errorCode"));
        assertFalse(filterChain.isCalled);
    }

    /**
     * testDoFilter06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:null<br>
     *         (���) requestURI:"/test/test.ext2"<br>
     *         (���) contextPath:"/test"<br>
     *         (���) restrictionEscapePaths:"/test.ext2"<br>
     *                "/test.ext3"<br>
     *         (���) prohibitedExtensionList:".ext1"<br>
     *                ".ext2"<br>
     *                ".ext3"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:"true"<br>
     *         (��ԕω�) sendError:�Ă΂�Ȃ�<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * �����o�^����Ă���g���q�`�F�b�N�ΏۊO���X�g��requestURI�̒l���܂܂��ꍇ�A�����o�^����Ă���g���q�֎~���X�g��requestURI����擾�����g���q�i".ext2"�j���܂܂�Ă��Ă��A�g���q�`�F�b�N���s�킸�Arequest�̑����lEXTENSION_THRU_KEY��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter06() throws Exception {
        // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:"" 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add(".ext1");
        prohibitedExtensionList.add(".ext2");
        prohibitedExtensionList.add(".ext3");
        // restrictionEscapePaths:"/test/test"
        ArrayList<String> restrictionEscapePaths = new ArrayList<String>();
        restrictionEscapePaths.add("/test.ext2");
        restrictionEscapePaths.add("/test.ext3");
        
        // prohibitedExtensionList��ݒ�
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePaths��ݒ�
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        
        // requestURI��ݒ�
        request.setRequestURI("/test/test.ext2");
        request.setContextPath("/test");
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:null<br>
     *         (���) requestURI:"/test/test.ext"<br>
     *         (���) contextPath:"/test"<br>
     *         (���) restrictionEscapePaths:���List<br>
     *         (���) prohibitedExtensionList:���List<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:"true"<br>
     *         (��ԕω�) sendError:�Ă΂�Ȃ�<br>
     *         (��ԕω�) doFilter:���s����<br>
     *         
     * <br>
     * �g���q�`�F�b�N�ΏۊO���X�g��requestURI�̒l���܂܂ꂸ�A�g���q�֎~���X�g��requestURI����擾�����g���q�i".ext"�j���܂܂�Ȃ��ꍇ�Arequest�̑����lEXTENSION_THRU_KEY��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter07() throws Exception {
        // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:���List 
        ArrayList prohibitedExtensionList = new ArrayList();
        // restrictionEscapePaths:���List
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionList��ݒ�
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePaths��ݒ�
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURI��ݒ�
        request.setRequestURI("/test/test.ext");
        request.setContextPath("/test");
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(-1, UTUtil.getPrivateField(response, "errorCode"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter08()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) EXTENSION_THRU_KEY:null<br>
     *         (���) requestURI:"/test/test.ext1"<br>
     *         (���) contextPath:"/test"<br>
     *         (���) restrictionEscapePaths:���List<br>
     *         (���) prohibitedExtensionList:".ext1"<br>
     *                ".ext2"<br>
     *                ".ext3"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) EXTENSION_THRU_KEY:"true"<br>
     *         (��ԕω�) sendError:HttpServletResponse.SC_NOT_FOUND���^������<br>
     *         (��ԕω�) doFilter:���s���Ȃ�<br>
     *         
     * <br>
     * �g���q�`�F�b�N�ΏۊO���X�g��requestURI�̒l���܂܂ꂸ�A�����o�^����Ă���g���q�֎~���X�g��requestURI����擾�����g���q�i".ext1"�j���܂܂��ꍇ�Arequest�̑����lEXTENSION_THRU_KEY��true��ݒ肵�Arequest�̏�ԃR�[�h��"404"��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter08() throws Exception {
           // �O����
        ExtensionFilter filter = new ExtensionFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ExtensionFilter_ServletResponseStub01 response =
            new ExtensionFilter_ServletResponseStub01();
        
        ExtensionFilter_FilterChainStub01 filterChain =
            new ExtensionFilter_FilterChainStub01();
       
        // prohibitedExtensionList:���List 
        ArrayList<String> prohibitedExtensionList = new ArrayList<String>();
        prohibitedExtensionList.add(".ext1");
        prohibitedExtensionList.add(".ext2");
        prohibitedExtensionList.add(".ext3");
        // restrictionEscapePaths:���List
        ArrayList restrictionEscapePaths = new ArrayList();
        
        // prohibitedExtensionList��ݒ�
        UTUtil.setPrivateField(filter,
                "prohibitedExtensionList", 
                prohibitedExtensionList);
        // restrictionEscapePaths��ݒ�
        UTUtil.setPrivateField(filter, 
                "restrictionEscapePaths",
                restrictionEscapePaths); 
        // requestURI��ݒ�
        request.setRequestURI("/test/test.ext1");
        request.setContextPath("/test");
        
        // �e�X�g���{
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("EXTENSION_THRU_KEY"));
        assertEquals(HttpServletResponse.SC_NOT_FOUND, UTUtil.getPrivateField(response, "errorCode"));
        assertFalse(filterChain.isCalled);
    }

}
