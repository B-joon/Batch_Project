package com.example.springbatch5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.example.springbatch5.persistence.mapper")
@SpringBootApplication
public class Springbatch5Application {

	public static void main(String[] args) {
		SpringApplication.run(Springbatch5Application.class, args);
	}

}
