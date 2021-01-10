package com.nz.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:config/druid/druid-bean.xml"})
@MapperScan("com.nz.test.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class TaskMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMybatisApplication.class, args);
	}

}
