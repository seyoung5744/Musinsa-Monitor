package com.zerobase.musinsamonitor;

import com.zerobase.musinsamonitor.crawler.MusinsaCrawler;
import com.zerobase.musinsamonitor.model.CrawledResult;
import com.zerobase.musinsamonitor.model.constants.Category;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusinsaMonitorApplication {

//    private static final int[] pages = {1, 11, 21, 31, 41, 51, 61, 71, 81, 91};
    private static final int[] pages = {1};

    public static void main(String[] args) {
//        SpringApplication.run(MusinsaMonitorApplication.class, args);

//        MusinsaCrawler musinsaCrawler = new MusinsaCrawler();
//        CrawledResult crawledResult = null;
//        for (int startPage : pages) {
//            crawledResult= musinsaCrawler.crawling(startPage, startPage);
//        }
//
//        System.out.println(crawledResult);

        System.out.println(Category.numToString());

    }
}
