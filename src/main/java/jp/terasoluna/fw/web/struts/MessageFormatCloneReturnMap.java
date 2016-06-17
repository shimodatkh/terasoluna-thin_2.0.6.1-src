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

/**
 * Strutsのバグ(STR-2172)回避用HashMap(MessageFormatキャッシュ)。
 * <p>
 * StrutsのMessageResourcesは、キャッシュからgetしたMessageFormatを同期化せずに複数スレッドでアクセス可能にしている。<br>
 * このバグを回避するため、キャッシュから同一インスタンスを返さず、cloneインスタンスを返すよう拡張している。
 * </p>
 * <p>
 * また、MessageFormatで使用するサブフォーマットの中には、
 * (具体的には、{0,number}等の記述時に使用されるDecimalFormatが該当。)
 * cloneしなければスレッドセーフであるが、
 * format実行後にcloneすると、MessageFormatインスタンス(サブフォーマットのインスタンス含む)が
 * スレッドごとに存在するにも関わらず、スレッドアンセーフとなるものがある。<br>
 * このクラスは、format実行前にputメソッドにMessageFormatインスタンスが渡される前提で、
 * putメソッド内にてcloneを行うことで、この問題を回避している。<br>
 * </p>
 * <p>
 * このクラスは、StrutsのMessageResourcesのバグ回避専用クラスである。<br>
 * putメソッド(null値の設定なし)とgetメソッドのみ使用されることを前提としている。<br>
 * その他のメソッドを使用した場合の、Mapとしての動作は保証しない。
 * また、putの戻り値では、前回のputの引数で渡されたインスタンスではなく、等価なcloneインスタンスを返す。
 * </p>
 * @see MessageFormatCacheMapFactory
 */
public class MessageFormatCloneReturnMap extends HashMap<String, MessageFormat> {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 8965893764397816159L;

    /**
     * MessageFormatインスタンスのcloneインスタンスをキャッシュする。
     * @param key MessageFormatキャッシュ時のキー
     * @param value キャッシュするMessageFormatインスタンス
     * @return メソッド実行前にキャッシュされていたMessageFormatインスタンス
     */
    @Override
    public MessageFormat put(String key, MessageFormat value) {
        // format実行済みのものをcloneすると、
        // スレッドアンセーフになるフォーマットが存在するため、
        // (具体的には、{0,number}等の記述時に使用されるDecimalFormatが該当。)
        // get時の毎回cloneとは別に、put時にも、
        // format未実行状態のMessageFormatをcloneしてキャッシュする。
        // (引数で渡ってきたMessageFormatは、このあとStrutsのMessageResourcesによってformatが実行されるので、
        //  引数で渡ってきたMessageFormat自体はキャッシュしない)
        return super.put(key, (MessageFormat) value.clone());
    }

    /**
     * キャッシュされたMessageFormatのcloneインスタンスを返す。
     * @param key MessageFormatキャッシュ時のキー
     * @return MessageFormatインスタンス
     */
    @Override
    public MessageFormat get(Object key) {
        MessageFormat messageFormat = super.get(key);
        if (messageFormat != null) {
            messageFormat = (MessageFormat) messageFormat.clone();
        }
        return messageFormat;
    }
}
