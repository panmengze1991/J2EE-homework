package sc.ustc.Proxy;

import sc.ustc.handler.ExecutorHandler;
import sc.ustc.service.Executor;

import java.lang.reflect.Proxy;

public class ActionProxy {
    public Object getProxy(Object object) {
        ExecutorHandler executorHandler = new ExecutorHandler();
        executorHandler.setObject(object);
        return Proxy.newProxyInstance(Executor.class.getClassLoader(), object.getClass().getInterfaces(), executorHandler);
    }
}
