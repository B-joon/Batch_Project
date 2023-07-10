package com.example.statisticsbatch.batch.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = {MakeStatisticsJobConfig.class, TestBatchConfig.class})
class MakeStatisticsJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test_expirePassesStep() throws Exception {

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();

        // then
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals("makeStatisticsJob", jobInstance.getJobName());

    }

}