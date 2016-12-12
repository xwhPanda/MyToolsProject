package xwh.com.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2016/12/8 14:13
 */
public class LoadContentUtil {
    public LoadContentUtil(){
    }

    public ReadingBook getText(ReadingBook readingBook){
        Document document = null;
        try {
            document = Jsoup.connect(readingBook.getNextChapterUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = document.select("a[href]");
        for (Element link : links){
            if ("下一章".equals(link.text()))
                readingBook.setNextChapterUrl(readingBook.getHostUrl() + link.attr("href"));
            else if ("上一章".equals(link.text()))
                readingBook.setLastChapterUrl(readingBook.getHostUrl() + link.attr("href"));
        }
        readingBook.setReadChapter(document.getElementsByAttributeValue("itemprop","headline").text());
        Element element = document.getElementById("chapterContentWapper");
        Elements elements = element.getAllElements();
        String text = elements.get(0).html().toString().replaceAll("<br>","");
        int index = text.indexOf("<div");
        readingBook.setChapterContent(text.substring(0,index));
        return readingBook;
    }
}
