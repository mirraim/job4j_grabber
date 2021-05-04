package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse {
    List<Post> posts;

    public SqlRuParse() {
        posts = new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse parser = new SqlRuParse();
       String url = "https://www.sql.ru/forum/job-offers";
        for (int i = 1; i < 5; i++) {
            parser.parsePage(url + "/" + i);
        }
    }

    public void parsePage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Post post = new Post();
            Element href = td.child(0);
            String attrUrl = href.attr("href");
            post.setUrl(attrUrl);
            post.setTopic(href.text());
            post.setDetails(getDetails(attrUrl));
            //Element date = td.parent().child(5);
            //System.out.println(date.text());
            post.setDate(getDate(attrUrl));
            posts.add(post);
        }
    }

    public String getDetails(String href) throws IOException {
        Document doc = Jsoup.connect(href).get();
        Elements row = doc.select("td.msgBody");
        int count = 0;
        for (Element td : row) {
            count++;
            if (count == 2) {
                return td.text();
            }
        }
        return "";
    }

    public LocalDateTime getDate(String href) throws IOException {
        Document doc = Jsoup.connect(href).get();
        Elements row = doc.select("td.msgFooter");
        Element element = row.first();
        String date = element.text();
        String rsl = date.substring(0, date.indexOf('[')).trim();
        return new SqlRuDateTimeParser().parse(rsl);
    }
}
