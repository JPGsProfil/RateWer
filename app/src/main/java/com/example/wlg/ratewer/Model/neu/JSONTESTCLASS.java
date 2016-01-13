package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 13.01.2016.
 */
public class JSONTESTCLASS {

    private String origin;

    @Override
    public String toString() {
        return "{" +
                "\"origin\": \"" + origin + "\"" +
                "}";
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }



}
