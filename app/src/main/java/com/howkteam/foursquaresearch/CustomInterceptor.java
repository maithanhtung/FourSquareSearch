package com.howkteam.foursquaresearch;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();
    HttpUrl originalHttpUrl = original.url();
    HttpUrl url = originalHttpUrl.newBuilder()
            .addQueryParameter("insecure", "cool")
            .addQueryParameter("client_id", "CYEMKOM4OLTP5PHMOFVUJJAMWT5CH5G1JBCYREATW21XLLSZ")
            .addQueryParameter("client_secret", "GYNP4URASNYRNRGXR5UEN2TGTKJHXY5FGSAXTIHXEUG1GYM2")
            .addQueryParameter("v", "20171026")
            .build();
    Request.Builder requestBuilder = original.newBuilder()
            .url(url);
    Request req = requestBuilder.build();
    return chain.proceed(req);
  }
}
