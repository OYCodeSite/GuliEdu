package com.oy.guli.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author OY
 * @Date 2021/3/27
 */
@EnableFeignClients
@EnableDiscoveryClient // nacos 注册
@SpringBootApplication
@ComponentScan(basePackages = {"com.oy.guli"})
@MapperScan("com.oy.guli.educenter.mapper")
public class UcenterApplication {
    
     public static void main(String[] args) {
           SpringApplication.run(UcenterApplication.class, args);
      }
}
