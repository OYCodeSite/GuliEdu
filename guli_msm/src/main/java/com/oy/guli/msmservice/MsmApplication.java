package com.oy.guli.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author OY
 * @Date 2021/3/27
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.oy.guli")
public class MsmApplication {
     public static void main(String[] args) {
           SpringApplication.run(MsmApplication.class, args);
      }
}
