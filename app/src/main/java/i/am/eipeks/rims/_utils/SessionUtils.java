package i.am.eipeks.rims._utils;


import android.content.Context;
import android.content.SharedPreferences;

import i.am.eipeks.rims.Constants;

public class SessionUtils {

    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreferences;

    public SessionUtils(Context context){
        sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void setAppHasRunBefore(boolean hasRunBefore){
        editor.putBoolean(Constants.HAS_RUN_BEFORE, hasRunBefore);
        editor.commit();
    }

    public static void setAppToken(String token){
        editor.putString(Constants.APP_TOKEN, token);
        editor.commit();
    }

    public static void setLoggedIn(boolean loggedIn){
        editor.putBoolean(Constants.IS_LOGGED_IN, loggedIn);
        editor.commit();
    }

    public static void setUserLoggedIn(String user){
        editor.putString(Constants.USER_NAME, user);
        editor.commit();
    }

    public static void setUserId(String userId){
        editor.putString(Constants.USER_ID, userId);
        editor.commit();
    }

    public static boolean hasAppRunOnce(){ return sharedPreferences.getBoolean(Constants.HAS_RUN_BEFORE, false); }

    public static String getAppToken(){ return sharedPreferences.getString(Constants.APP_TOKEN, null); }

    public static boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false);
    }

    public static String getUserLoggedIn(){
        return sharedPreferences.getString(Constants.USER_NAME, null);
    }

    public static String getUserId(){ return sharedPreferences.getString(Constants.USER_ID, null); }

}
