package xwh.com.myapplication;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
    private TextView title;
    private TextView content;
    private TextView nextChapter;
    private LoadContentUtil loadContentUtil;

    @Override
    void initView() {
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        nextChapter = (TextView) findViewById(R.id.next_chapter);
        loadContentUtil = new LoadContentUtil(mHandler);

        ReadingBook readingBook = new ReadingBook();
        readingBook.setHostUrl("http://www.17k.com/");
        readingBook.setNextChapterUrl("http://www.17k.com/chapter/2252459/27007649.html");
        loadContentUtil.startLoad(readingBook);

        nextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContentUtil.startLoad(loadContentUtil.getReadingBook());
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    sweetAlertDialog.dismiss();
                    content.setText((String)msg.obj);
                    title.setText(loadContentUtil.getReadingBook().getReadChapter());
                    break;
                case 2:
                    sweetAlertDialog.show();
                    break;
            }
        }
    };
}
