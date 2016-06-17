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

/**
 * �����̃t�B�[���h�̑��֓��̓`�F�b�N���s���C���^�t�F�[�X�B
 *
 * �v���[���e�[�V�����w�ŃA�N�V�����t�H�[���̕����t�B�[���h�Ԃ̈ˑ��֌W�ɂ��
 * ���̓`�F�b�N�����s����ꍇ�́A���̃C���^�t�F�[�X�����������N���X���쐬����B
 * {@link #validate(String, String[])} ���\�b�h�̑������ɂ͌��ؑΏۂ̒l�A
 * �������ɂ͈ˑ�����t�B�[���h�̒l���z��œn�����B���؃G���[�̏ꍇ��
 * <code>false</code> ��ԋp���邱�ƁB<br>
 * ���ؑΏۂ̃t�B�[���h�� <code>null</code> �܂��͋󕶎��œn�����ꍇ������
 * �̂ŁA���ӂ��K�v�ł���B�܂��A���̌��؃��[���ɂ̓f�t�H���g��
 * �G���[���b�Z�[�W�����݂��Ȃ����߁A<code>validation.xml</code>
 * �ɂ͕K�����b�Z�[�W�̐ݒ���s�����ƁB<br>
 * <strong>�����̌��؃��[����JavaScript�ł̃`�F�b�N���T�|�[�g���Ă��Ȃ��B
 * </strong>
 * <br>
 * <br>
 * �A�N�V�����t�H�[����value�t�B�[���h�̒l���Avalue1�t�B�[���h�̒l�ȏ�A
 * value2�t�B�[���h�̒l�ȉ��ł��邱�Ƃ����؂���ꍇ�A�ȉ��̂悤�Ɏ����A�ݒ��
 * �s���B
 * <h5>{@link MultiFieldValidator} �̎�����</h5>
 * <code><pre>
 * public boolean validate(String value, String[] fields) {
 *     int value0 = Integer.parseInt(value);
 *     int value1 = Integer.parseInt(fields[0]);
 *     int value2 = Integer.parseInt(fields[1]);
 *     return (value1 <= value0 && value2 >= value0);
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
 */
public interface MultiFieldValidator {

    /**
     * �����t�B�[���h�̑��֓��̓`�F�b�N�����s����B
     * <br>
     * ���ؑΏۂ̒l�͑������œn�����B���؂ɕK�v�ȑ��̃t�B�[���h��
     * �l�͑������ɔz��Ƃ��ēn�����B���؃G���[�̏ꍇ�� <code>false</code>
     * ��ԋp���邱�ƁB
     *
     * @param value ���ؑΏۂ̒l
     * @param fields ���؂ɕK�v�ȑ��̃t�B�[���h�̒l�z��
     * @return �G���[���Ȃ���� <code>true</code>
     */
    boolean validate(String value, String[] fields);

}
