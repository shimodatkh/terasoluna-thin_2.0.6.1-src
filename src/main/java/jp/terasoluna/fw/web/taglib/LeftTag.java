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

/**
 * <p>
 *  ������̍��[����w�肳�ꂽ���������̕������؂�o�� <code>left</code>
 *  �^�O�̎����N���X�B
 * </p>
 *
 * <p>
 *  {@link jp.terasoluna.fw.util.StringUtil} �N���X��
 * <code>substring()</code> ���\�b�h�ɂ���āA������̍��[����w�肳�ꂽ
 * ��������؂�o���B
 * </p>
 *
 * <br>
 *
 * <h5>�^�O���T�|�[�g���鑮��</h5>
 * <p> {@link StringFormatterTagBase} ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</h5>
 * <p>
 *  {@link StringFormatterTagBase} ���Q�ƁB<br>
 *  �ȉ��A���̃N���X�Œǉ����ꂽ�X�N���v�e�B���O�ϐ�
 * </p>
 *
 * <p><div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <th>������</th>
 *   <th>�f�t�H���g</th>
 *   <th>���s����</th>
 *   <th>�L�q</th>
 *  </tr>
 *  <tr>
 *   <td> length </td>
 *   <td> - </td>
 *   <td> <code>true</code> </td>
 *   <td align="left">
 *    �t�H�[�}�b�g�Ώۂ̕����񂩂�؂�o���������B
 *   </td>
 *  </tr>
 * </table>
 * </div></p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <p>
 *  <code>&quot;form0001&quot;</code> bean��
 *  <code>&quot;field001&quot;</code> �v���p�e�B�̒l���A�����T������؂�o����
 *  �o�͂���ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:left name="form0001"
 *             property="field001"
 *             length="5" /&gt;
 * </pre></code></p>
 *
 * <p>
 *  ��L�� bean�̃v���p�e�B���A�J�X�^���^�O�ŏo�͂����ɃX�N���v�e�B���O�ϐ�
 *  <code>&quot;cut&quot;</code>�փZ�b�g����ꍇ�ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:left id="cut"
 *             name="form0001"
 *             property="field001"
 *             length="5" /&gt;
 * </pre></code></p>
 *
 * <p>
 *  �w�蕶�������؂�o�����ƂȂ镶������Abean������o�����ɁA
 *  �J�X�^���^�O�� <code>value</code>
 *  �����ɂ���Ďw�肷��ꍇ�ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 *
 * <p><code><pre>
 *  &lt;t:left value="�w�蕶�������؂�o���O�̕�����  "
 *             length="5" /&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.taglib.StringFormatterTagBase
 *
 */
public class LeftTag extends StringFormatterTagBase {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 220591418239073704L;

    /**
     * �؂�o��������̒����B
     *
     */
    protected int length = 0;

    /**
     * �؂�o��������̒������擾����B
     *
     * @return �؂�o��������̒���
     */
    public int getLength() {
        return this.length;
    }

    /**
     * �؂�o��������̒�����ݒ肷��B
     *
     * @param length �؂�o��������̒���
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * ���ׂẴA���P�[�g���ꂽ�������������B
     */
    @Override
    public void release() {
        super.release();
        length = 0;
    }

    /**
     * �w�肳�ꂽ������̍��[���� <code>lenth</code> �����Ŏw�肳�ꂽ����������
     * ��������擾����B
     *
     * @param s �t�H�[�}�b�g�Ώۂ̕�����
     * @return �؂�o���ꂽ������
     */
    @Override
    protected String doFormat(String s) {
        return s.length() > length ? s.substring(0, length) : s;
    }

}
