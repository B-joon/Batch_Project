package com.example.statisticsbatch;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// 스케쥴을 사용하기 위한 어노테이션
@EnableScheduling
// Batch를 사용하기 위한 어노테이션
@EnableBatchProcessing
@SpringBootApplication
@MapperScans({
        @MapperScan("com.example.statisticsbatch.persistence.mapper"),
        @MapperScan("com.example.statisticsbatch.batch.mapper")
})
public class StatisticsBatchProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsBatchProjectApplication.class, args);
    }

}
