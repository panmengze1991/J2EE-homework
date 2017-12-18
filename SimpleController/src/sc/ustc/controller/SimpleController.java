package sc.ustc.controller;

import sc.ustc.Proxy.ExecutorProxy;
import sc.ustc.impl.ExecutorInterface;
import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;
import sc.ustc.model.Result;
import sc.ustc.service.Executor;
import sc.ustc.utils.SCUtil;
import sc.ustc.utils.XmlResolveHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class SimpleController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Action> actionList = null;
        List<Interceptor> interceptorList = null;


        resp.setContentType("text/html;charset=utf-8");

        // 解析请求的URL，并拆分出action的名称
        String url = req.getRequestURL().toString();
        String actionStr = url.substring(url.lastIndexOf('/') + 1, url.length() - 3);
        String resultStr = "";

        // action查找状态
        boolean findSuccess = false;
        // 获取参数map
        Map<String, String[]> parameterMap = req.getParameterMap();

        // 通过工具类获取配置文件解析结果
        XmlResolveHelper helper = SCUtil.getXmlResolveHelper();
        if (helper != null) {
            actionList = helper.getActionList();
            interceptorList = helper.getInterceptorList();
        }
        if (actionList == null) {
            return;
        }

        for (Action action : actionList) {
            if (action.getName().equals(actionStr)) {
                // find action & dispatch
                findSuccess = true;
                List<Interceptor> refInterceptorList = SCUtil.getRefInterceptorList(action, interceptorList);
//                try {
//                    // 通过反射获取类和对象
//                    Class actionClass = Class.forName(action.getClassPath());
//                    Object object = actionClass.newInstance();
//
//                    // 通过反射遍历设置对象属性
//                    Field[] fields = actionClass.getDeclaredFields();
//                    for (Field field : fields) {
//                        field.setAccessible(true);
//                        for (String key : parameterMap.keySet()) {
//                            if (key.equals(field.getName())) {
//                                field.set(object, (parameterMap.get(key))[0]);
//                            }
//                        }
//                    }
//
//                    // 通过反射调用方法
//                    Method method = actionClass.getMethod(action.getMethod());
//                    resultStr = (String) method.invoke(object);
//
//                    ExecutorProxy actionProxy = new ExecutorProxy();
//                    ExecutorInterface ai = (ExecutorInterface) actionProxy.getProxy(object,actionClass);
//                    ai.execute();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                // 遍历action对应的结果列表，寻找到对应的result并执行
                try {
                    // 设置代理，来执行interceptor以及action
                    ExecutorProxy executorProxy = new ExecutorProxy();
                    Executor executor = new Executor();
                    ExecutorInterface ai = (ExecutorInterface) executorProxy.getProxy(executor);
                    resultStr = (String) ai.execute(action, refInterceptorList, parameterMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Result result : action.getResultList()) {
                    if (result.getName().equals(resultStr)) {
                        if ("forward".equals(result.getType())) {
                            // 使用RequestDispatcher进行页面跳转
                            req.getRequestDispatcher(result.getValue()).forward(req, resp);
                        } else if ("redirect".equals(result.getType())) {
                            // 使用Redirect进行页面跳转
                            resp.sendRedirect(result.getValue());
                        }
                    } else {
                        PrintWriter out = resp.getWriter();
                        out.println("<html>"
                                + "<head><title>COS</title></head>"
                                + "<body>" + "没有请求的资源。" + "</body>"
                                + "</html>");
                    }
                }

                System.out.println("SC处理完成");
            }
        }

        // 如果没有查找到action，返回失败页面。
        if (!findSuccess) {
            PrintWriter out = resp.getWriter();
            out.println("<html>"
                    + "<head><title>COS</title></head>"
                    + "<body>" + "不可识别的 action 请求。" + "</body>"
                    + "</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
