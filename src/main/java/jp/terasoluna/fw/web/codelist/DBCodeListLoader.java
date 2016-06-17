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

package jp.terasoluna.fw.web.codelist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.object.MappingSqlQuery;


/**
 * �R�[�h���X�g���̏��������f�[�^�x�[�X��p���čs���A
 * {@link jp.terasoluna.fw.web.codelist.AbstractMultilingualCodeListLoader}�A
 * {@link jp.terasoluna.fw.web.codelist.ReloadableCodeListLoader}
 * �����N���X�ł���B
 *
 * <p>
 * ���̃N���X��p���ăR�[�h���X�g�𐶐�����ꍇ�́A
 * dataSource�����Ɏg�p����f�[�^�\�[�X���w�肵�����ƁA
 * init-method������load���w�肵�Aload()���\�b�h���ŏ��Ɏ��s����K�v������B<br>
 * �܂��A�{�N���X�̓X���b�h�Z�[�t�ł͂Ȃ����߁A
 * �Ɩ��ǋ@�\�������̓T�[�o�ǋ@�\���𗘗p���A
 * �R�[�h���X�g�ւ̎Q�Ƃ��s���Ȃ���Ԃɂ�reload()���\�b�h��
 * ���s����悤�ɕǐ݌v���s���K�v������B<br>
 * �X���b�h�Z�[�t�Ƃ���ꍇ�A�R�[�h���X�g�Q�Ǝ��ɔr�����䂪������
 * ���\�򉻂������炷�����ƂȂ蓾�邽�ߒ��ӂ��K�v�ł���B
 * </p>
 *
 * <p>
 * �R�[�h���X�g���f�[�^�x�[�X����擾���邽�߂� SQL �́ABean��`�t�@�C������
 * �ݒ肷��B
 * </p>
 * <p>
 * <strong>Bean��`�t�@�C���̐ݒ��B</strong>
 * <br>
 * �f�[�^�\�[�X��TerasolunaDataSource�Ƃ��Ē�`���Ă���ꍇ�B
 * </p>
 * <p>
 * <code><pre>
 * &lt;bean id=<strong>&quot;loader1&quot;</strong>
 *       class=&quot;jp.terasoluna.fw.web.codelist.DBCodeListLoader&quot;
 *       init-method=&quot;load&quot;&gt;
 *   &lt;property name=&quot;defaultLocale&quot; value=&quot;ja&quot; /&gt;
 *   &lt;property name=&quot;dataSource&quot;&gt;
 *     &lt;ref bean=<strong>&quot;TerasolunaDataSource&quot;</strong>/&gt;
 *   &lt;/property&gt;
 *   &lt;property name=&quot;sql&quot;&gt;
 *     &lt;value&gt;<strong>SELECT KEY, VALUE, LANGUAGE, COUNTRY, VARIANT FROM CODE_LISTS</strong>&lt;/value&gt;
 *   &lt;/property&gt;
 *
 * &lt;/bean&gt;
 * </code></pre>
 * </p>
 * <p>
 *  SQL�Ŏ擾���ꂽ�R�[�h���X�g���i�[����N���X LocaleCodeBean �́A����
 *  id �A name�Alanguage�Acountry�Avariant �������A
 *   SELECT ���Ŏw�肳�ꂽ�J�����̏��Ԃ� LocaleCodeBean ��
 *  �i�[�����B<br>
 *  ��L��ł́A KEY �� id �AVALUE �� name�ALANGUAGE��language�A
 *  �ACOUNTRY��country�AVARIANT��variant �̏��ԂŊi�[�����B<br>
 *  �擾���ꂽ�J������2�ɖ����Ȃ��ꍇ�A�Ⴆ�Ώ�L���
 *   KEY �̂ݎ擾�����ꍇ�́A name�Alanguage�Acountry�Avariant ��
 *   null ���i�[�����B
 *   SELECT ���Ŏ擾�����J������6�ȏ�ł���Ƃ��́A
 *  6�߈ȍ~�̃J�����͖��������B<br>
 *  �R�[�h���X�g�����ۉ��Ή�����Ă��Ȃ��ꍇ�́A
 *  LANGUAGE�ACOUNTRY�AVARIANT�J�������擾���Ȃ��Ă悢�B
 * </p>
 * <p>
 * SQL�Ŏ擾���ꂽ����(LocaleCodeBean)����R�[�h���X�g���Ƃ��ĕێ�����
 * Map&lt;Locale,List&lt;CodeBean&gt;&gt;���쐬����B
 * Map�̃L�[Locale��LocaleCodeBean��language�Acountry�Avariant����쐬����B
 * </p>
 * <p>
 *  JSP ���ɂ����āA Struts ��&lt;logic:iterate&gt;
 *  �^�O�� name ������A&lt;html:options&gt;�^�O��
 *  collection ������bean���Ƃ��ĎQ�Ƃ���B<br>
 *  �ȉ��́Abean���� &quot;loader1&quot; �Ƃ��āA
 *  &lt;html:options&gt; �� collection�����Ɏw��
 *  �����ꍇ�̗�ł���B
 * </p>
 * <strong>JSP ���ł̃R�[�h���X�g�g�p��B</strong><br>
 * <p>
 *  <code><pre>
 *  &lt;ts:defineCodeList id=<b>"loader1"</b> /&gt;
 *  �c
 *  &lt;html:select property="selectOptions"&gt;
 *    &lt;html:options collection=<b>"loader1"</b>
 *                  labelProperty="name"
 *                  property="id"/&gt;
 *  &lt;/html:select&gt;
 *  </pre></code>
 * </p>
 * �R�[�h���X�g�̃����[�h�ɂ��ẮA
 * {@link
 *  jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction}
 *
 * ���A�܂��R�[�h���X�g��JSP���ł̎g�p���@�́A
 * {@link
 *  jp.terasoluna.fw.web.taglib.DefineCodeListTag}
 * �ƁA
 * {@link
 *  jp.terasoluna.fw.web.taglib.WriteCodeCountTag}
 * ���Q�Ƃ̂��ƁB
 *
 * </p>
 * @see jp.terasoluna.fw.web.codelist.CodeBean
 * @see
 *    jp.terasoluna.fw.web.struts.actions.ReloadCodeListAction
 * @see jp.terasoluna.fw.web.taglib.DefineCodeListTag
 * @see jp.terasoluna.fw.web.taglib.WriteCodeCountTag
 */
