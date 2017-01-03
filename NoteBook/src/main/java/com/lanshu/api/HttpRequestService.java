package com.lanshu.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/12 17:04
 */
public interface HttpRequestService {
//    http://zhannei.baidu.com/cse/search?q=将夜&click=1&s=15772447660171623812
//    http://www.00ksw.net/

    /**
     * 搜索
     * @param searchString
     * @param actionType
     * @param s
     * @return
     */
    @GET("http://zhannei.baidu.com/cse/search")
    Observable<ResponseBody> searchBook(@Query("q") String searchString, @Query("entry") String actionType, @Query("s") String s);

    /**
     * 获取首页推荐
     * @return
     */
    @Headers({"Accept-Encoding: None","Content-Type: text/html;charset=utf-8"})
    @GET("/")
    Observable<ResponseBody> getHomeRecommend();
}