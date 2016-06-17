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

package jp.terasoluna.fw.web.struts.form;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.beans.IndexedBeanWrapper;
import jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;
import org.apache.struts.validator.validwhen.ValidWhen;

/**
 * <code>Validator</code> �ǉ����[���N���X�B
 * 
 * <p>
 * <code>Struts</code>���񋟂��� {@link FieldChecks} ���g���������̓`�F�b�N�N���X�B<br>
 * {@link FieldChecksEx} �̊g�����[���Ƃ��ẮA�ȉ��̂��̂�����B
 * </p>
 * <table border="1">
 * <tr>
 * <td><center><b>���ؖ�</b></center></td>
 * <td><center><b>�N���C�A���g�`�F�b�N</b></center></td>
 * <td><center><b>���\�b�h��</b></center></td>
 * <td><center><b>(validation-rules.xml�ɋL�q����) ���[����</b></center></td>
 * <td><center><b>�T�v</b></center></td>
 * </tr>
 * <tr>
 * <td>�p�����`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateAlphaNumericString(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateAlphaNumericString()}</td>
 * <td><code>alphaNumericString</code></td>
 * <td>�t�B�[���h�l�����p�p�����݂̂ō\������Ă��邩�ǂ������`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�啶���p�����`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateCapAlphaNumericString(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest)
 * validateCapAlphaNumericString()}</td>
 * <td><code>capAlphaNumericString</code></td>
 * <td>�l���啶���̔��p�p�����݂̂ō\������Ă��邩�ǂ������`�F�b�N�B </td>
 * </tr>
 * <tr>
 * <td>�t�B�[���h���l�`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateNumber(Object, ValidatorAction, Field, ActionMessages,
 * Validator, HttpServletRequest) validateNumber()}</td>
 * <td><code>number</code></td>
 * <td>�t�B�[���h�l�����l�ł��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>���p�J�i�����`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateHankakuKanaString(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateHankakuKanaString()}</td>
 * <td><code>hankakuKanaString</code></td>
 * <td>�t�B�[���h�l�����p�J�i�݂̂ō\������Ă��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>���p�����`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateHankakuString(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateHankakuString()}</td>
 * <td><code>hankakuString</code></td>
 * <td>�t�B�[���h�l�����p�̕����݂̂ō\������Ă��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�S�p�����`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateZenkakuString(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateZenkakuString()}</td>
 * <td><code>zenkakuString</code></td>
 * <td>�t�B�[���h�l���S�p�̕����݂̂ō\������Ă��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�S�p�J�i�����`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateZenkakuKanaString(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateZenkakuKanaString()}</td>
 * <td><code>zenkakuKanaString</code></td>
 * <td>�t�B�[���h�l���S�p�J�i�����݂̂ō\������Ă��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>���͋֎~�����`�F�b�N</td>
 * <td><center>�~</center></td>
 * <td>{@link #validateProhibited(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateProhibited()}</td>
 * <td><code>prohibited</code></td>
 * <td>�t�B�[���h�l�ɓ��͋֎~�������܂܂�Ă��Ȃ����Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�����񒷈�v�`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateStringLength(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateStringLength()}</td>
 * <td><code>stringLength</code></td>
 * <td>�t�B�[���h�l���w�肵�������񒷂ƈ�v���邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>����������`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateNumericString(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateNumericString()}</td>
 * <td><code>numericString</code></td>
 * <td>�t�B�[���h�l�������݂̂ō\������Ă��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�o�C�g�񒷃`�F�b�N</td>
 * <td><center>�~</center></td>
 * <td>{@link #validateByteLength(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateByteLength()}</td>
 * <td><code>byteLength</code></td>
 * <td>�t�B�[���h�l�̃o�C�g�񒷂��w�肵�������ƈ�v���邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�o�C�g��͈̓`�F�b�N</td>
 * <td><center>�~</center></td>
 * <td>{@link #validateByteRange(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateByteRange()}</td>
 * <td><code>byteRange</code></td>
 * <td>�t�B�[���h�l�̃o�C�g�񒷂��w�肵���͈͈ȓ��ł��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>���t�͈̓`�F�b�N</td>
 * <td><center>��</center></td>
 * <td>{@link #validateDateRange(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateDateRange()}</td>
 * <td><code>dateRange</code></td>
 * <td>�t�B�[���h�l�̓��t���w�肵���͈͈ȓ��ł��邱�Ƃ��`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�����t�B�[���h�`�F�b�N</td>
 * <td><center>�~</center></td>
 * <td>{@link #validateMultiField(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateMultiField()}</td>
 * <td><code>multiField</code></td>
 * <td>�����t�B�[���h�Ԃ̑��փ`�F�b�N�B</td>
 * </tr>
 * <tr>
 * <td>�z��E�R���N�V�����^�t�B�[���h�S�v�f�`�F�b�N</td>
 * <td><center>�~</center></td>
 * <td>{@link #validateArraysIndex(Object, ValidatorAction, Field,
 * ActionMessages, Validator, HttpServletRequest) validateArraysIndex()}</td>
 * <td>(<code>validation.xml</code>�� <code>&lt;depend&gt;</code>�v�f�Ō���)</td>
 * <td>depends�����Ŏw�肳�ꂽ���̓`�F�b�N��z��E�R���N�V�����^�̃t�B�[���h�l �̑S�Ă̒l�ɑ΂��ă`�F�b�N���s���B</td>
 * </tr>
 * </ul>
 * </table>
 * <p>
 * �t�B�[���h�`�F�b�N�̏ڍׂɂ��ẮA�e���\�b�h�̐������Q�ƁB<br>
 * �g������Validator�𗘗p���邽�߂ɂ́A�A�N�V�������� ���ؓ��e���L�q������`�t�@�C��( <code>validation.xml</code> )
 * ���쐬����K�v������B
 * </p>
 * <p>�K�{�`�F�b�N�ȊO�̃`�F�b�N���[���ł́A
 * ���p�X�y�[�X�݂̂̕����񂪓��͒l�Ƃ��ēn����Ă����ꍇ�A�G���[�Ɣ��肳��Ȃ��B
 * �G���[�Ƃ���ꍇ�͕K�{�`�F�b�N�Ƒg�ݍ��킹�邩�A
 * ���p�X�y�[�X�̃`�F�b�N��ǉ����邱�ƁB</p>
 * 
 * <h5>�P�̂̃t�B�[���h����</h5>
 * <p>
 * <code>&lt;formset&gt;</code>�^�O������ <code>&lt;form&gt;</code> �v�f��
 * <code>name</code> �����ɁA <code>struts-config.xml</code>�Œ�`����Ă���
 * �A�N�V�����̃p�X�����L�q����B<br>
 * �L�q���Ȃ��ꍇ�́A�Y������A�N�V�����p�X�̌��؂͈�؍s���Ȃ��B <code>&lt;field&gt;</code>�v�f��
 * <code>property</code> �����ɁA �Ώۂ̃t�B�[���h���A<code>depends</code>������
 * <code>validation-rules.xml</code>�Œ�`����Ă��� ���؃��[�������L�q����B
 * �ЂƂ̃t�H�[���ɕ����̌��؂��s���ꍇ�i�K�{���́A�����Ȃǁj�́A �J���}��؂�ŋL�q���Ă����B<br>
 * �������A�o�͂����G���[���b�Z�[�W�͂P�t�B�[���h�ɑ΂��A �P�݂̂ł���B �i�K�{���́A�����`�F�b�N�̗����Ɉᔽ���Ă���ꍇ�A�ǂꂩ�P��
 * �G���[�����o�͂���Ȃ��j
 * </p>
 * <h5>validation.xml�̋L�q��i�P�̃t�B�[���h���؁j</h5>
 * <code><pre>
 *  &lt;formset&gt;
 *    �E�E�E
 *    &lt;!-- �P�̂̃t�B�[���h���� --&gt;
 *    &lt;form name=&quot;/logon&quot;&gt;
 *      &lt;field property=&quot;companyId&quot;
 *          depends=&quot;required,alphaNumericString,maxlength&quot;&gt;
 *        &lt;arg0 key=&quot;logon.companyId&quot;/&gt;
 *        &lt;arg1 key=&quot;${var:maxlength}&quot; resource=&quot;false&quot;/&gt;
 *        &lt;var&gt;
 *          &lt;var-name&gt;maxlength&lt;/var-name&gt;
 *          &lt;var-value&gt;10&lt;/var-value&gt;
 *        &lt;/var&gt;
 *      &lt;/field&gt;
 *    &lt;/form&gt;
 *    �E�E�E
 *  &lt;/formset&gt;
 * </pre></code>
 * <h5>�z��E�R���N�V�����^�̈ꗗ����</h5>
 * <p>
 * �z��E�R���N�V�����^�̈ꗗ���؂́A���L�̓_���g�����Ă���B
 * <ul>
 * <li>�Ώۃt�B�[���h�̔z��E�R���N�V�����v�f�ɑ΂��A�r���ŃG���[�� �ԋp����Ă��Ō�̗v�f�܂Ō��؂��s��</li>
 * <li>�e�v�f�̃C���f�b�N�X�ԍ����G���[���b�Z�[�W�ɔ��f�ł���</li>
 * </ul>
 * ��{�I�ɂ͒P�̃t�B�[���h���؂Ɠ��l<code>validation.xml</code>�� <code>&lt;form&gt;</code>
 * �v�f���쐬���邪�A �z��E�R���N�V�����^�̈ꗗ���؂��s�����߂ɂ́A���L�̐ݒ肪�K�v�ł���B
 * <ol>
 * <li>�z��E�R���N�V�����^�̈ꗗ�\���ɂ́A<b>�K�� <code>property</code> �����Ńt�H�[�����̃t�B�[���h�����w�肷��</b>�B�܂��v���p�e�B���ɂ�
 * JXPathIndexedBeanWrapperImpl�̎d�l�ɏ]���A�l�X�g�����v���p�e�B���� �w�肷�邱�Ƃ��\�ł���B</li>
 * <li><code>&lt;depends&gt;</code>�Ŏw�肷�郋�[���́A
 * <code>validation-rules.xml</code>�� ������ <code>Array</code> �ł��郋�[������p����B</li>
 * <li>���؂����t�H�[����FormEx�C���^�t�F�[�X�� �������Ă���K�v������B</li>
 * </li>
 * </ol>
 * �z��E�R���N�V�����^�̈ꗗ���؂ɑΉ����郋�[���͈ȉ��̒ʂ�ł���B
 * <ul>
 * <li><code>requiredArray</code></li>
 * <li><code>minLengthArray</code></li>
 * <li><code>maxLengthArray</code></li>
 * <li><code>maskArray</code></li>
 * <li><code>byteArray</code></li>
 * <li><code>shortArray</code></li>
 * <li><code>integerArray</code></li>
 * <li><code>longArray</code></li>
 * <li><code>floatArray</code></li>
 * <li><code>doubleArray</code></li>
 * <li><code>dateArray</code></li>
 * <li><code>intRangeArray</code></li>
 * <li><code>doubleRangeArray</code></li>
 * <li><code>floatRangeArray</code></li>
 * <li><code>creditCardArray</code></li>
 * <li><code>emailArray</code></li>
 * <li><code>urlArray</code></li>
 * <li><code>alphaNumericStringArray</code></li>
 * <li><code>hankakuKanaStringArray</code></li>
 * <li><code>hankakuStringArray</code></li>
 * <li><code>zenkakuStringArray</code></li>
 * <li><code>zenkakuKanaStringArray</code></li>
 * <li><code>capAlphaNumericStringArray</code></li>
 * <li><code>numberArray</code></li>
 * <li><code>numericStringArray</code></li>
 * <li><code>prohibitedArray</code></li>
 * <li><code>stringLengthArray</code></li>
 * <li><code>dateRangeArray</code></li>
 * <li><code>byteLengthArray</code></li>
 * <li><code>byteRangeArray</code></li>
 * </ul>
 * </p>
 * <h5>validation.xml�̋L�q��i�z��E�R���N�V�����^�ꗗ�\���̌��؁j</h5>
 * <code><pre>
 *  &lt;formset&gt;
 *    �E�E�E
 *    &lt;!-- �z��E�R���N�V�����^�ꗗ�\���̌��؊m�F --&gt;
 *    &lt;form name=&quot;/isValid&quot;&gt;
 *      &lt;field property=&quot;codeArray&quot;
 *          depends=&quot;requiredArray,alphaNumericStringArray&quot;&gt;
 *        &lt;arg0 &lt;b&gt;key=&quot;##INDEX&quot; resource=&quot;false&quot;&lt;/b&gt;/&gt;
 *        &lt;arg1 key=&quot;sampleValidate.codeArray&quot;/&gt;
 *      &lt;/field&gt;
 *    &lt;/form&gt;
 *    �E�E�E
 *  &lt;/formset&gt;
 * </pre></code>
 * <p>
 * �z��E�R���N�V�����^�̃C���f�b�N�X�ԍ����G���[���b�Z�[�W�� ���ߍ��ނ��߂ɂ́A<code>&lt;arg0�`3&gt;</code>�v�f�̉��ꂩ��
 * <code>key</code>�� <code>&quot;##INDEX&quot;</code> �Ƃ�����������w�肵�A<code>&lt;resource&gt;</code>
 * �v�f�� <code>&quot;false&quot;</code> �ɂ���B<br>
 * �P�̂̃t�B�[���h���؂Ɠ��l�A<code>&lt;depends&gt;</code>
 * �ŕ����̌��؂��s���ꍇ�J���}��؂�ŕ����̌��؃��[�����w��ł���B<br>
 * <code>Validator</code> �t���[�����[�N�𗘗p�����t�H�[���́A <code>
 * {@link DynaValidatorActionFormEx}
 * </code>�A
 * <code>
 * {@link ValidatorActionFormEx}
 * </code> ���Q�ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 * @see jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl
 * 
 */
