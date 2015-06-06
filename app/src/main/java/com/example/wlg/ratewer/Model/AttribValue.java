package com.example.wlg.ratewer.Model;

import org.w3c.dom.Attr;

/**
 * all cards have various categories (like hair) and values (e.g. black)
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

    /**
     * copy constructor
     * @param _attribValue source
     */
    public AttribValue(AttribValue _attribValue)
    {
        attr    = _attribValue.attr;
        value   = _attribValue.value;
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

    /**
     * own compare function to display values in option menu in specific order
     * @param _attr2 value 2
     * @return order
     */
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
            else if(this.value.equals("false") || this.value.equals("no") )
            {
                retval = "nein".compareTo("ja");
            }
            else
            {
                retval = this.value.compareTo(_attr2.value);
            }
        }
        return retval;
    }
}
