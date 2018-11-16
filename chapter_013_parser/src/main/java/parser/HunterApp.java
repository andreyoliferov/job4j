package parser;


import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * @autor aoliferov
 * @since 14.11.2018
 */
public class HunterApp implements Job {

    public static final Logger LOG = LoggerFactory.getLogger(StoreSQL.class);

    public static void main(String[] args) throws Exception {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = newJob(HunterApp.class)
                .withIdentity("myJob", "group1") // name "myJob", group "group1"
                .build();

//        CronTrigger trigger = newTrigger()
//                 .withIdentity("trigger3", "group1")
//                 .startNow()
//                 .withSchedule(cronSchedule("0 0 12 * * ?"))
//                 .forJob("myJob", "group1")
//                 .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .forJob("myJob", "group1")
                .build();

        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("hello");

//        try (StoreSQL store = new StoreSQL()) {
//            Thread tSql = new Thread(() -> new HeadHunterAPI(store).perform());
//            Thread tHh = new Thread(() -> new ParserSqlRu(store).perform());
//            tSql.start();
//            tHh.start();
//            tSql.join();
//            tHh.join();
//        } catch (Exception e) {
//            LOG.error(e.getMessage());
//        }
    }
}
