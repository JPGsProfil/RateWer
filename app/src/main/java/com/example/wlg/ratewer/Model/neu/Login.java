package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 02.02.2016.
 */
public class Login {

    private String name;
    private String password;

    public Login(String _name, String _password) {
        name = _name;
        password = _password;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }
}
