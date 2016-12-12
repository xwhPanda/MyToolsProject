package xwh.com.api;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/12 17:04
 */
public interface HttpRequestService {
//    http://zhannei.baidu.com/cse/search?q=将夜click=1&s=15772447660171623812

    @GET("cse/search")
    Observable<Result<Object>> searchBook(@Query("q") String searchString, @Query("click") String actionType, @Query("s") String s);
}
