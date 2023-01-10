package com.zerobase.musinsamonitor.crawler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceDto {
    private int productId;

    private int ranking;

    private int price;

    private int delPrice;

    private int coupon;
}
