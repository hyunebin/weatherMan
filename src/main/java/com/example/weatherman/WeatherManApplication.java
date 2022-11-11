package com.example.weatherman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//27a33bd90fd310111ea89decca81e65c
@SpringBootApplication
@EnableScheduling
public class WeatherManApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherManApplication.class, args);
    }

}
