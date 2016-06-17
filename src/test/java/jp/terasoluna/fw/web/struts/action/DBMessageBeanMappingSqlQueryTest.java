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

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import jp.terasoluna.fw.exception.SystemException;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import com.mockrunner.mock.jdbc.MockResultSet;

/**
 * {@link jp.terasoluna.fw.web.struts.action.DBMessageBeanMappingSqlQuery} 
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * DB����擾�����s��DBMessageBean�C���X�^���X�ɋl�ߑւ��ĕԂ����\�b�h��
 * ��������MappingSqlQuery�����N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.action.DBMessageBeanMappingSqlQuery
 */
public class DBMessageBeanMappingSqlQueryTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBMessageBeanMappingSqlQueryTest.class);
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DBMessageBeanMappingSqlQueryTest(String name) {
        super(name);
    }

    /**
     * testMapRow01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) rs:|"test01"|"�e�X�g���b�Z�[�W�O�P"|<br>
     *                �Ƃ������e��ResultSet<br>
     *         (����) rowNum:1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageBean:key->"test01"<br>
     *                  value->"�e�X�g���b�Z�[�W�O�P"<br>
     *         (��ԕω�) createDBMessageBean:�����ɑO�������rs��ݒ肵�ČĂяo�����<br>
     *         
     * <br>
     * createDBMessageBean���\�b�h���Ăяo����Ă��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRow01() throws Exception {
        // �O����
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("�e�X�g���b�Z�[�W�O�P");
        rs.addColumn(list2);
        
        rs.first();
        
        // �e�X�g���{
        DBMessageBean actual = query.mapRow(rs, 1);

        // ����
        assertEquals("test01", actual.getKey());
        assertEquals("�e�X�g���b�Z�[�W�O�P", actual.getValue());
    }


    /**
     * testCreateDBMessageBean01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) rs:�J��������0��ResultSet<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.db.message.bean.mapping.sql.query"<br>
     *                    ���b�v������O�Fnull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "SQL for DB message returns 0 column(s) result set."<br>
     *         
     * <br>
     * ResultSet�̃J��������0�̏ꍇ�A�G���[���O���o�͂��A
     * ��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDBMessageBean01() throws Exception {
        // �O����
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");        
        rs.first();

        // �e�X�g���{
        try {
            query.createDBMessageBean(rs);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.db.message.bean.mapping.sql.query",
                e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "SQL for DB message returns 0 column(s) result set."));
        }
    }

    /**
     * testCreateDBMessageBean02()
     * <br><br>
     * 
     *  (����n) or (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) rs:|"test01"|<br>
     *                �Ƃ������e��ResultSet<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.db.message.bean.mapping.sql.query"<br>
     *                    ���b�v������O�Fnull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "SQL for DB message returns 1 column(s) result set.."<br>
     *         
     * <br>
     * ResultSet�̃J��������1�̏ꍇ�A�G���[���O���o�͂��A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDBMessageBean02() throws Exception {
        // �O����
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);
        
        rs.first();

        // �e�X�g���{
        try {
            query.createDBMessageBean(rs);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.db.message.bean.mapping.sql.query",
                e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "SQL for DB message returns 1 column(s) result set."));
        }
    }

    /**
     * testCreateDBMessageBean03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) rs:|"test01"|"�e�X�g���b�Z�[�W�O�P"|<br>
     *                �Ƃ������e��ResultSet<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageBean:key->"test01"<br>
     *                  value->"�e�X�g���b�Z�[�W�O�P"<br>
     *         
     * <br>
     * �J��������2�̂Ƃ��Akey, value�Ƃ��ɐ���ɐݒ肳�ꂽDBMessageBean���Ԃ���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDBMessageBean03() throws Exception {
        // �O����
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);
        
        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("�e�X�g���b�Z�[�W�O�P");
        rs.addColumn(list2);
        
        rs.first();

        // �e�X�g���{
        DBMessageBean actual = query.createDBMessageBean(rs);
        
        // ����
        assertEquals("test01", 
                     UTUtil.getPrivateField(actual, "key"));
        assertEquals("�e�X�g���b�Z�[�W�O�P",
                     UTUtil.getPrivateField(actual, "value"));
    }

    /**
     * testCreateDBMessageBean04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) rs:|"test01"|"�e�X�g���b�Z�[�W�O�P"|"ja"|<br>
     *                �Ƃ������e��ResultSet<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SystemException�F<br>
     *                    �G���[�R�[�h�F"errors.db.message.bean.mapping.sql.query"<br>
     *                    ���b�v������O�Fnull<br>
     *         (��ԕω�) ���O:�y�G���[���O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "SQL for DB message returns 3 column(s) result set."<br>
     *         
     * <br>
     * ResultSet�̃J��������3�̏ꍇ�A�G���[���O���o�͂��A��O���X���[���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDBMessageBean04() throws Exception {
        // �O����
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("test01");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("�e�X�g���b�Z�[�W�O�P");
        rs.addColumn(list2);
        
        List<String> list3 = new ArrayList<String>();
        list3.add("ja");
        rs.addColumn(list3);
        
        rs.first();

        // �e�X�g���{
        try {
            query.createDBMessageBean(rs);
            fail();
        } catch (SystemException e) {
            // ����
            assertEquals("errors.db.message.bean.mapping.sql.query",
                e.getErrorCode());
            assertTrue(LogUTUtil.checkError(
                "SQL for DB message returns 3 column(s) result set."));
        }
    }

    /**
     * testCreateDBMessageBean05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) rs:|null|null|<br>
     *                �Ƃ������e��ResultSet<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageBean:key->""<br>
     *                  value->""<br>
     *         (��ԕω�) ���O:�y�x�����O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "DBMessage resource keys contain null or empty."<br>
     *         
     * <br>
     * ResultSet�̑��J������null�ŁA�����J������null�̏ꍇ�A
     * �x�����O���o�͂��Akey, value�Ƃ��ɋ󕶎���̐ݒ肳�ꂽ
     * DBMessageBean���Ԃ���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDBMessageBean05() throws Exception {
        // �O����
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add(null);
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add(null);
        rs.addColumn(list2);
        
        rs.first();

        // �e�X�g���{
        DBMessageBean actual = query.createDBMessageBean(rs);
        
        // ����
        assertEquals("", 
            UTUtil.getPrivateField(actual, "key"));
        assertEquals("",
            UTUtil.getPrivateField(actual, "value"));
        assertTrue(LogUTUtil.checkWarn(
            "DBMessage resource keys contain null or empty."));
    }

    /**
     * testCreateDBMessageBean06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) rs:|""|""|<br>
     *                �Ƃ������e��ResultSet<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) DBMessageBean:key->""<br>
     *                  value->""<br>
     *         (��ԕω�) ���O:�y�x�����O�z<br>
     *                    �����b�Z�[�W��<br>
     *                    "DBMessage resource keys contain null or empty."<br>
     *         
     * <br>
     * ResultSet�̑��J�������󕶎���ŁA�����J�������󕶎���̏ꍇ�A
     * �x�����O���o�͂��Akey, value�Ƃ��ɋ󕶎���̐ݒ肳�ꂽDBMessageBean��
     * �Ԃ���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateDBMessageBean06() throws Exception {
        // �O����
        DataSource ds = null;
        String sql = null;
        DBMessageBeanMappingSqlQuery query 
            = new DBMessageBeanMappingSqlQuery(ds, sql);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        
        List<String> list1 = new ArrayList<String>();
        list1.add("");
        rs.addColumn(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("");
        rs.addColumn(list2);
        
        rs.first();

        // �e�X�g���{
        DBMessageBean actual = query.createDBMessageBean(rs);
        
        // ����
        assertEquals("", 
            UTUtil.getPrivateField(actual, "key"));
        assertEquals("",
            UTUtil.getPrivateField(actual, "value"));
        assertTrue(LogUTUtil.checkWarn(
            "DBMessage resource keys contain null or empty."));
    }

}
