package com.zerobase.musinsamonitor.model.responsedto;

import com.zerobase.musinsamonitor.repository.entity.TodayDiscountedProduct;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAndDiscountResponseDto {

    private final String category;
    private final int productId;
    private final String productName;
    private final String productUrl;
    private final String brand;
    private final String brandUrl;
    private final int ranking;
    private final String img;
    private final int price;
    private final int discountPrice;
    private final double discountRate;
    private final LocalDateTime createdAt;



    @Builder
    public ProductAndDiscountResponseDto(TodayDiscountedProduct entity) {
        this.category = entity.getProduct().getCategory();
        this.productId = entity.getProduct().getProductId();
        this.productName = entity.getProduct().getProductName();
        this.productUrl = entity.getProduct().getProductUrl();
        this.brand = entity.getProduct().getBrand();
        this.brandUrl = entity.getProduct().getBrandUrl();
        this.ranking = entity.getProduct().getRanking();
        this.img = entity.getProduct().getImg();
        this.price = entity.getProduct().getPrice();
        this.discountPrice = entity.getDiscountPrice();
        this.discountRate = entity.getDiscountRate();
        this.createdAt = entity.getCreatedAt();
    }

}
