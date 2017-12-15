package sc.ustc.impl;

import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;

import java.util.List;
import java.util.Map;

public interface ExecutorInterface {
    Object execute(Action action, List<Interceptor> interceptorList, Map<String, String[]> parameterMap);
}
