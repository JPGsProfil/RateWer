package com.example.wlg.ratewer.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.wlg.ratewer.Model.EditorData;
import com.example.wlg.ratewer.Model.EditorSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sabine
 */
public class EditorController {

    EditorData editor;
    String DataString;

    public  EditorController()
    {
        DataString = "";
        editor = new EditorData();
    }

    public void FillData(String text)
    {
        editor.fillSets(text);
    }

    public EditorSet GetSet(int _Position)
    {
        return editor.getSet(_Position);
    }

    public void RemoveSet(int _Position)
    {
        editor.removeSet(_Position);
    }

    public void FillList(int viewID)
    {
        for (int i =0 ; i < editor.getSetsSize(); i++)
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
            editor.fillSets(text);
            DataString = text;

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public String GetData()
    {
        return DataString;
    }

    public String GetSetName(int id)
    {

        String text = "";

        text = editor.getSet(id-1).getSetName();

        return text;
    }

    public int GetSetsSize()
    {
        return editor.getSetsSize();
    }

    public void AddNewSet(EditorSet _Set)
    {
        editor.addNewSet(_Set);
    }

    //write file
    public  void writeFile(Context _ctx)
    {
        String jsonString;
        //test json element erstellen und dann in die datei schreiben!
/*
        Log.d("", "start");
        String jsonString;
            ArrayList sets = new ArrayList();
            EditorSet testset = new EditorSet();
            testset.SetName("test1");
            testset.SetID(1);
            testset.SetAttributes("Job, Haarfarbe, Brille, test");
            testset.SetCard(new EditorCard(1, "card", "", null));
        Log.d("", "start2");

        EditorSet testset2 = new EditorSet();
            testset2.SetName("test 2");
            testset2.SetID(2);
            testset2.SetAttributes("Haarfarbe, Brille, X");
            testset2.SetCard(new EditorCard(1, "card", "", null));

            sets.add(testset);
            sets.add(testset2);
  */        Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonString = gson.toJson(editor.getSets());


        BufferedWriter bufferWriter = null;
        FileOutputStream outputStream;

        try
        {
            File file = new File(_ctx.getFilesDir() + "sets.json");

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
