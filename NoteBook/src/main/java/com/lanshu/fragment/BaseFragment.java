package com.lanshu.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/10 20:18
 */
public abstract class BaseFragment extends Fragment {
    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,getLayoutViewId(),container,false);
        Log.e("TAG",binding + " : id");
        view = binding.getRoot();
        createView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected abstract void createView();
    protected abstract int getLayoutViewId();
}
