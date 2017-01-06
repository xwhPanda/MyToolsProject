package com.lanshu.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/12 16:36
 */
public class RetrofitManager {
    public HttpRequestService httpRequestService;
    public Retrofit retrofit;
    public int connectTimeOut = 10;
    public int readTimeOut = 10;
//    public String baseUrl = "http://zhannei.baidu.com/";
    public String baseUrl = "http://www.lingdiankanshu.co/";
    public RetrofitManager instance;

    public RetrofitManager(){
        instance = this;
    }

    public RetrofitManager(String baseUrl){
        instance = this;
        this.baseUrl = baseUrl;
    }

    public RetrofitManager init(){
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.connectTimeout(connectTimeOut, TimeUnit.SECONDS);
        okBuilder.readTimeout(readTimeOut,TimeUnit.SECONDS.SECONDS);
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okBuilder.build())
                .build();
        httpRequestService = retrofit.create(HttpRequestService.class);
        return instance;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
