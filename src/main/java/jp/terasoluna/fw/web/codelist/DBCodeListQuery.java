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

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;

/**
 * �f�[�^�x�[�X����R�[�h���X�g�擾���s�� RDBMS�I�y���[�V�����N���X�B
 *
 * �f�[�^�x�[�X�ɐڑ�����f�[�^�\�[�X�Ǝg�p����SQL�����R���X�g���N�^�Ŏw�肵�āA
 * execute���\�b�h�����s���邱�ƂŁA�f�[�^�x�[�X����R�[�h���X�g���擾���邱�Ƃ�
 * �ł���B
 * ���̃N���X��
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListLoader}
 * �ł̂ݗ��p�����B
 *
 *
 */
public class DBCodeListQuery extends MappingSqlQuery {

    /**
     * �f�[�^�\�[�X��SQL���̐ݒ���s���R���X�g���N�^�B
     *
     * @param dataSource �f�[�^�x�[�X�ڑ��Ɏg�p����f�[�^�\�[�X�B
     * @param sql �R�[�h���X�g�擾�Ɏg�p����SQL���B
     */
    public DBCodeListQuery(DataSource dataSource, String sql) {
        super(dataSource, sql);
    }

    /**
     * 1�s�擾���邲�ƂɌĂ΂��B
     *
     * <p>
     * �擾�����s��1��ڂ�id��2��ڂ�name�Ƃ��ăf�[�^�x�[�X����擾�����l��
     * CodeBean�C���X�^���X�����т���B
     * </p>
     *
     * @param rs ���݂̍s��������ResultSet�B
     * @param rowNum ���ݎQ�Ƃ��Ă���s�ԍ��B�i�ŏ���0�s�ځj
     * @throws SQLException SQL��O�B
     * @return �擾�������ʂ��i�[���ꂽ�C���X�^���X�B
     */
    @Override
    protected Object mapRow(ResultSet rs, int rowNum)
        throws SQLException {
        return createCodeBean(rs);
    }

    /**
     * ResultSet����l���擾���ACodeBean�C���X�^���X�𐶐�����B
     *
     * @param rs �l��ێ�����ResultSet�B
     * @return �l���i�[���ꂽCodeBean�C���X�^���X�B
     * @throws SQLException SQL��O�B
     */
    private LocaleCodeBean createCodeBean(ResultSet rs) throws SQLException {
        LocaleCodeBean cb = new LocaleCodeBean();
        int columnCount = rs.getMetaData().getColumnCount();
        if (columnCount > 0) {
            // 1��ڂ����݂���ꍇ�B
            String id = rs.getString(1);
            if (id == null) {
                id = "";
            }
            cb.setId(id);
        }

        if (columnCount > 1) {
            // 2��ڂ����݂���ꍇ�B
            String name = rs.getString(2);
            if (name == null) {
                name = "";
            }
            cb.setName(name);
        }

        if (columnCount > 2) {
            // 3��ڂ����݂���ꍇ�B
            String language = rs.getString(3);
            if (language == null) {
                language = "";
            }
            cb.setLanguage(language);
        }

        if (columnCount > 3) {
            // 4��ڂ����݂���ꍇ�B
            String country = rs.getString(4);
            if (country == null) {
                country = "";
            }
            cb.setCountry(country);
        }

        if (columnCount > 4) {
            // 5��ڂ����݂���ꍇ�B
            String variant = rs.getString(5);
            if (variant == null) {
                variant = "";
            }
            cb.setVariant(variant);
        }
        return cb;
    }

}
