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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  <code>date</code>�^�O�̎����N���X�B
 * </p>
 * <p>
 *  <code>pattern</code> �����ɂ���Ďw�肳�ꂽ�`���ɏ]����
 *  ���t�E�������t�H�[�}�b�g����B
 *  <code>DateTag</code> �N���X�ł́A<code>pattern</code> �����Ŏw�肳�ꂽ
 *  �o�͌`���̕������ <code>java.text.SimpleDateFormat</code> �N���X��
 *  <em>�����p�^�[��������</em>�Ƃ��ĉ��߂��A�t�H�[�}�b�g����B
 *  �����p�^�[��������̏ڍׂɂ��ẮA<code>java.text.SimpleDateFormat</code>
 *  �N���X�̃h�L�������g���Q�Ƃ̂��ƁB
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p>{@link DateFormatterTagBase} ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>{@link DateFormatterTagBase} ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <li>
 *  <code>&quot;form0001&quot;</code> bean��
 *  <code>&quot;field001&quot;</code> �v���p�e�B�̒l���w�肵���`���Ƀt�H�[�}�b�g
 *  ���ďo�͂���B
 * </li>
 * <p><code><pre>
 *  &lt;t:date name="form0001"
 *             property="field001"
 *             pattern="yyyyy.MMMMM.dd GGG hh:mm aaa" /&gt;
 * =&gt 1996.July.10 AD 12:08 PM
 * </pre></code></p>
 * <br>
 * <li>
 *  ��L�� bean�̃v���p�e�B���A�o�͂����ɃX�N���v�e�B���O�ϐ�
 *  <code>&quot;formatted&quot;</code>�փZ�b�g����B
 * </li>
 * <p><code><pre>
 *  &lt;t:date id="formatted"
 *             name="form0001"
 *             property="field001"
 *             pattern="yyyyy.MMMMM.dd GGG hh:mm aaa" /&gt;
 * </pre></code></p>
 * <br>
 * <li>
 *  �t�H�[�}�b�g������t�����f�[�^�� <code>bean</code> ������o�����ɁA
 *  �^�O�� <code>value</code>�����ɂ���Ďw�肷��ꍇ�B
 * </li>
 * <p><code><pre>
 *  &lt;t:date value="2002/07/25 04:56:23"
 *             pattern="yyyyy.MMMMM.dd GGG hh:mm aaa" /&gt;
 * </pre></code></p>
 *
 * @see java.text.SimpleDateFormat
 * @see jp.terasoluna.fw.web.taglib.DateFormatterTagBase
 *
 */
public class DateTag extends DateFormatterTagBase {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = -9087010541359988539L;

    /**
     * <code>pattern</code> �����Ŏw�肳�ꂽ <code>SimpleDateFormat</code>
     * �`���̃t�H�[�}�b�g�ɏ]���āA�w�肳�ꂽ���t�����f�[�^���t�H�[�}�b�g����B
     *
     * @param date ���t�����f�[�^
     * @return <code>pattern</code> �����Ŏw�肳�ꂽ�o�͌`���̕�����
     */
    @Override
    protected String doFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
