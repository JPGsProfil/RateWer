package com.example.wlg.ratewer.IO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jean on 05.05.2015.
 */
public class JSONCards
{
    @SerializedName("id")
    private int id;

    private String name;

    private String hair;

    private String eye;

    @SerializedName("moustache")
    private Boolean moustache;

    @SerializedName("image")
    private String imageName;

    private int imageId;


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

    public String getImageName() { return imageName;  }

    public void setImageName(String imageName) { this.imageName = imageName; }

    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }
}
