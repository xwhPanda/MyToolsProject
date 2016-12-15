package xwh.com.bean;

import java.io.Serializable;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/8 14:46
 */
public class ReadingBook extends Book implements Serializable{
    /* 正在读的章节 */
    private String readChapter;
    /* 书籍地址 */
    private String hostUrl;
    /**/
    private String url;
    /* 上次读的时间 */
    private String latestReadTime;
    /* 上一章 */
    private String lastChapterUrl;
    /* 下一章 */
    private String nextChapterUrl;
    /* 当前章节内容 */
    private String chapterContent;
    /* 书籍图片 */
    private String bookPic;
    /* 简介 */
    private String desc;

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

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
                ", chapterContent='" + chapterContent + '\'' +
                ", bookPic='" + bookPic + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
