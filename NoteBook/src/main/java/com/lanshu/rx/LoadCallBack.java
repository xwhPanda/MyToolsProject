package com.lanshu.rx;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/12 15:25
 */
public abstract class LoadCallBack<T> {

    public void onStartLoad(){}
    public void onError(){}
    public void onSuccess(T result){}
    public void onCompleted(){}
}
