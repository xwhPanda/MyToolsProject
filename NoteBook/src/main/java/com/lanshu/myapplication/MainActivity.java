package com.lanshu.myapplication;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lanshu.R;
import com.lanshu.databinding.ActivityMainBinding;
import com.lanshu.fragment.BookClassifyFragment;
import com.lanshu.fragment.BookRecommendFragment;
import com.lanshu.fragment.BookStoreFragment;
import com.lanshu.fragment.BookshelfFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    private List<Fragment> fragments = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        binding.bottomNavigationBar.setBarBackgroundColor(R.color.colorPrimaryDark);
        binding.bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.recommend,"推荐"))
                .addItem(new BottomNavigationItem(R.mipmap.bookshelf,"书架"))
                .addItem(new BottomNavigationItem(R.mipmap.classify,"分类"))
                .addItem(new BottomNavigationItem(R.mipmap.bookcity,"书城"))
        .initialise();

        binding.bottomNavigationBar.setTabSelectedListener(this);

        initFragments();
        setDefaultFragment(0);
    }

    /**
     * 设置默认的fragment
     */
    private void setDefaultFragment(int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_layout,fragments.get(index));
        transaction.commit();
    }

    /**
     * 初始化fragment
     */
    private void initFragments(){
        fragments.add(new BookRecommendFragment());
        fragments.add(new BookshelfFragment());
        fragments.add(new BookClassifyFragment());
        fragments.add(new BookStoreFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments.size() > 0){
            if (position < fragments.size()){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()){
                    transaction.show(fragment);
                }else {
                    transaction.add(R.id.content_layout,fragment);
                }
                transaction.commit();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments.size() > 0){
            if (position < fragments.size()){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = fragments.get(position);
                transaction.hide(fragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
