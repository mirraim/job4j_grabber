package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String topic;
    private String url;
    private String author;
    private int answers;
    private int views;
    private LocalDateTime date;

    public Post(String topic, String url, String author, int answers, int views, LocalDateTime date) {
        this.topic = topic;
        this.url = url;
        this.author = author;
        this.answers = answers;
        this.views = views;
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return answers == post.answers
                && views == post.views
                && Objects.equals(topic, post.topic)
                && Objects.equals(url, post.url)
                && Objects.equals(author, post.author)
                && Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, url, author, answers, views, date);
    }

    @Override
    public String toString() {
        return "Post{" + "topic='" + topic
                + ", url='" + url
                + ", author='" + author
                + ", answers=" + answers
                + ", views=" + views
                + ", date=" + date + '}';
    }
}