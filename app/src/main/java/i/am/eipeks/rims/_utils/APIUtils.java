package i.am.eipeks.rims._utils;


import i.am.eipeks.rims._network.Auth;

public class APIUtils {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String BASE_URL = "https://afternoon-meadow-98231.herokuapp.com/api/";

    public static Auth getAuth(){
        return RetrofitUtils.getRetrofitClient(BASE_URL).create(Auth.class);
    }
}
