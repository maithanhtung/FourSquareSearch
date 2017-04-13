package com.howkteam.foursquaresearch;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class API {
  public static final String BASE_URL = "https://api.foursquare.com/v2/";

  static CustomInterceptor interceptor = new CustomInterceptor();

  static OkHttpClient okHttpClient = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();

  static RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

  static Retrofit retrofit = new Retrofit.Builder()
          .client(okHttpClient)
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(rxAdapter)
          .build();

  public static Retrofit get() {
    return retrofit;
  }
}
