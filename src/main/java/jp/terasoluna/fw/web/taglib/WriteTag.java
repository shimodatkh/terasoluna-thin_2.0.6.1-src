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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 *  �w�肵�� bean �v���p�e�B�̒l��ϊ���<code>JspWriter</code>�Ƃ���
 *  �\������<code>write</code>�^�O�̎����N���X�B
 * </p>
 * <p>
 *  �w�肵��<code>bean</code>�v���p�e�B�̒l�����o���A
 *  <code>String</code>�Ƃ��Č��݂� <code>JspWriter</code> �ɗ^����B
 *  �v���p�e�B�l�̃N���X�p�ɍ\������Ă���<code>PropertyEditor</code>
 *  ������ꍇ�A <code>getAsText()</code> ���\�b�h���Ă΂��B
 *  ����ȊO�̏ꍇ�́A�ʏ� <code>toString()</code> �ł̕ϊ����K�p�����B
 *  �܂��A�����ɂ��A�ȉ��̂悤�ɕt���ϊ����s���B
 * </p>
 * <p>
 *  <li>null�������͋󕶎��� <code>&quot;&amp;nbsp;&quot;</code> �ƒu��</li>
 *  <li>���p�X�y�[�X�� <code>&quot;&amp;nbsp;&quot;</code> �ƒu��</li>
 *  <li>���s�R�[�h�� <code>&lt;br&gt;</code> �ƒu��</li>
 *  <li>���s�����𖳎�</li>
 * </p>
 * <p>
 * �Ȃ��A���̃^�O���g�p����ꍇ�A���s�R�[�h���c�����Ƃ��ł��Ȃ��B<br>
 * ���s�R�[�h���c���K�v������ꍇ��struts���񋟂��Ă���&lt;bean:write&gt;�^�O���g�p���邱�ƁB
 * </p>
 * <br>
 * <h5>�^�O���T�|�[�g���鑮��</h5> 
 * <li><code>write</code> �^�O�ł́A�ȉ��̑������T�|�[�g����B</li> <br>
 * <br>
 * <div align="center">
 * <table width="90%" border="1">
 *   <tr>
 *    <td> <b>������</b> </td>
 *    <td> <b>�f�t�H���g�l</b> </td>
 *    <td> <b>�K�{��</b> </td>
 *    <td> <b>���s����</b> </td>
 *    <td> <b>�T�v</b> </td>
 *   </tr>
 *   <tr>
 *    <td> <code>filter</code> </td>
 *    <td> true </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      ���̑�����true�ɃZ�b�g�����ꍇ�A
 *      �\�����ꂽ�v���p�e�B�l�� HTML���ŃZ���V�e�B�u�ȕ����̂��߂�
 *      �t�B���^�[�����B �����Ă��̂悤�ȑS�Ă̕����́A
 *      �����ȕ����Œu����������B
 *      �f�t�H���g�ł́A�t�B���^�����O���s����B
 *      �����ɂ��邽�߂ɂ́A���̑����ɖ����I�� false ���Z�b�g����K�v������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceNullToNbsp</code> </td>
 *    <td> true </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      ���̑�����true�ɃZ�b�g����A
 *      �w�肵��bean�v���p�e�B�̒l���󕶎��y�сAnull�̏ꍇ
 *      <code>&amp;nbsp;</code>���o�͂���B
 *      �����ɂ��邽�߂ɂ́A���̑����ɖ����I�� false ���Z�b�g����K�v������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceSpToNbsp</code> </td>
 *    <td> true </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      ���̑�����true�ɃZ�b�g����A
 *      �w�肵��bean�v���p�e�B�̒l��1Byte�R�[�h�̃X�y�[�X�����݂���ꍇ
 *      <code>&amp;nbsp;</code>�ɒu������B
 *      �����ɂ��邽�߂ɂ́A���̑����ɖ����I�� false ���Z�b�g����K�v������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>replaceLFtoBR</code> </td>
 *    <td> true </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      ���̑�����true�ɃZ�b�g�����ꍇ�A
 *      �w�肵��bean�v���p�e�B�̒l�̉��s�R�[�h�������͕��A������
 *      <code>&lt;br&gt;</code>�ɒu�������B
 *      �����ɂ��邽�߂ɂ́A���̑����ɖ����I�� false ���Z�b�g����K�v������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>ignore</code> </td>
 *    <td> false </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      ���̑�����true�ɃZ�b�g����A
 *      name �� scope�����Ŏw�肵�� bean �����݂��Ȃ��ꍇ�A
 *      �Ȃɂ������Ƀ��^�[������B
 *      �f�t�H���g�l�� false (���̃^�O ���C�u�����̒��̂ق��̃^�O��
 *      �������Ȃ��悤�Ɏ��s����O���X���[�����)�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>name</code> </td>
 *    <td> - </td>
 *    <td> <code>true</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      property (�w�肪����ꍇ) �ɂ���Ďw�肵���l��
 *      ���o�����߂ɁA�v���p�e�B���A�N�Z�X����� bean �̑��������w�肷��B
 *      property ���w�肳��Ȃ��ꍇ�A���� bean ���g�̒l���\�������B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>property</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      name �ɂ���Ďw�肵�� bean ��ŃA�N�Z�X�����
 *      �v���p�e�B�̖��O���w�肷��B ���̒l�̓V���v���A�C���f�b�N�X�t���A
 *      �܂��̓l�X�g���ꂽ�v���p�e�B�Q�Ǝ��ɂȂ�B
 *      �w�肳��Ȃ��ꍇ�́Aname �ɂ���Ď��ʂ��ꂽ
 *      bean �� ���ꎩ�g��\������B
 *      �w�肵���v���p�e�B���k����߂��ꍇ�A�����\������Ȃ��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>scope</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      name �ɂ���Ďw�肵�� bean �����o�����߂Ɍ������ꂽ
 *      �σX�R�[�v���w�肷��B �w�肳��Ȃ��ꍇ�APageContext.findAttribute()
 *      �ɂ���ēK�p���ꂽ�f�t�H���g�̃��[�����K�p�����B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>fillColumn</code> </td>
 *    <td> - </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      fillColumn�ɂ���Ďw�肳�ꂽ�������ŋ�؂�A
 *      ��؂����I�[��&lt;br&gt;��t�^����B �������̐������͔��p�ł��A�S�p�ł�
 *      �P�̕����Ƃ݂Ȃ��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td> <code>addBR</code> </td>
 *    <td> false </td>
 *    <td> <code>false</code> </td>
 *    <td> <code>true</code> </td>
 *    <td align="left">
 *      ���̑�����true�ɃZ�b�g�����ꍇ�A�v���p�e�B�l�̖�����&lt;br&gt;��t�^����B
 *      �f�t�H���g��false�B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <h5>���L����</h5>
 * �Q�Ƃ���l��null��󕶎���̏ꍇ�A�������̑����̎d�l����������B
 * ���̏ꍇ�A�ȉ��̗D�揇�ł����ꂩ�̎d�l���K�p�����B
 * <ul>
 * <li>ignore�����̎d�l�u���̑�����true�ɃZ�b�g����Aname �� scope�����Ŏw�肵�� bean �����݂��Ȃ��ꍇ�A�Ȃɂ������Ƀ��^�[������v</li>
 * <li>replaceNullToNbsp�����̎d�l�u���̑�����true�ɃZ�b�g����A �w�肵��bean�v���p�e�B�̒l���󕶎��y�сAnull�̏ꍇ &amp;nbsp;���o�͂���v</li>
 * <li>property�����̎d�l�u�w�肵���v���p�e�B���k����߂��ꍇ�A�����\������Ȃ��v</li>
 * <li>addBR�����̎d�l�u���̑�����true�ɃZ�b�g�����ꍇ�A�v���p�e�B�l�̖�����&lt;br&gt;��t�^����v</li>
 * </ul>
 * �Ⴆ�΁AreplaceNullToNbsp�����̎d�l�ɂ��A�u&amp;nbsp;���o�͂���v���Ƃ����肷��ƁA
 * property�����̎d�l�u�����\������Ȃ��v��A
 * addBR�����̎d�l�u������&lt;br&gt;��t�^����v�͓K�p����Ȃ��B<br>
 * ��������͒l�̑g�ݍ��킹�p�^�[���Ƃ��̎��̏o�͌��ʂ͈ȉ��̒ʂ�B<br>
 * <div align="center">
 * <table width="90%" border="1">
 *   <tr>
 *    <td> <b>replaceNullToNbsp</b> </td>
 *    <td> <b>addBR</b> </td>
 *    <td> <b>name+property�����Ŏw�肵���v���p�e�B�̒l</b> </td>
 *    <td> <b>�o�͌���</b> </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> true </td>
 *    <td> null </td>
 *    <td> &amp;nbsp; </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> true </td>
 *    <td> �󕶎��� </td>
 *    <td> &amp;nbsp; </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> false </td>
 *    <td> null </td>
 *    <td> &amp;nbsp; </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> false </td>
 *    <td> �󕶎��� </td>
 *    <td> &amp;nbsp; </td>
 *   </tr>
 *   
 *   <tr>
 *    <td> false </td>
 *    <td> true </td>
 *    <td> null </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 *   <tr>
 *    <td> false </td>
 *    <td> true </td>
 *    <td> �󕶎��� </td>
 *    <td> &lt;br&gt; </td>
 *   </tr>
 *   <tr>
 *    <td> false </td>
 *    <td> false </td>
 *    <td> null </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 *   <tr>
 *    <td> false </td>
 *    <td> false </td>
 *    <td> �󕶎��� </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 * </table>
 * </div>
 * ��L�́Aname������property�����ŏo�͂���^�[�Q�b�g���w�肵�Ă���Aname�����Ŏw�肵��bean�̒l��null�łȂ��ꍇ�B<br>
 * name������property�����ŏo�͂���^�[�Q�b�g���w�肵�Ă��Ă��Aname�����Ŏw�肵��bean�̒l��null�̏ꍇ�́A�ȉ��̕\�Ɋ܂܂��悤�ɁA�u�o�͖����v�ƂȂ�B<br>
 * name�����݂̂ŏo�͂���^�[�Q�b�g���w�肷��ꍇ�́A�ȉ��̂悤�ɂȂ�B<br>
 * name�����Ŏw�肵��bean�̒l��null�̏ꍇ�́Aignore�����̎d�l���D�悳��Aname������property�����Ŏw�肵���v���p�e�B�̒l��null�ł���ꍇ�Ƃ͌��ʂ��قȂ邱�Ƃɒ��ӁB<br>
 * �Ȃ��Aname�����Ŏw�肵��bean�̒l��null�̏ꍇ�ł��G���[�ɂȂ�Ȃ��悤�Aignore������true�ɂȂ��Ă��邱�Ƃ��O��ł���B
 * <div align="center">
 * <table width="90%" border="1">
 *   <tr>
 *    <td> <b>replaceNullToNbsp</b> </td>
 *    <td> <b>addBR</b> </td>
 *    <td> <b>name�����Ŏw�肵��bean�̒l</b> </td>
 *    <td> <b>�o�͌���</b> </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> true </td>
 *    <td> null </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> true </td>
 *    <td> �󕶎��� </td>
 *    <td> &amp;nbsp; </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> false </td>
 *    <td> null </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 *   <tr>
 *    <td> true </td>
 *    <td> false </td>
 *    <td> �󕶎��� </td>
 *    <td> &amp;nbsp; </td>
 *   </tr>
 *   
 *   <tr>
 *    <td> false </td>
 *    <td> true </td>
 *    <td> null </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 *   <tr>
 *    <td> false </td>
 *    <td> true </td>
 *    <td> �󕶎��� </td>
 *    <td> &lt;br&gt; </td>
 *   </tr>
 *   <tr>
 *    <td> false </td>
 *    <td> false </td>
 *    <td> null </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 *   <tr>
 *    <td> false </td>
 *    <td> false </td>
 *    <td> �󕶎��� </td>
 *    <td> �o�͖��� </td>
 *   </tr>
 * </table>
 * </div>
 * <br>
 * <li>���̃^�O�ɂ���Đݒ肳���X�N���v�e�B���O�ϐ��͂���܂���B</li>
 * <br>
 * <h5>�g�p���@</h5>
 * <p><code><pre>
 * &lt;logic:iterate id=&quot;form&quot;
 *     property="myMap" indexId=&quot;index&quot; &gt;
 *     &lt;t:write name=&quot;form&quot; property=&quot;value&quot; /&gt;
 * &lt;/logic:iterate&gt;
 * </pre></code></p>
 *
 */
public class WriteTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -6953813130272994790L;

    /**
     * ���O�N���X
     */
    private static Log log = LogFactory.getLog(WriteTag.class);

    /**
     * <p>
     *  ���ꕶ���� <code>HTML</code> �ɑΉ����������ɒu��������B
     * </p>
     */
    protected boolean filter = true;

    /**
     * <p>
     *  <code>filter</code>��<code>get</code>���\�b�h
     * </p>
     * @return filter filter
     */
    public boolean getFilter() {
        return this.filter;
    }

    /**
     * <p>
     *  <code>filter</code>��<code>set</code>���\�b�h
     * </p>
     *
     * @param filter filter�����l
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * <p>
     *  <code>null</code>�������͋󕶎���&nbsp;�ƒu������B
     * </p>
     */
    protected boolean replaceNullToNbsp = true;

    /**
     * <p>
     *  <code>replaceNullToNbsp</code>��<code>get</code>���\�b�h
     * </p>
     *
     * @return replaceNullToNbsp
     *   <code>null</code> ���� <code>&amp;nbsp;</code> �ϊ��t���O
     */
    public boolean getReplaceNullToNbsp() {
        return this.replaceNullToNbsp;
    }

    /**
     * <p>
     *  <code>replaceNullToNbsp</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param replaceNullToNbsp
     *   <code>null</code> ���� <code>&amp;nbsp;</code> �ϊ��t���O
     */
    public void setReplaceNullToNbsp(boolean replaceNullToNbsp) {
        this.replaceNullToNbsp = replaceNullToNbsp;
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
     * <p>
     *  ���s�R�[�h��<code>&lt;br&gt;</code>�ƒu������B
     * </p>
     */
    protected boolean replaceLFtoBR = true;

    /**
     * <p>
     *  <code>replaceLFtoBR</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return replaceLFtoBR
     *   ���s�R�[�h���� <code>&lt;br&gt;</code> �ϊ��t���O
     */
    public boolean getReplaceLFtoBR() {
        return this.replaceLFtoBR;
    }

    /**
     * <p>
     *  <code>replaceLFtoBR</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param replaceLFtoBR
     *   ���s�R�[�h���� <code>&lt;br&gt;</code> �ϊ��t���O
     */
    public void setReplaceLFtoBR(boolean replaceLFtoBR) {
        this.replaceLFtoBR = replaceLFtoBR;
    }

    /**
     * <p>
     *  <code>name</code> �� <code>scope</code> �����Ŏw�肳�ꂽ
     *  <code>Bean</code> �����݂��Ȃ��ꍇ�A�������Ȃ�
     * </p>
     */
    protected boolean ignore = false;

    /**
     * <p>
     *  <code>ignore</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return ignore ignore�����l
     */
    public boolean getIgnore() {
        return this.ignore;
    }

    /**
     * <p>
     *  <code>ignore</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param ignore ignore�����l
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * <p>
     *  <code>property</code> �Ŏw�肵���l�����o���ׂ� <code>Bean</code> ��
     * </p>
     */
    protected String name = null;

    /**
     * <p>
     *  <code>name</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     *  <code>name</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param name name�����l
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     *  <code>name</code> �ɂ���Ďw�肳�ꂽ <code>Bean</code> ���
     *  �A�N�Z�X�����v���p�e�B��
     * </p>
     */
    protected String property = null;

    /**
     * <p>
     *  <code>property</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return property
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * <p>
     *  <code>property</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param property property�����l
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * <p>
     *  <code>scope</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return scope
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * <p>
     *  <code>name</code> �ɂ���Ďw�肵�� <code>bean</code>
     * �����o���ׂɌ�������X�R�[�v��
     * </p>
     */
    protected String scope = null;

    /**
     * <p>
     *  <code>scope</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param scope scope�����l
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * <p>
     *  <code>fillColumn</code> �ɂ���Ďw�肳�ꂽ <code>Bean</code>
     *  ��ŃA�N�Z�X�����v���p�e�B��
     * </p>
     */
    protected int fillColumn = -1;

    /**
     * <p>
     *  <code>fillColumn</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return fillColumn
     */
    public int getFillColumn() {
        return this.fillColumn;
    }

    /**
     * <p>
     *  <code>fillColumn</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param fillColumn fillColumn�����l
     */
    public void setFillColumn(int fillColumn) {
        this.fillColumn = fillColumn;
    }

    /**
     * <p>
     *  �v���p�e�B�l�̖�����&lt;br&gt;��������B
     * </p>
     */
    protected boolean addBR = false;

    /**
     * <p>
     *  <code>addBR</code> �� <code>get</code> ���\�b�h
     * </p>
     *
     * @return addBR addBR�����l
     */
    public boolean getAddBR() {
        return this.addBR;
    }

    /**
     * <p>
     *  <code>addBR</code> �� <code>set</code> ���\�b�h
     * </p>
     *
     * @param addBR addBR�����l
     */
    public void setAddBR(boolean addBR) {
        this.addBR = addBR;
    }

    /**
     * <p>�J�n�^�O�̏������s���܂��B</p>
     *
     * @return int ��������w��
     * @exception JspException JSP��O�����������ꍇ
     */
    @Override
    public int doStartTag() throws JspException {

        //�v�����ꂽbean����������
        if (ignore && TagUtil.lookup(pageContext, name, scope) == null) {
            return SKIP_BODY;
        }

        //�v�����ꂽ�v���p�e�B����������
        Object value = TagUtil.lookup(pageContext, name, property, scope);
        if (value == null) {
            if (replaceNullToNbsp) {
                TagUtil.write(pageContext, "&nbsp;");
            }
            return SKIP_BODY;
        }

        String output = value.toString();
        if (output.length() == 0) {
            if (replaceNullToNbsp) {
                TagUtil.write(pageContext, "&nbsp;");
                return SKIP_BODY;
            }
            
            // �󕶎���̏ꍇ�AreplaceNullToNbsp��false�ł���΁A
            // addBR�ɏ]��<br>��t�^���邱�Ƃ�����̂ŁA
            // �܂�return���Ȃ��B
        }

        //�v���p�e�B�l��\������
        StringReader   sr = null;
        BufferedReader br = null;
        try {
            sr = new StringReader(output);
            br = new BufferedReader(sr);
            StringBuilder sbuilder = new StringBuilder();
            StringBuilder strBuilder = new StringBuilder();
            String tmpLine = null;
            int sizeMngCount = 1;
            int index = 0;

            // List�ɉ��s�R�[�h�ŋ�؂��Ċi�[����B
            List<String> lines = new ArrayList<String>();
            while ((tmpLine = br.readLine()) != null) {
                lines.add(tmpLine);
            }

            for (String line : lines) {
                if (index > 0 && replaceLFtoBR) {
                    // ���s�R�[�h��<br>��
                    sbuilder.append("<br>");
                    // �����J�E���g�����Z�b�g����
                    sizeMngCount = 1;
                }

                if (!"".equals(line)) {
                    strBuilder.setLength(0);
                    char ch = line.charAt(0);
                    for (int i = 0; i < line.length(); i++, sizeMngCount++) {
                        ch = line.charAt(i);
                        strBuilder.append(ch);
                        // �w�肳�ꂽ�T�C�Y�ŋ�؂�A\n��t�^����B
                        if (fillColumn > 0 && sizeMngCount > 1
                                && sizeMngCount % fillColumn == 0) {
                            // �������A�w�肳�ꂽ�T�C�Y�ł����Ă��A�ȉ��̏ꍇ�ɂ́A\n��t�^���Ȃ��B
                            // �s������replaceLFtoBR == true (���̓f�[�^���̉��s�R�[�h�ɂ��A���Ƃ���<br>�����镔���ł��邽��)
                            // �ŏI�s�̍s�� (���̕�����̏I�[�ł���A���s�R�[�h�ɂ��s���ł͂Ȃ�����)
                            if (i == line.length() - 1 && replaceLFtoBR) {
                                // �������Ȃ�
                            } else if (i == line.length() - 1 && index == lines.size() - 1) {
                                // �������Ȃ�
                            } else {
                                strBuilder.append("\n");
                            }
                        }
                    }
                    line = strBuilder.toString();
                }

                if (filter) {
                    // �T�j�^�C�Y
                    line = TagUtil.filter(line);
                }
                // ���p�X�y�[�X��&nbsp;�ɒu�����A\n��<br>�ɒu������
                char[] content = line.toCharArray();
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < content.length; i++) {
                    switch (content[i]) {
                    case ' ':
                        if (replaceSpToNbsp) {
                            result.append("&nbsp;");
                        } else {
                            result.append(content[i]);
                        }
                        break;
                    case '\n':
                        result.append("<br>");
                        break;
                    default:
                        result.append(content[i]);
                        break;
                    }
                }
                sbuilder.append(result);
                ++index;
            }

            // �v���p�e�B�l�̖�����<br>��t�^����
            if (addBR) {
                sbuilder.append("<br>");
            }

            output = sbuilder.toString();
        } catch (IOException e) {
            log.error("StringReader IO error.");

            throw new JspTagException(e.getMessage());
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

        return SKIP_BODY;
    }

    /**
     * <p>���ׂẴA���P�[�g���ꂽ�������������B</p>
     */
    @Override
    public void release() {
        super.release();
        this.filter = true;
        this.replaceNullToNbsp = true;
        this.replaceSpToNbsp = true;
        this.replaceLFtoBR = true;
        this.ignore = false;
        this.name = null;
        this.property = null;
        this.scope = null;
        this.fillColumn = -1;
    }

}
