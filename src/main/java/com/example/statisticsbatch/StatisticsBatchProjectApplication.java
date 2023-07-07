package com.example.statisticsbatch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.statisticsbatch.persistence.mapper")
public class StatisticsBatchProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsBatchProjectApplication.class, args);
    }

}
