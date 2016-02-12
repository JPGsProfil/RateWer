package com.example.wlg.ratewer.IO;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jean on 12.02.2016.
 */
public class LocalStorage
{
    public static final String PREFS_NAME = "LOCALPREFERENCES";
    public static final String PREFS_KEY = "USER_ID";


    public static int getUserId(Context _context)
    {
        int restoredId = 0;
        SharedPreferences settings;
        settings = _context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        restoredId = settings.getInt(PREFS_KEY, 0);
        return restoredId;
    }


    public static void updateUserId(Context _context, int _userId)
    {
        SharedPreferences sharedpreferences = _context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(PREFS_KEY, _userId);
        editor.commit();
    }
}
