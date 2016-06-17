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

package jp.terasoluna.fw.web.struts.taglib;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * RandomUtilTest �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class RandomUtilTest extends TestCase {

    /**
     * Constructor for RandomUtilTest.
     * @param arg0
     */
    public RandomUtilTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testGenerateRandomID01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FC<br>
     * <br>
     * ���͒l<br>
     * �Ȃ�<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String=�����_��ID<br>
     * <br>
     * 10000����s���A����ID�����݂��邩�ۂ��m�F������e�X�g�P�[�X
     */
    public void testGenerateRandomID01() {

        //�e�X�g�ݒ�
        List<String> list = new ArrayList<String>();
        list.add(RandomUtil.generateRandomID());

        //�e�X�g���s&�e�X�g���ʊm�F
        for(int i = 0; i < 10000; i++){
            String rand = RandomUtil.generateRandomID();
            if(list.contains(rand)){
                fail();
            }
            list.add(rand);
        }

        list.clear();
        list = null;

    } /* testGenerateRandomID End */

} /* RandomUtilTest Class End */
