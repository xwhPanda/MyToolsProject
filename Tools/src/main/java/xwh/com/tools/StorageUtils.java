package xwh.com.tools;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/15 10:53
 */
public class StorageUtils {

    /**
     * 判断内置SD卡是否可用
     * @return
     */
    public static boolean isSdcardCanUse(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取内置SD卡路径
     * @return
     */
    public static String getSdcardPath(){
        if (isSdcardCanUse())
            return Environment.getExternalStorageDirectory().getPath();
        else
            return null;
    }

    /**
     * 获取内置SD卡总大小
     * @param context
     * @return
     */
    private static String getSdcardTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     * @param context
     * @return
     */
    private static String getSdcardAvailableSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2)
            blockSize = stat.getBlockSizeLong();
        else
            blockSize = stat.getBlockSize();
        long availableBlocks = 0;
        if (SDK_INT >= JELLY_BEAN_MR2)
            availableBlocks = stat.getAvailableBlocksLong();
        else
            availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }
}
