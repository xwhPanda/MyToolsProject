package com.lanshu.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/12 17:04
 */
public interface HttpRequestService {
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
    @GET("/")
    Observable<ResponseBody> getHomeRecommend();

    /**
     * 获取书籍详情
     * @return
     */
    @GET("{name}")
    Observable<ResponseBody> getBookDetail(@Path("name") String name);
}
