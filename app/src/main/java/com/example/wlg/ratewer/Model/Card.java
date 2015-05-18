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
    public List<AttribValue> attriList;
    //public String kategory;
    //public int androidId;
    //public int groupId;

    public Card( String _name, String _image, List<AttribValue> _attriList)
    {
        INIT_ID ++;
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
    }

}
