package com.example.wlg.ratewer.Model.to_erase;

import com.example.wlg.ratewer.Model.to_erase.Attribute_New;

/**
 * Created by RareHue on 30.11.2015.
 */
public class Question_New {

    private boolean isAttributQuestion; // anything else would be guessing the cards name
    Attribute_New attribut; // attribute name and value
    String cardName;

    Question_New(Attribute_New _attribute)
    {
        isAttributQuestion = true;
        attribut = _attribute;
    }

    Question_New(String _cardName)
    {
        isAttributQuestion = false;
        cardName = _cardName;
    }

}
