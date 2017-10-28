package i.am.eipeks.rims._utils;


import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitUtils {

    private static Retrofit retrofit = null;

//    private static OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(@NonNull Chain chain) throws IOException {
//                    Request request = chain.request()
//                            .newBuilder()
//                            .addHeader("Authorization", "Bearer")
//                            .addHeader("Accept", "application/json")
//                            .build();
//                    return chain.proceed(request);
//                }
//            }).build();

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