public class DBCodeListLoader extends AbstractMultilingualCodeListLoader implements ReloadableCodeListLoader {

    /**
     * ���O�N���X�B
     */
    private Log log = LogFactory.getLog(DBCodeListLoader.class);
    
    /**
     * �R�[�h���X�g���擾���邽�߂�SQL�B
     */
    private String sql = null;

    /**
     * DB�ڑ��Ɏg�p����f�[�^�\�[�X�B
     */
    private DataSource dataSource = null;

    /**
     * �R�[�h���X�g�Ɏg�p���郍�b�N�I�u�W�F�N�g�B
     */
    private final Object lockObject = new Object();

	/**
     * �R�[�h���X�g�̏��������s���B
     *
     * <p>
     * {@link #loadCodeList()}���g���Ċ��ɐݒ肳��Ă���
     * dataSource�Ǝw�肳�ꂽSQL�������ɃR�[�h���X�g�𐶐�����B
     * �R�[�h���X�g�����łɑ��݂���ꍇ�ɂ́A�����s��Ȃ��B
     * </p>
     */
    public void load() {
        if (log.isDebugEnabled()) {
            log.debug("load() called.");
        }
        if (localeMap != null) {
            // ���łɃR�[�h���X�g���ǂݍ��܂�Ă���Ƃ��ɂ͉������Ȃ��B
            return;
        }
        // �R�[�h���X�g��ǂݍ��ށB
        loadCodeList();
    }

