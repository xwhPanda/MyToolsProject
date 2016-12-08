package xwh.com.mytoolsproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/15 11:25
 */
public class MainActivity extends AppCompatActivity {
    int i = 0;
    private XWHSwipeRefreshLayout xwhSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        xwhSwipeRefreshLayout = (XWHSwipeRefreshLayout) findViewById(R.id.layout);

        for (int i = 0;i<20;i++){
            list.add(i + "");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this,list));

        xwhSwipeRefreshLayout.setRefreshing(true);
        xwhSwipeRefreshLayout.setOnRefreshListener(new XWHSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(1,5000);
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1)
                xwhSwipeRefreshLayout.setRefreshing(false);
        }
    };


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
        private Context context;
        private List<String> lists;

        public MyAdapter(Context context,List<String> lists){
            this.context = context;
            this.lists = lists;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText(lists.get(position));
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            protected TextView textView;
            public MyHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
