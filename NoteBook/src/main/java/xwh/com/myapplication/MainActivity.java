package xwh.com.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Callable;

import retrofit2.adapter.rxjava.Result;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xwh.com.api.RetrofitManager;

public class MainActivity extends BaseActivity {
    private TextView title;
    private TextView content;
    private TextView nextChapter;
    private LoadContentUtil loadContentUtil;
    private ReadingBook readingBook;
    private Single<ReadingBook> single;

    @Override
    void initView() {
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        nextChapter = (TextView) findViewById(R.id.next_chapter);
        loadContentUtil = new LoadContentUtil();

        readingBook = new ReadingBook();
        readingBook.setHostUrl("http://www.17k.com/");
        readingBook.setNextChapterUrl("http://www.17k.com/chapter/2252459/27007649.html");

        nextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSubscribe();
                subscription = single.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleSubscriber() {
                            @Override
                            public void onSuccess(Object value) {
                                content.setText(((ReadingBook)value).getChapterContent());
                            }

                            @Override
                            public void onError(Throwable error) {
                            }
                        });
            }
        });

        single = Single.fromCallable(new Callable() {
            @Override
            public ReadingBook call() throws Exception {
                readingBook = loadContentUtil.getText(readingBook);
                return readingBook;
            }
        });
//        subscription = single.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleSubscriber() {
//                    @Override
//                    public void onSuccess(Object value) {
//                        content.setText(((ReadingBook)value).getChapterContent());
//                    }
//
//                    @Override
//                    public void onError(Throwable error) {
//                    }
//                });


        new RetrofitManager().httpRequestService.searchBook("将夜","1","15772447660171623812")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Result<Object>>() {
            @Override
            public void onCompleted() {
                Log.e("TAG","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","onError" + "\n");
                Log.e("TAG",e.getMessage());
            }

            @Override
            public void onNext(Result<Object> stringResult) {
                Log.e("TAG","onNext");
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }
}
