package com.example.wlg.ratewer.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabine
 */
public class EditorCard
{
    public int id;
    public String name;
    public String image; //image path on the device
    public List<String> attriList; // attributes of this card (hair = yellow ...), extra class for options menu

    public EditorCard(int _id, String _name, String _image, List<String> _attriList)
    {
        id = _id;
        attriList = new ArrayList<>();
        attriList = _attriList;
        image = _image;
        if(_name.equals(""))
        {
            _name = "er";
        }
        name = _name;
    }

    // copy object
    public EditorCard(EditorCard _card)
    {
        attriList = new ArrayList<>();
        //System.out.println("Bin in copy constructor von Card");
        id =_card.id;
        name = _card.name;
        image = _card.image;
        //System.out.println("AttriValue List Groesse: "+_card.attributList.size());
        for(int index = 0; index < _card.attriList.size(); index ++)
        {

            String curAttriValue = _card.attriList.get(index);
            //System.out.println("curAttriValue " + curAttriValue.attr);
            attriList.add(curAttriValue);
        }
        //System.out.println("Fertig mit cpy Card, neue attrvalList: "+attributList.size());
    }

    public boolean DoesCardContainAttrValue(String _attr, String _val)
    {
        if(_attr.equals("Ist es?"))
        {
            if(_val.equals(name))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        for (int i=0; i<attriList.size(); i++)
        {
            if(attriList.get(i).equals(_attr))
            {
                if(attriList.get(i).equals(_val))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
