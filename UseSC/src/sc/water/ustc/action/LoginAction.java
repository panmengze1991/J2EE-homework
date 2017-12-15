package sc.water.ustc.action;

import sc.water.ustc.utils.CommonUtils;

public class LoginAction {

    private String userName;
    private String password;

    public String handleLogin() {

        if (CommonUtils.isNotEmpty(this.userName) && CommonUtils.isNotEmpty(this.password)) {
            return "success";
        } else {
            return "failure";
        }
    }

}
