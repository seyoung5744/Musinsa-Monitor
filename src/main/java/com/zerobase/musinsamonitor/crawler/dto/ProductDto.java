package com.zerobase.musinsamonitor.crawler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private int productId;

    private String productName;

    private String productUrl;

    private String productIcon;

    private String img;

    private String brand;

    private String brandUrl;

    private int ranking;

    private String category;

    private int price;

    private int coupon;

    private float rating;

    private int ratingCount;
}
