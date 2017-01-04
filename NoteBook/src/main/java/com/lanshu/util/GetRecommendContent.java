package com.lanshu.util;

import com.lanshu.bean.ReadingBook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Author xwh
 * Email 1152046774@qq.com
 * CreateTime 2017/1/3 15:43
 */
public class GetRecommendContent {

    public static List<ReadingBook> getRecommendBook(String html) {
        List<ReadingBook> bookList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        return bookList;
    }
}
