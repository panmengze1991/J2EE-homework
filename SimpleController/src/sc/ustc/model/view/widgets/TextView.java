package sc.ustc.model.view.widgets;

public class TextView extends Widget {
    @Override
    public String getHtml() {
        return "<input type=\"text\" value=\""+this.getValue()+"\""+" name=\""+this.getName()+"\"/>\n";
    }
}
