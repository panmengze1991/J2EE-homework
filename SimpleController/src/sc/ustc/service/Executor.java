package sc.ustc.service;

import sc.ustc.impl.ExecutorInterface;
import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;

import java.util.List;
import java.util.Map;

public class Executor implements ExecutorInterface {
    @Override
    public Object execute(Action action, List<Interceptor> interceptorList, Map<String, String[]> parameterMap) {
        // 实际上并不会执行
        System.out.println("----------Executor");
        return "Executor";
    }
}
