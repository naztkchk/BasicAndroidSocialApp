package com.pllug.course.tkachuk.basicandroidsocialapp.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static ApiService service;

    //URL
    private static final String ROOT_URL ="http://jsonplaceholder.typicode.com";


    public static ApiService getRetroClient() {
        if (service == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createDefaultOkHttpClient())
                    .build();

            service = retrofit.create(ApiService.class);
        }
        return service;
    }

    private static OkHttpClient createDefaultOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();
    }
}
