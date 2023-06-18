package com.lyx.tgyunxiaobot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

/**
 * @author lyx
 * @createTime 2023/6/18 22:43
 */
public class Douban {
    public static void main(String[] args) throws IOException {
        String url = "https://movie.douban.com/chart";
        Document document = Jsoup.connect(url).get();
        weekly(document);
        newMovie(document);
    }

    private static void weekly(Document document) {
        Elements movieItems = document.select("#listCont2 > li");
        for (Element movieItem : movieItems) {
            Element nameElement = movieItem.selectFirst("a");
            String name = null;
            String coverUrl = null;
            if (nameElement != null) {
                name = nameElement.text();
                coverUrl = nameElement.absUrl("href");
            }
            System.out.println("电影名：" + name);
            System.out.println("封面图片链接：" + coverUrl);
            System.out.println("------------------------------");
        }
    }

    private static void newMovie(Document document) {
        Elements tables = document.select(".article > .indent > * > table");
        System.out.println("-----------豆瓣新片榜单----------");
        tables.forEach(table -> {
            String url;
            String name;
            String imgUrl;
            String rating;
            try {
                Element item = table.selectFirst(".item");
                Element a = Objects.requireNonNull(item).selectFirst("a.nbg");
                url = Objects.requireNonNull(a).absUrl("href");
                name = a.attr("title");
                imgUrl = Objects.requireNonNull(a.selectFirst("img")).absUrl("src");
                rating = Objects.requireNonNull(item.selectFirst(".rating_nums")).text();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } 
            System.out.println("电影名：" + name);
            System.out.println("rating = " + rating);
            System.out.println("封面图片链接：" + imgUrl);
            System.out.println("电影地址 = " + url);
            System.out.println("------------------------------");

        });

    }
}
