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

package jp.terasoluna.fw.web;


import java.io.Serializable;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>���O�I�����[�U��񒊏ۃN���X</p>
 *
 * <p>
 *  ���O�I�����̃��[�U����\���I�u�W�F�N�g(<code>UVO</code>)���ۃN���X�ł���B
 *  �p���N���X�̐����́A<code>ApplicationResources.properties</code>��
 *  <code>user.value.object</code>���L�[�Ƃ��Čp���N���X����ݒ肵�A
 *  <code>createUserValueObject()</code>���s�����Ƃɂ�萶�������B
 * </p>
 * <h5><code>ApplicationResources.properties</code>�̐ݒ��</h5>
 * <ul>
 *   <li>user.value.object=jp.terasoluna.sample.xxxx.SampleUVO</li>
 * </ul>
 * <h5> <code>SampleUVO</code> �̎�����</h5>
 * <code><pre>
 * public class SampleUVO extends UserValueObject {
 *   // �K�v�ɉ����Ď���
 *   public String[] getFieldNames() {
 *       return new String[]{"companyId", "userId", "address", ...};
 *   }
 *
 *   // ���ID
 *   String companyId = null;
 *   // ���[�UID
 *   String userId = null;
 *   // �Z��
 *   String address = null;
 *   �E�E�E
 *   // �t�B�[���h��getter�Asetter��
 *   �E�E�E
 * }
 * </pre></code>
 * <h5>���O�I���Ɩ����W�b�N�̎�����</h5>
 * <code><pre>
 * public ResultBean execute(LogonBean params) {
 *    �E�E�E
 *    // UVO�̐���
 *    SampleUVO uvo = (SampleUVO) UserValueObject.createUserValueObject();
 *    �E�E�E
 *    // UVO�Ƀ��[�U����ݒ�
 *    uvo.setCompanyId(companyId);
 *    uvo.setUserId(userId);
 *    uvo.setAddress(address);
 *    �E�E�E
 * }
 * </pre></code>
 *
 */
public abstract class UserValueObject implements Serializable {

    /**
     * ���O�N���X
     */
    private static Log log
          = LogFactory.getLog(UserValueObject.class);

    /**
     * <code>ApplicationResources</code> �t�@�C����
     * <code>UserValueObject</code> �p���N���X���w�肷��L�[�B
     */
    public static final String USER_VALUE_OBJECT_PROP_KEY = "user.value.object";

    /**
     * <code>UserValueObject</code> �p���N���X�̃C���X�^���X�쐬���s
     * �������G���[�R�[�h�B
     */
    private static final String UVO_CLASS_ERROR = "errors.uvo.class";

    /**
     * <code>UserValueObject</code> �p���N���X��
     * HTTP�Z�b�V��������擾����ۂɗp����L�[�B
     */
    public static final String USER_VALUE_OBJECT_KEY = "USER_VALUE_OBJECT";

    /**
     * ���[�U���I�u�W�F�N�g�𐶐�����B
     *
     * @return ���[�U���I�u�W�F�N�g
     */
    public static UserValueObject createUserValueObject() {

        UserValueObject userValueObject = null;
        String className =
            PropertyUtil.getProperty(USER_VALUE_OBJECT_PROP_KEY);
        if (className != null) {
            try {
                userValueObject = (UserValueObject) ClassUtil.create(className);
            } catch (ClassLoadException e) {
                log.error("illegal uvo class:" + className, e);
                throw new SystemException(e, UVO_CLASS_ERROR, className);
            } catch (ClassCastException e) {
                log.error("illegal uvo class:" + className, e);
                throw new SystemException(e, UVO_CLASS_ERROR, className);
            }
        } else {
            log.error("specify " + USER_VALUE_OBJECT_PROP_KEY + ".");
            return null;
        }

        return userValueObject;
    }

}
