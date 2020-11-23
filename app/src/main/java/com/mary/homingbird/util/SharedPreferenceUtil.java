package com.mary.homingbird.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    private static final String TAG = "SharedPreferenceUtil";

    public static String SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY";

    public static void clearSharedPreference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void setSharedPreference(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, value);
        DlogUtil.d(TAG, "key : "+key+", value : "+ value);
        editor.commit();
    }

}
