package xwh.com.util;

import android.content.Context;
import android.os.Build;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/15 15:14
 */
public class Tools {

    /**
     * 获取颜色
     * @param context
     * @param colorId
     * @return
     */
    public static int getColor(Context context , int colorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId,null);
        }else {
            return context.getResources().getColor(colorId);
        }
    }

    /**
     * 获取string
     * @param context
     * @param stringId
     * @return
     */
    public static String getString(Context context,int stringId){
        return context.getResources().getString(stringId);
    }
}
