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

    public static void setYearLoggedIn(int yearLoggedIn){
        editor.putInt(Constants.YEAR_LOGGED_IN, yearLoggedIn);
        editor.commit();
    }

    public static void setMonthLoggedIn(int monthLoggedIn){
        editor.putInt(Constants.YEAR_LOGGED_IN, monthLoggedIn);
        editor.commit();
    }

    public static void setDateLoggedIn(int dateLoggedIn){
        editor.putInt(Constants.YEAR_LOGGED_IN, dateLoggedIn);
        editor.commit();
    }

    public static void setHourLoggedIn(int hourLoggedIn){
        editor.putInt(Constants.YEAR_LOGGED_IN, hourLoggedIn);
        editor.commit();
    }

    public static void setMinuteLoggedIn(int minuteLoggedIn){
        editor.putInt(Constants.MINUTE_LOGGED_IN, minuteLoggedIn);
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

    public static void setCurrentVehicleId(int currentVehicleId){
        editor.putInt(Constants.CURRENT_VEHICLE_ID, currentVehicleId);
        editor.commit();
    }

    public static void setCurrentTripId(int currentTripId){
        editor.putInt(Constants.CURRENT_VEHICLE_ID, currentTripId);
        editor.commit();
    }

    public static boolean hasAppRunOnce(){ return sharedPreferences.getBoolean(Constants.HAS_RUN_BEFORE, false); }

    public static String getAppToken(){ return sharedPreferences.getString(Constants.APP_TOKEN, null); }

    public static boolean isLoggedIn(){ return sharedPreferences.getBoolean(Constants.IS_LOGGED_IN, false); }

    public static String getUserLoggedIn(){ return sharedPreferences.getString(Constants.USER_NAME, null); }

    public static String getUserId(){ return sharedPreferences.getString(Constants.USER_ID, null); }

    public static int getMinuteLoggedIn(){ return sharedPreferences.getInt(Constants.MINUTE_LOGGED_IN, 0); }

    public static int getHourLoggedIn(){ return sharedPreferences.getInt(Constants.HOUR_LOGGED_IN, 0); }

    public static int getDateLoggedIn(){ return sharedPreferences.getInt(Constants.DATE_LOGGED_IN, 0); }

    public static int getMonthLoggedIn(){ return sharedPreferences.getInt(Constants.MONTH_LOGGED_IN, 0); }

    public static int getYearLoggedIn(){ return sharedPreferences.getInt(Constants.YEAR_LOGGED_IN, 0); }

    public static int getCurrentVehicleId(){
        return sharedPreferences.getInt(Constants.CURRENT_VEHICLE_ID, 0);
    }

    public static int getCurrentTripId(){
        return sharedPreferences.getInt(Constants.CURRENT_TRIP_ID, 0);
    }
}
