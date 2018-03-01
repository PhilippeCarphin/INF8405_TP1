package com.example.mounia.tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;

/**
 * Created by mounianordine on 18-02-26.
 */

public class SharedPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "pref";
    private static final String POINTACCES = "pointAcces";

    public SharedPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveList(String pointAccesString) {
        editor.putString(POINTACCES, pointAccesString);
        editor.commit();
    }

    public String getList() {
        return pref.getString(POINTACCES, "");
    }
}