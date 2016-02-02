package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 02.02.2016.
 */
public class LoginResult {

    final   String ACCEPT = "accepted";
    private String result; //Can be "accepted" or "<ErrorMessage>"

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        result = result;
    }

    @Override
    public String toString() {
        return result;
    }

    public boolean checkResult()
    {
        return result.equals(ACCEPT);
    }
}
