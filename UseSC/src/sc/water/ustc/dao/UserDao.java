package sc.water.ustc.dao;

import sc.ustc.dao.BaseDAO;
import sc.water.ustc.model.UserBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends BaseDAO {

    public UserDao() {
//        // mysql
//        Url = "jdbc:mysql://127.0.0.1:3306/COS";
//        Driver = "com.mysql.jdbc.Driver";
//        User = "root";
//        Password = "qq452705789";

        // sqlite
        Url = "jdbc:sqlite://c:/sqlite/SQLiteStudio/COS.db";
        Driver = "org.sqlite.JDBC";
    }

    @Override
    public UserBean query(String sql) {
        UserBean user = null;
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                user = new UserBean();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPass(resultSet.getString("userPass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean insert(String sql) {
        return executeUpdate(sql);
    }

    @Override
    public boolean update(String sql) {
        return executeUpdate(sql);
    }

    @Override
    public boolean delete(String sql) {
        return executeUpdate(sql);
    }

    private boolean executeUpdate(String sql) {
        int result;
        try {
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            result = 0;
            e.printStackTrace();
        }
        return result > 0;
    }
}
