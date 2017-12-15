package sc.ustc.impl;

import sc.ustc.model.Action;

public interface InterceptorInterface {
    void preAction(Action action);
    void afterAction(Action action);
}
