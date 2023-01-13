package com.zerobase.musinsamonitor.service;

import static com.zerobase.musinsamonitor.exception.ErrorCode.DO_NOT_SUPPORTED_CATEGORY;
import static com.zerobase.musinsamonitor.exception.ErrorCode.NON_EXISTENT_BRAND;
import static com.zerobase.musinsamonitor.exception.ErrorCode.NON_EXISTENT_PRODUCT;

import com.zerobase.musinsamonitor.crawler.constants.Category;
import com.zerobase.musinsamonitor.dto.ProductAndPricesResponseDto;
import com.zerobase.musinsamonitor.dto.ProductResponseDto;
import com.zerobase.musinsamonitor.exception.CustomException;
import com.zerobase.musinsamonitor.repository.ProductJpaRepository;
import com.zerobase.musinsamonitor.repository.ProductQueryRepository;
import com.zerobase.musinsamonitor.repository.entity.Price;
import com.zerobase.musinsamonitor.repository.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
            throw new CustomException(NON_EXISTENT_BRAND);
        }

        return this.productQueryRepository.findByBrand(brandName, pageable);
    }

    public Page<ProductResponseDto> findTodayProductsByCategory(Category category, String period, Pageable pageable) {
        if (!Category.getCategoryList().contains(category.getCategory())) {
            throw new CustomException(DO_NOT_SUPPORTED_CATEGORY);
        }
        return productQueryRepository.findTodayProductByCategory(category, period, pageable);
    }

    public ProductAndPricesResponseDto findProductAndPrice(int productId) {
        boolean exists = productJpaRepository.existsByProductId(productId);
        if (!exists) {
            throw new CustomException(NON_EXISTENT_PRODUCT);
        }

        Product product = this.productQueryRepository.findProductById(productId);
        List<Price> priceList = this.productQueryRepository.findPriceByProductId(productId);
        return new ProductAndPricesResponseDto(product, priceList);
    }
}
