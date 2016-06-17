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

import jp.terasoluna.fw.util.StringUtil;

/**
 * <p>
 *  ������̗����i�����A����щE���j�̃z���C�g�X�y�[�X���폜����
 *  <code>trim</code>�^�O�̎����N���X�B
 * </p>
 * <p>
 *  <code>jp.terasoluna.fw.util.StringUtil</code> �N���X��
 *  <code>trim()</code> ���\�b�h�ɂ���ĕ�����̍��E�̃z���C�g�X�y�[�X��
 *  �폜����B
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
 * <p> {@link StringFormatterTagBase} ���Q�ƁB</p>
 *
 * <br>
 *
 * <h5>�g�p���@</h5>
 * <p>
 *  <code>&quot;form0001&quot;</code> bean��
 *  <code>&quot;field001&quot;</code> �v���p�e�B�̒l�����E������
 *  �z���C�g�X�y�[�X���������ďo�͂���ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 * <p><code><pre>
 *  &lt;t:trim name="form0001"
 *             property="field001" /&gt;
 * </pre></code></p>
 * <p>
 *  ��L�� bean�̃v���p�e�B���A�J�X�^���^�O�ŏo�͂����ɃX�N���v�e�B���O�ϐ�
 *  <code>&quot;trimmed&quot;</code>
 *  �փZ�b�g����ꍇ�ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 * <p><code><pre>
 *  &lt;t:trim id="trimmed"
 *             name="form0001"
 *             property="field001" /&gt;
 * </pre></code></p>
 * <p>
 *  ���E�����̃z���C�g�X�y�[�X���������錳�̕������bean������o�����ɁA
 *  �J�X�^���^�O�� <code>value</code>
 *  �����ɂ���Ďw�肷��ꍇ�ɂ́A�ȉ��̂悤�ɋL�q����B
 * </p>
 * <p><code><pre>
 *  &lt;t:trim value="���E�����z���C�g�X�y�[�X�����O�̕�����  " /&gt;
 * </pre></code></p>
 *
 * @see jp.terasoluna.fw.web.taglib.StringFormatterTagBase
 *
 * 
 */
public class RTrimTag extends StringFormatterTagBase {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 5169181206026521676L;

    /**
     * �S�p�X�y�[�X�̃g�����ۑ����B
     */
    protected boolean zenkaku = false;

    /**
     * �S�p�X�y�[�X�̃g�����ۑ������擾����B
     *
     * @return �S�p�X�y�[�X�̃g�����ۑ���
     */
    public boolean getZenkaku() {
        return this.zenkaku;
    }

    /**
     * �S�p�X�y�[�X�̃g�����ۑ�����ݒ肷��B
     *
     * @param zenkaku �S�p�X�y�[�X�̃g�����ۑ���
     */
    public void setZenkaku(boolean zenkaku) {
        this.zenkaku = zenkaku;
    }

    /**
     * <p>�w�肳�ꂽ������̉E���̃z���C�g�X�y�[�X���폜����B</p>
     *
     * @param s �t�H�[�}�b�g�Ώۂ̕�����
     * @return �E���̃z���C�g�X�y�[�X���폜���ꂽ������
     */
    @Override
    protected String doFormat(String s) {
    	if (zenkaku) {
    		return StringUtil.rtrimZ(s);
    	}
        return StringUtil.rtrim(s);
    }

}
