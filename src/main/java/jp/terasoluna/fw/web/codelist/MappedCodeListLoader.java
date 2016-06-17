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

package jp.terasoluna.fw.web.codelist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * �R�[�h���X�g���̏������� <code>Map</code> �ōs���A
 * {@link jp.terasoluna.fw.web.codelist.CodeListLoader}
 * �����N���X�B<br>
 * <br>
 * ���̃N���X��p���ăR�[�h���X�g���`������ꍇ�́A
 * {@link #setCodeListMap(Map)} ���\�b�h�ŃR�[�h���X�g���� <code>Map</code>
 * �`���ŗ^������A{@link #load()} �����s����K�v������B<br>
 * <h5>Spring�t���[�����[�N�ł̎g�p��B</h5>
 * <code><pre>
 * &lt;bean id="reader1"
 *       class="jp.terasoluna.fw.web.codelist.MappedCodeListLoader"
 *       init-method="load"&gt;
 *     &lt;property name="codeListMap"&gt;
 *         &lt;map&gt;
 *             &lt;entry key="001"&gt;
 *                 &lt;value&gt;value001&lt;/value&gt;
 *             &lt;/entry&gt;
 *             &lt;entry key="002"&gt;
 *                 &lt;value&gt;value002&lt;/value&gt;
 *             &lt;/entry&gt;
 *             &lt;entry key="003"&gt;
 *                 &lt;value&gt;value003&lt;/value&gt;
 *             &lt;/entry&gt;
 *         &lt;/map&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * <h5>Spring�t���[�����[�N�ł̎g�p��(���ۉ������R�[�h���X�g���`)�B</h5>
 * <code><pre>
 * &lt;bean id="reader1"
 *       class="jp.terasoluna.fw.web.codelist.MappedCodeListLoader"
 *       init-method="load"&gt;
 *     &lt;property name="codeListMap"&gt;
 *         &lt;map&gt;
 *             &lt;entry key="ja"&gt;
 *                 &lt;map&gt;
 *                    &lt;entry key="001"&gt;
 *                         &lt;value&gt;�l001&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="002"&gt;
 *                         &lt;value&gt;�l002&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="003"&gt;
 *                         &lt;value&gt;�l003&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                 &lt;/map&gt;
 *             &lt;/entry&gt;
 *         &lt;/map&gt;
 *         &lt;map&gt;
 *             &lt;entry key="en"&gt;
 *                 &lt;map&gt;
 *                    &lt;entry key="001"&gt;
 *                         &lt;value&gt;value001&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="002"&gt;
 *                         &lt;value&gt;value002&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                     &lt;entry key="003"&gt;
 *                         &lt;value&gt;value003&lt;/value&gt;
 *                     &lt;/entry&gt;
 *                 &lt;/map&gt;
 *             &lt;/entry&gt;
 *         &lt;/map&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 *
 */
public class MappedCodeListLoader extends AbstractMultilingualCodeListLoader {

    /**
     * �R�[�h���X�g�������������ɗ^���邽�߂� <code>Map</code> �B
     */
    private Map<String, ?> codeListMap = null;

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
     * @param codeListMap �R�[�h���X�g��������� <code>Map</code>
     */
    public void setCodeListMap(Map<String, ?> codeListMap) {
        this.codeListMap = codeListMap;
    }

    /**
     * �R�[�h���X�g�̏��������s���B<br>
     * <br>
     * ���ɐݒ肳��Ă��� <code>codeListMap</code> �̏�񂩂�
     * {@link CodeBean} �𐶐�����B
     */
    public void load() {
        if (localeMap != null) {
            return;
        }

        Map<Locale, List<CodeBean>> localeCodeListMap =
                                     new HashMap<Locale, List<CodeBean>>();
        if (codeListMap != null) {
            Iterator<String> it = codeListMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object value = codeListMap.get(key);

                // value��Map�̏ꍇ�A���P�[���̃R�[�h���X�g���쐬
                // value��String�̏ꍇ�A�f�t�H���g���P�[���̃R�[�h���X�g���쐬
                if (value instanceof Map) {
                    Map clMap = (Map) value;
                    if (clMap == null) {
                        continue;
                    }

                    List<CodeBean> list = new ArrayList<CodeBean>();
                    Iterator clMapIt = clMap.keySet().iterator();
                    while (clMapIt.hasNext()) {
                        String id = (String) clMapIt.next();
                        CodeBean cb = new CodeBean();
                        cb.setId(id);
                        cb.setName((String) clMap.get(id));
                        list.add(cb);
                    }
                    localeCodeListMap.put(createLocale(key), list);
                } else if (value instanceof String) {

                    Locale locale = defaultLocale;
                    List<CodeBean> list = localeCodeListMap.get(locale);
                    if (list == null) {
                        list = new ArrayList<CodeBean>();
                    }

                    CodeBean cb = new CodeBean();
                    cb.setId(key);
                    cb.setName((String) value);
                    list.add(cb);

                    localeCodeListMap.put(locale, list);
                }
                // Map�AString�ȊO�̏ꍇ�A�R�[�h���X�g���Ƃ��ĕێ����Ȃ�
            }

            for (Entry<Locale, List<CodeBean>> entry : localeCodeListMap
                    .entrySet()) {
                localeCodeListMap.put(entry.getKey(), Collections
                        .unmodifiableList(entry.getValue()));
            }
        }

        localeMap = Collections.unmodifiableMap(localeCodeListMap);
    }
	
    /**
     * ���P�[�������񂩂烍�P�[���I�u�W�F�N�g���쐬����B
     * 
     * @param locale
     *            ���P�[��������
     * @return ���P�[���I�u�W�F�N�g
     */
    Locale createLocale(String locale) {
        if (locale == null || locale.length() == 0) {
            return defaultLocale;
        }

        Locale result;
        String[] localeElements = locale.split("_", 3);
        switch (localeElements.length) {
        case 1:
            result = createLocale(localeElements[0], "", "");
            break;
        case 2:
            result = createLocale(localeElements[0], localeElements[1], "");
            break;
        case 3:
            result = createLocale(localeElements[0], localeElements[1],
                    localeElements[2]);
            break;
        default:
            result = Locale.getDefault();
        }
        return result;
    }
}
