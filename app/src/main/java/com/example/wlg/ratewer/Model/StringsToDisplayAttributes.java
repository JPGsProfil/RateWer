package com.example.wlg.ratewer.Model;

/**
 *
 * used for option menu
 * map attributes to option menu id ...
 */
public class StringsToDisplayAttributes
{
    private static int INIT_ID = 0;
    public int id;      // option menu id
    public int groupId; // option menu group id
    public String attr; // category displayed in option menu
    public String value; // value
    //public String kategory;
    //public String question;
    //public int androidId;


    /**
     * constructor
     * @param _attribute category displayed in option menu
     * @param _value value displayed in option menu entry
     * @param _groupId to order option menu entries
     */
    public StringsToDisplayAttributes(String _attribute, String _value, int _groupId)
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
    public StringsToDisplayAttributes(StringsToDisplayAttributes _stringsToDisplayAttributes)
    {
        id          = _stringsToDisplayAttributes.id;
        groupId     = _stringsToDisplayAttributes.groupId;
        attr        = _stringsToDisplayAttributes.attr;
        value       = _stringsToDisplayAttributes.value;
    }




}
