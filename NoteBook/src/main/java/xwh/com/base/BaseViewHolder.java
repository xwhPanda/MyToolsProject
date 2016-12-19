package xwh.com.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by admin on 2016/12/19.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewSparseArray;
    public View rootView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.rootView = itemView;
        viewSparseArray = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId){
        View view = viewSparseArray.get(viewId);
        if (view == null){
            view = rootView.findViewById(viewId);
            viewSparseArray.put(viewId,view);
        }
        return (T)view;
    }
}
