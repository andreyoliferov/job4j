package parser;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Приложение по поиску вакансий для Java разработчиков
 * в настоящее время выполняется поиск по сайтам
 * sql.ru - методом парсинга
 * hh.ru - GET запросом к публичному API
 * @autor aoliferov
 * @since 14.11.2018
 */
public class HunterApp implements Job {

    public static final Logger LOG = LoggerFactory.getLogger(HunterApp.class);
    public static Properties properties;

    /**
     * Точка запуска основного приложения по поиску вакансий на сайтах
     * sql.ru(парсинг) и hh.ru(get запросы через публичный api)
     */
    public static void main(String[] args) throws Exception {
        properties = new Properties();
        try (InputStream is = StoreSQL.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(is);
        }
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail job = newJob(HunterApp.class).withIdentity("Поиск вакансий Java").build();
        /* Триггер в котором указывается интервал, сразу начинает выполнение */
        SimpleTrigger trigger = newTrigger()
                .withIdentity("Расписание поиска вакансий Java")
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(Integer.parseInt(properties.getProperty("cron.periodH")))
                        .repeatForever())
                .forJob("Поиск вакансий Java")
                .build();
        /* Триггер, в котором можно указать расписание, выполнение строго по расписанию */
//        CronTrigger trigger = newTrigger()
//                 .withIdentity("Расписание поиска вакансий Java")

//                 .withSchedule(cronSchedule(properties.getProperty("cron.time")))
//                 .forJob("Поиск вакансий Java")
//                 .build();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("Поиск вакансий начат! {}", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        try (StoreSQL store = new StoreSQL()) {
            Thread tSql = new Thread(() -> new HeadHunterAPI(store).perform());
            Thread tHh = new Thread(() -> new ParserSqlRu(store).perform());
            tSql.start();
            tHh.start();
            tSql.join();
            tHh.join();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.info("Поиск вакансий завершен успешно! {}", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
