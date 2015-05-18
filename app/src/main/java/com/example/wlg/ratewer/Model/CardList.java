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


    private void FillCardList(String _jsonCardString)
    {
        System.out.println("jsonString "+_jsonCardString);
        try
        {
            JSONArray allCards = new JSONArray(_jsonCardString);
            for(int index = 0; index < allCards.length(); index ++)
            {
                String cardStri = allCards.get(index).toString();
                //System.out.println("cardstri[i] " + cardStri);
                JSONObject curCard = new JSONObject(allCards.get(index).toString());
                Iterator curCardKeysIt = curCard.keys();
                //Iterator curCardValueIt = ;
                while (curCardKeysIt.hasNext())
                {
                    String curCat = (String) curCardKeysIt.next();
                    String curValue = curCard.getString(curCat);
                    //System.out.println("curCat " + curCat + "   value: "+curValue);
                    AttribValue curAttribValue = new AttribValue(curCat,curValue);
                    //m_cardList.add(new Card())
                }

                //JSONObject jkategories  = reader.getJSONObject("attributes");
            }
/*
            JSONObject reader = new JSONObject(_jsonCardString);
            JSONObject jkategories  = reader.getJSONObject("attributes");

            // Get questions
            JSONObject jquestions = reader.getJSONObject("questionobj");
            JSONArray jquestionsArray  = jquestions.getJSONArray("questions");

            // end of: get questions


            Iterator curCategoryIt = jkategories.keys();
            int groupId = 1;
            while (curCategoryIt.hasNext())
            {
                String curCat = (String) curCategoryIt.next();
                //System.out.println("Kathegory: "+curCat);
                String question = jquestionsArray.get(groupId-1).toString();
                JSONArray values  = jkategories.getJSONArray(curCat);
                for(int i=0;i<values.length(); i++)
                {
                    String val = values.get(i).toString();
                    System.out.println("Kathegory " + curCat + "  values: " + val + "  Groupid " + groupId);
                    if( jquestionsArray.length() >= groupId)
                    {
                        //System.out.println("Bin in if jquestionobj, Laenge: "+jquestionsArray.length());
                        // regular add (should be called always, otherwhise json file not correct
                        // System.out.println("question3: "+question + " val: "+val);
                        attriList.add(new Attributes(curCat, val, question ,groupId));
                    }
                    else
                    {
                        // System.out.println("Bin in else jquestionobj");
                        // json file incorrect, fill Question with "Frage 1,2,3,...)
                        String Frage = "Frage"+i;
                        attriList.add(new Attributes(curCat, val,Frage,groupId));
                    }

                }

                groupId++;

            }


            //System.out.println("Attrisize: "+attriList.size());
*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }



}
