package com.example.statisticsbatch.config;

import com.example.statisticsbatch.statistics.StatisticsTasklet;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class BatchJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final StatisticsTasklet statisticsTasklet;

    private static final Logger log = LoggerFactory.getLogger(BatchJobConfiguration.class);

    @Bean
    public Step statisticsStep() {
        return stepBuilderFactory.get("statisticsStep")
                .tasklet(statisticsTasklet)
                .build();
    }

    // 배치 종료 코드
    @Bean
    public Job statisticsJob(Step statisticsStep) {
        return jobBuilderFactory.get("statisticsJob")
                .incrementer(new RunIdIncrementer())
                .flow(statisticsStep)
                .end()
                .listener(new JobCompletionNotificationListener())
                .build();
    }

    // 배치 시작 코드
    @Scheduled(cron = "0 0 8 * * *")
    public void runStatisticsJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher().run(statisticsJob(statisticsStep()), jobParameters);

        log.info("Statistics job completed");
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(new ResourcelessTransactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
