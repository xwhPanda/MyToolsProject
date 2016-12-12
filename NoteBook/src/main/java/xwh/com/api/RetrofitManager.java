package xwh.com.api;

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
    public String baseUrl = "http://zhannei.baidu.com/";

    public RetrofitManager(){
        OkHttpClient.Builder okBuileder = new OkHttpClient.Builder();
        okBuileder.connectTimeout(connectTimeOut, TimeUnit.SECONDS);
        okBuileder.readTimeout(readTimeOut,TimeUnit.SECONDS.SECONDS);
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okBuileder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpRequestService = retrofit.create(HttpRequestService.class);
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
