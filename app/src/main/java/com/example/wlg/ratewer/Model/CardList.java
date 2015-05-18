package com.example.wlg.ratewer.Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jean on 18.05.2015.
 */
public class CardList
{
    public List<Card> m_cardList;
    private String jsonCardString;

    public CardList(String _jsonCardString)
    {
        m_cardList = new ArrayList<>();
        jsonCardString = _jsonCardString;
        FillCardList(jsonCardString);
    }

    public int GetLenght()
    {
        return m_cardList.size();
    }


    private void FillCardList(String _jsonCardString)
    {
        System.out.println("jsonString "+_jsonCardString);
        try
        {
            JSONArray allCards = new JSONArray(_jsonCardString);
            for(int index = 0; index < allCards.length(); index ++)
            {
                //Card curCard = new Card();
                //String cardStri = allCards.get(index).toString();
                //System.out.println("cardstri[i] " + cardStri);
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
                m_cardList.add(curCard);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }



}
