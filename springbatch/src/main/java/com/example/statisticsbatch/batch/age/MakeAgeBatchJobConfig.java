package com.example.statisticsbatch.batch.age;

import com.example.statisticsbatch.persistence.vo.FollowerVO;
import com.example.statisticsbatch.persistence.vo.batch.AgeVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@EnableBatchProcessing
@Configuration
public class MakeAgeBatchJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private static final Logger log = LoggerFactory.getLogger(MakeAgeBatchJobConfig.class);

    /**
     * Age 통계 Batch Job /
     * makeAgeBatchStep() Step을 실행
     * @return
     */
    @Bean
    public Job makeAgeBatchJob() {
        log.info("makeAgeBatchJob Start");
        return this.jobBuilderFactory.get("makeAgeBatchJob")
                .start(makeAgeBatchStep())
                .build();
    }

    /**
     * 데이터 읽기, 데이터 가공, 데이터 쓰기 설정 메소드
     * @return
     */
    @Bean
    public Step makeAgeBatchStep() {
        return this.stepBuilderFactory.get("makeAgeBatchStep")
                .<FollowerVO, AgeVO>chunk(100)
                .reader(addAgeBatchItemReader(null))
                .processor(addAgeBatchItemProcessor())
                .writer(addAgeBatchItemWriter())
                .build();
    }

    /**
     * batch 작업을 위한 데이터 호출
     * @param age
     * @return FollowerVO
     */
    @Bean
    @StepScope
    public MyBatisCursorItemReader<FollowerVO> addAgeBatchItemReader(@Value("#{JobParameters['age']}") Long age) {
        MyBatisCursorItemReader<FollowerVO> reader = new MyBatisCursorItemReader<>();

        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.example.statisticsbatch.persistence.mapper.FollowerMapper.ageData");
        reader.setParameterValues(Map.of("age", age));

        return reader;
    }

    /**
     * addAgeBatchItemReader() 에서 호출한 데이터를 가공
     * @return AgeVO
     */
    @Bean
    public ItemProcessor<FollowerVO, AgeVO> addAgeBatchItemProcessor() {
        return followerVO -> {

            log.info(followerVO.toString());

            AgeVO ageVO = new AgeVO();

            ageVO.setAge(followerVO.getAge());
            ageVO.setCount(followerVO.getCount());

            return ageVO;
        };
    }

    /**
     * 가공한 데이터를 DB table 에 저장
     * @return
     */
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<AgeVO> addAgeBatchItemWriter() {
        MyBatisBatchItemWriter<AgeVO> writer = new MyBatisBatchItemWriter<>();

        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.example.statisticsbatch.persistence.mapper.BatchMapper.setAge");

        return writer;
    }

}
