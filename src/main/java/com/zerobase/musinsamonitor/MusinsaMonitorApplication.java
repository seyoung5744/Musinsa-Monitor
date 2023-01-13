package com.zerobase.musinsamonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class MusinsaMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusinsaMonitorApplication.class, args);
    }
}
