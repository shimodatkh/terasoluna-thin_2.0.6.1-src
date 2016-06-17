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

/**
 * <p>
 *  �����_��ID�����N���X�B
 * </p>
 * 
 * <p>
 *  {@link LinkTag} �� {@link FormTag} ���ŁA�A�N�V���� <code>URL</code>
 *  �ɃL���b�V�������p�̃����_�� <code>ID</code> ��ǉ����Ɏg�p�����B
 * </p>
 * 
 * 
 * @see FormTag
 * @see LinkTag
 * 
 */
public class RandomUtil {

    /**
     * �����_��ID��URL�ɒǉ�����Ƃ��̃p�����[�^���B
     */
    public static final String RANDOM_ID_KEY = "r";

    /**
     * �����W�F�l���[�^�B
     */
    private static final java.util.Random RANDOM = new java.util.Random();

    /**
     * <p>�����_��ID������𐶐�����B</p>
     *
     * @return �����_��ID
     */
    public static String generateRandomID() {
        long r = RANDOM.nextLong();
        long t = System.currentTimeMillis();
        return String.valueOf(Math.abs(r + t));
    }

}
