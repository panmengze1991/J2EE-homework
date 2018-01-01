package sc.ustc.model.view.widgets;

public class CheckBoxView extends Widget {
    @Override
    public String getHtml() {
        return "<input type=\"checkbox\" value=\""+this.getValue()+"\""+" name=\""+this.getName()+"\"/>\n";
    }
}
