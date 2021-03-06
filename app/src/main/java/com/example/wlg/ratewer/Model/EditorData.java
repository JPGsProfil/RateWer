package com.example.wlg.ratewer.Model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabine
 */
public class EditorData
{
    Object m_Data;
    JSONArray m_JSONArray;
    String fileName = "sets.json";
    List<EditorSet> sets;

    public EditorData()
    {
        m_Data = null;
        sets = new ArrayList<EditorSet>();

    }


    public String getFileName()
    {
        return  fileName;
    }

    public void fillSets(String text)
    {
        Gson gson = new Gson();
        EditorSet[] obj = gson.fromJson(text,EditorSet[].class);
        for (EditorSet set : obj) {
            addNewSet(set);
        }

    }

    public List<EditorSet> getSets()
    {
        return sets;
    }
    public void editSet(EditorSet _Set)
    {
        sets.add(_Set);
    }

    public void addNewSet(EditorSet _Set)
    {
        sets.add(_Set);
    }

    public void removeSet(int _Position)
    {
        sets.remove(_Position);
    }

    public EditorSet getSet(int _Position)
    {
        return sets.get(_Position);
    }

    public EditorSet getSetByID(int _ID)
    {
        return sets.get(_ID);
    }

    public JSONObject getCard(int _SetID, int _CardID)
    {
        return null;
    }

/*
    public void SetGameList(String viewName)
    {
        Gson gson = new Gson();
        EditorSet[] obj = gson.fromJson(text,EditorSet[].class);
        Log.d("test", obj[0].getSetName());

    }
*/


    public void setSet(int _SetID,String _Name, String _AttributList,ArrayList<EditorCard> _CardList)
    {
            sets.add(new EditorSet(_Name,_SetID,_AttributList,_CardList));
    }

    public int getSetsSize() {
        return sets.size();
    }
}

