package com.lanshu.util;

import com.lanshu.bean.Author;
import com.lanshu.bean.ReadingBook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/24 15:02
 */
public class GetBookDetail {

    public static void getDetail(String html){
//        Log.e("TAG",html);
        ReadingBook readingBook = new ReadingBook();
        Document document = Jsoup.parse(html);
        Elements box_con = document.getElementsByClass("box_con");
        getBookDetail(box_con.get(0),readingBook);
    }

    private static ReadingBook getBookDetail(Element element,ReadingBook readingBook){
        String bookPic = element.getElementsByTag("img").first().attr("src");
        readingBook.setBookPic(GetRecommendContent.HOST_URL + bookPic);
        Element infoElement = element.getElementById("info");
        Elements h1 = infoElement.getElementsByTag("h1");
        readingBook.setBookName(h1.get(0).html());
        Elements p = infoElement.getElementsByTag("p");
        Author author = new Author();
        author.setName(p.get(0).text().split("：")[1]);
        readingBook.setAuthor(author);
        readingBook.setLatestUpdateChapterAddress(p.get(3).select("a[href]").attr("href"));
        readingBook.setLatestUpdateTime(p.get(2).text().split("：")[1]);
        readingBook.setLatestUpdateChapter(p.get(3).text().split("：")[1]);
        return readingBook;
    }
}
