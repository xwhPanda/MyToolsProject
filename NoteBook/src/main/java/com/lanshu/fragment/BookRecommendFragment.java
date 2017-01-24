package com.lanshu.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.lanshu.R;
import com.lanshu.adapter.RecommendBookAdapter;
import com.lanshu.api.RetrofitManager;
import com.lanshu.base.DividerItemDecoration;
import com.lanshu.base.OnRecyclerViewItemClick;
import com.lanshu.bean.ReadingBook;
import com.lanshu.databinding.BookRecommendFragmentBinding;
import com.lanshu.myapplication.BookDetailActivity;
import com.lanshu.rx.LoadCallBack;
import com.lanshu.rx.SubscriptionUtil;
import com.lanshu.util.GetRecommendContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscription;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/23 14:53
 */
public class BookRecommendFragment extends BaseFragment {
    private Subscription subscription;
    private RecommendBookAdapter adapter;
    private List<ReadingBook> readingBookList = new ArrayList<>();
    private BookRecommendFragmentBinding bookRecommendFragmentBinding;

    @Override
    protected void createView() {
        bookRecommendFragmentBinding = DataBindingUtil.bind(view);
        adapter = new RecommendBookAdapter(readingBookList);
        bookRecommendFragmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookRecommendFragmentBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        bookRecommendFragmentBinding.recyclerView.setAdapter(adapter);
        adapter.setItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClickListener(int position) {
                startActivity(new Intent(getActivity(), BookDetailActivity.class).putExtra("code",readingBookList.get(position).getHostUrl()));
            }
        });

        subscription = new SubscriptionUtil().init(new RetrofitManager().init().httpRequestService.getHomeRecommend(), new LoadCallBack<ResponseBody>() {
            @Override
            public void onStartLoad() {

            }

            @Override
            public void onSuccess(ResponseBody result) {
                try {
                    readingBookList.clear();
                    readingBookList.addAll(GetRecommendContent.getRecommendBook(result.string()));
                    adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.book_recommend_fragment;
    }
}
