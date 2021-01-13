package com.nz.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.nz.test.mapper")
@SpringBootApplication
public class TaskShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskShiroApplication.class, args);
	}

}
