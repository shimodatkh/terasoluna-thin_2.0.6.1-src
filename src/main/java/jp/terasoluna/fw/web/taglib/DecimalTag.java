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

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p><code>decimal</code>�^�O�̎����N���X�B</p>
 *
 * <p>
 *  �����A����я����_�t�����l���t�H�[�}�b�g���ďo�́A
 *  ���邢�̓X�N���v�e�B���O�ϐ��Ƃ��Ē�`����B<br>
 * </p>
 * <p>
 *  �t�H�[�}�b�g�ΏۂƂȂ鐔�l�f�[�^�́A<code>java.math.BigDecimal</code>
 *  �^�A���邢�� <code>java.lang.String</code> �^���T�|�[�g����B
 *  <code>java.lang.String</code> �^�̏ꍇ�A<code>BigDecimal</code>
 *  �̃R���X�g���N�^�ɂ���ĉ��߉\�ȕ�����ƂȂ��Ă���K�v������B
 * </p>
 *
 * <p>
 *  <code>BigDecimal</code> �̃R���X�g���N�^�ŉ��ߕs�\�ł������ꍇ�́A
 *  <code>NumberFormatException</code> ���X���[�����B
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p><code>DecimalTag</code> �ł́A�ȉ��̑������T�|�[�g����B</p>
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
 *    <td><code>id</code></td>
 *    <td>�Ȃ�</td>
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
 *    <td><code>filter</code></td>
 *    <td><code>true</code></td>
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
 *      ������Ȃ������Ƃ��ɖ������邩�ǂ������w�肷��B<code>false</code> ��
 *      �w�肷��ƁAbean��������Ȃ������Ƃ��� <code>JspException</code>
 *      ����������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>name</code></td>
 *    <td>�Ȃ�</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�t�H�[�}�b�g�Ώۂ̕�������v���p�e�B�Ɏ���bean�̖��O�B
 *      <code>property</code> �������w�肳��Ă��Ȃ������Ƃ��ɂ́A
 *      <code>name</code> �����Ŏw�肳�ꂽ�C���X�^���X
 *      ���t�H�[�}�b�g�̑ΏۂƂȂ�B���̏ꍇ�́A���̃C���X�^���X���g��
 *      <code>java.math.BigDecimal</code> �^�ł��邩�A���邢��
 *      <code>java.lang.String</code> �^�i���E���̋󔒏������
 *      <code>BigDecimal</code> �̃R���X�g���N�^�ɂ���ĉ��߉\�ł�����́j
 *      �̂ǂ��炩�ł���K�v������B<code>value</code>
 *      �������w�肳��Ă����ꍇ�ɂ́A���������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>property</code></td>
 *    <td>�Ȃ�</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> �����Ŏw�肳�ꂽ bean
 *      �ɂ����ăA�N�Z�X�����v���p�e�B�̖��O�B<code>value</code> ������
 *      �w�肳��Ă����ꍇ�ɂ͖��������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scope</code></td>
 *    <td>�i<code>findAttribute()</code> ���\�b�h�̌��������j</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left"><code>name</code> �����Ŏw�肳�ꂽbean
 *      ����������ۂ̃X�R�[�v�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>value</code></td>
 *    <td>�Ȃ�</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�t�H�[�}�b�g���镶����B������́A�E���̋󔒏������
 *      <code>BigDecimal</code>
 *      �̃R���X�g���N�^�ɂ���ĉ��߉\�ł���K�v������B<code>value</code>
 *      �������w�肵���ꍇ�ɂ́A<code>name</code>
 *      �����A����� <code>property</code> �����͖��������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>pattern</code></td>
 *    <td>�Ȃ�</td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�t�H�[�}�b�g����o�͌`���B<code>pattern</code>
 *      �����Ŏw�肵���o�͌`���́A<code>DecimalFormat</code>
 *      �N���X�̃p�^�[���Ƃ��ĉ��߂����B�ڍׂ́A<code>DecimalFormat</code>
 *      �N���X�̃h�L�������g���Q�Ƃ̂��ƁB
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scale</code></td>
 *    <td>�Ȃ�</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�ۂߓ����̏����_�ȉ������B<code>n</code>
 *      ���w�肵���ꍇ�ɂ́A������ <code>n + 1</code> �ʂ��ۂ߂���B
 *      �ۂ߃��[�h��<code>round</code>�����Ŏw�肷��B<code>round</code>
 *      �������w�肳��Ă��Ȃ��ꍇ�́A�l�̌ܓ����s����B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>round</code></td>
 *    <td>�Ȃ�</td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">�ۂ߃��[�h�B<code>scale</code>������
 *      �w�肳��Ă��鎞�A�L���ɂȂ�B<code>ROUND_HALF_UP</code>�i�l�̌ܓ��j�A
 *      <code>ROUND_FLOOR</code>�i�؂�̂āj�A<code>ROUND_CEILING</code>
 *     �i�؂�グ�j�� �ݒ�\�ł���B�f�t�H���g��<code>ROUND_HALF_UP
 *      </code>�����s�����B�����R�̐ݒ�ȊO���w�肵���ꍇ�́A
 *      <code>IllegalArgumentException</code>���X���[�����B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>
 *  <code>DecimalTag</code> �ł͈ȉ��̕ϐ����T�|�[�g����B
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
 *   <td align="left">���̃J�X�^���^�O�ŏo�͂����A�X�N���v�e�B���O�ϐ���
 *    �ݒ肷��ꍇ�̕ϐ����B
 *   </td>
 *  </tr>
 * </table>
 * </div>
 */
public class DecimalTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -2317420257734211793L;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(DecimalTag.class);
    
    /**
     * ���̃y�[�W���ŗ��p�ł���悤�ɂ��邽�߂̃X�N���v�e�B���O�ϐ��̖��O�B
     *
     */
    protected String id = null;

    /**
     * �X�N���v�e�B���O�ϐ��̖��O���擾����B
     *
     * @return �X�N���v�e�B���O�ϐ�
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * �X�N���v�e�B���O�ϐ��̖��O��ݒ肷��B
     *
     * @param id �X�N���v�e�B���O�ϐ��̖��O
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * HTML���ꕶ�����o�͂���ۂɃt�B���^�[���邩�ǂ����B�f�t�H���g��
     * <code>true</code>�B
     */
    protected boolean filter = true;

    /**
     * �o�͒���HTML���ꕶ�����t�B���^�[���邩�ǂ������擾����B
     *
     * @return �o�͒���HTML���ꕶ�����t�B���^�[����ꍇ�� <code>true</code>
     */
    public boolean getFilter() {
        return this.filter;
    }

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
     * bean��������Ȃ������ꍇ�ɖ������邩�ǂ������擾����B
     *
     * @return ��������ꍇ�� <code>true</code>
     */
    public boolean getIgnore() {
        return this.ignore;
    }

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
     * �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B�̖��O���擾����B
     *
     * @return �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B�̖��O
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B�̖��O��ݒ肷��B
     *
     * @param property �w�肳�ꂽbean�ɂ����ăA�N�Z�X�����v���p�e�B�̖��O
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
     * @param scope �w�肳�ꂽbean����������X�R�[�v
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * �o�͂̃t�H�[�}�b�g���w�肷��p�^�[���B
     * <code>DecimalFormat</code> �Ɠ��������Ŏw�肷��B
     *
     */
    protected String pattern = null;

    /**
     * �o�͂̃t�H�[�}�b�g���w�肷��p�^�[�����擾����B
     *
     * @return �o�͂̃t�H�[�}�b�g���w�肷��p�^�[��
     */
    public String getPattern() {
        return this.pattern;
    }

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
     * �l�̌ܓ���̏����_�ȉ��̌����B<code>n</code> ���w�肵���ꍇ�ɂ́A
     * ������ <code>n + 1</code> �ʂ��l�̌ܓ������B
     *
     */
    protected int scale = -1;

    /**
     * �l�̌ܓ���̏����_�ȉ��̌������擾����B
     *
     * @return �����_�ȉ��̌���
     */
    public int getScale() {
        return this.scale;
    }

    /**
     * �l�̌ܓ���̏����_�ȉ��̌�����ݒ肷��B
     *
     * @param scale �����_�ȉ��̌���
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * �ۂ߃��[�h�B
     *
     */
    protected String round = null;

    /**
     * �ۂ߃��[�h���擾����B
     *
     * @return �ۂ߃��[�h
     */
    public String getRound() {
        return this.round;
    }

    /**
     * �ۂ߃��[�h��ݒ肷��B
     *
     * @param round �ۂ߃��[�h
     */
    public void setRound(String round) {
        this.round = round;
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

        // �v���p�e�B�̒l��String�^�ł���Έ�xBigDecimal�֕ϊ�����
        BigDecimal bd = null;
        if (value instanceof String) {
            String trimed = StringUtil.rtrim((String) value);
            if ("".equals(trimed)) {
                return SKIP_BODY;  //  �����o�͂��Ȃ�
            }
            bd = new BigDecimal(trimed);
        } else if (value instanceof BigDecimal) {
            bd = (BigDecimal) value;
        } else {
            return SKIP_BODY;  // �����o�͂��Ȃ�
        }

        // �����_�ȉ��������w�肳��Ă����ꍇ
        if (scale >= 0) {
            // round�����ɐݒ肳�ꂽ�ۂ߃��[�h�����s����i�ݒ肪�����ꍇ�͎l�̌ܓ��j
            if (round != null) {
                if ("ROUND_FLOOR".equalsIgnoreCase(round)) {
                    bd = bd.setScale(scale, BigDecimal.ROUND_FLOOR);
                } else if ("ROUND_CEILING".equalsIgnoreCase(round)) {
                    bd = bd.setScale(scale, BigDecimal.ROUND_CEILING);
                } else if ("ROUND_HALF_UP".equalsIgnoreCase(round)) {
                    bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
                } else {
                    log.error("Please set a rounding mode");
                    throw new IllegalArgumentException(
                            "Please set a rounding mode");
                }
            } else {
                bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
            }
        }

        // �t�H�[�}�b�g����
        DecimalFormat df = new DecimalFormat(pattern);
        String output = df.format(bd);

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
        scale = -1;
    }

}
