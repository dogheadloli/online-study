package com.rui.edu.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: ChangRui
 * @Date: 2020/4/1
 * @Description:
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(MsmApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/edumsm/.*")))
                .build();
    }

    private ApiInfo MsmApiInfo() {

        return new ApiInfoBuilder()
                .title("网站-短信发送")
                .description("本文档描述了短信发送微服务接口定义")
                .version("1.0")
                .contact(new Contact("rui", "http://xxx.com", "2598740025@qq.com"))
                .build();
    }
}
