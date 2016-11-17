package xwh.com.tools.update;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    //文件名
    private String fileName;
    //文件下载路径
    private String downloadUrl;
    //文件大小
    private long fileSize;
    //当前下载大小
    private long currentSize;
    //下载状态
    private int downloadState;

    public Downloader(String fileSavePath, String fileName,String downloadUrl){
        this.fileSavePath = fileSavePath;
        this.fileName = fileName;
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

    private void initFile(){
        File file = new File(fileSavePath);
        if (!file.exists())
            file.mkdirs();
        File saveFile = new File(fileSavePath,fileName);
        if (!saveFile.exists())
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    class DownloadRunnable implements Runnable {

        @Override
        public void run() {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(downloadUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10 * 1000);
                connection.setReadTimeout(10 *1000);
                connection.setRequestMethod("GET");
                InputStream is = connection.getInputStream();
                fileSize = connection.getContentLength();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