    /**
     * �R�[�h���X�g�̍ēǂݍ��݂��s���B
     *
     * <p>
     * codeLists�̓�������������{@link #loadCodeList()}
     * ���Ăяo���B�Ɩ��ǒ��̊Ǘ��ҋƖ��ɂė��p���邱�Ƃ��]�܂����B
     * </p>
     */
    public void reload() {
        if (log.isDebugEnabled()) {
            log.debug("reload() called.");
        }
        if (localeMap == null) {
            // codeLists�����݂��Ȃ��ꍇ�͂��̂܂�loadCodeList���ĂԁB
            loadCodeList();
        } else {
            synchronized (lockObject) {
                // codeLists�����݂���ꍇ�A
                // �ǂݍ��ݒ��ɍēǂݍ��݂�h�����ߓ��������B
                loadCodeList();
            }
        }
    }

    /**
     * �R�[�h���X�g��ǂݍ��ށB
     *
     * dataSource�Ǝw�肳�ꂽSQL�������ɃR�[�h���X�g�𐶐�����B
     */
    @SuppressWarnings("unchecked")
    protected void loadCodeList() {
        if (log.isDebugEnabled()) {
            log.debug("loadCodeList() called.");
        }

        Map<Locale, List<CodeBean>> resultMap = 
                                        new HashMap<Locale, List<CodeBean>>();

        // �f�[�^�x�[�X����l���R�[�h���X�g���擾����B
        MappingSqlQuery query = new DBCodeListQuery(dataSource, sql);
        query.compile();
        List<LocaleCodeBean> resultList = query.execute();
        for (LocaleCodeBean lcb : resultList) {
            CodeBean cb = new CodeBean();
            cb.setId(lcb.getId());
            cb.setName(lcb.getName());

            Locale curLocale = createLocale(lcb);
            if (curLocale == null) {
                curLocale = defaultLocale;
            }

            List<CodeBean> codeList = resultMap.get(curLocale);
            // �R�[�h���X�g�����݂��Ȃ��ꍇ�A����������B
            if (codeList == null) {
                codeList = new ArrayList<CodeBean>();
            }
            codeList.add(cb);


            resultMap.put(curLocale, codeList);
        }

        for (Entry<Locale, List<CodeBean>> entry : resultMap.entrySet()) {
            resultMap.put(entry.getKey(), 
                             Collections.unmodifiableList(entry.getValue()));
        }

        localeMap = Collections.unmodifiableMap(resultMap);
    }
    
    /**
     * Locale�I�u�W�F�N�g�𐶐�����B<br>
     * ����R�[�h�A���R�[�h�A�o���A���g�R�[�h����Locale�I�u�W�F�N�g�𐶐�����B
     * ����R�[�h���^�����Ă��Ȃ��ꍇ�́A�f�t�H���g���P�[���̌���R�[�h�̂�
     * ���i�[���ALocale�I�u�W�F�N�g�𐶐�����B
     *
     * @param bean �R�[�h���X�g
     *
     * @return
     *      ����R�[�h�A���R�[�h�A�o���A���g�R�[�h���i�[����Locale�I�u�W�F�N�g�B
     */
    Locale createLocale(LocaleCodeBean bean) {
        return createLocale(bean.getLanguage(), bean.getCountry(), bean
                .getVariant());
    }

    /**
     * dataSource���擾����B
     *
     * @return dataSource ��\���t�B�[���h�l�B
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * dataSource��ݒ肷��B
     *
     * @param dataSource dataSource��\���t�B�[���h�l�B
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * �R�[�h���X�g���擾����SQL���擾����B
     * @return SQL��
     */
    public String getSql() {
        return sql;
    }

    /**
     * �R�[�h���X�g���擾����SQL��ݒ肷��B
     * @param sql SQL��
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

}