public class FieldChecksEx extends FieldChecks {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -5669122584668175380L;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(FieldChecksEx.class);

    /**
     * �z��C���f�b�N�X�^�t�B�[���h��Index�l�B
     */
    public static final String INDEX = "##INDEX";

    /**
     * ���p�J�i������ł���B<code>ApplicationResources</code> �t�@�C���ɒ�`����B
     */
    protected static String hankakuKanaList = "����������������������¯�������������������֬�������ܦ��ް�����";

    /**
     * �S�p�J�i������B<code>ApplicationResources</code> �t�@�C���ɒ�`����B
     */
    protected static String zenkakuKanaList = "�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\"
            + "�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z" + "�o�r�u�x�{�p�s�v�y�|�}�~����������������������������"
            + "�����������b���[";

    /**
     * <code>ApplicationResources</code> �t�@�C���ɒ�`���ꂽ���p�����e�[�u�����擾����L�[�B
     */
    protected static final String HANKAKU_KANA_LIST_KEY = "validation.hankaku.kana.list";

    /**
     * <code>ApplicationResources</code> �t�@�C���ɒ�`���ꂽ�S�p�����e�[�u�����擾����L�[�B
     */
    protected static final String ZENKAKU_KANA_LIST_KEY = "validation.zenkaku.kana.list";

