package com.example.wlg.ratewer.Model;

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
     * @param _Value value 2
     * @return order
     */
    @Override
    public int compareTo(AttribValue _Value)
    {
        //write code here for compare name
        int result= this.attr.compareTo(_Value.attr);
        if(result == 0)    // same attrib -> look for value
        {
            if(this.value.equals("true") || this.value.equals("yes"))
            {
                result = "ja".compareTo("nein");
            }
            else if(this.value.equals("false") || this.value.equals("no") )
            {
                result = "nein".compareTo("ja");
            }
            else
            {
                result = this.value.compareTo(_Value.value);
            }
        }
        return result;
    }
}
