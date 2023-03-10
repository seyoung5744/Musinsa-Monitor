package com.zerobase.musinsamonitor.controller;

import com.zerobase.musinsamonitor.crawler.Crawler;
import com.zerobase.musinsamonitor.crawler.MusinsaCrawler;
import com.zerobase.musinsamonitor.crawler.constants.Category;
import com.zerobase.musinsamonitor.crawler.dto.CrawledResult;
import com.zerobase.musinsamonitor.model.responsedto.ProductAndPricesResponseDto;
import com.zerobase.musinsamonitor.model.responsedto.ProductResponseDto;
import com.zerobase.musinsamonitor.scheduler.MusinsaScheduler;
import com.zerobase.musinsamonitor.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/brand")
    @ApiOperation("브랜드 별 상품 리스트")
    public ResponseEntity<Page<ProductResponseDto>> findProductsByBrand(@RequestParam String brandName,
        Pageable pageable) {
        Page<ProductResponseDto> results = productService.findProductsByBrand(brandName, pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/product/best")
    @ApiOperation("금일 인기 상품 리스트")
    public ResponseEntity<Page<ProductResponseDto>> findTodayProductsByCategory(@RequestParam Category category
        , @RequestParam String period
        , Pageable pageable
    ) {
        Page<ProductResponseDto> results = productService.findTodayProductsByCategory(category, period,
            pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/product/detail")
    @ApiOperation("상품 상세 정보")
    public ResponseEntity<ProductAndPricesResponseDto> findProductAndPrice(@RequestParam int productId) {
        ProductAndPricesResponseDto result = productService.findProductAndPrice(productId);
        return ResponseEntity.ok(result);
    }
}
