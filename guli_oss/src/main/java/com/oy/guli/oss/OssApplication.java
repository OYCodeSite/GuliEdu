package com.oy.guli.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author OY
 * @Date 2021/3/6
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OssApplication {
     public static void main(String[] args) {
           SpringApplication.run(OssApplication.class, args);
     }
}
