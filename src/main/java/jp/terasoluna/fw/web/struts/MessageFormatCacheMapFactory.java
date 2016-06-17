/*
 * Copyright (c) 2011 NTT DATA Corporation
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

package jp.terasoluna.fw.web.struts;

import java.text.MessageFormat;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.struts.action.DBMessageResources;
import jp.terasoluna.fw.web.struts.action.PropertyMessageResourcesEx;
import jp.terasoluna.fw.web.struts.util.SpringMessageResources;

/**
 * Strutsのバグ(STR-2172)回避用HashMap(MessageFormatキャッシュ)のファクトリクラス。
 * <p>
 * StrutsのMessageResourcesは、キャッシュからgetしたMessageFormatを同期化せずに複数スレッドでアクセス可能にしている。<br>
 * このバグを回避するための拡張HashMapを生成する。
 * </p>
 * <p>
 * 参考)<br>
 * 少なくともSun JDKの実装を使用している限りは、日付時刻系のサブフォーマット({0,date}等)を使用しない限り、
 * MessageFormatの仕様には反するものの、同一インスタンスのformatメソッドを複数スレッドで同時に実行しても、
 * 全く問題ない。(これが、StrutsのMessageResourcesのバグSTR-2172がWon't Fixとなり、修正されなかった理由。)<br>
 * このクラスではバグの回避方法を調整可能としている。system.propertiesに以下の設定を行う。
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>system.properties設定例</legend>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>同一MessageFormatを複数スレッドで使用しないよう、常にcloneする(MessageFormatの仕様に準拠)</legend>
 * <code>
 * messageResources.messageFormatClone = enable
 * </code>
 * </fieldset>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>日付時刻系のサブフォーマットが使用されたときのみ、同一MessageFormatを複数スレッドで使用しないよう、cloneする (デフォルト)<br>
 * (SunのMessageFormatおよびそこから使用されるFormatクラスの実装に合わせ最適化)</legend>
 * <code>
 * messageResources.messageFormatClone = dateFormatOnly
 * </code>
 * </fieldset>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>バグ回避を行わず、Strutsの実装のまま動作する</legend>
 * <code>
 * messageResources.messageFormatClone = disable
 * </code>
 * </fieldset>
 * 設定自体を省略した場合や、上記以外の設定値が設定された場合は <strong>dateFormatOnly</strong> として扱われる。<br>
 * </p>
 * @see MessageFormatCloneReturnMap
 * @see MessageFormatCloneReturnIfUseDateFormatMap
 * @see PropertyMessageResourcesEx
 * @see DBMessageResources
 * @see SpringMessageResources
 */
public class MessageFormatCacheMapFactory {

    /**
     * ログインスタンス
     */
    private static Log log = LogFactory
            .getLog(MessageFormatCacheMapFactory.class);

    /**
     * messageFormatClone設定値のプロパティキー
     */
    private static final String MESSAGE_FORMAT_CLONE_KEY = "messageResources.messageFormatClone";

    /**
     * messageFormatClone設定値:enable
     */
    private static final String ENABLE = "enable";

    /**
     * messageFormatClone設定値:disable
     */
    private static final String DISABLE = "disable";

    /**
     * messageFormatClone設定値:dateFormatOnly
     */
    private static final String DATE_FORMAT_ONLY = "dateFormatOnly";

    /**
     * Strutsのバグ(STR-2172)回避用HashMapインスタンスを返す。
     * <p>
     * 設定により、バグ回避が無効化されている場合は、nullを返す。
     * </p>
     * @return Strutsのバグ(STR-2172)回避用HashMapインスタンス
     */
    public static HashMap<String, MessageFormat> getInstance() {
        String cloneSetting = PropertyUtil
                .getProperty(MESSAGE_FORMAT_CLONE_KEY);
        if (log.isDebugEnabled()) {
            log.debug(MESSAGE_FORMAT_CLONE_KEY + " = " + cloneSetting);
        }
        if (cloneSetting == null) {
            log.debug("use MessageFormatCloneReturnAtDateFormatOnlyMap.");
            return new MessageFormatCloneReturnIfUseDateFormatMap();
        }

        cloneSetting = cloneSetting.toLowerCase();
        if (ENABLE.equals(cloneSetting)) {
            log.debug("use MessageFormatCloneReturnMap.");
            return new MessageFormatCloneReturnMap();
        } else if (DATE_FORMAT_ONLY.toLowerCase().equals(cloneSetting)) {
            log.debug("use MessageFormatCloneReturnAtDateFormatOnlyMap.");
            return new MessageFormatCloneReturnIfUseDateFormatMap();
        } else if (DISABLE.equals(cloneSetting)) {
            return null;
        } else {
            log.warn(MESSAGE_FORMAT_CLONE_KEY + " = " + cloneSetting
                    + " is invalid. set \"" + ENABLE + "\", \"" + DISABLE
                    + "\" or \"" + DATE_FORMAT_ONLY + "\".");
            log.warn("use MessageFormatCloneReturnAtDateFormatOnlyMap.");
            return new MessageFormatCloneReturnIfUseDateFormatMap();
        }
    }
}
