package com.example.wlg.ratewer.Model;

/**
 * Created by RareHue on 28.11.2015.
 */
public class Card_New {

    private static int INIT_ID = 0;
    public int id;
    public String name;
    public String image;
    public int viewID;
    public int imageID;
    public boolean isVisible;
    public AttributeContainer_New attributes;

    public Card_New( String _name, String _image, AttributeContainer_New _attributes)
    {
        id          = generateNextID();
        attributes  = _attributes;
        image       = _image;
        name        = _name;
        viewID      = 0;
        imageID     = 0;
        isVisible   = true;

    }

    /// why would i clone a card ?
    /*
    public Card_New(Card_New _card)
    {
        id =_card.id;
        name = _card.name;
        image = _card.image;
        viewID = _card.viewID;
        imageID = _card.imageID;
        attributes = new AttributeContainer_New(attributes);
    }*/

    private static int generateNextID()
    {
        return ++INIT_ID;
    }

    public boolean DoesCardContainAttrValue(String _attributeName, String _value)
    {
        if(_attributeName.equals("Ist es?"))
        {
            if(_value.equals(name))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return attributes.contains(_attributeName,_value);
    }
}
