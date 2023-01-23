package com.zerobase.musinsamonitor.crawler;

import com.zerobase.musinsamonitor.crawler.dto.CrawledResult;

public interface Crawler {
    CrawledResult crawling(String category);
}
