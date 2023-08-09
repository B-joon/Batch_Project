package com.example.springbatch5.batch.gender.schedul;

import com.example.springbatch5.batch.gender.MakeGenderBatchJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Gender Batch Scheduler
 */

@Component
public class GenderScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MakeGenderBatchJobConfig genderJob;

    private static final Logger log = LoggerFactory.getLogger(GenderScheduler.class);

    @Scheduled(cron = "${spring.task.scheduling.cron.gender}", zone="Asia/Seoul") // cron 표현식 초 분 시 일 월 주 년
    public void runGenderBatchJob() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(10);
        LocalDateTime res = yesterday.with(LocalTime.of(8, 0));
        // 파라미터가 필요한 경우 아래와 같이 추가
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("create_at", res.toString())
                .addDate("date", new Date())
                .toJobParameters();
//        viewJobInfo("JobName");

        log.info("Starting the makeGenderBatchJob");

        try {
            JobExecution execution = jobLauncher.run(genderJob.makeGenderBatchJob(jobRepository), jobParameters);
            log.info("makeGenderBatchJob Status : " + execution.getStatus());
            log.info("makeGenderBatchJob completed");
        } catch (Exception e) {
            log.info(e.toString());
            log.info("Gender Batch Job Failed");
        }

    }

    /**
     * Batch 작업 확인 및 작업 삭제
     * @param jobName
     */
//    public void viewJobInfo(String jobName) {
//        List<JobInstance> jobInstances = jobExplorer.getJobInstances(jobName, 0, 100);
//        for (JobInstance jobInstance : jobInstances) {
//            List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
//            for (JobExecution jobExecution : jobExecutions) {
//                log.info("Job Name: " + jobExecution.getJobInstance().getJobName());
//                log.info("Job Parameters: " + jobExecution.getJobParameters());
//                log.info("Job Status: " + jobExecution.getStatus());
//                if ("makeBatchJob".equals(jobExecution.getJobInstance().getJobName()) && BatchStatus.COMPLETED.equals(jobExecution.getStatus())) {
//                    jobRepository.deleteJobExecution(jobExecution);
//                }
//            }
//        }
//    }

}
