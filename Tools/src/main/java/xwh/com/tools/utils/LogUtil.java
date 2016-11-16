package xwh.com.tools.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/15 14:00
 */
public class LogUtil {
    private static boolean IS_DEBUG = true;

    public void init(Context context){
        IS_DEBUG = isDebug(context);
    }

    public void setIsDebug(boolean isDebug){
        IS_DEBUG = isDebug;
    }

    /**
     * 是否是debug
     * @param context
     * @return
     */
    private static boolean isDebug(Context context){
        try{
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            return (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void e(String tag,String msg){
        if (IS_DEBUG)
            Log.e(tag,msg);
    }

    public static void i(String tag,String msg){
        if (IS_DEBUG)
            Log.i(tag,msg);
    }

    public static void w(String tag,String msg){
        if (IS_DEBUG)
            Log.w(tag,msg);
    }

    public static void d(String tag,String msg){
        if (IS_DEBUG)
            Log.d(tag,msg);
    }

    public static void e(String msg){
        if (IS_DEBUG)
            Log.e(_FILE_(),getFileLineMethod() + msg);
    }

    public static void i(String msg){
        if (IS_DEBUG)
            Log.i(_FILE_(),getFileLineMethod() + msg);
    }

    public static void w(String msg){
        if (IS_DEBUG)
            Log.w(_FILE_(),getFileLineMethod() + msg);
    }

    public static void d(String msg){
        if (IS_DEBUG)
            Log.d(_FILE_(),getFileLineMethod() + msg);
    }

    public static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ")
                .append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }

    public static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return traceElement.getFileName();
    }

}
