package com.oy.guli.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author OY
 * @Date 2021/4/5
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = "com.oy.guli")
@MapperScan("com.oy.guli.staservice.mapper")
@SpringBootApplication
@EnableScheduling
public class StaApplication {
     public static void main(String[] args) {
           SpringApplication.run(StaApplication.class, args);
      }
}
