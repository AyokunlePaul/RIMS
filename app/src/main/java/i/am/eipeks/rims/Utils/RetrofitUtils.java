package i.am.eipeks.rims.Utils;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitUtils {

    private static Retrofit retrofit = null;

    static Retrofit getRetrofitClient(String baseUrl){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
