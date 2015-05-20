package com.example.wlg.ratewer.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jean on 18.05.2015.
 */
public class AttributList
{
    public List<StringsToDisplayAttributs> attriList = new ArrayList<>();


    private String jsonAttriString;

    public AttributList(CardList _cardList)
    {
        //jsonAttriString = _jsonAttriString;
        //FillAttributeList(jsonAttriString);
        FillAttributeListFromCardList(_cardList);
    }



    private void FillAttributeListFromCardList(CardList _cardList)
    {
        List<AttribValue> attribValueList = new ArrayList<>();
        //KeyValuesUnique.add(new AttribValue("ja","n"));

        for(int index1 = 0; index1 < _cardList.GetSize(); index1 ++)
        {
            for(int iValues = 0; iValues < _cardList.m_List.get(0).attriList.size(); iValues ++)
            {
                String curAttrib    = _cardList.m_List.get(index1).attriList.get(iValues).attr;
                String curValue     = _cardList.m_List.get(index1).attriList.get(iValues).value;
                AttribValue newAttribValue = new AttribValue(curAttrib,curValue);
                // check whether list contains this key value
                if(!attribValueList.contains(newAttribValue))
                {
                    attribValueList.add(newAttribValue);
                }
            }
        }

        // to display in cathegories and order (menu)
        Collections.sort(attribValueList);
        // debugging: display if works:
        System.out.println("Print Set!!!!!");
        for(int index = 0; index <attribValueList.size(); index++)
        {
            System.out.println(attribValueList.get(index).attr+"   "+attribValueList.get(index).value);
        }


        String prevAttrib = "-1";
        int curGroupId = 0;
        // now we know how many values contain to one attribute -> make list with groupid ...
        for(int index = 0; index < attribValueList.size(); index ++)
        {
            if(!attribValueList.get(index).attr.equals(prevAttrib))
            {
                curGroupId++;
            }
            String curAttr = attribValueList.get(index).attr;
            String curValue = attribValueList.get(index).value;

            attriList.add(new StringsToDisplayAttributs(curAttr, curValue,curAttr,curGroupId));
            prevAttrib =curAttr;
        }

    }


    /* old version, read from separate json-object
    private void FillAttributeList(String jsonAttriString)
    {
        //System.out.println("jsonString "+jsonAttriString);
        try
        {
            JSONObject JSONcomplete = new JSONObject(jsonAttriString);
            JSONObject attribandquestions = JSONcomplete.getJSONObject("attribandquestions");
            //JSONObject reader = new JSONObject(jsonAttriString);
            JSONObject jkategories  = attribandquestions.getJSONObject("attributes");

            // Get questions
            JSONObject jquestions = attribandquestions.getJSONObject("questionobj");
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
                    int groupminus1=groupId-1;
                    System.out.println("groupId-1 "+groupminus1);
                    System.out.println("Kathegory " + curCat + "  values: " + val + "  Groupid " + groupId + " Frage: "+question);
                    if( jquestionsArray.length() >= groupId)
                    {
                        attriList.add(new StringsToDisplayAttributs(curCat, val, question ,groupId));
                    }
                    else
                    {
                        // json file incorrect, fill Question with "Frage 1,2,3,...)
                        String Frage = "Frage"+i;
                        attriList.add(new StringsToDisplayAttributs(curCat, val,Frage,groupId));
                    }
                }
                groupId++;
            }



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }*/

}
