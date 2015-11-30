package com.example.wlg.ratewer.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RareHue on 28.11.2015.
 */
public class Attribute_New implements Comparable<Attribute_New>{

    private String name;
    private List<String> values;

    /*
    escpially useful for the questions
     */
    public  Attribute_New(String _name, String _value)
    {
        name = _name;

        List singleAttributList = new ArrayList<String>();
        singleAttributList.add(_value);

    }

    public Attribute_New(String _name, List<String> _values)
    {
        name     = _name;
        values   = _values;
    }

    /**
     * copy constructor
     * @param _other source
     */
    public Attribute_New(Attribute_New _other)
    {
        name    = _other.name;
        values   = _other.values;
    }


    public String getName() {
        return this.name;
    }

    public boolean contains(String _value) {
        return values.contains(_value);
    }

    @Override public boolean equals(Object _other)
    {
        if (!(_other instanceof Attribute_New))
        {
            return false;
        }
        else
        {
            if(this.name.equals (((Attribute_New) _other).name) && this.values.equals (((Attribute_New) _other).values))
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
     * @param _other value 2
     * @return order
     */
    @Override
    public int compareTo(Attribute_New _other)
    {
        return this.name.compareTo(name);

    }
}
