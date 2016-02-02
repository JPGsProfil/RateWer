package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 02.02.2016.
 */
public class LoginResult {

    final   String ACCEPT = "accepted";
    private String Result; //Can be "accepted" or "<ErrorMessage>"

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return Result;
    }

    public boolean checkResult()
    {
        return Result.equals(ACCEPT);
    }
}
