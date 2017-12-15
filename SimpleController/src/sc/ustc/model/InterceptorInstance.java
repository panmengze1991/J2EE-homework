package sc.ustc.model;

import java.lang.reflect.Method;

public class InterceptorInstance {
    private Object interceptor;
    private Method method;

    public InterceptorInstance(Object interceptor, Method method) {
        this.interceptor = interceptor;
        this.method = method;
    }

    public Object getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Object interceptor) {
        this.interceptor = interceptor;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
