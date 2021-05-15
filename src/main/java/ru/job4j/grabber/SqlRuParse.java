package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                String attrUrl = href.attr("href");
                posts.add(detail(attrUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(link).get();
            post.setTopic(getTopic(doc))
                    .setUrl(link)
                    .setDetails(getDetails(doc))
                    .setDate(getDate(doc));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public String getDetails(Document doc) {
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

    public LocalDateTime getDate(Document doc) {

        Elements row = doc.select("td.msgFooter");
        Element element = row.first();
        String date = element.text();
        String rsl = date.substring(0, date.indexOf('[')).trim();
        return new SqlRuDateTimeParser().parse(rsl);
    }

    public String getTopic(Document doc) {
        Elements row = doc.select(".messageHeader");
        Element element = row.first();
        return element.text();
    }
}
