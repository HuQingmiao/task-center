package com.mucfc.taskcenter.control;

import com.mucfc.taskcenter.common.BasicForm;

/**
 *
 * Created by HuQingmiao on 2015/5/18
 */
public class LoginForm  extends BasicForm {

    private String username;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
