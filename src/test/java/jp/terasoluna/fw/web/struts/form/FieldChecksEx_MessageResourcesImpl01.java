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

import java.util.Locale;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * FieldChecksExのテストで使用するMessageResourcesの実装クラス。
 *
 */
@SuppressWarnings("serial")
public class FieldChecksEx_MessageResourcesImpl01 extends MessageResources {

    /**
     * 返却するメッセージ。
     */
    public String message = null;

    /**
     * コンストラクタ。
     * @param factory factory
     * @param config config
     * @param returnNull returnNull
     */
    public FieldChecksEx_MessageResourcesImpl01(MessageResourcesFactory factory, String config, boolean returnNull) {
        super(factory, config, returnNull);
    }

    /**
     * コンストラクタ。
     * @param factory factory
     * @param config config
     */
    public FieldChecksEx_MessageResourcesImpl01(MessageResourcesFactory factory, String config) {
        super(factory, config);
    }

    /**
     * メッセージを取得する。
     * @param local ロケール
     * @param key メッセージキー
     */
    @Override
    public String getMessage(Locale locale, String key) {
        return message;
    }

}