    /**
     * UNICODE�����R�[�h'\u0000'����'\u00ff'�͈͓̔��ɑ��݂��� �S�p������B
     */
    protected static final String ZENKAKU_BEGIN_U00_LIST = "�_�������N�ʁ��}�L���~��";

    static {
        String value = null;
        // ���p�J�i������e�[�u�����擾
        value = PropertyUtil.getProperty(HANKAKU_KANA_LIST_KEY);
        if (value != null) {
            hankakuKanaList = value;
        }

        // �S�p�J�i������e�[�u�����擾
        value = PropertyUtil.getProperty(ZENKAKU_KANA_LIST_KEY);
        if (value != null) {
            zenkakuKanaList = value;
        }
    }

    /**
     * �w�肳�ꂽ���������p�J�i�����ł��邱�Ƃ��`�F�b�N����B
     * 
     * @param c
     *            ����
     * @return ���p�J�i�����ł���� <code>true</code>
     */
    protected static boolean isHankakuKana(char c) {
        return hankakuKanaList.indexOf(c) >= 0;
    }

    /**
     * �w�肳�ꂽ���������p�����ł��邱�Ƃ��`�F�b�N����B
     * 
     * @param c
     *            ����
     * @return ���p�����ł���� <code>true</code>
     */
    protected static boolean isHankaku(char c) {
        return (c <= '\u00ff' && ZENKAKU_BEGIN_U00_LIST.indexOf(c) < 0)
                || isHankakuKana(c);
    }

    /**
     * �w�肳�ꂽ�������S�p�����ł��邱�Ƃ��`�F�b�N����B
     * 
     * @param c
     *            ����
     * @return �S�p�����ł���� <code>true</code>
     */
    protected static boolean isZenkaku(char c) {
        return !isHankaku(c);
    }

