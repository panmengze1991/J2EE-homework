package sc.water.ustc.model;

import sc.ustc.dao.Conversation;
import sc.ustc.model.BaseBean;
import sc.water.ustc.dao.UserDao;

/**
 * Author        Daniel
 * Class:        UserBean
 * Date:         2018/1/3 14:22
 * Description:  UserBean，包含了父类中查表需要的键值对
 */
public class UserBean extends BaseBean{
    private Integer userId;
    private String userName;
    private String userPass;

    public UserBean(){}

    public UserBean(String value) {
        super(value);
    }

    public UserBean(String value, String column) {
        super(value, column);
    }

    public boolean signIn() {
        UserDao userDao = new UserDao();
        userDao.openDBConnection();
        UserBean user = userDao.query(new UserBean(userName,"userName"));
        userDao.closeDBConnection();
        return this.userPass.equals(user.getUserPass());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }


}
