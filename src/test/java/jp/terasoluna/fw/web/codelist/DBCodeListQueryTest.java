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
import java.util.List;

import javax.sql.DataSource;

import jp.terasoluna.utlib.MockDataSource;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mockrunner.mock.jdbc.MockResultSet;

/**
 * {@link jp.terasoluna.fw.web.codelist.DBCodeListQuery} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �f�[�^�x�[�X����R�[�h���X�g�擾���s�� RDBMS�I�y���[�V�����N���X�B<br>
 * ���̃N���X��jp.terasoluna.fw.web.codelist.DBCodeListLoader�ł̂ݗ��p�����B<br>
 * �E�O�����<br>
 * mapRow���\�b�h�̓X�[�p�[�N���X����Ăяo����A������rs��null��A��v�f����������Ȃ���Ԃ͑��݂��Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.codelist.DBCodeListQuery
 */
@SuppressWarnings("unused")
public class DBCodeListQueryTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DBCodeListQueryTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DBCodeListQueryTest(String name) {
        super(name);
    }

    /**
     * testDBCodeListQuery01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FCG
     * <br><br>
     * ���͒l�F(����) dataSource:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�udataSource is required�v�ƌ������b�Z�[�W��������InvalidDataAccessApiUsageException<br>
     *         
     * <br>
     * dataSource��null�̏ꍇ�udataSource is required�v�ƌ������b�Z�[�W��������InvalidDataAccessApiUsageException���������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBCodeListQuery01() throws Exception {

        try {
            // �e�X�g���{
            DBCodeListQuery query = new DBCodeListQuery(null, "abc");
        } catch (InvalidDataAccessApiUsageException e) {
            // ����
            assertEquals("dataSource is required", e.getMessage());
        }
    }

    /**
     * testDBCodeListQuery02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) dataSource:not null<br>
     *         (����) sql:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) jdbcTemplate��dataSource<br>
     *                    �i�e�N���XRdbmsOperation�̃t�B�[���h�j:not null<br>
     *         (��ԕω�) sql<br>
     *                    �i�e�N���XRdbmsOperation�̃t�B�[���h�j:null<br>
     *         
     * <br>
     * dataSourcet��not null�Asql��null�̏ꍇ���ꂼ��Anot null�Anull���t�B�[���h�ɃZ�b�g����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBCodeListQuery02() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();

        // �e�X�g���{
        DBCodeListQuery query = new DBCodeListQuery(ds, null);

        // ����
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        assertSame(ds, template.getDataSource());
        assertNull(UTUtil.getPrivateField(query, "sql"));

    }

    /**
     * testDBCodeListQuery03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) dataSource:not null<br>
     *         (����) sql:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) jdbcTemplate��dataSource<br>
     *                    �i�e�N���XRdbmsOperation�̃t�B�[���h�j:not null<br>
     *         (��ԕω�) sql<br>
     *                    �i�e�N���XRdbmsOperation�̃t�B�[���h�j:not null<br>
     *         
     * <br>
     * dataSourcet�Asql��not null�̏ꍇ����������Z�b�g����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDBCodeListQuery03() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();

        // �e�X�g���{
        DBCodeListQuery query = new DBCodeListQuery(ds, "abc");

        // ����
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        assertSame(ds, template.getDataSource());
        assertEquals("abc", UTUtil.getPrivateField(query, "sql"));
    }

    /**
     * testMapRow01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) ResultSet rs:not null<br>
     *         (���) ������1�J������:not null<br>
     *         (���) ������2�J������:���݂��Ȃ�<br>
     *         (���) ������3�J������:���݂��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) CodeBean:id��1�J�����ڂ̒l�Aname�ɋ󕶎�<br>
     *         
     * <br>
     * rs�̃J��������1�̏ꍇid��1�J�����ڂ̒l�Aname���󕶎���LocaleCodeBean���Ԃ邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRow01() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // �e�X�g�Ώۂ̃R���X�g���N�^���g�p���Ă��邽�߁A�ēx�ݒ肷��
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        List<String> list = new ArrayList<String>();
        list.add("value1");
        rs.addColumn("column1", list);
        rs.first();
        
        // �e�X�g���{
        LocaleCodeBean result = (LocaleCodeBean) query.mapRow(rs, 0);

        // ����
        assertEquals("value1", result.getId());
        assertEquals("", result.getName());
    }

    /**
     * testMapRow02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) ResultSet rs:not null<br>
     *         (���) ������1�J������:not null<br>
     *         (���) ������2�J������:not null<br>
     *         (���) ������3�J������:���݂��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) CodeBean:id��1�J�����ڂ̒l�Aname��2�J�����ڂ̒l<br>
     *         
     * <br>
     * rs�̃J��������2�̏ꍇid��1�J�����ڂ̒l�Aname��2�J�����ڂ̒l��������LocaleCodeBean���Ԃ邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRow02() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // �e�X�g�Ώۂ̃R���X�g���N�^���g�p���Ă��邽�߁A�ēx�ݒ肷��
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        List<String> list = new ArrayList<String>();
        list.add("value1");
        rs.addColumn("column1", list);
        List<String> list2 = new ArrayList<String>();
        list2.add("value2");
        rs.addColumn("column2", list2);
        rs.first();
        
        // �e�X�g���{
        LocaleCodeBean result = (LocaleCodeBean) query.mapRow(rs, 0);

        // ����
        assertEquals("value1", result.getId());
        assertEquals("value2", result.getName());
    }

    /**
     * testMapRow03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) ResultSet rs:not null<br>
     *         (���) ������1�J������:not null<br>
     *         (���) ������2�J������:not null<br>
     *         (���) ������3�J������:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) CodeBean:id��1�J�����ڂ̒l�Aname��2�J�����ڂ̒l<br>
     *         
     * <br>
     * rs�̃J��������3�̏ꍇid��1�J�����ڂ̒l�Aname��2�J�����ڂ̒l��������LocaleCodeBean���Ԃ�3�J�����ڂ͖�������邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRow03() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // �e�X�g�Ώۂ̃R���X�g���N�^���g�p���Ă��邽�߁A�ēx�ݒ肷��
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        List<String> list = new ArrayList<String>();
        list.add("value1");
        rs.addColumn("column1", list);
        List<String> list2 = new ArrayList<String>();
        list2.add("value2");
        rs.addColumn("column2", list2);
        List<String> list3 = new ArrayList<String>();
        list3.add("value3");
        rs.addColumn("column3", list3);
        rs.first();
        
        // �e�X�g���{
        LocaleCodeBean result = (LocaleCodeBean) query.mapRow(rs, 0);

        // ����
        assertEquals("value1", result.getId());
        assertEquals("value2", result.getName());
    }

    /**
     * testMapRow04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FCD
     * <br><br>
     * ���͒l�F(����) ResultSet rs:not null<br>
     *         (���) ������1�J������:null<br>
     *         (���) ������2�J������:null<br>
     *         (���) ������3�J������:���݂��Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) LocaleCodeBean���󕶎��Aname���󕶎�<br>
     *         
     * <br>
     * rs�̃J��������2�̏ꍇ��1�J�����ځA2�J�����ڂ�null�̏ꍇid�ɋ󕶎��Aname�ɋ󕶎��̒l��������CodeBean���Ԃ���邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMapRow04() throws Exception {
        // �O����
        DataSource ds = new MockDataSource();
        DBCodeListQuery query = new DBCodeListQuery(ds, null);
        
        // �e�X�g�Ώۂ̃R���X�g���N�^���g�p���Ă��邽�߁A�ēx�ݒ肷��
        JdbcTemplate template = (JdbcTemplate) UTUtil.getPrivateField(query,
                "jdbcTemplate");
        template.setDataSource(ds);
        UTUtil.setPrivateField(query, "sql", null);

        // �[��ResultSet�̐ݒ�
        MockResultSet rs = new MockResultSet("testid");
        rs.addColumn("column1");
        rs.addColumn("column2");
        
        // �e�X�g���{
        LocaleCodeBean result = (LocaleCodeBean) query.mapRow(rs, 0);

        // ����
        assertEquals("", result.getId());
        assertEquals("", result.getName());
    }

}
