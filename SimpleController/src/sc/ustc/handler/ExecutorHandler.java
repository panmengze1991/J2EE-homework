package sc.ustc.handler;

import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;
import sc.ustc.model.InterceptorInstance;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ExecutorHandler implements InvocationHandler {

    private Object object;

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 传入的action
        Action action = (Action) args[0];
        // 传入的interceptor列表
        List<Interceptor> interceptorList = (List<Interceptor>) args[1];
        // 参数map
        Map<String, String[]> parameterMap = (Map<String, String[]>) args[2];
        // 存储interceptor的栈
        Stack<InterceptorInstance> interceptorStack = new Stack<>();
        // 返回的结果字符串
        String resultStr ;

        try {
            // 通过反射获取类和对象
            Class actionClass = Class.forName(action.getClassPath());
            Object actionObject = actionClass.newInstance();

            // 通过反射遍历设置对象属性
            Field[] fields = actionClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                for (String key : parameterMap.keySet()) {
                    if (key.equals(field.getName())) {
                        field.set(actionObject, (parameterMap.get(key))[0]);
                    }
                }
            }

            // 通过反射调用方法
            // interceptor pre
            for (Interceptor interceptor:interceptorList) {
                Class interceptorClass = Class.forName(interceptor.getClassPath());
                Object interceptorObject = interceptorClass.newInstance();
                Class type = Action.class;
                Method preDo = interceptorClass.getMethod(interceptor.getPreDo(),type);
                Method afterDo = interceptorClass.getMethod(interceptor.getAfterDo(),type);
                preDo.invoke(interceptorObject,action);
                // interceptor入栈
                interceptorStack.push(new InterceptorInstance(interceptorObject,afterDo));
            }

            // action

            Method actionMethod = actionClass.getMethod(action.getMethod());
            resultStr = (String) actionMethod.invoke(actionObject);
            System.out.println("do action: resultStr= " + resultStr);

            // interceptor after
            while (!interceptorStack.empty()){
                InterceptorInstance interceptorInstance = interceptorStack.pop();
                Class type = Action.class;
                interceptorInstance.getMethod().invoke(interceptorInstance.getInterceptor(),action);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultStr = "failure";
        }
        return resultStr;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
