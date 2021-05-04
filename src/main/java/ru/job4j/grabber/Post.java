package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String id;
    private String topic;
    private String url;
    private String author;
    private String details;
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

    public Post() {
    }

    public String getTopic() {
        return topic;
    }

    public Post setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Post setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Post setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public Post setDetails(String details) {
        this.details = details;
        return this;
    }

    public int getAnswers() {
        return answers;
    }

    public Post setAnswers(int answers) {
        this.answers = answers;
        return this;
    }

    public int getViews() {
        return views;
    }

    public Post setViews(int views) {
        this.views = views;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Post setDate(LocalDateTime date) {
        this.date = date;
        return this;
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
                + ", details=" + details
                + ", answers=" + answers
                + ", views=" + views
                + ", date=" + date + '}';
    }
}
