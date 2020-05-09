package com.rui.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.rui.edu.mapper")
@ComponentScan({"com.rui.edu","com.rui.common"})
public class OnlineStudyCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStudyCmsApplication.class, args);
    }

}
