package com.example.statisticsbatch.batch.age.schedul;

import com.example.statisticsbatch.batch.age.MakeAgeBatchJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
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
    private MakeAgeBatchJobConfig jobConfig;

    private static final Logger log = LoggerFactory.getLogger(AgeScheduler.class);

    @Scheduled(cron = "${spring.task.scheduling.cron.age}", zone="Asia/Seoul")
    public void runAgeBatchJob() {
        try {
            // 파라미터가 필요한 경우 아래와 같이 추가
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("age", 19L)
                    .addDate("date", new Date())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(jobConfig.makeAgeBatchJob(), jobParameters);
            BatchStatus batchStatus = jobExecution.getStatus();

            if (batchStatus == BatchStatus.STARTED) {
                log.info("Age 통계 Batch Start");
            } else if (batchStatus == BatchStatus.COMPLETED) {
                log.info("Age 통계 Batch End");
            }
        } catch (JobExecutionException e) {
            log.info("Age 통계 Batch 작업 중 Error 발생 : " + e);
        }
    }

}
