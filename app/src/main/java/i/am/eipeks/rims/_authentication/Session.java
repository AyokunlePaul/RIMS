package i.am.eipeks.rims._authentication;


import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public Session(Context context){
        sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setAppHasRunBefore(boolean hasRunBefore){
        editor.putBoolean("frequency", hasRunBefore);
        editor.commit();
    }

    void setLoggedIn(boolean loggedIn){
        editor.putBoolean("loggedIn", loggedIn);
        editor.commit();
    }

    void setUserLoggedIn(String user){
        editor.putString("user", user);
        editor.commit();
    }

    public boolean hasAppRunOnce(){
        return sharedPreferences.getBoolean("frequency", false);
    }

    boolean isLoggedIn(){
        return sharedPreferences.getBoolean("loggedIn", false);
    }

    public String getUserLoggedIn(){
        return sharedPreferences.getString("user", null);
    }

}
