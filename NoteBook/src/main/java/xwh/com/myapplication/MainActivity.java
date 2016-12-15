package xwh.com.myapplication;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import xwh.com.api.RetrofitManager;
import xwh.com.bean.ReadingBook;
import xwh.com.util.ParseHtmlFormSearch;
import xwh.com.util.Tools;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView title;
    private TextView content;
    private TextView nextChapter;
    private LoadContentUtil loadContentUtil;
    private ReadingBook readingBook;
    private Single<ReadingBook> single;

    @Override
    void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("haha");
        toolbar.setTitleTextColor(Tools.getColor(this,R.color.white));
        setSupportActionBar(toolbar);


        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        nextChapter = (TextView) findViewById(R.id.next_chapter);
        loadContentUtil = new LoadContentUtil();

        subscription = new RetrofitManager().init().httpRequestService.searchBook("将夜","1","10977942222484467615")
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
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
                    List<ReadingBook> bookList = ParseHtmlFormSearch.getListFromHtml(stringResult.string().toString());
                    Log.e("TAG",bookList.toString());
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
