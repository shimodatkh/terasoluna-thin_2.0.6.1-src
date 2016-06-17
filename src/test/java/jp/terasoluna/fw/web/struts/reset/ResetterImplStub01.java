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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.struts.form.FormEx;

import org.apache.struts.action.ActionMapping;

/**
 * ResetterImplTest�Ŏg�p����X�^�u�N���X�B
 *
 *
 */
public class ResetterImplStub01 extends ResetterImpl {

    /**
     * getActionReset�̖߂�l�ƂȂ�C���X�^���X�B
     */
    public ActionReset actionReset = null;

    /**
     * resetSelectField���Ăяo���ꂽ�񐔁B
     */
    public int resetSelectFieldCount = 0;

    /**
     * resetSelectField�̑��������L���b�V������List�B
     */
    public List<FormEx> resetSelectFieldArg0 = new ArrayList<FormEx>();

    /**
     * resetSelectField�̑��������L���b�V������List�B
     */
    public List<Map> resetSelectFieldArg1 = new ArrayList<Map>();

    /**
     * resetSelectField�̑�O�������L���b�V������List�B
     */
    public List<HttpServletRequest> resetSelectFieldArg2 =
    	new ArrayList<HttpServletRequest>();

    /**
     * resetValue���Ăяo���ꂽ�񐔁B
     */
    public int resetValueCount = 0;

    /**
     * resetValue�̑��������L���b�V������List�B
     */
    public List<FormEx> resetValueArg0 = new ArrayList<FormEx>();

    /**
     * resetValue�̑��������L���b�V������List�B
     */
    public List<Entry> resetValueArg1 = new ArrayList<Entry>();

    /**
     * �C���X�^���X�ϐ�actionReset��ԋp����B
     */
    @Override
    protected ActionReset getActionReset(ActionMapping mapping,
            HttpServletRequest request) {
        return this.actionReset;
    }

    /**
     * �Ăяo���̓��e���L���b�V������B
     */
    @Override
    protected void resetSelectField(FormEx form,
            Map<String, Object> propMap, HttpServletRequest request) {
        this.resetSelectFieldCount++;
        this.resetSelectFieldArg0.add(form);
        this.resetSelectFieldArg1.add(propMap);
        this.resetSelectFieldArg2.add(request);
    }

    /**
     * �Ăяo���̓��e���L���b�V������B
     */
    @Override
    protected void resetValue(FormEx form, Entry<String, Object> entry) {
        this.resetValueCount++;
        this.resetValueArg0.add(form);
        this.resetValueArg1.add(entry);
    }

}
