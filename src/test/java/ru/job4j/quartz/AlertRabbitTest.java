package ru.job4j.quartz;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlertRabbitTest {

    @Test
    public void whenGetInterval() {
        int rsl = AlertRabbit.getInterval("rabbit.properties");
        assertEquals(rsl, 10);
    }
}