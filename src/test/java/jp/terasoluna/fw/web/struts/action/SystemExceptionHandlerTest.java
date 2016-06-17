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

import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ExceptionUtil;
import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.MockHttpSession;
import jp.terasoluna.utlib.MockServletContext;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * {@link jp.terasoluna.fw.web.struts.action.SystemExceptionHandler} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * SystemException��O�����N���X�B<br>
 * �V�X�e����O���̃��O�o�͂� �G���[��ʂւ̑J�ڂ��s���B<br>
 * �A�N�V�������s���ɃV�X�e����O�����������Ƃ��́A�G���[�������O�o�͂�����ŁA���Y�A�N�V�����}�b�s���O�ɒ�`����Ă���V�X�e���G���[��ʂɑJ�ڂ���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.SystemExceptionHandler
 */
public class SystemExceptionHandlerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SystemExceptionHandlerTest.class);
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
    public SystemExceptionHandlerTest(String name) {
        super(name);
    }

    /**
     * testExecute01()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�Fnull<br>
     *         
     * <br>
     * �����̗�O��SystemException�ł͂Ȃ��Apath�������w�肳�ꂽ���Apath�����Ŏw�肳�ꂽ�p�X���A�N�V�����t�H���[�h�ɐݒ肳��A���΃R���e�L�X�g���[�g��true�ɐݒ肳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute01() throws Exception {
        // �O����
        
        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O�ł͂Ȃ���O
        Exception e = new Exception();
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
       
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        // ���N�G�X�g�̏�ԕω��̊m�F
        assertNull(req.getAttribute(PageContext.EXCEPTION));

    }

    /**
     * testExecute02()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:not null<br>
     *         (����) mapping:not null<br>
     *               �yinput�t�B�[���h��"/errorInput.do"���ݒ肳��Ă���z
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorInput.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�ŁA<br>
     *                    ��O���o�^����Ă��Ȃ����ƁB<br>
     *         
     * <br>
     * �����̗�O��SystemException�ł͂Ȃ��A�A�N�V�����}�b�s���O��input�������w�肳�ꂽ���Ainput�����Ŏw�肳�ꂽ�p�X���A�N�V�����t�H���[�h�ɐݒ肳�ꑊ�΃R���e�L�X�g���[�g��true�ɐݒ肳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute02() throws Exception {
        // �O����
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O�ł͂Ȃ���O
        Exception e = new Exception();
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setKey("action.message.key");
        
        // �A�N�V�����}�b�s���O��Input������ݒ�
        ActionMapping mapping = new ActionMapping();
        mapping.setInput("/errorInput.do");
        
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorInput.do", forward.getPath());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertNull(req.getAttribute(PageContext.EXCEPTION));
    }

    /**
     * testExecute03()
     * <br><br>
     * 
     * �ُ�n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:not null<br>
     *               �yinput�t�B�[���h��"/errorInput.do"���ݒ肳��Ă���z
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *                Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *                MessageResources�o<br>
     *                 SystemExceptionHandlerTest.error.message="��O���b�Z�[�W"<br>
     *                }<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�FSystemException<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F"��O���b�Z�[�W"<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ��O�ݒ����path�����ƁA�A�N�V�����}�b�s���O��input�����������w�肳��Ă��鎞�Apath�������D�悳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute03() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        // �A�N�V�����}�b�s���O��Input������ݒ�
        ActionMapping mapping = new ActionMapping();
        mapping.setInput("/errorInput.do");
        
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
        
        // MessageResources�̐ݒ�
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // ���N�G�X�g��MessageResources�o�^
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward 
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        
        // ���N�G�X�g�̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));
        
        // ��O�̏�ԕω��̊m�F
        assertEquals("��O���b�Z�[�W", UTUtil.getPrivateField(e, "message"));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNull(actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute04()
     * <br><br>
     * 
     * �ُ�n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:not null<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *                Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *                MessageResources�o<br>
     *                 SystemExceptionHandlerTest.error.message="��O���b�Z�[�W"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��Fnull<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�ŁA<br>
     *                    SystemException���o�^����Ă��邱�ƁB<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F"��O���b�Z�[�W"<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ��O�ݒ����path�����ƁA�A�N�V�����}�b�s���O��input����������null�̎��A�A�N�V�����t�H���[�h�̑J�ڐ悪null�ł��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute04() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
        
        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // ���N�G�X�g��MessageResources��ݒ�
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertNull(forward.getPath());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // �V�X�e����O�̏�ԕω��̊m�F�B
        assertEquals("��O���b�Z�[�W", UTUtil.getPrivateField(e, "message"));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNull(actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute05()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:MockActionMapping<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:Globals.MESSAGE_KEY<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *                Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *                MessageResources�o<br>
     *                 SystemExceptionHandlerTest.error.message ="��O���b�Z�[�W"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�ŁA<br>
     *                    SystemException���o�^����Ă��邱�ƁB<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F"��O���b�Z�[�W"<br>
     *                    ���ݒ肳��Ă��邱�ƁB<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ��O�ݒ����bundle������Globals.MESSAGE_KEY�̎��A�L�[��Globals.MESSAGE_KEY�ł���MessageResources���p�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute05() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        // �A�N�V�������b�Z�[�W�̃L�[���w��
        eConfig.setKey("action.message.key");
        // ���b�Z�[�W���\�[�X�ւ̃o���h���L�[���w��
        eConfig.setBundle(Globals.MESSAGES_KEY);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // ���N�G�X�g��MessageResources�o�^
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // �V�X�e����O�̏�ԕω��̊m�F
        assertEquals("��O���b�Z�[�W", UTUtil.getPrivateField(e, "message"));
        
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNull(actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute06()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:MockActionMapping<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:anotherKey<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *               Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *               MessageResources�o<br>
     *                SystemExceptionHandlerTest.error.message ="��O���b�Z�[�W"<br>
     *               �p<br>
     *               anotherKey�Ŏ擾���Ă�null���Ԃ��Ă���<br>
     *               �y�T�[�u���b�g�R���e�L�X�g�����z<br>
     *               anotherKey�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *               MessageResources�o<br>
     *                SystemExceptionHandlerTest.error.message ="�T�[�u���b�g��O���b�Z�[�W"<br>
     *               �p<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION:<br>
     *                    SystemException���o�^����Ă��邱�ƁB<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F"�T�[�u���b�g��O���b�Z�[�W"<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ��O�ݒ����bundle�������A�T�[�u���b�g�R���e�L�X�g�̃L�[�ł���Ƃ��A�T�[�u���b�g�R���e�L�X�g�Ɋi�[���ꂽ���b�Z�[�W���\�[�X��<br>
     * �p�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute06() throws Exception {
        // �O����
        
        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        // ���b�Z�[�W���\�[�X�ւ̃o���h���L�[���w��
        eConfig.setBundle("anotherKey");
        // �A�N�V�������b�Z�[�W�̃L�[
        eConfig.setKey("action.message.key");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");
        PropertyMessageResources servletResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources02");

        // �T�[�u���b�g�R���e�L�X�g��MessageResources�o�^
        context.setAttribute("anotherKey", servletResources);

        // ���N�G�X�g�Ƀ��b�Z�[�W���\�[�X�o�^
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNull(actionMessage.getValues());
        }
        
        // �V�X�e����O�̏�ԕω��̊m�F
        assertEquals("�T�[�u���b�g��O���b�Z�[�W", 
            UTUtil.getPrivateField(e, "message"));
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
        
    }

    /**
     * testExecute07()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:MockActionMapping<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *               Globals.MESSAGES_KEY�Ŏ擾���Ă�null���Ԃ��Ă���<br>
     *               �y�T�[�u���b�g�R���e�L�X�g�����z<br>
     *               Globals.MESSAGES_KEY�Ŏ擾���Ă�null���Ԃ��Ă���<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�ŁA<br>
     *                    SystemException���o�^����Ă��邱�ƁB<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F"SystemExceptionHandlerTest.error.message"<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    resource�t�B�[���h��false�ł���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X���擾�ł��Ȃ����A�G���[�R�[�h�����b�Z�[�W�̑���ɐݒ肳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute07() throws Exception {
        // �O����
        
        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // ���b�Z�[�W���\�[�X�͐ݒ肵�Ȃ�
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // �V�X�e����O�̏�ԕω��̊m�F
        assertEquals("SystemExceptionHandlerTest.error.message",
            UTUtil.getPrivateField(e, "message"));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertFalse(actionMessage.isResource());
            assertNull(actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute08()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:MockActionMapping<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *                Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *                MessageResources�o<br>
     *                 "aaaaa"�Ƃ������b�Z�[�W�L�[�œo�^����Ă��Ȃ�<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�ŁA<br>
     *                    SystemException���o�^����Ă��邱�ƁB<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F(null�ł��邱��)<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ���b�Z�[�W�������擾�ł��Ȃ����Anull���ݒ肳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute08() throws Exception {
        // �O����
        
        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "aaaaa");
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources. returnNull��true�ɂ��Ă���̂ɒ���
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01",
                true); 
        
        // ���N�G�X�g��MessageResources�o�^
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);

        // �e�X�g���{
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // �V�X�e����O�̏�ԕω��̊m�F
        // getMessage()�͒P�Ȃ�getter�ł͂Ȃ��Anull�̂Ƃ��̓G���[�R�[�h��ԋp����̂Œ���
        assertNull(UTUtil.getPrivateField(e, "message"));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNull(actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testExecute09()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:ExceptionConfigEx<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:MockActionMapping<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *                Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *                MessageResources�o<br>
     *                 SystemExceptionHandlerTest.error.message.null = null<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *                  ��O�ݒ���̃��W���[���F�A�N�V�����p�X�̃��W���[��<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�ŁA<br>
     *                    SystemException���o�^����Ă��邱�ƁB<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F"error.msg"<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ��O�ݒ���ExceptionConfigEx�̏ꍇ�A��O�ݒ���̃��W���[�����A�N�V�����p�X�̃��W���[���ɐݒ肷�邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute09() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message");
        
        // ��O�ݒ���
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setModule("error");
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
        = new PropertyMessageResources(factory,
            SystemExceptionHandler.class
            .getPackage().getName().replace('.', '/')
            + "/SystemExceptionHandler_MessageResources01");
        
        // ���N�G�X�g��MessageResources�o�^
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);

        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertEquals("error", forward.getModule());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // �V�X�e����O�̏�ԕω��̊m�F
        assertEquals("��O���b�Z�[�W",UTUtil.getPrivateField(e, "message"));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNull(actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }
    
    
    
    /**
     * testExecute10()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString=null<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:MockActionMapping<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *                Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *                MessageResources�o<br>
     *                 SystemExceptionHandlerTest.error.message.empty
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�ŁA<br>
     *                    SystemException���o�^����Ă��邱�ƁB<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F""<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��null�ł���
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ���b�Z�[�W�������󕶎���̎��A�󕶎��񂪐ݒ肳��邱�ƁB<br>
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute10() throws Exception {
        // �O����
        
        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message.empty");
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");
        
        // ���N�G�X�g��MessageResources�o�^
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     

        // �e�X�g���{
        ActionForward forward  
            = handler.execute(e, eConfig, mapping, form, req, res);

        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        
        // ���N�G�X�g�����̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));

        // �V�X�e����O�̏�ԕω��̊m�F
        assertEquals("", UTUtil.getPrivateField(e, "message"));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNull(actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }
    

    /**
     * testExecute11()
     * <br><br>
     * 
     * �ُ�n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:SystemException optionString={"1","22","333","4444","5555"}<br>
     *         (����) eConfig:ExceptionConfig<br>
     *               �ypath�t�B�[���h��"/errorPath.do"���ݒ肳��Ă���z<br>
     *         (����) mapping:not null<br>
     *               �yinput�t�B�[���h��"/errorInput.do"���ݒ肳��Ă���z
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (���) bundle:null<br>
     *         (���) resources:�y���N�G�X�g�����z<br>
     *                Globals.MESSAGES_KEY�Ŏ擾����ƈȉ���MessageResource���܂�MessageResources���Ԃ�<br>
     *                MessageResources�o<br>
     *                 SystemExceptionHandlerTest.error.message="��O���b�Z�[�W"<br>
     *                }<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:�t�H���[�h��F"/errorPath.do"<br>
     *         (��ԕω�) request:���N�G�X�g�����F<br>
     *                    PageContext.EXCEPTION�FSystemException<br>
     *         (��ԕω�) se:�G���[���b�Z�[�W�F"��O���b�Z�[�W"<br>
     *         (��ԕω�) ActionMessages�F"action.message.key"�œo�^����Ă���
     *                                    values��{"1","22","333","4444","5555"}
     *                                    �ł��邱��
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "sessionHash = " + sessionHash<br>
     *                    ExceptionUtil.getStackTrace(se)<br>
     *         
     * <br>
     * ��O�ݒ����path�����ƁA�A�N�V�����}�b�s���O��input�����������w�肳��Ă��鎞�Apath�������D�悳��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testExecute11() throws Exception {
        // �O����
    
        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();
    
        String[] options = {"1","22","333","4444","5555"};
        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.error.message", options );
        
        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");
        eConfig.setKey("action.message.key");
        
        // �A�N�V�����}�b�s���O��Input������ݒ�
        ActionMapping mapping = new ActionMapping();
        mapping.setInput("/errorInput.do");
        
        DynaActionForm form = new DynaActionForm();
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        MockServletContext context = new MockServletContext();
        
        // MessageResources�̐ݒ�
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");
    
        // ���N�G�X�g��MessageResources�o�^
        req.setAttribute(Globals.MESSAGES_KEY, requestResources);
        
        // �T�[�u���b�g�R���e�L�X�g�����N�G�X�g����Q�Ƃ���B
        session.setServletContext(context);
        req.setSession(session);     
    
        // �e�X�g���{
        ActionForward forward 
            = handler.execute(e, eConfig, mapping, form, req, res);
    
        // ����
        
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        
        // ���N�G�X�g�̏�ԕω��̊m�F
        assertSame(e, req.getAttribute(PageContext.EXCEPTION));
        
        // ��O�̏�ԕω��̊m�F
        assertEquals("��O���b�Z�[�W", UTUtil.getPrivateField(e, "message"));
        
        // ActionMessages�̏�ԕω��̊m�F
        ActionMessages messages
            = (ActionMessages) req.getAttribute(Globals.ERROR_KEY);
        assertEquals(1, messages.size());
        Iterator it = messages.get();
        while (it.hasNext()) {
            ActionMessage actionMessage = (ActionMessage) it.next();
            assertEquals("action.message.key", actionMessage.getKey());
            assertNotNull(actionMessage.getValues());
            assertEquals(options, actionMessage.getValues());
        }
        
        // ���O�o�͂̊m�F
        String sessionHash = RequestUtil.getSessionHash(req);
        assertTrue(LogUTUtil
            .checkError("sessionHash = " + sessionHash));
        assertTrue(LogUTUtil
            .checkError(ExceptionUtil.getStackTrace(e)));
    }

    /**
     * testGetErrorMessage01()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) se:not null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     *         
     * <br>
     * �G���[�R�[�h��null�̎��A���b�Z�[�W��null�ŕԋp����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage01() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O(�G���[�R�[�hnull)
        SystemException e 
            = new SystemException(new Exception(), null);

        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // �Ăяo������
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // �e�X�g���{
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // ����
        assertNull(result);
    }

    /**
     * testGetErrorMessage02()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) se:not null<br>
     *         (����) resource:key="{0}�f�t�H���g���b�Z�[�W"<br>
     *                en_US.key="{0}message"<br>
     *         (���) locale:null<br>
     *         (���) errorCode:"key"<br>
     *         (���) option:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"{0}�f�t�H���g���b�Z�[�W"<br>
     *         
     * <br>
     * ���P�[���A�u��������null�ł��鎞�A�f�t�H���g���P�[���̃��b�Z�[�W���u�����ꂸ�ɏo�͂���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage02() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O(�G���[�R�[�hnull)
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key");
        
        MockHttpServletRequest req = new MockHttpServletRequest();
        
        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // �Ăяo������
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // �e�X�g���{
        // private���\�b�h���������s
        Object result = UTUtil.invokePrivate(handler,
            "getErrorMessage", clazz, args);

        // ����
        assertEquals("{0}�f�t�H���g���b�Z�[�W", (String)result);
    }

    /**
     * testGetErrorMessage03()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) se:not null<br>
     *         (����) resource:key="{0}�f�t�H���g���b�Z�[�W"<br>
     *                en_US.key="{0}message"<br>
     *         (���) locale:"en_US"<br>
     *         (���) errorCode:"key"<br>
     *         (���) option:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"{0}message"<br>
     *         
     * <br>
     * ���P�[�����w�肳�ꂽ���A�w�肳�ꂽ���P�[���̃��b�Z�[�W���o�͂���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage03() throws Exception {
        // �O����
        
        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key");

        MockHttpServletRequest req = new MockHttpServletRequest();
        Locale locale = new Locale("en_US");
        req.setLocale(locale);

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // �Ăяo������
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // �e�X�g���{
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // ����
        assertEquals("{0}message", (String)result);
    }

    /**
     * testGetErrorMessage04()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) se:not null<br>
     *         (����) resource:key="{0}�f�t�H���g���b�Z�[�W"<br>
     *                en_US.key="{0}message"<br>
     *         (���) locale:null<br>
     *         (���) errorCode:"key"<br>
     *         (���) option:{"option1"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"option1�f�t�H���g���b�Z�[�W"<br>
     *         
     * <br>
     * �u�������񂪎w�肳��Ă���Ƃ��A���b�Z�[�W�̒u�����s�Ȃ��邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage04() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �G���[���b�Z�[�W��u�����郁�b�Z�[�W
        String[] options = {"option1"};

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key", options);

        MockHttpServletRequest req = new MockHttpServletRequest();

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // �Ăяo������
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // �e�X�g���{
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // ����
        assertEquals("option1�f�t�H���g���b�Z�[�W", (String)result);
    }

    /**
     * testGetErrorMessage05()
     * <br><br>
     * 
     * ����n
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) se:not null<br>
     *         (����) resource:key="{0}�f�t�H���g���b�Z�[�W"<br>
     *                en_US.key="{0}message"<br>
     *         (���) locale:"en_US"<br>
     *         (���) errorCode:"key"<br>
     *         (���) option:{"option1"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"option1message"<br>
     *         
     * <br>
     * ���P�[���A�u�������񂪎w�肳�ꂽ���A�w�肳�ꂽ���P�[���̃��b�Z�[�W���u������ďo�͂���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("static-access")
    public void testGetErrorMessage05() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �G���[���b�Z�[�W��u�����郁�b�Z�[�W
        String[] options = {"option1"};

        // �V�X�e����O
        SystemException e = new SystemException(new Exception(),
             "SystemExceptionHandlerTest.key", options);

        // �[�����N�G�X�g
        MockHttpServletRequest req = new MockHttpServletRequest();
        // ���P�[���ݒ�
        Locale locale = new Locale("en_US");
        req.setLocale(locale);

        // MessageResources
        MessageResourcesFactory factory
            = PropertyMessageResourcesFactory.createFactory();
        PropertyMessageResources requestResources 
            = new PropertyMessageResources(factory,
                SystemExceptionHandler.class
                .getPackage().getName().replace('.', '/')
                + "/SystemExceptionHandler_MessageResources01");

        // �Ăяo������
        Class[] clazz = {HttpServletRequest.class,
                         SystemException.class,
                         MessageResources.class};
        Object[] args = {req, e, requestResources};

        // �e�X�g���{
        Object result 
            = UTUtil.invokePrivate(handler, "getErrorMessage", clazz, args);

        // ����
        assertEquals("option1message", (String)result);
    }

    /**
     * testGetLogger01()
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(�O�����) log:-<br>
     * <br>
     * ���Ғl�F(�߂�l) LogFactory.getLog(SystemExceptionHandler.class)�Ɠ���̃C���X�^���X<br>
     * <br>
     * ����n1���݂̂̃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLogger01() throws Exception {
        // �O����

        // �V�X�e����O�n���h��
        SystemExceptionHandler handler = new SystemExceptionHandler();

        // �e�X�g���{
        Log logger = handler.getLogger();

        // ����
        assertSame(LogFactory.getLog(SystemExceptionHandler.class), logger);
    }
}
