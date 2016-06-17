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

import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * Strutsのバグ(STR-2172)回避用HashMap(MessageFormatキャッシュ)。
 * <p>
 * StrutsのMessageResourcesは、キャッシュからgetしたMessageFormatを同期化せずに複数スレッドでアクセス可能にしている。<br>
 * このバグを回避するため、キャッシュから同一インスタンスを返さず、cloneインスタンスを返すよう拡張している。<br>
 * ただし、全てのMessageFormatを無条件にcloneするのではなく、
 * 明らかにスレッドアンセーフ問題が発生する、DateFormatをサブフォーマットに持つMessageFormatのみ、
 * get時にcloneする。
 * </p>
 * <p>
 * このクラスは、StrutsのMessageResourcesのバグ回避専用クラスである。<br>
 * putメソッド(null値の設定なし)とgetメソッドのみ使用されることを前提としている。<br>
 * その他のメソッドを使用した場合の、Mapとしての動作は保証しない。<br>
 * また、putの戻り値では、前回のputの引数で渡されたインスタンスではなく、等価なcloneインスタンスを返す場合がある。
 * (get時にcloneインスタンスを返す条件を満たすものがこれに該当する。)
 * </p>
 * @see MessageFormatCacheMapFactory
 */
public class MessageFormatCloneReturnIfUseDateFormatMap
                                                       extends
                                                       HashMap<String, MessageFormat> {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 5766672928587118585L;

    /**
     * get時にMessageFormatのcloneインスタンスを返すMap
     */
    private MessageFormatCloneReturnMap cloneReturnMap = new MessageFormatCloneReturnMap();

    /**
     * MessageFormatインスタンスをキャッシュする。
     * @param key MessageFormatキャッシュ時のキー
     * @param value キャッシュするMessageFormatインスタンス
     * @return メソッド実行前にキャッシュされていたMessageFormatインスタンス
     */
    @Override
    public MessageFormat put(String key, MessageFormat value) {
        boolean needClone = false;
        for (Format format : value.getFormats()) {
            if (format instanceof DateFormat) {
                needClone = true;
                break;
            }
        }
        if (needClone) {
            return cloneReturnMap.put(key, value);
        }
        return super.put(key, value);
    }

    /**
     * キャッシュされたMessageFormatインスタンスを、必要に応じてcloneして返す。
     * @param key MessageFormatキャッシュ時のキー
     * @return MessageFormatインスタンス
     */
    @Override
    public MessageFormat get(Object key) {
        MessageFormat messageFormat = super.get(key);
        if (messageFormat == null) {
            messageFormat = cloneReturnMap.get(key);
        }
        return messageFormat;
    }
}