    /**
     * �w�肳�ꂽ�������S�p�J�i�����ł��邱�Ƃ��`�F�b�N����B
     * 
     * @param c
     *            ����
     * @return �S�p�J�i�����ł���� <code>true</code>
     */
    protected static boolean isZenkakuKana(char c) {
        return zenkakuKanaList.indexOf(c) >= 0;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���p�����ł��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A<code>validation-rules.xml</code>
     * �ɋL�q����B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Struts�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            ActionMessages �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateAlphaNumericString(Object bean,
            ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {
        // �t�B�[���h�̃N���[���쐬
        Field fieldClone = (Field) field.clone();
        fieldClone.addVar("mask", "^[0-9a-zA-Z]*$", "false");
        // �p���l�`�F�b�N���s��
        return validateMask(bean, va, fieldClone, errors, validator, request);
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���啶���p�����ł��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A<code>validation-rules.xml</code>
     * �ɋL�q����B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Struts�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            ActionMessages �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateCapAlphaNumericString(Object bean,
            ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {

        // �t�B�[���h�̃N���[���쐬
        Field fieldClone = (Field) field.clone();
        fieldClone.addVar("mask", "^[0-9A-Z]*$", "false");
        // �p���l�`�F�b�N���s��
        return validateMask(bean, va, fieldClone, errors, validator, request);
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�����l�ł��邱�Ƃ��`�F�b�N����B
     * 
     * �܂��A���͂��ꂽ�������p���A<code>BigDecimal</code> �^�𐶐�����
     * �����Ő����s�\�Ȃ�΃G���[�p��ActionMessage���쐬���A<code>false</code> ��ԋp����B
     * 
     * ���ɐ������̌������w�肳��Ă���ꍇ�ɁA�����̊m�F���s���B <code>validation.xml</code> ��
     * <code>isAccordedInteger()</code> �� <code>"true"</code> �w�肳��Ă���ꍇ�̂�
     * ���������̓���`�F�b�N���s����B �`�F�b�N�Ɉ������������ꍇ�́A�G���[�p��ActionMessage���쐬���Afalse��ԋp����B
     * 
     * �Ō�ɏ������̌������w�肳��Ă���ꍇ�ɁA�����̊m�F���s���B
     * validation.xml��isAccordedScale��"true"�ł���ꍇ�̂� ���������̓���`�F�b�N���s����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A<code>validation-rules.xml</code>
     * �ɋL�q����B<br>
     * ���L�́A������3�A������2�ł��鐔�l�����؂����ł���B
     * </p>
     * 
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;number&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;integerLength&lt;/var-name&gt;
     *      &lt;var-value&gt;3&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;scale&lt;/var-name&gt;
     *      &lt;var-value&gt;2&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;isAccordedInteger&lt;/var-name&gt;
     *      &lt;var-value&gt;true&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;isAccordedScale&lt;/var-name&gt;
     *      &lt;var-value&gt;true&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     * <tr>
     * <td><center><b><code>var-name</code></b></center></td>
     * <td><center><b><code>var-value</code></b></center></td>
     * <td><center><b>�K�{��</b></center></td>
     * <td><center><b>�T�v</b></center></td>
     * </tr>
     * <tr>
     * <td> <code>integerLength</code> </td>
     * <td> ���������� </td>
     * <td> <code>false</code> </td>
     * <td>�����̌�����ݒ肷��A<code>isAccordedInteger</code>�w�肪
     * �����Ƃ��́A�w�茅���ȓ��̌��؂��s���B�ȗ����A�܂��͔񐔒l�� �ݒ肵���Ƃ��́A���؂��s�Ȃ�Ȃ��B</td>
     * </tr>
     * <tr>
     * <td> <code>scale</code> </td>
     * <td> ���������� </td>
     * <td> <code>false</code> </td>
     * <td>�����l�̌�����ݒ肷��A<code>isAccordedScale</code>�w�肪
     * �����Ƃ��́A�w�茅���ȓ��̌��؂��s���B�ȗ����A�܂��͔񐔒l�� �ݒ肵���Ƃ��́A���؂��s�Ȃ�Ȃ��B</td>
     * </tr>
     * <tr>
     * <td> <code>isAccordedInteger</code> </td>
     * <td> ����������v�`�F�b�N </td>
     * <td> <code>false</code> </td>
     * <td> <code>true</code>���w�肳�ꂽ�Ƃ��A���������̈�v�`�F�b�N�� �s�Ȃ���B�ȗ����A<code>true</code>�ȊO�̕����񂪐ݒ肳�ꂽ����
     * �����ȓ��`�F�b�N�ƂȂ�B</td>
     * </tr>
     * <tr>
     * <td> <code>isAccordedScale</code> </td>
     * <td> ����������v�`�F�b�N </td>
     * <td> <code>false</code> </td>
     * <td> <code>true</code>���w�肳�ꂽ�Ƃ��A���������̈�v�`�F�b�N�� �s�Ȃ���B�ȗ����A<code>true</code>�ȊO�̕����񂪐ݒ肳�ꂽ����
     * �����ȓ��`�F�b�N�ƂȂ�B</td>
     * </tr>
     * </table>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Struts�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            ActionMessages �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateNumber(Object bean, ValidatorAction va,
            Field field, ActionMessages errors, Validator validator,
            HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        // �����������擾
        String integerLength = field.getVarValue("integerLength");
        // �����������擾
        String scaleStr = field.getVarValue("scale");
        // ����������v�`�F�b�N��
        String isAccordedInteger = field.getVarValue("isAccordedInteger");
        // ����������v�`�F�b�N��
        String isAccordedScale = field.getVarValue("isAccordedScale");

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isHankaku(chars[i])) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }
        // �����������w�肳��Ă���ꍇ�A�������`�F�b�N���s��
        if (GenericValidator.isInt(integerLength)) {
            try {
                BigDecimal bd = new BigDecimal(value);
                // ��������Βl�̂ݒ��o
                BigInteger bi = bd.toBigInteger().abs();
                // ��������
                int length = bi.toString().length();
                // validation.xml�Ŏw�肳�ꂽ����
                int checkLength = Integer.valueOf(integerLength).intValue();
                // �����I�[�o���́Afalse��ԋp
                if (length > checkLength) {
                    errors.add(field.getKey(), Resources.getActionMessage(
                            validator, request, va, field));
                    return false;
                }
                // ��v�w�肳��Ă���Ƃ�
                if (isAccordedInteger != null
                        && "true".equals(isAccordedInteger)) {
                    // �����s��v�́Afalse��ԋp
                    if (length != checkLength) {
                        errors.add(field.getKey(), Resources.getActionMessage(
                                validator, request, va, field));
                        return false;
                    }
                }
            } catch (NumberFormatException nfe) {
                // ���l�^�ɕϊ��ł��Ȃ����Afalse��ԋp
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }

        // �����������w�肳��Ă���ꍇ�A�������`�F�b�N���s��
        if (GenericValidator.isInt(scaleStr)) {
            int scale = 0;
            int checkScale = 0;
            try {
                BigDecimal bd = new BigDecimal(value);
                scale = bd.scale();
                // validation.xml�Ŏw�肳�ꂽ����
                checkScale = Integer.valueOf(scaleStr).intValue();
            } catch (NumberFormatException e) {
                // ���l�^�ɕϊ��ł��Ȃ����Afalse��ԋp
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
            // �����I�[�o���́Afalse��ԋp
            if (scale > checkScale) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
            // ��v�w�肳��Ă���Ƃ�
            if (isAccordedScale != null && "true".equals(isAccordedScale)) {
                // �����s��v�́Afalse��ԋp
                if (scale != checkScale) {
                    errors.add(field.getKey(), Resources.getActionMessage(
                            validator, request, va, field));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�����p�J�i������ł��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A<code>validation-rules.xml</code>
     * �ɋL�q����B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            <code>Validator</code>�ɂ��p�ӂ��ꂽ <code>ValidatorAction</code>
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            ActionMessages �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateHankakuKanaString(Object bean,
            ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isHankakuKana(chars[i])) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�����p������ł��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B�G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            <code>Validator</code>�ɂ��p�ӂ��ꂽ <code>ValidatorAction</code>
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateHankakuString(Object bean,
            ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isHankaku(chars[i])) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���S�p������ł��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B�G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            <code>Validator</code>�ɂ��p�ӂ��ꂽ <code>ValidatorAction</code>
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateZenkakuString(Object bean,
            ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isZenkaku(chars[i])) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���S�p�J�^�J�i������ł��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B�G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            <code>Validator</code>�ɂ��p�ӂ��ꂽ <code>ValidatorAction</code>
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateZenkakuKanaString(Object bean,
            ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isZenkakuKana(chars[i])) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�ɓ��͋֎~�����񂪍������Ă��邩 �ǂ������`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B<br>
     * �ȉ��́A�֎~������`�F�b�N�̐ݒ��ł���B
     * </p>
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;prohibited&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;chars&lt;/var-name&gt;
     *      &lt;var-value&gt;!&quot;#$%&amp;'()&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     * <tr>
     * <td><center><b><code>var-name</code></b></center></td>
     * <td><center><b><code>var-value</code></b></center></td>
     * <td><center><b>�K�{��</b></center></td>
     * <td><center><b>�T�v</b></center></td>
     * </tr>
     * <tr>
     * <td> chars </td>
     * <td>���͋֎~�L�����N�^</td>
     * <td>false</td>
     * <td>���͕����񂪁A���͋֎~�L�����N�^�̉��ꂩ�ɑΉ�����Ƃ��A <code>false</code>��ԋp����B�ȗ�����<code>true</code></td>
     * </tr>
     * </table>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            <code>Validator</code>�ɂ��p�ӂ��ꂽ <code>ValidatorAction</code>
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateProhibited(Object bean, ValidatorAction va,
            Field field, ActionMessages errors, Validator validator,
            HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // ���͋֎~������
        String prohibitedStr = field.getVarValue("chars");
        if (prohibitedStr == null || "".equals(prohibitedStr)) {
            // ���͋֎~������null�܂��͋󕶎��̎��Atrue��ԋp
            return true;
        }

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        char[] chars = value.toCharArray();

        // ���͋֎~�����񂪖��ݒ�̏ꍇ�A�`�F�b�N���s��Ȃ�
        for (int i = 0; i < chars.length; i++) {
            if (prohibitedStr.indexOf(chars[i]) >= 0) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�������݂̂���Ȃ镶����ł��邱�Ƃ� �`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            <code>Validator</code>�ɂ��p�ӂ��ꂽ <code>ValidatorAction</code>
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateNumericString(Object bean,
            ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {
        // �t�B�[���h�̃N���[���쐬
        Field fieldClone = (Field) field.clone();
        fieldClone.addVar("mask", "^[0-9]*$", "false");
        // ���l�`�F�b�N���s��
        return validateMask(bean, va, fieldClone, errors, validator, request);
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̕����񒷂���v���Ă��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B<br>
     * �ȉ��́A�����񒷂�5�ł���Ƃ��̂�<code>true</code>��ԋp���� ���؂̐ݒ��ł���B
     * </p>
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;stringLength&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;length&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     * <tr>
     * <td><center><b><code>var-name</code></b></center></td>
     * <td><center><b><code>var-value</code></b></center></td>
     * <td><center><b>�K�{��</b></center></td>
     * <td><center><b>�T�v</b></center></td>
     * </tr>
     * <tr>
     * <td> stringLength </td>
     * <td>���͕����񌅐�</td>
     * <td>false</td>
     * <td>���͕����񒷂��w�肳�ꂽ�����ł���Ƃ��� <code>true</code> �ȗ�����<code>true</code>���ԋp�����B</td>
     * </tr>
     * </table>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateStringLength(Object bean, ValidatorAction va,
            Field field, ActionMessages errors, Validator validator,
            HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // ������
        String lengthStr = field.getVarValue("stringLength");
        // �����񒷎w�肪���݂��Ȃ����A�`�F�b�N���s��Ȃ�
        if (lengthStr == null || "".equals(lengthStr)) {
            return true;
        }
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        int checkLength = 0;
        try {
            checkLength = Integer.valueOf(lengthStr).intValue();
        } catch (NumberFormatException e) {
            // �����񒷎w�肪int�ϊ��s�\�ȏꍇ
            log.error("stringLength is not numeric(integer).", e);
            return true;
        }
        // ���͂��ꂽ�������length�����������Ȃ����Afalse��ԋp
        if (value.length() != checkLength) {
            errors.add(field.getKey(), Resources.getActionMessage(validator,
                    request, va, field));
            return false;
        }

        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̃o�C�g�񒷂���v���Ă��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B<br>
     * �ȉ��́A�o�C�g�񒷂�5�ł���Ƃ��̂�<code>true</code>��ԋp���� ���؂̐ݒ��ł���B
     * </p>
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;byteLength&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;byteLength&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;encoding&lt;/var-name&gt;
     *      &lt;var-value&gt;Windows-31J&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     * <tr>
     * <td><center><b><code>var-name</code></b></center></td>
     * <td><center><b><code>var-value</code></b></center></td>
     * <td><center><b>�K�{��</b></center></td>
     * <td><center><b>�T�v</b></center></td>
     * </tr>
     * <tr>
     * <td> byteLength </td>
     * <td>���͕�����o�C�g��</td>
     * <td>false</td>
     * <td>���͕�����o�C�g�������؂��邽�߂̃o�C�g���B</td>
     * </tr>
     * <tr>
     * <td> encoding </td>
     * <td>�����G���R�[�f�B���O</td>
     * <td>false</td>
     * <td>���͕������o�C�g��ɕϊ�����ۂɎg�p���镶���G���R�[�f�B���O�B �ȗ����ꂽ�ꍇ��VM�̃f�t�H���g�G���R�[�f�B���O���g�p�����B</td>
     * </tr>
     * </table>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateByteLength(Object bean, ValidatorAction va,
            Field field, ActionMessages errors, Validator validator,
            HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // ���͒l���擾
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        // �o�C�g��
        String lengthStr = field.getVarValue("byteLength");
        // �o�C�g���w�肪���݂��Ȃ����A�`�F�b�N���s��Ȃ�
        if (lengthStr == null || "".equals(lengthStr)) {
            if (log.isInfoEnabled()) {
                log.info("length is not specified.");
            }
            return true;
        }
        int checkLength = 0;
        try {
            checkLength = Integer.valueOf(lengthStr).intValue();
        } catch (NumberFormatException e) {
            // �����񒷎w�肪int�ϊ��s�\�ȏꍇ
            log.error("byteLength is not numeric(integer).", e);
            return true;
        }

        // �G���R�[�f�B���O
        String encoding = field.getVarValue("encoding");

        int bytesLength = getByteLength(value, encoding);

        // ���͂��ꂽ������̃o�C�g����length�����������Ȃ����Afalse��ԋp
        if (bytesLength != checkLength) {
            errors.add(field.getKey(), Resources.getActionMessage(validator,
                    request, va, field));
            return false;
        }

        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̃o�C�g�񒷂� �w�肵���͈͓��ł��邱�Ƃ��`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B<br>
     * �ȉ��́A�o�C�g�񒷂�5�ȏ�A10�ȉ��ł���Ƃ��̂� <code>true</code> ��ԋp���錟�؂̐ݒ��ł���B
     * </p>
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;escape&quot;
     *      depends=&quot;byteRange&quot;&gt;
     *    &lt;arg0 key=&quot;sample.escape&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;maxByte&lt;/var-name&gt;
     *      &lt;var-value&gt;10&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;minByte&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;encoding&lt;/var-name&gt;
     *      &lt;var-value&gt;Windows-31J&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     * <tr>
     * <td><center><b><code>var-name</code></b></center></td>
     * <td><center><b><code>var-value</code></b></center></td>
     * <td><center><b>�K�{��</b></center></td>
     * <td><center><b>�T�v</b></center></td>
     * </tr>
     * <tr>
     * <td> maxByte </td>
     * <td>�ő�o�C�g��</td>
     * <td>false</td>
     * <td>���͕�����o�C�g�������؂��邽�߂̍ő�o�C�g���B �ȗ������ꍇ��int�^�̍ő�l�B</td>
     * </tr>
     * <tr>
     * <td> minByte </td>
     * <td>�ő�o�C�g��</td>
     * <td>false</td>
     * <td>���͕�����o�C�g�������؂��邽�߂̍ŏ��o�C�g���B �ȗ������ꍇ��0�B</td>
     * </tr>
     * <tr>
     * <td> encoding </td>
     * <td>�����G���R�[�f�B���O</td>
     * <td>false</td>
     * <td>���͕������o�C�g��ɕϊ�����ۂɎg�p���镶���G���R�[�f�B���O�B �ȗ����ꂽ�ꍇ��VM�̃f�t�H���g�G���R�[�f�B���O���g�p�����B</td>
     * </tr>
     * </table>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateByteRange(Object bean, ValidatorAction va,
            Field field, ActionMessages errors, Validator validator,
            HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // ���͒l���擾
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        // �G���R�[�f�B���O
        String encoding = field.getVarValue("encoding");

        // ���͕����̃o�C�g��
        int bytesLength = getByteLength(value, encoding);

        int min = 0;
        String minStr = field.getVarValue("minByte");
        if (!GenericValidator.isBlankOrNull(minStr)) {
            try {
                min = Integer.parseInt(minStr);
            } catch (NumberFormatException e) {
                log.error("", e);
            }
        }
        
        int max = Integer.MAX_VALUE;
        String maxStr = field.getVarValue("maxByte");
        if (!GenericValidator.isBlankOrNull(maxStr)) {
            try {
                max = Integer.parseInt(maxStr);
            } catch (NumberFormatException e) {
                log.error("", e);
            }
        }

        if (!GenericValidator.isInRange(bytesLength, min, max)) {
            errors.add(field.getKey(), Resources.getActionMessage(validator,
                    request, va, field));
            return false;
        }
        return true;
    }

    /**
     * ���t���w�肵���͈͓��ł��邩�ǂ����`�F�b�N����B
     * 
     * <p>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B �G���[���b�Z�[�W�̃t�H�[�}�b�g�́A
     * <code>validation-rules.xml</code>�ɋL�q����B<br>
     * �ȉ��́A���t��2000/01/01����2010/12/31�͈͓̔��ł��邩�ǂ����� ���؂̐ݒ��ł���B
     * </p>
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;/sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;date&quot;
     *      depends=&quot;dateRange&quot;&gt;
     *    &lt;arg key=&quot;label.date&quot; position=&quot;0&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;startDate&lt;/var-name&gt;
     *      &lt;var-value&gt;2000/01/01&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;endDate&lt;/var-name&gt;
     *      &lt;var-value&gt;2010/12/31&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;datePattern&lt;/var-name&gt;
     *      &lt;var-value&gt;yyyy/MM/dd&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     * <tr>
     * <td><center><b><code>var-name</code></b></center></td>
     * <td><center><b><code>var-value</code></b></center></td>
     * <td><center><b>�K�{��</b></center></td>
     * <td><center><b>�T�v</b></center></td>
     * </tr>
     * <tr>
     * <td> startDate </td>
     * <td>�J�n���t</td>
     * <td>false</td>
     * <td>���t�͈͂̊J�n��臒l�ƂȂ���t�B
     * datePattern�܂���datePatternStrict�Ŏw�肵�����t�t�H�[�}�b�g�ƈ�v���邱�ƁB</td>
     * </tr>
     * <tr>
     * <td> endDate </td>
     * <td>�I�����t</td>
     * <td>false</td>
     * <td>���t�͈͂̏I����臒l�ƂȂ���t�B
     * datePattern�܂���datePatternStrict�Ŏw�肵�����t�t�H�[�}�b�g�ƈ�v���邱�ƁB</td>
     * </tr>
     * <tr>
     * <td> datePattern </td>
     * <td>���t�p�^�[��</td>
     * <td>false</td>
     * <td>���t�̃p�^�[��������������BDate�^�̃t�H�[�}�b�g���[���ɏ]���B</td>
     * </tr>
     * <tr>
     * <td> datePatternStrict </td>
     * <td>���t�̃p�^�[��������������BDate�^�̃t�H�[�}�b�g���[���ɏ]���B</td>
     * <td>false</td>
     * <td>���t�p�^�[���̃`�F�b�N�������ɍs�����ǂ����B ���t�p�^�[����yyyy/MM/dd�̏ꍇ�A2001/1/1�̓G���[�ƂȂ�B
     * datePattern���w�肳��Ă���ꍇ�AdatePattern�Ŏw�肳�ꂽ�t�H�[�}�b�g ���D�悳���B</td>
     * </tr>
     * </table>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateDateRange(Object bean, ValidatorAction va,
            Field field, ActionMessages errors, Validator validator,
            HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        // ���͓��t�̑Ó����`�F�b�N
        String datePattern = field.getVarValue("datePattern");
        String datePatternStrict = field.getVarValue("datePatternStrict");
        Locale locale = RequestUtils.getUserLocale(request, null);
        Date result = null;
        try {
            if (datePattern != null && datePattern.length() > 0) {
                result = GenericTypeValidator.formatDate(value, datePattern,
                        false);
            } else if (datePatternStrict != null
                    && datePatternStrict.length() > 0) {
                result = GenericTypeValidator.formatDate(value,
                        datePatternStrict, true);
            } else {
                result = GenericTypeValidator.formatDate(value, locale);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (result == null) {
            errors.add(field.getKey(), Resources.getActionMessage(validator,
                    request, va, field));
            return false;
        }

        // ���t�͈̓`�F�b�N
        // �J�n��
        String startDateStr = field.getVarValue("startDate");
        // �I����
        String endDateStr = field.getVarValue("endDate");

        if (startDateStr == null && endDateStr == null) {
            // ���t�͈͂��w�肳��Ă��Ȃ��ꍇ�͐���Ƃ݂Ȃ�
            return true;
        }

        // �J�n���t�ȍ~���ǂ����`�F�b�N
        if (startDateStr != null && startDateStr.length() > 0) {
            Date startDate = null;

            if (datePattern != null && datePattern.length() > 0) {
                startDate = GenericTypeValidator.formatDate(startDateStr,
                        datePattern, false);
            } else if (datePatternStrict != null
                    && datePatternStrict.length() > 0) {
                startDate = GenericTypeValidator.formatDate(startDateStr,
                        datePatternStrict, true);
            } else {
                startDate = GenericTypeValidator.formatDate(startDateStr,
                        locale);
            }

            if (startDate == null) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
            if (result.before(startDate)) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }

        // �I�����t�ȑO���ǂ����`�F�b�N
        if (endDateStr != null && endDateStr.length() > 0) {
            Date endDate = null;

            if (datePattern != null && datePattern.length() > 0) {
                endDate = GenericTypeValidator.formatDate(endDateStr,
                        datePattern, false);
            } else if (datePatternStrict != null
                    && datePatternStrict.length() > 0) {
                endDate = GenericTypeValidator.formatDate(endDateStr,
                        datePatternStrict, true);
            } else {
                endDate = GenericTypeValidator.formatDate(endDateStr, locale);
            }

            if (endDate == null) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
            if (result.after(endDate)) {
                errors.add(field.getKey(), Resources.getActionMessage(
                        validator, request, va, field));
                return false;
            }
        }

        return true;
    }

    /**
     * �����t�B�[���h�̑��փ`�F�b�N���s���B
     * 
     * ���̌��؃��[���̎��s�ɂ�{@link MultiFieldValidator} �̎����N���X�� �K�v�B���������N���X��
     * <code>validation.xml</code> �ɐݒ���s���B<br>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B ���̌��؃��[���ɂ̓f�t�H���g�̃G���[���b�Z�[�W���Ȃ����߁A
     * ���b�Z�[�W�� <code>validation.xml</code> �ɕK���L�q���邱�ƁB<br>
     * �A�N�V�����t�H�[����value�t�B�[���h�̒l���Avalue1�t�B�[���h�̒l�ȏ�A
     * value2�t�B�[���h�̒l�ȉ��ł��邱�Ƃ����؂���ꍇ�A�ȉ��̂悤�Ɏ����A �ݒ���s���B
     * <h5>{@link MultiFieldValidator} �̎�����</h5>
     * <code><pre>
     * public boolean validate(String value, String[] depends) {
     *  int value0 = Integer.parseInt(value);
     *  int value1 = Integer.parseInt(depends[0]);
     *  int value2 = Integer.parseInt(depends[1]);
     *  return (value1 &lt;= value0 &amp;&amp; value2 &gt;= value0);
     * }
     * </pre></code>
     * <h5>validation.xml�̐ݒ��</h5>
     * <code><pre>
     * &lt;form name=&quot;/validateMultiField&quot;&gt;
     *   &lt;field property=&quot;value&quot; depends=&quot;multiField&quot;&gt;
     *     &lt;msg key=&quot;errors.multiField&quot;
     *             name=&quot;multiField&quot;/&gt;
     *     &lt;arg key=&quot;label.value&quot; position=&quot;0&quot; /&gt;
     *     &lt;arg key=&quot;label.value1&quot; position=&quot;1&quot; /&gt;
     *     &lt;arg key=&quot;label.value2&quot; position=&quot;2&quot; /&gt;
     *     &lt;var&gt;
     *       &lt;var-name&gt;fields&lt;/var-name&gt;
     *       &lt;var-value&gt;value1,value2&lt;/var-value&gt;
     *     &lt;/var&gt;
     *     &lt;var&gt;
     *       &lt;var-name&gt;multiFieldValidator&lt;/var-name&gt;
     *       &lt;var-value&gt;sample.SampleMultiFieldValidator&lt;/var-value&gt;
     *     &lt;/var&gt;
     *   &lt;/field&gt;
     * &lt;/form&gt;
     * </pre></code>
     * <h5>���b�Z�[�W���\�[�X�t�@�C���̐ݒ��</h5>
     * <code>
     * errors.multiField={0}��{1}����{2}�̊Ԃ̒l����͂��Ă��������B
     * </code>
     * 
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     * <tr>
     * <td><center><b><code>var-name</code></b></center></td>
     * <td><center><b><code>var-value</code></b></center></td>
     * <td><center><b>�K�{��</b></center></td>
     * <td><center><b>�T�v</b></center></td>
     * </tr>
     * <tr>
     * <td> fields </td>
     * <td>���؂ɕK�v�ȑ��̃t�B�[���h��</td>
     * <td>false</td>
     * <td>�����̃t�B�[���h���w�肷��ꍇ�̓t�B�[���h�����J���}��؂�� �w�肷��B</td>
     * </tr>
     * <tr>
     * <td> multiFieldValidator </td>
     * <td>{@link MultiFieldValidator} �����N���X��</td>
     * <td>false</td>
     * <td>�����t�B�[���h�̑��փ`�F�b�N���s�� {@link MultiFieldValidator} �����N���X���B</td>
     * </tr>
     * </table>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return ���͒l����������� <code>true</code>
     */
    public static boolean validateMultiField(Object bean, ValidatorAction va,
            Field field, ActionMessages errors, Validator validator,
            HttpServletRequest request) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // ���ؑΏۂ̒l���擾
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        // ���̃t�B�[���h�Ɉˑ������K�{���̓`�F�b�N���l�����A
        // ���ؒl��null�܂��͋󕶎��̏ꍇ�ł������t�B�[���h�`�F�b�N�͎��s����B

        // MultiFieldValidator�����N���X�����擾
        String multiFieldValidatorClass = field
                .getVarValue("multiFieldValidator");

        if (multiFieldValidatorClass == null
                || "".equals(multiFieldValidatorClass)) {
            log.error("var value[multiFieldValidator] is required.");
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is required.");
        }

        MultiFieldValidator mfv = null;
        try {
            mfv = (MultiFieldValidator) ClassUtil
                    .create(multiFieldValidatorClass);
        } catch (ClassLoadException e) {
            log.error("var value[multiFieldValidator] is invalid.", e);
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is invalid.", e);
        } catch (ClassCastException e) {
            log.error("var value[multiFieldValidator] is invalid.", e);
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is invalid.", e);
        }

        // ���؂̈ˑ��t�B�[���h�̒l���擾
        String fields = field.getVarValue("fields");
        List<String> valueList = new ArrayList<String>();
        if (fields != null) {
            StringTokenizer st = new StringTokenizer(fields, ",");
            while (st.hasMoreTokens()) {
                String f = st.nextToken();
                f = f.trim();
                valueList.add(ValidatorUtils.getValueAsString(bean, f));
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("dependent fields:" + valueList);
        }

        String[] values = new String[valueList.size()];

        valueList.toArray(values);

        boolean result = mfv.validate(value, values);

        if (!result) {
            errors.add(field.getKey(), Resources.getActionMessage(validator,
                    request, va, field));
            return false;
        }

        return true;
    }

    /**
     * �z��E�R���N�V�����^�̃t�B�[���h�l��S�v�f�`�F�b�N����B
     * <p>
     * �z��E�R���N�V�����^�̃t�H�[���̗v�f�����ׂČ��؂��A�������� �C���f�b�N�X���� <code>ActionMessage</code>�ɒǉ�����B
     * ���s�����T�u���\�b�h�̈����̌^�A�����́A���̃��\�b�h�� ��v���Ă���K�v������B
     * Field��property�����ɂ�JXPathIndexedBeanWrapperImpl�̎d�l�ɏ]���A
     * �l�X�g�����v���p�e�B����ݒ肷�邱�Ƃ��\�ł���B
     * </p>
     * 
     * @param bean
     *            �����Ώۂ̃I�u�W�F�N�g
     * @param va
     *            Struts�ɂ��p�ӂ��ꂽ<code>ValidatorAction</code>
     * @param field
     *            �t�B�[���h�I�u�W�F�N�g
     * @param errors
     *            �A�N�V�����G���[
     * @param validator
     *            Validator�C���X�^���X
     * @param request
     *            HTTP���N�G�X�g
     * @return �v�f���ׂĂ̓��͒l����������� <code>true</code>
     */
    public static boolean validateArraysIndex(@SuppressWarnings("unused")
    Object bean, ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {

        // ���͈����̐�
        int methodArgCount = 6;

        // �z��E�R���N�V�����̑������ʁi��ł�false���ԋp���ꂽ�ꍇ�Afalse�j
        boolean totalResult = true;
        try {
            // �R���X�g���N�^�̈����ƂȂ�N���X�p�^�[�����擾����
            Class[] paramClass = getParamClass(va);
            if (paramClass == null) {
                log.error("Can not get class pattern.");
                return true;
            }
            // paramClass���v�f��=0�̂Ƃ��A�G���[���O���o�͂��Atrue�ԋp
            if (paramClass.length == 0) {
                log.error("Class pattern length is zero.");
                return true;
            }

            // �e�v�f�̌��؂��s�����\�b�h���擾����
            Method method = getMethod(va, paramClass);
            if (method == null) {
                log.error("Can not get validateMethod.");
                return true;
            }

            // �t�H�[���I�u�W�F�N�g���擾����
            ActionForm form = getActionForm(request);
            if (form == null) {
                log.error("Can not get ActionForm.");
                return true;
            }

            // �v�f���Ƃ̃o���f�[�V�������[���ɋ��ʂł���A
            // ���O�ݒ�s�v�̃I�u�W�F�N�g
            Object[] argParams = new Object[methodArgCount];
            argParams[0] = form;
            argParams[1] = va;
            argParams[3] = errors;
            argParams[4] = validator;
            argParams[5] = request;

            IndexedBeanWrapper wrapper = new JXPathIndexedBeanWrapperImpl(form);
            Map<String, Object> propertyMap = wrapper
                    .getIndexedPropertyValues(field.getKey());

            int index = 0;

            for (String key : propertyMap.keySet()) {
                // �C���f�b�N�X�t���̃v���p�e�B���Ńt�B�[���h������������
                Field indexedField = (Field) field.clone();
                indexedField.setIndexedListProperty(null);
                indexedField = getArrayIndexField(indexedField, index);
                indexedField.setKey(key);
                indexedField.setProperty(key);

                argParams[2] = indexedField; // �t�B�[���h

                // ���̓`�F�b�N���\�b�h�̌Ăяo��
                Object resultObj = method
                        .invoke(FieldChecksEx.class, argParams);
                // ���ʉ��
                if (!isValid(resultObj)) {
                    totalResult = false;
                }
                index++;
            }

        } catch (IllegalArgumentException e) {
            log.error("", e);
            return true;
        } catch (IllegalAccessException e) {
            log.error("", e);
            return true;
        } catch (InvocationTargetException e) {
            log.error("", e);
            return true;
        }
        return totalResult;
    }

    /**
     * �t�H�[�������I�u�W�F�N�g���擾����B
     * 
     * <b>���̃��\�b�h�̓A�N�V�����t�H�[����ΏۂƂ��Ă��邽�� �t�H�[���ȊO�ŁA���p���s�Ȃ����Ƃ𐄏����Ȃ��B</b>
     * 
     * @param request
     *            HTTP���N�G�X�g
     * @return �t�H�[�������I�u�W�F�N�g
     */
    protected static ActionForm getActionForm(HttpServletRequest request) {

        // ���N�G�X�g�����Őݒ肳��Ă���A�N�V�����t�H�[�������擾����
        String formName = ActionFormUtil.getActionFormName(request);
        // �t�H�[�������擾�ł��Ȃ��ꍇnull��ԋp
        if (formName == null) {
            return null;
        }

        // �Z�b�V��������t�H�[�����擾
        Object obj = request.getSession(true).getAttribute(formName);
        ActionForm form = null;
        if (obj instanceof ActionForm) {
            form = (ActionForm) obj;
        }

        // �Z�b�V�����ɑ��݂��Ȃ����A���N�G�X�g����擾
        if (form == null) {
            obj = request.getAttribute(formName);
            if (obj instanceof ActionForm) {
                form = (ActionForm) obj;
            }
        }
        return form;
    }

    /**
     * ���؃��[�����\�b�h�ɓn���������N���X�z����擾����B
     * 
     * <b>���̃��\�b�h�̓A�N�V�����t�H�[����ΏۂƂ��Ă��邽�� �t�H�[���ȊO�ŁA���p���s�Ȃ����Ƃ𐄏����Ȃ��B</b>
     * 
     * @param va
     *            Struts�ɂ��p�ӂ��ꂽ<code>ValidatorAction</code>
     * @return �����N���X�z��
     */
    protected static Class[] getParamClass(ValidatorAction va) {

        String methodParams = va.getMethodParams();
        if (methodParams == null) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(methodParams, ",");
        Class[] paramClass = new Class[st.countTokens()];

        for (int i = 0; st.hasMoreTokens(); i++) {
            try {
                String key = st.nextToken().trim();
                paramClass[i] = ClassUtils.getClass(key);
            } catch (ClassNotFoundException e) {
                log.error("", e);
                return null;
            }
        }
        return paramClass;
    }

    /**
     * �z��E�R���N�V�����̗v�f�����؂��郁�\�b�h���擾����B
     * 
     * <b>���̃��\�b�h�̓A�N�V�����t�H�[����ΏۂƂ��Ă��邽�� �t�H�[���ȊO�ŁA���p���s�Ȃ����Ƃ𐄏����Ȃ��B</b>
     * 
     * @param va
     *            Struts�ɂ��p�ӂ��ꂽ<code>ValidatorAction</code>
     * @param paramClass
     *            �����N���X�z��
     * @return ���؃��\�b�h�I�u�W�F�N�g
     */
    protected static Method getMethod(ValidatorAction va, Class[] paramClass) {

        String methodNameSource = va.getName();
        if (methodNameSource == null || "".equals(methodNameSource)) {
            // ���\�b�h���w�肪null�܂��͋󕶎��̂Ƃ�null�ԋp�B
            return null;
        }

        // name��������"Array"�������������\�b�h���𐶐�����
        // xxxxArray��validateXxxx
        char[] chars = methodNameSource.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        String validate = "validate" + new String(chars);
        String methodName = validate.substring(0, validate.length()
                - "Array".length());

        Method method = null;
        try {
            method = FieldChecksEx.class.getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            try {
                method = ValidWhen.class.getMethod(methodName, paramClass);
            } catch (NoSuchMethodException e1) {
                log.error("", e);
                log.error("", e1);
                return null;
            }
        }
        return method;
    }

    /**
     * ���t���N�V������p���Ď��s���ꂽ���\�b�h�̌��� �I�u�W�F�N�g����A���؂������������ۂ����`�F�b�N����B
     * 
     * <b>���̃��\�b�h�̓A�N�V�����t�H�[����ΏۂƂ��Ă��邽�� �t�H�[���ȊO�ŁA���p���s�Ȃ����Ƃ𐄏����Ȃ��B</b>
     * 
     * @param result
     *            ���ʃI�u�W�F�N�g
     * @return ���ؐ������� <code>true</code>
     */
    protected static boolean isValid(Object result) {
        boolean isValid = false;

        if (result instanceof Boolean) {
            Boolean valid = (Boolean) result;
            isValid = valid.booleanValue();
        } else {
            // Boolean�^�ł͂Ȃ��Ƃ��Atrue��ԋp����B
            return true;
        }

        return isValid;
    }

    /**
     * �w�肳�ꂽIndex�̃t�B�[���h���擾����B
     * 
     * <b>���̃��\�b�h�̓A�N�V�����t�H�[����ΏۂƂ��Ă��邽�� �t�H�[���ȊO�ŁA���p���s�Ȃ����Ƃ𐄏����Ȃ��B</b>
     * 
     * @param field
     *            �u�����t�B�[���h�I�u�W�F�N�g
     * @param pos
     *            �������̃C���f�b�N�X
     * @return �u����̃t�B�[���h�I�u�W�F�N�g
     */
    protected static Field getArrayIndexField(Field field, int pos) {
        Field cloneField = (Field) field.clone();

        // ##index��������A���݂̗v�f���ɏ���������
        Arg argParam = null;
        String argStr = null;

        argParam = cloneField.getArg(0);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(0).setKey(replaceIndexString(argStr, pos + 1));
        }

        argParam = cloneField.getArg(1);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(1).setKey(replaceIndexString(argStr, pos + 1));
        }

        argParam = cloneField.getArg(2);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(2).setKey(replaceIndexString(argStr, pos + 1));
        }

        argParam = cloneField.getArg(3);
        if (argParam != null) {
            argStr = argParam.getKey();
            cloneField.getArg(3).setKey(replaceIndexString(argStr, pos + 1));
        }

        return cloneField;
    }

    /**
     * <code>arg</code>�̒l��##INDEX�̎��A�������̃C���f�b�N�X�l�� �u������
     * 
     * <b>���̃��\�b�h�̓A�N�V�����t�H�[����ΏۂƂ��Ă��邽�� �t�H�[���ȊO�ŁA���p���s�Ȃ����Ƃ𐄏����Ȃ��B</b>
     * 
     * @param arg
     *            <code>ActionMessage</code>�̒u�����L�[���
     * @param pos
     *            �������̃C���f�b�N�X
     * @return �L�[��##INDEX�̎��A���݂̃C���f�b�N�X��ԋp����
     */
    protected static String replaceIndexString(String arg, int pos) {
        if (arg == null) {
            return null;
        }
        if (INDEX.equalsIgnoreCase(arg)) {
            return Integer.toString(pos);
        }
        return arg;
    }

    /**
     * �w�肳�ꂽ������̃o�C�g�񒷂��擾����B<br>
     * �������̃G���R�[�f�B���O�Ńo�C�g��ɕϊ����邪�A�w�肳��Ă��Ȃ������� �A�G���R�[�h�Ɏ��s����ꍇ�̓f�t�H���g�̃G���R�[�f�B���O��
     * �o�C�g��ɕϊ����s���B
     * 
     * @param value
     *            �o�C�g�񒷂��擾����Ώۂ̕�����
     * @param encoding
     *            �����G���R�[�f�B���O
     * @return �o�C�g��
     */
    protected static int getByteLength(String value, String encoding) {
        if (value == null || "".equals(value)) {
            return 0;
        }
        byte[] bytes = null;
        if (encoding == null || "".equals(encoding)) {
            bytes = value.getBytes();
        } else {
            try {
                bytes = value.getBytes(encoding);
            } catch (UnsupportedEncodingException e) {
                if (log.isWarnEnabled()) {
                    log.warn(encoding + " is not supported.");
                }
                bytes = value.getBytes();
            }
        }
        return bytes == null ? 0 : bytes.length;
    }

    /**
     * ���p�J�i�̃��X�g���擾����B
     * 
     * @return ���p�J�i���X�g
     */
    public static String getHankakuKanaList() {
        return hankakuKanaList;
    }

    /**
     * �S�p�J�i�̃��X�g���擾����B
     * 
     * @return �S�p�J�i���X�g
     */
    public static String getZenkakuKanaList() {
        return zenkakuKanaList;
    }

}
