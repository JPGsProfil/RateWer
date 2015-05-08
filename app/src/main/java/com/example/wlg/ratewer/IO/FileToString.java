package com.example.wlg.ratewer.IO;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jean on 08.05.2015.
 */
public class FileToString
{
    public static String ReadTextFile(InputStream _InputStream)
    {
        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String json = null;
        try
        {
            // http://stackoverflow.com/questions/19945411/android-java-how-can-i-parse-a-local-json-file-from-assets-folder-into-a-listvi (08.05.15)
            int size = _InputStream.available();
            byte[] buffer = new byte[size];
            _InputStream.read(buffer);
            _InputStream.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException e)
        {
            Log.d("File reading failed", e.getMessage());
        }

        return json;
    }
}
