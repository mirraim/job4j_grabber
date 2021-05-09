package ru.job4j.grabber;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public PsqlStore(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cnn.prepareStatement(
                "insert into post (name, text, link, created) values (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTopic());
            statement.setString(2, post.getDetails());
            statement.setString(3, post.getUrl());
            statement.setString(4, post.getDate().toString());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(String.valueOf(generatedKeys.getInt(1)));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement(
                "select * from post;"
        )) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rsl.add(
                            new Post().setId(String.valueOf(resultSet.getInt("id")))
                                    .setTopic(resultSet.getString("name"))
                                    .setUrl(resultSet.getString("link"))
                                    .setDetails(resultSet.getString("text"))
                                    .setDate(LocalDateTime.parse(resultSet.getString("created"))
                    ));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post findById(String id) {
        Post rsl = null;
        try (PreparedStatement statement = cnn.prepareStatement(
                "select * from post where id=?;"
        )) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rsl = new Post().setId(String.valueOf(resultSet.getInt("id")))
                                    .setTopic(resultSet.getString("name"))
                                    .setUrl(resultSet.getString("link"))
                                    .setDetails(resultSet.getString("text"))
                                    .setDate(LocalDateTime.parse(resultSet.getString("created"))
                                    );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rsl;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

}
