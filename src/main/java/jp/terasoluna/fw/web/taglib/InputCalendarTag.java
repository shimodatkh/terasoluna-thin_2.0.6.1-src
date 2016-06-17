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

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.util.StringUtil;

/**
 * �w�肳�ꂽ�t�B�[���h�ɑ΂��đI���������t�̓��͂�
 * �s���J�����_�[���͋@�\�B
 * 
 * <p>
 * �ȉ��̍��ڂ����b�Z�[�W���\�[�X�t�@�C���ɋL�q���邱�ƂŁA
 * �J�����_�[���͋@�\�̐ݒ��ύX���邱�Ƃ��ł���B<br>
 * �Ȃ��A���b�Z�[�W���\�[�X�t�@�C�����́ucalendar�v�Œ�Ƃ���B<br>
 * ��F�ucalendar.properties�v�A�ucalendar_en.properties�v<br>
 * </p>
 * <ul>
 *   <li>�x����`</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">�@�\�ڍ�</td>
 *     <td style="text-align: left;">
 *       ���b�Z�[�W���\�[�X�t�@�C���Ɉȉ��̂悤�ɋL�q���邱�Ƃ�
 *       �J�����_�[�̋x�����w�肷�邱�Ƃ��ł���B
 *       ���b�Z�[�W���\�[�X�L�[�́A�ucalendar.holiday.�v�������Œ�Ƃ��āA
 *       ���̌�ɁA�u1�v����A�Ԃ�U�邱�ƂƂ���B
 *       �p�����[�^�́A�u�N�v�u���v�u���v�u�x���T�v�v���u,(�J���})�v��
 *       ��؂��ċL�q���邱�ƂƂ���B
 *       ���N�������t�̋x����`�́u�N�v���u0�v��
 *       �w�肷�邱�ƂŖ��N�ƔF������B
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">�ݒ��</td>
 *     <td style="text-align: left;">
 *       calendar.holiday.1=0,1,1,����<br/>
 *       calendar.holiday.2=0,2,11,�����L�O�̓�<br/>
 *       calendar.holiday.3=0,4,29,���a�̓�<br/>
 *       calendar.holiday.4=0,5,3,���@�L�O��<br/>
 *       calendar.holiday.5=0,5,4,�݂ǂ�̓�<br/>
 *       calendar.holiday.6=0,5,5,���ǂ��̓�<br/>
 *       calendar.holiday.7=0,11,3,�����̓�<br/>
 *       calendar.holiday.8=0,11,23,�ΘJ���ӂ̓�<br/>
 *       calendar.holiday.9=0,12,23,�V�c�a����<br/>
 *       calendar.holiday.10=2009,1,12,���l�̓�<br/>
 *       calendar.holiday.11=2009,3,20,�t���̓�<br/>
 *       calendar.holiday.12=2009,7,20,�C�̓�<br/>
 *       calendar.holiday.13=2009,9,21,�h�V�̓�<br/>
 *       calendar.holiday.14=2009,9,23,�H���̓�<br/>
 *       calendar.holiday.15=2009,10,12,�̈�̓�<br/>
 *       �F
 *     </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>�{�^���\��������ύX</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">�@�\�ڍ�</td>
 *     <td style="text-align: left;">
 *       ���b�Z�[�W���\�[�X�t�@�C���Ɉȉ��̂悤�ɋL�q���邱�Ƃ�
 *       �J�����_�[��\������{�^���̕������ύX���邱�Ƃ��ł���B
 *       ���b�Z�[�W���\�[�X�L�[�́A�ucalendar.button.string�v�Œ�Ƃ���B
 *       �f�t�H���g�́uCalendar�v�ƂȂ�B
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">�ݒ��</td>
 *     <td style="text-align: left;">
 *       calendar.button.string=�J�����_�[
 *     </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>�X�^�C���v���t�B�b�N�X�ύX</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">�@�\�ڍ�</td>
 *     <td style="text-align: left;">
 *       ���b�Z�[�W���\�[�X�t�@�C���Ɉȉ��̂悤�ɋL�q���邱�Ƃ�
 *       �J�����_�[�ɂĎg�p����X�^�C���V�[�g�̃v���t�B�b�N�X�A
 *       ����щ摜�t�@�C���̃v���t�B�b�N�X��ύX���邱�Ƃ��ł���B
 *       <br/>
 *       ���b�Z�[�W���\�[�X�L�[�́A�ucalendar.style.themeprefix�v�Œ�Ƃ���B
 *       �f�t�H���g�́uBlueStyle�v�ƂȂ�B
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">�ݒ��</td>
 *     <td style="text-align: left;">
 *       calendar.style.themeprefix=BlueStyle
 *     </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>���ݓ��t�\��������ύX</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">�@�\�ڍ�</td>
 *     <td style="text-align: left;">
 *       ���b�Z�[�W���\�[�X�t�@�C���Ɉȉ��̂悤�ɋL�q���邱�Ƃ�
 *       �J�����_�[�̉����ɕ\������錻�ݓ��t�ɕt�^���镶�����
 *       �ύX���邱�Ƃ��ł���B
 *       <br/>
 *       ���b�Z�[�W���\�[�X�L�[�́A�ucalendar.today.string�v�Œ�Ƃ���B
 *       �f�t�H���g�́uToday is �v�ƂȂ�B
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">�ݒ��</td>
 *     <td style="text-align: left;">calendar.today.string=Today is </td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>�J�����_�[�摜�ۑ��ꏊ�ύX</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">�@�\�ڍ�</td>
 *     <td style="text-align: left;">
 *       ���b�Z�[�W���\�[�X�t�@�C���Ɉȉ��̂悤�ɋL�q���邱�Ƃ�
 *       �J�����_�[���͋@�\�ɂĎg�p����摜�̕ۑ��ꏊ��ύX���邱�Ƃ��ł���B
 *       �Ō�́u/�v�ŏI���K�v������B
 *       �摜�̕ۑ��ꏊ�͕ύX�\�����A�摜�t�@�C���̖��O�͕ύX���邱�Ƃ��ł��Ȃ��B
 *       <br/>
 *       ���b�Z�[�W���\�[�X�L�[�́A�ucalendar.img.dir�v�Œ�Ƃ���B
 *       �f�t�H���g�́uimg/calendar/�v�ƂȂ�B
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">�ݒ��</td>
 *     <td style="text-align: left;">calendar.img.dir=image/</td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>�X�^�C���V�[�g�ۑ��ꏊ�ύX</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">�@�\�ڍ�</td>
 *     <td style="text-align: left;">
 *       ���b�Z�[�W���\�[�X�t�@�C���Ɉȉ��̂悤�ɋL�q���邱�Ƃ�
 *       �J�����_�[���͋@�\�ɂĎg�p����X�^�C���V�[�g�̕ۑ��ꏊ��
 *       �ύX���邱�Ƃ��ł���B�Ō�́u/�v�ŏI���K�v������B
 *       ���̋@�\�Ŏg�p����X�^�C���V�[�g�̃t�@�C�����́A
 *       �u<�v���t�B�b�N�X> + InputCalendar.css�v�ł���B
 *       <br/>
 *       ���b�Z�[�W���\�[�X�L�[�́A�ucalendar.stylesheet.dir�v�Œ�Ƃ���B
 *       �f�t�H���g�́ucss/�v�ƂȂ�B
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">�ݒ��</td>
 *     <td style="text-align: left;">calendar.stylesheet.dir=style/</td>
 *   </tr>
 * </table>
 * </div>
 * <br/>
 * <ul>
 *   <li>�O��JavaScript�t�@�C���ۑ��ꏊ�ύX</li>
 * </ul>
 * <div style="text-align: center;">
 * <table style="width:90%;border:1pt;" frame="box">
 *   <tr>
 *     <td style="font-weight:bold;width:60px;">�@�\�ڍ�</td>
 *     <td style="text-align: left;">
 *       ���b�Z�[�W���\�[�X�t�@�C���Ɉȉ��̂悤�ɋL�q���邱�Ƃ�
 *       �J�����_�[���͋@�\�ɂĎg�p����O��JavaScript�̕ۑ��ꏊ��
 *       �ύX���邱�Ƃ��ł���B�Ō�́u/�v�ŏI���K�v������B
 *       ���̋@�\�Ŏg�p����JavaScript�̃t�@�C�����́A
 *       �uInputCalendar.js�v�ł���B
 *       <br/>
 *       ���b�Z�[�W���\�[�X�L�[�́A�ucalendar.javascript.dir�v�Œ�Ƃ���B
 *       �f�t�H���g�́ujs/�v�ƂȂ�B
 *     </td>
 *   </tr>
 *   <tr>
 *     <td style="font-weight:bold;">�ݒ��</td>
 *     <td style="text-align: left;">calendar.javascript.dir=javascript/</td>
 *   </tr>
 * </table>
 * </div>
 * <p/>
 * <strong>�J�����_�[���͋@�\�̎g�p��</strong>
 * <p>
 * �ȉ��̂悤�ɁA���̓t�B�[���h�Ƒ΂ƂȂ�悤�ɋL�q����B
 * �J�����_�[���͋@�\��for�����ɂ́A�ΏۂƂȂ���̓t�B�[���h�̖��O��
 * �w�肷��B�ȉ��̗�ł́Atext�v�f��property���������̓t�B�[���h��
 * ���O�ƂȂ邽�߁Afor�����ɂ́Atext�v�f��property�����Ɠ����l��
 * �w�肷��B
 * </p>
 * <code>
 *   &lt;html:text property="value" /&gt;<br>
 *   &lt;t:inputCalendar for="value" format="yyyy-MM-dd" /&gt;
 * </code>
 * <br/>
 * <p/>
 * <strong>�^�O���T�|�[�g���鑮��</strong>
 * <p>
 * �J�����_�[���͋@�\�ł́A�ȉ��̑������T�|�[�g����B
 * </p>
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td><b>������</b></td>
 *    <td><b>�f�t�H���g�l</b></td>
 *    <td><b>�K�{��</b></td>
 *    <td><b>���s����</b></td>
 *    <td><b>�T�v</b></td>
 *   </tr>
 *   <tr>
 *    <td><code>for</code></td>
 *    <td><code>-</code></td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td style="text-align: left;">
 *      �I���������t����͂�����̓t�B�[���h���w�肷��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>format</code></td>
 *    <td><code>yyyy/MM/dd</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td style="text-align: left;">
 *      �J�����_�[�̃t�H�[�}�b�g���w�肷��B<br/>
 *      �w��ł�����t�`���́uy(�N)�v�uM(��)�v�ud(��)�v�A
 *      ��ؕ����Ƃ��Ắu/�v�u-�v�u.�v�u���p�X�y�[�X�v
 *      �̂����ꂩ�ł���B�܂��A��؂蕶���́A�ꕶ���݂̂��g�p���邱�ƁB
 *      �uyyyy/MM-dd�v�̂悤�ɕ����̋�؂蕶�����g�p���邱�Ƃ͂ł��Ȃ��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>formatKey</code></td>
 *    <td><code>-</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td style="text-align: left;">
 *      �J�����_�[�̃t�H�[�}�b�g�����b�Z�[�W���\�[�X����
 *      �擾���邽�߂̃L�[�l���w�肷��B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <p/>
 * 
 */
