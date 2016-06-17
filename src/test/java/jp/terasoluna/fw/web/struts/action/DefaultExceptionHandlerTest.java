/*
 * Copyright (c) 2008 NTT DATA Corporation
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

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.ExceptionConfig;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler} �N���X�̃u���b�N�{�b�N�X�e�X�g.
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �w�肵�����O���x���Ń��O���o�͂����O�n���h���B<br>
 * ��O���̃��O�o�͂� �G���[��ʂւ̑J�ڂ��s���B<br>
 * �A�N�V�������s���ɃV�X�e����O�����������Ƃ��́A�G���[�������O�o�͂�����ŁA���Y�A�N�V�����}�b�s���O�ɒ�`����Ă���V�X�e���G���[��ʂɑJ�ڂ���B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.action.DefaultExceptionHandler
 */
public class DefaultExceptionHandlerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������.
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefaultExceptionHandlerTest.class);
    }

    /**
     * �������������s��.
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s��.
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^.
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DefaultExceptionHandlerTest(String name) {
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
     *                  path="/errorPath.do"<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (��ԕω�) ���O:�yERROR���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     *
     * <br>
     * ExceptionConfig��ݒ肵���ꍇ�A�f�t�H���g��ERROR���x����
     * ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute01() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // ��O
        Exception e = new Exception();

        // ��O�ݒ���
        ExceptionConfig eConfig = new ExceptionConfig();
        eConfig.setPath("/errorPath.do");

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // ����
        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("��O�n���h������O�����m���܂���", e));
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));

    }

    /**
     * testExecute02().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  module="module"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:path="/errorPath.do"<br>
     *                                module="module"<br>
     *         (��ԕω�) ���O:�yTRACE���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     *
     * <br>
     * ExceptionConfigEx��logLevel��TRACE��ݒ肵�A�J�ڐ惂�W���[����ݒ肵���ꍇ�A
     * TRACE���x���Ń��O���o�͂���A�A�N�V�����t�H���[�h�ɑJ�ڐ惂�W���[�����ݒ�
     * ����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute02() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // ��O
        Exception e = new Exception();

        // ��O�ݒ���
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setModule("module");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_TRACE);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // ����

        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertEquals("module", forward.getModule());
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkTrace("��O�n���h������O�����m���܂���", e));
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));

    }

    /**
     * testExecute03().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_DEBUG<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (��ԕω�) ���O:�yDEBUG���O�z"��O�n���h������O�����m���܂���"<br>
     *
     * <br>
     * ExceptionConfigEx��logLevel��DEBUG��ݒ肵���ꍇ�A
     * DEBUG���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute03() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // ��O
        Exception e = new Exception();

        // ��O�ݒ���
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_DEBUG);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // ����

        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));

    }

    /**
     * testExecute04().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_INFO<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (��ԕω�) ���O:�yINFO���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     * <br>
     * ExceptionConfigEx��logLevel��INFO��ݒ肵���ꍇ�A
     * INFO���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute04() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // ��O
        Exception e = new Exception();

        // ��O�ݒ���
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_INFO);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // ����

        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkInfo("��O�n���h������O�����m���܂���", e));
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));

    }

    /**
     * testExecute05().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_WARN<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (��ԕω�) ���O:�yWARN���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     * <br>
     * ExceptionConfigEx��logLevel��WARN��ݒ肵���ꍇ�A
     * WARN���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute05() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // ��O
        Exception e = new Exception();

        // ��O�ݒ���
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_WARN);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // ����

        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkWarn("��O�n���h������O�����m���܂���", e));
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));

    }

    /**
     * testExecute06().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_ERROR<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (��ԕω�) ���O:�yERROR���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     * <br>
     * ExceptionConfigEx��logLevel��ERROR��ݒ肵���ꍇ�A
     * ERROR���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute06() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // ��O
        Exception e = new Exception();

        // ��O�ݒ���
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_ERROR);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // ����

        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("��O�n���h������O�����m���܂���", e));
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));

    }

    /**
     * testExecute07().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) ex:Exception<br>
     *         (����) eConfig:ExceptionConfigEx<br>
     *                  path="/errorPath.do"<br>
     *                  logLevel=DefaultExceptionHandler.LOG_LEVEL_FATAL<br>
     *         (����) mapping:not null<br>
     *         (����) formInstance:not null<br>
     *         (����) request:not null<br>
     *         (����) response:not null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F(�߂�l) ActionForward:path="/errorPath.do"<br>
     *                                module=null<br>
     *         (��ԕω�) ���O:�yFATAL���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     * <br>
     * ExceptionConfigEx��logLevel��FATAL��ݒ肵���ꍇ�A
     * FATAL���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute07() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // ��O
        Exception e = new Exception();

        // ��O�ݒ���
        ExceptionConfigEx eConfig = new ExceptionConfigEx();
        eConfig.setPath("/errorPath.do");
        eConfig.setLogLevel(DefaultExceptionHandler.LOG_LEVEL_FATAL);

        ActionMapping mapping = new ActionMapping();
        DynaActionForm formInstance = new DynaActionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        ActionForward forward = handler.execute(e, eConfig, mapping, formInstance,
                request, response);

        // ����

        // �߂�l�̊m�F
        assertEquals("/errorPath.do", forward.getPath());
        assertNull(forward.getModule());
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkFatal("��O�n���h������O�����m���܂���", e));
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));

    }

    /**
     * testLogExceptionException01().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     *
     * <br>
     * �X�[�p�[�N���X��ExceptionHandler#logException(Exception e)�̏�����
     * �I�[�o�[���C�h���A�f�o�b�O���O���o�͂���Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionException01() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = null;
        
        // �e�X�g���{
        handler.logException(e);
    
        // ����
    
        // ���O�̊m�F
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));
    
    }

    /**
     * testLogExceptionException02().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yDEBUG���O�z�i�o�͂���Ȃ��j<br>
     *
     * <br>
     * �X�[�p�[�N���X��ExceptionHandler#logException(Exception e)�̏�����
     * �I�[�o�[���C�h���A�f�o�b�O���O���o�͂���Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionException02() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = new Exception();
        
        // �e�X�g���{
        handler.logException(e);
    
        // ����
    
        // ���O�̊m�F
        assertFalse(LogUTUtil.checkDebug("��O�n���h������O�����m���܂���", e));
    
    }

    /**
     * testLogExceptionExceptionString01().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�z"��O�n���h������O�����m���܂���"<br>
     *
     * <br>
     * ������e��null��logLevel��null�̏ꍇ�A�f�t�H���g��ERROR���x����
     * ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionString01() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = null;
        String logLevel = null;
        
        // �e�X�g���{
        handler.logException(e, logLevel);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("��O�n���h������O�����m���܂���"));
    
    }

    /**
     * testLogExceptionExceptionString02().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yTRACE���O�z"��O�n���h������O�����m���܂���"<br>
     *
     * <br>
     * ������e��null��logLevel���ݒ肳��Ă���ꍇ�A�ݒ肳�ꂽ���O���x����
     * ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionString02() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        
        // �e�X�g���{
        handler.logException(e, logLevel);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkTrace("��O�n���h������O�����m���܂���"));
    
    }

    /**
     * testLogExceptionExceptionString03().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �o�͂�����O�F������Exception<br>
     * <br>
     * ������e��null�łȂ�logLevel��null�̏ꍇ�A�f�t�H���g��ERROR���x����
     * ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionString03() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = new Exception();
        String logLevel = null;
        
        // �e�X�g���{
        handler.logException(e, logLevel);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("��O�n���h������O�����m���܂���", e));
    
    }

    /**
     * testLogExceptionExceptionString04().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yTRACE���O�z"��O�n���h������O�����m���܂���"<br>
     *                         �o�͂�����O�F������Exception<br>
     * <br>
     * ������e��null�łȂ�logLevel���ݒ肳��Ă���ꍇ�A�ݒ肳�ꂽ���O���x����
     * ���O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionString04() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        
        // �e�X�g���{
        handler.logException(e, logLevel);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkTrace("��O�n���h������O�����m���܂���", e));
    
    }

    /**
     * testLogExceptionExceptionStringString01().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:null<br>
     *         (����) message:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�znull<br>
     *
     * <br>
     * ������e��null��logLevel�ƃ��b�Z�[�W���ݒ肳��Ă��Ȃ��ꍇ�A
     * �f�t�H���g��ERROR���x����null�̃��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString01() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = null;
        String message = null;
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError(null));
    
    }

    /**
     * testLogExceptionExceptionStringString02().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:null<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�z"hoge"<br>
     *
     * <br>
     * ������e��null��logLevel���ݒ肳��Ă��炸���b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * �f�t�H���g��ERROR���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString02() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = null;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString03().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yTRACE���O�z"hoge"<br>
     *
     * <br>
     * ������e��null��logLevel��TRACE�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * TRACE���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString03() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkTrace("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString04().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_DEBUG<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yDEBUG���O�z"hoge"<br>
     *
     * <br>
     * ������e��null��logLevel��DEBUG�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * DEBUG���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString04() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_DEBUG;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkDebug("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString05().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_INFO<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yINFO���O�z"hoge"<br>
     *
     * <br>
     * ������e��null��logLevel��INFO�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * INFO���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString05() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_INFO;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkInfo("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString06().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_WARN<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yWARN���O�z"hoge"<br>
     *
     * <br>
     * ������e��null��logLevel��WARN�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * WARN���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString06() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_WARN;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkWarn("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString07().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_ERROR<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�z"hoge"<br>
     *
     * <br>
     * ������e��null��logLevel��ERROR�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * ERROR���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString07() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_ERROR;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString08().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:null<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_FATAL<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yFATAL���O�z"hoge"<br>
     *
     * <br>
     * ������e��null��logLevel��FATAL�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * FATAL���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString08() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = null;
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_FATAL;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkFatal("hoge"));
    
    }

    /**
     * testLogExceptionExceptionStringString09().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:null<br>
     *         (����) message:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�znull<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel�ƃ��b�Z�[�W���ݒ肳��Ă��Ȃ��ꍇ�A
     * �f�t�H���g��ERROR���x����null�̃��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString09() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = null;
        String message = null;
    
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError(null, e));
    
    }

    /**
     * testLogExceptionExceptionStringString10().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:null<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�z"hoge"<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel���ݒ肳��Ă��炸���b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * �f�t�H���g��ERROR���x���Ń��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString10() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = null;
        String message = "hoge";
    
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString11().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yTRACE���O�z"hoge"<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel��TRACE�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * TRACE���x���Ń��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString11() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkTrace("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString12().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_DEBUG<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yDEBUG���O�z"hoge"<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel��DEBUG�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * DEBUG���x���Ń��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString12() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_DEBUG;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkDebug("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString13().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_INFO<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yINFO���O�z"hoge"<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel��INFO�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * INFO���x���Ń��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString13() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_INFO;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkInfo("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString14().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_WARN<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yWARN���O�z"hoge"<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel��WARN�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * WARN���x���Ń��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString14() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_WARN;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkWarn("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString15().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_ERROR<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�z"hoge"<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel��ERROR�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * ERROR���x���Ń��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString15() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_ERROR;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("hoge", e));
    
    }

    /**
     * testLogExceptionExceptionStringString16().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) e:Exception<br>
     *         (����) logLevel:DefaultExceptionHandler.LOG_LEVEL_FATAL<br>
     *         (����) message:"hoge"<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yFATAL���O�z"hoge"<br>
     *                         �o�͂�����O�F������Exception<br>
     *
     * <br>
     * ������e�ɗ�O���ݒ肳��Ă���AlogLevel��FATAL�Ń��b�Z�[�W���ݒ肳��Ă���ꍇ�A
     * FATAL���x���Ń��O�Ɨ�O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionExceptionStringString16() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        Exception e = new Exception();
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_FATAL;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(e, logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkFatal("hoge", e));
    
    }

    /**
     * testLogExceptionStringString01().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) logLevel:null<br>
     *         (����) message:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�znull<br>
     *
     * <br>
     * ������logLevel��null��message��null�̏ꍇ�A
     * �f�t�H���g��ERROR���x����null�̃��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionStringString01() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = null;
        String message = null;
        
        // �e�X�g���{
        handler.logException(logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError(null));
    
    }

    /**
     * testLogExceptionStringString02().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) logLevel:null<br>
     *         (����) message:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yERROR���O�z"hoge"<br>
     *
     * <br>
     * ������logLevel��null��message��null�łȂ��ꍇ�A
     * �f�t�H���g��ERROR���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionStringString02() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = null;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkError("hoge"));
    
    }

    /**
     * testLogExceptionStringString03().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (����) message:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yTRACE���O�znull<br>
     *
     * <br>
     * ������logLevel���ݒ肳��Ă���message��null�̏ꍇ�A
     * �ݒ肳�ꂽ���O���x����null�̃��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionStringString03() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = null;
        
        // �e�X�g���{
        handler.logException(logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkTrace(null));
    
    }

    /**
     * testLogExceptionStringString04().
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) logLevel:DefaultExceptionHandler.LOG_LEVEL_TRACE<br>
     *         (����) message:null<br>
     *         (�O�����) messages:-<br>
     * <br>
     * ���Ғl�F
     *         (��ԕω�) ���O:�yTRACE���O�z"hoge"<br>
     *
     * <br>
     * ������logLevel���ݒ肳��Ă���message��null�łȂ��ꍇ�A
     * �ݒ肳�ꂽ���O���x���Ń��O���o�͂���邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLogExceptionStringString04() throws Exception {
        // �O����
    
        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();
    
        String logLevel = DefaultExceptionHandler.LOG_LEVEL_TRACE;
        String message = "hoge";
        
        // �e�X�g���{
        handler.logException(logLevel, message);
    
        // ����
    
        // ���O�̊m�F
        assertTrue(LogUTUtil.checkTrace("hoge"));
    
    }

    /**
     * testGetLogger01()
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(�O�����) logger:-<br>
     * <br>
     * ���Ғl�F(�߂�l) LogFactory.getLog(DefaultExceptionHandler.class)�Ɠ���̃C���X�^���X<br>
     * <br>
     * ����n1���݂̂̃e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLogger01() throws Exception {
        // �O����

        // �ėp��O�n���h��
        DefaultExceptionHandler handler = new DefaultExceptionHandler();

        // �e�X�g���{
        Log logger = handler.getLogger();

        // ����
        assertSame(LogFactory.getLog(DefaultExceptionHandler.class), logger);
    }

}
