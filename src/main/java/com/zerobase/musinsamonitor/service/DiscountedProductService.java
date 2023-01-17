package com.zerobase.musinsamonitor.service;

import com.zerobase.musinsamonitor.dto.ProductAndDiscountResponseDto;
import com.zerobase.musinsamonitor.repository.TodayDiscountedProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountedProductService {

    private final TodayDiscountedProductQueryRepository todayDiscountedProductQueryRepository;

    public Page<ProductAndDiscountResponseDto> findTodayDiscountedProducts(Pageable pageable) {
        return todayDiscountedProductQueryRepository.findTodayDiscountedProducts(pageable);
    }
}
