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

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ���ۉ��Ή�����{@link jp.terasoluna.fw.web.codelist.CodeListLoader}��
 * �������邽�߂̒��ۃN���X�B
 *
 * <p>
 * ���̃N���X�͍��ۉ����ꂽ�R�[�h���X�g�������P�[����
 * �R�[�h���X�g�̃}�b�v�`���ŕێ�����B
 * �܂��A���̃}�b�v���烍�P�[�����w�肵��
 * �R�[�h���X�g���擾���郁�\�b�h���������Ă���B
 * <br> 
 * �R�[�h���X�g���̓Ǎ��݂́A���̃N���X���p�������T�u�N���X�Ŏ�������B
 * 
 * @see DBCodeListLoader
 * @see MappedCodeListLoader
 * 
 */
public abstract class AbstractMultilingualCodeListLoader implements CodeListLoader {

    /**
     * ���O�N���X�B
     */
    private Log log = LogFactory.getLog(AbstractMultilingualCodeListLoader.class);
    
    /**
     * ���ۉ��Ή��R�[�h���X�g���}�b�v
     * @see jp.terasoluna.fw.web.codelist.CodeBean
     */
    protected Map<Locale, List<CodeBean>> localeMap = null;

    
    /**
     * ���P�[�����w�肳��Ă��Ȃ��ꍇ�̃f�t�H���g���P�[���B �R�[�h���X�g��
     * �Ń��P�[�����w�肳��Ă��Ȃ��ꍇ�A ���̃��P�[�����ݒ肳���B
     * �f�t�H���g�ł̓T�[�o�[��JVM�̌���R�[�h�݂̂����P�[���Ƃ��Ďg�p����B
     */
    protected Locale defaultLocale
                            = new Locale(Locale.getDefault().getLanguage());

    /**
     * �f�t�H���g���P�[����ݒ肷��B
     * 
     * @param defaultLocale �f�t�H���g���P�[��
     */
    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /** 
     * �R�[�h���X�g���擾����B
     *
     * �R�[�h���X�g�� {@link CodeBean} �̔z��Ƃ��Ď擾�ł���B<br>
     * ���R�[�h���X�g�͌����Ƃ��ăA�v���P�[�V�������ň�ӂƂȂ���ł���B
     * ���̃��\�b�h���I�[�o�[���C�h����ꍇ�́A�Ɩ����W�b�N�Ȃǂ�
     * �R�[�h���X�g�̓��e���ҏW����Ă��e�����Ȃ��悤�Ɏ�������K�v������B
     *
     * @return �R�[�h���X�g
     */
    public CodeBean[] getCodeBeans() {
        return createCodeBeans(defaultLocale);
    }

    /**
     * ���P�[�����w�肵�ăR�[�h���X�g���擾����B
     * <p>
     * �R�[�h���X�g�� {@link CodeBean} �̔z��Ƃ��Ď擾�ł���B<br>
     * �����Ŏw�肵�����P�[���ɑΉ�����R�[�h���X�g���擾�ł��Ȃ��ꍇ�A
     * �f�t�H���g���P�[���ɑΉ�����R�[�h���X�g���擾����B
     * 
     * ���R�[�h���X�g�͌����Ƃ��ăA�v���P�[�V�������ň�ӂƂȂ���ł���B
     * ���̃��\�b�h���I�[�o�[���C�h����ꍇ�́A�Ɩ����W�b�N�Ȃǂ�
     * �R�[�h���X�g�̓��e���ҏW����Ă��e�����Ȃ��悤�Ɏ�������K�v������B
     *
     * @param locale ���P�[��
     * @return �R�[�h���X�g
     */
    public CodeBean[] getCodeBeans(Locale locale) {
        
        if (log.isDebugEnabled()) {
            log.debug("getCodeBeans(" + locale + ") called.");
        }

        CodeBean[] result = createCodeBeans(locale);

        // �R�[�h���X�g���擾�ł��Ȃ������ꍇ�A�f�t�H���g���P�[���ōč쐬
        if (locale != null && result.length == 0) {
            result = createCodeBeans(defaultLocale);
        }
        return result;
    }
    
    /**
     * ���P�[���ɑΉ�����R�[�h���X�g���쐬����B
     * <p>
     * �����Ŏw�肵�����P�[���ɑΉ�����R�[�h���X�g�����݂��Ȃ��ꍇ�A
     * ��ʂ̃��P�[���ɑΉ�����R�[�h���X�g���쐬����B
     * 
     * @param locale ���P�[��
     * @return �R�[�h���X�g
     */
    protected CodeBean[] createCodeBeans(Locale locale) {
        
        if (log.isDebugEnabled()) {
            log.debug("createCodeBeans(" + locale + ") called.");
        }
        
        if (localeMap == null) {
            if (log.isDebugEnabled()) {
                log.debug("field codeListsMap is null.");
            }
            // codeListsMap�����݂��Ȃ��Ƃ��͋�̔z���Ԃ��B
            return new CodeBean[0];
        }

        if (locale == null) {
            if (log.isDebugEnabled()) {
                log.debug("arg locale is null. replace locale default : "
                        + defaultLocale);
            }
            if (defaultLocale == null) {
                throw new IllegalStateException("Default locale is null.");
            }
            locale = defaultLocale;
        }

        List<CodeBean> codeLists = localeMap.get(locale);
        
        // �R�[�h���X�g���擾�ł��Ȃ��ꍇ�A��ʂ̃��P�[���ōč쐬
        if (codeLists == null) {
            if (locale.getVariant().length() > 0) {
                return createCodeBeans(new Locale(locale.getLanguage(), locale
                        .getCountry()));
            } else if (locale.getCountry().length() > 0) {
                return createCodeBeans(new Locale(locale.getLanguage()));
            }

            // codeLists�����݂��Ȃ��Ƃ��͋�̔z���Ԃ��B
            return new CodeBean[0];
        }

        CodeBean[] cb = new CodeBean[codeLists.size()];
        for (int i = 0; i < codeLists.size(); i++) {
            cb[i] = new CodeBean();
            cb[i].setId(codeLists.get(i).getId());
            cb[i].setName(codeLists.get(i).getName());
        }
        return cb;
    }
    
    /**
     * ����R�[�h�A���R�[�h�A�o���A���g�R�[�h���烍�P�[�����쐬����B
     * <p>
     * ����R�[�h��null�܂��͋󕶎��̏ꍇ�̓f�t�H���g���P�[����ԋp����B
     * 
     * @param language ����R�[�h
     * @param country ���R�[�h
     * @param variant �o���A���g�R�[�h
     * @return ���P�[��
     */
    protected Locale createLocale(String language, String country,
            String variant) {
        
        // ����R�[�h���ݒ肳��Ă��Ȃ��ꍇ�A�f�t�H���g���P�[����ԋp
        if (language == null || language.length() == 0) {
            return defaultLocale;
        }
        // ���R�[�h���ݒ肳��Ă��Ȃ��ꍇ�A����R�[�h�ō쐬�������P�[����ԋp
        if (country == null || country.length() == 0) {
            return new Locale(language);
        }
        // �o���A���g�R�[�h���ݒ肳��Ă��Ȃ��ꍇ�A
        // ����R�[�h�A���R�[�h�ō쐬�������P�[����ԋp
        if (variant == null || variant.length() == 0) {
            return new Locale(language, country);
        }
        return new Locale(language, country, variant);
    }

}
