package com.mycompany.myapp.config;

import com.mycompany.myapp.service.ComplexJobService;
import com.mycompany.myapp.utils.jobs.ComplexScheduledJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@ComponentScan("com.jobs")
public class QuartzJobSchedulerConf {

    @Bean//this is complex job1
    public JobDetailFactoryBean jobDetailFactoryBean(ComplexJobService complexJobService){

        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(ComplexScheduledJob.class);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("complexJobService", complexJobService);
        factory.setJobDataAsMap(map);
        factory.setDurability(true);

        return factory;
    }
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(ComplexJobService complexJobService){

        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailFactoryBean(complexJobService).getObject());
        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        stFactory.setStartDelay(3000);

        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ComplexJobService complexJobService) {

        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();

        Properties properties = new Properties();

        properties.setProperty("org.quartz.threadPool.threadCount", "1");

        //scheduler.setConfigLocation(new ClassPathResource("\\properties\\quartz.properties"));
        scheduler.setTriggers(
            cronTriggerFactoryBean(complexJobService).getObject());
        scheduler.setQuartzProperties(properties);
        return scheduler;
    }
}
