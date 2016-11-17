package xwh.com.mytoolsproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;

import xwh.com.tools.utils.StorageUtils;
import xwh.com.tools.utils.ToastUtil;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/15 11:25
 */
public class MainActivity extends AppCompatActivity {
    int i = 0;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        editText = (EditText) findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("TAG","---------------1");
                if (s.length() > 0){
                    Log.e("TAG","---------------2");
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,1000);
                }else {
                    Log.e("TAG","---------------3");
                    handler.removeCallbacks(runnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String path = StorageUtils.getSdcardPath() + "/aaaa/";
        String name = "a.txt";
        File file = new File(path);
        File saveFile = new File(path,name);
        if (!file.exists())
            file.mkdirs();
        if (!saveFile.exists())
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.e("TAG","runnable");
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("TAG"," get message ! ");
        }
    };

    public void buttonEvent(View view){
        ToastUtil.showToast(this,"这是第 " + ++i + " 个Toast ！");
    }
}
