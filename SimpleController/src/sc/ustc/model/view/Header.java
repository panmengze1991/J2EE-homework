package sc.ustc.model.view;

import sc.ustc.impl.HtmlInterface;

public class Header implements HtmlInterface {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getHtml() {
        String header = "<head>\n<title>";
        String footer = "</title>\n</head>\n";
        return header + this.getTitle() + footer;
    }
}
