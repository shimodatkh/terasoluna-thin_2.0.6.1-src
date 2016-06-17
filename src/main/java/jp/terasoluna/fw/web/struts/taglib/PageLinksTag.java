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

package jp.terasoluna.fw.web.struts.taglib;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.struts.form.ActionFormUtil;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

/**
 * �y�[�W�P�ʂɃy�[�W��J�ڂ���@�\�B
 * <p/>
 * Struts��<code>&lt;logic:iterate&gt;</code>�v�f�ɂ���Ē�`���ꂽ�ꗗ��
 * �y�[�W�J�ڂ̃����N��\������B
 * �����N�́A����JSP�ł���ΑΉ�����ꗗ�̏㉺���E�̂ǂ��ւł��\�����邱�Ƃ��\�ł���B
 * <p/>
 * �y�[�W�����N�@�\���g�p����ꍇ�́A
 * �A�N�V�����t�H�[���Ɉȉ��̃t�B�[���h��p�ӂ��āA
 * ���̋@�\�̑����ɑ΂��Ďw�肷��K�v������B
 * <p/>
 * <ul>
 * <li>�\���s����ێ�����t�B�[���h</li>
 * <li>�\���J�n�C���f�b�N�X��ێ�����t�B�[���h</li>
 * <li>�ꗗ���S������ێ�����t�B�[���h</li>
 * </ul>
 * <br/><br/>
 * <strong>�^�O���T�|�[�g���鑮��</strong>
 * <p>�y�[�W�����N�@�\�ł́A�ȉ��̑������T�|�[�g����B</p>
 * <div align="center">
 *  <table width="90%" border="1">
 *   <tr>
 *    <td><b>������</b></td>
 *    <td><b>�f�t�H���g�l</b></td>
 *    <td><b>�K�{��</b></td>
 *    <td><b>���s����</b></td>
 *    <td><b>�T�v</b></td>
 *   </tr>
 *   <tr>
 *    <td><code>id</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      ���̑����ɕ����񂪎w�肳�ꂽ�ꍇ�A
 *      �y�[�W�����N�̏o�͐����ʂł͂Ȃ��y�[�W�R���e�L�X�g�ɕۑ�����B
 *      ���̑����͕ۑ�����L�[�ƂȂ�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>action</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �ꗗ�\����ʂ̕\�����s���A�N�V�����p�X�����w�肷��B<br/>
 *      submit������false�̏ꍇ�͕K�{�����ƂȂ�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>name</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �\���s���A�J�n�s�C���f�b�N�X�A�ꗗ���S�s�����擾����Bean���w�肷��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>rowProperty</code></td>
 *    <td>-</td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �\���s����ێ�����t�B�[���h���w�肷��B
 *      name�������w�肳��Ă��Ȃ��ꍇ�͒��ڒl���擾����B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>indexProperty</code></td>
 *    <td>-</td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �\���J�n�C���f�b�N�X��ێ�����t�B�[���h���w�肷��B
 *      name�������w�肳��Ă��Ȃ��ꍇ�͒��ڒl���擾����B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>totalProperty</code></td>
 *    <td>-</td>
 *    <td><code>true</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      <code>&lt;logic:iterate&gt;</code>�v�f�ɂ����
 *      ��`���ꂽ�ꗗ���̑S�s����ێ�����t�B�[���h���w�肷��B
 *      name�������w�肳��Ă��Ȃ��ꍇ�͒��ڒl���擾����B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>scope</code></td>
 *    <td>-</td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      name�����Ŏw�肵��Bean���擾����X�R�[�v���w�肷��B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>submit</code></td>
 *    <td><code>false</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �����N�ł͂Ȃ��A�T�u�~�b�g���s���ꍇ��true���w�肷��B�f�t�H���g��false�B
 *      false�̏ꍇ�Aaction�������K�{�����ƂȂ�B
 *      �Ȃ��A���̑�����true�ɐݒ肷���action�����͖����ƂȂ�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>forward</code></td>
 *    <td><code>false</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      TERASOLUNA��DispatchAction���g�p���ăt�H���[�h�ɂ��U�蕪�����s���ꍇ��
 *      �g�p���鑮���Btrue���w�肷���event�����ɐݒ肳�ꂽ�l��Hidden�^�O���o�͂���B
 *      �܂��A����Hidden�^�O��value������"forward_pageLinks"�Ƃ���B
 *      �f�t�H���g��false�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>event</code></td>
 *    <td><code>"event"</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      TERASOLUNA��DispatchAction���g�p���ăt�H���[�h�ɂ��U�蕪�����s���ꍇ��
 *      �g�p���鑮���Bforward������true�ɂ����ꍇ�A���̑����Ɏw�肵�����O��
 *      Hidden�^�O�����������B�f�t�H���g��"event"�ƂȂ�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>resetIndex</code></td>
 *    <td><code>false</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �T�u�~�b�g���ɗL���ɂȂ鑮���ŁAtrue�ɐݒ肷���
 *      �w��͈̓��Z�b�g���s�����߂�startIndex��endIndex��Hidden�^�O���o�͂���B
 *      �f�t�H���g��false�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>currentPageIndex</code></td>
 *    <td><code>"currentPageIndex"</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �Ή�����ꗗ�̌��݃y�[�W�����y�[�W�R���e�L�X�g�ɕۑ�����ۂ̃L�[�ƂȂ�B
 *      �f�t�H���g��"currentPageIndex"�ƂȂ�B
 *    </td>
 *   </tr>
 *   <tr>
 *    <td><code>totalPageCount</code></td>
 *    <td><code>"totalPageCount"</code></td>
 *    <td><code>false</code></td>
 *    <td><code>true</code></td>
 *    <td align="left">
 *      �Ή�����ꗗ�̑��y�[�W�����y�[�W�R���e�L�X�g�ɕۑ�����ۂ̃L�[�ƂȂ�B
 *      �f�t�H���g��"totalPageCount"�ƂȂ�B
 *    </td>
 *   </tr>
 *  </table>
 * </div>
 * <br/>
 *
 * <br/>
 *
 * <strong>�J�X�^���^�O�̃X�N���v�e�B���O�ϐ�</strong>
 * <p>
 *  <code>PageLinksTag</code> �ł͈ȉ��̕ϐ����T�|�[�g����B
 * </p>
 * <div align="center">
 * <table width="90%" border="1">
 *  <tr>
 *   <td><b>�ϐ���</b></td>
 *   <td><b>�^</b></td>
 *   <td><b>�L���͈�</b></td>
 *   <td><b>�L�q</b></td>
 *  </tr>
 *  <tr>
 *   <td>
 *     �J�X�^���^�O��<code>id</code>�����Ŏw�肳�ꂽ���O�B
 *   </td>
 *   <td><code>java.lang.String</code></td>
 *   <td>�J�X�^���^�O�ȍ~</td>
 *   <td align="left">
 *    ��ʂ֏o�͂���y�[�W�����N(<a>�^�O�Ȃ�)��ێ�����B
 *   </td>
 *  </tr>
 *  <tr>
 *   <td>
 *     �J�X�^���^�O��<code>currentPageIndex</code>�����Ŏw�肳�ꂽ���O�A
 *     �܂��̓f�t�H���g�l�B
 *   </td>
 *   <td><code>java.lang.Integer</code></td>
 *   <td>�J�X�^���^�O�ȍ~</td>
 *   <td align="left">
 *    �Ή�����ꗗ���̌��݂̃y�[�W�ԍ���ێ�����B
 *   </td>
 *  </tr>
 *  <tr>
 *   <td>
 *     �J�X�^���^�O��<code>totalPageCount</code>�����Ŏw�肳�ꂽ���O�A
 *     �܂��̓f�t�H���g�l�B
 *   </td>
 *   <td><code>java.lang.Integer</code></td>
 *   <td>�J�X�^���^�O�ȍ~</td>
 *   <td align="left">
 *    �Ή�����ꗗ���̑��y�[�W����ێ�����B
 *   </td>
 *  </tr>
 * </table>
 * </div>
 *
 * <br/>
 *
 * <strong>�g�p���@</strong>
 * <li>�v���p�e�B�t�@�C���̐ݒ�</li>
 * <p/>
 * ��ʂɏo�͂��郊���N�̐ݒ�̓v���p�e�B�t�@�C���ɋL�q���s���B
 * �L�q����̂́A���݂̃y�[�W����W�����v�y�[�W���ƁA�\���L���̐ݒ�ł���B
 * �Ȃ��A�����N�͈ȉ��̏����ɂ����Ă���Ε����L�q���邱�Ƃ��\�ł���B
 * <p/>
 * <br/>
 * <li>�v���p�e�B�t�@�C���ݒ�̏������</li>
 * <p>
 * &nbsp;&nbsp;<code><b>pageLinks.<i>&lt;�J�ڕ���&gt;</i><i>&lt;�J��
 * �y�[�W��&gt;</i>.char=<i>&lt;�\��������&gt;</i></b></code><br/>
 * &nbsp;&nbsp;<i>�J�ڕ���</i>&nbsp�F
 * ���݂̕\���y�[�W�ԍ������O���i<i>next</i>�̏ꍇ�͌���j�B<br/>
 * &nbsp;&nbsp;<i>�J�ڃy�[�W��</i>&nbsp;�F
 * ���݂̕\���y�[�W�ԍ�����̃W�����v�y�[�W���B���l�͎��R�ɐݒ�\�B<br/>
 * &nbsp;&nbsp;<i>�\��������</i>&nbsp;�F
 * ��ʂɕ\�������镶���B���̕����������N�ΏۂƂȂ�B
 * </p>
 * <br/>
 * <p>
 * &nbsp;&nbsp;<b><code>pageLinks.maxDspLinkSize =
 * <i>&lt;�\���y�[�W��&gt;</i></code></b><br/>
 * &nbsp;&nbsp;<i>�\���y�[�W��</i>&nbsp;�F
 * �y�[�W���𒼐ڎw�肷�郊���N�̍ő�\����
 * </p>
 * <p/>
 * <br/>
 * <li>�v���p�e�B�t�@�C���ݒ��</li>
 * <P>
 * <code>
 * &nbsp;&nbsp;pageLinks.prev10.char=&amp;lt;&amp;lt;<br/>
 * &nbsp;&nbsp;pageLinks.prev1.char=&amp;lt;<br/>
 * &nbsp;&nbsp;pageLinks.next1.char=&amp;gt;<br/>
 * &nbsp;&nbsp;pageLinks.next10.char=&amp;gt;&amp;gt;<br/>
 * &nbsp;&nbsp;pageLinks.maxDirectLinkCount=10
 * </code>
 * </P>
 * <p/>
 * <br/>
 * <br/>
 * <li>�ꗗ�����y�[�W�P�ʂɃf�[�^�x�[�X����擾����g�p��</li>
 * <p>
 * �ȉ��ɁA�y�[�W�����N�@�\���g�p������������B���̗�́A�y�[�W��؂�ւ��邲�Ƃ�
 * �f�[�^�x�[�X�ɃA�N�Z�X���s���\������ꗗ���݂̂��擾�����ł���B
 * ���̗�̏ꍇ�́A�ꗗ�\����ʂ֍ŏ��ɑJ�ڂ���A�N�V�����ƁA
 * �y�[�W�����N�@�\�̃A�N�V����(action����)������ł����Ȃ��B
 * �ȉ��̗�ł́A�y�[�W�����N�ɐݒ肷��t�B�[���h��String���g�p���Ă��邪�A
 * String�ȊO�̃I�u�W�F�N�g�̐ݒ���\�ł���B
 * </p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Struts�ݒ�t�@�C���̗�
 * </legend>
 * <p/>
 * <code>
 * &lt;form-beans&gt;<br/>
 * &nbsp;&nbsp;&lt;form-bean name="dynaFormBean"<br/>
 * &nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.action.DynaActionForm" &gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- �擾�����ꗗ����ێ�����t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>userBeans</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.blogic.UserBean[]"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- 1�y�[�W�ɕ\�����錏����ێ������t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>row</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="10"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- �\������y�[�W�̊J�n�C���f�b�N�X��ێ������t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>startIndex</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="0"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- �ꗗ���̑S������ێ������t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>totalCount</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type="java.lang.String"/&gt;<br/>
 * &nbsp;&nbsp;&lt;/form-bean&gt;<br/>
 * &lt;/form-beans&gt;<br/>
 * <br/>
 * &lt;!-- �y�[�W�P�ʂ̈ꗗ�����擾����A�N�V���� --&gt;<br/>
 * &lt;action path="<b>/list</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.action.ListAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * name="dynaFormBean" scope="session"&gt;<br/>
 * &nbsp;&nbsp;&lt;forward name="success" path="/listSRC.do"/&gt;<br/>
 * &lt;/action&gt;<br/>
 * &lt;action path="/listSRC"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.actions.ForwardAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parameter="/daoTestList.jsp"&gt;<br/>
 * &lt;/action&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSP�̗�
 * </legend>
 * <p/>
 * <code>
 * <b>&lt;ts:pageLinks action="/list"
 * name="dynaFormBean" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;</b><br/>
 * &lt;table border="1" frame="box"&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;logic:iterate id="userBean"
 * name="dynaFormBean" property="userBeans"&gt;</b><br/>
 * &nbsp;&nbsp;&lt;tr&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="id"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="name"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="age"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param1"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param2"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param3"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param4"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param5"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param6"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param7"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&lt;/tr&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;/logic:iterate&gt;</b><br/>
 * &lt;/table&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * �T�[�r�X�w�̗�
 * </legend>
 * <p/>
 * <code>
 * DynaActionForm dynaForm = (DynaActionForm) form;<br/>
 * 
 * //�\�������̎擾<br/>
 * String strRow = (String) dynaForm.get("<b>row</b>");<br/>
 * //�J�n�s�C���f�b�N�X�̎擾<br/>
 * String strIndex = (String) dynaForm.get("<b>startIndex</b>");<br/>
 * int row = 10;<br/>
 * int startIndex = 0;<br/>
 * <br/>
 * //int�ւ̕ϊ�����<br/>
 * ......<br/>
 * <br/>
 * //�S�̌����擾<br/>
 * String totalCount<br/>
 *     = <b>dao.executeForObject("getUserCount", null, String.class);</b><br/>
 * <br/>
 * //�ꗗ���擾<br/>
 * UserBean[] bean = <b>dao.executeForObjectList("getUserList", null,
 * UserBean.class, startIndex, row);</b><br/>
 * <br/>
 * //�A�N�V�����t�H�[���ւ̐ݒ�<br/>
 * dynaForm.set("<b>totalCount</b>", totalCount);<br/>
 * dynaForm.set("<b>userBeans</b>", bean);
 * </code>
 * </fieldset>
 * <p/>
 * <br/>
 * <br/>
 * <li>�ꗗ�����A�N�V�����t�H�[������擾����g�p��</li>
 * <p>
 * �ȉ��ɁA�y�[�W�����N�@�\���g�p������������B���̗�́A�y�[�W��؂�ւ���Ƃ���
 * ���łɎ擾�ς݂̈ꗗ���(�S����)���A�N�V�����t�H�[������擾�����ł���B
 * ���̗�̏ꍇ�́A�ꗗ�\����ʂ֍ŏ��ɑJ�ڂ���A�N�V�����ƁA
 * �y�[�W�����N�@�\�̃A�N�V����(action����)�͕ʂ̃A�N�V�����Ƃ���B
 * �ꗗ�\����ʂ֍ŏ��ɑJ�ڂ���A�N�V�����ł́A�ꗗ���̑S�������擾���A
 * �y�[�W�����N�@�\�̃A�N�V�����͂����܂ŉ�ʂ�\������݂̂̃A�N�V�����Ƃ���B
 * �ȉ��̗�ł́A�y�[�W�����N�ɐݒ肷��t�B�[���h��String���g�p���Ă��邪�A
 * String�ȊO�̃I�u�W�F�N�g�̐ݒ���\�ł���B
 * </p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Struts�ݒ�t�@�C���̗�
 * </legend>
 * <p/>
 * <code>
 * &lt;form-beans&gt;<br/>
 * &nbsp;&nbsp;&lt;form-bean name="dynaFormBean"<br/>
 * &nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.action.DynaActionForm" &gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- �擾�����ꗗ����ێ�����t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>userBeans</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.blogic.UserBean[]"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- 1�y�[�W�ɕ\�����錏����ێ������t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>row</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="10"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;
 * &lt;!-- �\������y�[�W�̊J�n�C���f�b�N�X��ێ������t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>startIndex</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="java.lang.String" initial="0"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- �ꗗ���̑S������ێ������t�B�[���h --&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;form-property name="<b>totalCount</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;type="java.lang.String"/&gt;<br/>
 * &nbsp;&nbsp;&lt;/form-bean&gt;<br/>
 * &lt;/form-beans&gt;<br/>
 * <br/>
 * &lt;!-- �ꗗ��񂷂ׂĂ��擾����A�N�V���� --&gt;<br/>
 * &lt;action path="/list"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="jp.terasoluna.strutspring.action.ListAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * name="dynaFormBean" scope="session"&gt;<br/>
 * &nbsp;&nbsp;&lt;forward name="success" path="/listSRC.do"/&gt;<br/>
 * &lt;/action&gt;<br/>
 * &lt;!-- ��ʕ\���݂̂̃A�N�V���� --&gt;<br/>
 * &lt;action path="<b>/listSRC</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * type="org.apache.struts.actions.ForwardAction"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parameter="/daoTestList.jsp"&gt;<br/>
 * &lt;/action&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSP�̗�
 * </legend>
 * <p/>
 * <code>
 * <b>&lt;ts:pageLinks action="/listSRC"
 * name="dynaFormBean" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;</b><br/>
 * &lt;table border="1" frame="box"&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;bean:define id="startIndex" name="dynaFormBean"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;property="startIndex"
 * type="java.lang.String" /&gt;<br/>
 * &nbsp;&nbsp;&lt;logic:iterate id="userBean"
 * name="dynaFormBean" length="10"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;property="userBeans"
 * offset="&lt;%=startIndex%&gt;"&gt;</b><br/>
 * &nbsp;&nbsp;&lt;tr&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="id"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="name"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="age"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param1"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param2"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param3"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param4"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param5"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param6"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;bean:write
 * name="userBean" property="param7"/&gt;&lt;/td&gt;<br/>
 * &nbsp;&nbsp;&lt;/tr&gt;<br/>
 * &nbsp;&nbsp;<b>&lt;/logic:iterate&gt;</b><br/>
 * &lt;/table&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * �T�[�r�X�w�̗�i�ŏ��̈ꗗ��ʕ\���̂݁j
 * </legend>
 * <p/>
 * <code>
 * DynaActionForm dynaForm = (DynaActionForm) form;<br/>
 * <br/>
 * //�S�̌����擾<br/>
 * String totalCount<br/>
 *     = <b>dao.executeForObject("getUserCount", null, String.class);</b><br/>
 * <br/>
 * //�ꗗ���擾<br/>
 * UserBean[] bean = <b>dao.executeForObjectList("getUserList", null,
 * UserBean.class);</b><br/>
 * <br/>
 * //�A�N�V�����t�H�[���ւ̐ݒ�<br/>
 * dynaForm.set("<b>totalCount</b>", totalCount);<br/>
 * dynaForm.set("<b>userBeans</b>", bean);
 * </code>
 * </fieldset>
 * <p/>
 * <br/><br/>
 * <li>�T�u�~�b�g���s�������ꍇ�̎g�p��</li>
 * <p/>
 * �y�[�W�����N�@�\�́A�f�t�H���g�ł�&lt;a&gt;���g�p���āA�w�肳�ꂽ�A�N�V�����ւ�
 * �����N���쐬���邽�߁A�T�u�~�b�g���s��Ȃ��B
 * �T�u�~�b�g���s���ꍇ�́A<code>submit</code>������<code>true</code>�ɐݒ肷��B
 * ���̐ݒ���s���ƁA�y�[�W�����N�������ɁAJavaScript�ɂ��T�u�~�b�g�������s���B
 * �Ȃ��A<code>submit</code>������<code>true</code>�ɐݒ肷���
 * <code>action</code>�����͖����ƂȂ�B
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSP�̗�i�ꕔ�j
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks <b>submit="true"</b>
 * name="dynaFormBean" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;<br/>
 * </code>
 * </fieldset>
 * <p/>
 * <br/><br/>
 * <li>DispatchAction���g�p���ăt�H���[�h���s���ꍇ�̎g�p��</li>
 * <p/>
 * TERASOLUNA��DispatchAction���g�p���ăt�H���[�h���s���ꍇ�́A
 * forward������"true"�ɂ���K�v������Bforward������"true"�Ƃ����ꍇ�A��ʂ�
 * �u&lt;input type="hidden" name="event" value="forward_pageLinks" /&gt;�v��
 * �o�͂���B�܂��A�o�͂���Hidden�^�O��name�����̒l��event�����Ɏw�肳�ꂽ�l�ƂȂ�B
 * �f�t�H���g��"event"�ł��邽�߁A�w�肵�Ȃ��ꍇ�͏�L��Hidden�^�O���o�͂����B
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSP�̗�i�ꕔ�j
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks submit="true" name="dynaFormBean"
 * rowProperty="row"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * totalProperty="totalCount"
 * indexProperty="startIndex"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * <b>forward="true" event="forwardParameter" /&gt;</b><br/>
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * ��L��JSP�ɂ���ďo�͂��ꂽHTML�̗�iHidden�^�O�j
 * </legend>
 * <p/>
 * <code>
 * &lt;input type="hidden" name="<b>forwardParameter</b>"
 * value="<b>forward_pageLinks</b>" /&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Struts�ݒ�t�@�C���̗�
 * </legend>
 * <p/>
 * <code>
 * &lt;action path="/list"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * name="dynaFormBean" scope="request"&gt;<br>
 * &nbsp;&nbsp;&nbsp;
 * &lt;forward name="<b>pageLinks</b>" path="/pageLinks.do" /&gt;<br>
 * &nbsp;&nbsp;&nbsp;
 * &lt;forward name="regist"&nbsp;path="/regist.do"&nbsp;/&gt;<br>
 * &lt;/action&gt;<br>
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Bean��`�t�@�C���̗�
 * </legend>
 * <p/>
 * <code>
 * &lt;bean name="/list"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * class="jp.terasoluna.fw.web.struts.actions.DispatchAction"&gt;<br>
 * &nbsp;&nbsp;&nbsp;
 * &lt;property name="event"&gt;
 * &lt;value&gt;forwardParameter&lt;/value&gt;
 * &lt;/property&gt;<br>
 * &lt;/bean&gt;<br>
 * </code>
 * </fieldset>
 * <p/>

 * <br/><br/>
 * <li>���݃y�[�W���A���y�[�W���̏o��</li>
 * <p/>
 * �y�[�W�����N�@�\�ł́A���݃y�[�W���Ƒ��y�[�W����<code>pageContext</code>��
 * �ۑ�����B�ۑ�����L�[�́A<code>currentPageIndex</code>������
 * <code>totalPageCount</code>�����ɂĎw��\�ł���B
 * �����̃f�t�H���g�l�͏�L���Q�Ƃ̂��ƁB
 * ���݃y�[�W����ё��y�[�W������ʂɕ\������ꍇ�́A�ȉ��̂悤�Ɏg�p����B
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSP�̗�i�ꕔ�j
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks action="/pageLink" rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex" /&gt;<br/>
 * ���<br>
 * ���݂́m&lt;bean:write name="<b>currentPageIndex</b>"/&gt;�n�y�[�W�ł��B<br>
 * �S���Łm&lt;bean:write name="<b>totalPageCount</b>"/&gt;�n�y�[�W����܂��B
 * </code>
 * </fieldset>
 * <br/><br/>
 * <li>id�����̎g�p��</li>
 * <p/>
 * id�������w�肷��ƁA�y�[�W�����N����ʂɏo�͂����ɁA�w�肳�ꂽ��������L�[��
 * <code>pageContext</code>�ɕۑ����邽�߁A�o�͏ꏊ�����R�ɕύX���邱�Ƃ��ł���B
 * ���݃y�[�W�⑍�y�[�W�����y�[�W�����N�̏㕔�ɏo�͂������Ƃ��ȂǂɎg�p����B
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * JSP�̗�i�ꕔ�j
 * </legend>
 * <p/>
 * <code>
 * &lt;ts:pageLinks action="/pageLink" <b>id="reservePageLinks"</b>
 * rowProperty="row"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;totalProperty="totalCount"
 * indexProperty="startIndex"<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;currentPageIndex="reserveCurrentPageIndex"
 * totalPageCount="reserveTotalPageCount" /&gt;<br/>
 * ���<br>
 * ���݂́m&lt;bean:write name="reserveCurrentPageIndex"/&gt;�n�y�[�W�ł��B<br>
 * �S���Łm&lt;bean:write name="reserveTotalPageCount"/&gt;�n�y�[�W����܂��B<br>
 * &lt;bean:write name="<b>reservePageLinks</b>"/&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <br/>
 * <br/>
 *
 */
