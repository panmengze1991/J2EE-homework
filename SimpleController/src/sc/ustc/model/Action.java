package sc.ustc.model;

import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private String classPath;
    private String method;
    private List<String> interceptorRefList;
    private List<Result> resultList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public void addResult(Result result){
        if(resultList == null){
            resultList = new ArrayList<>();
        }
        resultList.add(result);
    }

    public List<String> getInterceptorRefList() {
        return interceptorRefList;
    }

    public void setInterceptorRefList(List<String> interceptorRefList) {
        this.interceptorRefList = interceptorRefList;
    }

    public void addInterceptorRef(String ref){
        if(interceptorRefList == null){
            interceptorRefList = new ArrayList<>();
        }
        interceptorRefList.add(ref);
    }
}
