package xwh.com.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

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
    private final int TIME_OUT = 10 * 1000;
    private Handler mHandler;
    private LoadAsyncTask loadAsyncTask;
    private ReadingBook readingBook;

    public LoadContentUtil(Handler handler){
        mHandler = handler;
    }

    public void startLoad(ReadingBook readingBook){
        this.readingBook = readingBook;
        loadAsyncTask = null;
        loadAsyncTask = new LoadAsyncTask();
        loadAsyncTask.setReadingBook(readingBook);
        loadAsyncTask.execute();
    }

    public ReadingBook getReadingBook(){
        return readingBook;
    }

    class LoadAsyncTask extends AsyncTask<String,Integer,String>{
        private ReadingBook readingBook;

        public LoadAsyncTask(){}

        public LoadAsyncTask(ReadingBook readingBook){
            this.readingBook = readingBook;
        }

        public void setReadingBook(ReadingBook readingBook){
            this.readingBook = readingBook;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mHandler.sendEmptyMessage(2);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                Document document = Jsoup.connect(readingBook.getNextChapterUrl()).get();
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
                result = text.substring(0,index);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    }
}
