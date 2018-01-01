package sc.ustc.controller;

import sc.ustc.Proxy.ExecutorProxy;
import sc.ustc.impl.ExecutorInterface;
import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;
import sc.ustc.model.Result;
import sc.ustc.service.Executor;
import sc.ustc.utils.ConfigResolveHelper;
import sc.ustc.utils.SCUtil;
import sc.ustc.utils.ViewResolveHelper;

import javax.servlet.ServletContext;
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
        ServletContext servletContext = req.getSession().getServletContext();
        System.out.println("开始");

        resp.setContentType("text/html;charset=utf-8");

        // 解析请求的URL，并拆分出action的名称
        String url = req.getRequestURL().toString();
        String actionStr = url.substring(url.lastIndexOf('/') + 1, url.length() - 3);
        String resultStr = "";

        // action查找状态
        boolean findAction = false;
        // result查找状态
        boolean findResult = false;
        // 查找不到action或result的页面提示语句
        String errorMsg;

        // 获取参数map
        Map<String, String[]> parameterMap = req.getParameterMap();

        // 通过工具类获取配置文件解析结果
        ConfigResolveHelper helper = SCUtil.getXmlResolveHelper(new ConfigResolveHelper(),
                "/WEB-INF/classes/controller.xml", servletContext);
        actionList = helper.getActionList();
        interceptorList = helper.getInterceptorList();

        for (Action action : actionList) {
            if (action.getName().equals(actionStr)) {

                // find action & dispatch
                findAction = true;
                List<Interceptor> refInterceptorList = SCUtil.getRefInterceptorList(action, interceptorList);

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
                        findResult = true;
                        if ("forward".equals(result.getType())) {
                            // 如果是forward
                            if (result.getValue().endsWith("_view.xml")) {
                                // result结尾是需要解析的xml时，打印对应的html
                                String html = SCUtil.getXmlResolveHelper(new ViewResolveHelper(), result.getValue(),
                                        servletContext).getView().getHtml();
                                System.out.println(html);
                                PrintWriter out = resp.getWriter();
                                out.println(html);
                            } else {
                                // 使用RequestDispatcher进行页面跳转
                                req.getRequestDispatcher(result.getValue()).forward(req, resp);
                            }
                        } else if ("redirect".equals(result.getType())) {
                            // 使用Redirect进行页面跳转
                            resp.sendRedirect(result.getValue());
                        }
                    }
                }
            }
        }

        //  如果没有查找到action，返回失败页面。
        if (!findAction || !findResult) {
            errorMsg = findAction ? "没有请求的资源。" : "不可识别的 action 请求。";
            PrintWriter out = resp.getWriter();
            out.println("<html>"
                    + "<head><title>COS</title></head>"
                    + "<body>" + errorMsg + "</body>"
                    + "</html>");
        }

        System.out.println("SC处理完成");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
