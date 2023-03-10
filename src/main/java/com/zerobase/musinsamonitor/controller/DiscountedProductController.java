package com.zerobase.musinsamonitor.controller;


import com.zerobase.musinsamonitor.model.responsedto.ProductAndDiscountResponseDto;
import com.zerobase.musinsamonitor.service.DiscountedProductService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("금일 할인 상품 리스트")
    public Page<ProductAndDiscountResponseDto> findTodayDiscountedProducts(Pageable pageable) {
        return discountedProductService.findTodayDiscountedProducts(pageable);
    }
}
