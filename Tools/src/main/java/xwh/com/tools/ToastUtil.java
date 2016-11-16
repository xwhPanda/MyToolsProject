package xwh.com.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/15 11:18
 */
public class ToastUtil {
    private static Toast mToast;

    /**
     * 显示toast，解决toast在界面停留很久
     * @param context
     * @param content
     */
    public static void showToast(Context context,String content){
        if (mToast == null)
            mToast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        else
            mToast.setText(content);
        mToast.show();
    }
}
