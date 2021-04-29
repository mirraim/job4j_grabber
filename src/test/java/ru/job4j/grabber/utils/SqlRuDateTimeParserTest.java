package ru.job4j.grabber.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SqlRuDateTimeParserTest {

    @Test
    public void whenToday() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "сегодня, 02:30";
        LocalDateTime rsl = parser.parse(date);
        assertEquals(rsl.toLocalDate(), LocalDate.now());
    }

    @Test
    public void whenYesterday() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "вчера, 02:30";
        LocalDateTime rsl = parser.parse(date);
        assertEquals(rsl.toLocalDate(), LocalDate.now().minusDays(1));
    }

    @Test
    public void whenDate() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "13 ноя 16, 02:30";
        LocalDateTime rsl = parser.parse(date);
        LocalDateTime expected = LocalDateTime.of(2016, 11, 13, 2, 30);
        assertEquals(rsl, expected);
    }
}