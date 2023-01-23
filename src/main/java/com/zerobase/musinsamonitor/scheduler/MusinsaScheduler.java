package com.zerobase.musinsamonitor.scheduler;

import com.zerobase.musinsamonitor.crawler.Crawler;
import com.zerobase.musinsamonitor.crawler.MusinsaCrawler;
import com.zerobase.musinsamonitor.crawler.constants.Category;
import com.zerobase.musinsamonitor.crawler.dto.CrawledResult;
import com.zerobase.musinsamonitor.repository.PriceRepository;
import com.zerobase.musinsamonitor.repository.ProductJpaRepository;
import com.zerobase.musinsamonitor.repository.TodayDiscountedProductRepository;
import com.zerobase.musinsamonitor.repository.entity.Price;
import com.zerobase.musinsamonitor.repository.entity.Product;
import com.zerobase.musinsamonitor.repository.entity.TodayDiscountedProduct;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class MusinsaScheduler {

    private final ProductJpaRepository productJpaRepository;

    private final PriceRepository priceRepository;

    private final TodayDiscountedProductRepository todayDiscountedProductRepository;


    @Scheduled(cron = "${scheduler.crawling.time}")
    public void musinsaScheduling() {
        Crawler crawler = new MusinsaCrawler();
        for (String category : Category.getCategoryList()) {
            log.info("insert new category -> " + category);
            this.save(
                crawler.crawling(category)
            );
        }
    }

    @Transactional
    public void save(CrawledResult crawledResult) {

        List<Product> productEntities = crawledResult.getProducts().stream()
            .map(Product::new)
            .collect(Collectors.toList());

        List<Product> products = this.productJpaRepository.saveAll(productEntities);

        List<Price> priceEntities = crawledResult.getPrices().stream()
            .map(Price::new)
            .collect(Collectors.toList());

        this.priceRepository.saveAll(priceEntities);

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
