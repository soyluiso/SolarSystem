package com.mycompany.myapp.utils.jobs;

import com.mycompany.myapp.service.ComplexJobService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution

@Component
public class ComplexScheduledJob extends QuartzJobBean {

    @Autowired
    private ComplexJobService complexJobService;
    private static int count;

    @Override
    protected void executeInternal(JobExecutionContext jobContext)
        throws JobExecutionException {

        complexJobService.execute();
        count++;
        System.out.println("Job count " + count);
    }

    public void setComplexJobService(ComplexJobService complexJobService) {
        this.complexJobService = complexJobService;
    }
}
