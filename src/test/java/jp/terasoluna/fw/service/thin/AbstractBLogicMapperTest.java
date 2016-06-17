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

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.PropertyAccessException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.thin.AbstractBLogicMapper} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �r�W�l�X���W�b�N���o�͏�񔽉f���ۃN���X�B<br>
 * BLogicIOPlugIn�ɂ���Đ������ꂽBLogicResources�����ƂɁA
 * Web�w�̃I�u�W�F�N�g�ƁA�r�W�l�X���W�b�N�Ԃ̃f�[�^�̃}�b�s���O���s���@�\��
 * �W�񂵂����ۃN���X�ł���B<br>
 * <br>
 * �O������F<br>
 * ���ۃN���XAbstractBLogicMapper�����������e�X�g�p�N���X���쐬���A
 * �e�e�X�g�P�[�X�̑O������ɂ��郁�\�b�h���������邱�ƁB
 * <p>
 *
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 */
@SuppressWarnings("unused")
public class AbstractBLogicMapperTest extends TestCase {

    private static final String TESTBEAN01_NAME = "jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01";

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractBLogicMapperTest.class);
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
    public AbstractBLogicMapperTest(String name) {
        super(name);
    }

    /**
     * testMapBLogicParams01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:null<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:�|<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:�}�b�s���O��`�Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * ������BLogicIO��null�œn���ꂽ���Anull��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams01() throws Exception {

        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = null;

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{�E����
        assertNull(mapper.mapBLogicParams(request, response, io));
    }

    /**
     * testMapBLogicParams02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null�iio.getBLogicParams().size() == 0�j<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:��`�Ȃ�<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:�}�b�s���O��`�Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * �}�b�s���O��`�iblogic-params�j���L�q����Ă��Ȃ����Anull��ԋp���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams02() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{�E����
        assertNull(mapper.mapBLogicParams(request, response, io));

    }

    /**
     * testMapBLogicParams03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:���݂��Ȃ�JavaBean��<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:request�F<br>
     *                 "requestValue"��"blogicRequestValue"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.create"<br>
     *                    ���b�v������O�FClassLoadException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "bean creation failure."<br>
     *
     * <br>
     * inputBeanName�ɑ��݂��Ȃ�JavaBean�����`�������ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams03() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName("nothingClass");
        BLogicProperty property = new BLogicProperty();
        property.setSource("request");
        property.setProperty("requestValue");
        property.setBLogicProperty("blogicRequestValue");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.create", e.getErrorCode());
            assertEquals(ClassLoadException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("bean creation failure."));
        }
    }

    /**
     * testMapBLogicParams04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():����propName+"_FromForm"��ԋp<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:form�F<br>
     *                 "userName"��"blogicUserName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:[blogicUserName="userName_FromForm"]<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * property�̎w�肪1���̏ꍇ�A�擾���ɑΉ�������͒l�擾���\�b�h���Ăяo���A�Ɩ����W�b�N�̓��͏��Bean�̃t�B�[���h�Ƀ}�b�s���O���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams04() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{�E����
        AbstractBLogicMapper_BeanStub01 result = (AbstractBLogicMapper_BeanStub01) mapper
                .mapBLogicParams(request, response, io);
        assertEquals("userName_FromForm", result.getBlogicUserName());

    }

    /**
     * testMapBLogicParams05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():����propName+"_FromForm"��ԋp<br>
     *         (���) getValueFromRequest():����propName+"_FromRequest"��ԋp<br>
     *         (���) getValueFromSession():����propName+"_FromSession"��ԋp<br>
     *         (���) getValueFromApplication():����propName+"_FromApplication"��ԋp<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:form�F<br>
     *                 "userName"��"blogicUserName"<br>
     *                request�F<br>
     *                 "requestValue"��"blogicRequestValue"<br>
     *                session�F<br>
     *                 "sessionValue"��"blogicSessionValue"<br>
     *                application�F<br>
     *                 "applicationValue"��"blogicApplicationValue"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:[blogicUserName="userName_FromForm"]<br>
     *                  [blogicRequestValue="rrequestValue_FromRequest"]<br>
     *                  [blogicSessionValue="sessionValue_FromSession"]<br>
     *                  [blogicApplicationValue="applicationValue_FromApplication"]<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * property�̎w�肪�������̏ꍇ�A�擾���ɑΉ�������͒l�擾���\�b�h���Ăяo���A�Ɩ����W�b�N�̓��͏��Bean�̃t�B�[���h�Ƀ}�b�s���O���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams05() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);
        BLogicProperty property2 = new BLogicProperty();
        property2.setSource("request");
        property2.setProperty("requestValue");
        property2.setBLogicProperty("blogicRequestValue");
        io.setBLogicParam(property2);
        BLogicProperty property3 = new BLogicProperty();
        property3.setSource("session");
        property3.setProperty("sessionValue");
        property3.setBLogicProperty("blogicSessionValue");
        io.setBLogicParam(property3);
        BLogicProperty property4 = new BLogicProperty();
        property4.setSource("application");
        property4.setProperty("applicationValue");
        property4.setBLogicProperty("blogicApplicationValue");
        io.setBLogicParam(property4);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{�E����
        AbstractBLogicMapper_BeanStub01 result = (AbstractBLogicMapper_BeanStub01) mapper
                .mapBLogicParams(request, response, io);
        assertEquals("userName_FromForm", result.getBlogicUserName());
        assertEquals("requestValue_FromRequest", result.getBlogicRequestValue());
        assertEquals("sessionValue_FromSession", result.getBlogicSessionValue());
        assertEquals("applicationValue_FromApplication", result.getBlogicApplicationValue());
    }

    /**
     * testMapBLogicParams06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():����propName+"_FromForm"��ԋp<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:form�F<br>
     *                 "userName"���w��Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:[userName="userName_FromForm"]<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *
     * <br>
     * �Ɩ����W�b�N���L�[���w�肳��Ă��Ȃ��ꍇ�A���͏��Bean�̃}�b�s���O��̃t�B�[���h���͎擾���̃L�[���ƂȂ邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams06() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{�E����
        AbstractBLogicMapper_BeanStub01 result = (AbstractBLogicMapper_BeanStub01) mapper
                .mapBLogicParams(request, response, io);
        assertEquals("userName_FromForm", result.getUserName());
    }

    /**
     * testMapBLogicParams07()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():����propName+"_FromForm"��ԋp<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:page:<br>
     *                 "userName" �� "blogicUserName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.getvalue"<br>
     *                    �u��������P�F<br>
     *                    "userName"<br>
     *                    ���b�v������O�FBLogicMapperException(NoSuchMethodException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "no such method."<br>
     *
     * <br>
     * �擾���ɑz�肵�Ȃ���������w�肵�����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams07() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        // �擾���ɑz��O�̕������ݒ�
        property.setSource("page");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.getvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // NoSuchMethodException�̃`�F�b�N
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("no such method."));
        }

    }

    /**
     * testMapBLogicParams08()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():Exception����<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:error�F<br>
     *                 "userName"��"blogicUserName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.getvalue"<br>
     *                    �u��������P�F<br>
     *                    "userName"<br>
     *                    ���b�v������O�FBLogicMapperException(InvocationTargetException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "exception is thrown out by invokeMethod."<br>
     *
     * <br>
     * ���t���N�V�����Ŏ��s���郁�\�b�h�őz��O�̗�O�������������ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams08() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("error");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.getvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // InvocationTargetException�̃`�F�b�N
            assertEquals(InvocationTargetException.class.getName(), e
                    .getCause().getCause().getClass().getName());
            assertTrue(LogUTUtil
                    .checkError("exception is thrown out by invokeMethod."));
        }

    }

    /**
     * testMapBLogicParams09()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():����propName+"_FromForm"��ԋp<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:form�F<br>
     *                 "userName"��"nothing"�i���݂��Ȃ��t�B�[���h�j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.setproperty"<br>
     *                    �u��������P�F<br>
     *                    "nothing"<br>
     *                    ���b�v������O�FBLogicMapperException(PropertyAccessException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "setBeanProperty failure."<br>
     *
     * <br>
     * �Ɩ����W�b�N���L�[���œ��͏��Bean�ɑ��݂��Ȃ��t�B�[���h���w�肵�����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams09() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("form");
        property.setProperty("userName");
        property.setBLogicProperty("nothing");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.setproperty", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("nothing", e.getOptions()[0]);
            // PropertyAccessException�̃`�F�b�N
            assertEquals(PropertyAccessException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("setBeanProperty failure."));
        }
    }

    /**
     * testMapBLogicParams10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:""�i�󕶎��j�F<br>
     *                 "userName" �� "blogicUserName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.source"<br>
     *                    ���b�v������O�FBLogicMapperException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "source is illegal."<br>
     *
     * <br>
     * �擾����""�i�󕶎��j���w�肵�����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams10() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource("");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.source", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("source is illegal."));
        }
    }

    /**
     * testMapBLogicParams11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:null�F<br>
     *                 "userName" �� "blogicUserName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.source"<br>
     *                    ���b�v������O�FBLogicMapperException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "source is illegal."<br>
     *
     * <br>
     * �擾����null���w�肵�����ASystemException���������邱�ƁB�i�{�����肦�Ȃ��j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams11() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);
        BLogicProperty property = new BLogicProperty();
        property.setSource(null);
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            Object result = mapper.mapBLogicParams(request, response, io);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.source", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("source is illegal."));
        }
    }

    /**
     * testMapBLogicParams12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:null<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:request�F<br>
     *                 "requestValue"��"blogicRequestValue"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * inputBeanName��null�̂Ƃ��Anull���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams12() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(null);
        BLogicProperty property = new BLogicProperty();
        property.setSource("request");
        property.setProperty("requestValue");
        property.setBLogicProperty("blogicRequestValue");
        io.setBLogicParam(property);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        assertNull(mapper.mapBLogicParams(request, response, io));
    }

    /**
     * testMapBLogicParams13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicParams().size()  > 0�j<br>
     *         (���) getValueFromForm():�|<br>
     *         (���) getValueFromRequest():�|<br>
     *         (���) getValueFromSession():�|<br>
     *         (���) getValueFromError():�|<br>
     *         (���) inputBeanName:"jp.terasoluna.fw.service.thin.AbstractBLogicMapper_BeanStub01"<br>
     *         (���) property(�擾���F�擾���̃L�[�� �� �Ɩ����W�b�N���L�[���j:�}�b�s���O��`�Ȃ��B<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *
     * <br>
     * blogic-io��property�w�肪�Ȃ��ꍇ�A���͒l�N���X����������Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicParams13() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        io.setInputBeanName(TESTBEAN01_NAME);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        assertNull(mapper.mapBLogicParams(request, response, io));
    }

    /**
     * testMapBLogicResult01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *         (����) result:null<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:�|<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.result"<br>
     *                    ���b�v������O�FBLogicMapperException(NullPointerException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "result is null."<br>
     *         (��ԕω�) request:�[<br>
     *
     * <br>
     * ������BLogicResult��null�œn���ꂽ���ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult01() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();

        // BLogicResult�ݒ�
        BLogicResult result = null;

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.result", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            // NullPointerException�̃`�F�b�N
            assertEquals(NullPointerException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("BLogicResult is null."));
        }
    }

    /**
     * testMapBLogicResult02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:null<br>
     *         (����) result:not null�iresult.getResultObject()==null�j<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:�|<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) request:�Ȃ�<br>
     *
     * <br>
     * ������BlogicIO��null�A���Aresult������resultObject��null�̎��A���������ɏ������I�����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult02() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = null;

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        result.setResultObject(null);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        mapper.mapBLogicResult(request, response, io, result);

        // ����
        assertFalse(request.getAttributeNames().hasMoreElements());
    }

    /**
     * testMapBLogicResult03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:null<br>
     *         (����) result:not null�iresult.getResultObject()!=null�j<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:�|<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.notnull"<br>
     *                    ���b�v������O�FBLogicMapperException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "bean is not null."<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * ������BlogicIO��null�A���Aresult������resultObject��null�łȂ����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult03() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = null;

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        result.setResultObject("not null");

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.notnull", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("bean is not null."));
        }
    }

    /**
     * testMapBLogicResult04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null�iio.getBLogicResults().size() == 0�j<br>
     *         (����) result:not null�iresult.getResultObject()==null�j<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:�}�b�s���O��`�Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) request:�Ȃ�<br>
     *
     * <br>
     * �}�b�s���O��`�iblogic-results�j���L�q����Ă��炸�A���Aresult������resultObject��null�̎��A���������ɏ������I�����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult04() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        result.setResultObject(null);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        mapper.mapBLogicResult(request, response, io, result);

        // ����
        assertFalse(request.getAttributeNames().hasMoreElements());
    }

    /**
     * testMapBLogicResult05()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null�iio.getBLogicResults().size() == 0�j<br>
     *         (����) result:not null�iresult.getResultObject()!=null�j<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:�}�b�s���O��`�Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.notnull"<br>
     *                    ���b�v������O�FBLogicMapperException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "bean is not null."<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * �}�b�s���O��`�iblogic-results�j���L�q����Ă��炸�A���Aresult������resultObject��null�łȂ����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult05() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        result.setResultObject("not null");

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.notnull", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("bean is not null."));
        }
    }

    /**
     * testMapBLogicResult06()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:not null<br>
     *                �iresult.getResultObject()!=null�j<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:form�F<br>
     *                "nothing"�i���݂��Ȃ��t�B�[���h�j ��"userName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.getproperty"<br>
     *                    �u��������P�F<br>
     *                    "nothing"<br>
     *                    ���b�v������O�FBLogicMapperException(PropertyAccessException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "getBeanProperty failure."<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * �Ɩ����W�b�N���L�[���ŏo�͏��Bean�ɑ��݂��Ȃ��t�B�[���h���w�肵�����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult06() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        property.setBLogicProperty("nothing");
        io.setBLogicResult(property);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.getproperty", e.getErrorCode());
            assertEquals("nothing", e.getOptions()[0]);
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            // PropertyAccessException�̃`�F�b�N
            assertEquals(PropertyAccessException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("getBeanProperty failure."));
        }
    }

    /**
     * testMapBLogicResult07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:result.getResultObject() [blogicUserName="���ꑾ�Y"]<br>
     *         (���) setValueToForm():request�Ɂu���f��L�[��+"_ToForm"�v���L�[�Ɉ���value��ݒ�<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:form�F<br>
     *                "blogicUserName"�� "userName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) request:[userName_ToForm="���ꑾ�Y"]<br>
     *
     * <br>
     * property�̎w�肪1���̏ꍇ�A���f��ɑΉ�����o�͒l���f���\�b�h���Ăяo���A���f��̃t�B�[���h�Ƀ}�b�s���O���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult07() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        bean.setBlogicUserName("���ꑾ�Y");
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        mapper.mapBLogicResult(request, response, io, result);

        // ����
        assertEquals("���ꑾ�Y", request.getAttribute("userName_ToForm"));
    }

    /**
     * testMapBLogicResult08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:result.getResultObject() [blogicUserName="���ꑾ�Y"�AblogicRequestValue="requestValue�AblogicSessionValue="sessionValue"]<br>
     *         (���) setValueToForm():request�Ɂu���f��L�[��+"_ToForm"�v���L�[�Ɉ���value��ݒ�<br>
     *         (���) setValueToRequest():request�Ɂu���f��L�[��+"_ToRequest"�v���L�[�Ɉ���value��ݒ�<br>
     *         (���) setValueToSession():request�Ɂu���f��L�[��+"_ToSession"�v���L�[�Ɉ���value��ݒ�<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:form�F<br>
     *                "blogicUserName"�� "userName"<br>
     *                request�F<br>
     *                "blogicRequestValue" ��"requestValue"<br>
     *                session�F<br>
     *                "blogicSessionValue" ��"sessionValue"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) request:[userName_ToForm="���ꑾ�Y"]<br>
     *                    [requestValue_ToRequest="requestValue"]<br>
     *                    [sessionValue_ToSession="sessionValue"]<br>
     *
     * <br>
     * property�̎w�肪�������̏ꍇ�A���f��ɑΉ�����o�͒l���f���\�b�h���Ăяo���A���f��̃t�B�[���h�Ƀ}�b�s���O���邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult08() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);
        BLogicProperty property2 = new BLogicProperty();
        property2.setDest("request");
        property2.setProperty("requestValue");
        property2.setBLogicProperty("blogicRequestValue");
        io.setBLogicResult(property2);
        BLogicProperty property3 = new BLogicProperty();
        property3.setDest("session");
        property3.setProperty("sessionValue");
        property3.setBLogicProperty("blogicSessionValue");
        io.setBLogicResult(property3);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        bean.setBlogicUserName("���ꑾ�Y");
        bean.setBlogicRequestValue("requestValue");
        bean.setBlogicSessionValue("sessionValue");
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        mapper.mapBLogicResult(request, response, io, result);

        // ����
        assertEquals("���ꑾ�Y", request.getAttribute("userName_ToForm"));
        assertEquals("requestValue", request
                .getAttribute("requestValue_ToRequest"));
        assertEquals("sessionValue", request
                .getAttribute("sessionValue_ToSession"));
    }

    /**
     * testMapBLogicResult09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:result.getResultObject() [userName="���ꑾ�Y"�AblogicUserName=null]<br>
     *         (���) setValueToForm():request�Ɂu���f��L�[��+"_ToForm"�v���L�[�Ɉ���value��ݒ�<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:form�F<br>
     *                �w��Ȃ��� "userName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) request:[userName_ToForm="���ꑾ�Y"]<br>
     *
     * <br>
     * �Ɩ����W�b�N���L�[���w�肳��Ă��Ȃ��ꍇ�A���f�l�Ƃ��Ď擾����t�B�[���h�̖��O�͔��f��̃L�[���Ɠ����ɂȂ邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult09() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("form");
        property.setProperty("userName");
        io.setBLogicResult(property);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        bean.setUserName("���ꑾ�Y");
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        mapper.mapBLogicResult(request, response, io, result);

        // ����
        assertEquals("���ꑾ�Y", request.getAttribute("userName_ToForm"));
    }

    /**
     * testMapBLogicResult10()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:not null<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:page:<br>
     *                  "blogicUserName"��"userName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.setvalue"<br>
     *                    �u��������P�F<br>
     *                    "userName"<br>
     *                    ���b�v������O�FBLogicMapperException(NoSuchMethodException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "no such method."<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * ���f��ɑz�肵�Ȃ���������w�肵�����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult10() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        // �擾���ɑz��O�̕������ݒ�
        property.setDest("page");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.setvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // NoSuchMethodException�̃`�F�b�N
            assertEquals(NoSuchMethodException.class.getName(), e.getCause()
                    .getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("no such method."));
        }
    }

    /**
     * testMapBLogicResult11()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:not null<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():Exception����<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:error�F<br>
     *                "blogicUserName"�� "userName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.setvalue"<br>
     *                    �u��������P�F<br>
     *                    "userName"<br>
     *                    ���b�v������O�FBLogicMapperException(InvocationTargetException)<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "exception is thrown out by invokeMethod."<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * ���t���N�V�����Ŏ��s���郁�\�b�h�őz��O�̗�O�������������ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult11() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("error");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.setvalue", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertEquals("userName", e.getOptions()[0]);
            // InvocationTargetException�̃`�F�b�N
            assertEquals(InvocationTargetException.class.getName(), e
                    .getCause().getCause().getClass().getName());
            assertTrue(LogUTUtil
                    .checkError("exception is thrown out by invokeMethod."));
        }
    }

    /**
     * testMapBLogicResult12()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:not null<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:""�i�󕶎��j�F<br>
     *                 "blogicUserName" ��"userName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.dest"<br>
     *                    ���b�v������O�FBLogicMapperException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "dest is illegal."<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * ���f���""�i�󕶎��j���w�肵�����ASystemException���������邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult12() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest("");
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.dest", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("dest is illegal."));
        }
    }

    /**
     * testMapBLogicResult13()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null<br>
     *                �iio.getBLogicResults().size() > 0�j<br>
     *         (����) result:not null<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:null�F<br>
     *                 "blogicUserName"�� "userName"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:SystemException�F<br>
     *                    ���b�Z�[�W�L�[�F"errors.blogic.mapper.dest"<br>
     *                    ���b�v������O�FBLogicMapperException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "dest is illegal."<br>
     *         (��ԕω�) request:�|<br>
     *
     * <br>
     * ���f���null���w�肵�����ASystemException���������邱�ƁB�i�{�����肦�Ȃ��j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult13() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();
        BLogicProperty property = new BLogicProperty();
        property.setDest(null);
        property.setProperty("userName");
        property.setBLogicProperty("blogicUserName");
        io.setBLogicResult(property);

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        AbstractBLogicMapper_BeanStub01 bean = new AbstractBLogicMapper_BeanStub01();
        result.setResultObject(bean);

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        try {
            mapper.mapBLogicResult(request, response, io, result);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.blogic.mapper.dest", e.getErrorCode());
            assertEquals(BLogicMapperException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError("dest is illegal."));
        }
    }

    /**
     * testMapBLogicResult14()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) request:not null<br>
     *         (����) response:not null<br>
     *         (����) io:not null�iio.getBLogicResults().size() == 0�j<br>
     *         (����) result:not null�iresult.getResultObject() instanceof AbstractBLogicDownloadObject�j<br>
     *         (���) setValueToForm():�|<br>
     *         (���) setValueToRequest():�|<br>
     *         (���) setValueToSession():�|<br>
     *         (���) setValueToError():�|<br>
     *         (���) property(�擾���F�Ɩ����W�b�N���L�[�� �� ���f��̃L�[���j:�}�b�s���O��`�Ȃ�<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) void:�|<br>
     *         (��ԕω�) ��O:�|<br>
     *         (��ԕω�) ���O:�|<br>
     *         (��ԕω�) request:�Ȃ�<br>
     *
     * <br>
     * �}�b�s���O��`�iblogic-results�j���L�q����Ă��炸�A���Aresult������resultObject��AbstractBLogicDownloadObject���p�����鎞�A���������ɏ������I�����邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapBLogicResult14() throws Exception {
        // �[�����N�G�X�g
        HttpServletRequest request = new MockHttpServletRequest();

        // �[�����X�|���X
        HttpServletResponse response = new MockHttpServletResponse();

        // BlogicIO�ݒ�
        BLogicIO io = new BLogicIO();

        // BLogicResult�ݒ�
        BLogicResult result = new BLogicResult();
        result.setResultObject(new AbstractBLogicMapper_AbstractDownloadObjectStub01());

        // AbstractBLogicMapperImpl����
        AbstractBLogicMapperImpl01 mapper = new AbstractBLogicMapperImpl01();

        // �e�X�g���{
        mapper.mapBLogicResult(request, response, io, result);

        // ����
        assertFalse(request.getAttributeNames().hasMoreElements());
    }


}
