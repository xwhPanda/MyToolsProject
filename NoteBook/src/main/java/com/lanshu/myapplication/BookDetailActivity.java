package com.lanshu.myapplication;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lanshu.R;
import com.lanshu.api.RetrofitManager;
import com.lanshu.rx.LoadCallBack;
import com.lanshu.rx.SubscriptionUtil;
import com.lanshu.util.GetBookDetail;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscription;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/24 15:05
 */
public class BookDetailActivity extends AppCompatActivity {
    private ViewDataBinding binding;
    private Subscription subscription;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail);

        code = getIntent().getStringExtra("code");
        Log.e("TAG",code);

        subscription = new SubscriptionUtil().init(new RetrofitManager().init().httpRequestService.getBookDetail(code), new LoadCallBack<ResponseBody>() {
            @Override
            public void onStartLoad() {
                super.onStartLoad();
            }

            @Override
            public void onError() {
                super.onError();
            }

            @Override
            public void onSuccess(ResponseBody result) {
                super.onSuccess(result);
                try {
                    GetBookDetail.getDetail(result.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }
        });
    }
}
