package com.oy.guli.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author OY
 * @Date 2021/3/21
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages={"com.oy.guli.vod","com.oy.guli.common"})
public class VodApplication {
     public static void main(String[] args) {
           SpringApplication.run(VodApplication.class, args);
      }
}
