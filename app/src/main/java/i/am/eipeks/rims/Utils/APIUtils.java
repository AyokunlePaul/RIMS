package i.am.eipeks.rims.Utils;


import i.am.eipeks.rims._network.Auth;

public class APIUtils {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String BASE_URL = "http://terms.smartwebhack.com/api/";

    public static Auth getAuth(){
        return RetrofitUtils.getRetrofitClient(BASE_URL).create(Auth.class);
    }

}
