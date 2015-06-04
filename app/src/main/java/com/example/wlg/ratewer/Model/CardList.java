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
    public List<Card> m_List = new ArrayList<>();
    //private String jsonCardString;

    public CardList(String _jsonCardString)
    {
        FillCardList(_jsonCardString);
        ShuffleList();
        System.out.println("geshuffelt!!!");
    }

    // copy cardlist
    public CardList(CardList _cardList)
    {

            for(int index=0; index < _cardList.m_List.size(); index ++)
            {
                System.out.println("vor copy Card: ");
                System.out.println("Groeße uebergebener Cardlist: "+_cardList.m_List.size());
                System.out.println("_cardList.m_List.get(index) "+_cardList.m_List.get(index).name);
                Card cardToAdd = new Card(_cardList.m_List.get(index));   // should be a copy

                System.out.println("vor ABsturz: "+cardToAdd.name);
                m_List.add(cardToAdd);

            }



    }



    private void ShuffleList()
    {
        Collections.shuffle(m_List);
    }

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
                String curId = "0";
                List<AttribValue> curAttrValueList = new ArrayList<>();

                JSONObject curCardobj = new JSONObject(allCards.get(index).toString());
                Iterator curCardKeysIt = curCardobj.keys();
                //Iterator curCardValueIt = ;
                while (curCardKeysIt.hasNext())
                {
                    String curCat = (String) curCardKeysIt.next();
                    String curValue = curCardobj.getString(curCat);
                    // handle bool
                    if (curValue.equals("") || curValue.equals(0) || curValue.equals("0") || curValue.equals("false"))
                    {
                        curValue = "no";
                    }
                    else
                    {
                        if(curValue.equals(1) || curValue.equals("1") || curValue.equals("true"))
                        {
                            curValue = "yes";
                        }
                    }

                    //System.out.println("curCat " + curCat + "   value: "+curValue);
                    if(curCat.equals("name"))
                    {
                        curName = curValue;
                    }
                    else
                    if(curCat.equals("image"))
                    {
                        curimage = curValue;
                    }
                    else
                    if(curCat.equals("id"))
                    {
                        curId = curValue;
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
