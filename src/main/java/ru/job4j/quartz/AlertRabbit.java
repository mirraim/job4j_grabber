package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * Вывод в консоль сообщений каждые 10 секунд
 */
public class AlertRabbit {

    public static void main(String[] args) {
        try {
            //Создание класса, управляющего всеми работами
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            //Создание задачи
            JobDetail job = newJob(Rabbit.class).build();

            //Создание расписания. Здесь настраивается периодичность запуска
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(getInterval("rabbit.properties"))
                    .repeatForever();

            //Триггер - это указание, когда будет осуществлен запуск. В данной ситуации - сразу
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static Integer getInterval(String path) {
        String period = "0";
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream(path)) {
            Properties config = new Properties();
            config.load(in);
            period = config.getProperty("rabbit.interval");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(period);
    }

    /**
     * Здесь описывается задача, которую будет исполнять основной класс
     */
    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }
}