public class PageLinksTag extends TagSupport {

    /**
     * �V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 9017738370826462823L;

    /**
     * ���O�N���X�B
     */
    private static Log log =
         LogFactory.getLog(PageLinksTag.class);

    /**
     * �o�͐�ύX�pID�B
     */
    protected String id = null;

    /**
     * �y�[�W�����N�������ɋN������A�N�V�������B
     */
    protected String action = null;

    /**
     * �\���J�n�C���f�b�N�X�ƑS������ێ�����Bean���B
     */
    protected String name = null;

    /**
     * �\���s���̃t�B�[���h���B
     */
    protected String rowProperty = null;

    /**
     * �\���J�n�C���f�b�N�X�̃t�B�[���h���B
     */
    protected String indexProperty = null;

    /**
     * �S�����̃t�B�[���h���B
     */
    protected String totalProperty = null;

    /**
     * �擾����Bean�̃X�R�[�v�B
     */
    protected String scope = null;

    /**
     * �T�u�~�b�g�t���O�B
     */
    protected boolean submit = false;

    /**
     * �t�H���[�h�t���O�B
     */
    protected boolean forward = false;

    /**
     * �C�x���g�p�����[�^�B
     */
    protected String event = DEFAULT_EVENT;

    /**
     * �w��͈̓C���f�b�N�X�o�̓t���O�B
     */
    protected boolean resetIndex = false;

