package com.com.tools.update;

import android.content.Context;
import android.os.Handler;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/11/16 15:35
 */
public class DownloadManager {
    private Context context;
    //下载进度回调
    private DownloadProgressCallback mDownloadProgressCallback;
    private Handler mHandler;

    private Downloader downloader;

    public DownloadManager(Context context, String fileSavePath, String fileName,String downloadUrl){
        downloader = new Downloader(fileSavePath,fileName,downloadUrl);
        mHandler = new Handler();
    }

    public void startDownload(){
        downloader.startDownload();
    }

    public void setDownloadProgressCallback(DownloadProgressCallback callback){
        mDownloadProgressCallback = callback;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mDownloadProgressCallback.refreshProgress(downloader.getProgress());
            mHandler.postDelayed(runnable,1000);
        }
    };

    //停止计时
    private void stopTime(){
        mHandler.removeCallbacks(runnable);
    }

}
