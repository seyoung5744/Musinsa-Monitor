package com.zerobase.musinsamonitor.controller;


import com.zerobase.musinsamonitor.dto.ProductAndDiscountResponseDto;
import com.zerobase.musinsamonitor.service.DiscountedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class DiscountedProductController {

    private final DiscountedProductService discountedProductService;

    @GetMapping("/product/discount/today-list")
    public Page<ProductAndDiscountResponseDto> findTodayDiscountedProducts(Pageable pageable) {
        return discountedProductService.findTodayDiscountedProducts(pageable);
    }
}
