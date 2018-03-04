package vumobile.com.fitness.club.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by toukirul on 21/1/2018.
 */

public class RetrofitClient {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(String base_url){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60 / 2, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
