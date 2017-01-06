package com.lanshu.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.lanshu.R;
import com.lanshu.adapter.RecommendBookAdapter;
import com.lanshu.api.RetrofitManager;
import com.lanshu.bean.ReadingBook;
import com.lanshu.databinding.ActivityMainBinding;
import com.lanshu.rx.LoadCallBack;
import com.lanshu.rx.SubscriptionUtil;
import com.lanshu.util.GetRecommendContent;
import com.lanshu.util.Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Single;
import rx.Subscription;

public class MainActivity extends AppCompatActivity{
    private Subscription subscription;
    private Toolbar toolbar;
    private TextView title;
    private TextView content;
    private TextView nextChapter;
    private LoadContentUtil loadContentUtil;
    private ReadingBook readingBook;
    private Single<ReadingBook> single;

    private List<ReadingBook> readingBookList = new ArrayList<>();
    private RecommendBookAdapter recommendBookAdapter;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
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

        recommendBookAdapter = new RecommendBookAdapter(readingBookList);
        binding.recyclerView.setAdapter(recommendBookAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadContentUtil = new LoadContentUtil();

        subscription = new SubscriptionUtil().init(new RetrofitManager().init().httpRequestService.getHomeRecommend(), new LoadCallBack<ResponseBody>() {
            @Override
            public void onStartLoad() {

            }

            @Override
            public void onSuccess(ResponseBody result) {
                try {

                    readingBookList.addAll(GetRecommendContent.getRecommendBook(result.string()));
                    recommendBookAdapter.notifyDataSetChanged();
//                    GetRecommendContent.getRecommendBook(result.string().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
