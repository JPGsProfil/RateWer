package com.example.wlg.ratewer.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabine on 21.02.2016.
 */
public class EditorSet {

    String Name;
    int ID;
    //int userID ; //creator
    String Eigenschaften;
    List<EditorCard> Cards;

    public EditorSet()
    {
        ID = -1;
        Name = "";
        Eigenschaften= "";
        Cards = new ArrayList<EditorCard>();
    }

    public EditorSet(String _Name,int _ID, String _Attr)
    {
        ID = _ID;
        Name = _Name;
        Eigenschaften = _Attr;

        Cards = new ArrayList<EditorCard>();
    }

    public EditorSet(String _Name,int _ID, String _Attr,ArrayList<EditorCard> _Cards)
    {
        ID = _ID;
        Name = _Name;
        Eigenschaften = _Attr;
        for (int i = 0 ; i < _Cards.size(); i++)
        {
            Cards.set(i, new EditorCard(_Cards.get(i)));
        }
    }


    public EditorSet(EditorSet _Set)
    {
        ID = _Set.getID();
        Name = _Set.getSetName();
        Eigenschaften = _Set.getAttributes();
        for (int i = 0 ; i < _Set.Cards.size(); i++)
        {
            Cards.set(i, new EditorCard(_Set.getCard(i)));
        }
    }

    public String getSetName()
    {
        return Name;
    }


    public int getID()
    {
        return ID;
    }

    public EditorCard getCard(int index)
    {
        return Cards.get(index);
    }

    public int getCardCount() {return Cards.size();}

    public String getAttributes()
    {
        return Eigenschaften;
    }


    public void setName(String _Name)
    {
        Name= _Name;
    }


    public void setID(int _ID)
    {
        ID = _ID;
    }

    public void addCard(EditorCard _Card)
    {
        Cards.add(_Card);
    }

    public void setCard(int _Pos,EditorCard _Card)
    {
        EditorCard tmpCard = Cards.get(_Pos);
        tmpCard.setCardName(_Card.getCardName());
        tmpCard.setCardImagePath(_Card.getCardImagePath());
        tmpCard.setCardAttribute(0, _Card.getCardAttribute(0));
        tmpCard.setCardAttribute(1,_Card.getCardAttribute(1));
        tmpCard.setCardAttribute(2,_Card.getCardAttribute(2));

    }

    public void removeCard(int _Pos)
    {
        Cards.remove(_Pos);
    }

    public void setAttributes(String _Attri)
    {
        Eigenschaften = _Attri;
    }
}
