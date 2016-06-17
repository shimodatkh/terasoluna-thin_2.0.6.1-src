package jp.terasoluna.fw.web.struts.util;

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.message.DBMessage;
import jp.terasoluna.fw.message.DBMessageResourceDAO;

/**
 * {@link SpringMessageResourcesTest}の試験のために使用される。
 * 
 * {@link SpringMessageResourcesTest}から使用される。
 * 
 */
public class SpringMessageResourcesTest_DBMessageResourceDAOImpl01 implements
        DBMessageResourceDAO {

    public List<DBMessage> findDBMessages() {
        List<DBMessage> list = new ArrayList<DBMessage>();
        DBMessage message = new DBMessage(
                "property.message.key", "ja", "JP", null, "テストDBメッセージ");
        list.add(message);
        return list;
    }

}
