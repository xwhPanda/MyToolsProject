package xwh.com.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import xwh.com.bean.Author;
import xwh.com.bean.ReadingBook;

/**
 * Created by admin on 2016/12/13.
 */

public class ParseHtmlFormSearch {

    public static List<ReadingBook> getListFromHtml(String html){
        List<ReadingBook> bookList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("result-item result-game-item");
//        Log.e("TAG",elements.get(0).html());
        for (Element element : elements){
            ReadingBook readingBook = new ReadingBook();
            readingBook.setBookPic(getBookImgUrl(element));
            readingBook.setBookName(getBookName(element));
            readingBook.setHostUrl(getBookUrl(element));
            readingBook.setDesc(getDesc(element));
            readingBook.setAuthor(getAuthor(element));
            readingBook.setWordNumber(getValue(getDetailElements(element).get(1).text()));
            readingBook.setClickNumber(getValue(getDetailElements(element).get(2).text()));
            readingBook.setUpdateStatu(getValue(getDetailElements(element).get(3).text()));
            bookList.add(readingBook);
        }
        return bookList;
    }

    /**
     * 获取书籍图片
     * @param element
     * @return
     */
    private static String getBookImgUrl(Element element){
        Elements elements = element.getElementsByClass("result-game-item-pic");
        if (elements != null && elements.size() > 0){
            Elements imgs = elements.get(0).select("[src]");
            return imgs.get(0).attr("abs:src");
        }
        return null;
    }

    /**
     * 获取element下的所有href节点
     * @param element
     * @return
     */
    private static Elements getHrefs(Element element){
        Elements elements = element.getElementsByClass("result-game-item-detail");
        Elements links = elements.get(0).getElementsByClass("result-item-title result-game-item-title");
        Element link = links.get(0);
        Elements hrefs = link.select("a[href]");
        return hrefs;
    }

    /**
     * 获取书籍名
     * @param element
     * @return
     */
    private static String getBookName(Element element){
        Elements hrefs = getHrefs(element);
        return hrefs.get(0).attr("title");
    }

    /**
     * 获取书籍地址
     * @param element
     * @return
     */
    private static String getBookUrl(Element element){
        Elements hrefs = getHrefs(element);
        return hrefs.get(0).attr("href");
    }

    /**
     * 获取简介
     * @param element
     * @return
     */
    private static String getDesc(Element element){
        Elements elements = element.getElementsByClass("result-game-item-desc");
        Element link = elements.get(0);
        return link.text();
    }

    /**
     * 获取 作者，字数，点击，更新状态 节点
     * @param element
     * @return
     */
    private static Elements getDetailElements(Element element){
        Elements elements = element.getElementsByClass("result-game-item-info");
        Element link = elements.get(0);
        Elements links = link.select("p");
        return links;
    }

    /**
     * 获取作者
     * @param element
     * @return
     */
    private static Author getAuthor(Element element){
        Author author = new Author();
        String name = getValue(getDetailElements(element).get(0).text());
        author.setName(name);
        return author;
    }

    /**
     * 处理字数，点击，更新状态
     * @param text
     * @return
     */
    private static String getValue(String text){
        int index = text.indexOf("：");
        return text.substring(index + 1).trim();
    }
}
