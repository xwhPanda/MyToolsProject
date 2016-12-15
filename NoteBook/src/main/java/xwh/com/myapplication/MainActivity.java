package xwh.com.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import xwh.com.api.RetrofitManager;
import xwh.com.bean.ReadingBook;
import xwh.com.util.ParseHtmlFormSearch;

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

        subscription = new RetrofitManager().init().httpRequestService.searchBook("将夜","1","15772447660171623812")
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                content.setText("hahahahahaahah");
            }
        })
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.e("TAG","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","onError");
                e.printStackTrace();
            }

            @Override
            public void onNext(ResponseBody stringResult) {
                Log.e("TAG","onNext");
                try {
                    ParseHtmlFormSearch.getListFromHtml(stringResult.string().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }
}
