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
    public List<StringsToDisplayAttributs> attriList = new ArrayList<>();


    private String jsonAttriString;

    public AttributList(String _jsonAttriString)
    {
        jsonAttriString = _jsonAttriString;
        FillAttributeList(jsonAttriString);
    }


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
                    System.out.println("Kathegory " + curCat + "  values: " + val + "  Groupid " + groupId);
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


    }

}
