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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  ���t�������t�H�[�}�b�g���ďo�́A���邢�̓X�N���v�e�B���O�ϐ��Ƃ���
 *  ��`����J�X�^���^�O���߂̒��ۊ��N���X�B
 * </p>
 * <p>
 *  ���t�����̃t�H�[�}�b�g���s���J�X�^���^�O�̃N���X�͂��̊��N���X��
 *  �p�����č쐬����B
 * </p>
 * <p>
 *  �t�H�[�}�b�g�ΏۂƂȂ���t�����f�[�^�Ƃ��ẮA<code>java.util.Date</code>
 *  �^�A���邢�� <code>java.lang.String</code> �^���T�|�[�g����B�������A
 *  <code>java.lang.String</code> �^�̃f�[�^���t�H�[�}�b�g����ۂɂ́A
 *  ���̕����� <code>&quot;yyyy/MM/dd HH:mm:ss&quot;</code>
 *  �̌`���ƂȂ��Ă���K�v������B
 *  �iformat������getDefaultDateFormat() ���\�b�h�̃I�[�o�[���C�h�ŕύX�\�j<br>
 * </p>
 * <p>
 *  {@link DateFormatterTagBase} �N���X���p�������T�u�N���X�ł́A
 *  ���ۂɕ�����̃t�H�[�}�b�g���s�����ۃ��\�b�h
 *  <code>doFormat(Date date)</code> ����������B
 * </p>
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p><code>DateFormatterTagBase</code> �ł́A�ȉ��̑������T�|�[�g����B</p>
 * <div align="center">
 *  <table width="90%" border="1" bgcolor="#FFFFFF">
 *   <tr>
 *    <td> <b>������</b> </td>
 *    <td> <b>�f�t�H���g�l</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *    <td> <b>���s����</b> </td>
 *    <td> <b>�T�v</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>id</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>false</code> </td>
 *    <td align="left">�t�H�[�}�b�g���ꂽ����������X�|���X�֏o�͂����ɁA
 *      �X�N���v�e�B���O�ϐ��ɃZ�b�g����ۂɎw�肷��B
 *      �t�H�[�}�b�g���ꂽ��������X�N���v�e�B���O�ϐ��ɃZ�b�g����ꍇ�ɂ́A
 *      <code>filter</code> �����̎w��Ɋւ�炸HTML
 *      ���ꕶ���̓G�X�P�[�v����Ȃ��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>filter</code> </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�t�H�[�}�b�g���ꂽ��������o�͂���ۂɁAHTML���ꕶ����
 *      �G�X�P�[�v���邩�ǂ������w�肷��B�������A<code>id</code> ������
 *      �w�肳��Ă����ꍇ�ɂ́A���������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>ignore</code></td>
 *    <td><code>false</code></td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> �����Ŏw�肵�� bean��
 *      ������Ȃ������Ƃ��ɖ������邩�ǂ������w�肷��B<code>false</code>
 *      ���w�肷��ƁAbean��������Ȃ������Ƃ��� <code>JspException</code>
 *      ����������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>name</code></td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�t�H�[�}�b�g�Ώۂ̕�������v���p�e�B�Ɏ���
 *      bean�̖��O�B<code>property</code> �������w�肳���
 *      ���Ȃ������Ƃ��ɂ́A<code>name</code> �����Ŏw�肳�ꂽ�C���X�^���X��
 *      ���t�H�[�}�b�g�̑ΏۂƂȂ�B���̏ꍇ�́A
 *      ���̃C���X�^���X���g�� <code>java.util.Date</code> �^�ł��邩�A���邢��
 *      <code>java.lang.String</code> �^�i����&quot;yyyy/MM/dd HH:mm:ss&quot;
 *      �̌`���ƂȂ��Ă�����́j�̂ǂ��炩�ł���K�v������B<code>value</code>
 *      �������w�肳��Ă����ꍇ�ɂ́A���������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>property</code></td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> �����Ŏw�肳�ꂽ bean
 *      �ɂ����ăA�N�Z�X�����v���p�e�B�̖��O�B<code>value</code> ������
 *      �w�肳��Ă����ꍇ�ɂ͖��������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scope</code></td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td>�i<code>findAttribute()</code>���\�b�h�̌��������j</td>
 *    <td align="left"><code>name</code> �����Ŏw�肳�ꂽ bean
 *      ����������ۂ̃X�R�[�v�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>value</code></td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�t�H�[�}�b�g���镶����B������́A
 *      &quot;yyyy/MM/dd HH:mm:ss&quot;�̌`���ƂȂ��Ă���K�v������B
 *      �iformat������getDefaultDateFormat() ���\�b�h�̃I�[�o�[���C�h�ŕύX�\�j
 *      <code>value</code> �������w�肵���ꍇ�ɂ́A<code>name</code>
 *      �����A����� <code>property</code>
 *      �����͖��������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>pattern</code></td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�t�H�[�}�b�g����o�͌`���B<code>pattern</code>
 *      �����Ŏw�肵���o�͌`���́A<code>DateFormatterTagBase</code>
 *      �N���X�̃T�u�N���X�ŉ��߂����B�ڍׂ́A�T�u�N���X�̃h�L�������g��
 *      �Q�Ƃ̂��ƁB
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>format</code></td>
 *    <td> yyyy/MM/dd HH:mm:ss </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">���t�����̃t�H�[�}�b�g�B�f�t�H���g�l��ύX����ꍇ��
 *      <code>getDefaultDateFormat()</code>
 *      ���\�b�h���I�[�o�[���C�h����B
 *    </td>
 *   </tr>
 * </table>
 * </div>
 * <br><br>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>
 *  <code>DateFormatterTagBase</code> �ł͈ȉ��̕ϐ����T�|�[�g����B
 * </p>
 * <div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <td><b>�ϐ���</b></td>
 *   <td><b>�^</b></td>
 *   <td><b>�L���͈�</b></td>
 *   <td><b>�L�q</b></td>
 *  </tr>
 *  <tr>
 *   <td>�J�X�^���^�O�� <code>id</code> �����Ŏw�肳�ꂽ���O</td>
 *   <td><code>String</code></td>
 *   <td>�J�n�^�O�ȍ~</td>
 *   <td align="left">
 *    ���̃J�X�^���^�O�ŏo�͂����A�X�N���v�e�B���O�ϐ���
 *    �ݒ肷��ꍇ�̕ϐ����B
 *   </td>
 *  </tr>
 * </table>
 * </div>
 *
 * @see jp.terasoluna.fw.web.taglib.DateTag
 * @see jp.terasoluna.fw.web.taglib.JDateTag
 *
 */
public abstract class DateFormatterTagBase extends TagSupport {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(DateFormatterTagBase.class);
    
    /**
     * ���t�����̃t�H�[�}�b�g�̃f�t�H���g�l�B
     * ������f�[�^����t�����Ƃ��ĉ��߂���ۂɎg�p�����B
     *
     */
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

    /**
     * ���t�����̃t�H�[�}�b�g�̃f�t�H���g�l���擾����B
     *
     * <p>
     * �f�t�H���g�l��ύX����ꍇ�͂��̃��\�b�h���I�[�o�[���C�h����B
     * </p>
     * <p>
     * ���f�t�H���g�l��"yyyy/MM/dd HH:mm:ss"
     * </p>
     *
     * @return ���t�����̃t�H�[�}�b�g�̃f�t�H���g�l
     */
    protected String getDefaultDateFormat() {
        return DATE_FORMAT;
    }

    /**
     * ���͂ƂȂ���t�����̃t�H�[�}�b�g�B
     */
    protected String format = null;

    /**
     * ���͂ƂȂ���t�����̃t�H�[�}�b�g���擾����B
     *
     * @return format ���t�����̃t�H�[�}�b�g
     */
    public String getFormat() {
        return format;
    }

    /**
     * ���͂ƂȂ���t�����̃t�H�[�}�b�g��ݒ肷��B
     *
     * @param format ���t�����̃t�H�[�}�b�g
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * ���̃y�[�W���ŗ��p�ł���悤�ɂ��邽�߂̃X�N���v�e�B���O�ϐ��̖��O�B
     *
     */
    protected String id = null;

    /**
     * �X�N���v�e�B���O�ϐ��̖��O��ݒ肷��B
     *
     * @param id �X�N���v�e�B���O�ϐ���
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * �o�͒���HTML���ꕶ�����t�B���^�[���邩�ǂ����B�f�t�H���g��
     * <code>true</code>�B
     *
     */
    protected boolean filter = true;

    /**
     * �o�͒���HTML���ꕶ�����t�B���^�[���邩�ǂ�����ݒ肷��B
     *
     * @param filter �o�͒���HTML���ꕶ�����t�B���^�[����ꍇ��
     *               <code>true</code>
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * bean��������Ȃ������Ƃ��ɁA�P�ɖ�������i�����o�͂��Ȃ��j���ǂ����B
     * �������Ȃ��ꍇ�ɂ́A��O�𓊂���B�f�t�H���g�� <code>false</code>
     * �i��O�𓊂���j�B
     */
    protected boolean ignore = false;

    /**
     * bean��������Ȃ������ꍇ�ɖ������邩�ǂ�����ݒ肷��B
     *
     * @param ignore ��������ꍇ�� <code>true</code>
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * �t�H�[�}�b�g�Ώۂ̃f�[�^���܂�bean�̖��O�B
     *
     */
    protected String name = null;

    /**
     * �t�H�[�}�b�g�Ώۂ̃f�[�^���܂�bean�̖��O��ݒ肷��B
     *
     * @param name bean�̖��O
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B�̖��O�B
     *
     */
    protected String property = null;

    /**
     * �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B����ݒ肷��B
     *
     * @param property �v���p�e�B��
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * �w�肳�ꂽbean����������X�R�[�v�B
     *
     */
    protected String scope = null;

    /**
     * �w�肳�ꂽbean����������X�R�[�v��ݒ肷��B
     *
     * @param scope �X�R�[�v
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * �o�͂̃t�H�[�}�b�g���w�肷��p�^�[���B
     * <code>SimpleDateFormat</code> �Ɠ��������Ŏw�肷��B
     *
     */
    protected String pattern = null;

    /**
     * �o�͂̃t�H�[�}�b�g���w�肷��p�^�[����ݒ肷��B
     *
     * @param pattern �p�^�[��
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * �t�H�[�}�b�g�Ώۂ̒l�i������j�B
     *
     */
    protected String value = null;

    /**
     * �t�H�[�}�b�g�Ώۂ̒l��ݒ肷��B
     *
     * @param value �t�H�[�}�b�g�Ώۂ̒l
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w���B��� <code>SKIP_BODY</code>
     * @throws JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {

        Object value = this.value;
        if (value == null) {
            // bean���Ȃ��Ă���������ꍇ�ɂ́A�v�����ꂽbean�����b�N�A�b�v��
            // ������Ȃ����ꍇ�ɂ́A���^�[������
            if (ignore) {
                if (TagUtil.lookup(pageContext, name, scope) == null) {
                    return SKIP_BODY;  // �����o�͂��Ȃ�
                }
            }

            // �v�����ꂽ�v���p�e�B�̒l�����b�N�A�b�v����
            value = TagUtil.lookup(pageContext, name, property, scope);
            if (value == null) {
                return SKIP_BODY;  // �����o�͂��Ȃ�
            }
        }

        // �v���p�e�B�̒l��String�^�ł���Έ�xDate�֕ϊ�����
        Date date = null;
        if (value instanceof String) {
            // �E���g����������
            String trimed = StringUtil.rtrim((String) value);
            // ���t�t�H�[�}�b�g���擾����
            String dateFormat = StringUtils.defaultIfEmpty(
                                    getFormat(), getDefaultDateFormat());

            // ��xDate�^�ɕϊ�����
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            try {
                date = sdf.parse(trimed);
            } catch (ParseException e) {
                log.error("Date parsing error.");
                
                throw new JspTagException(e.getMessage());
            }
        } else if (value instanceof Date) {
            date = (Date) value;
        } else {
            return SKIP_BODY;  // �����o�͂��Ȃ�
        }

        // �t�H�[�}�b�g����
        String output = doFormat(date);

        if (id != null) {
            // id���w�肳��Ă����Ƃ��ɂ́A�X�N���v�e�B���O�ϐ��Ƃ��ė��p�ł���
            // �悤�Ƀy�[�W�X�R�[�v�ɃZ�b�g����B
            pageContext.setAttribute(id, output);
        } else {
            // id���w�肳��Ă��Ȃ��Ƃ��ɂ́A�v���p�e�B�̒l�����C�^�Ƀv�����g
            // ����B�K�؂Ƀt�B���^����B
            if (filter) {
                TagUtil.write(pageContext, TagUtil.filter(output));
            } else {
                TagUtil.write(pageContext, output);
            }
        }

        return SKIP_BODY;
    }

    /**
     * ���ׂẴA���P�[�g���ꂽ�������������B
     *
     */
    @Override
    public void release() {
        super.release();
        id = null;
        filter = true;
        ignore = false;
        name = null;
        property = null;
        scope = null;
        pattern = null;
        value = null;
    }

    /**
     * ���t�����̃t�H�[�}�b�g���s�����ۃ��\�b�h�B
     * �T�u�N���X�ŃI�[�o�[���C�h����B
     *
     * @param date ���t����
     * @return �t�H�[�}�b�g���ꂽ������
     */
    protected abstract String doFormat(Date date);

}
