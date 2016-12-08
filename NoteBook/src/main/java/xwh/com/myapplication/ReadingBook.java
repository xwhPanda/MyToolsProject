package xwh.com.myapplication;

import java.io.Serializable;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/8 14:46
 */
public class ReadingBook extends Book implements Serializable{
    private String readChapter;
    private String hostUrl;
    private String url;
    private String latestReadTime;
    private String lastChapterUrl;
    private String nextChapterUrl;

    public String getReadChapter() {
        return readChapter;
    }

    public void setReadChapter(String readChapter) {
        this.readChapter = readChapter;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatestReadTime() {
        return latestReadTime;
    }

    public void setLatestReadTime(String latestReadTime) {
        this.latestReadTime = latestReadTime;
    }

    public String getLastChapterUrl() {
        return lastChapterUrl;
    }

    public void setLastChapterUrl(String lastChapterUrl) {
        this.lastChapterUrl = lastChapterUrl;
    }

    public String getNextChapterUrl() {
        return nextChapterUrl;
    }

    public void setNextChapterUrl(String nextChapterUrl) {
        this.nextChapterUrl = nextChapterUrl;
    }

    @Override
    public String toString() {
        return "ReadingBook{" +
                "readChapter='" + readChapter + '\'' +
                ", hostUrl='" + hostUrl + '\'' +
                ", url='" + url + '\'' +
                ", latestReadTime='" + latestReadTime + '\'' +
                ", lastChapterUrl='" + lastChapterUrl + '\'' +
                ", nextChapterUrl='" + nextChapterUrl + '\'' +
                '}';
    }
}
