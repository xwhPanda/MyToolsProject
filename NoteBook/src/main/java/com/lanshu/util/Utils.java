package com.lanshu.util;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/10 10:43
 */
public class Utils {

    @BindingAdapter("image")
    public static void loadImage(SimpleDraweeView view,String url){
        view.setImageURI(url);
    }

}
