package sc.ustc.model.jdbc;

/**
 * Author        Daniel
 * Class:        Property
 * Date:         2018/1/1 18:28
 * Description:  O/R映射值的属性
 */
public class Property {
    private String name;
    private String column;
    private String type;
    private boolean lazy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }
}
