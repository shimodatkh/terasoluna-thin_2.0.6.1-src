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
 * DateFormatterTag�N���X�̃e�X�g���s���ۂɎg�p����bean�B
 *
 */
public class DateFormatterTagBase_BeanStub01 {
    
    /**
     * String�^�̃t�B�[���h�B
     */
    private String testField = null;

    /**
     * Date�^�̃t�B�[���h�B
     */
    private Date dateField = null;
    
    /**
     * Integer�^�̃t�B�[���h�B
     */
    private Integer timeField = null;

    /**
     * timeField ���擾����B
     * @return timeField ��\���t�B�[���h�l�B
     */
    public Integer getTimeField() {
        return timeField;
    }
    
    /**
     * timeField ��ݒ肷��B
     * @param timeField timeField��\���t�B�[���h�l�B
     */
    public void setTimeField(Integer timeField) {
        this.timeField = timeField;
    }
    
    /**
     * dateField ���擾����B
     * @return dateField��\���t�B�[���h�l�B
     */
    public Date getDateField() {
        return dateField;
    }
    
    /**
     * dateField ��ݒ肷��B
     * @param dateField ��\���t�B�[���h�l�B
     */
    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }
    
    /**
     * testField ���擾����B
     * @return testField ��\���t�B�[���h�l�B
     */
    public String getTestField() {
        return testField;
    }

    /**
     * testField ��ݒ肷��B
     * @param testField testField��\���t�B�[���h�l�B
     */
    public void setTestField(String testField) {
        this.testField = testField;
    }
    
    
}
