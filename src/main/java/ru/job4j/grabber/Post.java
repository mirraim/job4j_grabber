package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String id;
    private String topic;
    private String url;
    private String details;
    private LocalDateTime date;

    public Post() {
    }

    public Post(String id, String topic, String url, String details, LocalDateTime date) {
        this.id = id;
        this.topic = topic;
        this.url = url;
        this.details = details;
        this.date = date;
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

    public Post setId(String id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Post setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public Post setDetails(String details) {
        this.details = details;
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
        return Objects.equals(id, post.id)
                && Objects.equals(topic, post.topic)
                && Objects.equals(url, post.url)
                && Objects.equals(details, post.details)
                && Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, url, details, date);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Post{");
        sb.append("id='").append(id).append('\'');
        sb.append(", topic='").append(topic).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
