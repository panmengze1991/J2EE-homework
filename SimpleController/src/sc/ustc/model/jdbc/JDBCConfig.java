package sc.ustc.model.jdbc;

/**
 * Author        Daniel
 * Class:        JDBCConfig
 * Date:         2018/1/1 18:18
 * Description:  解析O/R映射得出的JDBC配置
 */
public class JDBCConfig {
    private String  driverClass;
    private String  urlPath;
    private String  dbUserName;
    private String  dbUserPassword;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbUserPassword() {
        return dbUserPassword;
    }

    public void setDbUserPassword(String dbUserPassword) {
        this.dbUserPassword = dbUserPassword;
    }
}
