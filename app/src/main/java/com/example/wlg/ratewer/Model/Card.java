package com.example.wlg.ratewer.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean on 21.04.2015.
 */
public class Card
{
    private static int INIT_ID = 0;
    public int id;
    public String name;
    public String image;
    public int viewID;
    public int imageID;
    public List<AttribValue> attriList;

    public Card( String _name, String _image, List<AttribValue> _attriList)
    {
        id          = INIT_ID;
        //groupId     = _groupId;
        attriList = new ArrayList<>();
        attriList = _attriList;
        image = _image;
        if(_name.equals(""))
        {
            _name = "er";
        }
        name = _name;
        viewID = 0;
        imageID = 0;
        INIT_ID ++;
    }

    // copy object
    public Card(Card _card)
    {
        id =_card.id;
        name = _card.name;
        image = _card.image;
        viewID = _card.viewID;
        imageID = _card.imageID;
        for(int index = 0; index < _card.attriList.size(); index ++)
        {
            AttribValue curAttriValue = new AttribValue(_card.attriList.get(index).attr,_card.attriList.get(index).value);
            attriList.add(curAttriValue);
        }
    }


}
