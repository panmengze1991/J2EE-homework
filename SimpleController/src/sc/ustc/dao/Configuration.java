package sc.ustc.dao;

import sc.ustc.model.jdbc.JDBCClass;
import sc.ustc.model.jdbc.JDBCConfig;
import sc.ustc.utils.ConfigResolveHelper;
import sc.ustc.utils.SCUtil;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Author        Daniel
 * Class:        Configuration
 * Date:         2018/1/1 15:39
 * Description:  对象关系映射配置文件解析类
 */
public class Configuration {

    public void config(ServletContext servletContext) {
        ConfigResolveHelper helper = SCUtil.getXmlResolveHelper(new ConfigResolveHelper(),
                "/WEB-INF/classes/or_mapping.xml", servletContext);
        JDBCConfig jdbcConfig = helper.getJdbcConfig();
        List<JDBCClass> jdbcClassList = helper.getJdbcClassList();

        // 配置Conversation属性
        Conversation.setJdbcClassList(jdbcClassList);
        Conversation.setJdbcConfig(jdbcConfig);
    }
}

