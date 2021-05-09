package ru.job4j.grabber;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class PsqlStoreTest {

    public Connection init() {
        try (InputStream in = new FileInputStream("app.properties")) {
            Properties cfg = new Properties();
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            return DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void save() throws SQLException {
        Post post = new Post().setTopic("Dev")
                .setUrl("url")
                .setDetails("Some details")
                .setDate(LocalDateTime.now());
        PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()));
        int before = store.getAll().size();
        store.save(post);
        assertEquals(store.getAll().size(), before + 1);
    }

    @Ignore
    @Test
    public void getAll() throws SQLException {
        PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()));
        List<Post> posts = store.getAll();
        assertEquals(posts.size(), 1);
    }

    @Test
    public void findById() throws SQLException {
        Post post = new Post().setTopic("Dev")
                .setUrl("url")
                .setDetails("Some details")
                .setDate(LocalDateTime.now());
        PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()));
        store.save(post);
        assertEquals(store.findById(post.getId()), post);
    }
}