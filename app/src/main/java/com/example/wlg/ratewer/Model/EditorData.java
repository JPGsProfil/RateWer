package com.example.wlg.ratewer.Model;

import android.util.Log;

import com.example.wlg.ratewer.Controller.EditorController;
import com.example.wlg.ratewer.Model.neu.Cardset;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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

    public void FillSets(String text)
    {
        Gson gson = new Gson();
        EditorSet[] obj = gson.fromJson(text,EditorSet[].class);
        for (EditorSet set : obj) {
            addNewSet(set);
        }

    }

    public void editSet(EditorSet _Set)
    {
        sets.add(_Set);
    }

    public void addNewSet(EditorSet _Set)
    {
        sets.add(_Set);
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

    public int GetSetsSize() {
        return sets.size();
    }
}

