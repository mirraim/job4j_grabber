package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * Вывод в консоль сообщений каждые 10 секунд
 */
public class AlertRabbit {
    private Properties config;

    public AlertRabbit(Properties config) {
        this.config = config;
    }

    public static void main(String[] args) {
        AlertRabbit alertRabbit = new AlertRabbit(getProperties("rabbit.properties"));
        try (Connection cn = alertRabbit.init(alertRabbit.config)) {
            alertRabbit.executeScheduler(cn, alertRabbit.config);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    /**
     * Метод получает интервал из .properties
     * @param config объект Properties
     * @return Значение интервала в Integer
     */
    public static Integer getInterval(Properties config) {
        return Integer.parseInt(config.getProperty("rabbit.interval"));
    }

    public void executeScheduler(Connection connection, Properties properties)
                                            throws SchedulerException, InterruptedException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(getJob(connection), getTrigger(properties));
        Thread.sleep(10000);
        scheduler.shutdown();
    }

    /**
     * Подключение к БД
     * @param config принимает Properties
     * @return Connection
     * @throws SQLException исключение
     * @throws ClassNotFoundException исключение
     */
    private Connection init(Properties config) throws SQLException, ClassNotFoundException {
       Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
    }

    /**
     * Возвращает Properties из файла
     * @param path принимает путь к файлу в формате строки
     * @return Properties
     */
    private static Properties getProperties(String path) {
        Properties config = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream(path)) {
            config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    /**
     * Метод создает и настраивает Job для Schedule
     * @param connection - подключение к БД
     * @return задача, которую выполняет класс
     */
        private JobDetail getJob(Connection connection) {
        JobDataMap data = new JobDataMap();
        data.put("store", connection);

        JobDetail job = newJob(Rabbit.class)
                .usingJobData(data)
                .build();
        return job;
    }

    /**
     * Метод создает расписание(периодичность запуска) и возвращает триггер для гачала запуска
     * @param config принимает Properties с указаением интервала
     * @return Trigger с встроенным расписанием
     */
    private Trigger getTrigger(Properties config) {
        //Создание расписания. Здесь настраивается периодичность запуска
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(getInterval(config))
                .repeatForever();

        //Триггер - это указание, когда будет осуществлен запуск. В данной ситуации - сразу
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        return trigger;
    }

    /**
     * Здесь описывается задача, которую будет исполнять основной класс
     */
    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        /**
         * Метод исполнения Job
         * Выводит в консоль текст
         * Получает Connection из контекста
         * Добавляет новую запись с текущим временем в базу данных в таблицу rabbit
          * @param context контекст
         * @throws JobExecutionException исключение
         */
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("store");

            try (PreparedStatement statement =
                         cn.prepareStatement("insert into rabbit(created_date) values (?)")) {
                statement.setLong(1,  System.currentTimeMillis());
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
