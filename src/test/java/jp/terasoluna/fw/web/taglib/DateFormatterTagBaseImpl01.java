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

import java.util.Date;

/**
 * DateFormatterTagBaseImpl01�B
 * StringFormatterTagBase�̃e�X�g�p�N���X<br>
 * �T�v<br>
 * DateFormatterTagBase��abstract�N���X�ł��邽�߁A
 * �e�X�g�p�ɍ쐬����N���X�ł���B
 */
@SuppressWarnings("serial")
public class DateFormatterTagBaseImpl01 extends DateFormatterTagBase {
    @Override
    public String doFormat(Date s) {
        System.out.println("start doFormat(" + s.getClass() + ")");
        System.out.println("s=" + s.toString());
        return "<DateFormatterTagBaseTest>";
    } /* doFormat End */
} /* DateFormatterTagBaseImpl Class End */
