package sc.water.ustc.action;

import sc.water.ustc.dao.UserDao;
import sc.water.ustc.model.UserBean;
import sc.water.ustc.utils.CommonUtils;

public class LoginAction {

    private String userName;
    private String password;

    // 登录处理
    public String handleLogin() {
        String resultMsg = "failure";
        if (CommonUtils.isNotEmpty(this.userName) && CommonUtils.isNotEmpty(this.password)) {
            UserBean userBean = new UserBean();
            userBean.setUserName(userName);
            userBean.setUserPass(password);
            resultMsg = userBean.signIn() ? "success" : "login_failure";
        }
        return resultMsg;
    }

}
