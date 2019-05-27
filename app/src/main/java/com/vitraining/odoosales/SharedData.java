package com.vitraining.odoosales;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {
    private static final String myPref = "myPref";
    private static SharedPreferences sp;

    public static String getKey(Context c, String key){
        sp = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void setKey(Context c, String key, String value){
        sp = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(key, value);
        editor.commit();
    }
}
