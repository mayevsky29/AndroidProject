package com.example.myappfrontend.network.account;

import com.example.myappfrontend.constants.Urls;
import com.example.myappfrontend.network.interceptors.JWTInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountService {
    private static AccountService instance;
    private Retrofit retrofit;

    private AccountService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor((new JWTInterceptor()))
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Urls.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static AccountService getInstance() {
        if(instance==null)
            instance=new AccountService();
        return instance;
    }
// створення обєкту на основі інтерфейсу
    public AccountApi jsonApi() {
        return retrofit.create(AccountApi.class);
    }

}