    /**
     * ���݃y�[�W�ԍ��ۑ��p�p�����[�^�B
     */
    protected String currentPageIndex = CURRENT_PAGE_INDEX;

    /**
     * ���݃y�[�W�ԍ��ۑ��p�p�����[�^�B
     */
    protected String totalPageCount = TOTAL_PAGE_COUNT;

    /**
     * �ݒ肳��Ă���id�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * id�����ɒl��ݒ肷��B
     * @param id �ݒ肷��l
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * �ݒ肳��Ă���action�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getAction() {
        return this.action;
    }

    /**
     * action�����ɒl��ݒ肷��B
     * @param action �ݒ肷��l
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * �ݒ肳��Ă���id�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getName() {
        return name;
    }

    /**
     * name�����ɒl��ݒ肷��B
     * @param name �ݒ肷��l
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �ݒ肳��Ă���rowProperty�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getRowProperty() {
        return rowProperty;
    }

    /**
     * rowProperty�����ɒl��ݒ肷��B
     * @param rowProperty �ݒ肷��l
     */
    public void setRowProperty(String rowProperty) {
        this.rowProperty = rowProperty;
    }

    /**
     * �ݒ肳��Ă���indexProperty�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getIndexProperty() {
        return indexProperty;
    }

    /**
     * indexProperty�����ɒl��ݒ肷��B
     * @param indexProperty �ݒ肷��l
     */
    public void setIndexProperty(String indexProperty) {
        this.indexProperty = indexProperty;
    }

