package com.codecademy.goldmedal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GoldMedalApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoldMedalApplication.class, args);
    }

}
