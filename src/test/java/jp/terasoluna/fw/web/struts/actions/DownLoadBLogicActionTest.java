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

package jp.terasoluna.fw.web.struts.actions;

import java.util.Arrays;

import javax.servlet.ServletContext;

import jp.terasoluna.fw.service.thin.AbstractBLogicMapper;
import jp.terasoluna.fw.service.thin.AbstractBLogicMapperImpl01;
import jp.terasoluna.fw.service.thin.BLogicIO;
import jp.terasoluna.fw.service.thin.BLogicResources;
import jp.terasoluna.fw.service.thin.BLogicResult;
import jp.terasoluna.fw.web.struts.action.ActionMappingEx;
import jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

/**
 * {@link jp.terasoluna.fw.web.struts.actions.DownLoadBLogicAction} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �_�E�����[�h�������s���ꍇ��BLogic�̋N�����s���N���X�ł���B
 * <p>
 *
 * @see jp.terasoluna.fw.web.struts.actions.DownLoadBLogicAction
 */
public class DownLoadBLogicActionTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DownLoadBLogicActionTest.class);
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
        LogUTUtil.flush();
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
    public DownLoadBLogicActionTest(String name) {
        super(name);
    }

    /**
     * testProcessBLogicResult01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                ��resultObject��AbstractDownloadObject�p���N���X��ݒ肷�邱��<br>
     *         (����) request:MockHttpServletRequest<br>
     *         (����) response:MockHttpServletResponse<br>
     *         (���) mapBLogicResult�̖߂�l:AbstarctBLogicMapper�̃X�^�u<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) response:�X�g���[���ɒl���������܂�Ă��邱��<br>
     *
     * <br>
     * FileDownLoadUtil#download�����s����邱�Ƃ��m�F����e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testProcessBLogicResult01() throws Exception {
        // �O����
        // request
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);

        // DownloadBLogicAction
        DownloadBLogicAction<AbstractDownloadObject> action =
            new DownloadBLogicAction<AbstractDownloadObject>();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();
        AbstractBLogicMapper mapper = new AbstractBLogicMapperImpl01();

        // ServletContext
        ServletContext context = new MockServletContext();
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("blogicIO");
        resources.setBLogicIO(blogicIO);
        context.setAttribute(BLogicResources.BLOGIC_RESOURCES_KEY + "prefix",
                resources);
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix",
                mapper);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        // ActionMappingEx
        ActionMappingEx mappingEx = new ActionMappingEx();
        mappingEx.setPath("blogicIO");

        // BLogicResult��AbstractDownloadObject�p���N���X�ł���
        // DownloadString�N���X�̃C���X�^���X���Z�b�g
        DownloadString resultObject = new DownloadString("abc", "abc");
        BLogicResult result = new BLogicResult();
        result.setResultObject(resultObject);


        // �e�X�g���{
        action.processBLogicResult(result, request, response, mappingEx);

        // ����
        assertFalse(str.equals(Arrays
                .toString(response.getContentAsByteArray())));

    }

    /**
     * testProcessBLogicResult02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) result:BLogicResult<br>
     *                ��resultObject��AbstractDownloadObject�p���N���X��ݒ肷�邱��<br>
     *                ��resultString��"abc"��ݒ肷�邱��<br>
     *         (����) request:MockHttpServletRequest<br>
     *                ��pathInfo��"path"��ݒ肷�邱��<br>
     *         (����) response:MockHttpServletResponse<br>
     *         (���) mapBLogicResult�̖߂�l:AbstarctBLogicMapper�̃X�^�u<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) response:�X�g���[���ɒl���������܂�Ă��邱��<br>
     *           (��ԕω�) ���O�o��:���O���x���FWARN<br>
     *                          ���O���b�Z�[�W�Fresult string must not be set. path :path<br>
     *
     * <br>
     * FileDownLoadUtil#download�����s����邱�Ƃ��m�F����e�X�g
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testProcessBLogicResult02() throws Exception {
        // �O����
        // request
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // ����p
        String str = Arrays.toString(response.getContentAsByteArray());

        // ModuleUtil.getPrefix(req)�F"prefix"
        ModuleConfig moduleConfig = new ModuleConfigImpl01();
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        request.setPathInfo("path");

        // DownloadBLogicAction
        DownloadBLogicAction<AbstractDownloadObject> action =
            new DownloadBLogicAction<AbstractDownloadObject>();

        // servlet
        AbstractBLogicAction_ActionServletStub01 servlet =
            new AbstractBLogicAction_ActionServletStub01();
        AbstractBLogicMapper mapper = new AbstractBLogicMapperImpl01();

        // ServletContext
        ServletContext context = new MockServletContext();
        BLogicResources resources = new BLogicResources();
        BLogicIO blogicIO = new BLogicIO();
        blogicIO.setPath("blogicIO");
        resources.setBLogicIO(blogicIO);
        context.setAttribute(BLogicResources.BLOGIC_RESOURCES_KEY + "prefix", resources);
        context.setAttribute(BLogicIOPlugIn.BLOGIC_MAPPER_KEY + "prefix", mapper);
        servlet.setServletContext(context);

        UTUtil.setPrivateField(action, "servlet", servlet);

        // ActionMappingEx
        ActionMappingEx mappingEx = new ActionMappingEx();
        mappingEx.setPath("blogicIO");

        // BLogicResult��AbstractDownloadObject�p���N���X�ł���
        // DownloadString�N���X�̃C���X�^���X���Z�b�g
        DownloadString resultObject = new DownloadString("abc", "abc");
        BLogicResult result = new BLogicResult();
        result.setResultObject(resultObject);
        result.setResultString("abc");


        // �e�X�g���{
        action.processBLogicResult(result, request, response, mappingEx);

        // ����
        assertFalse(str.equals(Arrays.toString(response.getContentAsByteArray())));
        assertTrue(LogUTUtil.checkWarn("result string must not be set. path :path"));
    }
}
