package com.oy.guli.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author OY
 * @Date 2021/3/24
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.oy.guli"}) //指定扫描位置
@MapperScan("com.oy.guli.educms.mapper")
public class CmsApplication {
     public static void main(String[] args) {
           SpringApplication.run(CmsApplication.class, args);
      }
}
