package com.example.statisticsbatch.batch.job;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@MapperScans({
        @MapperScan("com.example.statisticsbatch.persistence.mapper"),
        @MapperScan("com.example.statisticsbatch.batch.mapper")
})
public class TestBatchConfig {
}
