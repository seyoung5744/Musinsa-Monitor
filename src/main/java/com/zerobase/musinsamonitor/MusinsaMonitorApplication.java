package com.zerobase.musinsamonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MusinsaMonitorApplication {

    //    private static final int[] pages = {1, 11, 21, 31, 41, 51, 61, 71, 81, 91};
    private static final int[] pages = {1};

    public static void main(String[] args) {
        SpringApplication.run(MusinsaMonitorApplication.class, args);

//        Crawler crawler = new MusinsaCrawler();
//
//        CrawledResult result = crawler.crawling();
//
//        System.out.println(result);
//        System.out.println(Category.numToString());


    }
}
