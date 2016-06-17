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

package jp.terasoluna.fw.web.taglib;

import java.io.BufferedReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * WriteTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * <br>
 */
public class WriteTagTest extends TestCase {

    // �e�X�g�Ώ�
    WriteTag tag = null;

    // PageContext��Bean���i�[����ۂ̃L�[��
    private static final String LOOKUP_BEAN = "lookup_bean";

    /**
     * Constructor for BodyTagTest.
     * @param arg0
     */
    public WriteTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (WriteTag) TagUTUtil.create(WriteTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoStartTag01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * 
     * ���͒l
     * ignore=true<br>
     * RequestUtils.lookup(PageContext, name, scope)=null<br>
     * value=�[<br>
     * replaceNullToNbsp=�[<br>
     * filter=�[<br>
     * replaceSpToNbsp=�[<br>
     * fillColumn=�[<br>
     * replaceLTtoBR=�[<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=�|<br>
     * 
     * bean�v���p�e�B�ɉ��s�R�[�h�i�������͕��A�����j���܂܂�Ă����Ƃ��A
     * <br>�ɒu�����鏈���𖳌��ɂ��邽�߂̑�����true�ł��w�肵��bean��
     * null�Ŏ擾�����ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag01() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = null;

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(Tag.SKIP_BODY, result);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());

    }

    /**
     * testDoStartTag02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * 
     * ���͒l
     * ignore=true<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value=null<br>
     * replaceNullToNbsp=true<br>
     * filter=�[<br>
     * replaceSpToNbsp=�[<br>
     * fillColumn=�[<br>
     * replaceLTtoBR=�[<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������u&nbsp;�v<br>
     * 
     * bean�v���p�e�B�ɉ��s�R�[�h�i�������͕��A�����j���܂܂�Ă����Ƃ��A
     * <br>�ɒu�����鏈���𖳌��ɂ��邽�߂̑�����true�ł��w�肵��bean��
     * ����Ɏ擾�ł���ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag02() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue(null);

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());

    }

    /**
     * testDoStartTag03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=null<br>
     * value=null<br>
     * replaceNullToNbsp=false<br>
     * filter=�[<br>
     * replaceSpToNbsp=�[<br>
     * fillColumn=�[<br>
     * replaceLTtoBR=�[<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=�|<br>
     * 
     * bean�v���p�e�B�ɉ��s�R�[�h�i�������͕��A�����j���܂܂�Ă����Ƃ��A
     * <br>�ɒu�����鏈���𖳌��ɂ��邽�߂̑�����false�ł��w�肵��bean
     * ��null�̏ꍇ�̃e�X�g�P�[�X
     * ���Ȃ����̃e�X�g�P�[�X�ł�null�������͋󕶎���&nbsp;�ƒu�����邩�ǂ���
     * ���肷�邽�߂̑�����false�ł������ꍇ�̃e�X�g�P�[�X���܂ށB<br>
     */
    public void testDoStartTag03() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue(null);

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());

    }

    /**
     * testDoStartTag04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=null<br>
     * value=null<br>
     * replaceNullToNbsp=true<br>
     * filter=�[<br>
     * replaceSpToNbsp=�[<br>
     * fillColumn=�[<br>
     * replaceLTtoBR=�[<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=�|<br>
     * 
     * bean�v���p�e�B�ɉ��s�R�[�h�i�������͕��A�����j���܂܂�Ă����Ƃ��A
     * <br>�ɒu�����鏈���𖳌��ɂ��邽�߂̑�����false�ł��w�肵��bean��
     * null�̏ꍇ�̃e�X�g�P�[�X
     * ���Ȃ����̃e�X�g�P�[�X�ł�null�������͋󕶎���&nbsp;�ƒu�����邩�ǂ���
     * ���肷�邽�߂̑�����true�ł������ꍇ�̃e�X�g�P�[�X���܂ށB<br>
     */
    public void testDoStartTag04() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = null;

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value=""�i�󕶎��j<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=�|<br>
     * 
     * bean����擾�����v���p�e�B�l���󕶎��ŁA����null�������͋󕶎���&nbsp;
     * �ƒu�����邩�ǂ������肷�邽�߂̑�����false�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag05() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());

    }

    /**
     * testDoStartTag06�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value=""�i�󕶎��j<br>
     * replaceNullToNbsp=true<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������u&nbsp;�v<br>
     * 
     * bean����擾�����v���p�e�B�l���󕶎��ŁA����null�������͋󕶎���&nbsp;
     * �ƒu�����邩�ǂ������肷�邽�߂̑�����true�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag06() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());

    }

    /**
     * testDoStartTag07�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="<>&"'"<br>
     * replaceNullToNbsp=false<br>
     * filter=true<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������u&lt;&gt;&amp;&quat;&#39�v<br>
     * 
     * bean����擾�����v���p�e�B�l��HTML��ł̓��ꕶ�����܂܂�A
     * �����ꕶ����HTML�ɑΉ����������ɒu�������邽�߂̑�����
     * true�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag07() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("<>&\"");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(true));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&lt;&gt;&amp;&quot;", reader.readLine());

    }

    /**
     * testDoStartTag08�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a b c"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������ua&nbsp;b&nbsp;c�v<br>
     * 
     * bean����擾�����v���p�e�B�l�ɔ��p�X�y�[�X���܂܂�A�����ꕶ����
     * HTML�ɑΉ����������ɒu�������邽�߂̑�����true�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag08() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a b c");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("a&nbsp;b&nbsp;c", reader.readLine());

    }

    /**
     * testDoStartTag09�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="1234567"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=3<br>
     * replaceLTtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������u123<br>456<br>7�v<br>
     * 
     * bean����擾�����v���p�e�B�l��bean�v���p�e�B����擾�����l���A
     * 1�J�����ɂĕ\������T�C�Y�����傫�������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag09() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("1234567");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(3));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("123<br>456<br>7", reader.readLine());

    }

    /**
     * testDoStartTag10�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a�i���s�R�[�h\n�j�i���s�R�[�h\r�jb"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=true<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������ua<br><br>b�v<br>
     * 
     * bean����擾�����v���p�e�B�l��bean�v���p�e�B����擾�����l�ɉ��s�R�[�h
     * ���܂܂�A�����s�R�[�h��<br>�ƒu�����A�o�b�t�@�Ɋi�[���ǂ������肷��
     * ���߂̑�����true�������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag10() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a\n\rb");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("a<br><br>b", reader.readLine());

    }

    /**
     * testDoStartTag11�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a�i���s�R�[�h�jb"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������uab�v<br>
     * 
     * bean����擾�����v���p�e�B�l��bean�v���p�e�B����擾�����l�ɉ��s�R�[�h
     * ���܂܂�A�����s�R�[�h�𖳎����A�o�b�t�@�Ɋi�[���邩�ǂ������肷�邽��
     * �̑�����true�������ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoStartTag11() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a\nb");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("ab", reader.readLine());

    }
    /**
     * testDoStartTag12�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="a b c"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=0<br>
     * replaceLTtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=null<br>
     * 
     * 1�J�����ɂĕ\������T�C�Y��0�������ꍇ�A
     * <br>���t�^����Ȃ����Ƃ��m�F����B<br>
     */
    public void testDoStartTag12() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("a b c");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(0));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("a&nbsp;b&nbsp;c", reader.readLine());

    }
    /**
     * testDoStartTag13�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l
     * ignore=false<br>
     * bean=null<br>
     * value=�[<br>
     * replaceNullToNbsp=true<br>
     * filter=�[<br>
     * replaceSpToNbsp=�[<br>
     * fillColumn=�[<br>
     * replaceLTtoBR=�[<br>
     * 
     * ���Ғl
     * �߂�l:JspException<br>
     * �o�͓��e=-<br>
     * 
     * �w�肵��bean��null�̏ꍇ�AJspException���������邱�Ƃ��m�F����B<br>
     * ���Ȃ����̃e�X�g�P�[�X�ł�null�������͋󕶎���&nbsp;�ƒu�����邩�ǂ���
     * ���肷�邽�߂̑�����true�ł������ꍇ�̃e�X�g�P�[�X���܂ށB<br>
     */
    public void testDoStartTag13() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = null;

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "property", "value");

        try {
            // �e�X�g���s
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            return;
        }
    }

    /**
     * testDoStartTag14�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="ab"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=false<br>
     * fillColumn=10<br>
     * replaceLTtoBR=true<br>
     * addBR=true<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������uab<br>�v<br>
     * 
     * addBR������true�̏ꍇ�A�v���p�e�B�l�̖����ɉ��s�R�[�h���t�^����邱�ƁB<br>
     */
    public void testDoStartTag14() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("ab");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(10));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(true));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("ab<br>", reader.readLine());
    }
    
    /**
     * testDoStartTag15�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=false<br>
     * RequestUtils.lookup(PageContext, name, scope)=not null<br>
     * value="123456"<br>
     * replaceNullToNbsp=false<br>
     * filter=false<br>
     * replaceSpToNbsp=true<br>
     * fillColumn=3<br>
     * replaceLFtoBR=false<br>
     * 
     * ���Ғl
     * �߂�l:int=SKIP_BODY<br>
     * �o�͓��e=������u123<br>456�v<br>
     * 
     * bean����擾�����v���p�e�B�l��bean�v���p�e�B����擾�����l���A
     * 1�J�����ɂĕ\������T�C�Y�����傫�������ꍇ�̃e�X�g�P�[�X�A������
     * fillColumn��value�̔{���łȂ��ꍇ�B<br>
     */
    public void testDoStartTag15() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("123456");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(false));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(3));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("123<br>456", reader.readLine());

    }

    /**
     * testDoStartTag16()�B<br>
     * 
     * (����n)<br>
     * 
     * bean����擾�����v���p�e�B�l��HTML��ł̓��ꕶ�����܂܂�A
     * �����ꕶ����HTML�ɑΉ����������ɒu�������邽�߂̑�����true�̏ꍇ�A
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}�����s���ꍇ�ɁA
     * (�T�j�^�C�Y��ł͂Ȃ�)
     * �T�j�^�C�Y�O�̕������ő}���ʒu�����肳��邱�Ƃ��m�F����B<br>
     */
    public void testDoStartTag16() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("123<567890");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(5));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("123&lt;5<br>67890", reader.readLine());

    }

    /**
     * testDoStartTag17�B<br>
     * 
     * (����n)<br>
     * 
     * replaceLFtoBR��true�ŁA���A
     * bean����擾�����v���p�e�B�l�ɉ��s�R�[�h���܂܂��ꍇ�A
     * (�v���p�e�B�l�̉��s�R�[�h�ɂ����<br>�^�O�����������ꍇ�A)
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}���ʒu����̍ۂɁA�v���p�e�B�l�̉��s�ʒu���ӎ�����邱��
     * (�v���p�e�B�l�̉��s�R�[�h�ɂ����<br>�^�O���������ꂽ�Ƃ��́A
     *  ��������A<br>�^�O�}���ʒu����̂��߂̕������J�E���g����蒼������)
     * ���m�F����B<br>
     */
    public void testDoStartTag17() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("123\n4567890");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(5));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("123<br>45678<br>90", reader.readLine());

    }

    /**
     * testDoStartTag18�B<br>
     * 
     * (����n)<br>
     * 
     * replaceLFtoBR��false�ŁA���A
     * bean����擾�����v���p�e�B�l�ɉ��s�R�[�h���܂܂��ꍇ�A
     * (�v���p�e�B�l�̉��s�R�[�h���폜�����ꍇ�A)
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}���ʒu����̍ۂɁA�v���p�e�B�l�̉��s�ʒu���ӎ�����Ȃ�����
     * (�폜�����v���p�e�B�l�̉��s�R�[�h�͕������Ƃ��Ă��J�E���g�����A
     *  <br>�^�O�}���ʒu�ɉe����^���Ȃ�����)
     * ���m�F����B<br>
     */
    public void testDoStartTag18() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("123\n4567890");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(5));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("12345<br>67890", reader.readLine());

    }

    /**
     * testDoStartTag19�B<br>
     * 
     * (����n)<br>
     * 
     * replaceLFtoBR��true�ŁA���A
     * bean����擾�����v���p�e�B�l�ɉ��s�R�[�h���܂܂�A
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}���ʒu(�}���\��ʒu�̒���)�ɁA���Ƃ�����s�R�[�h������ꍇ�ɁA
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}�����s��Ȃ�����
     * (���Ƃ��炠����s�ɂ��<br>�^�O�ƁA
     *  fillColumn�̐ݒ�ɂ��<br>�^�O���_�u���ďo�͂�����1�����o�͂��邱��)
     * ���m�F����B<br>
     */
    public void testDoStartTag19() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("12345\n67890");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(5));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("12345<br>67890", reader.readLine());

    }

    /**
     * testDoStartTag20�B<br>
     * 
     * (����n)<br>
     * 
     * replaceLFtoBR��false�ŁA���A
     * bean����擾�����v���p�e�B�l�ɉ��s�R�[�h���܂܂�A
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}���ʒu(�}���\��ʒu�̒���)�ɁA���Ƃ��Ɖ��s�R�[�h������ꍇ�A
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}�����s������
     * (���Ƃ��Ƃ�����s��<br>�^�O�ɂȂ炸�ɍ폜�����̂ŁA
     *  fillColumn�̐ݒ�ɂ��<br>�^�O���o�͂��邱��)
     * ���m�F����B<br>
     */
    public void testDoStartTag20() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("12345\n67890");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(5));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("12345<br>67890", reader.readLine());

    }

    /**
     * testDoStartTag21�B<br>
     * 
     * (����n)<br>
     * 
     * replaceSpToNbsp��true�ŁA���A
     * bean����擾�����v���p�e�B�l�ɃX�y�[�X���܂܂�A
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}���ʒu�̒��O�ɁA�X�y�[�X������ꍇ�ɁA
     * �������ʒu(�ϊ����ꂽ&nbsp;�̒���)��fillColumn�̐ݒ�ɂ��<br>�^�O�}�����s������
     * ���m�F����B<br>
     */
    public void testDoStartTag21() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("1234 5678 90");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(5));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1234&nbsp;<br>5678&nbsp;<br>90", reader.readLine());

    }

    /**
     * testDoStartTag22�B<br>
     * 
     * (����n)<br>
     * 
     * replaceSpToNbsp��false�ŁA���A
     * bean����擾�����v���p�e�B�l�ɃX�y�[�X���܂܂�A
     * fillColumn�̐ݒ�ɂ��<br>�^�O�}���ʒu�̒��O�ɁA�X�y�[�X������ꍇ�ɁA
     * �������ʒu(�ϊ�����Ȃ��X�y�[�X�̒���)��fillColumn�̐ݒ�ɂ��<br>�^�O�}�����s������
     * ���m�F����B<br>
     */
    public void testDoStartTag22() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("1234 5678 90");

        // PageContext�Ƀ|�b�v�A�b�v�\���p�X�N���v�g��`���o�͔���ϐ���ݒ�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(5));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("1234 <br>5678 <br>90", reader.readLine());

    }

    /**
     * testDoStartTag23�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean��property�ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��true�A
     * addBR��true�ŁA
     * bean����擾�����v���p�e�B�l��null�̏ꍇ�ɁA
     * &nbsp; ���o�͂���邱��
     * ���m�F����B<br>
     */
    public void testDoStartTag23() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue(null);

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());
    }

    /**
     * testDoStartTag24�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean��property�ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��true�A
     * addBR��true�ŁA
     * bean����擾�����v���p�e�B�l���󕶎���̏ꍇ�ɁA
     * &nbsp; ���o�͂���邱��
     * ���m�F����B<br>
     */
    public void testDoStartTag24() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("");

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());
    }

    // replaceNullToNbsp��true�AaddBR��false�Abean����擾�����v���p�e�B�l��null �� &nbsp; �̃e�X�g��testDoStartTag02
    // replaceNullToNbsp��true�AaddBR��false�Abean����擾�����v���p�e�B�l���󕶎��� �� &nbsp; �̃e�X�g��testDoStartTag06
    
    /**
     * testDoStartTag25�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean��property�ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��false�A
     * addBR��true�ŁA
     * bean����擾�����v���p�e�B�l��null�̏ꍇ�ɁA
     * �����o�͂���Ȃ�����
     * ���m�F����B<br>
     */
    public void testDoStartTag25() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue(null);

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag26�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean��property�ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��false�A
     * addBR��true�ŁA
     * bean����擾�����v���p�e�B�l���󕶎���̏ꍇ�ɁA
     * <br>���o�͂���邱��
     * ���m�F����B<br>
     */
    public void testDoStartTag26() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        WriteTag_BeanStub01 bean = new WriteTag_BeanStub01();
        bean.setValue("");

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "property", "value");
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<br>", reader.readLine());
    }

    // replaceNullToNbsp��false�AaddBR��false�Abean����擾�����v���p�e�B�l��null �� �o�͖��� �̃e�X�g��testDoStartTag03
    // replaceNullToNbsp��false�AaddBR��false�Abean����擾�����v���p�e�B�l���󕶎��� �� �o�͖��� �̃e�X�g��testDoStartTag05

    /**
     * testDoStartTag27�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean���̂��̂ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��true�A
     * addBR��true�ŁA
     * bean�̒l��null�̏ꍇ�ɁA
     * �����o�͂���Ȃ�����
     * ���m�F����B<br>
     */
    public void testDoStartTag27() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        String bean = null;

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag28�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean���̂��̂ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��true�A
     * addBR��true�ŁA
     * bean�̒l���󕶎���̏ꍇ�ɁA
     * &nbsp; ���o�͂���邱��
     * ���m�F����B<br>
     */
    public void testDoStartTag28() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        String bean = "";

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());
    }

    // replaceNullToNbsp��true�AaddBR��false�Abean�̒l��null �� �o�͖��� �̃e�X�g��testDoStartTag01

    /**
     * testDoStartTag29�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean���̂��̂ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��true�A
     * addBR��false�ŁA
     * bean�̒l���󕶎���̏ꍇ�ɁA
     * &nbsp; ���o�͂���邱��
     * ���m�F����B<br>
     */
    public void testDoStartTag29() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        String bean = "";

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(true));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("&nbsp;", reader.readLine());
    }

    /**
     * testDoStartTag30�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean���̂��̂ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��false�A
     * addBR��true�ŁA
     * bean�̒l��null�̏ꍇ�ɁA
     * �����o�͂���Ȃ�����
     * ���m�F����B<br>
     */
    public void testDoStartTag30() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        String bean = null;

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());
    }

    /**
     * testDoStartTag31�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean���̂��̂ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��false�A
     * addBR��true�ŁA
     * bean�̒l���󕶎���̏ꍇ�ɁA
     * <br> ���o�͂���邱��
     * ���m�F����B<br>
     */
    public void testDoStartTag31() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        String bean = "";

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<br>", reader.readLine());
    }

    /**
     * testDoStartTag32�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean���̂��̂ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��false�A
     * addBR��false�ŁA
     * bean�̒l��null�̏ꍇ�ɁA
     * �����o�͂���Ȃ�����
     * ���m�F����B<br>
     */
    public void testDoStartTag32() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        String bean = null;

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());
    }


    /**
     * testDoStartTag33�B<br>
     * 
     * (����n)<br>
     * 
     * ���͒l�̎Q�Ɛ悪bean���̂��̂ł���Ƃ��́A
     * �EreplaceNullToNbsp��true/false
     * �EaddBR��true/false
     * �E���͒l��null/�󕶎�
     * �̑g�ݍ��킹�e�X�g�B
     * (��������d�l�̑g�ݍ��킹�e�X�g�B)
     * (���̃e�X�g�Ŏ��{���Ă��Ȃ��p�^�[���̂ݎ��{�B)
     * 
     * replaceNullToNbsp��false�A
     * addBR��false�ŁA
     * bean�̒l���󕶎���̏ꍇ�ɁA
     * �����o�͂���Ȃ�����
     * ���m�F����B<br>
     */
    public void testDoStartTag33() throws Exception {

        // �J�X�^���^�O�̃C���X�^���X�Ɋ֘A�t�����ꂽPageContext���擾
        PageContext pc = TagUTUtil.getPageContext(tag);

        // PageContext�Ƀe�X�g�f�[�^�Ƃ��Ċi�[����Bean�̐ݒ�
        String bean = "";

        // PageContext��Bean���i�[�B
        pc.setAttribute(LOOKUP_BEAN, bean, PageContext.SESSION_SCOPE);

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", LOOKUP_BEAN);
        UTUtil.setPrivateField(tag, "scope", "session");
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "addBR", new Boolean(false));

        // �e�X�g���s
        int result = tag.doStartTag();

        // �e�X�g���ʊm�F
        assertEquals(result, Tag.SKIP_BODY);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertNull(reader.readLine());
    }

    /**
     * testRelease01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FC<br>
     * 
     * ���͒l
     * filter=false<br>
     * replaceNullToNbsp=false<br>
     * replaceSpToNbsp=false<br>
     * replaceLFtoBR=false<br>
     * ignore=true<br>
     * name="name"<br>
     * property="property"<br>
     * scope="scope"<br>
     * fillColumn=100<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * filter=true<br>
     * replaceNullToNbsp=true<br>
     * replaceSpToNbsp=true<br>
     * replaceLFtoBR=true<br>
     * ignore=false<br>
     * name=Null<br>
     * property=Null<br>
     * scope=Null<br>
     * fillColumn=-1<br>
     * 
     * �O������Ƃ��Đݒ肵���e�l���A���s���Ɋe�����l������������邱�Ƃ��m�F����<br>
     */
    public void testRelease01() throws Exception {

        //�e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));
        UTUtil.setPrivateField(tag, "name", "name");
        UTUtil.setPrivateField(tag, "property", "property");
        UTUtil.setPrivateField(tag, "scope", "scope");
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(100));

        //�e�X�g���s
        tag.release();

        //�e�X�g���ʊm�F
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "filter"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceNullToNbsp"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "replaceLFtoBR"));
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "ignore"));
        assertNull(UTUtil.getPrivateField(tag, "name"));
        assertNull(UTUtil.getPrivateField(tag, "property"));
        assertNull(UTUtil.getPrivateField(tag, "scope"));
        assertEquals(-1, UTUtil.getPrivateField(tag, "fillColumn"));

    }

    /**
     * testSetFilter01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l Filter="filter"<br>
     * 
     * ���Ғl �߂�l:void<br>
     * ��ԕω�:filter=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetFilter01() throws Exception {

        //�e�X�g���s
        tag.setFilter(false);

        //�e�X�g���ʊm�F
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "filter"));

    }

    /**
     * testGetFilter01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * filter=false
     * 
     * ���Ғl
     * �߂�l:boolean<br>
     * filter=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetFilter01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "filter", new Boolean(false));

        //�e�X�g���ʊm�F
        assertFalse(tag.getFilter());

    }

    /**
     * testSetReplaceNullToNbsp01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * replaceNullToNbsp="replaceNullToNbsp"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:replaceNullToNbsp=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetReplaceNullToNbsp01() throws Exception {

        //�e�X�g���s
        tag.setReplaceNullToNbsp(false);

        //�e�X�g���ʊm�F
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceNullToNbsp"));

    }

    /**
     * testGetReplaceNullToNbsp01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * replaceNullToNbsp=false
     * 
     * ���Ғl
     * �߂�l:boolean<br>
     * replaceNullToNbsp=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetReplaceNullToNbsp01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "replaceNullToNbsp", new Boolean(false));

        //�e�X�g���ʊm�F
        assertFalse(tag.getReplaceNullToNbsp());

    }

    /**
     * testSetReplaceSpToNbsp01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * replaceSpToNbsp="replaceSpToNbsp"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:replaceSpToNbsp=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetReplaceSpToNbsp01() throws Exception {

        //�e�X�g���s
        tag.setReplaceSpToNbsp(false);

        //�e�X�g���ʊm�F
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceSpToNbsp"));

    }

    /**
     * testGetReplaceSpToNbsp01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * replaceSpToNbsp=false
     * 
     * ���Ғl
     * �߂�l:boolean<br>
     * replaceSpToNbsp=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetReplaceSpToNbsp01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "replaceSpToNbsp", new Boolean(false));

        //�e�X�g���ʊm�F
        assertFalse(tag.getReplaceSpToNbsp());

    }

    /**
     * testSetReplaceLFtoBR01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * replaceLFtoBR="replaceLFtoBR"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:replaceLFtoBR=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetReplaceLFtoBR01() throws Exception {

        //�e�X�g���s
        tag.setReplaceLFtoBR(false);

        //�e�X�g���ʊm�F
        assertFalse((Boolean) UTUtil.getPrivateField(tag, "replaceLFtoBR"));

    }

    /**
     * testGetReplaceLFtoBR01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * replaceLFtoBR=false
     * 
     * ���Ғl
     * �߂�l:boolean<br>
     * replaceLFtoBR=false<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetReplaceLFtoBR01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "replaceLFtoBR", new Boolean(false));

        //�e�X�g���ʊm�F
        assertFalse(tag.getReplaceLFtoBR());

    }

    /**
     * testSetIgnore01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore="ignore"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:ignore=true<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetIgnore01() throws Exception {

        //�e�X�g���s
        tag.setIgnore(true);

        //�e�X�g���ʊm�F
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "ignore"));

    }

    /**
     * testGetignore01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * ignore=true
     * 
     * ���Ғl
     * �߂�l:boolean<br>
     * ignore=true<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetIgnore01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "ignore", new Boolean(true));

        //�e�X�g���ʊm�F
        assertTrue(tag.getIgnore());

    }

    /**
     * testSetName01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * name="name"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:name="name"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetName01() throws Exception {

        //�e�X�g���s
        tag.setName("name");

        //�e�X�g���ʊm�F
        assertEquals("name", UTUtil.getPrivateField(tag, "name"));

    }

    /**
     * testGetName01�B<br>0
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * name="name"
     * 
     * ���Ғl
     * �߂�l:String<br>
     * name="name"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetName01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "name", "name");

        //�e�X�g���ʊm�F
        assertEquals("name", tag.getName());

    }

    /**
     * testSetProperty01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * property="property"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:property="property"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetProperty01() throws Exception {

        //�e�X�g���s
        tag.setProperty("property");

        //�e�X�g���ʊm�F
        assertEquals("property", UTUtil.getPrivateField(tag, "property"));

    }

    /**
     * testGetProperty01�B<br>
     * 
     * (����n)<br>
     * �ϓ_:A<br>
     * 
     * ���͒l
     * property="property"
     * 
     * ���Ғl
     * �߂�l:String<br>
     * property="property"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetProperty01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "property", "property");

        //�e�X�g���ʊm�F
        assertEquals("property", tag.getProperty());

    }

    /**
     * testSetScope01�B<br>
     * 
     * (����n)<br>
     * �ϓ_:A<br>
     * 
     * ���͒l
     * scope="scope"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:scope="scope"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetScope01() throws Exception {

        //�e�X�g���s
        tag.setScope("scope");

        //�e�X�g���ʊm�F
        assertEquals("scope", UTUtil.getPrivateField(tag, "scope"));

    }

    /**
     * testGetScope01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * scope="scope"
     * 
     * ���Ғl
     * �߂�l:String<br>
     * ��ԕω�:scope="scope"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetScope01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "scope", "scope");

        //�e�X�g���ʊm�F
        assertEquals("scope", tag.getScope());

    }

    /**
     * testSetFillColumn01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * fillColumn="fillColumn"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:fillColumn=100<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetFillColumn01() throws Exception {

        //�e�X�g���s
        tag.setFillColumn(100);

        //�e�X�g���ʊm�F
        assertEquals(100, UTUtil.getPrivateField(tag, "fillColumn"));

    }

    /**
     * testGetFillColumn01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * fillColumn=false
     * 
     * ���Ғl
     * �߂�l:boolean<br>
     * fillColumn=100<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetFillColumn01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "fillColumn", new Integer(100));

        //�e�X�g���ʊm�F
        assertEquals(100, tag.getFillColumn());

    }

    /**
     * testSetAddBR01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * addBR="true"<br>
     * 
     * ���Ғl
     * �߂�l:void<br>
     * ��ԕω�:addBR="true"<br>
     * 
     * �Z�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testSetAddBR01() throws Exception {

        //�e�X�g���s
        tag.setAddBR(true);

        //�e�X�g���ʊm�F
        assertTrue((Boolean) UTUtil.getPrivateField(tag, "addBR"));

    }

    /**
     * testGetAddBR01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l
     * addBR="true"
     * 
     * ���Ғl
     * �߂�l:boolean<br>
     * addBR="true"<br>
     * 
     * �Q�b�g�����l���m�F����e�X�g�P�[�X<br>
     */
    public void testGetAddBR01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        UTUtil.setPrivateField(tag, "addBR", new Boolean(true));

        //�e�X�g���ʊm�F
        assertTrue(tag.getAddBR());

    }

}
