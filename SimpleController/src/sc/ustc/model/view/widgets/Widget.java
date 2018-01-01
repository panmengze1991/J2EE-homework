package sc.ustc.model.view.widgets;

        import sc.ustc.impl.HtmlInterface;

public abstract class Widget implements HtmlInterface {
    private String name;
    private String label;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
