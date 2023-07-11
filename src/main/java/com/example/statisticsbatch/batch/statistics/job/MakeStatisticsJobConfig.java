package com.example.statisticsbatch.batch.statistics.job;

import com.example.statisticsbatch.persistence.vo.StatisticsVO;
import com.example.statisticsbatch.persistence.vo.StudentVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MakeStatisticsJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private static final Logger log = LoggerFactory.getLogger(MakeStatisticsJobConfig.class);

    // Step 을 실행 시키는 담당
    @Bean
    public Job makeStatisticsJob() {
        log.info("makeStatisticsJob Start");
        return this.jobBuilderFactory.get("makeStatisticsJob")
                .start(makeStatisticsStep())
                .build();
    }

    // 일련의 데이터 처리 과정을 담당
    @Bean
    public Step makeStatisticsStep() {
        return this.stepBuilderFactory.get("makeStatisticsStep")
                // 데이터를 가공하기 위한 개수 지정
                // db에 데이터가 1000개가 있고 chunk를 100으로 지정하면 100개 씩 총 10번 동작함.
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
        reader.setQueryId("Mapper interface 경로 + Mapper.class 이름 + 쿼리 id");
//        reader.setParameterValues(Map.of("from", from, "to", to));
        return reader;
    }

    // 호출한 데이터 가공
    @Bean
    public ItemProcessor<StudentVO, StatisticsVO> addStatisticsItemProcessor() {
        return new MakeStatisticsItemProcessor();
//        return studentVO -> {
//            StatisticsVO statisticsVO = new StatisticsVO();
//            statisticsVO.setFavorite_subject(item.getStudent_favorite_subject());
//            return statisticsVO;
//        };
    }

    // 가공한 데이터 결과 db table 에 저장
    @Bean
    @StepScope
    public MyBatisBatchItemWriter<StatisticsVO> addStatisticsItemWriter() {
        MyBatisBatchItemWriter<StatisticsVO> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        // MyBatisCursorItemReader 메소드의 reader.setQueryId() 와 동일함.
        writer.setStatementId("Mapper interface 경로 + Mapper.class 이름 + 쿼리 id");

        return writer;
    }

}
