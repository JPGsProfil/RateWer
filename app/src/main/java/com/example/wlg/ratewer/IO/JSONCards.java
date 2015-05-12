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

    private Boolean isWoman;

    private Boolean wearGlasses;

    @SerializedName("image")
    private String imageName;

    private int imageId;

    private int viewId;



    public int GetId()
    {
        return id;
    }



    public void SetId(int id)
    {
        this.id = id;
    }

    public String GetName()
    {
        return name;
    }

    public void SetName(String name)
    {
        this.name = name;
    }

    public String GetHair() {
        return hair;
    }

    public void SetHair(String hair)
    {
        this.hair = hair;
    }

    public String GetEye() {
        return eye;
    }


    public Boolean HasMoustache()
    {
        return moustache;
    }


    public String GetImageName() { return imageName;  }

    public int GetImageId()
    {
        return imageId;
    }

    public void SetImageId(int imageId)
    {
        this.imageId = imageId;
    }

    public Boolean IsWoman()
    {
        return isWoman;
    }




    public Boolean IsWearGlasses()
    {
        return wearGlasses;
    }


    public String GetGender()
    {
        if(isWoman == true)
        {
            return "Frau";
        }
        else
        {
            return "Mann";
        }
    }

    public void SetMoustache(Boolean moustache)
    {
        this.moustache = moustache;
    }

    public void SetIsWoman(Boolean isWoman)
    {
        this.isWoman = isWoman;
    }

    public void SetWearGlasses(Boolean wearGlasses)
    {
        this.wearGlasses = wearGlasses;
    }

    public int GetViewId()
    {
        return viewId;
    }

    public void SetViewId(int viewId)
    {
        this.viewId = viewId;
    }
}
