package sc.ustc.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.model.Action;
import sc.ustc.model.Interceptor;
import sc.ustc.model.Result;

import java.util.ArrayList;
import java.util.List;

public class ControllerResolveHelper extends DefaultHandler{
    private List<Action> actionList;
    private List<Interceptor> interceptorList;
    private Action action;
    private Result result;
    private Interceptor interceptor;
    private String tagName;

    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    // 文件开始
    @Override
    public void startDocument() throws SAXException {
        this.actionList = new ArrayList<>();
        this.interceptorList = new ArrayList<>();
    }

    // 结点开始
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch (qName){
            case "action":
                this.action=new Action();
                this.action.setName(attributes.getValue(0));
                this.action.setClassPath(attributes.getValue(1));
                this.action.setMethod(attributes.getValue(2));
                break;
            case "result":
                this.result=new Result();
                this.result.setName(attributes.getValue(0));
                this.result.setType(attributes.getValue(1));
                this.result.setValue(attributes.getValue(2));
                break;
            case "interceptor-ref":
                this.action.addInterceptorRef(attributes.getValue(0));
                break;
            case "interceptor":
                this.interceptor=new Interceptor();
                this.interceptor.setName(attributes.getValue(0));
                this.interceptor.setClassPath(attributes.getValue(1));
                this.interceptor.setPreDo(attributes.getValue(2));
                this.interceptor.setAfterDo(attributes.getValue(3));
                break;
        }
        this.tagName=qName;
    }

    // 结点结束
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equals("action")){
            this.actionList.add(this.action);
            this.action = null;
        } else if(qName.equals("result")){
            this.action.addResult(this.result);
            this.result = null;
        } else if(qName.equals("interceptor")){
            this.interceptorList.add(this.interceptor);
        }
        this.tagName=null;
    }

    // 文件结束
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public List<Interceptor> getInterceptorList() {
        return this.interceptorList;
    }

    public void setInterceptorList(List<Interceptor> interceptorList) {
        this.interceptorList = interceptorList;
    }
}
