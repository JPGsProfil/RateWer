package com.example.wlg.ratewer.Model;

/**
 * Created by Jean on 21.04.2015.
 */
public class StringsToDisplayAttributs
{
    private static int INIT_ID = 0;
    public int id;
    public int groupId;
    public String attr;
    public String value;
    //public String kategory;
    //public String question;
    public int androidId;

    public StringsToDisplayAttributs(String _attribute, String _value, int _groupId)
    {
        INIT_ID ++;
        id          = INIT_ID;
        //kategory   = _kategory;
        groupId     = _groupId;
        attr   = _attribute;
        value       = _value;
        //question    = _question;
        //androidId   = _androidId;
    }

    // copy function
    public StringsToDisplayAttributs(StringsToDisplayAttributs _stringsToDisplayAttributs)
    {
        id          = _stringsToDisplayAttributs.id;
        groupId     = _stringsToDisplayAttributs.groupId;
        attr        = _stringsToDisplayAttributs.attr;
        value       = _stringsToDisplayAttributs.value;
    }




}
