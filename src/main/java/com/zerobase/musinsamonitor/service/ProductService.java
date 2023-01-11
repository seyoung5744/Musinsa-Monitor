package com.zerobase.musinsamonitor.service;

import com.zerobase.musinsamonitor.crawler.constants.Category;
import com.zerobase.musinsamonitor.dto.ProductResponseDto;
import com.zerobase.musinsamonitor.repository.ProductJpaRepository;
import com.zerobase.musinsamonitor.repository.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryRepository productQueryRepository;


    public Page<ProductResponseDto> findProductsByBrand(String brandName, Pageable pageable) {
        boolean exists = productJpaRepository.existsByBrand(brandName);
        if (!exists) {
            throw new RuntimeException("존재하지 않는 브랜드입니다.");
        }

        return this.productQueryRepository.findByBrand(brandName, pageable);
    }

    public Page<ProductResponseDto> findTodayProductsByCategory(Category category, String period, Pageable pageable) {
        return productQueryRepository.findTodayProductByCategory(category, period, pageable);
    }
}
