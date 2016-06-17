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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx;
import jp.terasoluna.fw.web.struts.form.FormEx;
import jp.terasoluna.utlib.DynaActionFormCreator;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * {@link jp.terasoluna.fw.service.thin.BLogicMapper} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N���o�͏�񔽉f�N���X�B<br>
 * BLogicIOPlugIn�ɂ���Đ������ꂽBLogicResources�����ƂɁA
 * Web�w�̃I�u�W�F�N�g�ƁA�r�W�l�X���W�b�N�Ԃ̃f�[�^�̃}�b�s���O���s���B
 * <p>
 *
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 */
@SuppressWarnings("unused")
public class BLogicMapperTest extends TestCase {

    /**
     * �t�@�C���p�X���v���p�e�B����擾
     */
    private static final String CONFIG_FILE_NAME = BLogicMapperTest.class
            .getResource("BLogicMapperTest.xml").getPath();

    private static final String RULES_FILE_NAME = BLogicMapperTest.class
            .getResource("BLogicMapperTest-rules.xml").getPath();

    private final DynaActionFormCreator creator;

    private DynaValidatorActionFormEx formEx = null;
    
    private String resource = "resource.path";

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.formEx =
            (DynaValidatorActionFormEx) this.creator.create(CONFIG_FILE_NAME);
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
    public BLogicMapperTest(String name) {
        super(name);

        this.creator = new DynaActionFormCreator(RULES_FILE_NAME);
    }

