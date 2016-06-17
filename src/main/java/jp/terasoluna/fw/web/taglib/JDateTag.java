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

import java.util.Date;

import jp.terasoluna.fw.util.DateUtil;

/**
 * <p>
 *  ���t�����f�[�^��a��Ƃ��ăt�H�[�}�b�g����jdate�^�O�̎����N���X�B
 * </p>
 *
 * <p>
 * ���t�����̃f�[�^�̃t�H�[�}�b�g���s���ۂɁA�a��̌���
 * <code>�i&quot;���a&quot;�A&quot;S&quot;�Ȃǁj</code>��A
 * �a��̔N�i����� <code>&quot;2002&quot;</code> �N�ł͂Ȃ��A
 * ���� <code>&quot;14&quot;�N</code> �Ȃǁj�A����їj���̓��{��\�L
 * �i<code>&quot;���j��&quot;�A&quot;��&quot;</code> �Ȃǁj�ɕϊ�����B
 * </p>
 *
 * <p>
 *  �t�H�[�}�b�g�́A<code>pattern</code> �����ɂ���Ďw�肳�ꂽ�`���ɏ]����
 *  �s����B{@link JDateTag} �N���X�ł́A��{�I�ɂ� {@link DateTag}
 *  �N���X�Ɠ��l�� <code>pattern</code> �����Ŏw�肳�ꂽ�o�͌`���̕������
 *  <code>java.text.SimpleDateFormat</code> �N���X��<em>�����p�^�[��������</em>
 *  �Ƃ��ĉ��߂��A�t�H�[�}�b�g����B�������A�����p�^�[��������̉��߂ɂ�����
 *  �ȉ��̓_�� {@link DateTag} �N���X�ƈقȂ�B
 * </p>
 *
 * <div width="90%" align="center">
 *  <table border="1">
 *   <tr>
 *    <th>�L��</th>
 *    <th><code>&nbsp;SimpleDateFormat</code> �ł̈Ӗ�&nbsp;</th>
 *    <th><code>&nbsp;JdateTag</code> �ł̈Ӗ�&nbsp;</th>
 *   </tr>
 *   <tr>
 *    <td>G</td>
 *    <td align="left">�I��<br>��FAD</td>
 *    <td align="left">�a���<br>
 *                     ��F<code>HHHH</code> �� ����<br>
 *                         <code>HHH</code> �� H</td>
 *   </tr>
 *   <tr>
 *    <td>y</td>
 *    <td align="left">�N�i����j<br>��F2002</td>
 *    <td align="left">�N�i�a��j<br>��F14</td>
 *   </tr>
 *   <tr>
 *    <td>E</td>
 *    <td align="left">�j��<br>��FTuesday</td>
 *    <td align="left">�j���i���{��\�L�j<br><br>
 *                     ��F<code>EEEE</code> �� ���j��<br>
 *                         <code>EEE</code> �� ��</td>
 *   </tr>
 * </table>
 * </div>
 *
 * <p>
 *  ��L�̋L���̂����A�a����A����јa��N�ɂ��Ă�
 *  {@link jp.terasoluna.fw.util.DateUtil} �N���X��
 *  <code>dateToWarekiString(String pattern, java.util.Date date)</code>
 *  ���\�b�h�ɂ���ĕϊ������B�a����A����јa��N�̐ݒ���@��
 *  �ϊ��\�ȔN�ɂ��Ă̐��������Ȃǂ́A
 *  {@link jp.terasoluna.fw.util.DateUtil} ���Q�ƁB
 * </p>
 *
 * <p>
 *  �܂��A�j���ɂ��Ă� <code>SimpleDateFotmat</code>
 *  �̃R���X�g���N�^�ɂ����ă��P�[���� <code>&quot;ja&quot;</code>
 *  �Ɏw�肷�邱�Ƃŕϊ������B���P�[���⎞���p�^�[��������̏ڍׂɂ��ẮA
 *  <code>java.text.SimpleDateFormat</code>
 *  �N���X�̃h�L�������g���Q�Ƃ̂��ƁB
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p> {@link DateFormatterTagBase} ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p> {@link DateFormatterTagBase} ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <p>
 *  <code>&quot;form0001&quot;</code> <code>bean</code>��
 *  <code>&quot;field001&quot;</code> �v���p�e�B�̒l���w�肵���`���Ƀt�H�[�}�b�g
 *  ���ďo�͂���ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:jdate name="form0001"
 *              property="field001"
 *              pattern="GGGGyy�NMM��dd��(EEEE) hh��mm��ss�b" /&gt;
 *
 * =&gt ����14�N07��26��(���j��) 11��04��07�b
 *
 *  &lt;t:jdate name="form0001"
 *              property="field001"
 *              pattern="G. yy�NMM��dd��(E) hh��mm��ss�b" /&gt;
 *
 * =&gt H. 14�N07��26��(��) 11��04��07�b
 * </pre></code></p>
 *
 * <p>
 *  ��L�� bean�̃v���p�e�B���A�J�X�^���^�O�ŏo�͂����ɃX�N���v�e�B���O�ϐ�
 *  <code>&quot;formatted&quot;</code>
 *  �փZ�b�g����ꍇ�ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:jdate id="formatted"
 *              name="form0001"
 *              property="field001"
 *              pattern="GGGGyy�NMM��dd��(EEEE) hh��mm��ss�b" /&gt;
 * </pre></code></p>
 *
 * <p>
 *  �t�H�[�}�b�g������t�����f�[�^��bean������o�����ɁA
 *  �J�X�^���^�O�� <code>value</code>
 *  �����ɂ���Ďw�肷��ꍇ�ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:jdate value="2002/07/25 04:56:23"
 *              pattern="GGGGyy�NMM��dd��(EEEE) hh��mm��ss�b" /&gt;
 * </pre></code></p>
 *
 * @see java.text.SimpleDateFormat
 * @see jp.terasoluna.fw.util.DateUtil
 * @see jp.terasoluna.fw.web.taglib.DateFormatterTagBase
 *
 */
public class JDateTag extends DateFormatterTagBase {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 4097026346025126637L;

    /**
     * <code>pattern</code> �����Ŏw�肳�ꂽ�t�H�[�}�b�g�ɂ��������āA
     * �w�肳�ꂽ���t�����f�[�^��a��Ƃ��ăt�H�[�}�b�g����B
     *
     * @param date ���t�����f�[�^
     * @return ���t�����f�[�^��a��Ƃ��ăt�H�[�}�b�g����������
     */
    @Override
    protected String doFormat(Date date) {
        return DateUtil.dateToWarekiString(pattern, date);
    }

}
