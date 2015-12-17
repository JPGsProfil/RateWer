package com.example.wlg.ratewer.Model;

import java.util.Iterator;
import java.util.List;

/**
 * Created by RareHue on 28.11.2015.
 */
public class AttributeContainer_New {

    private List<Attribute_New> attributes;

    public AttributeContainer_New()
    {
        attributes.clear();
    }

    public AttributeContainer_New(AttributeContainer_New _other) {
        attributes.addAll(_other.attributes);
    }

    public void clearAttributes()
    {
        attributes.clear();
    }

    public void fillAttributes(CardList _cardList)
    {
        // todo: kann erst nach neuer kartenliste gemacht werden
        // temporäre Liste machen ???
        //neues Attribut?
        // nein -> neuer wert?
        //füllen
    }

    public void updateAttributes(CardList _cardList)
    {
        clearAttributes();
        fillAttributes(_cardList);
    }

    public boolean contains(String _AttributName, String _AttributValue)
    {
        boolean result = false;
        Attribute_New attribute;
        Iterator<Attribute_New> AttributIterator = attributes.iterator();
        while(AttributIterator.hasNext() && !result)
        {
            attribute = AttributIterator.next();
            if (attribute.getName().equals(_AttributName)) {
             result |= attribute.contains(_AttributValue);
            }
        }
        return result;
    }



}
