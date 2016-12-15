package xwh.com.bean;

import java.io.Serializable;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/8 14:43
 */
public class Book implements Serializable{
    private String bookName;
    private String bookDesc;
    private String author;
    private String time;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
