package sc.ustc.model.view;

import sc.ustc.impl.HtmlInterface;

public class View implements HtmlInterface {
    private Header header;
    private Body body;

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

    @Override
    public String getHtml() {
        String header = "<html>\n";
        String footer = "</html>\n";
        return header + this.header.getHtml()+body.getHtml()+footer;
    }
}
