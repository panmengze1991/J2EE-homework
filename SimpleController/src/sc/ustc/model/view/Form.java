package sc.ustc.model.view;

import sc.ustc.impl.HtmlInterface;
import sc.ustc.model.view.widgets.Widget;

import java.util.ArrayList;
import java.util.List;

public class Form implements HtmlInterface {
    private String name;
    private String action;
    private String method;
    private List<Widget> widgetList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Widget> getWidgetList() {
        return widgetList;
    }

    public void setWidgetList(List<Widget> widgetList) {
        this.widgetList = widgetList;
    }

    public void addWidgetList(Widget widget){
        if(this.widgetList == null){
            this.widgetList = new ArrayList<>();
        }
        this.widgetList.add(widget);
    }

    @Override
    public String getHtml() {
        StringBuilder builder = new StringBuilder();
        String header = "<form name=\""+this.getName()+"\" action=\""+this.action+"\" method=\""+this.method+"\">\n";
        builder.append(header);
        for(Widget widget:widgetList){
            builder.append(widget.getHtml());
        }
        String footer = "</form>\n";
        builder.append(footer);

        return builder.toString();
    }
}
