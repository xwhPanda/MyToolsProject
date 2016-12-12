package xwh.com.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/8 14:06
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected SweetAlertDialog sweetAlertDialog;
    protected Context mContext;
    protected Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mContext = this;
        sweetAlertDialog = new SweetAlertDialog(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    abstract void initView();
    abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    protected void unSubscribe(){
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
