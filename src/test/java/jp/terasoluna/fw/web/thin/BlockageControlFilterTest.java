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

import jp.terasoluna.utlib.MockFilterConfig;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;


/**
 * {@link jp.terasoluna.fw.web.thin.BlockageControlFilter} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �Ɩ��Ǐ�Ԃ��ǂ����̃`�F�b�N���s���B<br>
 * �A�N�Z�X�����p�X���Ɩ��Ǐ�Ԃ������ꍇ�́A{@link BlockageException} ���X���[����B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.thin.BlockageControlFilter
 */
public class BlockageControlFilterTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BlockageControlFilterTest.class);
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
        UTUtil.setPrivateField(BlockageControlFilter.class, 
                               "controller",
                               null);   
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public BlockageControlFilterTest(String name) {
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
     *         (���) config:not null<br>
     *         (���) getController():BlockageControlFilter_BlockageControllerStub01�C���X�^���X<br>
     *         (���) controller:BlockageControlFilter_BlockageControllerStub01�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) config:�����ŗ^����FilterConfig�Ɠ���̃C���X�^���X<br>
     *         (��ԕω�) getController():�Ăяo����Ȃ�<br>
     *         (��ԕω�) controller:�O������Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * controller��null�Ŗ����ꍇ�Aconroll�����ŗ^����config���ݒ肳��AgetController���Ăяo����Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit01() throws Exception {
        // �O����
    	MockFilterConfig existingConfig = new MockFilterConfig();
    	BlockageController existingController = 
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	UTUtil.setPrivateField(BlockageControlFilter.class,
                    			"controller",
                        		existingController);
    	// �t�B���^�̗p��
    	BlockageControlFilter_BlockageControlFilterStub01 filter = 
    		new BlockageControlFilter_BlockageControlFilterStub01();
    	
    	filter.setConfig(existingConfig);
    	
    	// �V�����ݒ肳���R���t�B�O
    	MockFilterConfig newConfig = new MockFilterConfig();
    	
    	BlockageControlFilter_BlockageControllerStub01 newController = 
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	filter.newController = newController;
    	
        // �e�X�g���{
    	filter.init(newConfig);
        // ����
    	assertSame(newConfig,UTUtil.getPrivateField(filter,"config"));
    	assertFalse(filter.isCalled);
    	assertSame(existingController,
    				UTUtil.getPrivateField(BlockageControlFilter.class,
    						"controller"));
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
     *         (���) config:null<br>
     *         (���) getController():BlockageControlFilter_BlockageControllerStub01�C���X�^���X<br>
     *         (���) controller:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) config:�����ŗ^����FilterConfig�Ɠ���̃C���X�^���X<br>
     *         (��ԕω�) getController():�Ăяo�����<br>
     *         (��ԕω�) controller:getController()���瓾����C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * controller��null�̏ꍇ�A�����ŗ^����config���ݒ肳��AgetController���Ăяo����A�t�B�[���h�ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInit02() throws Exception {
        // �O����
    	// �t�B���^�̗p��
    	BlockageControlFilter_BlockageControlFilterStub01 filter =
    		new BlockageControlFilter_BlockageControlFilterStub01();
    	
    	// �V�����ݒ肳���R���t�B�O
    	MockFilterConfig newConfig = new MockFilterConfig();
    	
    	BlockageControlFilter_BlockageControllerStub01 newController =
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	filter.newController = newController;
    	
        // �e�X�g���{
    	filter.init(newConfig);
        // ����
    	assertSame(newConfig,UTUtil.getPrivateField(filter,"config"));
    	assertTrue(filter.isCalled);
    	assertSame(newController,
        			UTUtil.getPrivateField(BlockageControlFilter.class,
        					"controller"));
    }

    /**
     * testGetBlockageController01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) controller:BlockageControlFilter_BlockageControllerStub01�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) BlockageController:�O������Őݒ肵���C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetBlockageController01() throws Exception {
        // �O����
    	BlockageController existingController = 
    		new BlockageControlFilter_BlockageControllerStub01();
    	
    	UTUtil.setPrivateField(BlockageControlFilter.class,
                    			"controller",
                                existingController);

        // �e�X�g���{
        BlockageController actual =
            BlockageControlFilter.getBlockageController();
        
        // ����
        assertSame(existingController,actual);
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
     *         (���) BLOCKAGE_THRU_KEY:"aaaaa"<br>
     *         (���) blockageController:BlockageControlFilter_BlockageControllerStub01<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) BLOCKAGE_THRU_KEY:�O������Ɠ����l<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * request�̑����l�ł���BLOCKAGE_THRU_KEY��null�łȂ��ꍇ�A�ʋƖ��ւ̑J�ڃ`�F�b�N���s�킸�ɏI�����邱�ƁB<br>
     * FilterChain#doFilter()���\�b�h�����s���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter01() throws Exception {
        // �O����
        BlockageController newController =
            new BlockageControlFilter_BlockageControllerStub01();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setAttribute("BLOCKAGE_THRU_KEY","aaaaa");
        
        BlockageControlFilter_FilterChainStub01 filterChain =
            new BlockageControlFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request,response,filterChain);
        // ����
        assertEquals("aaaaa", request.getAttribute("BLOCKAGE_THRU_KEY"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) BLOCKAGE_THRU_KEY:null<br>
     *         (���) blockageController:BlockageControlFilter_BlockageControllerStub01<br>
     *         (���) .isCheckRequired():false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) BLOCKAGE_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��false�̏ꍇ�A�ȍ~�̃`�F�b�N���s�킸�Arequest�̑����lBLOCKAGE_THRU_KEY��true��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter02() throws Exception {
        // �O����
        BlockageController newController = 
            new BlockageControlFilter_BlockageControllerStub01();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain =
            new BlockageControlFilter_FilterChainStub01();
        
        // �e�X�g���{
        filter.doFilter(request,response,filterChain);
        // ����
        assertEquals("true", request.getAttribute("BLOCKAGE_THRU_KEY"));
        assertTrue(filterChain.isCalled);
    }

    /**
     * testDoFilter03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) req:not null<br>
     *         (����) res:not null<br>
     *         (����) chain:not null<br>
     *         (���) BLOCKAGE_THRU_KEY:null<br>
     *         (���) blockageController:BlockageControlFilter_BlockageControllerStub02<br>
     *         (���) .isCheckRequired():true<br>
     *         (���) .isBlockaded():true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) BLOCKAGE_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:BlockageException�F<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��ture�ŁA�Ɩ��ǃ`�F�b�N��true�̏ꍇ�Arequest�̑����lBLOCKAGE_THRU_KEY��ݒ肵��BlockageException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter03() throws Exception {
        // �O����
        BlockageController newController = 
            new BlockageControlFilter_BlockageControllerStub02();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain =
            new BlockageControlFilter_FilterChainStub01();
        
        // �e�X�g���{
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (BlockageException ex) {
            // ����
            assertEquals("true", request.getAttribute("BLOCKAGE_THRU_KEY"));
            assertFalse(filterChain.isCalled);
        }
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
     *         (���) BLOCKAGE_THRU_KEY:null<br>
     *         (���) blockageController:BlockageControlFilter_BlockageControllerStub03<br>
     *         (���) .isCheckRequired():true<br>
     *         (���) .isBlockaded():false<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) BLOCKAGE_THRU_KEY:"true"<br>
     *         (��ԕω�) chain.doFilter:���s����<br>
     *         
     * <br>
     * �ʋƖ��ւ̑J�ڃ`�F�b�N��true�A�Ɩ��ǃ`�F�b�N��false�̏ꍇ�Arequest�̑����lBLOCKAGE_THRU_KEY��ݒ肵�ď������I�����邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter04() throws Exception {
        // �O����
        BlockageController newController = 
            new BlockageControlFilter_BlockageControllerStub03();
        
        BlockageControlFilter filter = new BlockageControlFilter();
        UTUtil.setPrivateField(filter, "controller", newController);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain = 
            new BlockageControlFilter_FilterChainStub01();
        
        // �e�X�g���{]
        filter.doFilter(request, response, filterChain);
        // ����
        assertEquals("true", request.getAttribute("BLOCKAGE_THRU_KEY"));
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
     *         (���) BLOCKAGE_THRU_KEY:null<br>
     *         (���) blockageController:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) chain.doFilter:���s���Ȃ�<br>
     *         (��ԕω�) ��O:NullPointerException�F<br>
     *         
     * <br>
     * blockageController��null�̏ꍇ�ANullPointerException���������邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoFilter05() throws Exception {
        // �O����
        
        BlockageControlFilter filter = new BlockageControlFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test/logon.do");
        request.setContextPath("/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        BlockageControlFilter_FilterChainStub01 filterChain = 
            new BlockageControlFilter_FilterChainStub01();
        
        // �e�X�g���{
        try {
            filter.doFilter(request, response, filterChain);
            fail();
        } catch (NullPointerException e) {
            // ����
            assertFalse(filterChain.isCalled);
        }
    }

    /**
     * testGetControllerClass01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) BLOCKAGE_CONTROLLER_CLASS:BlockageController.class<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Class:�O������Őݒ肵���C���X�^���X�Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetControllerClass01() throws Exception {
        // �O����
        BlockageControlFilter filter = new BlockageControlFilter();
        
        // �e�X�g���{
        Class actual = filter.getControllerClass();
        
        // ����
        assertEquals(BlockageController.class,actual);
    }

    /**
     * testGetErrorCode01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) BLOCKAGE_CONTROLLER_ERROR:"errors.blockage.controller"<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"errors.blockage.controller"<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrorCode01() throws Exception {
        // �O����
        BlockageControlFilter filter = new BlockageControlFilter();
        
        // �e�X�g���{
        String actual = filter.getErrorCode();
        
        // ����
        assertEquals("errors.blockage.controller",actual);
    }

    /**
     * testGetDefaultControllerBeanId01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) DEFAULT_BLOCKAGE_BEAN_ID:"blockageController"<br>
     *                ��static final<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"blockageController"<br>
     *         
     * <br>
     * ����n1���̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetDefaultControllerBeanId01() throws Exception {
        // �O����
        BlockageControlFilter filter = new BlockageControlFilter();
        
        // �e�X�g���{
        String actual = filter.getDefaultControllerBeanId();
        
        // ����
        assertEquals("blockageController",actual);
    }

}
