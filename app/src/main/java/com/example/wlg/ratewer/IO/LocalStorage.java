package com.example.wlg.ratewer.IO;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jean on 12.02.2016.
 */
public class LocalStorage
{


    public static int getUserId(Context _context)
    {
        SharedPreferences prefs = _context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        int restoredId = prefs.getInt("idName", 0);
        return restoredId;
    }


    public static void updateUserId(int _userId, Context _context)
    {
        SharedPreferences sharedpreferences = _context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("userid", _userId);
        editor.apply();
    }
}
