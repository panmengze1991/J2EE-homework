package sc.ustc.dao;

import sc.ustc.Proxy.BeanProxy;
import sc.ustc.model.BaseBean;
import sc.ustc.model.jdbc.JDBCClass;
import sc.ustc.model.jdbc.JDBCConfig;
import sc.ustc.model.jdbc.Property;
import sc.ustc.utils.SCUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Author        Daniel
 * Class:        Conversation
 * Date:         2018/1/1 15:44
 * Description:  数据表操作，实现CRUD
 */
public class Conversation {
    private static List<JDBCClass> jdbcClassList;
    private static JDBCConfig jdbcConfig;

    private static Connection conn = null;
    private static Statement statement = null;

    static void setJdbcClassList(List<JDBCClass> jdbcClassList) {
        Conversation.jdbcClassList = jdbcClassList;
    }

    static void setJdbcConfig(JDBCConfig jdbcConfig) {
        Conversation.jdbcConfig = jdbcConfig;
    }

    public static void openDBConnection() {
        try {
            Class.forName(jdbcConfig.getDriverClass());// 指定连接类型
            conn = DriverManager.getConnection(jdbcConfig.getUrlPath(), jdbcConfig.getDbUserName(), jdbcConfig
                    .getDbUserPassword());// 获取连接
            statement = conn.createStatement();// 获取执行sql语句的statement对象
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeDBConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * author:      Daniel
     * description: 通过某个键值来查询对象
     */
    public static <T extends BaseBean> T getObject(T bean) {
        T proxy = null;
        JDBCClass jdbcClass = getCurrentORMap(bean);
        try {
            // 初始化懒加载列表，提供给代理判断
            List<Property> lazyPropertyList = new ArrayList<>();
            // 初始化非懒加载SQL语句
            StringBuilder builder = new StringBuilder("");
            for (Property property : jdbcClass.getPropertyList()) {
                if (!property.isLazy()) {
                    builder.append(property.getColumn());
                    builder.append(",");
                } else {
                    lazyPropertyList.add(property);
                }
            }
            System.out.println("正常加载启动："+builder.toString());
            ResultSet resultSet = query(bean, jdbcClass.getTable(), builder.substring(0, builder.length() - 1));

            // 获取cglib动态代理
            System.out.println("代理启动");
            BeanProxy beanProxy = new BeanProxy(lazyPropertyList, jdbcClass.getTable());
            proxy = (T) beanProxy.getProxy(bean.getClass());

            proxy = SCUtil.resultSetToBean(resultSet, proxy);
            proxy.setColumn(bean.getColumn());
            proxy.setValue(bean.getValue());
            System.out.println("正常加载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxy;
    }

    /**
     * author:      Daniel
     * description: 查表原子操作
     */
    public static ResultSet query(BaseBean bean, String table, String fieldName) {
        JDBCClass jdbcClass = getCurrentORMap(bean);
        try {
            // 提供任意单个参数查询，如果没定义列，默认使用xml定义的主键column
            String column = bean.getColumn() == null ? jdbcClass.getId().getName() : bean.getColumn();
            String select = "select " + fieldName;
            String from = " from " + table;
            String where = " where " + column + "='" + bean.getValue() + "'";
            // 查表
            return statement.executeQuery(select + from + where);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * author:      Daniel
     * description: 获取当前的映射对象
     */
    private static <T extends BaseBean> JDBCClass getCurrentORMap(T t) {
        // 获取并截取代理的真实类名
        String simpleName = t.getClass().getSimpleName();
        simpleName = simpleName.indexOf('$')>0?simpleName.substring(0,simpleName.indexOf('$')):simpleName;
        for (JDBCClass jdbcClass : jdbcClassList) {
            if (simpleName.equals(jdbcClass.getName())) {
                return jdbcClass;
            }
        }
        return new JDBCClass();
    }

    /**
     * author:      Daniel
     * description: 通过某个键值来删除对象
     */
    public static <T extends BaseBean> boolean deleteObject(T bean) {
        JDBCClass jdbcClass = getCurrentORMap(bean);
        try {
            String column = bean.getColumn() == null ? jdbcClass.getId().getName() : bean.getColumn();
            String delete = "delete from " + jdbcClass.getTable();
            String where = " where " + column + "='" + bean.getValue() + "'";
            return statement.executeUpdate(delete + where)>0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * author:      Daniel
     * description: 添加对象
     */
    public static <T extends BaseBean> boolean insertObject(T bean) {
        JDBCClass jdbcClass = getCurrentORMap(bean);
        try {
            // todo 插入对象sql语句构造
            String sql = "insert into user(...) values(...)";
            return statement.executeUpdate(sql)>0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * author:      Daniel
     * description: 更新对象
     */
    public static <T extends BaseBean> boolean updateObject(T bean) {
        JDBCClass jdbcClass = getCurrentORMap(bean);
        try {
            // todo 更新对象sql语句构造
            String sql = "insert into user(...) values(...)";
            return statement.executeUpdate(sql)>0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
