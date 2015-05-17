package com.example.wlg.ratewer.IO;

/**
 * Created by Jean on 17.05.2015.
 */
public class Attributes
{
    private static int INIT_ID = 0;
    public int id;
    public String attribute;
    public String question;
    public int androidId;

    public Attributes(String _attribute, String _question)
    {
        INIT_ID ++;
        id          = INIT_ID;
        attribute   = _attribute;
        question    = _question;
        //androidId   = _androidId;
    }
}
