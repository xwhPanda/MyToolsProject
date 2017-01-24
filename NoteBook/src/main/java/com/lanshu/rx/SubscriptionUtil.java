package com.lanshu.rx;

import android.util.Log;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/3 16:04
 */
public class SubscriptionUtil {
    private Subscription mSubscription;

    public Subscription init(Observable<ResponseBody> observable, final LoadCallBack loadCallBack) {
        mSubscription = observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadCallBack.onStartLoad();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        loadCallBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("TAG", "onError : " + e.getMessage());
                        loadCallBack.onError();

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loadCallBack.onSuccess(responseBody);
                    }
                });
        return mSubscription;
    }

    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }
}
