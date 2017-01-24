package xwh.com.mytoolsproject;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xwh.com.mytoolsproject.databinding.Index2Binding;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/26 15:03
 */
public class Index2Activity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Index2Binding binding = DataBindingUtil.setContentView(this,R.layout.index_2);
        binding.setClickUtils(new ClickUtils());
    }
}
