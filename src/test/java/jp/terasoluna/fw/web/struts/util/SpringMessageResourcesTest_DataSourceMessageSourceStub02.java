package jp.terasoluna.fw.web.struts.util;

import jp.terasoluna.fw.message.DataSourceMessageSource;

/**
 * {@link SpringMessageResourcesTest}�̎����̂��߂Ɏg�p�����B
 * 
 * {@link SpringMessageResourcesTest}����g�p�����B
 * 
 */
public class SpringMessageResourcesTest_DataSourceMessageSourceStub02 extends
        DataSourceMessageSource {
    
    /**
     * �R���X�g���N�^
     */
    public SpringMessageResourcesTest_DataSourceMessageSourceStub02() {
        this.dbMessageResourceDAO = 
            new SpringMessageResourcesTest_DBMessageResourceDAOImpl02(); 
    }
}
