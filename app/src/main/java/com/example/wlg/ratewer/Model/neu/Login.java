package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 02.02.2016.
 */
public class Login {

    private String Name;
    private String Password;

    public Login(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
