package com.example.wlg.ratewer.IO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jean on 05.05.2015.
 */
public class JSONCards
{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("hair")
    private String hair;

    @SerializedName("eye")
    private String eye;

    @SerializedName("moustache")
    private Boolean moustache;


    public int getId()
    {
        return id;
    }



    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public Boolean isMoustache() {
        return moustache;
    }

    public void setMoustache(Boolean moustache) {
        this.moustache = moustache;
    }
}
