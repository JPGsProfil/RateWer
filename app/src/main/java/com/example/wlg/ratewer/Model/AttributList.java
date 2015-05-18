package com.example.wlg.ratewer.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jean on 18.05.2015.
 */
public class AttributList
{
    public List<Attributes> attriList = new ArrayList<>();


    private String jsonAttriString;

    public AttributList(String _jsonAttriString)
    {
        jsonAttriString = _jsonAttriString;
        FillAttributeList(jsonAttriString);
    }


    private void FillAttributeList(String jsonAttriString)
    {
        System.out.println("jsonString "+jsonAttriString);
        try
        {
            JSONObject reader = new JSONObject(jsonAttriString);
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


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}
