package sc.ustc.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sc.ustc.model.jdbc.Id;
import sc.ustc.model.jdbc.JDBCClass;
import sc.ustc.model.jdbc.JDBCConfig;
import sc.ustc.model.jdbc.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Author        Daniel
 * Class:        ConfigResolveHelper
 * Date:         2018/1/1 15:39
 * Description:  对象关系映射配置文件解析类
 */
public class ConfigResolveHelper extends DefaultHandler {
    private JDBCConfig jdbcConfig;
    private List<JDBCClass> jdbcClassList;
    private Id id;
    private Property property;
    private JDBCClass jdbcClass;

    private String tagName;
    private String currentObject = "";
    private String currentJDBCProperty = "";

    public JDBCConfig getJdbcConfig() {
        return jdbcConfig;
    }

    public List<JDBCClass> getJdbcClassList() {
        return jdbcClassList;
    }

    // 文件开始
    @Override
    public void startDocument() throws SAXException {
        this.jdbcConfig = new JDBCConfig();
        this.jdbcClassList = new ArrayList<>();
    }

    // 结点开始
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
            case "jdbc":
                this.currentObject = qName;
                break;
            case "class":
                this.jdbcClass = new JDBCClass();
                this.currentObject = qName;
                break;
            case "property":
                if ("class".equals(this.currentObject)) {
                    this.property = new Property();
                }
                break;
            case "id":
                if ("class".equals(this.currentObject)) {
                    this.id = new Id();
                }
                break;
        }
        this.tagName = qName;
    }

    // 结点结束
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName) {
            case "property":
                if ("class".equals(this.currentObject)) {
                    this.jdbcClass.addPropertyList(this.property);
                }
                this.property = null;
                break;
            case "id":
                this.jdbcClass.setId(this.id);
                break;
            case "class":
                this.jdbcClassList.add(this.jdbcClass);
                this.jdbcClass = null;
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
            // jdbc property或jdbc class或jdbc class property的标题
            case "name":
                setJDBCName(value);
                break;
            // jdbc property值
            case "value":
                setJDBCProperty(value);
                break;
            // jdbc class 属性值
            case "table":
                this.jdbcClass.setTable(value);
                break;
            // jdbc class property 属性值
            case "column":
                this.property.setColumn(value);
                break;
            case "type":
                this.property.setType(value);
                break;
            case "lazy":
                this.property.setLazy(Boolean.valueOf(value));
                break;
        }
    }

    /**
     * author:      Daniel
     * description: 设置name标签对应属性
     */
    private void setJDBCName(String value) {
        if (this.property != null) {
            this.property.setName(value);
        } else if (this.id != null) {
            this.id.setName(value);
        } else if ("jdbc".equals(this.currentObject)) {
            this.currentJDBCProperty = value;
        } else if ("class".equals(this.currentObject)) {
            this.jdbcClass.setName(value);
        }
    }

    /**
     * author:      Daniel
     * description: 设置JDBC参数
     */
    private void setJDBCProperty(String value) {
        switch (this.currentJDBCProperty) {
            case "driver_class":
                this.jdbcConfig.setDriverClass(value);
                break;
            case "url_path":
                this.jdbcConfig.setUrlPath(value);
                break;
            case "db_username":
                this.jdbcConfig.setDbUserName(value);
                break;
            case "db_userpassword":
                this.jdbcConfig.setDbUserPassword(value);
                break;
        }
    }
}

