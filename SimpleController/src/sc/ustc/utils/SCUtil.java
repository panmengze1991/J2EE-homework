package sc.ustc.utils;

import org.xml.sax.SAXException;
import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SCUtil {
    public static XmlResolveHelper getXmlResolveHelper(){
        try {
            // 构建SAXParser
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            // 实例化  DefaultHandler对象
            XmlResolveHelper parseXml=new XmlResolveHelper();
            // 加载资源文件 转化为一个输入流
            InputStream stream=XmlResolveHelper.class.getClassLoader().getResourceAsStream("controller.xml");
            // 调用parse()方法
            parser.parse(stream, parseXml);
            // 返回结果
            return parseXml;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Interceptor> getRefInterceptorList(Action action, List<Interceptor> interceptorList){
        List<Interceptor> refInterceptorList = new ArrayList<>();
        for(String ref: action.getInterceptorRefList()){
            for(Interceptor interceptor:interceptorList){
                if(ref.equals(interceptor.getName())){
                    refInterceptorList.add(interceptor);
                }
            }
        }
        return refInterceptorList;
    }
}
