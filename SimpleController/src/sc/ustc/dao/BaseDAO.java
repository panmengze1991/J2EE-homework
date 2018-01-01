package sc.ustc.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public abstract class BaseDAO {
    protected static String Url = "";
    protected static String Driver = "";
    protected static String User = "";
    protected static String Password = "";

    private Connection conn = null;
    protected Statement statement = null;

    public void openDBConnection() {
        try {
            Class.forName(Driver);// 指定连接类型
            conn = DriverManager.getConnection(Url, User, Password);// 获取连接
            statement = conn.createStatement();// 获取执行sql语句的statement对象
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDBConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract Object query(String sql);

    public abstract boolean insert(String sql);

    public abstract boolean update(String sql);

    public abstract boolean delete(String sql);
}