    /**
     * testBLogicMapper01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) resources:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "resources file location is not specified"<br>
     *         
     * <br>
     * �R���X�g���N�^������null�l�̏ꍇ�A��O���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMapper01() throws Exception {
        
        // �e�X�g���{
        try {
            new BLogicMapper(null);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(LogUTUtil.checkError("resources file location is " +
                    "not specified"));
        }
        
    }

    /**
     * testBLogicMapper02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) resources:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException�F<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "resources file location is not specified"<br>
     *         
     * <br>
     * �R���X�g���N�^��������l�̏ꍇ�A��O���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMapper02() throws Exception {
        // �e�X�g���{
        try {
            new BLogicMapper("");
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals(IllegalArgumentException.class.getName(),
                    e.getClass().getName());
            assertTrue(LogUTUtil.checkError("resources file location is " +
                    "not specified"));
        }
        
    }

    /**
     * testBLogicMapper03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) resources:"resource.path"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) blogicMapper:not Null
     * <br>
     * �R���X�g���N�^�̈��������݂���ꍇ�A�C���X�^���X����������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testBLogicMapper03() throws Exception {
        // �e�X�g���{
        BLogicMapper blogicMapper = new BLogicMapper(resource);
        
        // ����
        assertNotNull(blogicMapper);
    }

    /**
     * testGetValueFromRequest01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propName:"requestValue"<br>
     *         (����) request:[requestValue="requestValue"]<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"requestValue"<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[��request�ɒl���i�[����Ă������A�擾�����l��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromRequest01() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        request.setAttribute("requestValue", "requestValue");

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertEquals("requestValue", mapper.getValueFromRequest("requestValue",
                request, response));
    }

    /**
     * testGetValueFromRequest02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propName:"value"<br>
     *         (����) request:[requestValue="requestValue"]<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[��request�ɒl���i�[����Ă��Ȃ����Anull��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromRequest02() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute("requestValue", "requestValue");

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertNull(mapper.getValueFromRequest("value", request, response));
    }

    /**
     * testGetValueFromRequest03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:""<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * ������propName���󕶎��̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromRequest03() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���s
        try {
            Object result = mapper.getValueFromRequest("", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromRequest04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * ������propName��null�̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromRequest04() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���s
        try {
            Object result = mapper.getValueFromRequest(null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToRequest01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"requestValue"<br>
     *         (����) request:[requestValue="requestValue"]<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) request:[requestValue="value"]<br>
     *
     * <br>
     * ����value�̒l��request�ɔ��f����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToRequest01() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute("requestValue", "requestValue");

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToRequest("value", "requestValue", request, response);

        // ����
        assertEquals("value", request.getAttribute("requestValue"));
    }

    /**
     * testSetValueToRequest02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:""<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = []"<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * ����propName�̒l���󕶎��̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToRequest02() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            mapper.setValueToRequest("value", "", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testSetValueToRequest03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [null]"<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * ����propName�̒l��null�̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToRequest03() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            mapper.setValueToRequest("value", null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToRequest04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *         (����) propName:"requestValue"<br>
     *         (����) request:[requestValue="requestValue"]<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) request:requestValue���폜�����<br>
     *
     * <br>
     * value��null�̎��Arequest�ɓo�^����Ă���Attribute���폜����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToRequest04() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute("requestValue", "requestValue");

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToRequest(null, "requestValue", request, response);

        // ����
        assertFalse(request.getAttributeNames().hasMoreElements());
    }

    /**
     * testSetValueToRequest05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"requestValue"<br>
     *         (����) request:requestValue�ݒ�Ȃ�<br>
     *         (����) response:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) request:[requestValue="value"]<br>
     *
     * <br>
     * ����value�̒l��request�ɔ��f����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToRequest05() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToRequest("value", "requestValue", request, response);

        // ����
        assertEquals("value", request.getAttribute("requestValue"));
    }

    /**
     * testGetValueFromSession01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propName:"sessionValue"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"sessionValue"<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[��session�ɒl���i�[����Ă������A�擾�����l��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromSession01() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertEquals("sessionValue", mapper.getValueFromSession("sessionValue",
                request, response));
    }

    /**
     * testGetValueFromSession02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propName:"value"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[��session�ɒl���i�[����Ă��Ȃ����Anull��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromSession02() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertNull(mapper.getValueFromSession("value", request, response));
    }

    /**
     * testGetValueFromSession03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:""<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * ����propName�̒l���󕶎��̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromSession03() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            Object result = mapper.getValueFromSession("", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromSession04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * ����propName�̒l��null�̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromSession04() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            Object result = mapper.getValueFromSession(null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToSession01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"sessionValue"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) session:[sessionValue="value"]<br>
     *
     * <br>
     * ����value�̒l��session�ɔ��f����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToSession01() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToSession("value", "sessionValue", request, response);

        // ����
        assertEquals("value", session.getAttribute("sessionValue"));
    }

    /**
     * testSetValueToSession02()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:""<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = []"<br>
     *         (��ԕω�) session:�|<br>
     *
     * <br>
     * ����propName�̒l���󕶎��̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToSession02() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            mapper.setValueToSession("value", "", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testSetValueToSession03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [null]"<br>
     *         (��ԕω�) session:�|<br>
     *
     * <br>
     * ����propName�̒l��null�̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToSession03() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            mapper.setValueToSession("value", null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testSetValueToSession04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *         (����) propName:"sessionValue"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:[sessionValue="sessionValue"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) session:sessionValue���폜�����<br>
     *
     * <br>
     * value��null�̎��Asession�ɓo�^����Ă���Attribute���폜����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToSession04() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        session.setAttribute("sessionValue", "sessionValue");

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToSession(null, "sessionValue", request, response);

        // ����
        assertFalse(session.getAttributeNames().hasMoreElements());
    }

    /**
     * testSetValueToSession05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"sessionValue"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) session�irequest.getSession()�j:sessionValue�ݒ�Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) session:[sessionValue="value"]<br>
     *
     * <br>
     * ����value�̒l��session�ɔ��f����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToSession05() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToSession("value", "sessionValue", request, response);

        // ����
        assertEquals("value", session.getAttribute("sessionValue"));
    }

    /**
     * testGetValueFromForm01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propName:"param1"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:[param1="param1"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"param1"<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[��form�ɒl���i�[����Ă������A�擾�����l��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromForm01() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // �A�N�V�����t�H�[������
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param1", "param1");

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertEquals("param1", mapper.getValueFromForm("param1", request,
                response));
    }

    /**
     * testGetValueFromForm02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propName:"param2"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"session"<br>
     *         (���) form:[param2="param2"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"param2"<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[��form�ɒl���i�[����Ă������A�擾�����l��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromForm02() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("session");

        // �A�N�V�����t�H�[������
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param2", "param2");

        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        session.setAttribute(mapping.getAttribute(), form);

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertEquals("param2", mapper.getValueFromForm("param2", request,
                response));
    }

    /**
     * testGetValueFromForm03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propName:"param0"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"session"<br>
     *         (���) form:param0�̃t�B�[���h�Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:PropertyAccessException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [param0]"<br>
     *
     * <br>
     * ����propName�Ŏw�肳���t�B�[���h��form�ɑ��݂��Ȃ����A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromForm03() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("session");

        // �A�N�V�����t�H�[������
        FormEx form = this.formEx;

        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        session.setAttribute(mapping.getAttribute(), form);

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            Object result = mapper
                    .getValueFromForm("param0", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [param0]"));
        }
    }

    /**
     * testGetValueFromForm04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:""<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:PropertyAccessException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * ������propName���󕶎��̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromForm04() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // �A�N�V�����t�H�[������
        FormEx form = this.formEx;

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            Object result = mapper.getValueFromForm("", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromForm05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:PropertyAccessException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * ������propName��null�̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromForm05() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // �A�N�V�����t�H�[������
        FormEx form = this.formEx;

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            Object result = mapper.getValueFromForm(null, request, response);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

    /**
     * testGetValueFromForm06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propName:"param1"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:[param1="param1"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"java.lang.String"<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���l�X�Ƃ����v���p�e�B���Q�Ƃ��Ă���ꍇ�A
     * �v���p�e�B�l�𐳏�Ɏ擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromForm06() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");
        mapping.setName("testGetValueFromForm06");

        // �A�N�V�����t�H�[������
        BlogicMapper_ActionFormStub01 form =
        	new BlogicMapper_ActionFormStub01();
        form.setParam1("param1");

        // �[�����N�G�X�g
        HttpServletRequest request =
        	new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertEquals("java.lang.String",
        		mapper.getValueFromForm("param1.class.name", request,
        		response));
    }

    /**
     * testSetValueToForm01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"param1"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:[param1="param1"]<br>
     *                modified=false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) form:[param1="value"]<br>
     *                    modified=true<br>
     *
     * <br>
     * ����value�̒l��form�ɔ��f����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToForm01() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // �A�N�V�����t�H�[������
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param1", "param1");
        form.setModified(false);

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToForm("value", "param1", request, response);

        // ����
        assertEquals("value", form.get("param1"));
        assertTrue(form.isModified());
    }

    /**
     * testSetValueToForm02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"param2"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"session"<br>
     *         (���) form:[param2="param2"]<br>
     *                modified=false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) form:[param2="value"]<br>
     *                    modified=true<br>
     *
     * <br>
     * ����value�̒l��form�ɔ��f����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToForm02() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("session");

        // �A�N�V�����t�H�[������
        DynaValidatorActionFormEx form = this.formEx;
        form.set("param2", "param2");
        form.setModified(false);

        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        session.setAttribute(mapping.getAttribute(), form);

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToForm("value", "param2", request, response);

        // ����
        assertEquals("value", form.get("param2"));
        assertTrue(form.isModified());
    }

    /**
     * testSetValueToForm03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"param0"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:param0�̃t�B�[���h�Ȃ�<br>
     *                modified=false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:PropertyAccessException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [param0]"<br>
     *         (��ԕω�) form:modified=false<br>
     *
     * <br>
     * ����propName�Ŏw�肳���t�B�[���h��form�ɑ��݂��Ȃ����A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToForm03() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // �A�N�V�����t�H�[������
        FormEx form = this.formEx;
        form.setModified(false);

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            mapper.setValueToForm("value", "param0", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [param0]"));
            assertFalse(form.isModified());
        }
    }

    /**
     * testSetValueToForm04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:""<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:not null<br>
     *                modified=false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:PropertyAccessException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = []"<br>
     *         (��ԕω�) form:modified=false<br>
     *
     * <br>
     * ����propName�̒l���󕶎��̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToForm04() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // �A�N�V�����t�H�[������
        FormEx form = this.formEx;
        form.setModified(false);

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            mapper.setValueToForm("value", "", request, response);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = []"));
            assertFalse(form.isModified());
        }
    }

    /**
     * testSetValueToForm05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:not null<br>
     *                modified=false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:PropertyAccessException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [null]"<br>
     *         (��ԕω�) form:modified=false<br>
     *
     * <br>
     * ����propName�̒l��null�̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToForm05() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");

        // �A�N�V�����t�H�[������
        FormEx form = this.formEx;
        form.setModified(false);

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            mapper.setValueToForm("value", null, request, response);
            fail();
        } catch (PropertyAccessException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
            assertFalse(form.isModified());
        }
    }

    /**
     * testSetValueToForm06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) value:"value"<br>
     *         (����) propName:"param3.value"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) mapping:not null<br>
     *         (���) mapping.getScope():"request"<br>
     *         (���) form:[param3=HashMap]<br>
     *                ��ActionForm�����N���X<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) form:[param3=HashMap{ value="value"}]<br>
     *
     * <br>
     * ������propName�Ƀl�X�g�����v���p�e�B���w�肳�ꂽ�ꍇ�A
     * ����ɒl���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetValueToForm06() throws Exception {
        // �A�N�V�����}�b�s���O
        ActionMapping mapping = new ActionMapping();
        mapping.setScope("request");
        Map map = new HashMap();

        // �A�N�V�����t�H�[������
        BlogicMapper_ActionFormStub01 form =
        	new BlogicMapper_ActionFormStub01();
        form.setParam3(map);

        // �[�����N�G�X�g
        HttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setAttribute(Globals.MAPPING_KEY, mapping);
        request.setAttribute(mapping.getAttribute(), form);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{
        mapper.setValueToForm("value", "param3.value", request, response);

        // ����
        Map result = form.getParam3();
        assertEquals("value", result.get("value"));
    }

    /**
     * testGetValueFromApplication01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propName:"sessionValue"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) servletContext:[applicationValue="applicationValue"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"applicationValue"<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[�ɃT�[�u���b�g�R���e�L�X�g�ɒl���i�[����Ă������A
     * �擾�����l��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromApplication01() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        ServletContext context = session.getServletContext();
        context.setAttribute("applicationValue", "applicationValue");

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertEquals("applicationValue",
            mapper.getValueFromApplication("applicationValue", request, response));
    }

    /**
     * testGetValueFromApplication02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propName:"value"<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) servletContext:[applicationValue="applicationValue"]<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������propName���L�[�ɃT�[�u���b�g�R���e�L�X�g�ɒl���i�[����Ă��Ȃ����A
     * null��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromApplication02() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();
        ServletContext context = session.getServletContext();
        context.setAttribute("applicationValue", "applicationValue");

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        assertNull(mapper.getValueFromApplication("value", request, response));
    }

    /**
     * testGetValueFromApplication03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:""<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) servletContext:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = []"<br>
     *
     * <br>
     * ����propName�̒l���󕶎��̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromApplication03() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            Object result = mapper.getValueFromApplication("", request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil.checkError("illegal argument:propName = []"));
        }
    }

    /**
     * testGetValueFromApplication04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) propName:null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) servletContext:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "illegal argument:propName = [null]"<br>
     *
     * <br>
     * ����propName�̒l��null�̎��A��O���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValueFromApplication04() throws Exception {
        // �[���Z�b�V����
        HttpSession session = new MockHttpSession();

        // �[�����N�G�X�g
        MockHttpServletRequest request = new BLogicMapper_MockHttpServletRequest01();
        request.setSession(session);

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BLogicMapper����
        BLogicMapper mapper = new BLogicMapper(resource);

        // �e�X�g���{�E����
        try {
            Object result = mapper.getValueFromApplication(null, request, response);
            fail();
        } catch (IllegalArgumentException e) {
            // �e�X�g���ʊm�F
            assertTrue(LogUTUtil
                    .checkError("illegal argument:propName = [null]"));
        }
    }

}
