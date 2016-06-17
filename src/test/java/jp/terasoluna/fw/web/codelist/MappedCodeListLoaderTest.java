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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader} �N���X
 * �̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �R�[�h���X�g���̏�������Map�ōs���A
 * jp.terasoluna.fw.web.codelist.CodeListLoader�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 */
public class MappedCodeListLoaderTest extends TestCase {

    /**
     * �e�X�g�ΏہB
     */
    private MappedCodeListLoader test = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MappedCodeListLoaderTest.class);
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
        test = new MappedCodeListLoader();
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
    public MappedCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testGetCodeListMap01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) codeListMap:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Map:�C���X�^���X�ϐ���codeListMap<br>
     *
     * <br>
     * �C���X�^���X�ϐ���codeListMap���擾�ł��邱�Ƃ��m�F����B<br>
     * ������n1���̂݊m�F�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeListMap01() throws Exception {
        // �O����
        Map map = new HashMap();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        Object result = test.getCodeListMap();

        // ����
        assertSame(map, result);
    }

    /**
     * testSetCodeListMap01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) codeListMap:not null<br>
     *         (���) codeListMap:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeListMap:not null�i�����Ɏw�肵��Map�j<br>
     *
     * <br>
     * �����Ɏw�肵��Map���C���X�^���X�ϐ�codeListMap�ɐݒ肳��邱�Ƃ��m�F����B<br>
     * ������n1���̂݊m�F�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCodeListMap01() throws Exception {
        // �O����
        UTUtil.setPrivateField(test, "codeListMap", null);

        // �e�X�g���{
        Map<String, String> map = new HashMap<String, String>();
        test.setCodeListMap(map);

        // ����
        Object result = UTUtil.getPrivateField(test, "codeListMap");
        assertSame(map, result);
    }

    /**
     * testLoad01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) localeMap:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�ω��Ȃ�<br>
     *
     * <br>
     * �C���X�^���X�ϐ���codeLists��not null�̏ꍇ�A�����s��ꂸ�������I�����邱�Ƃ��m�F����BcodeLists�ϐ��ɕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad01() throws Exception {
        // �O����
        CodeBean cb = new CodeBean();
        cb.setId("id");
        cb.setName("name");
        List<CodeBean> list = new ArrayList<CodeBean>();
        list.add(cb);
        Map<Locale, List<CodeBean>> localeMap = new HashMap<Locale, List<CodeBean>>();
        localeMap.put(Locale.JAPANESE, list);
        
        test.defaultLocale = Locale.JAPANESE;
        test.localeMap = localeMap;

        // �e�X�g���{
        test.load();

        // ����
        Map<Locale, List<CodeBean>> result = test.localeMap;
        assertSame(localeMap, result);
        
        List<CodeBean> codeLists = result.get(Locale.JAPANESE);
        assertSame(list, codeLists);
        assertEquals(1, codeLists.size());
        CodeBean codebean = codeLists.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
    }

    /**
     * testLoad02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) codeListMap:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����0��List<br>
     *
     * <br>
     * codeListMap��null�̏ꍇ�AcodeLists���v�f��0��List�Ƃ��Đݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad02() throws Exception {
        // �O����
        test.localeMap = null;
        UTUtil.setPrivateField(test, "codeListMap", null);

        // �e�X�g���{
        test.load();

        // ����
        Map<Locale, List<CodeBean>> result = test.localeMap;
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) codeListMap:�v�f��0��Map<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����0��List<br>
     *
     * <br>
     * codeListMap�̗v�f����̏ꍇ�AcodeLists���v�f��0��List�Ƃ��Đݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad03() throws Exception {
        // �O����
        test.localeMap = null;
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        test.load();

        // ����
        Map<Locale, List<CodeBean>> result = test.localeMap;
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) codeListMap:�v�f��1��Map<br>
     *                {"id","name"}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����1��List<br>
     *                    CodeBean{"id","name"}<br>
     *
     * <br>
     * codeListMap��1���̗v�f������ꍇ�A����Map�̓��e��codeLists������������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad04() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "name");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        test.load();

        // ����
        Map<Locale, List<CodeBean>> result = test.localeMap;
        List<CodeBean> resutCodeList = result.get(Locale.JAPANESE);
        assertEquals(1, resutCodeList.size());
        CodeBean codebean = resutCodeList.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
    }

    /**
     * testLoad05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) codeListMap:�v�f��3��Map<br>
     *                {"id1","name1"},<br>
     *                {"id2","name2"},<br>
     *                {"id3","name3"}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����1��List<br>
     *                    CodeBean{"id1","name1"},<br>
     *                    CodeBean{"id2","name2"},<br>
     *                    CodeBean{"id3","name3"},<br>
     *
     * <br>
     * codeListMap�ɕ������̗v�f������ꍇ�A����Map�̓��e��codeLists������������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad05() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        Map<String, String> map = new TreeMap<String, String>();
        map.put("id1", "name1");
        map.put("id2", "name2");
        map.put("id3", "name3");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        test.load();

        // ����
        Map<Locale, List<CodeBean>> result = test.localeMap;
        List<CodeBean> resutCodeList = result.get(Locale.JAPANESE);
        
        assertEquals(3, resutCodeList.size());

        // 1����
        CodeBean codebean = resutCodeList.get(0);
        assertEquals("id1", codebean.getId());
        assertEquals("name1", codebean.getName());

        // 2����
        codebean = resutCodeList.get(1);
        assertEquals("id2", codebean.getId());
        assertEquals("name2", codebean.getName());

        // 3����
        codebean = resutCodeList.get(2);
        assertEquals("id3", codebean.getId());
        assertEquals("name3", codebean.getName());
    }
    
    /**
     * testLoad06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) localeMap:null<br>
     *         (���) codeListMap:�v�f��3��Map<br>
     *                {"id1","name1"},<br>
     *                {"id2","name2"},<br>
     *                {"id3","name3"}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����1��List<br>
     *                    CodeBean{"id1","name1"},<br>
     *                    CodeBean{"id2","name2"},<br>
     *                    CodeBean{"id3","name3"},<br>
     *
     * <br>
     * codeListMap�ɕ������̗v�f������ꍇ�A����Map�̓��e��codeLists������������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad06() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        Map<String, Map<String, String>> map = 
                        new LinkedHashMap<String, Map<String,String>>();
        Map<String, String> childMap = new LinkedHashMap<String, String>();
        childMap.put("001", "val_1");
        childMap.put("002", "val_2");
        childMap.put("003", "val_3");
        map.put("ja", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("004", "val_4");
        childMap.put("005", "val_5");
        map.put("en", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("006", "val_6");
        childMap.put("007", "val_7");
        map.put("en_GB", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("008", "val_8");
        childMap.put("009", "val_9");
        map.put("en_US", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("010", "val_10");
        childMap.put("011", "val_11");
        map.put("en_US_us01", childMap);
        
        childMap = new TreeMap<String, String>();
        childMap.put("012", "val_12");
        childMap.put("013", "val_13");
        map.put("en_US_us02", childMap);

        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        test.load();

        // ����
        Map<Locale, List<CodeBean>> result = test.localeMap;
        // ���P�[��: ja
        List<CodeBean> codeList = result.get(new Locale("ja"));
        assertEquals(3, codeList.size());
        
        assertEquals("001", codeList.get(0).getId());
        assertEquals("val_1", codeList.get(0).getName());
        
        assertEquals("002", codeList.get(1).getId());
        assertEquals("val_2", codeList.get(1).getName());
        
        assertEquals("003", codeList.get(2).getId());
        assertEquals("val_3", codeList.get(2).getName());
        
        // ���P�[��: en
        codeList = result.get(new Locale("en"));
        assertEquals(2, codeList.size());
        
        assertEquals("004", codeList.get(0).getId());
        assertEquals("val_4", codeList.get(0).getName());
        
        assertEquals("005", codeList.get(1).getId());
        assertEquals("val_5", codeList.get(1).getName());
        
        // ���P�[��: en_GB
        codeList = result.get(new Locale("en", "GB"));
        assertEquals(2, codeList.size());
        
        assertEquals("006", codeList.get(0).getId());
        assertEquals("val_6", codeList.get(0).getName());
        
        assertEquals("007", codeList.get(1).getId());
        assertEquals("val_7", codeList.get(1).getName());
        
        // ���P�[��: en_US
        codeList = result.get(new Locale("en", "US"));
        assertEquals(2, codeList.size());
        
        assertEquals("008", codeList.get(0).getId());
        assertEquals("val_8", codeList.get(0).getName());
        
        assertEquals("009", codeList.get(1).getId());
        assertEquals("val_9", codeList.get(1).getName());
        
        // ���P�[��: en_US_us01
        codeList = result.get(new Locale("en", "US", "us01"));
        assertEquals(2, codeList.size());
        
        assertEquals("010", codeList.get(0).getId());
        assertEquals("val_10", codeList.get(0).getName());
        
        assertEquals("011", codeList.get(1).getId());
        assertEquals("val_11", codeList.get(1).getName());
        
        // ���P�[��: en_US_us02
        codeList = result.get(new Locale("en", "US", "us02"));
        assertEquals(2, codeList.size());
        
        assertEquals("012", codeList.get(0).getId());
        assertEquals("val_12", codeList.get(0).getName());
        
        assertEquals("013", codeList.get(1).getId());
        assertEquals("val_13", codeList.get(1).getName());
    }

    /**
     * testCreateLocale01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F""
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale01() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // �e�X�g���{
        Locale result = test.createLocale("");
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }
    
    /**
     * testCreateLocale02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F"__us01"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale02() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // �e�X�g���{
        Locale result = test.createLocale("__us01");
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }
    
    /**
     * testCreateLocale03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F_US_
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale03() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // �e�X�g���{
        Locale result = test.createLocale("_US_");
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }
    /**
     * testCreateLocale04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F"_US_us01"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: LOCALE.JAPANESE
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale04() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // �e�X�g���{
        Locale result = test.createLocale("_US_us01");
        
        // ����
        assertEquals(Locale.JAPANESE, result);
    }

    /**
     * testCreateLocale05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F"en"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: en
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale05() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // �e�X�g���{
        Locale result = test.createLocale("en");
        
        // ����
        assertEquals(new Locale("en"), result);
    }
    /**
     * testCreateLocale06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F"en__us01"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: en
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale06() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;

        // �e�X�g���{
        Locale result = test.createLocale("en__us01");
        
        // ����
        assertEquals(new Locale("en"), result);
    }
    /**
     * testCreateLocale07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F"en_US"
     *         (���) defaultLocale = LOCALE.JAPANESE
     *         
     * <br>
     * ���Ғl: en_US
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale07() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // �e�X�g���{
        Locale result = test.createLocale("en_US");
        
        // ����
        assertEquals(new Locale("en", "US"), result);
    }
    
    /**
     * testCreateLocale08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�F
     * <br><br>
     * ���͒l�F"en_US_us01"
     *         
     * <br>
     * ���Ғl: en_US_us01
     *         
     * <br>
     * ���͒l�ɑΉ����郍�P�[�����擾���邱�Ƃ��m�F����B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateLocale08() throws Exception {
        // �O����
        test.localeMap = null;
        test.defaultLocale = Locale.JAPANESE;
        
        // �e�X�g���{
        Locale result = test.createLocale("en_US_us01");
        
        // ����
        assertEquals(new Locale("en", "US", "us01"), result);
    }
}
