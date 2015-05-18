package com.example.wlg.ratewer.IO;

/**
 * Created by Jean on 17.05.2015.
 */
public class Attributes
{
    private static int INIT_ID = 0;
    public int id;
    public int groupId;
    public String attribute;
    public String kategory;
    public String question;
    public int androidId;

    public Attributes(String _kategory, String _attribute, String _question, int _groupId)
    {
        INIT_ID ++;
        id          = INIT_ID;
        kategory   = _kategory;
        groupId     = _groupId;
        attribute   = _attribute;
        question    = _question;
        //androidId   = _androidId;
    }
}
