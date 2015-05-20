package com.example.wlg.ratewer.Model;

import org.w3c.dom.Attr;

/**
 * Created by Jean on 18.05.2015.
 */
public class AttribValue implements Comparable<AttribValue>
{
    public String attr;
    public String value;

   public AttribValue(String _attr, String _value)
    {
        attr    = _attr;
        value   = _value;
    }


    @Override public boolean equals(Object _second)
    {
        if (!(_second instanceof AttribValue))
        {
            return false;
        }
        else
        {
            if(this.attr.equals (((AttribValue) _second).attr) && this.value.equals (((AttribValue) _second).value))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    @Override
    public int compareTo(AttribValue _attr2)
    {
        //write code here for compare name
        int retval= this.attr.compareTo(_attr2.attr);
        if(retval == 0)    // same attrib -> look for value
        {
            if(this.value.equals("true") || this.value.equals("yes"))
            {
                retval = "ja".compareTo("nein");
            }
            else
            {
                retval = this.value.compareTo(_attr2.value);
            }
        }
        return retval;
    }
}
