package com.zerobase.musinsamonitor.service;

import com.zerobase.musinsamonitor.crawler.dto.CrawledResult;
import com.zerobase.musinsamonitor.repository.PriceRepository;
import com.zerobase.musinsamonitor.repository.ProductJpaRepository;
import com.zerobase.musinsamonitor.repository.ProductQueryRepository;
import com.zerobase.musinsamonitor.repository.TodayDiscountedProductRepository;
import com.zerobase.musinsamonitor.repository.entity.Price;
import com.zerobase.musinsamonitor.repository.entity.Product;
import com.zerobase.musinsamonitor.repository.entity.TodayDiscountedProduct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final ProductJpaRepository productJpaRepository;

    private final PriceRepository priceRepository;

    private final TodayDiscountedProductRepository todayDiscountedProductRepository;

    public void save(CrawledResult crawledResult) {

        // 상품 : price, product_icon, product_name, ranking, rating, rating_count 변경 가능성 있음
        List<Product> productEntities = crawledResult.getProducts().stream()
            .map(Product::new)
            .collect(Collectors.toList());

        List<Product> products = this.productJpaRepository.saveAll(productEntities);

        List<Price> priceEntities = crawledResult.getPrices().stream()
            .map(Price::new)
            .collect(Collectors.toList());

        List<Price> prices = this.priceRepository.saveAll(priceEntities);

        if (!ObjectUtils.isEmpty(products)) {

            List<TodayDiscountedProduct> todayDiscountedProducts = products.stream()
                .filter(e -> e.getCoupon() > 0)
                .map(e -> new TodayDiscountedProduct(
                    e
                    , e.getPrice() - e.getCoupon()
                    , (int) ((double) e.getCoupon() / e.getPrice() * 100)
                    , e.getUpdatedAt()
                )).collect(Collectors.toList());

            this.todayDiscountedProductRepository.saveAll(todayDiscountedProducts);
        }
    }
}
