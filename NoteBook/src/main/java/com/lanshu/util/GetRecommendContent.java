package com.lanshu.util;

import android.util.Log;

import com.lanshu.bean.Author;
import com.lanshu.bean.ReadingBook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/3 15:43
 * 获取推荐内容
 */
public class GetRecommendContent {
    public static final String HOST_URL = "http://www.lingdiankanshu.co";

    public static List<ReadingBook> getRecommendBook(String html) {
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("hotcontent");
        Elements elements = element.getElementsByClass("l");
        Element elementOne = elements.first();
        Elements items = elementOne.getElementsByClass("item");
        return getHotBookList(items);
    }

    /**
     * 从elements中获取数据列表
     * @param elements
     * @return
     */
    private static List<ReadingBook> getHotBookList(Elements elements){
        List<ReadingBook> books = new ArrayList<>();
        if (elements != null){
            for (Element element : elements){
                ReadingBook readingBook = getBookFromElement(element);
                if (readingBook != null)
                    books.add(readingBook);
            }
        }

        return books;
    }

    /**
     * 从element中解析书本信息
     * @return
     */
    private static ReadingBook getBookFromElement(Element element){
        ReadingBook readingBook = new ReadingBook();
        String bookPic = element.getElementsByTag("img").first().attr("src");
        readingBook.setBookPic(HOST_URL + bookPic);
        Elements elements = element.getElementsByTag("dt");
        Author author = new Author();
        author.setName(elements.first().select("span").text());
        readingBook.setAuthor(author);
        readingBook.setHostUrl(HOST_URL + elements.first().select("a[href]").first().attr("href"));
        readingBook.setBookName(elements.first().select("a[href]").first().text());
        Elements elements1 = element.getElementsByTag("dd");
        readingBook.setDesc(elements1.first().text().replaceAll(" ",""));
        Log.e("TAG",readingBook.toString());
        return readingBook;
    }
}
