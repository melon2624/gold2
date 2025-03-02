package com.melo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoldApplication.class, args);
    }


}
