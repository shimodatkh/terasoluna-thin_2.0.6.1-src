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

package jp.terasoluna.fw.web.struts.reset;

import javax.servlet.http.HttpServletRequest;

/**
 * ResetterTest�Ŏg�p����X�^�u�N���X�B
 *
 *
 */
public class ResetterImplStub02 extends ResetterImpl {

    /**
     * getResetterResources�̖߂�l�B
     */
    public ResetterResources resetterResources = null;

    /**
     * getActionReset�̃e�X�g�Ŏg�p�����X�^�u���\�b�h�B
     */
    @Override
    protected ResetterResources getResetterResources(HttpServletRequest request) {
        return this.resetterResources;
    }
}
