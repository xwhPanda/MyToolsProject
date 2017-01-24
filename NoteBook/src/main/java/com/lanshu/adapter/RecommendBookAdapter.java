package com.lanshu.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanshu.BR;
import com.lanshu.R;
import com.lanshu.base.OnRecyclerViewItemClick;
import com.lanshu.bean.ReadingBook;
import com.lanshu.databinding.HomeRecommendItemBinding;

import java.util.List;

/**
 * Created by XWH on 2017/1/5.
 * 1152046774@qq.com
 */

public class RecommendBookAdapter extends RecyclerView.Adapter<RecommendBookAdapter.RecommendBookHolder> {
    private List<ReadingBook> readingBookList;
    private OnRecyclerViewItemClick itemClick;

    public RecommendBookAdapter(List<ReadingBook> readingBookList){
        this.readingBookList = readingBookList;
    }

    @Override
    public RecommendBookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.home_recommend_item,parent,false);
        return new RecommendBookHolder(dataBinding.getRoot());
    }

    public void setItemClick(OnRecyclerViewItemClick itemClick){
        this.itemClick = itemClick;
    }

    @Override
    public void onBindViewHolder(RecommendBookAdapter.RecommendBookHolder holder, final int position) {
        ReadingBook readingBook = readingBookList.get(position);
        holder.bindReadBook(readingBook);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick != null){
                    itemClick.onItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return readingBookList.size();
    }

    class RecommendBookHolder extends RecyclerView.ViewHolder{
        private HomeRecommendItemBinding binding;
        private View rootView;

        public RecommendBookHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            binding = DataBindingUtil.bind(itemView);
        }

        public View getRootView(){
            return rootView;
        }

        public void bindReadBook(ReadingBook readingBook){
            binding.setVariable(BR.readBook,readingBook);
            binding.executePendingBindings();
        }
    }
}
