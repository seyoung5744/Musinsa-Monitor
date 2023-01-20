package com.zerobase.musinsamonitor.dto;

import com.zerobase.musinsamonitor.repository.entity.Cart;
import com.zerobase.musinsamonitor.repository.entity.Product;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartResponseDto {

    private final int ranking;
    private final int productId;
    private final String img;
    private final String productName;
    private final String productUrl;
    private final String brand;
    private final String brandUrl;
    private final LocalDateTime createdAt;
    private final String category;
    private final int price;

    @Builder
    public CartResponseDto(Cart cart) {
        Product product = cart.getProduct();
        this.ranking = product.getRanking();
        this.productId = product.getProductId();
        this.img = product.getImg();
        this.productName = product.getProductName();
        this.productUrl = product.getProductUrl();
        this.brand = product.getBrand();
        this.brandUrl = product.getBrandUrl();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.createdAt = cart.getCreatedAt();
    }

}
