package com.oy.guli.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author OY
 * @Date 2021/4/3
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.oy.guli"})
@MapperScan("com.oy.guli.eduorder.mapper")
public class OrdersApplication {
     public static void main(String[] args) {
           SpringApplication.run(OrdersApplication.class, args);
      }
}
