package sc.ustc.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sc.ustc.dao.Conversation;
import sc.ustc.model.BaseBean;
import sc.ustc.model.jdbc.Property;
import sc.ustc.utils.SCUtil;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;

/**
 * author:      Daniel
 * description: 代理Bean
 */
public class BeanProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();
    private List<Property> lazyPropertyList;
    private String tableName;

    public BeanProxy(List<Property> lazyPropertyList, String tableName) {
        this.lazyPropertyList = lazyPropertyList;
        this.tableName = tableName;
    }

    /**
     * author:      Daniel
     * description: 获取Proxy
     */
    public Object getProxy(Class clazz) {
        //设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    /**
     * author:      Daniel
     * description: 实现MethodInterceptor接口方法
     */
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        // 如果方法开头为get，并且取到的值为空
        if (method.getName().startsWith("get") && proxy.invokeSuper(obj, args)==null) {
            System.out.println("懒加载启动："+method.getName());
            String fieldName = method.getName().substring(3, method.getName().length());
            char[] nameChars = fieldName.toCharArray();
            nameChars[0] += 32;
            fieldName = String.valueOf(nameChars);

            for (Property property : lazyPropertyList) {
                if (property.getName().equals(fieldName)) {
                    // 需要懒加载
                    Conversation.openDBConnection();
                    ResultSet resultSet = Conversation.query((BaseBean) obj, tableName, fieldName);
                    obj = SCUtil.resultSetToBean(resultSet, (BaseBean) obj);
                    Conversation.closeDBConnection();
                }
            }
            System.out.println("懒加载完成："+method.getName());
        }
        //通过代理类调用父类中的方法
        return proxy.invokeSuper(obj, args);
    }
}