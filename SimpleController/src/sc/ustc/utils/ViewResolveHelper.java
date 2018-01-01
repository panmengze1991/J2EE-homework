package sc.ustc.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.model.view.Body;
import sc.ustc.model.view.Form;
import sc.ustc.model.view.Header;
import sc.ustc.model.view.View;
import sc.ustc.model.view.widgets.ButtonView;
import sc.ustc.model.view.widgets.CheckBoxView;
import sc.ustc.model.view.widgets.TextView;
import sc.ustc.model.view.widgets.Widget;

import java.util.ArrayList;
import java.util.List;

public class ViewResolveHelper extends DefaultHandler {
    private View view;
    private Header header;
    private Body body;
    private Form form;
    private List<Widget> widgetList;
    private Widget widget;
    private String tagName;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<Widget> getWidgetList() {
        return widgetList;
    }

    public void setWidgetList(List<Widget> widgetList) {
        this.widgetList = widgetList;
    }

    // 文件开始
    @Override
    public void startDocument() throws SAXException {
        this.view = new View();
        this.widgetList = new ArrayList<>();
    }

    // 结点开始
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
            case "header":
                this.header = new Header();
                break;
            case "body":
                this.body = new Body();
                break;
            case "form":
                this.form = new Form();
                break;
            case "textView":
                this.widget = new TextView();
                break;
            case "buttonView":
                this.widget = new ButtonView();
                break;
            case "checkBoxView":
                this.widget = new CheckBoxView();
                break;
        }
        this.tagName = qName;
    }

    // 结点结束
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName) {
            case "header":
                this.view.setHeader(this.header);
                break;
            case "body":
                this.view.setBody(this.body);
                break;
            case "form":
                this.body.setForm(this.form);
                break;
            case "textView":
            case "buttonView":
            case "checkBoxView":
                this.form.addWidgetList(widget);
                this.widget = null;
                break;
        }
        this.tagName = null;
    }

    // 文件结束
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        // 截取字段值
        String value = new String(ch, start, length);
        if (this.tagName == null) {
            return;
        }
        switch (this.tagName) {

            // header的标题
            case "title":
                this.header.setTitle(value);
                break;

            // form或widget的name属性
            case "name":
                if (this.widget != null) {
                    // 如果子项不为空，说明在子项内部
                    this.widget.setName(value);
                } else if (this.form != null) {
                    // 否则可能是在form中
                    this.form.setName(value);
                }
                break;

            // form的action属性
            case "action":
                this.form.setAction(value);
                break;

            // form的method属性
            case "method":
                this.form.setMethod(value);
                break;

            // widget的label属性
            case "label":
                this.widget.setLabel(value);
                break;

            // widget的value属性
            case "value":
                this.widget.setValue(value);
                break;

        }
    }

}
