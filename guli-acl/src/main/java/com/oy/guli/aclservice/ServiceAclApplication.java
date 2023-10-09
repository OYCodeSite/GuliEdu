package com.oy.guli.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author OY
 * @Date 2021/4/8
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.oy.guli")
@MapperScan("com.oy.guli.aclservice.mapper")
public class ServiceAclApplication {
     public static void main(String[] args) {
           SpringApplication.run(ServiceAclApplication.class, args);
      }
}
