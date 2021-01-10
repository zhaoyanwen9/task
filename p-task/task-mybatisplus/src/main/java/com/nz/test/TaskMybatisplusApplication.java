package com.nz.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * https://www.cnblogs.com/liuyj-top/p/12976396.html
 */
@EnableDiscoveryClient
@MapperScan("com.nz.test.mapper.**")
@SpringBootApplication
public class TaskMybatisplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskMybatisplusApplication.class, args);
    }

}