    /**
     * �ݒ肳��Ă���totalProperty�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getTotalProperty() {
        return totalProperty;
    }

    /**
     * totalProperty�����ɒl��ݒ肷��B
     * @param totalProperty �ݒ肷��l
     */
    public void setTotalProperty(String totalProperty) {
        this.totalProperty = totalProperty;
    }

    /**
     * �ݒ肳��Ă���scope�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getScope() {
        return scope;
    }

    /**
     * scope�����ɒl��ݒ肷��B
     * @param scope �ݒ肷��l
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * �ݒ肳��Ă���submit�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public boolean getSubmit() {
        return submit;
    }

    /**
     * submit�����ɒl��ݒ肷��B
     * @param submit �ݒ肷��l
     */
    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

    /**
     * �ݒ肳��Ă���forward�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public boolean getForward() {
        return forward;
    }

    /**
     * forward�����ɒl��ݒ肷��B
     * @param forward �ݒ肷��l
     */
    public void setForward(boolean forward) {
        this.forward = forward;
    }

    /**
     * �ݒ肳��Ă���event�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getEvent() {
        return this.event;
    }

    /**
     * event�����ɒl��ݒ肷��B
     * @param event �ݒ肷��l
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * �ݒ肳��Ă���resetIndex�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public boolean getResetIndex() {
        return resetIndex;
    }

    /**
     * resetIndex�����ɒl��ݒ肷��B
     * @param resetIndex �ݒ肷��l
     */
    public void setResetIndex(boolean resetIndex) {
        this.resetIndex = resetIndex;
    }

