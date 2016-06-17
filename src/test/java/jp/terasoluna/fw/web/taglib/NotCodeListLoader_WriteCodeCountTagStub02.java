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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.web.codelist.CodeBean;

/**
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * CodeListLoader���������Ȃ��N���X�̃C���X�^���X�B
 * <p>	
 * 
 */

public class NotCodeListLoader_WriteCodeCountTagStub02 {
    /**
     * �R�[�h���X�g�������������ɗ^���邽�߂� <code>Map</code> �B
     */
    private Map<String, String> codeListMap = null;

    /**
     * �R�[�h���X�g�B
     * 
     * @see CodeBean
     */
    private List<CodeBean> codeLists = null;

    /**
     * �R�[�h���X�g��������� <code>Map</code> ���擾����B
     * 
     * @return �R�[�h���X�g��������� <code>Map</code>
     */
    public Map getCodeListMap() {
        return codeListMap;
    }

    /**
     * �R�[�h���X�g��������� <code>Map</code> ��ݒ肷��B
     * 
     * @param codeListMap
     *            �R�[�h���X�g��������� <code>Map</code>
     */
    public void setCodeListMap(Map<String, String> codeListMap) {
        this.codeListMap = codeListMap;
    }

    /**
     * �R�[�h���X�g�̏��������s���B<br>
     * <br>
     * ���ɐݒ肳��Ă��� <code>codeListMap</code> �̏�񂩂� {@link CodeBean} �𐶐�����B
     */
    public void load() {
        if (codeLists != null) {
            return;
        }
        if (codeListMap == null) {
            codeLists = Collections.unmodifiableList(new ArrayList<CodeBean>());
            return;
        }
        List<CodeBean> list = new ArrayList<CodeBean>();
        Iterator it = codeListMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = codeListMap.get(key);
            CodeBean cb = new CodeBean();
            cb.setId(key);
            cb.setName(value);
            list.add(cb);
        }
        codeLists = Collections.unmodifiableList(list);
    }

    /**
     * �R�[�h���X�g���擾����B<br>
     * <br>
     * �R�[�h���X�g�� {@link CodeBean} �̔z��Ƃ��Ď擾�ł���B<br>
     * ���R�[�h���X�g�͌����Ƃ��ăA�v���P�[�V�������ň�ӂƂȂ���ł���B ���̃��\�b�h���I�[�o�[���C�h����ꍇ�́A�Ɩ����W�b�N�Ȃǂ�
     * �R�[�h���X�g�̓��e���ҏW����Ă��e�����Ȃ��悤�Ɏ�������K�v������B
     * 
     * @return �R�[�h���X�g
     */
    public CodeBean[] getCodeBeans() {
            return null;
    }
}
