package com.zerobase.musinsamonitor.dto;

import com.zerobase.musinsamonitor.repository.entity.Product;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private final int ranking;
    private final int productId;
    private final String img;
    private final String productName;
    private final String productUrl;
    private final String brand;
    private final String brandUrl;
    private final LocalDate updatedAt;
    private final String category;
    private final int price;

    @Builder
    public ProductResponseDto(Product product) {
        this.ranking = product.getRanking();
        this.productId = product.getProductId();
        this.img = product.getImg();
        this.productName = product.getProductName();
        this.productUrl = product.getProductUrl();
        this.brand = product.getBrand();
        this.brandUrl = product.getBrandUrl();
        this.updatedAt = product.getUpdatedAt().toLocalDate();
        this.category = product.getCategory();
        this.price = product.getPrice();
    }

}
