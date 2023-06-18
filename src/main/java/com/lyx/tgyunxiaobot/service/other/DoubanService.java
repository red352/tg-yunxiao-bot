package com.lyx.tgyunxiaobot.service.other;

import com.lyx.tgyunxiaobot.client.DoubanClient;
import com.lyx.tgyunxiaobot.model.other.douban.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lyx
 * @createTime 2023/6/19 0:15
 */
@Service
public class DoubanService {
    @Autowired
    private DoubanClient doubanClient;
    @Autowired
    private TemplateEngine templateEngine;

    @Cacheable(value = "DoubanNew")
    public Map<String, String> getNewMovies() {
        Document document = getDocument();
        Elements tables = document.select(".article > .indent > * > table");
        List<Movie> movieList = new ArrayList<>(tables.size());
        tables.forEach(table -> {
            try {
                Element item = table.selectFirst(".item");
                Element a = Objects.requireNonNull(item).selectFirst("a.nbg");
                String url = Objects.requireNonNull(a).absUrl("href");
                String name = a.attr("title");
                String imgUrl = Objects.requireNonNull(a.selectFirst("img")).absUrl("src");
                String rating = Objects.requireNonNull(item.selectFirst(".rating_nums")).text();
                movieList.add(new Movie(name, imgUrl, rating, url));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Context context = new Context();
        return movieList.stream().collect(Collectors.toMap(
                Movie::getCoverUrl,
                movie -> processMovie(context, movie)
        ));
    }

    @Cacheable(value = "DoubanWeek")
    public List<String> getWeeklyMovies() {
        Document document = getDocument();
        Elements movieItems = document.select("#listCont2 > li");
        List<Movie> movieList = new ArrayList<>(movieItems.size());
        for (Element movieItem : movieItems) {
            Element nameElement = Objects.requireNonNull(movieItem.selectFirst("a"));
            String name = nameElement.text();
            String movieUrl = nameElement.absUrl("href");
            Movie movie = new Movie();
            movie.setName(name);
            movie.setMovieUrl(movieUrl);
            movieList.add(movie);
        }
        return renderDataList(movieList);
    }

    @CacheEvict(cacheNames = {"DoubanNew", "DoubanWeek"}, allEntries = true)
    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    public void cacheEvict() {
        getNewMovies();
        getWeeklyMovies();
    }

    private List<String> renderDataList(List<Movie> movieList) {
        Context context = new Context();
        List<String> data = new ArrayList<>(movieList.size());
        movieList.forEach(movie -> {
            String stringMovie = processMovie(context, movie);
            data.add(stringMovie);
        });
        return data;
    }

    private String processMovie(Context context, Movie movie) {
        context.setVariable("movie", movie);
        return templateEngine.process("douban", context);
    }

    public Document getDocument() {
        Mono<String> data = doubanClient.getDocumentData();
        return Jsoup.parse(Objects.requireNonNull(data.block()));
    }
}
