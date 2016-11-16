package xwh.com.tools;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/15 10:49
 */
public class FileUtils {

    /**
     * 根据文件名判断文件是否是图片，暂时是jpg，png，jpeg
     * @param fileName
     * @return
     */
    public static boolean isImage(String fileName){
        String name = fileName.toLowerCase();
        if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg"))
            return true;
        else
            return false;
    }
}