    /**
     * �ݒ肳��Ă���currentPageIndex�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getCurrentPageIndex() {
        return this.currentPageIndex;
    }

    /**
     * currentPageIndex�����ɒl��ݒ肷��B
     * @param currentPageIndex �ݒ肷��l
     */
    public void setCurrentPageIndex(String currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    /**
     * �ݒ肳��Ă���totalPageCount�����l��ԋp����B
     * @return �ݒ肳��Ă���l
     */
    public String getTotalPageCount() {
        return this.totalPageCount;
    }

    /**
     * totalPageCount�����ɒl��ݒ肷��B
     * @param totalPageCount �ݒ肷��l
     */
    public void setTotalPageCount(String totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    /**
     * �A�N�V�����������K�{�̃G���[���b�Z�[�W�B
     */
    protected static String ERROR_MESSAGE_ACTION_REQUIRED =
    "Action attribute is required when submit attribute is \"false\".";

    /**
     * �擾�����\���s��(row)��0�ȉ��̏ꍇ�̃G���[���b�Z�[�W�B
     */
    protected static String WARN_MESSAGE_ILLEGAL_ROW =
    "Row param is illegal.";

    /**
     * <code>ApplicationResources</code> �t�@�C���ɂ�����
     * �y�[�W�W�����v�p�����N�̃v���p�e�B���̃v���t�B�b�N�X�B
     */
    protected static String PAGE_LINKS_PREFIX = "pageLinks.";

    /**
     * <code>ApplicationResources</code> �t�@�C���ɂ�����O���ւ�
     * �y�[�W�W�����v�p�����N�̃v���p�e�B���̍\���v�f�B
     */
    protected static String PREV_LINKS = "prev";

    /**
     * <code>ApplicationResources</code> �t�@�C���ɂ��������ւ�
     * �y�[�W�W�����v�p�����N�̃v���p�e�B���̍\���v�f�B
     */
    protected static String NEXT_LINKS = "next";

    /**
     * <code>ApplicationResources</code> �t�@�C���ɂ�����L���\����
     * �y�[�W�W�����v�p�����N�̃v���p�e�B���̍\���v�f�B
     */
    protected static String CHAR_LINKS = ".char";

    /**
     * <code>ApplicationResources</code> �t�@�C���ɂ�����
     * �y�[�W�W�����v�p�����N�̒��ڔԍ��w��̕\���ő吔��
     * �v���p�e�B���̍\���v�f�B
     */
    protected static String MAX_DSP_SIZE = "maxDirectLinkCount";

    /**
     * �y�[�W�����N�@�\�ŏo�͂���JavaScript�̏o�̓t���O
     */
    protected static String PAGELINKS_JAVASCRIPT_KEY
        = "pageLinksJavaScriptKey";

    /**
     * �t�H���[�h���B
     */
    protected static String FORWARD_NAME = "forward_pageLinks";

    /**
     * �f�t�H���g�C�x���g�p�����[�^�B
     */
    protected static String DEFAULT_EVENT = "event";

    /**
     * ���y�[�W�����y�[�W�R���e�L�X�g�ɓo�^����L�[�B
     */
    protected static String TOTAL_PAGE_COUNT = "totalPageCount";

    /**
     * ���݃y�[�W�����y�[�W�R���e�L�X�g�ɓo�^����L�[�B
     */
    protected static String CURRENT_PAGE_INDEX = "currentPageIndex";

    /**
     * �v���p�e�B�t�@�C���̃����N�o�^ID���L�[�Ƃ��ĕ\�������N���i�[����}�b�v�B
     */
    protected Map<String, String> links = new HashMap<String, String>();

    /**
     * �ő�y�[�W�W�����v���B
     */
    protected int maxLinkNo = 1;

    /**
     * �ő咼�ڎw�胊���N�ԍ����B
     */
    protected int maxPageCount = 10;

    /**
     * �^�O�]���J�n���ɌĂ΂�郁�\�b�h�B
     *
     * @return ��������w��
     * @throws JspException JSP��O
     */
    @Override
    public int doStartTag() throws JspException {

        //�����`�F�b�N
        if (!submit && (action == null || "".equals(action))) {
            log.error(ERROR_MESSAGE_ACTION_REQUIRED);
            throw new JspException(ERROR_MESSAGE_ACTION_REQUIRED);
        }

        // �v���p�e�B�t�@�C�����y�[�W�W�����v�p�����N�^�O���擾
        getLinkProperty();

        //�\���s�����擾
        Object objRow = lookup(pageContext, name, rowProperty, scope);
        int row = getInt(objRow);

        //�擾�����\���s����0�ȉ��̏ꍇ�͏������I������B
        if (row <= 0) {
            if (log.isWarnEnabled()) {
                log.warn(WARN_MESSAGE_ILLEGAL_ROW);
            }
            return EVAL_BODY_INCLUDE;
        }

        //�J�n�s�C���f�b�N�X���擾
        Object objIndex = lookup(pageContext, name, indexProperty, scope);
        int startIndex = getInt(objIndex);

        //�S�������擾
        Object objTotal = lookup(pageContext, name, totalProperty, scope);
        int totalCount = getInt(objTotal);

        //StringBuilder�̐���
        StringBuilder sb = new StringBuilder();

        //���݃y�[�W���A���y�[�W����ݒ肷��B
        attributePageCount(
                getPageIndex(row, startIndex), getPageCount(row, totalCount));

        if (submit) {
            //submit������true�̂Ƃ��́A�T�u�~�b�g���s���y�[�W�����N���o�͂���B

            //�\���s���A�J�n�C���f�b�N�X�̃^�O���o��
            defineHtml(row, startIndex, totalCount);

            //�O�y�[�W�����N�̐ݒ�
            addPrevSubmit(sb, row, startIndex, totalCount);

            //�y�[�W�ԍ������N�̐ݒ�
            addDirectSubmit(sb, row, startIndex, totalCount);

            //���y�[�W�����N�̐ݒ�
            addNextSubmit(sb, row, startIndex, totalCount);

        } else {
            //submit������false�̏ꍇ

            //�O�y�[�W�����N�̐ݒ�
            addPrevLink(sb, row, startIndex, totalCount);

            //�y�[�W�ԍ������N�̐ݒ�
            addDirectLink(sb, row, startIndex, totalCount);

            //���y�[�W�����N�̐ݒ�
            addNextLink(sb, row, startIndex, totalCount);
        }

        //StringBuilder�ɂ��߂����e���o�͂���B
        if (id == null || "".equals(id)) {
            try {
                JspWriter writer = pageContext.getOut();
                writer.println(sb.toString());
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new JspTagException(e.toString());
            }
        } else {
            pageContext.setAttribute(id, sb.toString());
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * HTML�̒�`���o�͂���B
     * 
     * @param row �\���s��
     * @param startIndex �\���J�n�C���f�b�N�X
     * @param totalCount �S����
     * @throws JspException JSP��O
     */
    protected void defineHtml(int row, int startIndex, int totalCount)
        throws JspException {

        JspWriter writer = pageContext.getOut();
        try {

            //�������O��Hidden�^�O���o�͂��Ȃ����߁A�t���O���m���߂�B
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + rowProperty)) {

                //�\��������Hidden�^�O��ǉ�
                writer.println("<input type=\"hidden\" name=\""
                        + rowProperty + "\" value=\"" + row + "\"/>");

                //���o�͂�����t���O�𗧂Ă�B
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + rowProperty);
            }

            //�������O��Hidden�^�O���o�͂��Ȃ����߁A�t���O���m���߂�B
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + indexProperty)) {

                //�\���J�n�C���f�b�N�X��Hidden�^�O��ǉ�
                writer.println("<input type=\"hidden\" name=\""
                        + indexProperty + "\" value=\"" + startIndex + "\"/>");

                //���o�͂�����t���O�𗧂Ă�B
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + indexProperty);
            }

            //�������O��Hidden�^�O���o�͂��Ȃ����߁A�t���O���m���߂�B
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + event) && forward) {

                //�\���J�n�C���f�b�N�X��Hidden�^�O��ǉ�
                writer.println("<input type=\"hidden\" name=\"" + event
                        + "\" value=\"\"/>");

                //���o�͂�����t���O�𗧂Ă�B
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + event);
            }

            //�������O��Hidden�^�O���o�͂��Ȃ����߁A�t���O���m���߂�B
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                    + "resetIndex") && resetIndex) {

                //startIndex��Hidden�^�O��ǉ�
                if (!"startIndex".equals(indexProperty)) {
                    writer.println("<input type=\"hidden\" name=\"" +
                            "startIndex\" value=\"" + startIndex + "\"/>");
                }

                //endIndex��Hidden�^�O��ǉ�
                int endIndex = startIndex + row - 1;
                if (endIndex >= totalCount) {
                    writer.println("<input type=\"hidden\" name=\"" +
                            "endIndex\" value=\"" + (totalCount - 1) + "\"/>");
                } else {
                    writer.println("<input type=\"hidden\" name=\"" +
                            "endIndex\" value=\"" + endIndex + "\"/>");
                }

                //���o�͂�����t���O�𗧂Ă�B
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY
                        + "resetIndex");
            }

            //�t�H�[�������擾
            String formName = ActionFormUtil
                    .getActionFormName((HttpServletRequest) pageContext
                            .getRequest());

            //�T�u�~�b�g���s��JavaScript��ǉ�����B
            //�������A�y�[�W�����N�^�O�������L�q����Ă���ꍇ�͂P��̂�
            if (!getPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY)) {
                writer.println("<script type=\"text/javascript\">");
                writer.println("<!--");
                writer.println("  function pageLinkSubmit(rowObj, indexObj,"
                        + " row, startIndex){");
                writer.println("    rowObj.value = row;");
                writer.println("    indexObj.value = startIndex;");

                //forward������true�̏ꍇ�́Aevent������Hidden�^�O��
                //�p�����[�^��ݒ肷��B
                if (forward) {
                    writer.print("    document.");
                    writer.print(formName);
                    writer.print(".");
                    writer.print(event);
                    writer.print(".value = \"");
                    writer.print(FORWARD_NAME);
                    writer.println("\";");
                }

                writer.print("    document.");
                writer.print(formName);
                writer.println(".submit();");
                writer.println("  }");
                writer.println("// -->");
                writer.println("</script>");

                //���o�͂�����t���O�𗧂Ă�B
                setPageContextFlg(pageContext, PAGELINKS_JAVASCRIPT_KEY);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new JspTagException(e.toString());
        }
    }

    /**
     * �O�y�[�W�ɑJ�ڂ��郊���N��������StringBuilder�ɒǉ�����B
     * 
     * @param sb �ǉ��Ώۂ�StringBuilder
     * @param row �\���s��
     * @param startIndex �\���J�n�C���f�b�N�X
     * @param totalCount �ꗗ���̑S����
     */
    protected void addPrevSubmit(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //�t�H�[�������擾
        String formName = ActionFormUtil
                .getActionFormName((HttpServletRequest) pageContext
                        .getRequest());

        //�O�y�[�W�����N�̐���
        for (int i = maxLinkNo; i > 0; i--) {
            String linkKey = PREV_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);
            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }
            int index = startIndex - (i * row);
            if (index < 0) {
                sb.append(linkValue + "&nbsp;");
            } else {
                sb.append("<a href=\"#\" onclick=\"pageLinkSubmit(");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(rowProperty);
                sb.append(",");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(indexProperty);
                sb.append(",");
                sb.append(row);
                sb.append(",");
                sb.append(index);
                sb.append(")\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * �y�[�W�ԍ������N��������StringBuilder�ɒǉ�����B
     * 
     * @param sb �ǉ��Ώۂ�StringBuilder
     * @param row �\���s��
     * @param startIndex �\���J�n�C���f�b�N�X
     * @param totalCount �ꗗ���̑S����
     */
    protected void addDirectSubmit(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //�t�H�[�������擾
        String formName = ActionFormUtil
                .getActionFormName((HttpServletRequest) pageContext
                        .getRequest());

        //�y�[�W�W�����v�p�����N�̒��ڔԍ��w��̕\���ő吔���擾
        String directLinkNo = links.get(MAX_DSP_SIZE);
        if (directLinkNo != null) {
            try {
                maxPageCount = Integer.parseInt(directLinkNo);
            } catch (NumberFormatException e) {
                // NumberFormatException�����������ꍇ�A
                // ���̃v���p�e�B�͖�������maxDirectLinkCount�ɂ�
                // �f�t�H���g�l���g�p�����
            }
        }

        //�S�y�[�W�����擾����B
        int pageCount = getPageCount(row, totalCount);

        //���݂̃y�[�W�C���f�b�N�X���擾����B
        int pageIndex = getPageIndex(row, startIndex);

        //�\���ŏI�y�[�W����ѕ\���J�n�y�[�W
        int startPage = 0;
        int endPage = 0;

        //�S�y�[�W�����A�y�[�W�ԍ������N�̕\�������傫���A
        //���A�\������y�[�W�C���f�b�N�X���A�y�[�W�ԍ������N�̕\�����̔������
        //�傫���ꍇ�́A�\���J�n�y�[�W�C���f�b�N�X���A�\������y�[�W�C���f�b�N�X��
        //���킹�ĕϓ�������B
        //��Ƃ��āA�S�y�[�W���F�P�O�y�[�W�A�y�[�W�ԍ������N�̕\�����F�T�A
        //�\������y�[�W�C���f�b�N�X�F�T�̏ꍇ�AstartPage���Q�ƂȂ�
        //endPage���T�ƂȂ�B���̏ꍇ�A��ʂɕ\�����郊���N�́u3 4 5 6 7�v�ƂȂ�B
        if (pageCount > maxPageCount && pageIndex > (maxPageCount / 2)) {

            //�\���ŏI�y�[�W���ő�y�[�W���Ƃ���B
            endPage = maxPageCount;

            startPage = (pageIndex - (endPage / 2)) - 1;
            if (startPage + endPage > pageCount) {
                startPage = pageCount - endPage;
            }
        } else {
            endPage = pageCount < maxPageCount ? pageCount : maxPageCount;
            startPage = 0;
        }

        //�y�[�W�ԍ������N�̐������[�v
        int size = startPage + endPage;
        for (int i = startPage; i < size; i++) {
            int idx = i + 1;
            if (pageIndex == idx) {
                sb.append("<b>");
                sb.append(idx);
                sb.append("</b>&nbsp;");
            } else {
                sb.append("<a href=\"#\" onclick=\"pageLinkSubmit(");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(rowProperty);
                sb.append(",");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(indexProperty);
                sb.append(",");
                sb.append(row);
                sb.append(",");
                sb.append(i * row);
                sb.append(")\">");
                sb.append(idx);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * ���y�[�W�ɑJ�ڂ��郊���N��������StringBuilder�ɒǉ�����B
     * 
     * @param sb �ǉ��Ώۂ�StringBuilder
     * @param row �\���s��
     * @param startIndex �\���J�n�C���f�b�N�X
     * @param totalCount �ꗗ���̑S����
     */
    protected void addNextSubmit(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //�t�H�[�������擾
        String formName = ActionFormUtil
                .getActionFormName((HttpServletRequest) pageContext
                        .getRequest());

        //���y�[�W�����N�̐���
        for (int i = 1; i <= maxLinkNo; i++) {
            String linkKey = NEXT_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);

            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }

            int index = startIndex + (i * row);
            if (index > (totalCount - 1)) {
                sb.append(linkValue + "&nbsp;");
            } else {
                sb.append("<a href=\"#\" onclick=\"pageLinkSubmit(");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(rowProperty);
                sb.append(",");
                sb.append("document.");
                sb.append(formName);
                sb.append(".");
                sb.append(indexProperty);
                sb.append(",");
                sb.append(row);
                sb.append(",");
                sb.append(index);
                sb.append(")\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * �O�y�[�W�ɑJ�ڂ��郊���N��������StringBuilder�ɒǉ�����B
     * 
     * @param sb �ǉ��Ώۂ�StringBuilder
     * @param row �\���s��
     * @param startIndex �\���J�n�C���f�b�N�X
     * @param totalCount �ꗗ���̑S����
     */
    protected void addPrevLink(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //TagUtils�̃C���X�^���X����
        TagUtils tagUtils = TagUtils.getInstance();

        // ���X�|���X�p�����[�^�̎擾
        HttpServletResponse response
            = (HttpServletResponse) pageContext.getResponse();

        //�A�N�V����URL�̎擾
        String url = null;
        url = response.encodeURL(
                tagUtils.getActionMappingURL(action, pageContext));

        //�O�y�[�W�����N�̐���
        for (int i = maxLinkNo; i > 0; i--) {
            String linkKey = PREV_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);

            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }

            int index = startIndex - (i * row);
            if (index < 0) {
                sb.append(linkValue + "&nbsp;");
            } else {
                sb.append("<a href=\"" + url);
                if (url.indexOf("?") < 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(rowProperty);
                sb.append("=");
                sb.append(row);
                sb.append("&");
                sb.append(indexProperty);
                sb.append("=");
                sb.append(index);
                sb.append("\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * �y�[�W�ԍ������N��������StringBuilder�ɒǉ�����B
     * 
     * @param sb �ǉ��Ώۂ�StringBuilder
     * @param row �\���s��
     * @param startIndex �\���J�n�C���f�b�N�X
     * @param totalCount �ꗗ���̑S����
     */
    protected void addDirectLink(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //TagUtils�̃C���X�^���X����
        TagUtils tagUtils = TagUtils.getInstance();

        // ���X�|���X�p�����[�^�̎擾
        HttpServletResponse response
            = (HttpServletResponse) pageContext.getResponse();

        //�A�N�V����URL�̎擾
        String url = null;
        url = response.encodeURL(
                tagUtils.getActionMappingURL(action, pageContext));

        //�y�[�W�W�����v�p�����N�̒��ڔԍ��w��̕\���ő吔���擾
        String directLinkNo = links.get(MAX_DSP_SIZE);
        if (directLinkNo != null) {
            try {
                maxPageCount = Integer.parseInt(directLinkNo);
            } catch (NumberFormatException e) {
                // NumberFormatException�����������ꍇ�A
                // ���̃v���p�e�B�͖�������maxDirectLinkCount�ɂ�
                // �f�t�H���g�l���g�p�����
            }
        }

        //�S�y�[�W�����擾����B
        int pageCount = getPageCount(row, totalCount);

        //���݂̃y�[�W�C���f�b�N�X���擾����B
        int pageIndex = getPageIndex(row, startIndex);

        //�\���ŏI�y�[�W����ѕ\���J�n�y�[�W
        int startPage = 0;
        int endPage = 0;

        //�S�y�[�W�����A�y�[�W�ԍ������N�̕\�������傫���A
        //���A�\������y�[�W�C���f�b�N�X���A�y�[�W�ԍ������N�̕\�����̔������
        //�傫���ꍇ�́A�\���J�n�y�[�W�C���f�b�N�X���A�\������y�[�W�C���f�b�N�X��
        //���킹�ĕϓ�������B
        //��Ƃ��āA�S�y�[�W���F�P�O�y�[�W�A�y�[�W�ԍ������N�̕\�����F�T�A
        //�\������y�[�W�C���f�b�N�X�F�T�̏ꍇ�AstartPage���Q�ƂȂ�
        //endPage���T�ƂȂ�B���̏ꍇ�A��ʂɕ\�����郊���N�́u3 4 5 6 7�v�ƂȂ�B
        if (pageCount > maxPageCount && pageIndex > (maxPageCount / 2)) {

            //�\���ŏI�y�[�W���ő�y�[�W���Ƃ���B
            endPage = maxPageCount;

            startPage = (pageIndex - (endPage / 2)) - 1;
            if (startPage + endPage > pageCount) {
                startPage = pageCount - endPage;
            }
        } else {
            endPage = pageCount < maxPageCount ? pageCount : maxPageCount;
            startPage = 0;
        }

        //�y�[�W�ԍ������N�̐������[�v
        int size = startPage + endPage;
        for (int i = startPage; i < size; i++) {
            int idx = i + 1;
            if (pageIndex == idx) {
                sb.append("<b>");
                sb.append(idx);
                sb.append("</b>&nbsp;");
            } else {
                // �y�[�W�W�����v�p�����N�^�O���Z�b�g
                sb.append("<a href=\"" + url);
                if (url.indexOf("?") < 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(rowProperty);
                sb.append("=");
                sb.append(row);
                sb.append("&");
                sb.append(indexProperty);
                sb.append("=");
                sb.append(i * row);
                sb.append("\">");
                sb.append(idx);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * ���y�[�W�ɑJ�ڂ��郊���N��������StringBuilder�ɒǉ�����B
     * 
     * @param sb �ǉ��Ώۂ�StringBuilder
     * @param row �\���s��
     * @param startIndex �\���J�n�C���f�b�N�X
     * @param totalCount �ꗗ���̑S����
     */
    protected void addNextLink(StringBuilder sb, int row, int startIndex,
            int totalCount) {

        //TagUtils�̃C���X�^���X����
        TagUtils tagUtils = TagUtils.getInstance();

        // ���X�|���X�p�����[�^�̎擾
        HttpServletResponse response
            = (HttpServletResponse) pageContext.getResponse();

        //�A�N�V����URL�̎擾
        String url = null;
        url = response.encodeURL(
                tagUtils.getActionMappingURL(action, pageContext));

        //���y�[�W�����N�̐���
        for (int i = 1; i <= maxLinkNo; i++) {
            String linkKey = NEXT_LINKS + i + CHAR_LINKS;
            String linkValue = links.get(linkKey);

            if (linkValue == null
                    || "".equals(linkValue)) {
                continue;
            }

            int index = startIndex + (i * row);
            if (index > (totalCount - 1)) {
                sb.append(linkValue + "&nbsp;");
            } else {

                sb.append("<a href=\"" + url);
                if (url.indexOf("?") < 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(rowProperty);
                sb.append("=");
                sb.append(row);
                sb.append("&");
                sb.append(indexProperty);
                sb.append("=");
                sb.append(index);
                sb.append("\">");
                sb.append(linkValue);
                sb.append("</a>&nbsp;");
            }
        }
    }

    /**
     * �v���p�e�B�t�@�C���ɒ�`����Ă���y�[�W�W�����v�p�����N��`���擾����B
     * �擾�����N���X�ϐ��Ɋi�[����B
     */
    private void getLinkProperty() {
        // �v���p�e�B�t�@�C���ɒ�`����Ă��郊���N�\����o�^����
        Enumeration enume
            = PropertyUtil.getPropertyNames(PAGE_LINKS_PREFIX);
        while (enume.hasMoreElements()) {
            String propName = (String) enume.nextElement();
            String id = propName.substring(PAGE_LINKS_PREFIX.length());
            String link = PropertyUtil.getProperty(propName);

            // �ő�y�[�W�W�����v����ݒ�
            if ((id != null)
                    && (id.startsWith(PREV_LINKS)
                    || id.startsWith(NEXT_LINKS))) {
                String strLinkNo = id.substring(4, id.lastIndexOf(CHAR_LINKS));
                int intLinkNo = 0;
                try {
                    intLinkNo = Integer.parseInt(strLinkNo);
                } catch (NumberFormatException e) {
                    // �������Ȃ�
                    continue;
                }
                if (intLinkNo > maxLinkNo) {
                    maxLinkNo = intLinkNo;
                }
                links.put(id, link);
            } else if (MAX_DSP_SIZE.equals(id)) {
                links.put(id, link);
            }
        }
    }

    /**
     * �\���y�[�W�ԍ����Z�o���ĕԋp����B
     * 
     * @param row �\���s��
     * @param startIndex ���ݕ\������Ă���y�[�W�̕\���J�n�C���f�b�N�X
     * @return �Z�o�����\���y�[�W�ԍ�
     */
    protected int getPageIndex(int row, int startIndex) {

        //�\���y�[�W�ԍ��̎Z�o
        int pageIndex = 0;
        if (row > 0) {
            pageIndex = startIndex / row + 1;
        } else {
            pageIndex = 0;
        }
        if (row > 0 && startIndex % row > 0) {
            pageIndex++;
        }

        return pageIndex;

    }

    /**
     * �y�[�W�����Z�o���ĕԋp����B
     * 
     * @param row �\���s��
     * @param totalCount �S����
     * @return �Z�o�����y�[�W��
     */
    protected int getPageCount(int row, int totalCount) {

        //�\���y�[�W�ԍ��̎Z�o
        int pageCount = 0;
        if (row > 0) {
            pageCount = totalCount / row;
            if (totalCount % row > 0) {
                pageCount++;
            }
        } else {
            pageCount = 1;
        }

        return pageCount;

    }

    /**
     * �w�肳�ꂽKEY�ɂĎ擾�����l��^�U�l�ɕϊ����ĕԋp����B
     * �Ȃ��A����key��null�̏ꍇ�́AIllegalArgumentException����������B
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param key FLG���擾����KEY
     * @return �w�肳�ꂽKEY�ɂĎ擾�����o�͏�ԃt���O
     */
    protected boolean getPageContextFlg(
            PageContext pageContext, String key) {
        //�y�[�W�R���e�L�X�g����t���O���擾����B
        Object obj = pageContext.getAttribute(key);
        Boolean bol = new Boolean(false);
        if (obj != null && obj instanceof Boolean) {
           bol = (Boolean) obj;
        }
        return bol.booleanValue();
    }

    /**
     * �y�[�W�R���e�L�X�g�ɑ΂��āA�w�肳�ꂽKEY�̃t���O��ݒ肷��B
     * �Ȃ��A����key��null�̏ꍇ�́AIllegalArgumentException����������B
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param key FLG��ݒ肷��KEY
     */
    protected void setPageContextFlg(
            PageContext pageContext, String key) {
        //�y�[�W�R���e�L�X�g�Ƀt���O�𗧂Ă�B
        pageContext.setAttribute(key, Boolean.valueOf(true));
    }

    /**
     * name���w�肳��ĂȂ��ꍇ�́Aproperty�̒l�𒼐ڎ擾����B
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param name �v���p�e�B��ێ�����Bean��
     * @param property �v���p�e�B
     * @param scope �X�R�[�v
     * @return �擾�����l
     * @throws JspException JSP��O
     */
    protected Object lookup(PageContext pageContext, String name, 
            String property, String scope) throws JspException{
        if (property == null || "".equals(property)) {
            return null;
        }
        Object retObj = null;
        if (name != null && !"".equals(name)) {
            retObj = TagUtils.getInstance().lookup(pageContext, name,
                    property, scope);
        } else {
            retObj = TagUtils.getInstance().lookup(pageContext,
                    property, scope);
        }
        return retObj;
    }

    /**
     * �����̃I�u�W�F�N�g��int�ɕϊ����ĕԋp����B
     * 
     * @param obj int�ɕϊ�����I�u�W�F�N�g 
     * @return �擾�����l
     * @throws JspException JSP��O
     */
    protected int getInt(Object obj) throws JspException{
        int retInt = 0;
        String value = ObjectUtils.toString(obj);
        if (!"".equals(value)) {
            try {
                retInt = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
                throw new JspException(e);
            }
        }
        return retInt;
    }

    /**
     * ���݃y�[�W���A���y�[�W�����y�[�W�R���e�L�X�g�ɕۑ�����B
     * 
     * @param now ���݃y�[�W��
     * @param total ���y�[�W��
     */
    protected void attributePageCount(
            int now, int total) {

        if (total <= 0) {
            now = 0;
        }

        //���݃y�[�W�����y�[�W�R���e�L�X�g�ɕۑ�����B
        if (currentPageIndex != null && !"".equals(currentPageIndex)) {
            pageContext.setAttribute(currentPageIndex, now);
        } else {
            pageContext.setAttribute(CURRENT_PAGE_INDEX , now);
        }

        //���y�[�W�����y�[�W�R���e�L�X�g�ɕۑ�����B
        if (totalPageCount != null && !"".equals(totalPageCount)) {
            pageContext.setAttribute(totalPageCount, total);
        } else {
            pageContext.setAttribute(TOTAL_PAGE_COUNT, total);
        }
    }

    /**
     * ���ׂẴA���P�[�g���ꂽ�������������B
     */
    @Override
    public void release() {
        super.release();
        id = null;
        action = null;
        name = null;
        rowProperty = null;
        indexProperty = null;
        totalProperty = null;
        scope = null;
        submit = false;
        forward = false;
        event = DEFAULT_EVENT;
        resetIndex = false;
        currentPageIndex = CURRENT_PAGE_INDEX;
        totalPageCount = TOTAL_PAGE_COUNT;
    }

}
