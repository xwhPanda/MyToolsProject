package xwh.com.tools.update;

import xwh.com.tools.utils.LogUtil;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/16 15:47
 */
public class Downloader {
    private final int DOWNLOAD_INIT = 0;
    private final int DOWNLOADING = 1;
    private final int DOWNLOADED = 2;
    private final int DOWNLOAD_ERROR = 3;
    //文件存放路径
    private String fileSavePath;
    //文件下载路径
    private String downloadUrl;
    //文件大小
    private long fileSize;
    //当前下载大小
    private long currentSize;
    //下载状态
    private int downloadState;

    public Downloader(String fileSavePath, String downloadUrl){
        this.fileSavePath = fileSavePath;
        this.downloadUrl = downloadUrl;
    }

    /**
     * 获取下载进度
     * @return
     */
    public int getProgress(){
        if (fileSize == 0){
            LogUtil.e("FileSize can not be zero !");
            return 0;
        }
        return (int)(currentSize * 100 / fileSize);
    }

    /**
     * 开始下载
     */
    public void startDownload(){}

    /**
     * 获取下载状态
     * @return
     */
    public int getDownloadState(){
        return downloadState;
    }
}
