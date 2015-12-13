package com.example.wlg.ratewer.Network;

/**
 * Created by RareHue on 11.12.2015.
 */

public class Hello {

    private String id;
    private String content;

    public String getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public  Hello
        (String id, String content ) {
        this.id = id;
        this.content = content;
    }

}