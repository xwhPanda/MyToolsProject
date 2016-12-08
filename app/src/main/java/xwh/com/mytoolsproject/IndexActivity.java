package xwh.com.mytoolsproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/8 10:12
 */
public class IndexActivity extends AppCompatActivity {
    private TextView text;
    private Button nextPage;
    private final static String BASE_URL = "http://www.17k.com/";
    private String chapterAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        text = (TextView) findViewById(R.id.text);
        nextPage = (Button) findViewById(R.id.nextPage);

        new Thread(){
            @Override
            public void run() {
                try {
                    chapterAddress = "chapter/2252459/27007649.html";
                    Document document = Jsoup.connect(BASE_URL + chapterAddress).get();
                    Elements links = document.select("a[href]");
                    for (Element link : links){
                        Log.e("TAG",link.html().toString());
                    }
                    Element element = document.getElementById("chapterContentWapper");
                    Elements elements = element.getAllElements();
                    String text = elements.get(0).html().toString().replaceAll("<br>","");
                    int index = text.indexOf("<div");
                    String string = text.substring(0,index);
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    msg.obj = string;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    text.setText((String)msg.obj);
                    break;
            }
        }
    };
}
