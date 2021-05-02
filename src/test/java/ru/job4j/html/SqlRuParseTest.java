package ru.job4j.html;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParseTest {

    @Test
    public void whenGetDetails() throws IOException {
        SqlRuParse parser = new SqlRuParse();
        String url = "https://www.sql.ru/forum/484798/pravila-foruma";
        String rsl = parser.getDetails(url);
        String expected = "Правилами форума временно считаются правила форума Работа "
                + "связанные с публикацией вакансий. Сообщение было отредактировано: 17 окт 07, 11:30";
        Assert.assertEquals(rsl, expected);
    }

    @Test
    public void whenGetDate() throws IOException {
        SqlRuParse parser = new SqlRuParse();
        String url = "https://www.sql.ru/forum/484798/pravila-foruma";
        LocalDateTime rsl = parser.getDate(url);
        LocalDateTime expected = LocalDateTime.of(2007, 10, 17, 1, 49);
        Assert.assertEquals(rsl, expected);
    }

}