package sc.ustc.utils;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SCUtil {
    public static <T extends DefaultHandler> T getXmlResolveHelper(T handler, String path, ServletContext
            servletContext) {
        try {
            // 构建SAXParser
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // 加载资源文件 转化为一个输入流
            InputStream stream = servletContext.getResourceAsStream(path);
            // 调用parse()方法
            parser.parse(stream, handler);
            stream.close();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return handler;
    }

    public static List<Interceptor> getRefInterceptorList(Action action, List<Interceptor> interceptorList) {
        List<Interceptor> refInterceptorList = new ArrayList<>();
        for (String ref : action.getInterceptorRefList()) {
            for (Interceptor interceptor : interceptorList) {
                if (ref.equals(interceptor.getName())) {
                    refInterceptorList.add(interceptor);
                }
            }
        }
        return refInterceptorList;
    }
}
