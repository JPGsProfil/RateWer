package com.example.wlg.ratewer.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabine
 */
public class EditorCard
{
    public int ID;
    public String Name;
    public String ImagePath; //image path on the device
    public List<String> AttriList; // attributes of this card (hair = yellow ...), extra class for options menu

    public EditorCard()
    {
        ID = -1;
        AttriList = new ArrayList<String>();
        ImagePath = "";
        Name = "";
    }

    public EditorCard(int _id, String _name, String _image, List<String> _attriList)
    {
        ID = _id;
        AttriList = new ArrayList<>();
        AttriList = _attriList;
        ImagePath = _image;
        if(_name.equals(""))
        {
            _name = "Karte";
        }
        Name = _name;
    }

    // copy object
    public EditorCard(EditorCard _card)
    {
        AttriList = new ArrayList<>();
        ID =_card.ID;
        Name = _card.Name;
        ImagePath = _card.ImagePath;
        for(int index = 0; index < _card.AttriList.size(); index ++)
        {
            String curAttriValue = _card.AttriList.get(index);
            AttriList.add(curAttriValue);
        }
    }

    public String getCardName(){ return Name; }

    public String getCardImagePath(){ return ImagePath; }

    public String getCardAttribute(int pos){return AttriList.get(pos);}

    public void setCardName(String _CardName){ Name = _CardName; }

    public void setCardImagePath(String _ImagePath){ ImagePath = _ImagePath; }

    public void setCardAttribute(int pos, String _Attr){AttriList.set(pos,_Attr);}

    public void addCardAttribute(String _Attr){AttriList.add(_Attr);}

    public void setID(int _ID){ID = _ID;}

    public int getID(){return ID;}

    public boolean doesCardContainAttrValue(String _attr, String _val)
    {
        if(_attr.equals("Ist es?"))
        {
            if(_val.equals(Name))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        for (int i=0; i<AttriList.size(); i++)
        {
            if(AttriList.get(i).equals(_attr))
            {
                if(AttriList.get(i).equals(_val))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
