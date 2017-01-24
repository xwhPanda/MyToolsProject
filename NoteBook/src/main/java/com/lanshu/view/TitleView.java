package com.lanshu.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.lanshu.R;
import com.lanshu.databinding.TitleViewLayoutBinding;
import com.lanshu.util.EventListener;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/23 11:00
 */
public class TitleView extends LinearLayout{
    private Context context;
    private TitleViewLayoutBinding binding;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        ViewDataBinding bindingUtil = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.title_view_layout,this,true);
        binding = DataBindingUtil.bind(bindingUtil.getRoot());
        binding.setEventListener(new EventListener());
    }
}
