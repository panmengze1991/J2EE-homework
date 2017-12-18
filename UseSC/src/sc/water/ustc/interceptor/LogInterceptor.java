package sc.water.ustc.interceptor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sc.ustc.impl.InterceptorInterface;
import sc.ustc.model.Action;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogInterceptor implements InterceptorInterface {

    Document document;
    Element log;
    Element actionElement;
    File file = new File("\\log.xml");

    public void preAction(Action action) {
        System.out.println("----------LogInterceptor preAction");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String SysTime = df.format(new Date()); //获取当前系统时间,即为访问开始时间

        if (!file.exists()) {       //如果该日志文件不存在则创建该文件
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//创建文件工厂
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            log = document.createElement("log");
            // 创建子节点，并设置属性
            actionElement = document.createElement("actionElement");
            // 为action添加子节点
            Element name = document.createElement("name");
            name.setTextContent(action.getName());
            actionElement.appendChild(name);
            Element stime = document.createElement("s-time");
            stime.setTextContent(SysTime);
            actionElement.appendChild(stime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterAction(Action action) {
        System.out.println("----------LogInterceptor afterAction");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String SysTime = df.format(new Date()); //获取当前系统时间,即为访问结束时间

        String result = "success"; //设置返回值

        Element etime = document.createElement("e-time");
        etime.setTextContent(SysTime);
        actionElement.appendChild(etime);
        //返回结果
        Element eresult = document.createElement("result");
        eresult.setTextContent(result);
        actionElement.appendChild(eresult);


        // 为根节点添加子节点
        log.appendChild(actionElement);
        // 将根节点添加到Document下
        document.appendChild(log);
        // 创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();
        // 创建Transformer对象
        Transformer tf;
        try {
            //生成xml
            tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(document), new StreamResult(file));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
