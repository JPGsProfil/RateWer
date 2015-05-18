package com.example.wlg.ratewer.Model;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jean on 21.04.2015.
 * class for board options and properties
 */

public class Board
{
    public static final int COLUMN_PER_ROW = 4;   // later dynamic or depending on oriantation


   // public static final int AMOUNT_OF_PERSON = 24;
    // list for random placed cards (order)
    /*
    private List<Integer> cardsOrder; // to shuffle the cards
    public Board(int _amountOfCards)
    {
        cardsOrder = new ArrayList<>();
        for (int index = 0; index < _amountOfCards; ++index)
        {
            cardsOrder.add(index);
        }
        Collections.shuffle(cardsOrder);
    }

    public int GetCardAtIndex(int index)
    {
        return cardsOrder.get(index);
    }

    public int GetAmountOfCards()
    {
        return cardsOrder.size();
    }

*/




    //outsourced function of this class, instead left this:
    // delete it
   // public int returnNumerTwo()
   // {
    //    return 2;
   // }

}
