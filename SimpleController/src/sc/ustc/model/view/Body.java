package sc.ustc.model.view;

import sc.ustc.impl.HtmlInterface;

public class Body implements HtmlInterface {
    private Form form;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public String getHtml() {
        String header = "<body>\n";
        String footer = "</body>\n";
        return header + form.getHtml() + footer;
    }
}
