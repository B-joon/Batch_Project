package com.example.springbatch5.batch.age.schedul;

import com.example.springbatch5.batch.age.MakeAgeBatchJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Age Batch Scheduler
 */

@Component
public class AgeScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MakeAgeBatchJobConfig ageJob;

    private static final Logger log = LoggerFactory.getLogger(AgeScheduler.class);

    @Scheduled(cron = "${spring.task.scheduling.cron.age}", zone="Asia/Seoul")
    public void runAgeBatchJob() {

        // 파라미터가 필요한 경우 아래와 같이 추가
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("age", 19L)
                .addDate("date", new Date())
                .toJobParameters();

        log.info("Starting the makeAgeBatchJob");

        try {
            JobExecution execution = jobLauncher.run(ageJob.makeAgeBatchJob(jobRepository), jobParameters);
            log.info("makeAgeBatchJob Status : " + execution.getStatus());
            log.info("makeAgeBatchJob completed");
        } catch (Exception e) {
            log.info(e.toString());
            log.info("Age Batch Job Failed");
        }

    }

}
