package com.zerobase.musinsamonitor.service;

import com.zerobase.musinsamonitor.crawler.dto.CrawledResult;
import com.zerobase.musinsamonitor.crawler.dto.ProductDto;
import com.zerobase.musinsamonitor.repository.PriceRepository;
import com.zerobase.musinsamonitor.repository.ProductJpaRepository;
import com.zerobase.musinsamonitor.repository.ProductQueryRepository;
import com.zerobase.musinsamonitor.repository.entiry.Price;
import com.zerobase.musinsamonitor.repository.entiry.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryRepository productQueryRepository;

    private final PriceRepository priceRepository;

    public void save(CrawledResult crawledResult) {

        // 상품 : price, product_icon, product_name, ranking, rating, rating_count 변경 가능성 있음
        List<Product> productEntities = crawledResult.getProducts().stream()
            .map(Product::new)
            .collect(Collectors.toList());

        this.productJpaRepository.saveAll(productEntities);

        List<Price> priceEntities = crawledResult.getPrices().stream()
            .map(Price::new)
            .collect(Collectors.toList());

        this.priceRepository.saveAll(priceEntities);
    }
}
