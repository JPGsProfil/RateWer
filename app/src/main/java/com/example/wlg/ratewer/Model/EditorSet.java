package com.example.wlg.ratewer.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rofel on 21.02.2016.
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

    public String getAttributes()
    {
        return Eigenschaften;
    }


    public void SetName(String _Name)
    {
        Name= _Name;
    }


    public void SetID(int _ID)
    {
        ID = _ID;
    }

    public void SetCard(EditorCard _Card)
    {

        int count = Cards.size();
        Cards.add(_Card);
    }

    public void SetAttributes(String _Attri)
    {
        Eigenschaften = _Attri;
    }
}
