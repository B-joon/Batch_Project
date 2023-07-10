package com.example.statisticsbatch.batch.schedul;

import com.example.statisticsbatch.batch.job.MakeStatisticsJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private MakeStatisticsJobConfig jobConfig;

    private static final Logger log = LoggerFactory.getLogger(BatchScheduler.class);

    // 배치 시작 코드
    @Scheduled(cron = "${spring.task.scheduling.cron}") // cron 표현식 초 분 시 일 월 주 년
    public void runStatisticsJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(jobConfig.makeStatisticsJob(), jobParameters);
    }

}
