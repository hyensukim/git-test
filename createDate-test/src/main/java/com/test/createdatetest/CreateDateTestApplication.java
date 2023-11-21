package com.test.createdatetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class CreateDateTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreateDateTestApplication.class, args);
    }

}
