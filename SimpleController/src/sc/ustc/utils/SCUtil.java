package sc.ustc.utils;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.model.Action;
import sc.ustc.model.BaseBean;
import sc.ustc.model.Interceptor;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferStrategy

public class SCUtil {
    public static <T extends DefaultHandler> T getXmlResolveHelper(T handler, String path, ServletContext
            servletContext) {
        try {
            // 构建SAXParser
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // 加载资源文件 转化为一个输入流
            InputStream stream = servletContext.getResourceAsStream(path);
            // 调用parse()方法
            parser.parse(stream, handler);
            stream.close();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return handler;
    }

    public static List<Interceptor> getRefInterceptorList(Action action, List<Interceptor> interceptorList) {
        List<Interceptor> refInterceptorList = new ArrayList<>();
        for (String ref : action.getInterceptorRefList()) {
            for (Interceptor interceptor : interceptorList) {
                if (ref.equals(interceptor.getName())) {
                    refInterceptorList.add(interceptor);
                }
            }
        }
        return refInterceptorList;
    }

    /**
     * author:      Daniel
     * description: resultSet转换为对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseBean> T resultSetToBean(ResultSet rs, T proxy) throws Exception {
        //取得Method
        Method[] methods = proxy.getClass().getDeclaredMethods();
        // 用于获取列数、或者列类型
        ResultSetMetaData meta = rs.getMetaData();
        if (rs.next()) {
            // 循环获取指定行的每一列的信息
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                //  获取列名并设置方法名
                String colName = meta.getColumnName(i);
                String setMethodName = "set" + colName;
                // 遍历Method
                for (Method method : methods) {
                    if (method.getName().equalsIgnoreCase(setMethodName)) {
                        setMethodName = method.getName();
                        // 获取当前位置的值，返回Object类型
                        Object value = rs.getObject(colName);
                        if (value == null) {
                            continue;
                        }
                        try {
                            // 实行Set方法
                            Method setMethod = proxy.getClass().getMethod(
                                    setMethodName, value.getClass());
                            setMethod.invoke(proxy, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return proxy;
    }
}
