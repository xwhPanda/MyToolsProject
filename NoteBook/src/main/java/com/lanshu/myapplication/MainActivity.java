package com.lanshu.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lanshu.R;
import com.lanshu.adapter.RecommendBookAdapter;
import com.lanshu.api.RetrofitManager;
import com.lanshu.bean.ReadingBook;
import com.lanshu.databinding.ActivityMainBinding;
import com.lanshu.rx.LoadCallBack;
import com.lanshu.rx.SubscriptionUtil;
import com.lanshu.util.GetRecommendContent;

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

        binding.bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        binding.bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_launcher,"书架"))
        .initialise();

        recommendBookAdapter = new RecommendBookAdapter(readingBookList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(recommendBookAdapter);

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
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
