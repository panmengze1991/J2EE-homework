package sc.water.ustc.interceptor;

import sc.ustc.impl.InterceptorInterface;
import sc.ustc.model.Action;

public class TestInterceptor implements InterceptorInterface{

    public void preAction(Action action) {
        System.out.println("----------TestInterceptor preAction");
    }

    @Override
    public void afterAction(Action action) {
        System.out.println("----------TestInterceptor afterAction");
    }
}
