package com.example.statisticsbatch.batch.job;

import com.example.statisticsbatch.batch.mapper.StatisticsMapper;
import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import com.example.statisticsbatch.persistence.vo.StudentVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class MakeStatisticsJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private StatisticsMapper statisticsMapper;

    private static final Logger log = LoggerFactory.getLogger(MakeStatisticsJobConfig.class);

//    // 배치 시작 코드
//    @Scheduled(cron = "0 0 8 * * *")
//    public void runStatisticsJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("jobId", String.valueOf(System.currentTimeMillis()))
//                .toJobParameters();
//
//        jobLauncher().run(statisticsJob(statisticsStep()), jobParameters);
//
//        log.info("Statistics job completed");
//    }
//
//    @Bean
//    public JobLauncher jobLauncher() throws Exception {
//        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
//        jobLauncher.setJobRepository(jobRepository());
//        jobLauncher.afterPropertiesSet();
//        return jobLauncher;
//    }
//
//    @Bean
//    public JobRepository jobRepository() throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTransactionManager(new ResourcelessTransactionManager());
//        factory.afterPropertiesSet();
//        return factory.getObject();
//    }

    // Step 을 실행 시키는 담당
    @Bean
    public Job makeStatisticsJob() {
        log.info("[addStatisticsItemWriter] batch 작업 시작");
        return this.jobBuilderFactory.get("makeStatisticsJob")
                .start(makeStatisticsStep())
                .build();
    }

    // 일련의 데이터 처리 과정을 담당
    @Bean
    public Step makeStatisticsStep() {
        return this.stepBuilderFactory.get("makeStatisticsStep")
                // 데이터를 가공하기 위한 개수 지정
                .<StudentVO, StatisticsVO>chunk(100)
                // 데이터 호출을 위한 메소드 지정
                .reader(addStatisticsItemReader())
                // 데이터 가공을 위한 메소드 지정
                .processor(addStatisticsItemProcessor())
                // 가공한 데이터 저장을 위한 메소드 지정
                .writer(addStatisticsItemWriter())
                .build();
    }

    // batch 작업을 위한 데이터 호출
    @Bean
    @StepScope
    public MyBatisCursorItemReader<StudentVO> addStatisticsItemReader() {
        MyBatisCursorItemReader<StudentVO> reader = new MyBatisCursorItemReader<>();
        reader.setName("addStatisticsItemReader");
        // SqlSessionFactory를 주입받아 MyBatisCursorItemReader를 생성하고 필요한 설정을 추가합니다.
        reader.setSqlSessionFactory(sqlSessionFactory);
        // setQueryId 메서드를 사용하여 MyBatis의 Mapper XML에서 사용하는 쿼리의 ID를 지정합니다.
        // 해당 쿼리는 PassMapper.xml에 정의되어 있어야 합니다.
        reader.setQueryId("");
        return reader;
    }

    // 호출한 데이터 가공
    @Bean
    public ItemProcessor<StudentVO, StatisticsVO> addStatisticsItemProcessor() {
        return new MakeStatisticsItemProcessor();
    }

    // 가공한 데이터 결과 db table 에 저장
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<StatisticsVO> addStatisticsItemWriter() {
        MyBatisBatchItemWriter<StatisticsVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);

        log.info("[addStatisticsItemWriter] batch 작업 종료");

        return writer;
    }

}
