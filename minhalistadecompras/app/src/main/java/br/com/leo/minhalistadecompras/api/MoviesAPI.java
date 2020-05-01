package br.com.leo.minhalistadecompras.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesAPI {

    public static String BASE_URL = "https://api.themoviedb.org/";
    public static String API_KEY = "37b78eaa986b6dc09b95caf277c41a24";
    public static Retrofit retrofit;

    public static void retrofitSetup(){

        // criar OkHttp client
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        // criar Retrofit
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build());

        retrofit = retrofitBuilder.build();
    }

}
