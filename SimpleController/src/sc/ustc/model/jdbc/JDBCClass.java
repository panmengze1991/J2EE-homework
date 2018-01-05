package sc.ustc.model.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * Author        Daniel
 * Class:        JDBCClass
 * Date:         2018/1/1 18:19
 * Description:  解析O/R映射得到的类关系映射
 */
public class JDBCClass {
    private String name;
    private String table;
    private Id id;
    private List<Property> propertyList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public void addPropertyList(Property property){
        if(this.propertyList == null){
            this.propertyList = new ArrayList<>();
        }
        this.propertyList.add(property);
    }
}
