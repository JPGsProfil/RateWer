package com.example.wlg.ratewer.Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jean on 18.05.2015.
 */
public class CardList
{
    public List<Card> m_List ;
    //private String jsonCardString;

    public CardList(String _jsonCardString)
    {
        m_List = new ArrayList<>();
        FillCardList(_jsonCardString);
        ShuffleList();
        System.out.println("geshuffelt!!!");
    }

    // copy cardlist
    public CardList(CardList _cardList)
    {
            m_List = new ArrayList<>();
            for(int index=0; index < _cardList.m_List.size(); index ++)
            {
                //System.out.println("vor copy Card: ");
                //System.out.println("Groesse uebergebener Cardlist: "+_cardList.m_List.size());
                //System.out.println("_cardList.m_List.get(index) "+_cardList.m_List.get(index).name);
                Card cardToAdd = new Card(_cardList.m_List.get(index));   // should be a copy

                //System.out.println("vor ABsturz: "+cardToAdd.name);
                m_List.add(cardToAdd);

            }



    }

    /**
     * used to compare ChosenCard of the enemy with the card from current cardList
     * necessary because cardList is dynamic
     * some cards will be removed each turn
     *
     * @param _cardId each card has it's own id for identification
     * @return current index of the card with this id
     */
    public int GetIndexFromCardId(int _cardId)
    {
        for(int i=0; i<m_List.size(); i++)
        {
            if(m_List.get(i).id == _cardId)
            {
                return i;
            }
        }
        return 0;   //only if no card found, should not happen
    }

    /**
     * if player is asking for an attribute which the enemy card doesn't have -> player can flip all cards with this attribute
     * @param _attr category
     * @param _value    the value depending to the category
     */
    public void RemoveCardsWithAttriValue(String _attr, String _value)
    {
        // backwards because deleting arrayentries backwards is much more easy
        for(int i = m_List.size()-1; i > 0; i--)
        {
            boolean hasAttrBeenFound = m_List.get(i).DoesCardContainAttrValue(_attr,_value);
            if (hasAttrBeenFound)
            {
                m_List.remove(i);
            }
        }
    }

    /**
     * if player is asking for an attribute which the enemy card has -> player can flip all cards without this attribute (many !!)
     * @param _attr category
     * @param _value    the value depending to the category
     */
    public void RemoveCardsWithoutAttriValue(String _attr, String _value)
    {
        for(int i = m_List.size()-1; i > 0; i--)
        {
            boolean hasAttrBeenFound = m_List.get(i).DoesCardContainAttrValue(_attr,_value);
            if (!hasAttrBeenFound)
            {
                m_List.remove(i);
            }
        }
    }


    /**
     * called at the beginning
     * changes order of cards to make game more varied
     */
    private void ShuffleList()
    {
        Collections.shuffle(m_List);
        // after shuffle -> id doesn't match anymore
        for(int index = 0; index < m_List.size(); index++)
        {
            m_List.get(index).id = index;
        }
    }

    /**
     * amount of cards in this list
     * so you don't have to call CardList.mList.size()
     * @return
     */
    public int GetSize()
    {
        if(m_List == null)
        {
            return 0;
        }
        else
        {
            return m_List.size();
        }

    }


    /**
     * get cards from json
     * save them into list
     * only done one time (at the beginning of the game)
     * @param _jsonCardString
     */
    private void FillCardList(String _jsonCardString)
    {
        //System.out.println("jsonString "+_jsonCardString);
        try
        {
            JSONObject JSONcomplete = new JSONObject(_jsonCardString);
            JSONArray allCards = JSONcomplete.getJSONArray("cards");
            //JSONArray allCards = new JSONArray(_jsonCardString);
            for(int index = 0; index < allCards.length(); index ++)
            {
                String curName = "";
                String curimage = "";
                //String curId = "0";
                List<AttribValue> curAttrValueList = new ArrayList<>();

                JSONObject curCardobj = new JSONObject(allCards.get(index).toString());
                Iterator curCardKeysIt = curCardobj.keys();
                //Iterator curCardValueIt = ;
                while (curCardKeysIt.hasNext())
                {
                    String curCat = (String) curCardKeysIt.next();
                    String curValue = curCardobj.getString(curCat);
                    // handle bool
                    // not necessary if well formed json
                    if (curValue.equals("")  || curValue.equals("0") || curValue.equals("false"))
                    {
                        curValue = "no";
                    }
                    else
                    {
                        if( curValue.equals("1") || curValue.equals("true"))
                        {
                            curValue = "yes";
                        }
                    }

                    //System.out.println("curCat " + curCat + "   value: "+curValue);
                    if(curCat.equals("name"))
                    {
                        curName = curValue;
                    }
                    else if(curCat.equals("image"))
                    {
                        curimage = curValue;
                    }

                    else if(curCat.equals("id"))
                    {
                        //curId = curValue; not needed, shuffle later
                    }
                    else
                    {
                        AttribValue curAttribValue = new AttribValue(curCat,curValue);
                        curAttrValueList.add(curAttribValue);
                    }
                }
                Card curCard = new Card(curName,curimage,curAttrValueList);
                m_List.add(curCard);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
