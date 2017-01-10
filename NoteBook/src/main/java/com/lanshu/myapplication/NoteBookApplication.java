package com.lanshu.myapplication;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/8 14:12
 */
public class NoteBookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
