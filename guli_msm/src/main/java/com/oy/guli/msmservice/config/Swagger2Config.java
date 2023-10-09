package com.oy.guli.msmservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author OY
 * @Date 2021/2/27
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .build();

    }

    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("网站-阿里云验证码API文档")
                .description("本文档描述了阿里云验证码服务接口定义")
                .version("1.0")
                .contact(new Contact("OY", "http://oy6090.top", "2097291754@qq.com"))
                .build();
    }
}
