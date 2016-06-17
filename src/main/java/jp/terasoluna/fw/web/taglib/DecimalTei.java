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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * <p><code>decimal</code> �^�O�̂��߂� <code>TagExtraInfo</code> �N���X�B</p>
 *
 * <p>{@link DecimalTag} �N���X���쐬����X�N���v�e�B���O�ϐ��ɂ��Ă�
 * ����񋟂���B</p>
 *
 * <p>�쐬�����X�N���v�e�B���O�ϐ��̕ϐ�����^�A����уX�R�[�v�ɂ��ẮA
 * {@link DecimalTag} �N���X�̃h�L�������g���Q�Ƃ̂��ƁB</p>
 *
 * @see jp.terasoluna.fw.web.taglib.DecimalTag
 *
 */
public class DecimalTei extends TagExtraInfo {

    /**
     * <p>{@link DecimalTag} �N���X���T�|�[�g����J�X�^���^�O���쐬����A
     * <code>id</code> �����ɑΉ������X�N���v�g�ϐ��Ɋւ�������擾����B</p>
     *
     * @param data �J�X�^���^�O�̑����A����т��̒l�̏��
     * @return �J�X�^���^�O�̃X�N���v�e�B���O�ϐ����
     */
    @Override
    public VariableInfo[] getVariableInfo(TagData data) {

        if (data.getId() != null) {
            return new VariableInfo [] {
                new VariableInfo(data.getId(),
                                    "java.lang.String",
                                    true,
                                    VariableInfo.AT_BEGIN)};
        }
        return new VariableInfo [] {};
    }

}