public class InputCalendarTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -3163804374592471898L;

    /**
     * �Ή��e�L�X�g�t�B�[���h�w�葮���B
     * �I���������t����͂���t�B�[���h�����w�肷��B
     */
    private String forId = null;

    /**
     * �t�H�[�}�b�g�w�葮���B
     * �t�B�[���h�ɓ��͂�����t�̃t�H�[�}�b�g���w�肷��B
     * �uy�v�uM�v�ud�v��3�������g�p�B
     * ��؂蕶���́u/�v�u-�v�u.�v�u �v�̂S���g�p�\�ł���B
     */
    private String format = CALENDAR_DEFAULT_FORMAT;

    /**
     * �t�H�[�}�b�g�L�[�w�葮���B
     * �t�B�[���h�ɓ��͂�����t�̃t�H�[�}�b�g��
     * ���b�Z�[�W���\�[�X�̃L�[���w�肷��B
     */
    private String formatKey = null;

    /**
     * �Ή��e�L�X�g�t�B�[���h�w�葮���̒l��ԋp����B
     * @return �Ή��e�L�X�g�t�B�[���h�w�葮���̒l
     */
    public String getFor() {
        return forId;
    }

    /**
     * �Ή��e�L�X�g�t�B�[���h�w�葮���̒l��ݒ肷��B
     * @param forId �ݒ肷��l
     */
    public void setFor(String forId) {
        this.forId = forId;
    }

    /**
     * �t�H�[�}�b�g�w�葮���̒l��ԋp����B
     * @return �t�H�[�}�b�g�w�葮���̒l
     */
    public String getFormat() {
        return format;
    }

    /**
     * �t�H�[�}�b�g�w�葮���̒l��ݒ肷��B
     * @param format �ݒ肷��l
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * �t�H�[�}�b�g�L�[�w�葮���̒l��ԋp����B
     * @return �t�H�[�}�b�g�L�[�w�葮���̒l
     */
    public String getFormatKey() {
        return formatKey;
    }

    /**
     * �t�H�[�}�b�g�L�[�w�葮���̒l��ݒ肷��B
     * @param formatKey �ݒ肷��l
     */
    public void setFormatKey(String formatKey) {
        this.formatKey = formatKey;
    }

    /**
     * �J�����_�[���͋@�\�ɂĎg�p����J�����_�[�摜�ۑ��ꏊ��
     * ���b�Z�[�W���\�[�X����擾���邽�߂̎擾�L�[�B
     */
    protected static final String
        CALENDAR_IMG_DIR = "calendar.img.dir";

    /**
     * �J�����_�[���͋@�\�ɂĎg�p���錻�ݓ��t�t�^�������
     * ���b�Z�[�W���\�[�X����擾���邽�߂̎擾�L�[�B
     */
    protected static final String
        CALENDAR_TODAY_STRING = "calendar.today.string";

    /**
     * �J�����_�[���͋@�\�ɂĎg�p����{�^���\���������
     * ���b�Z�[�W���\�[�X����擾���邽�߂̎擾�L�[�B
     */
    protected static final String
        CALENDAR_BUTTON_VALUE = "calendar.button.value";

    /**
     * �J�����_�[���͋@�\�ɂĎg�p����X�^�C���v���t�B�b�N�X��
     * ���b�Z�[�W���\�[�X����擾���邽�߂̎擾�L�[�B
     */
    protected static final String
        CALENDAR_STYLE_THEMEPREFIX = "calendar.style.themeprefix";

    /**
     * �J�����_�[���͋@�\�ɂĎg�p����X�^�C���V�[�g�ۑ��ꏊ��
     * ���b�Z�[�W���\�[�X����擾���邽�߂̎擾�L�[�B
     */
    protected static final String
        CALENDAR_STYLESHEET_DIR = "calendar.stylesheet.dir";

    /**
     * �J�����_�[���͋@�\�ɂĎg�p����J�����_�[�O��JavaScript�ۑ��ꏊ��
     * ���b�Z�[�W���\�[�X����擾���邽�߂̎擾�L�[�B
     */
    protected static final String
        CALENDAR_JAVASCRIPT_DIR = "calendar.javascript.dir";

    /**
     * �J�����_�[���͋@�\�ɂĎg�p����x����`����
     * ���b�Z�[�W���\�[�X����擾���邽�߂̎擾�L�[�B
     */
    protected static final String
        CALENDAR_HOLIDAY_PREFIX = "calendar.holiday";

    /**
     * �f�t�H���g�̃t�H�[�}�b�g�B
     */
    protected static final String
        CALENDAR_DEFAULT_FORMAT = "yyyy/MM/dd";

    /**
     * �f�t�H���g�X�^�C���v���t�B�b�N�X���B
     */
    protected static final String
        CALENDAR_DEFAULT_STYLEPREFIX_NAME = "BlueStyle";

    /**
     * �f�t�H���g�̉摜�ۑ��ꏊ�p�X�B
     */
    protected static final String
        CALENDAR_DEFAULT_IMAGE_PATH = "img/calendar/";

    /**
     * �f�t�H���g�̃J�����_�[�{�^���\��������B
     */
    protected static final String
        CALENDAR_DEFAULT_BUTTON_VALUE = "Calendar";

    /**
     * �f�t�H���g�̃X�^�C���V�[�g�ۑ��ꏊ�B
     */
    protected static final String
        CALENDAR_DEFAULT_STYLESHEET_DIR = "css/";

    /**
     * �f�t�H���g�̃J�����_�[�O��JavaScript�ۑ��ꏊ�B
     */
    protected static final String
        CALENDAR_DEFAULT_JAVASCRIPT_DIR = "js/";

    /**
     * �J�����_�[���͋@�\���Ďg�p����JavaScript�̃t�@�C�����B
     */
    protected static final String
        CALENDAR_JAVASCRIPT_FILE_NAME = "InputCalendar.js";

    /**
     * �J�����_�[���͋@�\���Ďg�p����X�^�C���V�[�g�̃t�@�C����(�v���t�B�b�N�X����)�B
     */
    protected static final String
        CALENDAR_STYLESHEET_FILE_NAME = "InputCalendar.css";

    /**
     * �o�͏�ԃt���O�L�[�B
     */
    protected static final String
        INPUTCALENDAR_FLG = "InputCalendarFlg";

    /**
     * �Ή����Ă���t�H�[�}�b�g�����Q�B
     */
    protected static final String
        FORMAT_VALUE = "yMd ./-";

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     * ���ۉ��Ή��̂��߂̓��@�\��p�̃��\�[�X�o���h�����擾����B
     * ���@�\���g�p���邽�߂�JavaScript����у{�^���̏o�͂��s���B
     * 
     * @return ��������w��
     * @exception JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {

        //���\�[�X�o���h������
        ResourceBundle calendarBundle = null;

        //���P�[���擾
        Locale locale = pageContext.getRequest().getLocale();

        //�J�����_�[���͋@�\��p�̃��\�[�X�o���h�����擾����B
        try {
            calendarBundle
                = ResourceBundle.getBundle("calendar", locale);
        } catch (MissingResourceException e) {
            //���b�Z�[�W���\�[�X���ݒ肳��Ă��Ȃ��ꍇ�͉������Ȃ��B
            //�K�v�Ȓl���f�t�H���g���g�p�����B
        }

        //�J�����_�[���͋@�\�̗v�f�������L�q����Ă��Ă��A
        //JavaScript��`��JSP�ň�x�o�͂�����A�o�͂��Ȃ��B
        //�o�͂�������pageContext�Ƀt���O�𗧂ĂĔ��f����B
        if (!getPageContextFlg(pageContext, INPUTCALENDAR_FLG)) {
            //�J�����_�[���͋@�\�Ŏg�p����JavaScript��`�̏o��
            defineJavaScript(calendarBundle);

            //��x�o�͂������̂Ńt���O�𗧂Ă�B
            setPageContextFlg(pageContext, INPUTCALENDAR_FLG);
        }

        //�J�����_�[��ʂ��o�͂��邽�߂̃{�^�����o�͂���B
        defineButton(calendarBundle);

        return SKIP_BODY;
    }

    /**
     * JavaScript�̒�`�������o�͂���B
     * ���{�ꃍ�P�[���t���O�A���ݓ��t�t��������A�X�^�C���v���t�B�b�N�X�A
     * �J�����_�[�摜�ꏊ�w��A�J�����_�[�X�^�C���V�[�g�ꏊ�w��A
     * �J�����_�[JavaScript�ꏊ�w��̏o�͂��s���B
     * �܂��A�X�^�C���V�[�g��&lt;link&gt;�v�f�A
     * �J�����_�[���͋@�\��&lt;script&gt;�v�f�̏o�͂��s���B
     * 
     * @param calendarBundle �J�����_�[���͋@�\�p���\�[�X�o���h��
     * @throws JspException JSP��O
     */
    protected void defineJavaScript(ResourceBundle calendarBundle)
            throws JspException {

        //���P�[���̎擾
        Locale currentLocale = pageContext.getRequest().getLocale();

        //���{�ꃍ�P�[���t���O
        boolean jpFlg = false;
        if ((Locale.JAPAN.getLanguage()).equals(currentLocale.getLanguage())) {
            jpFlg = true;
        }

        //DateFormatSymbols�̐�������сA���P�[���ɑΉ������j���A���̎擾���s���B
        DateFormatSymbols symbols = new DateFormatSymbols(currentLocale);
        String[] weekdays = mapWeekdays(symbols);
        String[] months = mapMonths(symbols);

        //���ݓ��t�t������������b�Z�[�W���\�[�X����擾�B
        String todayString = getParameter(calendarBundle,
                CALENDAR_TODAY_STRING, "");

        //�X�^�C���v���t�B�b�N�X�����b�Z�[�W���\�[�X����擾�B
        String styleThemePrefix = getParameter(calendarBundle,
                CALENDAR_STYLE_THEMEPREFIX, CALENDAR_DEFAULT_STYLEPREFIX_NAME);

        //�J�����_�[�摜�ꏊ�w������b�Z�[�W���\�[�X����擾�B
        String imageDir = getParameter(calendarBundle, CALENDAR_IMG_DIR,
                CALENDAR_DEFAULT_IMAGE_PATH);
        imageDir = imageDir + styleThemePrefix + "/";

        //�J�����_�[�X�^�C���V�[�g�ꏊ�w������b�Z�[�W���\�[�X����擾�B
        String styleFileName = styleThemePrefix + CALENDAR_STYLESHEET_FILE_NAME;
        String styleSheetDir = getParameter(calendarBundle,
                CALENDAR_STYLESHEET_DIR, CALENDAR_DEFAULT_STYLESHEET_DIR);

        //�J�����_�[JavaScript�ꏊ�w������b�Z�[�W���\�[�X����擾�B
        String javaScriptFileName = CALENDAR_JAVASCRIPT_FILE_NAME;
        String javaScriptDir = getParameter(calendarBundle,
                CALENDAR_JAVASCRIPT_DIR, CALENDAR_DEFAULT_JAVASCRIPT_DIR);

        //script�v�f�̊J�n�^�O���o��
        TagUtil.writeln(
                pageContext, "<script type=\"text/javascript\">");
        TagUtil.writeln(pageContext, "<!--");

        //���{�ꃍ�P�[���t���O�̏o��
        TagUtil.writeln(pageContext, 
                defineObjectVariable("localja", String.valueOf(jpFlg)));

        //�����X�g�̏o��
        TagUtil.writeln(pageContext, 
                defineArrayVariable("jscalendarMonthName", months));

        //�j�����X�g�̏o��
        TagUtil.writeln(pageContext, 
                defineArrayVariable("jscalendarDayName", weekdays));

        //���ݓ��t�t��������̏o��
        TagUtil.writeln(pageContext, 
                defineStringVariable("jscalendarTodayString",
                todayString));

        //�X�^�C���v���t�B�b�N�X�̏o��
        TagUtil.writeln(pageContext, 
                defineStringVariable("jscalendarThemePrefix",
                styleThemePrefix));

        //�J�����_�[�摜�ꏊ�w��̏o��
        TagUtil.writeln(pageContext, 
                defineStringVariable("jscalendarImgDir", imageDir));

        //script�v�f�̕��^�O���o��
        TagUtil.writeln(pageContext, "-->");
        TagUtil.writeln(pageContext, "</script>");

        //�J�����_�[���͋@�\��JavaScript�O���t�@�C����
        //��荞��script�v�f�̏o��
        TagUtil.write(pageContext, "<script type=\"text/javascript\" src=\"");
        TagUtil.write(pageContext, javaScriptDir);
        TagUtil.write(pageContext, javaScriptFileName);
        TagUtil.writeln(pageContext, "\" ></script>");

        //�J�����_�[���͋@�\�̊O���X�^�C���V�[�g��
        //��荞�݂��s��link�v�f�̏o��
        TagUtil.writeln(pageContext, 
                "<link rel=\"stylesheet\" href=\"" + styleSheetDir
                + styleFileName + "\"  type=\"text/css\"/>");

        //script�v�f�̊J�n�^�O���o��
        TagUtil.writeln(pageContext,
                "<script type=\"text/javascript\">");
        TagUtil.writeln(pageContext, "<!--");

        //�x����`�����擾����B
        List<Map<String, String>> holidays = getHolidayList(calendarBundle);

        //�擾�����x����`���o��
        for (int i = 0; i < holidays.size() ; i++) {
            Map<String, String> map = holidays.get(i);
            TagUtil.write(pageContext, "jscalendarAddHoliday('");
            TagUtil.write(pageContext, map.get("year"));
            TagUtil.write(pageContext, "', '");
            TagUtil.write(pageContext, map.get("month"));
            TagUtil.write(pageContext, "', '");
            TagUtil.write(pageContext, map.get("day"));
            TagUtil.write(pageContext, "', '");
            TagUtil.write(pageContext, map.get("desc"));
            TagUtil.writeln(pageContext, "');");
        }
        TagUtil.writeln(pageContext, "");

        //JavaScript�̏��������s��script�v�f�̏o��
        TagUtil.writeln(pageContext, "jscalendarInit();");

        //script�v�f�̕��^�O���o��
        TagUtil.writeln(pageContext, "-->");
        TagUtil.writeln(pageContext, "</script>");

    }

    /**
     * ���b�Z�[�W���\�[�X�ɒ�`���ꂽ�x����`���擾����B
     * 
     * @param calendarBundle �J�����_�[���͋@�\�p���\�[�X�o���h��
     * @return �擾�����x����`���X�g
     */
    protected List<Map<String, String>> getHolidayList(
            ResourceBundle calendarBundle) {

        //�x����`���X�g����
        List<Map<String, String>> holidays
            = new ArrayList<Map<String, String>>();

        if (calendarBundle == null) {
            return holidays;
        }

        //�擾�����L�[�l�����`����Ă���x�����擾����B
        //�x����`���@��
        //�ucomponent.calendar.holiday.1=2004,11,23,�ΘJ���ӂ̓��v
        //�̂悤�Ɂu�N�v�u���v�u���v�u�T�v�v���u,�v������ŋL�q���邱�ƁB
        for (int i = 1; ; i++) {

            //��`���ێ�MAP
            Map<String, String> map = new HashMap<String, String>();

            //���b�Z�[�W���\�[�X�L�[�̐���
            String key = CALENDAR_HOLIDAY_PREFIX + "." + i;

            //���b�Z�[�W���\�[�X�L�[����p�����[�^���擾����B
            String param = null;
            try {
                param = calendarBundle.getString(key);
            } catch (MissingResourceException e) {
                //NULL�̏ꍇ�͂���ȏ��`���Ȃ����̂Ƃ��ă��[�v���I��
                break;
            }

            //PARAM���擾�ł����ꍇ�́u,�v������ɕ�������B
            String[] paramSprit = param.split(",");

            //������A4�ɕ�����Ă��Ȃ���Έȉ��̍�Ƃ��s��Ȃ��B
            if (paramSprit.length != 4) {
                continue;
            }

            //��������`����Ă����ꍇ�́AMAP�Ɋi�[���āA��`���X�g�Ɋi�[����B
            map.put("year",  StringUtil.trim(paramSprit[0]));
            map.put("month", StringUtil.trim(paramSprit[1]));
            map.put("day",   StringUtil.trim(paramSprit[2]));
            map.put("desc",  StringUtil.trim(paramSprit[3]));
            holidays.add(map);

        }

        //�x����`���X�g��ԋp����B
        return holidays;

    }

    /**
     * �J�����_�[��ʂ�\�����邽�߂̃{�^�����o�͂���B
     *
     * @param calendarBundle �J�����_�[���͋@�\�p���\�[�X�o���h��
     * @throws JspException JSP��O
     */
    protected void defineButton(ResourceBundle calendarBundle)
            throws JspException {

        //���t�t�H�[�}�b�g����
        String dateFormat = null;

        //�^�O�����ɂĎw�肳�ꂽ���t�t�H�[�}�b�g���擾���āA
        //JS�t�@�C���ɂđΉ����Ă���^���ۂ����肷��B
        //�܂��A���t�t�H�[�}�b�g���擾�ł����A���t�t�H�[�}�b�g�L�[��
        //�w�肳��Ă����ꍇ�̓L�[����p�����[�^���擾���Ă���
        //JS�t�@�C���ɑΉ����Ă��邩������s���B
        if (formatKey != null) {
            dateFormat = createFormat(getParameter(calendarBundle, formatKey,
                    CALENDAR_DEFAULT_FORMAT));
        } else {
            dateFormat = createFormat(format);
        }

        //�{�^���\��������̎擾
        String buttonValue = getParameter(calendarBundle,
                CALENDAR_BUTTON_VALUE, 
                CALENDAR_DEFAULT_BUTTON_VALUE);

        TagUtil.writeln(pageContext, 
                "<input type=\"button\" onclick=\""
                + "jscalendarPopUpCalendar(this,this.form.elements['"
                + forId + "'],'" + dateFormat + "');\" value=\""
                + buttonValue + "\" />");
    }

    /**
     * ���b�Z�[�W���\�[�X����L�[�����ɒl���擾���ĕԋp����B
     * �L�[��null�܂��͋󔒂̏ꍇ�A�擾�����l��null�̏ꍇ��
     * �f�t�H���g�l��ԋp����B
     * @param calendarBundle �J�����_�[���͋@�\�p���\�[�X�o���h��
     * @param key ���b�Z�[�W���\�[�X�̃L�[
     * @param def �f�t�H���g�ƂȂ�l
     * @return ���b�Z�[�W���\�[�X����擾�����l�A�܂��̓f�t�H���g�l
     */
    protected String getParameter(ResourceBundle calendarBundle,
            String key, String def) {

        if (calendarBundle == null || key == null || "".equals(key)) {
            return def;
        }

        String retValue = null;

        //�擾�������\�[�X�o���h������L�[�����Ƃɒl���擾����B
        try {
            retValue = calendarBundle.getString(key);
        } catch (MissingResourceException e) {
            //�w�肳�ꂽ�L�[�����݂��Ȃ����߃f�t�H���g��ԋp����B
            return def;
        }

        return retValue;
    }

    /**
     * �����ɂēn���ꂽ�l��<code>JavaScript</code>��
     * <code>String</code>�錾�Ƃ��ĕԋp����B<br>
     * ��F�ϐ����u<code>kanji</code>�v�A�l�u�����v��
     * �u<code>var kanji = "����";\n</code>�v�ƂȂ�B
     * 
     * @param name <code>JavaScript</code>�Ő錾����ϐ���
     * @param value �ϐ��̒l
     * @return �ҏW�㕶����
     */
    protected String defineStringVariable(String name, String value) {
        StringBuilder retValue = new StringBuilder("var ");
        retValue.append(name);
        retValue.append(" = \"");
        retValue.append(value);
        retValue.append("\";");
        return retValue.toString();
    }

    /**
     * �����ɂēn���ꂽ�l��<code>JavaScript</code>��
     * <code>Object</code>�錾�Ƃ��āA�ԋp����B<br>
     * ��F�ϐ����u<code>obj1</code>�v�A
     * �l�u<code>document.forms[0]</code>�v
     * �ˁu<code>var obj1 = document.forms[0];\n</code>�v�ƂȂ�B
     * 
     * @param name <code>JavaScript</code>�Ő錾����ϐ���
     * @param value �ϐ��̒l
     * @return �ҏW�㕶����
     */
    protected String defineObjectVariable(String name, String value) {
        StringBuilder retValue = new StringBuilder("var ");
        retValue.append(name);
        retValue.append(" = ");
        retValue.append(value);
        retValue.append(";");
        return retValue.toString();
    }

    /**
     * �����ɂēn���ꂽ�z���<code>JavaScript</code>��
     * <code>Array</code>�^�Ƃ��ĕҏW���ĕԋp����B<br>
     * ��F�ϐ����u<code>array</code>�v�A�l�u<code>10,20,30</code>�v
     * �ˁu<code>var obj1 = new Array("10","20","30");\n</code>�v<br>
     * �Ȃ��A����array��null�̏ꍇ�́ANullPointerException����������B
     * 
     * @param arrayName <code>JavaScript</code>�Ő錾����ϐ���
     * @param array <code>Array</code>�Ƃ��č쐬����l��ێ�����z��
     * @return �ҏW�㕶����
     */
    protected String defineArrayVariable(String arrayName, String[] array) {
        StringBuilder retValue = new StringBuilder("var ");
        retValue.append(arrayName);
        retValue.append(" = new Array(");
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                retValue.append(",");
            }
            retValue.append("\"");
            retValue.append(array[i]);
            retValue.append("\"");
        }
        retValue.append(");");
        return retValue.toString();
    }

    /**
     * �w�肳�ꂽ�f�[�g�t�H�[�}�b�g�V���{������u�j���v���擾���ĕԋp����B
     * 
     * @param symbols �f�[�g�t�H�[�}�b�g�V���{��(���P�[���ݒ�ς�)
     * @return �擾�����u�j���v���i�[�����z��
     */
    protected String[] mapWeekdays(DateFormatSymbols symbols) {
        String[] weekdays = new String[7];
        String[] localeWeekdays = symbols.getShortWeekdays();
        weekdays[0] = localeWeekdays[Calendar.SUNDAY];
        weekdays[1] = localeWeekdays[Calendar.MONDAY];
        weekdays[2] = localeWeekdays[Calendar.TUESDAY];
        weekdays[3] = localeWeekdays[Calendar.WEDNESDAY];
        weekdays[4] = localeWeekdays[Calendar.THURSDAY];
        weekdays[5] = localeWeekdays[Calendar.FRIDAY];
        weekdays[6] = localeWeekdays[Calendar.SATURDAY];
        return weekdays;
    }

    /**
     * �w�肳�ꂽ�f�[�g�t�H�[�}�b�g�V���{������u���v���擾���ĕԋp����B
     * 
     * @param symbols �f�[�g�t�H�[�}�b�g�V���{��(���P�[���ݒ�ς�)
     * @return �擾�����u���v���i�[�����z��
     */
    protected String[] mapMonths(DateFormatSymbols symbols) {
        String[] months = new String[12];
        String[] localeMonths = symbols.getMonths();
        months[0]  = localeMonths[Calendar.JANUARY];
        months[1]  = localeMonths[Calendar.FEBRUARY];
        months[2]  = localeMonths[Calendar.MARCH];
        months[3]  = localeMonths[Calendar.APRIL];
        months[4]  = localeMonths[Calendar.MAY];
        months[5]  = localeMonths[Calendar.JUNE];
        months[6]  = localeMonths[Calendar.JULY];
        months[7]  = localeMonths[Calendar.AUGUST];
        months[8]  = localeMonths[Calendar.SEPTEMBER];
        months[9]  = localeMonths[Calendar.OCTOBER];
        months[10] = localeMonths[Calendar.NOVEMBER];
        months[11] = localeMonths[Calendar.DECEMBER];
        return months;
    }

    /**
     * �w�肳�ꂽKEY�ɂĎ擾�����l��^�U�l�ɕϊ����ĕԋp����B
     * �Ȃ��A����key��null�̏ꍇ�́ANullPointerException����������B
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param key FLG���擾����KEY
     * @return �w�肳�ꂽKEY�ɂĎ擾�����o�͏�ԃt���O
     */
    protected boolean getPageContextFlg(
            PageContext pageContext, String key) {
        //�y�[�W�R���e�L�X�g����t���O���擾����B
        Object obj = pageContext.getAttribute(key);
        Boolean bol = Boolean.valueOf(false);
        if (obj != null && obj instanceof Boolean) {
           bol = (Boolean) obj;
        }
        return bol.booleanValue();
    }

    /**
     * �y�[�W�R���e�L�X�g�ɑ΂��āA�w�肳�ꂽKEY�̃t���O��ݒ肷��B
     * �Ȃ��A����key��null�̏ꍇ�́ANullPointerException����������B
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param key FLG��ݒ肷��KEY
     */
    protected void setPageContextFlg(
            PageContext pageContext, String key) {
        //�y�[�W�R���e�L�X�g�Ƀt���O�𗧂Ă�B
        pageContext.setAttribute(key, Boolean.valueOf(true));
    }

    /**
     * �����̕�����ɑ΂��ăf�[�g�t�H�[�}�b�g�̐������s���B
     * ������<code>null</code>�A�܂��͋󔒂̏ꍇ�́A
     * ���̂܂ܕԋp����B
     * �܂��A�����񂪃f�[�g�t�H�[�}�b�g�ɑΉ����Ă��Ȃ��ꍇ�́A��������B
     * 
     * @param format �f�[�g�t�H�[�}�b�g
     * @return ������̃t�H�[�}�b�g������
     */
    protected String createFormat(String format) {

        //NULL�̏ꍇ�́A�f�t�H���g�̃f�[�g�t�H�[�}�b�g�Ƃ���B
        if (format == null || "".equals(format)) {
            return CALENDAR_DEFAULT_FORMAT;
        }

        //��������ꕶ���ÑΉ����镶�����ۂ��m�F����B
        //�Ή����Ă��Ȃ��ꍇ�́A�r�����Ă���ԋp����B
        StringBuilder retValue = new StringBuilder("");
        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);
            if (FORMAT_VALUE.indexOf(String.valueOf(c)) != -1) {
                retValue.append(c);
            }
        }

        //�����������ʂ�ԋp����B
        return StringUtil.trim(retValue.toString());

    }

    /**
     * ���ׂẴA���P�[�g���ꂽ�������������B
     */
    @Override
    public void release() {
        super.release();
        forId = null;
        format = CALENDAR_DEFAULT_FORMAT;
        formatKey = null;
    }

}
