package com.example.wlg.ratewer.Model.neu;

/**
 * Created by RareHue on 10.01.2016.
 */
public class Attribute {

    private int ID;
    private String Name;
    private int Cardset_ID;
    private String UpdateDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCardset_ID() {
        return Cardset_ID;
    }

    public void setCardset_ID(int cardset_ID) {
        Cardset_ID = cardset_ID;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }
}
