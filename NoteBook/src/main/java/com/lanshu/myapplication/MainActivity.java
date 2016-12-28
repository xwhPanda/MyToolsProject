package com.lanshu.myapplication;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.lanshu.api.RetrofitManager;
import com.lanshu.bean.ReadingBook;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import com.lanshu.R;

import com.lanshu.util.ParseHtmlFormSearch;
import com.lanshu.util.Tools;

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
        toolbar.setTitle("App");
        toolbar.setTitleTextColor(Tools.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_search)
                    Toast.makeText(MainActivity.this,"ssss",Toast.LENGTH_SHORT).show();
                return false;
            }
        });


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
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(ResponseBody stringResult) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }
}
