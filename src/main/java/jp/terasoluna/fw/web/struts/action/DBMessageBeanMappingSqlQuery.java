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

package jp.terasoluna.fw.web.struts.action;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.object.MappingSqlQuery;
/**
 * DB����擾�����s��DBMessageBean�C���X�^���X�ɋl�ߑւ��ĕԂ����\�b�h��
 * ��������MappingSqlQuery�����N���X�B
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageBean
 * @see jp.terasoluna.fw.web.struts.action.MessageResourcesDAOImpl
 * 
 */
public class DBMessageBeanMappingSqlQuery extends MappingSqlQuery {
    
    /**
     * SQL�̐ݒ�Ɍ�肪������ꍇ������킷�G���[�R�[�h
     */
    private static final String DB_MESSAGE_BEAN_MAPPING_SQL_QUERY
        = "errors.db.message.bean.mapping.sql.query";
    
    /**
     * ���O�N���X�B
     */ 
    private static Log log 
        = LogFactory.getLog(DBMessageBeanMappingSqlQuery.class);

    /**
     * �R���X�g���N�^�B
     * 
     * @param dataSource �f�[�^�\�[�X
     * @param sql SQL
     */
    DBMessageBeanMappingSqlQuery(DataSource dataSource, String sql) {
        super(dataSource, sql);
    }
    
    /**
     * �s��DBMessageBean�ɋl�ߑւ��ĕԂ��B
     * 
     * @param rs �s
     * @param rowNum �������Ă���s�ԍ�
     * 
     * @return �s����l�ߑւ���ꂽDBMessageBean
     * 
     * @throws SQLException �ݒ肳�ꂽSQL������Ă����ꍇ
     */
    @Override
    protected DBMessageBean mapRow(ResultSet rs, int rowNum)
    throws SQLException {
        // �s��DBMessageBean�C���X�^���X�ɋl�ߑւ�
        return createDBMessageBean(rs); 
    }

    /**
     * �s��DBMessageBean�ɋl�ߑւ��ĕԂ��B
     * 
     * @param rs �s
     * 
     * @return �s����l�ߑւ���ꂽDBMessageBean
     * 
     * @throws SQLException �ݒ肳�ꂽSQL������Ă����ꍇ
     */
    protected DBMessageBean createDBMessageBean(ResultSet rs) 
    throws SQLException {
        DBMessageBean msg = new DBMessageBean();
        int columnCount = 0;
        String key = null;
        String value = null;
        
        // ���b�Z�[�W���\�[�X�̓����Ă���e�[�u�����K�؂Ȍ`�łȂ��Ƃ���
        // ��O���X���[�B
        columnCount = rs.getMetaData().getColumnCount();
        if (columnCount != 2) {
            // SQL�̕Ԃ��r���[��2��ł͂Ȃ��ꍇ�ASQL������Ă���
            log.error("SQL for DB message returns "
                      + columnCount + " column(s) result set.");
            throw new SystemException(null, DB_MESSAGE_BEAN_MAPPING_SQL_QUERY);
        }
        
        // key��ݒ�
        key = rs.getString(1);
        if (key == null || "".equals(key)) {
            if (log.isWarnEnabled()) {
                log.warn("DBMessage resource keys contain null or empty.");
            }
            key = "";
        }        
        msg.setKey(key);        
        
        // value��ݒ�
        value = rs.getString(2);
        if (value == null) {
            value = "";
        }
        msg.setValue(value);
        
        return msg;
    }
}
