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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * �J�X�^���^�O�@�\�S�ʂɒʂ��郆�[�e�B���e�B�N���X�B
 * 
 */
public class TagUtil {

    /**
     * �X�R�[�v�}�b�v�B
     */
    private static final Map<String, Integer> SCOPES = 
        new HashMap<String, Integer>();

    /**
     * �X�R�[�v�}�b�v�̐������s���B
     */
    static {
        SCOPES.put("application", PageContext.APPLICATION_SCOPE);
        SCOPES.put("session", PageContext.SESSION_SCOPE);
        SCOPES.put("request", PageContext.REQUEST_SCOPE);
        SCOPES.put("page", PageContext.PAGE_SCOPE);
    }

    /**
     * �����̃X�R�[�v���ƈ�v����X�R�[�v�̒萔��ԋp����B
     * ��v����X�R�[�v�����݂��Ȃ��ꍇ�͗�O�𔭐�������B
     * 
     * @param scopeName �擾����萔�̃X�R�[�v��
     * @return �擾�����X�R�[�v�̒萔
     * @throws JspException ��v����X�R�[�v�����݂��Ȃ��ꍇ
     */
    public static int getScope(String scopeName) throws JspException {

        if (scopeName == null) {
            throw new JspException();
        }

        Integer scope = SCOPES.get(scopeName.toLowerCase());

        if (scope == null) {
            throw new JspException();
        }

        return scope.intValue();
    }

    /**
     * �w�肳�ꂽBean���w�肳�ꂽ�X�R�[�v����擾����B
     * ������scopeName��Null�̏ꍇ��PageScope����擾����B
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param name �擾����Bean��
     * @param scopeName Bean���擾����X�R�[�v��
     * @return �擾����Bean
     * @throws JspException ��v����X�R�[�v�����݂��Ȃ��ꍇ
     */
    public static Object lookup(PageContext pageContext, String name,
            String scopeName)
            throws JspException {

        if (scopeName == null) {
            return pageContext.findAttribute(name);
        }

        return pageContext.getAttribute(name, getScope(scopeName));

    }

    /**
     * �w�肳�ꂽBean���擾���A����Bean���������
     * �w�肳�ꂽ�v���p�e�B�l���擾����B
     * �����̃v���p�e�B��Null�̏ꍇ�͎擾����Bean��ԋp����B
     * Bean���擾�ł��Ȃ������ꍇ�́A��O�𔭐�������B
     *  
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param name �擾����Bean��
     * @param property Bean�̃v���p�e�B��
     * @param scopeName Bean���擾����X�R�[�v��
     * @return �擾����Bean
     * @throws JspException ��v����X�R�[�v�����݂��Ȃ��ꍇ,
     * �w�肳�ꂽBean�����݂��Ȃ��ꍇ
     */
    public static Object lookup(PageContext pageContext, String name,
            String property, String scopeName) throws JspException {

        //�w�肳�ꂽBean���擾����B
        Object bean = lookup(pageContext, name, scopeName);

        //Bean��Null�̏ꍇ�͗�O�Ƃ���B
        if (bean == null) {
            throw new JspException();
        }

        //�v���p�e�B���w�肳��Ă��Ȃ��ꍇ�́ABean��ԋp����B
        if (property == null) {
            return bean;
        }

        //�擾����Bean����w�肳�ꂽ�v���p�e�B�̒l���擾����B
        //��O����JspException�Ń��b�v���ē�����B
        try {
            return PropertyUtils.getProperty(bean, property);

        } catch (IllegalAccessException e) {
            throw new JspException(e);
        } catch (IllegalArgumentException e) {
            throw new JspException(e);
        } catch (InvocationTargetException e) {
            throw new JspException(e);
        } catch (NoSuchMethodException e) {
            throw new JspException(e);
        }

    }

    /**
     * PageContext����JspWriter���擾���āA�w�肳�ꂽ�e�L�X�g���o�͂���B
     * ���s�Ȃ��ŁB
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param text �o�͂���e�L�X�g
     * @throws JspException
     * I/O�G���[�����������ꍇ��IOException�����b�v������O
     */
    public static void write(PageContext pageContext, String text)
            throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(text);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /**
     * PageContext����JspWriter���擾���āA�w�肳�ꂽ�e�L�X�g���o�͂���B
     * ���s����ŁB
     * 
     * @param pageContext �y�[�W�R���e�L�X�g
     * @param text �o�͂���e�L�X�g
     * @throws JspException
     * I/O�G���[�����������ꍇ��IOException�����b�v������O
     */
    public static void writeln(PageContext pageContext, String text)
            throws JspException {
        JspWriter writer = pageContext.getOut();
        try {
            writer.println(text);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /**
     * HTML�o�͎��ɃT�j�^�C�Y���s���B
     * �u<�v�u>�v�u&�v�u"�v�u'�v��u������B
     * 
     * @param value �T�j�^�C�Y�Ώە�����
     * @return �ҏW�ςݕ�����
     */
    public static String filter(String value) {

        if (value == null) {
            return null;
        }

        char[] content = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&#39;");
                    break;
                default:
                    result.append(content[i]);
                    break;
            }
        }

        return result.toString();
    }

}
