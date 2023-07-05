package com.example.statisticsbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("통계 작업이 성공적으로 완료되었습니다.");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.error("통계 작업이 실패하였습니다.");
        }
    }
}
