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

package jp.terasoluna.fw.web.codelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.AbstractMultilingualCodeListLoader} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * <br>
 * �E�O�����<br>
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.AbstractMultilingualCodeListLoader
 */
public class AbstractMultilingualCodeListLoaderTest extends TestCase {
    
    private static Map<Locale, List<CodeBean>> CODE_LIST01;
    static {
        CODE_LIST01 = new HashMap<Locale, List<CodeBean>>();
        
        List<CodeBean> list;
        
        CodeBean b;
        // ja
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("ja_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("ja_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("ja_3");
        list.add(b);
        
        CODE_LIST01.put(Locale.JAPANESE, list);
        
        // en
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("en_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("en_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("en_3");
        list.add(b);
        
        CODE_LIST01.put(Locale.ENGLISH, list);
        
        // en_GB
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("en_GB_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("en_GB_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("en_GB_3");
        list.add(b);
        
        CODE_LIST01.put(Locale.UK, list);
        
        // en_GB_gb01
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("en_GB_gb01_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("en_GB_gb01_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("en_GB_gb01_3");
        list.add(b);
        
        CODE_LIST01.put(new Locale("en", "GB", "gb01"), list);
        
        // de_AT_at01
        list = new ArrayList<CodeBean>();
        b = new CodeBean();
        b.setId("001");
        b.setName("de_AT_at01_1");
        list.add(b);
        
        b = new CodeBean();
        b.setId("002");
        b.setName("de_AT_at01_2");
        list.add(b);
        
        b = new CodeBean();
        b.setId("003");
        b.setName("de_AT_at01_3");
        list.add(b);
        
        CODE_LIST01.put(new Locale("de", "AT", "at01"), list);
    }
    
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(
                AbstractMultilingualCodeListLoaderTest.class);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * testDefaultLocale01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�FVM�̌���݂̂̃f�t�H���g���P�[��
     *         
     * <br>
     * �f�t�H���g���P�[����ݒ肵�Ȃ��ꍇ��VM�̌���݂̂�
     * �f�t�H���g���P�[���ƂȂ邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefaultLocale01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        assertEquals(new Locale(Locale.getDefault().getLanguage()), 
                loader.defaultLocale);
    }
    
    /**
     * testSetDefaultLocale01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnew Locale("ja", "JP", "tokyo")<br>
     *         
     * <br>
     * ���Ғl�F���͒l�Ɠ������P�[��
     *         
     * <br>
     * �����Ŏw�肵�����P�[�����ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDefaultLocale01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        Locale locale = new Locale("ja", "JP", "tokyo");
        loader.setDefaultLocale(locale);
        assertEquals(locale, loader.defaultLocale);
    }
    
    /**
     * testSetDefaultLocale01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�FLocale.getDefault()<br>
     *         
     * <br>
     * ���Ғl�F0��
     *         
     * <br>
     * ���ۉ��Ή��R�[�h���X�g���null�̏ꍇ�A0���̃R�[�h���X�g���擾���邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateCodeBeans01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = null;
        CodeBean[] codeList = loader.createCodeBeans(Locale.getDefault());
        
        assertEquals(codeList.length, 0);
    }
    
    /**
     * testCreateCodeBeans02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�FLocale.getDefault()<br>
     *         
     * <br>
     * ���Ғl�F0��
     *         
     * <br>
     * ���ۉ��Ή��R�[�h���X�g���ɑ��݂��Ȃ����P�[���̃R�[�h���X�g���w�肵���ꍇ�A
     * 0���̃R�[�h���X�g���擾���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateCodeBeans02() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = loader.createCodeBeans(Locale.CHINESE);
        
        assertEquals(codeList.length, 0);
    }
    
    /**
     * testCreateCodeBeans03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnew Locale("en" , "GB", "gb01")<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'en_GB_gb01_XX'�ł��邱��
     *         
     * <br>
     * �ȉ��̏����ŃR�[�h���X�g���擾�ł��邱�Ƃ��m�F����B<br>
     * �E�����̃��P�[���Ƀo���A���g�܂Ŏw��B<br>
     * �ECODE_LIST01�Ƀo���A���g�܂ň�v����R�[�h���X�g��񂪑��݂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateCodeBeans03() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(new Locale("en" , "GB", "gb01"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_GB_gb01_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnew Locale("en" , "GB", "gb02")<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'en_GB_XX'�ł��邱��
     *         
     * <br>
     * �ȉ��̏����ŃR�[�h���X�g���擾�ł��邱�Ƃ��m�F����B<br>
     * �E�����̃��P�[���Ƀo���A���g�܂Ŏw��B<br>
     * �ECODE_LIST01�Ƀo���A���g�܂ň�v����R�[�h���X�g��񂪑��݂��Ȃ��B
     * �ECODE_LIST01�ɍ��R�[�h�܂ň�v����R�[�h���X�g��񂪑��݂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateCodeBeans04() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(new Locale("en" , "GB", "gb02"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_GB_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnew Locale("en" , "US", "us01")<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'en_XX'�ł��邱��
     *         
     * <br>
     * �ȉ��̏����ŃR�[�h���X�g���擾�ł��邱�Ƃ��m�F����B<br>
     * �E�����̃��P�[���Ƀo���A���g�܂Ŏw��B<br>
     * �ECODE_LIST01�ɍ��R�[�h�A�o���A���g�܂ň�v����R�[�h���X�g��񂪑��݂��Ȃ��B
     * �ECODE_LIST01�Ɍ���R�[�h����v����R�[�h���X�g��񂪑��݂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateCodeBeans05() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(new Locale("en" , "US", "us01"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnull<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'ja_x'�ł��邱��
     *         
     * <br>
     * ������null�̏ꍇ�A�f�t�H���g���P�[���̃R�[�h���X�g���擾���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateCodeBeans06() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.defaultLocale = Locale.JAPANESE;
        loader.localeMap = CODE_LIST01;
        CodeBean[] codeList = 
            loader.createCodeBeans(null);
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("ja_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testCreateCodeBeans07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnull<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'ja_x'�ł��邱��
     *         
     * <br>
     * �f�t�H���g���P�[����null��������null�̏ꍇ�A
     * IllegalStateException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateCodeBeans07() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.defaultLocale = null;
        loader.localeMap = CODE_LIST01;
        
        try {
            loader.createCodeBeans(null);
        } catch (IllegalStateException e) {
            assertTrue(true);
            return;
        }
        
        fail();
    }
    
    /**
     * testGetCodeBeansByLocale01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnew Locale("en" , "GB", "gb01")<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'en_GB_gb01_XX'�ł��邱��
     *         
     * <br>
     * �ȉ��̏����ŃR�[�h���X�g���擾�ł��邱�Ƃ��m�F����B<br>
     * �ECODE_LIST01�Ƀ��P�[���Ɉ�v����R�[�h���X�g��񂪑��݂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeansByLocale01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = Locale.JAPANESE;
        CodeBean[] codeList = 
            loader.getCodeBeans(new Locale("en" , "GB", "gb01"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("en_GB_gb01_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testGetCodeBeansByLocale02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�FLocale.CHINESE<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'ja_XX'�ł��邱��
     *         
     * <br>
     * ���P�[���Ɉ�v����R�[�h���X�g�����݂��Ȃ��ꍇ�A�f�t�H���g���P�[����
     * �R�[�h���X�g���擾���邱�Ɗm�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeansByLocale02() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = Locale.JAPANESE;
        CodeBean[] codeList = 
            loader.getCodeBeans(Locale.CHINESE);
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("ja_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testGetCodeBeansByLocale03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnew Locale("de", "AT", "at01")<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'de_AT_at01_XX'�ł��邱��
     *         
     * <br>
     * ���P�[���Ɉ�v����R�[�h���X�g�����݂��Ȃ��ꍇ�A�f�t�H���g���P�[����
     * �R�[�h���X�g���擾���邱�Ɗm�F����B
     * �f�t�H���g���P�[���̕������ʂ̃��P�[���̏ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeansByLocale03() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = new Locale("de", "AT", "at01");
        CodeBean[] codeList = 
            loader.getCodeBeans(new Locale("de"));
        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("de_AT_at01_" + (i + 1), b.getName());
        }
    }
    
    /**
     * testGetCodeBeansByLocale04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�Fnew Locale(de", "AT", "XX")<br>
     *         
     * <br>
     * ���Ғl�F 0���B
     *         
     * <br>
     * �f�t�H���g���P�[���ɂ��R�[�h���X�g����`����Ă��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeansByLocale04() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = new Locale("de");
        CodeBean[] codeList = 
            loader.getCodeBeans(new Locale("de", "AT", "XX"));
        
        assertEquals(0, codeList.length);
    }
    
    
    /**
     * testGetCodeBeans01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F 3���B�擾�����R�[�h���X�g�̃R�[�h�l��'ja_XX'�ł��邱��
     *         
     * <br>
     * ���P�[���Ɉ�v����R�[�h���X�g�����݂��Ȃ��ꍇ�A�f�t�H���g���P�[����
     * �R�[�h���X�g���擾���邱�Ɗm�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans01() throws Exception {
        MultilingualCodeListLoaderImpl01 loader =
                                        new MultilingualCodeListLoaderImpl01();
        loader.localeMap = CODE_LIST01;
        loader.defaultLocale = Locale.JAPAN;
        CodeBean[] codeList = 
            loader.getCodeBeans();

        
        assertEquals(3, codeList.length);
        for (int i = 0; i < codeList.length; i++) {
            CodeBean b = codeList[i];
            assertEquals(String.format("%1$03d", i + 1), b.getId());
            assertEquals("ja_" + (i + 1), b.getName());
        }
    }
}
