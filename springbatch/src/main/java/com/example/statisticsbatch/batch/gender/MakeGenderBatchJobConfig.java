package com.example.statisticsbatch.batch.gender;

import com.example.statisticsbatch.persistence.vo.FollowerVO;
import com.example.statisticsbatch.persistence.vo.batch.GenderVO;
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

import java.time.LocalDateTime;
import java.util.Map;

@EnableBatchProcessing
@Configuration
public class MakeGenderBatchJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private static final Logger log = LoggerFactory.getLogger(MakeGenderBatchJobConfig.class);

    /**
     * Gender 통계 Batch Job /
     * makeGenderBatchStep() Step을 실행
     * @return
     */
    @Bean
    public Job makeGenderBatchJob() {
        log.info("makeGenderBatchJob Start");
        return this.jobBuilderFactory.get("makeGenderBatchJob")
                .start(makeGenderBatchStep())
                .build();
    }

    /**
     * 데이터 읽기, 데이터 가공, 데이터 쓰기 설정 메소드
     * @return
     */
    @Bean
    public Step makeGenderBatchStep() {
        return this.stepBuilderFactory.get("makeGenderBatchStep")
                // 데이터를 가공하기 위한 개수 지정
                // db에 데이터가 1000개가 있고 chunk를 100으로 지정하면 100개 씩 총 10번 동작함.
                // <FollowerVO, GenderVO> FollowerVO : 호출 데이터 VO, GenderVO : 가공 후 DB 저장 데이터 VO
                .<FollowerVO, GenderVO>chunk(2)
                // 데이터 호출을 위한 메소드 지정
                .reader(addGenderBatchItemReader(null))
                // 데이터 가공을 위한 메소드 지정
                .processor(addGenderBatchItemProcessor())
                // 가공한 데이터 저장을 위한 메소드 지정
                .writer(addGenderBatchItemWriter())
                .build();
    }

    /**
     * batch 작업을 위한 데이터 호출
     * @param date
     * @return FollowerVO
     */
    @Bean
    @StepScope
    public MyBatisCursorItemReader<FollowerVO> addGenderBatchItemReader(@Value("#{JobParameters['create_at']}") String date) {

        LocalDateTime create_at = LocalDateTime.parse(date);

        MyBatisCursorItemReader<FollowerVO> reader = new MyBatisCursorItemReader<>();
        reader.setName("addGenderBatchItemReader");
        // SqlSessionFactory를 주입받아 MyBatisCursorItemReader를 생성하고 필요한 설정을 추가합니다.
        reader.setSqlSessionFactory(sqlSessionFactory);
        // setQueryId 메서드를 사용하여 MyBatis의 Mapper XML에서 사용하는 쿼리의 ID를 지정합니다.
        // 해당 쿼리는 PassMapper.xml에 정의되어 있어야 합니다.
        reader.setQueryId("com.example.statisticsbatch.persistence.mapper.FollowerMapper.genderData");
        reader.setParameterValues(Map.of("create_at", create_at));
        // 파라미터를 추가해야 할 때 콤마(,)를 사용하여 추가하면 됩니다.
        // reader.setParameterValues(Map.of("create_at", create_at, "modify_at", modify_at));

        return reader;
    }

    /**
     * addGenderBatchItemReader() 에서 호출한 데이터를 가공
     * @return GenderVO
     */
    @Bean            // <FollowerVO, GenderVO> FollowerVO : 호출 데이터 VO, GenderVO : 가공 후 DB 저장 데이터 VO
    public ItemProcessor<FollowerVO, GenderVO> addGenderBatchItemProcessor() {
        return new MakeGenderBatchItemProcessor();
    }

    /**
     * addGenderBatchItemProcessor()에서 가공한 데이터를 DB table 에 저장
     * @return
     */
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<GenderVO> addGenderBatchItemWriter() {
        MyBatisBatchItemWriter<GenderVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        // MyBatisCursorItemReader 메소드의 reader.setQueryId() 와 동일함.
        writer.setStatementId("com.example.statisticsbatch.persistence.mapper.BatchMapper.setGender");

        return writer;
    }

}
