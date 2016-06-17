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

package jp.terasoluna.fw.web.struts.taglib;

import javax.servlet.jsp.PageContext;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * FormTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class FormTagTest extends TestCase {

    //�e�X�g�Ώ�
    FormTag tag = null;

    /**
     * Constructor for FormTagTest.
     * @param arg0
     */
    public FormTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (FormTag) TagUTUtil.create(FormTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * renderFormStartElement01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l<br>
     * beanName=null<br>
     * method=null<br>
     * styleClass=null<br>
     * enctype=null<br>
     * onreset=null<br>
     * onsubmit=null<br>
     * style=null<br>
     * styleId=null<br>
     * target=null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:Form�^�O��name=null,method=post,action=not null�݂̂��ݒ肳���B<br>
     * <br>
     * styleClass, enctype, onreset, onsubmit, style<br>
     * styleId, target��null�̏ꍇ��Form�^�O�ɂ͑}������Ȃ��B<br>
     */
    public void testRenderFormStartElement01() throws Exception {
        // �e�X�g�ݒ�
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");
        
        // �e�X�g���s
        String result = tag.renderFormStartElement();

        // �e�X�g���ʊm�F
        // �����_��ID�̑O���m�F
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"null\" method=\"post\" action=\"contextPath/action?r=",
                ward);
    } /* testRenderFormStartElement01 End */

    /**
     * renderFormStartElement02�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA<br>
     * <br>
     * ���͒l<br>
     * beanName=not null<br>
     * method=not null<br>
     * styleClass=not null<br>
     * enctype=not null<br>
     * onreset=not null<br>
     * onsubmit=not null<br>
     * style=not null<br>
     * styleId=not null<br>
     * target=not null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:Form�^�O��beanName=not null, method=not null, styleClass=not null<br>
     * enctype=not null, onreset=not null, onsubmit=not null, style=not null<br>
     * styleId=not null, target=not null�Ƃ��Đݒ肳���B<br>
     * <br>
     * styleClass, enctype, onreset, onsubmit, style<br>
     * styleId, target��not null�̏ꍇ��Form�^�O�ɑ}�������B<br>
     */
    public void testRenderFormStartElement02() throws Exception {
        // �e�X�g�ݒ�
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");

        UTUtil.setPrivateField(tag, "beanName", "beanName");
        UTUtil.setPrivateField(tag, "method", "method");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "enctype", "enctype");
        UTUtil.setPrivateField(tag, "onreset", "onreset");
        UTUtil.setPrivateField(tag, "onsubmit", "onsubmit");
        UTUtil.setPrivateField(tag, "style", "style");
        UTUtil.setPrivateField(tag, "styleId", "styleId");
        UTUtil.setPrivateField(tag, "target", "target");

        // �e�X�g���s
        String result = tag.renderFormStartElement();

        // �e�X�g���ʊm�F
        // �����_��ID�̑O���m�F
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"beanName\" method=\"method\" action=\"contextPath/action?r=",
                ward);
        // �����_��ID�̑O���m�F
        index = result.indexOf("class=");
        ward = result.substring(index);
        assertEquals(
                "class=\"styleClass\" enctype=\"enctype\" onreset=\"onreset\" onsubmit=\"onsubmit\" style=\"style\" id=\"styleId\" target=\"target\">",
                ward);
    } /* testRenderFormStartElement02 End */

    /**
     * renderFormStartElement03�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l<br>
     * beanName=""<br>
     * method=""<br>
     * styleClass=""<br>
     * enctype=""<br>
     * onreset=""<br>
     * onsubmit=""<br>
     * style=""<br>
     * styleId=""<br>
     * target=""<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:Form�^�O��beanName="", method="", styleClass=""<br>
     * enctype="", onreset="", onsubmit="", style=""<br>
     * styleId="", target=""�Ƃ��Đݒ肳���B<br>
     * <br>
     * styleClass, enctype, onreset, onsubmit, style<br>
     * styleId, target��""�̏ꍇ��Form�^�O�ɑ}�������B<br>
     */
    public void testRenderFormStartElement03() throws Exception {
        // �e�X�g�ݒ�
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");

        UTUtil.setPrivateField(tag, "beanName", "");
        UTUtil.setPrivateField(tag, "method", "");
        UTUtil.setPrivateField(tag, "styleClass", "");
        UTUtil.setPrivateField(tag, "enctype", "");
        UTUtil.setPrivateField(tag, "onreset", "");
        UTUtil.setPrivateField(tag, "onsubmit", "");
        UTUtil.setPrivateField(tag, "style", "");
        UTUtil.setPrivateField(tag, "styleId", "");
        UTUtil.setPrivateField(tag, "target", "");

        // �e�X�g���s
        String result = tag.renderFormStartElement();

        // �e�X�g���ʊm�F
        // �����_��ID�̑O���m�F
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"\" method=\"\" action=\"contextPath/action?r=",
                ward);
        // �����_��ID�̑O���m�F
        index = result.indexOf("class=");
        ward = result.substring(index);
        assertEquals(
                "class=\"\" enctype=\"\" onreset=\"\" onsubmit=\"\" style=\"\" id=\"\" target=\"\">",
                ward);
    } /* testRenderFormStartElement03 End */

    /**
     * renderFormStartElement04�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * <br>
     * ���͒l<br>
     * beanName=not null<br>
     * method=not null<br>
     * styleClass=not null<br>
     * enctype=null<br>
     * onreset=""<br>
     * onsubmit=not null<br>
     * style=null<br>
     * styleId=""<br>
     * target=not null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:Form�^�O��beanName=not null, method=not null, styleClass=not null<br>
     * onreset="", onsubmit=not null<br>
     * styleId="", target=not null�Ƃ��Đݒ肳���B<br>
     * <br>
     * styleClass, onsubmit, target��not null�̏ꍇ��Form�^�O�ɑ}�������B<br>
     * onreset, styleId��""�̏ꍇ��Form�^�O�ɑ}�������B<br>
     */
    public void testRenderFormStartElement04() throws Exception {
        // �e�X�g�ݒ�
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");

        UTUtil.setPrivateField(tag, "beanName", "beanName");
        UTUtil.setPrivateField(tag, "method", "method");
        UTUtil.setPrivateField(tag, "styleClass", "styleClass");
        UTUtil.setPrivateField(tag, "onreset", "");
        UTUtil.setPrivateField(tag, "onsubmit", "onsubmit");
        UTUtil.setPrivateField(tag, "styleId", "");
        UTUtil.setPrivateField(tag, "target", "target");

        // �e�X�g���s
        String result = tag.renderFormStartElement();

        // �e�X�g���ʊm�F
        // �����_��ID�̑O���m�F
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals(
                "<form name=\"beanName\" method=\"method\" action=\"contextPath/action?r=",
                ward);
        // �����_��ID�̑O���m�F
        index = result.indexOf("class=");
        ward = result.substring(index);
        assertEquals(
                "class=\"styleClass\" onreset=\"\" onsubmit=\"onsubmit\" id=\"\" target=\"target\">",
                ward);
    } /* testRenderFormStartElement04 End */

    /**
     * getActionMappingURL01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * url=NotNull("contextPath/action")<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String=������
     * �ucontextPath/action?r=[�L���b�V�������p�����_��ID]�v<br>
     * <br>
     * url��Not Null�A���A�����u�H�v��url��
     * �ЂƂ��܂܂�Ă��Ȃ��ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testGetActionMappingURL01() throws Exception {

        // �e�X�g�ݒ�
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action");
        PageContext pc = TagUTUtil.getPageContext(tag);

        // �e�X�g���s
        String result = tag.getActionMappingURL("action", pc);

        // �e�X�g���ʊm�F
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals("contextPath/action?r=", ward);

    } /* testGetActionMappingURL01 End */

    /**
     * getActionMappingURL02�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FF<br>
     * <br>
     * ���͒l<br>
     * url=NotNull("contextPath/action?cz=75")<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String=������
     * �ucontextPath/action?r=[�L���b�V�������p�����_��ID]�v<br>
     * <br>
     * url��Not Null�A���A�����u�H�v��url��
     * �܂܂�Ă���ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testGetActionMappingURL02() throws Exception {

        // �e�X�g�ݒ�
        TagUTUtil.setContextPath(tag, "contextPath");
        tag.setAction("action?cz=75");
        PageContext pc = TagUTUtil.getPageContext(tag);

        // �e�X�g���s
        String result = tag.getActionMappingURL("action?cz=75", pc);

        // �e�X�g���ʊm�F
        int index = result.indexOf("r=");
        String ward = result.substring(0, index + 2);
        assertEquals("contextPath/action?cz=75&r=", ward);

    } /* testGetActionMappingURL02 End */

}
