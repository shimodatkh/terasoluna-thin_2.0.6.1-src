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

package jp.terasoluna.fw.web.struts.form;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.util.PropertyUtil;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.FormPropertyConfig;

/**
 * アクションフォーム関連のユーティリティクラス。
 *
 * <p>
 *  アクションフォーム処理関連で用いられる機能をユーティリティ
 *  として集約している。
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.form.FormEx
 * @see
 *  jp.terasoluna.fw.web.struts.form.DynaValidatorActionFormEx
 * @see
 *  jp.terasoluna.fw.web.struts.form.ValidatorActionFormEx
 *
 */
public class ActionFormUtil {
    /**
     * インデックス長の最大値のキー
     */
    private static final String FORM_ARRAY_MAX_LENGTH_KEY = "form.array.max.length";
    /**
     * インデックス長の最大値のデフォルト値
     */
    private static final int DEFAULT_MAX_LENGTH = 5000;
    /**
     * インデックス長の最大値
     */
    private static final int MAX_LENGTH = getFormArrayMaxLength(PropertyUtil
            .getProperty(FORM_ARRAY_MAX_LENGTH_KEY));

    /**
     * インデックス長の最大値を返却する。
     * 
     * @param formArrayMaxLength インデックス長の最大値文字列
     * @return インデックス長の最大値
     */
    protected static int getFormArrayMaxLength(String formArrayMaxLength) {
        int max = DEFAULT_MAX_LENGTH;
        try {
            if (formArrayMaxLength != null) {
                max = Integer.parseInt(formArrayMaxLength);
            }
        } catch (Throwable e) {
            // 例外が発生した場合はデフォルト値を使用する
        }
        return max;
    }

    /**
     * インデックスの長さをチェックする。<br>
     * <p>
     * インデックス長が最大値よりも大きい場合は{@link IllegalArgumentException}をスローする。 最大値のデフォルトは5000であり、プロパティファイルに設定可能である。<br>
     * {@link PropertyUtil}からアクセスできるプロパティファイルに<code>form.array.max.length</code>のキーに対して最大値を設定すること。<br>
     * ・<code>form.array.max.length</code>の設定がない<br>
     * ・設定値がintでパース出来ない<br>
     * の場合はデフォルト値を使用する。
     * </p>
     * 
     * @param index インデックス位置
     * @throws IllegalArgumentException インデックス長が最大値よりも大きい場合
     */
    protected static void checkIndexLength(int index)
                                                     throws IllegalArgumentException {
        if (index > MAX_LENGTH) {
            throw new IllegalArgumentException("index size is too long. : "
                    + index);
        }
    }
    /**
     * 指定したフィールドの設定情報を取得する。
     *
     * @param fieldName フィールド名
     * @param mapping マッピング情報
     * @return FormPropertyConfig
     */
    public static FormPropertyConfig getPropertyConfig(
        String fieldName,
        ActionMapping mapping) {
        String name = mapping.getName();
        if (name == null) {
            return null;
        }
        FormBeanConfig config =
            mapping.getModuleConfig().findFormBeanConfig(name);
        if (config == null) {
            return null;
        }
        return config.findFormPropertyConfig(fieldName);
    }

    /**
     * DynaActionFormの指定されたプロパティ値を
     * 初期化する。
     *
     * @param form DynaActionForm のインスタンス
     * @param fieldName 初期化対象のプロパティ
     * @param mapping アクションマッピング
     */
    public static void initialize(
        DynaActionForm form,
        String fieldName,
        ActionMapping mapping) {
        // パラメータ値のチェック
        if (form == null
            || fieldName == null
            || "".equals(fieldName)
            || mapping == null) {
            return;
        }

        FormPropertyConfig config =
            ActionFormUtil.getPropertyConfig(fieldName, mapping);
        if (config == null) {
            return;
        }
        form.set(fieldName, config.initial());

    }

    /**
     *  セッションスコープに格納された論理名が&quot;_&quot;で始まる全ての
     *  アクションフォームを削除するユーティリティメソッド。
     *
     * <p>
     *  ただし exclude で指定されたアクションフォームは削除しない。
     * </p>
     *
     * @param session HTTPセッション
     * @param exclude 削除対象としないアクションフォーム名
     */
    public static void clearActionForm(HttpSession session, String exclude) {

        Enumeration enumeration = session.getAttributeNames();
        List<String> removeList = new ArrayList<String>();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            if (key.startsWith("_")) {
                if (exclude == null || !key.equals(exclude)) {
                    if (session.getAttribute(key) instanceof ActionForm) {
                        removeList.add(key);
                    }
                }
            }
        }

        int cnt = removeList.size();
        for (int i = 0; i < cnt; i++) {
            String removeKey = removeList.get(i);
            session.removeAttribute(removeKey);
        }
    }

    /**
     * セッションスコープに格納された論理名が&quot;_&quot;で
     * 始まる全てのアクションフォームを削除するユーティリティメソッド。
     *
     * @param session HTTPセッション
     */
    public static void clearActionForm(HttpSession session) {
        clearActionForm(session, null);
    }

    /**
     *  HTTPリクエスト属性として設定されているアクションマッピングから
     *  アクションフォーム名を取得する。
     *
     * @param req HTTPリクエスト
     * @return アクションフォーム名
     */
    public static String getActionFormName(HttpServletRequest req) {
        ActionMapping mapping =
            (ActionMapping) req.getAttribute(Globals.MAPPING_KEY);
        if (mapping == null) {

            // 以降は実行しない
            return null;
        }
        return mapping.getName();
    }

}
