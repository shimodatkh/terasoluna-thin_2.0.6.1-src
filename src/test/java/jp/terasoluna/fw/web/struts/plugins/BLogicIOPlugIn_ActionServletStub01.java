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

package jp.terasoluna.fw.web.struts.plugins;

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionServlet;

/**
 * BLogicIOPlugInTestクラスで利用する。
 * 
 * @version 2004/04/06
 */
public class BLogicIOPlugIn_ActionServletStub01
        extends ActionServlet {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = -2391654312205591109L;

    /**
     * サーブレットコンテキストスタブ。
     * ルールファイルで指定されたリソースを保持する。
     */
    private BLogicIOPlugIn_MockServletContextStub01 ctx 
        = new BLogicIOPlugIn_MockServletContextStub01();

    /**
     * サーブレットコンテキストを返却する。
     * 
     * @return 擬似サーブレットコンテキスト
     */
    @Override
    public ServletContext getServletContext() {
        return ctx;
    }
}
