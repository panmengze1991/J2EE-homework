package sc.ustc.model.view.widgets;

public class ButtonView extends Widget {
    @Override
    public String getHtml() {
        return "<input type=\"submit\" value=\""+this.getValue()+"\"/>\n";
    }
}
