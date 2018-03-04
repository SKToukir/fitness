package vumobile.com.fitness.club.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by toukirul on 14/2/2018.
 */

public class TokenPreference {

    public static final String SHARED_PREF = "ah_firebase";
    public static final String TOKEN_KEY = "key_firebase_token";

    public static void storeToken(Context context, String token){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        String token = pref.getString(TOKEN_KEY,"null");
        return token;
    }
}