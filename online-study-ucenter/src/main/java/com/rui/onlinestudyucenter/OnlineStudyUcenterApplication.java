package com.rui.onlinestudyucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.rui.edu.mapper")
@ComponentScan({"com.rui.edu","com.rui.common"})
@EnableDiscoveryClient
public class OnlineStudyUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStudyUcenterApplication.class, args);
    }

}
