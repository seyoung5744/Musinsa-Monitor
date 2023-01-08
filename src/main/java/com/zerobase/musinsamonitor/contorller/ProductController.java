package com.zerobase.musinsamonitor.contorller;

import com.zerobase.musinsamonitor.crawler.Crawler;
import com.zerobase.musinsamonitor.crawler.MusinsaCrawler;
import com.zerobase.musinsamonitor.crawler.dto.CrawledResult;
import com.zerobase.musinsamonitor.dto.ProductResponseDto;
import com.zerobase.musinsamonitor.service.CrawlingService;
import com.zerobase.musinsamonitor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CrawlingService crawlingService;

    @GetMapping("/api/product/brand/list")
    public Page<String> findBrandList(){
        return productService.findAllBrand(PageRequest.of(0, 20, Sort.by("brand")));
    }

    @GetMapping("/api/product/brand")
    public Page<ProductResponseDto> findBrandList(@RequestParam String brandName){
        return productService.findByBrand(brandName, PageRequest.of(0, 20, Sort.by("productName")));
    }

    @PostMapping("/api/product/save")
    public void saveCrawlingDate(){
        Crawler crawler = new MusinsaCrawler();
        CrawledResult crawledResult = crawler.crawling();
        crawlingService.save(crawledResult);
//        crawlingService.save(null);
    }
}
