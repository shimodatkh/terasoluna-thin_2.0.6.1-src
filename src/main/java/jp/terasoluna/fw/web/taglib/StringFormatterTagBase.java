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
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  ��������t�H�[�}�b�g���ďo�́A���邢�̓X�N���v�e�B���O�ϐ��Ƃ��Ē�`����
 *  �J�X�^���^�O���߂̒��ۊ��N���X�B
 *  </p>
 * <p>
 *  ������̐؂�o���A�g�������̃t�H�[�}�b�g���s���J�X�^���^�O�̃N���X��
 *  ���̊��N���X���p�����č쐬����B(<code>TERASOLUNA</code> �̒񋟂���
 *  <code>fmtx</code> �J�X�^���^�O���C�u������)
 * </p>
 * <p>
 *  ���̃N���X�ɂ���ăt�H�[�}�b�g���ꂽ�����񂪁A���X�|���X�֏o�͂����
 *  �̂��A���邢�̓X�N���v�e�B���O�ϐ��ɃZ�b�g�����̂�����̈Ⴂ�́A
 *  <code>id</code> �����̗L���Ō��肳���B
 * </p>
 * <p>
 *  �t�H�[�}�b�g�ΏۂƂȂ镶����́A�ȉ��̗D�揇���Ō��肳���B
 * </p>
 * <ol>
 *  <li><code>value</code> �������w�肳��Ă���ꍇ�ɂ́A
 *      ���̎w�肳�ꂽ��������t�H�[�}�b�g����B
 *  <li><code>name</code> ������ <code>bean</code>�����w�肳��Ă���ꍇ�ɂ́A
 *      ���̎w�肳�ꂽ <code>bean</code> �̃C���X�^���X�́A
 *      <code>property</code> �����Ŏw�肳�ꂽ�v���p�e�B���t�H�[�}�b�g����B
 * </ol>
 * <p>
 *  �������A<code>name</code> �������w�肳��Ă���Ƃ��ɁA
 *  <code>property</code> �������w�肳��Ă��Ȃ��ꍇ�ɂ́A<code>name</code>
 *  �����Ŏ������C���X�^���X�̒l�i<code>toString()</code> ���\�b�h��
 *  �Ԃ����I�u�W�F�N�g�̕�����\���j���A�t�H�[�}�b�g�̑ΏۂƂȂ�B
 * </p>
 * <p>
 *  <code>name</code> �����Ŏw�肳�ꂽ <code>bean</code> ��������Ȃ������Ƃ���
 *  �J�X�^���^�O�̓���́A<code>ignore</code> �����ɂ���Č��肳���B
 *  <code>ignore</code> ������true�ł���ꍇ�ɂ́A
 *  <code>bean</code> ��������Ȃ������Ƃ��ɂ͒P�ɖ�������A�����o�͂��Ȃ�
 *  �i<code>id</code> �������w�肳��Ă���ꍇ�ɂ́A�X�N���v�e�B���O�ϐ���
 *  �Z�b�g����Ȃ��j�B
 * </p>
 * <p>
 *  <code>ignore</code> ������false�i�f�t�H���g�j�ł���ꍇ�ɂ́A
 *  <code>name</code> �������w�肳�ꂽbean��������Ȃ������Ƃ��ɂ�
 *  <code>JspException</code> ����������B
 * </p>
 * <p>
 *  <code>bean</code> �̌����ΏۂƂ��āA<code>scope</code>
 *  �������w�肷�邱�Ƃ��ł���B
 *  <code>scope</code> �������w�肵�Ȃ������ꍇ�ɂ́A
 *  <code>javax.servlet.jsp.PageContext</code> �N���X��
 *  <code>findAttribute()</code> ���\�b�h�̌��������Ō��������B
 * </p>
 * <p>
 *  <code>id</code> �������w�肳�ꂽ�ꍇ�ɂ́A�t�H�[�}�b�g���ꂽ��������o��
 *  �����ɁA<code>id</code> �����Ŏw�肳�ꂽ�ϐ����̃X�N���v�e�B���O�ϐ��ɃZ�b�g
 *  ����B
 * </p>
 * <p>
 *  <code>filter</code> ������ <code>true</code>�i�f�t�H���g�j��
 *  ����Ƃ��ɂ́A�o�͂����ۂɁA<code>HTML</code> ���ꕶ���i
 *  <code>org.apache.struts.util.ResponceUtils</code> �N���X��
 *  <code>filter()</code> ���\�b�h�ŏ����������́B
 *  �u&lt;�v�A�u&gt;�v�A�u&amp;�v�A�u&quot;�v�̂S����)���G�X�P�[�v����B
 *  �X�N���v�e�B���O�ϐ��ɃZ�b�g����ꍇ�ɂ́A<code>filter</code> �����͖���
 *  ����A�t�H�[�}�b�g���ꂽ�����񂪂��̂܂܃X�N���v�e�B���O�ϐ��ɃZ�b�g����
 *  ��B
 * </p>
 * <p>
 *  <code>replaceSpToNbsp</code>������<code>true</code>�i�f�t�H���g�j��
 *  ����Ƃ��ɂ́A�o�͂����ۂɁA1Byte�R�[�h�̃X�y�[�X�i���p�X�y�[�X�j��
 *  ���݂���ꍇ�u&amp;nbsp;�v�ɕϊ�����B
 *  �X�N���v�e�B���O�ϐ��ɃZ�b�g����ꍇ�ɂ́A<code>replaceSpToNbsp</code> �����͖���
 *  ����A�t�H�[�}�b�g���ꂽ�����񂪂��̂܂܃X�N���v�e�B���O�ϐ��ɃZ�b�g����
 *  ��
 * </p>
 * <p>
 *  {@link StringFormatterTagBase} �N���X���p�������T�u�N���X�ł́A
 *  ���ۂɕ�����̃t�H�[�}�b�g���s�����ۃ��\�b�h <code>doFormat()</code>
 *  ����������B
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>
 *  <code>StringFormatterTagBase</code> �ł́A�ȉ��̑������T�|�[�g����B
 * </p>
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td> <b>������</b> </td>
 *    <td> <b>�f�t�H���g</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *    <td> <b>���s����</b> </td>
 *    <td> <b>�L�q</b> </td>
 *   </tr>
 *   <tr>
 *    <td> id </td>
 *    <td> - </td>
 *    <td> �C�� </td>
 *    <td> false </td>
 *    <td align="left">
 *     �t�H�[�}�b�g������������o�͂����ɁA�X�N���v�e�B���O�ϐ�
 *     �ɃZ�b�g����ۂɎw�肷��B�t�H�[�}�b�g���ꂽ��������X�N���v�e�B���O
 *     �ϐ��ɃZ�b�g����ꍇ�ɂ́A<code>filter</code> �����̎w��Ɋւ�炸HTML
 *     ���ꕶ���̓G�X�P�[�v����Ȃ��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> filter </td>
 *    <td>true</td>
 *    <td> - </td>
 *    <td> �C�� </td>
 *    <td align="left">
 *     �t�H�[�}�b�g���ꂽ��������o�͂���ۂɁA<code>HTML</code> ���ꕶ����
 *     �G�X�P�[�v���邩�ǂ������w�肷��B�������A<code>id</code> ������
 *     �w�肳��Ă����ꍇ�ɂ́A���������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> ignore </td>
 *    <td>false</td>
 *    <td> - </td>
 *    <td> �C�� </td>
 *    <td align="left">
 *     <code>name</code> �����Ŏw�肵�� bean��
 *     ������Ȃ������Ƃ��ɖ������邩�ǂ������w�肷��B<code>false</code> ��
 *     �w�肷��ƁA<code>bean</code> ��������Ȃ������Ƃ���
 *     <code>JspException</code>����������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> name </td>
 *    <td> - </td>
 *    <td> - </td>
 *    <td> �C�� </td>
 *    <td align="left">
 *     �t�H�[�}�b�g�Ώۂ̕�������v���p�e�B�Ɏ���
 *     <code>bean</code> �̖��O�B<code>property</code> �������w�肳���
 *     ���Ȃ������Ƃ��ɂ́A<code>name</code> �����Ŏw�肳�ꂽ�C���X�^���X��
 *     ������\�� <code>toString()</code> ���\�b�h�ŕԂ���镶����j
 *     ���t�H�[�}�b�g�̑ΏۂƂȂ�B<code>value</code>
 *     �������w�肳��Ă����ꍇ�ɂ́A���������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> property </td>
 *    <td> - </td>
 *    <td> - </td>
 *    <td> �C�� </td>
 *    <td align="left"><code>name</code> �����Ŏw�肳�ꂽ <code>bean</code>
 *     �ɂ����ăA�N�Z�X�����v���p�e�B�̖��O�B<code>value</code> ������
 *     �w�肳��Ă����ꍇ�ɂ͖��������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> scope </td>
 *    <td>�i<code>findAttribute()</code>���\�b�h�̌��������j</td>
 *    <td> - </td>
 *    <td> �C�� </td>
 *    <td align="left"><code>name</code> �����Ŏw�肳�ꂽ <code>bean</code>
 *     ����������ۂ̃X�R�[�v�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> value </td>
 *    <td>�Ȃ�</td>
 *    <td> - </td>
 *    <td> �C�� </td>
 *    <td align="left">�t�H�[�}�b�g���镶����B<code>value</code> ������
 *     �w�肵���ꍇ�ɂ́A<code>name</code> �����A����� <code>property</code>
 *     �����͖��������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceSpToNbsp</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *     ���̑�����true�ɃZ�b�g����A
 *     �w�肵��bean�v���p�e�B�̒l��1Byte�R�[�h�̃X�y�[�X�����݂���ꍇ
 *     <code>&amp;nbsp;</code>�ɒu������B
 *     �����ɂ��邽�߂ɂ́A���̑����ɖ����I�� false ���Z�b�g����K�v������B
 *     �������A<code>id</code> �������w�肳��Ă����ꍇ�ɂ́A���������B
 *     </td>
 *   </tr>
 *  </table>
 * </div>
 *
 * <br>
 *
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>
 *  <code>StringFormatterTagBase</code> �ł͈ȉ��̕ϐ����T�|�[�g����B
 * </p>
 * <div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <td> <b>�X�N���v�e�B���O�ϐ���</b> </td>
 *   <td> <b>�^</b> </td>
 *   <td> <b>�ϐ��̗��p�\�͈�</b> </td>
 *   <td> <b>�L�q</b> </td>
 *  </tr>
 *  <tr>
 *   <td>�J�X�^���^�O��<code>id</code>�����Ŏw�肳�ꂽ���O</td>
 *   <td><code>java.lang.String</code></td>
 *   <td>�J�n�^�O�ȍ~</td>
 *   <td align="left">
 *    ���̃J�X�^���^�O�ŏo�͂����A�X�N���v�e�B���O�ϐ��� �ݒ肷��ꍇ�̕ϐ����B
 *   </td>
 *  </tr>
 * </table>
 * </div>
 *
 * @see jp.terasoluna.fw.web.taglib.LeftTag
 * @see jp.terasoluna.fw.web.taglib.RTrimTag
 * @see jp.terasoluna.fw.web.taglib.LTrimTag
 * @see jp.terasoluna.fw.web.taglib.TrimTag
 *
 */
public abstract class StringFormatterTagBase extends TagSupport {

    /**
     * ���O�N���X
     */
    private static Log log = LogFactory.getLog(StringFormatterTagBase.class);
    
    /**
     * ���̃y�[�W���ŗ��p�ł���悤�ɂ��邽�߂̃X�N���v�e�B���O�ϐ��̖��O�B
     *
     */
    protected String id = null;

    /**
     * �X�N���v�e�B���O�ϐ��̖��O���擾����B
     *
     * @return �X�N���v�e�B���O�ϐ���
     */
    @Override
    public String getId() {
        return this.id;
    }

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
     * �o�͒���HTML���ꕶ�����t�B���^�[���邩�ǂ����B�f�t�H���g��true�B
     *
     */
    protected boolean filter = true;

    /**
     * �o�͒���HTML���ꕶ�����t�B���^�[���邩�ǂ������擾����B
     *
     * @return �o�͒���HTML���ꕶ�����t�B���^�[����ꍇ��true
     */
    public boolean getFilter() {
        return this.filter;
    }

    /**
     * �o�͒���HTML���ꕶ�����t�B���^�[���邩�ǂ�����ݒ肷��B
     *
     * @param filter �o�͒���HTML���ꕶ�����t�B���^�[����ꍇ��true
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * bean��������Ȃ������Ƃ��ɁA�P�ɖ�������i�����o�͂��Ȃ��j���ǂ����B
     * �������Ȃ��ꍇ�ɂ́A��O�𓊂���B�f�t�H���g��false�i��O�𓊂���j�B
     */
    protected boolean ignore = false;

    /**
     * bean��������Ȃ������ꍇ�ɖ������邩�ǂ������擾����B
     *
     * @return ��������ꍇ��true
     */
    public boolean getIgnore() {
        return this.ignore;
    }

    /**
     * bean��������Ȃ������ꍇ�ɖ������邩�ǂ�����ݒ肷��B
     *
     * @param ignore ��������ꍇ��true
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
     * �t�H�[�}�b�g�Ώۂ̃f�[�^���܂�bean�̖��O���擾����B
     *
     * @return bean�̖��O
     */
    public String getName() {
        return this.name;
    }

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
     * �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B�����擾����B
     *
     * @return �v���p�e�B��
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B����ݒ肷��B
     *
     * @param property �v���p�e�B��
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * �w�肳�ꂽbean����������X�R�[�v
     *
     */
    protected String scope = null;

    /**
     * �w�肳�ꂽbean����������X�R�[�v���擾����B
     *
     * @return �X�R�[�v
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * �w�肳�ꂽbean����������X�R�[�v��ݒ肷��B
     *
     * @param scope �X�R�[�v
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * �t�H�[�}�b�g�Ώۂ̒l�i������j
     *
     */
    protected String value = null;

    /**
     * �t�H�[�}�b�g�Ώۂ̒l���擾����B
     *
     * @return �t�H�[�}�b�g�Ώۂ̒l
     */
    public String getValue() {
        return this.value;
    }

    /**
     * �t�H�[�}�b�g�Ώۂ̒l��ݒ肷��B
     *
     * @param value �t�H�[�}�b�g�Ώۂ̒l
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>
     *  ���p�X�y�[�X�� <code>&amp;nbsp;</code> �ƒu������B
     * </p>
     */
    protected boolean replaceSpToNbsp = true;

    /**
     * <p>
     *  <code>replaceSpToNbsp</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return replaceSpToNbsp
     *   ���p�X�y�[�X���� <code>&amp;nbsp;</code> �ϊ��t���O
     */
    public boolean getReplaceSpToNbsp() {
        return this.replaceSpToNbsp;
    }

    /**
     * <p>
     *  <code>replaceSpToNbsp</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param replaceSpToNbsp
     *   ���p�X�y�[�X���� <code>&amp;nbsp;</code> �ϊ��t���O
     */
    public void setReplaceSpToNbsp(boolean replaceSpToNbsp) {
        this.replaceSpToNbsp = replaceSpToNbsp;
    }

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w��
     * @throws JspException �^�O�]�����ɃG���[
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

        // �t�H�[�}�b�g����
        String output = doFormat(value.toString());

        if (id != null) {
            // id���w�肳��Ă����Ƃ��ɂ́A�X�N���v�e�B���O�ϐ��Ƃ��ė��p�ł���
            // �悤�Ƀy�[�W�X�R�[�v�ɃZ�b�g����B
            pageContext.setAttribute(id, output);
        } else {
            // id���w�肳��Ă��Ȃ��Ƃ��ɂ́A�v���p�e�B�̒l�����C�^�Ƀv�����g
            // ����B�K�؂Ƀt�B���^����B
            if (filter) {
                output = TagUtil.filter(output);
            }

            // ���p�X�y�[�X��&nbsp;�ɒu��
            StringReader sr = null;
            BufferedReader br = null;
            String line = null;
            try {
                sr = new StringReader(output);
                br = new BufferedReader(sr);
                StringBuilder sbuf = new StringBuilder();
                StringBuilder strBuf = new StringBuilder();
                int index = 0;
                while ((line = br.readLine()) != null) {
                     // ���p�X�y�[�X��&nbsp;�ɒu��
                    if (replaceSpToNbsp && !"".equals(line)) {
                        strBuf.setLength(0);
                        char ch = line.charAt(0);
                        int i = 0;
                        for (i = 0; i < line.length(); i++) {
                            ch = line.charAt(i);
                            // ���p�X�y�[�X�ł���ꍇ�A"&nbsp;"�ɕϊ����܂��B
                            if (ch == ' ') {
                                strBuf.append("&nbsp;");
                            } else {
                                strBuf.append(ch);
                            }
                        }
                        line = strBuf.toString();
                    }
                    sbuf.append(line);
                    ++index;
                }
                output = sbuf.toString();
            } catch (IOException e) {
                if (log.isWarnEnabled()) {
                    log.warn("StringReader IO error : " + e);
                }
            } finally {
                if (sr != null) {
                    sr.close();
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e1) {
                    if (log.isWarnEnabled()) {
                        log.warn("StringReader close error : " + e1);
                    }
                }
            }

            TagUtil.write(pageContext, output);
        }

        return SKIP_BODY;
    }

    /**
     * ���ׂẴA���P�[�g���ꂽ�������������
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
        value = null;
        replaceSpToNbsp = true;
    }

    /**
     * ������̃t�H�[�}�b�g���s�����ۃ��\�b�h�B
     * �T�u�N���X�ŃI�[�o�[���C�h����B
     *
     * @param s �t�H�[�}�b�g�Ώە�����
     * @return �t�H�[�}�b�g���ꂽ������
     */
    protected abstract String doFormat(String s);

}
