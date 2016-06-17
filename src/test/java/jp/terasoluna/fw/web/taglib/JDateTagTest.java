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

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.TagUTUtil;

/**
 * JDateTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class JDateTagTest extends PropertyTestCase {

    // �e�X�g�Ώ�
    JDateTag tag = null;

    /**
     * Constructor for JDateTagTest.
     * @param arg0
     */
    public JDateTagTest(String arg0) {
        super(arg0);
        tag = (JDateTag) TagUTUtil.create(JDateTag.class);
    }
    
    /*
     * @see PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        /*
         * �v���p�e�B�t�@�C���̕ς��ƂȂ�Map�̍쐬�B
         */
        Map<String, String> m = new HashMap<String, String>();
        m.put("wareki.gengo.0.name", "����");
        m.put("wareki.gengo.0.roman", "H");
        m.put("wareki.gengo.0.startDate", "1989/01/08");
        m.put("wareki.gengo.1.name", "���a");
        m.put("wareki.gengo.1.roman", "S");
        m.put("wareki.gengo.1.startDate", "1926/12/25");
        m.put("wareki.gengo.2.name", "�吳");
        m.put("wareki.gengo.2.roman", "T");
        m.put("wareki.gengo.2.startDate", "1912/07/30");
        m.put("wareki.gengo.3.name", "����");
        m.put("wareki.gengo.3.roman", "M");
        m.put("wareki.gengo.3.startDate", "1868/09/04");
        // �v���p�e�B�Ƃ���Map��ǉ��B
        addPropertyAll(m);
    }

    /*
     * @see PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {}

    /**
     * testDoFormat01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l<br>
     * date="2004-11-24 10:31:00.000000000"<br>
     * pattern="Gyy.MM.dd"<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String="H16.11.24"<br>
     * <br>
     * ����date����сApattern��Null�ł͂Ȃ��ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat01() throws Exception {

        // �e�X�g�ݒ�
        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        Date date = new Date(time.getTime());
        tag.pattern = "Gyy.MM.dd";

        // �e�X�g���s
        String result = tag.doFormat(date);

        // �e�X�g���ʊm�F
        assertEquals("H16.11.24", result);

    } /* testDoFormat01 End */

} /* JDateTagTest Class End */
