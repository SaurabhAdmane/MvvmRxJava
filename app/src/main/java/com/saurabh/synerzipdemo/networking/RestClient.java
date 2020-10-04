package com.saurabh.synerzipdemo.networking;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import com.saurabh.synerzipdemo.BuildConfig;


/**
 * Created by SaurabhA on 03,October,2020
 */

/**
 * For REST over HTTP(S). Holds the client for other services to put interfaces against.
 */
public class RestClient {

    private static Retrofit retrofit = null;

    public static synchronized Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = getOkHttpClient();
            retrofit = new Retrofit.Builder()
                    .baseUrl(SynerzipAPI.getBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SynerzipConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    @NotNull
    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .certificatePinner(new CertificatePinner.Builder()
                        .build())
                .addInterceptor(BuildConfig.DEBUG ? httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) : httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE))
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
