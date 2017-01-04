package com.lanshu.myapplication;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.lanshu.R;
import com.lanshu.api.RetrofitManager;
import com.lanshu.bean.ReadingBook;
import com.lanshu.rx.LoadCallBack;
import com.lanshu.rx.SubscriptionUtil;
import com.lanshu.util.Tools;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Single;
import rx.Subscription;

public class MainActivity extends BaseActivity {
    private Subscription subscription;
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

        subscription = new SubscriptionUtil().init(new RetrofitManager().init().httpRequestService.getHomeRecommend(), new LoadCallBack<ResponseBody>() {
            @Override
            public void onStartLoad() {

            }

            @Override
            public void onSuccess(ResponseBody result) {
                try {
//                    List<ReadingBook> bookList = ParseHtmlFormSearch.getListFromHtml(result.string().toString());
                    Log.e("TAG",result.string());

//                    GetRecommendContent.getRecommendBook(result.string().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleted() {

            }
        });


//        new Thread(){
//            @Override
//            public void run() {
//                Connection connection = Jsoup.connect("http://www.00ksw.net/");
//                try {
//                    Log.e("TAG",connection.get().html());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
