package com.lanshu.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

/**
 * Created by admin on 2016/12/19.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>{
    private int layoutId;
    private Context mContext;
    private Collection collection;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public BaseAdapter(Context context, Collection collection,int layoutId){
        this.collection = collection;
        this.mContext = context;
        this.layoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutId,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if(onItemClickListener != null)
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.rootView,position);
                }
            });

        if (onItemLongClickListener != null)
            holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClick(holder.rootView,position);
                    return false;
                }
            });
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
}
