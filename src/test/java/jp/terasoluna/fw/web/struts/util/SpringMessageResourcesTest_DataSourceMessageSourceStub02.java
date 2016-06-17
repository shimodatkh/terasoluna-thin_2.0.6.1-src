package jp.terasoluna.fw.web.struts.util;

import jp.terasoluna.fw.message.DataSourceMessageSource;

/**
 * {@link SpringMessageResourcesTest}の試験のために使用される。
 * 
 * {@link SpringMessageResourcesTest}から使用される。
 * 
 */
public class SpringMessageResourcesTest_DataSourceMessageSourceStub02 extends
        DataSourceMessageSource {
    
    /**
     * コンストラクタ
     */
    public SpringMessageResourcesTest_DataSourceMessageSourceStub02() {
        this.dbMessageResourceDAO = 
            new SpringMessageResourcesTest_DBMessageResourceDAOImpl02(); 
    }
}
