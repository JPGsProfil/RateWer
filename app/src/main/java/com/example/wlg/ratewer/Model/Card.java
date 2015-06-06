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
    public boolean isVisible;
    public List<AttribValue> attriList; // attributes of this card (hair = yellow ...), extra class for options menu

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
        isVisible = true;
        INIT_ID ++;
    }

    // copy object
    public Card(Card _card)
    {
        attriList = new ArrayList<>();
        //System.out.println("Bin in copy constructor von Card");
        id =_card.id;
        name = _card.name;
        image = _card.image;
        viewID = _card.viewID;
        imageID = _card.imageID;
        //System.out.println("AttriValue List Groesse: "+_card.attriList.size());
        for(int index = 0; index < _card.attriList.size(); index ++)
        {

            AttribValue curAttriValue = new AttribValue(_card.attriList.get(index));
            //System.out.println("curAttriValue " + curAttriValue.attr);
            attriList.add(curAttriValue);
        }
        //System.out.println("Fertig mit cpy Card, neue attrvalList: "+attriList.size());
    }

    public boolean DoesCardContainAttrValue(String _attr, String _val)
    {
        for (int i=0; i<attriList.size(); i++)
        {
            if(attriList.get(i).attr.equals(_attr))
            {
                if(attriList.get(i).value.equals(_val))
                {
                    return true;
                }
                else    // may not have 2 attributes (hair = yellow, hair = black) -> if value doesn't match, break)
                {
                    return false;
                }
            }
        }
        return false;
    }


}
