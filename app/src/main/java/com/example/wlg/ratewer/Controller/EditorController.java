package com.example.wlg.ratewer.Controller;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.example.wlg.ratewer.Model.EditorCard;
import com.example.wlg.ratewer.Model.EditorData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.wlg.ratewer.Model.EditorSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by sabine
 */
public class EditorController {

    EditorData editor;

    public  EditorController()
    {
        editor = new EditorData();
    }

    public void FillList(int viewID)
    {
        for (int i =0 ; i <editor.GetSetsSize(); i++)
        {
            editor.getSet(i).getSetName();
        }
    }
    //read file
    public  void readFile(Context _ctx)
    {
        StringBuffer buffer = new StringBuffer();


        try
        {
            File file = new File(_ctx.getFilesDir() + "sets.json");
            FileReader fileReader = new FileReader(file);

            StringBuffer output = new StringBuffer();
            BufferedReader reader = new BufferedReader(fileReader);
            String read;
            String text = "";
            if (file != null)
            {
                while ((read = reader.readLine()) != null)
                {
                    buffer.append(read + "\n");
                    text = (text + read);
                    Log.d("",read);
                }
            }
            fileReader.close();
            Gson gson = new Gson();
            editor.FillSets(text);

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public int GetSetsSize()
    {
        return editor.GetSetsSize();
    }
    //write file
    public  void writeFile(Context _ctx)
    {
        //test json element erstellen und dann in die datei schreiben!

        Log.d("", "start");
        String jsonString;
            ArrayList sets = new ArrayList();
            EditorSet testset = new EditorSet();
            testset.SetName("test1");
            testset.SetID(1);
            testset.SetCard(new EditorCard(1, "card", "", null));
        Log.d("", "start2");

        EditorSet testset2 = new EditorSet();
            testset2.SetName("test1");
            testset2.SetID(1);
            testset2.SetCard(new EditorCard(1, "card", "", null));

            sets.add(testset);
            sets.add(testset2);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonString = gson.toJson(sets);


        BufferedWriter bufferWriter = null;
        FileOutputStream outputStream;
        try
        {
            File file = new File(_ctx.getFilesDir() + "sets.json");
            Log.d("",_ctx.getFilesDir().toString());

            if (!file.exists())
            {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(jsonString);
        }catch(IOException e)
        {
            e.printStackTrace();
        }finally {
            try {
                if (bufferWriter != null)
                {
                    bufferWriter.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
