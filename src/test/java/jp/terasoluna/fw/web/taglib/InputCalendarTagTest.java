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
import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockPageContext;

/**
 * {@link jp.terasoluna.framework.web.taglib.InputCalendarTag} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �w�肳�ꂽ�t�B�[���h�ɑ΂��đI���������t�̓��͂��s���J�����_�[���͋@�\�B
 * <p>
 * 
 * @see jp.terasoluna.framework.web.taglib.InputCalendarTag
 */
public class InputCalendarTagTest extends TestCase {

    /**
     * �e�X�g�ΏۃN���X
     */
    private InputCalendarTag calendar = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(InputCalendarTagTest.class);
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
        calendar = (InputCalendarTag) TagUTUtil.create(InputCalendarTag.class);
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
        calendar = null;
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public InputCalendarTagTest(String name) {
        super(name);
    }

    /**
     * testGetFor01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) forId:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetFor01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(calendar, "forId", param);

        // �e�X�g���{
        String value = calendar.getFor();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetFor01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) forId:"abc"<br>
     *         (���) forId:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) forId:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFor01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(calendar, "forId", null);

        // �e�X�g���{
        calendar.setFor(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(calendar, "forId"));
    }

    /**
     * testGetFormat01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) format:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetFormat01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(calendar, "format", param);

        // �e�X�g���{
        String value = calendar.getFormat();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetFormat01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) format:"abc"<br>
     *         (���) format:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) format:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFormat01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(calendar, "format", null);

        // �e�X�g���{
        calendar.setFormat(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(calendar, "format"));
    }

    /**
     * testGetFormatKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) formatKey:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetFormatKey01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(calendar, "formatKey", param);

        // �e�X�g���{
        String value = calendar.getFormatKey();

        // ����
        assertEquals(param, value);
    }

    /**
     * testSetFormatKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) formatKey:"abc"<br>
     *         (���) formatKey:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) formatKey:"abc"<br>
     *         
     * <br>
     * ������n�ꌏ�̂݃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetFormatKey01() throws Exception {
        // �O����
        String param = "abc";
        UTUtil.setPrivateField(calendar, "formatKey", null);

        // �e�X�g���{
        calendar.setFormatKey(param);

        // ����
        assertEquals(param, UTUtil.getPrivateField(calendar, "formatKey"));
    }

    /**
     * testDoStartTag01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) calendar.properties<br>
     *                �̑���:�Ȃ�<br>
     *         (���) getPageContextFlg�i�j<br>
     *                �̖߂�l:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:SKIP_BODY<br>
     *         (��ԕω�) defineJavaScript�i�j�̌ďo:
     *                   �ȉ��̈����ŌĂяo����Ă��邱�Ƃ��m�F<br>
     *                    ����1�FcalendarBundle(null)<br>
     *         (��ԕω�) defineButton�i�j�̌ďo:
     *                   �ȉ��̈����ŌĂяo����Ă��邱�Ƃ��m�F<br>
     *                    ����1�FcalendarBundle(null)<br>
     *         (��ԕω�) pageContext��INPUTCALENDAR_FLG�̏��:true<br>
     *         
     * <br>
     * �w�肳�ꂽ�v���p�e�B�t�@�C�������݂��Ȃ��A����JavaScript�̏o�͂��s���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag01() throws Exception {

        // �e�X�g�ΏۃX�^�u�̐���
        InputCalendarTag_InputCalendarTagStub01 calendar
            = (InputCalendarTag_InputCalendarTagStub01)
                TagUTUtil.create(InputCalendarTag_InputCalendarTagStub01.class);

        // �O����
        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();

        pageContext.setAttribute(InputCalendarTag.INPUTCALENDAR_FLG,
                Boolean.valueOf(false));
        request.addLocale(Locale.CANADA);

        // �e�X�g���{
        int value = calendar.doStartTag();

        // ����
        assertEquals(Tag.SKIP_BODY, value);
        assertTrue(calendar.isDefineJavaScriptCalled());
        assertTrue(calendar.isDefineButtonCalled());
        assertNull(calendar.getDefineJavaScriptCalendarBundle());
        assertNull(calendar.getDefineButtonCalendarBundle());
        boolean bol = ((Boolean) pageContext.getAttribute(
                InputCalendarTag.INPUTCALENDAR_FLG)).booleanValue();
        assertTrue(bol);
    }

    /**
     * testDoStartTag02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) calendar.properties<br>
     *                �̑���:����<br>
     *         (���) getPageContextFlg�i�j<br>
     *                �̖߂�l:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:SKIP_BODY<br>
     *         (��ԕω�) defineJavaScript�i�j�̌ďo:�Ăяo����Ȃ����Ƃ��m�F<br>
     *         (��ԕω�) defineButton�i�j�̌ďo:
     *                   �ȉ��̈����ŌĂяo����Ă��邱�Ƃ��m�F<br>
     *                    ����1�FcalendarBundle<br>
     *         (��ԕω�) pageContext��INPUTCALENDAR_FLG�̏��:true<br>
     *         
     * <br>
     * �w�肳�ꂽ�v���p�e�B�t�@�C�������݂���A����JavaScript�̏o�͂��s��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag02() throws Exception {

        // �e�X�g�ΏۃX�^�u�̐���
        InputCalendarTag_InputCalendarTagStub01 calendar
            = (InputCalendarTag_InputCalendarTagStub01)
                TagUTUtil.create(InputCalendarTag_InputCalendarTagStub01.class);

        // �O����
        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();

        pageContext.setAttribute(InputCalendarTag.INPUTCALENDAR_FLG,
            Boolean.valueOf(true));
        request.addLocale(Locale.UK);

        // �e�X�g���{
        int value = calendar.doStartTag();

        // ����
        assertEquals(Tag.SKIP_BODY, value);
        assertFalse(calendar.isDefineJavaScriptCalled());
        assertTrue(calendar.isDefineButtonCalled());
        assertNull(calendar.getDefineJavaScriptCalendarBundle());
        assertNotNull(calendar.getDefineButtonCalendarBundle());
        boolean bol = ((Boolean) pageContext.getAttribute(
                InputCalendarTag.INPUTCALENDAR_FLG)).booleanValue();
        assertTrue(bol);
    }

    /**
     * testDefineJavaScript01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:null<br>
     *         (���) pageContext.getRequest().getLocale():Locale.JAPAN<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) writer:<script type="text/javascript"><br>
     *                    <!--<br>
     *                    var localja = true;<br>
     *                    var jscalendarMonthName = new Array("1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��");<br>
     *                    var jscalendarDayName = new Array("��","��","��","��","��","��","�y");<br>
     *                    var jscalendarTodayString = "";<br>
     *                    var jscalendarThemePrefix = "BlueStyle";<br>
     *                    var jscalendarImgDir = "img/calendar/BlueStyle/";<br>
     *                    --><br>
     *                    </script><br>
     *                    <script type="text/javascript" src="js/InputCalendar.js" ></script><br>
     *                    <link rel="stylesheet" href="css/BlueStyleInputCalendar.css"  type="text/css"/><br>
     *                    <script type="text/javascript"><br>
     *                    <!--<br>
     *                    <br>
     *                    jscalendarInit();<br>
     *                    --><br>
     *                    </script><br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X��null�A���P�[�������{�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineJavaScript01() throws Exception {
        // �O����
        // Mock�I�u�W�F�N�g�̐���
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();
        request.addLocale(Locale.JAPAN);

        //���\�[�X�o���h������
        ResourceBundle calendarBundle = null;

        // �e�X�g���{
        calendar.defineJavaScript(calendarBundle);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("var localja = true;",reader.readLine());
        assertEquals("var jscalendarMonthName = new Array(\"1��\",\"2��\",\"" +
                "3��\",\"4��\",\"5��\",\"6��\",\"7��\",\"8��\",\"9��\"," +
                "\"10��\",\"11��\",\"12��\");",reader.readLine());
        assertEquals("var jscalendarDayName = new Array(\"��\",\"��\"," +
                "\"��\",\"��\",\"��\",\"��\",\"�y\");",reader.readLine());
        assertEquals("var jscalendarTodayString = \"\";",reader.readLine());
        assertEquals("var jscalendarThemePrefix = \"BlueStyle\";"
                ,reader.readLine());
        assertEquals("var jscalendarImgDir = \"img/calendar/BlueStyle/\";"
                ,reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertEquals("<script type=\"text/javascript\" " +
                "src=\"js/InputCalendar.js\" ></script>",reader.readLine());
        assertEquals("<link rel=\"stylesheet\" " +
                "href=\"css/BlueStyleInputCalendar.css\"  type=\"text/css\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("",reader.readLine());
        assertEquals("jscalendarInit();",reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDefineJavaScript02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (���) pageContext.getRequest().getLocale():Locale.CANADA<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3,abs<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) writer:<script type="text/javascript"><br>
     *                    <!--<br>
     *                    var localja = false;<br>
     *                    var jscalendarMonthName = new Array("January","February","March","April","May","June","July","August","September","October","November","December");<br>
     *                    var jscalendarDayName = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");<br>
     *                    var jscalendarTodayString = "";<br>
     *                    var jscalendarThemePrefix = "BlueStyle";<br>
     *                    var jscalendarImgDir = "img/calendar/BlueStyle/";<br>
     *                    --><br>
     *                    </script><br>
     *                    <script type="text/javascript" src="js/InputCalendar.js" ></script><br>
     *                    <link rel="stylesheet" href="css/BlueStyleInputCalendar.css"  type="text/css"/><br>
     *                    <script type="text/javascript"><br>
     *                    <!--<br>
     *                    jscalendarAddHoliday('0', '1', '3', 'abs');<br>
     *                    <br>
     *                    jscalendarInit();<br>
     *                    --><br>
     *                    </script><br>
     *         
     * <br>
     * ���b�Z�[�W���\�[�X�ɋx����`���ꌏ�A���P�[�����J�i�_�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineJavaScript02() throws Exception {
        // �O����
        // Mock�I�u�W�F�N�g�̐���
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(calendar);
        MockHttpServletRequest request
            = (MockHttpServletRequest) pageContext.getRequest();
        request.addLocale(Locale.CANADA);

        //���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // �e�X�g���{
        calendar.defineJavaScript(calendarBundle);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("var localja = false;",reader.readLine());
        assertEquals("var jscalendarMonthName = new Array(\"January\"," +
                "\"February\",\"March\",\"April\",\"May\",\"June\"," +
                "\"July\",\"August\",\"September\",\"October\"," +
                "\"November\",\"December\");",reader.readLine());
        assertEquals("var jscalendarDayName = new Array(\"Sun\"," +
                "\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fri\",\"Sat\");"
                ,reader.readLine());
        assertEquals("var jscalendarTodayString = \"\";",reader.readLine());
        assertEquals("var jscalendarThemePrefix = \"BlueStyle\";"
                ,reader.readLine());
        assertEquals("var jscalendarImgDir = \"img/calendar/BlueStyle/\";"
                ,reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertEquals("<script type=\"text/javascript\" " +
                "src=\"js/InputCalendar.js\" ></script>",reader.readLine());
        assertEquals("<link rel=\"stylesheet\" " +
                "href=\"css/BlueStyleInputCalendar.css\"  type=\"text/css\"/>"
                ,reader.readLine());
        assertEquals("<script type=\"text/javascript\">",reader.readLine());
        assertEquals("<!--",reader.readLine());
        assertEquals("jscalendarAddHoliday('0', '1', '3', 'abs');"
                ,reader.readLine());
        assertEquals("",reader.readLine());
        assertEquals("jscalendarInit();",reader.readLine());
        assertEquals("-->",reader.readLine());
        assertEquals("</script>",reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testGetHolidayList01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:null<br>
     *         (���) -<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:�v�f��0�̃��X�g<br>
     *         
     * <br>
     * ������null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList01() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle = null;

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList02.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1�����݂��Ȃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:�v�f��0�̃��X�g<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList02() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList02"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList03.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:�v�f��0�̃��X�g<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���󕶎��̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList03() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList03"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList04.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:�v�f��0�̃��X�g<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���J���}�ŋ�؂�ꂽ�R�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList04() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList04"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList05.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3,4,5<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:�v�f��0�̃��X�g<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���J���}�ŋ�؂�ꂽ�T�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList05() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList05"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList06.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3,abc<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:
     *                  �v�f��1�A���e���ȉ��Ƃ��郊�X�g<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���J���}�ŋ�؂�ꂽ�S�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList06() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList06"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList07.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:
     *                 calendar.holiday.1= 0 , 1 , 3 , abc<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:
     *                  �v�f��1�A���e���ȉ��Ƃ��郊�X�g<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���󔒂ƃJ���}�ŋ�؂�ꂽ�S�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList07() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList07"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList08.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=,0,1,3,abc<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:�v�f��0�̃��X�g<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���J���}��
     * ��؂�ꂽ�S�̕�����̐擪�ɃJ���}�����镶����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList08() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList08"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList09.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3,abc,<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:
     *                  �v�f��1�A���e���ȉ��Ƃ��郊�X�g<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���J���}��
     * ��؂�ꂽ�S�̕�����̖����ɃJ���}�����镶����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList09() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList09"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList10.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1�����݂��Ȃ��B<br>
     *                calendar.holiday.2=0,1,3,abc<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:�v�f��0�̃��X�g<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l��null�A<br>
     * calendar.holiday.2�̐ݒ�l���J���}�ŋ�؂�ꂽ�S�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList10() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList10"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertTrue(holidays.isEmpty());
    }

    /**
     * testGetHolidayList11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList11.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=<br>
     *                calendar.holiday.2=0,1,3,abc<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:
     *                  �v�f��1�A���e���ȉ��Ƃ��郊�X�g<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���󕶎��A<br>
     * calendar.holiday.2�̐ݒ�l���J���}�ŋ�؂�ꂽ�S�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList11() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList11"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList12.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3,abc<br>
     *                calendar.holiday.2�����݂��Ȃ��B<br>
     *                calendar.holiday.3=1000,10,19,def<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:
     *                 �v�f��1�A���e���ȉ��Ƃ��郊�X�g<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���J���}�ŋ�؂�ꂽ�S�̕�����A
     * calendar.holiday.2�̐ݒ�l��null�A<br>
     * calendar.holiday.3�̐ݒ�l���J���}�ŋ�؂�ꂽ�S�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList12() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList12"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertEquals(1, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
    }

    /**
     * testGetHolidayList13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList13.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3,abc<br>
     *                calendar.holiday.2=<br>
     *                calendar.holiday.3=1000,10,19,def<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:
     *                  �v�f��2�A���e���ȉ��Ƃ��郊�X�g<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *                  [1]=map{ year="1000" month="10" day="19" desc="def"}<br>
     *         
     * <br>
     * calendar.holiday.1�̐ݒ�l���J���}�ŋ�؂�ꂽ�S�̕�����A
     * calendar.holiday.2�̐ݒ�l���󕶎��A<br>
     * calendar.holiday.3�̐ݒ�l���J���}�ŋ�؂�ꂽ�S�̕�����̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList13() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList13"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertEquals(2, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
        map = holidays.get(1);
        assertEquals("1000", map.get("year"));
        assertEquals("10", map.get("month"));
        assertEquals("19", map.get("day"));
        assertEquals("def", map.get("desc"));
    }

    /**
     * testGetHolidayList14()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_getHolidayList14.properties]<br>
     *         (���) ���b�Z�[�W���\�[�X�ݒ�:calendar.holiday.1=0,1,3,abc<br>
     *                calendar.holiday.2=0,4,2,�x��<br>
     *                calendar.holiday.3=2000,10,6,�x��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List<Map<String, String>>:
     *                  �v�f��3�A���e���ȉ��Ƃ��郊�X�g<br>
     *                  [0]=map{ year="0" month="1" day="3" desc="abc"}<br>
     *                  [1]=map{ year="0" month="4" day="2" desc="�x��"}<br>
     *                  [2]=map{ year="2000" month="10" day="6" desc="�x��"}<br>
     *         
     * <br>
     * �v���p�e�B�ɂR�̐���ȋx���ݒ肪����Ă����ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetHolidayList14() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar_getHolidayList14"
                    , Locale.JAPAN);

        // �e�X�g���{
        List<Map<String, String>> holidays
            = calendar.getHolidayList(calendarBundle);

        // ����
        assertEquals(3, holidays.size());
        Map<String, String> map = holidays.get(0);
        assertEquals("0", map.get("year"));
        assertEquals("1", map.get("month"));
        assertEquals("3", map.get("day"));
        assertEquals("abc", map.get("desc"));
        map = holidays.get(1);
        assertEquals("0", map.get("year"));
        assertEquals("4", map.get("month"));
        assertEquals("2", map.get("day"));
        assertEquals("�x��", map.get("desc"));
        map = holidays.get(2);
        assertEquals("2000", map.get("year"));
        assertEquals("10", map.get("month"));
        assertEquals("6", map.get("day"));
        assertEquals("�x��", map.get("desc"));
    }

    /**
     * testDefineButton01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:null<br>
     *         (���) formatKey:null<br>
     *         (���) forId:null<br>
     *         (���) format:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) writer:<input type="button"
     *  onclick="jscalendarPopUpCalendar(this,this.form.
     *  elements['null'],'yyyy/MM/dd');" value="Calendar" /><br>
     *         
     * <br>
     * formatKey��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineButton01() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle = null;
        UTUtil.setPrivateField(calendar, "forId", null);
        UTUtil.setPrivateField(calendar, "format", null);
        UTUtil.setPrivateField(calendar, "formatKey", null);

        // �e�X�g���{
        calendar.defineButton(calendarBundle);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<input type=\"button\" " +
                "onclick=\"jscalendarPopUpCalendar(" +
                "this,this.form.elements['null'],'yyyy/MM/dd');\" " +
                "value=\"Calendar\" />", reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testDefineButton02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (���) formatKey:"formatKey"<br>
     *         (���) forId:"id"<br>
     *         (���) format:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) writer:<input type="button" 
     * onclick="jscalendarPopUpCalendar(this,this.form.elements['id'],
     * 'yyyy-MM-dd');" value="CalCal" /><br>
     *         
     * <br>
     * formatKey��Not null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineButton02() throws Exception {
        // �O����
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);
        UTUtil.setPrivateField(calendar, "forId", "id");
        UTUtil.setPrivateField(calendar, "format", null);
        UTUtil.setPrivateField(calendar, "formatKey", "formatKey");

        // �e�X�g���{
        calendar.defineButton(calendarBundle);

        // ����
        BufferedReader reader = TagUTUtil.getOutputReader(calendar);
        assertEquals("<input type=\"button\" " +
                "onclick=\"jscalendarPopUpCalendar(" +
                "this,this.form.elements['id'],'yyyy-MM-dd');\" " +
                "value=\"CalCal\" />", reader.readLine());
        assertNull(reader.readLine());
    }

    /**
     * testGetParameter01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:null<br>
     *         (����) def:"default"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"default"<br>
     *         
     * <br>
     * ����calendarBundle��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParameter01() throws Exception {
        // �O����
        String key = null;
        String def = "default";
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle = null;

        // �e�X�g���{
        String value = calendar.getParameter(calendarBundle, key, def);

        // ����
        assertEquals(def, value);
    }

    /**
     * testGetParameter02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (����) key:null<br>
     *         (����) def:"default"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"default"<br>
     *         
     * <br>
     * ����key��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParameter02() throws Exception {
        // �O����
        String key = null;
        String def = "default";
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // �e�X�g���{
        String value = calendar.getParameter(calendarBundle, key, def);

        // ����
        assertEquals(def, value);
    }

    /**
     * testGetParameter03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_en_GB.properties]<br>
     *         (����) key:""<br>
     *         (����) def:"default"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"default"<br>
     *         
     * <br>
     * ����key���󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParameter03() throws Exception {
        // �O����
        String key = "";
        String def = "default";
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // �e�X�g���{
        String value = calendar.getParameter(calendarBundle, key, def);

        // ����
        assertEquals(def, value);
    }

    /**
     * testGetParameter04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_en_GB.properties]<br>
     *                [�l�̕ێ��Ȃ��B]<br>
     *         (����) key:"key1"<br>
     *         (����) def:"default"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"default"<br>
     *         
     * <br>
     * ����key�ɑ΂���l�����݂��Ȃ��ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParameter04() throws Exception {
        // �O����
        String key = "key1";
        String def = "default";
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // �e�X�g���{
        String value = calendar.getParameter(calendarBundle, key, def);

        // ����
        assertEquals(def, value);
    }

    /**
     * testGetParameter05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) calendarBundle:not null[calendar_en_GB.properties]<br>
     *                ["key2"="abc"]<br>
     *         (����) key:"key2"<br>
     *         (����) def:"default"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * ����key�ɑ΂���l�����݂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetParameter05() throws Exception {
        // �O����
        String key = "key2";
        String def = "default";
        // ���\�[�X�o���h������
        ResourceBundle calendarBundle
            = ResourceBundle.getBundle("calendar", Locale.UK);

        // �e�X�g���{
        String value = calendar.getParameter(calendarBundle, key, def);

        // ����
        assertEquals("abc", value);
    }

    /**
     * testDefineStringVariable01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) value:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var null = \"null\";"<br>
     *         
     * <br>
     * name��null�Avalue��null�ł���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineStringVariable01() throws Exception {
        //�e�X�g���{
        String value = calendar.defineStringVariable(null, null);

        //����
        assertEquals("var null = \"null\";", value);
    }

    /**
     * testDefineStringVariable02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""<br>
     *         (����) value:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var  = \"\";"<br>
     *         
     * <br>
     * name���󕶎��Avalue���󕶎��ł���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineStringVariable02() throws Exception {
        //�e�X�g���{
        String value = calendar.defineStringVariable("", "");

        //����
        assertEquals("var  = \"\";", value);
    }

    /**
     * testDefineStringVariable03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"variable"<br>
     *         (����) value:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var variable = \"abc\";"<br>
     *         
     * <br>
     * name��������Avalue��������̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineStringVariable03() throws Exception {
        //�e�X�g���{
        String value = calendar.defineStringVariable("variable", "abc");

        //����
        assertEquals("var variable = \"abc\";", value);
    }

    /**
     * testDefineObjectVariable01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) value:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var null = null;"<br>
     *         
     * <br>
     * name��null�Avalue��null�ł���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineObjectVariable01() throws Exception {
        //�e�X�g���{
        String value = calendar.defineObjectVariable(null, null);

        //����
        assertEquals("var null = null;", value);
    }

    /**
     * testDefineObjectVariable02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""<br>
     *         (����) value:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var  = ;"<br>
     *         
     * <br>
     * name���󕶎��Avalue���󕶎��ł���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineObjectVariable02() throws Exception {
        //�e�X�g���{
        String value = calendar.defineObjectVariable("", "");

        //����
        assertEquals("var  = ;", value);
    }

    /**
     * testDefineObjectVariable03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"variable"<br>
     *         (����) value:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var variable = abc;"<br>
     *         
     * <br>
     * name��������Avalue��������̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineObjectVariable03() throws Exception {
        //�e�X�g���{
        String value = calendar.defineObjectVariable("variable", "abc");

        //����
        assertEquals("var variable = abc;", value);
    }

    /**
     * testDefineArrayVariable01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) arrayName:null<br>
     *         (����) array:�v�f��0�̔z��<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var null = new Array();"<br>
     *         
     * <br>
     * name��null�Aarray���v�f��0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineArrayVariable01() throws Exception {
        //�e�X�g���{
        String value = calendar.defineArrayVariable(null, new String[]{});

        //����
        assertEquals("var null = new Array();", value);
    }

    /**
     * testDefineArrayVariable02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) arrayName:""<br>
     *         (����) array:�v�f��1�̈ȉ��̔z��<br>
     *                [0]="abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var  = new Array(\"abc\");"<br>
     *         
     * <br>
     * name���󕶎��Aarray���v�f��1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineArrayVariable02() throws Exception {
        //�e�X�g���{
        String value = calendar.defineArrayVariable("", new String[]{"abc"});

        //����
        assertEquals("var  = new Array(\"abc\");", value);
    }

    /**
     * testDefineArrayVariable03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) arrayName:"variable"<br>
     *         (����) array:�v�f��3�̈ȉ��̔z��<br>
     *                [0]="abc"<br>
     *                [1]="def"<br>
     *                [2]="ghi"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"var variable = <br>
     *                  new Array(\"abc\",\"def\",\"ghi\");"<br>
     *         
     * <br>
     * name��������Aarray���v�f��3�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDefineArrayVariable03() throws Exception {
        //�e�X�g���{
        String value = calendar.defineArrayVariable(
                        "variable", new String[]{"abc","def","ghi"});

        //����
        assertEquals("var variable = new Array(\"abc\",\"def\",\"ghi\");"
                , value);
    }

    /**
     * testMapWeekDays01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) symbols:DateFormatSymbols(Locale.JAPANESE)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String[]:�ȉ��̕�����̔z��<br>
     *                  String[0] -> ��<br>
     *                  String[1] -> ��<br>
     *                  String[2] -> ��<br>
     *                  String[3] -> ��<br>
     *                  String[4] -> ��<br>
     *                  String[5] -> ��<br>
     *                  String[6] -> �y<br>
     *         
     * <br>
     * ������n1���̂݊m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapWeekDays01() throws Exception {
        // �O����
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.JAPANESE);

        // �e�X�g���{
        String[] value = calendar.mapWeekdays(symbols);

        // ����
        assertEquals("��", value[0]);
        assertEquals("��", value[1]);
        assertEquals("��", value[2]);
        assertEquals("��", value[3]);
        assertEquals("��", value[4]);
        assertEquals("��", value[5]);
        assertEquals("�y", value[6]);
    }

    /**
     * testMapMonths01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) symbols:DateFormatSymbols(Locale.JAPANESE)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String[]:�ȉ��̕�����̔z��<br>
     *                  String[0] -> 1��<br>
     *                  String[1] -> 2��<br>
     *                  String[2] -> 3��<br>
     *                  String[3] -> 4��<br>
     *                  String[4] -> 5��<br>
     *                  String[5] -> 6��<br>
     *                  String[6] -> 7��<br>
     *                  String[7] -> 8��<br>
     *                  String[8] -> 9��<br>
     *                  String[9] -> 10��<br>
     *                  String[10] -> 11��<br>
     *                  String[11] -> 12��<br>
     *         
     * <br>
     * ������n1���̂݊m�F
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapMonths01() throws Exception {
        // �O����
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.JAPANESE);

        // �e�X�g���{
        String[] value = calendar.mapMonths(symbols);

        // ����
        assertEquals("1��", value[0]);
        assertEquals("2��", value[1]);
        assertEquals("3��", value[2]);
        assertEquals("4��", value[3]);
        assertEquals("5��", value[4]);
        assertEquals("6��", value[5]);
        assertEquals("7��", value[6]);
        assertEquals("8��", value[7]);
        assertEquals("9��", value[8]);
        assertEquals("10��", value[9]);
        assertEquals("11��", value[10]);
        assertEquals("12��", value[11]);
    }

    /**
     * testGetPageContextFlg01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         
     * <br>
     * ����key��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg01() throws Exception {
        // �O����
        String key = null;

        //MockRunner��MockPageContext���g�p����ƁA
        //getAttaribute���\�b�h�̈���key��null�ł�NullPointerException��
        //�������Ȃ����߁ASpring��MockPageContext���g�p����B
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        calendar.setPageContext(pageContext);

        // �e�X�g���{
        try {
            calendar.getPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //��O���m�F�B
        }
    }

    /**
     * testGetPageContextFlg02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:""<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=Boolean(true)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * ����key���󔒂ŁApageContext��Boolean(true)�Œl�����݂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg02() throws Exception {
        // �O����
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key",new Boolean(true));

        // �e�X�g���{
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // ����
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=String("true")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * pageContext��String("true")�Œl�����݂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg03() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key","true");

        // �e�X�g���{
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // ����
        assertFalse(value);
    }

    /**
     * testGetPageContextFlg04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=Boolean(true)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * pageContext��Boolean(true)�Œl�����݂���ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg04() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key",new Boolean(true));

        // �e�X�g���{
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // ����
        assertTrue(value);
    }

    /**
     * testGetPageContextFlg05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:"key"=null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * pageContext�ɐݒ肳��Ă���l��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPageContextFlg05() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);
        pageContext.setAttribute("key",null);

        // �e�X�g���{
        boolean value = calendar.getPageContextFlg(pageContext, key);

        // ����
        assertFalse(value);
    }

    /**
     * testSetPageContextFlg01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:null<br>
     *         (���) pageContext���ŕێ����Ă���l:�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         
     * <br>
     * ����key��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPageContextFlg01() throws Exception {
        // �O����
        String key = null;

        //MockRunner��MockPageContext���g�p����ƁA
        //setAttaribute���\�b�h�̈���key��null�ł�NullPointerException��
        //�������Ȃ����߁ASpring��MockPageContext���g�p����B
        org.springframework.mock.web.MockPageContext pageContext
            = new org.springframework.mock.web.MockPageContext();
        calendar.setPageContext(pageContext);

        // �e�X�g���{
        try {
            calendar.setPageContextFlg(pageContext, key);
            fail();
        } catch (IllegalArgumentException e) {
            //��O���m�F�B
        }
    }

    /**
     * testSetPageContextFlg02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:""<br>
     *         (���) pageContext���ŕێ����Ă���l:�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext���ŕێ����Ă���l:""=Boolean(true)<br>
     *         
     * <br>
     * ����key���󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPageContextFlg02() throws Exception {
        // �O����
        String key = "";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);

        // �e�X�g���{
        calendar.setPageContextFlg(pageContext, key);

        // ����
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testSetPageContextFlg03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) pageContext:not null<br>
     *         (����) key:"key"<br>
     *         (���) pageContext���ŕێ����Ă���l:�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) pageContext���ŕێ����Ă���l:"key"=Boolean(true)<br>
     *         
     * <br>
     * ����key��"key"�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPageContextFlg03() throws Exception {
        // �O����
        String key = "key";
        PageContext pageContext = TagUTUtil.getPageContext(calendar);

        // �e�X�g���{
        calendar.setPageContextFlg(pageContext, key);

        // ����
        assertTrue(((Boolean)pageContext.getAttribute(key)).booleanValue());
    }

    /**
     * testCreateFormat01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) format:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"yyyy/MM/dd"<br>
     *         
     * <br>
     * ������null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateFormat01() throws Exception {
        // �O����
        String format = null;

        // �e�X�g���{
        String value = calendar.createFormat(format);

        // ����
        assertEquals("yyyy/MM/dd", value);
    }

    /**
     * testCreateFormat02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) format:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"yyyy/MM/dd"<br>
     *         
     * <br>
     * �������󔒂̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateFormat02() throws Exception {
        // �O����
        String format = "";

        // �e�X�g���{
        String value = calendar.createFormat(format);

        // ����
        assertEquals("yyyy/MM/dd", value);
    }

    /**
     * testCreateFormat03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) format:"yyyy-MM-dd hh:mm:ss"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"yyyy-MM-dd"<br>
     *         
     * <br>
     * ������"yyyy-MM-dd hh:mm:ss"�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateFormat03() throws Exception {
        // �O����
        String format = "yyyy-MM-dd hh:mm:ss";

        // �e�X�g���{
        String value = calendar.createFormat(format);

        // ����
        assertEquals("yyyy-MM-dd", value);
    }

    /**
     * testRelease01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) super.parent:new TagSupport()<br>
     *         (���) forId:"abc"<br>
     *         (���) format:"abc"<br>
     *         (���) formatKey:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) super.parent:null<br>
     *         (��ԕω�) forId:null<br>
     *         (��ԕω�) format:"yyyy/MM/dd"<br>
     *         (��ԕω�) formatKey:null<br>
     *         
     * <br>
     * ����n�̂݁B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRelease01() throws Exception {
        // �O����
        UTUtil.setPrivateField(calendar, "parent", new TagSupport());
        UTUtil.setPrivateField(calendar, "forId", "abc");
        UTUtil.setPrivateField(calendar, "format", "abc");
        UTUtil.setPrivateField(calendar, "formatKey", "abc");

        // �e�X�g���{
        calendar.release();

        // ����
        assertNull(UTUtil.getPrivateField(calendar, "parent"));
        assertNull(UTUtil.getPrivateField(calendar, "forId"));
        assertEquals(InputCalendarTag.CALENDAR_DEFAULT_FORMAT
                , UTUtil.getPrivateField(calendar, "format"));
        assertNull(UTUtil.getPrivateField(calendar, "formatKey"));
    }

}
